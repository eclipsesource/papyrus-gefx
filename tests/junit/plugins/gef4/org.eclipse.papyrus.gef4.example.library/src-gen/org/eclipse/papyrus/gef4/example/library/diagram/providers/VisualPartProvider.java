package org.eclipse.papyrus.gef4.example.library.diagram.providers;

import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gmf.runtime.notation.BasicCompartment;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.gef4.parts.LabelContentPart;
import org.eclipse.papyrus.gef4.parts.ListCompartmentContentPart;
import org.eclipse.papyrus.gef4.parts.NodeContentPart;
import org.eclipse.papyrus.gef4.provider.AbstractVisualPartProvider;

import javafx.scene.Node;

public class VisualPartProvider extends AbstractVisualPartProvider {

	@Override
	public IContentPart<Node, ? extends Node> createContentPart(
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
			// System.out.println("View not supported: " + view);
			return (org.eclipse.gef4.mvc.parts.IContentPart<javafx.scene.Node, ? extends javafx.scene.Node>) new org.eclipse.gmf.runtime.notation.util.NotationSwitch() {
				@Override
				public Object caseDecorationNode(DecorationNode object) {
					return new LabelContentPart(object);
				}

				@Override
				public Object caseShape(Shape object) {
					return new NodeContentPart(object);
				}

				@Override
				public Object caseBasicCompartment(BasicCompartment object) {
					return new ListCompartmentContentPart<DecorationNode>(object);
				}
			}.doSwitch(view);
		// return new EmptyContentPart(view);
		}
	}

}
