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

import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.image.ImageRegistry;
import org.eclipse.papyrus.gef4.utils.BorderColors;
import org.eclipse.papyrus.gef4.utils.BorderStrokeStyles;
import org.eclipse.papyrus.gef4.utils.NotationUtil;
import org.eclipse.papyrus.gef4.utils.TextOverflowEnum;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SemanticAdapter;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;

public abstract class AbstractLabelContentPart<N extends Node> extends NotationContentPart<View, N> {

	private String currentImagePath;

	protected Label label;

	private IParser parser;

	public AbstractLabelContentPart(final View view) {
		super(view);
	}

	@Override
	protected void refreshVisualInTransaction(final N visual) {
		super.refreshVisualInTransaction(visual);
		refreshBorder();
		refreshLabel();
		refreshTextAlignment();
		refreshFont();
		refreshIcon();
	}

	protected Label getLabelVisual() {
		return label;
	}

	protected TextOverflowEnum getTextOverflow() {
		return NotationUtil.getTextOverflow(getView());
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
		return null;
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
		label.setTextFill(getFontColor());
		label.setFont(getFont());
	}

	protected String getText() {
		IParser parser = getParser();
		if (parser != null) {
			return parser.getPrintString(new SemanticAdapter(getElement(), getView()), ParserOptions.NONE.intValue());
		}
		return "<No parser>";
	}

	protected final IParser getParser() {
		if (parser == null) {
			parser = ParserService.getInstance().getParser(new SemanticAdapter(getElement(), getView()));
		}
		return parser;
	}

	protected void refreshBorder() {
		BorderStroke stroke = null;
		final BorderColors borderColors = getBorderColors();
		final BorderStrokeStyles borderStyles = getBorderStyles();

		stroke = new BorderStroke(borderColors.getTop(), borderColors.getRight(), borderColors.getBottom(), borderColors.getLeft(), borderStyles.getTop(), borderStyles.getRight(), borderStyles.getBottom(),
				borderStyles.getLeft(),
				getCornerRadii(), getBorderWidths(),
				getMargin());
		final Border border = new Border(stroke);
		label.setBorder(border);
	}

	@Override
	protected String getStyleClass() {
		return "genericLabel";
	}
}
