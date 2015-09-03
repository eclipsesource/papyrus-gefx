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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.image.ImageRegistry;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LabelContentPart extends ContainerContentPart<View, StackPane> {

	private static final String imagePath = "platform:/plugin/org.eclipse.uml2.uml.edit/icons/full/obj16";

	private static double REM;

	public static final double scale(int pixelSize) {
		if (REM < 0.01) {
			REM = Math.rint(new Text("").getLayoutBounds().getHeight());
		}
		return REM * pixelSize / 12;
	}

	private String currentImagePath;

	public LabelContentPart(View view) {
		super(view);
	}

	@Override
	protected StackPane doCreateVisual() {
		StackPane content = new StackPane();
		Label label = new Label();
		refreshLabel(label);
		refreshIcon(label);
		content.getChildren().add(label);
		return content;
	}

	@Override
	protected void doRefreshVisual(StackPane visual) {
		super.doRefreshVisual(visual);
		refreshLabel((Label) visual.getChildren().get(0));
		refreshTextAlignment();
		refreshFont();
		refreshIcon((Label) visual.getChildren().get(0));
	}

	protected void refreshIcon(Label label) {
		String imagePath = getImagePath();
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
			Image image = ImageRegistry.INSTANCE.getImage(imagePath);
			if (image != null && !image.isError()) {
				ImageView imageView = new ImageView(image);
				label.setGraphic(imageView);
			} else {
				label.setGraphic(null);
			}
		}
	}

	protected String getImagePath() {
		EObject semanticElement = getElement();
		if (semanticElement == null) {
			return null;
		}
		return imagePath + "/" + semanticElement.eClass().getName() + ".gif";
	}

	protected void refreshLabel(Label label) {
		String text = getText();

		label.setText(text == null ? "" : text.trim());
	}

	protected void refreshTextAlignment() {
		getVisual().setAlignment(getTextAlignment());
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
		((Label) getVisual().getChildren().get(0)).setTextFill(Color.BLACK);
		((Label) getVisual().getChildren().get(0)).setFont(Font.font("Segoe UI", FontWeight.NORMAL, scale(9)));
	}

	protected String getText() {
		EObject element = getElement();

		return element.toString(); // TODO GMF-like label providers
	}

	@Override
	protected String getStyleClass() {
		return "genericLabel";
	}

}
