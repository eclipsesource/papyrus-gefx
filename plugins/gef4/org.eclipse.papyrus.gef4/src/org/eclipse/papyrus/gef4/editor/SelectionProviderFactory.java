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

import org.eclipse.gef4.mvc.fx.domain.FXDomain;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.ui.parts.ISelectionProviderFactory;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IWorkbenchPart;

import com.google.inject.Inject;

public class SelectionProviderFactory implements ISelectionProviderFactory {

	@Inject
	FXDomain domain;

	@Override
	public ISelectionProvider create(IWorkbenchPart workbenchPart) {
		return new ViewerSelectionProvider(domain.getAdapter(FXViewer.class));
	}

}
