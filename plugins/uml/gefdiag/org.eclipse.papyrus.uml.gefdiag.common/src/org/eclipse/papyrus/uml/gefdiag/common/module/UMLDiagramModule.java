/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.gefdiag.common.module;

import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.module.GEFFxModule;
import org.eclipse.papyrus.gef4.palette.Palette;
import org.eclipse.papyrus.gef4.provider.IContentChildrenProvider;
import org.eclipse.papyrus.uml.gefdiag.common.provider.StereotypeAwareContentChildrenProvider;

import com.google.inject.TypeLiteral;

public abstract class UMLDiagramModule extends GEFFxModule {

	/**
	 * @see org.eclipse.papyrus.gef4.module.GEFFxModule#configure()
	 *
	 */
	@Override
	protected void configure() {
		super.configure();

		bindElementTypesRegistry();
	}

	@Override
	protected void bindDefaultContentChildrenProvider() {
		binder().bind(new TypeLiteral<IContentChildrenProvider<View>>() {
		}).to(StereotypeAwareContentChildrenProvider.class);

	}

	/**
	 * @see org.eclipse.papyrus.gef4.module.GEFFxModule#bindPalette()
	 *
	 */
	@Override
	protected void bindPalette() {
		binder().bind(Palette.class).to(FakePalette.class);
	}

	protected void bindElementTypesRegistry() {
		binder().bind(ElementTypeRegistry.class).toInstance(ElementTypeRegistry.getInstance());
	}

}
