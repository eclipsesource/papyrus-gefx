package org.eclipse.papyrus.uml.gefdiag.statemachine.editor;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.gefdiag.common.editor.GEFDiagramPageModel;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;

public class StateMachineDiagramPageModel extends GEFDiagramPageModel {

	public StateMachineDiagramPageModel(Diagram diagram) {
		super(diagram);
	}

	@Override
	public IEditorPart createIEditorPart() throws PartInitException {
		return new StateMachineDiagramEditor(getDiagram());
	}

}
