
package org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts;

import org.eclipse.papyrus.uml.gefdiag.common.locator.TemplateLocator;

public class RedefinableTemplateSignatureEditPart extends org.eclipse.papyrus.gef4.parts.NodeContentPart {

	public RedefinableTemplateSignatureEditPart(org.eclipse.gmf.runtime.notation.Shape view) {
		super(view);
		setLocator(new TemplateLocator(this));
	}

}
