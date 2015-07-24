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
package org.eclipse.papyrus.infra.gefdiag.common.part.uml;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gefdiag.common.part.LabelContentPart;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class PackageLabelContentPart extends LabelContentPart {

	public PackageLabelContentPart(View view) {
		super(view);
	}

	@Override
	protected void refreshTextAlignment() {
		Label label = getVisual();

		double paddingWidth = 5;
		double paddingHeight = 2;

		label.setPadding(new Insets(paddingHeight, paddingWidth, paddingHeight, paddingWidth));

		label.setAlignment(Pos.CENTER);
	}

	@Override
	protected String getStyleClass() {
		return "packageLabel";
	}

}
