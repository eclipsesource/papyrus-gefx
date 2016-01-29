package org.eclipse.papyrus.uml.gefdiag.component.edit.parts;

public class PortEditPart extends org.eclipse.papyrus.gef4.parts.NodeContentPart {

	public PortEditPart(org.eclipse.gmf.runtime.notation.Shape view) {
		super(view);
		setLocator(new org.eclipse.papyrus.gef4.layout.BorderItemLocator(this));
	}
}
