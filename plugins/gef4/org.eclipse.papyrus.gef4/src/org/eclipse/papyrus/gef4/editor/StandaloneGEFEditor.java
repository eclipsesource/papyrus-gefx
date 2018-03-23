/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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
package org.eclipse.papyrus.gef4.editor;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.gef4.module.GEFFxModule;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import com.google.inject.Module;

public abstract class StandaloneGEFEditor<MODEL> extends GEFEditor<MODEL> {

	private ResourceSet resourceSet;

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		MODEL diagram = getDiagramRoot(input);
		if (diagram == null) {
			throw new PartInitException("Invalid editor input: " + input);
		}
		init(getModule(diagram));
	}

	protected Module getModule(MODEL diagramRoot) {
		return new GEFFxModule();
	}

	protected abstract MODEL getDiagramRoot(IEditorInput input);

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

}
