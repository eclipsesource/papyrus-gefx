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

import org.eclipse.gef.mvc.fx.parts.LayeredRootPart;
import org.eclipse.gmf.runtime.notation.Diagram;

import javafx.scene.Group;

public class DiagramRootPart extends LayeredRootPart {

	protected Diagram diagram;

	public DiagramRootPart() {
		super();
	}

	public DiagramRootPart(Diagram diagram) {
		this();
		setDiagram(diagram);
	}

	@Override
	protected void doActivate() {
		super.doActivate();
	}

	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
	}

	public Diagram getDiagram() {
		return diagram;
	}

	@Override
	protected Group doCreateVisual() {
		Group visual = super.doCreateVisual();
		getContentLayer().getStyleClass().add("contentLayer");
		getFeedbackLayer().getStyleClass().add("feedbackLayer");
		getHandleLayer().getStyleClass().add("handleLayer");
		return visual;
	}
}
