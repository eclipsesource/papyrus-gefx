package org.eclipse.papyrus.uml.gefdiag.statemachine.providers;

import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gmf.runtime.notation.BasicCompartment;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.util.NotationSwitch;
import org.eclipse.papyrus.gef4.parts.ListCompartmentContentPart;
import org.eclipse.papyrus.gef4.parts.NodeContentPart;
import org.eclipse.papyrus.gef4.provider.AbstractVisualPartProvider;
import org.eclipse.papyrus.uml.gefdiag.common.parts.NamedElementLabelContentPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.CommentAnnotatedElementEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.CommentBodyEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.CommentEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConnectionPointReferenceEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConnectionPointReferenceNameEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConnectionPointReferenceStereotypeEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConstraintBodyEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConstraintConstrainedElementEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConstraintNameLabelEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ContextLinkAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ContextLinkEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.DoActivityStateBehaviorStateEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.EntryStateBehaviorEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ExitStateBehaviorEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.FinalStateEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.FinalStateFloatingLabelEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.FinalStateStereotypeEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.GeneralizationEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.GeneralizationStereotypeEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.InternalTransitionEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PackageEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateChoiceEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateChoiceFloatingLabelEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateChoiceStereotypeEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateDeepHistoryEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateDeepHistoryFloatingLabelEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateDeepHistoryStereotypeEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateEntryPointEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateEntryPointFloatingLabelEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateEntryPointStereotypeEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateExitPointEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateExitPointFloatingLabelEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateExitPointStereotypeEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateForkEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateForkNameEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateForkStereotypeEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateInitialEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateInitialFloatingLabelEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateInitialStereotypeEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateJoinEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateJoinFloatingLabelEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateJoinStereotypeEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateJunctionEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateJunctionFloatingLabelEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateJunctionStereotypeEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateShallowHistoryEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateShallowHistoryFloatingLabelEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateShallowHistoryStereotypeEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateTerminateEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateTerminateFloatingLabelEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateTerminateStereotypeEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.RegionCompartmentEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.RegionEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateCompartmentEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateFloatingLabelEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateMachineCompartmentEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateMachineEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateMachineNameEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.StateNameEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.TransitionEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.TransitionGuardEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.TransitionNameEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.TransitionStereotypeEditPart;

import javafx.scene.Node;

public class VisualPartProvider extends AbstractVisualPartProvider {

