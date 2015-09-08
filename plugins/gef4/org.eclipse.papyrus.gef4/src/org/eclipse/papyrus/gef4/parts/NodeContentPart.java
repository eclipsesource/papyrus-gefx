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
import org.eclipse.gef4.geometry.planar.IGeometry;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.gef4.nodes.DoubleBorderPane;
import org.eclipse.papyrus.gef4.shapes.CornerBendPath;
import org.eclipse.papyrus.gef4.shapes.CornerBendRectanglePath;
import org.eclipse.papyrus.gef4.shapes.PackagePath;
import org.eclipse.papyrus.gef4.shapes.RoundedRectanglePath;
import org.eclipse.papyrus.gef4.utils.BorderStrokeStyles;
import org.eclipse.papyrus.gef4.utils.ShapeTypeEnum;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;

public class NodeContentPart extends ContainerContentPart<Shape, VBox>implements IPrimaryContentPart {

	/** indicate if The corner bend decoration is instantiate. */
	private boolean cornerBend;

	/** indicate if the double line decoration is set. */
	private boolean doubleLine;

	/* not managed Child nodes needs its own region to be correctly displayed(TOFIX) */
	// TODO Affixed node on a XY pane: Change bounds behaviors must be in charge to do the right placement ??
	private final Pane affichedNodeRegion = new Pane();

	public NodeContentPart(final Shape view) {
		super(view);
	}

	@Override
	protected VBox doCreateVisual() {
		final VBox vBox = new VBox();

		affichedNodeRegion.setManaged(false);
		vBox.getChildren().add(affichedNodeRegion);

		return vBox;
	}

	@Override
	protected void doRefreshVisual(final VBox visual) {
		super.doRefreshVisual(visual);
		affichedNodeRegion.toFront();
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
					// get the margin
					final Insets childMargin = ((NotationContentPart<Shape, ?>) child).getPadding();
					// get the Label child
					final Region label = (Region) ((Region) child.getVisual()).getChildrenUnmodifiable().get(0);
					tabWidth = Math.max(label.getWidth() + childMargin.getLeft() + childMargin.getRight(), tabWidth);
					tabHeight += ((Region) child.getVisual()).getHeight();
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
			boolean ellipseInstatiate = false;
			if (region.getShape() instanceof FXGeometryNode<?>) {
				final IGeometry geometry = ((FXGeometryNode<?>) region.getShape()).getGeometry();
				if ((geometry instanceof Ellipse)) {
					ellipseInstatiate = false;
				}
			}
			if (!ellipseInstatiate) {
				final FXGeometryNode<Ellipse> ellipseShape = new FXGeometryNode<Ellipse>();
				ellipseShape.setGeometry(new Ellipse(0, 0, width, height));
				region.setShape(ellipseShape);
			}
			break;

		default:
			if (!getCornerRadii().equals(CornerRadii.EMPTY)) {// RoundedRectangle case
				region.setShape(new RoundedRectanglePath(width, height, getCornerRadii()));
			} else {
				// Rectangle case
				region.setShape(null);
			}
			break;
		}

		// Rotate the figure.
		region.setRotate(getRotate());

		if (null != region.getShape()) {
			region.getShape().toFront();
		}
	}

	@Override
	protected void refreshDecoration() {
		final VBox region = getVisual();
		final double width = getWidth();
		final double height = getHeight();

		// CornerBend
		if (ShapeTypeEnum.CORNER_BEND_RECTANGLE.equals(getShapeType())) {
			if (!cornerBend || resizing) {

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
				cornerBend = true;
			} else {
				for (int i = 0; i < region.getChildren().size(); i++) {
					if (region.getChildren().get(i) instanceof CornerBendPath) {
						region.getChildren().get(i).autosize();
					}
				}
			}
		} else if (cornerBend) {
			// Delete cornerBend if exist
			for (int i = 0; i < region.getChildren().size(); i++) {
				if (region.getChildren().get(i) instanceof CornerBendPath) {
					region.getChildren().remove(i);
				}
			}
			cornerBend = false;
		}

		// If have double Line. For active Class for example
		if (hasDoubleBorder() && null == region.getShape()) {
			if (!doubleLine || resizing) {

				for (int i = 0; i < region.getChildren().size(); i++) {
					if (region.getChildren().get(i) instanceof DoubleBorderPane) {
						region.getChildren().remove(i);
					}
				}

				// Create the decoration
				final DoubleBorderPane pane = new DoubleBorderPane(region, width, height, getBorderWidths(), getBorderStyles(), getBorderColors(), getDoubleBorderWidths());
				region.getChildren().add(pane);
				doubleLine = true; // TODO passe to true: needs to pass to false in case of resize.
			} else {
				// If resize
				for (int i = 0; i < region.getChildren().size(); i++) {
					if (region.getChildren().get(i) instanceof DoubleBorderPane) {
						region.getChildren().get(i).autosize();
					}
				}
			}
		} else if (doubleLine) {
			// Delete ActiveLine if exist
			for (int i = 0; i < region.getChildren().size(); i++) {
				if (region.getChildren().get(i) instanceof DoubleBorderPane) {
					region.getChildren().remove(i);
				}
			}
			doubleLine = false;
		}

	}

	/**
	 * take into account of the double border on padding.
	 *
	 * @return the padding
	 * @see org.eclipse.papyrus.gef4.parts.NotationContentPart#getPadding()
	 */
	// TODO TEST
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
	protected double getMinHeight() {
		return 100;
	}

	@Override
	protected double getMinWidth() {
		return 100;
	}

	@Override
	protected void addChildVisual(final IVisualPart<Node, ? extends Node> child, final int index) {
		if (child.getVisual() != null) {
			if (child instanceof AffixedNodeContentPart) {
				affichedNodeRegion.getChildren().add(child.getVisual());
			} else {
				getVisual().getChildren().add(child.getVisual());
			}
		}
	}

	@Override
	protected void removeChildVisual(final IVisualPart<Node, ? extends Node> child, final int index) {
		final Node childVisual = child.getVisual();
		if (childVisual == null) {
			return;
		}
		if (child instanceof AffixedNodeContentPart) {
			affichedNodeRegion.getChildren().remove(child.getVisual());
		} else {
			getVisual().getChildren().remove(childVisual);
		}
	}

	@Override
	protected String getStyleClass() {
		return "genericNode";
	}

}
