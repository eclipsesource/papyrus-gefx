/*****************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  EclipseSource
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.gefdiag.clazz;

public class ClassConstants {
	public static final String ASSOCIATION_SOURCE_ROLE = "Association_SourceRoleLabel";
	public static final String ASSOCIATION_TARGET_ROLE = "Association_TargetRoleLabel";

	public static final String ASSOCIATION_SOURCE_MULTIPLICITY = "Association_TargetMultiplicityLabel"; // XXX Reversed to workaround a Papyrus inconsistency
	public static final String ASSOCIATION_TARGET_MULTIPLICITY = "Association_SourceMultiplicityLabel"; // XXX Reversed to workaround a Papyrus inconsistency

	public static final String ASSOCIATION_NAME = "Association_NameLabel";
	public static final String ASSOCIATION_STEREOTYPE = "Association_StereotypeLabel";
}
