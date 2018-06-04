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
package org.eclipse.papyrus.gef4.anchors;

import org.eclipse.gef.fx.anchors.AbstractAnchor;
import org.eclipse.gef.fx.anchors.AnchorKey;
import org.eclipse.gef.geometry.convert.fx.FX2Geometry;
import org.eclipse.gef.geometry.planar.Point;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;

public class BorderAnchor extends AbstractAnchor {

	private final int distance;
	private final Border side;

	public BorderAnchor(Node anchorage, Border side, int distance) {
		super(anchorage);
		this.distance = distance;
		this.side = side;
	}

	@Override
	protected Point computePosition(AnchorKey key) {
		Bounds layoutBounds = getAnchorage().getLayoutBounds();
		Point target = new Point(0, 0);
		switch (side) {
		case NORTH:
			target.translate(distance, 0);
			break;
		case SOUTH:
			target.translate(distance, layoutBounds.getHeight());
			break;
		case EAST:
			target.translate(layoutBounds.getWidth(), distance);
			break;
		case WEST:
			target.translate(0, distance);
			break;
		}
		Point2D positionInScene = getAnchorage().localToScene(new Point2D(target.x(), target.y()));
		Point position = FX2Geometry.toPoint(key.getAnchored().sceneToLocal(positionInScene));
		return position;
	}

	public static enum Border {
		NORTH, SOUTH, EAST, WEST
	}

}
