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

import org.eclipse.gef4.mvc.fx.policies.IFXOnClickPolicy;
import org.eclipse.gef4.mvc.models.FocusModel;
import org.eclipse.gef4.mvc.models.SelectionModel;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IRootPart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gef4.mvc.policies.AbstractInteractionPolicy;
import org.eclipse.gef4.mvc.viewer.IViewer;
import org.eclipse.papyrus.gef4.parts.NotationContentPart;

import com.google.common.reflect.TypeToken;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

@SuppressWarnings("serial")
public class FocusAndSelectOnClickPolicy extends AbstractInteractionPolicy<Node> implements IFXOnClickPolicy {
	@Override
	public void click(MouseEvent e) {
		// focus and select are only done on single click
		if (e.getClickCount() > 1) {
			return;
		}

		IVisualPart<Node, ? extends Node> host = getHost();
		
		IViewer<Node> viewer = host.getRoot().getViewer();
		IVisualPart<Node, ? extends Node> targetPart = viewer.getVisualPartMap().get(e.getTarget());
		if (targetPart instanceof NotationContentPart){
			NotationContentPart<?, ?> targetNotationPart = (NotationContentPart<?, ?>)targetPart;
			
			NotationContentPart<?, ?> targetPrimaryPart = targetNotationPart.getPrimaryContentPart();
			
			if (targetPrimaryPart == host){
				select(targetPrimaryPart, e);
			} //Else: We're just a parent of the target element. Do nothing
			
		} else if (host instanceof IRootPart){ // Root or Unknown target: select the root
			select((IRootPart<Node, ? extends Node>)host, e);
		}
	}

	protected void select(IRootPart<Node, ? extends Node> target, MouseEvent e) {
		FocusModel<Node> focusModel = target.getRoot().getViewer().getAdapter(new TypeToken<FocusModel<Node>>() {
		});
		SelectionModel<Node> selectionModel = getHost().getRoot().getViewer().getAdapter(new TypeToken<SelectionModel<Node>>() {
		});

		IContentPart<Node, ? extends Node> firstChild = (IContentPart<Node, ? extends Node>) target.getChildrenUnmodifiable().get(0);

		focusModel.setFocus(firstChild);
		if (e.isControlDown()){
			//TODO append
			//focusModel.set
		} else {
			selectionModel.setSelection(firstChild);
		}
	}

	protected void select(IContentPart<Node, ? extends Node> target, MouseEvent e) {

		if (target instanceof NotationContentPart) {
			IContentPart<Node, ? extends Node> primary = ((NotationContentPart<?, ?>) target).getPrimaryContentPart();
			if (primary != null && primary != target) {
				select(primary, e);
				return;
			}
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
			}
		} else {
			if (append) {
				// append to current selection (as new primary)
				selectionModel.appendToSelection(target);
			} else {
				// clear old selection, target should become the only
				// selected
				selectionModel.setSelection(target);
			}
		}
	}
}
