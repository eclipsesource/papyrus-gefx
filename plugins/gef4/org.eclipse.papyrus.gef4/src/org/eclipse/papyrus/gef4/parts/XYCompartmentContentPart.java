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
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.papyrus.gef4.utils.VisualPartUtil;

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
		scrollPane.getStylesheets().add(URI.createPlatformPluginURI(VisualPartUtil.VIEWPORT_SCROLL_PANE_STYLE, false).toPlatformString(false));

		return scrollPane;
	}

	@Override
	protected String getStyleClass() {
		return "genericXYCompartment";//$NON-NLS-1$
	}
}
