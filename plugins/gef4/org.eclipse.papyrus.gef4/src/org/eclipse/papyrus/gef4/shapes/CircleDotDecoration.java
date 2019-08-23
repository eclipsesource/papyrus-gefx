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
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CircleDotDecoration implements ShapeDecoration {

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

		Circle innerCircle = new Circle();
		innerCircle.centerXProperty().bind(region.widthProperty().divide(2));
		innerCircle.centerYProperty().bind(region.heightProperty().divide(2));
		innerCircle.radiusProperty().bind(radius.divide(2));

		innerCircle.setManaged(false);
		decorationContainer.add(innerCircle);

		region.setShape(outerCircle);
		region.setScaleShape(false);

		Runnable refresh = () -> {
			StyleService partStyleService = visualPart.getAdapter(StyleService.class);

			Color fgColor;
			if (partStyleService != null) {
				fgColor = partStyleService.getBorderColors().getRight();
			} else {
				fgColor = Color.BLACK;
			}
			
			innerCircle.setFill(fgColor);
			innerCircle.setStroke(null);
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
				decorationContainer.remove(innerCircle);
			}
		};
	}

}
