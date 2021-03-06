
package org.eclipse.papyrus.uml.gefdiag.activity.edit.parts;

import javafx.geometry.Insets;

public class CommentEditPartCN extends org.eclipse.papyrus.gef4.gmf.parts.ShapeContentPart {

	public CommentEditPartCN(org.eclipse.gmf.runtime.notation.Shape view) {
		super(view);
	}
	
	@Override
	protected void refreshLayout() {
		super.refreshLayout();
		// Hard coded padding to avoid the bent corner
		getVisual().setPadding(new Insets(2, 20, 2, 2));
	}

	@Override
	protected boolean isAutoWidth() {
		return false; // The width is fixed; the height will expand as necessary (Wrapping text)
	}

	@Override
	protected boolean isAutoHeight() {
		return true;
	}

}
