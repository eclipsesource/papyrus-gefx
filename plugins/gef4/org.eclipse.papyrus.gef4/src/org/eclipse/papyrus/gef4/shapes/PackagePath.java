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
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;

/**
 * Create a package path with the tab dimension tabWidth*tabHeight
 */
public class PackagePath extends Path {

	private NumberExpression width, height;
	private NumberExpression tabWidth, tabHeight;

	/**
	 *
	 * Constructor.
	 *
	 * @param width
	 *            the total width of the package
	 * @param height
	 *            the total height of the package
	 * @param tabWidth
	 *            the width of the tab
	 * @param tabHeight
	 *            the height of the tab
	 * @param minWidthDelta
	 *            the minimum difference between the width of the package and the width of the tab
	 * @param minHeightDelta
	 *            the minimum difference between the height of the package and the height of the tab
	 */
	public PackagePath(NumberExpression width, NumberExpression height, NumberExpression tabWidth, NumberExpression tabHeight, double minWidthDelta, double minHeightDelta) {
		this.tabWidth = tabWidth;
		this.tabHeight = tabHeight;
		this.width = width;
		this.height = height;

		init();
	}

	private void init() {
		getElements().add(new MoveTo(0, 0));
		HLineTo tabWidthLine = new HLineTo();
		tabWidthLine.xProperty().bind(tabWidth);
		getElements().add(tabWidthLine);
		VLineTo tabHeightLine = new VLineTo();
		tabHeightLine.yProperty().bind(tabHeight);
		getElements().add(tabHeightLine);
		HLineTo widthLine = new HLineTo();
		widthLine.xProperty().bind(width);
		getElements().add(widthLine);
		VLineTo heightLine = new VLineTo();
		heightLine.yProperty().bind(height);
		getElements().add(heightLine);
		getElements().add(new HLineTo(0));
		getElements().add(new VLineTo(0));
	}
}
