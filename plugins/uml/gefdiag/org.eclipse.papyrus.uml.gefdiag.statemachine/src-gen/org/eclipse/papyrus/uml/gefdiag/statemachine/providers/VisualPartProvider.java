package org.eclipse.papyrus.uml.gefdiag.statemachine.providers;

import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.papyrus.uml.gefdiag.common.provider.AbstractUMLVisualPartProvider;

import javafx.scene.Node;

public class VisualPartProvider extends AbstractUMLVisualPartProvider {

	@Override
	public IContentPart<? extends Node> createContentPart(org.eclipse.gmf.runtime.notation.View view) {
		switch (view.getType()) {
		case "17002":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateExitPointStereotypeEditPart(view);
		case "7000":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.TransitionEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "7002":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.TransitionGuardEditPart(view);
		case "7001":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.TransitionNameEditPart(view);
		case "13000":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateShallowHistoryEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "13001":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateShallowHistoryFloatingLabelEditPart(view);
		case "7003":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.TransitionStereotypeEditPart(view);
		case "17000":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateExitPointEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "17001":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateExitPointFloatingLabelEditPart(view);
		case "3002":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.RegionCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "3000":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.RegionEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "670":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConstraintConstrainedElementEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "6666":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.CommentBodyEditPart(view);
		case "13002":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateShallowHistoryStereotypeEditPart(view);
		case "6669":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConstraintBodyEditPart(view);
		case "6668":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConstraintNameLabelEditPart(view);
		case "16000":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateEntryPointEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "6001":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateNameEditPart(view);
		case "6000":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "8501":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ContextLinkAppliedStereotypeEditPart(view);
		case "6002":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "12000":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateJunctionEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "16002":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateEntryPointStereotypeEditPart(view);
		case "16001":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateEntryPointFloatingLabelEditPart(view);
		case "8500":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ContextLinkEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "2001":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateMachineNameEditPart(view);
		case "2000":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateMachineEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "680":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.InternalTransitionEditPart(view);
		case "12002":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateJunctionStereotypeEditPart(view);
		case "12001":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateJunctionFloatingLabelEditPart(view);
		case "PapyrusUMLStateMachineDiagram":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PackageEditPart((org.eclipse.gmf.runtime.notation.Diagram) view);
		case "9001":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateJoinFloatingLabelEditPart(view);
		case "5000":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.FinalStateEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "9002":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateJoinStereotypeEditPart(view);
		case "5001":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.FinalStateFloatingLabelEditPart(view);
		case "5002":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.FinalStateStereotypeEditPart(view);
		case "15002":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateTerminateStereotypeEditPart(view);
		case "19000":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.GeneralizationEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "19003":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateFloatingLabelEditPart(view);
		case "15001":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateTerminateFloatingLabelEditPart(view);
		case "15000":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateTerminateEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "19002":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.GeneralizationStereotypeEditPart(view);
		case "9000":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateJoinEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "690":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.EntryStateBehaviorEditPart(view);
		case "691":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.DoActivityStateBehaviorStateEditPart(view);
		case "692":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ExitStateBehaviorEditPart(view);
		case "2002":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateMachineCompartmentEditPart((org.eclipse.gmf.runtime.notation.DecorationNode) view);
		case "11001":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateChoiceFloatingLabelEditPart(view);
		case "11000":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateChoiceEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "11002":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateChoiceStereotypeEditPart(view);
		case "8002":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateInitialStereotypeEditPart(view);
		case "8000":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateInitialEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "8001":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateInitialFloatingLabelEditPart(view);
		case "14001":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateDeepHistoryFloatingLabelEditPart(view);
		case "14002":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateDeepHistoryStereotypeEditPart(view);
		case "18000":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConnectionPointReferenceEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "18001":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConnectionPointReferenceNameEditPart(view);
		case "14000":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateDeepHistoryEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "18002":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConnectionPointReferenceStereotypeEditPart(view);
		case "666":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.CommentEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "667":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.CommentAnnotatedElementEditPart((org.eclipse.gmf.runtime.notation.Connector) view);
		case "10000":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateForkEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "668":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConstraintEditPart((org.eclipse.gmf.runtime.notation.Shape) view);
		case "10002":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateForkStereotypeEditPart(view);
		case "10001":
			return new org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateForkNameEditPart(view);
		default:
			return super.createContentPart(view);
		}
	}
}
