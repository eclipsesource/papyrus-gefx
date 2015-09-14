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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gmf.runtime.notation.FillStyle;
import org.eclipse.gmf.runtime.notation.GradientStyle;
import org.eclipse.gmf.runtime.notation.LineStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.appearance.helper.AppearanceHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.NamedStyleProperties;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.PositionEnum;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * The Class NotationUtil.
 */
// TODO add default value as attribute of methods ??

public class NotationUtil {


	public static final String TEXT_OVERFLOW = "textOverflow";



	public static final String GRADIENT_COLOR = "gradientColor";



	public static final String SPACING = "spacing";



	public static final String IS_CORNER_BEND_RECTANGLE = "isCornerBendRectangle";



	public static final String SHAPE_TYPE = "shapeType";



	public static final String ROTATE = "rotate";



	public static final String PADDING = "padding";



	public static final String MARGIN = "margin";



	public static final String BACKGROUND_COLOR = "backgroundColor";



	public static final String EFFECT = "effect";



	public static final String DOUBLE_BORDER_WIDTH = "doubleBorderWidth";



	public static final String CORNER_RADIUS = "cornerRadius";



	public static final String CORNER_BEND_WIDTH = "cornerBendWidth";



	public static final String CORNER_BEND_COLOR = "cornerBendColor";



	public static final String BORDER_WIDTH = "borderWidth";



	public static final String LINE_DASH_GAP = "lineDashGap";



	public static final String LINE_DASH_LENGTH = "lineDashLength";



	public static final String BORDER_COLOR = "borderColor";



	protected static final int[] DEFAULT_CUSTOM_DASH = new int[] { 5, 5 };



	public static final String LIGHTNESS = "lightness";


	/**
	 * The NamedStyle property to set the background paint.
	 */
	private static final String BACKGROUND_PAINT = "backgroundPaint";


	/**
	 * Apply hsl color lightness to a color.
	 *
	 * @param view
	 *            the view
	 * @param color
	 *            the color
	 * @return the color
	 */
	protected static Color applyLightness(final View view, final Color color) {
		Color newColor = color;
		final int colorLightness = getColorLightness(view);
		final List<Integer> hslColor = ColorUtil.getHsl(color);
		if (0 <= colorLightness) {
			hslColor.set(2, colorLightness);
			newColor = ColorUtil.getColor(hslColor.get(0), hslColor.get(1), hslColor.get(2), getOpacity(view));
		}
		return newColor;
	}


	/**
	 * Gets the background paint apply to the view. see {@link Paint#valueOf(String)}
	 *
	 * @param view
	 *            the view
	 * @return the background color
	 */
	public static Paint getBackgroundPaint(final View view) {
		Paint paint = null;
		final String fillColor1 = NotationUtils.getStringValue(view, BACKGROUND_PAINT, null);
		if (null != fillColor1) {
			paint = Paint.valueOf(fillColor1);
		}
		return paint;
	}


	/**
	 * Gets the background gradient end position.
	 *
	 * @param view
	 *            the view
	 * @return the background gradient end position
	 */
	public static Point2D getBackgroundGradientEndPosition(final View view) {
		final FillStyle style = (FillStyle) view.getStyle(NotationPackage.Literals.FILL_STYLE);
		int gradientStyle = GradientStyle.VERTICAL;// Vertical by default //TODO MIA DEFAULT_VALUE

		Point2D endPoint;

		// Get the gradient style
		if (null != style) {
			if (null != style.getGradient()) {
				gradientStyle = style.getGradient().getGradientStyle();
			}
		}

		if (GradientStyle.VERTICAL == gradientStyle) {
			endPoint = new Point2D(0.5, 1);
		} else {
			endPoint = new Point2D(1, 0.5);
		}
		return endPoint;
	}


	/**
	 * Gets the background gradient start position.
	 *
	 * @param view
	 *            the view
	 * @return the background gradient start position
	 */
	public static Point2D getBackgroundGradientStartPosition(final View view) {

		final FillStyle style = (FillStyle) view.getStyle(NotationPackage.Literals.FILL_STYLE);
		Point2D startPoint;

		int gradientStyle = GradientStyle.VERTICAL;// Vertical by default //TODO MIA DEFAULT_VALUE

		// Get the gradient style
		if (null != style) {
			if (null != style.getGradient()) {
				gradientStyle = style.getGradient().getGradientStyle();
			}
		}

		if (GradientStyle.VERTICAL == gradientStyle) {
			startPoint = new Point2D(0.5, 0);
		} else {
			startPoint = new Point2D(0, 0.5);
		}

		return startPoint;
	}



