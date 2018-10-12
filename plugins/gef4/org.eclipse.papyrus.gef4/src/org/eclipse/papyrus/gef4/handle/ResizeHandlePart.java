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
package org.eclipse.papyrus.gef4.handle;


import org.eclipse.gef.fx.utils.NodeUtils;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.parts.AbstractHandlePart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.style.GEFStyle;

import com.google.common.collect.SetMultimap;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class ResizeHandlePart extends AbstractHandlePart<Rectangle> {

	private Direction resizeDirection;

	@Override
	protected Rectangle doCreateVisual() {
		Rectangle resizeTracker = new Rectangle();
		resizeTracker.getStyleClass().add(GEFStyle.RESIZE_HANDLE);
		resizeTracker.translateXProperty().bind(resizeTracker.widthProperty().add(resizeTracker.strokeWidthProperty()).divide(2).negate());
		resizeTracker.translateYProperty().bind(resizeTracker.heightProperty().add(resizeTracker.strokeWidthProperty()).divide(2).negate());

		resizeTracker.setWidth(4);
		resizeTracker.setHeight(4);

		return resizeTracker;
	}

	@Override
	protected void doRefreshVisual(Rectangle visual) {
		refreshLocation(visual);
	}

	public void setDirection(Direction resizeDirection) {
		this.resizeDirection = resizeDirection;
	}

	public Direction getResizeDirection() {
		return this.resizeDirection;
	}

	protected void refreshLocation(Rectangle visual) {
		if (resizeDirection == null) {
			throw new IllegalStateException("The direction for this resize tracker wasn't set");
		}

		// only update when bound to anchorage
		SetMultimap<IVisualPart<? extends Node>, String> anchorages = getAnchoragesUnmodifiable();
		if (anchorages.keySet().size() < 1) {
			return;
		}

		IVisualPart<?> anchorage = anchorages.keySet().iterator().next();
		Node anchorageVisual = anchorage.getVisual();

		Bounds anchorageBounds = anchorageVisual.getLayoutBounds();

		double x, y;

		switch (resizeDirection) {
		case NORTH:
			x = (anchorageBounds.getMinX() + anchorageBounds.getMaxX()) / 2;
			y = anchorageBounds.getMinY();
			break;
		case SOUTH:
			x = (anchorageBounds.getMinX() + anchorageBounds.getMaxX()) / 2;
			y = anchorageBounds.getMaxY();
			break;
		case EAST:
			x = anchorageBounds.getMaxX();
			y = (anchorageBounds.getMinY() + anchorageBounds.getMaxY()) / 2;
			break;
		case WEST:
			x = anchorageBounds.getMinX();
			y = (anchorageBounds.getMinY() + anchorageBounds.getMaxY()) / 2;
			break;
		case NORTH_EAST:
			x = anchorageBounds.getMaxX();
			y = anchorageBounds.getMinY();
			break;
		case SOUTH_EAST:
			x = anchorageBounds.getMaxX();
			y = anchorageBounds.getMaxY();
			break;
		case SOUTH_WEST:
			x = anchorageBounds.getMinX();
			y = anchorageBounds.getMaxY();
			break;
		case NORTH_WEST:
			x = anchorageBounds.getMinX();
			y = anchorageBounds.getMinY();
			break;
		default:
			System.out.println("Unsupported resize direction: " + resizeDirection);
			return;
		}

		Point pointInScene = NodeUtils.localToScene(anchorageVisual, new Point(x, y));

		Point coordinates = NodeUtils.sceneToLocal(visual.getParent(), pointInScene);
		visual.relocate(coordinates.x(), coordinates.y());
	}

}
