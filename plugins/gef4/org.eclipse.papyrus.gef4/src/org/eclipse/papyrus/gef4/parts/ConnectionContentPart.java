/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.parts;

import org.eclipse.gef4.fx.nodes.FXConnection;
import org.eclipse.gef4.mvc.fx.policies.FXBendPolicy;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.fx.anchors.SlidableFxAnchor;
import org.eclipse.papyrus.gef4.policies.ConnectionBendPolicy;
import org.eclipse.papyrus.gef4.policies.ConnectionReconnectNotationPolicy;
import org.eclipse.papyrus.gef4.policies.ConnectionReconnectSemanticPolicy;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import javafx.scene.Node;

public class ConnectionContentPart<E extends Edge> extends NotationContentPart<E, FXConnection> {

	/** Role for Connection source anchorage */
	public static final String SOURCE = "source";

	/** Role for Connection target anchorage */
	public static final String TARGET = "target";

	protected ConnectionContentPart(E view) {
		super(view);

		setAdapter(FXBendPolicy.class, new ConnectionBendPolicy());
		// FIXME: works only for element-based links now
		setAdapter(ConnectionReconnectSemanticPolicy.class, new ConnectionReconnectSemanticPolicy());
		setAdapter(ConnectionReconnectNotationPolicy.class, new ConnectionReconnectNotationPolicy());
	}

	@Override
	protected void doActivate() {
		super.doActivate();
		configureAnchorages();
	}

	protected void configureAnchorages() {
		View source = getView().getSource();
		View target = getView().getTarget();

		IContentPart<Node, ? extends Node> sourcePart = getViewer().getContentPartMap().get(source);
		IContentPart<Node, ? extends Node> targetPart = getViewer().getContentPartMap().get(target);

		// addAnchorage(sourcePart, SOURCE);
		// addAnchorage(targetPart, TARGET);
	}

	protected IContentPart<Node, ? extends Node> getSourcePart() {
		return getContentPart(getView().getSource());
	}

	protected IContentPart<Node, ? extends Node> getTargetPart() {
		return getContentPart(getView().getTarget());
	}

	@Override
	protected FXConnection doCreateVisual() {
		return new FXConnection();
	}

	@Override
	protected void doRefreshVisual(FXConnection connection) {
		super.doRefreshVisual(connection);

		connection.getCurveNode().setStrokeWidth(2);
		// TODO parse bendpoints and corner radius and jumps and...

		// TODO Implement IFXDecoration (Arrow, Circle, ...)
	}

	@Override
	public SetMultimap<? extends Object, String> getContentAnchorages() {
		SetMultimap<View, String> anchorages = HashMultimap.create();
		anchorages.put(getView().getSource(), SOURCE);
		anchorages.put(getView().getTarget(), TARGET);
		return anchorages;
	}

	@Override
	protected void attachToAnchorageVisual(IVisualPart<Node, ? extends Node> anchorage, String role) {
		Edge notation = getView();
		switch (role) {
		case SOURCE:
			getVisual().setStartAnchor(
					new SlidableFxAnchor(anchorage.getVisual(), notation.getSourceAnchor()));
			break;
		case TARGET:
			getVisual().setEndAnchor(
					new SlidableFxAnchor(anchorage.getVisual(), notation.getTargetAnchor()));
			break;
		default:
			throw new IllegalStateException("Cannot attach to anchor with role <" + role + ">.");
		}
	}

	@Override
	protected void detachFromAnchorageVisual(
			IVisualPart<Node, ? extends Node> anchorage, String role) {
		if (SOURCE.equals(role)) {
			getVisual().setStartPoint(getVisual().getStartPoint());
		} else if (TARGET.equals(role)) {
			getVisual().setEndPoint(getVisual().getEndPoint());
		} else {
			throw new IllegalStateException(
					"Cannot detach from anchor with role <" + role + ">.");
		}
	}

	@Override
	public void attachToContentAnchorage(Object contentAnchorage, String role) {
		// FIXME: NOOP for now
	}

	@Override
	public void detachFromContentAnchorage(Object contentAnchorage, String role) {
		// FIXME: NOOP for now
	}

	@Override
	protected void addChildVisual(IVisualPart<Node, ? extends Node> child, int index) {
		// Floating labels attached to the edge
		// Nothing yet
	}

	@Override
	protected void removeChildVisual(IVisualPart<Node, ? extends Node> child, int index) {
		// Floating labels attached to the edge
		// Nothing yet
	}

	@Override
	protected String getStyleClass() {
		return "genericConnection";
	}

}
