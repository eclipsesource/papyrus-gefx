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
package org.eclipse.papyrus.gef4.decorations;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;

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

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.gef4.decorations.DecorationFactory#createOpenArrow()
	 */
	@Override
	public Shape createOpenArrow() {
		return new Polyline(getArrowPoints());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.gef4.decorations.DecorationFactory#createClosedArrow()
	 */
	@Override
	public Shape createClosedArrow() {
		Polygon arrow = new Polygon(getArrowPoints());
		arrow.setFill(Color.WHITE);
		arrow.setStroke(Color.BLACK);
		return arrow;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.gef4.decorations.DecorationFactory#createEmptyDiamond()
	 */
	@Override
	public Shape createEmptyDiamond() {
		return createDiamond(Color.BLACK, Color.WHITE);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.gef4.decorations.DecorationFactory#createFullDiamond()
	 */
	@Override
	public Shape createFullDiamond() {
		return createDiamond(Color.BLACK, Color.BLACK);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.gef4.decorations.DecorationFactory#createDiamond(javafx.scene.paint.Paint, javafx.scene.paint.Paint)
	 */
	@Override
	public Shape createDiamond(Paint stroke, Paint fill) {
		Polygon diamond = new Polygon(getDiamondPoints());
		diamond.setStroke(stroke);
		diamond.setFill(fill);
		return diamond;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.gef4.decorations.DecorationFactory#createCircle()
	 */
	@Override
	public Shape createCircle() {
		double radius = 3;
		return new Circle(-radius, 0, radius);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.gef4.decorations.DecorationFactory#createCrossCircle()
	 */
	@Override
	public Shape createCrossCircle() {
		double radius = 10;
		Circle containmentCircle = new Circle(-radius, 0, radius);
		containmentCircle.setFill(Color.WHITE);
		containmentCircle.setStroke(Color.BLACK);
		Line verticalLine = new Line(-radius, -radius + 1, -radius, radius - 1); // +1/-1 to avoid overlap with the circle
		Line horizontalLine = new Line(-radius * 2 + 1, 0, -1, 0); // +1/-1 to avoid overlap with the circle
		Group containmentLink = new Group(containmentCircle, verticalLine, horizontalLine);

		// return containmentLink;
		return containmentCircle; // FIXME Groups not supported yet (GEF4 only supports Shape decorators)
	}

	protected double[] getArrowPoints() {
		double width = 8;
		double height = 5;
		return new double[] { width, height, 0, 0, width, -height };
	}

	protected double[] getDiamondPoints() {
		int height = 10;
		int width = 15;

		return new double[] { -width / 2., -height / 2., 0, 0, -width / 2., height / 2., -width, 0 };
	}

}
