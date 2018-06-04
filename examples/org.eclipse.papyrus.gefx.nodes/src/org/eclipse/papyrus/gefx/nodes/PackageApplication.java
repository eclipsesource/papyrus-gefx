/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (EclipseSource) cletavernier@eclipsesource.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gefx.nodes;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import org.eclipse.papyrus.gefx.nodes.shapes.FullPackagePath;
import org.eclipse.papyrus.gefx.nodes.shapes.OuterPackagePath;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * An example application showing a (simplified) Package on a Diagram.
 * Illustrates Issue #16: Improve Shapes rendering
 */
public class PackageApplication extends AbstractNodeApplication {

	public static void main(String[] args) {
		Application.launch(PackageApplication.class);
	}

	@Override
	protected List<Node> getNodes() {
		// Example package with only the external-border shape (Issue: missing tab
		// bottom border)
		VBox packageNode1 = createBasePackage((node, label) -> node.setShape(new OuterPackagePath(node.widthProperty(),
				node.heightProperty(), label.widthProperty(), label.heightProperty(), 40, 15)), 50, 25);

		// Example package with the full border shape (Issue: duplicate tab bottom
		// border)
		VBox packageNode2 = createBasePackage((node, label) -> node.setShape(new FullPackagePath(node.widthProperty(),
				node.heightProperty(), label.widthProperty(), label.heightProperty(), 40, 15)), 50, 200);

		return Arrays.asList(new Node[] { packageNode1, packageNode2 });
	}

	// Creates a simplified class shape (only one compartment, no children)
	protected void createClassIn(VBox packageNode) {
		VBox classNode = new VBox();
		Label classLabel = new Label("Class");
		Pane classCompartment = new VBox();
		classNode.getChildren().addAll(classLabel, classCompartment);
		classCompartment.setBorder(
				new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1, 0, 0, 0))));
		configureDefaultNode(classNode);

		classNode.setLayoutX(30);
		classNode.setLayoutY(10);
		classNode.setPrefWidth(65);
		classNode.setPrefHeight(85);

		((Pane) packageNode.getChildren().get(1)).getChildren().add(classNode);
	}

	// Creates a simplified package shape (without scrollbars)
	protected VBox createBasePackage(BiConsumer<Region, Label> setShape, double x, double y) {
		VBox packageNode = new VBox();
		configureDefaultNode(packageNode);
		Label packageLabel = new Label("Package");
		packageLabel.setPadding(new Insets(3));
		Pane packageCompartment = new Pane();
		packageNode.getChildren().addAll(packageLabel, packageCompartment);

		setShape.accept(packageNode, packageLabel);

		packageNode.setMinWidth(250);
		packageNode.setMinHeight(140);
		packageNode.setLayoutX(x);
		packageNode.setLayoutY(y);

		createClassIn(packageNode);

		return packageNode;
	}

}
