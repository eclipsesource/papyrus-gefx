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
package org.eclipse.papyrus.uml.gefdiag.component.module;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.locators.BorderItemLocator;
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
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ModelEditPart;
import org.eclipse.papyrus.uml.gefdiag.common.module.UMLDiagramModule;
import org.eclipse.papyrus.uml.gefdiag.component.edit.parts.PortEditPart;
import org.eclipse.papyrus.uml.gefdiag.component.providers.ContentPartProvider;

import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

public class ComponentDiagramModule extends UMLDiagramModule {

	public static final double DEFAULT_COMPONENT_PRIORITY = UMLDiagramModule.MAX_UML_PRIORITY + 1;
	public static final double MAX_COMPONENT_PRIORITY = DEFAULT_COMPONENT_PRIORITY + 9;

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
		locators.addBinding().toInstance(new AbstractGMFProviderParticipant<Optional<Locator>>(DEFAULT_COMPONENT_PRIORITY,
				PortEditPart.class) {

			@Override
			protected Optional<Locator> doCreateInstance(BaseContentPart<? extends View, ?> basePart) {
				return Optional.of(new BorderItemLocator(basePart));
			}
		});
	}

	protected void bindPalette() {
		bind(PreferencesHint.class).toInstance(new PreferencesHint("org.eclipse.papyrus.uml.diagram.component"));
		bind(PaletteRenderer.class).to(DefaultPaletteRenderer.class);
	}

	@Provides
	public PaletteDescriptor getPaletteDescriptor(Injector injector) {
		PapyrusPaletteDescriptor paletteDescriptor = new PapyrusPaletteDescriptor("org.eclipse.papyrus.uml.gefdiag.component.palette", "Component Diagram Palette");
		injector.injectMembers(paletteDescriptor);
		return paletteDescriptor;
	}

	@Provides
	public Collection<PaletteConfiguration> getPaletteConfigurations() {
		assert ModelEditPart.class != null; // Just a compile-time test to make sure we get the diagram dependency; since we get the palette from it.

		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.getResource(URI.createPlatformPluginURI("/org.eclipse.papyrus.uml.diagram.component/model/PapyrusUMLComponentDiagram.paletteconfiguration", true), true);
		return resource.getContents().stream().filter(PaletteConfiguration.class::isInstance).map(PaletteConfiguration.class::cast).collect(Collectors.toList());
	}

}
