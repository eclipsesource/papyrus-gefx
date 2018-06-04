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
package org.eclipse.papyrus.gef4.utils;

import org.eclipse.gef.common.activate.ActivatableSupport;
import org.eclipse.gef.common.activate.IActivatable;

import javafx.beans.property.ReadOnlyBooleanProperty;

public abstract class AbstractActivatable implements IActivatable {

	private ActivatableSupport acs = new ActivatableSupport(this);

	protected abstract void doActivate();

	protected abstract void doDeactivate();

	@Override
	public void activate() {
		acs.activate(null, this::doActivate);
	}

	@Override
	public ReadOnlyBooleanProperty activeProperty() {
		return acs.activeProperty();
	}

	@Override
	public void deactivate() {
		acs.deactivate(null, this::doDeactivate);
	}

	@Override
	public boolean isActive() {
		return acs.isActive();
	}

}
