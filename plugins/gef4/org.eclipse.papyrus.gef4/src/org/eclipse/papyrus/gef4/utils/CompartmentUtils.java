/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.utils;

import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;

import javafx.scene.Node;

/**
 * List of utility methods related to GMF Compartments
 *
 * @author Camille Letavernier
 *
 */
public class CompartmentUtils {

	/**
	 * Returns the nearest ContentPart representing a CollapsablePart {@link DrawerStyle}
	 *
	 * Since DecorationNodes are sometimes used as Compartments,
	 *
	 * @param currentPart
	 * @return
	 */
	public static IVisualPart<Node, ? extends Node> getCollapsablePart(IVisualPart<Node, ? extends Node> currentPart) {
		IVisualPart<Node, ? extends Node> part = currentPart;
		while (part != null) {
			View notationModel = NotationHelper.findView(currentPart);
			if (notationModel == null) {
				part = part.getParent();
				continue;
			}

			if (notationModel.getStyle(NotationPackage.eINSTANCE.getDrawerStyle()) != null) {
				return part;
			}

			part = part.getParent();
		}

		return null;
	}
}
