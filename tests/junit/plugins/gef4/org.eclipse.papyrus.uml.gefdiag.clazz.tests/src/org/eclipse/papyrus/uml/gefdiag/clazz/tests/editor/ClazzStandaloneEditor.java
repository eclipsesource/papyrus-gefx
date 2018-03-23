package org.eclipse.papyrus.uml.gefdiag.clazz.tests.editor;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.gef4.gmf.editor.StandaloneGMFEditor;
import org.eclipse.papyrus.infra.gmfdiag.css.helper.CSSHelper;
import org.eclipse.papyrus.uml.gefdiag.clazz.module.ClassDiagramModule;

import com.google.inject.Module;
import com.google.inject.util.Modules;

public class ClazzStandaloneEditor extends StandaloneGMFEditor {

	public static final String ID = "org.eclipse.papyrus.uml.gefdiag.clazz.editor";

	@Override
	protected Module getModule(Diagram diagram) {
		return Modules.override(super.getModule(diagram)).with(new ClassDiagramModule());
	}

	@Override
	protected ResourceSet createResourceSet() {
		ResourceSet resourceSet = super.createResourceSet();
		CSSHelper.installCSSSupport(resourceSet);
		return resourceSet;
	}

}
