package org.eclipse.papyrus.gef4.policies;

import java.util.List;
import java.util.function.Consumer;

import org.eclipse.gef4.geometry.planar.Dimension;
import org.eclipse.gef4.mvc.fx.parts.FXPartUtils;
import org.eclipse.gef4.mvc.fx.policies.IFXOnDragPolicy;
import org.eclipse.gef4.mvc.fx.tools.FXClickDragTool;
import org.eclipse.gef4.mvc.models.SelectionModel;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gef4.mvc.policies.AbstractInteractionPolicy;
import org.eclipse.papyrus.gef4.parts.NotationContentPart;
import org.eclipse.papyrus.gef4.utils.ModelUtil;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public abstract class AbstractMultiSelectionDragPolicy extends AbstractInteractionPolicy<Node> implements IFXOnDragPolicy {

	/**
	 * Propagate the event to selected elements. //FIXME: Propagating to Drag Policies is not relevant, as several policies can react to the drag action. We also need to check the kind of policy (e.g. Move vs Resize vs Select ...)
	 *
	 * @param e
	 *            the e
	 * @param delta
	 *            the delta
	 * @param actionToPropagate
	 *            the action to propagate
	 */
	protected final void propagate(final MouseEvent e, final Dimension delta, final Consumer<IFXOnDragPolicy> actionToPropagate) {
		final SelectionModel<Node> selectionModel = ModelUtil.getSelectionModel(getHost());

		List<IContentPart<Node, ? extends Node>> selection = selectionModel.getSelectionUnmodifiable();
		if (selection.size() > 1) {

			// If I'm the main receiver of the event, I propagate it to other selected elements
			// If I'm not the main receiver, do nothing; someone else will do the propagation

			Node targetNode = null;
			if (e.getTarget() instanceof Node) {
				targetNode = (Node) e.getTarget();
			}
			if (targetNode == null) {
				return;
			}
			if (FXPartUtils.retrieveVisualPart(getHost().getRoot().getViewer(), targetNode) == getHost()) {

				for (final IContentPart<Node, ? extends Node> selectedPart : selection) {
					if (selectedPart != getPrimaryHost()) {
						for (final IFXOnDragPolicy dragPolicy : selectedPart.getAdapters(FXClickDragTool.ON_DRAG_POLICY_KEY).values()) {
							actionToPropagate.accept(dragPolicy);
						}
					}
				}

			}
		}
	}

	boolean isPropagating = false;


	protected final void propagate(final Consumer<IFXOnDragPolicy> actionToPropagate) {

		if (isPropagating) {
			return;
		}

		isPropagating = true;
		try {
			final SelectionModel<Node> selectionModel = ModelUtil.getSelectionModel(getHost());

			List<IContentPart<Node, ? extends Node>> selection = selectionModel.getSelectionUnmodifiable();
			if (selection.size() > 1) {
				for (final IContentPart<Node, ? extends Node> selectedPart : selection) {
					if (selectedPart != getPrimaryHost()) {
						for (final IFXOnDragPolicy dragPolicy : selectedPart.getAdapters(FXClickDragTool.ON_DRAG_POLICY_KEY).values()) {
							actionToPropagate.accept(dragPolicy);
						}
					}
				}
			}
		} finally {
			isPropagating = false;
		}
	}

	/**
	 * Gets the primary host.
	 *
	 * @return the primary host
	 */
	protected IVisualPart<Node, ? extends Node> getPrimaryHost() {
		IVisualPart<Node, ? extends Node> host = getHost();
		if (host instanceof NotationContentPart) {
			host = ((NotationContentPart<?, ?>) host).getPrimaryContentPart();
		}
		return host;
	}

}
