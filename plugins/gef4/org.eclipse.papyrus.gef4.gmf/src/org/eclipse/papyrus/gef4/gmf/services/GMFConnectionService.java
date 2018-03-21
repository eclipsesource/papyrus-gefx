package org.eclipse.papyrus.gef4.gmf.services;

import java.util.Map;

import javax.inject.Inject;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.common.adapt.AdaptableSupport;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.parts.ConnectionContentPart;
import org.eclipse.papyrus.gef4.services.AnchorageService;
import org.eclipse.papyrus.gef4.utils.ActivatableBoundAdaptable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import javafx.scene.Node;

public class GMFConnectionService extends ActivatableBoundAdaptable<AnchorageService, ConnectionContentPart<View>>
		implements AnchorageService {

	private DiagramEventBroker eventBroker;
	private NotificationListener notificationListener;
	private Edge edge;

	@Override
	protected void doActivate() {
		this.edge = getEdge();
		notificationListener = msg -> {

			if (!isActive()) {
				return;
			}

			if (!(msg.isTouch())) {
				if (isSourceOrTargetChanged(msg)) {
					getAdaptable().updateAnchorages();
				}

				if (isAnchorChanged(msg)) {
					getAdaptable().updateAnchors();
				}
			}
		};

		eventBroker.addNotificationListener(getEdge(), notificationListener);
	}

	@Inject
	protected void setEventBroker(DiagramEventBroker eventBroker) {
		this.eventBroker = eventBroker;
	}

	protected Edge getEdge() {
		return (Edge) getAdaptable().getContent();
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
		if (getEdge().getSource() != null && getEdge().getTarget() != null) { // Avoid creating partial anchorages
			anchorages.put(getEdge().getSource(), ConnectionContentPart.SOURCE);
			anchorages.put(getEdge().getTarget(), ConnectionContentPart.TARGET);
		}
		return anchorages;
	}

	@Override
	protected AdaptableSupport<AnchorageService> createAdaptableSupport() {
		return new AdaptableSupport<>(this);
	}

	@Override
	protected void doDeactivate() {
		eventBroker.removeNotificationListener(edge, notificationListener);
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

}
