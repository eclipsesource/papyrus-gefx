package org.eclipse.papyrus.gef4.example.library.diagram.providers;

public class ContentPartProvider extends org.eclipse.papyrus.gef4.gmf.services.AbstractContentPartProvider {

	@Override
	public org.eclipse.gef.mvc.fx.parts.IContentPart<?> createContentPart(org.eclipse.gmf.runtime.notation.View view) {
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
			return (org.eclipse.gef.mvc.fx.parts.IContentPart<?>) new org.eclipse.gmf.runtime.notation.util.NotationSwitch() {
				@Override
				public Object caseDecorationNode(org.eclipse.gmf.runtime.notation.DecorationNode object) {
					return new org.eclipse.papyrus.uml.gefdiag.common.parts.NamedElementLabelContentPart(object);
				}

				@Override
				public Object caseShape(org.eclipse.gmf.runtime.notation.Shape object) {
					return new org.eclipse.papyrus.gef4.parts.NodeContentPart<>(object);
				}

				@Override
				public Object caseBasicCompartment(org.eclipse.gmf.runtime.notation.BasicCompartment object) {
					return new org.eclipse.papyrus.gef4.parts.ListCompartmentContentPart<org.eclipse.gmf.runtime.notation.DecorationNode>(
							object);
				}
			}.doSwitch(view);
		// return new EmptyContentPart(view);
		}
	}

}
