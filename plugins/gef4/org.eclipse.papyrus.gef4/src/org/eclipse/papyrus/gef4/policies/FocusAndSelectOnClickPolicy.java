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
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.policies;

import org.eclipse.gef4.geometry.planar.Dimension;
import org.eclipse.gef4.mvc.fx.policies.IFXOnDragPolicy;
import org.eclipse.gef4.mvc.models.FocusModel;
import org.eclipse.gef4.mvc.models.SelectionModel;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IRootPart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gef4.mvc.policies.AbstractInteractionPolicy;
import org.eclipse.gef4.mvc.viewer.IViewer;
import org.eclipse.papyrus.gef4.parts.NotationContentPart;
import org.eclipse.papyrus.gef4.utils.PolicyUtil;

import com.google.common.reflect.TypeToken;

import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * This policy manages Focus and Select actions, in response to mouse clicks
 *
 * In practice, this is implemented as a Drag policy, because we need detailed access
 * to mouse pressed and mouse released events, that the IFXOnClickPolicy doesn't provide
 *
 * @author Camille Letavernier
 *
 */
@SuppressWarnings("serial")
public class FocusAndSelectOnClickPolicy extends AbstractInteractionPolicy<Node> implements IFXOnDragPolicy {

	private boolean wasSelected = false;

	@Override
	public void press(MouseEvent e) {
		// focus and select are only done on single click
		if (e.getClickCount() > 1) {
			return;
		}

		wasSelected = false;

		IVisualPart<Node, ? extends Node> host = getHost();

		IViewer<Node> viewer = host.getRoot().getViewer();
		NotationContentPart<?, ?> targetPrimaryPart = PolicyUtil.getTargetPrimaryPart(this, e);

		if (targetPrimaryPart == host) {
			select(targetPrimaryPart, e);
		} else if (host instanceof IRootPart) { // Root or Unknown target: select the root
			IVisualPart<Node, ? extends Node> targetPart = viewer.getVisualPartMap().get(e.getTarget());
			if (targetPart == host || targetPart == null) { // Target part is null when clicking outside the root
				select((IRootPart<Node, ? extends Node>) host, e);
			}
		}
	}

	@Override
	public void release(MouseEvent e, Dimension delta) {
		// focus and select are only done on single click
		if (e.getClickCount() > 1 || !e.isStillSincePress()) {
			return;
		}

		IVisualPart<Node, ? extends Node> host = getHost();

		IViewer<Node> viewer = host.getRoot().getViewer();
		NotationContentPart<?, ?> targetPrimaryPart = PolicyUtil.getTargetPrimaryPart(this, e);

		if (targetPrimaryPart == host) {
			unselect(targetPrimaryPart, e);
		} else if (host instanceof IRootPart) { // Root or Unknown target: select the root
			IVisualPart<Node, ? extends Node> targetPart = viewer.getVisualPartMap().get(e.getTarget());
			if (targetPart == host || targetPart == null) { // Target part is null when clicking outside the root
				select((IRootPart<Node, ? extends Node>) host, e);
			}
		}
	}

	protected void select(IRootPart<Node, ? extends Node> target, MouseEvent e) {
		FocusModel<Node> focusModel = target.getRoot().getViewer().getAdapter(new TypeToken<FocusModel<Node>>() {
		});
		SelectionModel<Node> selectionModel = getHost().getRoot().getViewer().getAdapter(new TypeToken<SelectionModel<Node>>() {
		});

		IContentPart<Node, ? extends Node> firstChild = (IContentPart<Node, ? extends Node>) target.getChildrenUnmodifiable().get(0);

		focusModel.setFocus(firstChild);
		if (e.isControlDown()) {
			// TODO append
			// focusModel.set
		} else {
			selectionModel.setSelection(firstChild);
		}
	}

	protected void select(IContentPart<Node, ? extends Node> target, MouseEvent e) {

		FocusModel<Node> focusModel = target.getRoot().getViewer().getAdapter(new TypeToken<FocusModel<Node>>() {
		});
		SelectionModel<Node> selectionModel = getHost().getRoot().getViewer().getAdapter(new TypeToken<SelectionModel<Node>>() {
		});

		focusModel.setFocus(target);

		boolean append = e.isControlDown();
		if (!selectionModel.isSelected(target)) {
			if (append) {
				// append to current selection (as new primary)
				selectionModel.appendToSelection(target);
				wasSelected = true;
			} else {
				// clear old selection, target should become the only
				// selected
				selectionModel.setSelection(target);
				wasSelected = true;
			}
		}
	}

	protected void unselect(IContentPart<Node, ? extends Node> target, MouseEvent e) {
		// The host has been selected during the mouse pressed event. Do not unselect it when releasing...
		if (wasSelected) {
			return;
		}

		FocusModel<Node> focusModel = target.getRoot().getViewer().getAdapter(new TypeToken<FocusModel<Node>>() {
		});
		SelectionModel<Node> selectionModel = getHost().getRoot().getViewer().getAdapter(new TypeToken<SelectionModel<Node>>() {
		});

		focusModel.setFocus(target);

		boolean append = e.isControlDown();

		if (selectionModel.isSelected(target)) {
			if (append) {
				// deselect the target edit part (ensure we get a new
				// primary selection)
				selectionModel.removeFromSelection(target);
			} else {
				// select only the target
				selectionModel.setSelection(target);
			}
		}
	}

	@Override
	public void drag(MouseEvent e, Dimension delta) {
		// Nothing
	}

	@Override
	public void hideIndicationCursor() {
		// Nothing
	}

	@Override
	public boolean showIndicationCursor(KeyEvent event) {
		return false;
	}

	@Override
	public boolean showIndicationCursor(MouseEvent event) {
		return false;
	}
}
