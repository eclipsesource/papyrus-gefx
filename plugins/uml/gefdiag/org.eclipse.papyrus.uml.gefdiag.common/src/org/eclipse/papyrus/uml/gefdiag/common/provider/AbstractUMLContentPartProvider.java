/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.gefdiag.common.provider;

import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.services.AbstractContentPartProvider;
import org.eclipse.papyrus.gef4.parts.EmptyContentPart;
import org.eclipse.papyrus.uml.gefdiag.common.parts.StereotypeLabelContentPart;

import javafx.scene.Node;

//FIXME
//Placeholder class to avoid exceptions for concepts that are not yet supported (Or not properly supported)
//This should be replaced with an extension point to declare new VisualPartProviders (Similar to the GMF EditPartProvider extension)
public abstract class AbstractUMLContentPartProvider extends AbstractContentPartProvider {

	@Override
	public IContentPart<? extends Node> createContentPart(View view) {
		switch (view.getType()) {
		case "DynamicStereotypeLabel":
			return new StereotypeLabelContentPart(view);
		case "compartment_shape_display":
			return new EmptyContentPart(view);
		}
		return null;
	}

}
