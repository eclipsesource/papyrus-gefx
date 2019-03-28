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
package org.eclipse.papyrus.gef4.gmf.style;

import org.eclipse.papyrus.gef4.gmf.utils.NotationUtil;
import org.eclipse.papyrus.gef4.services.style.LabelStyleService;
import org.eclipse.papyrus.gef4.utils.TextOverflowEnum;

public class NotationLabelStyleProvider extends AbstractNotationStyleService implements LabelStyleService {

	@Override
	public TextOverflowEnum getTextOverflow() {
		return NotationUtil.getTextOverflow(getView());
	}

}
