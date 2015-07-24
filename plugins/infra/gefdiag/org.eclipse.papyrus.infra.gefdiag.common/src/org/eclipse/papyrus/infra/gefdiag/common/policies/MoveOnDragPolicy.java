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
import org.eclipse.papyrus.infra.gefdiag.common.Activator;
import org.eclipse.papyrus.infra.gefdiag.common.model.ChangeBoundsModel;
import org.eclipse.papyrus.infra.gefdiag.common.part.NotationContentPart;
import org.eclipse.papyrus.infra.gefdiag.common.util.BoundsUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class MoveOnDragPolicy extends AbstractFXOnDragPolicy {

	@Override
	public void drag(MouseEvent e, Dimension delta) {

		propagate(e, delta, policy -> policy.drag(e, delta));

		// Nothing
		ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);
		Bounds newBounds = computeNewBounds(getBounds(), delta);
		boundsModel.addManagedElement(getPrimaryHost(), newBounds);
	}



	@Override
	public void press(MouseEvent e) {
		// Nothing
	}

	protected final int toPixels(double pos) {
		return (int) Math.round(pos);
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

	@Override
	public void release(MouseEvent e, Dimension delta) {

		// Propagation, in case of multi-selection

		propagate(e, delta, policy -> policy.release(e, delta));

		// Own behavior

		ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);

		Bounds bounds = getBounds();

		Bounds newBounds = computeNewBounds(bounds, delta);
		if (bounds == null || newBounds == null) {
			boundsModel.removeManagedElement(getPrimaryHost());
			return;
		}

		SetRequest setXRequest = new SetRequest(bounds, NotationPackage.Literals.LOCATION__X, newBounds.getX());
		SetRequest setYRequest = new SetRequest(bounds, NotationPackage.Literals.LOCATION__Y, newBounds.getY());

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(bounds);
		if (provider != null) {
			CompositeCommand moveCommand = new CompositeCommand("Move element");
			moveCommand.add(provider.getEditCommand(setXRequest));
			moveCommand.add(provider.getEditCommand(setYRequest));


			try {
				ServiceUtilsForEObject.getInstance().getTransactionalEditingDomain(bounds).getCommandStack().execute(new GMFtoEMFCommandWrapper(moveCommand));
			} catch (ServiceException ex) {
				Activator.log.error(ex);
			}
		}

		try {
			boundsModel.removeManagedElement(getPrimaryHost());
		} catch (Exception ex) {
			Activator.log.error(ex);
		}
	}

	protected Bounds computeNewBounds(Bounds currentBounds, Dimension delta) {
		if (currentBounds == null || delta == null) {
			return null;
		}

		Bounds newBounds = NotationFactory.eINSTANCE.createBounds();

		int xOffset = toPixels(delta.getWidth());
		int yOffset = toPixels(delta.getHeight());

		if (xOffset == 0 && yOffset == 0) {
			return null;
		}

		newBounds.setX(currentBounds.getX() + xOffset);
		newBounds.setY(currentBounds.getY() + yOffset);

		Node visual = getPrimaryHost().getVisual();

		newBounds.setWidth(BoundsUtil.getWidth(visual));
		newBounds.setHeight(BoundsUtil.getHeight(visual));

		return newBounds;
	}

	protected IVisualPart<Node, ? extends Node> getPrimaryHost() {
		IVisualPart<Node, ? extends Node> host = getHost();
		if (host instanceof NotationContentPart) {
			host = ((NotationContentPart<?, ?>) host).getPrimaryContentPart();
		}
		return host;
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
