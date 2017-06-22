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
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.Activator;
import org.eclipse.papyrus.gef4.model.ChangeBoundsModel;
import org.eclipse.papyrus.gef4.parts.NotationContentPart;
import org.eclipse.papyrus.gef4.utils.BoundsUtil;
import org.eclipse.papyrus.gef4.utils.ModelUtil;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class AffixedNodeMoveOnDragHandler extends AbstractMultiSelectionDragHandler {

	@Override
	public void drag(final MouseEvent e, final Dimension delta) {

		propagate(e, delta, policy -> policy.drag(e, delta));

		// Nothing
		final ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);
		final Bounds newBounds = computeNewBounds(getBounds(), delta);

		if (null != newBounds) {
			boundsModel.addManagedElement(getPrimaryHost(), newBounds);
		}
	}

	@Override
	public void startDrag(final MouseEvent e) {
		// Nothing
	}

	protected final int toPixels(final double pos) {
		return (int) Math.round(pos);
	}

	@Override
	public void abortDrag() {
		// Propagation, in case of multi-selection
		propagate(handler -> handler.abortDrag());

		// Own behavior
		final ChangeBoundsModel boundsModel = ModelUtil.getChangeBoundsModel(getHost());
		boundsModel.removeManagedElement(getPrimaryHost());
	}

	@Override
	public void endDrag(final MouseEvent e, final Dimension delta) {

		// Propagation, in case of multi-selection

		propagate(e, delta, policy -> policy.endDrag(e, delta));

		// Own behavior

		final ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);

		final Bounds bounds = getBounds();

		final Bounds newBounds = computeNewBounds(bounds, delta);
		if (bounds == null || newBounds == null) {
			boundsModel.removeManagedElement(getPrimaryHost());
			return;
		}

		final SetRequest setXRequest = new SetRequest(bounds, NotationPackage.Literals.LOCATION__X, newBounds.getX());
		final SetRequest setYRequest = new SetRequest(bounds, NotationPackage.Literals.LOCATION__Y, newBounds.getY());

		final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(bounds);
		if (provider != null) {
			final CompositeCommand moveCommand = new CompositeCommand("Move element");
			moveCommand.add(provider.getEditCommand(setXRequest));
			moveCommand.add(provider.getEditCommand(setYRequest));

			AdapterFactoryEditingDomain.getEditingDomainFor(bounds).getCommandStack().execute(new GMFtoEMFCommandWrapper(moveCommand));
		}

		try {
			boundsModel.removeManagedElement(getPrimaryHost());
		} catch (final Exception ex) {
			Activator.error(ex);
		}
	}

	protected Bounds computeNewBounds(final Bounds currentBounds, final Dimension delta) {
		if (currentBounds == null || delta == null) {
			return null;
		}

		final Bounds newBounds = NotationFactory.eINSTANCE.createBounds();

		final int xOffset = toPixels(delta.getWidth());
		final int yOffset = toPixels(delta.getHeight());

		if (xOffset == 0 && yOffset == 0) {
			return null;
		}

		newBounds.setX(currentBounds.getX() + xOffset);
		newBounds.setY(currentBounds.getY() + yOffset);

		final Node visual = getPrimaryHost().getVisual();

		newBounds.setWidth(BoundsUtil.getWidth(visual));
		newBounds.setHeight(BoundsUtil.getHeight(visual));

		return newBounds;
	}

	@Override
	protected IVisualPart<? extends Node> getPrimaryHost() {
		IVisualPart<? extends Node> host = getHost();
		if (host instanceof NotationContentPart) {
			host = ((NotationContentPart<?, ?>) host).getPrimaryContentPart();
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
		// Nothing
	}

	@Override
	public boolean showIndicationCursor(KeyEvent event) {
		return false;
	}

	@Override
	public boolean showIndicationCursor(MouseEvent event) {
		return false;
	}

}
