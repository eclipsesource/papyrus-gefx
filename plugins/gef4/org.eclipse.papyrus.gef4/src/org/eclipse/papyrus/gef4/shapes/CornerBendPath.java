/*****************************************************************************
 * Copyright (c) 2015, 2019 CEA LIST, EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 * Camille Letavernier (EclipseSource) - Issue 16
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.shapes;

import javafx.beans.binding.DoubleExpression;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.scene.shape.VLineTo;

/**
 * The Class CornerBendRectanglePath. A Rectangle Path set with a bendedCorner à the top right. First implementation don't take into accound of percent.
 */
public class CornerBendPath extends Path {

	private double cornerWidth;
	private DoubleExpression width;

	public CornerBendPath(DoubleExpression width, double cornerBendWidth) {
		this.width = width;
		this.cornerWidth = cornerBendWidth;

		// create the corner bend
		getElements().add(new MoveTo(0, 0));
		getElements().add(new VLineTo(cornerWidth));
		getElements().add(new HLineTo(cornerWidth));
		getElements().add(new ClosePath());

		// Layout
		setManaged(false);

		this.layoutXProperty().bind(this.width.subtract(cornerWidth));

		// Default Value
		setStrokeLineJoin(StrokeLineJoin.ROUND);
		setStrokeType(StrokeType.INSIDE);
	}

}
