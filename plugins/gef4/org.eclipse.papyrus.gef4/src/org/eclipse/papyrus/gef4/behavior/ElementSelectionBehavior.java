/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.behavior;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.gef.mvc.fx.behaviors.SelectionBehavior;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

/**
 * <p>
 * Override the reference {@link SelectionBehavior} to install handles on each selected element, in case of multi-selection
 * The default was to have global handles for the selection area
 * </p>
 */
public class ElementSelectionBehavior extends SelectionBehavior {

	@Override
	protected void addHandles(List<? extends IVisualPart<?>> targets) {
		if (targets.size() == 1) {
			super.addHandles(targets);
		} else {
			targets.stream().map(Collections::singletonList).forEach(super::addHandles);
		}
	}

	@Override
	protected void removeHandles(Collection<? extends IVisualPart<?>> targets) {
		if (targets.size() == 1) {
			super.removeHandles(targets);
		} else {
			targets.stream().map(Collections::singletonList).forEach(super::removeHandles);
		}
	}

}
