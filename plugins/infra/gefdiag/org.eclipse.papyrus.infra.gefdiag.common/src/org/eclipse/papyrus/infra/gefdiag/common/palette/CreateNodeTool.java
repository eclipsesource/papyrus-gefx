/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gefdiag.common.palette;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.mvc.fx.gestures.IGesture;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.CursorSupport;
import org.eclipse.gef.mvc.fx.handlers.IHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnClickHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnDragHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnHoverHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnStrokeHandler;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.parts.PartUtils;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.gef4.gmf.editor.handlers.CreateNodeHandler;
import org.eclipse.papyrus.gef4.tools.AbstracTool;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * <p>
 * A Tool that supports creation of a Node by clicking on a target (To create at a given
 * location with a default size), or by Dragging a rectangular area (To create at
 * the given location, with a custom size).
 * </p>
 * <p>
 * The tool supports several element type IDs. In most cases, this is an XOR situation
 * (0..1 tool will match the target). If more than one element type ID matches the target,
 * the tool will use the first matching element type (TODO: Ultimately, the tool should offer the
 * choice to the user, e.g. via a context menu at the end of the interaction)
 * </p>
 */
public class CreateNodeTool extends AbstracTool {

	private Collection<String> elementIds;

	private CreateNodeToolHandler activeHandler;

	@Inject
	private TransactionalEditingDomain editingDomain;

	public CreateNodeTool(Collection<String> elementIds) {
		this.elementIds = elementIds;
	}

	@Override
	public <T extends IHandler> List<? extends T> resolve(IGesture contextGesture, Node target, IViewer viewer, Class<T> handlerType) {
		if (!isActive()) {
			return null;
		}

		if (handlerType.isAssignableFrom(CreateNodeToolHandler.class)) {
			IVisualPart<? extends Node> iVisualPart;
			if (target == viewer.getCanvas()) {
				iVisualPart = viewer.getRootPart();
			} else {
				iVisualPart = PartUtils.retrieveVisualPart(viewer, target);

				assert iVisualPart != null : "Unable to find a part for node " + target + " (" + target.getStyleClass() + ")";

				// FIXME: In which case can this be null? It seems we may find a Pane
				// that is neither the Diagram Part nor the RootPart
				if (iVisualPart == null) {
					iVisualPart = viewer.getRootPart();
				}
			}

			if (iVisualPart != null) {
				CreateNodeToolHandler handler;
				if (activeHandler != null && (activeHandler.isDragInProgress() || iVisualPart == activeHandler.getHost())) {
					handler = activeHandler;
				} else {
					handler = new CreateNodeToolHandler(iVisualPart, elementIds);
				}

				if (handlerType.isInstance(handler)) {
					T clickHandler = handlerType.cast(handler);
					setActiveHandler(handler);
					return Collections.singletonList(clickHandler);
				}
			}
		}
		resetActiveHandler();
		return null;
	}

	@Override
	protected void doDeactivate() {
		resetActiveHandler();
	}

	private void setActiveHandler(CreateNodeToolHandler newHandler) {
		if (newHandler == activeHandler) {
			return;
		}
		resetActiveHandler();
		this.activeHandler = newHandler;
	}

	private void resetActiveHandler() {
		if (activeHandler != null) {
			activeHandler.reset();
			activeHandler = null;
		}
	}

	/**
	 * <p>
	 * This handler is to the {@link CreateNodeTool} and a single {@link IVisualPart} (Typically the one
	 * hovered by the tool at the beginning of a mouse interaction).
	 * </p>
	 * <p>
	 * This handler delegates to the part's {@link CreateNodeHandler} if there is one.
	 * </p>
	 */
	class CreateNodeToolHandler extends AbstractHandler implements IOnClickHandler, IOnDragHandler, IOnStrokeHandler, IOnHoverHandler {

		private Collection<String> elementIds;

		// XXX: Since CreateNodeToolHandler is already associated to a single VisualPart,
		// do we need to store the partHandler that was used to display feedback?
		private CreateNodeHandler partHandler;

		private boolean dragInProgress;

		public CreateNodeToolHandler(IVisualPart<? extends Node> iVisualPart, Collection<String> elementIds) {
			setAdaptable(iVisualPart);
			this.elementIds = elementIds;
		}

		/**
		 * @return
		 */
		private boolean isDragInProgress() {
			return dragInProgress;
		}

		@Override
		public void hover(MouseEvent e) {
			CreateNodeHandler newPartHandler = getPartHandler();
			updateFeedback(newPartHandler, e, null);
		}

