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
package org.eclipse.papyrus.gef4.services.style;

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

public interface StyleService {

	double getWidth();

	double getHeight();

	double getY();

	double getX();

	ScrollBarPolicy getHorizontalBarPolicy();

	ScrollBarPolicy getVerticalBarPolicy();

	double getRotate();

	Color getFontColor();

	int getNotationFontColor();

	int getFontSize();

	String getFontName();

	Font getFont(int fontSize);

	Font getFont();

	double getCornerBendWidth();

	Paint getCornerBendColor();

	Effect getEffect();

	int getShadowWidth();

	Color getShadowColor();

	DropShadow getShadow();

	Pos getTextAlignment();

	Insets getDoubleBorderWidths();

	boolean hasDoubleBorder();

	BorderWidths getBorderWidths();

	ShapeTypeEnum getShapeType();

	double getSpacing();

	Insets getPadding();

	Insets getMargin();

	CornerRadii getCornerRadii();

	BorderStrokeStyles getBorderStyles();

	Point2D getBackgroundGradientEndPosition();

	Point2D getBackgroundGradientStartPosition();

	Paint getBackgroundPaint();

	Color getBackgroundColor2();

	Color getBackgroundColor1();

	int getTransparency();

	BorderColors getBorderColors();

}
