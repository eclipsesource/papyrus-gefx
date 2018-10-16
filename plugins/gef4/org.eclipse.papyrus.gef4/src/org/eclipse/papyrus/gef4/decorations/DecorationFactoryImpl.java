/*****************************************************************************
 * Copyright (c) 2016, 2018 CEA LIST, EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *	EclipseSource - Add support for line attributes (Color, width)
 *****************************************************************************/
package org.eclipse.papyrus.gef4.decorations;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

public class DecorationFactoryImpl implements DecorationFactory {

	private static DecorationFactory instance = new DecorationFactoryImpl();

	public static DecorationFactory getInstance() {
		return instance;
	}

	public static void setInstance(DecorationFactory factory) {
		instance = factory;
	}

	protected DecorationFactoryImpl() {
		// Nothing
	}

	@Override
	public Node createOpenArrow(Color lineColor, int lineWidth) {
		Polyline polyline = new Polyline(getArrowPoints(lineWidth));
		polyline.setStroke(lineColor);
		return polyline;
	}

	@Override
	public Node createEmptyArrow(Color lineColor, int lineWidth) {
		Polygon arrow = new Polygon(getArrowPoints(lineWidth));
		// XXX Use plain white because we don't want to see the line
		// under the decoration. Ideally, we should clip the line
		// after the decoration starts, and use a transparent background
		arrow.setFill(Color.WHITE);
		arrow.setStroke(lineColor);
		return arrow;
	}

	@Override
	public Node createFullArrow(Color lineColor, int lineWidth) {
		Polygon arrow = new Polygon(getArrowPoints(lineWidth));
		arrow.setFill(lineColor);
		arrow.setStroke(lineColor);
		return arrow;
	}

	@Override
	public Node createEmptyDiamond(Color lineColor, int lineWidth) {
		// XXX Use plain white because we don't want to see the line
		// under the decoration. Ideally, we should clip the line
		// after the decoration starts, and use a transparent background
		return createDiamond(lineColor, Color.WHITE, lineWidth);
	}

	@Override
	public Node createFullDiamond(Color lineColor, int lineWidth) {
		return createDiamond(lineColor, lineColor, lineWidth);
	}

	protected Node createDiamond(Paint stroke, Paint fill, int lineWidth) {
		Polygon diamond = new Polygon(getDiamondPoints(lineWidth));
		diamond.setStroke(stroke);
		diamond.setFill(fill);
		return diamond;
	}

	@Override
	public Node createCircle(Color lineColor, int lineWidth) {
		double radius = 3;
		Circle circle = new Circle(-radius, 0, radius);
		circle.setStroke(lineColor);
		return circle;
	}

	@Override
	public Node createCrossCircle(Color lineColor, int lineWidth) {
		double radius = 10;
		Circle containmentCircle = new Circle(-radius, 0, radius);
		// XXX In this case, we can use Transparent, because the horizontal line
		// directly overlaps the connection line, so the connection won't be visible
		// under the decoration (Or, we expect it to be)
		containmentCircle.setFill(Color.TRANSPARENT);
		containmentCircle.setStroke(lineColor);

		Line verticalLine = new Line(-radius, -radius + 1, -radius, radius - 1); // +1/-1 to avoid overlap with the circle
		Line horizontalLine = new Line(-radius * 2 + 1, 0, -1, 0); // +1/-1 to avoid overlap with the circle
		verticalLine.setStroke(lineColor);
		horizontalLine.setStroke(lineColor);

		// XXX We currently hide the horizontal line, because it directly overlaps with the connection line
		// Since we use a transparent background, the connection is visible and we don't need to draw that
		// line on top of it. Once we support clipping for the connection below the decoration, we can (and should) restore
		// the horizontal line.
		Group containmentLink = new Group(containmentCircle, verticalLine/* , horizontalLine */);

		return containmentLink;
	}

	protected double[] getArrowPoints(int lineWidth) {
		double width = 8;
		double height = 5;
		return new double[] { width, height, 0, 0, width, -height };
	}

	protected double[] getDiamondPoints(int lineWidth) {
		int height = 10;
		int width = 15;

		return new double[] { -width / 2., -height / 2., 0, 0, -width / 2., height / 2., -width, 0 };
	}

}
