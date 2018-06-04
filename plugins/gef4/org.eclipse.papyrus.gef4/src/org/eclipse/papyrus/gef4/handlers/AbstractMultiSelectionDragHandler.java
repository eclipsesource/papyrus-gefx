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
package org.eclipse.papyrus.gef4.handlers;

import java.util.List;
import java.util.function.Consumer;

import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.mvc.fx.gestures.ClickDragGesture;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnDragHandler;
import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.parts.PartUtils;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.utils.ModelUtil;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public abstract class AbstractMultiSelectionDragHandler extends AbstractHandler implements IOnDragHandler {

	/**
	 * Propagate the event to selected elements. //FIXME: Propagating to Drag Policies is not relevant, as several policies can react to the drag action. We also need to check the kind of policy (e.g. Move vs Resize vs Select ...)
	 *
	 * @param e
	 *            the e
	 * @param delta
	 *            the delta
	 * @param actionToPropagate
	 *            the action to propagate
	 */
	protected final void propagate(final MouseEvent e, final Dimension delta, final Consumer<IOnDragHandler> actionToPropagate) {
		final SelectionModel selectionModel = ModelUtil.getSelectionModel(getHost());

		List<IContentPart<? extends Node>> selection = selectionModel.getSelectionUnmodifiable();
		if (selection.size() > 1) {

			// If I'm the main receiver of the event, I propagate it to other selected elements
			// If I'm not the main receiver, do nothing; someone else will do the propagation

			Node targetNode = null;
			if (e.getTarget() instanceof Node) {
				targetNode = (Node) e.getTarget();
			}
			if (targetNode == null) {
				return;
			}
			if (PartUtils.retrieveVisualPart(getHost().getRoot().getViewer(), targetNode) == getHost()) {

				for (final IContentPart<? extends Node> selectedPart : selection) {
					if (selectedPart != getPrimaryHost()) {
						for (final IOnDragHandler dragHandler : selectedPart.getAdapters(ClickDragGesture.ON_DRAG_POLICY_KEY).values()) {
							actionToPropagate.accept(dragHandler);
						}
					}
				}

			}
		}
	}

	boolean isPropagating = false;


	protected final void propagate(final Consumer<IOnDragHandler> actionToPropagate) {

		if (isPropagating) {
			return;
		}

		isPropagating = true;
		try {
			final SelectionModel selectionModel = ModelUtil.getSelectionModel(getHost());

			List<IContentPart<? extends Node>> selection = selectionModel.getSelectionUnmodifiable();
			if (selection.size() > 1) {
				for (final IContentPart<? extends Node> selectedPart : selection) {
					if (selectedPart != getPrimaryHost()) {
						for (final IOnDragHandler dragHandler : selectedPart.getAdapters(ClickDragGesture.ON_DRAG_POLICY_KEY).values()) {
							actionToPropagate.accept(dragHandler);
						}
					}
				}
			}
		} finally {
			isPropagating = false;
		}
	}

	/**
	 * Gets the primary host.
	 *
	 * @return the primary host
	 */
	protected IVisualPart<? extends Node> getPrimaryHost() {
		IVisualPart<? extends Node> host = getHost();
		if (host instanceof BaseContentPart) {
			host = ((BaseContentPart<?, ?>) host).getPrimaryContentPart();
		}
		return host;
	}

}
