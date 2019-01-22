/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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
package org.eclipse.papyrus.gef4.gmf.locators;

import javax.inject.Inject;

import org.eclipse.core.runtime.Assert;
import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.geometry.planar.Line;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.geometry.planar.Polygon;
import org.eclipse.gef.geometry.planar.Polyline;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.layout.Locator;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Node;



/**
 * Polygon- or Polyline-based Locator. The Polygon/Polyline is used to define the
 * set of acceptable positions for this node
 *
 * This is typically used to locate items on the border of a rectangle
 *
 * This locator accepts a vertical and horizontal shift (To move items inside or outside)
 *
 * @author Camille Letavernier
 *
 */
public class BorderItemLocator implements Locator {

	private final ChangeListener<Bounds> boundsListener;
	private BaseContentPart<? extends View, ?> part;

	@Inject
	public BorderItemLocator(IVisualPart<?> visualPart) {
		this.part = getBasePartChecked(visualPart);
		boundsListener = (bounds, oldValue, newValue) -> refreshLayout();
	}

	@SuppressWarnings("unchecked") // Checks are done in the body
	private static BaseContentPart<? extends View, ?> getBasePartChecked(IVisualPart<?> visualPart) {
		Assert.isLegal(visualPart instanceof BaseContentPart, "BorderItemLocator can only be used with a BaseContentPart<View, ?>");
		BaseContentPart<?, ?> basePart = (BaseContentPart<?, ?>) visualPart;
		Assert.isLegal(basePart.getContent() instanceof View, "BorderItemLocator can only be used with a BaseContentPart<View, ?>");
		return (BaseContentPart<? extends View, ?>) basePart;
	}

	protected BaseContentPart<? extends View, ?> getPart() {
		return this.part;
	}

	/**
	 * Returns a Polyline or Poligon representing the valid positions for the element
	 *
	 * Other Nodes are not supported yet (But might be in the future). For other kinds of nodes, the algorithm will rely on their bound's outline (Polyline representing the rectangle bounds)
	 *
	 * @return
	 */
	protected final IGeometry getConstraint(Dimension nodeSize) {
		BaseContentPart<? extends View, ?> host = getPart();
		if (host.getParent() == null) {
			return new Rectangle(0, 0, 0, 0);
		}

		ObservableValue<Bounds> boundsProperty = host.getParent().getVisual().layoutBoundsProperty();
		boundsProperty.removeListener(boundsListener); // addListener will not ensure uniqueness. Remove the listener before re-adding it to ensure it is not reapplied during each refresh
		boundsProperty.addListener(boundsListener);
		Bounds parentBounds = boundsProperty.getValue();

		return doGetConstraint(parentBounds, nodeSize);
	}

	protected IGeometry doGetConstraint(Bounds parentBounds, Dimension nodeSize) {
		return new Rectangle(0, 0, parentBounds.getWidth(), parentBounds.getHeight()); // Position 0, 0 relative to the parent element
	}

	private void refreshLayout() {
		BaseContentPart<? extends View, ?> host = getPart();
		if (host.getLocator() == this) {
			host.refreshVisual();
		} else { // I'm not active anymore; remove the listener and do nothing
			if (host.getParent() != null && host.getParent().getVisual() != null) {
				Node visual = host.getParent().getVisual();
				visual.layoutBoundsProperty().removeListener(boundsListener);
			}
		}
	}

	@Override
	public void applyLayout(Node node) {
		View hostView = getPart().getContent();
		if (!(hostView instanceof org.eclipse.gmf.runtime.notation.Node)) {
			return;
		}

		org.eclipse.gmf.runtime.notation.Node hostNode = (org.eclipse.gmf.runtime.notation.Node) hostView;
		LayoutConstraint notationBounds = hostNode.getLayoutConstraint();

		int x, y;
		if (notationBounds instanceof Location) {
			Location location = (Location) notationBounds;
			x = location.getX();
			y = location.getY();
		} else { // Default to top-left
			x = 0;
			y = 0;
		}

		Point targetPoint = new Point(x, y);

		node.setManaged(false);
		node.autosize(); // The node is not managed and will be ignored by the parent layout. We need to call autosize explicitly

		Point nearestPoint = getNearestValidPosition(node, targetPoint);

		double width = node.getLayoutBounds().getWidth();
		double height = node.getLayoutBounds().getHeight();

		if (nearestPoint != null) {
			node.setLayoutX(nearestPoint.x - width / 2);
			node.setLayoutY(nearestPoint.y - height / 2);
		}
	}

	protected static final Point findNearestPoint(Polyline polyline, Point point) {
		double minDistance = Double.MAX_VALUE;
		Point nearestPoint = null;

		for (Line outline : polyline.getCurves()) {
			Point nearestLinePoint = outline.getProjection(point);

			double distanceSquared = getDistanceSquared(nearestLinePoint, point);
			if (distanceSquared < minDistance) {
				minDistance = distanceSquared;
				nearestPoint = nearestLinePoint;
			}
		}

		return nearestPoint;
	}

	protected static final double getDistanceSquared(Point p1, Point p2) {
		double i = p1.x - p2.x;
		double j = p1.y - p2.y;
		return i * i + j * j;
	}

	@Override
	public Point getNearestValidPosition(Node node, Point targetPoint) {
		Bounds layoutBounds = node.getLayoutBounds();
		IGeometry constraint = getConstraint(new Dimension(layoutBounds.getWidth(), layoutBounds.getHeight()));

		final Polyline constraintPolyline;

		if (constraint instanceof Polyline) {
			constraintPolyline = (Polyline) constraint;
		} else if (constraint instanceof Polygon) {
			Polygon polygon = (Polygon) constraint;
			constraintPolyline = polygon.getOutline();
		} else {
			constraintPolyline = constraint.getBounds().getOutline();
		}

		return findNearestPoint(constraintPolyline, targetPoint);
	}
}




