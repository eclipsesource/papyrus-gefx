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
package org.eclipse.papyrus.infra.gefdiag.common.provider;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.gef4.mvc.fx.parts.FXDefaultHandlePartFactory;
import org.eclipse.gef4.mvc.parts.IHandlePart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.papyrus.infra.gefdiag.common.part.DiagramContentPart;

import javafx.scene.Node;

public class HandlePartFactory extends FXDefaultHandlePartFactory {
	@Override
	protected List<IHandlePart<Node, ? extends Node>> createMultiSelectionHandleParts(List<? extends IVisualPart<Node, ? extends Node>> targets, Map<Object, Object> contextMap) {
		List<IHandlePart<Node, ? extends Node>> allHandles = new LinkedList<>();
		for (IVisualPart<Node, ? extends Node> target : targets) {
			allHandles.addAll(createSingleSelectionHandleParts(target, contextMap));
		}
		return allHandles;
	}

	@Override
	protected List<IHandlePart<Node, ? extends Node>> createSingleSelectionHandleParts(IVisualPart<Node, ? extends Node> target, Map<Object, Object> contextMap) {
		if (target instanceof DiagramContentPart) {
			return Collections.emptyList();
		}
		return super.createSingleSelectionHandleParts(target, contextMap);
	}
}
