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
public enum ShapeTypeEnum {
	NONE("none"), //$NON-NLS-1$
	PACKAGE("package"), //$NON-NLS-1$
	OVAL("oval"), //$NON-NLS-1$
	CORNER_BEND_RECTANGLE("cornerBendRectangle");//$NON-NLS-1$


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
	private ShapeTypeEnum(final String literal) {
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

	/** The Constant LINE_STYLE_ARRAY. */
	private static final ShapeTypeEnum[] LINE_STYLE_ARRAY = new ShapeTypeEnum[] { NONE, PACKAGE, OVAL, CORNER_BEND_RECTANGLE };

	/**
	 * Gets the by literal.
	 *
	 * @param literal
	 *            the literal
	 * @return the by literal
	 */
	public static ShapeTypeEnum getByLiteral(final String literal) {
		for (int i = 0; i < LINE_STYLE_ARRAY.length; ++i) {
			final ShapeTypeEnum result = LINE_STYLE_ARRAY[i];
			if (result.getLiteral().equals(literal)) {
				return result;
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
