/*****************************************************************************
 * Copyright (c) 2015 - 2018 CEA LIST, EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *	EclipseSource - Support Bendpoints, Floating Labels & Line Attributes
 *****************************************************************************/
package org.eclipse.papyrus.gef4.parts;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.gef.fx.anchors.IAnchor;
import org.eclipse.gef.fx.nodes.Connection;
import org.eclipse.gef.fx.nodes.GeometryNode;
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
	protected Group doCreateVisual() {
		// Wrap the Connection in a Group, so we can add floating labels around it
		// Note: this breaks GEF Connection Policies
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

		// TODO parse corner radius and jumps and routers...

		refreshAnchors();
		refreshDecorations();
		refreshBendpoints();
		refreshLineStyle();
	}

	/**
	 * Refresh the line visual appearance: width, style, color
	 */
	protected void refreshLineStyle() {
		Node curve = connection.getCurve();
		if (curve instanceof GeometryNode) {
			int lineWidth = getEdgeStyleService().getLineWidth();
			if (lineWidth < 1) {
				lineWidth = 1;
			}
			Color lineColor = getEdgeStyleService().getLineColor();
			if (lineColor == null) {
				lineColor = Color.BLACK;
			}

			List<Double> strokeDashArray = getEdgeStyleService().getDashStyle();
			if (strokeDashArray == null) {
				strokeDashArray = Collections.emptyList();
			}

			((GeometryNode<?>) curve).setStroke(lineColor);
			((GeometryNode<?>) curve).setStrokeWidth(lineWidth);
			((GeometryNode<?>) curve).getStrokeDashArray().setAll(strokeDashArray);
		}
		// Else: Custom Connection Node; we can't style it.
	}

	// TODO: Currently we only focus on visuals. For interactions, to rely on GEFx's BendConnectionPolicy,
	// we may need to implement IBendableContentPart later on. Alternatively, we may use custom handles, independent from the part
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
		Node sourceDecoration = getDecoration(sourceDecorationName);
		connection.setStartDecoration(sourceDecoration);

		String targetDecorationName = getEdgeStyleService().getTargetDecoration();
		Node targetDecoration = getDecoration(targetDecorationName);
		connection.setEndDecoration(targetDecoration);
	}

	protected Node getDecoration(String name) {
		if (name == null) {
			return null;
		}
		Color lineColor = getEdgeStyleService().getLineColor();
		int lineWidth = getEdgeStyleService().getLineWidth();

		switch (name) {
		case DecorationFactory.BLACK_ARROW:
			return DecorationFactory.instance.createFullArrow(lineColor, lineWidth);
		case DecorationFactory.WHITE_ARROW:
			return DecorationFactory.instance.createEmptyArrow(lineColor, lineWidth);
		case DecorationFactory.OPEN_ARROW:
			return DecorationFactory.instance.createOpenArrow(lineColor, lineWidth);
		case DecorationFactory.CIRCLE:
			return DecorationFactory.instance.createCircle(lineColor, lineWidth);
		case DecorationFactory.CROSS_CIRCLE:
			return DecorationFactory.instance.createCrossCircle(lineColor, lineWidth);
		case DecorationFactory.WHITE_DIAMOND:
		case DecorationFactory.SOLID_DIAMOND_EMPTY:
			return DecorationFactory.instance.createEmptyDiamond(lineColor, lineWidth);
		case DecorationFactory.BLACK_DIAMOND:
		case DecorationFactory.SOLID_DIAMOND_FILLED:
			return DecorationFactory.instance.createFullDiamond(lineColor, lineWidth);
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

	protected void refreshAnchors() {
		for (Map.Entry<IVisualPart<?>, String> entry : getAnchoragesUnmodifiable().entries()) {
			IVisualPart<?> anchorage = entry.getKey();
			String role = entry.getValue();
			doAttachToAnchorageVisual(anchorage, role);
		}
	}

	@Override
	public void updateAnchors() {
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
	protected Collection<String> getStyleClasses() {
		return Collections.singletonList("genericConnection");
	}

	public Connection getConnection() {
		return connection;
	}

}
