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
package org.eclipse.papyrus.uml.gefdiag.clazz.parts;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.gefdiag.common.parts.NamedElementLabelContentPart;

@Deprecated
public class PackageLabelContentPart extends NamedElementLabelContentPart {

	public PackageLabelContentPart(View view) {
		super(view);
	}

	@Override
	protected String getStyleClass() {
		return "packageLabel";
	}

}
