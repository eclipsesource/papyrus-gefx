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

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

/**
 * <p>
 * A generic factory to instantiate helpers that are bound to a {@link IVisualPart}
 * </p>
 */
public interface HelperProvider<T> {

	/**
	 * The {@link AdapterKey#role(String) role} used to register the provided value
	 * as an adapter of the given visual part
	 */
	String ROLE = "HelperProviderRole"; //$NON-NLS-1$
	
	/**
	 * <p>
	 * Return an instance of T for this VisualPart.
	 * </p>
	 *
	 * @param part
	 *
	 * @return
	 * 		An instance of T
	 */
	T get(IVisualPart<?> part);
}
