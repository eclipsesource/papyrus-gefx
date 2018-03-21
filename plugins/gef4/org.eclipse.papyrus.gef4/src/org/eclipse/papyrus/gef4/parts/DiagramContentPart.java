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

import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class DiagramContentPart<MODEL> extends BaseContentPart<MODEL, Pane> {

	private Label frame;

	public DiagramContentPart(MODEL model) {
		super(model);
	}

	@Override
	protected Pane doCreateVisual() {
		Pane group = new Pane();

		frame = new Label();
		group.getChildren().add(frame);

		return group;
	}

	@Override
	protected void refreshVisualInTransaction(Pane visual) {
		super.refreshVisualInTransaction(visual);
		refreshBackground();
		boolean displayFrame = false;
		// displayFrame = NotationUtils.getBooleanValue(getView(), NamedStyleProperties.DISPLAY_HEADER, false);

		if (displayFrame) {
			// frame.setText(getElement().eClass().getName() + "\n" + getView().getType());
			// frame.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, null, null, null)));
			// frame.setPadding(new Insets(3));
			//
			// visual.setPadding(new Insets(5));
			//
			// visual.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, null, null, null)));
			//
			// visual.setLayoutX(10);
			// visual.setLayoutY(10);
			//
			// visual.setMinSize(600, 768);
			// visual.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			// visual.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		} else {
			visual.setBorder(null);
			visual.setPadding(new Insets(0));

			visual.setLayoutX(0);
			visual.setLayoutY(0);

			visual.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			visual.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			visual.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		}

		frame.setManaged(displayFrame);
		frame.setVisible(displayFrame);
	}

	protected void refreshBackground() {
		if (getViewer() != null) {
			Parent viewerCanvas = getViewer().getCanvas();
			if (viewerCanvas instanceof Region) {

				// FIXME CSS Paint. Currently commented out because it fails with rgb() CSS syntax

				// Hard code a white background instead
				Paint fill = Color.WHITE;
				final BackgroundFill backgroundFill = new BackgroundFill(fill, null, null);
				final Background background = new Background(backgroundFill);
				// set the Background
				((Region) viewerCanvas).setBackground(background);


				// Paint fill = null;
				// // Background to fill a simple gradient
				// if (null != getStyleProvider().getBackgroundPaint()) {
				// fill = getStyleProvider().getBackgroundPaint();
				// } else {
				// fill = new LinearGradient(getStyleProvider().getBackgroundGradientStartPosition().getX(), getStyleProvider().getBackgroundGradientStartPosition().getY(), getStyleProvider().getBackgroundGradientEndPosition().getX(),
				// getStyleProvider().getBackgroundGradientEndPosition().getY(),
				// true, CycleMethod.NO_CYCLE, new Stop(0, getStyleProvider().getBackgroundColor2()), new Stop(1, getStyleProvider().getBackgroundColor1()));
				// }
				// final BackgroundFill backgroundFill = new BackgroundFill(fill, getStyleProvider().getCornerRadii(), null);
				// final Background background = new Background(backgroundFill);
				// // set the Background
				// ((Region) viewerCanvas).setBackground(background);
			}
		}
	}

	@Override
	protected void doRemoveChildVisual(IVisualPart<? extends Node> child, int index) {
		Node childVisual = child.getVisual();
		if (childVisual == null) {
			return;
		}

		getVisual().getChildren().remove(childVisual);
	}

	@Override
	protected void doAddChildVisual(IVisualPart<? extends Node> child, int index) {
		if (child.getVisual() != null) {
			getVisual().getChildren().add(child.getVisual());
		}
	}

	@Override
	protected String getStyleClass() {
		return "diagram";
	}

}
