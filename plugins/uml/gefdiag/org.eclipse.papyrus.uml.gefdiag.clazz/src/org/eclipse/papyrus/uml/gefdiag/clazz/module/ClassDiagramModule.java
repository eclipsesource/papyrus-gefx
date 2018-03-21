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
package org.eclipse.papyrus.uml.gefdiag.clazz.module;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.palette.Palette;
import org.eclipse.papyrus.gef4.provider.IContentPartProvider;
import org.eclipse.papyrus.uml.gefdiag.clazz.providers.ContentPartProvider;
import org.eclipse.papyrus.uml.gefdiag.common.module.UMLDiagramModule;

import com.google.inject.TypeLiteral;

public class ClassDiagramModule extends UMLDiagramModule {

	@Override
	protected void bindIContentPartProvider() {
		binder().bind(new TypeLiteral<IContentPartProvider<View>>() {
		}).to(ContentPartProvider.class);
	}

	/**
	 * @see org.eclipse.papyrus.gef4.module.GEFFxModule#bindPalette()
	 *
	 */
	protected void bindPalette() {
		bind(Palette.class).to(DemoClassPalette.class);
	}

}
