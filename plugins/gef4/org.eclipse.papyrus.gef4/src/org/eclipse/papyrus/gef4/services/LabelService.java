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

/**
 * <p>
 * A {@link LabelService} typically meant to be bound to a {@link IVisualPart}, via a
 * {@link HelperProviderParticipant}. The service doesn't receive parameters after it's
 * been instantiated; it will only be polled to get a new label when its host is refreshed.
 * </p>
 * <p>
 * TODO The label service is also responsible for hooking any listener that may affect
 * its host element, and refresh it when necessary.
 * </p>
 *
 * @see HelperProviderParticipant#get(org.eclipse.gef.mvc.fx.parts.IVisualPart)
 */
@FunctionalInterface
public interface LabelService {

	/**
	 *
	 * @return
	 * 		The text of the current element. <code>null</code> is equivalent to the empty string
	 */
	String getText();
}
