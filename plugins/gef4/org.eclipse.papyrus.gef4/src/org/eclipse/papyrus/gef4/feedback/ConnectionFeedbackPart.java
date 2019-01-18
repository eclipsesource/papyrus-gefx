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

import org.eclipse.gef.fx.nodes.Connection;
import org.eclipse.gef.mvc.fx.parts.AbstractFeedbackPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.style.GEFStyle;

public class ConnectionFeedbackPart extends AbstractFeedbackPart<Connection> implements ConnectionCreationFeedbackPart {

	private Anchors anchors;

	public ConnectionFeedbackPart(Anchors creationAnchors) {
		this.anchors = creationAnchors;
	}

	@Override
	public void updateAnchors(Anchors anchors) {
		this.anchors = anchors;
		refreshVisual();
	}

	@Override
	public IVisualPart<?> getAnchorage() {
		return getAnchoragesUnmodifiable().keySet().iterator().next();
	}

	@Override
	protected Connection doCreateVisual() {
		Connection connection = new Connection();
		connection.getStyleClass().add(GEFStyle.CONNECTION_CREATION_FEEDBACK);
		return connection;
	}

	@Override
	protected void doRefreshVisual(Connection visual) {
		visual.setStartAnchor(anchors.getSourceAnchor());
		visual.setEndAnchor(anchors.getTargetAnchor());
	}

}
