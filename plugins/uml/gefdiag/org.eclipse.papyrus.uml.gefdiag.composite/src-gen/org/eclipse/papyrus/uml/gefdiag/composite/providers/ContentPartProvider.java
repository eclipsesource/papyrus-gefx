package org.eclipse.papyrus.uml.gefdiag.composite.providers;

public class ContentPartProvider
		extends org.eclipse.papyrus.uml.gefdiag.common.provider.AbstractUMLContentPartProvider {

	@Override
	public org.eclipse.gef.mvc.fx.parts.IContentPart<?> createContentPart(org.eclipse.gmf.runtime.notation.View view) {
		switch (view.getType()) {

		case "Node_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.NodeCompositeNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InstanceValue_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InstanceValueFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DataType_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DataTypeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Comment_AnnotatedElementEdge":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CommentAnnotatedElementEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "IntervalConstraint_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.IntervalConstraintNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Node_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.NodeCompositeFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "StateMachine_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.StateMachineCompositeFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "CallEvent_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CallEventNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Comment_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CommentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "AnyReceiveEvent_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.AnyReceiveEventNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Property_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PropertyPartFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InformationItem_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InformationItemFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Usage_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.UsageEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "ExecutionEnvironment_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ExecutionEnvironmentCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "PrimitiveType_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PrimitiveTypeNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "CallEvent_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CallEventFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Connector_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConnectorAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Class_StructureCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ClassCompositeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Signal_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SignalEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InformationFlow_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InformationFlowNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Property_StructureCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PropertyPartCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Dependency_RoleBindingStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.RoleBindingAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "CallEvent_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CallEventEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "PrimitiveType_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PrimitiveTypeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Artifact_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ArtifactFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OpaqueBehavior_StructureCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.OpaqueBehaviorCompositeCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "StateMachine_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.StateMachineCompositeNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InformationItem_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InformationItemFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "LiteralUnlimitedNatural_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralUnlimitedNaturalNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "StringExpression_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.StringExpressionFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "LiteralInteger_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralIntegerFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InteractionConstraint_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionConstraintEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ConnectableElement_CollaborationRoleNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationRoleNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Actor_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActorEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Usage_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.UsageNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interaction_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionCompositeNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "UseCase_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.UseCaseNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "SignalEvent_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SignalEventEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Connector_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConnectorNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "FunctionBehavior_StructureCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.FunctionBehaviorCompositeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "IntervalConstraint_BodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.IntervalConstraintSpecificationEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DurationConstraint_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationConstraintEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InstanceValue_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InstanceValueEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "LiteralNull_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralNullEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "IntervalConstraint_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.IntervalConstraintEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Usage_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.UsageAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Node_StructureCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.NodeCompositeCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "InformationFlow_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InformationFlowAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DataType_OperationCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DataTypeOperationCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "ProtocolStateMachine_StructureCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ProtocolStateMachineCompositeCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "AnyReceiveEvent_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.AnyReceiveEventFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interval_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.IntervalFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Constraint_BodyLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConstraintSpecificationEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ProtocolStateMachine_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ProtocolStateMachineCompositeFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InformationItem_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InformationItemEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Manifestation_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ManifestationNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Device_StructureCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeviceCompositeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "ComponentRealization_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ComponentRealizationAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Manifestation_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ManifestationAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InformationItem_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InformationItemNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ExecutionEnvironment_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ExecutionEnvironmentCompositeFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TimeConstraint_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeConstraintEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Property_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PropertyPartEditPartCN(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InteractionConstraint_BodyLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionConstraintSpecificationEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "IntervalConstraint_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.IntervalConstraintNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InterfaceFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "StateMachine_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.StateMachineCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Comment_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CommentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Dependency_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DependencyAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Device_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeviceCompositeFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interval_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.IntervalEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Artifact_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ArtifactNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "LiteralUnlimitedNatural_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralUnlimitedNaturalEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OpaqueExpression_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.OpaqueExpressionFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Port_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PortEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Activity_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActivityCompositeNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ExecutionEnvironment_StructureCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ExecutionEnvironmentCompositeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Node_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.NodeCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Device_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeviceCompositeFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "LiteralUnlimitedNatural_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralUnlimitedNaturalFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DurationConstraint_BodyLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationConstraintSpecificationEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Constraint_BodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConstraintSpecificationEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Abstraction_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.AbstractionEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Collaboration_StructureCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationCompositeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "DataType_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DataTypeFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ProtocolStateMachine_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ProtocolStateMachineCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Node_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.NodeCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Connector_TargetMultiplicityLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConnectorMultiplicityTargetEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Artifact_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ArtifactEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Expression_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ExpressionFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Duration_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "PrimitiveType_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PrimitiveTypeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "TimeObservation_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeObservationNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Substitution_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SubstitutionAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InterfaceFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ProtocolStateMachine_StructureCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ProtocolStateMachineCompositeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "TimeObservation_EventEdge":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeObservationEventEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Class_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ClassCompositeFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TimeExpression_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeExpressionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TimeConstraint_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeConstraintNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Node_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.NodeCompositeNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DurationConstraint_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationConstraintNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Substitution_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SubstitutionEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Class_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ClassCompositeNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DataType_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DataTypeFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Artifact_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ArtifactNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InteractionConstraint_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionConstraintEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "IntervalConstraint_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.IntervalConstraintEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "FunctionBehavior_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.FunctionBehaviorCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "StringExpression_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.StringExpressionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Enumeration_LiteralCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.EnumerationEnumerationLiteralCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "DataType_AttributeCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DataTypeAttributeCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "LiteralInteger_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralIntegerNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DurationInterval_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationIntervalNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InterfaceRealization_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InterfaceRealizationNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Dependency_RoleBindingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.RoleBindingRoleNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Port_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PortAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interaction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionCompositeFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Activity_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActivityCompositeFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DurationConstraint_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationConstraintEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Comment_BodyLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CommentBodyEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ChangeEvent_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ChangeEventEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "DurationInterval_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationIntervalFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TimeConstraint_BodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeConstraintSpecificationEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Dependency_RoleBindingEdge":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.RoleBindingEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "CollaborationUse_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationUseNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "FunctionBehavior_StructureCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.FunctionBehaviorCompositeCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "TimeConstraint_BodyLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeConstraintSpecificationEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DeploymentSpecification_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeploymentSpecificationFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ConnectableElement_CollaborationRoleShape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationRoleEditPartCN(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Device_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeviceCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "LiteralNull_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralNullNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Collaboration_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationCompositeNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Deployment_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeploymentEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "InformationFlow_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InformationFlowEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Comment_BodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CommentBodyEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ExecutionEnvironment_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ExecutionEnvironmentCompositeFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Component_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ComponentCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "TimeExpression_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeExpressionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OpaqueBehavior_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.OpaqueBehaviorCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Class_StructureCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ClassCompositeCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Signal_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SignalEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Representation_KeywordLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.RepresentationTagLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Collaboration_StructureCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationCompositeCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Expression_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ExpressionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Activity_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActivityCompositeNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Actor_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActorFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Generalization_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.GeneralizationAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Class_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ClassCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Port_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PortNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InterfaceRealization_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InterfaceRealizationAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Generalization_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.GeneralizationEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Collaboration_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationCompositeNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Signal_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SignalNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Connector_SourceMultiplicityLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConnectorMultiplicitySourceEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InformationFlow_ConveyedLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InformationFlowConveyedLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Collaboration_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationCompositeFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Dependency_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DependencyEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "LiteralBoolean_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralBooleanNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Device_StructureCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeviceCompositeCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Actor_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActorNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InterfaceEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Parameter_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ParameterNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Enumeration_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.EnumerationEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InteractionConstraint_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionConstraintNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Dependency_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DependencyNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OpaqueBehavior_StructureCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.OpaqueBehaviorCompositeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "OpaqueExpression_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.OpaqueExpressionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Realization_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.RealizationNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Manifestation_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ManifestationEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Connector_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConnectorEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Component_StructureCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ComponentCompositeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "DeploymentSpecification_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeploymentSpecificationEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ProtocolStateMachine_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ProtocolStateMachineCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Operation_OperationLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.OperationEditPartCLN(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Artifact_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ArtifactEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Interaction_StructureCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionCompositeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "StateMachine_StructureCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.StateMachineCompositeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Duration_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Actor_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActorEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Constraint_ConstrainedElementEdge":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConstraintConstrainedElementEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Constraint_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConstraintEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Abstraction_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.AbstractionAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Component_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ComponentCompositeNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Node_StructureCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.NodeCompositeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "ChangeEvent_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ChangeEventFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "UseCase_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.UseCaseFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Realization_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.RealizationEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Interaction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "AnyReceiveEvent_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.AnyReceiveEventEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Component_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ComponentCompositeFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Constraint_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConstraintNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TimeEvent_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeEventEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "PrimitiveType_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PrimitiveTypeFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Property_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PropertyPartNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interaction_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionCompositeFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "StringExpression_PackagedElementShape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.StringExpressionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "CollaborationUse_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationUseFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ProtocolStateMachine_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ProtocolStateMachineCompositeNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "LiteralString_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralStringEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OpaqueBehavior_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.OpaqueBehaviorCompositeFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "LiteralInteger_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralIntegerEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InteractionConstraint_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionConstraintNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DurationObservation_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationObservationStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TimeEvent_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeEventNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Signal_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SignalFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OpaqueExpression_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.OpaqueExpressionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "DurationConstraint_BodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationConstraintSpecificationEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DataType_OperationCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DataTypeOperationCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Collaboration_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "IntervalConstraint_BodyLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.IntervalConstraintSpecificationEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ComponentRealization_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ComponentRealizationEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "LiteralBoolean_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralBooleanFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "LiteralString_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralStringNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Actor_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActorFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "LiteralNull_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralNullFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "FunctionBehavior_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.FunctionBehaviorCompositeNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Parameter_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ParameterAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Component_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ComponentCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "TimeObservation_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeObservationStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Enumeration_LiteralCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.EnumerationEnumerationLiteralCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "UseCase_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.UseCaseNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Duration_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InterfaceRealization_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InterfaceRealizationEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Device_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeviceCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "DurationConstraint_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationConstraintNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "CollaborationUse_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationUseEditPartCN(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "TimeConstraint_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeConstraintEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "EnumerationLiteral_LiteralLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.EnumerationLiteralEditPartCLN(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "DurationInterval_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationIntervalEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OpaqueBehavior_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.OpaqueBehaviorCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Interaction_StructureCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionCompositeCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "FunctionBehavior_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.FunctionBehaviorCompositeNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "UseCase_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.UseCaseEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Interval_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.IntervalNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DeploymentSpecification_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeploymentSpecificationEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "StateMachine_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.StateMachineCompositeNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Abstraction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.AbstractionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "StateMachine_StructureCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.StateMachineCompositeCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "OpaqueBehavior_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.OpaqueBehaviorCompositeNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Enumeration_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.EnumerationNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Device_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeviceCompositeNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Constraint_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConstraintEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Enumeration_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.EnumerationFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "PrimitiveType_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PrimitiveTypeNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DataType_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DataTypeNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ProtocolStateMachine_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ProtocolStateMachineCompositeFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DeploymentSpecification_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeploymentSpecificationNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Component_StructureCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ComponentCompositeCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Interaction_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Enumeration_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.EnumerationNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InterfaceEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Parameter_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ParameterEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Artifact_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ArtifactFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Deployment_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeploymentAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DeploymentSpecification_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeploymentSpecificationNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Component_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ComponentCompositeNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Signal_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SignalNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Collaboration_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Port_BehaviorFloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.BehaviorPortFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InformationItem_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InformationItemNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TimeInterval_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeIntervalFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "LiteralBoolean_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralBooleanEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Activity_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActivityCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Interaction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionCompositeNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Property_AttributeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PropertyEditPartCLN(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Link_DescriptorEdge":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LinkDescriptorEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Expression_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ExpressionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "SignalEvent_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SignalEventFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Activity_StructureCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActivityCompositeCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "ProtocolStateMachine_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ProtocolStateMachineCompositeNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Constraint_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ConstraintNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Interface_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InterfaceNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Activity_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActivityCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "LiteralString_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.LiteralStringFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ChangeEvent_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ChangeEventNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "CompositeStructure":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CompositeStructureDiagramEditPart(
					(org.eclipse.gmf.runtime.notation.Diagram) view);
		case "InteractionConstraint_BodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InteractionConstraintSpecificationEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DeploymentSpecification_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeploymentSpecificationFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "FunctionBehavior_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.FunctionBehaviorCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Representation_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.RepresentationEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "DurationObservation_EventEdge":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationObservationEventEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "FunctionBehavior_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.FunctionBehaviorCompositeFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Port_BehaviorShape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.BehaviorPortEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Node_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.NodeCompositeFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TimeInterval_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeIntervalNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DataType_AttributeCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DataTypeAttributeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Component_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ComponentCompositeFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ConnectableElement_CollaborationRoleFloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationRoleFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Realization_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.RealizationAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DataType_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DataTypeNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Deployment_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeploymentNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DurationObservation_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationObservationNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DurationObservation_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DurationObservationEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Port_BehaviorEdge":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.BehaviorPortLinkEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "TimeExpression_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeExpressionFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "FunctionBehavior_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.FunctionBehaviorCompositeFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TimeEvent_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeEventFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TimeInterval_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeIntervalEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Class_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ClassCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Collaboration_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.CollaborationCompositeFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ExecutionEnvironment_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ExecutionEnvironmentCompositeNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Device_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DeviceCompositeNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Enumeration_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.EnumerationEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Class_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ClassCompositeNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TimeConstraint_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeConstraintNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Signal_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SignalFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "PrimitiveType_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.PrimitiveTypeFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TimeObservation_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.TimeObservationEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OpaqueBehavior_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.OpaqueBehaviorCompositeNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InformationItem_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InformationItemEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "StateMachine_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.StateMachineCompositeFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ExecutionEnvironment_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ExecutionEnvironmentCompositeNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "UseCase_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.UseCaseFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OpaqueBehavior_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.OpaqueBehaviorCompositeFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Actor_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActorNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ExecutionEnvironment_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ExecutionEnvironmentCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "UseCase_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.UseCaseEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "StateMachine_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.StateMachineCompositeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ComponentRealization_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ComponentRealizationNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "SignalEvent_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SignalEventNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ExecutionEnvironment_StructureCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ExecutionEnvironmentCompositeCompartmentEditPartCN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Interface_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InterfaceNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Enumeration_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.EnumerationFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DataType_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.DataTypeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InstanceValue_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.InstanceValueNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Activity_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActivityCompositeFloatingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Substitution_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.SubstitutionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Activity_StructureCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ActivityCompositeCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Class_FloatingNameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.composite.edit.parts.ClassCompositeFloatingLabelEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);

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
