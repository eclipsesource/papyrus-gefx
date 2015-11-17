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
 * The Effect Enum.
 */
public enum EffectEnum {

	/** none effect. */
	NONE("none"), // $NON-NLS-1$
	/** The reflection effect. */
	REFLECTION("reflection"), // $NON-NLS-1$
	/** The blur effect. */
	BLUR("blur"), // $NON-NLS-1$
	/** The lighting effect. */
	LIGHTING("lighting"), // $NON-NLS-1$
	/** The inner shadow effect. */
	INNER_SHADOW("innerShadow"), // $NON-NLS-1$
	/** The glow effect. */
	GLOW("glow");//$NON-NLS-1$

	/** The literal. */
	private String literal;

	/**
	 * Instantiates a new line style enum.
	 *
	 * @param literal
	 *            the literal
	 */
	private EffectEnum(final String literal) {
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
	public static EffectEnum getByLiteral(final String literal) {
		if (literal == null) {
			return null;
		}

		for (EffectEnum effect : EffectEnum.values()) {
			if (literal.equalsIgnoreCase(effect.getLiteral())) {
				return effect;
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
