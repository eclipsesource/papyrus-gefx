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
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Layout and visualization API and Implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.parts;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public abstract class ContainerContentPart<MODEL, R extends Region> extends BaseContentPart<MODEL, R> {

	protected ContainerContentPart(final MODEL model) {
		super(model);
	}

	@Override
	protected void refreshVisualInTransaction(final R visual) {
		super.refreshVisualInTransaction(visual);

		// resetStyle();

		// Layout refresh
		refreshLayout();

		// Visual refresh
		refreshBorder();
		refreshBackground();
		refreshShadow();
		refreshEffect();
		refreshDecoration();
	}

	protected void refreshLayout() {
		// Refresh Padding
		getVisual().setPadding(getStyleProvider().getPadding());

		// Refresh Spacing for VBox and Hbox
		if (getVisual() instanceof VBox) {
			((VBox) getVisual()).setSpacing(getStyleProvider().getSpacing());
		} else if (getVisual() instanceof HBox) {
			((HBox) getVisual()).setSpacing(getStyleProvider().getSpacing());
		}
	}

	protected void refreshBackground() {
		// Do nothing, the implementation is in charge to manage that.
	}

	protected void refreshShadow() {
		// Do nothing, the implementation is in charge to manage that.
	}

	protected void refreshBorder() {
		// Do nothing, the implementation is in charge to manage that.
	}

	protected void refreshEffect() {
		// Do nothing, the implementation is in charge to manage that.
	}

	protected void refreshDecoration() {
		// Do nothing, the implementation is in charge to manage that.
	}

}
