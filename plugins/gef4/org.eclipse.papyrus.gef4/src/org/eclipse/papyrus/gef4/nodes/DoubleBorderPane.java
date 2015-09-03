/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.gef4.nodes;

import org.eclipse.papyrus.gef4.utils.BorderColors;
import org.eclipse.papyrus.gef4.utils.BorderStrokeStyles;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * The Class DoubleLinePane. Permits to have the double border style.
 */
// TODO create constructor with default value.
// TODO MIA permits double dash/custom/dot line
public class DoubleBorderPane extends Pane {
	private final VBox region;
	private final double width;
	private final double height;
	private final BorderWidths borderWidths;
	private final BorderStrokeStyles borderStyles;
	private final BorderColors borderColors;
	private final Insets doubleBorderWidth;

	public DoubleBorderPane(final VBox region, final double width, final double height, final BorderWidths borderWidths, final BorderStrokeStyles borderStrokeStyles, final BorderColors borderColors, final Insets doubleBorderWidth) {
		this.region = region;
		this.width = width;
		this.height = height;
		this.borderWidths = borderWidths;
		this.borderStyles = borderStrokeStyles;
		this.borderColors = borderColors;
		this.doubleBorderWidth = doubleBorderWidth;

		init();
	}

	protected void init() {

		// Top border
		if (borderStyles.isDoubleBorder(BorderStrokeStyles.Position.TOP)) {
			final Line topLine = new Line(borderStyles.isDoubleBorder(BorderStrokeStyles.Position.LEFT) ? doubleBorderWidth.getLeft() : 0, borderStyles.isDoubleBorder(BorderStrokeStyles.Position.TOP) ? doubleBorderWidth.getTop() : 0,
					width - (borderStyles.isDoubleBorder(BorderStrokeStyles.Position.RIGHT) ? doubleBorderWidth.getRight() : 0), borderStyles.isDoubleBorder(BorderStrokeStyles.Position.TOP) ? doubleBorderWidth.getTop() : 0);
			topLine.setManaged(false);
			topLine.setStroke(borderColors.getTop());
			topLine.setStrokeWidth(borderWidths.getTop());
			getChildren().add(topLine);
		}

		// Right border
		if (borderStyles.isDoubleBorder(BorderStrokeStyles.Position.RIGHT)) {
			final Line rightLine = new Line(width - (borderStyles.isDoubleBorder(BorderStrokeStyles.Position.RIGHT) ? doubleBorderWidth.getRight() : 0), borderStyles.isDoubleBorder(BorderStrokeStyles.Position.TOP) ? doubleBorderWidth.getTop() : 0,
					width - (borderStyles.isDoubleBorder(BorderStrokeStyles.Position.RIGHT) ? doubleBorderWidth.getRight() : 0), height - (borderStyles.isDoubleBorder(BorderStrokeStyles.Position.BOTTOM) ? doubleBorderWidth.getBottom() : 0));
			rightLine.setManaged(false);
			rightLine.setStroke(borderColors.getRight());
			rightLine.setStrokeWidth(borderWidths.getRight());
			getChildren().add(rightLine);
		}

		// Bottom border
		if (borderStyles.isDoubleBorder(BorderStrokeStyles.Position.BOTTOM)) {
			final Line bottomLine = new Line(borderStyles.isDoubleBorder(BorderStrokeStyles.Position.LEFT) ? doubleBorderWidth.getLeft() : 0, height - (borderStyles.isDoubleBorder(BorderStrokeStyles.Position.BOTTOM) ? doubleBorderWidth.getBottom() : 0),
					width - (borderStyles.isDoubleBorder(BorderStrokeStyles.Position.RIGHT) ? doubleBorderWidth.getRight() : 0), height - (borderStyles.isDoubleBorder(BorderStrokeStyles.Position.BOTTOM) ? doubleBorderWidth.getBottom() : 0));
			bottomLine.setManaged(false);
			bottomLine.setStroke(borderColors.getBottom());
			bottomLine.setStrokeWidth(borderWidths.getBottom());
			getChildren().add(bottomLine);
		}

		// Left border
		if (borderStyles.isDoubleBorder(BorderStrokeStyles.Position.LEFT)) {
			final Line leftLine = new Line(borderStyles.isDoubleBorder(BorderStrokeStyles.Position.LEFT) ? doubleBorderWidth.getLeft() : 0, borderStyles.isDoubleBorder(BorderStrokeStyles.Position.TOP) ? doubleBorderWidth.getTop() : 0,
					borderStyles.isDoubleBorder(BorderStrokeStyles.Position.LEFT) ? doubleBorderWidth.getLeft() : 0, height - (borderStyles.isDoubleBorder(BorderStrokeStyles.Position.BOTTOM) ? doubleBorderWidth.getBottom() : 0));
			leftLine.setManaged(false);
			leftLine.setStroke(borderColors.getLeft());
			leftLine.setStrokeWidth(borderWidths.getLeft());
			getChildren().add(leftLine);
		}

		final javafx.scene.shape.Shape shape = null != region.getShape() ? region.getShape()
				: new Rectangle(width, height);
		shape.setFill(Color.BLACK);
		setClip(javafx.scene.shape.Shape.intersect(shape, shape));
		setManaged(false);
	}

}
