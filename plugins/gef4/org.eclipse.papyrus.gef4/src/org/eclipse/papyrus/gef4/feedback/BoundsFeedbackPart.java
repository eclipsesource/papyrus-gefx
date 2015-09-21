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
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - add absolute bounds.
 *  
 *****************************************************************************/
package org.eclipse.papyrus.gef4.feedback;

import org.eclipse.gef4.mvc.fx.parts.AbstractFXFeedbackPart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.papyrus.gef4.utils.BoundsUtil;
import org.eclipse.papyrus.gef4.utils.EffectsUtil;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoundsFeedbackPart extends AbstractFXFeedbackPart<Rectangle> {

	private final IVisualPart<Node, ? extends Node> host;

	private Bounds newBounds;// Absolute bounds

	/**
	 * Instantiates a new bounds feedback part.
	 *
	 * @param host
	 *            the host
	 * @param newBounds
	 *            the new bounds in relative.
	 */
	public BoundsFeedbackPart(final IVisualPart<Node, ? extends Node> host, final Bounds newBounds) {
		this.host = host;

		newBounds.setX(BoundsUtil.getAbsoluteX(host) + getDeltaX(newBounds));
		newBounds.setY(BoundsUtil.getAbsoluteY(host) + getDeltaY(newBounds));

		this.newBounds = newBounds;
	}

	// Update feedback while resizing or moving
	public void updateBounds(final Bounds newBounds) {

		newBounds.setX(BoundsUtil.getAbsoluteX(host) + getDeltaX(newBounds));
		newBounds.setY(BoundsUtil.getAbsoluteY(host) + getDeltaY(newBounds));

		this.newBounds = newBounds;

		refreshVisual();
	}

	protected int getDeltaY(final Bounds newBounds) {
		return (int) (newBounds.getY() - host.getVisual().getLayoutY());
	}

	protected int getDeltaX(final Bounds newBounds) {
		return (int) (newBounds.getX() - host.getVisual().getLayoutX());
	}

	@Override
	protected Rectangle createVisual() {
		return new Rectangle();
	}

	public IVisualPart<Node, ? extends Node> getHost() {
		return host;
	}

	@Override
	protected void doRefreshVisual(final Rectangle visual) {
		visual.setManaged(false);
		visual.setFill(Color.TRANSPARENT);
		visual.setStroke(Color.BLACK);
		visual.setStrokeWidth(1);

		visual.setX(newBounds.getX());
		visual.setY(newBounds.getY());

		visual.setEffect(EffectsUtil.BOUNDS_FEEDBACK_EFFECT);

		if (newBounds.getHeight() > 0) {
			visual.setHeight(Math.max(newBounds.getHeight(), ((Region) host.getVisual()).getMinHeight()));
		}

		if (newBounds.getWidth() > 0) {
			visual.setWidth(Math.max(newBounds.getWidth(), ((Region) host.getVisual()).getMinWidth()));
		}

		// refresh rotate
		visual.setRotate(host.getVisual().getRotate());

	}

	@Override
	protected void doDeactivate() {
		// FIXME we shouldn't need to "deactivate" the effect manually to remove the visual.
		// Where should this be done? AbstractFeedback seems to work with Anchorage/Anchored. Does it make sense for this case?
		final Parent p = getVisual().getParent();
		if (p instanceof Group) {
			((Group) p).getChildren().remove(getVisual());
		}
		super.doDeactivate();
	}

}
