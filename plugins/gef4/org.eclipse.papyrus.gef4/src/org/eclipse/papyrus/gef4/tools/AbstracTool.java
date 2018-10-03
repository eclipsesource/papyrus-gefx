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
package org.eclipse.papyrus.gef4.tools;

import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.papyrus.gef4.utils.ActivatableBound;

public abstract class AbstracTool extends ActivatableBound<IDomain> implements Tool {

	@Override
	protected void doActivate() {
		// Subclasses may override
	}

	@Override
	protected void doDeactivate() {
		// Subclasses may override
	}

}