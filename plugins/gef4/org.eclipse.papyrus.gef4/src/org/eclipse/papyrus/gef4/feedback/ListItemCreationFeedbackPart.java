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

import javafx.scene.shape.Rectangle;

// TODO
// This feedback is a little bit specific, as it changes the background of a ListCompartment,
// rather than adding a new FeedbackElement on top of the diagram. How can we support this?
public class ListItemCreationFeedbackPart extends AbstractCreationFeedbackPart<Rectangle> {

	public ListItemCreationFeedbackPart(org.eclipse.gef.geometry.planar.Rectangle creationBounds) {
		super(creationBounds);
		throw new UnsupportedOperationException("TODO ListItemCreationFeedbackPart is not implemented yet; please do not use it");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.mvc.fx.parts.AbstractVisualPart#doCreateVisual()
	 */
	@Override
	protected Rectangle doCreateVisual() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.mvc.fx.parts.AbstractVisualPart#doRefreshVisual(javafx.scene.Node)
	 */
	@Override
	protected void doRefreshVisual(Rectangle visual) {
		// TODO Auto-generated method stub

	}

}
