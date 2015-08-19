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
package org.eclipse.papyrus.gef4.utils;

import javafx.scene.Node;

public class BoundsUtil {
	public static final int getWidth(Node visual) {
		return (int) Math.round(visual.getLayoutBounds().getWidth());
	}

	public static final int getHeight(Node visual) {
		return (int) Math.round(visual.getLayoutBounds().getHeight());
	}
}
