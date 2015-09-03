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
package org.eclipse.papyrus.gef4.provider;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.gef4.common.adapt.AdapterKey;
import org.eclipse.gef4.geometry.planar.BezierCurve;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultHandlePartFactory;
import org.eclipse.gef4.mvc.fx.policies.AbstractFXOnDragPolicy;
import org.eclipse.gef4.mvc.fx.policies.FXBendOnSegmentHandleDragPolicy;
import org.eclipse.gef4.mvc.operations.ITransactional;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IHandlePart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gef4.mvc.policies.IPolicy;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.commands.wrappers.OperationToGEFCommandWrapper;
import org.eclipse.papyrus.gef4.parts.DiagramContentPart;

import com.google.inject.Provider;

import javafx.scene.Node;

public class HandlePartFactory extends FXDefaultHandlePartFactory {
	@Override
	protected List<IHandlePart<Node, ? extends Node>> createMultiSelectionHandleParts(List<? extends IVisualPart<Node, ? extends Node>> targets, Map<Object, Object> contextMap) {
		List<IHandlePart<Node, ? extends Node>> allHandles = new LinkedList<>();
		for (IVisualPart<Node, ? extends Node> target : targets) {
			allHandles.addAll(createSingleSelectionHandleParts(target, contextMap));
		}
		return allHandles;
	}

	@Override
	protected List<IHandlePart<Node, ? extends Node>> createSingleSelectionHandleParts(IVisualPart<Node, ? extends Node> target, Map<Object, Object> contextMap) {
		if (target instanceof DiagramContentPart) {
			return Collections.emptyList();
		}
		return super.createSingleSelectionHandleParts(target, contextMap);
	}

	@Override
	protected IHandlePart<Node, ? extends Node> createCurveSelectionHandlePart(final IVisualPart<Node, ? extends Node> targetPart,
			Provider<BezierCurve[]> segmentsProvider, int segmentCount, int segmentIndex, double segmentParameter) {

		IHandlePart<Node, ? extends Node> result = super.createCurveSelectionHandlePart(targetPart, segmentsProvider,
				segmentCount, segmentIndex, segmentParameter);

		// injector.injectMembers(part);

		result.setAdapter(AdapterKey.get(AbstractFXOnDragPolicy.class),
				new FXBendOnSegmentHandleDragPolicy() {

					// FIXME: discuss and revisit
					@Override
					protected void commit(IPolicy<Node> policy) {
						if (policy != null && policy instanceof ITransactional) {
							IUndoableOperation o = ((ITransactional) policy).commit();
							if (o != null && o.canExecute()) {
								IContentPart<Node, ? extends Node> targetPartImpl = (IContentPart<Node, ? extends Node>) targetPart;
								View hostView = (View) targetPartImpl.getContent();
								OperationToGEFCommandWrapper gefWrapper = new OperationToGEFCommandWrapper(o);
								GEFtoEMFCommandWrapper emfWrapper = new GEFtoEMFCommandWrapper(gefWrapper);
								AdapterFactoryEditingDomain.getEditingDomainFor(hostView).getCommandStack().execute(emfWrapper);
							}
						}
					}
				});

		return result;
	}
}
