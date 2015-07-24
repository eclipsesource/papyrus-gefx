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
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IEditorModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorActionBarContributor;

public class GEFPageModel implements IEditorModel {

	private Diagram diagram;

	private Image tabImage;

	public GEFPageModel(Diagram diagram) {
		this.diagram = diagram;
	}

	@Override
	public String getTabTitle() {
		return diagram.getName() == null ? "" : diagram.getName();
	}

	@Override
	public Image getTabIcon() {
		if (tabImage == null) {
			tabImage = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("org.eclipse.papyrus.uml.diagram.clazz", "icons/obj16/Diagram_Class.gif");
		}

		return tabImage;
	}

	@Override
	public Object getRawModel() {
		return diagram;
	}

	@Override
	public void dispose() {
		// Nothing
	}

	@Override
	public IEditorPart createIEditorPart() throws PartInitException {
		return new GEFEditor(diagram);
	}

	@Override
	public EditorActionBarContributor getActionBarContributor() {
		return null; // Required?
	}

}
