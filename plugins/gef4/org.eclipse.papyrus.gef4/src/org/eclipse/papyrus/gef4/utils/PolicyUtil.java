/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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
import org.eclipse.gef4.mvc.policies.IPolicy;
import org.eclipse.gef4.mvc.viewer.IViewer;
import org.eclipse.papyrus.gef4.parts.NotationContentPart;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class PolicyUtil {

	/**
	 * Return the nearest IPrimaryPart for this mouse event, 
	 * or null if the target is not owned by an IPrimaryPart
	 * 
	 * @param event
	 * @return
	 */
	public static NotationContentPart<?, ?> getTargetPrimaryPart(IPolicy<Node> policy, MouseEvent event) {
		IViewer<Node> viewer = policy.getHost().getRoot().getViewer();
		IVisualPart<Node, ? extends Node> targetPart = viewer.getVisualPartMap().get(event.getTarget());
		if (targetPart instanceof NotationContentPart) {
			NotationContentPart<?, ?> targetNotationPart = (NotationContentPart<?, ?>) targetPart;
			return targetNotationPart.getPrimaryContentPart();
		}
		return null;
	}
}
