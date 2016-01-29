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
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - add collapse handle part implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.gef4.geometry.planar.BezierCurve;
import org.eclipse.gef4.mvc.behaviors.HoverBehavior;
import org.eclipse.gef4.mvc.behaviors.IBehavior;
import org.eclipse.gef4.mvc.behaviors.SelectionBehavior;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultSelectionHandlePartFactory;
import org.eclipse.gef4.mvc.fx.policies.FXBendFirstAnchorageOnSegmentHandleDragPolicy;
import org.eclipse.gef4.mvc.parts.IHandlePart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.papyrus.gef4.handle.CollapseHandlePart;
import org.eclipse.papyrus.gef4.parts.DiagramContentPart;
import org.eclipse.papyrus.gef4.utils.CompartmentUtils;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

import javafx.scene.Node;

public class HandlePartFactory extends FXDefaultSelectionHandlePartFactory {

	@Inject
	private Injector injector;

	@Override
	protected List<IHandlePart<Node, ? extends Node>> createMultiSelectionHandleParts(final List<? extends IVisualPart<Node, ? extends Node>> targets, IBehavior<Node> contextBehavior, final Map<Object, Object> contextMap) {
		final List<IHandlePart<Node, ? extends Node>> allHandles = new LinkedList<>();
		for (final IVisualPart<Node, ? extends Node> target : targets) {
			allHandles.addAll(createSingleSelectionHandleParts(target, contextBehavior, contextMap));
		}
		return allHandles;
	}

	@Override
	protected List<IHandlePart<Node, ? extends Node>> createSingleSelectionHandleParts(final IVisualPart<Node, ? extends Node> target, IBehavior<Node> contextBehavior, final Map<Object, Object> contextMap) {
		if (target instanceof DiagramContentPart) {
			return Collections.emptyList();
		}
		return super.createSingleSelectionHandleParts(target, contextBehavior, contextMap);
	}

	@Override
	public List<IHandlePart<Node, ? extends Node>> createHandleParts(List<? extends IVisualPart<Node, ? extends Node>> targets, IBehavior<Node> contextBehavior, Map<Object, Object> contextMap) {
		if (contextBehavior instanceof SelectionBehavior) {
			return super.createHandleParts(targets, contextBehavior, contextMap);
		} else if (contextBehavior instanceof HoverBehavior) {
			return createHoverHandleParts(targets, (HoverBehavior<Node>) contextBehavior, contextMap);
		}
		return Collections.emptyList();
	}

	protected List<IHandlePart<Node, ? extends Node>> createHoverHandleParts(final List<? extends IVisualPart<Node, ? extends Node>> targets, final HoverBehavior<Node> contextBehavior, final Map<Object, Object> contextMap) {
		List<IHandlePart<Node, ? extends Node>> result = new ArrayList<>();
		for (IVisualPart<Node, ? extends Node> target : targets) {
			result.addAll(createHoverHandleParts(target, contextBehavior, contextMap));
		}
		return result;
	}

	@Override
	protected List<IHandlePart<Node, ? extends Node>> createSingleSelectionHandlePartsForCurve(IVisualPart<Node, ? extends Node> target, IBehavior<Node> contextBehavior, Map<Object, Object> contextMap, Provider<BezierCurve[]> segmentsProvider) {
		List<IHandlePart<Node, ? extends Node>> result = super.createSingleSelectionHandlePartsForCurve(target, contextBehavior, contextMap, segmentsProvider);

		for (IHandlePart<Node, ? extends Node> handlePart : result) {
			handlePart.setAdapter(new FXBendFirstAnchorageOnSegmentHandleDragPolicy());
		}

		return result;
	}

	protected List<IHandlePart<Node, ? extends Node>> createHoverHandleParts(final IVisualPart<Node, ? extends Node> target, final HoverBehavior<Node> contextBehavior, final Map<Object, Object> contextMap) {
		// Check if the target or one of its parents are a compartment
		final IVisualPart<Node, ? extends Node> compartment = CompartmentUtils.getCollapsablePart(target);

		// FIXME/TODO: We need a policy to trigger this hover. Children of a Compartment may have a different Hover policy that is not related to collapse
		// contextBehavior and/or contextMap should be checked to determine which kind of Hover we need to handle
		if (null != compartment) {
			final List<IHandlePart<Node, ? extends Node>> handles = new ArrayList<IHandlePart<Node, ? extends Node>>();
			// create handle part
			final CollapseHandlePart collapseHandlePart = new CollapseHandlePart();
			injector.injectMembers(collapseHandlePart);
			handles.add(collapseHandlePart);
			return handles;
		}

		return Collections.emptyList();
	}
}
