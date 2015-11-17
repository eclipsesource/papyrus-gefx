package org.eclipse.papyrus.gef4.parts;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.utils.FXUtils;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public class LabelContentPart extends AbstractLabelContentPart<Label> {

	public LabelContentPart(View view) {
		super(view);
	}

	@Override
	protected Label doCreateVisual() {
		return label = new Label();
	}

	@Override
	protected void refreshVisualInTransaction(Label visual) {
		super.refreshVisualInTransaction(visual);
		refreshPadding();
	}

	/**
	 * Refresh text alignment.
	 * //TODO: getText alignment of the parents if in compartment ??
	 */
	@Override
	protected void refreshTextAlignment() {
		final Pos textAlignment = getTextAlignment();
		getVisual().setAlignment(textAlignment);

		getVisual().setMaxWidth(Double.MAX_VALUE);

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

	protected void refreshPadding() {
		FXUtils.setPadding(label, 2, 5);
	}

}
