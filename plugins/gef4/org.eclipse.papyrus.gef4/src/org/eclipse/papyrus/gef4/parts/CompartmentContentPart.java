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
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.papyrus.gef4.utils.BoundsUtil;
import org.eclipse.papyrus.gef4.utils.ShapeTypeEnum;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Shape;

abstract public class CompartmentContentPart<V extends DecorationNode, R extends Region> extends ContainerContentPart<V, Region> {

	public CompartmentContentPart(final V view) {
		super(view);
	}


	@Override
	protected void refreshBounds() {
		super.refreshBounds();
		VBox.setVgrow(getVisual(), Priority.ALWAYS);
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
		final R region = (R) getVisual();

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
	protected double getMinHeight() {
		return 10;
	}

	@Override
	protected String getStyleClass() {
		return "genericListCompartment";
	}

}
