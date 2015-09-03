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
package org.eclipse.papyrus.gef4.utils;

import javafx.scene.paint.Color;

/**
 * The Class BorderColors.
 */
public final class BorderColors {


	/** The top color. */
	Color top;

	/** The right color. */
	Color right;

	/** The bottom color. */
	Color bottom;

	/** The left color. */
	Color left;

	/** A BorderColors with all border set to {@link Color#BLACK}. */
	public static final BorderColors BLACK = new BorderColors(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK);

	/** A BorderColors with all border set to {@link Color#TRANSPARENT}. */
	public static final BorderColors TRANSPARENT = new BorderColors(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);
	/**
	 * Creates a new BorderColors using the given color for all four borders.
	 *
	 * @param color
	 *            the color
	 */
	public BorderColors(final Color color) {
		this(color, color, color, color);
	}

	/**
	 * Creates a new BorderWidths.
	 *
	 * @param top
	 *            the top
	 * @param right
	 *            the right
	 * @param bottom
	 *            the bottom
	 * @param left
	 *            the left
	 */
	public BorderColors(final Color top, final Color right, final Color bottom, final Color left) {
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
	}

	/**
	 * Gets the top color.
	 *
	 * @return the top color
	 */
	public final Color getTop() {
		return top;
	}

	/**
	 * Gets the right color.
	 *
	 * @return the right color
	 */
	public final Color getRight() {
		return right;
	}

	/**
	 * Gets the bottom color.
	 *
	 * @return the bottom color
	 */
	public final Color getBottom() {
		return bottom;
	}

	/**
	 * Gets the left color.
	 *
	 * @return the left color
	 */
	public final Color getLeft() {
		return left;
	}

}
