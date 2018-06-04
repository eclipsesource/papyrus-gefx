/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (EclipseSource) cletavernier@eclipsesource.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.gmf.style;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.utils.NotationUtil;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.services.style.StyleService;
import org.eclipse.papyrus.gef4.utils.BorderStrokeStyles;
import org.eclipse.papyrus.gef4.utils.ShapeTypeEnum;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderWidths;

//TODO: How to override the StyleProvider only for Nodes?
//=> Check module config
public class ShapeStyleProvider extends NotationStyleService implements StyleService {

	public ShapeStyleProvider(BaseContentPart<? extends View, ?> part) {
		super(part);
	}

	/**
	 * take into account of the double border on padding.
	 *
	 * @return the padding
	 * @see org.eclipse.papyrus.gef4.parts.BaseContentPart#getPadding()
	 */
	@Override
	public Insets getPadding() {
		final Insets padding = super.getPadding();
		double top = 0;
		double right = 0;
		double bottom = 0;
		double left = 0;
		// Only if doubleBorder is applied
		if (hasDoubleBorder() && getShapeType() == ShapeTypeEnum.NONE) {

			final BorderStrokeStyles borderStyles = getBorderStyles();
			if (borderStyles.isDoubleBorder(BorderStrokeStyles.Position.TOP)) {
				top = getDoubleBorderWidths().getTop();
			}
			// Right
			if (borderStyles.isDoubleBorder(BorderStrokeStyles.Position.RIGHT)) {
				right = getDoubleBorderWidths().getRight();
			}
			// Bottom
			if (borderStyles.isDoubleBorder(BorderStrokeStyles.Position.BOTTOM)) {
				bottom = getDoubleBorderWidths().getBottom();
			}
			// Left
			if (borderStyles.isDoubleBorder(BorderStrokeStyles.Position.LEFT)) {
				left = getDoubleBorderWidths().getLeft();
			}
		}
		return new Insets(padding.getTop() + top, padding.getRight() + right, padding.getBottom() + bottom,
				padding.getLeft() + left);
	}

	/**
	 * Gets the border widths.
	 *
	 * @return the border widths
	 */
	@Override
	public BorderWidths getBorderWidths() {
		return NotationUtil.getBorderWidths(getView(), 1);
	}

}
