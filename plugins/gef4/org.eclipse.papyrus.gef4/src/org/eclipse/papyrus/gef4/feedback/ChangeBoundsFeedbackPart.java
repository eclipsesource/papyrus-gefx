/*****************************************************************************
 * Copyright (c) 2018 EclipseSource.
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.fx.utils.NodeUtils;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.parts.AbstractFeedbackPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.style.GEFStyle;

import javafx.scene.Node;
import javafx.scene.shape.Polygon;

/**
 * <p>
 * A Feedback Part indicating the final location of a Node after its bounds were changed
 * (Typically after a Move or Resize operation)
 * </p>
 * <p>
 * This Part supports Rectangle Nodes. It <strong>doesn't support</strong> rotation.
 * </p>
 */
public class ChangeBoundsFeedbackPart extends AbstractFeedbackPart<Polygon> {

	private org.eclipse.gef.geometry.planar.Rectangle newBounds; // Bounds in host's parent coordinates

	/**
	 * Instantiates a new bounds feedback part.
	 *
	 * @param host
	 *            the host
	 * @param newBounds
	 *            the new bounds in relative.
	 */
	public ChangeBoundsFeedbackPart(final org.eclipse.gef.geometry.planar.Rectangle newBounds) {
		this.newBounds = newBounds;
	}

	// Update feedback while resizing or moving
	public void updateBounds(final org.eclipse.gef.geometry.planar.Rectangle newBounds) {
		this.newBounds = newBounds;
		refreshVisual();
	}

	@Override
	protected Polygon doCreateVisual() {
		Polygon polygon = new Polygon();
		polygon.getPoints().addAll(0., 0., 1., 0., 1., 1., 0., 1.);
		polygon.getStyleClass().add(GEFStyle.BOUNDS_FEEDBACK);
		return polygon;
	}

	/**
	 * @NoOverride
	 * @NoReference
	 */
	public IVisualPart<?> getAnchorage() {
		return getAnchoragesUnmodifiable().keySet().iterator().next();
	}

	@Override
	protected void doRefreshVisual(final Polygon feedback) {
		IVisualPart<?> anchorage = getAnchorage();
		Node anchorageParent = anchorage.getVisual().getParent();

		List<Point> rectanglePoints = new ArrayList<>();
		rectanglePoints.add(new Point(newBounds.getX(), newBounds.getY()));
		rectanglePoints.add(new Point(newBounds.getX() + newBounds.getWidth(), newBounds.getY()));
		rectanglePoints.add(new Point(newBounds.getX() + newBounds.getWidth(), newBounds.getY() + newBounds.getHeight()));
		rectanglePoints.add(new Point(newBounds.getX(), newBounds.getY() + newBounds.getHeight()));

		int i = 0;
		for (Point rectanglePoint : rectanglePoints) {
			Point pointInScene = NodeUtils.localToScene(anchorageParent, rectanglePoint);
			Point coordinates = NodeUtils.sceneToLocal(feedback.getParent(), pointInScene);
			feedback.getPoints().set(i++, coordinates.x());
			feedback.getPoints().set(i++, coordinates.y());
		}
	}

}
