/*****************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  EclipseSource - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.shapes;

import org.eclipse.fx.core.Subscription;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.shape.Shape;

/**
 * <p>
 * A decorator to apply a {@link Shape} on a VisualPart.
 * </p>
 *
 * <p>
 * The visual part must use a {@link Region} visual.
 * </p>
 */
public interface ShapeDecoration {

	/**
	 * <p>
	 * Apply a decoration on this visual part (Typically via {@link Region#setShape(Shape)})
	 * </p>
	 *
	 * @param visualPart
	 *            The visual part to decorate
	 * @param decorationContainer
	 *            The parent in which extra decorations should be added, if required (Typically,
	 *            this is the Region's getChildren() list, but some parts might use a dedicated
	 *            container)
	 * @return
	 *         A Decoration used to refresh or dispose this shape decoration when it's no longer required
	 */
	Decoration applyShape(IVisualPart<? extends Region> visualPart, ObservableList<Node> decorationContainer);

	/**
	 * An instance of a Decoration applied to a visual part
	 */
	interface Decoration extends Subscription {
		/**
		 * Refresh the decoration, e.g. to match the visualPart's color scheme
		 */
		void refresh();

	}

}
