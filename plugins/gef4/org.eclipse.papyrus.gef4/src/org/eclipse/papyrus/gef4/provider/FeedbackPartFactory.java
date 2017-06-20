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

import java.util.List;
import java.util.Map;

import org.eclipse.gef.mvc.fx.parts.DefaultSelectionFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.IFeedbackPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.google.inject.Inject;
import com.google.inject.Injector;

import javafx.scene.Node;

public class FeedbackPartFactory extends DefaultSelectionFeedbackPartFactory {

	@Inject
	private Injector injector;

	@Override
	public List<IFeedbackPart<? extends Node>> createFeedbackParts(List<? extends IVisualPart<? extends Node>> targets, Map<Object, Object> contextMap) {
//		if (contextBehavior instanceof ChangeBoundsBehavior) {
//			return createBoundsFeedback(targets,
//					(ChangeBoundsBehavior) contextBehavior, contextMap);
//		}
//
//		if (contextBehavior instanceof SelectionBehavior) {
//			if (targets.size() == 1 && targets.get(0) instanceof DiagramContentPart) {
//				return Collections.emptyList();
//			}
//			return super.createFeedbackParts(targets, contextBehavior, contextMap);
//		}

		
		return super.createFeedbackParts(targets, contextMap);
		//return Collections.emptyList();
	}

//	protected List<IFeedbackPart<? extends Node>> createBoundsFeedback(List<? extends IVisualPart<? extends Node>> targets, ChangeBoundsBehavior contextBehavior, Map<Object, Object> contextMap) {
//		List<IFeedbackPart<? extends Node>> result = new LinkedList<>();
//
//		for (IVisualPart<? extends Node> target : targets) {
//			Bounds newBounds = (Bounds) contextMap.get(target);
//			BoundsFeedbackPart boundsFeedbackPart = new BoundsFeedbackPart(target, newBounds);
//			injector.injectMembers(boundsFeedbackPart);
//			result.add(boundsFeedbackPart);
//		}
//
//		return result;
//	}
}
