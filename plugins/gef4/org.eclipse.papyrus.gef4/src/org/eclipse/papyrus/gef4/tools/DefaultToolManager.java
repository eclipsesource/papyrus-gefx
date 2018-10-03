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

import javax.inject.Inject;

import org.eclipse.fx.core.Subscription;
import org.eclipse.fx.core.observable.FXObservableUtil;

import javafx.beans.binding.ObjectExpression;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class DefaultToolManager implements ToolManager {

	private ReadOnlyObjectWrapper<Tool> activeToolProperty = new ReadOnlyObjectWrapper<>();

	@Inject
	public DefaultToolManager() {
		doSetTool(getDefaultTool());
	}

	@Override
	public void setActiveTool(Tool activeTool) {
		if (activeTool == null) {
			doSetTool(getDefaultTool());
		} else {
			doSetTool(activeTool);
		}
	}

	private Subscription activeToolSubscription;

	private void doSetTool(Tool activeTool) {
		Tool previousTool = getActiveTool();
		if (activeTool == previousTool) {
			return;
		}

		Subscription.disposeIfExists(activeToolSubscription);
		activeToolSubscription = null;

		if (previousTool != null) {
			previousTool.deactivate();
		}

		activeToolProperty.set(activeTool);
		if (activeTool != null) {
			activeTool.activate();
			activeToolSubscription = FXObservableUtil.onChange(activeTool.activeProperty(), activeState -> {
				if (!activeState) {
					doSetTool(getDefaultTool());
				}
			});
		}
	}

	protected Tool getDefaultTool() {
		return null;
	}

	@Override
	public Tool getActiveTool() {
		return activeToolProperty().getValue();
	}

	@Override
	public ObjectExpression<Tool> activeToolProperty() {
		return activeToolProperty.getReadOnlyProperty();
	}

}
