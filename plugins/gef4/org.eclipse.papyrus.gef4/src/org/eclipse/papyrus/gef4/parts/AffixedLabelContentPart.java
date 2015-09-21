/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.gef4.parts;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.utils.TextOverflowEnum;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;

/**
 * The Class AffixedLabelContentPart.
 */
public class AffixedLabelContentPart extends org.eclipse.papyrus.gef4.parts.LabelContentPart implements IPrimaryContentPart {

	/**
	 * Instantiates a new affixed label content part.
	 *
	 * @param view
	 *            the view
	 */
	public AffixedLabelContentPart(final View view) {
		super(view);
	}

	/**
	 * Gets the style class.
	 *
	 * @return the style class
	 * @see org.eclipse.papyrus.gef4.parts.LabelContentPart#getStyleClass()
	 */
	@Override
	protected String getStyleClass() {
		return "genericAffixedLabel";
	}


	/**
	 * Do refresh visual.
	 *
	 * @param visual
	 *            the visual
	 * @see org.eclipse.papyrus.gef4.parts.LabelContentPart#doRefreshVisual(javafx.scene.layout.StackPane)
	 */
	@Override
	protected void doRefreshVisual(final StackPane visual) {
		super.doRefreshVisual(visual);
		visual.toFront();
		// Set rotate
		visual.setRotate(getRotate());
	}

	/**
	 * Refresh border.
	 *
	 * @see org.eclipse.papyrus.gef4.parts.ContainerContentPart#refreshBorder()
	 */
	@Override
	protected void refreshBorder() {
		BorderStroke stroke = null;
		stroke = new BorderStroke(getBorderColors().getTop(), getBorderColors().getRight(), getBorderColors().getBottom(), getBorderColors().getLeft(), getBorderStyles().getTop(), getBorderStyles().getRight(), getBorderStyles().getBottom(),
				getBorderStyles().getLeft(),
				getCornerRadii(), getBorderWidths(), null);
		final Border border = new Border(stroke);

		getVisual().setBorder(border);
	}

	/**
	 * Refresh shadow.
	 *
	 * @see org.eclipse.papyrus.gef4.parts.ContainerContentPart#refreshShadow()
	 */
	@Override
	protected void refreshShadow() {
		final DropShadow shadow = getShadow();
		if (null != shadow) {
			getVisual().setEffect(shadow);
		}
	}

	/**
	 * Refresh background.
	 *
	 * @see org.eclipse.papyrus.gef4.parts.ContainerContentPart#refreshBackground()
	 */
	@Override
	protected void refreshBackground() {
		final StackPane region = getVisual();
		Paint fill = null;
		// Background to fill a simple gradient
		if (null != getBackgroundPaint()) {
			fill = getBackgroundPaint();
		} else {
			fill = new LinearGradient(getBackgroundGradientStartPosition().getX(), getBackgroundGradientStartPosition().getY(), getBackgroundGradientEndPosition().getX(), getBackgroundGradientEndPosition().getY(),
					true, CycleMethod.NO_CYCLE, new Stop(0, getBackgroundColor2()), new Stop(1, getBackgroundColor1()));
		}
		final BackgroundFill backgroundFill = new BackgroundFill(fill, getCornerRadii(), null);
		final Background background = new Background(backgroundFill);
		// set the Background
		region.setBackground(background);
	}

	/**
	 * Gets the text overflow. For External Label, all the label must be shown.
	 *
	 * @return the text overflow
	 * @see org.eclipse.papyrus.gef4.parts.LabelContentPart#getTextOverflow()
	 */
	@Override
	protected TextOverflowEnum getTextOverflow() {
		return TextOverflowEnum.VISIBLE;
	}

}
