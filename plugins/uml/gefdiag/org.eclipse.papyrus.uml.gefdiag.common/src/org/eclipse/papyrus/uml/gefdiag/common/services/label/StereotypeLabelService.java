/*****************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.gefdiag.common.services.label;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.services.AbstractGMFProviderParticipant;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.services.LabelService;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;

public class StereotypeLabelService extends AbstractGMFProviderParticipant<LabelService> {
	
	/**
	 * Suffix for {@link View#getType() View Type} representing {@link Stereotype} labels
	 */
	public static final String STEREOTYPE_LABEL_SUFFIX = "StereotypeLabel";

	public StereotypeLabelService(double priority) {
		super(priority, part -> part.getContent().getType().endsWith(STEREOTYPE_LABEL_SUFFIX) && part.getContent().getElement() instanceof Element);
	}

	@Override
	protected LabelService doCreateInstance(BaseContentPart<? extends View, ?> basePart) {
		return () -> getLabel(basePart);
	}
	
	protected String getLabel(BaseContentPart<? extends View, ?> basePart) {
		return null; //TODO Find & display stereotype
	}

}
