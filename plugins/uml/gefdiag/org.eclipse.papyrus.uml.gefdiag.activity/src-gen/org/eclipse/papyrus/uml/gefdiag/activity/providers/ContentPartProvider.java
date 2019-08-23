package org.eclipse.papyrus.uml.gefdiag.activity.providers;

public class ContentPartProvider
		extends org.eclipse.papyrus.uml.gefdiag.common.provider.AbstractUMLContentPartProvider {

	@Override
	public org.eclipse.gef.mvc.fx.parts.IContentPart<?> createContentPart(org.eclipse.gmf.runtime.notation.View view) {
		switch (view.getType()) {

		case "InputPin_CreateLinkActionInputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInCreateLinkActionAsInputValueAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_ClearStructuralFeatureActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInClearStructuralFeatureActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_ReadLinkActionInputValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInReadLinkActionAsInputValueValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_StartObjectBehaviorActionArgumentShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInStartObjectBehaviorActionAsArgumentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_SendSignalActionTargetValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInSendSigActAsTargetValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ReduceAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReduceActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_CreateLinkObjectActionInputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInCreateLinkObjectActionAsInputValueLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_CreateLinkActionInputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInCreateLinkActionAsInputValueLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_AddStructuralFeatureValueActionValueNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInAddStructuralFeatureValueActionAsValueLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_AddVariableValueActionValueNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInAddVariableValueActionAsValueLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "StartObjectBehaviorAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.StartObjectBehaviorActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_SendSignalActionTargetShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInSendSigActAsTargetEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "CentralBufferNode_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CentralBufferNodeFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ObjectFlow_IconLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ObjectFlowInterruptibleIconEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_AddStructuralFeatureValueActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInAddStructuralFeatureValueActionAsObjectAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "AcceptEventAction_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.AcceptTimeEventActionAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_StartClassifierBehaviorActionObjectValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInStartClassifierBehaviorActionAsObjectValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "CreateLinkObjectAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CreateLinkObjectActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ConditionalNode_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ConditionalNodeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_CallBehaviorActionResultNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInCBActLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_ValueSpecificationActionResultStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInValSpecActAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_LoopNodeVariableShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInLoopNodeAsLoopVariableEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_CreateLinkObjectActionInputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInCreateLinkObjectActionAsInputValueAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_CallBehaviorActionArgumentStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInCBActAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_ClearAssociationActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInClearAssociationActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_AddStructuralFeatureValueActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInAddStructuralFeatureValueActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_TestIdentityActionFirstValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInTestIdentityActionAsFirstValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ExpansionRegion_KeywordLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ExpansionRegionKeywordEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_ReadIsClassifiedObjectActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInReadIsClassifiedObjectActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_ReadLinkActionResultShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInReadLinkActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "MergeNode_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.MergeNodeFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_AddVariableValueActionInsertAtNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInAddVariableValueActionAsInsertAtLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_TestIdentityActionFirstValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInTestIdentityActionAsFirstValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ReadStructuralFeatureAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReadStructuralFeatureActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_CreateLinkActionInputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInCreateLinkActionAsInputValueEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_CreateLinkObjectActionResultShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInCreateLinkObjectActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_ReadStructuralFeatureActionObjectValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInReadStructuralFeatureAsObjectValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_StructuredActivityNodeInputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInStructuredActivityNodeAsStructuredNodeInputsAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_AddStructuralFeatureValueActionInsertAtShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInAddStructuralFeatureValueActionAsInserAtEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_OpaqueActionInputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInOpaqueActEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "IntervalConstraint_LocalPostconditionNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.IntervalConstraintAsLocalPostcondNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_LoopNodeVariableInputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInLoopNodeAsVariableLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_CreateLinkObjectActionInputValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInCreateLinkObjectActionAsInputValueValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_AddVariableValueActionValueShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInAddVariableValueActionAsValueEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_ClearAssociationActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInClearAssociationActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InterruptibleActivityRegion_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InterruptibleActivityRegionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_ReadIsClassifiedObjectActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInReadIsClassifiedObjectActionAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Constraint_PreconditionLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ConstraintInActivityAsPrecondEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_LoopNodeVariableInputValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInLoopNodeAsVariableValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TimeConstraint_LocalPostconditionBodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.TimeConstraintAsLocalPostcondBodyEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ClearStructuralFeatureAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ClearStructuralFeatureActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_ClearAssociationActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInClearAssociationActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_AddStructuralFeatureValueActionInsertAtShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInAddStructuralFeatureValueActionAsInserAtEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_OpaqueActionInputValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInOActValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_LoopNodeVariableInputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInLoopNodeAsVariableEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "TestIdentityAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.TestIdentityActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_DestroyObjectActionTargetValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInDestroyObjectActionValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ConditionalNode_ActivityNodeCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ConditionalNodeStructuredActivityNodeContentCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "InputPin_StartObjectBehaviorActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInStartObjectBehaviorActionAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DestroyLinkAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.DestroyLinkActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_ReadExtentActionResultShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInReadExtentActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "CreateObjectAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CreateObjectActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_DestroyLinkActionInputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInDestroyLinkActionAsInputValueAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_StartObjectBehaviorActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInStartObjectBehaviorActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_UnmarshallActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInUnmarshallActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_AddVariableValueActionInsertAtNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInAddVariableValueActionAsInsertAtLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_SendSignalActionArgumentStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInSendSigActAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_AddVariableValueActionInsertAtStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInAddVariableValueActionAsInsertAtAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_StartClassifierBehaviorActionObjectValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInStartClassifierBehaviorActionAsObjectValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ReadExtentAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReadExtentActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_ReduceActionCollectionStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInReduceActionAsCollectionAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_DestroyObjectActionTargetShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInDestroyObjectActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_AddVariableValueActionInsertAtValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInAddVariableValueActionAsInsertAtValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_CallOperationActionArgumentStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInCOActAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ReadSelfAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReadSelfActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_TestIdentityActionFirstNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInTestIdentityActionAsFirstLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_OpaqueActionInputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInOpaqueActEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "StructuredActivityNode_KeywordLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.StructuredActivityNodeKeywordEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_SendObjectActionRequestValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInSendObjActAsReqValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_BroadcastSignalActionArgumentStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInBroadcastSignalActionAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ObjectFlow_SelectionLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ObjectFlowSelectionEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_ReadStructuralFeatureActionResultShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInReadStructuralFeatureAsResultEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ExceptionHandler_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ExceptionHandlerEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "ValuePin_AddStructuralFeatureValueActionInsertAtValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInAddStructuralFeatureValueActionAsInserAtValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InterruptibleActivityRegion_ActivityNodeCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InterruptibleActivityRegionInterruptibleActivityRegionContentCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "ValuePin_ReadLinkActionInputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInReadLinkActionAsInputValueEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Constraint_PostconditionLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ConstraintInActivityAsPostcondEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_CallOperationActionTargetValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInCOActAsTargetValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_AddStructuralFeatureValueActionInsertAtNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInAddStructuralFeatureValueActionAsInserAtLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_ReadLinkActionInputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInReadLinkActionAsInputValueAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_ReduceActionCollectionShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInReduceActionAsCollectionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_StartObjectBehaviorActionArgumentValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInStartObjectBehaviorActionAsArgumentValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_ReadLinkActionInputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInReadLinkActionAsInputValueEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_SendSignalActionArgumentShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInSendSigActEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ForkNode_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ForkNodeAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_AddStructuralFeatureValueActionValueNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInAddStructuralFeatureValueActionAsValueLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "SendObjectAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.SendObjectActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_SendSignalActionArgumentNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInSendSigActLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "LoopNode_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.LoopNodeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_BroadcastSignalActionArgumentStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInBroadcastSignalActionAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ObjectFlow_GuardLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ObjectFlowGuardEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_LoopNodeVariableInputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInLoopNodeAsVariableEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ControlFlow_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ControlFlowEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "OutputPin_OpaqueActionOutputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInOActLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_AddStructuralFeatureValueActionValueShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInAddStructuralFeatureValueActionAsValueEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "StructuredActivityNode_ActivityNodeCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.StructuredActivityNodeStructuredActivityNodeContentCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "ActionInputPin_LoopNodeVariableInputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInLoopNodeAsVariableLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ForkNode_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ForkNodeFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_ClearStructuralFeatureActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInClearStructuralFeatureActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ControlFlow_GuardLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ControlFlowGuardEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "AddStructuralFeatureValueAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.AddStructuralFeatureValueActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DecisionNode_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.DecisionNodeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "FlowFinalNode_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.FlowFinalNodeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "AddStructuralFeatureValueAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.AddStructuralFeatureValueActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_UnmarshallActionResultStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInUnmarshallActionAsResultAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_ReadIsClassifiedObjectActionResultStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInReadIsClassifiedObjectActionAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValueSpecificationAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValueSpecificationActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Action_LocalPreconditionEdge":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionLocalPreconditionEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "OutputPin_ReduceActionResultShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInReduceActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_StartObjectBehaviorActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInStartObjectBehaviorActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_ClearAssociationActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInClearAssociationActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_CreateLinkObjectActionInputValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInCreateLinkObjectActionAsInputValueValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ExpansionRegion_ActivityNodeCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ExpansionRegionStructuredActivityNodeContentCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "OutputPin_LoopNodeBodyOutputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInLoopNodeAsBodyOutputEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_OpaqueActionOutputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInOpaqueActEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ReclassifyObjectAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReclassifyObjectActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ReadIsClassifiedObjectAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReadIsClassifiedObjectActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_CallOperationActionTargetShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInCallOpActAsTargetEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OpaqueAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OpaqueActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_BroadcastSignalActionArgumentNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInBroadcastSignalActionLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_StartClassifierBehaviorActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInStartClassifierBehaviorActionAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DurationConstraint_LocalPreconditionBodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.DurationConstraintAsLocalPrecondBodyEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInAddStructuralFeatureValueActionAsInserAtAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ConditionalNode_KeywordLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ConditionalNodeKeywordEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActivityParameterNode_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityParameterNodeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Comment_BodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CommentBodyLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_CreateLinkActionInputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInCreateLinkActionAsInputValueEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActivityPartition_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityPartitionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_ReadStructuralFeatureActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInReadStructuralFeatureAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "DataStoreNode_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.DataStoreNodeFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_SendSignalActionArgumentNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInSendSigActLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_SendObjectActionRequestValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInSendObjActAsReqValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActivityFinalNode_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityFinalNodeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_ReduceActionCollectionShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInReduceActionAsCollectionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_TestIdentityActionFirstStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInTestIdentityActionAsFirstAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "JoinNode_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.JoinNodeFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DataStoreNode_SelectionLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.DataStoreSelectionEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActivityPartition_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityPartitionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActivityPartition_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityPartitionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_ClearAssociationActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInClearAssociationActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "SendObjectAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.SendObjectActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_BroadcastSignalActionArgumentShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInBroadcastSignalActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_CallBehaviorActionResultShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInCallBeActEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_ReduceActionCollectionNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInReduceActionAsCollectionLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_CreateLinkObjectActionInputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInCreateLinkObjectActionAsInputValueLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_BroadcastSignalActionArgumentNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInBroadcastSignalActionLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_SendObjectActionRequestNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInSendObjActAsReqLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_OpaqueActionInputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInOpaqueActEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_SendObjectActionTargetNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInSendObjActAsTargetLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_TestIdentityActionSecondNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInTestIdentityActionAsSecondLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_OpaqueActionInputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInOActLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ObjectFlow_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ObjectFlowEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "ValuePin_DestroyObjectActionTargetNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInDestroyObjectActionLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_OpaqueActionInputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInOActLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "UnmarshallAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.UnmarshallActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "StartObjectBehaviorAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.StartObjectBehaviorActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_TestIdentityActionSecondValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInTestIdentityActionAsSecondValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_DestroyObjectActionTargetNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInDestroyObjectActionLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_AddStructuralFeatureValueActionValueStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInAddStructuralFeatureValueActionAsValueAppliedStereotypeWrappingLabel2EditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DestroyObjectAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.DestroyObjectActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_UnmarshallActionObjectValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInUnmarshallActionAsObjectValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ReadIsClassifiedObjectAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReadIsClassifiedObjectActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_AddStructuralFeatureValueActionValueShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInAddStructuralFeatureValueActionAsValueEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_StartObjectBehaviorActionArgumentValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInStartObjectBehaviorActionAsArgumentValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Constraint_LocalPostconditionBodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ConstraintAsLocalPostcondBodyEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "FlowFinalNode_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.FlowFinalNodeFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_StartObjectBehaviorActionObjectValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInStartObjectBehaviorActionAsObjectValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValueSpecificationAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValueSpecificationActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "BroadcastSignalAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.BroadcastSignalActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_OpaqueActionInputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInOActAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_ReadStructuralFeatureActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInReadStructuralFeatureAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_SendObjectActionRequestShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInSendObjActAsReqEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_ReadExtentActionResultNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInReadExtentActionLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_AddStructuralFeatureValueActionObjectValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInAddStructuralFeatureValueActionAsObjectValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ObjectFlow_WeightLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ObjectFlowWeightEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DurationConstraint_LocalPreconditionNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.DurationConstraintAsLocalPrecondNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_CreateLinkObjectActionInputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInCreateLinkObjectActionAsInputValueAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_StructuredActivityNodeInputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInStructuredActivityNodeAsStructuredNodeInputsAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "IntervalConstraint_LocalPreconditionNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.IntervalConstraintAsLocalPrecondNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_ReadLinkActionInputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInReadLinkActionAsInputValueLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_CallOperationActionArgumentShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInCallOpActEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "CallBehaviorAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CallBehaviorActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ClearAssociationAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ClearAssociationActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_CallOperationActionTargetNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInCOActAsTargetLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_DestroyLinkActionInputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInDestroyLinkActionAsInputValueLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ReadLinkAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReadLinkActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_CallBehaviorActionArgumentShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInCallBeActEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ReadVariableAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReadVariableActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_DestroyObjectActionTargetStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInDestroyObjectActionAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_CreateLinkActionInputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInCreateLinkActionAsInputValueAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "CreateLinkObjectAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CreateLinkObjectActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_DestroyLinkActionInputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInDestroyLinkActionAsInputValueAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_AddStructuralFeatureValueActionValueValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInAddStructuralFeatureValueActionAsValueValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_AddStructuralFeatureValueActionInsertAtStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInAddStructuralFeatureValueActionAsInserAtAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ControlFlow_IconLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ControlFlowInterruptibleIconEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "CreateLinkObjectAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CreateLinkObjectActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_StartObjectBehaviorActionArgumentNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInStartObjectBehaviorActionAsArgumentLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "SendSignalAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.SendSignalActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_AcceptEventActionResultShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInAcceptEventActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_AddStructuralFeatureValueActionResultStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInAddStructuralFeatureValueActionAsResultAppliedStereotypeWrappingLabel3EditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_SendObjectActionTargetNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInSendObjActAsTargetLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_CreateLinkActionInputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInCreateLinkActionAsInputValueAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TimeConstraint_LocalPreconditionBodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.TimeConstraintAsLocalPrecondBodyEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_ReadLinkActionInputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInReadLinkActionAsInputValueEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_CallBehaviorActionResultStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInCBActAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DecisionNode_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.DecisionNodeFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "AddStructuralFeatureValueAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.AddStructuralFeatureValueActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_StartClassifierBehaviorActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInStartClassifierBehaviorActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_AddStructuralFeatureValueActionValueNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInAddStructuralFeatureValueActionAsValueLabel2EditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_ReduceActionCollectionNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInReduceActionAsCollectionLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_SendSignalActionArgumentStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInSendSigActAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_ValueSpecificationActionResultNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInValSpecActLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "LoopNode_KeywordLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.LoopNodeKeywordEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_CallOperationActionResultShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInCallOpActEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ReadExtentAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReadExtentActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "UnmarshallAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.UnmarshallActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_ReadIsClassifiedObjectActionResultShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInReadIsClassifiedObjectActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_CallBehaviorActionArgumentStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInCBActAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_SendSignalActionTargetValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInSendSigActAsTargetValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_UnmarshallActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInUnmarshallActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ControlFlow_WeightLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ControlFlowWeightEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_LoopNodeVariableInputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInLoopNodeAsVariableEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_SendObjectActionTargetShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInSendObjActAsTargetEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_BroadcastSignalActionArgumentValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInBroadcastSignalActionValueLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_AddVariableValueActionValueStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInAddVariableValueActionAsValueAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_CreateLinkActionInputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInCreateLinkActionAsInputValueLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InitialNode_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InitialNodeAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_LoopNodeResultShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInLoopNodeAsResultEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_StartClassifierBehaviorActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInStartClassifierBehaviorActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "SequenceNode_KeywordLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.SequenceNodeKeywordEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_StartObjectBehaviorActionArgumentStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInStartObjectBehaviorActionAsArgumentAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ReadLinkAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReadLinkActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_ReadLinkActionResultNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInReadLinkActionLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_AddStructuralFeatureValueActionValueShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInAddStructuralFeatureValueActionAsValueEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_ReclassifyObjectActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInReclassifyObjectActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InitialNode_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InitialNodeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_BroadcastSignalActionArgumentStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInBroadcastSignalActionAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_CallBehaviorActionArgumentNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInCBActLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "AddVariableValueAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.AddVariableValueActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_ClearStructuralFeatureActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInClearStructFeatActAsObjectAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "IntervalConstraint_LocalPreconditionShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.IntervalConstraintAsLocalPrecondEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_LoopNodeVariableInputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInStructuredActivityNodeAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_TestIdentityActionFirstNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInTestIdentityActionAsFirstLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_CallOperationActionTargetShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInCallOpActAsTargetEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ClearAssociationAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ClearAssociationActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_TestIdentityActionFirstStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInTestIdentityActionAsFirstAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Constraint_LocalPreconditionBodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ConstraintAsLocalPrecondBodyEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Activity_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_CallOperationActionTargetNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInCOActAsTargetLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_SendSignalActionTargetNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInSendSigActAsTargetLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DurationConstraint_LocalPostconditionBodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.DurationConstraintAsLocalPostcondBodyEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_StartObjectBehaviorActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInStartObjectBehaviorActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_TestIdentityActionSecondShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInTestIdentityActionAsSecondEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_LoopNodeResultStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInLoopNodeAsResultAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_ReclassifyObjectActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInReclassifyObjectActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_CreateLinkObjectActionResultNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInCreateLinkObjectActionLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "SendSignalAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.SendSignalActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_SendObjectActionTargetStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInSendObjActAsTargetAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Constraint_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ConstraintNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Activity_Shape_CN":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "DurationConstraint_LocalPostconditionNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.DurationConstraintAsLocalPostcondNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_SendObjectActionTargetValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInSendObjActAsTargetValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Activity_KeywordLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityIsSingleExecutionCNEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ReadStructuralFeatureAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReadStructuralFeatureActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_DestroyObjectActionTargetStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInDestroyObjectActionAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_CreateLinkObjectActionInputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInCreateLinkObjectActionAsInputValueAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TestIdentityAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.TestIdentityActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_SendObjectActionRequestShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInSendObjActAsReqEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_CallBehaviorActionArgumentValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInCBActValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Activity_ActivityNodeCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityActivityContentCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "InputPin_TestIdentityActionSecondStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInTestIdentityActionAsSecondAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_UnmarshallActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInUnmarshallActionAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_UnmarshallActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInUnmarshallActionAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_UnmarshallActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInUnmarshallActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_CreateObjectActionResultStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInCreateObjectActionAsResultAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ReadVariableAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReadVariableActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_CallOperationActionArgumentShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInCallOpActEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_ReadStructuralFeatureActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInReadStructuralFeatureAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "StartClassifierBehaviorAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.StartClassifierBehaviorActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_StartClassifierBehaviorActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInStartClassifierBehaviorActionAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_ReadLinkActionInputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInReadLinkActionAsInputValueAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_ClearAssociationActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInClearAssociationActionAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TimeConstraint_LocalPreconditionNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.TimeConstraintAsLocalPrecondNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_StartClassifierBehaviorActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInStartClassifierBehaviorActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_SendObjectActionTargetStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInSendObjActAsTargetAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_CallOperationActionArgumentStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInCOActAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_ReduceActionCollectionStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInReduceActionAsCollectionAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_BroadcastSignalActionArgumentShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInBroadcastSignalActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_ClearAssociationActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInClearAssociationActionAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Activity_KeywordLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityIsSingleExecutionEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_StartObjectBehaviorActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInStartObjectBehaviorActionAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_DestroyLinkActionInputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInDestroyLinkActionAsInputValueAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_DestroyLinkActionInputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInDestroyLinkActionAsInputValueLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_ReadVariableActionResultStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInReadVariableActionAsResultAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_ReadIsClassifiedObjectActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInReadIsClassifiedObjectActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_ReduceActionCollectionNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInReduceActionAsCollectionLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Activity_ActivityNodeCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityCNContentCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "ActionInputPin_ReadIsClassifiedObjectActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInReadIsClassifiedObjectActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DataStoreNode_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.DataStoreNodeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_ReadSelfActionResultNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInReadSelfActionLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_StartObjectBehaviorActionArgumentStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInStartObjectBehaviorActionAsArgumentAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Constraint_LocalPreconditionNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ConstraintAsLocalPrecondNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_DestroyLinkActionInputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInDestroyLinkActionAsInputValueEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_LoopNodeVariableInputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInLoopNodeAsVariableAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_DestroyObjectActionTargetStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInDestroyObjectActionAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_CreateObjectActionResultNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInCreateObjectActionAsResultLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_AddStructuralFeatureValueActionResultShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInAddStructuralFeatureValueActionAsResultEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_LoopNodeBodyOutputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInLoopNodeAsBodyOutputLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_SendObjectActionTargetShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInSendObjActAsTargetEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInReadIsClassifiedObjectActionAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_AddStructuralFeatureValueActionInsertAtNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInAddStructuralFeatureValueActionAsInserAtLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "IntervalConstraint_LocalPreconditionBodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.IntervalConstraintAsLocalPrecondBodyEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_ReadLinkActionResultStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInReadLinkActionAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_TestIdentityActionFirstNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInTestIdentityActionAsFirstLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TimeConstraint_LocalPostconditionNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.TimeConstraintAsLocalPostcondNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_CreateLinkActionInputValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInCreateLinkActionAsInputValueValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_TestIdentityActionResultStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInTestIdentityActionItemAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_SendObjectActionTargetStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInSendObjActAsTargetAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "StructuredActivityNode_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.StructuredActivityNodeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "AddVariableValueAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.AddVariableValueActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_ClearStructuralFeatureActionObjectValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInClearStructuralFeatureActionAsObjectValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_OpaqueActionOutputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInOActAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ReadStructuralFeatureAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReadStructuralFeatureActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_ReclassifyObjectActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInReclassifyObjectActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_CallBehaviorActionArgumentShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInCallBeActEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "SendObjectAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.SendObjectActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_UnmarshallActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInUnmarshallActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_UnmarshallActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInUnmarshallActionAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_ReadLinkActionInputValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInReadLinkActionAsInputValueValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_ReduceActionCollectionStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInReduceActionAsCollectionAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_CallBehaviorActionArgumentValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInCBActValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_StartObjectBehaviorActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInStartObjectBehaviorActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_AddVariableValueActionInsertAtShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInAddVariableValueActionAsInsertAtEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_ReduceActionCollectionValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInReduceActionAsCollectionValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_StartObjectBehaviorActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInStartObjectBehaviorActionAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Comment_AnnotatedElementEdge":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CommentLinkEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "ValuePin_StartObjectBehaviorActionObjectValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInStartObjectBehaviorActionAsObjectValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ClearStructuralFeatureAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ClearStructuralFeatureActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ObjectFlow_KeywordLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.DecisionInputFlowEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_ClearStructuralFeatureActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInClearStructuralFeatureActionAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_AddVariableValueActionInsertAtNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInAddVariableValueActionAsInsertAtLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_SendSignalActionTargetNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInSendSigActAsTargetLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_TestIdentityActionSecondShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInTestIdentityActionAsSecondEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_UnmarshallActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInUnmarshallActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_AddStructuralFeatureValueActionValueStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInAddStructuralFeatureValueActionAsValueAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OpaqueAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OpaqueActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_AddVariableValueActionValueNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInAddVariableValueActionAsValueLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_TestIdentityActionSecondNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInTestIdentityActionAsSecondLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ReclassifyObjectAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReclassifyObjectActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_ReadIsClassifiedObjectActionObjectValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInReadIsClassifiedObjectActionAsObjectValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_LoopNodeVariableInputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInLoopNodeAsVariableAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_ReclassifyObjectActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInReclassifyObjectActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "FlowFinalNode_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.FlowFinalNodeAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_ClearStructuralFeatureActionObjectValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInClearStructuralFeatureActionAsObjectValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_OpaqueActionInputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInOActAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_LoopNodeBodyOutputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInLoopNodeAsBodyOutputAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_LoopNodeResultNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInLoopNodeAsResultLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_BroadcastSignalActionArgumentValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInBroadcastSignalActionValueLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Action_LocalPostconditionEdge":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionLocalPostconditionEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "ObjectFlow_TransformationLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ObjectFlowTransformationEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_StructuredActivityNodeInputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInStructuredActivityNodeAsStructuredNodeInputsEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_StructuredActivityNodeInputValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInStructuredActivityNodeAsStructuredNodeInputsValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_ReadStructuralFeatureActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInReadStructuralFeatureAsObjectNameLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_AddStructuralFeatureValueActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInAddStructuralFeatureValueActionAsObjectAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_TestIdentityActionSecondShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInTestIdentityActionAsSecondEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_UnmarshallActionObjectValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInUnmarshallActionAsObjectValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_CallBehaviorActionArgumentStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInCBActAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_SendSignalActionTargetShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInSendSigActAsTargetEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "UnmarshallAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.UnmarshallActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_ReadStructuralFeatureActionResultNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInReadStructuralFeatureAsResultLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Parameter_ParameterLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ParameterEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_UnmarshallActionResultNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInUnmarshallActionAsResultLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Activity_PreconditionCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityCNPreConditionsCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "ValuePin_CreateLinkActionInputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInCreateLinkActionAsInputValueEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "SendSignalAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.SendSignalActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_TestIdentityActionResultShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInTestIdentityActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_StartObjectBehaviorActionArgumentNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInStartObjectBehaviorActionAsArgumentLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_ClearStructuralFeatureActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInClearStructuralFeatureActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_ReadLinkActionInputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInReadLinkActionAsInputValueLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ForkNode_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ForkNodeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_TestIdentityActionFirstStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInTestIdentityActionAsFirstAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_AddStructuralFeatureValueActionInsertAtNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInAddStructuralFeatureValueActionAsInserAtLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_CreateLinkObjectActionInputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInCreateLinkObjectActionAsInputValueLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_StartClassifierBehaviorActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInStartClassifierBehaviorActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "CallOperationAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CallOperationActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_AddVariableValueActionInsertAtShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInAddVariableValueActionAsInsertAtEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_CallOperationActionArgumentValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInCOActValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_ReduceActionCollectionValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInReduceActionAsCollectionValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Comment_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CommentEditPartCN(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_CallOperationActionTargetValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInCOActAsTargetValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_StructuredActivityNodeInputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActivityParameterNode_StreamLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityParameterNodeStreamLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_LoopNodeVariableInputValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInLoopNodeAsVariableValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_CreateLinkObjectActionInputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInCreateLinkObjectActionAsInputValueEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "DecisionNode_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.DecisionNodeAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Constraint_LocalPreconditionShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ConstraintAsLocalPrecondEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_TestIdentityActionSecondNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInTestIdentityActionAsSecondLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ExceptionHandler_TypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ExceptionHandlerTypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_StartClassifierBehaviorActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInStartClassifierBehaviorActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Activity_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_CallBehaviorActionArgumentShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInCallBeActEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Activity_PostconditionCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityCNPostConditionsCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "ActionInputPin_AddStructuralFeatureValueActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInAddStructuralFeatureValueActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "CentralBufferNode_SelectionLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CentralBufferNodeSelectionEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_UnmarshallActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInUnmarshallActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_ReclassifyObjectActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInReclassifyObjectActionAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "CreateLinkAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CreateLinkActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActivityFinalNode_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityFinalNodeAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "CallBehaviorAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CallBehaviorActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "NamedElement_DefaultShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ShapeNamedElementEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Constraint_BodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ConstraintBodyEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InitialNode_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InitialNodeFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_SendObjectActionTargetNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInSendObjActAsTargetLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ExpansionNode_InputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ExpansionNodeAsInEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_ReclassifyObjectActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInReclassifyObjectActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_ReclassifyObjectActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInReclassifyObjectActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_SendSignalActionArgumentNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInSendSigActLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_ReadStructuralFeatureActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInReadStructuralFeatureAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Activity_PreconditionCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityActivityPreConditionsCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "InputPin_OpaqueActionInputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInOActAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "CreateObjectAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CreateObjectActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_SendObjectActionRequestNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInSendObjActAsReqLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_AddVariableValueActionInsertAtShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInAddVariableValueActionAsInsertAtEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "DestroyObjectAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.DestroyObjectActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActivityParameterNode_ExceptionLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityParameterNodeExceptionLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_DestroyObjectActionTargetShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInDestroyObjectActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "JoinNode_JoinSpecLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.JoinSpecEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_ReadIsClassifiedObjectActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInReadIsClassifiedObjectActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_StructuredActivityNodeOutputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInStructuredActivityNodeAsStructuredNodeInputsAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInAddStructuralFeatureValueActionAsInserAtAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_ClearStructuralFeatureActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInClearStructuralFeatureActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ExpansionRegion_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ExpansionRegionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "SequenceNode_ActivityNodeCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.SequenceNodeStructuredActivityNodeContentCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "ValuePin_StartObjectBehaviorActionArgumentStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInStartObjectBehaviorActionAsArgumentAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "SequenceNode_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.SequenceNodeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_ReadLinkActionInputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInReadLinkActionAsInputValueAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "MergeNode_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.MergeNodeAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ReduceAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReduceActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_ReadStructuralFeatureActionObjectValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInReadStructuralFeatureAsObjectValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "AddVariableValueAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.AddVariableValueActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ReadIsClassifiedObjectAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReadIsClassifiedObjectActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_OpaqueActionInputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInOActLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_CreateLinkActionInputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInCreateLinkActionAsInputValueLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "AcceptEventAction_TriggerLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.AcceptTimeEventActionLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_SendObjectActionTargetValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInSendObjActAsTargetValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_LoopNodeVariableInputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInLoopNodeAsVariableLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActivityPartition_ActivityNodeCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityPartitionActivityPartitionContentCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "ReclassifyObjectAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReclassifyObjectActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_AddStructuralFeatureValueActionValueStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInAddStructuralFeatureValueActionAsValueAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_AddStructuralFeatureValueActionResultNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInAddStructuralFeatureValueActionAsResultLabel3EditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "AcceptEventAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.AcceptEventActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_TestIdentityActionResultNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInTestIdentityActionItemLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_StructuredActivityNodeOutputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_StartObjectBehaviorActionArgumentShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInStartObjectBehaviorActionAsArgumentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_LoopNodeVariableStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInLoopNodeAsLoopVariableAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_ReduceActionResultStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInReduceActionAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_AcceptEventActionResultStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInAcceptEventActionAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_AddStructuralFeatureValueActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInAddStructuralFeatureValueActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_StartClassifierBehaviorActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInStartClassifierBehaviorActionAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_ReclassifyObjectActionObjectValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInReclassifyObjectActionAsObjectValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "LoopNode_ActivityNodeCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.LoopNodeStructuredActivityNodeContentCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "ControlFlow_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ControlFlowAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_CreateLinkObjectActionInputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInCreateLinkObjectActionAsInputValueEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_CreateLinkActionInputValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInCreateLinkActionAsInputValueValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_DestroyLinkActionInputValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInDestroyLinkActionAsInputValueValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_ClearAssociationActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInClearAssociationActionAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_StartObjectBehaviorActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInStartObjectBehaviorActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_SendSignalActionArgumentStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInSendSigActAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_CallOperationActionArgumentShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInCallOpActEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ReadSelfAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReadSelfActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_BroadcastSignalActionArgumentNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInBroadcastSignalActionLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_SendSignalActionArgumentValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInSendSigActValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_ReadVariableActionResultShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInReadVariableActionAsResultEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Activity_NameLabel_CN":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityNameEditPartCN(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ReadVariableAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReadVariableActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "BroadcastSignalAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.BroadcastSignalActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActivityParameterNode_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ParameterNodeNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_AddVariableValueActionInsertAtValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInAddVariableValueActionAsInsertAtValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_ReduceActionCollectionShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInReduceActionAsCollectionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_ClearAssociationActionObjectValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInClearAssociationActionAsObjectValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_AddStructuralFeatureValueActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInAddStructuralFeatureValueActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_StartObjectBehaviorActionArgumentNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInStartObjectBehaviorActionAsArgumentLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_OpaqueActionInputValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInOActValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_SendObjectActionRequestStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInSendObjActAsReqAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_ReadIsClassifiedObjectActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInReadIsClassifiedObjectActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_SendSignalActionTargetNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInSendSigActAsTargetLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_TestIdentityActionFirstShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInTestIdentityActionAsFirstEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_TestIdentityActionFirstShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInTestIdentityActionAsFirstEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_StructuredActivityNodeInputValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInStructuredActivityNodeAsStructuredNodeInputsValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "PapyrusUMLActivityDiagram":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityDiagramEditPart(
					(org.eclipse.gmf.runtime.notation.Diagram) view);
		case "ValuePin_TestIdentityActionSecondStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInTestIdentityActionAsSecondAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_SendSignalActionArgumentShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInSendSigActEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "DurationConstraint_LocalPreconditionShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.DurationConstraintAsLocalPrecondEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "DataStoreNode_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.DataStoreNodeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_StartClassifierBehaviorActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInStartClassifierBehaviorActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DestroyObjectAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.DestroyObjectActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_CallOperationActionTargetStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInCOActAsTargetAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_ReclassifyObjectActionObjectValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInReclassifyObjectActionAsObjectValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_DestroyObjectActionTargetValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInDestroyObjectActionValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_AddVariableValueActionValueShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInAddVariableValueActionAsValueEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_ReadIsClassifiedObjectActionObjectValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInReadIsClassifiedObjectActionAsObjectValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "TimeConstraint_LocalPostconditionShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.TimeConstraintAsLocalPostcondEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_StructuredActivityNodeInputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInStructuredActivityNodeAsStructuredNodeInputsEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_ClearAssociationActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInClearAssociationActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_ClearStructuralFeatureActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInClearStructuralFeatureActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Constraint_ConstrainedElementEdge":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ConstraintConstrainedElementEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Constraint_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ConstraintEditPartCN(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_StartObjectBehaviorActionResultShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInStartObjectBehaviorActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ObjectFlow_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ObjectFlowAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_ClearStructuralFeatureActionResultNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInClearStructuralFeatureActionLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ExpansionNode_OutputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ExpansionNodeAsOutEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_DestroyLinkActionInputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInDestroyLinkActionAsInputValueEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "AcceptEventAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.AcceptEventActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_ReadStructuralFeatureActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInReadStructuralFeatureAsObjectNameLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DurationConstraint_LocalPostconditionShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.DurationConstraintAsLocalPostcondEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_DestroyLinkActionInputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInDestroyLinkActionAsInputValueLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_CreateObjectActionResultShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInCreateObjectActionAsResultEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_CallOperationActionTargetShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInCallOpActAsTargetEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_AddVariableValueActionInsertAtStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInAddVariableValueActionAsInsertAtAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_TestIdentityActionSecondStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInTestIdentityActionAsSecondAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "JoinNode_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.JoinNodeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Constraint_LocalPostconditionNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ConstraintAsLocalPostcondNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "AcceptEventAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.AcceptEventActionLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_SendSignalActionArgumentShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInSendSigActEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ReadSelfAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReadSelfActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_ReadStructuralFeatureActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInReadStructuralFeatureAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_DestroyObjectActionTargetNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInDestroyObjectActionLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_AddVariableValueActionValueNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInAddVariableValueActionAsValueLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_ReadExtentActionResultStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInReadExtentActionAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_SendSignalActionTargetShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInSendSigActAsTargetEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "BroadcastSignalAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.BroadcastSignalActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_ReadVariableActionResultNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInReadVariableActionAsResultLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_ClearStructuralFeatureActionResultStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInClearStructuralFeatureActionAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_ReadLinkActionInputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInReadLinkActionAsInputValueLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_CallOperationActionTargetStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInCOActAsTargetAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "IntervalConstraint_LocalPostconditionShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.IntervalConstraintAsLocalPostcondEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_ReadIsClassifiedObjectActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInReadIsClassifiedObjectActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_CallBehaviorActionArgumentNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInCBActLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_SendObjectActionRequestStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInSendObjActAsReqAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_ReclassifyObjectActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInReclassifyObjectActionAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "StartObjectBehaviorAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.StartObjectBehavoiurActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "DestroyLinkAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.DestroyLinkActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ClearAssociationAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ClearAssociationActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActivityFinalNode_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityFinalNodeFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_CallOperationActionArgumentValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInCOActValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_ReadStructuralFeatureActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInReadStructuralFeatureAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "CallOperationAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CallOperationActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_StructuredActivityNodeInputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInStructuredActivityNodeAsStructuredNodeInputsLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OpaqueAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OpaqueActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_SendSignalActionTargetStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInSendSigActAsTargetAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_CallOperationActionResultNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInCOActLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Activity_ParameterCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityActivityParametersCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "ValuePin_AddStructuralFeatureValueActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInAddStructuralFeatureValueActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_AddStructuralFeatureValueActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInAddStructuralFeatureValueActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_StartObjectBehaviorActionObjectShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInStartObjectBehaviorActionAsObjectEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "TestIdentityAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.TestIdentityActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "CallOperationAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CallOperationActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Activity_ParameterCompartment_CN":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityCNParametersCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "CentralBufferNode_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CentralBufferNodeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_CreateLinkObjectActionResultStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInCreateLinkObjectActionAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_StructuredActivityNodeInputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInStructuredActivityNodeAsStructuredNodeInputsLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_AddVariableValueActionValueValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInAddVariableValueActionAsValueValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_ReadSelfActionResultStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInReadSelfActionAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_TestIdentityActionSecondValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInTestIdentityActionAsSecondValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_CallOperationActionTargetNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInCOActAsTargetLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ClearStructuralFeatureAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ClearStructuralFeatureActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "DestroyLinkAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.DestroyLinkActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_StartObjectBehaviorActionResultNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInStartObjectBehaviorActionLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_AddVariableValueActionInsertAtStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInAddVariableValueActionAsInsertAtAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "MergeNode_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.MergeNodeEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_StructuredActivityNodeOutputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInStructuredActivityNodeAsStructuredNodeInputsLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_AddStructuralFeatureValueActionValueValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInAddStructuralFeatureValueActionAsValueValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "StartClassifierBehaviorAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.StartClassifierBehaviorActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_AddVariableValueActionValueValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInAddVariableValueActionAsValueValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "IntervalConstraint_LocalPostconditionBodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.IntervalConstraintAsLocalPostcondBodyEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_AddVariableValueActionValueStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInAddVariableValueActionAsValueAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInReadIsClassifiedObjectActionAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_AcceptEventActionResultNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInAcceptEventActionLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_DestroyLinkActionInputValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInDestroyLinkActionAsInputValueValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_StructuredActivityNodeInputNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInStructuredActivityNodeAsStructuredNodeInputsLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_CallOperationActionArgumentNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInCOActLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_CallOperationActionResultStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInCOActAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "NamedElement_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ShapeNamedElementNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Activity_PostconditionCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActivityActivityPostConditionsCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "InputPin_StructuredActivityNodeInputStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInStructuredActivityNodeAsStructuredNodeInputsAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ReadLinkAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReadLinkActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ObjectFlow_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ObjectFlowNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_AddStructuralFeatureValueActionObjectValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInAddStructuralFeatureValueActionAsObjectValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ReduceAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReduceActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_ClearAssociationActionObjectValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInClearAssociationActionAsObjectValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_ReadIsClassifiedObjectActionResultNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInReadIsClassifiedObjectActionLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ControlFlow_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ControlFlowNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_AddVariableValueActionValueShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInAddVariableValueActionAsValueEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_StartObjectBehaviorActionResultStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInStartObjectBehaviorActionAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_SendObjectActionRequestStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInSendObjActAsReqAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_SendSignalActionArgumentValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInSendSigActValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ExceptionHandler_IconLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ExceptionHandlerIconEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_ReduceActionResultNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInReduceActionLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_SendSignalActionTargetStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInSendSigActAsTargetAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "Constraint_LocalPostconditionShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ConstraintAsLocalPostcondEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_ReclassifyObjectActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInReclassifyObjectActionAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_AddStructuralFeatureValueActionInsertAtValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInAddStructuralFeatureValueActionAsInserAtValueEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_AddStructuralFeatureValueActionInsertAtShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInAddStructuralFeatureValueActionAsInserAtEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_CallOperationActionTargetStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInCOActAsTargetAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ReadExtentAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReadExtentActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_BroadcastSignalActionArgumentShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInBroadcastSignalActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_CallBehaviorActionArgumentNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInCBActLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_DestroyObjectActionTargetShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInDestroyObjectActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_ReadSelfActionResultShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ReadSelfActionOutputPinEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "DecisionNode_DecisionInputLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.DecisionInputEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_ClearStructuralFeatureActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInClearStructuralFeatureActionAsObjectAppliedStereotypeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ActionInputPin_SendSignalActionTargetStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInSendSigActAsTargetAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "JoinNode_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.JoinNodeAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_AddVariableValueActionValueStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInAddVariableValueActionAsValueAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_SendObjectActionRequestShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInSendObjActAsReqEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_BroadcastSignalActionArgumentValueLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionPinInBroadcastSignalActionValueLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValueSpecificationAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValueSpecificationActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_ValueSpecificationActionResultShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInValSpecActEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "CreateLinkAction_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CreateLinkActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_CallOperationActionArgumentNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInCOActLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_AddStructuralFeatureValueActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInAddStructuralFeatureValueActionAsObjectAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_UnmarshallActionResultShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInUnmarshallActionAsResultEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_ReadStructuralFeatureActionResultStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInReadStructuralFeatureAsResultWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "CallBehaviorAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CallBehaviorActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_SendObjectActionRequestNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInSendObjActAsReqLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_ClearStructuralFeatureActionObjectNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInClearStructuralFeatureActionAsObjectLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "CentralBufferNode_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CentralBufferNodeLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "StartClassifierBehaviorAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.StartClassifierBehaviorActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_ReadStructuralFeatureActionObjectStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInReadStructuralFeatureAsObjectAppliedStereotypeWrappingLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "ValuePin_CreateLinkObjectActionInputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInCreateLinkObjectActionAsInputValueEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "TimeConstraint_LocalPreconditionShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.TimeConstraintAsLocalPrecondEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_SendObjectActionTargetShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInSendObjActAsTargetEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ValuePin_TestIdentityActionFirstShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ValuePinInTestIdentityActionAsFirstEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_DestroyLinkActionInputShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInDestroyLinkActionAsInputValueEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "OutputPin_LoopNodeVariableNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInLoopNodeAsLoopVariableLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "OutputPin_ClearStructuralFeatureActionResultShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.OutputPinInClearStructuralFeatureActionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ActionInputPin_CallOperationActionArgumentStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.ActionInputPinInCOActAppliedStereotypeEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "InputPin_StartObjectBehaviorActionArgumentShape":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInStartObjectBehaviorActionAsArgumentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "InputPin_CallOperationActionArgumentNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.InputPinInCOActLabelEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "CreateObjectAction_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CreateObjectActionNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);
		case "CreateLinkAction_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.activity.edit.parts.CreateLinkActionFloatingNameEditPart(
					(org.eclipse.gmf.runtime.notation.View) view);

		default:
			// System.out.println("View not supported: " + view);
			return (org.eclipse.gef.mvc.fx.parts.IContentPart<?>) new org.eclipse.gmf.runtime.notation.util.NotationSwitch() {
				@Override
				public Object caseDecorationNode(org.eclipse.gmf.runtime.notation.DecorationNode object) {
					return new org.eclipse.papyrus.gef4.gmf.parts.NotationLabelContentPart(object);
				}

				@Override
				public Object caseShape(org.eclipse.gmf.runtime.notation.Shape object) {
					return new org.eclipse.papyrus.gef4.gmf.parts.ShapeContentPart(object);
				}

				@Override
				public Object caseBasicCompartment(org.eclipse.gmf.runtime.notation.BasicCompartment object) {
					return new org.eclipse.papyrus.gef4.gmf.parts.NotationListCompartmentContentPart(object);
				}
			}.doSwitch(view);
		// return new EmptyContentPart(view);
		}
	}

}
