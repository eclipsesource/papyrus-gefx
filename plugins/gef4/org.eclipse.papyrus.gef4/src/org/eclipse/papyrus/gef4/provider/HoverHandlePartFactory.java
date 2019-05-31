/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.gef4.provider;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.mvc.fx.parts.IHandlePart;
import org.eclipse.gef.mvc.fx.parts.IHandlePartFactory;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import javax.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
public class HoverHandlePartFactory implements IHandlePartFactory {

	public static final String ROLE = "HoverHandlePartProvider";

	@Inject
	private Injector injector;

	@Override
	public List<IHandlePart<?>> createHandleParts(List<? extends IVisualPart<?>> targets, Map<Object, Object> contextMap) {
		if (targets.isEmpty()) {
			return Collections.emptyList();
		}

		IVisualPart<?> target = targets.get(0);
		IHandlePartProvider handleProvider = target.getAdapter(AdapterKey.get(IHandlePartProvider.class, ROLE));

		if (handleProvider == null) {
			return Collections.emptyList();
		}

		List<IHandlePart<?>> result = handleProvider.createHandleParts(target, contextMap);
		result.stream().forEach(injector::injectMembers);
		return result;
	}

}
