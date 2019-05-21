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
import org.eclipse.papyrus.gef4.parts.LabelContentPart;
import org.eclipse.papyrus.gef4.services.style.StyleService;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberExpression;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

// FIXME: the Package Shape should take border width into account for best results
// FIXME: when using a Shape, Region's Borders disappear (= are applied to the shape instead of the region).
// This causes layout differences in children (Esp. label) compared to the "no shape" situation
public class PackageShapeDecoration implements ShapeDecoration {

	@Override
	public Decoration applyShape(IVisualPart<? extends Region> visualPart, ObservableList<Node> decorationContainer) {
		Region region = visualPart.getVisual();

		// get the tab dimension of the package (As an Observable value, see Issue #9)

		NumberExpression tabHeight = new SimpleDoubleProperty(0);
		NumberExpression tabWidth = new SimpleDoubleProperty(60); // TODO CSS minTabWidth

		for (final IVisualPart<? extends Node> child : visualPart.getChildrenUnmodifiable()) {
			if (child instanceof LabelContentPart) {
				LabelContentPart<?> childPart = (LabelContentPart<?>) child;
				// get the margin
				final Insets childMargin = childPart.getAdapter(StyleService.class).getPadding();
				// get the Label child
				final Label label = childPart.getVisual();
				tabWidth = Bindings.max(tabWidth, label.widthProperty().add(childMargin.getLeft()).add(childMargin.getRight()));
				tabHeight = Bindings.add(tabHeight, label.heightProperty().add(childMargin.getBottom()).add(childMargin.getTop()));
			} else {
				break; // The package tab only depends on the labels at the top of the childrens list (e.g. Stereotype, Name). Labels below other children are ignored (e.g. Label, Compartment, Label)
			}
		}

		tabHeight = Bindings.max(10, tabHeight);

		// create package shape
		final PackagePath packageShape = new PackagePath(region.widthProperty(), region.heightProperty(), tabWidth, tabHeight, 35, 35);
		region.setShape(packageShape);

		// Close the tab area
		final Line closingLine = new Line(0, 0, 0 /* Bound */, 0);

		double strokeDelta = 3; // 1 for the left border, 1 for the right border, and 0.5 on each side to compensate for StrokeType.CENTERED
		closingLine.endXProperty().bind(tabWidth.subtract(closingLine.strokeWidthProperty().multiply(strokeDelta))); // Total width

		// Layout.
		closingLine.setManaged(false);
		closingLine.layoutYProperty().bind(tabHeight.add(closingLine.strokeWidthProperty().divide(2)));
		closingLine.layoutXProperty().bind(closingLine.strokeWidthProperty().multiply(strokeDelta / 2));

		decorationContainer.add(closingLine);
		Runnable refresh = () -> {
			StyleService styleService = visualPart.getAdapter(StyleService.class);
			if (styleService != null) {
				closingLine.setStroke(styleService.getBorderColors().getRight());
				closingLine.setStrokeWidth(styleService.getBorderWidths().getRight());
			} else {
				closingLine.setStrokeWidth(1);
				closingLine.setStroke(Color.BLACK);
			}
		};
		refresh.run();

		return new Decoration() {

			@Override
			public void dispose() {
				region.setShape(null);
				decorationContainer.remove(closingLine);
			}

			@Override
			public void refresh() {
				refresh.run();
			}
		};
	}

}
