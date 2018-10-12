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
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.handle.Direction;
import org.eclipse.papyrus.gef4.model.ChangeBoundsModel;
import org.eclipse.papyrus.gef4.utils.ModelUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;

import javafx.geometry.BoundingBox;
import javafx.scene.Node;

public class ResizeNodeHandler extends AbstractHandler implements ResizeHandler {

	private static Logger logger = LoggerCreator.createLogger(ResizeNodeHandler.class);

	@Inject
	TransactionalEditingDomain editingDomain;

	@Override
	public ICommand resize(Dimension delta, Direction direction) {
		Rectangle boundsInParent = computeNewBoundsInParent(delta, direction);

		// FIXME GMF's SetBoundsCommand can't be reused here because it depends on GEF
		// Legacy. We should add a JavaFX equivalent command
		AbstractTransactionalCommand setBoundsCommand = new AbstractTransactionalCommand(editingDomain, "Resize node",
				null) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				Bounds bounds = getBounds();
				if (bounds == null && getHostView() != null) {
					bounds = NotationFactory.eINSTANCE.createBounds();
					getHostView().setLayoutConstraint(bounds);
				}
				bounds.setX((int) boundsInParent.getX());
				bounds.setY((int) boundsInParent.getY());
				bounds.setWidth((int) boundsInParent.getWidth());
				bounds.setHeight((int) boundsInParent.getHeight());
				return CommandResult.newOKCommandResult();
			}
		};

		return setBoundsCommand;
	}

	@Override
	public void showFeedback(Dimension delta, Direction direction) {
		final ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);
		final Rectangle newBounds = computeNewBoundsInParent(delta, direction);
		if (newBounds == null) { // If the host element doesn't have bounds (e.g. Connections)
			return;
		}
		boundsModel.addManagedElement(getHost(), newBounds);
	}

	@Override
	public void removeFeedback() {
		try {
			final ChangeBoundsModel boundsModel = ModelUtil.getChangeBoundsModel(getHost());
			boundsModel.removeManagedElement(getHost());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	protected Rectangle computeNewBoundsInParent(final Dimension delta, Direction direction) {
		Node hostVisual = getHost().getVisual();
		Node parentVisual = hostVisual.getParent();
		javafx.geometry.Bounds visualBounds = hostVisual.getBoundsInParent();
		javafx.geometry.Bounds sceneBounds = parentVisual.localToScene(visualBounds);

		final double xOffset = delta.getWidth();
		final double yOffset = delta.getHeight();

		double x, y, width, height;

		int directionConstant = direction.getPositionConstant();
		if ((directionConstant & Direction.NORTH_VALUE) == Direction.NORTH_VALUE) {
			// Resize to North
			y = sceneBounds.getMinY() + yOffset;
			height = sceneBounds.getHeight() - yOffset;
		} else if ((directionConstant & Direction.SOUTH_VALUE) == Direction.SOUTH_VALUE) {
			// Resize to South
			y = sceneBounds.getMinY();
			height = sceneBounds.getHeight() + yOffset;
		} else {
			// Horizontal resize
			y = sceneBounds.getMinY();
			height = sceneBounds.getHeight();
		}

		if ((directionConstant & Direction.EAST_VALUE) == Direction.EAST_VALUE) {
			// Resize to East
			x = sceneBounds.getMinX();
			width = sceneBounds.getWidth() + xOffset;
		} else if ((directionConstant & Direction.WEST_VALUE) == Direction.WEST_VALUE) {
			// Resize to West
			x = sceneBounds.getMinX() + xOffset;
			width = sceneBounds.getWidth() - xOffset;
		} else {
			// Vertical resize
			x = sceneBounds.getMinX();
			width = sceneBounds.getWidth();
		}

		javafx.geometry.Bounds deltaBoundsInScene = new BoundingBox(x, y, width, height);
		javafx.geometry.Bounds deltaBoundsInParent = parentVisual.sceneToLocal(deltaBoundsInScene);

		final Rectangle newBounds = new Rectangle();

		newBounds.setX(deltaBoundsInParent.getMinX());
		newBounds.setY(deltaBoundsInParent.getMinY());
		newBounds.setWidth(Math.max(getHostMinWidth(), deltaBoundsInParent.getWidth()));
		newBounds.setHeight(Math.max(getHostMinHeight(), deltaBoundsInParent.getHeight()));

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

	protected org.eclipse.gmf.runtime.notation.Node getHostView() {
		final IVisualPart<?> host = getHost();
		final View hostView = NotationHelper.findView(host);
		if (hostView instanceof org.eclipse.gmf.runtime.notation.Node) {
			return (org.eclipse.gmf.runtime.notation.Node) hostView;
		}
		return null;
	}

	protected Bounds getBounds() {
		org.eclipse.gmf.runtime.notation.Node hostView = getHostView();
		if (hostView == null) {
			return null;
		}

		final LayoutConstraint layout = hostView.getLayoutConstraint();
		return layout instanceof Bounds ? (Bounds) layout : null;
	}

}
