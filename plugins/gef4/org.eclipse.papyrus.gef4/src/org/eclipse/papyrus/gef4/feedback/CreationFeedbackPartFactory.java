/*****************************************************************************
 * Copyright (c) 2019 EclipseSource.
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

import org.eclipse.fx.core.log.Logger;
import org.eclipse.fx.core.log.LoggerCreator;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IFeedbackPart;
import org.eclipse.gef.mvc.fx.parts.IFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.parts.DiagramContentPart;
import org.eclipse.papyrus.gef4.parts.ListCompartmentContentPart;
import org.eclipse.papyrus.gef4.parts.XYCompartmentContentPart;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * <p>
 * Default implementation of the IFeedbackPartFactory for nodes creation feedback.
 * While this implementation aims at providing generic defaults, it should probably
 * be overridden for diagram-specific feedbacks.
 * </p>
 */
public class CreationFeedbackPartFactory implements IFeedbackPartFactory {

	private static final Logger logger = LoggerCreator.createLogger(CreationFeedbackPartFactory.class);

	@Inject
	private Injector injector;

	@Override
	public List<IFeedbackPart<?>> createFeedbackParts(List<? extends IVisualPart<?>> targets, Map<Object, Object> contextMap) {
		assert targets.size() == 1 : "Creation Feedback must be installed separately on each part";

		List<IFeedbackPart<?>> result = new ArrayList<>(targets.size());
		for (IVisualPart<?> target : targets) {
			assert target instanceof IContentPart || target instanceof IRootPart : "CreationFeedback can only be installed on IContentParts or IRootPart";
			Rectangle creationBounds = (Rectangle) contextMap.get(target);
			final IFeedbackPart<?> feedbackPart;
			if (target instanceof XYCompartmentContentPart || target instanceof IRootPart || target instanceof DiagramContentPart) {
				feedbackPart = new NodeCreationFeedbackPart(creationBounds);
			} else if (target instanceof ListCompartmentContentPart) {
				feedbackPart = new ListItemCreationFeedbackPart(creationBounds);
			} else {
				feedbackPart = null;
				logger.warning("Unsupported creation feedback: " + target.getClass().getName());
			}

			if (feedbackPart != null) {
				injector.injectMembers(feedbackPart);
				result.add(feedbackPart);
			}
		}

		return result;
	}

}
