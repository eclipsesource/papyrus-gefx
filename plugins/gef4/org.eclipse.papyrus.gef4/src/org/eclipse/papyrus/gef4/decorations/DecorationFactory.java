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

import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;


public interface DecorationFactory {

	String OPEN_ARROW = "openArrow";

	String WHITE_ARROW = "whiteArrow";

	String BLACK_ARROW = "blackArrow";

	String CIRCLE = "circle";

	String CROSS_CIRCLE = "crossCircle";

	String WHITE_DIAMOND = "whiteDiamond";

	String BLACK_DIAMOND = "blackDiamond";

	DecorationFactory instance = new DecorationFactoryImpl();

	Shape createOpenArrow();

	Shape createClosedArrow();

	Shape createEmptyDiamond();

	Shape createFullDiamond();

	Shape createDiamond(Paint stroke, Paint fill);

	Shape createCircle();

	Shape createCrossCircle();

}