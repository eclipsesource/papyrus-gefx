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
package org.eclipse.papyrus.gef4.services;

import javax.inject.Inject;

import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.google.inject.Injector;

public abstract class AbstractProviderParticipant<T> implements HelperProviderParticipant<T> {

	private Injector injector;

	@Inject
	public void setInjector(Injector injector) {
		this.injector = injector;
	}

	@Override
	public T get(IVisualPart<?> part) {
		T instance = createInstance(part);
		injector.injectMembers(instance);
		return instance;
	}

	protected abstract T createInstance(IVisualPart<?> part);

}
