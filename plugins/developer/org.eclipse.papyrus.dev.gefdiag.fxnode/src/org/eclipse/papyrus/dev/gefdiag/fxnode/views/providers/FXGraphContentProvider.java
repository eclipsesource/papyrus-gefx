/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.dev.gefdiag.fxnode.views.providers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.parts.DiagramContentPart;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class FXGraphContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
		// Nothing
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// Nothing
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof DiagramContentPart) {
			return new Object[] { ((DiagramContentPart) inputElement).getVisual().getScene() };
		}

		if (inputElement instanceof BaseContentPart) {
			BaseContentPart<?, ?> part = (BaseContentPart<?, ?>) inputElement;
			// Also show anchored parts
			List<Node> result = new LinkedList<>();
			result.add(part.getVisual());

			part.getAnchoredsUnmodifiable().stream().forEach(p -> result.add(p.getVisual()));

			// for (IVisualPart<Node, ? extends Node> anchored : part.getAnchored()){
			//
			// }
			return result.toArray();
		}

		return new Object[0];
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof Parent) {
			Parent parent = (Parent) parentElement;
			List<Node> children = new ArrayList<>(parent.getChildrenUnmodifiable());
			if (parent instanceof Pane) {
				Pane pane = (Pane) parent;
				if (pane.getShape() != null) {
					children.add(pane.getShape());
				}
			}
			return children.toArray();
		}
		if (parentElement instanceof Scene) {
			return new Object[] { ((Scene) parentElement).getRoot() };
		}
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof Node) {
			return ((Node) element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

}
