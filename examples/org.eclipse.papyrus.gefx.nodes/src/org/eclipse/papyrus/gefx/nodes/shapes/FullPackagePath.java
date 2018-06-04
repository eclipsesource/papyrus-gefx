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
package org.eclipse.papyrus.gefx.nodes.shapes;

import javafx.beans.binding.NumberExpression;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;

/**
 * Create a package path with the tab dimension tabWidth*tabHeight
 */
public class FullPackagePath extends Path {

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
	 *            the minimum difference between the width of the package and the
	 *            width of the tab
	 * @param minHeightDelta
	 *            the minimum difference between the height of the package and the
	 *            height of the tab
	 */
	public FullPackagePath(NumberExpression width, NumberExpression height, NumberExpression tabWidth,
			NumberExpression tabHeight, double minWidthDelta, double minHeightDelta) {
		this.tabWidth = tabWidth;
		this.tabHeight = tabHeight;
		this.width = width;
		this.height = height;

		init();
	}

	private void init() {
		// Start below the label; we'll draw it last
		MoveTo initialPosition = new MoveTo();
		initialPosition.yProperty().bind(tabHeight);
		initialPosition.setX(0);
		getElements().add(initialPosition);

		HLineTo topLine = new HLineTo();
		topLine.xProperty().bind(width);

		getElements().add(topLine);
		VLineTo packageHeightLine = new VLineTo();
		packageHeightLine.yProperty().bind(height);
		getElements().add(packageHeightLine);

		getElements().add(new HLineTo(0));
		// Go back to the top of the tab header
		getElements().add(new VLineTo(0));
		// Draw the tab header
		HLineTo tabTopLine = new HLineTo();
		tabTopLine.xProperty().bind(tabWidth);
		getElements().add(tabTopLine);
		VLineTo tabRightLine = new VLineTo();
		tabRightLine.yProperty().bind(tabHeight);
		getElements().add(tabRightLine);
	}
}
