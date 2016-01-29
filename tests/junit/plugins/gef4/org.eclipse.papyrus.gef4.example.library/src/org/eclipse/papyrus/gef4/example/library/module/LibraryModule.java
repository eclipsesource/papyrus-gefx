package org.eclipse.papyrus.gef4.example.library.module;

import org.eclipse.gef4.common.adapt.inject.AdaptableScopes;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.papyrus.gef4.example.library.diagram.providers.VisualPartProvider;
import org.eclipse.papyrus.gef4.module.GEFFxModule;
import org.eclipse.papyrus.gef4.provider.IVisualPartProvider;

import com.google.inject.TypeLiteral;

import javafx.scene.Node;

public class LibraryModule extends GEFFxModule {

	@Override
	protected void bindIVisualPartProvider() {
		binder().bind(new TypeLiteral<IVisualPartProvider<Node>>() {
		}).to(VisualPartProvider.class)
				.in(AdaptableScopes.typed(FXViewer.class));
	}
}
