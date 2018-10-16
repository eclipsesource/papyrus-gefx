/*****************************************************************************
 * Copyright (c) 2015, 2018 CEA LIST, EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Camille Letavernier (EclipseSource) cletavernier@eclipsesource.com - Migrate to GEF5
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.handlers;

import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnDragHandler;
import org.eclipse.gef.mvc.fx.models.FocusModel;
import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.parts.PartUtils;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.papyrus.gef4.parts.AffixedLabelContentPart;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.parts.IPrimaryContentPart;
import org.eclipse.papyrus.gef4.utils.ModelUtil;

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
public class SelectOnClickHandler extends AbstractHandler implements IOnDragHandler {

	private boolean wasSelected = false;

	@Override
	public void startDrag(MouseEvent e) {
		// focus and select are only done on single click
		if (e.getClickCount() > 1) {
			return;
		}

		wasSelected = false;

		IVisualPart<?> host = getHost();

		if (host instanceof IRootPart) {
			select((IRootPart<?>) host, e);
		} else if (host instanceof BaseContentPart<?, ?>) {
			select((BaseContentPart<?, ?>) host, e);
		}
	}

	@Override
	public void endDrag(MouseEvent e, Dimension delta) {
		// focus and select are only done on single click
		if (e.getClickCount() > 1 || !e.isStillSincePress()) {
			return;
		}

		IVisualPart<?> host = getHost();

		IViewer viewer = host.getRoot().getViewer();

		// XXX a few debug asserts to make sure the handler is only installed on selectable elements
		assert host instanceof IContentPart || host instanceof IRootPart;
		assert host instanceof IPrimaryContentPart || host instanceof AffixedLabelContentPart || host instanceof IRootPart;

		if (host instanceof IRootPart) { // Root or Unknown target: select the root
			IVisualPart<?> targetPart = PartUtils.retrieveVisualPart(viewer, (Node) e.getTarget());
			if (targetPart == host || targetPart == null) { // Target part is null when clicking outside the root
				select((IRootPart<?>) host, e);
			}
		} else {
			unselect((IContentPart<?>) host, e);
		}
	}

	protected void select(IRootPart<?> target, MouseEvent e) {
		FocusModel focusModel = ModelUtil.getFocusModel(target);
		SelectionModel selectionModel = ModelUtil.getSelectionModel(getHost());

		IContentPart<?> firstChild = (IContentPart<?>) target.getChildrenUnmodifiable().get(0);

		focusModel.setFocus(firstChild);
		if (e.isControlDown()) {
			// Do nothing
		} else {
			selectionModel.setSelection(firstChild);
		}
	}

	protected void select(IContentPart<?> target, MouseEvent e) {

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

	protected void unselect(IContentPart<?> target, MouseEvent e) {
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
				if (selectionModel.getSelectionUnmodifiable().isEmpty()) {
					selectionModel.setSelection(target.getRoot().getContentPartChildren().get(0));
				}
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
	public void abortDrag() {
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
