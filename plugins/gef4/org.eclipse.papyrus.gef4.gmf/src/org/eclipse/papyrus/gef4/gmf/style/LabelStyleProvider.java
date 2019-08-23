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
package org.eclipse.papyrus.gef4.gmf.style;

import org.eclipse.papyrus.gef4.services.style.StyleService;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;

import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;

/**
 * A {@link StyleService} for Label nodes. It is used to customize the standard
 * {@link Region} properties of Labels (i.e. the label's background and border,
 * but not the label-specific style properties like text alignment or overflow)
 */
public class LabelStyleProvider extends NotationStyleService {

	// FIXME This is basic support for "nameBackgroundColor" but "nameBackgroundColor" 
	// should be generalized for GEFx. This is a temporary solution to improve backwards
	// compatibility with Papyrus
	@Override
	public Paint getBackgroundPaint() {
		Paint paint = null;
		final String labelBackground = NotationUtils.getStringValue(getView(), "nameBackgroundColor", null);
		if (null != labelBackground) {
			try {
				paint = Paint.valueOf(labelBackground);
			} catch (IllegalArgumentException ex) {
				// Not a named color; unsupported for now; skip
			}
		}
		return paint;
	}
	
	@Override
	public CornerRadii getCornerRadii() {
		// For label backgrounds, only round the top corners
		CornerRadii cornerRadii = super.getCornerRadii();
		CornerRadii labelRadii = new CornerRadii(cornerRadii.getTopLeftHorizontalRadius(), cornerRadii.getTopRightHorizontalRadius(), 0, 0, false);
		return labelRadii;
	}
	
}