	/**
	 * Gets the border color with attributes lineColor and borderColor.
	 *
	 * @param view
	 *            the view
	 * @return the border color
	 */
	public static BorderColors getBorderColor(final View view) {

		// The different border colors
		BorderColors borderColors = null;

		final LineStyle style = (LineStyle) view.getStyle(NotationPackage.Literals.LINE_STYLE);

		if (null != style) {
			// Set with notation lineStyle
			final Color color = ColorUtil.getColor(style.getLineColor(), 1);
			borderColors = new BorderColors(color);
		} else {

			// Set with borderColor namedStyle
			final EList<String> colors = NotationUtils.getStringListValue(view, BORDER_COLOR, null);
			if (colors != null && 1 == colors.size()) {
				borderColors = new BorderColors(Color.web(colors.get(0)));
			} else if (colors != null && 4 == colors.size()) {
				borderColors = new BorderColors(Color.web(colors.get(0)), Color.web(colors.get(1)), Color.web(colors.get(2)), Color.web(colors.get(3)));
			}
		}
		return null != borderColors ? borderColors : BorderColors.TRANSPARENT;
	}

	/**
	 * Gets the border style.
	 *
	 * @param view
	 *            the view
	 * @param defaultCustomDash
	 *            the default custom dash
	 * @return the border style
	 */
	public static BorderStrokeStyles getBorderStyle(final View view) {

		BorderStrokeStyles borderStyles = null;

		// notation Line
		final LineStyle style = (LineStyle) view.getStyle(NotationPackage.Literals.LINE_TYPE_STYLE); // TODO take into account lineStyle.

		// Set with borderStyle namedStyle
		final EList<String> borderStyleList = NotationUtils.getStringListValue(view, NamedStyleProperties.BORDER_STYLE, null);
		if (null != borderStyleList) {
			if (1 == borderStyleList.size()) {
				final BorderStrokeStyleEnum lineStyle = BorderStrokeStyleEnum.getByLiteral(borderStyleList.get(0));
				if (lineStyle != null) {
					final BorderStrokeStyle borderStrokeStyle = lineStyle.getBorderStrokeStyle();
					borderStyles = new BorderStrokeStyles(borderStrokeStyle);

					// Set double line
					final boolean doubleBorder = lineStyle.equals(BorderStrokeStyleEnum.DOUBLE);
					borderStyles.setDouble(doubleBorder, doubleBorder, doubleBorder, doubleBorder);
				}
			} else if (4 == borderStyleList.size()) {
				final BorderStrokeStyleEnum topLineStyle = BorderStrokeStyleEnum.getByLiteral(borderStyleList.get(0));
				final BorderStrokeStyleEnum rightLineStyle = BorderStrokeStyleEnum.getByLiteral(borderStyleList.get(1));
				final BorderStrokeStyleEnum bottomLineStyle = BorderStrokeStyleEnum.getByLiteral(borderStyleList.get(2));
				final BorderStrokeStyleEnum leftLineStyle = BorderStrokeStyleEnum.getByLiteral(borderStyleList.get(3));

				if (null != topLineStyle && null != rightLineStyle && null != bottomLineStyle && null != leftLineStyle) {

					// Custom BorderStrokeStyle with customDash
					final BorderStrokeStyle customizedStrokeStyle = new BorderStrokeStyle(null, null, null, 10, 0, getCustomStyle(view));

					// customized Dash BorderStrokeStyle with namedStyle lineDashLength and lineDashGap
					final BorderStrokeStyle customizeDashStrokeStyle = new BorderStrokeStyle(null, null, null, 10, 0, getCustomDash(view));

					// Test if its a custom Style then test if its a custom Dash for top
					final BorderStrokeStyle topBorderStrokeStyle = topLineStyle.equals(BorderStrokeStyleEnum.CUSTOM) ? customizedStrokeStyle
							: topLineStyle.equals(BorderStrokeStyleEnum.DASH.getLiteral()) ? customizeDashStrokeStyle : topLineStyle.getBorderStrokeStyle();
							// //Test if its a custom Dash
							// topBorderStrokeStyle = topLineStyle.equals(BorderStrokeStyleEnum.DASH.getLiteral()) ? customizeDashStrokeStyle : topLineStyle.getBorderStrokeStyle();

					// Test if its a custom Style then test if its a custom Dash for right
					final BorderStrokeStyle rightBorderStrokeStyle = rightLineStyle.equals(BorderStrokeStyleEnum.CUSTOM) ? customizedStrokeStyle
							: rightLineStyle.equals(BorderStrokeStyleEnum.DASH) ? customizeDashStrokeStyle : rightLineStyle.getBorderStrokeStyle();

					// Test if its a custom Style then test if its a custom Dash for bottom
					final BorderStrokeStyle bottomBorderStrokeStyle = bottomLineStyle.equals(BorderStrokeStyleEnum.CUSTOM) ? customizedStrokeStyle
							: bottomLineStyle.equals(BorderStrokeStyleEnum.DASH) ? customizeDashStrokeStyle : bottomLineStyle.getBorderStrokeStyle();

					// Test if its a custom Style then test if its a custom Dash for left
					final BorderStrokeStyle leftBorderStrokeStyle = leftLineStyle.equals(BorderStrokeStyleEnum.CUSTOM) ? customizedStrokeStyle
							: leftLineStyle.equals(BorderStrokeStyleEnum.DASH) ? customizeDashStrokeStyle : leftLineStyle.getBorderStrokeStyle();

					borderStyles = new BorderStrokeStyles(topBorderStrokeStyle, rightBorderStrokeStyle, bottomBorderStrokeStyle, leftBorderStrokeStyle);

					// Set double line
					borderStyles.setDouble(
							topLineStyle.equals(BorderStrokeStyleEnum.DOUBLE),
							rightLineStyle.equals(BorderStrokeStyleEnum.DOUBLE),
							bottomLineStyle.equals(BorderStrokeStyleEnum.DOUBLE),
							leftLineStyle.equals(BorderStrokeStyleEnum.DOUBLE));

				}
			}
		}
		return null != borderStyles ? borderStyles : BorderStrokeStyles.SOLID;
	}


