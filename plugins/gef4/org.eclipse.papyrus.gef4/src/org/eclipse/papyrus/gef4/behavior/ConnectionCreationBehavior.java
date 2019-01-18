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
package org.eclipse.papyrus.gef4.behavior;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.fx.core.log.Logger;
import org.eclipse.fx.core.log.LoggerCreator;
import org.eclipse.gef.mvc.fx.behaviors.AbstractBehavior;
import org.eclipse.gef.mvc.fx.parts.IFeedbackPart;
import org.eclipse.gef.mvc.fx.parts.IFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.papyrus.gef4.feedback.Anchors;
import org.eclipse.papyrus.gef4.feedback.ConnectionCreationFeedbackPart;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

public class ConnectionCreationBehavior extends AbstractBehavior {

	public static final String CONNECTION_CREATION_ROLE = "connectionCreation";

	private static final Logger logger = LoggerCreator.createLogger(ConnectionCreationBehavior.class);

	private final Map<IVisualPart<?>, IFeedbackPart<?>> currentFeedbackParts = new HashMap<>();

	private final ObservableMap<IVisualPart<?>, Anchors> feedbackRequests = FXCollections.observableHashMap();

	private final MapChangeListener<IVisualPart<?>, Anchors> feedbackListener = this::creationModelChanged;

	@Override
	protected void doActivate() {
		feedbackRequests.addListener(feedbackListener);
		super.doActivate();
	}

	@Override
	public void doDeactivate() {
		feedbackRequests.keySet().forEach(this::deleteFeedback);
		feedbackRequests.removeListener(feedbackListener);
		super.doDeactivate();
	}

	public void addFeedback(IVisualPart<?> host, Anchors anchors) {
		feedbackRequests.put(host, anchors);
	}

	public void deleteFeedback(IVisualPart<?> host) {
		feedbackRequests.remove(host);
	}

	private void creationModelChanged(MapChangeListener.Change<? extends IVisualPart<?>, ? extends Anchors> change) {
		IVisualPart<?> modifiedPart = change.getKey();
		IFeedbackPartFactory feedbackPartFactory = getFeedbackPartFactory(modifiedPart.getViewer());
		if (feedbackPartFactory == null) {
			logger.debug("No feedback factory installed for " + CONNECTION_CREATION_ROLE);
			return;
		}
		if (change.getValueAdded() != null) { // Addition or Update
			IFeedbackPart<?> feedback = currentFeedbackParts.get(modifiedPart);
			if (feedback == null) {
				List<IVisualPart<?>> targets = Collections.singletonList(modifiedPart);
				List<IFeedbackPart<?>> newFeedbackParts = feedbackPartFactory.createFeedbackParts(targets, new HashMap<Object, Object>(change.getMap()));
				addFeedback(Collections.singletonList(modifiedPart), newFeedbackParts);
			} else {
				updateFeedback(feedback, change.getValueAdded());
			}
		} else { // Removal
			removeFeedback(Collections.singletonList(modifiedPart));
		}
	}

	protected void updateFeedback(IFeedbackPart<?> feedbackPart, Anchors anchors) {
		if (feedbackPart instanceof ConnectionCreationFeedbackPart) {
			((ConnectionCreationFeedbackPart) feedbackPart).updateAnchors(anchors);
		}
	}

	protected void addFeedback(List<IVisualPart<?>> targets, List<IFeedbackPart<?>> feedbackParts) {
		super.addAnchoreds(targets, feedbackParts);
		if (feedbackParts != null) {
			for (IFeedbackPart<?> f : feedbackParts) {
				if (f instanceof ConnectionCreationFeedbackPart) {
					currentFeedbackParts.put(((ConnectionCreationFeedbackPart) f).getAnchorage(), f);
				}
			}
		}
		getFeedbackPerTargetSet().put(new HashSet<>(targets), feedbackParts);
	}

	protected void removeFeedback(List<IVisualPart<?>> targets) {
		for (IVisualPart<?> target : targets) {
			IFeedbackPart<?> f = currentFeedbackParts.get(target);
			if (f == null) {
				// A different feedback has been installed. The CreationBehavior should probably not be installed in this case
				continue;
			}

			currentFeedbackParts.remove(target);
		}

		if (!targets.isEmpty()) {
			super.removeFeedback(targets);
		}
	}

	@Override
	protected IFeedbackPartFactory getFeedbackPartFactory(IViewer viewer) {
		return getFeedbackPartFactory(viewer, CONNECTION_CREATION_ROLE);
	}

}
