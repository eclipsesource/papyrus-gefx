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

import org.eclipse.fx.core.Subscription;
import org.eclipse.papyrus.gef4.style.GEFStyle;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

/**
 * <p>
 * A special feedback part that simply highlights the target node.
 * This feedback part's visual is never actually used.
 * </p>
 */
public class ListItemCreationFeedbackPart extends AbstractCreationFeedbackPart<Node> {

	private static final List<Subscription> disposeList = new ArrayList<>();

	public ListItemCreationFeedbackPart(org.eclipse.gef.geometry.planar.Rectangle creationBounds) {
		super(creationBounds);
		// XXX Creation bounds aren't useful for a ListItem insertion. We should use different
		// feedback factories, or at least different parameters (e.g. including the element type/id,
		// or even a complete GEF3-style CreateRequest)
	}

	@Override
	protected Node doCreateVisual() {
		// TODO Add an actual visual node in the target compartment? (e.g. a line at the end of the list?)
		// Currently this is not too useful, because we always create list items at the end; but if we
		// support item insertion (Between two existing items), such feedback would be very useful
		return new Rectangle(); // Unused for now, but still required for IVisualPart API
	}

	protected Node getTargetNode() {
		return getAnchorage().getVisual();
	}

	@Override
	protected void doRefreshVisual(Node visual) {
		Node targetNode = getTargetNode();
		if (targetNode != null && !targetNode.getStyleClass().contains(GEFStyle.CREATION_FEEDBACK_AREA)) {
			targetNode.getStyleClass().add(GEFStyle.CREATION_FEEDBACK_AREA);
			disposeList.add(() -> targetNode.getStyleClass().remove(GEFStyle.CREATION_FEEDBACK_AREA));
		}
	}

	@Override
	protected void doDeactivate() {
		disposeList.forEach(Subscription::disposeIfExists);
		disposeList.clear();
		super.doDeactivate();
	}

}
