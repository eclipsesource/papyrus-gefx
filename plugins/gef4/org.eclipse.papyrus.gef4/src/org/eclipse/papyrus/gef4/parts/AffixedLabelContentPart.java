/*****************************************************************************
 * Copyright (c) 2015, 2019 CEA LIST, EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *  EclipseSource
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.parts;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.papyrus.gef4.utils.BorderColors;
import org.eclipse.papyrus.gef4.utils.BorderStrokeStyles;

import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderWidths;

/**
 * {@link LabelContentPart} for floating labels, that are not visually owned
 * by their parent (e.g. small node labels, connection labels...)
 */
public class AffixedLabelContentPart<MODEL> extends LabelContentPart<MODEL> {

	/**
	 * Instantiates a new affixed label content part.
	 *
	 * @param view
	 *            the view
	 */
	public AffixedLabelContentPart(final MODEL model) {
		super(model);
		// Locator is required; for now it is handled by the GMF subclass
	}

	@Override
	public boolean isSelectable() {
		// Avoid selecting invisible (empty) labels during marquee selection 
		String text = getText();
		return text != null && ! text.isEmpty();
	}
	
	@Override
	protected void refreshVisualInTransaction(Label visual) {
		super.refreshVisualInTransaction(visual);

		refreshPosition();
		refreshBorder();
	}
	
	@Override
	protected void refreshBackground() {
		// Skip. We don't want a background on affixed labels (at least for now)
	}

	protected void refreshPosition() {
		if (getLocator() != null) {
			getLocator().applyLayout(getVisual());
		}
	}

	protected void refreshBorder() {
		BorderStroke stroke = null;
		final BorderColors borderColors = getStyleProvider().getBorderColors();
		final BorderStrokeStyles borderStyles = getStyleProvider().getBorderStyles();
		final BorderWidths borderWidths = getStyleProvider().getBorderWidths();

		Border border = null;
		if (borderWidths != null) {
			stroke = new BorderStroke(borderColors.getTop(), borderColors.getRight(), borderColors.getBottom(), borderColors.getLeft(), borderStyles.getTop(), borderStyles.getRight(), borderStyles.getBottom(),
					borderStyles.getLeft(),
					getStyleProvider().getCornerRadii(), borderWidths,
					getStyleProvider().getMargin());
			border = new Border(stroke);
		}
		label.setBorder(border);
	}

	@Override
	protected Collection<String> getStyleClasses() {
		Collection<String> result = new ArrayList<>(super.getStyleClasses());
		result.add("genericAffixedLabel"); //$NON-NLS-1$
		return result;
	}

}
