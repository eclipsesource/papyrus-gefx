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

import org.eclipse.papyrus.gef4.image.ImageRegistry;
import org.eclipse.papyrus.gef4.services.ImageService;
import org.eclipse.papyrus.gef4.services.TextAdapter;
import org.eclipse.papyrus.gef4.services.style.LabelStyleService;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class AbstractLabelContentPart<MODEL, N extends Node> extends BaseContentPart<MODEL, N> {

	private String currentImagePath;

	protected Label label;

	public AbstractLabelContentPart(final MODEL model) {
		super(model);
	}

	@Override
	protected void refreshVisualInTransaction(final N visual) {
		super.refreshVisualInTransaction(visual);
		refreshLabel();
		refreshTextAlignment();
		refreshFont();
		refreshIcon();
	}

	protected TextAdapter getTextService() {
		return getAdapter(TextAdapter.class);
	}

	protected LabelStyleService getLabelStyleProvider() {
		return getAdapter(LabelStyleService.class);
	}

	protected ImageService getImageService() {
		return getAdapter(ImageService.class);
	}

	protected Label getLabelVisual() {
		return label;
	}

	protected void refreshIcon() {
		final String imagePath = getImagePath();
		if (imagePath == currentImagePath) {
			return;
		}
		if (imagePath != null && imagePath.equals(currentImagePath)) {
			return;
		}

		currentImagePath = imagePath;

		if (imagePath == null) {
			label.setGraphic(null);
		} else {
			final Image image = ImageRegistry.INSTANCE.getImage(imagePath);
			if (image != null && !image.isError()) {
				final ImageView imageView = new ImageView(image);
				label.setGraphic(imageView);
			} else {
				label.setGraphic(null);
			}
		}
	}

	protected String getImagePath() {
		return getImageService().getImagePath();
	}

	protected void refreshLabel() {
		final String text = getText();
		label.setText(text == null ? "" : text.trim());

		// FIXME after layout
		// Refresh the Wrap property
		// if (TextOverflowEnum.WRAP.equals(getTextOverflow())) {
		label.setWrapText(true);
		// } else {
		// label.setWrapText(false);
		// }
	}

	protected abstract void refreshTextAlignment();

	protected void refreshFont() {
		label.setTextFill(getStyleProvider().getFontColor());
		label.setFont(getStyleProvider().getFont());
	}

	protected String getText() {
		TextAdapter textService = getTextService();
		if (textService != null) {
			return textService.getText();
		}
		return "<No parser>";
	}

	@Override
	protected String getStyleClass() {
		return "genericLabel";
	}
}
