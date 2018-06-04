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
package org.eclipse.papyrus.gef4.gmf.utils;

import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;

public class GMFPartUtil {

	@SuppressWarnings("unchecked") // Checked in the body
	public static BaseContentPart<? extends View, ?> getBasePart(IVisualPart<?> part) {
		if (false == part instanceof BaseContentPart) {
			return null;
		}
		BaseContentPart<?, ?> basePart = (BaseContentPart<?, ?>) part;
		return basePart.getContent() instanceof View ? (BaseContentPart<? extends View, ?>) part : null;
	}

	public static boolean isBaseNotationPart(IVisualPart<?> part) {
		return getBasePart(part) != null;
	}

}
