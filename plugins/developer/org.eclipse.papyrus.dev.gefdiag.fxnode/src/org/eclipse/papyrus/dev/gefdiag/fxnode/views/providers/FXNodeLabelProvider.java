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
package org.eclipse.papyrus.dev.gefdiag.fxnode.views.providers;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.infra.tools.util.ListHelper;
import org.eclipse.swt.graphics.Image;

import javafx.scene.Node;
import javafx.scene.Scene;

public class FXNodeLabelProvider extends LabelProvider {
	@Override
	public Image getImage(Object element) {
		return super.getImage(element);
	}

	@Override
	public String getText(Object element) {
		String text;

		if (element instanceof Node) {
			text = element.getClass().getName();
			text += " [" + ListHelper.deepToString(((Node) element).getStyleClass(), ", ") + "]";
		} else if (element instanceof Scene) {
			text = "Scene";
		} else {
			text = "<>";
		}

		return text;
	}
}