	protected static List<Double> getCustomDash(final View view) {
		final List<Double> customDashList = new ArrayList<Double>();
		customDashList.add((double) NotationUtils.getIntValue(view, LINE_DASH_LENGTH, 5));// TODO DEFAULT_VALUE
		customDashList.add((double) NotationUtils.getIntValue(view, LINE_DASH_GAP, 5));// TODO DEFAULT_VALUE
		return customDashList;
	}


	protected static List<Double> getCustomStyle(final View view) {
		final int[] customDash = NotationUtils.getIntListValue(view, NamedStyleProperties.LINE_CUSTOM_VALUE, DEFAULT_CUSTOM_DASH);
		final List<Double> customDashList = new ArrayList<Double>();
		for (int j = 0; j < customDash.length; ++j) {
			customDashList.add((double) customDash[j]);
		}
		return customDashList;
	}

	/**
	 * Gets the border widths.
	 *
	 * @param view
	 *            the view
	 * @return the border widths
	 */
	public static BorderWidths getBorderWidths(final View view) {
		BorderWidths borderWidths = null;
		final int[] borderWidth = NotationUtils.getIntListValue(view, BORDER_WIDTH, null);
		if (null == borderWidth) {
			final LineStyle style = (LineStyle) view.getStyle(NotationPackage.Literals.LINE_STYLE);
			int lineWidth = 1;
			if (null != style && style.getLineWidth() >= 0) {
				lineWidth = style.getLineWidth();
			}
			borderWidths = new BorderWidths(lineWidth);
		} else if (1 == borderWidth.length) {
			borderWidths = new BorderWidths(borderWidth[0]);
		} else if (4 == borderWidth.length) {
			borderWidths = new BorderWidths(borderWidth[0], borderWidth[1], borderWidth[2], borderWidth[3]);
		} else {
			borderWidths = new BorderWidths(1);// TODO DEFAULT_VALUE;
		}
		return borderWidths;
	}


