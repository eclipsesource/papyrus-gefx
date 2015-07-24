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
package org.eclipse.papyrus.infra.gefdiag.common.policies;

import java.util.Collections;

import org.eclipse.gef4.mvc.fx.policies.AbstractFXOnClickPolicy;
import org.eclipse.gef4.mvc.models.FocusModel;
import org.eclipse.gef4.mvc.models.SelectionModel;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IRootPart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.papyrus.infra.gefdiag.common.part.EmptyContentPart;
import org.eclipse.papyrus.infra.gefdiag.common.part.NotationContentPart;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class FocusAndSelectOnClickPolicy extends AbstractFXOnClickPolicy {
	@Override
	public void click(MouseEvent e) {
		// focus and select are only done on single click
		if (e.getClickCount() > 1) {
			return;
		}

		IVisualPart<Node, ? extends Node> host = getHost();

		if (host instanceof EmptyContentPart) {
			host = host.getRoot();
		}

		if (host instanceof IContentPart) {
			select((IContentPart<Node, ? extends Node>) host, e);
		} else if (host instanceof IRootPart) {
			// check if click on background (either one of the root visuals, or
			// an unregistered visual)
			IVisualPart<Node, ? extends Node> targetPart = getHost().getRoot().getViewer().getVisualPartMap().get(e.getTarget());
			if (targetPart == null || targetPart instanceof IRootPart) {
				select(getHost().getRoot(), e);
			} else if (targetPart instanceof IContentPart) {
				select((IContentPart<Node, ? extends Node>) targetPart, e);
			}

			/*
			 * if (targetPart == null || targetPart == host) {
			 * // unset focus
			 * focusModel.setFocused(null);
			 * // remove all selected
			 * selectionModel.deselectAll();
			 * }
			 */
		}
	}

	protected void select(IRootPart<Node, ? extends Node> target, MouseEvent e) {
		FocusModel<Node> focusModel = target.getRoot().getViewer().<FocusModel<Node>> getAdapter(FocusModel.class);
		SelectionModel<Node> selectionModel = getHost().getRoot().getViewer().<SelectionModel<Node>> getAdapter(SelectionModel.class);

		IContentPart<Node, ? extends Node> firstChild = (IContentPart<Node, ? extends Node>) target.getChildren().get(0);

		focusModel.setFocused(firstChild);
		selectionModel.updateSelection(Collections.singletonList(firstChild));
	}

	protected void select(IContentPart<Node, ? extends Node> target, MouseEvent e) {

		if (target instanceof NotationContentPart) {
			IContentPart<Node, ? extends Node> primary = ((NotationContentPart<?, ?>) target).getPrimaryContentPart();
			if (primary != null && primary != target) {
				select(primary, e);
				return;
			}
		}

		FocusModel<Node> focusModel = target.getRoot().getViewer().<FocusModel<Node>> getAdapter(FocusModel.class);
		SelectionModel<Node> selectionModel = getHost().getRoot().getViewer().<SelectionModel<Node>> getAdapter(SelectionModel.class);

		focusModel.setFocused(target);

		boolean append = e.isControlDown();
		if (selectionModel.isSelected(target)) {
			if (append) {
				// deselect the target edit part (ensure we get a new
				// primary selection)
				selectionModel.deselect(Collections.singleton((IContentPart<Node, ? extends Node>) target));
			}
		} else {
			if (append) {
				// append to current selection (as new primary)
				selectionModel.select(Collections.singletonList((IContentPart<Node, ? extends Node>) target));
			} else {
				// clear old selection, target should become the only
				// selected
				selectionModel.updateSelection(Collections.singletonList((IContentPart<Node, ? extends Node>) target));
			}
		}
	}
}
