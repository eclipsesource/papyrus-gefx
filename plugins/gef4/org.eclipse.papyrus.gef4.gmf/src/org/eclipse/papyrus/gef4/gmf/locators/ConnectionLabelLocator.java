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

import java.util.LinkedList;
import java.util.Map;

import org.eclipse.gef.fx.nodes.Connection;
import org.eclipse.gef.geometry.convert.fx.FX2Geometry;
import org.eclipse.gef.geometry.convert.fx.Geometry2FX;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.parts.ConnectionContentPart;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * <p>
 * A specialization of the {@link AffixedLabelLocator}, using coordinates relative
 * to the center of its parent {@link ConnectionContentPart}.
 * </p>
 */
public class ConnectionLabelLocator extends AffixedLabelLocator {

	private final BaseContentPart<? extends View, ?> host;


	public ConnectionLabelLocator(BaseContentPart<? extends View, ?> basePart) {
		super(basePart);
		this.host = basePart;
	}

	/**
	 * Connections use the absolute coordinate system
	 */
	@Override
	protected Point2D getLocationInParent(Location location) {
		Point2D relative = super.getLocationInParent(location);
		Connection connection = findParentConnection();
		if (connection == null) {
			return relative;
		}

		connection.layoutBoundsProperty().removeListener(boundsListener);
		connection.layoutBoundsProperty().addListener(boundsListener);


		Point referencePoint = getReferencePoint(connection);

		// Move the reference point so that the center of the text overlaps it (Instead of the Top-Left point)
		Bounds textBounds = host.getVisual().getBoundsInLocal();
		double width = textBounds.getWidth();
		double height = textBounds.getHeight();
		Point delta = new Point(width / 2, height / 2).negate();

		return Geometry2FX.toFXPoint(referencePoint.getTranslated(delta).getTranslated(FX2Geometry.toPoint(relative)));
	}

	private Point getReferencePoint(Connection connection) {
		// FIXME: This isn't exactly the GMF convention for reference points. GMF finds the point at the
		// middle of the connection, following bendpoints.
		// Some labels may also use a different reference point (e.g. source/target rather than center),
		// so we probably need to delegate to the LabelContentPart or ConnectionContentPart
		return connection.getCenter();
	}

	private Connection findParentConnection() {
		IVisualPart<?> parent = host.getParent();
		if (parent == null) {
			return null;
		}
		Map<Node, IVisualPart<? extends Node>> partMap = parent.getViewer().getVisualPartMap();

		Node visual = parent.getVisual();
		LinkedList<Node> hierarchy = new LinkedList<>();
		hierarchy.add(visual);
		while (!hierarchy.isEmpty()) {
			Node next = hierarchy.poll();
			if (partMap.get(next) != parent) {
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

}
