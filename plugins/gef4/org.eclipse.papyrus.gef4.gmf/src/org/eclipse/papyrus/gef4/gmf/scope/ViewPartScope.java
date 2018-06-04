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
package org.eclipse.papyrus.gef4.gmf.scope;

import javax.inject.Singleton;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.scopes.PartScope;

import com.google.inject.Key;

@Singleton
public class ViewPartScope extends PartScope {

	@Override
	public void enter(Object scope) {
		super.enter(scope);
		if (scope instanceof View) {
			register(scope, Key.get(View.class), scope);
		}
	}

}
