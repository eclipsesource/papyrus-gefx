package org.eclipse.papyrus.uml.gefdiag.statemachine.providers;

import org.eclipse.papyrus.gef4.gmf.parts.ShapeContentPart;
import org.eclipse.papyrus.gef4.gmf.parts.StructureCompartmentContentPart;

public class ContentPartProvider
		extends org.eclipse.papyrus.uml.gefdiag.common.provider.AbstractUMLContentPartProvider {

	@Override
	public org.eclipse.gef.mvc.fx.parts.IContentPart<?> createContentPart(org.eclipse.gmf.runtime.notation.View view) {
		switch (view.getType()) {

		case "Comment_AnnotatedElementEdge":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.CommentAnnotatedElementEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Pseudostate_ExitPointFloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateExitPointFloatingLabelEditPart(
					view);
		case "Constraint_BodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConstraintBodyEditPart(
					view);
		case "Trigger_DeferrableTriggerLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.DeferrableTriggerEditPart(
					view);
		case "Pseudostate_JunctionShape":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateJunctionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Pseudostate_EntryPointShape":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateEntryPointEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Transition_InternalTransitionLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.InternalTransitionEditPart(
					view);
		case "State_Shape_TN":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateEditPartTN(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Pseudostate_ForkFloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateForkNameEditPart(
					view);
		case "FinalState_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.FinalStateFloatingLabelEditPart(
					view);
		case "State_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateNameEditPart(
					view);
		case "Pseudostate_JoinFloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateJoinFloatingLabelEditPart(
					view);
		case "Constraint_ConstrainedElementEdge":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConstraintConstrainedElementEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Pseudostate_ForkStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateForkStereotypeEditPart(
					view);
		case "Constraint_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConstraintEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "StateMachine_RegionCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateMachineCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Pseudostate_ShallowHistoryFloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateShallowHistoryFloatingLabelEditPart(
					view);
		case "Pseudostate_EntryPointStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateEntryPointStereotypeEditPart(
					view);
		case "Pseudostate_DeepHistoryShape":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateDeepHistoryEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Pseudostate_ShallowHistoryShape":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateShallowHistoryEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Pseudostate_InitialFloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateInitialFloatingLabelEditPart(
					view);
		case "Pseudostate_ExitPointStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateExitPointStereotypeEditPart(
					view);
		case "Pseudostate_ChoiceShape":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateChoiceEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "State_RegionCompartment_TN":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateCompartmentEditPartTN(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "State_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateEditPartTN(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "State_FloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateFloatingLabelEditPart(
					view);
		case "Pseudostate_InitialShape":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateInitialEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Pseudostate_InitialStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateInitialStereotypeEditPart(
					view);
		case "Behavior_EntryBehaviorLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.EntryStateBehaviorEditPart(
					view);
		case "StateMachine_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateMachineNameEditPart(
					view);
		case "Constraint_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConstraintNameLabelEditPart(
					view);
		case "State_RegionCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Constraint_KeywordLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ContextLinkAppliedStereotypeEditPart(
					view);
		case "Pseudostate_DeepHistoryStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateDeepHistoryStereotypeEditPart(
					view);
		case "Region_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.RegionEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Pseudostate_TerminateFloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateTerminateFloatingLabelEditPart(
					view);
		case "Transition_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.TransitionEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "PapyrusUMLStateMachineDiagram":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PackageEditPart(
					(org.eclipse.gmf.runtime.notation.Diagram) view);
		case "FinalState_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.FinalStateEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Pseudostate_ShallowHistoryStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateShallowHistoryStereotypeEditPart(
					view);
		case "Pseudostate_TerminateStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateTerminateStereotypeEditPart(
					view);
		case "Transition_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.TransitionNameEditPart(
					view);
		case "Pseudostate_EntryPointFloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateEntryPointFloatingLabelEditPart(
					view);
		case "State_NameLabel_TN":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateNameEditPartTN(
					view);
		case "Pseudostate_ChoiceFloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateChoiceFloatingLabelEditPart(
					view);
		case "Region_SubvertexCompartment":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.RegionCompartmentEditPart(
					(org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "Pseudostate_JoinShape":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateJoinEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Comment_BodyLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.CommentBodyEditPart(
					view);
		case "Pseudostate_JoinStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateJoinStereotypeEditPart(
					view);
		case "FinalState_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.FinalStateStereotypeEditPart(
					view);
		case "Pseudostate_TerminateShape":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateTerminateEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Behavior_ExitBehaviorLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ExitStateBehaviorEditPart(
					view);
		case "StateMachine_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateMachineEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ConnectionPointReference_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConnectionPointReferenceEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "ConnectionPointReference_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConnectionPointReferenceStereotypeEditPart(
					view);
		case "Comment_Shape":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.CommentEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Constraint_ContextEdge":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ContextLinkEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Generalization_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.GeneralizationStereotypeEditPart(
					view);
		case "Behavior_DoActivityBehaviorLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.DoActivityStateBehaviorStateEditPart(
					view);
		case "Pseudostate_JunctionStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateJunctionStereotypeEditPart(
					view);
		case "Generalization_Edge":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.GeneralizationEditPart(
					(org.eclipse.gmf.runtime.notation.Connector) view);
		case "Pseudostate_JunctionFloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateJunctionFloatingLabelEditPart(
					view);
		case "Transition_GuardLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.TransitionGuardEditPart(
					view);
		case "ConnectionPointReference_NameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConnectionPointReferenceNameEditPart(
					view);
		case "Pseudostate_ChoiceStereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateChoiceStereotypeEditPart(
					view);
		case "Pseudostate_ForkShape":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateForkEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Pseudostate_ExitPointShape":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateExitPointEditPart(
					(org.eclipse.gmf.runtime.notation.Shape) view);
		case "Transition_StereotypeLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.TransitionStereotypeEditPart(
					view);
		case "Pseudostate_DeepHistoryFloatingNameLabel":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateDeepHistoryFloatingLabelEditPart(
					view);

		default:
			System.out.println("View not supported: " + view);
			return (org.eclipse.gef.mvc.fx.parts.IContentPart<?>) new org.eclipse.gmf.runtime.notation.util.NotationSwitch() {
				@Override
				public Object caseDecorationNode(org.eclipse.gmf.runtime.notation.DecorationNode object) {
					return new org.eclipse.papyrus.uml.gefdiag.common.parts.NamedElementLabelContentPart(object);
				}

				@Override
				public Object caseShape(org.eclipse.gmf.runtime.notation.Shape object) {
					return new ShapeContentPart(object);
				}

				@Override
				public Object caseBasicCompartment(org.eclipse.gmf.runtime.notation.BasicCompartment object) {
					return new StructureCompartmentContentPart(
							object);
				}
			}.doSwitch(view);
		// return new EmptyContentPart(view);
		}
	}

}
