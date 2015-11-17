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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.image.ImageRegistry;
import org.eclipse.papyrus.gef4.utils.NotationUtil;
import org.eclipse.papyrus.gef4.utils.TextOverflowEnum;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SemanticAdapter;
import org.eclipse.papyrus.uml.diagram.common.parser.NamedElementLabelParser;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class AbstractLabelContentPart<N extends Node> extends NotationContentPart<View, N> {

	protected IParser parser;

	private static final String imagePath = "platform:/plugin/org.eclipse.uml2.uml.edit/icons/full/obj16";

	private String currentImagePath;

	protected Label label;

	public AbstractLabelContentPart(final View view) {
		super(view);
	}

	/**
	 *
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart#getParser()
	 *
	 */
	// TODO to be override at generation to set the good parser or get it directly on gmfgen model
	public IParser getParser() {
		if (parser == null) {
			parser = new NamedElementLabelParser();
		}
		return parser;
	}

	@Override
	protected void refreshVisualInTransaction(final N visual) {
		super.refreshVisualInTransaction(visual);
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
		final EObject semanticElement = getElement();
		if (semanticElement == null) {
			return null;
		}
		return imagePath + "/" + semanticElement.eClass().getName() + ".gif";//$NON-NLS-1$ //$NON-NLS-2$
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
		return getParser().getPrintString(new SemanticAdapter(getElement(), getView()), ParserOptions.NONE.intValue());
	}

	@Override
	protected String getStyleClass() {
		return "genericLabel";
	}
}
