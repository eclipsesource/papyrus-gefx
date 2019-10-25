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
package org.eclipse.papyrus.uml.gefdiag.common.module;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import javax.inject.Named;
import javax.inject.Singleton;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.common.adapt.inject.AdapterMaps;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.gef4.gmf.module.GMFModule;
import org.eclipse.papyrus.gef4.gmf.parts.FloatingLabelContentPart;
import org.eclipse.papyrus.gef4.gmf.parts.NotationLabelContentPart;
import org.eclipse.papyrus.gef4.gmf.parts.NotationListItemContentPart;
import org.eclipse.papyrus.gef4.gmf.services.GMFProviderParticipant;
import org.eclipse.papyrus.gef4.layout.Locator;
import org.eclipse.papyrus.gef4.palette.PaletteDescriptor;
import org.eclipse.papyrus.gef4.parts.LabelContentPart;
import org.eclipse.papyrus.gef4.services.HelperProviderParticipant;
import org.eclipse.papyrus.gef4.services.ImageService;
import org.eclipse.papyrus.gef4.services.LabelService;
import org.eclipse.papyrus.gef4.services.style.EdgeStyleService;
import org.eclipse.papyrus.infra.architecture.ArchitectureDescriptionUtils;
import org.eclipse.papyrus.infra.core.architecture.RepresentationKind;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureViewpoint;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram;
import org.eclipse.papyrus.infra.gmfdiag.style.PapyrusDiagramStyle;
import org.eclipse.papyrus.infra.gmfdiag.style.StylePackage;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.uml.gefdiag.common.services.UMLImageService;
import org.eclipse.papyrus.uml.gefdiag.common.services.edges.SimpleAssociationEdgeService;
import org.eclipse.papyrus.uml.gefdiag.common.services.label.CommentLabelService;
import org.eclipse.papyrus.uml.gefdiag.common.services.label.GeneralizationLabelService;
import org.eclipse.papyrus.uml.gefdiag.common.services.label.NamedElementLabelService;
import org.eclipse.papyrus.uml.gefdiag.common.services.label.OperationLabelService;
import org.eclipse.papyrus.uml.gefdiag.common.services.label.PropertyLabelService;
import org.eclipse.papyrus.uml.gefdiag.common.services.label.SlotLabelService;
import org.eclipse.papyrus.uml.gefdiag.common.services.label.StereotypeLabelService;

import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

/**
 * Abstract module to be extended by GMF/UML Diagram Editors
 */
public abstract class UMLDiagramModule extends GMFModule {

	/**
	 * The priority used for generic UML Elements
	 */
	public static final double DEFAULT_UML_PRIORITY = GMFModule.MAX_GMF_PRIORITY + 1;

	/**
	 * The highest priority level used by this Module. Modules that want to specialize this module
	 * should use values higher than this.
	 */
	public static final double MAX_UML_PRIORITY = DEFAULT_UML_PRIORITY + 9;

	/**
	 * Name of the injected {@link String} property representing the {@link IClientContext} ID
	 *
	 * @see {@link Named @Named}
	 */
	public static final String CLIENT_CONTEXT_ID = "clientContextID";

