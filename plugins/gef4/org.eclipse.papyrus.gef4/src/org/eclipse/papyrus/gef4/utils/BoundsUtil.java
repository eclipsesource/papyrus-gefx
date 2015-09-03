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
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - add getRelativeY & getRelativeX
 *****************************************************************************/
package org.eclipse.papyrus.gef4.utils;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BoundsUtil {
	public static final int getWidth(Node visual) {
		return (int) Math.round(visual.getLayoutBounds().getWidth());
	}

	public static final int getHeight(Node visual) {
		return (int) Math.round(visual.getLayoutBounds().getHeight());
	}

	/**
	 * Gets the relative y for child of VBox.
	 *
	 * @param visual
	 *            the child node
	 * @return the relative y position
	 */
	public static final int getRelativeY(Node visual) {
		int y = 0;
		Parent parent = visual.getParent();
		if (parent instanceof VBox) {
			ObservableList<Node> children = parent.getChildrenUnmodifiable();
			Insets padding = ((VBox) parent).getPadding();
			double spacing = ((VBox) parent).getSpacing();

			int i = 0;
			Node child = children.get(i);
			y += padding.getTop();
			while (!visual.equals(child)) {
				if (child.isVisible() && child.isManaged()) {
					y += getHeight(child) + spacing;
				}
				child = children.get(++i);
			}
		} else if (parent instanceof HBox) { // TODO test for Hbox
			y = (int) ((HBox) parent).getPadding().getLeft();
		}
		return y;
	}

	/**
	 * Gets the relative x for child node of VBox.
	 *
	 * @param visual
	 *            the child node
	 * @return the relative x position
	 */
	public static final int getRelativeX(Node visual) {
		int x = 0;
		Parent parent = visual.getParent();
		if (parent instanceof HBox) {
			ObservableList<Node> children = parent.getChildrenUnmodifiable();
			Insets padding = ((VBox) parent).getPadding();
			double spacing = ((VBox) parent).getSpacing();

			int i = 0;
			Node child = children.get(i);
			x += padding.getLeft();
			while (!visual.equals(child)) {
				if (child.isVisible() && child.isManaged()) {
					x += getWidth(child) + spacing;
				}
				child = children.get(++i);
			}
		} else if (parent instanceof VBox) {// TODO test for VBox
			x = (int) ((VBox) parent).getPadding().getLeft();
		}
		return x;
	}
}
