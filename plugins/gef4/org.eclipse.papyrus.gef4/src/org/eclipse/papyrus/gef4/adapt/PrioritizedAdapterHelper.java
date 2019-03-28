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

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.common.adapt.IAdaptable;

import com.google.common.reflect.TypeToken;

/**
 * <p>
 * An implementation of {@link AdapterHelper} based on priorities.
 * Priorities can be provided explicitly via {@link PrioritizedAdapter}, or
 * implicitly via the adapter's role: if the role can be parsed to a number,
 * that number will be used as the priority value.
 * </p>
 */
public class PrioritizedAdapterHelper implements AdapterHelper {

	@Override
	public <T> T getAdapter(IAdaptable adaptable, TypeToken<T> key) {
		return getBestAdapter(adaptable.getAdapters(key));
	}

	private <T> T getBestAdapter(Map<AdapterKey<? extends T>, T> adapters) {
		if (adapters.isEmpty()) {
			return null;
		} else if (adapters.size() == 1) {
			return adapters.values().iterator().next();
		}
		
		double maxPrio = Double.NEGATIVE_INFINITY;
		T result = null;
		for (Entry<AdapterKey<? extends T>, T> entry : adapters.entrySet()) {
			double priority = getPriority(entry);
			if (priority > maxPrio) {
				maxPrio = priority;
				result = entry.getValue();
			}
		}
		return result;
	}
	
	private <T> double getPriority(Map.Entry<AdapterKey<? extends T>, T> entry) {
		if (entry.getValue() instanceof PrioritizedAdapter) {
			return ((PrioritizedAdapter) entry.getValue()).getPriority();
		} else {
			try {
				// If the role can be parsed to a number, used that as the priority
				return Double.parseDouble(entry.getKey().getRole());
			} catch (NumberFormatException ex) {
				// Ignore; role doesn't have to be a number
			}
		}
		return Double.MIN_VALUE;
	}

}
