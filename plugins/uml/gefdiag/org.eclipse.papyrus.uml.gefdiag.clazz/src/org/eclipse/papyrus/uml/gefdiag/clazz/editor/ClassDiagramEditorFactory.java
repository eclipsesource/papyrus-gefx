package org.eclipse.papyrus.uml.gefdiag.clazz.editor;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageModel;
import org.eclipse.papyrus.infra.gefdiag.common.editor.GEFEditorFactory;

public class ClassDiagramEditorFactory extends GEFEditorFactory {
	
	public ClassDiagramEditorFactory() {
		super(ClassDiagramEditor.class, ClassDiagramEditor.DIAGRAM_TYPE);
	}

	@Override
	public IPageModel createIPageModel(Object pageIdentifier) {
		return new ClassDiagramPageModel((Diagram) pageIdentifier);
	}

}
