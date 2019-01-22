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
package org.eclipse.papyrus.gef4.gmf.locators;

import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.layout.Locator;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;

import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;

public class AffixedLabelLocator implements Locator {

	private final BaseContentPart<?, ?> host;

	private final ListChangeListener<? super Node> childrenListener;
	protected final ChangeListener<Bounds> boundsListener;

	private View view;

	public AffixedLabelLocator(BaseContentPart<? extends View, ?> host) {
		this.host = host;
		setView(host.getContent());

		childrenListener = (change) -> refreshChildren(change);
		boundsListener = (bounds, oldValue, newValue) -> {
			applyLayout(host.getVisual());
		};
	}

	private void setView(View view) {
		this.view = view;
	}

	@Override
	public void applyLayout(Node node) {
		installChildrenListener(node);

		Location location = getLocation();
		if (location == null) {
			return;
		}

		Point2D position = getLocationInParent(location);

		node.setManaged(false);
		host.getVisual().autosize();

		node.setLayoutX(position.getX());
		node.setLayoutY(position.getY());
	}

	protected Location getLocation() {
		if (view instanceof org.eclipse.gmf.runtime.notation.Node) {
			org.eclipse.gmf.runtime.notation.Node nodeView = (org.eclipse.gmf.runtime.notation.Node) view;
			LayoutConstraint constraint = nodeView.getLayoutConstraint();
			if (constraint instanceof Location) {
				return (Location) constraint;
			}
		}
		return null;
	}

	protected Point2D getLocationInParent(Location location) {
		return new Point2D(location.getX(), location.getY());
	}

	// We need a specific listener for Labels, because Labels are modified during rendering
	protected void refreshChildren(Change<? extends Node> listChange) {
		while (listChange.next()) {
			for (Node newChild : listChange.getAddedSubList()) {
				if (newChild instanceof Parent) {
					((Parent) newChild).getChildrenUnmodifiable().addListener(childrenListener);
				}
				newChild.layoutBoundsProperty().addListener(boundsListener);
			}

			for (Node removedChild : listChange.getRemoved()) {
				if (removedChild instanceof Parent) {
					((Parent) removedChild).getChildrenUnmodifiable().removeListener(childrenListener);
				}
				removedChild.layoutBoundsProperty().removeListener(boundsListener);
			}
		}
	}

	protected void installChildrenListener(Node node) {
		if (node instanceof Parent) {
			Parent parent = (Parent) node;
			parent.getChildrenUnmodifiable().removeListener(childrenListener);
			parent.getChildrenUnmodifiable().addListener(childrenListener);
			for (Node childNode : parent.getChildrenUnmodifiable()) {
				installChildrenListener(childNode);
			}
		}
	}

}
