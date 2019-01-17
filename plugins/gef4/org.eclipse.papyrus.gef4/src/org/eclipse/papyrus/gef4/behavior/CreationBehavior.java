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

import java.util.ArrayList;
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
import org.eclipse.papyrus.gef4.model.CreationModel;
import org.eclipse.papyrus.gef4.utils.ModelUtil;

import javafx.collections.MapChangeListener;
import javafx.scene.Node;

public class CreationBehavior extends AbstractBehavior {

	public static final String CREATION_ROLE = "elementCreation";

	protected final Map<IVisualPart<? extends Node>, IFeedbackPart<? extends Node>> currentFeedbackParts = new HashMap<>();

	private final MapChangeListener<IVisualPart<?>, Rectangle> creationModelListener = this::creationModelChanged;

	@Override
	public void doActivate() {
		super.doActivate();

		CreationModel creationModel = ModelUtil.getCreationModel(getHost());

		// register
		creationModel.getManagedElements().addListener(creationModelListener);
	}

	protected List<IVisualPart<? extends Node>> extractTargets(Map<IVisualPart<? extends Node>, Rectangle> managedElements) {
		return new ArrayList<>(managedElements.keySet());
	}

	@Override
	public void doDeactivate() {
		CreationModel creationModel = getHost().getRoot().getViewer().getAdapter(CreationModel.class);
		removeFeedback(extractTargets(creationModel.getManagedElements()));

		creationModel.getManagedElements().removeListener(creationModelListener);

		super.doDeactivate();
	}

	private void creationModelChanged(MapChangeListener.Change<? extends IVisualPart<? extends Node>, ? extends Rectangle> change) {
		IVisualPart<? extends Node> modifiedPart = change.getKey();
		if (modifiedPart == getHost()) {
			IFeedbackPartFactory feedbackPartFactory = getFeedbackPartFactory(modifiedPart.getViewer());
			if (feedbackPartFactory == null) {
				return;
			}
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

	protected void updateFeedback(IFeedbackPart<? extends Node> feedbackPart, Rectangle creationBounds) {
		if (feedbackPart instanceof AbstractCreationFeedbackPart) {
			((AbstractCreationFeedbackPart<?>) feedbackPart).updateBounds(creationBounds);
		}
	}

	protected void addFeedback(List<? extends IVisualPart<? extends Node>> targets, List<IFeedbackPart<? extends Node>> feedbackParts) {
		super.addAnchoreds(targets, feedbackParts);
		if (feedbackParts != null) {
			for (IFeedbackPart<? extends Node> f : feedbackParts) {
				if (f instanceof AbstractCreationFeedbackPart) {
					currentFeedbackParts.put(((AbstractCreationFeedbackPart<?>) f).getAnchorage(), f);
				}
			}
		}
		getFeedbackPerTargetSet().put(new HashSet<>(targets), feedbackParts);
	}

	protected void removeFeedback(List<? extends IVisualPart<? extends Node>> targets) {
		for (IVisualPart<? extends Node> target : targets) {
			IFeedbackPart<? extends Node> f = currentFeedbackParts.get(target);
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
