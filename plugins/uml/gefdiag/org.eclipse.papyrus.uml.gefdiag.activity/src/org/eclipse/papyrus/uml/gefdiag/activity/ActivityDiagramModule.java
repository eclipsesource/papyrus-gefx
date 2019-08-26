/*****************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  EclipseSource - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.gefdiag.activity;

import java.util.Optional;

import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.services.GMFProviderParticipant;
import org.eclipse.papyrus.gef4.layout.Locator;
import org.eclipse.papyrus.gef4.palette.DefaultPaletteRenderer;
import org.eclipse.papyrus.gef4.palette.PaletteDescriptor;
import org.eclipse.papyrus.gef4.palette.PaletteRenderer;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.provider.IContentPartProvider;
import org.eclipse.papyrus.gef4.services.ContentChildrenProvider;
import org.eclipse.papyrus.gef4.services.HelperProviderParticipant;
import org.eclipse.papyrus.infra.gefdiag.common.palette.PapyrusPaletteDescriptor;
import org.eclipse.papyrus.uml.gefdiag.activity.providers.ActivityContentChildrenProvider;
import org.eclipse.papyrus.uml.gefdiag.activity.providers.ContentPartProvider;
import org.eclipse.papyrus.uml.gefdiag.common.locator.PinLocator;
import org.eclipse.papyrus.uml.gefdiag.common.module.UMLDiagramModule;
import org.eclipse.uml2.uml.Pin;

import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

public class ActivityDiagramModule extends UMLDiagramModule {

	public static final double DEFAULT_ACTIVITY_PRIORITY = UMLDiagramModule.MAX_UML_PRIORITY + 1;
	public static final double MAX_ACTIVITY_PRIORITY = DEFAULT_ACTIVITY_PRIORITY + 9;

	@Override
	protected void configure() {
		super.configure();
		bindPalette();
	}

	@Override
	protected void bindLocators(Multibinder<HelperProviderParticipant<Optional<Locator>>> locators) {
		super.bindLocators(locators);

		Provider<Optional<Locator>> borderItemLocator = required(getProvider(PinLocator.class));
		locators.addBinding().toInstance(new GMFProviderParticipant<>(DEFAULT_ACTIVITY_PRIORITY, borderItemLocator,
				ActivityDiagramModule::isPin));
	}

	protected static boolean isPin(BaseContentPart<? extends View, ?> part) {
		return part.getContent().getElement() instanceof Pin;
	}
	
	protected void bindUMLContentChildrenProvider() {
		Multibinder<HelperProviderParticipant<ContentChildrenProvider<View>>> contentChildrenBinder = Multibinder
				.newSetBinder(binder(), new TypeLiteral<HelperProviderParticipant<ContentChildrenProvider<View>>>() {
					// Type Literal
				});

		Provider<ActivityContentChildrenProvider> activityContentChildrenProvider = getProvider(
				ActivityContentChildrenProvider.class);

		contentChildrenBinder.addBinding().toInstance(new GMFProviderParticipant<>(DEFAULT_ACTIVITY_PRIORITY, activityContentChildrenProvider));		
	}

	@Override
	protected void bindIContentPartProvider() {
		binder().bind(new TypeLiteral<IContentPartProvider<View>>() {
		}).to(ContentPartProvider.class);
	}

	protected void bindPalette() {
		bind(PreferencesHint.class).toInstance(new PreferencesHint("org.eclipse.papyrus.uml.diagram.activity"));
		bind(PaletteRenderer.class).to(DefaultPaletteRenderer.class);
	}

	@Provides
	public PaletteDescriptor getPaletteDescriptor(Injector injector) {
		PapyrusPaletteDescriptor paletteDescriptor = new PapyrusPaletteDescriptor(
				"org.eclipse.papyrus.uml.gefdiag.activity.palette", "Activity Diagram Palette");
		injector.injectMembers(paletteDescriptor);
		return paletteDescriptor;
	}

}
