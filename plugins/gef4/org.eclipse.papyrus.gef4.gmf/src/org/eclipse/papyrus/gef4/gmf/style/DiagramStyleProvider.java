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
package org.eclipse.papyrus.gef4.gmf.style;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;

import javafx.scene.paint.Color;

public class DiagramStyleProvider extends NotationStyleService {

	public DiagramStyleProvider(BaseContentPart<? extends View, ?> part) {
		super(part);
	}

	@Override
	public Color getBackgroundColor1() {
		String stringValue = NotationUtils.getStringValue(getView(), "fillColor", null);
		if (stringValue != null) {
			return Color.web(stringValue);
		}
		return null;
	}

	@Override
	public Color getBackgroundColor2() {
		String stringValue = NotationUtils.getStringValue(getView(), "gradientColor", null);
		if (stringValue != null) {
			return Color.web(stringValue);
		}
		return null;
	}

}
