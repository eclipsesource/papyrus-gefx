/*****************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  EclipseSource - Initial API and Implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.services.style.impl;

import org.eclipse.papyrus.gef4.services.style.StyleService;
import org.eclipse.papyrus.gef4.utils.BorderColors;
import org.eclipse.papyrus.gef4.utils.BorderStrokeStyles;
import org.eclipse.papyrus.gef4.utils.ShapeTypeEnum;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class EmptyStyleService implements StyleService {

	
	@Override
	public double getWidth() {
		return 0;
	}

	@Override
	public double getHeight() {
		return 0;
	}

	@Override
	public double getY() {
		return 0;
	}

	@Override
	public double getX() {
		return 0;
	}

	@Override
	public ScrollBarPolicy getHorizontalBarPolicy() {
		return ScrollBarPolicy.AS_NEEDED;
	}

	@Override
	public ScrollBarPolicy getVerticalBarPolicy() {
		return ScrollBarPolicy.AS_NEEDED;
	}

	@Override
	public double getRotate() {
		return 0;
	}

	@Override
	public Color getFontColor() {
		return Color.BLACK;
	}

	@Override
	public int getNotationFontColor() {
		return 0;
	}

	@Override
	public int getFontSize() {
		return 12;
	}

	@Override
	public String getFontName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Font getFont(int fontSize) {
		return Font.font(fontSize);
	}

	@Override
	public Font getFont() {
		return Font.getDefault();
	}

	@Override
	public double getCornerBendWidth() {
		return 0;
	}

	@Override
	public Paint getCornerBendColor() {
		return Color.BLACK;
	}

	@Override
	public Effect getEffect() {
		return null;
	}

	@Override
	public int getShadowWidth() {
		return 0;
	}

	@Override
	public Color getShadowColor() {
		return Color.BLACK;
	}

	@Override
	public DropShadow getShadow() {
		return null;
	}

	@Override
	public Pos getTextAlignment() {
		return Pos.BASELINE_LEFT;
	}

	@Override
	public Insets getDoubleBorderWidths() {
		return null;
	}

	@Override
	public boolean hasDoubleBorder() {
		return false;
	}

	@Override
	public BorderWidths getBorderWidths() {
		return new BorderWidths(1);
	}

	@Override
	public ShapeTypeEnum getShapeType() {
		return ShapeTypeEnum.NONE;
	}

	@Override
	public double getSpacing() {
		return 0;
	}

	@Override
	public Insets getPadding() {
		return new Insets(0);
	}

	@Override
	public Insets getMargin() {
		return null;
	}

	@Override
	public CornerRadii getCornerRadii() {
		return null;
	}

	@Override
	public BorderStrokeStyles getBorderStyles() {
		return BorderStrokeStyles.SOLID;
	}

	@Override
	public Point2D getBackgroundGradientEndPosition() {
		return new Point2D(0, 1);
	}

	@Override
	public Point2D getBackgroundGradientStartPosition() {
		return new Point2D(0, 0);
	}

	@Override
	public Paint getBackgroundPaint() {
		return Color.WHITE;
	}

	@Override
	public Color getBackgroundColor2() {
		return Color.WHITE;
	}

	@Override
	public Color getBackgroundColor1() {
		return Color.WHITE;
	}

	@Override
	public int getTransparency() {
		return 0;
	}

	@Override
	public BorderColors getBorderColors() {
		return new BorderColors(Color.BLACK);
	}

}
