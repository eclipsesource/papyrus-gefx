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

import org.eclipse.gef.mvc.fx.parts.AbstractFeedbackPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.utils.BoundsUtil;
import org.eclipse.papyrus.gef4.utils.EffectsUtil;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//FIXME For now, this is installed on all nodes, including floating labels
//Floating labels may use a different coordinates system, so they should get specific feedback
public class BoundsFeedbackPart extends AbstractFeedbackPart<Rectangle> {

	private final IVisualPart<? extends Node> host;

	private org.eclipse.gef.geometry.planar.Rectangle newBounds;// Absolute bounds

	/**
	 * Instantiates a new bounds feedback part.
	 *
	 * @param host
	 *            the host
	 * @param newBounds
	 *            the new bounds in relative.
	 */
	public BoundsFeedbackPart(final IVisualPart<? extends Node> host, final org.eclipse.gef.geometry.planar.Rectangle newBounds) {
		this.host = host;

		newBounds.setX(BoundsUtil.getAbsoluteX(host) + getDeltaX(newBounds));
		newBounds.setY(BoundsUtil.getAbsoluteY(host) + getDeltaY(newBounds));

		this.newBounds = newBounds;
	}

	// Update feedback while resizing or moving
	public void updateBounds(final org.eclipse.gef.geometry.planar.Rectangle newBounds) {

		newBounds.setX(BoundsUtil.getAbsoluteX(host) + getDeltaX(newBounds));
		newBounds.setY(BoundsUtil.getAbsoluteY(host) + getDeltaY(newBounds));

		this.newBounds = newBounds;

		refreshVisual();
	}

	protected int getDeltaY(final org.eclipse.gef.geometry.planar.Rectangle newBounds) {
		return (int) (newBounds.getY() - host.getVisual().getLayoutY());
	}

	protected int getDeltaX(final org.eclipse.gef.geometry.planar.Rectangle newBounds) {
		return (int) (newBounds.getX() - host.getVisual().getLayoutX());
	}

	@Override
	protected Rectangle doCreateVisual() {
		return new Rectangle();
	}

	public IVisualPart<? extends Node> getHost() {
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
			visual.setHeight(newBounds.getHeight());
		}

		if (newBounds.getWidth() > 0) {
			visual.setWidth(newBounds.getWidth());
		}

		// refresh rotate
		visual.setRotate(host.getVisual().getRotate());
	}

}
