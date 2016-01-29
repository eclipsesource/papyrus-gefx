/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.parts;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.layout.AffixedLabelLocator;

import javafx.scene.control.Label;

/**
 * The Class AffixedLabelContentPart.
 */
public class AffixedLabelContentPart extends LabelContentPart implements IPrimaryContentPart {

	/**
	 * Instantiates a new affixed label content part.
	 *
	 * @param view
	 *            the view
	 */
	public AffixedLabelContentPart(final View view) {
		super(view);
		setLocator(new AffixedLabelLocator(this));
	}

	@Override
	protected void refreshVisualInTransaction(Label visual) {
		super.refreshVisualInTransaction(visual);

		refreshPosition();
	}

	protected void refreshPosition() {
		if (getLocator() != null) {
			getLocator().applyLayout(getVisual());
		}
	}

	/**
	 * Gets the style class.
	 *
	 * @return the style class
	 * @see org.eclipse.papyrus.gef4.parts.LabelContentPart#getStyleClass()
	 */
	@Override
	protected String getStyleClass() {
		return "genericAffixedLabel";
	}

}
