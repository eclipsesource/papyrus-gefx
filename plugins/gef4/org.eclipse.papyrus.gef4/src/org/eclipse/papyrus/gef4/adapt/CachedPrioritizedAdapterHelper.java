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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.gef.common.adapt.IAdaptable;

import com.google.common.reflect.TypeToken;

/**
 * <p>
 * Cached version of the {@link PrioritizedAdapterHelper}
 * </p>
 * <p>
 * This adapter helper is meant to be bound to {@link IAdaptable} type it provides values for,
 * and will keep a cache of all requested keys; so that each key is resolved only once.
 * Equivalent keys (e.g. <code>Class&lt;T&gt;</code> and <code>TypeToken.of(Class&lt;T&gt;)</code>)
 * will be cached only once, and will always share the same value.
 * </p>
 */
public class CachedPrioritizedAdapterHelper extends PrioritizedAdapterHelper implements AdapterHelper{
	
	private final Map<TypeToken<?>, Optional<Object>> cachedValues = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public <T> T getAdapter(IAdaptable adaptable, TypeToken<T> key) {
		// This cast should be safe; as the delegate is type-safe
		return ((Optional<T>) cachedValues.computeIfAbsent(key, t -> Optional.ofNullable(super.getAdapter(adaptable, key)))).orElse(null);
	}

}