	/**
	 * @see org.eclipse.papyrus.gef4.module.GEFFxModule#configure()
	 *
	 */
	@Override
	protected void configure() {
		super.configure();

		bindElementTypesRegistry();
		configureClientContextID();
		bindLabelPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), LabelContentPart.class));

		configureLocators();

		Multibinder<HelperProviderParticipant<LabelService>> labelProviders = Multibinder.newSetBinder(binder(),
				new TypeLiteral<HelperProviderParticipant<LabelService>>() {
					// Type literal
				});

		configureLabelProviders(labelProviders);

		Multibinder<HelperProviderParticipant<EdgeStyleService>> edgeStyleProviders = Multibinder.newSetBinder(binder(),
				new TypeLiteral<HelperProviderParticipant<EdgeStyleService>>() {
					// Type literal
				});
		configureStyleProviders(edgeStyleProviders);

		Multibinder<HelperProviderParticipant<ImageService>> imageProviders = Multibinder.newSetBinder(binder(),
				new TypeLiteral<HelperProviderParticipant<ImageService>>() {
					// Type literal
				});
		configureImageProviders(imageProviders);

		bindPaletteDescriptorAsViewerAdapter();
	}

	protected void bindPaletteDescriptorAsViewerAdapter() {
		AdapterMaps.getAdapterMapBinder(binder(), IViewer.class).addBinding(AdapterKey.defaultRole()).to(PaletteDescriptor.class);
	}

	protected void configureLabelProviders(Multibinder<HelperProviderParticipant<LabelService>> labelProviders) {
		// Named element
		labelProviders.addBinding().toInstance(new NamedElementLabelService(DEFAULT_UML_PRIORITY));

		// Comment
		labelProviders.addBinding().toInstance(new CommentLabelService(DEFAULT_UML_PRIORITY));

		// Generalization
		labelProviders.addBinding().toInstance(new GeneralizationLabelService(DEFAULT_UML_PRIORITY));


		//
		// Specific Named Elements (Higher priority than NamedElement)
		//

		final double SPECIFIC_NAMED_ELEMENTS_PRIORITY = DEFAULT_UML_PRIORITY + 1;

		// Slot
		labelProviders.addBinding().toInstance(new SlotLabelService(SPECIFIC_NAMED_ELEMENTS_PRIORITY));

		// Property
		labelProviders.addBinding().toInstance(new PropertyLabelService(SPECIFIC_NAMED_ELEMENTS_PRIORITY));

		// Operation
		labelProviders.addBinding().toInstance(new OperationLabelService(SPECIFIC_NAMED_ELEMENTS_PRIORITY));

		//
		// Stereotype labels
		//

		labelProviders.addBinding().toInstance(new StereotypeLabelService(MAX_UML_PRIORITY));
	}

	protected void configureImageProviders(Multibinder<HelperProviderParticipant<ImageService>> imageProviders) {
		imageProviders.addBinding().toInstance(new GMFProviderParticipant<>(DEFAULT_UML_PRIORITY,
				getProvider(UMLImageService.class), FloatingLabelContentPart.class, NotationLabelContentPart.class, NotationListItemContentPart.class));
	}

	protected void configureStyleProviders(Multibinder<HelperProviderParticipant<EdgeStyleService>> edgeStyleProviders) {
		// Associations
		edgeStyleProviders.addBinding().toInstance(new SimpleAssociationEdgeService(DEFAULT_UML_PRIORITY));
	}

	protected void bindLabelPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// Nothing yet
	}

	protected void bindElementTypesRegistry() {
		binder().bind(ElementTypeRegistry.class).toInstance(ElementTypeRegistry.getInstance());
	}

	@Provides
	@Singleton
	protected IClientContext bindClientContext(@Named(CLIENT_CONTEXT_ID) String clientContextId) {
		return ClientContextManager.getInstance().getClientContext(clientContextId);
	}

	protected void configureClientContextID() {
		// Bind to the default Papyrus Type Context ID
		binder().bind(String.class).annotatedWith(Names.named(CLIENT_CONTEXT_ID)).toInstance(TypeContext.getDefaultContextId());
	}

	protected void configureLocators() {
		Multibinder<HelperProviderParticipant<Optional<Locator>>> locators = Multibinder.newSetBinder(binder(), new TypeLiteral<HelperProviderParticipant<Optional<Locator>>>() {
			// Type literal
		});

		bindLocators(locators);
	}

	protected void bindLocators(Multibinder<HelperProviderParticipant<Optional<Locator>>> locators) {
		// Subclasses may override
	}
	
	@Provides
	public Collection<PaletteConfiguration> getPaletteConfigurations(Diagram diagram) {
		return getPalettesFromArchitecture(diagram);
	}
	
	// XXX This implementation is a little bit brutal... 
	// At the very least we should use a separate palette provider class
	protected Collection<PaletteConfiguration> getPalettesFromArchitecture(Diagram diagram) {
		PapyrusDiagramStyle style = (PapyrusDiagramStyle)diagram.getStyle(StylePackage.Literals.PAPYRUS_DIAGRAM_STYLE);
		if (style == null) {
			return null;
		}
		String diagramKindId = style.getDiagramKindId();
		ArchitectureDescriptionUtils util = new ArchitectureDescriptionUtils((ModelSet)diagram.eResource().getResourceSet());
		Collection<MergedArchitectureViewpoint> viewpoints = util.getArchitectureContext().getViewpoints();
		for (MergedArchitectureViewpoint vp : viewpoints) {
			for (RepresentationKind repKind : vp.getRepresentationKinds()) {
				if (repKind instanceof PapyrusDiagram) {
					PapyrusDiagram diagramKind = (PapyrusDiagram) repKind;
					String id = diagramKind.getId();
					if (Objects.equals(diagramKindId, id)) {
						return diagramKind.getPalettes();
					}
				}
			}
		}
		return null;
	}

}
