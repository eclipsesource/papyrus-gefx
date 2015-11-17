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

import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.papyrus.gef4.utils.FXUtils;

import javafx.scene.layout.VBox;


public class ListCompartmentContentPart<V extends DecorationNode> extends CompartmentContentPart<V, VBox> {
	public ListCompartmentContentPart(final V view) {
		super(view);
	}

	@Override
	protected VBox doCreatePane() {
		return new VBox();
	}

	@Override
	protected String getStyleClass() {
		return "genericListCompartment";
	}

	@Override
	protected void refreshVisualInTransaction(VBox visual) {
		super.refreshVisualInTransaction(visual);
		refreshMargins();
	}

	protected void refreshMargins() {
		FXUtils.setPadding(compartment, 1, 5);
	}

}

