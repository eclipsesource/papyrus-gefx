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
package org.eclipse.papyrus.gef4.module;

import org.eclipse.gef.common.adapt.AdapterKey;

/**
 * <p>
 * Constants class for the Dependency Injection roles for Adapter Map bindings.
 * </p>
 *
 * <p>
 * When multiple roles are required, the default Module ({@link GEFFxModule}) will use the
 * {@link #FALLBACK_ROLE}. Editor-specific modules should use the {@link AdapterKey#defaultRole()},
 * which is the first choice when multiple adapters are bound to the same key.
 * </p>
 *
 * @see {@link AdapterKey#role(String)}
 * @see {@link AdapterKey}
 */
public final class AdapterRoles {

	/**
	 * <p>
	 * Role used to bind default adapters. This role should only be bound
	 * by the default {@link GEFFxModule}. Adapters bound with this role
	 * will have the lowest priority (i.e. they will only be used if no
	 * matching adapter is bound by specific editor modules).
	 * </p>
	 */
	public static final String FALLBACK_ROLE = "fallback";

	/**
	 * @return a new {@link AdapterKey} using the {@link #FALLBACK_ROLE}
	 */
	public static AdapterKey<?> fallbackRole() {
		return AdapterKey.role(FALLBACK_ROLE);
	}

	private AdapterRoles() {
		// No instances
	}
}
