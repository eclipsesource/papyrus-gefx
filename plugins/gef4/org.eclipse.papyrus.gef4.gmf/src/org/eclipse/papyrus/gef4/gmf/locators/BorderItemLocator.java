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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.fx.core.Subscription;
import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.geometry.planar.Line;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.geometry.planar.Polygon;
import org.eclipse.gef.geometry.planar.Polyline;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.layout.Locator;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.utils.ActivatableBound;

import javafx.beans.binding.ObjectExpression;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Region;

/**
 * Polygon- or Polyline-based Locator. The Polygon/Polyline is used to define
 * the set of acceptable positions for this node
 *
 * This is typically used to locate items on the border of a rectangle
 *
 * This locator accepts a vertical and horizontal shift (To move items inside or
 * outside)
 *
 * @author Camille Letavernier
 *
 */
public class BorderItemLocator extends ActivatableBound<BaseContentPart<? extends View, ?>> implements Locator {

	private ChangeListener<Bounds> boundsListener = (bounds, oldValue, newValue) -> refreshLayout();
	private final List<Subscription> onDeactivate = new ArrayList<>();
	private BaseContentPart<? extends View, ?> part;
	private Side side = Side.CENTERED;

	@Inject
	public BorderItemLocator() {
		// Nothing
	}

	@Override
	public void setAdaptable(BaseContentPart<? extends View, ?> adaptable) {
		super.setAdaptable(adaptable);
		this.part = adaptable;
	}

	@Override
	protected void doActivate() {
		ObjectExpression<Bounds> layoutBoundsProperty = part.getVisual().layoutBoundsProperty();
		ObjectExpression<Bounds> parentBoundsProperty = part.getParent().getVisual().layoutBoundsProperty();
		layoutBoundsProperty.addListener(boundsListener);
		parentBoundsProperty.addListener(boundsListener);
		onDeactivate.add(() -> layoutBoundsProperty.removeListener(boundsListener));
		onDeactivate.add(() -> parentBoundsProperty.removeListener(boundsListener));
	}

	public void setSide(Side side) {
		this.side = side;
	}

	@Override
	protected void doDeactivate() {
		onDeactivate.forEach(Subscription::disposeIfExists);
		onDeactivate.clear();
	}

	protected BaseContentPart<? extends View, ?> getPart() {
		return this.part;
	}

	/**
	 * Returns a Polyline or Poligon representing the valid positions for the
	 * element
	 *
	 * Other Nodes are not supported yet (But might be in the future). For other
	 * kinds of nodes, the algorithm will rely on their bound's outline (Polyline
	 * representing the rectangle bounds)
	 *
	 * @return
	 */
	protected final IGeometry getConstraint(Dimension nodeSize) {
		BaseContentPart<? extends View, ?> host = getPart();
		if (host.getParent() == null) {
			return new Rectangle(0, 0, 0, 0);
		}

		ObservableValue<Bounds> boundsProperty = host.getParent().getVisual().layoutBoundsProperty();
		Bounds parentBounds = boundsProperty.getValue();

		return doGetConstraint(parentBounds, nodeSize);
	}

	protected IGeometry doGetConstraint(Bounds parentBounds, Dimension nodeSize) {
		return new Rectangle(0, 0, parentBounds.getWidth(), parentBounds.getHeight()); // Position 0, 0 relative to the
																						// parent element
	}

