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
package org.eclipse.papyrus.gef4.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;

import javafx.scene.Group;
import javafx.scene.Node;

public class DiagramContentPart extends NotationContentPart<Diagram, Group> {


	public DiagramContentPart(Diagram view) {
		super(view);
	}

	@Override
	protected Group doCreateVisual() {
		return new Group();
	}

	@Override
	protected boolean childrenChanged(Notification msg) {
		if (msg.getNotifier() != getView()) {
			return false;
		}

		if (msg.getFeature() == NotationPackage.Literals.DIAGRAM__PERSISTED_EDGES || msg.getFeature() == NotationPackage.Literals.DIAGRAM__TRANSIENT_EDGES) {
			return true;
		}

		return super.childrenChanged(msg);
	}

	@Override
	protected List<View> getContentChildren() {
		List<View> allChildren = new ArrayList<>(super.getContentChildren());
		allChildren.addAll(getView().getEdges());
		return allChildren;
	}

	@Override
	protected void removeChildVisual(IVisualPart<Node, ? extends Node> child, int index) {
		Node childVisual = child.getVisual();
		if (childVisual == null) {
			return;
		}

		getVisual().getChildren().remove(childVisual);
	}

	@Override
	protected void addChildVisual(IVisualPart<Node, ? extends Node> child, int index) {
		if (child.getVisual() != null) {
			getVisual().getChildren().add(child.getVisual());
		}
	}

	@Override
	protected String getStyleClass() {
		return "diagram";
	}

}
