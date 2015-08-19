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
import org.eclipse.gmf.runtime.notation.DecorationNode;

import javafx.scene.Node;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class XYCompartmentContentPart<V extends DecorationNode> extends ContainerContentPart<V, Pane> {

	public XYCompartmentContentPart(V view) {
		super(view);
	}

	@Override
	protected Pane doCreateVisual() {
		return new Pane();
	}

	@Override
	protected void refreshBounds() {
		super.refreshBounds();

		Pane visual = getVisual();

		VBox.setVgrow(visual, Priority.ALWAYS);
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
	protected BorderWidths getBorderWidths() {
		// Only draw the top-line
		return new BorderWidths(1, 0, 0, 0);
	}

	@Override
	protected String getStyleClass() {
		return "genericXYCompartment";
	}


}
