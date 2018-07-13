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
package org.eclipse.papyrus.gef4.gmf.services;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.fx.core.observable.FXObservableUtil;
import org.eclipse.gef.common.activate.IActivatable;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.utils.GMFPartUtil;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.services.AbstractProviderParticipant;
import org.eclipse.papyrus.gef4.services.HelperProviderParticipant;

/**
 * An abstract {@link HelperProviderParticipant} for GMF implementation of
 * {@link BaseContentPart}
 */
public abstract class AbstractGMFProviderParticipant<T> extends AbstractProviderParticipant<T> {

	private static final Predicate<BaseContentPart<? extends View, ?>> ANY_MATCHER = any -> true;

	private double priority;
	private Predicate<BaseContentPart<? extends View, ?>> matcher;

	public AbstractGMFProviderParticipant(double priority) {
		this(priority, ANY_MATCHER);
	}

	public AbstractGMFProviderParticipant(double priority,
			Class<? extends BaseContentPart<? extends View, ?>> forClass) {
		this(priority, forClass::isInstance);
	}

	@SafeVarargs
	public AbstractGMFProviderParticipant(double priority, Class<? extends BaseContentPart<? extends View, ?>> forClass,
			Class<? extends BaseContentPart<? extends View, ?>>... forClasses) {
		this(priority, instance -> Stream.concat(Stream.of(forClass), Arrays.stream(forClasses))
				.anyMatch(c -> c.isInstance(instance)));
	}

	public AbstractGMFProviderParticipant(double priority, Predicate<BaseContentPart<? extends View, ?>> matcher) {
		this.priority = priority;
		this.matcher = matcher;
	}

	@Override
	public double getPriority(IVisualPart<?> part) {
		return GMFPartUtil.isBaseNotationPart(part) && matcher.test(GMFPartUtil.getBasePart(part)) //
				? priority //
				: Double.NaN;
	}

	@Override
	protected T createInstance(IVisualPart<?> part) {
		T instance = doCreateInstance(GMFPartUtil.getBasePart(part));
		if (instance instanceof IActivatable) {
			IActivatable activatable = (IActivatable) instance;
			FXObservableUtil.onChange(part.activeProperty(), isActive -> {
				if (isActive) {
					activatable.activate();
				} else {
					activatable.deactivate();
				}
			});
		}
		return instance;
	}

	protected abstract T doCreateInstance(BaseContentPart<? extends View, ?> basePart);
}
