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

import org.eclipse.gef4.common.adapt.inject.AdaptableScopes;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.papyrus.gef4.provider.IVisualPartProvider;
import org.eclipse.papyrus.uml.gefdiag.clazz.providers.VisualPartProvider;
import org.eclipse.papyrus.uml.gefdiag.common.module.UMLDiagramModule;

import com.google.inject.TypeLiteral;

import javafx.scene.Node;

public class ClassDiagramModule extends UMLDiagramModule {

	@Override
	protected void bindIVisualPartProvider() {
		binder().bind(new TypeLiteral<IVisualPartProvider<Node>>() {
		}).to(VisualPartProvider.class)
				.in(AdaptableScopes.typed(FXViewer.class));
	}

}
