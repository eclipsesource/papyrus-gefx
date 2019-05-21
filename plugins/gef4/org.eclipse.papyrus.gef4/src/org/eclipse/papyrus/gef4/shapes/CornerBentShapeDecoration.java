/*****************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  EclipseSource - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.shapes;

import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.services.style.StyleService;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class CornerBentShapeDecoration implements ShapeDecoration {

	@Override
	public Decoration applyShape(IVisualPart<? extends Region> visualPart, ObservableList<Node> decorationContainer) {

		Region region = visualPart.getVisual();
		double cornerWidth = 15;

		CornerBendRectanglePath mainPath = new CornerBendRectanglePath(region.widthProperty(), region.heightProperty(), cornerWidth);
		region.setShape(mainPath);
		region.setScaleShape(false);

		CornerBendPath corner = new CornerBendPath(region.widthProperty(), 15);
		decorationContainer.add(corner);

		Runnable refresh = () -> {
			StyleService partStyleService = visualPart.getAdapter(StyleService.class);
			if (partStyleService != null) {
				corner.setFill(partStyleService.getBackgroundColor2());
				corner.setStroke(partStyleService.getBorderColors().getRight());
				corner.setStrokeWidth(partStyleService.getBorderWidths().getRight());
			} else {
				corner.setFill(Color.TRANSPARENT);
				corner.setStroke(Color.BLACK);
				corner.setStrokeWidth(1);
			}
		};
		refresh.run();

		return new Decoration() {
			@Override
			public void refresh() {
				refresh.run();
			}

			@Override
			public void dispose() {
				region.setShape(null);
				decorationContainer.remove(corner);
			}
		};
	}

}
