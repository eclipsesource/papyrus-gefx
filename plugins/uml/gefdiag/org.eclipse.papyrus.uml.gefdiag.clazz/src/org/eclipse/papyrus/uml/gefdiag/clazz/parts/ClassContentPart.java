/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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
package org.eclipse.papyrus.uml.gefdiag.clazz.parts;


import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.gef4.parts.NodeContentPart;

//TODO is active - abstract(with CSS)
@Deprecated
public class ClassContentPart extends NodeContentPart {

	public ClassContentPart(Shape view) {
		super(view);
	}

	@Override
	public org.eclipse.uml2.uml.Class getElement() {
		EObject element = super.getElement();
		if (element instanceof org.eclipse.uml2.uml.Class) {
			return (org.eclipse.uml2.uml.Class) element;
		}
		return null;
	}

	@Override
	protected String getStyleClass() {
		return "class";
	}

}
