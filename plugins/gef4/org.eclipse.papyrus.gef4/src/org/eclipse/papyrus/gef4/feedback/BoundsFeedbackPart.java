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
package org.eclipse.papyrus.gef4.feedback;

import org.eclipse.gef4.mvc.fx.parts.AbstractFXFeedbackPart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.papyrus.gef4.utils.EffectsUtil;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoundsFeedbackPart extends AbstractFXFeedbackPart<Rectangle> {

	private final IVisualPart<Node, ? extends Node> host;

	private Bounds newBounds;

	public BoundsFeedbackPart(IVisualPart<Node, ? extends Node> host, Bounds newBounds) {
		this.host = host;
		this.newBounds = newBounds;
	}

	// Update feedback while resizing or moving
	public void updateBounds(Bounds newBounds) {
		this.newBounds = newBounds;
		refreshVisual();
	}

	@Override
	protected Rectangle createVisual() {
		return new Rectangle();
	}

	public IVisualPart<Node, ? extends Node> getHost() {
		return host;
	}

	@Override
	protected void doRefreshVisual(Rectangle visual) {
		visual.setManaged(false);
		visual.setFill(Color.TRANSPARENT);
		visual.setStroke(Color.BLACK);
		visual.setStrokeWidth(1);

		visual.setX(newBounds.getX());
		visual.setY(newBounds.getY());

		visual.setEffect(EffectsUtil.BOUNDS_FEEDBACK_EFFECT);

		if (newBounds.getHeight() > 0) {
			visual.setHeight(newBounds.getHeight());
		}

		if (newBounds.getWidth() > 0) {
			visual.setWidth(newBounds.getWidth());
		} else {
			visual.setWidth(100); // FIXME use host bounds
		}

		if (newBounds.getHeight() > 0) {
			visual.setHeight(newBounds.getHeight());
		} else {
			visual.setHeight(100); // FIXME use host bounds
		}

		// Bounds

	}

	@Override
	protected void doDeactivate() {
		// FIXME we shouldn't need to "deactivate" the effect manually to remove the visual.
		// Where should this be done? AbstractFeedback seems to work with Anchorage/Anchored. Does it make sense for this case?
		Parent p = getVisual().getParent();
		if (p instanceof Group) {
			((Group) p).getChildren().remove(getVisual());
		}
		super.doDeactivate();
	}

}
