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
package org.eclipse.papyrus.gef4.gmf.editor.provider;

import java.util.function.Function;

import org.eclipse.gef.mvc.fx.providers.DefaultAnchorProvider;
import org.eclipse.gmf.runtime.notation.Anchor;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;

public abstract class TerminalAnchorProvider extends DefaultAnchorProvider {

	protected <T> String getAnchorTerminal(T connectionElement, Function<T, Anchor> anchorSide) {
		Anchor anchor = anchorSide.apply(connectionElement);
		return anchor instanceof IdentityAnchor ? ((IdentityAnchor) anchor).getId() : null;
	}
}
