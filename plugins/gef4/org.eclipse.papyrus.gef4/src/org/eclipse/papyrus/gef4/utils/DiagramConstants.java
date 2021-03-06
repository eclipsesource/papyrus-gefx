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

public final class DiagramConstants {

	/**
	 * The anchorage role for the source of a Connection/Edge
	 */
	public static final String SOURCE_ROLE = "source";

	/**
	 * The anchorage role for the target of a Connection/Edge
	 */
	public static final String TARGET_ROLE = "target";

	/**
	 * A generic anchorage role for a fixed anchor position (e.g. Lifeline Body on a Lifeline Header)
	 */
	public static final String POSITION_ROLE = "position";

	/**
	 * The anchorage role for an anchor on the border
	 */
	public static final String BORDER_ROLE = "border";

	private DiagramConstants() {
		// Constants class
	}

}
