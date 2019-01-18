/*****************************************************************************
 * Copyright (c) 2018 EclipseSource.
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

import org.eclipse.gef.fx.anchors.IAnchor;

/**
 * <p>
 * A Pair of Source/Target anchors, typically used to configure feedback
 * </p>
 */
public interface Anchors {
	IAnchor getSourceAnchor();

	IAnchor getTargetAnchor();
}
