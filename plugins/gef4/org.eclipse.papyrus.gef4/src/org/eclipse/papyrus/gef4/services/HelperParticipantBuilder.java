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

import org.eclipse.gef.mvc.fx.parts.IVisualPart;

public class HelperParticipantBuilder<T> implements HelperProviderParticipant<T> {

	double priority = Double.MIN_VALUE;

	private HelperParticipantBuilder() {

	}

	public static <T> HelperParticipantBuilder<T> newBuilder() {
		return new HelperParticipantBuilder<>();
	}

	public HelperParticipantBuilder<T> priority(double priority) {
		this.priority = priority;
		return this;
	}

	public HelperParticipantBuilder<T> forClass(Class<?> injectedClass) {
		// TODO
		return this;
	}

	public HelperParticipantBuilder<T> inject() {
		// TODO
		return this;
	}

	@Override
	public T get(IVisualPart<?> part) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getPriority(IVisualPart<?> part) {
		return priority;
	}

}
