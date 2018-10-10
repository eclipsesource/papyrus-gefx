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

import org.eclipse.uml2.uml.MultiplicityElement;

public class MultiplicityHelper {

	public String getMultiplicity(MultiplicityElement element) {
		StringBuilder builder = new StringBuilder();

		int upper = element.getUpper();
		int lower = element.getLower();
		if (upper != 1 || lower != 1) {
			if (lower < 0 || upper == 0) {
				builder.append("[Invalid]");
			} else {
				builder.append('[');
				if (upper == lower) {
					builder.append(upper);
				} else if (lower == 0 && upper < 0) {
					builder.append('*');
				} else {
					builder.append(lower);
					builder.append("..");
					if (upper < 0) {
						builder.append('*');
					} else {
						builder.append(upper);
					}
				}

				builder.append(']');
			}
		}

		return builder.toString();
	}

}
