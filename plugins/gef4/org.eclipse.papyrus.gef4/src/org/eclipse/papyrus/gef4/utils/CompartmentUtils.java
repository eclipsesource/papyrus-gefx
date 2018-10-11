/*****************************************************************************
 * Copyright (c) 2015 - 2018 CEA LIST, EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  EclipseSource
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.utils;

import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.parts.CompartmentContentPart;

import javafx.scene.Node;

/**
 * List of utility methods related to Compartments
 *
 */
public class CompartmentUtils {

	/**
	 * Returns the nearest ContentPart representing a Collapsable Part
	 *
	 * @param currentPart
	 * @return
	 */
	public static IVisualPart<? extends Node> getCollapsablePart(IVisualPart<? extends Node> currentPart) {
		IVisualPart<? extends Node> part = currentPart;
		while (part != null) {
			if (part instanceof CompartmentContentPart) {
				return part;
			}

			part = part.getParent();
		}

		return null;
	}
}