	/**
	 * Gets the color lightness. return -1 if not set.
	 *
	 * @param view
	 *            the view
	 * @return the color lightness
	 */
	public static int getColorLightness(final View view) {
		return NotationUtils.getIntValue(view, LIGHTNESS, -1);
	}


	/**
	 * Gets the corner bend color.
	 *
	 * @param view
	 *            the view
	 * @return the corner bend color
	 */
	public static Paint getCornerBendColor(final View view) {
		return Color.web(NotationUtils.getStringValue(view, CORNER_BEND_COLOR, Color.WHITE.toString()));// TODO MIA DEFAULT_VALUE
	}


	/**
	 * Gets the corner bend width.
	 *
	 * @param view
	 *            the view
	 * @return the corner bend width
	 */
	public static double getCornerBendWidth(final View view) {
		// get the CSS value of cornerBendWidth
		final int cornerBendWidth = NotationUtils.getIntValue(view, CORNER_BEND_WIDTH, 20);// TODO MIA DEFAULT_VALUE
		return cornerBendWidth >= 0 ? cornerBendWidth : 20;
	}


	/**
	 * Gets the corner radii.
	 *
	 * @param view
	 *            the view
	 * @return the corner radii
	 */
	public static CornerRadii getCornerRadii(final View view) {
		CornerRadii cornerRadii;
		// get CSS the value of radius Width
		final double width = NotationUtils.getIntValue(view, NamedStyleProperties.RADIUS_WIDTH, 0);

		// get CSS the value of radius Height
		final double height = NotationUtils.getIntValue(view, NamedStyleProperties.RADIUS_HEIGHT, 0);

		cornerRadii = new CornerRadii(width, height, height, width, width, height, height, width != 0 ? width - 0.01 : 0, false, false, false, false, false, false, false, false);
		// -0.01 is a fix from a bug from CornerRadii with detect the return one as uniform

		// if cornerRadius NamedStyle is implemented
		final int[] cornerRadius = NotationUtils.getIntListValue(view, CORNER_RADIUS, null);
		if (null != cornerRadius && 4 == cornerRadius.length) {
			cornerRadii = new CornerRadii(cornerRadius[0], cornerRadius[1], cornerRadius[2], cornerRadius[3], false);
		}

		return cornerRadii;
	}


	/**
	 * Gets the double border widths.
	 *
	 * @param view
	 *            the view
	 * @return the double border widths
	 */
	public static Insets getDoubleBorderWidths(final View view) {
		Insets borderWidths = new Insets(0);

		// test if node containt a double line.
		if (hasDoubleBorder(view)) {
			final int[] borderWidth = NotationUtils.getIntListValue(view, DOUBLE_BORDER_WIDTH, null);
			final EList<String> borderStyle = NotationUtils.getStringListValue(view, NamedStyleProperties.BORDER_STYLE, null);

			if (null != borderWidth && 1 == borderWidth.length) {
				if (1 == borderStyle.size()) {
					final int width = borderStyle.get(0).equals(BorderStrokeStyleEnum.DOUBLE.getLiteral()) ? borderWidth[0] : 0;
					borderWidths = new Insets(width, width, width, width);
				} else if (4 == borderStyle.size()) {
					borderWidths = new Insets(
							borderStyle.get(0).equals(BorderStrokeStyleEnum.DOUBLE.getLiteral()) ? borderWidth[0] : 0,
							borderStyle.get(1).equals(BorderStrokeStyleEnum.DOUBLE.getLiteral()) ? borderWidth[0] : 0,
							borderStyle.get(2).equals(BorderStrokeStyleEnum.DOUBLE.getLiteral()) ? borderWidth[0] : 0,
							borderStyle.get(3).equals(BorderStrokeStyleEnum.DOUBLE.getLiteral()) ? borderWidth[0] : 0);
				}
			} else if (null != borderWidth && 4 == borderWidth.length) {
				if (1 == borderStyle.size()) {
					final boolean isDouble = borderStyle.get(0).equals(BorderStrokeStyleEnum.DOUBLE.getLiteral());
					borderWidths = new Insets(isDouble ? borderWidth[0] : 0, isDouble ? borderWidth[1] : 0, isDouble ? borderWidth[2] : 0, isDouble ? borderWidth[3] : 0);
				} else if (4 == borderStyle.size()) {
					borderWidths = new Insets(
							borderStyle.get(0).equals(BorderStrokeStyleEnum.DOUBLE.getLiteral()) ? borderWidth[0] : 0,
							borderStyle.get(1).equals(BorderStrokeStyleEnum.DOUBLE.getLiteral()) ? borderWidth[1] : 0,
							borderStyle.get(2).equals(BorderStrokeStyleEnum.DOUBLE.getLiteral()) ? borderWidth[2] : 0,
							borderStyle.get(3).equals(BorderStrokeStyleEnum.DOUBLE.getLiteral()) ? borderWidth[3] : 0);
				}
			} else {
				borderWidths = new Insets(5);// TODO MIA DEFAULT_VALUE;
			}
		}

		return borderWidths;
	}


