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

import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.infra.gefdiag.common.part.NodeContentPart;

import javafx.scene.layout.VBox;

public class PackageContentPart extends NodeContentPart {

	public PackageContentPart(Shape view) {
		super(view);
	}

	@Override
	protected void doRefreshVisual(VBox visual) {
		super.doRefreshVisual(visual);
		// visual.setRotate(0);
	}

	@Override
	protected String getStyleClass() {
		return "package";
	}
}
