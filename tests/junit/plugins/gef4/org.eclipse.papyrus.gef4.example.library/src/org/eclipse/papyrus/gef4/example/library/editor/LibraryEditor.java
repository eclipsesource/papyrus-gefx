/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (EclipseSource) cletavernier@eclipsesource.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.example.library.editor;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.gef4.example.library.module.LibraryModule;
import org.eclipse.papyrus.gef4.gmf.editor.StandaloneGMFEditor;

import com.google.inject.Module;
import com.google.inject.util.Modules;

public class LibraryEditor extends StandaloneGMFEditor {

	public static final String EDITOR_ID = "org.eclipse.papyrus.gef4.example.library.editor";

	@Override
	protected Module getModule(Diagram diagram) {
		return Modules.override(super.getModule(diagram)).with(new LibraryModule());
	}

	@Override
	protected ResourceSet createResourceSet() {
		ResourceSet resourceSet = super.createResourceSet();
		// CSSNotationResourceFactory factory = new CSSNotationResourceFactory();
		// resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("librarydiagram", factory);
		return resourceSet;
	}

}
