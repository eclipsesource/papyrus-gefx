/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.parts;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.image.ImageRegistry;
import org.eclipse.papyrus.gef4.utils.TextOverflowEnum;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SemanticAdapter;
import org.eclipse.papyrus.uml.diagram.common.parser.NamedElementLabelParser;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;

/**
 * The Class AffixedLabelContentPart.
 */
public class AffixedLabelContentPart extends ContainerContentPart<View, StackPane> implements IPrimaryContentPart {

	protected Label label;

	private String currentImagePath;

	protected IParser parser;

	private static final String imagePath = "platform:/plugin/org.eclipse.uml2.uml.edit/icons/full/obj16";

	private static double REM;

	public static final double scale(final int pixelSize) {
		if (REM < 0.01) {
			REM = Math.rint(new Text("").getLayoutBounds().getHeight());
		}
		return REM * pixelSize / 12;
	}

	/**
	 * Instantiates a new affixed label content part.
	 *
	 * @param view
	 *            the view
	 */
	public AffixedLabelContentPart(final View view) {
		super(view);
	}

	/**
	 * Gets the style class.
	 *
	 * @return the style class
	 * @see org.eclipse.papyrus.gef4.parts.LabelContentPart#getStyleClass()
	 */
	@Override
	protected String getStyleClass() {
		return "genericAffixedLabel";
	}

	@Override
	protected StackPane doCreateVisual() {
		final StackPane content = new StackPane();
		VBox.setVgrow(content, Priority.NEVER);
		label = new Label();
		VBox.setVgrow(label, Priority.NEVER);
		content.getChildren().add(label);
		refreshLabel();
		refreshIcon();
		return content;
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

		// Refresh the Wrap property
		if (TextOverflowEnum.WRAP.equals(getTextOverflow())) {
			label.setWrapText(true);
		} else {
			label.setWrapText(false);
		}
	}

	/**
	 * Do refresh visual.
	 *
	 * @param visual
	 *            the visual
	 * @see org.eclipse.papyrus.gef4.parts.LabelContentPart#doRefreshVisual(javafx.scene.layout.StackPane)
	 */
	@Override
	protected void refreshVisualInTransaction(final StackPane visual) {
		super.refreshVisualInTransaction(visual);
		// Set rotate
		visual.setRotate(getRotate());
	}

	/**
	 * Refresh border.
	 *
	 * @see org.eclipse.papyrus.gef4.parts.ContainerContentPart#refreshBorder()
	 */
	@Override
	protected void refreshBorder() {
		BorderStroke stroke = null;
		stroke = new BorderStroke(getBorderColors().getTop(), getBorderColors().getRight(), getBorderColors().getBottom(), getBorderColors().getLeft(), getBorderStyles().getTop(), getBorderStyles().getRight(), getBorderStyles().getBottom(),
				getBorderStyles().getLeft(),
				getCornerRadii(), getBorderWidths(), null);
		final Border border = new Border(stroke);

		getVisual().setBorder(border);
	}

	/**
	 * Refresh shadow.
	 *
	 * @see org.eclipse.papyrus.gef4.parts.ContainerContentPart#refreshShadow()
	 */
	@Override
	protected void refreshShadow() {
		final DropShadow shadow = getShadow();
		if (null != shadow) {
			getVisual().setEffect(shadow);
		}
	}

	/**
	 * Refresh background.
	 *
	 * @see org.eclipse.papyrus.gef4.parts.ContainerContentPart#refreshBackground()
	 */
	@Override
	protected void refreshBackground() {
		final StackPane region = getVisual();
		Paint fill = null;
		// Background to fill a simple gradient
		if (null != getBackgroundPaint()) {
			fill = getBackgroundPaint();
		} else {
			fill = new LinearGradient(getBackgroundGradientStartPosition().getX(), getBackgroundGradientStartPosition().getY(), getBackgroundGradientEndPosition().getX(), getBackgroundGradientEndPosition().getY(),
					true, CycleMethod.NO_CYCLE, new Stop(0, getBackgroundColor2()), new Stop(1, getBackgroundColor1()));
		}
		final BackgroundFill backgroundFill = new BackgroundFill(fill, getCornerRadii(), null);
		final Background background = new Background(backgroundFill);
		// set the Background
		region.setBackground(background);
	}

	/**
	 * Gets the text overflow. For External Label, all the label must be shown.
	 *
	 * @return the text overflow
	 * @see org.eclipse.papyrus.gef4.parts.LabelContentPart#getTextOverflow()
	 */
	protected TextOverflowEnum getTextOverflow() {
		return TextOverflowEnum.VISIBLE;
	}

	protected String getText() {
		return getParser().getPrintString(new SemanticAdapter(getElement(), getView()), ParserOptions.NONE.intValue());
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

}
