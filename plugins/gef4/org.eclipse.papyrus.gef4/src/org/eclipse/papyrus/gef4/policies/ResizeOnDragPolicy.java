/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.policies;

import java.util.List;
import java.util.function.Consumer;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.gef4.geometry.planar.Dimension;
import org.eclipse.gef4.mvc.fx.parts.AbstractFXSegmentHandlePart;
import org.eclipse.gef4.mvc.fx.policies.CursorSupport;
import org.eclipse.gef4.mvc.fx.policies.IFXOnDragPolicy;
import org.eclipse.gef4.mvc.fx.tools.FXClickDragTool;
import org.eclipse.gef4.mvc.models.SelectionModel;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gef4.mvc.policies.AbstractInteractionPolicy;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.gef4.model.ChangeBoundsModel;
import org.eclipse.papyrus.gef4.parts.NotationContentPart;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

import com.google.common.reflect.TypeToken;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

@SuppressWarnings("serial")
public class ResizeOnDragPolicy extends AbstractInteractionPolicy<Node> implements IFXOnDragPolicy {

	protected static final int NORTH_WEST = 0;

	protected static final int NORTH_EAST = 1;

	protected static final int SOUTH_EAST = 2;

	protected static final int SOUTH_WEST = 3;
	
	private CursorSupport cursorSupport = new CursorSupport(this);

