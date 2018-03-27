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
package org.eclipse.papyrus.gef4.gmf.services;

import java.util.Map;

import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IContentPartFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.scopes.PartScope;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

public class ProviderBasedContentPartFactory implements IContentPartFactory {

	@Inject
	private Injector injector;

	@Inject
	private PartScope scope;

	@Override
	public IContentPart<?> createContentPart(Object content, Map<Object, Object> contextMap) {
		if (content instanceof View) {
			final View view = (View) content;

			scope.enter(view);
			try {
				IContentPart<?> contentPart = injector.getInstance(Key.get(new TypeLiteral<IContentPart<?>>() {
					//
				}));
				assert contentPart != null;
				return contentPart;
			} finally {
				scope.exit(view);
			}
		}
		return null;
	}

}
