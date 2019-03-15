package org.eclipse.papyrus.gefx.e3.module;

import org.eclipse.gef.mvc.fx.ui.parts.ISelectionProviderFactory;
import org.eclipse.papyrus.gefx.e3.SelectionProviderFactory;

import com.google.inject.AbstractModule;

public class GEFEclipseUIModule extends AbstractModule {

	protected void configure() {
		bindSelectionProviderFactory();
	}

	protected void bindSelectionProviderFactory() {
		binder().bind(ISelectionProviderFactory.class).to(SelectionProviderFactory.class);
	}

}