	/**
	 * Gets the effect.
	 *
	 * @param view
	 *            the view
	 * @return the effect
	 */
	public static Effect getEffect(final View view) {
		Effect returnedEffect = null;

		final String effect = NotationUtils.getStringValue(view, EFFECT, EffectEnum.NONE.getLiteral());
		final EffectEnum effectEnum = EffectEnum.getByLiteral(effect);

		if (EffectEnum.NONE != effectEnum) {
			switch (effectEnum) {
			case REFLECTION:
				returnedEffect = new Reflection();
				break;
			case BLUR:
				returnedEffect = new GaussianBlur();
				break;
			case LIGHTING:
				returnedEffect = new Lighting();
				break;
			case INNER_SHADOW:
				returnedEffect = new InnerShadow();
				break;
			case GLOW:
				returnedEffect = new Glow(0.5);
				break;

			default:
				break;
			}
		}
		return returnedEffect;
	}


	/**
	 * Gets the fill color.
	 *
	 * @param view
	 *            the view
	 * @return the fill color
	 */
	public static Color getFillColor(final View view) {
		final FillStyle style = (FillStyle) view.getStyle(NotationPackage.Literals.FILL_STYLE);
		Color color = Color.TRANSPARENT;
		final double opacity = getOpacity(view);

		if (null != style) {
			final int fillcolor = style.getFillColor();
			color = ColorUtil.getColor(fillcolor, opacity);
		}

		// workaround for compartment which don't support fillColor
		final String fillColor1 = NotationUtils.getStringValue(view, BACKGROUND_COLOR, null);
		if (null != fillColor1) {
			final Color webColor = Color.web(fillColor1);
			color = new Color(webColor.getRed(), webColor.getGreen(), webColor.getBlue(), opacity);
		}

		// apply lightness with hsl color
		color = applyLightness(view, color);

		return color;
	}


	/**
	 * Gets the gradient color1.
	 *
	 * @param view
	 *            the view
	 * @return the gradient color1
	 */
	public static Color getGradientColor(final View view) {

		final FillStyle style = (FillStyle) view.getStyle(NotationPackage.Literals.FILL_STYLE);

		Color color = null;

		// if gradient is enable
		if (null != style && null != style.getGradient()) {
			final int fillcolor = style.getGradient().getGradientColor1();
			color = ColorUtil.getColor(fillcolor, getOpacity(view));
		} else {
			// workaround for compartment which don't support fillColor
			final String fillColor2 = NotationUtils.getStringValue(view, GRADIENT_COLOR, null);
			if (null != fillColor2) {
				final Color webColor = Color.web(fillColor2);
				color = new Color(webColor.getRed(), webColor.getGreen(), webColor.getBlue(), getOpacity(view));
			}
		}
		return null != color ? applyLightness(view, color) : getFillColor(view);
	}


	/**
	 * Gets the margin.
	 *
	 * @param view
	 *            the view
	 * @return the margin
	 */
	public static Insets getMargin(final View view) {
		Insets insets;

		final int[] margin = NotationUtils.getIntListValue(view, MARGIN, null);

		if (null != margin && 1 == margin.length) {
			insets = new Insets(margin[0]);
		} else
			if (null != margin && 4 == margin.length) {
			insets = new Insets(margin[0], margin[1], margin[2], margin[3]);
		} else {
			insets = new Insets(0);
		}
		return insets;
	}


