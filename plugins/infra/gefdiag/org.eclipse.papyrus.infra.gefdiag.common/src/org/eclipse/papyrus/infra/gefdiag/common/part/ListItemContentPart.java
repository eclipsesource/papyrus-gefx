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

import org.eclipse.gmf.runtime.notation.View;

import javafx.geometry.Insets;
import javafx.scene.control.Label;

public class ListItemContentPart extends LabelContentPart implements IPrimaryContentPart {

	public ListItemContentPart(View view) {
		super(view);
	}


	@Override
	protected void refreshTextAlignment() {
		super.refreshTextAlignment();

		Label label = getVisual();

		double paddingHeight = 0;
		double paddingWidth = 4;

		label.setRotate(0);

		label.setPadding(new Insets(paddingHeight, paddingWidth, paddingHeight, paddingWidth));
	}

}
