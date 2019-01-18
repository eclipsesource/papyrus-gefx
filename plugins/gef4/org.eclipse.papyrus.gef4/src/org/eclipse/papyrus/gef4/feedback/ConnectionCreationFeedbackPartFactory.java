/*****************************************************************************
 * Copyright (c) 2019 EclipseSource
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  EclipseSource - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.gef4.feedback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IFeedbackPart;
import org.eclipse.gef.mvc.fx.parts.IFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class ConnectionCreationFeedbackPartFactory implements IFeedbackPartFactory {

	@Inject
	private Injector injector;

	@Override
	public List<IFeedbackPart<?>> createFeedbackParts(List<? extends IVisualPart<?>> targets, Map<Object, Object> contextMap) {
		assert targets.size() == 1 : "Connection Creation Feedback must be installed separately on each part";

		List<IFeedbackPart<?>> result = new ArrayList<>(targets.size());
		for (IVisualPart<?> target : targets) {
			assert target instanceof IContentPart : "ConnectionCreationFeedback can only be installed on IContentParts";
			Anchors creationAnchors = (Anchors) contextMap.get(target);
			final IFeedbackPart<?> feedbackPart;

			feedbackPart = new ConnectionFeedbackPart(creationAnchors);
			if (feedbackPart != null) {
				injector.injectMembers(feedbackPart);
				result.add(feedbackPart);
			}
		}

		return result;
	}

}
