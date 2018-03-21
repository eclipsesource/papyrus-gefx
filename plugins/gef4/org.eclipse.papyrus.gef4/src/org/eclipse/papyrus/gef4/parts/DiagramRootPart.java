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

import javafx.scene.Group;

public class DiagramRootPart<MODEL> extends LayeredRootPart {

	protected MODEL modelRoot;

	public DiagramRootPart() {
		super();
	}

	public DiagramRootPart(MODEL modelRoot) {
		this();
		setModelRoot(modelRoot);
	}

	@Override
	protected void doActivate() {
		super.doActivate();
	}

	public void setModelRoot(MODEL modelRoot) {
		this.modelRoot = modelRoot;
	}

	public MODEL getModelRoot() {
		return modelRoot;
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
