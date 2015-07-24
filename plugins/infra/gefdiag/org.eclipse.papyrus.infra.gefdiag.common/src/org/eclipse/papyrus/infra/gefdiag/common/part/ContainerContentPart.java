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
package org.eclipse.papyrus.infra.gefdiag.common.part;

import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.View;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.Region;


public abstract class ContainerContentPart<V extends View, R extends Region> extends NotationContentPart<V, R> {

	protected ContainerContentPart(V view) {
		super(view);
	}

	@Override
	protected void doRefreshVisual(R visual) {
		super.doRefreshVisual(visual);
		refreshBounds();
		refreshBorder();
		refreshBackground();
	}

	protected void refreshBackground() {
		// Nothing
	}

	protected void refreshBounds() {
		R region = getVisual();

		region.setLayoutX(getX());
		region.setLayoutY(getY());

		region.setMinHeight(getHeight());
		region.setMinWidth(getWidth());
	}

	protected void refreshBorder() {
		R region = getVisual();

		BorderStroke stroke = new BorderStroke(getBorderColor(), getBorderStyle(), getCornerRadii(), getBorderWidths());
		Border border = new Border(stroke);

		region.setBorder(border);
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
