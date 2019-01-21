/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (EclipseSource) cletavernier@eclipsesource.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.gmf.locators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.fx.nodes.Connection;
import org.eclipse.gef.geometry.convert.fx.FX2Geometry;
import org.eclipse.gef.geometry.convert.fx.Geometry2FX;
import org.eclipse.gef.geometry.euclidean.Angle;
import org.eclipse.gef.geometry.planar.Line;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.parts.PartUtils;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.parts.ConnectionContentPart;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * <p>
 * A specialization of the {@link AffixedLabelLocator}, using coordinates
 * relative to the specified reference point (Center, Source or Target) on its
 * parent {@link ConnectionContentPart}.
 * </p>
 */
// Implementation note:
// This locator was implemented to support Papyrus/GMF Compatibility,
// including LinkLF support. When LinkLF has not been applied to links,
// Labels use a reference point defined as a % of the connection length.
// Once LinkLF is activated, the reference point is always 0% or 100%,
// so the labels stick to their closest connection end, regardless of 
// what happens on the opposite end (This causes labels to be more stable,
// but then we have to support 2 different cases)
public class ConnectionLabelLocator extends AffixedLabelLocator {

	private final BaseContentPart<? extends View, ?> host;
	private Reference reference = Reference.CENTER;
	
	private static final double CENTER_RATIO = 0.5;
	
	private static final String IS_UPDATED_POSITION = "IS_UPDATED_POSITION"; // LinkLF Constant; see org.eclipse.papyrus.infra.gmfdiag.common.locator.PapyrusLabelLocator#IS_UPDATED_POSITION

	/**
	 * <p>
	 * Distance of a Label to the Line Start/Target Point, on a scale [0.0; 1.0]. A
	 * value of 0.0 means that the label is placed on the Line's Start/End point. A
	 * higher value means that the point will be placed further away on the line.
	 * </p>
	 */
	private static final double REF_RATIO = 0.15; // 15% / 85% is equivalent to GMF's LabelLocator

	/**
	 * Opposite of {@link #REF_RATIO}
	 */
	private static final double OPP_RATIO = 1 - REF_RATIO;

	public ConnectionLabelLocator(BaseContentPart<? extends View, ?> basePart) {
		super(basePart);
		this.host = basePart;
	}

	/**
	 * For Connections that can have different labels, it is useful to specify a
	 * reference point for each label (e.g. above source, below target, above
	 * center...)
	 */
	public void setReferencePoint(Reference reference, Point offset) {
		Assert.isNotNull(reference);
		Assert.isNotNull(offset);
		this.reference = reference;
	}

	/**
	 * Connections use the absolute coordinate system
	 */
	@Override
	protected Point2D getLocationInParent(Location location) {
		Point2D offset = super.getLocationInParent(location);
		boolean useLinkLF = useLinkLF(location);
		Connection connection = findParentConnection();
		if (connection == null) {
			System.err.println("Unable to retrieve the connection owning this label:" + host.getVisual());
			return offset;
		}

		connection.layoutBoundsProperty().removeListener(boundsListener);
		connection.layoutBoundsProperty().addListener(boundsListener);

		Point referencePoint = getReferencePoint(connection, FX2Geometry.toPoint(offset), useLinkLF);

		// Move the reference point so that the center of the text overlaps it (Instead
		// of the Top-Left point)
		Bounds textBounds = host.getVisual().getLayoutBounds();
		double width = textBounds.getWidth();
		double height = textBounds.getHeight();
		Point delta = new Point(width / 2, height / 2).negate();

		return Geometry2FX.toFXPoint(referencePoint.getTranslated(delta));
	}

	private boolean useLinkLF(Location location) {
		if (location == null) {
			return false;
		}
		EObject parent = location.eContainer();
		if (! (parent instanceof View)) {
			return false;
		}
		
		View parentView = (View)parent;
		boolean useLinkLF = NotationUtils.getBooleanValue(parentView, IS_UPDATED_POSITION, false);
		return useLinkLF;
	}

	private Point getReferencePoint(Connection connection, Point offset, boolean useLinkLF) {
		PointAndSegmentOnConnection pointOnConnection = getPointOnConnection(connection, reference, useLinkLF);
		Line segment = pointOnConnection.segment;
		Point pointOnSegment = pointOnConnection.pointOnLine;

		// Normalize the angle in (-PI/2; PI/2)
		Angle angle = segment.getDirectionCCW();
		double theta = Math.atan(Math.tan(angle.rad()));
		
		// Calculate the offset based on the theta angle, 
		// according to the GMF algorithm
		Point calculatedOffset = getCalculatedOffset(theta, segment, offset);

		return pointOnSegment.getTranslated(calculatedOffset);
	}
	
	private static class PointAndSegmentOnConnection {
		public Point pointOnLine;
		public Line segment;
	}
	
