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

import javafx.scene.layout.CornerRadii;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;

/**
 * The Class RoundedRectanglePath. A RoundedRectangle Path set with a corner radii. First implementation don't take into account of percent.
 */
public class RoundedRectanglePath extends Path {

	double width;
	double height;
	CornerRadii cornerRadii;

	public RoundedRectanglePath(final double width, final double height, final CornerRadii cornerRadii) {
		this.width = width;
		this.height = height;
		this.cornerRadii = cornerRadii;

		init();
	}

	private void init() {
		final int x = 0;
		final int y = 0;
		// TopLeft
		getElements().add(new MoveTo(x, y + cornerRadii.getTopLeftVerticalRadius()));
		getElements().add(new QuadCurveTo(x, y, x + cornerRadii.getTopLeftHorizontalRadius(), y));
		// TopRight
		getElements().add(new LineTo(x + width - cornerRadii.getTopRightHorizontalRadius(), y));
		getElements().add(new QuadCurveTo(x + width, y, x + width, y + cornerRadii.getTopRightVerticalRadius()));
		// BottomRight
		getElements().add(new LineTo(x + width, y + height - cornerRadii.getBottomRightVerticalRadius()));
		getElements().add(new QuadCurveTo(x + width, y + height, x + width - cornerRadii.getBottomRightHorizontalRadius(), y + height));
		// BottomLeft
		getElements().add(new LineTo(x + cornerRadii.getBottomLeftHorizontalRadius(), y + height));
		getElements().add(new QuadCurveTo(x, y + height, x, y + height - cornerRadii.getBottomLeftVerticalRadius()));
		// Close
		getElements().add(new ClosePath());
	}
}
