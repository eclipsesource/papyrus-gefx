/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (EclipseSource) cletavernier@eclipsesource.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.feedback;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.fx.utils.NodeUtils;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.parts.AbstractFeedbackPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.style.GEFStyle;

import com.google.common.collect.SetMultimap;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.shape.Polygon;

/**
 * <p>
 * A SelectionFeedbackPart that is used to highlight selected nodes.
 * It supports rectangle nodes, which may optionally be rotated.
 * </p>
 */
public class NodeSelectionFeedbackPart extends AbstractFeedbackPart<Polygon> {

	@Override
	protected Polygon doCreateVisual() {
		Polygon selectionFeedback = new Polygon();
		selectionFeedback.getPoints().addAll(0., 0., 1., 0., 1., 1., 0., 1.);
		selectionFeedback.getStyleClass().add(GEFStyle.SELECTION_FEEDBACK);
		return selectionFeedback;
	}

	@Override
	protected void doRefreshVisual(Polygon visual) {
		refreshBounds(visual);
	}

	protected void refreshBounds(Polygon visual) {
		// only update when bound to anchorage
		SetMultimap<IVisualPart<? extends Node>, String> anchorages = getAnchoragesUnmodifiable();
		if (anchorages.keySet().size() < 1) {
			return;
		}

		IVisualPart<?> anchorage = anchorages.keySet().iterator().next();
		Node anchorageVisual = anchorage.getVisual();

		Bounds anchorageBounds = anchorageVisual.getLayoutBounds();

		List<Point> rectanglePoints = new ArrayList<>();
		rectanglePoints.add(new Point(anchorageBounds.getMinX(), anchorageBounds.getMinY()));
		rectanglePoints.add(new Point(anchorageBounds.getMaxX(), anchorageBounds.getMinY()));
		rectanglePoints.add(new Point(anchorageBounds.getMaxX(), anchorageBounds.getMaxY()));
		rectanglePoints.add(new Point(anchorageBounds.getMinX(), anchorageBounds.getMaxY()));

		int i = 0;
		for (Point rectanglePoint : rectanglePoints) {
			Point pointInScene = NodeUtils.localToScene(anchorageVisual, rectanglePoint);
			Point coordinates = NodeUtils.sceneToLocal(visual.getParent(), pointInScene);
			visual.getPoints().set(i++, coordinates.x());
			visual.getPoints().set(i++, coordinates.y());
		}

	}

}

