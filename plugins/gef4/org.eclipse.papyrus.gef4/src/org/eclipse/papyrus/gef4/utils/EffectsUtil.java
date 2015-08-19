/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.gef4.utils;

import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;

public class EffectsUtil {
	public static final Effect SHADOW_EFFECT = createShadowEffect();

	public static final Effect BOUNDS_FEEDBACK_EFFECT = createBoundsFeedbackEffect();

	public static Effect createBoundsFeedbackEffect() {
		DropShadow effect = new DropShadow();
		effect.setColor(Color.rgb(125, 173, 217));
		effect.setRadius(5);
		effect.setSpread(0.6);
		return effect;
	}

	public static Effect createShadowEffect() {
		DropShadow dropShadow = new DropShadow();
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.CORNFLOWERBLUE);

		return dropShadow;
	}

}
