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

import java.util.List;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public abstract class AbstractNodeApplication extends Application {

	protected Pane diagramLayer;

	@Override
	public void start(Stage primaryStage) throws Exception {
		diagramLayer = new Pane();

		Scene scene = new Scene(diagramLayer);

		diagramLayer.getChildren().addAll(getNodes());

		primaryStage.setWidth(600);
		primaryStage.setHeight(450);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	protected Border getDefaultBorder() {
		return new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1)));
	}

	protected Background getDefaultBackground() {
		return new Background(new BackgroundFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
				new Stop(0, Color.WHITE), new Stop(1, Color.CORNFLOWERBLUE)), null, null));
	}

	protected void configureDefaultNode(Region region) {
		region.setBorder(getDefaultBorder());
		region.setBackground(getDefaultBackground());
	}

	protected abstract List<Node> getNodes();

}
