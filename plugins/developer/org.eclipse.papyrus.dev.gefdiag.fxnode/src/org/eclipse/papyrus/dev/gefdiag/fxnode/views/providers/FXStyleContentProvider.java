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

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import javafx.css.CssMetaData;
import javafx.scene.Node;

public class FXStyleContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
		// Nothing
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// Nothing
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Node) {
			Node node = (Node) inputElement;

			Map<String, Object> properties = new LinkedHashMap<String, Object>();
			for (String styleClass : node.getStyleClass()) {
				properties.put("." + styleClass, styleClass);
			}

			for (CssMetaData<?, ?> meta : node.getCssMetaData()) {
				String property = meta.getProperty();
				properties.put(property, node);
			}
			return properties.entrySet().toArray();
		}
		return new Object[0];
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}

}
