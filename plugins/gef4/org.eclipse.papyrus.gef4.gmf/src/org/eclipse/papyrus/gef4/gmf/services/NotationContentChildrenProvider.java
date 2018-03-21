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
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.utils.NotationUtil;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.services.ContentChildrenAdapter;
import org.eclipse.papyrus.gef4.utils.ActivatableBound;

public class NotationContentChildrenProvider extends ActivatableBound<BaseContentPart<? extends View, ?>> implements ContentChildrenAdapter<View> {

	private final View view;

	private DiagramEventBroker eventBroker;

	@Inject
	protected NotationContentChildrenProvider(View view) {
		assert view != null;
		this.view = view;
	}

	@Inject
	protected void setEventBroker(DiagramEventBroker eventBroker) {
		assert eventBroker != null;
		this.eventBroker = eventBroker;
	}

	@Override
	public List<? extends View> getContentChildren() {
		Stream<Node> nodes = NotationUtil.getChildren(view).stream().filter(c -> c.isVisible());
		Stream<? extends View> nodesAndEdges;

		if (view instanceof Diagram) {
			nodesAndEdges = Stream.concat(nodes,
					NotationUtil.getEdges(view).stream().filter(
							(e) -> e.getSource() != null && e.getSource().isVisible() && e.getTarget() != null && e.getTarget().isVisible()));
		} else {
			nodesAndEdges = nodes;
		}

		return nodesAndEdges.collect(Collectors.toList());
	}

	protected boolean childrenChanged(Notification msg) {
		if (msg.getNotifier() != view) {
			return false;
		}

		if (view instanceof Diagram) {
			if (msg.getFeature() == NotationPackage.Literals.DIAGRAM__PERSISTED_EDGES || msg.getFeature() == NotationPackage.Literals.DIAGRAM__TRANSIENT_EDGES) {
				return true;
			}
		}

		if (msg.getFeature() == NotationPackage.Literals.VIEW__PERSISTED_CHILDREN || msg.getFeature() == NotationPackage.Literals.VIEW__TRANSIENT_CHILDREN) {
			return true;
		}

		return false;
	}

	@Override
	protected void doActivate() {
		eventBroker.addNotificationListener(view, msg -> {
			if (childrenChanged(msg)) {
				getAdaptable().updateContentChildren();
			}
		});
	}

	@Override
	protected void doDeactivate() {

	}

}
