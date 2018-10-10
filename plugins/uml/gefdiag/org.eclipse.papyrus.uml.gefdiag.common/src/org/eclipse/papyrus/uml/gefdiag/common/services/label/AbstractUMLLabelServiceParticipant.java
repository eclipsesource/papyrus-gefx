/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
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
package org.eclipse.papyrus.uml.gefdiag.common.services.label;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.services.AbstractGMFProviderParticipant;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.services.LabelService;
import org.eclipse.uml2.uml.Element;

/**
 * <p>
 * A {@link LabelService} participant for UML Elements.
 * </p>
 *
 * <p>
 * The participant matcher is based on the specified UML Element Class and priority
 * </p>
 *
 * <p>
 * The label can be provided by the subclass by overriding either {@link #getLabel(BaseContentPart)} or {@link #getLabel(Element)},
 * (Using the UML Element is easier; using the base part may give more flexibility, by accessing the Notation Model and Part Policies)
 * </p>
 *
 * @param <T>
 *            The type of UML Element supported by this label provider participant
 */
public abstract class AbstractUMLLabelServiceParticipant<T extends Element> extends AbstractGMFProviderParticipant<LabelService> {

	public static final String UNNAMED = "<Unnamed>";
	public static final String UNDEFINED = "<Undefined>";

	private final Class<T> supportedType;

	/**
	 * Build a new participant for the given UML Type and the specified priority
	 *
	 * @param priority
	 * @param umlElement
	 */
	protected AbstractUMLLabelServiceParticipant(double priority, Class<T> umlElement) {
		super(priority, basePart -> umlElement.isInstance(basePart.getContent().getElement()));
		this.supportedType = umlElement;
	}

	protected final T getElement(BaseContentPart<? extends View, ?> basePart) {
		EObject semanticElement = basePart.getContent().getElement();
		if (supportedType.isInstance(semanticElement)) {
			return supportedType.cast(basePart.getContent().getElement());
		}
		return null;
	}

	@Override
	protected final LabelService doCreateInstance(BaseContentPart<? extends View, ?> basePart) {
		return () -> getLabel(basePart);
	}

	// protected Subscription installListeners(BaseContentPart<? extends View, ?> basePart) {
	// return null;
	// }

	/**
	 * <p>
	 * Return the label for this base part. The default implementation delegates
	 * to {@link #getLabel(Element)}.
	 * </p>
	 * <p>
	 * Override this method if you need full access to the content part (Notation View,
	 * Policies...). Otherwise, it is recommended to override {@link #getLabel(Element)}.
	 * </p>
	 *
	 * @param basePart
	 * @return
	 */
	protected String getLabel(BaseContentPart<? extends View, ?> basePart) {
		return getLabel(getElement(basePart));
	}

	/**
	 * Return the label for this UML Element
	 *
	 * @param element
	 * @return
	 */
	protected abstract String getLabel(T element);

}
