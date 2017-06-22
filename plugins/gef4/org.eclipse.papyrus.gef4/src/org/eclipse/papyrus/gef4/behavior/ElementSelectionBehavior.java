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

import javafx.scene.Node;

/**
 * Override the reference {@link SelectionBehavior} to install handles on each selected element, in case of multi-selection 
 * The default was to have global handles for the selection area
 * 
 */
public class ElementSelectionBehavior extends SelectionBehavior {

	/**
	 * @see org.eclipse.gef.mvc.fx.behaviors.AbstractBehavior#addHandles(java.util.List)
	 *
	 * @param targets
	 */
	@Override
	protected void addHandles(List<? extends IVisualPart<? extends Node>> targets) {
		if (targets.size() == 1) {
			super.addHandles(targets);
			return;
		}
		for (IVisualPart<? extends Node> target : targets) {
			super.addHandles(Collections.singletonList(target));
		}
	}

	/**
	 * @see org.eclipse.gef.mvc.fx.behaviors.AbstractBehavior#removeHandles(java.util.Collection)
	 *
	 * @param targets
	 */
	@Override
	protected void removeHandles(Collection<? extends IVisualPart<? extends Node>> targets) {
		if (targets.size() == 1) {
			super.removeHandles(targets);
			return;
		}
		for (IVisualPart<? extends Node> target : targets) {
			super.removeHandles(Collections.singletonList(target));
		}
	}

}
