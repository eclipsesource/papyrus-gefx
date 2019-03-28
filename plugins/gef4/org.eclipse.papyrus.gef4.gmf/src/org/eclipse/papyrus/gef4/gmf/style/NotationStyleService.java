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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.FillStyle;
import org.eclipse.gmf.runtime.notation.FontStyle;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Size;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.utils.NotationUtil;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.parts.IPrimaryContentPart;
import org.eclipse.papyrus.gef4.services.style.StyleService;
import org.eclipse.papyrus.gef4.utils.BorderColors;
import org.eclipse.papyrus.gef4.utils.BorderStrokeStyles;
import org.eclipse.papyrus.gef4.utils.FXUtils;
import org.eclipse.papyrus.gef4.utils.ShapeTypeEnum;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class NotationStyleService extends AbstractNotationStyleService implements StyleService {

	protected EObject findSemanticElement() {
		final EObject element = getView().getElement();
		if (element == null) {
			if (this instanceof IPrimaryContentPart) {
				return null; // Do not go beyond the Primary part
			}

			final BaseContentPart<? extends View, ? extends Node> parent = getAdaptable().getParentBaseContentPart();
			if (parent != null) {
				return parent.getContent().getElement();
			}
		}
		return element;
	}

	@Override
	protected void doActivate() {
		super.doActivate();
		installListeners();
	}

	protected void installListeners() {
		DiagramEventBroker eventBroker = getEventBroker();
		eventBroker.addNotificationListener(getView(), getNotificationListener());
		final LayoutConstraint layout = getLayout();
		if (layout != null) {
			eventBroker.addNotificationListener(layout, getNotificationListener());
		}
	}

	@Override
	protected void doDeactivate() {
		uninstallListeners();
		super.doDeactivate();
	}

	protected void uninstallListeners() {
		DiagramEventBroker eventBroker = getEventBroker();

		eventBroker.removeNotificationListener(getView(), getNotificationListener());
		final LayoutConstraint layout = getLayout();
		if (layout != null) {
			eventBroker.removeNotificationListener(layout, getNotificationListener());
		}
	}

	@Override
	public BorderColors getBorderColors() {
		return NotationUtil.getBorderColor(getView());
	}

	@Override
	public int getTransparency() {
		int transparency = 0;
		final FillStyle style = (FillStyle) getView().getStyle(NotationPackage.Literals.FILL_STYLE);
		if (null != style) {
			transparency = style.getTransparency();
		}
		return transparency;
	}

	@Override
	public Color getBackgroundColor1() {
		return NotationUtil.getFillColor(getView());
	}

	@Override
	public Color getBackgroundColor2() {
		return NotationUtil.getGradientColor(getView());
	}

	/**
	 * Gets the background Paint.
	 *
	 * @return the background color
	 */
	@Override
	public Paint getBackgroundPaint() {
		return NotationUtil.getBackgroundPaint(getView());
	}

	@Override
	public Point2D getBackgroundGradientStartPosition() {
		return NotationUtil.getBackgroundGradientStartPosition(getView());
	}

	@Override
	public Point2D getBackgroundGradientEndPosition() {
		return NotationUtil.getBackgroundGradientEndPosition(getView());
	}

	@Override
	public BorderStrokeStyles getBorderStyles() {
		return NotationUtil.getBorderStyle(getView());
	}

	/**
	 * Gets the corner radii.
	 *
	 * @return the corner radii
	 */
	@Override
	public CornerRadii getCornerRadii() {
		return NotationUtil.getCornerRadii(getView());
	}

	/**
	 * Gets the margin.
	 *
	 * @return the margin
	 */
	@Override
	public Insets getMargin() {
		return NotationUtil.getMargin(getView());
	}

	/**
	 * Gets the padding.
	 *
	 * @return the padding
	 */
	@Override
	public Insets getPadding() {
		return NotationUtil.getPadding(getView());
	}

	/**
	 * Gets the spacing.
	 *
	 * @return the spacing
	 */
	@Override
	public double getSpacing() {
		return NotationUtil.getSpacing(getView());
	}

	/**
	 * Gets the shape type.
	 *
	 * @return the shape type
	 */
	@Override
	public ShapeTypeEnum getShapeType() {
		return NotationUtil.getShapeType(getView());
	}

	/**
	 * Gets the border widths.
	 *
	 * @return the border widths
	 */
	@Override
	public BorderWidths getBorderWidths() {
		return NotationUtil.getBorderWidths(getView(), 0);
	}

	/**
	 * Checks for double border.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean hasDoubleBorder() {
		return NotationUtil.hasDoubleBorder(getView());
	}

	/**
	 * Gets the double border widths.
	 *
	 * @return the double border widths
	 */
	@Override
	public Insets getDoubleBorderWidths() {
		return NotationUtil.getDoubleBorderWidths(getView());
	}

	/**
	 * Gets the text alignment.
	 *
	 * @return the text alignment
	 */
	@Override
	public Pos getTextAlignment() {
		return NotationUtil.getTextAlignment(getView());
	}

	/**
	 * Gets the shadow.
	 *
	 * @return the shadow
	 */
	@Override
	public DropShadow getShadow() {
		return NotationUtil.getShadow(getView());
	}

	/**
	 * Gets the shadow color.
	 *
	 * @return the shadow color
	 */
	@Override
	public Color getShadowColor() {
		return NotationUtil.getShadowColor(getView());
	}

	/**
	 * Gets the shadow width.
	 *
	 * @return the shadow width
	 */
	@Override
	public int getShadowWidth() {
		return NotationUtil.getShadowWidth(getView());
	}

	/**
	 * Gets the effect.
	 *
	 * @return the effect
	 */
	@Override
	public Effect getEffect() {
		return NotationUtil.getEffect(getView());
	}

	/**
	 * Gets the corner bend color.
	 *
	 * @return the corner bend color
	 */
	@Override
	public Paint getCornerBendColor() {
		return NotationUtil.getCornerBendColor(getView());
	}

	/**
	 * Gets the corner bend width.
	 *
	 * @return the corner bend width
	 */
	@Override
	public double getCornerBendWidth() {
		return NotationUtil.getCornerBendWidth(getView());
	}

	/**
	 * @return The FX Font, corresponding to the View's fontName and fontSize
	 */
	@Override
	public Font getFont() {
		return getFont(getFontSize());
	}

	/**
	 *
	 * @param fontSize The font size (In pixels)
	 * @return The FX Font, corresponding to this View's fontName and specified
	 *         fontSize (in pixels)
	 */
	@Override
	public Font getFont(int fontSize) {
		return new Font(getFontName(), FXUtils.scaleFont(fontSize));
	}

	/**
	 *
	 * @return The name of the font to use to render this part
	 */
	@Override
	public String getFontName() {
		View view = getView();
		FontStyle fontStyle = (FontStyle) view.getStyle(NotationPackage.eINSTANCE.getFontStyle());
		if (fontStyle == null) {
			return "Roboto"; //$NON-NLS-1$ //
		}

		return fontStyle.getFontName();
	}

	/**
	 *
	 * @return The size of the font to use to render this part
	 */
	@Override
	public int getFontSize() {
		View view = getView();
		FontStyle fontStyle = (FontStyle) view.getStyle(NotationPackage.eINSTANCE.getFontStyle());
		if (fontStyle == null) {
			return 9; //
		}

		return fontStyle.getFontHeight();
	}

	/**
	 *
	 * @return The font color, as a 24-bits integer (#00BBGGRR)
	 */
	@Override
	public int getNotationFontColor() {
		View view = getView();

		FontStyle fontStyle = (FontStyle) view.getStyle(NotationPackage.eINSTANCE.getFontStyle());
		if (fontStyle == null) {
			return 0; // Black,
		}

		return fontStyle.getFontColor();
	}

	/**
	 *
	 * @return The font color, as a JavaFX Color
	 */
	@Override
	public Color getFontColor() {
		int color = getNotationFontColor();

		return Color.rgb(color & 255, (color >> 8) & 255, color >> 16 & 255); // & 255 to retain only the last 8-bits
	}

	/**
	 * Gets the rotate.
	 *
	 * @return the rotate
	 */
	@Override
	public double getRotate() {
		return NotationUtil.getRotate(getView());
	}

	@Override
	public ScrollBarPolicy getVerticalBarPolicy() {
		return ScrollBarPolicy.AS_NEEDED;
	}

	@Override
	public ScrollBarPolicy getHorizontalBarPolicy() {
		return ScrollBarPolicy.AS_NEEDED;
	}

	// protected int getNotationMinWidth() {
	// return NotationUtil.getNotationMinWidth(view);
	// }
	//
	// protected int getNotationMinHeight() {
	// return NotationUtil.getNotationMinHeight(view);
	// }

	// May be null
	public LayoutConstraint getLayout() {
		if (getView() instanceof org.eclipse.gmf.runtime.notation.Node) {
			return ((org.eclipse.gmf.runtime.notation.Node) getView()).getLayoutConstraint();
		}
		return null;
	}

	/**
	 * <p>
	 * Returns the Bounds for the current view (Corresponding to its
	 * LocationConstraint). Note that if the location constraint is a simple Size or
	 * Location, they will be converted to Bounds for convenience.
	 * </p>
	 *
	 * @return
	 */
	public Bounds getBounds() {
		final LayoutConstraint constraint = getLayout();
		if (constraint instanceof Bounds) {
			return (Bounds) constraint;
		} else if (constraint instanceof Location) {
			// TODO Check this for floating labels
			final Bounds bounds = NotationFactory.eINSTANCE.createBounds();
			bounds.setX(((Location) constraint).getX());
			bounds.setY(((Location) constraint).getY());
			bounds.setWidth(-1);
			bounds.setHeight(-1);

			return bounds;
		} else if (constraint instanceof Size) {
			final Bounds bounds = NotationFactory.eINSTANCE.createBounds();
			bounds.setX(0);
			bounds.setY(0);
			bounds.setWidth(((Size) constraint).getWidth());
			bounds.setHeight(((Size) constraint).getHeight());

			return bounds;
		}

		return null;
	}

	@Override
	public double getX() {
		final Bounds bounds = getBounds();
		return bounds == null ? 0 : bounds.getX();
	}

	@Override
	public double getY() {
		final Bounds bounds = getBounds();
		return bounds == null ? 0 : bounds.getY();
	}

	@Override
	public double getHeight() {
		final Bounds bounds = getBounds();
		return bounds == null ? 0 : Math.max(20, bounds.getHeight());
	}

	@Override
	public double getWidth() {
		final Bounds bounds = getBounds();
		return bounds == null ? 0 : Math.max(20, bounds.getWidth());
	}
}
