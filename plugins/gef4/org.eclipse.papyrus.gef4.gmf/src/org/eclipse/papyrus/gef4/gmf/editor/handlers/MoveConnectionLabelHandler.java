/*****************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
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

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.fx.nodes.Connection;
import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.papyrus.gef4.gmf.locators.ConnectionLabelLocator;
import org.eclipse.papyrus.gef4.gmf.utils.LabelUtil;
import org.eclipse.papyrus.gef4.model.ChangeBoundsModel;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.utils.ModelUtil;

import javafx.geometry.BoundingBox;
import javafx.scene.Node;

/**
 * <p>
 * MoveHandler for Connection labels using the {@link ConnectionLabelLocator}.
 * </p>
 * 
 * <p>
 * This Locator rotates the Labels' reference point to follow the connection segment, and
 * the persisted X/Y coordinates have to take this rotation into account. For example,
 * when moving the label of a Vertical link to the left, we actually need to change
 * its Y coordinate, because it will be rotated 90°.
 * </p>
 */

public class MoveConnectionLabelHandler extends MoveNodeHandler implements MoveHandler {

	@Override
	public ICommand move(Dimension delta) {
		// Note: until https://github.com/eclipsesource/papyrus-gefx/issues/31, we can only
		// use one MoveHandler for all affixed labels (That includes Port labels and Connection Labels).
		// Later on, they should probably be separate handlers; but for now we need to support both cases.
		if (isConnectionLabel()) {
			// Connection floating label (With ConnectionLabelLocator)
			return moveConnectionLabel(delta);
		} else {
			// Node floating label
			return super.move(delta);
		}
	}
	
	protected ICommand moveConnectionLabel(Dimension delta) {
		final ChangeBoundsModel boundsModel = ModelUtil.getChangeBoundsModel(getHost());

		final Point newLocationInConnection = computeNewLocationInConnection(delta);
		if (newLocationInConnection == null) {
			IVisualPart<?> host = getHost();
			if (boundsModel.getManagedElements().containsKey(host)) {
				boundsModel.removeManagedElement(getHost());
			}
			return null;
		}
		
		AbstractTransactionalCommand moveCommand = new AbstractTransactionalCommand(editingDomain, "Move label", null) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				Location location = getLocation();
				if (location == null && getHostView() != null) {
					location = NotationFactory.eINSTANCE.createLocation();
					getHostView().setLayoutConstraint(location);
				}
				location.setX((int) newLocationInConnection.x());
				location.setY((int) newLocationInConnection.y());
				return CommandResult.newOKCommandResult();
			}
		};

		return moveCommand;
	}
	
	protected Point computeNewLocationInConnection(final Dimension delta) {
		assert delta != null;

		Node hostVisual = getHost().getVisual();

		final double xOffset = delta.getWidth();
		final double yOffset = delta.getHeight();

		javafx.geometry.Bounds visualBounds = hostVisual.getLayoutBounds();
		javafx.geometry.Bounds sceneBounds = hostVisual.localToScene(visualBounds);

		double x = sceneBounds.getMinX() + xOffset;
		double y = sceneBounds.getMinY() + yOffset;

		Node parentVisual = hostVisual.getParent();
		javafx.geometry.Bounds updatedBoundsInScene = new BoundingBox(x, y, sceneBounds.getWidth(),
				sceneBounds.getHeight());
		javafx.geometry.Bounds updatedBoundsInParent = parentVisual.sceneToLocal(updatedBoundsInScene);

		Connection connection = getLabelLocator().getConnection();
		Point normalPoint = LabelUtil.offsetFromRelativeCoordinate(hostVisual, connection, updatedBoundsInParent, getReferencePoint());

		return normalPoint;
	}
	
	protected Point getReferencePoint() {
		ConnectionLabelLocator labelLocator = getLabelLocator();
		return labelLocator.getReferencePoint();
	}
	
	protected boolean isConnectionLabel() {
		return getLabelLocator() != null;
	}
	
	protected ConnectionLabelLocator getLabelLocator() {
		if (getHost() instanceof BaseContentPart && ((BaseContentPart<?, ?>)getHost()).getLocator() instanceof ConnectionLabelLocator) {
			return (ConnectionLabelLocator) ((BaseContentPart<?, ?>) getHost()).getLocator();
		}
		return null;
	}

}
