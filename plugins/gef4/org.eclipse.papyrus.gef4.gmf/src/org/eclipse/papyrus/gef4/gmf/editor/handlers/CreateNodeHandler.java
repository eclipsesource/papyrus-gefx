/*****************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.gmf.editor.handlers;

import java.util.Collection;

import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gmf.runtime.common.core.command.ICommand;

import javafx.geometry.Point2D;

public interface CreateNodeHandler {

	ICommand create(Point2D location, Dimension size, Collection<String> elementTypes);

	void showFeedback(Point2D location, Dimension size, Collection<String> elementType);

	void removeFeedback();

}
