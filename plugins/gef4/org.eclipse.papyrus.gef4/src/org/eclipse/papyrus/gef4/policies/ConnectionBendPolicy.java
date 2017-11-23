/*****************************************************************************
 * Copyright (c) 2015 CEA LIST, Montages AG and others
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Michael Golubev (Montages) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.policies;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.gef.mvc.fx.operations.ITransactionalOperation;
import org.eclipse.gef.mvc.fx.policies.BendConnectionPolicy;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.papyrus.gef4.handlers.AbstractConnectionReconnectHandler;
import org.eclipse.papyrus.gef4.utils.OperationBuilder;

//
/**
 * FIXME: we have overridden some key parts of the FXBendPolicy and we probably want to override more.
 * Specifically I don't like how the {@link #locallyExecuteOperation()} works,
 * because it seems to break the ability to ban complex reroute operation by returning {@link UnexecutableCommand}
 * as part of the composite command.
 * <p/>
 * Also here we are chaining of 3 completely unrelated commands from 3 completely unrelated policies,
 * which would be better to do at the Tool. (need investigation on the ordering of the operations)
 * <p/>
 * So we will probably remove ```extends {@link FXBendPolicy}``` in future, it is mainly to
 * allow the existing tool to work.
 */
public class ConnectionBendPolicy extends BendConnectionPolicy {

	@Override
	public ITransactionalOperation commit() {
		IUndoableOperation visualUpdate = super.commit();
		if (visualUpdate == null) {
			return null;
		}

		return OperationBuilder.withForwardUndo() //
				.add(visualUpdate) //
				.add(getNotationUpdateOperation()) //
				.add(getSemanticUpdateOperation()) //
				.getResult();
	}

	protected IUndoableOperation getNotationUpdateOperation() {
		return requestFromTransactionalPolicy(ConnectionReconnectNotationPolicy.class);
	}

	protected IUndoableOperation getSemanticUpdateOperation() {
		return requestFromTransactionalPolicy(ConnectionReconnectSemanticPolicy.class);
	}

	// @SuppressWarnings("serial")
	// @Override
	// protected IAnchor findOrCreateAnchor(Point positionInLocal,
	// boolean canConnect) {
	//
	// IAnchor anchor = null;
	// // try to find an anchor that is provided from an underlying node
	// if (canConnect) {
	// Point selectedPointCurrentPositionInScene = FX2Geometry
	// .toPoint(getConnection().localToScene(
	// Geometry2FX.toFXPoint(positionInLocal)));
	//
	// List pickedNodes = NodeUtils.getNodesAt(getHost().getRoot()
	// .getVisual(), selectedPointCurrentPositionInScene.x,
	// selectedPointCurrentPositionInScene.y);
	// IVisualPart<? extends Node> anchorPart = getAnchorPart(getParts(pickedNodes));
	// if (anchorPart != null) {
	// Provider<? extends IAnchor> anchorProvider = anchorPart.getAdapter(
	// new TypeToken<Provider<? extends IAnchor>>() {
	// });
	//
	// if (anchorProvider instanceof PositionalAnchorProvider) {
	// IVisualPart<? extends Node> connectionPart = getHost();
	// return ((PositionalAnchorProvider) anchorProvider).getForContext(selectedPointCurrentPositionInScene, connectionPart);
	// } else {
	// return anchorProvider.get();
	// }
	// }
	// }
	// if (anchor == null) {
	// anchor = createUnconnectedAnchor(positionInLocal);
	// }
	// return anchor;
	// }

	// @SuppressWarnings("serial")
	// // FIXME: change to protected in super-class, use lambda
	// // FIXME: or extract?
	// private IContentPart<? extends Node> getAnchorPart(
	// List<IContentPart<? extends Node>> partsUnderMouse) {
	//
	// for (IContentPart<? extends Node> cp : partsUnderMouse) {
	// IContentPart<? extends Node> part = cp;
	// Provider<? extends IAnchor> anchorProvider = part
	// .getAdapter(new TypeToken<Provider<? extends IAnchor>>() {
	// });
	//
	// if (anchorProvider != null && anchorProvider.get() != null) {
	// return part;
	// }
	// }
	// return null;
	// }
	//
	// private List<IContentPart<? extends Node>> getParts(
	// List<? extends Node> nodesUnderMouse) {
	// List<IContentPart<? extends Node>> parts = new ArrayList<IContentPart<? extends Node>>();
	//
	// Map<Node, IVisualPart<? extends Node>> partMap = getHost()
	// .getRoot().getViewer().getVisualPartMap();
	// for (Node node : nodesUnderMouse) {
	// if (partMap.containsKey(node)) {
	// IVisualPart<? extends Node> part = partMap.get(node);
	// if (part instanceof IContentPart) {
	// parts.add((IContentPart<? extends Node>) part);
	// }
	// }
	// }
	// return parts;
	// }

	protected <P extends AbstractConnectionReconnectHandler> IUndoableOperation requestFromTransactionalPolicy(Class<P> policyClass) {
		P policy = getAdaptable().getAdapter(policyClass);
		if (policy == null) {
			return null;
		}
		return policy.createOperation();
	}

}
