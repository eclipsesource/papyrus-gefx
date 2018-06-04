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
package org.eclipse.papyrus.gef4.tools;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

public class DefaultToolManager implements ToolManager {

	private Property<Tool> activeToolProperty;

	// private static ToolHolder instance = new DefaultToolHolder();
	//
	// public static ToolHolder getInstance() {
	// return instance;
	// }

	@Override
	public void setTool(Tool activeTool) {
		activeToolProperty().setValue(activeTool);
	}

	@Override
	public Tool getActiveTool() {
		return activeToolProperty().getValue();
	}

	@Override
	public Property<Tool> activeToolProperty() {
		if (activeToolProperty == null) {
			activeToolProperty = new SimpleObjectProperty<>();
		}
		return activeToolProperty;
	}

}
