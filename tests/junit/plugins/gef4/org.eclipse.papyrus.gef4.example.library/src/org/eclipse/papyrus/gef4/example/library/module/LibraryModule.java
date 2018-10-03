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

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.example.library.diagram.providers.ContentPartProvider;
import org.eclipse.papyrus.gef4.gmf.module.GMFModule;
import org.eclipse.papyrus.gef4.provider.IContentPartProvider;

import com.google.inject.TypeLiteral;

public class LibraryModule extends GMFModule {

	@Override
	protected void bindIContentPartProvider() {
		binder().bind(new TypeLiteral<IContentPartProvider<View>>() {
		}).to(ContentPartProvider.class);
	}

	// TODO
	protected void bindPalette() {
		// binder().bind(PaletteRenderer.class).to(DefaultPaletteRenderer.class);
		// binder().bind(PaletteDescriptor.class).to(TODOLibraryPaletteDescriptor.class);
	}

}
