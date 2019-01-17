/*****************************************************************************
 * Copyright (c) 2019 EclipseSource
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  EclipseSource - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.gef4.model;

import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.ReadOnlyMapWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.Node;

/**
 * <p>
 * A model used to indicate nodes being created. This model
 * is mostly used for feedback.
 * </p>
 */
public class CreationModel {

	public static final String NODE_CREATION_PROPERTY = "nodeCreation"; //$NON-NLS-1$

	private ObservableMap<IVisualPart<? extends Node>, Rectangle> elements = FXCollections.observableHashMap();

	private ReadOnlyMapWrapper<IVisualPart<? extends Node>, Rectangle> elementsProperty = new ReadOnlyMapWrapper<>(this, NODE_CREATION_PROPERTY, elements);

	public ObservableMap<IVisualPart<? extends Node>, Rectangle> getManagedElements() {
		return elements;
	}

	public ReadOnlyMapProperty<IVisualPart<? extends Node>, Rectangle> elementsProperty() {
		return elementsProperty.getReadOnlyProperty();
	}

	public void addManagedElement(IVisualPart<? extends Node> element, Rectangle bounds) {
		this.elements.put(element, bounds);
	}

	public void removeManagedElement(IVisualPart<? extends Node> element) {
		this.elements.remove(element);
	}

}
