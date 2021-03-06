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
	CORNER_BEND_RECTANGLE("cornerBendRectangle"), //$NON-NLS-1$
	EMPTY_CIRCLE("emptyCircle"), //$NON-NLS-1$
	FULL_CIRCLE("fullCircle"), //$NON-NLS-1$
	DOT_CIRCLE("dotCircle"), //$NON-NLS-1$
	CROSS_CIRCLE("crossCircle"), //$NON-NLS-1$
	EMPTY_DIAMOND("emptyDiamond"), //$NON-NLS-1$
	FULL_DIAMOND("fullDiamond");//$NON-NLS-1$


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

	/**
	 * Gets the by literal.
	 *
	 * @param literal
	 *            the literal
	 * @return the by literal
	 */
	public static ShapeTypeEnum getByLiteral(final String literal) {
		if (literal == null) {
			return null;
		}

		for (ShapeTypeEnum shapeType : ShapeTypeEnum.values()) {
			if (literal.equalsIgnoreCase(shapeType.getLiteral())) {
				return shapeType;
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
