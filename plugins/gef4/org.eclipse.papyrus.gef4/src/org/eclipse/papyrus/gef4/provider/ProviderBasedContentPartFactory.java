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

import java.util.Map;

import org.eclipse.gef4.mvc.behaviors.IBehavior;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IContentPartFactory;
import org.eclipse.gmf.runtime.notation.View;

import com.google.inject.Inject;
import com.google.inject.Injector;

import javafx.scene.Node;

public class ProviderBasedContentPartFactory implements IContentPartFactory<Node> {

	@Inject
	private IVisualPartProvider<Node> provider;

	@Inject
	private Injector injector;

	@Override
	public IContentPart<Node, ? extends Node> createContentPart(Object content, IBehavior<Node> contextBehavior, Map<Object, Object> contextMap) {
		if (content instanceof View) {
			IContentPart<Node, ? extends Node> contentPart = provider.createContentPart((View) content);
			if (contentPart != null) {
				injector.injectMembers(contentPart);
			}
			return contentPart;
		}
		return null;
	}

}
