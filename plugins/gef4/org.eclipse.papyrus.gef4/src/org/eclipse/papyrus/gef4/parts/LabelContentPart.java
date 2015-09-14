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
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.image.ImageRegistry;
import org.eclipse.papyrus.gef4.utils.BoundsUtil;
import org.eclipse.papyrus.gef4.utils.NotationUtil;
import org.eclipse.papyrus.gef4.utils.TextOverflowEnum;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

	private static final String imagePath = "platform:/plugin/org.eclipse.uml2.uml.edit/icons/full/obj16";

	private static double REM;

	public static final double scale(final int pixelSize) {
		if (REM < 0.01) {
			REM = Math.rint(new Text("").getLayoutBounds().getHeight());
		}
		return REM * pixelSize / 12;
	}

	private String currentImagePath;

	private Label label;

	public LabelContentPart(final View view) {
		super(view);
	}

	@Override
	protected StackPane doCreateVisual() {
		final StackPane content = new StackPane();
		content.boundsInParentProperty().addListener(boundsListener);// TODO remove listener
		VBox.setVgrow(content, Priority.NEVER);
		label = new Label();
		content.getChildren().add(label);
		label.setAlignment(Pos.TOP_CENTER);
		refreshLabel();
		refreshIcon();

		return content;
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
	protected double getMinHeight() {
		final Insets margin = getMargin();
		final Insets padding = getPadding();

		double minHeight = margin.getTop() + margin.getBottom() + padding.getTop() + padding.getBottom();

		for (final Node child : label.getChildrenUnmodifiable()) {
			if (child instanceof Text) {
				minHeight += ((Text) child).getBoundsInParent().getHeight();
			}
		}
		return minHeight;
	}

	@Override
	protected double getMinWidth() {
		final Insets margin = getMargin();
		final Insets padding = getPadding();

		double minWidth = margin.getLeft() + margin.getRight() + padding.getLeft() + padding.getRight();

		// add text width if text overflow type is visible
		if (TextOverflowEnum.VISIBLE.equals(getTextOverflow())) {
			minWidth += BoundsUtil.getWidth(label);
		}

		return minWidth;
	}

	protected TextOverflowEnum getTextOverflow() {
		// It the parent text overflow property which is take.
		// TODO find how to select label from Label
		return NotationUtil.getTextOverflow(((NotationContentPart<View, StackPane>) getParent()).getView());
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
	 * //TODO: getText alignment of the parents if compartment ??
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


	@Override
	protected void refreshVisibility() {
		// FIXME workaround for unsupported elements
		switch (getView().getType()) {
		case "8510": // StereotypeDisplay for Class
		case "8518":
			getVisual().setVisible(false);
			getVisual().setManaged(false);
			return;
		}
		super.refreshVisibility();
	}

	protected void refreshFont() {
		label.setTextFill(Color.BLACK);
		label.setFont(Font.font("Segoe UI", FontWeight.NORMAL, scale(9)));
	}

	protected String getText() {
		final EObject element = getElement();

		return element.toString(); // TODO GMF-like label providers
	}

	@Override
	protected String getStyleClass() {
		return "genericLabel";
	}
}
