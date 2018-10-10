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

import java.util.Optional;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.services.AbstractGMFProviderParticipant;
import org.eclipse.papyrus.gef4.layout.BorderItemLocator;
import org.eclipse.papyrus.gef4.layout.Locator;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.provider.IContentPartProvider;
import org.eclipse.papyrus.gef4.services.HelperProviderParticipant;
import org.eclipse.papyrus.uml.gefdiag.common.module.UMLDiagramModule;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.ConnectionPointReferenceEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateEntryPointEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.edit.parts.PseudostateExitPointEditPart;
import org.eclipse.papyrus.uml.gefdiag.statemachine.providers.ContentPartProvider;

import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

public class StateMachineDiagramModule extends UMLDiagramModule {

	public static final double DEFAULT_STATE_PRIORITY = UMLDiagramModule.MAX_UML_PRIORITY + 1;
	public static final double MAX_STATE_PRIORITY = DEFAULT_STATE_PRIORITY + 9;

	@Override
	protected void bindIContentPartProvider() {
		binder().bind(new TypeLiteral<IContentPartProvider<View>>() {
		}).to(ContentPartProvider.class);
	}

	@Override
	protected void bindLocators(Multibinder<HelperProviderParticipant<Optional<Locator>>> locators) {
		super.bindLocators(locators);
		locators.addBinding().toInstance(new AbstractGMFProviderParticipant<Optional<Locator>>(DEFAULT_STATE_PRIORITY,
				PseudostateEntryPointEditPart.class, PseudostateExitPointEditPart.class, ConnectionPointReferenceEditPart.class) {

			@Override
			protected Optional<Locator> doCreateInstance(BaseContentPart<? extends View, ?> basePart) {
				return Optional.of(new BorderItemLocator(basePart));
			}
		});
	}
}
