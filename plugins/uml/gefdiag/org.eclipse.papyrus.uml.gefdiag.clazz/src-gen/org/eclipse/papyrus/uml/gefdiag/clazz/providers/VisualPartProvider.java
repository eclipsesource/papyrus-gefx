package org.eclipse.papyrus.uml.gefdiag.clazz.providers;

public class VisualPartProvider extends org.eclipse.papyrus.gef4.provider.AbstractVisualPartProvider {

	@Override
	public org.eclipse.gef4.mvc.parts.IContentPart<javafx.scene.Node, ? extends javafx.scene.Node> createContentPart(final org.eclipse.gmf.runtime.notation.View view) {
		switch (view.getType()) {
		case "5020":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ModelNameEditPartTN(view);
		case "5023":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.EnumerationNameEditPart(view);
		case "5026":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PackageNameEditPart(view);
		case "5032":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeNameEditPart(view);
		case "5153":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TimeObservationFloatingNameEditPart(view);
		case "6001":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeAssociationEditPart(view);
		case "5154":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TimeObservationStereotypeLabelEditPart(view);
		case "5155":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DurationObservationFloatingNameEditPart(view);
		case "6003":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationTargetNameEditPart(view);
		case "5035":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeNameEditPart(view);
		case "5156":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DurationObservationStereotypeLabelEditPart(view);
		case "6002":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationNameEditPart(view);
		case "5157":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DefaultNamedElementNameEditPart(view);
		case "6005":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationSourceNameEditPart(view);
		case "5037":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConstraintNameEditPart(view);
		case "0":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DiagramNameEditPart(view);
		case "1":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.MultiDependencyLabelEditPart(view);
		case "5029":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassNameEditPart(view);
		case "6010":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeSubstitutionEditPart(view);
		case "6012":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeRealizationEditPart(view);
		case "6011":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SubstitutionNameEditPart(view);
		case "6014":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AbstractionNameEditPart(view);
		case "6013":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.RealizationNameEditPart(view);
		case "6016":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.UsageNameEditPart(view);
		case "6015":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeAbstractionEditPart(view);
		case "5161":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationItemNameEditPart(view);
		case "5159":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConstraintBodyEditPart(view);
		case "5038":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.CommentBodyEditPart(view);
		case "6007":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotyperGeneralizationEditPart(view);
		case "6009":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceRealizationNameEditPart(view);
		case "6008":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeInterfaceRealizationEditPart(view);
		case "6021":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeElementImportEditPart(view);
		case "6020":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ElementImportAliasEditPart(view);
		case "6023":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.BindingSubstitutionEditPart(view);
		case "6022":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypePackageImportEditPart(view);
		case "6024":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationBranchRoleEditPart(view);
		case "6027":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeDependencyEditPart(view);
		case "6026":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DependencyNameEditPart(view);
		case "6017":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeUsageEditPart(view);
		case "6032":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassRoleTargetEditPart(view);
		case "6031":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassRoleSourceEditPart(view);
		case "7002":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentAttributeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "6034":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationMultiplicityTargetEditPart(view);
		case "5066":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassNameEditPart(view);
		case "7001":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InstanceSpecificationSlotCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "6033":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationMultiplicitySourceEditPart(view);
		case "7004":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentNestedClassifierCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "6036":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeTemplateBindingEditPart(view);
		case "5067":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConstraintLabelEditPart(view);
		case "7003":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentOperationCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "6035":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationBranchMutliplicityEditPart(view);
		case "7006":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceAttributeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "6038":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TargetISLinkLabelEditPart(view);
		case "7005":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SignalAttributeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "6037":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypeGeneralizationSetLabelEditPart(view);
		case "6030":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AppliedStereotypePackageMergeEditPart(view);
		case "7015":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.EnumerationEnumerationLiteralCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "7014":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.RedefinableTemplateSignatureTemplateParameterCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "7017":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassAttributeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "7016":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PackagePackageableElementCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "2001":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InstanceSpecificationEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "6041":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationFlowAppliedStereotypeEditPart(view);
		case "6040":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationFlowConveyedLabelEditPart(view);
		case "7008":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceNestedClassifierCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "7007":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceOperationCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "6039":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SourceISLinkLabelEditPart(view);
		case "7009":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ModelPackageableElementCompartmentEditPartTN((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "7021":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeOperationCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "2012":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.CommentEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2011":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConstraintEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2010":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "7020":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeAttributeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "2009":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2008":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2007":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PackageEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2006":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.EnumerationEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2005":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ModelEditPartTN((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2004":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2003":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SignalEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2002":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "7019":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassNestedClassifierCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "7018":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassOperationCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "7034":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassAttributeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "7037":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassNestedClassifierCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "7036":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassOperationCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "7039":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeAttributeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "7038":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TemplateSignatureTemplateParameterCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "2016":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ShortCutDiagramEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2015":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationNodeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2014":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DependencyNodeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2013":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3002":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PropertyForComponentEditPart(view);
		case "7040":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeOperationCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "3013":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.OperationForClassEditPart(view);
		case "3012":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PropertyForClassEditPart(view);
		case "3011":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ReceptionEditPart(view);
		case "3010":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3009":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PackageEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3008":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedClassForInterfaceEditPart(view);
		case "3007":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.OperationForInterfaceEditpart(view);
		case "3006":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PropertyForInterfaceEditPart(view);
		case "3005":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PropertyForSignalEditPart(view);
		case "3004":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedClassForComponentEditPart(view);
		case "3003":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.OperationForComponentEditPart(view);
		case "3024":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ModelEditPartTN((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3023":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3022":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SignalEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3021":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3020":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InstanceSpecificationEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3019":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.OperationForDataTypeEditPart(view);
		case "3018":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PropertyforDataTypeEditPart(view);
		case "3017":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.EnumerationLiteralEditPart(view);
		case "3016":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TemplateParameterEditPart(view);
		case "3015":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.RedefinableTemplateSignatureEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3014":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedClassForClassEditPart(view);
		case "4001":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4002":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.GeneralizationEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4003":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceRealizationEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "3035":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.OperationTemplateParameterEditPart(view);
		case "3034":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConnectableElementTemplateParameterEditPart(view);
		case "3033":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TemplateSignatureEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3031":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassifierTemplateParameterEditPart(view);
		case "3030":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SlotEditPart(view);
		case "3029":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConstraintEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3028":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.CommentEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3027":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3026":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3025":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.EnumerationEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "4010":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PackageImportEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4011":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PackageMergeEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4012":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ProfileApplicationEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4013":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.CommentAnnotatedElementEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4014":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConstraintConstrainedElementEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "3046":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedPrimitiveTypeForComponentEditPart(view);
		case "3045":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedDataTypeForComponentEditPart(view);
		case "3044":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedDataTypeForClassEditPart(view);
		case "3043":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedDataTypeForInterfaceEditPart(view);
		case "3042":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.OperationForPrimitiveTypeEditPart(view);
		case "3041":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PropertyforPrimitiveTypeEditPart(view);
		case "3040":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationItemEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3039":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ReceptionInInterfaceEditPart(view);
		case "3038":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedInterfaceForInterfaceEditPart(view);
		case "3037":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedInterfaceForComponentEditPart(view);
		case "3036":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedInterfaceForClassEditPart(view);
		case "4004":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SubstitutionEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4005":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.RealizationEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4006":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AbstractionEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4007":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.UsageEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4008":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DependencyEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4009":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ElementImportEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4020":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.GeneralizationSetEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4021":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InstanceSpecificationLinkEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "8501":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ContextLinkAppliedStereotypeEditPart(view);
		case "8502":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DataTypeFloatingNameEditPart(view);
		case "4023":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ContainmentLinkEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4024":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConnectorTimeObservationEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4025":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ConnectorDurationObservationEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "8500":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ContextLinkEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "3057":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedComponentForComponentEditPart(view);
		case "3056":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedComponentForInterfaceEditPart(view);
		case "3055":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedComponentForClassEditPart(view);
		case "3054":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedEnumerationForInterfaceEditPart(view);
		case "3053":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedEnumerationForComponentEditPart(view);
		case "3052":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedEnumerationForClassEditPart(view);
		case "3051":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedSignalForComponentEditPart(view);
		case "3050":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedSignalForClassEditPart(view);
		case "3049":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedSignalForInterfaceEditPart(view);
		case "3048":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedPrimitiveTypeForInterfaceEditPart(view);
		case "3047":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.NestedPrimitiveTypeForClassEditPart(view);
		case "4015":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TemplateBindingEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4016":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassDashedLinkEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4017":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassLinkEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4018":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DependencyBranchEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4019":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationBranchEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "8512":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationItemFloatingNameEditPart(view);
		case "5002":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InstanceSpecificationNameEditPart(view);
		case "8510":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassFloatingNameEditPart(view);
		case "8511":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PrimitiveTypeFloatingNameEditPart(view);
		case "2099":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationItemEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2097":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DefaultNamedElementEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2096":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TimeObservationEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2095":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DurationObservationEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "8505":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InstanceSpecificationFloatingNameEditPart(view);
		case "4026":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InformationFlowEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "8506":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SignalFloatingNameEditPart(view);
		case "8503":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentFloatingNameEditPart(view);
		case "8504":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationClassFloatingNameEditPart(view);
		case "8507":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceFloatingNameEditPart(view);
		case "8508":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.EnumerationFloatingNameEditPart(view);
		case "PapyrusUMLClassDiagram":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ModelEditPart((org.eclipse.gmf.runtime.notation.Diagram) view);
		case "5011":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.InterfaceNameEditPart(view);
		case "8521":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationFloatingNameEditPart(view);
		case "8522":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.DependencyFloatingNameEditPart(view);
		case "5005":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ComponentNameEditPart(view);
		case "5008":
			return new org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.SignalNameEditPart(view);
		default:
			// System.out.println("View not supported: " + view);
			return (org.eclipse.gef4.mvc.parts.IContentPart<javafx.scene.Node, ? extends javafx.scene.Node>) new org.eclipse.gmf.runtime.notation.util.NotationSwitch() {

				@Override
				public Object caseDecorationNode(final org.eclipse.gmf.runtime.notation.DecorationNode object) {
					return new org.eclipse.papyrus.uml.gefdiag.common.parts.NamedElementLabelContentPart(object);
				}

				@Override
				public Object caseShape(final org.eclipse.gmf.runtime.notation.Shape object) {
					return new org.eclipse.papyrus.gef4.parts.NodeContentPart(object);
				}

				@Override
				public Object caseBasicCompartment(final org.eclipse.gmf.runtime.notation.BasicCompartment object) {
					return new org.eclipse.papyrus.gef4.parts.ListCompartmentContentPart<org.eclipse.gmf.runtime.notation.DecorationNode>(object);
				}
			}.doSwitch(view);
		// return new EmptyContentPart(view);
		}
	}
}
