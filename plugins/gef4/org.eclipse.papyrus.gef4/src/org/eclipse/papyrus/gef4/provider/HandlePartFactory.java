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

import org.eclipse.gef.mvc.fx.parts.DefaultSelectionHandlePartFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class HandlePartFactory extends DefaultSelectionHandlePartFactory {

	@Inject
	private Injector injector;

//	@Override
//	protected List<IHandlePart<? extends Node>> createMultiSelectionHandleParts(final List<? extends IVisualPart<? extends Node>> targets, IBehavior contextBehavior, final Map<Object, Object> contextMap) {
//		final List<IHandlePart<? extends Node>> allHandles = new LinkedList<>();
//		for (final IVisualPart<? extends Node> target : targets) {
//			allHandles.addAll(createSingleSelectionHandleParts(target, contextBehavior, contextMap));
//		}
//		return allHandles;
//	}
//
//	@Override
//	protected List<IHandlePart<? extends Node>> createSingleSelectionHandleParts(final IVisualPart<? extends Node> target, IBehavior contextBehavior, final Map<Object, Object> contextMap) {
//		if (target instanceof DiagramContentPart) {
//			return Collections.emptyList();
//		}
//		return super.createSingleSelectionHandleParts(target, contextBehavior, contextMap);
//	}
//
//	@Override
//	public List<IHandlePart<? extends Node>> createHandleParts(List<? extends IVisualPart<? extends Node>> targets, IBehavior contextBehavior, Map<Object, Object> contextMap) {
//		if (contextBehavior instanceof SelectionBehavior) {
//			return super.createHandleParts(targets, contextBehavior, contextMap);
//		} else if (contextBehavior instanceof HoverBehavior) {
//			return createHoverHandleParts(targets, (HoverBehavior) contextBehavior, contextMap);
//		}
//		return Collections.emptyList();
//	}
//
//	protected List<IHandlePart<? extends Node>> createHoverHandleParts(final List<? extends IVisualPart<? extends Node>> targets, final HoverBehavior contextBehavior, final Map<Object, Object> contextMap) {
//		List<IHandlePart<? extends Node>> result = new ArrayList<>();
//		for (IVisualPart<? extends Node> target : targets) {
//			result.addAll(createHoverHandleParts(target, contextBehavior, contextMap));
//		}
//		return result;
//	}
//
//	@Override
//	protected List<IHandlePart<? extends Node>> createSingleSelectionHandlePartsForCurve(IVisualPart<? extends Node> target, IBehavior contextBehavior, Map<Object, Object> contextMap, Provider<BezierCurve[]> segmentsProvider) {
//		List<IHandlePart<? extends Node>> result = super.createSingleSelectionHandlePartsForCurve(target, contextBehavior, contextMap, segmentsProvider);
//
//		for (IHandlePart<? extends Node> handlePart : result) {
//			handlePart.setAdapter(new FXBendFirstAnchorageOnSegmentHandleDragPolicy());
//		}
//
//		return result;
//	}
//
//	protected List<IHandlePart<? extends Node>> createHoverHandleParts(final IVisualPart<? extends Node> target, final HoverBehavior contextBehavior, final Map<Object, Object> contextMap) {
//		// Check if the target or one of its parents are a compartment
//		final IVisualPart<? extends Node> compartment = CompartmentUtils.getCollapsablePart(target);
//
//		// FIXME/TODO: We need a policy to trigger this hover. Children of a Compartment may have a different Hover policy that is not related to collapse
//		// contextBehavior and/or contextMap should be checked to determine which kind of Hover we need to handle
//		if (null != compartment) {
//			final List<IHandlePart<? extends Node>> handles = new ArrayList<IHandlePart<? extends Node>>();
//			// create handle part
//			final CollapseHandlePart collapseHandlePart = new CollapseHandlePart();
//			injector.injectMembers(collapseHandlePart);
//			handles.add(collapseHandlePart);
//			return handles;
//		}
//
//		return Collections.emptyList();
//	}
}
