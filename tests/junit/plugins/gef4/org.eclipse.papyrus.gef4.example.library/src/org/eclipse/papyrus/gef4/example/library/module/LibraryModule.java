/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (EclipseSource) cletavernier@eclipsesource.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.example.library.module;

import javax.inject.Named;
import javax.inject.Singleton;

import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.example.library.diagram.providers.ContentPartProvider;
import org.eclipse.papyrus.gef4.gmf.module.GMFModule;
import org.eclipse.papyrus.gef4.provider.IContentPartProvider;

import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

public class LibraryModule extends GMFModule {
	
	/**
	 * Name of the injected {@link String} property representing the {@link IClientContext} ID
	 *
	 * @see {@link Named @Named}
	 */
	public static final String CLIENT_CONTEXT_ID = "clientContextID";

	@Override
	protected void bindIContentPartProvider() {
		binder().bind(new TypeLiteral<IContentPartProvider<View>>() {
		}).to(ContentPartProvider.class);
		
		bindPalette();
		bindElementTypesRegistry();
		configureClientContextID();
		bindPreferencesHint();
	}
	
	@Provides
	@Singleton
	protected IClientContext bindClientContext(@Named(CLIENT_CONTEXT_ID) String clientContextId) {
		return ClientContextManager.getInstance().getClientContext(clientContextId);
	}
	
	protected void configureClientContextID() {
		// Bind to the default Papyrus Type Context ID
		binder().bind(String.class).annotatedWith(Names.named(CLIENT_CONTEXT_ID)).toInstance("org.eclipse.gmf.runtime.emf.type.core.defaultContext");
	}

	protected void bindPreferencesHint() {
		bind(PreferencesHint.class).toInstance(new PreferencesHint("org.eclipse.papyrus.gef4.example.library"));
	}

	protected void bindElementTypesRegistry() {
		binder().bind(ElementTypeRegistry.class).toInstance(ElementTypeRegistry.getInstance());
	}

	// TODO
	protected void bindPalette() {
		// binder().bind(PaletteRenderer.class).to(DefaultPaletteRenderer.class);
		// binder().bind(PaletteDescriptor.class).to(TODOLibraryPaletteDescriptor.class);
	}

}
