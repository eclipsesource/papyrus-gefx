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

import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.parts.AbstractFeedbackPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import javafx.scene.Node;

public abstract class AbstractCreationFeedbackPart<T extends Node> extends AbstractFeedbackPart<T> {

	protected Rectangle creationBoundsInLocal;

	protected AbstractCreationFeedbackPart(Rectangle creationBoundsInLocal) {
		this.creationBoundsInLocal = creationBoundsInLocal;
	}

	public final void updateBounds(final Rectangle creationBounds) {
		this.creationBoundsInLocal = creationBounds;
		refreshVisual();
	}

	public final IVisualPart<? extends Node> getAnchorage() {
		return getAnchoragesUnmodifiable().keySet().iterator().next();
	}

}
