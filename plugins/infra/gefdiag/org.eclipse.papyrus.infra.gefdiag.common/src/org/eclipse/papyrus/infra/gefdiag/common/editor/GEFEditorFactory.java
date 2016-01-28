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
package org.eclipse.papyrus.infra.gefdiag.common.editor;

import org.eclipse.core.runtime.Assert;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.ui.extension.diagrameditor.AbstractEditorFactory;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageModel;

public abstract class GEFEditorFactory extends AbstractEditorFactory {

	private final String type;

	public GEFEditorFactory(Class<? extends GEF4DiagramEditor> diagramEditorClass, String type) {
		super(diagramEditorClass, type);
		Assert.isNotNull(type);
		this.type = type;
	}

	@Override
	public abstract IPageModel createIPageModel(Object pageIdentifier);

	@Override
	public boolean isPageModelFactoryFor(Object pageIdentifier) {
		if (pageIdentifier instanceof Diagram) {
			Diagram diagram = (Diagram) pageIdentifier;
			if (diagram.eIsProxy()) {
				return false;
			}
			return type.equals(diagram.getType());
		}
		return false;
	}

}
