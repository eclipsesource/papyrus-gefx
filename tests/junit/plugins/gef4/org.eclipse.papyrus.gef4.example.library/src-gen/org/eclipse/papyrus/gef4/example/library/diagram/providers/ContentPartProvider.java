package org.eclipse.papyrus.gef4.example.library.diagram.providers;

import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.papyrus.gef4.parts.EmptyContentPart;
import org.eclipse.papyrus.gef4.provider.AbstractContentPartProvider;

import javafx.scene.Node;

public class ContentPartProvider extends AbstractContentPartProvider {

	@Override
	public IContentPart<? extends Node> createContentPart(
			org.eclipse.gmf.runtime.notation.View view) {
		switch (view.getType()) {

		case "30001":
			return new org.eclipse.papyrus.gef4.example.library.diagram.edit.parts.BorrowsEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "20003":
			return new org.eclipse.papyrus.gef4.example.library.diagram.edit.parts.BookEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "40001":
			return new org.eclipse.papyrus.gef4.example.library.diagram.edit.parts.LibraryContentsEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "50002":
			return new org.eclipse.papyrus.gef4.example.library.diagram.edit.parts.PersonLabelEditPart(
					view);
		case "50001":
			return new org.eclipse.papyrus.gef4.example.library.diagram.edit.parts.LibraryLabelEditPart(
					view);
		case "50003":
			return new org.eclipse.papyrus.gef4.example.library.diagram.edit.parts.BookLabelEditPart(
					view);
		case "10002":
			return new org.eclipse.papyrus.gef4.example.library.diagram.edit.parts.PersonEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Library":
			return new org.eclipse.papyrus.gef4.example.library.diagram.edit.parts.LibraryDiagramEditPart(
					(org.eclipse.gmf.runtime.notation.Diagram) view);
		case "10001":
			return new org.eclipse.papyrus.gef4.example.library.diagram.edit.parts.LibraryEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		default:
			return new EmptyContentPart(view);
		}
	}

}
