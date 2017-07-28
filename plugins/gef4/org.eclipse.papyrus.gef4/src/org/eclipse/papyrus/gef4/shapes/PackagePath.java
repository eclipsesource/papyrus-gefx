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

import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;

/**
 * Create a package path with the tab dimension tabWidth*tabHeight
 */
public class PackagePath extends Path {

	double width;
	double height;
	double tabWidth;
	double tabHeight;

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
	public PackagePath(double width, double height, double tabWidth, double tabHeight, double minWidthDelta, double minHeightDelta) {
		this.width = Math.max(width, tabWidth + minWidthDelta);
		this.height = Math.max(height, tabHeight + minHeightDelta);
		this.tabWidth = tabWidth;
		this.tabHeight = tabHeight;

		init();
	}

	private void init() {
		getElements().add(new MoveTo(0, 0));
		getElements().add(new HLineTo(tabWidth));
		getElements().add(new VLineTo(tabHeight));
		getElements().add(new HLineTo(width));
		getElements().add(new VLineTo(height));
		getElements().add(new HLineTo(0));
		getElements().add(new VLineTo(0));
	}
}
