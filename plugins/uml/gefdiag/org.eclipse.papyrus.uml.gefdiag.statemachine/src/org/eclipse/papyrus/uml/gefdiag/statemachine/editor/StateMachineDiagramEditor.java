package org.eclipse.papyrus.uml.gefdiag.statemachine.editor;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.gefdiag.common.editor.GEF4DiagramEditor;
import org.eclipse.papyrus.uml.gefdiag.statemachine.module.StateMachineDiagramModule;

import com.google.inject.Module;

public class StateMachineDiagramEditor extends GEF4DiagramEditor {

	public static final String DIAGRAM_TYPE = "PapyrusUMLStateMachineDiagram"; //$NON-NLS-1$

	public StateMachineDiagramEditor(Diagram diagram) {
		super(diagram);
	}

	@Override
	protected Module createModule() {
		return new StateMachineDiagramModule(getDiagram());
	}

}
