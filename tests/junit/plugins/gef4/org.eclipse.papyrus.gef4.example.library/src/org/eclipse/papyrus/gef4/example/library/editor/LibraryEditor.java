package org.eclipse.papyrus.gef4.example.library.editor;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.gef4.editor.StandaloneGEFEditor;
import org.eclipse.papyrus.gef4.example.library.module.LibraryModule;

import com.google.inject.Module;

public class LibraryEditor extends StandaloneGEFEditor {

	public static final String EDITOR_ID = "org.eclipse.papyrus.gef4.example.library.editor";

	@Override
	protected Module getModule() {
		return new LibraryModule();
	}

	@Override
	protected ResourceSet createResourceSet() {
		ResourceSet resourceSet = super.createResourceSet();
		// CSSNotationResourceFactory factory = new CSSNotationResourceFactory();
		// resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("librarydiagram", factory);
		return resourceSet;
	}

}
