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

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.core.extension.diagrameditor.AbstractEditorFactory;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageModel;

public class GEFEditorFactory extends AbstractEditorFactory {

	public static final String TYPE = "GEF4Example";
	
	public GEFEditorFactory() {
		super(GEFEditor.class, TYPE);
	}
	
	@Override
	public IPageModel createIPageModel(Object pageIdentifier) {
		return new GEFPageModel((Diagram)pageIdentifier);
	}

	@Override
	public boolean isPageModelFactoryFor(Object pageIdentifier) {
		if (pageIdentifier instanceof Diagram){
			Diagram diagram = (Diagram)pageIdentifier;
			if (diagram.eIsProxy()){
				return false;
			}
			return TYPE.equals(diagram.getType());
		}
		return false;
	}

}
