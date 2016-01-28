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
package org.eclipse.papyrus.gef4.model;

import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.Bounds;

import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.ReadOnlyMapWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.Node;

public class ChangeBoundsModel {

	public static final String CHANGE_BOUNDS_PROPERTY = "changeBounds"; //$NON-NLS-1$

	private ObservableMap<IVisualPart<Node, ? extends Node>, Bounds> elements = FXCollections.observableHashMap();
	
	private ReadOnlyMapWrapper<IVisualPart<Node, ? extends Node>, Bounds> elementsProperty = new ReadOnlyMapWrapper<>(this, CHANGE_BOUNDS_PROPERTY, elements);

	public ObservableMap<IVisualPart<Node, ? extends Node>, Bounds> getManagedElements() {
		return elements;
	}
	
	public ReadOnlyMapProperty<IVisualPart<Node, ? extends Node>, Bounds> elementsProperty(){
		return elementsProperty.getReadOnlyProperty();
	}

	public void addManagedElement(IVisualPart<Node, ? extends Node> element, Bounds bounds) {
		this.elements.put(element, bounds);
	}

	public void removeManagedElement(IVisualPart<Node, ? extends Node> element) {
		this.elements.remove(element);
	}

}
