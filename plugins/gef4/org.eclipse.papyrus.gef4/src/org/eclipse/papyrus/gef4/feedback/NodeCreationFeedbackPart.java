/*****************************************************************************
 * Copyright (c) 2019 EclipseSource
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  EclipseSource - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.gef4.feedback;

import org.eclipse.gef.geometry.convert.fx.Geometry2FX;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.papyrus.gef4.style.GEFStyle;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

/**
 * <p>
 * Feedback part used to display creation feedback, when creating a node on a surface (XYLayout).
 * This feedback part draws a rectangle showing the location & size of the node to be created.
 * </p>
 */
public class NodeCreationFeedbackPart extends AbstractCreationFeedbackPart<Rectangle> {

	public NodeCreationFeedbackPart(org.eclipse.gef.geometry.planar.Rectangle creationBounds) {
		super(creationBounds);
	}

	@Override
	protected Rectangle doCreateVisual() {
		Rectangle rectangle = new Rectangle();
		rectangle.getStyleClass().add(GEFStyle.CREATION_FEEDBACK);
		return rectangle;
	}

	@Override
	protected void doRefreshVisual(Rectangle visual) {
		Bounds boundsInLocal = Geometry2FX.toFXBounds(creationBoundsInLocal);
		Bounds boundsInScene = getAnchorage().getVisual().localToScene(boundsInLocal);

		IRootPart<?> rootPart = getRoot();
		Node feedbackReference = rootPart.getVisual(); // ; ((LayeredRootPart) rootPart).getFeedbackLayer();
		Bounds boundsInRoot = feedbackReference.sceneToLocal(boundsInScene);

		visual.setX(boundsInRoot.getMinX());
		visual.setY(boundsInRoot.getMinY());

		double width = Math.max(20, boundsInRoot.getWidth());
		double height = Math.max(20, boundsInRoot.getHeight());
		visual.setWidth(width);
		visual.setHeight(height);
	}

}
