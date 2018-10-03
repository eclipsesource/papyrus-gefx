/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
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
package org.eclipse.papyrus.gef4.gestures;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.gef.mvc.fx.gestures.DefaultHandlerResolver;
import org.eclipse.gef.mvc.fx.gestures.IGesture;
import org.eclipse.gef.mvc.fx.gestures.IHandlerResolver;
import org.eclipse.gef.mvc.fx.handlers.IHandler;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.papyrus.gef4.tools.Tool;
import org.eclipse.papyrus.gef4.tools.ToolManager;

import javafx.scene.Node;

public class ToolHandlerResolver extends DefaultHandlerResolver implements IHandlerResolver {

	@Inject
	private ToolManager toolManager;

	@Override
	public <T extends IHandler> List<? extends T> resolve(IGesture contextGesture, Node target, IViewer viewer, Class<T> handlerType) {
		List<? extends T> activeToolHandlers = getActiveToolHandlers(contextGesture, target, viewer, handlerType);
		if (activeToolHandlers != null && !activeToolHandlers.isEmpty()) {
			return activeToolHandlers;
		}
		return super.resolve(contextGesture, target, viewer, handlerType);
	}

	private <T extends IHandler> List<? extends T> getActiveToolHandlers(IGesture contextGesture, Node target, IViewer viewer, Class<T> handlerType) {
		Tool activeTool = toolManager.getActiveTool();
		if (activeTool != null) {
			return activeTool.resolve(contextGesture, target, viewer, handlerType);
		}
		return null;
	}


}
