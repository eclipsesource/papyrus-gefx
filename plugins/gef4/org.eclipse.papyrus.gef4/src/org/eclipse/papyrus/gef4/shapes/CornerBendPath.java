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

import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;

/**
 * The Class CornerBendRectanglePath. A Rectangle Path set with a bendedCorner à the top right. First implementation don't take into accound of percent.
 */
public class CornerBendPath extends Path {

	private double cornerWidth;
	private double width;

	public CornerBendPath(double parentWidth, double cornerBendWidth) {
		this.cornerWidth = cornerBendWidth;
		this.width = parentWidth;

		init();
	}

	private void init() {
		// create the corner bend
		getElements().add(new MoveTo(width - cornerWidth, 0));
		getElements().add(new LineTo(width - cornerWidth, cornerWidth));
		getElements().add(new LineTo(width, cornerWidth));
		getElements().add(new ClosePath());

		// Default Value
		setStrokeLineJoin(StrokeLineJoin.ROUND);
		setStrokeType(StrokeType.INSIDE);
		setManaged(false);
	}
}
