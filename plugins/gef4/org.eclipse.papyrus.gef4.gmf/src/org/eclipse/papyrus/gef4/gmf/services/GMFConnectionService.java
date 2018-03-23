package org.eclipse.papyrus.gef4.gmf.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.parts.IBendableContentPart.BendPoint;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.parts.ConnectionContentPart;
import org.eclipse.papyrus.gef4.services.AnchorageService;
import org.eclipse.papyrus.gef4.services.ConnectionService;
import org.eclipse.papyrus.gef4.utils.ActivatableBound;

import com.google.common.base.Predicates;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import javafx.scene.Node;

/**
 * An {@link AnchorageService} implementation for GMF {@link Edge} Anchors
 */
public class GMFConnectionService extends ActivatableBound<IVisualPart<?>>
		implements AnchorageService, ConnectionService {

	/**
	 * From
	 * org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.AbsoluteBendpointsConvention.ConventionBase
	 */
	private static final int LINKLF_MAGIC = -643984;

	private DiagramEventBroker eventBroker;
	private NotificationListener notificationListener;
	private Edge edge;

	@Override
	protected void doActivate() {
		this.edge = getEdge();
		if (edge == null) {
			return;
		}
		notificationListener = msg -> {

			if (!isActive()) {
				return;
			}

			if (!(msg.isTouch())) {
				if (isSourceOrTargetChanged(msg)) {
					updateAnchorages();
				}

				if (isAnchorChanged(msg)) {
					updateAnchors();
				}

				if (isBendpointChanged(msg)) {
					updateBendpoints();
				}
			}
		};

		eventBroker.addNotificationListener(edge, notificationListener);
		eventBroker.addNotificationListener(edge.getBendpoints(), notificationListener);
	}

	private void updateBendpoints() {
		BaseContentPart<?, ?> host = getHost();
		host.refreshVisual(); // XXX This shouldn't be done synchronously
	}

	private void updateAnchorages() {
		BaseContentPart<?, ?> host = getHost();
		if (host != null) {
			host.updateAnchorages();
		}
	}

	private void updateAnchors() {
		BaseContentPart<?, ?> host = getHost();
		if (host != null) {
			host.updateAnchors();
		}
	}

	private BaseContentPart<?, ?> getHost() {
		if (getAdaptable() instanceof BaseContentPart) {
			return (BaseContentPart<?, ?>) getAdaptable();
		}
		return null;
	}

	@Inject
	protected void setEventBroker(DiagramEventBroker eventBroker) {
		this.eventBroker = eventBroker;
	}

	protected Edge getEdge() {
		BaseContentPart<?, ?> host = getHost();
		if (host != null && host.getContent() instanceof Edge) {
			return (Edge) host.getContent();
		}
		return null;
	}

	protected IVisualPart<? extends Node> getAnchorage(String role) {
		if (role == null) {
			return null;
		}

		for (Map.Entry<IVisualPart<? extends Node>, String> entry : getAdaptable().getAnchoragesUnmodifiable()
				.entries()) {
			if (role.equals(entry.getValue())) {
				return entry.getKey();
			}
		}

		return null;
	}

	@Override
	public SetMultimap<? extends Object, String> getModelAnchorages() {
		SetMultimap<View, String> anchorages = HashMultimap.create();
		if (getEdge() == null) {
			return anchorages;
		}
		if (getEdge().getSource() != null && getEdge().getTarget() != null) { // Avoid creating partial anchorages
			anchorages.put(getEdge().getSource(), ConnectionContentPart.SOURCE);
			anchorages.put(getEdge().getTarget(), ConnectionContentPart.TARGET);
		}
		return anchorages;
	}

	@Override
	protected void doDeactivate() {
		if (edge != null) {
			eventBroker.removeNotificationListener(edge, notificationListener);
		}
	}

	protected boolean isSourceOrTargetChanged(Notification msg) {
		return msg.getFeature() == NotationPackage.Literals.EDGE__SOURCE
				|| msg.getFeature() == NotationPackage.Literals.EDGE__TARGET;
	}

	protected boolean isAnchorChanged(Notification msg) {
		return msg.getFeature() == NotationPackage.Literals.EDGE__SOURCE_ANCHOR
				|| msg.getFeature() == NotationPackage.Literals.EDGE__TARGET_ANCHOR
				|| msg.getFeature() == NotationPackage.Literals.IDENTITY_ANCHOR__ID;
	}

	private boolean isBendpointChanged(Notification msg) {
		return edge != null && msg.getNotifier() == edge.getBendpoints();
	}

	@Override
	public List<BendPoint> getModelBendpoints() {
		if (edge == null) {
			return Collections.emptyList();
		}

		return getBendpoints(edge).stream() //
				.map(this::toGeometryPoint) //
				.filter(Predicates.notNull()) //
				.map(BendPoint::new) //
				.collect(Collectors.toList());
	}

	private Point toGeometryPoint(RelativeBendpoint gmfBendpoint) {
		if (isLinkLFBendpoint(gmfBendpoint)) {
			return new Point(gmfBendpoint.getSourceX(), gmfBendpoint.getSourceY());
		}
		return null; // XXX Legacy Papyrus, NOT SUPPORTED YET
	}

	private boolean isLinkLFBendpoint(RelativeBendpoint gmfBendpoint) {
		return gmfBendpoint.getTargetX() == LINKLF_MAGIC && gmfBendpoint.getTargetY() == LINKLF_MAGIC;
	}

	private Collection<RelativeBendpoint> getBendpoints(Edge edge) {
		if (edge.getBendpoints() instanceof RelativeBendpoints) {
			@SuppressWarnings("unchecked") // GMF is Java 1.4
			List<RelativeBendpoint> points = new ArrayList<>(((RelativeBendpoints) edge.getBendpoints()).getPoints());
			// Remove the first and last points, which are actually the anchors
			if (points.size() >= 2) {
				points.remove(0);
				points.remove(points.size() - 1);
			}
			return points;
		}

		return Collections.emptyList();
	}

}
