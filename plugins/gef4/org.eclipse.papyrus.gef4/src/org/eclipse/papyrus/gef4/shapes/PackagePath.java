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

/**
 * Create a package path with the tab dimension tabWidth*tabHeight
 */
public class PackagePath extends Path {

	double width;
	double height;
	double tabWidth;
	double tabHeight;

	public PackagePath(double width, double height, double tabWidth, double tabHeight) {
		this.width = width;
		this.height = height;
		this.tabWidth = tabWidth;
		this.tabHeight = tabHeight;

		init();
	}

	private void init() {
		getElements().add(new MoveTo(0, 0));
		getElements().add(new LineTo(tabWidth, 0f));
		getElements().add(new LineTo(tabWidth, tabHeight));
		getElements().add(new LineTo(width, tabHeight));
		getElements().add(new LineTo(width, height));
		getElements().add(new LineTo(0, height));
		getElements().add(new ClosePath());
	}
}
