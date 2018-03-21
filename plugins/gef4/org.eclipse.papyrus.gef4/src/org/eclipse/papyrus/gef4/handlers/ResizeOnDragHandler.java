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
package org.eclipse.papyrus.gef4.handlers;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.mvc.fx.handlers.CursorSupport;
import org.eclipse.gef.mvc.fx.handlers.IOnDragHandler;
import org.eclipse.gef.mvc.fx.parts.AbstractSegmentHandlePart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.model.ChangeBoundsModel;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.utils.ModelUtil;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ResizeOnDragHandler extends AbstractMultiSelectionDragHandler implements IOnDragHandler {

	protected static final int NORTH_WEST = 0;

	protected static final int NORTH_EAST = 1;

	protected static final int SOUTH_EAST = 2;

	protected static final int SOUTH_WEST = 3;

	private CursorSupport cursorSupport;

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
		if (getHost() instanceof AbstractSegmentHandlePart) {
			return ((AbstractSegmentHandlePart<?>) getHost()).getSegmentIndex();
		}

		return -1;
	}



	@Override
	public void startDrag(final MouseEvent e) {
		// Nothing
	}

	protected final int toPixels(final double pos) {
		return (int) Math.round(pos);
	}

	@Override
	public void endDrag(final MouseEvent e, final Dimension delta) {

		propagate(e, delta, policy -> policy.endDrag(e, delta));

		final Bounds bounds = getBounds();
		if (bounds == null) {
			return;
		}

		final ChangeBoundsModel boundsModel = ModelUtil.getChangeBoundsModel(getHost());

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

	@Override
	public void abortDrag() {
		propagate(policy -> policy.abortDrag());

		final ChangeBoundsModel boundsModel = ModelUtil.getChangeBoundsModel(getHost());
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

	protected IVisualPart<? extends Node> getAnchorageHost() {
		return getHost().getAnchoragesUnmodifiable().keys().iterator().next();
	}

	@Override
	protected IVisualPart<? extends Node> getPrimaryHost() {
		IVisualPart<? extends Node> host = getAnchorageHost();
		if (host instanceof BaseContentPart) {
			host = ((BaseContentPart<?, ?>) host).getPrimaryContentPart();
		}
		return host;
	}

	protected Bounds getBounds() {
		final IVisualPart<?> host = getPrimaryHost();

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
		if (cursorSupport != null) {
			cursorSupport.restoreCursor();
		}
	}

	@Override
	public boolean showIndicationCursor(KeyEvent event) {
		return false;
	}

	@Override
	public boolean showIndicationCursor(MouseEvent event) {
		if (cursorSupport != null) {
			cursorSupport.storeAndReplaceCursor(getCursor());
		}
		return true;
	}

	/**
	 * @see org.eclipse.gef.common.adapt.IAdaptable.Bound.Impl#setAdaptable(org.eclipse.gef.common.adapt.IAdaptable)
	 *
	 * @param adaptable
	 */
	@Override
	public void setAdaptable(IVisualPart<? extends Node> adaptable) {
		super.setAdaptable(adaptable);
		if (adaptable != null) {
			cursorSupport = adaptable.getViewer().getAdapter(CursorSupport.class);
		}
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
