package org.eclipse.papyrus.uml.gefdiag.clazz.editor;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.gefdiag.common.editor.GEFDiagramPageModel;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;

public class ClassDiagramPageModel extends GEFDiagramPageModel {

	public ClassDiagramPageModel(Diagram diagram) {
		super(diagram);
	}

	@Override
	public IEditorPart createIEditorPart() throws PartInitException {
		return new ClassDiagramEditor(getDiagram());
	}

}
