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

import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.behaviors.AbstractBehavior;
import org.eclipse.gef.mvc.fx.parts.IFeedbackPart;
import org.eclipse.gef.mvc.fx.parts.IFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.papyrus.gef4.feedback.AbstractCreationFeedbackPart;
import org.eclipse.papyrus.gef4.feedback.CreationFeedbackPart;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.Node;

public class CreationBehavior extends AbstractBehavior {

	public static final String CREATION_ROLE = "elementCreation";

	private final Map<IVisualPart<?>, IFeedbackPart<?>> currentFeedbackParts = new HashMap<>();

	private final ObservableMap<IVisualPart<?>, Rectangle> feedbackRequests = FXCollections.observableHashMap();

	private final MapChangeListener<IVisualPart<?>, Rectangle> feedbackListener = this::creationModelChanged;

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

	public void addFeedback(IVisualPart<?> host, Rectangle creationBounds) {
		feedbackRequests.put(host, creationBounds);
	}

	public void deleteFeedback(IVisualPart<? extends Node> host) {
		feedbackRequests.remove(host);
	}

	private void creationModelChanged(MapChangeListener.Change<? extends IVisualPart<?>, ? extends Rectangle> change) {
		IVisualPart<?> modifiedPart = change.getKey();
		IFeedbackPartFactory feedbackPartFactory = getFeedbackPartFactory(modifiedPart.getViewer());
		if (feedbackPartFactory == null) {
			return;
		}
		if (change.getValueAdded() != null) { // Addition or Update
			IFeedbackPart<? extends Node> feedback = currentFeedbackParts.get(modifiedPart);
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

	protected void updateFeedback(IFeedbackPart<?> feedbackPart, Rectangle creationBounds) {
		if (feedbackPart instanceof AbstractCreationFeedbackPart) {
			((AbstractCreationFeedbackPart<?>) feedbackPart).updateBounds(creationBounds);
		}
	}

	protected void addFeedback(List<IVisualPart<?>> targets, List<IFeedbackPart<?>> feedbackParts) {
		super.addAnchoreds(targets, feedbackParts);
		if (feedbackParts != null) {
			for (IFeedbackPart<?> f : feedbackParts) {
				if (f instanceof CreationFeedbackPart) {
					currentFeedbackParts.put(((CreationFeedbackPart) f).getAnchorage(), f);
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
		return getFeedbackPartFactory(viewer, CREATION_ROLE);
	}

}
