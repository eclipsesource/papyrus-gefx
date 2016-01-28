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

import javafx.scene.layout.BorderStrokeStyle;

/**
 * The Class BorderBorderStrokeStyles.
 */
public class BorderStrokeStyles {

	/** A BorderStrokeStyles with all borders set to {@link BorderStrokeStyle#SOLID}. */
	public static final BorderStrokeStyles SOLID = new BorderStrokeStyles(BorderStrokeStyle.SOLID);

	/** A BorderStrokeStyles with all borders set to {@link BorderStrokeStyle#DASHED}. */
	public static final BorderStrokeStyles DASHED = new BorderStrokeStyles(BorderStrokeStyle.DASHED);

	/** A BorderStrokeStyles with all borders set to {@link BorderStrokeStyle#DOTTED}. */
	public static final BorderStrokeStyles DOTTED = new BorderStrokeStyles(BorderStrokeStyle.DOTTED);

	/** A BorderStrokeStyles with all borders set to {@link BorderStrokeStyle#NONE}. */
	public static final BorderStrokeStyles NONE = new BorderStrokeStyles(BorderStrokeStyle.NONE);

	/** The top BorderStrokeStyle. */
	private final BorderStrokeStyle top;
	private boolean topDouble;

	/** The right BorderStrokeStyle. */
	private final BorderStrokeStyle right;
	private boolean rightDouble;

	/** The bottom BorderStrokeStyle. */
	private final BorderStrokeStyle bottom;
	private boolean bottomDouble;

	/** The left BorderStrokeStyle. */
	private final BorderStrokeStyle left;
	private boolean leftDouble;

	/**
	 * Creates a new BorderBorderStrokeStyles using the BorderStrokeStyle {@link BorderStrokeStyle#SOLID} for all four borders.
	 */
	public BorderStrokeStyles() {
		this(BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID);
	}

	/**
	 * Creates a new BorderBorderStrokeStyles using the given BorderStrokeStyle for all four borders.
	 *
	 * @param BorderStrokeStyle
	 *            the BorderStrokeStyle
	 */
	public BorderStrokeStyles(final BorderStrokeStyle BorderStrokeStyle) {
		this(BorderStrokeStyle, BorderStrokeStyle, BorderStrokeStyle, BorderStrokeStyle);
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
	public BorderStrokeStyles(final BorderStrokeStyle top, final BorderStrokeStyle right, final BorderStrokeStyle bottom, final BorderStrokeStyle left) {
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
	}

	/**
	 * Gets the top BorderStrokeStyle.
	 *
	 * @return the top BorderStrokeStyle
	 */
	public final BorderStrokeStyle getTop() {
		return top;
	}

	/**
	 * Gets the right BorderStrokeStyle.
	 *
	 * @return the right BorderStrokeStyle
	 */
	public final BorderStrokeStyle getRight() {
		return right;
	}

	/**
	 * Gets the bottom BorderStrokeStyle.
	 *
	 * @return the bottom BorderStrokeStyle
	 */
	public final BorderStrokeStyle getBottom() {
		return bottom;
	}

	/**
	 * Gets the left BorderStrokeStyle.
	 *
	 * @return the left BorderStrokeStyle
	 */
	public final BorderStrokeStyle getLeft() {
		return left;
	}

	/**
	 * Sets the line witch are double.
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
	public void setDouble(final boolean top, final boolean right, final boolean bottom, final boolean left) {
		this.topDouble = top;
		this.rightDouble = right;
		this.bottomDouble = bottom;
		this.leftDouble = left;
	}

	public boolean isDoubleBorder(final Position position) {
		boolean value = false;
		switch (position) {
		case TOP:
			value = topDouble;
			break;
		case RIGHT:
			value = rightDouble;
			break;
		case BOTTOM:
			value = bottomDouble;
			break;
		case LEFT:
			value = leftDouble;
			break;

		default:
			break;
		}
		return value;
	}

	public enum Position {
		TOP, RIGHT, BOTTOM, LEFT
	};

}
