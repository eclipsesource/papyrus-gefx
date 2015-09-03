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
package org.eclipse.papyrus.gef4.parts;

import org.eclipse.gef4.fx.nodes.FXGeometryNode;
import org.eclipse.gef4.geometry.planar.Ellipse;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.papyrus.gef4.utils.BoundsUtil;
import org.eclipse.papyrus.gef4.utils.ShapeTypeEnum;

import javafx.scene.Node;
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

abstract public class CompartmentContentPart<V extends DecorationNode, R extends Pane> extends ContainerContentPart<V, R> {

	public CompartmentContentPart(final V view) {
		super(view);
	}


	@Override
	protected void addChildVisual(final IVisualPart<Node, ? extends Node> child, final int index) {
		if (child.getVisual() != null) {
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
	protected void refreshBounds() {
		super.refreshBounds();
		final R visual = getVisual();
		VBox.setVgrow(visual, Priority.ALWAYS);
	}

	@Override
	protected void refreshBorder() {
		BorderStroke stroke = null;
		stroke = new BorderStroke(getBorderColors().getTop(), getBorderColors().getRight(), getBorderColors().getBottom(), getBorderColors().getLeft(), getBorderStyles().getTop(), getBorderStyles().getRight(), getBorderStyles().getBottom(),
				getBorderStyles().getLeft(),
					getCornerRadii(), getBorderWidths(),
					getMargin());
		final Border border = new Border(stroke);

		getVisual().setBorder(border);
	}

	@Override
	protected void refreshBackground() {
		final R region = getVisual();

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
		final R region = getVisual();

		// Set the shape
		final int relativeX = BoundsUtil.getRelativeX(region);
		final int relativeY = BoundsUtil.getRelativeY(region);
		final int width = BoundsUtil.getWidth(getVisual());
		final int height = BoundsUtil.getHeight(getVisual());

		if (ShapeTypeEnum.OVAL.equals(getShapeType())) {
			final FXGeometryNode<Ellipse> ellipseShape = new FXGeometryNode<Ellipse>();
			ellipseShape.setGeometry(new Ellipse(relativeX, relativeY, width, height));
			region.setShape(ellipseShape);
		} else {
			getVisual().setShape(null);
		}

		// Set the clip to avoid the compartment to be outside of the parent
		final Shape parentShape = ((Region) getParent().getVisual()).getShape();
		Shape clip = null;

		if (null != parentShape) {
			parentShape.setFill(Color.BLACK);
			clip = Shape.union(parentShape, parentShape);// Create a copy of parent shape
			clip.setFill(Color.BLACK);
		}

		if (null != clip) {
			clip.setTranslateX(-relativeX);
			clip.setTranslateY(-relativeY);
		}
		getVisual().setClip(clip);
	}

	@Override
	protected void refreshEffect() {
		getVisual().setEffect(getEffect());
	}

	@Override
	protected double getMinHeight() {
		return 7;
	}

	@Override
	protected String getStyleClass() {
		return "genericListCompartment";
	}

}
