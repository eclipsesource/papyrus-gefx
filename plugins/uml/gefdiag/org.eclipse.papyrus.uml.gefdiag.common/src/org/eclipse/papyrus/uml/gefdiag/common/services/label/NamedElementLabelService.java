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

import org.eclipse.uml2.uml.NamedElement;

public class NamedElementLabelService extends AbstractUMLLabelServiceParticipant<NamedElement> {

	public NamedElementLabelService(double priority) {
		super(priority, NamedElement.class);
	}

	@Override
	protected String getLabel(NamedElement element) {
		// Note: for generic named elements, always return the name as-is.
		// Subclasses may decided to use <Unnamed> instead of null; but that should
		// be decided separately for each element. Using <Unnamed> is not a good default choice.
		return element.getName();
	}

}
