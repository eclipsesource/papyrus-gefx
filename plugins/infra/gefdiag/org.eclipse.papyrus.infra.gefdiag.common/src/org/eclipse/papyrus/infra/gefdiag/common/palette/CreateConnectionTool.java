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
import org.eclipse.fx.core.log.Logger;
import org.eclipse.fx.core.log.LoggerCreator;
import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.mvc.fx.gestures.IGesture;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.CursorSupport;
import org.eclipse.gef.mvc.fx.handlers.IHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnClickHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnDragHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnHoverHandler;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.parts.PartUtils;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.gef4.gmf.editor.handlers.CreateConnectionHandler;
import org.eclipse.papyrus.gef4.tools.AbstracTool;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * <p>
 * A Tool that supports creation of Connections via a click on the source and target,
 * or via drag and drop between a source and a target.
 * </p>
 */
public class CreateConnectionTool extends AbstracTool {

	private static final Logger logger = LoggerCreator.createLogger(CreateConnectionTool.class);

	private Collection<String> elementIds;

	private CreateConnectionToolHandler activeHandler;

	@Inject
	private TransactionalEditingDomain editingDomain;

	private IContentPart<?> sourcePart;

	public CreateConnectionTool(Collection<String> elementIds) {
		this.elementIds = elementIds;
	}

