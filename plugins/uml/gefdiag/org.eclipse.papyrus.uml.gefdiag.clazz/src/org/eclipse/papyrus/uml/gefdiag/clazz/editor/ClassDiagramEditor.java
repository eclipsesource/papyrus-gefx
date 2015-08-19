package org.eclipse.papyrus.uml.gefdiag.clazz.editor;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.gefdiag.common.editor.GEF4DiagramEditor;
import org.eclipse.papyrus.uml.gefdiag.clazz.module.ClassDiagramModule;

import com.google.inject.Module;

public class ClassDiagramEditor extends GEF4DiagramEditor {
	
	public static final String DIAGRAM_TYPE = "PapyrusGEF4ClassDiagram"; //$NON-NLS-1$

	public ClassDiagramEditor(Diagram diagram) {
		super(diagram);
	}

	@Override
	protected Module createModule() {
		return new ClassDiagramModule(getDiagram());
	}

}
