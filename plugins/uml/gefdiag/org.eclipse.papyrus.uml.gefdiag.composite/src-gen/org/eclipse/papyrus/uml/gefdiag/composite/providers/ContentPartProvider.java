package org.eclipse.papyrus.uml.gefdiag.composite.providers;

import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.papyrus.gef4.parts.ConnectionContentPart;
import org.eclipse.papyrus.uml.gefdiag.common.provider.AbstractUMLContentPartProvider;

import javafx.scene.Node;

public class ContentPartProvider extends AbstractUMLContentPartProvider {

	@Override
	public IContentPart<? extends Node> createContentPart(org.eclipse.gmf.runtime.notation.View view) {
		switch (view.getType()) {
		case "3082":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeviceCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3081":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ComponentCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3080":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DataTypeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "5142":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeObservationNameEditPart(view);
		case "6111":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.StringExpressionFloatingLabelEditPart(view);
		case "5143":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeObservationStereotypeLabelEditPart(view);
		case "6110":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralUnlimitedNaturalFloatingLabelEditPart(view);
		case "6113":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeExpressionFloatingLabelEditPart(view);
		case "6112":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.OpaqueExpressionFloatingLabelEditPart(view);
		case "6115":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationFloatingLabelEditPart(view);
		case "6114":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ExpressionFloatingLabelEditPart(view);
		case "3088":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ParameterEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3087":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InterfaceEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3086":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3085":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ClassCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3084":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.NodeCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3083":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ExecutionEnvironmentCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "6106":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralBooleanFloatingLabelEditPart(view);
		case "6105":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeEventFloatingLabelEditPart(view);
		case "6108":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralNullFloatingLabelEditPart(view);
		case "6107":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralIntegerFloatingLabelEditPart(view);
		case "6109":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralStringFloatingLabelEditPart(view);
		case "3093":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ArtifactEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "5151":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationObservationNameEditPart(view);
		case "3092":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeploymentSpecificationEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "5152":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationObservationStereotypeLabelEditPart(view);
		case "3091":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActorEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "6001":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ComponentRealizationNameEditPart(view);
		case "6003":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SubstitutionNameEditPart(view);
		case "5156":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ClassCompositeNameEditPart(view);
		case "6002":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InterfaceRealizationNameEditPart(view);
		case "6005":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ManifestationNameEditPart(view);
		case "5158":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationCompositeNameEditPart(view);
		case "6004":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.RealizationNameEditPart(view);
		case "3097":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CommentEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3096":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.UseCaseEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3095":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SignalEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3094":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InformationItemEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "6117":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationIntervalFloatingLabelEditPart(view);
		case "6116":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeIntervalFloatingLabelEditPart(view);
		case "6119":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InstanceValueFloatingLabelEditPart(view);
		case "6118":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.IntervalFloatingLabelEditPart(view);
		case "5162":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ArtifactNameEditPart(view);
		case "5163":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InformationItemNameEditPart(view);
		case "5164":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SignalNameEditPart(view);
		case "5165":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.UseCaseNameEditPart(view);
		case "5166":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SignalEventNameEditPart(view);
		case "5167":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CallEventNameEditPart(view);
		case "5168":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.AnyReceiveEventNameEditPart(view);
		case "6016":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InterfaceRealizationAppliedStereotypeEditPart(view);
		case "6015":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ComponentRealizationAppliedStereotypeEditPart(view);
		case "5160":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActorNameEditPart(view);
		case "5161":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeploymentSpecificationNameEditPart(view);
		case "5159":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InterfaceNameEditPart(view);
		case "6007":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.UsageNameEditPart(view);
		case "6006":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.AbstractionNameEditPart(view);
		case "6009":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DependencyNameEditPart(view);
		case "6008":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeploymentNameEditPart(view);
		case "6021":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.UsageAppliedStereotypeEditPart(view);
		case "6020":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.AbstractionAppliedStereotypeEditPart(view);
		case "6023":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DependencyAppliedStereotypeEditPart(view);
		case "6022":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeploymentAppliedStereotypeEditPart(view);
		case "6025":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConnectorAppliedStereotypeEditPart(view);
		case "5178":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralBooleanNameEditPart(view);
		case "6024":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.GeneralizationAppliedStereotypeEditPart(view);
		case "5179":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralIntegerNameEditPart(view);
		case "6027":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.RoleBindingRoleNameEditPart(view);
		case "2100":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.StringExpressionEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "5171":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ChangeEventNameEditPart(view);
		case "5172":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeEventNameEditPart(view);
		case "6018":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.RealizationAppliedStereotypeEditPart(view);
		case "6017":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SubstitutionAppliedStereotypeEditPart(view);
		case "6019":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ManifestationAppliedStereotypeEditPart(view);
		case "5184":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.OpaqueExpressionNameEditPart(view);
		case "6032":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InformationFlowAppliedStereotypeEditPart(view);
		case "5185":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeExpressionNameEditPart(view);
		case "6031":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InformationFlowConveyedLabelEditPart(view);
		case "6034":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ParameterAppliedStereotypeEditPart(view);
		case "5186":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ExpressionNameEditPart(view);
		case "6033":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ParameterNameEditPart(view);
		case "5187":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationNameEditPart(view);
		case "5188":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeIntervalNameEditPart(view);
		case "6036":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeConstraintSpecificationEditPart(view);
		case "5189":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationIntervalNameEditPart(view);
		case "6035":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationConstraintSpecificationEditPart(view);
		case "6038":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionConstraintSpecificationEditPart(view);
		case "6037":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.IntervalConstraintSpecificationEditPart(view);
		case "2111":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeConstraintEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2110":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationConstraintEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "5180":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralNullNameEditPart(view);
		case "5181":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralStringNameEditPart(view);
		case "5182":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralUnlimitedNaturalNameEditPart(view);
		case "6030":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.RepresentationTagLabelEditPart(view);
		case "5183":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.StringExpressionNameEditPart(view);
		case "2108":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InstanceValueEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2107":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.IntervalEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2106":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationIntervalEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2105":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeIntervalEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2104":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2103":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ExpressionEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2102":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeExpressionEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2101":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.OpaqueExpressionEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "6029":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PortAppliedStereotypeEditPart(view);
		case "6028":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.RoleBindingAppliedStereotypeEditPart(view);
		case "2109":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CommentEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "5195":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.IntervalConstraintNameEditPart(view);
		case "5196":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionConstraintNameEditPart(view);
		case "5197":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConstraintNameEditPart(view);
		case "5198":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationRoleNameEditPartCN(view);
		case "5190":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.IntervalNameEditPart(view);
		case "5191":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InstanceValueNameEditPart(view);
		case "5192":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CommentBodyEditPart(view);
		case "5193":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationConstraintNameEditPart(view);
		case "5194":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeConstraintNameEditPart(view);
		case "2114":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConstraintEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2113":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionConstraintEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2112":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.IntervalConstraintEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "6039":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConstraintSpecificationEditPart(view);
		case "6054":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PropertyPartFloatingLabelEditPartCN(view);
		case "6053":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.BehaviorPortFloatingLabelEditPart(view);
		case "6056":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationUseFloatingLabelEditPartCN(view);
		case "6055":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationRoleFloatingLabelEditPartCN(view);
		case "3101":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PropertyEditPartCLN(view);
		case "6050":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConnectorNameEditPart(view);
		case "6052":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConnectorMultiplicityTargetEditPart(view);
		case "6051":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConnectorMultiplicitySourceEditPart(view);
		case "7033":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DataTypeAttributeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "7034":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DataTypeOperationCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "3102":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.OperationEditPartCLN(view);
		case "7048":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.EnumerationEnumerationLiteralCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "6079":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActivityCompositeFloatingLabelEditPart(view);
		case "3121":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.BehaviorPortEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3120":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConstraintEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3119":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionConstraintEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3118":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.IntervalConstraintEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3117":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeConstraintEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3116":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationConstraintEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3115":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationRoleEditPartCN((org.eclipse.gmf.runtime.notation.Shape) view);
		case "6087":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ExecutionEnvironmentCompositeFloatingLabelEditPart(view);
		case "6086":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeviceCompositeFloatingLabelEditPart(view);
		case "6089":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ClassCompositeFloatingLabelEditPart(view);
		case "6088":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.NodeCompositeFloatingLabelEditPart(view);
		case "6081":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ProtocolStateMachineCompositeFloatingLabelEditPart(view);
		case "6080":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionCompositeFloatingLabelEditPart(view);
		case "6083":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.FunctionBehaviorCompositeFloatingLabelEditPart(view);
		case "6082":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.StateMachineCompositeFloatingLabelEditPart(view);
		case "6085":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ComponentCompositeFloatingLabelEditPart(view);
		case "6084":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.OpaqueBehaviorCompositeFloatingLabelEditPart(view);
		case "7066":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.StateMachineCompositeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "6098":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InformationItemFloatingLabelEditPart(view);
		case "7065":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ProtocolStateMachineCompositeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "6097":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ArtifactFloatingLabelEditPart(view);
		case "7068":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.OpaqueBehaviorCompositeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "7067":
		case "7054":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.FunctionBehaviorCompositeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "6099":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SignalFloatingLabelEditPart(view);
		case "7069":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ComponentCompositeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "6090":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationCompositeFloatingLabelEditPart(view);
		case "6092":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PrimitiveTypeFloatingLabelEditPart(view);
		case "6091":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InterfaceFloatingLabelEditPart(view);
		case "6094":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DataTypeFloatingLabelEditPart(view);
		case "6093":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.EnumerationFloatingLabelEditPart(view);
		case "7064":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionCompositeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "6096":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeploymentSpecificationFloatingLabelEditPart(view);
		case "7063":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActivityCompositeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "6095":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActorFloatingLabelEditPart(view);
		case "7077":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PropertyPartCompartmentEditPartCN((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "4001":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LinkDescriptorEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4002":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CommentAnnotatedElementEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4003":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConstraintConstrainedElementEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "2067":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.EnumerationEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2066":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PrimitiveTypeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2065":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.OpaqueBehaviorCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "7071":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ExecutionEnvironmentCompositeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "2064":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.FunctionBehaviorCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "7070":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeviceCompositeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "2063":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.StateMachineCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "7073":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ClassCompositeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "2062":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ProtocolStateMachineCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "7072":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.NodeCompositeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "2061":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "7075":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationCompositeCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "2060":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActivityCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2070":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeviceCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "4010":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DependencyEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4011":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SubstitutionEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4012":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ManifestationEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4013":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConnectorEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "2078":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeploymentSpecificationEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2077":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActorEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2076":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InterfaceEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2075":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2073":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ClassCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2072":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.NodeCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2071":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ExecutionEnvironmentCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2069":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ComponentCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2068":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DataTypeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "4004":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ComponentRealizationEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4005":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InterfaceRealizationEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4006":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.RealizationEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4007":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.AbstractionEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4008":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.UsageEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4009":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeploymentEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "2081":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SignalEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2080":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InformationItemEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "4020":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.RepresentationEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4021":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InformationFlowEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4022":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.BehaviorPortLinkEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "5112":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActivityCompositeNameEditPart(view);
		case "5113":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionCompositeNameEditPart(view);
		case "5114":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ProtocolStateMachineCompositeNameEditPart(view);
		case "2089":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeEventEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2088":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ChangeEventEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2085":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.AnyReceiveEventEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2084":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CallEventEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2083":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SignalEventEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2082":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.UseCaseEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "CompositeStructure":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CompositeStructureDiagramEditPart((org.eclipse.gmf.runtime.notation.Diagram) view);
		case "2079":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ArtifactEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "4015":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.GeneralizationEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4017":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.RoleBindingEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4018":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeObservationEventEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "4019":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationObservationEventEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "5120":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DataTypeNameEditPart(view);
		case "5121":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ComponentCompositeNameEditPart(view);
		case "5122":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeviceCompositeNameEditPart(view);
		case "5123":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ExecutionEnvironmentCompositeNameEditPart(view);
		case "5124":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.NodeCompositeNameEditPart(view);
		case "5125":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PortNameEditPart(view);
		case "2099":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralUnlimitedNaturalEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3066":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.EnumerationLiteralEditPartCLN(view);
		case "2098":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralStringEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2097":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralNullEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2096":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralIntegerEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2095":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralBooleanEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2094":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeObservationEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "2093":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationObservationEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "5115":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.StateMachineCompositeNameEditPart(view);
		case "5116":
		case "5132":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.FunctionBehaviorCompositeNameEditPart(view);
		case "5117":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.OpaqueBehaviorCompositeNameEditPart(view);
		case "5118":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PrimitiveTypeNameEditPart(view);
		case "5119":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.EnumerationNameEditPart(view);
		case "3071":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationUseEditPartCN((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3070":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PropertyPartEditPartCN((org.eclipse.gmf.runtime.notation.Shape) view);
		case "6100":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.UseCaseFloatingLabelEditPart(view);
		case "6102":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CallEventFloatingLabelEditPart(view);
		case "6101":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SignalEventFloatingLabelEditPart(view);
		case "6104":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ChangeEventFloatingLabelEditPart(view);
		case "6103":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.AnyReceiveEventFloatingLabelEditPart(view);
		case "3079":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.EnumerationEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3078":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PrimitiveTypeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3077":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.OpaqueBehaviorCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3076":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.FunctionBehaviorCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3075":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.StateMachineCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3074":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ProtocolStateMachineCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3073":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3072":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActivityCompositeEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "3069":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PortEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "5126":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PropertyPartNameEditPartCN(view);
		case "5127":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationUseNameEditPart(view);
		case "StereotypeCommentLink":
			return new ConnectionContentPart<Connector>((Connector) view);
		default:
			return super.createContentPart(view);
		}
	}
}
