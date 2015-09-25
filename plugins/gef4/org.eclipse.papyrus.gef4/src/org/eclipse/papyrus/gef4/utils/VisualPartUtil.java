/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.gef4.utils;

import org.eclipse.gef4.mvc.parts.IRootPart;
import org.eclipse.gef4.mvc.parts.IVisualPart;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * The Class VisualPartUtil.
 */
public class VisualPartUtil {

	public static final String VIEWPORT_SCROLL_PANE_STYLE = "resources/internal/scrollPane.css";//$NON-NLS-1$

	/**
	 * Gets the position of the node on the children list of its parent.
	 *
	 * @return the position on parent, -1 if there is no occurrence.
	 */
	public int getPositionOnParent(final Node node) {
		final Parent parent = node.getParent();
		int position = -1;
		if (parent instanceof Parent) {
			final ObservableList<Node> childrenUnmodifiable = parent.getChildrenUnmodifiable();
			position = childrenUnmodifiable.indexOf(node);
		}
		return position;
	}


	/**
	 * Find the parent visual part instance.
	 *
	 * @param <T>
	 *            the generic type
	 * @param visualPart
	 *            the visual part
	 * @param parentVisualPartClass
	 *            the parent visual part class
	 * @return the parent with the instance. <code>null</code> if not found.
	 */
	public static <T extends IVisualPart<Node, ? extends Node>> T findParentVisualPartInstance(final IVisualPart<Node, ? extends Node> visualPart, final Class<T> parentVisualPartClass) {

		if (visualPart instanceof IRootPart) {
			return null;
		}

		if (parentVisualPartClass.isAssignableFrom(visualPart.getClass())) {
			return parentVisualPartClass.isInstance(visualPart) ? parentVisualPartClass.cast(visualPart) : null;
		}

		return findParentVisualPartInstance(visualPart.getParent(), parentVisualPartClass);
	}

}
