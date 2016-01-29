package org.eclipse.papyrus.uml.gefdiag.component.providers;

import org.eclipse.papyrus.uml.gefdiag.common.provider.AbstractUMLVisualPartProvider;

public class VisualPartProvider extends AbstractUMLVisualPartProvider {

	@Override
	public org.eclipse.gef4.mvc.parts.IContentPart<javafx.scene.Node, ? extends javafx.scene.Node> createContentPart(org.eclipse.gmf.runtime.notation.View view) {
		switch (view.getType()) {
		case "6032":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceFloatingLabelEditPart(view);
		case "5262":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ModelNameEditPart(view);
		case "6031":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.RectangleInterfaceFloatingLabelEditPart(view);
		case "7002":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PackagePackageableElementCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "7001":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentCompositeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "4010":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.DependencyEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "5265":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.DefaultNamedElementNameEditPart(view);
		case "5266":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.RectangleInterfaceNameEditPart(view);
		case "4012":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.SubstitutionEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "7006":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ModelPackageableElementCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "4013":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.AbstractionEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "5268":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PropertyPartNameEditPartCN(view);
		case "4014":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ManifestationEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "3200":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PackageEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "6030":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentFloatingLabelEditPart(view);
		case "6029":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.DependencyNodeFloatingLabelEditPart(view);
		case "4006":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceRealizationEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4007":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentRealizationEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4009":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ConstraintConstrainedElementEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "PapyrusUMLComponentDiagram":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentDiagramEditPart((org.eclipse.gmf.runtime.notation.Diagram) view);
		case "1":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PropertyForInterfaceEditPart(view);
		case "2":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.GeneralizationAppliedStereotypeEditPart(view);
		case "3205":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.RectangleInterfaceEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentRealizationNameEditPart(view);
		case "3204":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.DefaultNamedElementEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "4":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentRealizationAppliedStereotypeEditPart(view);
		case "5":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.OperationForInterfaceEditPart(view);
		case "3203":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.DependencyNodeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "6":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ReceptionInInterfaceEditPart(view);
		case "3202":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ModelEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3201":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.CommentEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "7008":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceAttributeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "4015":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.CommentAnnotatedElementEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4016":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.LinkDescriptorEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4017":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.DependencyBranchEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "7009":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceOperationCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "4018":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfacePortLinkEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4019":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ConnectorEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "6010":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceRealizationNameEditPart(view);
		case "6011":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceRealizationAppliedStereotypeEditPart(view);
		case "6016":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.UsageNameEditPart(view);
		case "5004":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentNameEditPart(view);
		case "2003":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2002":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "6007":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.AbstractionNameEditPart(view);
		case "6006":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.SubstitutionNameEditPart(view);
		case "6009":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.DependencyNameEditPart(view);
		case "6008":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ManifestationNameEditPart(view);
		case "3071":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "6021":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.AbstractionAppliedStereotypeEditPart(view);
		case "3070":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ComponentEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "6020":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.SubstitutionAppliedStereotypeEditPart(view);
		case "5252":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ConstraintNameEditPart(view);
		case "6023":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.DependencyAppliedStereotypeEditPart(view);
		case "5253":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ConstraintSpecificationEditPart(view);
		case "6022":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ManifestationAppliedStereotypeEditPart(view);
		case "5254":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PackageNameEditPart(view);
		case "6025":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ConnectorNameEditPart(view);
		case "5255":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.CommentBodyEditPart(view);
		case "4001":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.UsageEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "6024":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ConnectorAppliedStereotypeEditPart(view);
		case "4003":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.GeneralizationEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "3079":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PropertyPartEditPartCN((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3078":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3199":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ConstraintEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3077":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ModelEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3076":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PackageEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3075":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.ConstraintEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3074":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.CommentEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3072":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3069":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PortEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "5005":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.InterfaceNameEditPart(view);
		case "5006":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PortNameEditPart(view);
		case "6017":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.UsageAppliedStereotypeEditPart(view);
		case "5007":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PortAppliedStereotypeEditPart(view);
		case "5008":
			return new org.eclipse.papyrus.uml.gefdiag.component.edit.parts.MultiDependencyLabelEditPart(view);
		default:
			return super.createContentPart(view);
		}
	}
}
