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
package org.eclipse.papyrus.infra.gefdiag.common.part.uml;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.infra.gefdiag.common.part.ListItemContentPart;
import org.eclipse.papyrus.infra.gefdiag.common.util.EffectsUtil;
import org.eclipse.papyrus.uml.tools.utils.ICustomAppearance;
import org.eclipse.papyrus.uml.tools.utils.PropertyUtil;
import org.eclipse.uml2.uml.Property;

import javafx.scene.control.Label;

public class PropertyLabelContentPart extends ListItemContentPart {

	public PropertyLabelContentPart(Shape view) {
		super(view);
	}

	@Override
	protected String getText() {
		Collection<String> styles = new HashSet<String>();

		Property element = getElement();
		if (element == null) {
			return "";
		}

		// styles.add(ICustomAppearance.DISP_VISIBILITY);
		styles.add(ICustomAppearance.DISP_DERIVE);
		styles.add(ICustomAppearance.DISP_NAME);

		if (element.getType() != null) {
			styles.add(ICustomAppearance.DISP_TYPE);
		}

		styles.add(ICustomAppearance.DISP_MULTIPLICITY);
		styles.add(ICustomAppearance.DISP_DEFAULT_VALUE);
		// styles.add(ICustomAppearance.DISP_MODIFIERS);

		return PropertyUtil.getCustomLabel(element, styles);
	}

	@Override
	protected void doRefreshVisual(Label visual) {
		super.doRefreshVisual(visual);
		visual.setEffect(EffectsUtil.SHADOW_EFFECT);
	}

	@Override
	public Property getElement() {
		EObject element = super.getElement();
		if (element instanceof Property) {
			return (Property) element;
		}
		return null;
	}

	@Override
	protected String getStyleClass() {
		return "property";
	}

}
