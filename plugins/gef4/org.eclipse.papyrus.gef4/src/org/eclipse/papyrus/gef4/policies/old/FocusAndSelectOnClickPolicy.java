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
package org.eclipse.papyrus.gef4.policies.old;

import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.mvc.fx.models.FocusModel;
import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.gef4.mvc.fx.policies.IFXOnDragPolicy;
import org.eclipse.gef4.mvc.policies.AbstractInteractionPolicy;
import org.eclipse.papyrus.gef4.parts.NotationContentPart;
import org.eclipse.papyrus.gef4.utils.ModelUtil;
import org.eclipse.papyrus.gef4.utils.PolicyUtil;

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
public class FocusAndSelectOnClickPolicy extends AbstractInteractionPolicy implements IFXOnDragPolicy {

	private boolean wasSelected = false;

	@Override
	public void press(MouseEvent e) {
		// focus and select are only done on single click
		if (e.getClickCount() > 1) {
			return;
		}

		wasSelected = false;

		IVisualPart<? extends Node> host = getHost();

		IViewer viewer = host.getRoot().getViewer();
		NotationContentPart<?, ?> targetPrimaryPart = PolicyUtil.getTargetPrimaryPart(this, e);

		if (targetPrimaryPart == host) {
			select(targetPrimaryPart, e);
		} else if (host instanceof IRootPart) { // Root or Unknown target: select the root
			IVisualPart<? extends Node> targetPart = viewer.getVisualPartMap().get(e.getTarget());
			if (targetPart == host || targetPart == null) { // Target part is null when clicking outside the root
				select((IRootPart<? extends Node>) host, e);
			}
		}
	}

	@Override
	public void release(MouseEvent e, Dimension delta) {
		// focus and select are only done on single click
		if (e.getClickCount() > 1 || !e.isStillSincePress()) {
			return;
		}

		IVisualPart<? extends Node> host = getHost();

		IViewer viewer = host.getRoot().getViewer();
		NotationContentPart<?, ?> targetPrimaryPart = PolicyUtil.getTargetPrimaryPart(this, e);

		if (targetPrimaryPart == host) {
			unselect(targetPrimaryPart, e);
		} else if (host instanceof IRootPart) { // Root or Unknown target: select the root
			IVisualPart<? extends Node> targetPart = viewer.getVisualPartMap().get(e.getTarget());
			if (targetPart == host || targetPart == null) { // Target part is null when clicking outside the root
				select((IRootPart<? extends Node>) host, e);
			}
		}
	}

	protected void select(IRootPart<? extends Node> target, MouseEvent e) {
		FocusModel focusModel = ModelUtil.getFocusModel(target);
		SelectionModel selectionModel = ModelUtil.getSelectionModel(getHost());

		IContentPart<? extends Node> firstChild = (IContentPart<? extends Node>) target.getChildrenUnmodifiable().get(0);

		focusModel.setFocus(firstChild);
		if (e.isControlDown()) {
			// TODO append
			// focusModel.set
		} else {
			selectionModel.setSelection(firstChild);
		}
	}

	protected void select(IContentPart<? extends Node> target, MouseEvent e) {

		FocusModel focusModel = ModelUtil.getFocusModel(target);
		SelectionModel selectionModel = ModelUtil.getSelectionModel(getHost());

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

	protected void unselect(IContentPart<? extends Node> target, MouseEvent e) {
		// The host has been selected during the mouse pressed event. Do not unselect it when releasing...
		if (wasSelected) {
			return;
		}

		FocusModel focusModel = ModelUtil.getFocusModel(target);
		SelectionModel selectionModel = ModelUtil.getSelectionModel(getHost());

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
	public void dragAborted() {
		// Nothing to do: this is not a (real) drag policy
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
