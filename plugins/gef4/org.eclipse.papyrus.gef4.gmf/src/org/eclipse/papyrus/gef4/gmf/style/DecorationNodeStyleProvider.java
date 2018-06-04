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

import org.eclipse.gmf.runtime.notation.DrawerStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.TitleStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.services.style.CompartmentStyleService;

public class DecorationNodeStyleProvider extends AbstractNotationStyleService implements CompartmentStyleService {

	public DecorationNodeStyleProvider(BaseContentPart<? extends View, ?> part) {
		super(part);
	}

	/**
	 * Whether the title of the compartment should be displayed
	 *
	 * Defaults to false
	 *
	 * @see {@link #getTitle()}
	 *
	 * @return
	 */
	@Override
	public boolean isShowTitle() {
		TitleStyle titleStyle = (TitleStyle) getView().getStyle(NotationPackage.eINSTANCE.getTitleStyle());
		if (titleStyle == null) {
			return false;
		}

		return titleStyle.isShowTitle();
	}

	@Override
	public boolean isCollapsed() {
		final DrawerStyle style = (DrawerStyle) getView().getStyle(NotationPackage.eINSTANCE.getDrawerStyle());
		return style == null ? false : style.isCollapsed();
	}

}
