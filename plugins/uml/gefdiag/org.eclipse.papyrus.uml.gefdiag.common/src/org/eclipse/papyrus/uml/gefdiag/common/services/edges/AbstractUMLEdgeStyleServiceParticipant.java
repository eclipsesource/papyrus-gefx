/*****************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.gefdiag.common.services.edges;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.services.AbstractGMFProviderParticipant;
import org.eclipse.papyrus.gef4.gmf.style.ConnectorStyleProvider;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.services.style.EdgeStyleService;
import org.eclipse.uml2.uml.Element;

import javafx.scene.paint.Color;

public abstract class AbstractUMLEdgeStyleServiceParticipant<T extends Element> extends AbstractGMFProviderParticipant<EdgeStyleService> {

	private Class<T> supportedType;
	private boolean delegateToGMF;

	/**
	 * Build a new participant for the given UML Type and the specified priority
	 *
	 * @param priority
	 * @param umlElement
	 * @param delegateToGMF
	 *            When this participant doesn't return a value, this boolean indicates if we
	 *            should delegate to the default GMF implementation of the {@link EdgeStyleService}.
	 *            Set this to true when you want to hard-code some cases, but still want to delegate
	 *            the other cases to GMF Notation or CSS. Set this to false when you want this participant
	 *            to have full control over the style.
	 */
	protected AbstractUMLEdgeStyleServiceParticipant(double priority, Class<T> umlElement, boolean delegateToGMF) {
		super(priority, basePart -> umlElement.isInstance(basePart.getContent().getElement()));
		this.supportedType = umlElement;
		this.delegateToGMF = delegateToGMF;
	}

	protected final T getElement(BaseContentPart<? extends View, ?> basePart) {
		EObject semanticElement = basePart.getContent().getElement();
		if (supportedType.isInstance(semanticElement)) {
			return supportedType.cast(basePart.getContent().getElement());
		}
		return null;
	}

	@Override
	protected final EdgeStyleService doCreateInstance(BaseContentPart<? extends View, ?> basePart) {
		return new ConnectorStyleProvider(basePart) {

			@Override
			public List<Double> getDashStyle() {
				// TODO
				return super.getDashStyle();
			}

			@Override
			public Color getLineColor() {
				// TODO
				return super.getLineColor();
			}

			@Override
			public int getLineWidth() {
				// TODO
				return super.getLineWidth();
			}

			@Override
			public String getSourceDecoration() {
				String value = AbstractUMLEdgeStyleServiceParticipant.this.getSourceDecoration(basePart);
				return value == null && delegateToGMF ? super.getSourceDecoration() : value;
			}

			@Override
			public String getTargetDecoration() {
				String value = AbstractUMLEdgeStyleServiceParticipant.this.getTargetDecoration(basePart);
				return value == null && delegateToGMF ? super.getTargetDecoration() : value;
			}
		};
	}

	protected String getTargetDecoration(BaseContentPart<? extends View, ?> basePart) {
		return getTargetDecoration(getElement(basePart));
	}


	protected String getSourceDecoration(BaseContentPart<? extends View, ?> basePart) {
		return getSourceDecoration(getElement(basePart));
	}

	protected abstract String getSourceDecoration(T element);

	protected abstract String getTargetDecoration(T element);
}
