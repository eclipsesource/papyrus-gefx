/*****************************************************************************
 * Copyright (c) 2016 - 2018 CEA LIST, EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  EclipseSource - Take line attributes into account
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.decorations;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public interface DecorationFactory {

	String OPEN_ARROW = "openArrow";

	/**
	 * Alias for {@link #OPEN_ARROW}, for Papyrus GMF compatibility
	 */
	String OPEN__ARROW = "open_arrow";

	String WHITE_ARROW = "whiteArrow";

	/**
	 * Alias for {@link #WHITE_ARROW}, for Papyrus GMF compatibility
	 */
	String SOLID_ARROW_EMPRY = "solid_arrow_empty";

	String BLACK_ARROW = "blackArrow";

	/**
	 * Alias for {@link #BLACK_ARROW}, for Papyrus GMF compatibility
	 */
	String SOLID_ARROW_FILLED = "solid_arrow_filled";

	String CIRCLE = "circle";

	String CROSS_CIRCLE = "crossCircle";

	String WHITE_DIAMOND = "whiteDiamond";

	/**
	 * Alias for {@link #WHITE_DIAMOND}, for Papyrus GMF compatibility
	 */
	String SOLID_DIAMOND_EMPTY = "solid_diamond_empty";

	String BLACK_DIAMOND = "blackDiamond";

	/**
	 * Alias for {@link #BLACK_DIAMOND}, for Papyrus GMF compatibility
	 */
	String SOLID_DIAMOND_FILLED = "solid_diamond_filled";

	DecorationFactory instance = new DecorationFactoryImpl();

	Node createOpenArrow(Color lineColor, int lineWidth);

	Node createEmptyArrow(Color lineColor, int lineWidth);

	Node createFullArrow(Color lineColor, int lineWidth);

	Node createEmptyDiamond(Color lineColor, int lineWidth);

	Node createFullDiamond(Color lineColor, int lineWidth);

	Node createCircle(Color lineColor, int lineWidth);

	Node createCrossCircle(Color lineColor, int lineWidth);

}