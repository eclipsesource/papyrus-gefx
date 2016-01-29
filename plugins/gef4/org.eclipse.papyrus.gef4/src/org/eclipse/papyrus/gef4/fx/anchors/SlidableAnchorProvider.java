/*****************************************************************************
 * Copyright (c) 2015 CEA LIST, Montages AG and others
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Michael Golubev (Montages) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.fx.anchors;

import org.eclipse.gef4.common.adapt.IAdaptable;
import org.eclipse.gef4.fx.anchors.IAnchor;
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.mvc.parts.IVisualPart;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Bounds;
import javafx.scene.Node;

/**
 * Default implementation of the {@link PositionalAnchorProvider} which
 * serves configured {@link SlidableFxAnchor}s.
 */
public class SlidableAnchorProvider implements PositionalAnchorProvider, IAdaptable.Bound<IVisualPart<Node, ? extends Node>> {

	private ReadOnlyObjectProperty<IVisualPart<Node, ? extends Node>> myHost;

	@Override
	public IAnchor get() {
		return new SlidableFxAnchor(getVisual(), 0.5, 0.5);
	}

	@Override
	public IAnchor getForContext(Point scenePoint, IVisualPart<?, ?> connection) {
		if (scenePoint == null || connection == null) {
			return get();
		}

		Bounds sceneBounds = getVisual().localToScene(getVisual().getLayoutBounds());
		double ratioX = sceneBounds.getWidth() == 0 ? 1.0 : (scenePoint.x - sceneBounds.getMinX()) / sceneBounds.getWidth();
		double ratioY = sceneBounds.getHeight() == 0 ? 1.0 : (scenePoint.y - sceneBounds.getMinY()) / sceneBounds.getHeight();

		return new SlidableFxAnchor(getVisual(), ratioX, ratioY);
	}

	protected final Node getVisual() {
		return getAdaptable().getVisual();
	}

	@Override
	public IVisualPart<Node, ? extends Node> getAdaptable() {
		return myHost == null ? null : myHost.get();
	}

	@Override
	public void setAdaptable(IVisualPart<Node, ? extends Node> adaptable) {
		myHost = new ReadOnlyObjectWrapper<>(adaptable);
	}

	@Override
	public ReadOnlyObjectProperty<IVisualPart<Node, ? extends Node>> adaptableProperty() {
		return myHost;
	}
}