	private PointAndSegmentOnConnection getPointOnConnection(Connection connection, Reference reference, boolean useLinkLF) {
		ObservableList<Point> pointsUnmodifiable = connection.getPointsUnmodifiable();
		int pointsSize = pointsUnmodifiable.size();

		// For CENTER reference point, we take the entire segments list into account
		// For SOURCE or TARGET reference, we take the first or last segment into
		// account (To make sure the labels remain close to the connected node)
		// This is consistent with Papyrus/GMF

		Line segment;
		double ratioOnSegment;
		if (reference == Reference.CENTER) {
			List<Line> segments = new ArrayList<>(pointsSize - 1);
			Iterator<Point> pointsIterator = pointsUnmodifiable.iterator();
			Point point = pointsIterator.next();
			double connectionLength = 0;
			while (pointsIterator.hasNext()) {
				Point nextPoint = pointsIterator.next();
				Line nextSegment = new Line(point, nextPoint);
				segments.add(nextSegment);
				point = nextPoint;
				connectionLength += nextSegment.getLength();
			}

			double targetDist = CENTER_RATIO * connectionLength;
			segment = findSegment(segments, targetDist);
			double skippedLength = 0d;
			for (Line aSegment : segments) {
				if (aSegment == segment) {
					break;
				}
				skippedLength += aSegment.getLength();
			}

			ratioOnSegment = segment.getLength() == 0 ? 0 : (targetDist - skippedLength) / segment.getLength();
		}
		// NOTE: For SOURCE and TARGET, with Papyrus/LinkLF, the initial location is
		// based on a ratio.
		// However, after this point, the label position is persisted with a ratio of 0%
		// (or 100%), and the offset is set with an absolute value (So the labels don't
		// move after creation)
		// We don't support that yet; labels are always placed with a relative ratio.
		else if (reference == Reference.SOURCE) {
			segment = new Line(pointsUnmodifiable.get(0), pointsUnmodifiable.get(1));
			ratioOnSegment = useLinkLF ? 0 : REF_RATIO;
		} else if (reference == Reference.TARGET) {
			segment = new Line(pointsUnmodifiable.get(pointsSize - 2), pointsUnmodifiable.get(pointsSize - 1));
			ratioOnSegment = useLinkLF ? 1.0 : OPP_RATIO;
		} else {
			// Should never happen; we cover all enum cases
			throw new IllegalStateException("Reference point not specified");
		}
		
		PointAndSegmentOnConnection result = new PointAndSegmentOnConnection();
		result.segment = segment;
		result.pointOnLine = segment.get(ratioOnSegment);
		return result;
	}

	// Partial implementation from org.eclipse.gmf.runtime.diagram.ui.internal.figures.LabelHelper#calculatePointRelativeToPointOnLine
	private Point getCalculatedOffset(double theta, Line segment, Point offset) {
		Point normalizedOffset = offset;
		if (segment.getP1().x > segment.getP2().x) {
			normalizedOffset = offset.getScaled(-1, -1);
		}

		Point calculatedOffset = new Point(normalizedOffset.x //
				* Math.cos(theta) - normalizedOffset.y //
				* Math.sin(theta), normalizedOffset.x * Math.sin(theta) // 
				+ normalizedOffset.y * Math.cos(theta));

		return calculatedOffset;
	}

	private Line findSegment(List<Line> segments, double targetDist) {
		double current = 0;
		for (Line segment : segments) {
			current += segment.getLength();
			if (current >= targetDist) {
				return segment;
			}
		}
		return segments.get(segments.size() - 1);
	}

	private Connection findParentConnection() {
		IVisualPart<?> parent = host.getParent();
		if (parent == null) {
			return null;
		}

		IViewer viewer = host.getViewer();

		Node parentVisual = parent.getVisual();
		LinkedList<Node> hierarchy = new LinkedList<>();
		hierarchy.add(parentVisual);

		// Dig from the parent element's top-level node to all its children,
		// recursively, until we find a Connection
		while (!hierarchy.isEmpty()) {
			Node next = hierarchy.poll();
			IVisualPart<?> nextPart = PartUtils.retrieveVisualPart(viewer, next);
			if (nextPart != parent) {
				// That's not our figure anymore
				continue;
			}
			if (next instanceof Connection) {
				return (Connection) next;
			}
			if (next instanceof Parent) {
				hierarchy.addAll(((Parent) next).getChildrenUnmodifiable());
			}
		}
		return null;
	}

	/**
	 * Identify the reference point, when placing a Label on a Connection
	 */
	public static enum Reference {
		/** Location is Relative to the Center of the line */
		CENTER,
		/** Location is relative to the source anchor of the line */
		SOURCE,
		/** Location is relative to the target anchor of the line */
		TARGET;
	}

}
