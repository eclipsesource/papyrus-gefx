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
package org.eclipse.papyrus.gef4.services;

import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.google.common.collect.SetMultimap;

/**
 * A service to retrieve the anchorage points for a bound {@link IVisualPart}
 */
public interface AnchorageService {
	SetMultimap<? extends Object, String> getModelAnchorages();
}
