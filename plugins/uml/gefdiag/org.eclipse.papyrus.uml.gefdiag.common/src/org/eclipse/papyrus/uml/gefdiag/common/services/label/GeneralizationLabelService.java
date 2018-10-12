/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
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

import org.eclipse.uml2.uml.Generalization;

public class GeneralizationLabelService extends AbstractUMLLabelServiceParticipant<Generalization> {

	public GeneralizationLabelService(double priority) {
		super(priority, Generalization.class);
	}

	@Override
	protected String getLabel(Generalization element) {
		return OPEN_GUILLEMET + "generalization" + CLOSE_GUILLEMET;
	}

}