		@Override
		public void startDrag(MouseEvent e) {
			if (e.getButton() != MouseButton.PRIMARY) {
				return;
			}
			// Mouse press
			CreateNodeHandler newPartHandler = getPartHandler();
			updateFeedback(newPartHandler, e, null);
			this.dragInProgress = true;
		}

		@Override
		public void drag(MouseEvent e, Dimension delta) {
			if (!dragInProgress || e.isStillSincePress()) {
				return;
			}

			CreateNodeHandler newPartHandler = getPartHandler();
			updateFeedback(newPartHandler, e, delta);
		}

		@Override
		public void abortDrag() {
			dragInProgress = false;
			deactivate();
		}

		private void updateFeedback(CreateNodeHandler newPartHandler, MouseEvent e, Dimension delta) {
			if (partHandler != null && newPartHandler != partHandler) {
				partHandler.removeFeedback();
			}
			if (newPartHandler != null) {
				// FIXME Retrieve the correct coordinates & size
				partHandler = newPartHandler;
				Bounds boundsInTarget = getBoundsInTarget(e, delta);
				newPartHandler.showFeedback(new Point2D(boundsInTarget.getMinX(), boundsInTarget.getMinY()), new Dimension(boundsInTarget.getWidth(), boundsInTarget.getHeight()), elementIds);
			}
		}

		@Override
		public void endDrag(MouseEvent e, Dimension delta) {
			if (!dragInProgress) {
				return;
			}
			if (e.getButton() != MouseButton.PRIMARY) {
				return;
			}
			doCreate(e, delta);
			dragInProgress = false;
			reset();
			if (!e.isShortcutDown()) {
				deactivate();
			}
		}

		@Override
		public void hideIndicationCursor() {
			getHost().getViewer().getAdapter(CursorSupport.class).restoreCursor();
		}

		@Override
		public boolean showIndicationCursor(MouseEvent event) {
			getHost().getViewer().getAdapter(CursorSupport.class).storeAndReplaceCursor(Cursor.CLOSED_HAND);
			return true;
		}

		/**
		 * @see org.eclipse.gef.mvc.fx.handlers.IOnStrokeHandler#release(javafx.scene.input.KeyEvent)
		 *
		 * @param event
		 */
		@Override
		public void release(KeyEvent event) {
			if (event.getCode() == KeyCode.ESCAPE) {
				deactivate();
			}
		}

		@Override
		public void finalRelease(KeyEvent event) {
			if (event.getCode() == KeyCode.ESCAPE) {
				deactivate();
			}
		}

		private void reset() {
			System.out.println("Do reset");
			dragInProgress = false;
			if (partHandler != null) {
				partHandler.removeFeedback();
			}
		}

		private CreateNodeHandler getPartHandler() {
			return getAdaptable().getAdapter(CreateNodeHandler.class);
		}

		@Override
		public boolean showIndicationCursor(KeyEvent event) {
			return false;
		}

		@Override
		public void click(MouseEvent e) {
			// Do nothing on click events; we handle press (startDrag()) and release (endDrag()) separately
			return;
		}

		@Override
		public void abortPress() {
			// Nothing
		}

		@Override
		public void initialPress(KeyEvent event) {
			// Nothing
		}

		@Override
		public void press(KeyEvent event) {
			// Nothing
		}

		@Override
		public void hoverIntent(Node hoverIntent) {
			// Nothing
		}

		protected void doCreate(MouseEvent e, Dimension delta) {
			CreateNodeHandler newPartHandler = getPartHandler();

			if (newPartHandler != null) {
				Bounds boundsInTarget = getBoundsInTarget(e, delta);
				ICommand command = newPartHandler.create(new Point2D(boundsInTarget.getMinX(), boundsInTarget.getMinY()), new Dimension(boundsInTarget.getWidth(), boundsInTarget.getHeight()), elementIds);
				if (command != null) {
					editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(command));
				}
			}
		}

		protected Bounds getBoundsInTarget(MouseEvent e, Dimension dragDelta) {
			double sceneX = e.getSceneX();
			double sceneY = e.getSceneY();
			double width = 0, height = 0;
			if (dragDelta != null) {
				width = dragDelta.getWidth();
				height = dragDelta.getHeight();
				sceneX -= width;
				sceneY -= height;
			}
			Bounds boundsInScene = new BoundingBox(sceneX, sceneY, width, height);
			Bounds boundInTarget = getHost().getVisual().sceneToLocal(boundsInScene);

			return boundInTarget;
		}
	}

}
