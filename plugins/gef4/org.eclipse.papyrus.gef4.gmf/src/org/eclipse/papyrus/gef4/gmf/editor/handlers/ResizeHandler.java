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
package org.eclipse.papyrus.gef4.gmf.editor.handlers;

import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.gef4.handle.Direction;

public interface ResizeHandler {

	ICommand resize(Dimension delta, Direction direction);

	void showFeedback(Dimension delta, Direction direction);

	void removeFeedback();

}
