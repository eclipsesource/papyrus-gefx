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

public interface ImageService {

	/**
	 * Return the path to the image, for the element managed by this service
	 */
	// XXX: We should probably return an Image or a Node rather than a Path,
	// to support more formats & allow for decorations/overlays.
	String getImagePath();

}
