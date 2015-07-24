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
package org.eclipse.papyrus.infra.gefdiag.common.part;

import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.DecorationNode;

import javafx.scene.Node;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ListCompartmentContentPart<V extends DecorationNode> extends ContainerContentPart<V, VBox> {

	public ListCompartmentContentPart(V view) {
		super(view);
	}

	@Override
	protected VBox doCreateVisual() {
		return new VBox();
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
	protected void refreshBounds() {
		super.refreshBounds();

		VBox visual = getVisual();

		VBox.setVgrow(visual, Priority.ALWAYS);
	}

	@Override
	protected BorderWidths getBorderWidths() {
		// Only draw the top-line
		return new BorderWidths(1, 0, 0, 0);
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
