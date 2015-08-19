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

import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.Shape;

import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class NodeContentPart extends ContainerContentPart<Shape, VBox>implements IPrimaryContentPart {

	public NodeContentPart(Shape view) {
		super(view);
	}

	@Override
	protected VBox doCreateVisual() {
		return new VBox();
	}

	@Override
	protected double getMinHeight() {
		// return 30;
		return 100;
	}

	@Override
	protected double getMinWidth() {
		// return 50;
		return 100;
	}

	@Override
	protected void refreshBackground() {
		super.refreshBackground();

		BackgroundFill fill = new BackgroundFill(new LinearGradient(0, 0, 0, 1.0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.WHITE), new Stop(1, Color.web("#C3D7DD"))), getCornerRadii(), null);

		// BackgroundFill fill = new BackgroundFill(new RadialGradient(30, 1.0, 0.5, 0.5, 1.0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.WHITE), new Stop(0.5, Color.web("#C3D7DD")), new Stop(1.0, Color.RED)), null, null);

		// BackgroundFill fill = new BackgroundFill(new RadialGradient(30, 1.0, 0.5, 0.5, 1.0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.WHITE), new Stop(1, Color.web("#C3D7DD"))), null, null);

		getVisual().setBackground(new Background(fill));
	}

	@Override
	protected void addChildVisual(IVisualPart<Node, ? extends Node> child, int index) {
		if (child.getVisual() != null) {
			getVisual().getChildren().add(child.getVisual());
		}
	}

	@Override
	protected void removeChildVisual(IVisualPart<Node, ? extends Node> child, int index) {
		Node childVisual = child.getVisual();
		if (childVisual == null) {
			return;
		}

		getVisual().getChildren().remove(childVisual);
	}

	@Override
	protected String getStyleClass() {
		return "genericNode";
	}

}
