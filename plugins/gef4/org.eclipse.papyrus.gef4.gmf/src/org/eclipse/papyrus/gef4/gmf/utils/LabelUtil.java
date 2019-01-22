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
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.gmf.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.fx.nodes.Connection;
import org.eclipse.gef.geometry.convert.fx.FX2Geometry;
import org.eclipse.gef.geometry.euclidean.Straight;
import org.eclipse.gef.geometry.euclidean.Vector;
import org.eclipse.gef.geometry.planar.Line;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.geometry.planar.Rectangle;

import javafx.geometry.Bounds;
import javafx.scene.Node;

// From org.eclipse.gmf.runtime.diagram.ui.internal.figures.LabelHelper & adapted to GEF Geometry API
public class LabelUtil {

	public static Point offsetFromRelativeCoordinate(Node label, Connection parentConnection, Bounds bounds, Point referencePoint) {
		return offsetFromRelativeCoordinate(label, parentConnection, FX2Geometry.toRectangle(bounds), referencePoint);
	}

	static public Point offsetFromRelativeCoordinate(Node label, Connection parentConnection, Rectangle bounds, Point ref) {
		return offsetFromRelativeCoordinate(label, bounds, getConnectionSegments(parentConnection), ref);
	}
	
	public static List<Line> getConnectionSegments(Connection connection) {
		List<Line> segments = new ArrayList<>(connection.getPointsUnmodifiable().size() - 1);
		Iterator<Point> pointsIterator = connection.getPointsUnmodifiable().iterator();
		Point point = pointsIterator.next();
		while (pointsIterator.hasNext()) {
			Point nextPoint = pointsIterator.next();
			segments.add(new Line(point, nextPoint));
			point = nextPoint;
		}
		
		return segments;
	}

	public static Point offsetFromRelativeCoordinate(Node label, Rectangle bounds, List<Line> segments, Point ref) {
		// The reference is the label center
		Rectangle rect = bounds.getTranslated(bounds.getWidth() / 2, bounds.getHeight() / 2);

		Point normalPoint = normalizeRelativePointToPointOnLine(segments, ref, new Point(rect.getX() - ref.x(), rect.getY() - ref.y()));

		return normalPoint;
	}

	private static Point normalizeRelativePointToPointOnLine(List<Line> segments, Point ptOnLine, Point offset) {
		// XXX: We could use the Reference (START/END/CENTER) rather than computing distances to find the reference segment
		Line segment = findNearestLineSegment(segments, ptOnLine);
		if (segment != null) {
			if (Math.abs(segment.getX1() - segment.getX2()) < 0.01) { // Vertical segment
				if (segment.getP1().y < segment.getP2().y) {
					return offset.scale(-1, 1).getTransposed();
				} else {
					return offset.scale(1, -1).getTransposed();
				}
			} else {
				Point p = ptOnLine.getTranslated(offset);
				return getOrthogonalDistances(segment, ptOnLine, p);
			}
		}
		return null;
	}
	
	public static Line findNearestLineSegment(List<Line> segments, Point point) {
		double minDistance = Double.MAX_VALUE;
		Line nearestSegment = null;

		for (Line segment : segments) {
			Point nearestLinePoint = segment.getProjection(point);

			double distanceSquared = getDistanceSquared(nearestLinePoint, point);
			if (distanceSquared < minDistance) {
				minDistance = distanceSquared;
				nearestSegment = segment;
			}
		}

		return nearestSegment;
	}
	
	/**
	 * Computes distances, for comparison purposes (Skip Math.sqrt)
	 */
	private static final double getDistanceSquared(Point p1, Point p2) {
		double i = p1.x - p2.x;
		double j = p1.y - p2.y;
		return i * i + j * j;
	}

	private static Point getOrthogonalDistances(Line segment, Point ptOnLine, Point refPoint) {
		Line parallelSeg = getParallelLineThroughPoint(segment, refPoint);
		Point p1 = parallelSeg.getProjection(ptOnLine);
		double dx = p1.getDistance(refPoint) * ((p1.x() > refPoint.x()) ? -1 : 1);
		double dy = p1.getDistance(ptOnLine) * ((p1.y() < ptOnLine.y()) ? -1 : 1);
		Point orth = new Point(dx, dy);
		// Reflection in the y axis
		if (segment.getP1().x() > segment.getP2().x()) {
			orth = orth.scale(-1, -1);
		}
		return orth;
	}
	
	private static Line getParallelLineThroughPoint(Line segment, Point refPoint) {
		Straight straight = new Straight(segment);
		Point projection = straight.getProjection(new Vector(refPoint)).toPoint();
		Point delta = projection.getDifference(refPoint);
		return (Line) segment.getTranslated(delta);
	}

}
