/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.utils;

import java.util.Arrays;

import javafx.scene.layout.BorderStrokeStyle;

/**
 * Enumeration to define line style as String related to the Open Declaration {@link BorderStrokeStyle}
 *
 * @author Mickael ADAM
 *
 */
public enum BorderStrokeStyleEnum {

	/** The dash. */
	DASH(BorderStrokeStyle.DASHED, "dash"), //$NON-NLS-1$

	/** The dash dot. */
	DASH_DOT(new BorderStrokeStyle(null, null, null, 10, 0, Arrays.asList(5.0, 2.0, 1.0, 2.0)), "dashDot"), //$NON-NLS-1$

	/** The dash dot dot. */
	DASH_DOT_DOT(new BorderStrokeStyle(null, null, null, 10, 0, Arrays.asList(5.0, 2.0, 1.0, 2.0, 1.0, 2.0)), "dashDotDot"), //$NON-NLS-1$

	/** The dot. */
	DOT(new BorderStrokeStyle(null, null, null, 10, 0, Arrays.asList(1.0, 1.0)), "dot"), //$NON-NLS-1$

	/** The solid. */
	SOLID(BorderStrokeStyle.SOLID, "solid"), //$NON-NLS-1$

	/** The custom. */
	CUSTOM(new BorderStrokeStyle(null, null, null, 10, 0, Arrays.asList(5.0, 5.0)), "custom"), //$NON-NLS-1$

	/** The double. Is a solid line with a custom line as decoration */
	DOUBLE(BorderStrokeStyle.SOLID, "double"); //$NON-NLS-1$


	/** The line style. */
	private BorderStrokeStyle lineStyle;

	/** The literal. */
	private String literal;

	/**
	 * Instantiates a new line style enum.
	 *
	 * @param lineStyle
	 *            the line style
	 * @param literal
	 *            the literal
	 */
	private BorderStrokeStyleEnum(BorderStrokeStyle lineStyle, String literal) {
		this.lineStyle = lineStyle;
		this.literal = literal;
	}

	/**
	 * Gets the line style.
	 *
	 * @return the line style
	 */
	public BorderStrokeStyle getBorderStrokeStyle() {
		return lineStyle;
	}

	/**
	 * Gets the literal.
	 *
	 * @return the literal
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Gets the by literal.
	 *
	 * @param literal
	 *            the literal
	 * @return the by literal
	 */
	public static BorderStrokeStyleEnum getByLiteral(String literal) {
		if (literal == null) {
			return null;
		}

		for (BorderStrokeStyleEnum strokeStyle : BorderStrokeStyleEnum.values()) {
			if (literal.equalsIgnoreCase(strokeStyle.getLiteral())) {
				return strokeStyle;
			}
		}

		return null;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return literal;
	}
}
