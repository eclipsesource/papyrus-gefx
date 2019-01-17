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
package org.eclipse.papyrus.gef4.style;

import java.net.URL;

/**
 * <p>
 * This class declares some CSS Constants and gives access to the default stylesheet
 * </p>
 */
public final class GEFStyle {

	/**
	 * The CSS Class applied on resize handles
	 */
	public static final String RESIZE_HANDLE = "resize-handle";

	/**
	 * The CSS Class applied on selection feedback
	 */
	public static final String SELECTION_FEEDBACK = "selection-feedback";

	/**
	 * The CSS Class applied on bounds feedback
	 */
	public static final String BOUNDS_FEEDBACK = "bounds-feedback";

	/**
	 * The CSS Class applied on creation feedback
	 */
	public static final String CREATION_FEEDBACK = "creation-feedback";

	/**
	 * @return
	 * 		The URL to the gefx CSS Stylesheet, to be installed on the Scene.
	 *         This CSS configures some generic (model-independent) UI styles, like
	 *         feedback colors...
	 */
	public static URL getGEFxCSS() {
		return GEFStyle.class.getResource("gefx.css");
	}

}
