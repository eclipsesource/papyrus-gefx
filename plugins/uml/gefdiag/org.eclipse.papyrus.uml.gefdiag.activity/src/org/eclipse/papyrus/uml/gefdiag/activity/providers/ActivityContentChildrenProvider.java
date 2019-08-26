/*****************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  EclipseSource - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.gefdiag.activity.providers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.gefdiag.common.provider.StereotypeAwareContentChildrenProvider;

import com.google.common.collect.ImmutableSet;

public class ActivityContentChildrenProvider extends StereotypeAwareContentChildrenProvider {

	private static final Set<String> excludedTypes = ImmutableSet.of("Activity_KeywordLabel");
	
	@Override
	public List<? extends View> getContentChildren() {
		List<? extends View> contentChildren = super.getContentChildren();
		return contentChildren.stream().filter(view -> ! excludedTypes.contains(view.getType())).collect(Collectors.toList());
	}
	
}
