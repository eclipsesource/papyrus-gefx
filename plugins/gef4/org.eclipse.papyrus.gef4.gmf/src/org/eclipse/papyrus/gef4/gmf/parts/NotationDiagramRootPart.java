/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (EclipseSource) cletavernier@eclipsesource.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.gmf.parts;

import javax.inject.Inject;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.gef4.parts.DiagramRootPart;

public class NotationDiagramRootPart extends DiagramRootPart<Diagram> {

	@Inject
	public NotationDiagramRootPart(Diagram diagram) {
		super(diagram);
	}

}
