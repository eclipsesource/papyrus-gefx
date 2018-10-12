/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (EclipseSource) cletavernier@eclipsesource.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.feedback;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.geometry.planar.ICurve;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.geometry.planar.IShape;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.parts.DefaultSelectionHandlePartFactory;
import org.eclipse.gef.mvc.fx.parts.IFeedbackPart;
import org.eclipse.gef.mvc.fx.parts.IFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.google.common.reflect.TypeToken;
import com.google.inject.Injector;
import com.google.inject.Provider;

@SuppressWarnings("serial")
public class SelectionFeedbackFactory implements IFeedbackPartFactory {

	@Inject
	private Injector injector;

	@Override
	public List<IFeedbackPart<?>> createFeedbackParts(List<? extends IVisualPart<?>> targets, Map<Object, Object> contextMap) {
		return targets.stream().flatMap(target -> createSelectionFeedbackPart(target, contextMap).stream()).collect(Collectors.toList());
	}

	private Collection<IFeedbackPart<?>> createSelectionFeedbackPart(IVisualPart<?> target, Map<Object, Object> contextMap) {
		final Provider<? extends IGeometry> selectionHandlesGeometryInTargetLocalProvider = target
				.getAdapter(AdapterKey
						.get(new TypeToken<Provider<? extends IGeometry>>() {
						}, DefaultSelectionHandlePartFactory.SELECTION_HANDLES_GEOMETRY_PROVIDER));
		IGeometry selectionHandlesGeometry = (selectionHandlesGeometryInTargetLocalProvider != null)
				? selectionHandlesGeometryInTargetLocalProvider.get()
				: null;
		if (selectionHandlesGeometry == null) {
			return Collections.emptyList();
		}

		if (selectionHandlesGeometry instanceof ICurve) {
			// TODO Curves not supported yet
			return Collections.emptyList();
		} else if (selectionHandlesGeometry instanceof IShape) {
			if (selectionHandlesGeometry instanceof Rectangle) {
				return createFeedbackForRectangularOutline(target, contextMap);
			} else {
				// TODO Custom shapes not supported yet
				return Collections.emptyList();
			}
		} else {
			throw new IllegalStateException(
					"Unable to generate handles for this handle geometry. Expected ICurve or IShape, but got: "
							+ selectionHandlesGeometry);
		}
	}

	private Collection<IFeedbackPart<?>> createFeedbackForRectangularOutline(IVisualPart<?> target, Map<Object, Object> contextMap) {
		NodeSelectionFeedbackPart selectionFeedbackPart = injector.getInstance(NodeSelectionFeedbackPart.class);
		return Collections.singletonList(selectionFeedbackPart);
	}

}
