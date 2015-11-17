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

/**
 * The Enum ShapeTypeEnum.
 */
public enum TextOverflowEnum {
	WRAP("wrap"), //$NON-NLS-1$
	HIDDEN("hidden"), //$NON-NLS-1$
	VISIBLE("visible"); //$NON-NLS-1$


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
	private TextOverflowEnum(final String literal) {
		this.literal = literal;
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
	public static TextOverflowEnum getByLiteral(final String literal) {
		if (literal == null) {
			return null;
		}

		for (TextOverflowEnum overflow : TextOverflowEnum.values()) {
			if (literal.equalsIgnoreCase(overflow.getLiteral())) {
				return overflow;
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
