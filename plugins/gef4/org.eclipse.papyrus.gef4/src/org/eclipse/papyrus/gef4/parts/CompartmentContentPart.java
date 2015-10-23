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

import org.eclipse.gef4.fx.nodes.FXGeometryNode;
import org.eclipse.gef4.geometry.planar.Ellipse;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.DrawerStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.utils.BorderColors;
import org.eclipse.papyrus.gef4.utils.BorderStrokeStyles;
import org.eclipse.papyrus.gef4.utils.BoundsUtil;
import org.eclipse.papyrus.gef4.utils.ShapeTypeEnum;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

abstract public class CompartmentContentPart<V extends DecorationNode, R extends ScrollPane> extends ContainerContentPart<V, ScrollPane> {

	boolean collapsed = false;

	boolean canCollapse = true; // TODO set it throw CSS

	protected static final int MINIMUM_COMPARTMENT_HEIGHT = 15;

	public CompartmentContentPart(final V view) {
		super(view);
	}

	@Override
	protected void doRefreshVisual(final ScrollPane visual) {
		super.doRefreshVisual(visual);
		refreshCollapsed();
		refreshScrollBar();
	}

	protected void refreshScrollBar() {
		final ScrollPane visual = getVisual();
		final ScrollBarPolicy horizontalBarPolicy = collapsed ? ScrollBarPolicy.NEVER : getHorizontalBarPolicy();
		final ScrollBarPolicy verticalBarPolicy = collapsed ? ScrollBarPolicy.NEVER : getVerticalBarPolicy();

		visual.setHbarPolicy(horizontalBarPolicy);
		visual.setVbarPolicy(verticalBarPolicy);

		if (ScrollBarPolicy.NEVER.equals(verticalBarPolicy)) {
			((Region) visual.getContent()).setPrefWidth(visual.getViewportBounds().getWidth());
		}

		if (ScrollBarPolicy.NEVER.equals(horizontalBarPolicy)) {
			((Region) visual.getContent()).setPrefHeight(visual.getViewportBounds().getHeight());
		}
	}

	/**
	 * Refreshes the compartment's collapsed state
	 */
	protected void refreshCollapsed() {
		if (isCanCollapse()) {
			final DrawerStyle style = (DrawerStyle) ((View) getView()).getStyle(NotationPackage.eINSTANCE.getDrawerStyle());
			if (style != null) {
				setCollapsed(style.isCollapsed());
			}
		} else {
			setCollapsed(false);
		}
	}

	@Override
	protected void refreshBounds() {
		super.refreshBounds();
		VBox.setVgrow(getVisual(), Priority.ALWAYS);
	}

	@Override
	protected void refreshBorder() {
		BorderStroke stroke = null;
		final BorderColors borderColors = getBorderColors();
		final BorderStrokeStyles borderStyles = getBorderStyles();

		stroke = new BorderStroke(borderColors.getTop(), borderColors.getRight(), borderColors.getBottom(), borderColors.getLeft(), borderStyles.getTop(), borderStyles.getRight(), borderStyles.getBottom(),
				borderStyles.getLeft(),
				getCornerRadii(), getBorderWidths(),
				getMargin());
		final Border border = new Border(stroke);
		getVisual().setBorder(border);
	}

	@Override
	protected void refreshBackground() {
		final Region region = getVisual();
		// Background to fill a simple gradient
		final LinearGradient gradiant = new LinearGradient(getBackgroundGradientStartPosition().getX(), getBackgroundGradientStartPosition().getY(), getBackgroundGradientEndPosition().getX(), getBackgroundGradientEndPosition().getY(),
				true, CycleMethod.NO_CYCLE, new Stop(0, getBackgroundColor2()), new Stop(1, getBackgroundColor1()));
		final BackgroundFill fill = new BackgroundFill(gradiant, getCornerRadii(), getMargin());
		final Background background = new Background(fill);

		// set the Background
		region.setBackground(background);
	}