	@Override
	public IContentPart<Node, ? extends Node> createContentPart(View view) {
		switch (view.getType()) {

		case "17002":
			return new PseudostateExitPointStereotypeEditPart((View) view);
		case "7000":
			return new TransitionEditPart((Connector) view);
		case "7002":
			return new TransitionGuardEditPart((View) view);
		case "7001":
			return new TransitionNameEditPart((View) view);
		case "13000":
			return new PseudostateShallowHistoryEditPart((Shape) view);
		case "13001":
			return new PseudostateShallowHistoryFloatingLabelEditPart((View) view);
		case "7003":
			return new TransitionStereotypeEditPart((View) view);
		case "17000":
			return new PseudostateExitPointEditPart((Shape) view);
		case "17001":
			return new PseudostateExitPointFloatingLabelEditPart((View) view);
		case "3002":
			return new RegionCompartmentEditPart((DecorationNode) view);
		case "3000":
			return new RegionEditPart((Shape) view);
		case "670":
			return new ConstraintConstrainedElementEditPart((Connector) view);
		case "6666":
			return new CommentBodyEditPart((View) view);
		case "13002":
			return new PseudostateShallowHistoryStereotypeEditPart((View) view);
		case "6669":
			return new ConstraintBodyEditPart((View) view);
		case "6668":
			return new ConstraintNameLabelEditPart((View) view);
		case "16000":
			return new PseudostateEntryPointEditPart((Shape) view);
		case "6001":
			return new StateNameEditPart((View) view);
		case "6000":
			return new StateEditPart((Shape) view);
		case "8501":
			return new ContextLinkAppliedStereotypeEditPart((View) view);
		case "6002":
			return new StateCompartmentEditPart((DecorationNode) view);
		case "12000":
			return new PseudostateJunctionEditPart((Shape) view);
		case "16002":
			return new PseudostateEntryPointStereotypeEditPart((View) view);
		case "16001":
			return new PseudostateEntryPointFloatingLabelEditPart((View) view);
		case "8500":
			return new ContextLinkEditPart((Connector) view);
		case "2001":
			return new StateMachineNameEditPart((View) view);
		case "2000":
			return new StateMachineEditPart((Shape) view);
		case "680":
			return new InternalTransitionEditPart((Shape) view);
		case "12002":
			return new PseudostateJunctionStereotypeEditPart((View) view);
		case "12001":
			return new PseudostateJunctionFloatingLabelEditPart((View) view);
		case "PapyrusUMLStateMachineDiagram":
			return new PackageEditPart((Diagram) view);
		case "9001":
			return new PseudostateJoinFloatingLabelEditPart((View) view);
		case "5000":
			return new FinalStateEditPart((Shape) view);
		case "9002":
			return new PseudostateJoinStereotypeEditPart((View) view);
		case "5001":
			return new FinalStateFloatingLabelEditPart((View) view);
		case "5002":
			return new FinalStateStereotypeEditPart((View) view);
		case "15002":
			return new PseudostateTerminateStereotypeEditPart((View) view);
		case "19000":
			return new GeneralizationEditPart((Connector) view);
		case "19003":
			return new StateFloatingLabelEditPart((View) view);
		case "15001":
			return new PseudostateTerminateFloatingLabelEditPart((View) view);
		case "15000":
			return new PseudostateTerminateEditPart((Shape) view);
		case "19002":
			return new GeneralizationStereotypeEditPart((View) view);
		case "9000":
			return new PseudostateJoinEditPart((Shape) view);
		case "690":
			return new EntryStateBehaviorEditPart((Shape) view);
		case "691":
			return new DoActivityStateBehaviorStateEditPart((Shape) view);
		case "692":
			return new ExitStateBehaviorEditPart((Shape) view);
		case "2002":
			return new StateMachineCompartmentEditPart((DecorationNode) view);
		case "11001":
			return new PseudostateChoiceFloatingLabelEditPart((View) view);
		case "11000":
			return new PseudostateChoiceEditPart((Shape) view);
		case "11002":
			return new PseudostateChoiceStereotypeEditPart((View) view);
		case "8002":
			return new PseudostateInitialStereotypeEditPart((View) view);
		case "8000":
			return new PseudostateInitialEditPart((Shape) view);
		case "8001":
			return new PseudostateInitialFloatingLabelEditPart((View) view);
		case "14001":
			return new PseudostateDeepHistoryFloatingLabelEditPart((View) view);
		case "14002":
			return new PseudostateDeepHistoryStereotypeEditPart((View) view);
		case "18000":
			return new ConnectionPointReferenceEditPart((Shape) view);
		case "18001":
			return new ConnectionPointReferenceNameEditPart((View) view);
		case "14000":
			return new PseudostateDeepHistoryEditPart((Shape) view);
		case "18002":
			return new ConnectionPointReferenceStereotypeEditPart((View) view);
		case "666":
			return new CommentEditPart((Shape) view);
		case "667":
			return new CommentAnnotatedElementEditPart((Connector) view);
		case "10000":
			return new PseudostateForkEditPart((Shape) view);
		case "668":
			return new ConstraintEditPart((Shape) view);
		case "10002":
			return new PseudostateForkStereotypeEditPart((View) view);
		case "10001":
			return new PseudostateForkNameEditPart((View) view);

		default:
			// System.out.println("View not supported: " + view);
			return (IContentPart<Node, ? extends Node>) new NotationSwitch() {
				@Override
				public Object caseDecorationNode(DecorationNode object) {
					return new NamedElementLabelContentPart(object);
				}

				@Override
				public Object caseShape(Shape object) {
					return new NodeContentPart(object);
				}

				@Override
				public Object caseBasicCompartment(BasicCompartment object) {
					return new ListCompartmentContentPart<DecorationNode>(object);
				}
			}.doSwitch(view);
		// return new EmptyContentPart(view);
		}
	}

}
