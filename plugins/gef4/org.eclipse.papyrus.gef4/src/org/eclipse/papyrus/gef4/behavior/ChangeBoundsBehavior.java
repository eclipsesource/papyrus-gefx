/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.gef4.behavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.behaviors.AbstractBehavior;
import org.eclipse.gef.mvc.fx.parts.IFeedbackPart;
import org.eclipse.gef.mvc.fx.parts.IFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.papyrus.gef4.feedback.BoundsFeedbackPart;
import org.eclipse.papyrus.gef4.model.ChangeBoundsModel;

import javafx.collections.MapChangeListener;
import javafx.scene.Node;

public class ChangeBoundsBehavior extends AbstractBehavior implements MapChangeListener<IVisualPart<? extends Node>, Rectangle> {

	public static final String BOUNDS_ROLE = "bounds";

	protected final Map<IVisualPart<? extends Node>, IFeedbackPart<? extends Node>> currentFeedbackParts = new HashMap<>();

	@Override
	public void doActivate() {
		super.doActivate();

		ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);

		// register
		boundsModel.getManagedElements().addListener(this);

		// create feedback and handles if we are already selected
		updateFeedback(Collections.emptyMap(), boundsModel.getManagedElements());
	}

	protected List<IVisualPart<? extends Node>> extractTargets(Map<IVisualPart<? extends Node>, Rectangle> managedElements) {
		return new ArrayList<>(managedElements.keySet());
	}

	@Override
	public void doDeactivate() {
		ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);
		removeFeedback(extractTargets(boundsModel.getManagedElements()));

		boundsModel.getManagedElements().removeListener(this);

		super.doDeactivate();
	}

	@Override
	public void onChanged(MapChangeListener.Change<? extends IVisualPart<? extends Node>, ? extends Rectangle> change) {
		IVisualPart<? extends Node> modifiedPart = change.getKey();
		if (modifiedPart == getHost()) {
			IFeedbackPartFactory feedbackPartFactory = getFeedbackPartFactory(modifiedPart.getViewer());
			if (change.getValueAdded() != null) { // Addition or Update
				IFeedbackPart<? extends Node> feedback = currentFeedbackParts.get(modifiedPart);
				if (feedback == null) {
					List<IVisualPart<? extends Node>> targets = Collections.singletonList(modifiedPart);
					List<IFeedbackPart<? extends Node>> newFeedbackParts = feedbackPartFactory.createFeedbackParts(targets, new HashMap<Object, Object>(change.getMap()));
					addFeedback(Collections.singletonList(modifiedPart), newFeedbackParts);
				} else {
					updateFeedback(feedback, change.getValueAdded());
				}
			} else { // Removal
				removeFeedback(Collections.singletonList(modifiedPart));
			}
		}
	}

	protected void updateFeedback(IFeedbackPart<? extends Node> feedbackPart, Rectangle bounds) {
		if (feedbackPart instanceof BoundsFeedbackPart) {
			((BoundsFeedbackPart) feedbackPart).updateBounds(bounds);
		}
	}

	private void updateFeedback(Map<IVisualPart<? extends Node>, Rectangle> oldSelection, Map<IVisualPart<? extends Node>, Rectangle> newSelection) {
		List<IVisualPart<? extends Node>> partsToRemove = new LinkedList<>();
		List<IVisualPart<? extends Node>> partsToUpdate = new LinkedList<>();
		List<IVisualPart<? extends Node>> partsToCreate = new LinkedList<>();

		IVisualPart<? extends Node> host = getHost();

		if (oldSelection.containsKey(host)) {
			if (newSelection.containsKey(host)) {
				partsToUpdate.add(host);
			} else {
				partsToRemove.add(host);
			}
		} else if (newSelection.containsKey(host)) {
			partsToCreate.add(host);
		}

		removeFeedback(partsToRemove);

		IFeedbackPartFactory feedbackPartFactory = getFeedbackPartFactory(host.getViewer());

		Map<Object, Object> contextMap = new HashMap<>(newSelection);

		List<IFeedbackPart<? extends Node>> newFeedbackParts = feedbackPartFactory.createFeedbackParts(partsToCreate, contextMap);
		addFeedback(partsToCreate, newFeedbackParts);

		for (IVisualPart<? extends Node> partToUpdate : partsToUpdate) {
			IFeedbackPart<? extends Node> feedback = currentFeedbackParts.get(partToUpdate);
			if (feedback instanceof BoundsFeedbackPart) {
				((BoundsFeedbackPart) feedback).updateBounds(newSelection.get(partToUpdate));
			}
		}
	}

	protected void addFeedback(List<? extends IVisualPart<? extends Node>> targets, List<IFeedbackPart<? extends Node>> feedbackParts) {
		if (feedbackParts != null) {
			for (IFeedbackPart<? extends Node> f : feedbackParts) {
				if (f instanceof BoundsFeedbackPart) {
					currentFeedbackParts.put(((BoundsFeedbackPart) f).getHost(), f);
				}
			}
		}
		getFeedbackPerTargetSet().put(new HashSet<>(targets), feedbackParts);
		super.addAnchoreds(targets, feedbackParts);
	}

	protected void removeFeedback(List<? extends IVisualPart<? extends Node>> targets) {
		for (IVisualPart<? extends Node> target : targets) {
			IFeedbackPart<? extends Node> f = currentFeedbackParts.get(target);
			if (f == null) {
				// A different feedback has been installed (e.g. for connections). The ChangeBoundsBehavior should probably not be installed in this case
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
		return getFeedbackPartFactory(viewer, BOUNDS_ROLE);
	}
}