	@Override
	protected void refreshShape() {
		final ScrollPane region = getVisual();

		// Set the shape
		final int relativeX = BoundsUtil.getRelativeX(region);
		final int relativeY = BoundsUtil.getRelativeY(region);
		final int width = BoundsUtil.getWidth(region);
		final int height = BoundsUtil.getHeight(region);

		if (ShapeTypeEnum.OVAL.equals(getShapeType())) {
			final FXGeometryNode<Ellipse> ellipseShape = new FXGeometryNode<Ellipse>();
			ellipseShape.setGeometry(new Ellipse(relativeX, relativeY, width, height));
			region.setShape(ellipseShape);
		} else {
			region.setShape(null);
		}

		// Set the clip to avoid the compartment to be outside of the parent
		final Shape parentShape = ((Region) getParent().getVisual()).getShape();
		Shape clip = null;

		// set the clip in case of Ellipse/Oval
		if (null != parentShape && (parentShape instanceof FXGeometryNode<?> && ((FXGeometryNode<?>) parentShape).getGeometry() instanceof Ellipse)) {
			parentShape.setFill(Color.BLACK);
			clip = Shape.union(parentShape, parentShape);// Create a copy of parent shape
			clip.setFill(Color.BLACK);
			clip.setTranslateX(-relativeX);
			clip.setTranslateY(-relativeY);
		}
		region.setClip(clip);
	}

	@Override
	protected void refreshEffect() {
		getVisual().setEffect(getEffect());
	}

	@Override
	public double getMinHeight() {
		return MINIMUM_COMPARTMENT_HEIGHT;
	}

	@Override
	protected String getStyleClass() {
		return "genericListCompartment";
	}


	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(final boolean collapsed) {

		// Set the child visibility
		for (final Node child : getVisual().getChildrenUnmodifiable()) {
			child.setVisible(!collapsed);
		}

		if (this.collapsed != collapsed) {
			this.collapsed = collapsed;
			// refresh Collapse
			// Set the Height with animation
			final Bounds bounds = getBounds();
			if (collapsed) {
				getVisual().setMinHeight(getMinHeight());
				getVisual().setPrefHeight(bounds.getHeight());
				animateHeightTo(MINIMUM_COMPARTMENT_HEIGHT);
			} else {
				getVisual().setMinHeight(MINIMUM_COMPARTMENT_HEIGHT);
				getVisual().setPrefHeight(MINIMUM_COMPARTMENT_HEIGHT);

				animateHeightTo(bounds.getHeight());
			}
		}
	}

	protected void animateHeightTo(final double minHeight) {
		final WritableValue<Double> writableHeight = new WritableValue<Double>() {
			@Override
			public Double getValue() {
				return getVisual().getPrefHeight();
			}

			@Override
			public void setValue(final Double value) {
				getVisual().setMinHeight(value);
				getVisual().setPrefHeight(value);
			}
		};

		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		timeline.setAutoReverse(false);
		final KeyValue kv = new KeyValue(writableHeight, minHeight);
		final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}


	public boolean isCanCollapse() {
		return canCollapse;
	}

	public void setCanCollapse(final boolean canCollapse) {
		this.canCollapse = canCollapse;
	}

	/**
	 * Adds the child visual.
	 *
	 * @param child
	 *            the child
	 * @param index
	 *            the index
	 * @see org.eclipse.gef4.mvc.parts.AbstractVisualPart#addChildVisual(org.eclipse.gef4.mvc.parts.IVisualPart, int)
	 */
	@Override
	protected void addChildVisual(final IVisualPart<Node, ? extends Node> child, final int index) {
		final Node childVisual = child.getVisual();
		if (null != childVisual) {

			((Pane) getVisual().getContent()).getChildren().add(childVisual);
		}
	}

	/**
	 * Removes the child visual.
	 *
	 * @param child
	 *            the child
	 * @param index
	 *            the index
	 * @see org.eclipse.gef4.mvc.parts.AbstractVisualPart#removeChildVisual(org.eclipse.gef4.mvc.parts.IVisualPart, int)
	 */
	@Override
	protected void removeChildVisual(final IVisualPart<Node, ? extends Node> child, final int index) {
		final Node childVisual = child.getVisual();
		if (childVisual == null) {
			return;
		}
		((Pane) getVisual().getContent()).getChildren().remove(childVisual);
	}

}
