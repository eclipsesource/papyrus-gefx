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
package org.eclipse.papyrus.infra.gefdiag.common.provider;

import org.eclipse.gef4.mvc.parts.IFeedbackPart;
import org.eclipse.gef4.mvc.parts.IHandlePart;
import org.eclipse.gef4.mvc.parts.IRootPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.gefdiag.common.part.PapyrusRootPart;

import javafx.scene.Node;

public abstract class AbstractVisualPartProvider implements IVisualPartProvider<Node> {

	@Override
	public IRootPart<Node, ? extends Node> createRootPart(Diagram diagram) {
		return new PapyrusRootPart(diagram);
	}

	@Override
	public IFeedbackPart<Node, ? extends Node> createFeedbackPart() {
		return null;
	}

	@Override
	public IHandlePart<Node, ? extends Node> createHandlePart() {
		return null;
	}

}
