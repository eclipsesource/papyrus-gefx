package org.eclipse.papyrus.gef4.gmf.editor;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.gef4.editor.StandaloneGEFEditor;
import org.eclipse.papyrus.gef4.gmf.module.NotationDiagramModule;
import org.eclipse.papyrus.infra.ui.util.EditorUtils;
import org.eclipse.ui.IEditorInput;

import com.google.inject.Module;
import com.google.inject.util.Modules;

public abstract class StandaloneGMFEditor extends StandaloneGEFEditor<Diagram> {

	@Override
	protected Diagram getDiagramRoot(IEditorInput input) {
		URI uri = EditorUtils.getResourceURI(input);
		if (uri != null) {
			return loadDiagram(uri);
		}
		return null;
	}

	@Override
	protected Module getModule(Diagram diagramRoot) {
		return Modules.override(super.getModule(diagramRoot)).with(new NotationDiagramModule(diagramRoot));
	}

	protected Diagram loadDiagram(URI uri) {
		Resource inputResource = getResourceSet().getResource(uri, true);
		for (EObject rootElement : inputResource.getContents()) {
			if (rootElement instanceof Diagram) {
				return (Diagram) rootElement;
			}
		}
		return null;
	}

}
