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
import org.eclipse.gef4.mvc.fx.parts.FXDefaultHandlePartFactory;
import org.eclipse.gef4.mvc.parts.IHandlePart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.papyrus.gef4.handle.CollapseHandlePart;
import org.eclipse.papyrus.gef4.parts.DiagramContentPart;
import org.eclipse.papyrus.gef4.utils.CompartmentUtils;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

import javafx.scene.Node;

public class HandlePartFactory extends FXDefaultHandlePartFactory {

	@Inject
	private Injector injector;

	@Override
	protected List<IHandlePart<Node, ? extends Node>> createMultiSelectionHandleParts(final List<? extends IVisualPart<Node, ? extends Node>> targets, final Map<Object, Object> contextMap) {
		final List<IHandlePart<Node, ? extends Node>> allHandles = new LinkedList<>();
		for (final IVisualPart<Node, ? extends Node> target : targets) {
			allHandles.addAll(createSingleSelectionHandleParts(target, contextMap));
		}
		return allHandles;
	}

	@Override
	protected List<IHandlePart<Node, ? extends Node>> createSingleSelectionHandleParts(final IVisualPart<Node, ? extends Node> target, final Map<Object, Object> contextMap) {
		if (target instanceof DiagramContentPart) {
			return Collections.emptyList();
		}
		return super.createSingleSelectionHandleParts(target, contextMap);
	}

	@Override
	protected IHandlePart<Node, ? extends Node> createCurveSelectionHandlePart(final IVisualPart<Node, ? extends Node> targetPart,
			final Provider<BezierCurve[]> segmentsProvider, final int segmentCount, final int segmentIndex, final double segmentParameter) {

		final IHandlePart<Node, ? extends Node> result = super.createCurveSelectionHandlePart(targetPart, segmentsProvider,
				segmentCount, segmentIndex, segmentParameter);

		// injector.injectMembers(part);

		// result.setAdapter(AdapterKey.get(AbstractFXOnDragPolicy.class),
		// new FXBendOnSegmentHandleDragPolicy() {
		//
		// // FIXME: discuss and revisit
		// @Override
		// protected void commit(final IPolicy<Node> policy) {
		// if (policy != null && policy instanceof ITransactional) {
		// final IUndoableOperation o = ((ITransactional) policy).commit();
		// if (o != null && o.canExecute()) {
		// final IContentPart<Node, ? extends Node> targetPartImpl = (IContentPart<Node, ? extends Node>) targetPart;
		// final View hostView = (View) targetPartImpl.getContent();
		// final OperationToGEFCommandWrapper gefWrapper = new OperationToGEFCommandWrapper(o);
		// final GEFtoEMFCommandWrapper emfWrapper = new GEFtoEMFCommandWrapper(gefWrapper);
		// AdapterFactoryEditingDomain.getEditingDomainFor(hostView).getCommandStack().execute(emfWrapper);
		// }
		// }
		// }
		// });

		return result;
	}

	@Override
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

		return super.createHoverHandleParts(target, contextBehavior, contextMap);
	}
}
