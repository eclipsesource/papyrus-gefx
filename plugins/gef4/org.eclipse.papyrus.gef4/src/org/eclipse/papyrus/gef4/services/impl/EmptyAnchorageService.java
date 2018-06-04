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
package org.eclipse.papyrus.gef4.services.impl;

import org.eclipse.papyrus.gef4.services.AnchorageService;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

public final class EmptyAnchorageService implements AnchorageService {

	@Override
	public SetMultimap<? extends Object, String> getModelAnchorages() {
		return HashMultimap.create();
	}

}
