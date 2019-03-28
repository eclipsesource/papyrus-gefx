/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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
package org.eclipse.papyrus.gef4.gmf.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.utils.NotationUtil;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.services.ContentChildrenProvider;
import org.eclipse.papyrus.gef4.utils.ActivatableBound;

public class NotationContentChildrenProvider extends ActivatableBound<BaseContentPart<? extends View, ?>>
		implements ContentChildrenProvider<View> {

	private DiagramEventBroker eventBroker;

	private NotificationListener listener;

	private View view;

	@Inject
	protected void setEventBroker(DiagramEventBroker eventBroker) {
		assert eventBroker != null;
		this.eventBroker = eventBroker;
	}

	protected View getView() {
		if (view == null) {
			view = getAdaptable().getContent();
		}
		return view;
	}

	@Override
	public List<? extends View> getContentChildren() {
		Stream<Node> nodes = NotationUtil.getChildren(getView()).stream().filter(View::isVisible);
		Stream<? extends View> nodesAndEdges;

		if (getView() instanceof Diagram) {
			// XXX It seems that Edges in Papyrus are created in two-steps. Initially,
			// the Edge is present but no connected to source/target.
			// We have two ways around this issue: 1) Let this provider install a listener
			// on the Edges directly, and refresh
			// its children when edges become connected; 2) Return visible unconnected
			// Nodes, create an EditPart for it, and let this edit part
			// deal with source and targets. Currently, we choose Option 2, which is easier
			// and probably more flexible (But may result in an EditPart with a hidden
			// Figure; it's unclear whether this would be an issue)
			nodesAndEdges = Stream.concat(nodes,
					NotationUtil.getEdges(getView()).stream()
							.filter((e) -> (e.getSource() == null || e.getSource().isVisible())
									&& (e.getTarget() == null || e.getTarget().isVisible())));
		} else {
			nodesAndEdges = nodes;
		}

		return nodesAndEdges.collect(Collectors.toList());
	}

	protected boolean childrenChanged(Notification msg) {
		if (msg.getNotifier() != getView()) {
			return false;
		}

		if (getView() instanceof Diagram) {
			if (msg.getFeature() == NotationPackage.Literals.DIAGRAM__PERSISTED_EDGES
					|| msg.getFeature() == NotationPackage.Literals.DIAGRAM__TRANSIENT_EDGES) {
				return true;
			}
		}

		if (msg.getFeature() == NotationPackage.Literals.VIEW__PERSISTED_CHILDREN
				|| msg.getFeature() == NotationPackage.Literals.VIEW__TRANSIENT_CHILDREN) {
			return true;
		}

		return false;
	}

	@Override
	protected void doActivate() {
		assert getView() != null;
		listener = msg -> {
			if (childrenChanged(msg)) {
				getAdaptable().updateContentChildren();
			}
		};
		eventBroker.addNotificationListener(getView(), listener);
	}

	@Override
	protected void doDeactivate() {
		eventBroker.removeNotificationListener(getView(), listener);
	}

}
