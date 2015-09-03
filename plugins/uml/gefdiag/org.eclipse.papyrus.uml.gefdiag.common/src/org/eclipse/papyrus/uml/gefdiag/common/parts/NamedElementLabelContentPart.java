package org.eclipse.papyrus.uml.gefdiag.common.parts;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.parts.LabelContentPart;
import org.eclipse.uml2.uml.NamedElement;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;

public class NamedElementLabelContentPart extends LabelContentPart {

	public NamedElementLabelContentPart(View view) {
		super(view);
	}


	@Override
	protected String getText() {
		EObject element = getElement();
		if (element instanceof NamedElement) {
			return ((NamedElement) element).getName();
		}
		return super.getText();
	}

	@Override
	protected void doRefreshVisual(StackPane visual) {
		super.doRefreshVisual(visual);
		refreshBackground();
	}

	protected void refreshBackground() {
		// TODO create border with nameBackgroundColor namedStyle
	}

	@Override
	protected Insets getPadding() {
		return new Insets(5); // TODO : fix this. margin of nameLabel shall be set with Class>Label[kind=name]{margin:5;}
	}
}
