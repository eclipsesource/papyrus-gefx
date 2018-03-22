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

import javax.inject.Named;
import javax.inject.Singleton;

import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.papyrus.gef4.gmf.module.GMFModule;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;

import com.google.inject.Provides;
import com.google.inject.name.Names;

/**
 * Abstract module to be extended by GMF/UML Diagram Editors
 */
public abstract class UMLDiagramModule extends GMFModule {

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
	}

	// @Override
	// protected void bindDefaultContentChildrenProvider() {
	// binder().bind(new TypeLiteral<ContentChildrenAdapter<View>>() {
	// }).to(StereotypeAwareContentChildrenProvider.class).in(PartScoped.class);
	// }

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

}
