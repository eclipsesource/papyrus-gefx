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
package org.eclipse.papyrus.gef4.handlers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.parts.IPrimaryContentPart;
import org.eclipse.papyrus.gef4.parts.NotationContentPart;

import javafx.scene.Node;

public class MarqueeOnDragHandler extends org.eclipse.gef.mvc.fx.handlers.MarqueeOnDragHandler {
	@Override
	protected List<IContentPart<? extends Node>> getParts(List nodes) {
		List<IContentPart<? extends Node>> allParts = super.getParts(nodes);

		List<IContentPart<? extends Node>> result = new LinkedList<IContentPart<? extends Node>>();

		for (IContentPart<? extends Node> part : allParts) {
			if (part instanceof IPrimaryContentPart) {
				result.add(part);
			}
		}

		Iterator<IContentPart<? extends Node>> iterator = result.iterator();
		while (iterator.hasNext()) {
			IContentPart<? extends Node> part = iterator.next();
			if (result.contains(getPrimary(part.getParent()))) {
				iterator.remove();
			}
		}

		if (result.size() > 1) {
			result.remove(getHost().getRoot().getChildrenUnmodifiable().get(0)); // Remove the diagram edit part, if it is selected and is not the only selected element
		}

		return result;
	}

	private IContentPart<? extends Node> getPrimary(IVisualPart<? extends Node> parent) {
		if (parent instanceof NotationContentPart) {
			return ((NotationContentPart<?, ?>) parent).getPrimaryContentPart();
		}
		if (parent instanceof IContentPart) {
			return (IContentPart<? extends Node>) parent;
		}
		return null;
	}


}
