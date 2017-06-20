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

import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BoundsUtil {
	public static final int getWidth(final Node visual) {
		return (int) Math.round(visual.getLayoutBounds().getWidth());
	}

	public static final int getHeight(final Node visual) {
		return (int) Math.round(visual.getLayoutBounds().getHeight());
	}

	/**
	 * Gets the absolute x.
	 *
	 * @param host
	 *            the host
	 * @return the absolute x
	 */
	public static final int getAbsoluteX(final IVisualPart<?> host) {

		int x = (int) ((Node) host.getVisual()).getLayoutX();
		final IRootPart<?> root = host.getRoot();

		IVisualPart<?> parent = host.getParent();
		while (!root.equals(parent)) {
			x += ((Node) parent.getVisual()).getLayoutX();
			parent = parent.getParent();
		}

		return x;
	}

	/**
	 * Gets the absolute y.
	 *
	 * @param host
	 *            the host
	 * @return the absolute y
	 */
	public static final int getAbsoluteY(final IVisualPart<?> host) {

		int y = (int) ((Node) host.getVisual()).getLayoutY();
		final IRootPart<?> root = host.getRoot();

		IVisualPart<?> parent = host.getParent();
		while (!root.equals(parent)) {
			y += ((Node) parent.getVisual()).getLayoutY();
			parent = parent.getParent();
		}

		return y;
	}

	/**
	 * Gets the relative y for child of VBox.
	 *
	 * @param visual
	 *            the child node
	 * @return the relative y position
	 */
	public static final int getRelativeY(final Node visual) {
		int y = 0;
		final Parent parent = visual.getParent();
		if (parent instanceof VBox) {
			final ObservableList<Node> children = parent.getChildrenUnmodifiable();
			final Insets padding = ((VBox) parent).getPadding();
			final double spacing = ((VBox) parent).getSpacing();

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
	public static final int getRelativeX(final Node visual) {
		int x = 0;
		final Parent parent = visual.getParent();
		if (parent instanceof HBox) {
			final ObservableList<Node> children = parent.getChildrenUnmodifiable();
			final Insets padding = ((HBox) parent).getPadding();
			final double spacing = ((HBox) parent).getSpacing();

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
