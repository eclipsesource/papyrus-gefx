package org.eclipse.papyrus.gef4.example.library.module;

import org.eclipse.gef.common.adapt.inject.AdaptableScopes;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.papyrus.gef4.example.library.diagram.providers.ContentPartProvider;
import org.eclipse.papyrus.gef4.module.GEFFxModule;
import org.eclipse.papyrus.gef4.provider.IContentPartProvider;

import com.google.inject.TypeLiteral;

public class LibraryModule extends GEFFxModule {

	@Override
	protected void bindIContentPartProvider() {
		binder().bind(new TypeLiteral<IContentPartProvider>() {
		}).to(ContentPartProvider.class)
				.in(AdaptableScopes.typed(IViewer.class));
	}
}
