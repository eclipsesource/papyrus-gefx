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

import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.gef4.nodes.DoubleBorderPane;
import org.eclipse.papyrus.gef4.shapes.CornerBendPath;
import org.eclipse.papyrus.gef4.shapes.CornerBendRectanglePath;
import org.eclipse.papyrus.gef4.shapes.PackagePath;
import org.eclipse.papyrus.gef4.utils.BorderStrokeStyles;
import org.eclipse.papyrus.gef4.utils.ShapeTypeEnum;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Ellipse;

public class NodeContentPart extends ContainerContentPart<Shape, VBox> implements IPrimaryContentPart {

	public static final int DEFAULT_MIN_WIDTH = 100;
	public static final int DEFAULT_MIN_HEIGHT = 100;

	public NodeContentPart(final Shape view) {
		super(view);
	}

	@Override
	protected VBox doCreateVisual() {
		return new VBox();
	}

	@Override
	protected void refreshVisualInTransaction(final VBox visual) {
		super.refreshVisualInTransaction(visual);
		refreshBounds();
	}

	protected void refreshBounds() {
		final VBox region = getVisual();

		region.setLayoutX(getX());
		region.setLayoutY(getY());

		boolean autoSize = true; // FIXME configure (autoHeight + autoWidth)
		if (autoSize) {
			region.setMinWidth(getWidth());
			region.setMinHeight(getHeight());
			region.setPrefHeight(Region.USE_COMPUTED_SIZE);
			region.setPrefWidth(Region.USE_COMPUTED_SIZE);
		} else {
			region.setMinHeight(Region.USE_COMPUTED_SIZE);
			region.setMinWidth(Region.USE_COMPUTED_SIZE);
			region.setPrefHeight(getHeight());
			region.setPrefWidth(getWidth());
		}
	}

