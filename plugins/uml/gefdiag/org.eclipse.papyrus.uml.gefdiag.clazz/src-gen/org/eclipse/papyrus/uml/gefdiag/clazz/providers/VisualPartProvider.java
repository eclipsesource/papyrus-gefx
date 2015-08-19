package org.eclipse.papyrus.uml.gefdiag.clazz.providers;

public class VisualPartProvider extends org.eclipse.papyrus.gef4.provider.AbstractVisualPartProvider {

	@Override
	public org.eclipse.gef4.mvc.parts.IContentPart<javafx.scene.Node, ? extends javafx.scene.Node> createContentPart(org.eclipse.gmf.runtime.notation.View view) {
		switch(view.getType()) {
		case "5020":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ModelNameEditPartTN((org.eclipse.gmf.runtime.notation.View)view);
		case "PapyrusGEF4ClassDiagram":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ModelEditPart((org.eclipse.gmf.runtime.notation.Diagram)view);
		case "5023":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.EnumerationNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "5026":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PackageNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "5032":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "5153":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TimeObservationFloatingNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6001":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeAssociationEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "5154":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TimeObservationStereotypeLabelEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "5155":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DurationObservationFloatingNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6003":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationTargetNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "5035":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "5156":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DurationObservationStereotypeLabelEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6002":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "5157":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DefaultNamedElementNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6005":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationSourceNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "5037":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConstraintNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "0":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DiagramNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "1":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.MultiDependencyLabelEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "5029":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6010":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeSubstitutionEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6012":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeRealizationEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6011":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SubstitutionNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6014":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AbstractionNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6013":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.RealizationNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6016":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.UsageNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6015":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeAbstractionEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "5161":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationItemNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "5159":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConstraintBodyEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "5038":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.CommentBodyEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6007":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotyperGeneralizationEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6009":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceRealizationNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6008":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeInterfaceRealizationEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6021":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeElementImportEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6020":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ElementImportAliasEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6023":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.BindingSubstitutionEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6022":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypePackageImportEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6024":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationBranchRoleEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6027":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeDependencyEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6026":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DependencyNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6017":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeUsageEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6032":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassRoleTargetEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6031":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassRoleSourceEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "7002":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentAttributeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "6034":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationMultiplicityTargetEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "5066":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "7001":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InstanceSpecificationSlotCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "6033":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationMultiplicitySourceEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "7004":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentNestedClassifierCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "6036":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeTemplateBindingEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "5067":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConstraintLabelEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "7003":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentOperationCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "6035":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationBranchMutliplicityEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "7006":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceAttributeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "6038":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TargetISLinkLabelEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "7005":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SignalAttributeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "6037":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeGeneralizationSetLabelEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6030":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypePackageMergeEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "7015":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.EnumerationEnumerationLiteralCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "7014":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.RedefinableTemplateSignatureTemplateParameterCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "7017":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassAttributeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "7016":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PackagePackageableElementCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "2001":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InstanceSpecificationEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "6041":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationFlowAppliedStereotypeEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "6040":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationFlowConveyedLabelEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "7008":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceNestedClassifierCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "7007":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceOperationCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "6039":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SourceISLinkLabelEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "7009":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ModelPackageableElementCompartmentEditPartTN((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "7021":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeOperationCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "2012":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.CommentEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "2011":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConstraintEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "2010":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "7020":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeAttributeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "2009":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "2008":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "2007":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PackageEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "2006":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.EnumerationEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "2005":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ModelEditPartTN((org.eclipse.gmf.runtime.notation.Shape)view);
		case "2004":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "2003":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SignalEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "2002":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "7019":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassNestedClassifierCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "7018":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassOperationCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "7034":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassAttributeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "7037":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassNestedClassifierCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "7036":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassOperationCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "7039":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeAttributeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "7038":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TemplateSignatureTemplateParameterCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "2016":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ShortCutDiagramEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "2015":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationNodeEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "2014":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DependencyNodeEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "2013":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3002":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PropertyForComponentEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "7040":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeOperationCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode)view);
		case "3013":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.OperationForClassEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3012":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PropertyForClassEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3011":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ReceptionEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3010":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3009":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PackageEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3008":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedClassForInterfaceEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3007":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.OperationForInterfaceEditpart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3006":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PropertyForInterfaceEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3005":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PropertyForSignalEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3004":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedClassForComponentEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3003":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.OperationForComponentEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3024":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ModelEditPartTN((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3023":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3022":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SignalEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3021":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3020":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InstanceSpecificationEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3019":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.OperationForDataTypeEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3018":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PropertyforDataTypeEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3017":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.EnumerationLiteralEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3016":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TemplateParameterEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3015":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.RedefinableTemplateSignatureEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3014":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedClassForClassEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "4001":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "4002":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.GeneralizationEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "4003":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceRealizationEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "3035":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.OperationTemplateParameterEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3034":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConnectableElementTemplateParameterEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3033":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TemplateSignatureEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3031":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassifierTemplateParameterEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3030":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SlotEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3029":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConstraintEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3028":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.CommentEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3027":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3026":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3025":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.EnumerationEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "4010":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PackageImportEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "4011":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PackageMergeEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "4012":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ProfileApplicationEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "4013":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.CommentAnnotatedElementEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "4014":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConstraintConstrainedElementEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "3046":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedPrimitiveTypeForComponentEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3045":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedDataTypeForComponentEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3044":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedDataTypeForClassEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3043":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedDataTypeForInterfaceEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3042":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.OperationForPrimitiveTypeEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3041":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PropertyforPrimitiveTypeEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3040":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationItemEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3039":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ReceptionInInterfaceEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3038":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedInterfaceForInterfaceEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3037":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedInterfaceForComponentEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3036":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedInterfaceForClassEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "4004":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SubstitutionEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "4005":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.RealizationEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "4006":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AbstractionEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "4007":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.UsageEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "4008":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DependencyEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "4009":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ElementImportEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "4020":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.GeneralizationSetEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "4021":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InstanceSpecificationLinkEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "8501":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ContextLinkAppliedStereotypeEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "8502":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeFloatingNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "4023":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ContainmentLinkEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "4024":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConnectorTimeObservationEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "4025":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConnectorDurationObservationEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "8500":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ContextLinkEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "3057":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedComponentForComponentEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3056":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedComponentForInterfaceEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3055":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedComponentForClassEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3054":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedEnumerationForInterfaceEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3053":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedEnumerationForComponentEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3052":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedEnumerationForClassEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3051":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedSignalForComponentEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3050":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedSignalForClassEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3049":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedSignalForInterfaceEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3048":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedPrimitiveTypeForInterfaceEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "3047":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedPrimitiveTypeForClassEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "4015":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TemplateBindingEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "4016":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassDashedLinkEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "4017":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassLinkEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "4018":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DependencyBranchEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "4019":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationBranchEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "8512":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationItemFloatingNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "5002":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InstanceSpecificationNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "8510":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassFloatingNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "8511":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeFloatingNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "2099":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationItemEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "2097":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DefaultNamedElementEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "2096":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TimeObservationEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "2095":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DurationObservationEditPart((org.eclipse.gmf.runtime.notation.Shape)view);
		case "8505":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InstanceSpecificationFloatingNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "4026":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationFlowEditPart((org.eclipse.gmf.runtime.notation.Connector)view);
		case "8506":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SignalFloatingNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "8503":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentFloatingNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "8504":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassFloatingNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "8507":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceFloatingNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "8508":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.EnumerationFloatingNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "5011":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "8521":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationFloatingNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "8522":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DependencyFloatingNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "5005":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		case "5008":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SignalNameEditPart((org.eclipse.gmf.runtime.notation.View)view);
		default:
			// System.out.println("View not supported: " + view);
			return (org.eclipse.gef4.mvc.parts.IContentPart<javafx.scene.Node, ? extends javafx.scene.Node>)new org.eclipse.gmf.runtime.notation.util.NotationSwitch() {

				@Override
				public Object caseDecorationNode(org.eclipse.gmf.runtime.notation.DecorationNode object) {
					return new org.eclipse.papyrus.uml.gefdiag.common.parts.NamedElementLabelContentPart(object);
				}

				@Override
				public Object caseShape(org.eclipse.gmf.runtime.notation.Shape object) {
					return new org.eclipse.papyrus.gef4.parts.NodeContentPart(object);
				}

				@Override
				public Object caseBasicCompartment(org.eclipse.gmf.runtime.notation.BasicCompartment object) {
					return new org.eclipse.papyrus.gef4.parts.ListCompartmentContentPart<org.eclipse.gmf.runtime.notation.DecorationNode>(object);
				}
			}.doSwitch(view);
		// return new EmptyContentPart(view);
		}
	}
}
