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
package org.eclipse.papyrus.gef4.gmf.module;

import org.eclipse.gmf.runtime.notation.Diagram;

import com.google.inject.AbstractModule;

/**
 * A {@link DiagramModule} using a GMF Notation {@link Diagram} model
 */
public class NotationDiagramModule extends AbstractModule {

	private Diagram diagram;

	public NotationDiagramModule(Diagram diagram) {
		this.diagram = diagram;
	}

	@Override
	protected void configure() {
		binder().bind(Diagram.class).toInstance(diagram);
	}

}
