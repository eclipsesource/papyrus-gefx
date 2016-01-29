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
package org.eclipse.papyrus.gef4.utils;

//Some methods are copied from org.eclipse.gmf.runtime.draw2d.ui.figures.BaseSlidableAnchor to remove the dependency to GEF3/Draw2D
public class AnchorParser {

	final private static char TERMINAL_START_CHAR = '(';
	final private static char TERMINAL_DELIMITER_CHAR = ',';
	final private static char TERMINAL_END_CHAR = ')';

	public static double getX(String terminal) {
		try {
			return Double.parseDouble(terminal.substring(
					terminal.indexOf(TERMINAL_START_CHAR) + 1,
					terminal.indexOf(TERMINAL_DELIMITER_CHAR)));
		} catch (Exception ex) {
			return 0;
		}
	}

	public static double getY(String terminal) {
		try {
			return Double.parseDouble(terminal.substring(terminal
					.indexOf(TERMINAL_DELIMITER_CHAR) + 1,
					terminal.indexOf(TERMINAL_END_CHAR)));
		} catch (Exception ex) {
			return 0;
		}
	}


}
