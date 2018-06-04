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
package org.eclipse.papyrus.gef4.utils;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;

/**
 * Wrapper interface to expose a GEF Common {@link org.eclipse.gef.common.adapt.IAdaptable} as
 * an eclipse {@link IAdaptable}
 */
public class GEFCommonAdapter implements IAdaptable {

	private final org.eclipse.gef.common.adapt.IAdaptable adaptable;

	public GEFCommonAdapter(org.eclipse.gef.common.adapt.IAdaptable gefCommonAdaptable) {
		Assert.isNotNull(gefCommonAdaptable);
		this.adaptable = gefCommonAdaptable;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return this.adaptable.getAdapter(adapter);
	}

}
