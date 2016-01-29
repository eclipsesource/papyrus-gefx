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
package org.eclipse.papyrus.infra.gefdiag.common.internal.editor;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IEditorModel;
import org.eclipse.papyrus.infra.gefdiag.common.editor.GEF4DiagramEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorActionBarContributor;

public class GEFDiagramPageModel implements IEditorModel {

	private DiagramEditorDescriptor descriptor;

	private Diagram diagram;

	public GEFDiagramPageModel(Diagram diagram, DiagramEditorDescriptor descriptor) {
		this.descriptor = descriptor;
		this.diagram = diagram;
	}

	@Override
	public String getTabTitle() {
		return diagram.getName() == null ? "" : diagram.getName();
	}

	@Override
	public Image getTabIcon() {
		return descriptor.getImage();
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
		return new GEF4DiagramEditor(diagram, descriptor.getModule());
	}

	@Override
	public EditorActionBarContributor getActionBarContributor() {
		return null; // Required?
	}

	protected Diagram getDiagram() {
		return diagram;
	}

}
