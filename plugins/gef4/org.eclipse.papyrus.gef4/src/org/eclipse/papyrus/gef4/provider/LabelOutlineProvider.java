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
package org.eclipse.papyrus.gef4.provider;

import org.eclipse.gef.common.adapt.IAdaptable;
import org.eclipse.gef.geometry.convert.fx.FX2Geometry;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.parts.AbstractLabelContentPart;

import com.google.inject.Provider;

import javafx.scene.control.Label;

/**
 * An Outline Provider for Labels (Node name, List Items, Affixed Labels...)
 */
public class LabelOutlineProvider extends IAdaptable.Bound.Impl<IVisualPart<?>>
		implements Provider<IGeometry> {

	@Override
	public Rectangle get() {
		assert getAdaptable() instanceof AbstractLabelContentPart;
		assert getAdaptable().getVisual() instanceof Label;

		return FX2Geometry.toRectangle(getAdaptable().getVisual().getLayoutBounds());
	}

}
