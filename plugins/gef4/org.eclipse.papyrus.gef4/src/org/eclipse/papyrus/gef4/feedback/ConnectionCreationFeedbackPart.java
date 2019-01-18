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

import org.eclipse.gef.mvc.fx.parts.IVisualPart;

public interface ConnectionCreationFeedbackPart {

	void updateAnchors(Anchors anchors);

	IVisualPart<?> getAnchorage();

}
