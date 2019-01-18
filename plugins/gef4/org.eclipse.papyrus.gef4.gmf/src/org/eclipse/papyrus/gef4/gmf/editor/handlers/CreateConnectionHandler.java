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

import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gmf.runtime.common.core.command.ICommand;

import javafx.geometry.Point2D;

public interface CreateConnectionHandler {

	ICommand getSourceCommand(Point2D sceneLocation, Collection<String> elementIds);

	ICommand getCompleteCommand(Point2D sceneLocation, IContentPart<?> targetPart, Collection<String> elementIds);

	void showFeedback(Point2D sceneLocation, Collection<String> elementType);

	void showFeedback(Point2D sceneLocation, IContentPart<?> targetPart, Collection<String> elementType);

	void removeFeedback();

}
