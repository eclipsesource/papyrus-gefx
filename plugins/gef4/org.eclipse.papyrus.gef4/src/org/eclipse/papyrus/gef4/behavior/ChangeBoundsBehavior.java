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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.gef4.mvc.behaviors.AbstractBehavior;
import org.eclipse.gef4.mvc.parts.IFeedbackPart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.papyrus.gef4.feedback.BoundsFeedbackPart;
import org.eclipse.papyrus.gef4.model.ChangeBoundsModel;

import javafx.scene.Node;

public class ChangeBoundsBehavior extends AbstractBehavior<Node>implements PropertyChangeListener {

	protected final Map<IVisualPart<Node, ? extends Node>, IFeedbackPart<Node, ? extends Node>> currentFeedbackParts = new HashMap<>();

	@Override
	public void activate() {
		super.activate();

		ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);

		// register
		boundsModel.addPropertyChangeListener(this);

		// create feedback and handles if we are already selected
		updateFeedback(Collections.emptyMap(), boundsModel.getManagedElements());
	}

	protected List<IVisualPart<Node, ? extends Node>> extractTargets(Map<IVisualPart<Node, ? extends Node>, Bounds> managedElements) {
		return new ArrayList<IVisualPart<Node, ? extends Node>>(managedElements.keySet());
	}

	@Override
	public void deactivate() {
		ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);
		removeFeedback(extractTargets(boundsModel.getManagedElements()));

		boundsModel.removePropertyChangeListener(this);

		super.deactivate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals(ChangeBoundsModel.CHANGE_BOUNDS_PROPERTY)) {
			Map<IVisualPart<Node, ? extends Node>, Bounds> oldSelection = (Map<IVisualPart<Node, ? extends Node>, Bounds>) event.getOldValue();
			Map<IVisualPart<Node, ? extends Node>, Bounds> newSelection = (Map<IVisualPart<Node, ? extends Node>, Bounds>) event.getNewValue();

			updateFeedback(oldSelection, newSelection);
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

		addFeedback(partsToCreate, contextMap);

		for (IVisualPart<Node, ? extends Node> partToUpdate : partsToUpdate) {
			IFeedbackPart<Node, ? extends Node> feedback = currentFeedbackParts.get(partToUpdate);
			if (feedback instanceof BoundsFeedbackPart) {
				((BoundsFeedbackPart) feedback).updateBounds(newSelection.get(partToUpdate));
			}
		}
	}

	@Override
	protected void addFeedback(List<? extends IVisualPart<Node, ? extends Node>> targets, Map<Object, Object> contextMap) {
		super.addFeedback(targets, contextMap);
		List<IFeedbackPart<Node, ? extends Node>> feedbackParts = getFeedbackParts();
		if (feedbackParts != null) {
			for (IFeedbackPart<Node, ? extends Node> f : feedbackParts) {
				if (f instanceof BoundsFeedbackPart) {
					currentFeedbackParts.put(((BoundsFeedbackPart) f).getHost(), f);
				}
			}
		}
	}

	// protected void addFeedback(Map<IVisualPart<Node, ? extends Node>, Bounds> newSelection) {
	// ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);
	// Map<Object, Object> contextMap = new HashMap<>(boundsModel.getManagedElements());
	// addFeedback(extractTargets(boundsModel.getManagedElements()), contextMap);
	// }
	//
	// protected void removeFeedback(Map<IVisualPart<Node, ? extends Node>, Bounds> oldSelection) {
	// ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);
	// removeFeedback(extractTargets(boundsModel.getManagedElements()));
	// }

	@Override
	protected void removeFeedback(List<? extends IVisualPart<Node, ? extends Node>> targets) {
		super.removeFeedback(targets);

		for (IVisualPart<Node, ? extends Node> target : targets) {
			IFeedbackPart<Node, ? extends Node> f = currentFeedbackParts.get(target);

			getHost().getRoot().getViewer().getVisualPartMap().remove(f.getVisual());

			currentFeedbackParts.remove(target);
		}
	}
}