	@Override
	public <T extends IHandler> List<? extends T> resolve(IGesture contextGesture, Node target, IViewer viewer, Class<T> handlerType) {
		if (!isActive()) {
			return null;
		}

		if (handlerType.isAssignableFrom(CreateConnectionToolHandler.class)) {
			IVisualPart<?> visualPart;
			if (target == viewer.getCanvas()) {
				visualPart = viewer.getRootPart();
			} else {
				visualPart = PartUtils.retrieveVisualPart(viewer, target);

				assert visualPart != null : "Unable to find a part for node " + target + " (" + target.getStyleClass() + ")";

				// FIXME: In which case can this be null? It seems we may find a Pane
				// that is neither the Diagram Part nor the RootPart
				if (visualPart == null) {
					visualPart = viewer.getRootPart();
				}
			}


			if (visualPart != null) {
				CreateConnectionToolHandler handler;
				if (activeHandler != null && (sourcePart != null || visualPart == activeHandler.getHost())) {
					handler = activeHandler;
				} else {
					handler = new CreateConnectionToolHandler(visualPart);
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
		if (activeHandler != null) {
			activeHandler.getHost().getViewer().getAdapter(CursorSupport.class).restoreCursor();
		}
		resetActiveHandler();
		sourcePart = null;
	}

	private void setActiveHandler(CreateConnectionToolHandler newHandler) {
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

	class CreateConnectionToolHandler extends AbstractHandler implements IOnClickHandler, IOnDragHandler, IOnHoverHandler {

		private CreateConnectionHandler sourcePartHandler;

		public CreateConnectionToolHandler(IVisualPart<?> iVisualPart) {
			setAdaptable(iVisualPart);
		}

		@Override
		public void abortDrag() {
			CreateConnectionTool.this.deactivate();
		}

		@Override
		public void startDrag(MouseEvent e) {
			sourcePartHandler = getPartHandler(getHost());
		}

		@Override
		public void drag(MouseEvent e, Dimension delta) {
			if (e.isStillSincePress()) {
				return;
			}

			IVisualPart<?> dragTarget = getDragTarget(e);

			if (CreateConnectionTool.this.sourcePart == null) {
				if (dragTarget instanceof IContentPart) {
					sourcePart = (IContentPart<?>) dragTarget;
				} else {
					CreateConnectionTool.this.deactivate();
				}
			} else {
				updateFeedback(e);
			}
		}

		@Override
		public void endDrag(MouseEvent e, Dimension delta) {
			if (e.isStillSincePress()) {
				// Simple click on source or target
				if (sourcePart == null) {
					if (getHost() instanceof IContentPart) {
						sourcePart = (IContentPart<?>) getHost();
					} else {
						CreateConnectionTool.this.deactivate();
					}
				} else {
					createAndDeactivate(e, delta);
				}
			} else {
				// Release after a drag
				if (sourcePart == null) {
					CreateConnectionTool.this.deactivate();
				} else {
					createAndDeactivate(e, delta);
				}
			}
		}

		@Override
		public void hover(MouseEvent e) {
			updateFeedback(e);
		}

		protected void updateFeedback(MouseEvent e) {
			if (sourcePart == null) {
				// TODO Feedback: Am I a valid connection source?
			} else if (sourcePartHandler != null) {
				IVisualPart<?> targetPart = getDragTarget(e);
				if (targetPart instanceof IContentPart) {
					sourcePartHandler.showFeedback(getLocation(e), (IContentPart<?>) targetPart, elementIds);
				} else {
					sourcePartHandler.showFeedback(getLocation(e), elementIds);
				}
				// TODO Check command.canExecute() and show different cursor
			}
		}

		protected IVisualPart<?> getDragTarget(MouseEvent e) {
			Node node = e.getPickResult().getIntersectedNode();
			if (node != null) {
				return PartUtils.retrieveVisualPart(getHost().getViewer(), node);
			}
			return null;
		}

		@Override
		public void hideIndicationCursor() {
			getHost().getViewer().getAdapter(CursorSupport.class).restoreCursor();
		}

		@Override
		public boolean showIndicationCursor(MouseEvent event) {
			CursorSupport adapter = getHost().getViewer().getAdapter(CursorSupport.class);
			adapter.storeAndReplaceCursor(Cursor.CROSSHAIR);
			return adapter.isCursorChanged();
		}

		@Override
		public boolean showIndicationCursor(KeyEvent event) {
			CursorSupport adapter = getHost().getViewer().getAdapter(CursorSupport.class);
			adapter.storeAndReplaceCursor(Cursor.CROSSHAIR);
			return adapter.isCursorChanged();
		}

		protected void createAndDeactivate(MouseEvent e, Dimension delta) {
			try {
				doCreate(e, delta);
			} finally {
				if (!e.isShortcutDown()) {
					CreateConnectionTool.this.deactivate();
				} else {
					// Reset the tool without disabling it
					CreateConnectionTool.this.sourcePart = null;
				}
			}
		}

		private void reset() {
			if (sourcePartHandler != null) {
				sourcePartHandler.removeFeedback();
				sourcePartHandler = null;
			}
		}

		protected void doCreate(MouseEvent e, Dimension delta) {
			if (sourcePart == null) {
				return;
			}
			IVisualPart<?> targetPart = getDragTarget(e);

			if (false == sourcePart instanceof IContentPart || false == targetPart instanceof IContentPart) {
				logger.debug("Invalid step: source or target is not a content part");
				return;
			}

			final IContentPart<?> targetContentPart = (IContentPart<?>) targetPart;

			if (sourcePartHandler != null) {
				Point2D location = getLocation(e);
				ICommand command = sourcePartHandler.getCompleteCommand(location, targetContentPart, elementIds);

				if (command != null) {
					editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(command));
				}
			}
		}

		private Point2D getLocation(MouseEvent e) {
			return new Point2D(e.getSceneX(), e.getSceneY()); // Do not apply conversion; let the feedback decide (Easier for Connection Anchors, which are defined in Scene Coordinates)
		}

		protected CreateConnectionHandler getPartHandler(IVisualPart<?> part) {
			return part.getAdapter(CreateConnectionHandler.class);
		}

		@Override
		public void click(MouseEvent e) {
			// Do nothing on click events; we handle press (startDrag()) and release (endDrag()) separately
			return;
		}

		@Override
		public void hoverIntent(Node hoverIntent) {
			// Nothing
		}
	}

}