	@Override
	public void drag(final MouseEvent e, final Dimension delta) {

		propagate(e, delta, policy -> policy.drag(e, delta));

		final ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);
		final Bounds newBounds = computeNewBounds(getBounds(), delta);
		if (newBounds == null) { // If the host element doesn't have bounds (e.g. Connections)
			return;
		}
		boundsModel.addManagedElement(getPrimaryHost(), newBounds);
	}

	protected Bounds computeNewBounds(final Bounds bounds, final Dimension delta) {
		if (bounds == null) {
			return null;
		}

		final Bounds newBounds = NotationFactory.eINSTANCE.createBounds();

		final int xOffset = toPixels(delta.getWidth());
		final int yOffset = toPixels(delta.getHeight());

		if (xOffset == 0 && yOffset == 0) {
			return null;
		}

		int x, y, width, height;

		switch (getAnchorDirection()) {
		case NORTH_WEST: // Change all
			x = bounds.getX() + xOffset;
			y = bounds.getY() + yOffset;

			width = getCurrentWidth() - xOffset;
			height = getCurrentHeight() - yOffset;
			break;
		case NORTH_EAST: // Only change Y position and size
			x = bounds.getX();
			y = bounds.getY() + yOffset;

			width = getCurrentWidth() + xOffset;
			height = getCurrentHeight() - yOffset;
			break;
		case SOUTH_EAST: // Only change size
			x = bounds.getX();
			y = bounds.getY();

			width = getCurrentWidth() + xOffset;
			height = getCurrentHeight() + yOffset;
			break;
		case SOUTH_WEST: // Only change X position and size
			x = bounds.getX() + xOffset;
			y = bounds.getY();

			width = getCurrentWidth() - xOffset;
			height = getCurrentHeight() + yOffset;
			break;
		default:
			return null;
		}

		newBounds.setX(x);
		newBounds.setY(y);
		newBounds.setWidth(Math.max(getHostMinWidth(), width));
		newBounds.setHeight(Math.max(getHostMinHeight(), height));

		return newBounds;
	}

	// FIXME Improve specification of MinWidth/MinHeight before implementing
	private int getHostMinWidth() {
		// if (getPrimaryHost() instanceof ContainerContentPart) {
		// return ((ContainerContentPart<?, ?>) getPrimaryHost()).getMinWidth();
		// }
		return -1;
	}

	private int getHostMinHeight() {
		// if (getPrimaryHost() instanceof ContainerContentPart) {
		// return ((ContainerContentPart<?, ?>) getPrimaryHost()).getMinHeight();
		// }
		return -1;
	}

	protected int getAnchorDirection() {
		if (getHost() instanceof AbstractFXSegmentHandlePart) {
			return ((AbstractFXSegmentHandlePart<?>) getHost()).getSegmentIndex();
		}

		return SOUTH_EAST; // Arbitrary Default (Everyone resizes elements from the South East, right?)
	}



	@Override
	public void press(final MouseEvent e) {
		// Nothing
	}

	protected final int toPixels(final double pos) {
		return (int) Math.round(pos);
	}

	@Override
	public void release(final MouseEvent e, final Dimension delta) {

		propagate(e, delta, policy -> policy.release(e, delta));

		final Bounds bounds = getBounds();
		if (bounds == null) {
			return;
		}

		final ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);

		final Bounds newBounds = computeNewBounds(bounds, delta);
		if (newBounds == null) {
			boundsModel.removeManagedElement(getPrimaryHost());
			return;
		}

		final SetRequest setXRequest = new SetRequest(bounds, NotationPackage.Literals.LOCATION__X, newBounds.getX());
		final SetRequest setYRequest = new SetRequest(bounds, NotationPackage.Literals.LOCATION__Y, newBounds.getY());
		final SetRequest setWidthRequest = new SetRequest(bounds, NotationPackage.Literals.SIZE__WIDTH, newBounds.getWidth());
		final SetRequest setHeightRequest = new SetRequest(bounds, NotationPackage.Literals.SIZE__HEIGHT, newBounds.getHeight());


		final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(bounds);
		if (provider != null) {
			final CompositeCommand resizeCommand = new CompositeCommand("Resize element");
			resizeCommand.add(provider.getEditCommand(setXRequest));
			resizeCommand.add(provider.getEditCommand(setYRequest));
			resizeCommand.add(provider.getEditCommand(setWidthRequest));
			resizeCommand.add(provider.getEditCommand(setHeightRequest));

			AdapterFactoryEditingDomain.getEditingDomainFor(bounds).getCommandStack().execute(new GMFtoEMFCommandWrapper(resizeCommand));
		}

		boundsModel.removeManagedElement(getPrimaryHost());
	}

	protected int getCurrentWidth() {
		final Node visual = getPrimaryHost().getVisual();
		final javafx.geometry.Bounds local = visual.getLayoutBounds();

		return toPixels(local.getWidth());
	}

	protected int getCurrentHeight() {
		final Node visual = getPrimaryHost().getVisual();
		final javafx.geometry.Bounds local = visual.getLayoutBounds();

		return toPixels(local.getHeight());
	}

	protected IVisualPart<Node, ? extends Node> getAnchorageHost() {
		return getHost().getAnchoragesUnmodifiable().keys().iterator().next();
	}

	protected IVisualPart<Node, ? extends Node> getPrimaryHost() {
		IVisualPart<Node, ? extends Node> host = getAnchorageHost();
		if (host instanceof NotationContentPart) {
			host = ((NotationContentPart<?, ?>) host).getPrimaryContentPart();
		}
		return host;
	}

	protected void propagate(final MouseEvent e, final Dimension delta, final Consumer<IFXOnDragPolicy> actionToPropagate) {
		final SelectionModel<Node> selectionModel = getHost().getRoot().getViewer().getAdapter(new TypeToken<SelectionModel<Node>>() {
		});

		List<IContentPart<Node, ? extends Node>> selection = selectionModel.getSelectionUnmodifiable();
		if (selection.size() > 1) {

			// If I'm the main receiver of the event, I propagate it to other selected elements
			// If I'm not the main receiver, do nothing; someone else will do the propagation
			if (e.getTarget() == getHost().getVisual()) {

				for (final IContentPart<Node, ? extends Node> selectedPart : selection) {
					if (selectedPart != getPrimaryHost()) {
						for (final IFXOnDragPolicy dragPolicy : selectedPart.getAdapters(FXClickDragTool.ON_DRAG_POLICY_KEY).values()) {
							actionToPropagate.accept(dragPolicy);
						}
					}
				}

			}
		}
	}

	protected Bounds getBounds() {
		final IVisualPart<?, ?> host = getPrimaryHost();

		if (host == null) {
			return null;
		}

		final View hostView = NotationHelper.findView(host);
		if (hostView instanceof org.eclipse.gmf.runtime.notation.Node) {
			final LayoutConstraint layout = ((org.eclipse.gmf.runtime.notation.Node) hostView).getLayoutConstraint();
			if (layout instanceof Bounds) {
				return (Bounds) layout;
			}
		}

		return null;
	}

	@Override
	public void hideIndicationCursor() {
		getCursorSupport().restoreCursor();
	}

	@Override
	public boolean showIndicationCursor(KeyEvent event) {
		return false;
	}

	@Override
	public boolean showIndicationCursor(MouseEvent event) {
		getCursorSupport().storeAndReplaceCursor(getCursor());
		return true;
	}
	
	protected CursorSupport getCursorSupport(){
		return cursorSupport;
	}

	private Cursor getCursor() {
		switch (getAnchorDirection()) {
		case NORTH_EAST:
			return Cursor.NE_RESIZE;
		case NORTH_WEST:
			return Cursor.NW_RESIZE;
		case SOUTH_EAST:
			return Cursor.SE_RESIZE;
		case SOUTH_WEST:
			return Cursor.SW_RESIZE;
		default:
			return Cursor.DEFAULT;
		}
	}

}