	@Override
	protected void refreshBackground() {
		final VBox region = getVisual();
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

	@Override
	protected void refreshEffect() {
		final Effect effect = getEffect();
		final DropShadow shadow = getShadow();
		if (effect instanceof Reflection) {
			((Reflection) effect).setInput(shadow);
			getVisual().setEffect(effect);
		} else {
			if (null != shadow) {
				shadow.setInput(getEffect());
				getVisual().setEffect(shadow);
			} else {
				getVisual().setEffect(getEffect());
			}
		}
	}

	@Override
	protected void refreshShape() {
		final VBox region = getVisual();
		final double width = getWidth();
		final double height = getHeight();

		final ShapeTypeEnum shapeType = getShapeType();
		switch (shapeType) {
		case PACKAGE:
			double tabWidth = 60;// TODO nameStyle minPackageTabSize
			double tabHeight = 0;

			// get the tab dimension of the package
			for (final IVisualPart<Node, ? extends Node> child : getChildren()) {
				if (child instanceof LabelContentPart) {
					LabelContentPart childPart = (LabelContentPart) child;

					// get the margin
					final Insets childMargin = childPart.getPadding();
					// get the Label child
					final Label label = childPart.getVisual();
					tabWidth = Math.max(label.getWidth() + childMargin.getLeft() + childMargin.getRight(), tabWidth);
					tabHeight += label.getHeight() + childMargin.getBottom() + childMargin.getTop();
				}
			}

			// create package shape
			final PackagePath packageShape = new PackagePath(width, height, tabWidth, tabHeight);
			region.setScaleShape(false);
			region.setShape(packageShape);

			break;

		case CORNER_BEND_RECTANGLE:
			final double cornerBendWidth = getCornerBendWidth();
			region.setScaleShape(false);
			region.setShape(new CornerBendRectanglePath(width, height, cornerBendWidth));
			break;

		case OVAL:
			// Check if ellipse is aldready instanciate.
			if (!(region.getShape() instanceof Ellipse)) {
				Ellipse ellipse = new Ellipse(10, 10); // Size doesn't matter, it will be resized to match the region's. We still need a size, otherwise the Ellipse is not drawn at all
				ellipse.setFill(region.getBackground().getFills().get(0).getFill());
				ellipse.setStroke(region.getBorder().getStrokes().get(0).getBottomStroke());
				region.setShape(ellipse);
			}
			break;

		default:
			// Rectangle case (Might be cornered)
			region.setShape(null);
			break;
		}

		// Rotate the figure.
		region.setRotate(getRotate());
	}

	// TODO: Store figures directly rather than re-creating them
	@Override
	protected void refreshDecoration() {
		final VBox region = getVisual();
		final double width = getWidth();
		final double height = getHeight();


		// Delete cornerBend if exist
		for (int i = 0; i < region.getChildren().size(); i++) {
			if (region.getChildren().get(i) instanceof CornerBendPath) {
				region.getChildren().remove(i);
			}
		}
		// CornerBend
		if (ShapeTypeEnum.CORNER_BEND_RECTANGLE.equals(getShapeType())) {
			for (int i = 0; i < region.getChildren().size(); i++) {
				if (region.getChildren().get(i) instanceof CornerBendPath) {
					region.getChildren().remove(i);
				}
			}

			// Create the decoration
			final CornerBendPath cornerBendPath = new CornerBendPath(width, getCornerBendWidth());
			// Set the Color
			cornerBendPath.setFill(getCornerBendColor());
			// add it to children of region
			region.getChildren().add(cornerBendPath);
		}

		// Delete ActiveLine if exist
		for (int i = 0; i < region.getChildren().size(); i++) {
			if (region.getChildren().get(i) instanceof DoubleBorderPane) {
				region.getChildren().remove(i);
			}
		}
		// If have double Line. For active Class for example
		if (hasDoubleBorder() && null == region.getShape()) {
			// If double line are not already drawn
			for (int i = 0; i < region.getChildren().size(); i++) {
				if (region.getChildren().get(i) instanceof DoubleBorderPane) {
					region.getChildren().remove(i);
				}
			}

			// Create the decoration
			final DoubleBorderPane pane = new DoubleBorderPane(region, width, height, getBorderWidths(), getBorderStyles(), getBorderColors(), getDoubleBorderWidths());
			region.getChildren().add(pane);
		}
	}

	/**
	 * take into account of the double border on padding.
	 *
	 * @return the padding
	 * @see org.eclipse.papyrus.gef4.parts.NotationContentPart#getPadding()
	 */
	@Override
	protected Insets getPadding() {
		final Insets padding = super.getPadding();
		double top = 0;
		double right = 0;
		double bottom = 0;
		double left = 0;
		// Only if doubleBorder is applied
		if (hasDoubleBorder() && null == getVisual().getShape()) {

			final BorderStrokeStyles borderStyles = getBorderStyles();
			if (borderStyles.isDoubleBorder(BorderStrokeStyles.Position.TOP)) {
				top = getDoubleBorderWidths().getTop();
			}
			// Right
			if (borderStyles.isDoubleBorder(BorderStrokeStyles.Position.RIGHT)) {
				right = getDoubleBorderWidths().getRight();
			}
			// Bottom
			if (borderStyles.isDoubleBorder(BorderStrokeStyles.Position.BOTTOM)) {
				bottom = getDoubleBorderWidths().getBottom();
			}
			// Left
			if (borderStyles.isDoubleBorder(BorderStrokeStyles.Position.LEFT)) {
				left = getDoubleBorderWidths().getLeft();
			}
		}
		return new Insets(padding.getTop() + top, padding.getRight() + right, padding.getBottom() + bottom, padding.getLeft() + left);
	}


	@Override
	protected void refreshBorder() {
		BorderStroke stroke = null;
		stroke = new BorderStroke(getBorderColors().getTop(), getBorderColors().getRight(), getBorderColors().getBottom(), getBorderColors().getLeft(), getBorderStyles().getTop(), getBorderStyles().getRight(), getBorderStyles().getBottom(),
				getBorderStyles().getLeft(),
				getCornerRadii(), getBorderWidths(), null);
		final Border border = new Border(stroke);

		getVisual().setBorder(border);
	}

	@Override
	protected void refreshShadow() {
		final DropShadow shadow = getShadow();
		if (null != shadow) {
			getVisual().setEffect(shadow);
		}
	}

	@Override
	protected void addChildVisual(final IVisualPart<Node, ? extends Node> child, final int index) {
		if (child.getVisual() != null) {
			if (child instanceof AffixedNodeContentPart || child instanceof AffixedLabelContentPart) {
				child.getVisual().setManaged(false);
			}
			getVisual().getChildren().add(child.getVisual());
		}
	}

	@Override
	protected void removeChildVisual(final IVisualPart<Node, ? extends Node> child, final int index) {
		final Node childVisual = child.getVisual();
		if (childVisual == null) {
			return;
		}
		getVisual().getChildren().remove(childVisual);
	}

	@Override
	protected String getStyleClass() {
		return "genericNode";//$NON-NLS-1$
	}

}
