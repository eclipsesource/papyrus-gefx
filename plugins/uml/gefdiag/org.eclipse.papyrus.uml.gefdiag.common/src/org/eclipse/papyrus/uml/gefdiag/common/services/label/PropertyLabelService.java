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

import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

public class PropertyLabelService extends AbstractUMLLabelServiceParticipant<Property> {

	protected MultiplicityHelper multiplicityHelper = new MultiplicityHelper();

	public PropertyLabelService(double priority) {
		super(priority, Property.class);
	}

	@Override
	protected String getLabel(Property element) {
		StringBuilder builder = new StringBuilder();

		boolean typeOrName = false;
		if (element.getName() != null && !element.getName().isEmpty()) {
			builder.append(element.getName());
			typeOrName = true;
		}
		Type type = element.getType();
		if (type != null) {
			builder.append(": ");
			if (type.getName() == null || type.getName().isEmpty()) {
				builder.append(UNNAMED);
			} else {
				builder.append(type.getName());
			}
			typeOrName = true;
		}

		if (!typeOrName) {
			builder.append(UNNAMED);
		}

		builder.append(' ').append(multiplicityHelper.getMultiplicity(element));

		return builder.toString().trim();
	}


}
