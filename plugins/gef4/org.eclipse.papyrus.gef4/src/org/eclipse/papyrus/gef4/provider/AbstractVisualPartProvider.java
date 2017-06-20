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
package org.eclipse.papyrus.gef4.provider;

import org.eclipse.gef.mvc.fx.parts.IFeedbackPart;
import org.eclipse.gef.mvc.fx.parts.IHandlePart;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.gef4.parts.DiagramRootPart;

import javafx.scene.Node;

public abstract class AbstractVisualPartProvider implements IVisualPartProvider {

	@Override
	public IRootPart<? extends Node> createRootPart(Diagram diagram) {
		return new DiagramRootPart(diagram);
	}

	@Override
	public IFeedbackPart<? extends Node> createFeedbackPart() {
		return null;
	}

	@Override
	public IHandlePart<? extends Node> createHandlePart() {
		return null;
	}

}
