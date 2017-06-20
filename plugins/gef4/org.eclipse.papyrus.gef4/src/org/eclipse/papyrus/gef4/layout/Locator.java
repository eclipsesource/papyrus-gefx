/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.layout;

import org.eclipse.gef.geometry.planar.Point;

import javafx.scene.Node;

/**
 * This interface is used to position a node on its parent, independently from the parent layout
 *
 * This is useful e.g. nodes that require a specific position (Such as border items or overlapping elements)
 *
 * @author Camille Letavernier
 *
 */
public interface Locator {

	/**
	 * Reposition the target Node according to this Locator's logic
	 *
	 * @return
	 */
	public void applyLayout(Node node);

	/**
	 * Return the nearest valid position from the target point
	 *
	 * This can be used to e.g. display proper feedback during a move operation
	 *
	 * @param target
	 * @return
	 */
	// FIXME: Avoid confusion between Node position (top-left point) and anchoring position (Center of the node, or shifted, depending on the Locator)
	public default Point getNearestValidPosition(Node node, Point target) {
		return target;
	}

}
