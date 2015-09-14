/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Layout and visualization API and Implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.parts;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.DecorationNode;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * The Class ListCompartmentContentPart.
 *
 * @param <V>
 *            the value type
 */
public class ListCompartmentContentPart<V extends DecorationNode> extends CompartmentContentPart<V, ScrollPane> {

	/**
	 * Instantiates a new list compartment content part.
	 *
	 * @param view
	 *            the view
	 */
	public ListCompartmentContentPart(final V view) {
		super(view);
	}

	/**
	 * Adds the child visual.
	 *
	 * @param child
	 *            the child
	 * @param index
	 *            the index
	 * @see org.eclipse.gef4.mvc.parts.AbstractVisualPart#addChildVisual(org.eclipse.gef4.mvc.parts.IVisualPart, int)
	 */
	@Override
	protected void addChildVisual(final IVisualPart<Node, ? extends Node> child, final int index) {
		if (null != child.getVisual()) {
			// ((VBox) getVisual()).getChildren().add(child.getVisual());
			((VBox) ((ScrollPane) getVisual()).getContent()).getChildren().add(child.getVisual());
		}
	}

	/**
	 * Removes the child visual.
	 *
	 * @param child
	 *            the child
	 * @param index
	 *            the index
	 * @see org.eclipse.gef4.mvc.parts.AbstractVisualPart#removeChildVisual(org.eclipse.gef4.mvc.parts.IVisualPart, int)
	 */
	@Override
	protected void removeChildVisual(final IVisualPart<Node, ? extends Node> child, final int index) {
		final Node childVisual = child.getVisual();
		if (childVisual == null) {
			return;
		}
		// ((VBox) getVisual()).getChildren().remove(childVisual);
		((VBox) ((ScrollPane) getVisual()).getContent()).getChildren().remove(child.getVisual());
	}

	/**
	 * Do create visual.
	 *
	 * @return the visual
	 * @see org.eclipse.papyrus.gef4.parts.NotationContentPart#doCreateVisual()
	 */
	@Override
	protected ScrollPane doCreateVisual() {
		final VBox vBox = new VBox();
		vBox.boundsInParentProperty().addListener(boundsListener);
		final ScrollPane scrollPane = new ScrollPane(vBox);// TODO use of scrollPane

		// Set stylesheet to hide viewport child which can't
		scrollPane.getStylesheets().clear();
		scrollPane.getStylesheets().add(URI.createPlatformPluginURI("resources/scrollPane.css", false).toPlatformString(false));//$NON-NLS-1$

		return scrollPane;
	}

	/**
	 * Gets the style class.
	 *
	 * @return the style class
	 * @see org.eclipse.papyrus.gef4.parts.CompartmentContentPart#getStyleClass()
	 */
	@Override
	protected String getStyleClass() {
		return "genericListCompartment";
	}

	@Override
	protected void doRefreshVisual(final Region visual) {
		super.doRefreshVisual(visual);

		refreshScrollBar();
		if (ScrollBarPolicy.NEVER.equals(getVerticalBarPolicy())) {
			((VBox) ((ScrollPane) getVisual()).getContent()).setPrefWidth(((ScrollPane) getVisual()).getViewportBounds().getWidth());
		}

		if (ScrollBarPolicy.NEVER.equals(getHorizontalBarPolicy())) {
			((VBox) ((ScrollPane) getVisual()).getContent()).setPrefHeight(((ScrollPane) getVisual()).getViewportBounds().getHeight());
		}


	}

	protected void refreshScrollBar() {
		((ScrollPane) getVisual()).setHbarPolicy(getHorizontalBarPolicy());
		((ScrollPane) getVisual()).setVbarPolicy(getVerticalBarPolicy());
	}

	/**
	 * Gets the min width.
	 *
	 * @return the min width
	 * @see org.eclipse.papyrus.gef4.parts.ContainerContentPart#getMinWidth()
	 */
	@Override
	protected double getMinWidth() {
		double minWidth = -1;

		// gets for the minWidth of children
		final List<IVisualPart<Node, ? extends Node>> children = getChildren();
		for (final IVisualPart<Node, ? extends Node> visualPart : children) {
			final Node visual = visualPart.getVisual();
			if (visual.isManaged() && visual instanceof Region) {
				final Insets padding = getPadding();
				final Insets margin = getMargin();
				// Get the maximum minWidth
				minWidth = Math.max(minWidth, ((ContainerContentPart<?, ?>) visualPart).getMinWidth()
						+ padding.getLeft() + padding.getRight()
						+ margin.getLeft() + margin.getRight());
			}
		}
		return minWidth;
	}

	/**
	 * Gets the min height.
	 *
	 * @return the min height
	 * @see org.eclipse.papyrus.gef4.parts.CompartmentContentPart#getMinHeight()
	 */
	@Override
	protected double getMinHeight() {
		double minheight = 10;

		// If there is no scroll bar
		if (ScrollBarPolicy.NEVER.equals(getVerticalBarPolicy())) {
			final Insets padding = getPadding();
			final Insets margin = getMargin();
			minheight = padding.getTop() + padding.getBottom() + margin.getTop() + margin.getBottom();

			// gets for the minWidth of children
			final double spacing = getSpacing();
			// get children of visualPart
			final List<IVisualPart<Node, ? extends Node>> children = getChildren();

			for (final IVisualPart<Node, ? extends Node> visualPart : children) {
				final Node childVisual = visualPart.getVisual();
				if (childVisual.isVisible() && childVisual.isManaged() && childVisual instanceof Region) {
					// add the minHeight of each child plus the spacing
					minheight += ((ContainerContentPart<?, ?>) visualPart).getMinHeight() + spacing;
				}
			}
			minheight -= spacing; // nbChild-1 of spacing
		}
		return minheight;
	}
}

