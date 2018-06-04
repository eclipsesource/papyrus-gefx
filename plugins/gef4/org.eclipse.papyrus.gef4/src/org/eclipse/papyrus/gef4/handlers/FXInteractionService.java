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
package org.eclipse.papyrus.gef4.handlers;

import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

public interface FXInteractionService {

	boolean isCtrlPressed();

	boolean isSelected(IVisualPart<?> part);

	IVisualPart<?> getPartAt(Point point);

	IContentPart<?> getContentPartAt(Point point);

	void clearSelection();

	/**
	 * @param part
	 */
	void deselect(IContentPart<?> part);

	/**
	 * @param part
	 */
	void select(IContentPart<?> part);

}
