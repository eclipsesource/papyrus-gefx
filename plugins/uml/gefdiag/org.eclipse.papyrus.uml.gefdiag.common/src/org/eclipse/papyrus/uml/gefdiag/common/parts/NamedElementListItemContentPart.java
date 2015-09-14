/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.gefdiag.common.parts;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.parts.ListItemContentPart;
import org.eclipse.uml2.uml.NamedElement;

/**
 * The Class NamedElementListItemContentPart.
 */
public class NamedElementListItemContentPart extends ListItemContentPart {

	/**
	 * Instantiates a new named element list item content part.
	 *
	 * @param view
	 *            the view
	 */
	public NamedElementListItemContentPart(final View view) {
		super(view);
	}

	/**
	 * @see org.eclipse.papyrus.gef4.parts.LabelContentPart#getText()
	 *
	 * @return
	 */
	@Override
	protected String getText() {
		final EObject element = getElement();
		if (element instanceof NamedElement) {
			return ((NamedElement) element).getName();
		}
		return super.getText();
	}

	/**
	 * @see org.eclipse.papyrus.gef4.parts.LabelContentPart#getStyleClass()
	 *
	 * @return
	 */
	@Override
	protected String getStyleClass() {
		return "namedelementList";
	}
}
