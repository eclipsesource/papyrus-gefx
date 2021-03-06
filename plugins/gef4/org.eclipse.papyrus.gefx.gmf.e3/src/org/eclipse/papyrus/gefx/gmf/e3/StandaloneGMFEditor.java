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
package org.eclipse.papyrus.gefx.gmf.e3;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.gef4.gmf.module.NotationDiagramModule;
import org.eclipse.papyrus.gefx.e3.StandaloneGEFEditor;
import org.eclipse.papyrus.infra.ui.util.EditorUtils;
import org.eclipse.ui.IEditorInput;

import com.google.inject.Module;
import com.google.inject.util.Modules;

public abstract class StandaloneGMFEditor extends StandaloneGEFEditor<Diagram> {

	private ResourceSet resourceSet;

	@Override
	protected Diagram getDiagramRoot(IEditorInput input) {
		URI uri = EditorUtils.getResourceURI(input);
		if (uri != null) {
			return loadDiagram(uri);
		}
		return null;
	}

	@Override
	protected Module getModule(Diagram diagramRoot) {
		return Modules.override(super.getModule(diagramRoot)).with(new NotationDiagramModule(diagramRoot));
	}

	protected ResourceSet createResourceSet() {
		TransactionalEditingDomain editingDomain = TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain();
		return editingDomain.getResourceSet();
	}

	protected ResourceSet getResourceSet() {
		if (resourceSet == null) {
			resourceSet = createResourceSet();
		}
		return resourceSet;
	}

	protected Diagram loadDiagram(URI uri) {
		Resource inputResource = getResourceSet().getResource(uri, true);
		for (EObject rootElement : inputResource.getContents()) {
			if (rootElement instanceof Diagram) {
				return (Diagram) rootElement;
			}
		}
		return null;
	}

}
