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
package org.eclipse.papyrus.infra.gefdiag.common.part;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gefdiag.common.image.ImageRegistry;
import org.eclipse.papyrus.infra.tools.util.StringHelper;
import org.eclipse.uml2.uml.NamedElement;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LabelContentPart extends NotationContentPart<View, Label> {

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
	protected Label doCreateVisual() {
		Label label = new Label();
		refreshLabel(label);
		refreshIcon(label);
		return label;
	}

	@Override
	protected void doRefreshVisual(Label visual) {
		super.doRefreshVisual(visual);
		refreshLabel(visual);
		refreshTextAlignment();
		refreshFont();
		refreshIcon(visual);


		// final Timeline timeline = new Timeline();
		// visual.setOpacity(1);
		// timeline.setCycleCount(10);
		// timeline.setAutoReverse(true);
		//
		// final KeyValue kv = new KeyValue(visual.scaleXProperty(), 2);
		// final KeyValue kv2 = new KeyValue(visual.scaleYProperty(), 2);
		// final KeyFrame kf = new KeyFrame(Duration.millis(500), kv, kv2);
		// timeline.getKeyFrames().add(kf);
		// timeline.play();
		// timeline.setOnFinished(new EventHandler<ActionEvent>() {
		//
		// @Override
		// public void handle(ActionEvent event) {
		// visual.setScaleX(1);
		// visual.setScaleY(1);
		// }
		// });
	}

	protected void refreshIcon(Label label) {
		String imagePath = getImagePath();
		if (StringHelper.equals(imagePath, currentImagePath)) {
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
		Label label = getVisual();

		// FIXME what is the best way to specify that the label should expand horizontally? VBox only seems to support vertical expansion. Use a different Container?
		NotationContentPart<? extends View, ? extends Node> parentPart = getParentContentPart();
		double minWidth = 0;
		if (parentPart != null) {
			minWidth = parentPart.getWidth();
			BorderWidths parentWidths = parentPart.getBorderWidths();
			if (parentWidths != null) {
				minWidth -= parentWidths.getLeft() + parentWidths.getRight();
			}
		}

		double paddingWidth = 5;
		double paddingHeight = 2;

		label.setMinWidth(minWidth);

		label.setCenterShape(true);

		label.setPadding(new Insets(paddingHeight, paddingWidth, paddingHeight, paddingWidth));

		label.setAlignment(Pos.CENTER);
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
		getVisual().setTextFill(Color.BLACK);
		getVisual().setFont(Font.font("Segoe UI", FontWeight.NORMAL, scale(9)));
	}

	protected String getText() {
		EObject element = getElement();
		if (element instanceof NamedElement) {
			return ((NamedElement) element).getName();
		}
		return "";
	}

	@Override
	protected String getStyleClass() {
		return "genericLabel";
	}

}
