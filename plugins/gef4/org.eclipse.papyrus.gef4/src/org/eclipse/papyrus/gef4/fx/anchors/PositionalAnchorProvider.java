/*****************************************************************************
 * Copyright (c) 2015 CEA LIST, Montages AG and others
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Michael Golubev (Montages) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.fx.anchors;

import org.eclipse.gef4.fx.anchors.IFXAnchor;
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.mvc.fx.policies.FXBendPolicy;
import org.eclipse.gef4.mvc.parts.IVisualPart;

import com.google.inject.Provider;

/**
 * Extension for standard {@link Provider<IFXAnchor>} uses the contextual information
 * about mouse position and the subject connection at the time of requesting the creation
 * of {@link IFXAnchor}.
 * <p/>
 * Every {@link PositionalAnchorProvider} can be used as a non-contextual {@link Provider<IFXAnchor>},
 * so the same provider implementation should not fail e,g with standard {@link FXBendPolicy} which
 * does not pass any context.
 */
public interface PositionalAnchorProvider extends Provider<IFXAnchor> {

	public IFXAnchor getForContext(Point scenePoint, IVisualPart<?, ?> connection);


}