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
package org.eclipse.papyrus.gef4.provider;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.gef4.mvc.behaviors.IBehavior;
import org.eclipse.gef4.mvc.behaviors.SelectionBehavior;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultFeedbackPartFactory;
import org.eclipse.gef4.mvc.parts.IFeedbackPart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.papyrus.gef4.behavior.ChangeBoundsBehavior;
import org.eclipse.papyrus.gef4.feedback.BoundsFeedbackPart;
import org.eclipse.papyrus.gef4.parts.DiagramContentPart;

import com.google.inject.Inject;
import com.google.inject.Injector;

import javafx.scene.Node;

public class FeedbackPartFactory extends FXDefaultFeedbackPartFactory {

	@Inject
	private Injector injector;

	@Override
	protected List<IFeedbackPart<Node, ? extends Node>> createSelectionFeedbackParts(List<? extends IVisualPart<Node, ? extends Node>> targets, SelectionBehavior<Node> selectionBehavior, Map<Object, Object> contextMap) {
		if (targets.size() == 1 && targets.get(0) instanceof DiagramContentPart) {
			return Collections.emptyList();
		}
		return super.createSelectionFeedbackParts(targets, selectionBehavior, contextMap);
	}

	@Override
	public List<IFeedbackPart<Node, ? extends Node>> createFeedbackParts(List<? extends IVisualPart<Node, ? extends Node>> targets, IBehavior<Node> contextBehavior, Map<Object, Object> contextMap) {
		if (contextBehavior instanceof ChangeBoundsBehavior) {
			return createBoundsFeedback(targets,
					(ChangeBoundsBehavior) contextBehavior, contextMap);
		}

		return super.createFeedbackParts(targets, contextBehavior, contextMap);
	}

	protected List<IFeedbackPart<Node, ? extends Node>> createBoundsFeedback(List<? extends IVisualPart<Node, ? extends Node>> targets, ChangeBoundsBehavior contextBehavior, Map<Object, Object> contextMap) {
		List<IFeedbackPart<Node, ? extends Node>> result = new LinkedList<>();

		for (IVisualPart<Node, ? extends Node> target : targets) {
			Bounds newBounds = (Bounds) contextMap.get(target);
			BoundsFeedbackPart boundsFeedbackPart = new BoundsFeedbackPart(target, newBounds);
			injector.injectMembers(boundsFeedbackPart);
			result.add(boundsFeedbackPart);
		}

		return result;
	}
}
