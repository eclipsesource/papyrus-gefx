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

import org.eclipse.gef.mvc.fx.handlers.IHandler;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class HandlerUtil {

	/**
	 * Return the nearest IPrimaryPart for this mouse event,
	 * or null if the target is not owned by an IPrimaryPart
	 *
	 * @param event
	 * @return
	 */
	public static BaseContentPart<?, ?> getTargetPrimaryPart(IHandler handler, MouseEvent event) {
		IViewer viewer = handler.getHost().getRoot().getViewer();
		IVisualPart<? extends Node> targetPart = viewer.getVisualPartMap().get(event.getTarget());
		if (targetPart instanceof BaseContentPart) {
			BaseContentPart<?, ?> targetNotationPart = (BaseContentPart<?, ?>) targetPart;
			return targetNotationPart.getPrimaryContentPart();
		}
		return null;
	}
}
