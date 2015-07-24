/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gefdiag.common.editor;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Palette {

	private Parent paletteRoot;

	protected Parent initPalette() {
		VBox root = new VBox();

		Label label = new Label("Palette goes here");
		root.getChildren().add(label);

		return root;
	}

	public Node getVisual() {
		if (paletteRoot == null) {
			paletteRoot = initPalette();
		}
		return paletteRoot;
	}
}
