/*****************************************************************************
 * Copyright (c) 2015, 2019 CEA LIST, EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Layout and visualization API and Implementation
 *  EclipseSource - Issue 16
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.parts;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.fx.core.Subscription;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.layout.Locator;
import org.eclipse.papyrus.gef4.services.style.StyleService;
import org.eclipse.papyrus.gef4.shapes.CircleCrossDecoration;
import org.eclipse.papyrus.gef4.shapes.CircleDecoration;
import org.eclipse.papyrus.gef4.shapes.CircleDotDecoration;
import org.eclipse.papyrus.gef4.shapes.CornerBentShapeDecoration;
import org.eclipse.papyrus.gef4.shapes.DiamondDecoration;
import org.eclipse.papyrus.gef4.shapes.PackageShapeDecoration;
import org.eclipse.papyrus.gef4.shapes.ShapeDecoration;
import org.eclipse.papyrus.gef4.utils.BorderColors;
import org.eclipse.papyrus.gef4.utils.BorderStrokeStyles;
import org.eclipse.papyrus.gef4.utils.ShapeTypeEnum;

import javafx.scene.Node;
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

	private ShapeTypeEnum currentShapeType;
	private ShapeDecoration.Decoration currentDecoration;

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

	/**
	 * Indicates if the node should automatically be resized horizontally to match the size
	 * of its contents. The automatic size computation can only <strong>increase</strong> the size of a node
	 * (The size specified in the layout is then used as a minimum size).
	 * 
	 * @return
	 */
	protected boolean isAutoWidth() {
		// TODO Configure
		return true;
	}

	/**
	 * Indicates if the node should automatically be resized vertically to match the size
	 * of its contents. The automatic size computation can only <strong>increase</strong> the size of a node
	 * (The size specified in the layout is then used as a minimum size).
	 * 
	 * @return
	 */
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
		StyleService styleProvider = getStyleProvider();
		if (null != styleProvider.getBackgroundPaint()) {
			fill = styleProvider.getBackgroundPaint();
		} else {
			fill = new LinearGradient(styleProvider.getBackgroundGradientStartPosition().getX(), styleProvider.getBackgroundGradientStartPosition().getY(), styleProvider.getBackgroundGradientEndPosition().getX(),
					styleProvider.getBackgroundGradientEndPosition().getY(),
					true, CycleMethod.NO_CYCLE, new Stop(0, styleProvider.getBackgroundColor2()), new Stop(1, styleProvider.getBackgroundColor1()));
		}
		final BackgroundFill backgroundFill = new BackgroundFill(fill, styleProvider.getCornerRadii(), null);
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

	protected void refreshShape() {
		final VBox region = getVisual();

		final ShapeTypeEnum shapeType = getStyleProvider().getShapeType();
		if (shapeType != currentShapeType) {
			Subscription.disposeIfExists(currentDecoration);
			currentDecoration = null;
			this.currentShapeType = shapeType;

			boolean labelsUseAllWidth = true;
			boolean allowRoundedLabels = false; // Only supported when using the default rounded rectangle shape

			switch (shapeType) {
			case CORNER_BEND_RECTANGLE:
				ShapeDecoration decoration = new CornerBentShapeDecoration();
				this.currentDecoration = decoration.applyShape(this, region.getChildren());
				break;
			case OVAL:
				// Check if ellipse is already instantiated.
				if (!(region.getShape() instanceof Ellipse)) {
					Ellipse ellipse = new Ellipse(10, 10); // Size doesn't matter, it will be resized to match the region's. We still need a size, otherwise the Ellipse is not drawn at all
					ellipse.setFill(region.getBackground().getFills().get(0).getFill());
					ellipse.setStroke(region.getBorder().getStrokes().get(0).getBottomStroke());
					region.setShape(ellipse);
				}
				break;
			case PACKAGE:
				ShapeDecoration pkgDecoration = new PackageShapeDecoration();
				this.currentDecoration = pkgDecoration.applyShape(this, region.getChildren());
				labelsUseAllWidth = false;
				break;
			case EMPTY_CIRCLE:
				ShapeDecoration emptyCircleDecoration = new CircleDecoration(false);
				this.currentDecoration = emptyCircleDecoration.applyShape(this, region.getChildren());
				break;
			case FULL_CIRCLE:
				ShapeDecoration fullCircleDecoration = new CircleDecoration(true);
				this.currentDecoration = fullCircleDecoration.applyShape(this, region.getChildren());
				break;
			case EMPTY_DIAMOND:
				ShapeDecoration emptyDiamondDecoration = new DiamondDecoration(false); 
				this.currentDecoration = emptyDiamondDecoration.applyShape(this, region.getChildren());
				break;
			case FULL_DIAMOND:
				ShapeDecoration fullDiamondDecoration = new CircleDecoration(true);
				this.currentDecoration = fullDiamondDecoration.applyShape(this, region.getChildren());
				break;
			case DOT_CIRCLE:
				ShapeDecoration dotCircleDecoration = new CircleDotDecoration();
				this.currentDecoration = dotCircleDecoration.applyShape(this, region.getChildren());
				break;
			case CROSS_CIRCLE:
				ShapeDecoration crossCircleDecoration = new CircleCrossDecoration();
				this.currentDecoration = crossCircleDecoration.applyShape(this, region.getChildren());
				break;
			case NONE:
			default:
				// Rectangle case (Might be rounded)
				region.setShape(null);
				allowRoundedLabels = true;
				break;
			}

			for (final IVisualPart<? extends Node> child : getChildrenUnmodifiable()) {
				if (child instanceof LabelContentPart) {
					LabelContentPart<?> childPart = (LabelContentPart<?>) child;
					childPart.setUseAllWidth(labelsUseAllWidth);
					childPart.setAllowRounded(allowRoundedLabels);
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
	protected void refreshDecoration() {
		if (currentDecoration != null) {
			currentDecoration.refresh();
		}

		super.refreshDecoration();
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
		Subscription.disposeIfExists(currentDecoration);
		super.dispose();
	}

	@Override
	protected Collection<String> getStyleClasses() {
		return Collections.singletonList("genericNode"); //$NON-NLS-1$
	}

}
