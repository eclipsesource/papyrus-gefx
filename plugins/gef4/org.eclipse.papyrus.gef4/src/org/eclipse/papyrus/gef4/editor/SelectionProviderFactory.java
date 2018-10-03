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

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.ui.parts.ISelectionProviderFactory;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IWorkbenchPart;

import com.google.inject.Inject;

public class SelectionProviderFactory implements ISelectionProviderFactory {

	@Inject
	IDomain domain;

	@Override
	public ISelectionProvider create(IWorkbenchPart workbenchPart) {
		return new ViewerSelectionProvider(domain.getAdapter(AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE)));
	}

}
