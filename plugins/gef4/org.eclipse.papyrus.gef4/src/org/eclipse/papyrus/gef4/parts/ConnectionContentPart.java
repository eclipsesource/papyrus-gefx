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

import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.fx.nodes.Connection;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.notation.Anchor;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.decorations.DecorationFactory;
import org.eclipse.papyrus.gef4.fx.anchors.SlidableFxAnchor;
import org.eclipse.papyrus.gef4.utils.NotationUtil;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class ConnectionContentPart<E extends Edge> extends NotationContentPart<E, Connection> implements IPrimaryContentPart {

	/** Role for Connection source anchorage */
	public static final String SOURCE = "source";

	/** Role for Connection target anchorage */
	public static final String TARGET = "target";

	private SetMultimap<Object, String> contentAnchorages;

	public ConnectionContentPart(E view) {
		super(view);

//		setAdapter(new ConnectionBendPolicy());
//		// FIXME: works only for element-based links now
//		setAdapter(new ConnectionReconnectSemanticPolicy());
//		setAdapter(new ConnectionReconnectNotationPolicy());
	}

	@Override
	protected void doActivate() {
		super.doActivate();
		updateSourceAndTarget();
	}

	@Override
	protected NotificationListener createNotificationListener() {
		NotificationListener parentListener = super.createNotificationListener();

		return new NotificationListener() {

			@Override
			public void notifyChanged(final Notification msg) {

				if (!isActive()) {
					return;
				}

				if (!(msg.isTouch())) {
					if (isSourceOrTargetChanged(msg)) {
						updateSourceAndTarget();
					}

					if (isAnchorChanged(msg)) {
						updateAnchors();
					}
				}

				parentListener.notifyChanged(msg);
			}
		};
	}

	protected void updateSourceAndTarget() {
		SetMultimap<?, String> anchorages = doGetContentAnchorages();
		SetMultimap<?, String> notationAnchorages = getNotationAnchorages();
		for (Map.Entry<?, String> entry : notationAnchorages.entries()) {
			if (!anchorages.containsKey(entry.getKey())) {
				attachToContentAnchorage(entry.getKey(), entry.getValue());
			}
		}

		for (Map.Entry<?, String> entry : anchorages.entries()) {
			if (!notationAnchorages.containsKey(entry.getKey())) {
				detachFromContentAnchorage(entry.getKey(), entry.getValue());
			}
		}
	}

	protected boolean isSourceOrTargetChanged(Notification msg) {
		return msg.getFeature() == NotationPackage.Literals.EDGE__SOURCE || msg.getFeature() == NotationPackage.Literals.EDGE__TARGET;
	}

	protected boolean isAnchorChanged(Notification msg) {
		return msg.getFeature() == NotationPackage.Literals.EDGE__SOURCE_ANCHOR || msg.getFeature() == NotationPackage.Literals.EDGE__TARGET_ANCHOR || msg.getFeature() == NotationPackage.Literals.IDENTITY_ANCHOR__ID;
	}

	protected IContentPart<? extends Node> getSourcePart() {
		return getContentPart(getView().getSource());
	}

	protected IContentPart<? extends Node> getTargetPart() {
		return getContentPart(getView().getTarget());
	}

	@Override
	protected Connection doCreateVisual() {
		return new Connection();
	}

	@Override
	protected void refreshVisualInTransaction(Connection connection) {
		super.refreshVisualInTransaction(connection);


		String sourceDecorationName = NotationUtil.getSourceDecoration(getView());
		Shape sourceDecoration = getDecoration(sourceDecorationName);
		connection.setStartDecoration(sourceDecoration);

		String targetDecorationName = NotationUtil.getTargetDecoration(getView());
		Shape targetDecoration = getDecoration(targetDecorationName);
		connection.setEndDecoration(targetDecoration);

		// connection.getCurveNode().setStrokeWidth(1);
		// TODO parse bendpoints and corner radius and jumps and...

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
	public SetMultimap<? extends Object, String> doGetContentAnchorages() {
		if (contentAnchorages == null) { // Cannot initialize earlier, as this may be (indirectly) called from my parent's constructor. I may not be fully initialized yet...
			contentAnchorages = HashMultimap.create();
		}
		return contentAnchorages;
	}

	public SetMultimap<? extends Object, String> getNotationAnchorages() {
		SetMultimap<View, String> anchorages = HashMultimap.create();
		if (getView().getSource() != null || getView().getTarget() != null) { // Avoid creating partial anchorages
			anchorages.put(getView().getSource(), SOURCE);
			anchorages.put(getView().getTarget(), TARGET);
		}
		return anchorages;
	}

	protected void updateAnchors() {
		Anchor sourceAnchor = getView().getSourceAnchor();
		Anchor targetAnchor = getView().getTargetAnchor();

		if (sourceAnchor != null) {
			IVisualPart<? extends Node> anchorage = getAnchorage(SOURCE);
			if (anchorage != null) {
				getVisual().setStartAnchor(new SlidableFxAnchor(anchorage.getVisual(), sourceAnchor));
			}
		}
		if (targetAnchor != null) {
			IVisualPart<? extends Node> anchorage = getAnchorage(TARGET);
			if (anchorage != null) {
				getVisual().setEndAnchor(new SlidableFxAnchor(anchorage.getVisual(), sourceAnchor));
			}
		}

		refreshVisual();
	}

	protected IVisualPart<? extends Node> getAnchorage(String role) {
		if (role == null) {
			return null;
		}

		for (Map.Entry<IVisualPart<? extends Node>, String> entry : getAnchoragesUnmodifiable().entries()) {
			if (role.equals(entry.getValue())) {
				return entry.getKey();
			}
		}

		return null;
	}

	@Override
	protected void doAttachToAnchorageVisual(IVisualPart<? extends Node> anchorage, String role) {
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
	protected void doDetachFromAnchorageVisual(
			IVisualPart<? extends Node> anchorage, String role) {
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
	public void doAttachToContentAnchorage(Object contentAnchorage, String role) {
		contentAnchorages.put(contentAnchorage, role);
	}

	@Override
	public void doDetachFromContentAnchorage(Object contentAnchorage, String role) {
		contentAnchorages.put(contentAnchorage, role);
	}

	@Override
	protected void doAddChildVisual(IVisualPart<? extends Node> child, int index) {
		// Floating labels attached to the edge
		// Nothing yet
	}

	@Override
	protected void doRemoveChildVisual(IVisualPart<? extends Node> child, int index) {
		// Floating labels attached to the edge
		// Nothing yet
	}

	@Override
	protected String getStyleClass() {
		return "genericConnection";
	}

}
