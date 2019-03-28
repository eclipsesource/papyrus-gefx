/*****************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
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
package org.eclipse.papyrus.gef4.adapt;

import org.eclipse.gef.common.adapt.AdaptableSupport;
import org.eclipse.gef.common.adapt.IAdaptable;

import com.google.common.reflect.TypeToken;
import com.google.inject.ImplementedBy;

/**
 * <p>
 * Helper interface used to determine the best adapter at runtime, when
 * several adapters match a given key (Class or TypeToken)
 * </p>
 * <p>
 * This is typically used to bypass the default strategy of {@link AdaptableSupport}
 * and provide more complex adapter resolution (e.g. based on priorities)
 * </p>
 */
@ImplementedBy(CachedPrioritizedAdapterHelper.class)
public interface AdapterHelper {

	/**
	 * Get the adapter corresponding to the given <code>classKey</code>.
	 * If more than one adapter matches this key, implementations of {@link AdapterHelper}
	 * will provide an unambiguous value.
	 * 
	 * @param <T>
	 *            The adapter type
	 * @param adaptable
	 *            The adaptable from which adapters will be resolved
	 * @param classKey
	 *            The adapter key to resolve
	 * @return
	 *         An adapter matching the given key, or <code>null</code> if none matches. If
	 *         several adapters match the key, the result will depend on the {@link AdapterHelper}
	 *         implementation.
	 */
	default <T> T getAdapter(IAdaptable adaptable, Class<T> classKey) {
		return getAdapter(adaptable, TypeToken.of(classKey));
	}

	/**
	 * Get the adapter corresponding to the given <code>key</code>.
	 * If more than one adapter matches this key, implementations of {@link AdapterHelper}
	 * will provide an unambiguous value.
	 * 
	 * @param <T>
	 *            The adapter type
	 * @param adaptable
	 *            The adaptable from which adapters will be resolved
	 * @param key
	 *            The adapter key to resolve
	 * @return
	 *         An adapter matching the given key, or <code>null</code> if none matches. If
	 *         several adapters match the key, the result will depend on the {@link AdapterHelper}
	 *         implementation.
	 */
	<T> T getAdapter(IAdaptable adaptable, TypeToken<T> key);

}
