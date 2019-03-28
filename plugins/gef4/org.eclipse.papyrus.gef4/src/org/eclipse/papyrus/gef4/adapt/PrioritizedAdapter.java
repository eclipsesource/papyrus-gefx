/*****************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
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
package org.eclipse.papyrus.gef4.adapt;

/**
 * <p>
 * Optional interface to be implemented by adapters, to define their priority
 * compared to other similar adapters.
 * </p>
 */
public interface PrioritizedAdapter {

	/**
	 * The priority for this adapter. Higher values means higher priority
	 * 
	 * @return
	 */
	double getPriority();

	/**
	 * Set the priority for this adapter. Higher values means higher priority.
	 * This value is typically set by the Module for each adapter.
	 */
	void setPriority(double priority);
}
