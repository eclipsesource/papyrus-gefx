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
package org.eclipse.papyrus.infra.gefdiag.common.provider;

import java.util.Map;

import org.eclipse.gef4.mvc.behaviors.IBehavior;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IContentPartFactory;
import org.eclipse.gmf.runtime.notation.View;

import com.google.inject.Inject;
import com.google.inject.Injector;

import javafx.scene.Node;

public class ContentPartFactory implements IContentPartFactory<Node> {

	@Inject
	private Injector injector;

	private IVisualPartProvider<Node> provider = new VisualPartProvider();

	@Override
	public IContentPart<Node, ? extends Node> createContentPart(Object content, IBehavior<Node> contextBehavior, Map<Object, Object> contextMap) {
		if (content instanceof View) {
			View view = (View) content;
			IContentPart<Node, ? extends Node> result = provider.createContentPart(view);
			if (result != null) {
				injector.injectMembers(result);
			}
			/*
			 * int i = 0;
			 * for (View childView : (List<View>) view.getChildren()) {
			 * result.addContentChild(childView, i++);
			 * }
			 */
			return result;
		}
		return null;
	}

}