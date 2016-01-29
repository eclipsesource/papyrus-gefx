package org.eclipse.papyrus.uml.gefdiag.composite.edit.parts;

public class ParameterEditPart extends org.eclipse.papyrus.gef4.parts.NodeContentPart {

	public ParameterEditPart(org.eclipse.gmf.runtime.notation.Shape view) {
		super(view);
		setLocator(new org.eclipse.papyrus.gef4.layout.BorderItemLocator(this));
	}
}
