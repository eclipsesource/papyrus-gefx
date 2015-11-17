/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Layout and visualiastion
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.gefdiag.common.parts;

import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.parts.LabelContentPart;

import javafx.geometry.Insets;
import javafx.scene.control.Label;

/**
 * The Class NamedElementLabelContentPart.
 */
public class NamedElementLabelContentPart extends LabelContentPart {

	/**
	 * Instantiates a new named element label content part.
	 *
	 * @param view
	 *            the view
	 */
	public NamedElementLabelContentPart(final View view) {
		super(view);
	}

	/**
	 * @see org.eclipse.papyrus.gef4.parts.LabelContentPart#getText()
	 *
	 * @return
	 */
	@Override
	protected String getText() {
		return getParser().getEditString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view), ParserOptions.NONE.intValue());
	}


	/**
	 * @see org.eclipse.papyrus.gef4.parts.LabelContentPart#doRefreshVisual(javafx.scene.layout.StackPane)
	 *
	 * @param visual
	 */
	@Override
	protected void refreshVisualInTransaction(final Label visual) {
		super.refreshVisualInTransaction(visual);
	}

	/**
	 * @see org.eclipse.papyrus.gef4.parts.NotationContentPart#getPadding()
	 *
	 * @return
	 */
	@Override
	protected Insets getPadding() {
		return super.getPadding(); // return new Insets(5); // TODO : fix this. margin of nameLabel shall be set with Class>Label[kind=name]{margin:5;}
	}


	/**
	 * @see org.eclipse.papyrus.gef4.parts.LabelContentPart#getStyleClass()
	 *
	 * @return
	 */
	@Override
	protected String getStyleClass() {
		return "namedLabel";
	}
}
