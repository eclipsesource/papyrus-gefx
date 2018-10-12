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

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.gef.fx.anchors.IAnchor;
import org.eclipse.gef.fx.nodes.Connection;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.parts.IBendableContentPart.BendPoint;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.providers.IAnchorProvider;
import org.eclipse.papyrus.gef4.decorations.DecorationFactory;
import org.eclipse.papyrus.gef4.services.ConnectionService;
import org.eclipse.papyrus.gef4.services.HelperProvider;
import org.eclipse.papyrus.gef4.services.style.EdgeStyleService;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class ConnectionContentPart<MODEL> extends BaseContentPart<MODEL, Group> implements IPrimaryContentPart {

	/** Role for Connection source anchorage */
	public static final String SOURCE = "source";

	/** Role for Connection target anchorage */
	public static final String TARGET = "target";

	private EdgeStyleService edgeStyleService;

	private ConnectionService connectionService;

	private Connection connection;

	public ConnectionContentPart(MODEL model) {
		super(model);

		// setAdapter(new ConnectionBendPolicy());
		// // FIXME: works only for element-based links now
		// setAdapter(new ConnectionReconnectSemanticPolicy());
		// setAdapter(new ConnectionReconnectNotationPolicy());
	}

	@Override
	public boolean isSelectable() {
		// FIXME Connections should be selectable. However, we don't have a proper
		// selection feedback for Connections yet, so trying to select them would
		// crash the diagram. We don't want that :)
		return false;
	}

	@Override
	protected Group doCreateVisual() {
		this.connection = new Connection();
		return new Group(connection);
	}

	@Inject
	public void setEdgeStyleService(HelperProvider<EdgeStyleService> provider) {
		this.edgeStyleService = provider.get(this);
	}

	protected EdgeStyleService getEdgeStyleService() {
		return this.edgeStyleService;
	}

	@Override
	protected void refreshVisualInTransaction(Group group) {
		super.refreshVisualInTransaction(group);

		refreshDecorations();
		refreshBendpoints();
	}

	// TODO: Currently we only focus on visuals. For interactions, to rely on GEFx's BendConnectionPolicy,
	// we may need to implement IBendableContentPart later on.
	protected void refreshBendpoints() {
		connection.removeAllControlPoints();
		int i = 0;
		for (BendPoint bendpoint : getContentBendPoints()) {
			connection.addControlPoint(i++, getPosition(bendpoint));
		}
	}

	protected Point getPosition(BendPoint modelBendpoint) {
		return modelBendpoint.getPosition();
	}

	@Inject
	public void setConnectionService(HelperProvider<ConnectionService> connectionService) {
		this.connectionService = connectionService.get(this);
	}

	protected ConnectionService getConnectionService() {
		return this.connectionService;
	}

	@Override
	protected List<? extends MODEL> getContentChildren() {
		return super.getContentChildren();
	}

	public List<BendPoint> getContentBendPoints() {
		return getConnectionService().getModelBendpoints();
	}

	protected void refreshDecorations() {
		String sourceDecorationName = getEdgeStyleService().getSourceDecoration();
		Shape sourceDecoration = getDecoration(sourceDecorationName);
		connection.setStartDecoration(sourceDecoration);

		String targetDecorationName = getEdgeStyleService().getTargetDecoration();
		Shape targetDecoration = getDecoration(targetDecorationName);
		connection.setEndDecoration(targetDecoration);

		// connection.getCurveNode().setStrokeWidth(1);
		// TODO parse corner radius and jumps and routers...

		// TODO Implement IFXDecoration (Arrow, Circle, ...)
	}

	protected Shape getDecoration(String name) {
		switch (name) {
		case DecorationFactory.BLACK_ARROW:
			Shape blackArrow = DecorationFactory.instance.createClosedArrow();
			blackArrow.setFill(Color.BLACK);
			return blackArrow;
		case DecorationFactory.WHITE_ARROW:
			Shape whiteArrow = DecorationFactory.instance.createClosedArrow();
			whiteArrow.setFill(Color.WHITE);
			return whiteArrow;
		case DecorationFactory.OPEN_ARROW:
			return DecorationFactory.instance.createOpenArrow();
		case DecorationFactory.CIRCLE:
			return DecorationFactory.instance.createCircle();
		case DecorationFactory.CROSS_CIRCLE:
			return DecorationFactory.instance.createCrossCircle();
		case DecorationFactory.WHITE_DIAMOND:
			return DecorationFactory.instance.createEmptyDiamond();
		case DecorationFactory.BLACK_DIAMOND:
			return DecorationFactory.instance.createFullDiamond();
		default:
			return null;
		}
	}

	@Override
	protected void doAttachToAnchorageVisual(IVisualPart<?> anchorage, String role) {
		IAnchorProvider adapter = anchorage.getAdapter(IAnchorProvider.class);
		if (adapter == null) {
			String error = String.format("Unable to anchor part %s to anchorage %s (Role: %s)", this.getClass().getSimpleName(), anchorage.getClass().getSimpleName(), role);
			throw new IllegalStateException(error);
		}
		IAnchor anchor = adapter.get(this, role);
		switch (role) {
		case SOURCE:
			connection.setStartAnchor(anchor);
			break;
		case TARGET:
			connection.setEndAnchor(anchor);
			break;
		default:
			throw new IllegalStateException("Cannot attach to anchor with role <" + role + ">.");
		}
	}

	@Override
	public void updateAnchors() {
		for (Map.Entry<IVisualPart<?>, String> entry : getAnchoragesUnmodifiable().entries()) {
			IVisualPart<?> anchorage = entry.getKey();
			String role = entry.getValue();
			doAttachToAnchorageVisual(anchorage, role);
		}

		refreshVisual();
	}

	@Override
	protected void doDetachFromAnchorageVisual(
			IVisualPart<? extends Node> anchorage, String role) {
		if (SOURCE.equals(role)) {
			connection.setStartPoint(connection.getStartPoint());
		} else if (TARGET.equals(role)) {
			connection.setEndPoint(connection.getEndPoint());
		} else {
			throw new IllegalStateException(
					"Cannot detach from anchor with role <" + role + ">.");
		}
	}

	@Override
	protected void doAddChildVisual(IVisualPart<? extends Node> child, int index) {
		// Floating labels attached to the edge
		Node childVisual = child.getVisual();
		if (childVisual != null) {
			getVisual().getChildren().add(index, childVisual);
		}
	}

	@Override
	protected void doRemoveChildVisual(IVisualPart<? extends Node> child, int index) {
		// Floating labels attached to the edge
		Node childVisual = child.getVisual();
		if (childVisual != null) {
			getVisual().getChildren().remove(childVisual);
		}
	}

	@Override
	protected String getStyleClass() {
		return "genericConnection";
	}

}
