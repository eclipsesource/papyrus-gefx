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

import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.papyrus.infra.gefdiag.common.part.ConnectionContentPart;

public class AssociationContentPart extends ConnectionContentPart<Connector> {

	public AssociationContentPart(Connector view) {
		super(view);
	}

	@Override
	protected String getStyleClass() {
		return "association";
	}

}
