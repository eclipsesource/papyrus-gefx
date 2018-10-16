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

import java.util.Collection;
import java.util.Collections;

import org.eclipse.fx.core.Subscription;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.layout.Locator;
import org.eclipse.papyrus.gef4.nodes.DoubleBorderPane;
import org.eclipse.papyrus.gef4.shapes.CornerBendPath;
import org.eclipse.papyrus.gef4.shapes.CornerBendRectanglePath;
import org.eclipse.papyrus.gef4.shapes.PackagePath;
import org.eclipse.papyrus.gef4.utils.BorderColors;
import org.eclipse.papyrus.gef4.utils.BorderStrokeStyles;
import org.eclipse.papyrus.gef4.utils.ShapeTypeEnum;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberExpression;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
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
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Ellipse;

public class NodeContentPart<MODEL> extends ContainerContentPart<MODEL, VBox> implements IPrimaryContentPart {

	public static final int DEFAULT_MIN_WIDTH = 100;
	public static final int DEFAULT_MIN_HEIGHT = 100;

	/**
	 * Listener to refresh the shape when layout bounds change.
	 * This is especially useful if scaling == false (If scale == true, this is already handled by JavaFX)
	 *
	 * Asymmetric shapes (Package, comment) need to be maintained manually
	 */
	private ChangeListener<Bounds> boundsShapeListener = (a, b, c) -> refreshShape();

	public NodeContentPart(final MODEL view) {
		super(view);
	}

	@Override
	protected VBox doCreateVisual() {
		return new VBox();
	}

	@Override
	protected void refreshVisualInTransaction(final VBox visual) {
		refreshBorder();
		refreshBackground();
		refreshBounds();
		refreshShape();

		super.refreshVisualInTransaction(visual);

	}

	protected void refreshBounds() {
		final VBox region = getVisual();

		if (isAutoWidth()) {
			region.setMinWidth(getStyleProvider().getWidth());
			region.setPrefWidth(Region.USE_COMPUTED_SIZE);
		} else {
			region.setMinWidth(Region.USE_COMPUTED_SIZE);
			region.setPrefWidth(getStyleProvider().getWidth());
		}

		if (isAutoHeight()) {
			region.setMinHeight(getStyleProvider().getHeight());
			region.setPrefHeight(Region.USE_COMPUTED_SIZE);
		} else {
			region.setMinHeight(Region.USE_COMPUTED_SIZE);
			region.setPrefHeight(getStyleProvider().getHeight());
		}

		refreshLocator();
	}

	protected boolean isAutoWidth() {
		// TODO Configure
		return true;
	}

	protected boolean isAutoHeight() {
		// TODO Configure
		return true;
	}

	protected void refreshLocator() {
		Locator locator = getLocator();
		final VBox region = getVisual();

		if (locator != null) {
			locator.applyLayout(region);
		} else {
			region.setManaged(true); // A non-null locator will typically set this to false. We need to ensure it is true

			region.setLayoutX(getStyleProvider().getX());
			region.setLayoutY(getStyleProvider().getY());
		}
	}

	@Override
	protected void refreshBackground() {
		final VBox region = getVisual();
		Paint fill = null;
		// Background to fill a simple gradient
		if (null != getStyleProvider().getBackgroundPaint()) {
			fill = getStyleProvider().getBackgroundPaint();
		} else {
			fill = new LinearGradient(getStyleProvider().getBackgroundGradientStartPosition().getX(), getStyleProvider().getBackgroundGradientStartPosition().getY(), getStyleProvider().getBackgroundGradientEndPosition().getX(),
					getStyleProvider().getBackgroundGradientEndPosition().getY(),
					true, CycleMethod.NO_CYCLE, new Stop(0, getStyleProvider().getBackgroundColor2()), new Stop(1, getStyleProvider().getBackgroundColor1()));
		}
		final BackgroundFill backgroundFill = new BackgroundFill(fill, getStyleProvider().getCornerRadii(), null);
		final Background background = new Background(backgroundFill);
		// set the Background
		region.setBackground(background);
	}

	@Override
	protected void refreshEffect() {
		final Effect effect = getStyleProvider().getEffect();
		final DropShadow shadow = getStyleProvider().getShadow();
		if (effect instanceof Reflection) {
			((Reflection) effect).setInput(shadow);
			getVisual().setEffect(effect);
		} else {
			if (null != shadow) {
				shadow.setInput(getStyleProvider().getEffect());
				getVisual().setEffect(shadow);
			} else {
				getVisual().setEffect(getStyleProvider().getEffect());
			}
		}
	}

	private ShapeTypeEnum currentShapeType;
	private Subscription currentShapeSubscription;

