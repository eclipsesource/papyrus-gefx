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
package org.eclipse.papyrus.gef4.parts;

import org.eclipse.papyrus.gef4.utils.FXUtils;

import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

//FIXME Implement generic IParser (Or injected IParser) to avoid abstract class here
public abstract class ListItemContentPart<MODEL> extends LabelContentPart<MODEL> implements IPrimaryContentPart {

	public ListItemContentPart(MODEL model) {
		super(model);
	}

	@Override
	protected Label doCreateVisual() {
		Label thisLabel = super.doCreateVisual();

		VBox.setVgrow(thisLabel, Priority.SOMETIMES); // For line wrap
		thisLabel.setWrapText(true);

		return thisLabel;
	}

	/**
	 * @see org.eclipse.papyrus.gef4.parts.LabelContentPart#refreshVisualInTransaction(javafx.scene.control.Label)
	 *
	 * @param visual
	 */
	@Override
	protected void refreshVisualInTransaction(Label visual) {
		super.refreshVisualInTransaction(visual);
		refreshBorder();
	}

	@Override
	protected void refreshPadding() {
		FXUtils.setPadding(label, 0, 0); // Avoid padding for list items. Better use the parent VBox's spacing & padding
	}
	
	@Override
	protected void refreshBackground() {
		// Skip. We don't want a background on list item labels (at least for now)
	}

	protected void refreshBorder() {
//		BorderStroke stroke = null;
//		final BorderColors borderColors = getStyleProvider().getBorderColors();
//		final BorderStrokeStyles borderStyles = getStyleProvider().getBorderStyles();
//		final BorderWidths borderWidths = getStyleProvider().getBorderWidths();
//
//		Border border = null;
//		if (borderWidths != null) {
//			stroke = new BorderStroke(borderColors.getTop(), borderColors.getRight(), borderColors.getBottom(), borderColors.getLeft(), borderStyles.getTop(), borderStyles.getRight(), borderStyles.getBottom(),
//					borderStyles.getLeft(),
//					getStyleProvider().getCornerRadii(), borderWidths,
//					getStyleProvider().getMargin());
//			border = new Border(stroke);
//		}
//		label.setBorder(border);
	}

}
