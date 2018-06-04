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
package org.eclipse.papyrus.gef4.gmf.utils;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gmf.runtime.notation.View;

public class AdapterUtil {

	public static final String SEMANTIC_ROLE = "semantic"; //$NON-NLS-1$
	public static final String NOTATION_ROLE = "notation"; //$NON-NLS-1$

	public static void setModelAdapters(View view, EObject semanticElement, IContentPart<?> contentPart) {
		Assert.isNotNull(view);

		if (semanticElement != null) {
			contentPart.setAdapter(semanticElement, SEMANTIC_ROLE);
			contentPart.setAdapter(semanticElement, AdapterKey.DEFAULT_ROLE);
		}

		contentPart.setAdapter(view, NOTATION_ROLE);
	}

	public static EObject getSemanticElement(IContentPart<?> adaptable) {
		return adaptable.getAdapter(AdapterKey.get(EObject.class, SEMANTIC_ROLE));
	}

	public static View getView(IContentPart<?> adaptable) {
		return adaptable.getAdapter(AdapterKey.get(View.class, NOTATION_ROLE));
	}
}
