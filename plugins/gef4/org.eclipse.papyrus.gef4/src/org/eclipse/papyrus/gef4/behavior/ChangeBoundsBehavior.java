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
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.mvc.fx.behaviors.AbstractBehavior;
import org.eclipse.gef.mvc.fx.parts.IFeedbackPart;
import org.eclipse.gef.mvc.fx.parts.IFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.papyrus.gef4.feedback.BoundsFeedbackPart;
import org.eclipse.papyrus.gef4.model.ChangeBoundsModel;

import com.google.inject.Inject;

import javafx.collections.MapChangeListener;
import javafx.scene.Node;

public class ChangeBoundsBehavior extends AbstractBehavior implements MapChangeListener<IVisualPart<? extends Node>, Bounds> {

	@Inject
	IFeedbackPartFactory feedbackPartFactory;

	@Override
	public void doActivate() {
		super.doActivate();

		ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);

		// register
		boundsModel.getManagedElements().addListener(this);

		// create feedback and handles if we are already selected
		updateFeedback(Collections.emptyMap(), boundsModel.getManagedElements());
	}

	protected List<IVisualPart<? extends Node>> extractTargets(Map<IVisualPart<? extends Node>, Bounds> managedElements) {
		return new ArrayList<IVisualPart<? extends Node>>(managedElements.keySet());
	}

	@Override
	public void doDeactivate() {
		ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);
		removeFeedback(extractTargets(boundsModel.getManagedElements()));

		boundsModel.getManagedElements().removeListener(this);

		super.doDeactivate();
	}

	@Override
	public void onChanged(MapChangeListener.Change<? extends IVisualPart<? extends Node>, ? extends Bounds> change) {
		IVisualPart<? extends Node> modifiedPart = change.getKey();
		if (modifiedPart == getHost()) {
			if (change.getValueAdded() != null) { // Addition or Update
				List<IFeedbackPart<? extends Node>> feedback = getFeedbackPerTargetSet().get(Collections.singleton(modifiedPart));
				if (feedback == null) {
					addFeedback(Collections.singletonList(modifiedPart));
				} else {
					updateFeedback(feedback, change.getValueAdded());
				}
			} else { // Removal
				removeFeedback(Collections.singletonList(modifiedPart));
			}
		}
	}

	protected void updateFeedback(List<IFeedbackPart<? extends Node>> feedbackParts, Bounds bounds) {
		for (IFeedbackPart<? extends Node> feedbackPart : feedbackParts){
			if (feedbackPart instanceof BoundsFeedbackPart) {
				((BoundsFeedbackPart) feedbackPart).updateBounds(bounds);
			}
		}
	}

	private void updateFeedback(Map<IVisualPart<? extends Node>, Bounds> oldSelection, Map<IVisualPart<? extends Node>, Bounds> newSelection) {
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

		addFeedback(partsToCreate);

		for (IVisualPart<? extends Node> partToUpdate : partsToUpdate) {
			List<IFeedbackPart<? extends Node>> feedbacks = getFeedbackPerTargetSet().get(Collections.singleton(partToUpdate));
			for (IFeedbackPart<? extends Node> feedback : feedbacks) {
				if (feedback instanceof BoundsFeedbackPart) {
					((BoundsFeedbackPart) feedback).updateBounds(newSelection.get(partToUpdate));
				}
			}
		}
	}

	@Override
	protected void addFeedback(List<? extends IVisualPart<? extends Node>> targets) {
		super.addFeedback(targets);
//		if (feedbackParts != null) {
//			for (IFeedbackPart<? extends Node> f : feedbackParts) {
//				if (f instanceof BoundsFeedbackPart) {
//					currentFeedbackParts.put(((BoundsFeedbackPart) f).getHost(), f);
//				}
//			}
//		}
	}

	@Override
	protected void removeFeedback(Collection<? extends IVisualPart<? extends Node>> targets) {
		super.removeFeedback(targets);

//		for (IVisualPart<? extends Node> target : targets) {
//			IFeedbackPart<? extends Node> f = currentFeedbackParts.get(target);
//			if (f == null) {
//				// A different feedback has been installed (e.g. for connections). The
//				// ChangeBoundsBehavior should probably not be installed in this case
//				continue;
//			}
//
//			currentFeedbackParts.remove(target);
//		}
	}
}
