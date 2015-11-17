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

import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.papyrus.gef4.utils.FXUtils;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

//FIXME: Doesn't properly handle negative coordinates
public class XYCompartmentContentPart<V extends DecorationNode> extends CompartmentContentPart<V, Pane> {

	public XYCompartmentContentPart(final V view) {
		super(view);
	}

	@Override
	protected Pane doCreatePane() {
		return new Pane();
	}

	@Override
	protected void refreshVisualInTransaction(VBox visual) {
		super.refreshVisualInTransaction(visual);

		// Must be false for X/Y-based layout
		scrollPane.setFitToHeight(false);
		scrollPane.setFitToWidth(false);

		FXUtils.setPadding(compartment, 2, 2);
	}

	@Override
	protected String getStyleClass() {
		return "genericXYCompartment";//$NON-NLS-1$
	}
}
