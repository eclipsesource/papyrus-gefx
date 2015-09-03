/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.parts;

import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.View;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


public abstract class ContainerContentPart<V extends View, R extends Region> extends NotationContentPart<V, R> {

	protected ContainerContentPart(V view) {
		super(view);
	}

	@Override
	protected void doRefreshVisual(R visual) {
		super.doRefreshVisual(visual);

		// resetStyle();

		// Layout refresh
		refreshBounds();
		refreshLayout();

		// Visual refresh
		refreshShape();
		refreshBackground();
		refreshBorder();
		refreshShadow();
		refreshEffect();
		refreshDecoration();

		refreshChildren();

		// refreshFXCSSStyle();
	}

	/*
	 * private void refreshFXCSSStyle() {
	 * // addStyle("-fx-background-insets: 10;");
	 * }
	 * 
	 * protected void resetStyle() {
	 * getVisual().setStyle("");//$NON-NLS-1$
	 * getVisual().applyCss();
	 * }
	 * 
	 * protected void setStyle(String style) {
	 * getVisual().setStyle(style);
	 * getVisual().applyCss();
	 * }
	 * 
	 * protected void addStyle(String style) {
	 * getVisual().setStyle(getVisual().getStyle() + style);
	 * getVisual().applyCss();
	 * }
	 */

	protected void refreshLayout() {
		// Refresh Padding
		getVisual().setPadding(getPadding());

		// Refresh Spacing for VBox and Hbox
		if (getVisual() instanceof VBox) {
			((VBox) getVisual()).setSpacing(getSpacing());
		} else if (getVisual() instanceof HBox) {
			((HBox) getVisual()).setSpacing(getSpacing());
		}
	}

	protected void refreshBounds() {
		R region = getVisual();

		region.setLayoutX(getX());
		region.setLayoutY(getY());

		region.setMinHeight(getHeight());

		region.setMinWidth(getWidth());
	}



	protected void refreshBackground() {
		// Do nothing, the implementation is in charge to manage that.
	}

	protected void refreshShadow() {
		// Do nothing, the implementation is in charge to manage that.
	}

	protected void refreshBorder() {
		// Do nothing, the implementation is in charge to manage that.
	}

	protected void refreshEffect() {
		// Do nothing, the implementation is in charge to manage that.
	}

	protected void refreshShape() {
		// Do nothing, the implementation is in charge to manage that.
	}

	protected void refreshDecoration() {
		// Do nothing, the implementation is in charge to manage that.
	}

	@Override
	protected double getHeight() {
		Bounds bounds = getBounds();
		double height = bounds == null ? 0 : bounds.getHeight();
		return Math.max(height, getMinHeight());
	}

	@Override
	protected double getWidth() {
		Bounds bounds = getBounds();
		double width = bounds == null ? 0 : bounds.getWidth();
		return Math.max(width, getMinWidth());
	}

	protected double getMinHeight() {
		return 0;
	}

	protected double getMinWidth() {
		return 0;
	}

}
