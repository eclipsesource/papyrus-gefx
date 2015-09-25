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

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class LabelContentPart extends ContainerContentPart<View, StackPane> {

	protected IParser parser;

	private static final String imagePath = "platform:/plugin/org.eclipse.uml2.uml.edit/icons/full/obj16";

	private static double REM;

	public static final double scale(final int pixelSize) {
		if (REM < 0.01) {
			REM = Math.rint(new Text("").getLayoutBounds().getHeight());
		}
		return REM * pixelSize / 12;
	}

	private String currentImagePath;

	protected Label label;

	public LabelContentPart(final View view) {
		super(view);
	}

	@Override
	protected StackPane doCreateVisual() {
		final StackPane content = new StackPane();
		content.boundsInParentProperty().addListener(boundsListener);// TODO remove listener
		VBox.setVgrow(content, Priority.NEVER);
		label = new Label();
		VBox.setVgrow(label, Priority.NEVER);
		content.getChildren().add(label);
		refreshLabel();
		refreshIcon();
		content.toFront();
		return content;
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
	protected void doRefreshVisual(final StackPane visual) {
		super.doRefreshVisual(visual);
		refreshLabel();
		refreshTextAlignment();
		refreshFont();
		refreshIcon();
	}

	@Override
	public double getMinHeight() {
		final Insets margin = getMargin();
		final Insets padding = getPadding();

		double minHeight = margin.getTop() + margin.getBottom() + padding.getTop() + padding.getBottom();
		minHeight += label.getLayoutBounds().getHeight();
		return minHeight;
	}

	@Override
	public double getMinWidth() {
		final Insets margin = getMargin();
		final Insets padding = getPadding();

		double minWidth = margin.getLeft() + margin.getRight() + padding.getLeft() + padding.getRight();

		// add text width if text overflow type is visible
		if (TextOverflowEnum.VISIBLE.equals(getTextOverflow())) {
			// Create a temporary text to kno the full with of the label
			final Text text = new Text(label.getText());
			text.setFont(label.getFont());
			minWidth += text.getLayoutBounds().getWidth();

			minWidth += label.getGraphicTextGap();
			minWidth += label.getGraphic().getLayoutBounds().getWidth();
		}

		return minWidth;
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
		label.toFront();

		// Refresh the Wrap property
		if (TextOverflowEnum.WRAP.equals(getTextOverflow())) {
			label.setWrapText(true);
		} else {
			label.setWrapText(false);
		}
	}


	/**
	 * Refresh text alignment.
	 * //TODO: getText alignment of the parents if in compartment ??
	 */
	protected void refreshTextAlignment() {
		final Pos textAlignment = getTextAlignment();
		getVisual().setAlignment(textAlignment);

		// Set the text alignment in case of multi-line
		switch (textAlignment) {
		case CENTER_LEFT:
			label.setTextAlignment(TextAlignment.LEFT);
			break;
		case TOP_CENTER:
			label.setTextAlignment(TextAlignment.CENTER);
			break;
		case TOP_RIGHT:
			label.setTextAlignment(TextAlignment.RIGHT);
			break;

		default:
			label.setTextAlignment(TextAlignment.LEFT);
			break;
		}
	}

	protected void refreshFont() {
		label.setTextFill(Color.BLACK);// TODO get value from notation/css
		label.setFont(Font.font("Segoe UI", FontWeight.NORMAL, scale(9)));// TODO get value from notation/css
	}

	protected String getText() {
		return getParser().getPrintString(new SemanticAdapter(getElement(), getView()), ParserOptions.NONE.intValue());
	}

	@Override
	protected String getStyleClass() {
		return "genericLabel";
	}
}
