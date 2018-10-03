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
package org.eclipse.papyrus.uml.gefdiag.clazz.module;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.services.AbstractGMFProviderParticipant;
import org.eclipse.papyrus.gef4.layout.Locator;
import org.eclipse.papyrus.gef4.palette.DefaultPaletteRenderer;
import org.eclipse.papyrus.gef4.palette.PaletteDescriptor;
import org.eclipse.papyrus.gef4.palette.PaletteRenderer;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.provider.IContentPartProvider;
import org.eclipse.papyrus.gef4.services.HelperProviderParticipant;
import org.eclipse.papyrus.infra.gefdiag.common.palette.PapyrusPaletteDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteConfiguration;
import org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.RedefinableTemplateSignatureEditPart;
import org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TemplateSignatureEditPart;
import org.eclipse.papyrus.uml.gefdiag.clazz.providers.ContentPartProvider;
import org.eclipse.papyrus.uml.gefdiag.common.locator.TemplateLocator;
import org.eclipse.papyrus.uml.gefdiag.common.module.UMLDiagramModule;

import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

public class ClassDiagramModule extends UMLDiagramModule {

	public static final double DEFAULT_PRIORITY = UMLDiagramModule.DEFAULT_PRIORITY + 1;

	@Override
	protected void configure() {
		super.configure();
		bindPalette();
	}

	@Override
	protected void bindIContentPartProvider() {
		binder().bind(new TypeLiteral<IContentPartProvider<View>>() {
		}).to(ContentPartProvider.class);
	}

	@Override
	protected void bindLocators(Multibinder<HelperProviderParticipant<Optional<Locator>>> locators) {
		super.bindLocators(locators);
		locators.addBinding().toInstance(new AbstractGMFProviderParticipant<Optional<Locator>>(DEFAULT_PRIORITY,
				TemplateSignatureEditPart.class, RedefinableTemplateSignatureEditPart.class) {

			@Override
			protected Optional<Locator> doCreateInstance(BaseContentPart<? extends View, ?> basePart) {
				return Optional.of(new TemplateLocator(basePart));
			}
		});
	}

	protected void bindPalette() {
		bind(PreferencesHint.class).toInstance(new PreferencesHint("org.eclipse.papyrus.uml.diagram.clazz"));
		bind(PaletteRenderer.class).to(DefaultPaletteRenderer.class);
	}

	@Provides
	public PaletteDescriptor getPaletteDescriptor(Injector injector) {
		PapyrusPaletteDescriptor paletteDescriptor = new PapyrusPaletteDescriptor("org.eclipse.papyrus.uml.gefdiag.clazz.palette", "Class Diagram Palette");
		injector.injectMembers(paletteDescriptor);
		return paletteDescriptor;
	}

	@Provides
	public Collection<PaletteConfiguration> getPaletteConfigurations() {
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.getResource(URI.createPlatformPluginURI("/org.eclipse.papyrus.uml.diagram.clazz/model/PapyrusUMLClassDiagram.paletteconfiguration", true), true);
		return resource.getContents().stream().filter(PaletteConfiguration.class::isInstance).map(PaletteConfiguration.class::cast).collect(Collectors.toList());
	}

}
