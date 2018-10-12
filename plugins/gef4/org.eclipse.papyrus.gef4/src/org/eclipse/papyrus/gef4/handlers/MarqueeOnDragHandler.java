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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.parts.AffixedLabelContentPart;
import org.eclipse.papyrus.gef4.parts.DiagramContentPart;
import org.eclipse.papyrus.gef4.parts.IPrimaryContentPart;

import javafx.scene.Node;

public class MarqueeOnDragHandler extends org.eclipse.gef.mvc.fx.handlers.MarqueeOnDragHandler {
	@Override
	protected List<IContentPart<? extends Node>> getParts(List<Node> nodes) {
		List<IContentPart<?>> allSelectedParts = super.getParts(nodes);

		// Filter the results: only keep selectable parts
		List<IContentPart<?>> allSelectableParts = new ArrayList<>();

		for (IContentPart<?> part : allSelectedParts) {
			if (part instanceof DiagramContentPart) {
				continue;
			}

			if (part instanceof IPrimaryContentPart || part instanceof AffixedLabelContentPart) {
				allSelectableParts.add(part);
			}
		}

		// Filter the results again: only keep the top-most part when several parts belong to the same hierarchy
		List<IContentPart<?>> result = allSelectableParts.stream().filter(part -> allSelectableParts.stream().noneMatch(otherPart -> isParent(otherPart, part))).collect(Collectors.toList());

		return result;
	}

	private boolean isParent(IVisualPart<?> parentPart, IVisualPart<?> childPart) {
		if (parentPart == childPart) {
			return false;
		}
		IVisualPart<?> parent = childPart.getParent();
		while (parent != null) {
			if (parent == parentPart) {
				return true;
			}
			parent = parent.getParent();
		}
		return false;
	}

}
