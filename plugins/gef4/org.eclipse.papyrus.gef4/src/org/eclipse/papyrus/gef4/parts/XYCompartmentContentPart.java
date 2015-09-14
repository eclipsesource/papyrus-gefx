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

import org.eclipse.emf.common.util.URI;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.DecorationNode;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class XYCompartmentContentPart<V extends DecorationNode> extends CompartmentContentPart<V, ScrollPane> {



	public XYCompartmentContentPart(final V view) {
		super(view);
	}

	@Override
	protected ScrollPane doCreateVisual() {
		final Pane pane = new Pane();
		pane.boundsInParentProperty().addListener(boundsListener);

		final ScrollPane scrollPane = new ScrollPane(pane);

		// Set stylesheet to hide viewport child which can't
		scrollPane.getStylesheets().clear();
		scrollPane.getStylesheets().add(URI.createPlatformPluginURI("resources/scrollPane.css", false).toPlatformString(false));

		return scrollPane;
	}

	@Override
	protected void addChildVisual(final IVisualPart<Node, ? extends Node> child, final int index) {
		if (null != child.getVisual() && null != ((ScrollPane) getVisual()).getContent()) {
			((Pane) ((ScrollPane) getVisual()).getContent()).getChildren().add(child.getVisual());
		}
	}

	@Override
	protected void removeChildVisual(final IVisualPart<Node, ? extends Node> child, final int index) {
		final Node childVisual = child.getVisual();
		if (childVisual == null) {
			return;
		}
		if (null != ((ScrollPane) getVisual()).getContent()) {
			((Pane) ((ScrollPane) getVisual()).getContent()).getChildren().remove(childVisual);
		}
	}


	@Override
	protected String getStyleClass() {
		return "genericXYCompartment";
	}
}
