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
package org.eclipse.papyrus.gef4.parts;

import org.eclipse.papyrus.gef4.utils.FXUtils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.text.TextAlignment;

public class LabelContentPart<MODEL> extends AbstractLabelContentPart<MODEL, Label> {

	protected boolean useAllWidth = true;
	private boolean allowRounded = false;

	public LabelContentPart(MODEL model) {
		super(model);
	}

	@Override
	protected Label doCreateVisual() {
		return label = new Label();
	}

	@Override
	protected void refreshVisualInTransaction(Label visual) {
		super.refreshVisualInTransaction(visual);
		refreshPadding();
		refreshBackground();
	}
	
	protected void refreshBackground() {
		final Label region = getVisual();
		Paint fill = null;
		// Background to fill a simple gradient
		if (null != getStyleProvider().getBackgroundPaint()) {
			fill = getStyleProvider().getBackgroundPaint();
		} else {
			fill = new LinearGradient(getStyleProvider().getBackgroundGradientStartPosition().getX(), getStyleProvider().getBackgroundGradientStartPosition().getY(), getStyleProvider().getBackgroundGradientEndPosition().getX(),
					getStyleProvider().getBackgroundGradientEndPosition().getY(),
					true, CycleMethod.NO_CYCLE, new Stop(0, getStyleProvider().getBackgroundColor2()), new Stop(1, getStyleProvider().getBackgroundColor1()));
		}
		
		CornerRadii radii = allowRounded ? getStyleProvider().getCornerRadii() : null;
		Insets insets = allowRounded ? null : new Insets(1, 1, 0, 1); // FIXME This is a workaround for package labels with a non-transparent background, to avoid drawing the label background over the Package Path. See Issue #39
		
		final BackgroundFill backgroundFill = new BackgroundFill(fill, radii, insets);
		final Background background = new Background(backgroundFill);
		// set the Background
		region.setBackground(background);
	}

	/**
	 * Refresh text alignment.
	 * //TODO: getText alignment of the parents if in compartment ??
	 */
	@Override
	protected void refreshTextAlignment() {
		final Pos textAlignment = getStyleProvider().getTextAlignment();
		getVisual().setAlignment(textAlignment);

		if (useAllWidth) {
			getVisual().setMaxWidth(Double.MAX_VALUE);
		} else {
			getVisual().setMaxWidth(Region.USE_COMPUTED_SIZE);
		}

		// Set the text alignment in case of multi-line
		switch (textAlignment) {
		case CENTER_LEFT:
			label.setTextAlignment(TextAlignment.LEFT);
			break;
		case TOP_CENTER:
			label.setTextAlignment(TextAlignment.CENTER);
			break;
		case TOP_RIGHT:
			label.setTextAlignment(TextAlignment.RIGHT);
			break;
		default:
			label.setTextAlignment(TextAlignment.LEFT);
			break;
		}
	}

	public void setUseAllWidth(boolean useAllWidth) {
		this.useAllWidth = useAllWidth;
		refreshTextAlignment();
	}

	public void setAllowRounded(boolean allowRoundedLabels) {
		this.allowRounded  = allowRoundedLabels;
		refreshBackground();
	}

	protected void refreshPadding() {
		FXUtils.setPadding(label, 2, 5);
	}


}
