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
package org.eclipse.papyrus.uml.gefdiag.clazz.editor;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageModel;
import org.eclipse.papyrus.infra.gefdiag.common.editor.GEFEditorFactory;

public class ClassDiagramEditorFactory extends GEFEditorFactory {

	public ClassDiagramEditorFactory() {
		super(ClassDiagramEditor.class, ClassDiagramEditor.DIAGRAM_TYPE);
	}

	@Override
	public IPageModel createIPageModel(Object pageIdentifier) {
		return new ClassDiagramPageModel((Diagram) pageIdentifier);
	}

	@Override
	public String getLabel() {
		return "Class Diagram (GEF4)";
	}

}