	protected void refreshShape() {
		final VBox region = getVisual();

		final ShapeTypeEnum shapeType = getStyleProvider().getShapeType();
		if (shapeType != currentShapeType) {
			Subscription.disposeIfExists(currentShapeSubscription);
			currentShapeSubscription = null;
			this.currentShapeType = shapeType;

			boolean labelsUseAllWidth = true;

			switch (shapeType) {
			case CORNER_BEND_RECTANGLE:
				final NumberExpression width = region.widthProperty();
				final NumberExpression height = region.heightProperty();

				final double cornerBendWidth = getStyleProvider().getCornerBendWidth();
				setShape(new CornerBendRectanglePath(width, height, cornerBendWidth), false);
				break;

			case OVAL:
				// Check if ellipse is aldready instanciate.
				if (!(region.getShape() instanceof Ellipse)) {
					Ellipse ellipse = new Ellipse(10, 10); // Size doesn't matter, it will be resized to match the region's. We still need a size, otherwise the Ellipse is not drawn at all
					ellipse.setFill(region.getBackground().getFills().get(0).getFill());
					ellipse.setStroke(region.getBorder().getStrokes().get(0).getBottomStroke());
					setShape(ellipse, true);
				}
				break;
			case PACKAGE:
				// get the tab dimension of the package (As an Observable value, see Issue #9)

				NumberExpression tabHeight = new SimpleDoubleProperty(0);
				NumberExpression tabWidth = new SimpleDoubleProperty(60); // TODO CSS minTabWidth

				for (final IVisualPart<? extends Node> child : getChildrenUnmodifiable()) {
					if (child instanceof LabelContentPart) {
						LabelContentPart<?> childPart = (LabelContentPart<?>) child;

						// get the margin
						final Insets childMargin = childPart.getStyleProvider().getPadding();
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
				setShape(packageShape, false);

				labelsUseAllWidth = false;
				break;
			default:
				// Rectangle case (Might be rounded)
				region.setShape(null);
				break;
			}

			for (final IVisualPart<? extends Node> child : getChildrenUnmodifiable()) {
				if (child instanceof LabelContentPart) {
					LabelContentPart<?> childPart = (LabelContentPart<?>) child;
					childPart.setUseAllWidth(labelsUseAllWidth);
				} else {
					// The package tab only depends on the labels at the top of the childrens list (e.g. Stereotype, Name).
					// Labels below other children are ignored (e.g. Label, Compartment, Label)
					break;
				}
			}
		}

		// Rotate the figure.
		region.setRotate(getStyleProvider().getRotate());
	}

	protected void setShape(javafx.scene.shape.Shape shape, boolean scale) {
		Region region = getVisual();
		if (shape == null && region.getShape() != null) {
			// Removing
			region.setShape(null);
			region.layoutBoundsProperty().removeListener(boundsShapeListener);
		} else if (shape != null) {
			if (region.getShape() == null) {
				// Adding
				region.layoutBoundsProperty().addListener(boundsShapeListener);
			}
			// Adding or Modifying
			region.setShape(shape);
			region.setScaleShape(scale);
			region.setCenterShape(true);
		}
	}

	// TODO: Store figures directly rather than re-creating them
	@Override
	protected void refreshDecoration() {
		final VBox region = getVisual();
		final double width = getStyleProvider().getWidth();
		final double height = getStyleProvider().getHeight();


		// Delete cornerBend if exist
		for (int i = 0; i < region.getChildren().size(); i++) {
			if (region.getChildren().get(i) instanceof CornerBendPath) {
				region.getChildren().remove(i);
			}
		}

		// Delete ActiveLine if exist
		for (int i = 0; i < region.getChildren().size(); i++) {
			if (region.getChildren().get(i) instanceof DoubleBorderPane) {
				region.getChildren().remove(i);
			}
		}
		// If have double Line. For active Class for example
		if (getStyleProvider().hasDoubleBorder() && null == region.getShape()) {
			// If double line are not already drawn
			for (int i = 0; i < region.getChildren().size(); i++) {
				if (region.getChildren().get(i) instanceof DoubleBorderPane) {
					region.getChildren().remove(i);
				}
			}

			// Create the decoration
			final DoubleBorderPane pane = new DoubleBorderPane(region, width, height, getStyleProvider().getBorderWidths(), getStyleProvider().getBorderStyles(), getStyleProvider().getBorderColors(), getStyleProvider().getDoubleBorderWidths());
			region.getChildren().add(pane);
		}
	}

	@Override
	protected void refreshBorder() {
		BorderStroke stroke = null;
		final BorderColors borderColors = getStyleProvider().getBorderColors();
		final BorderStrokeStyles borderStyles = getStyleProvider().getBorderStyles();
		final BorderWidths borderWidths = getStyleProvider().getBorderWidths();

		Border border = null;
		if (borderWidths != null) {
			stroke = new BorderStroke(borderColors.getTop(), borderColors.getRight(), borderColors.getBottom(), borderColors.getLeft(), borderStyles.getTop(), borderStyles.getRight(), borderStyles.getBottom(),
					borderStyles.getLeft(),
					getStyleProvider().getCornerRadii(), borderWidths,
					getStyleProvider().getMargin());
			border = new Border(stroke);
		}
		getVisual().setBorder(border);
	}

	@Override
	protected void refreshShadow() {
		final DropShadow shadow = getStyleProvider().getShadow();
		if (null != shadow) {
			getVisual().setEffect(shadow);
		}
	}

	@Override
	protected void doAddChildVisual(final IVisualPart<? extends Node> child, final int index) {
		if (child.getVisual() != null) {
			this.currentShapeType = null; // Force a shape refresh XXX Find a better way to do that, e.g. listening on the children
			getVisual().getChildren().add(index, child.getVisual());
		}
	}

	@Override
	protected void doRemoveChildVisual(final IVisualPart<? extends Node> child, final int index) {
		final Node childVisual = child.getVisual();
		if (childVisual == null) {
			return;
		}
		this.currentShapeType = null; // Force a shape refresh XXX Find a better way to do that, e.g. listening on the children
		getVisual().getChildren().remove(childVisual);
	}

	/**
	 * @see org.eclipse.gef.mvc.fx.parts.AbstractVisualPart#dispose()
	 *
	 */
	@Override
	public void dispose() {
		getVisual().layoutBoundsProperty().removeListener(boundsShapeListener);
		super.dispose();
	}

	@Override
	protected Collection<String> getStyleClasses() {
		return Collections.singletonList("genericNode"); //$NON-NLS-1$
	}

}