	/**
	 * Gets the opacity.
	 *
	 * @param view
	 *            the view
	 * @return the opacity
	 */
	public static double getOpacity(final View view) {
		final FillStyle style = (FillStyle) view.getStyle(NotationPackage.Literals.FILL_STYLE);
		int transparancy = 0;
		double opacity = 1;
		if (null != style) {
			transparancy = style.getTransparency();
			if (0 <= transparancy) {
				opacity = 1.0 - ((double) transparancy / 100);
			}
		}
		return opacity;
	}


	/**
	 * Gets the padding.
	 *
	 * @param view
	 *            the view
	 * @return the padding
	 */
	public static Insets getPadding(final View view) {
		Insets insets;

		final int[] padding = NotationUtils.getIntListValue(view, PADDING, null);

		if (null != padding && 1 == padding.length) {
			insets = new Insets(padding[0]);
		} else
			if (null != padding && 4 == padding.length) {
			insets = new Insets(padding[0], padding[1], padding[2], padding[3]);
		} else {
			// old Label margin which actually are padding
			final int topMaring = NotationUtils.getIntValue(view, NamedStyleProperties.TOP_MARGIN_PROPERTY, 0);
			final int rightMaring = NotationUtils.getIntValue(view, NamedStyleProperties.RIGHT_MARGIN_PROPERTY, 0);
			final int bottomMaring = NotationUtils.getIntValue(view, NamedStyleProperties.BOTTOM_MARGIN_PROPERTY, 0);
			final int leftMaring = NotationUtils.getIntValue(view, NamedStyleProperties.LEFT_MARGIN_PROPERTY, 0);

			insets = new Insets(topMaring, rightMaring, bottomMaring, leftMaring);
		}

		return insets;
	}


	/**
	 * Gets the rotate.
	 *
	 * @param view
	 *            the view
	 * @return the rotate
	 */
	public static double getRotate(final View view) {
		// get the CSS value of rotate
		return NotationUtils.getIntValue(view, ROTATE, 0);
	}


	/**
	 * Gets the shadow.
	 *
	 * @param view
	 *            the view
	 * @return the shadow
	 */
	public static DropShadow getShadow(final View view) {
		final boolean shadow = AppearanceHelper.showShadow(view);
		DropShadow dropShadow = null;
		if (shadow) {
			final int shadowWidth = getShadowWidth(view);
			dropShadow = new DropShadow(5, shadowWidth, shadowWidth, getShadowColor(view));
		}
		return dropShadow;
	}


	/**
	 * Gets the shadow color.
	 *
	 * @param view
	 *            the view
	 * @return the shadow color
	 */
	public static Color getShadowColor(final View view) {
		return Color.web(NotationUtils.getStringValue(view, NamedStyleProperties.SHADOW_COLOR, Color.BLACK.toString()));// TODO DEFAULT_VALUE
	}


	/**
	 * Gets the shadow width.
	 *
	 * @param view
	 *            the view
	 * @return the shadow width
	 */
	public static int getShadowWidth(final View view) {
		// get the CSS value of shadowWidth
		final int shadowWidth = NotationUtils.getIntValue(view, NamedStyleProperties.SHADOW_WIDTH, 3);// TODO MIA DEFAULT_VALUE
		return shadowWidth >= 0 ? shadowWidth : 3;
	}


	/**
	 * Gets the shape type.
	 *
	 * @param view
	 *            the view
	 * @return the shape type
	 */
	public static ShapeTypeEnum getShapeType(final View view) {
		ShapeTypeEnum shapeTypeEnum = ShapeTypeEnum.NONE;

		// priority: shapeTye>package>CornerBend>Oval
		// Get shape type
		final String shapetype = NotationUtils.getStringValue(view, SHAPE_TYPE, null);
		if (null != shapetype) {
			shapeTypeEnum = ShapeTypeEnum.getByLiteral(shapetype);
		} else {
			final boolean isPackage = NotationUtils.getBooleanValue(view, NamedStyleProperties.IS_PACKAGE, false);
			if (isPackage) {
				shapeTypeEnum = ShapeTypeEnum.PACKAGE;
			} else {
				final boolean isCornerBendRectangle = NotationUtils.getBooleanValue(view, IS_CORNER_BEND_RECTANGLE, false);
				if (isCornerBendRectangle) {
					shapeTypeEnum = ShapeTypeEnum.CORNER_BEND_RECTANGLE;
				} else {
					final boolean isOval = NotationUtils.getBooleanValue(view, NamedStyleProperties.IS_OVAL, false);
					if (isOval) {
						shapeTypeEnum = ShapeTypeEnum.OVAL;
					}

				}
			}
		}
		return shapeTypeEnum;
	}


