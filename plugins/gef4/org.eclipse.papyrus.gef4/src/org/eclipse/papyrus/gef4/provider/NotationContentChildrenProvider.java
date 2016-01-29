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
package org.eclipse.papyrus.gef4.provider;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

public class NotationContentChildrenProvider implements IContentChildrenProvider<View> {

	private static final IContentChildrenProvider<View> instance = new NotationContentChildrenProvider();

	protected NotationContentChildrenProvider() {
		// Nothing
	}

	@Override
	public List<? extends View> getContentChildren(final View parent) {

		Stream<View> nodesAndEdges = getChildren(parent).stream().filter(c -> c.isVisible());

		if (parent instanceof Diagram) {
			nodesAndEdges = Stream.concat(nodesAndEdges,
					getEdges(parent).stream().filter(
							(e) -> e.getSource() != null && e.getSource().isVisible() && e.getTarget() != null && e.getTarget().isVisible()));
		}

		return nodesAndEdges.collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked") // GMF Notation uses Java 1.4
	protected final List<View> getChildren(View view) {
		return view.getChildren();
	}

	@SuppressWarnings("unchecked") // GMF Notation uses Java 1.4
	protected final List<Edge> getEdges(View view) {
		if (view instanceof Diagram) {
			return ((Diagram) view).getEdges();
		} else {
			return Collections.emptyList();
		}
	}

	public static IContentChildrenProvider<View> getInstance() {
		return instance;
	}

}
