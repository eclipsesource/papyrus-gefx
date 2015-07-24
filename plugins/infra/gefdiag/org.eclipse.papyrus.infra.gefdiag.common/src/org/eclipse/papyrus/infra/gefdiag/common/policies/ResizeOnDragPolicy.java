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
package org.eclipse.papyrus.infra.gefdiag.common.policies;

import java.util.function.Consumer;

import org.eclipse.gef4.geometry.planar.Dimension;
import org.eclipse.gef4.mvc.fx.parts.AbstractFXSegmentHandlePart;
import org.eclipse.gef4.mvc.fx.policies.AbstractFXOnDragPolicy;
import org.eclipse.gef4.mvc.fx.tools.FXClickDragTool;
import org.eclipse.gef4.mvc.models.SelectionModel;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gefdiag.common.model.ChangeBoundsModel;
import org.eclipse.papyrus.infra.gefdiag.common.part.NotationContentPart;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class ResizeOnDragPolicy extends AbstractFXOnDragPolicy {

	protected static final int NORTH_WEST = 0;

	protected static final int NORTH_EAST = 1;

	protected static final int SOUTH_EAST = 2;

	protected static final int SOUTH_WEST = 3;

	@Override
	public void drag(MouseEvent e, Dimension delta) {

		propagate(e, delta, policy -> policy.drag(e, delta));

		ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);
		Bounds newBounds = computeNewBounds(getBounds(), delta);
		boundsModel.addManagedElement(getPrimaryHost(), newBounds);
	}

	protected Bounds computeNewBounds(Bounds bounds, Dimension delta) {
		if (bounds == null) {
			return null;
		}

		Bounds newBounds = NotationFactory.eINSTANCE.createBounds();

		int xOffset = toPixels(delta.getWidth());
		int yOffset = toPixels(delta.getHeight());

		// FIXME: Identify the selected Handle (Top-left, ...) and change the position/size accordingly (e.g. moving top left anchor will change the position and size)
		// Currently, this policy only works "as expected" for the bottom-right Handle (i.e. add the delta to the current size)

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
		newBounds.setWidth(width);
		newBounds.setHeight(height);

		return newBounds;
	}

	protected int getAnchorDirection() {
		if (getHost() instanceof AbstractFXSegmentHandlePart) {
			return ((AbstractFXSegmentHandlePart<?>) getHost()).getSegmentIndex();
		}

		return SOUTH_EAST; // Arbitrary Default (Everyone resizes elements from the South East, right?)
	}

	@Override
	public void press(MouseEvent e) {
		// Nothing
	}

	protected final int toPixels(double pos) {
		return (int) Math.round(pos);
	}

	@Override
	public void release(MouseEvent e, Dimension delta) {

		propagate(e, delta, policy -> policy.release(e, delta));

		Bounds bounds = getBounds();
		if (bounds == null) {
			return;
		}

		ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);

		Bounds newBounds = computeNewBounds(bounds, delta);
		if (newBounds == null) {
			boundsModel.removeManagedElement(getPrimaryHost());
			return;
		}

		SetRequest setXRequest = new SetRequest(bounds, NotationPackage.Literals.LOCATION__X, newBounds.getX());
		SetRequest setYRequest = new SetRequest(bounds, NotationPackage.Literals.LOCATION__Y, newBounds.getY());
		SetRequest setWidthRequest = new SetRequest(bounds, NotationPackage.Literals.SIZE__WIDTH, newBounds.getWidth());
		SetRequest setHeightRequest = new SetRequest(bounds, NotationPackage.Literals.SIZE__HEIGHT, newBounds.getHeight());


		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(bounds);
		if (provider != null) {
			CompositeCommand resizeCommand = new CompositeCommand("Resize element");
			resizeCommand.add(provider.getEditCommand(setXRequest));
			resizeCommand.add(provider.getEditCommand(setYRequest));
			resizeCommand.add(provider.getEditCommand(setWidthRequest));
			resizeCommand.add(provider.getEditCommand(setHeightRequest));

			try {
				ServiceUtilsForEObject.getInstance().getTransactionalEditingDomain(bounds).getCommandStack().execute(new GMFtoEMFCommandWrapper(resizeCommand));
			} catch (ServiceException e1) {
				e1.printStackTrace();
			}
		}

		boundsModel.removeManagedElement(getPrimaryHost());
	}

	protected int getCurrentWidth() {
		Node visual = getPrimaryHost().getVisual();
		javafx.geometry.Bounds local = visual.getLayoutBounds();

		return toPixels(local.getWidth());
	}

	protected int getCurrentHeight() {
		Node visual = getPrimaryHost().getVisual();
		javafx.geometry.Bounds local = visual.getLayoutBounds();

		return toPixels(local.getHeight());
	}

	protected IVisualPart<Node, ? extends Node> getAnchorageHost() {
		return getHost().getAnchorages().keys().iterator().next();
	}

	protected IVisualPart<Node, ? extends Node> getPrimaryHost() {
		IVisualPart<Node, ? extends Node> host = getAnchorageHost();
		if (host instanceof NotationContentPart) {
			host = ((NotationContentPart<?, ?>) host).getPrimaryContentPart();
		}
		return host;
	}

	protected void propagate(MouseEvent e, Dimension delta, Consumer<AbstractFXOnDragPolicy> actionToPropagate) {
		SelectionModel<Node> selectionModel = getHost().getRoot().getViewer().getAdapter(SelectionModel.class);

		if (selectionModel.getSelected().size() > 1) {

			// If I'm the main receiver of the event, I propagate it to other selected elements
			// If I'm not the main receiver, do nothing; someone else will do the propagation
			if (e.getTarget() == getHost().getVisual()) {

				for (IContentPart<Node, ? extends Node> selectedPart : selectionModel.getSelected()) {
					if (selectedPart != getPrimaryHost()) {
						for (AbstractFXOnDragPolicy dragPolicy : selectedPart.getAdapters(FXClickDragTool.DRAG_TOOL_POLICY_KEY).values()) {
							actionToPropagate.accept(dragPolicy);
						}
					}
				}

			}
		}
	}

	protected Bounds getBounds() {
		IVisualPart<?, ?> host = getPrimaryHost();

		if (host == null) {
			return null;
		}

		View hostView = NotationHelper.findView(host);
		if (hostView instanceof org.eclipse.gmf.runtime.notation.Node) {
			LayoutConstraint layout = ((org.eclipse.gmf.runtime.notation.Node) hostView).getLayoutConstraint();
			if (layout instanceof Bounds) {
				return (Bounds) layout;
			}
		}

		return null;
	}

}