	private void refreshLayout() {
		BaseContentPart<? extends View, ?> host = getPart();
		if (host.getLocator() == this) {
			host.refreshVisual();
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
		node.autosize(); // The node is not managed and will be ignored by the parent layout. We need to
							// call autosize explicitly

		Point nearestPoint = getNearestValidPosition(node, targetPoint);

		double width = node.getLayoutBounds().getWidth();
		double height = node.getLayoutBounds().getHeight();

		if (nearestPoint != null) {
			if (side == Side.CENTERED) {
				node.setLayoutX(nearestPoint.x - width / 2);
				node.setLayoutY(nearestPoint.y - height / 2);
			} else {
				Point2D delta = getLocationDelta(node);
				SideOnParent sideOnParent = findSideOnParent(nearestPoint, node.getParent());

				boolean inside = side == Side.INSIDE;
				switch (sideOnParent) {
				case TOP:
					if (inside) {
						delta = new Point2D(0, 0);
					} else {
						delta = delta.add(0, -height);
					}
					break;
				case BOTTOM:
					if (inside) {
						delta = new Point2D(0, -height);
					} else {
						delta = new Point2D(0, -delta.getY());
					}
					break;
				case LEFT:
					if (inside) {
						delta = new Point2D(0, 0);
					} else {
						delta = delta.add(-width, 0);
					}
					break;
				case RIGHT:
					if (inside) {
						delta = new Point2D(-width, 0);
					} else {
						delta = new Point2D(-delta.getX(), 0);
					}
					break;
				}

				node.setLayoutX(nearestPoint.x + delta.getX());
				node.setLayoutY(nearestPoint.y + delta.getY());
			}
		}
	}

	private SideOnParent findSideOnParent(Point nearestPointInParent, Parent parent) {
		Bounds parentBounds = parent.getLayoutBounds();

		SideOnParent hSide = nearestPointInParent.x() < parentBounds.getCenterX() ? SideOnParent.LEFT
				: SideOnParent.RIGHT;
		SideOnParent vSide = nearestPointInParent.y() < parentBounds.getCenterY() ? SideOnParent.TOP
				: SideOnParent.BOTTOM;

		final Point hp1, hp2;
		if (hSide == SideOnParent.LEFT) {
			hp1 = new Point(parentBounds.getMinX(), parentBounds.getMinY());
			hp2 = new Point(parentBounds.getMinX(), parentBounds.getMaxY());
		} else {
			hp1 = new Point(parentBounds.getMaxX(), parentBounds.getMinY());
			hp2 = new Point(parentBounds.getMaxX(), parentBounds.getMaxY());
		}
		Line hLine = new Line(hp1, hp2);

		final Point vp1, vp2;
		if (vSide == SideOnParent.TOP) {
			vp1 = new Point(parentBounds.getMinX(), parentBounds.getMinY());
			vp2 = new Point(parentBounds.getMaxX(), parentBounds.getMinY());
		} else {
			vp1 = new Point(parentBounds.getMinX(), parentBounds.getMaxY());
			vp2 = new Point(parentBounds.getMaxX(), parentBounds.getMaxY());
		}
		Line vLine = new Line(vp1, vp2);

		Polyline lines = new Polyline(new Line[] { hLine, vLine });

		Point findNearestPoint = findNearestPoint(lines, nearestPointInParent);
		if (hLine.contains(findNearestPoint)) {
			return hSide;
		} else {
			return vSide;
		}
	}

	/**
	 *
	 * @param node the node being placed
	 * @return A delta to be applied on layout, to account e.g. for borders (When it
	 *         is necessary to overlap the node's border over the parent's border,
	 *         especially when side = INSIDE or OUTSIDE)
	 */
	protected Point2D getLocationDelta(Node node) {
		if (node instanceof Region) {
			Border border = ((Region) node).getBorder();
			if (border != null) {
				List<BorderStroke> strokes = border.getStrokes();
				if (strokes != null && !strokes.isEmpty()) {
					double x = 0, y = 0;
					for (BorderStroke s : strokes) {
						BorderWidths widths = s.getWidths();
						x = Math.max(Math.max(x, widths.getLeft()), widths.getRight());
						y = Math.max(Math.max(y, widths.getTop()), widths.getBottom());
					}
					return new Point2D(x, y);
				}
			}
		}
		return new Point2D(0, 0);
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

	public enum SideOnParent {
		TOP, RIGHT, BOTTOM, LEFT;
	}
}
