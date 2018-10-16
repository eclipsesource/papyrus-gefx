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

import java.util.Collection;
import java.util.Collections;

import javafx.scene.Node;

public class EmptyContentPart<MODEL> extends BaseContentPart<MODEL, Node> {

	public EmptyContentPart(MODEL model) {
		super(model);
	}

	@Override
	protected Node doCreateVisual() {
		return null;
	}

	@Override
	protected Collection<String> getStyleClasses() {
		return Collections.emptyList();
	}

}
