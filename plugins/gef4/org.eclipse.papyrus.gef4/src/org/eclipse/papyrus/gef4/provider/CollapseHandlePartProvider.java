/*****************************************************************************
 * Copyright (c) 2017 EclipseSource and others.
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
package org.eclipse.papyrus.gef4.provider;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gef.mvc.fx.parts.IHandlePart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.Activator;
import org.eclipse.papyrus.gef4.handle.CollapseHandlePart;
import org.eclipse.papyrus.gef4.utils.CompartmentUtils;

import com.google.inject.Singleton;

@Singleton
public class CollapseHandlePartProvider implements IHandlePartProvider {

	/**
	 * @see org.eclipse.papyrus.gef4.provider.IHandlePartProvider#createHandleParts(org.eclipse.gef.mvc.fx.parts.IVisualPart, java.util.Map)
	 *
	 * @param target
	 * @param contextMap
	 * @return
	 */
	@Override
	public List<IHandlePart<?>> createHandleParts(IVisualPart<?> target, Map<Object, Object> contextMap) {
		// Check if the target or one of its parents are a compartment
		final IVisualPart<?> compartment = CompartmentUtils.getCollapsablePart(target);

		if (null != compartment) {
			// create handle part
			final CollapseHandlePart collapseHandlePart = new CollapseHandlePart();
			return Collections.singletonList(collapseHandlePart);
		} else {
			Activator.getDefault().getLog().log(
					new Status(IStatus.WARNING, Activator.PLUGIN_ID, "The handle provider " + getClass().getName() + " only works with Compartments"));
		}

		return Collections.emptyList();
	}

}
