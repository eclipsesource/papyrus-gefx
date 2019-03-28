/*****************************************************************************
 * Copyright (c) 2018, 2019 EclipseSource and others.
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
package org.eclipse.papyrus.gef4.services.impl;

import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.fx.core.observable.FXObservableUtil;
import org.eclipse.gef.common.activate.IActivatable;
import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.common.adapt.IAdaptable;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.services.HelperProvider;
import org.eclipse.papyrus.gef4.services.HelperProviderParticipant;

/**
 * <p>
 * An Implementation of {@link HelperProvider} based on one or more {@link HelperProviderParticipant}s
 * </p>
 *
 * <p>
 * Participants are ordered based on their {@link HelperProviderParticipant#getPriority(IVisualPart) priority};
 * the one with the higher priority for a given {@link IVisualPart} is used to provide an instance of <code>T</code>
 * for that part.
 * </p>
 *
 * <p>
 * {@link HelperProviderImpl} should always be provided with at least one default participant, that can provide
 * a value of <code>T</code> for any {@link IVisualPart} (The default participant is typically registered
 * with the lowest priority). If <code>T</code> is meant to be optional or nullable; then it should be specified explicitly
 * with a {@link HelperProvider}&lt;{@link Optional}&lt;T&gt;&gt;
 * </p>
 *
 * @param <T>
 */
public class HelperProviderImpl<T> implements HelperProvider<T> {

	private final Set<HelperProviderParticipant<T>> participants;

	@Inject
	public HelperProviderImpl(Set<HelperProviderParticipant<T>> participants) {
		this.participants = participants;
	}

	@Override
	public T get(IVisualPart<?> part) {
		HelperProviderParticipant<T> bestMatch = getBestParticipant(part);
		assert bestMatch != null;
		T result = bestMatch == null ? null : bestMatch.get(part);
		assert result != null;

		if (result instanceof Optional || result instanceof com.google.common.base.Optional) {
			Optional<?> opt;
			if (result instanceof Optional) {
				opt = ((Optional<?>) result);
			} else {
				opt = ((com.google.common.base.Optional<?>) result).toJavaUtil();
			}
			opt.ifPresent(value -> bindAdapter(part, value));
		} else {
			bindAdapter(part, result);
		}

		return result;
	}

	private void bindAdapter(IVisualPart<?> part, Object adapter) {
		if (part.getAdapter(AdapterKey.get(adapter.getClass(), ROLE)) == null) {
			// Register as an adapter of VisualPart (This will also activate the adapter later, when necessary)
			part.setAdapter(adapter, ROLE);
		} else {
			// Another adapter is already registered for the same Key; we have to manually support
			// IActivatable and IAdaptable.Bound
			if (adapter instanceof IActivatable) {
				IActivatable activatableResult = (IActivatable) adapter;
				FXObservableUtil.onChange(part.activeProperty(), active -> {
					if (active) {
						activatableResult.activate();
					} else {
						activatableResult.deactivate();
					}
				});
			}

			if (adapter instanceof IAdaptable.Bound) {
				bindAdapter((IAdaptable.Bound<?>) adapter, part);
			}
		}

		// Regardless of how the adapter was registered; we need to activate
		// it if the part is already active
		if (adapter instanceof IActivatable && part.isActive()) {
			((IActivatable) adapter).activate();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void bindAdapter(IAdaptable.Bound adapter, IVisualPart<?> part) {
		// XXX rawType: IAdaptable.Bound#setAdaptable(part) is the opposite of
		// part#setAdapter(IAdaptable.Bound); so the type is expected to be
		// consistent. However we don't have any way to check that here.
		// If #setAdaptable throws a ClassCastException here, then this would
		// indicate a module-configuration issue (Which we don't want to catch)
		adapter.setAdaptable(part);
	}

	private HelperProviderParticipant<T> getBestParticipant(IVisualPart<?> part) {
		// Note: the initial value doesn't matter, because we only compare priorities
		// if we already have a match. So it's actually possible to declare a participant
		// with a priority of Double.MIN_VALUE.
		double currentPriority = Double.MIN_VALUE;
		HelperProviderParticipant<T> bestMatch = null;
		for (HelperProviderParticipant<T> participant : participants) {
			double priority = participant.getPriority(part);
			if (Double.isNaN(priority)) {
				continue;
			}

			if (bestMatch == null || priority > currentPriority) {
				currentPriority = priority;
				bestMatch = participant;
			}
		}

		return bestMatch;
	}

	// TODO ParticipantBuilder

}
