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

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class CircleCrossDecoration implements ShapeDecoration {

	@Override
	public Decoration applyShape(IVisualPart<? extends Region> visualPart, ObservableList<Node> decorationContainer) {
		Region region = visualPart.getVisual();

		DoubleProperty radius = new SimpleDoubleProperty();
		radius.bind(
				Bindings.createDoubleBinding(
						() -> Math.max(4, Math.min(region.getWidth(), region.getHeight())) / 2,
						region.widthProperty(),
						region.heightProperty()));

		Circle outerCircle = new Circle();
		outerCircle.radiusProperty().bind(radius);
		outerCircle.centerXProperty().bind(radius);
		outerCircle.centerYProperty().bind(radius);
		
		DoubleProperty strokeWidth = new SimpleDoubleProperty(1);
		DoubleExpression lineWH = Bindings.createDoubleBinding(() -> radius.get() * Math.sqrt(2) - strokeWidth.get(), radius, strokeWidth);
		
		Line cross1 = new Line(); // \
		cross1.endXProperty().bind(lineWH);
		cross1.endYProperty().bind(lineWH);
		cross1.strokeWidthProperty().bind(strokeWidth);
		
		Line cross2 = new Line(); // /
		cross2.startYProperty().bind(lineWH);
		cross2.endXProperty().bind(lineWH);
		cross2.strokeWidthProperty().bind(strokeWidth);
		
		DoubleBinding layoutX = Bindings.createDoubleBinding(() -> region.getWidth() / 2 - lineWH.get() / 2, region.widthProperty(), lineWH);
		DoubleBinding layoutY = Bindings.createDoubleBinding(() -> region.getHeight() / 2 - lineWH.get() / 2, region.heightProperty(), lineWH);
		
		cross1.layoutXProperty().bind(layoutX);
		cross1.layoutYProperty().bind(layoutY);
		cross2.layoutXProperty().bind(layoutX);
		cross2.layoutYProperty().bind(layoutY);

		cross1.setManaged(false);
		cross2.setManaged(false);
		decorationContainer.addAll(cross1, cross2);

		region.setShape(outerCircle);
		region.setScaleShape(false);
		
		Runnable refresh = () -> {
			StyleService partStyleService = visualPart.getAdapter(StyleService.class);

			Color fgColor;
			double borderWidth;
			if (partStyleService != null) {
				fgColor = partStyleService.getBorderColors().getRight();
				borderWidth = partStyleService.getBorderWidths().getRight();
			} else {
				fgColor = Color.BLACK;
				borderWidth = 1;
			}
			
			strokeWidth.set(borderWidth);
			
			cross1.setStroke(fgColor);
			cross2.setStroke(fgColor);
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
				region.setScaleShape(true);
				decorationContainer.removeAll(cross1, cross2);
			}
		};
	}

}
