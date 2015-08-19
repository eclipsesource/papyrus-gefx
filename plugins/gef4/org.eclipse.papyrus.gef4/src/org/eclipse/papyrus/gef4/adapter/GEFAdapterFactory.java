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
package org.eclipse.papyrus.gef4.adapter;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;

public class GEFAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (adaptableObject instanceof org.eclipse.gef4.common.adapt.IAdaptable) {
			return ((org.eclipse.gef4.common.adapt.IAdaptable) adaptableObject).getAdapter(adapterType);
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class[] { EObject.class, View.class };
	}

}
