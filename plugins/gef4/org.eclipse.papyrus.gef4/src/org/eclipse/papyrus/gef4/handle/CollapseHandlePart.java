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
package org.eclipse.papyrus.gef4.handle;

import org.eclipse.gef.mvc.fx.parts.AbstractHandlePart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.parts.CompartmentContentPart;
import org.eclipse.papyrus.gef4.utils.CompartmentUtils;

import com.google.common.collect.SetMultimap;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Border;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class CollapseHandlePart extends AbstractHandlePart<StackPane> {

	/** The width of the collapse handle part. */
	private static final int WIDTH = 7;

	/** The height of the collapse handle part. */
	private static final int HEIGHT = 7;

	/**
	 * @see org.eclipse.gef4.mvc.parts.AbstractVisualPart#createVisual()
	 *
	 * @return
	 */
	@Override
	protected StackPane doCreateVisual() {
		final StackPane stackPane = new StackPane();
		stackPane.setPickOnBounds(true);
		stackPane.setStyle(""
				+ "-fx-background-color: linear-gradient(from 0% 50% to 100% 50%, white 0%, lightgrey 100%);"//$NON-NLS-1$
				+ "-fx-background-radius:3;"//$NON-NLS-1$
				+ "-fx-border-color:darkgrey;"//$NON-NLS-1$
				+ "-fx-border-radius:2;");//$NON-NLS-1$
		stackPane.applyCss();

		stackPane.setPrefSize(WIDTH, HEIGHT);
		stackPane.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		stackPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

		return stackPane;
	}

	/**
	 * @see org.eclipse.gef4.mvc.parts.AbstractVisualPart#doRefreshVisual(java.lang.Object)
	 *
	 * @param visual
	 */
	@Override
	protected void doRefreshVisual(final StackPane visual) {
		// check if we have a host
		final SetMultimap<IVisualPart<? extends Node>, String> anchorages = getAnchoragesUnmodifiable();
		if (anchorages.isEmpty()) {
			return;
		}
		// determine center location of host visual
		final IVisualPart<?> anchorage = anchorages.keys()
				.iterator().next();

		// Get the parent compartment
		final IVisualPart<?> compartment = CompartmentUtils.getCollapsablePart(anchorage);

		if (compartment instanceof CompartmentContentPart) {

			boolean isCollapsed = ((CompartmentContentPart<?, ?>) compartment).isCollapsed();

			getVisual().getChildren().clear();

			if (isCollapsed) {
				Group group = new Group();
				group.getChildren().add(new Line(0, 2, 4, 2)); // Horizontal
				Line e = new Line(2, 0, 2, 4); // Vertical
				e.setFill(Color.BLACK);
				group.getChildren().add(e);
				getVisual().getChildren().add(group);
			} else {
				Group group = new Group();
				group.getChildren().add(new Line(0, 2, 4, 2)); // Horizontal
				getVisual().getChildren().add(group);
			}

			refreshHandleLocation(compartment);
		}
	}

	/**
	 * Refresh handle location.
	 *
	 * @param compartment
	 *            the host visual part
	 */
	protected void refreshHandleLocation(final IVisualPart<? extends Node> compartment) {
		Node hostVisual = compartment.getVisual();

		int left = 0, top = 0;
		if (hostVisual instanceof Region) {
			// Make sure the handle doesn't overlap the border (If any)
			Border hostBorder = ((Region) hostVisual).getBorder();
			if (hostBorder != null && hostBorder.getInsets() != null) {
				left = (int) Math.round(hostBorder.getInsets().getLeft());
				top = (int) Math.round(hostBorder.getInsets().getTop());
			}
		}

		Bounds hostBounds = hostVisual.getBoundsInParent();
		Parent parent = hostVisual.getParent();
		if (parent != null) {
			hostBounds = parent.localToScene(hostBounds);
		}
		Point2D location = getVisual().getParent().sceneToLocal(hostBounds.getMinX(), hostBounds.getMinY());
		getVisual().setLayoutX(location.getX() + left);
		getVisual().setLayoutY(location.getY() + top);
	}

}
