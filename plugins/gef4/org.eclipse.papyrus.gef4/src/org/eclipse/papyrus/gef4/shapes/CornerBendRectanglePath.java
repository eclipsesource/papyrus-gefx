/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.shapes;

import javafx.beans.binding.NumberExpression;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;

/**
 * The Class CornerBendRectanglePath. A Rectangle Path set with a bendedCorner à the top right. First implementation don't take into accound of percent.
 */
public class CornerBendRectanglePath extends Path {

	private double cornerWidth;
	private NumberExpression width;
	private NumberExpression height;

	public CornerBendRectanglePath(NumberExpression width, NumberExpression height, double cornerBendWidth) {
		this.cornerWidth = cornerBendWidth;
		this.width = width;
		this.height = height;

		init();
	}

	private void init() {
		// TopLeft
		getElements().add(new MoveTo(0, 0));

		HLineTo hLineToCorner = new HLineTo();
		hLineToCorner.xProperty().bind(width.subtract(cornerWidth));
		getElements().add(hLineToCorner);
		LineTo cornerDiagonalLine = new LineTo();
		cornerDiagonalLine.xProperty().bind(width);
		cornerDiagonalLine.setY(cornerWidth);
		getElements().add(cornerDiagonalLine);

		VLineTo cornerToBottom = new VLineTo();
		cornerToBottom.yProperty().bind(height);
		getElements().add(cornerToBottom);
		getElements().add(new HLineTo(0));
		getElements().add(new VLineTo(0));
	}
}
