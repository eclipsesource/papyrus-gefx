/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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
package org.eclipse.papyrus.uml.gefdiag.statemachine.module;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.common.adapt.inject.AdapterMaps;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.layout.BorderItemLocator;
import org.eclipse.papyrus.gef4.provider.IContentPartProvider;
import org.eclipse.papyrus.uml.gefdiag.common.module.UMLDiagramModule;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConnectionPointReferenceEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateEntryPointEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateExitPointEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.providers.ContentPartProvider;

import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;

public class StateMachineDiagramModule extends UMLDiagramModule {

	@Override
	protected void configure() {
		super.configure();
		bindNodeLocators();
	}

	@Override
	protected void bindIContentPartProvider() {
		binder().bind(new TypeLiteral<IContentPartProvider<View>>() {
		}).to(ContentPartProvider.class);
	}

	protected void bindNodeLocators() {
		bindEntryPointLocator(AdapterMaps.getAdapterMapBinder(binder(), PseudostateEntryPointEditPart.class));
		bindExitPointLocator(AdapterMaps.getAdapterMapBinder(binder(), PseudostateExitPointEditPart.class));
		bindExitPointLocator(AdapterMaps.getAdapterMapBinder(binder(), ConnectionPointReferenceEditPart.class));
	}


	protected void bindEntryPointLocator(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(BorderItemLocator.class);
	}

	protected void bindExitPointLocator(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(BorderItemLocator.class);
	}

	protected void bindConnectionPointReferenceLocator(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(BorderItemLocator.class);
	}
}