	/**
	 * Gets the spacing.
	 *
	 * @param view
	 *            the view
	 * @return the spacing
	 */
	public static double getSpacing(final View view) {
		return NotationUtils.getIntValue(view, SPACING, 0);
	}


	/**
	 * Gets the text alignment.
	 *
	 * @param view
	 *            the view
	 * @return the text alignment
	 */
	public static Pos getTextAlignment(final View view) {
		// get the value of the CSS property
		final String labelAlignment = NotationUtils.getStringValue(view, NamedStyleProperties.TEXT_ALIGNMENT, null);

		Pos textAlignment = Pos.TOP_CENTER;
		if (labelAlignment != null) {
			if (PositionEnum.LEFT.toString().equals(labelAlignment)) {
				textAlignment = Pos.TOP_LEFT;
			} else
				if (PositionEnum.RIGHT.toString().equals(labelAlignment)) {
				textAlignment = Pos.TOP_RIGHT;
			} else
					if (PositionEnum.CENTER.toString().equals(labelAlignment)) {
				textAlignment = Pos.BASELINE_CENTER;
			}
		}
		return textAlignment;
	}


	/**
	 * Checks for double border.
	 *
	 * @param view
	 *            the view
	 * @return true, if successful
	 */
	public static boolean hasDoubleBorder(final View view) {
		final EList<String> borderStyleList = NotationUtils.getStringListValue(view, NamedStyleProperties.BORDER_STYLE, null);
		return null != borderStyleList ? borderStyleList.contains(BorderStrokeStyleEnum.DOUBLE.getLiteral()) : false;
	}

	public static TextOverflowEnum getTextOverflow(final View view) {
		TextOverflowEnum textOverflowEnum = null;

		final String shapetype = NotationUtils.getStringValue(view, TEXT_OVERFLOW, null);
		if (null != shapetype) {
			textOverflowEnum = TextOverflowEnum.getByLiteral(shapetype);
		}
		return null != textOverflowEnum ? textOverflowEnum : TextOverflowEnum.HIDDEN;
	}


	public static ScrollBarPolicy getVerticalBarPolicy(final View view) {

		final String verticalPolicy = NotationUtils.getStringValue(view, "verticalScrollBar", null);

		ScrollBarPolicy scrollBarPolicy = ScrollBarPolicy.NEVER;
		if (verticalPolicy != null) {
			if ("always".equals(verticalPolicy)) {
				scrollBarPolicy = ScrollBarPolicy.ALWAYS;
			} else
				if ("asNeeded".equals(verticalPolicy)) {
				scrollBarPolicy = ScrollBarPolicy.AS_NEEDED;
			} else
					if ("never".equals(verticalPolicy)) {
				scrollBarPolicy = ScrollBarPolicy.NEVER;
			}
		}
		return scrollBarPolicy;

	}


	public static ScrollBarPolicy getHorizontalBarPolicy(final View view) {
		final String verticalPolicy = NotationUtils.getStringValue(view, "horizontalScrollBar", null);

		ScrollBarPolicy scrollBarPolicy = ScrollBarPolicy.NEVER;
		if (verticalPolicy != null) {
			if ("always".equals(verticalPolicy)) {
				scrollBarPolicy = ScrollBarPolicy.ALWAYS;
			} else
				if ("asNeeded".equals(verticalPolicy)) {
				scrollBarPolicy = ScrollBarPolicy.AS_NEEDED;
			} else
					if ("never".equals(verticalPolicy)) {
				scrollBarPolicy = ScrollBarPolicy.NEVER;
			}
		}
		return scrollBarPolicy;

	}
}
