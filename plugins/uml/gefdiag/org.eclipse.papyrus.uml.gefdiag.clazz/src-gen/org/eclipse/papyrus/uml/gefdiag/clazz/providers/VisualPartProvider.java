package org.eclipse.papyrus.uml.gefdiag.clazz.providers;

import org.eclipse.papyrus.uml.gefdiag.common.provider.AbstractUMLVisualPartProvider;

public class VisualPartProvider extends AbstractUMLVisualPartProvider {

	@Override
	public org.eclipse.gef4.mvc.parts.IContentPart<javafx.scene.Node, ? extends javafx.scene.Node> createContentPart(
			org.eclipse.gmf.runtime.notation.View view) {
		switch (view.getType()) {

		case "Association_TargetMultiplicityLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationMultiplicityTargetEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DataType_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Association_SourceRoleLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationSourceNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Comment_AnnotatedElementEdge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.CommentAnnotatedElementEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Class_InterfaceNestedClassifierLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedClassForInterfaceEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Element_ContainmentEdge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ContainmentLinkEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Property_SignalAttributeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PropertyForSignalEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Comment_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.CommentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InformationItem_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationItemFloatingNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Usage_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.UsageEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "ProfileApplication_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ProfileApplicationEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Class_NestedClassifierCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassNestedClassifierCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "PrimitiveType_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Component_InterfaceNestedClassifierLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedComponentForInterfaceEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Operation_ClassOperationLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.OperationForClassEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Signal_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SignalEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "PrimitiveType_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Signal_InterfaceNestedClassifierLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedSignalForInterfaceEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InformationItem_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationItemFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "PrimitiveType_OperationCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeOperationCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Usage_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.UsageNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Class_OperationCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassOperationCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Interface_OperationCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceOperationCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Class_ClassNestedClassifierLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedClassForClassEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "GeneralizationSet_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.GeneralizationSetEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Interface_NestedClassifierCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceNestedClassifierCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Usage_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeUsageEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InformationFlow_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationFlowAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DataType_OperationCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeOperationCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "AssociationClass_TargetRoleLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassRoleTargetEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InformationItem_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationItemEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "AssociationClass_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_NestedClassifierCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceNestedClassifierCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "InformationItem_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationItemNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Package_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PackageNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceFloatingNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Association_TargetRoleLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationTargetNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ClassifierTemplateParameter_TemplateParameterLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassifierTemplateParameterEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Comment_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.CommentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Dependency_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeDependencyEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "AssociationClass_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassLinkEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Enumeration_ComponentNestedClassifierLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedEnumerationForComponentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "DurationObservation_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DurationObservationFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Property_InterfaceAttributeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PropertyForInterfaceEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Operation_ComponentOperationLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.OperationForComponentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Package_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PackageNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Constraint_BodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConstraintBodyEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "NamedElement_DefaultShape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DefaultNamedElementEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Abstraction_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AbstractionEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "InstanceSpecification_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InstanceSpecificationNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InstanceSpecification_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InstanceSpecificationFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Model_PackagedElementCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ModelPackageableElementCompartmentEditPartTN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "ElementImport_AliasLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ElementImportAliasEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DataType_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeFloatingNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Operation_PrimitiveTypeOperationLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.OperationForPrimitiveTypeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Association_BranchMultiplicityLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationBranchMutliplicityEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "PrimitiveType_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Substitution_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeSubstitutionEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TimeObservation_EventEdge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConnectorTimeObservationEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Class_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TemplateBinding_SubstitutionLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.BindingSubstitutionEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Component_AttributeCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentAttributeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Signal_AttributeCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SignalAttributeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Association_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Constraint_KeywordLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ContextLinkAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Substitution_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SubstitutionEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Model_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ModelNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Class_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DataType_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Association_BranchRoleLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationBranchRoleEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TemplateSignature_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TemplateSignatureEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Enumeration_LiteralCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.EnumerationEnumerationLiteralCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "DataType_AttributeCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeAttributeCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "PackageMerge_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypePackageMergeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InterfaceRealization_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceRealizationNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Package_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PackageEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "PackageMerge_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PackageMergeEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "InstanceSpecification_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InstanceSpecificationEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "TimeObservation_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TimeObservationFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Component_AttributeCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentAttributeCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Interface_OperationCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceOperationCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "PrimitiveType_AttributeCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeAttributeCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Enumeration_ClassNestedClassifierLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedEnumerationForClassEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Class_NestedClassifierCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassNestedClassifierCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Comment_BodyLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.CommentBodyEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DataType_ClassNestedClassifierLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedDataTypeForClassEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InformationFlow_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationFlowEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Comment_BodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.CommentBodyEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Component_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Reception_ReceptionLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ReceptionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Signal_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SignalEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "PrimitiveType_AttributeCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeAttributeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Class_AttributeCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassAttributeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Constraint_ContextEdge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ContextLinkEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Interface_ComponentNestedClassifierLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedInterfaceForComponentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ConnectableElementTemplateParameter_TemplateParameterLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConnectableElementTemplateParameterEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Class_OperationCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassOperationCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Generalization_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotyperGeneralizationEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Signal_ComponentNestedClassifierLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedSignalForComponentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Class_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Generalization_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.GeneralizationEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "InterfaceRealization_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeInterfaceRealizationEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InstanceSpecification_TargetRoleLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TargetISLinkLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Component_NestedClassifierCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentNestedClassifierCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "AssociationClass_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Property_PrimitiveTypeAttributeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PropertyforPrimitiveTypeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Signal_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SignalNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InformationFlow_ConveyedLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationFlowConveyedLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Dependency_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DependencyEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "ElementImport_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeElementImportEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Package_PackagedElementCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PackagePackageableElementCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Slot_SlotLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SlotEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Interface_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "PrimitiveType_InterfaceNestedClassifierLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedPrimitiveTypeForInterfaceEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Enumeration_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.EnumerationEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Association_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "AssociationClass_OperationCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassOperationCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Dependency_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DependencyNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Realization_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.RealizationNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TemplateBinding_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeTemplateBindingEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OperationTemplateParameter_TemplateParameterLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.OperationTemplateParameterEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Component_ClassNestedClassifierLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedComponentForClassEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Constraint_ConstrainedElementEdge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConstraintConstrainedElementEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Abstraction_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeAbstractionEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Component_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Realization_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.RealizationEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Diagram_ShortcutShape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ShortCutDiagramEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Component_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentFloatingNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Constraint_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConstraintNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_InterfaceNestedClassifierLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedInterfaceForInterfaceEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Enumeration_InterfaceNestedClassifierLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedEnumerationForInterfaceEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "PrimitiveType_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Property_DataTypeAttributeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PropertyforDataTypeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "DataType_InterfaceNestedClassifierLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedDataTypeForInterfaceEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "DurationObservation_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DurationObservationStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Signal_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SignalFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Operation_DataTypeOperationLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.OperationForDataTypeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "TemplateParameter_TemplateParameterLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TemplateParameterEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "DataType_OperationCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeOperationCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Model_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ModelNameEditPartTN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Dependency_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DependencyFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Component_ComponentNestedClassifierLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedComponentForComponentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Association_BranchEdge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationBranchEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "RedefinableTemplateSignature_TemplateParameterCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.RedefinableTemplateSignatureTemplateParameterCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Operation_InterfaceOperationLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.OperationForInterfaceEditpart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Association_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Component_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InstanceSpecification_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InstanceSpecificationNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TimeObservation_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TimeObservationStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Enumeration_LiteralCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.EnumerationEnumerationLiteralCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "InterfaceRealization_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceRealizationEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Interface_AttributeCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceAttributeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Dependency_MultiNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.MultiDependencyLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Association_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeAssociationEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "EnumerationLiteral_LiteralLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.EnumerationLiteralEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Component_OperationCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentOperationCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Interface_ClassNestedClassifierLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedInterfaceForClassEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Property_ComponentAttributeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PropertyForComponentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Abstraction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AbstractionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Dependency_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DependencyNodeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "AssociationClass_TetherEdge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassDashedLinkEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Association_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationNodeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "PrimitiveType_ClassNestedClassifierLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedPrimitiveTypeForClassEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Constraint_PackagedElementShape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConstraintEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Enumeration_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.EnumerationNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "AssociationClass_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InstanceSpecification_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InstanceSpecificationLinkEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Signal_AttributeCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SignalAttributeCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "GeneralizationSet_ConstraintLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConstraintLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Enumeration_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.EnumerationFloatingNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TemplateSignature_TemplateParameterCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TemplateSignatureTemplateParameterCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "PrimitiveType_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DataType_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Package_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PackageEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Enumeration_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.EnumerationNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Component_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Signal_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SignalNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Constraint_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConstraintBodyEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Component_NestedClassifierCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentNestedClassifierCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "ElementImport_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ElementImportEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "NamedElement_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DefaultNamedElementNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InformationItem_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationItemNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "AssociationClass_SourceRoleLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassRoleSourceEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "GeneralizationSet_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeGeneralizationSetLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Model_PackagedElementCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ModelPackageableElementCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Reception_InterfaceReceptionLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ReceptionInInterfaceEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Constraint_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConstraintNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Signal_ClassNestedClassifierLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedSignalForClassEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "AssociationClass_NestedClassifierCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassNestedClassifierCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "PrimitiveType_OperationCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeOperationCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Dependency_BranchEdge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DependencyBranchEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Model_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ModelEditPartTN(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InstanceSpecification_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InstanceSpecificationEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Property_ClassAttributeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PropertyForClassEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "AssociationClass_AttributeCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassAttributeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "DurationObservation_EventEdge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConnectorDurationObservationEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "PrimitiveType_ComponentNestedClassifierLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedPrimitiveTypeForComponentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "DataType_AttributeCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeAttributeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Component_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Realization_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeRealizationEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DataType_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Class_ComponentNestedClassifierLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedClassForComponentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Component_OperationCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentOperationCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "DurationObservation_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DurationObservationEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "PackageImport_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypePackageImportEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Package_PackagedElementCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PackagePackageableElementCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Interface_AttributeCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceAttributeCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "InstanceSpecification_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InstanceSpecificationFloatingNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InstanceSpecification_SlotCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InstanceSpecificationSlotCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Class_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InstanceSpecification_SourceRoleLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SourceISLinkLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "PapyrusUMLClassDiagram":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ModelEditPart(
					(org.eclipse.gmf.runtime.notation.Diagram) view);
		case "Enumeration_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.EnumerationEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Class_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Signal_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SignalFloatingNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "PrimitiveType_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeFloatingNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TimeObservation_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TimeObservationEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InformationItem_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationItemEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InstanceSpecification_SlotCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InstanceSpecificationSlotCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "RedefinableTemplateSignature_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.RedefinableTemplateSignatureEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "DataType_ComponentNestedClassifierLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedDataTypeForComponentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Class_AttributeCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassAttributeCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Model_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ModelEditPartTN(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Diagram_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DiagramNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Enumeration_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.EnumerationFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DataType_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Constraint_PackagedElementShape":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConstraintEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "TemplateBinding_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TemplateBindingEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Substitution_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SubstitutionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "PackageImport_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PackageImportEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Class_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassFloatingNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Association_SourceMultiplicityLabel":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationMultiplicitySourceEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);

		default:
			return super.createContentPart(view);
		}
	}

}
