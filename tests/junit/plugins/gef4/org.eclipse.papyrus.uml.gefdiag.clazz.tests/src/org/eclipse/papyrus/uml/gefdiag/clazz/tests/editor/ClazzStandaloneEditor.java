package org.eclipse.papyrus.uml.gefdiag.clazz.tests.editor;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.gef4.editor.StandaloneGEFEditor;
import org.eclipse.papyrus.infra.gmfdiag.css.helper.CSSHelper;
import org.eclipse.papyrus.uml.gefdiag.clazz.module.ClassDiagramModule;

import com.google.inject.Module;

public class ClazzStandaloneEditor extends StandaloneGEFEditor {

	public static final String ID = "org.eclipse.papyrus.uml.gefdiag.clazz.editor";

	@Override
	protected Module getModule() {
		return new ClassDiagramModule();
	}

	@Override
	protected ResourceSet createResourceSet() {
		ResourceSet resourceSet = super.createResourceSet();
		CSSHelper.installCSSSupport(resourceSet);
		return resourceSet;
	}

}
