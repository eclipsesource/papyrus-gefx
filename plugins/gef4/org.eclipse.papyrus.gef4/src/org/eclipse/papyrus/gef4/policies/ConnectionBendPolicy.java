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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.gef4.fx.anchors.IFXAnchor;
import org.eclipse.gef4.fx.nodes.FXUtils;
import org.eclipse.gef4.geometry.convert.fx.Geometry2JavaFX;
import org.eclipse.gef4.geometry.convert.fx.JavaFX2Geometry;
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.mvc.fx.policies.FXBendPolicy;
import org.eclipse.gef4.mvc.operations.ITransactional;
import org.eclipse.gef4.mvc.operations.ITransactionalOperation;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gef4.mvc.policies.IPolicy;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.papyrus.gef4.fx.anchors.PositionalAnchorProvider;
import org.eclipse.papyrus.gef4.utils.OperationBuilder;

import com.google.common.reflect.TypeToken;
import com.google.inject.Provider;

import javafx.scene.Node;

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
public class ConnectionBendPolicy extends FXBendPolicy {

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

	@SuppressWarnings("serial")
	@Override
	protected IFXAnchor findOrCreateAnchor(Point positionInLocal,
			boolean canConnect) {

		IFXAnchor anchor = null;
		// try to find an anchor that is provided from an underlying node
		if (canConnect) {
			Point selectedPointCurrentPositionInScene = JavaFX2Geometry
					.toPoint(getConnection().localToScene(
							Geometry2JavaFX.toFXPoint(positionInLocal)));

			List<Node> pickedNodes = FXUtils.getNodesAt(getHost().getRoot()
					.getVisual(), selectedPointCurrentPositionInScene.x,
					selectedPointCurrentPositionInScene.y);
			IVisualPart<Node, ? extends Node> anchorPart = getAnchorPart(getParts(pickedNodes));
			if (anchorPart != null) {
				Provider<? extends IFXAnchor> anchorProvider = anchorPart.getAdapter(
						new TypeToken<Provider<? extends IFXAnchor>>() {
						});

				if (anchorProvider instanceof PositionalAnchorProvider) {
					IVisualPart<Node, ? extends Node> connectionPart = getHost();
					return ((PositionalAnchorProvider) anchorProvider).getForContext(selectedPointCurrentPositionInScene, connectionPart);
				} else {
					return anchorProvider.get();
				}
			}
		}
		if (anchor == null) {
			anchor = createUnconnectedAnchor(positionInLocal);
		}
		return anchor;
	}

	@SuppressWarnings("serial")
	// FIXME: change to protected in super-class, use lambda
	// FIXME: or extract?
	private IContentPart<Node, ? extends Node> getAnchorPart(
			List<IContentPart<Node, ? extends Node>> partsUnderMouse) {

		for (IContentPart<Node, ? extends Node> cp : partsUnderMouse) {
			IContentPart<Node, ? extends Node> part = cp;
			Provider<? extends IFXAnchor> anchorProvider = part
					.getAdapter(new TypeToken<Provider<? extends IFXAnchor>>() {
					});

			if (anchorProvider != null && anchorProvider.get() != null) {
				return part;
			}
		}
		return null;
	}

	private List<IContentPart<Node, ? extends Node>> getParts(
			List<Node> nodesUnderMouse) {
		List<IContentPart<Node, ? extends Node>> parts = new ArrayList<IContentPart<Node, ? extends Node>>();

		Map<Node, IVisualPart<Node, ? extends Node>> partMap = getHost()
				.getRoot().getViewer().getVisualPartMap();
		for (Node node : nodesUnderMouse) {
			if (partMap.containsKey(node)) {
				IVisualPart<Node, ? extends Node> part = partMap.get(node);
				if (part instanceof IContentPart) {
					parts.add((IContentPart<Node, ? extends Node>) part);
				}
			}
		}
		return parts;
	}

	protected <P extends IPolicy<? extends Node> & ITransactional> IUndoableOperation requestFromTransactionalPolicy(Class<P> policyClass) {
		P policy = getAdaptable().getAdapter(policyClass);
		if (policy == null) {
			return null;
		}
		policy.init();
		return policy.commit();
	}

}
