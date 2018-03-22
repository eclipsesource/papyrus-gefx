package org.eclipse.papyrus.uml.gefdiag.component.providers;

public class ContentPartProvider
		extends org.eclipse.papyrus.uml.gefdiag.common.provider.AbstractUMLContentPartProvider {

	@Override
	public org.eclipse.gef.mvc.fx.parts.IContentPart<?> createContentPart(org.eclipse.gmf.runtime.notation.View view) {
		switch (view.getType()) {

		case "Property_InterfaceAttributeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PropertyForInterfaceEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Comment_AnnotatedElementEdge":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.CommentAnnotatedElementEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Package_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PackageNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Constraint_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ConstraintEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Constraint_BodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ConstraintSpecificationEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "NamedElement_DefaultShape":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.DefaultNamedElementEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Abstraction_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.AbstractionEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Model_PackagedElementCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ModelPackageableElementCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Dependency_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.DependencyNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Package_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PackageEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Manifestation_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ManifestationEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Component_PackagedElementShape":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Connector_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ConnectorEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Component_StructureCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentCompositeCompartmentEditPartPCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Component_StructureCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentCompositeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Interface_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.RectangleInterfaceEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Comment_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.CommentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Usage_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.UsageEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Connector_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ConnectorAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Component_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentNameEditPartPCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_ClassifierFloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.RectangleInterfaceFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Constraint_ConstrainedElementEdge":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ConstraintConstrainedElementEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Constraint_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ConstraintEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Abstraction_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.AbstractionAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Component_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "NamedElement_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.DefaultNamedElementNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Component_PackagedElementShape_CCN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Substitution_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.SubstitutionAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Model_PackagedElementCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ModelPackageableElementCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Reception_InterfaceReceptionLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ReceptionInInterfaceEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Link_DescriptorEdge":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.LinkDescriptorEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Component_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentFloatingLabelEditPartPCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Constraint_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ConstraintNameEditPartPCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Constraint_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ConstraintNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceNameEditPartPCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Property_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PropertyPartNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_ClassifierNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.RectangleInterfaceNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Dependency_BranchEdge":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.DependencyBranchEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "PapyrusUMLComponentDiagram":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentDiagramEditPart(
					(org.eclipse.gmf.runtime.notation.Diagram) view);
		case "Model_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ModelEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Substitution_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.SubstitutionEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Model_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ModelNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Usage_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.UsageNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_OperationCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceOperationCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Connector_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ConnectorNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InterfaceRealization_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceRealizationNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Package_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PackageEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Model_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ModelNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_ClassifierShape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.RectangleInterfaceEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Usage_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.UsageAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Port_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PortAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_OperationCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceOperationCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Component_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Dependency_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.DependencyNodeFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ComponentRealization_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentRealizationEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Comment_BodyLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.CommentBodyEditPartPCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_ClassifierShape":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.RectangleInterfaceEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Constraint_BodyLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ConstraintSpecificationEditPartPCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Manifestation_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ManifestationNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Package_PackagedElementCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PackagePackageableElementCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Operation_InterfaceOperationLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.OperationForInterfaceEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Interface_ClassifierNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.RectangleInterfaceNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_AttributeCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceAttributeCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "ComponentRealization_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentRealizationAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Manifestation_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ManifestationAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Comment_BodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.CommentBodyEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Package_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PackageNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Property_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PropertyPartEditPartCN(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InterfaceRealization_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceRealizationEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Interface_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceFloatingLabelEditPartPCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Component_NameLabel_CCN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_AttributeCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceAttributeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Dependency_MultiNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.MultiDependencyLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Comment_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.CommentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Dependency_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.DependencyAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_ClassifierFloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.RectangleInterfaceFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Component_PackagedElementShape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Generalization_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.GeneralizationAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Port_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PortNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InterfaceRealization_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceRealizationAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Generalization_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.GeneralizationEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Component_FloatingNameLabel_CCN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Port_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PortEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ComponentRealization_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentRealizationNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Abstraction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.AbstractionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Dependency_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.DependencyNodeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Dependency_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.DependencyEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Model_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ModelEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Package_PackagedElementCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PackagePackageableElementCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Interface_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Link_InterfacePortEdge":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfacePortLinkEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Component_StructureCompartment_CCN":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentCompositeCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Substitution_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.SubstitutionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceEditPart(
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
