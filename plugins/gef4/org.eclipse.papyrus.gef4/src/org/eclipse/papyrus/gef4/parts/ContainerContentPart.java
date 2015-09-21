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
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Layout and visualization API and Implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.parts;

import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.View;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


public abstract class ContainerContentPart<V extends View, R extends Region> extends NotationContentPart<V, R> {

	protected ContainerContentPart(final V view) {
		super(view);
	}


	protected final ChangeListener<? super javafx.geometry.Bounds> boundsListener = new ChangeListener<javafx.geometry.Bounds>() {

		@Override
		public void changed(final ObservableValue<? extends javafx.geometry.Bounds> observable, final javafx.geometry.Bounds oldValue, final javafx.geometry.Bounds newValue) {
			if (!decorationToRefresh && (getHeight() != newValue.getHeight() || getWidth() != newValue.getWidth())) {
				refreshShape();
				decorationToRefresh = true;
				refreshDecoration();
				decorationToRefresh = false;
			}
		}

	};

	@Override
	protected void doRefreshVisual(final R visual) {

		super.doRefreshVisual(visual);

		// resetStyle();

		// Layout refresh
		refreshLayout();
		refreshBounds();

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
		final R region = getVisual();

		region.setLayoutX(getX());
		region.setLayoutY(getY());
		region.setMinWidth(getMinWidth());
		region.setMinHeight(getMinHeight());

		final Bounds bounds = getBounds();
		if (null != bounds) {
			region.setPrefWidth(bounds.getWidth());
			region.setPrefHeight(bounds.getHeight());
		}
		region.autosize();
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
		return getVisual().getHeight();
	}

	@Override
	protected double getWidth() {
		return getVisual().getWidth();
	}

	public double getMinHeight() {
		return 0;
	}

	public double getMinWidth() {
		return 0;
	}

}
