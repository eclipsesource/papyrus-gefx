/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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
package org.eclipse.papyrus.gef4.utils;

import javafx.geometry.Insets;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 * A set of utility methods to manipulate FX Nodes
 *
 * @author Camille Letavernier
 *
 */
public class FXUtils {

	/**
	 * Utility method for quickly changing the padding of an element.
	 *
	 * The same value is used for respectively Top and Bottom, and Left and Right
	 *
	 * @param region
	 *            The region to modify
	 * @param topBottom
	 *            Top and Bottom padding
	 * @param leftRight
	 *            Left and Right padding
	 */
	public static final void setPadding(Region region, int topBottom, int leftRight) {
		region.setPadding(new Insets(topBottom, leftRight, topBottom, leftRight));
	}

	private static double REM;

	/**
	 * Converts a font in pixels to points, taking current DPI into account
	 *
	 * @param pixels
	 * @return
	 */
	public static final double scaleFont(int pixels) {
		if (REM < 0.01) {
			REM = Math.rint(new Text("").getLayoutBounds().getHeight());
		}
		return REM * pixels / 12;
	}
}
