/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.gefdiag.common.locator;

import javax.inject.Inject;

import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.geometry.planar.Polyline;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.layout.BorderItemLocator;

import javafx.geometry.Bounds;

public class TemplateLocator extends BorderItemLocator {

	@Inject
	public TemplateLocator(IVisualPart<?> visualPart) {
		super(visualPart);
	}

	@Override
	protected IGeometry doGetConstraint(Bounds parentBounds, Dimension nodeSize) {

		double ratio = 0.35; // Fraction of the top-right corner that can be used to position the Template

		double delta = nodeSize.getWidth() * 0.25; // Shift the template to the left so that it is not centered on the border

		Point topCenter = new Point(parentBounds.getWidth() * (1 - ratio), 0); // Do not use a delta here; the first point is not on the right border anyway
		Point topRight = new Point(parentBounds.getWidth() - delta, 0);
		Point right = new Point(parentBounds.getWidth() - delta, parentBounds.getHeight() * ratio);

		Polyline topRightCorner = new Polyline(topCenter, topRight, right);

		return topRightCorner;
	}

}
