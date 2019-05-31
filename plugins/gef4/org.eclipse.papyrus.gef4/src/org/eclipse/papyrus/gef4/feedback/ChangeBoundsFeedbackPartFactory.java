/*****************************************************************************
 * Copyright (c) 2018 EclipseSource.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  EclipseSource - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.feedback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.parts.IFeedbackPart;
import org.eclipse.gef.mvc.fx.parts.IFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import javax.inject.Inject;
import com.google.inject.Injector;

public class ChangeBoundsFeedbackPartFactory implements IFeedbackPartFactory {

	@Inject
	private Injector injector;

	@Override
	public List<IFeedbackPart<?>> createFeedbackParts(List<? extends IVisualPart<?>> targets, Map<Object, Object> contextMap) {
		assert targets.size() == 1 : "ChangeBounds Feedback must be installed separately on each part";

		List<IFeedbackPart<?>> result = new ArrayList<>(targets.size());
		for (IVisualPart<?> target : targets) {
			Rectangle newBounds = (Rectangle) contextMap.get(target);
			ChangeBoundsFeedbackPart boundsFeedbackPart = new ChangeBoundsFeedbackPart(newBounds);
			injector.injectMembers(boundsFeedbackPart);
			result.add(boundsFeedbackPart);
		}

		return result;
	}
}
