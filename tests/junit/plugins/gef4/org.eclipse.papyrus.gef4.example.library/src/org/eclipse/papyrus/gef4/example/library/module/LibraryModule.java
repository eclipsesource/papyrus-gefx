package org.eclipse.papyrus.gef4.example.library.module;

import org.eclipse.gef.common.adapt.inject.AdaptableScopes;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.papyrus.gef4.example.library.diagram.providers.VisualPartProvider;
import org.eclipse.papyrus.gef4.module.GEFFxModule;
import org.eclipse.papyrus.gef4.provider.IVisualPartProvider;

import com.google.inject.TypeLiteral;

public class LibraryModule extends GEFFxModule {

	@Override
	protected void bindIVisualPartProvider() {
		binder().bind(new TypeLiteral<IVisualPartProvider>() {
		}).to(VisualPartProvider.class)
				.in(AdaptableScopes.typed(IViewer.class));
	}
}
