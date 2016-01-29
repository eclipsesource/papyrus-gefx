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

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.utils.FXUtils;

import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

//FIXME Implement generic IParser (Or injected IParser) to avoid abstract class here
public abstract class ListItemContentPart extends LabelContentPart implements IPrimaryContentPart {

	public ListItemContentPart(View view) {
		super(view);
	}

	@Override
	protected Label doCreateVisual() {
		Label thisLabel = super.doCreateVisual();

		VBox.setVgrow(thisLabel, Priority.SOMETIMES); // For line wrap
		thisLabel.setWrapText(true);

		return thisLabel;
	}

	@Override
	protected void refreshPadding() {
		FXUtils.setPadding(label, 0, 0); // Avoid padding for list items. Better use the parent VBox's spacing & padding
	}

}
