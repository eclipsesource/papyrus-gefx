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
package org.eclipse.papyrus.gef4.gmf.module.anchors;

import java.util.function.Function;

import org.eclipse.gef.fx.anchors.IAnchor;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.providers.DefaultAnchorProvider;
import org.eclipse.gef.mvc.fx.providers.IAnchorProvider;
import org.eclipse.gmf.runtime.notation.Anchor;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.utils.AdapterUtil;

import javafx.scene.Node;

/**
 * <p>
 * An {@link IAnchorProvider} using GMF Ratio Anchors
 * </p>
 */
public class RatioAnchorProvider extends DefaultAnchorProvider {

	public static final String SOURCE_ROLE = "source";
	public static final String TARGET_ROLE = "target";

	@Override
	public IAnchor get(IVisualPart<? extends Node> anchoredPart, String role) {
		if (anchoredPart instanceof IContentPart) {
			// FIXME: This method is called during activation, but at this point the View
			// Adapter may not be activated yet (Depending on the order in which adapters
			// are activated). As a result, the connectionElement is initially null
			View connectionElement = AdapterUtil.getView((IContentPart<?>) anchoredPart);
			if (connectionElement instanceof Edge) {
				String anchorTerminal = getAnchorTerminal((Edge) connectionElement,
						SOURCE_ROLE.equals(role) ? Edge::getSourceAnchor : Edge::getTargetAnchor);
				IAnchor anchorFromTerminal = getAnchorFromTerminal(anchoredPart, anchorTerminal);
				if (anchorFromTerminal != null) {
					return anchorFromTerminal;
				}
			}
		}
		return super.get(anchoredPart, role);
	}

	private IAnchor getAnchorFromTerminal(IVisualPart<? extends Node> anchoredPart, String anchorTerminal) {
		if (anchorTerminal == null || anchorTerminal.isEmpty()) {
			return new RatioAnchor(getAdaptable().getVisual(), 0, 0);
		}

		if (anchorTerminal.startsWith("(") && anchorTerminal.endsWith(")")) {
			anchorTerminal = anchorTerminal.substring(1, anchorTerminal.length() - 1);
			int separatorIndex = anchorTerminal.indexOf(",");
			if (separatorIndex > -1) {
				String xStr = anchorTerminal.substring(0, separatorIndex);
				String yStr = anchorTerminal.substring(separatorIndex + 1);

				try {
					double ratioX = Double.parseDouble(xStr);
					double ratioY = Double.parseDouble(yStr);
					return new RatioAnchor(getAdaptable().getVisual(), ratioX, ratioY);
				} catch (NumberFormatException ex) {
					return null;
				}
			}
		}
		return null;
	}

	private <T> String getAnchorTerminal(T connectionElement, Function<T, Anchor> anchorSide) {
		Anchor anchor = anchorSide.apply(connectionElement);
		return anchor instanceof IdentityAnchor ? ((IdentityAnchor) anchor).getId() : null;
	}

}
