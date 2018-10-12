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
package org.eclipse.papyrus.gef4.decorations;

import javafx.scene.Node;
import javafx.scene.paint.Paint;


public interface DecorationFactory {

	String OPEN_ARROW = "openArrow";

	String WHITE_ARROW = "whiteArrow";

	String BLACK_ARROW = "blackArrow";

	String CIRCLE = "circle";

	String CROSS_CIRCLE = "crossCircle";

	String WHITE_DIAMOND = "whiteDiamond";

	String BLACK_DIAMOND = "blackDiamond";

	DecorationFactory instance = new DecorationFactoryImpl();

	Node createOpenArrow();

	Node createClosedArrow();

	Node createEmptyDiamond();

	Node createFullDiamond();

	Node createDiamond(Paint stroke, Paint fill);

	Node createCircle();

	Node createCrossCircle();

}