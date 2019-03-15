/*****************************************************************************
 * Copyright (c) 2016 - 2019 CEA LIST, EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  EclipseSource
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
import org.eclipse.papyrus.gef4.gmf.locators.ConnectionLabelLocator;
import org.eclipse.papyrus.gef4.gmf.locators.ConnectionLabelLocator.Reference;
import org.eclipse.papyrus.gef4.gmf.services.AbstractGMFProviderParticipant;
import org.eclipse.papyrus.gef4.layout.Locator;
import org.eclipse.papyrus.gef4.palette.DefaultPaletteRenderer;
import org.eclipse.papyrus.gef4.palette.PaletteDescriptor;
import org.eclipse.papyrus.gef4.palette.PaletteRenderer;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.provider.IContentPartProvider;
import org.eclipse.papyrus.gef4.services.HelperProviderParticipant;
import org.eclipse.papyrus.gef4.services.LabelService;
import org.eclipse.papyrus.infra.gefdiag.common.palette.PapyrusPaletteDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteConfiguration;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ModelEditPart;
import org.eclipse.papyrus.uml.gefdiag.clazz.ClassConstants;
import org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationFloatingNameEditPart;
import org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationMultiplicitySourceEditPart;
import org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationMultiplicityTargetEditPart;
import org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationSourceNameEditPart;
import org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.AssociationTargetNameEditPart;
import org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.ClassEditPart;
import org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.PackageNameEditPart;
import org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.RedefinableTemplateSignatureEditPart;
import org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TemplateParameterEditPart;
import org.eclipse.papyrus.uml.gefdiag.clazz.edit.parts.TemplateSignatureEditPart;
import org.eclipse.papyrus.uml.gefdiag.clazz.providers.ContentPartProvider;
import org.eclipse.papyrus.uml.gefdiag.clazz.service.label.AssociationLabelService;
import org.eclipse.papyrus.uml.gefdiag.common.locator.TemplateLocator;
import org.eclipse.papyrus.uml.gefdiag.common.module.UMLDiagramModule;
import org.eclipse.papyrus.uml.gefdiag.common.services.label.StereotypeLabelService;

import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

public class ClassDiagramModule extends UMLDiagramModule {

	public static final double DEFAULT_CLASS_PRIORITY = UMLDiagramModule.MAX_UML_PRIORITY + 1;
	public static final double MAX_CLASS_PRIORITY = DEFAULT_CLASS_PRIORITY + 9;

	@Override
	protected void configure() {
		super.configure();
		bindPalette();
	}

	@Override
	protected void bindIContentPartProvider() {
		binder().bind(new TypeLiteral<IContentPartProvider<View>>() {
		}).to(ContentPartProvider.class);
		
		registerPartClass(ClassEditPart.class);
		registerPartClass(TemplateParameterEditPart.class);
		registerPartClass(PackageNameEditPart.class);
	}

	@Override
	protected void bindLocators(Multibinder<HelperProviderParticipant<Optional<Locator>>> locators) {
		super.bindLocators(locators);
		locators.addBinding().toInstance(new AbstractGMFProviderParticipant<Optional<Locator>>(DEFAULT_CLASS_PRIORITY,
				TemplateSignatureEditPart.class, RedefinableTemplateSignatureEditPart.class) {

			@Override
			protected Optional<Locator> doCreateInstance(BaseContentPart<? extends View, ?> basePart) {
				return Optional.of(new TemplateLocator(basePart));
			}
		});

		locators.addBinding().toInstance(new AbstractGMFProviderParticipant<Optional<Locator>>(
				DEFAULT_CLASS_PRIORITY, AssociationFloatingNameEditPart.class, AssociationMultiplicitySourceEditPart.class, AssociationMultiplicityTargetEditPart.class, AssociationSourceNameEditPart.class, AssociationTargetNameEditPart.class) {
			@Override
			protected Optional<Locator> doCreateInstance(BaseContentPart<? extends View, ?> basePart) {
				ConnectionLabelLocator locator = new ConnectionLabelLocator(basePart);
				View view = basePart.getContent();
				switch (view.getType()) {
				// Source and Target are reversed: the sourceRoleLabel is closer to the association target end
				case ClassConstants.ASSOCIATION_SOURCE_ROLE:
					locator.setReference(Reference.TARGET);
					break;
				case ClassConstants.ASSOCIATION_TARGET_ROLE:
					locator.setReference(Reference.SOURCE);
					break;
				case ClassConstants.ASSOCIATION_SOURCE_MULTIPLICITY:
					locator.setReference(Reference.TARGET);
					break;
				case ClassConstants.ASSOCIATION_TARGET_MULTIPLICITY:
					locator.setReference(Reference.SOURCE);
					break;
				case ClassConstants.ASSOCIATION_NAME:
					locator.setReference(Reference.CENTER);
					break;
				case ClassConstants.ASSOCIATION_STEREOTYPE:
					locator.setReference(Reference.CENTER);
					break;
				default:
					System.err.println("Unknown type: " + view.getType());
				}
				return Optional.of(locator);
			}
		});
	}

	/**
	 * @see org.eclipse.papyrus.uml.gefdiag.common.module.UMLDiagramModule#configureLabelProviders(com.google.inject.multibindings.Multibinder)
	 *
	 * @param labelProviders
	 */
	@Override
	protected void configureLabelProviders(Multibinder<HelperProviderParticipant<LabelService>> labelProviders) {
		super.configureLabelProviders(labelProviders);


		// Association (+ Role labels)
		labelProviders.addBinding().toInstance(new AssociationLabelService(DEFAULT_CLASS_PRIORITY));
		
		// Register the StereotypeLabelService with a higher priority than specific elements,
		// since it only applies to StereotypeLabels (Other label providers are only filtered by semantic element)
		labelProviders.addBinding().toInstance(new StereotypeLabelService(MAX_CLASS_PRIORITY));
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
		assert ModelEditPart.class != null; // Just a compile-time test to make sure we get the class diagram dependency; since we get the palette from it.

		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.getResource(URI.createPlatformPluginURI("/org.eclipse.papyrus.uml.diagram.clazz/model/PapyrusUMLClassDiagram.paletteconfiguration", true), true);
		return resource.getContents().stream().filter(PaletteConfiguration.class::isInstance).map(PaletteConfiguration.class::cast).collect(Collectors.toList());
	}

}
