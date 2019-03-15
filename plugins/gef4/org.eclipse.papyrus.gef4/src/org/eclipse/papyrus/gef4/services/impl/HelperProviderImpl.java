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
package org.eclipse.papyrus.gef4.services.impl;

import java.util.Set;

import javax.inject.Inject;

import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.services.HelperProvider;
import org.eclipse.papyrus.gef4.services.HelperProviderParticipant;

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
		if (part.getAdapter(result.getClass()) == null) {
			part.setAdapter(result);
		}
		return result;
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
