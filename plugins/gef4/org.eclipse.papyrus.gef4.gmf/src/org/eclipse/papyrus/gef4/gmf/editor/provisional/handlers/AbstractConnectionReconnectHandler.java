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
package org.eclipse.papyrus.gef4.gmf.editor.provisional.handlers;

import java.util.Optional;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.fx.anchors.IAnchor;
import org.eclipse.gef.fx.nodes.Connection;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.parts.PartUtils;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

import com.google.common.collect.SetMultimap;

import javafx.scene.Node;

public abstract class AbstractConnectionReconnectHandler extends AbstractHandler {

	/**
	 * FIXME: It would be better to use IPolicy<Connection> but we cannot do it,
	 * because IPolicy must be <V, VR> and not <VR>
	 */
	protected Connection getConnection() {
		return (Connection) getHost().getVisual();
	}

	/**
	 * @param anchor
	 * @return model of the node which given anchor is connected to, which is
	 *         expected to be a {@link View} for all notation-based connections
	 */
	protected View getAnchorageView(IAnchor anchor) {
		return (View) getAnchorageContent(anchor);
	}

	/**
	 * @param anchor
	 * @return model of the node which given anchor is connected to
	 */
	protected Object getAnchorageContent(IAnchor anchor) {
		Node anchorageNode = anchor.getAnchorage();
		if (anchorageNode != getConnection()) {
			IViewer viewer = getHost().getRoot().getViewer();

			IVisualPart<? extends Node> part = PartUtils.retrieveVisualPart(viewer, anchorageNode);

			if (part instanceof IContentPart) {
				return ((IContentPart<? extends Node>) part).getContent();
			}
		}
		return null;
	}

	/**
	 * Safe assumption, it does not makes sense to attach notation- or semantic-
	 * policies to not-content parts.
	 */
	@Override
	public IContentPart<? extends Node> getHost() {
		return (IContentPart<? extends Node>) super.getHost();
	}

	protected Edge getHostNotationEdge() {
		return (Edge) getHost().getContent();
	}

	protected TransactionalEditingDomain getDomain() {
		View hostView = (View) getHost().getContent();
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(hostView);
		assert domain != null;

		return domain;
	}

	/**
	 * Returns the anchorage for given role from the host <strong>model</strong>
	 */
	protected Optional<View> findHostAnchorageForRole(String endRole) {
		return firstKeyForValue(getHost().getContentAnchoragesUnmodifiable(), endRole)
				.map(AbstractConnectionReconnectHandler::asView);
	}

	protected static <K, V> Optional<K> firstKeyForValue(SetMultimap<K, V> setMultimap, V value) {
		return setMultimap.entries().stream() //
				.filter(e -> e.getValue().equals(value)) //
				.map(e -> e.getKey()).findFirst();
	}

	protected static View asView(Object eobject) {
		return eobject instanceof View ? (View) eobject : null;
	}

	public abstract IUndoableOperation createOperation();

}
