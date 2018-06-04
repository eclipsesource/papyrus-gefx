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
package org.eclipse.papyrus.gef4.gmf.module.anchors;

import org.eclipse.gef.fx.anchors.AbstractAnchor;
import org.eclipse.gef.fx.anchors.AnchorKey;
import org.eclipse.gef.geometry.planar.Point;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;

public class RatioAnchor extends AbstractAnchor {

	private double ratioX;
	private double ratioY;

	public RatioAnchor(Node anchorage, double ratioX, double ratioY) {
		super(anchorage);
		this.ratioX = ratioX;
		this.ratioY = ratioY;
	}

	@Override
	protected Point computePosition(AnchorKey key) {
		Bounds hostBoundsInParent = getAnchorage().getBoundsInParent();

		// Target, relative to the Host's top-left corner
		Point target = new Point(hostBoundsInParent.getWidth() * ratioX, hostBoundsInParent.getHeight() * ratioY);

		Parent parent = getAnchorage().getParent();
		Bounds hostBoundsInScene = hostBoundsInParent;
		if (parent != null) {
			hostBoundsInScene = parent.localToScene(hostBoundsInParent);
		}

		// The location of the host in the anchored's local coordinates
		Point2D hostLocationInAnchored = key.getAnchored().sceneToLocal(hostBoundsInScene.getMinX(),
				hostBoundsInScene.getMinY());

		return target.translate(hostLocationInAnchored.getX(), hostLocationInAnchored.getY());
	}

}
