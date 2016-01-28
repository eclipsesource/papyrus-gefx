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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.gef4.mvc.behaviors.AbstractBehavior;
import org.eclipse.gef4.mvc.parts.IFeedbackPart;
import org.eclipse.gef4.mvc.parts.IFeedbackPartFactory;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.papyrus.gef4.feedback.BoundsFeedbackPart;
import org.eclipse.papyrus.gef4.model.ChangeBoundsModel;

import com.google.inject.Inject;

import javafx.collections.MapChangeListener;
import javafx.scene.Node;

public class ChangeBoundsBehavior extends AbstractBehavior<Node> implements MapChangeListener<IVisualPart<Node, ? extends Node>, Bounds> {

	protected final Map<IVisualPart<Node, ? extends Node>, IFeedbackPart<Node, ? extends Node>> currentFeedbackParts = new HashMap<>();

	@Inject
	IFeedbackPartFactory<Node> feedbackPartFactory;

	@Override
	public void doActivate() {
		super.doActivate();

		ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);

		// register
		boundsModel.getManagedElements().addListener(this);

		// create feedback and handles if we are already selected
		updateFeedback(Collections.emptyMap(), boundsModel.getManagedElements());
	}

	protected List<IVisualPart<Node, ? extends Node>> extractTargets(Map<IVisualPart<Node, ? extends Node>, Bounds> managedElements) {
		return new ArrayList<IVisualPart<Node, ? extends Node>>(managedElements.keySet());
	}

	@Override
	public void doDeactivate() {
		ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);
		removeFeedback(extractTargets(boundsModel.getManagedElements()));

		boundsModel.getManagedElements().removeListener(this);

		super.doDeactivate();
	}

	@Override
	public void onChanged(MapChangeListener.Change<? extends IVisualPart<Node, ? extends Node>, ? extends Bounds> change) {

		IVisualPart<Node, ? extends Node> modifiedPart = change.getKey();
		if (modifiedPart == getHost()) {
			if (change.getValueAdded() != null) { // Addition or Update
				IFeedbackPart<Node, ? extends Node> feedback = currentFeedbackParts.get(modifiedPart);
				if (feedback == null) {
					List<IVisualPart<Node, ? extends Node>> targets = Collections.singletonList(modifiedPart);
					List<IFeedbackPart<Node, ? extends Node>> newFeedbackParts = feedbackPartFactory.createFeedbackParts(targets, this, new HashMap<Object, Object>(change.getMap()));
					addFeedback(Collections.singletonList(modifiedPart), newFeedbackParts);
				} else {
					updateFeedback(feedback, change.getValueAdded());
				}
			} else { // Removal
				removeFeedback(Collections.singletonList(modifiedPart));
			}
		}
	}

	protected void updateFeedback(IFeedbackPart<Node, ? extends Node> feedbackPart, Bounds bounds) {
		if (feedbackPart instanceof BoundsFeedbackPart) {
			((BoundsFeedbackPart) feedbackPart).updateBounds(bounds);
		}
	}

	private void updateFeedback(Map<IVisualPart<Node, ? extends Node>, Bounds> oldSelection, Map<IVisualPart<Node, ? extends Node>, Bounds> newSelection) {
		List<IVisualPart<Node, ? extends Node>> partsToRemove = new LinkedList<>();
		List<IVisualPart<Node, ? extends Node>> partsToUpdate = new LinkedList<>();
		List<IVisualPart<Node, ? extends Node>> partsToCreate = new LinkedList<>();

		IVisualPart<Node, ? extends Node> host = getHost();

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

		Map<Object, Object> contextMap = new HashMap<>(newSelection);

		List<IFeedbackPart<Node, ? extends Node>> newFeedbackParts = feedbackPartFactory.createFeedbackParts(partsToCreate, this, contextMap);
		addFeedback(partsToCreate, newFeedbackParts);

		for (IVisualPart<Node, ? extends Node> partToUpdate : partsToUpdate) {
			IFeedbackPart<Node, ? extends Node> feedback = currentFeedbackParts.get(partToUpdate);
			if (feedback instanceof BoundsFeedbackPart) {
				((BoundsFeedbackPart) feedback).updateBounds(newSelection.get(partToUpdate));
			}
		}
	}

	@Override
	protected void addFeedback(List<? extends IVisualPart<Node, ? extends Node>> targets, List<? extends IFeedbackPart<Node, ? extends Node>> feedbackParts) {
		super.addFeedback(targets, feedbackParts);
		if (feedbackParts != null) {
			for (IFeedbackPart<Node, ? extends Node> f : feedbackParts) {
				if (f instanceof BoundsFeedbackPart) {
					currentFeedbackParts.put(((BoundsFeedbackPart) f).getHost(), f);
				}
			}
		}
	}

	@Override
	protected void removeFeedback(List<? extends IVisualPart<Node, ? extends Node>> targets) {
		super.removeFeedback(targets);

		for (IVisualPart<Node, ? extends Node> target : targets) {
			IFeedbackPart<Node, ? extends Node> f = currentFeedbackParts.get(target);
			if (f == null) {
				// A different feedback has been installed (e.g. for connections). The ChangeBoundsBehavior should probably not be installed in this case
				continue;
			}

			currentFeedbackParts.remove(target);
		}
	}
}
