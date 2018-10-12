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
package org.eclipse.papyrus.uml.gefdiag.common.module;

import java.util.Optional;

import javax.inject.Named;
import javax.inject.Singleton;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.common.adapt.inject.AdapterMaps;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.papyrus.gef4.gmf.module.GMFModule;
import org.eclipse.papyrus.gef4.layout.Locator;
import org.eclipse.papyrus.gef4.parts.LabelContentPart;
import org.eclipse.papyrus.gef4.services.HelperProviderParticipant;
import org.eclipse.papyrus.gef4.services.LabelService;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.uml.gefdiag.common.services.UMLImageService;
import org.eclipse.papyrus.uml.gefdiag.common.services.label.CommentLabelService;
import org.eclipse.papyrus.uml.gefdiag.common.services.label.GeneralizationLabelService;
import org.eclipse.papyrus.uml.gefdiag.common.services.label.NamedElementLabelService;
import org.eclipse.papyrus.uml.gefdiag.common.services.label.OperationLabelService;
import org.eclipse.papyrus.uml.gefdiag.common.services.label.PropertyLabelService;
import org.eclipse.papyrus.uml.gefdiag.common.services.label.SlotLabelService;

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

	}

	protected void bindLabelPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(
				AdapterKey.defaultRole())
				.to(UMLImageService.class);

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

}
