/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  EclipseSource - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.gef4.gmf.editor.handlers;

import javax.inject.Inject;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.fx.core.log.Logger;
import org.eclipse.fx.core.log.LoggerCreator;
import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.model.ChangeBoundsModel;
import org.eclipse.papyrus.gef4.utils.ModelUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;

import javafx.geometry.BoundingBox;
import javafx.scene.Node;

/**
 * <p>
 * Simple implementation of {@link MoveHandler} that changes the
 * {@link Location} of a Node.
 * </p>
 */
public class MoveNodeHandler extends org.eclipse.gef.common.adapt.IAdaptable.Bound.Impl<IVisualPart<? extends Node>>
		implements MoveHandler {

	private static Logger logger = LoggerCreator.createLogger(MoveNodeHandler.class);

	@Inject
	TransactionalEditingDomain editingDomain;

	@Override
	public void showFeedback(Dimension delta) {
		final ChangeBoundsModel boundsModel = ModelUtil.getChangeBoundsModel(getHost());
		final Rectangle newBounds = computeNewBoundsInParent(delta);

		if (null != newBounds) {
			boundsModel.addManagedElement(getHost(), newBounds);
		}
	}

	@Override
	public ICommand move(Dimension delta) {
		final ChangeBoundsModel boundsModel = ModelUtil.getChangeBoundsModel(getHost());

		final Rectangle newBoundsInParent = computeNewBoundsInParent(delta);
		if (newBoundsInParent == null) {
			IVisualPart<?> host = getHost();
			if (boundsModel.getManagedElements().containsKey(host)) {
				boundsModel.removeManagedElement(getHost());
			}
			return null;
		}

		AbstractTransactionalCommand moveCommand = new AbstractTransactionalCommand(editingDomain, "Move node", null) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				Location location = getLocation();
				if (location == null) {
					if (getHostView() != null) {
						location = NotationFactory.eINSTANCE.createLocation();
						getHostView().setLayoutConstraint(location);
					} else {
						return CommandResult.newErrorCommandResult("Unable to move the selected part: " + getHost());
					}
				}
				location.setX((int) newBoundsInParent.getX());
				location.setY((int) newBoundsInParent.getY());
				return CommandResult.newOKCommandResult();
			}
		};

		return moveCommand;
	}

	@Override
	public void removeFeedback() {
		final ChangeBoundsModel boundsModel = ModelUtil.getChangeBoundsModel(getHost());
		try {
			boundsModel.removeManagedElement(getHost());
		} catch (final Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	protected Rectangle computeNewBoundsInParent(final Dimension delta) {
		assert delta != null;

		Node hostVisual = getHost().getVisual();

		final double xOffset = delta.getWidth();
		final double yOffset = delta.getHeight();

		javafx.geometry.Bounds visualBounds = hostVisual.getLayoutBounds();
		javafx.geometry.Bounds sceneBounds = hostVisual.localToScene(visualBounds);

		double x = sceneBounds.getMinX() + xOffset;
		double y = sceneBounds.getMinY() + yOffset;

		Node parentVisual = hostVisual.getParent();
		javafx.geometry.Bounds deltaBoundsInScene = new BoundingBox(x, y, sceneBounds.getWidth(),
				sceneBounds.getHeight());
		javafx.geometry.Bounds deltaBoundsInParent = parentVisual.sceneToLocal(deltaBoundsInScene);

		final Rectangle newBounds = new Rectangle();

		newBounds.setX(deltaBoundsInParent.getMinX());
		newBounds.setY(deltaBoundsInParent.getMinY());
		newBounds.setWidth(deltaBoundsInParent.getWidth());
		newBounds.setHeight(deltaBoundsInParent.getHeight());

		return newBounds;
	}

	protected org.eclipse.gmf.runtime.notation.Node getHostView() {
		final IVisualPart<?> host = getHost();
		final View hostView = NotationHelper.findView(host);
		if (hostView instanceof org.eclipse.gmf.runtime.notation.Node) {
			return (org.eclipse.gmf.runtime.notation.Node) hostView;
		}
		return null;
	}

	protected Location getLocation() {
		org.eclipse.gmf.runtime.notation.Node hostView = getHostView();
		if (hostView == null) {
			return null;
		}

		final LayoutConstraint layout = hostView.getLayoutConstraint();
		return layout instanceof Location ? (Location) layout : null;
	}

}
