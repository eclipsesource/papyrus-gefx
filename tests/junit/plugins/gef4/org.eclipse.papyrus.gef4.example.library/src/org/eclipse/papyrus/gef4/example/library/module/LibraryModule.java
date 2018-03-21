package org.eclipse.papyrus.gef4.example.library.module;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.example.library.diagram.providers.ContentPartProvider;
import org.eclipse.papyrus.gef4.example.library.editor.palette.LibraryPalette;
import org.eclipse.papyrus.gef4.gmf.module.GMFModule;
import org.eclipse.papyrus.gef4.palette.Palette;
import org.eclipse.papyrus.gef4.provider.IContentPartProvider;

import com.google.inject.TypeLiteral;

public class LibraryModule extends GMFModule {

	@Override
	protected void bindIContentPartProvider() {
		binder().bind(new TypeLiteral<IContentPartProvider<View>>() {
		}).to(ContentPartProvider.class);
	}

	protected void bindPalette() {
		binder().bind(Palette.class).to(LibraryPalette.class);
	}

}
