/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gefdiag.common.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.gef4.common.properties.IPropertyChangeNotifier;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.Bounds;

import javafx.scene.Node;

public class ChangeBoundsModel implements IPropertyChangeNotifier {

	public static final String CHANGE_BOUNDS_PROPERTY = "changeBounds"; //$NON-NLS-1$

	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private Map<IVisualPart<Node, ? extends Node>, Bounds> elements = new HashMap<>();

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	public Map<IVisualPart<Node, ? extends Node>, Bounds> getManagedElements() {
		return Collections.unmodifiableMap(elements);
	}

	public void addManagedElement(IVisualPart<Node, ? extends Node> element, Bounds bounds) {
		Map<IVisualPart<Node, ? extends Node>, Bounds> oldContents = Collections.unmodifiableMap(new HashMap<>(this.elements));
		this.elements.put(element, bounds);
		pcs.firePropertyChange(CHANGE_BOUNDS_PROPERTY, oldContents, getManagedElements());
	}

	public void removeManagedElement(IVisualPart<Node, ? extends Node> element) {
		Map<IVisualPart<Node, ? extends Node>, Bounds> oldContents = Collections.unmodifiableMap(new HashMap<>(this.elements));
		this.elements.remove(element);
		pcs.firePropertyChange(CHANGE_BOUNDS_PROPERTY, oldContents, getManagedElements());
	}

}
