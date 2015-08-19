package org.eclipse.papyrus.uml.gefdiag.common.parts;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.parts.ListItemContentPart;
import org.eclipse.uml2.uml.NamedElement;

public class NamedElementListItemContentPart extends ListItemContentPart {

	public NamedElementListItemContentPart(View view) {
		super(view);
	}
	
	@Override
	protected String getText() {
		EObject element = getElement();
		if (element instanceof NamedElement){
			return ((NamedElement)element).getName();
		}
		return super.getText();
	}

}
