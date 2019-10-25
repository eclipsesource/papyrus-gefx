/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (EclipseSource) cletavernier@eclipsesource.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.gmf.module;

import java.util.Optional;
import java.util.function.Predicate;

import javax.inject.Singleton;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.fx.core.ThreadSynchronize;
import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.common.adapt.inject.AdaptableScopes;
import org.eclipse.gef.common.adapt.inject.AdapterMaps;
import org.eclipse.gef.mvc.fx.behaviors.AbstractBehavior;
import org.eclipse.gef.mvc.fx.parts.AbstractHandlePart;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IContentPartFactory;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.editor.handlers.CreateConnectionAndViewHandler;
import org.eclipse.papyrus.gef4.gmf.editor.handlers.CreateElementAndViewHandler;
import org.eclipse.papyrus.gef4.gmf.editor.handlers.MoveConnectionLabelHandler;
import org.eclipse.papyrus.gef4.gmf.editor.handlers.MoveNodeHandler;
import org.eclipse.papyrus.gef4.gmf.editor.handlers.MoveOnDragHandler;
import org.eclipse.papyrus.gef4.gmf.editor.handlers.ResizeNodeHandler;
import org.eclipse.papyrus.gef4.gmf.editor.handlers.ResizeOnDragHandler;
import org.eclipse.papyrus.gef4.gmf.editor.provisional.handlers.CollapseOnClickHandler;
import org.eclipse.papyrus.gef4.gmf.locators.AffixedLabelLocator;
import org.eclipse.papyrus.gef4.gmf.locators.ConnectionLabelLocator;
import org.eclipse.papyrus.gef4.gmf.module.anchors.RatioAnchorProvider;
import org.eclipse.papyrus.gef4.gmf.parts.ConnectorContentPart;
import org.eclipse.papyrus.gef4.gmf.parts.FloatingLabelContentPart;
import org.eclipse.papyrus.gef4.gmf.parts.NotationDiagramContentPart;
import org.eclipse.papyrus.gef4.gmf.parts.NotationDiagramRootPart;
import org.eclipse.papyrus.gef4.gmf.parts.NotationLabelContentPart;
import org.eclipse.papyrus.gef4.gmf.parts.ShapeContentPart;
import org.eclipse.papyrus.gef4.gmf.scope.ViewPartScope;
import org.eclipse.papyrus.gef4.gmf.services.EditingDomainTransactionService;
import org.eclipse.papyrus.gef4.gmf.services.GMFConnectionService;
import org.eclipse.papyrus.gef4.gmf.services.GMFProviderParticipant;
import org.eclipse.papyrus.gef4.gmf.services.NotationContentChildrenProvider;
import org.eclipse.papyrus.gef4.gmf.services.ProviderBasedContentPartFactory;
import org.eclipse.papyrus.gef4.gmf.style.ConnectorStyleProvider;
import org.eclipse.papyrus.gef4.gmf.style.DecorationNodeStyleProvider;
import org.eclipse.papyrus.gef4.gmf.style.DiagramStyleProvider;
import org.eclipse.papyrus.gef4.gmf.style.LabelStyleProvider;
import org.eclipse.papyrus.gef4.gmf.style.NotationLabelStyleProvider;
import org.eclipse.papyrus.gef4.gmf.style.NotationStyleService;
import org.eclipse.papyrus.gef4.gmf.style.ShapeStyleProvider;
import org.eclipse.papyrus.gef4.gmf.utils.AdapterUtil;
import org.eclipse.papyrus.gef4.gmf.utils.GMFPartUtil;
import org.eclipse.papyrus.gef4.handle.CollapseHandlePart;
import org.eclipse.papyrus.gef4.layout.Locator;
import org.eclipse.papyrus.gef4.module.AdapterRoles;
import org.eclipse.papyrus.gef4.parts.AffixedLabelContentPart;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.parts.CompartmentContentPart;
import org.eclipse.papyrus.gef4.parts.ConnectionContentPart;
import org.eclipse.papyrus.gef4.parts.IPrimaryContentPart;
import org.eclipse.papyrus.gef4.parts.LabelContentPart;
import org.eclipse.papyrus.gef4.parts.NodeContentPart;
import org.eclipse.papyrus.gef4.provider.IContentPartProvider;
import org.eclipse.papyrus.gef4.scopes.PartScope;
import org.eclipse.papyrus.gef4.scopes.PartScoped;
import org.eclipse.papyrus.gef4.services.AnchorageService;
import org.eclipse.papyrus.gef4.services.ConnectionService;
import org.eclipse.papyrus.gef4.services.ContentChildrenProvider;
import org.eclipse.papyrus.gef4.services.HelperProvider;
import org.eclipse.papyrus.gef4.services.HelperProviderParticipant;
import org.eclipse.papyrus.gef4.services.TransactionService;
import org.eclipse.papyrus.gef4.services.impl.HelperProviderImpl;
import org.eclipse.papyrus.gef4.services.style.CompartmentStyleService;
import org.eclipse.papyrus.gef4.services.style.EdgeStyleService;
import org.eclipse.papyrus.gef4.services.style.LabelStyleService;
import org.eclipse.papyrus.gef4.services.style.StyleService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;

import javafx.scene.Node;

/**
 * <p>
 * Abstract module to be extended by specific diagram editors based on
 * GMF/Notation.
 * </p>
 */
// TODO Split the module between base GMF and full GMF
// Base should only contain Part Factories and Notation-related services
// Full should contain the Element Types and Client Context
public abstract class GMFModule extends AbstractModule {

	public static final double DEFAULT_GMF_PRIORITY = 1;
	public static final double MAX_GMF_PRIORITY = 9;

	@Override
	protected void configure() {
		bind(TransactionService.class).to(EditingDomainTransactionService.class);

		bindIContentPartFactory();
		bindIContentPartProvider();
		bindDefaultContentChildrenProvider();

		bindScope();

		bindContentPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), BaseContentPart.class));
		bindConnectionAdapters(AdapterMaps.getAdapterMapBinder(binder(), ConnectionContentPart.class));
		bindNodeAdapters(AdapterMaps.getAdapterMapBinder(binder(), NodeContentPart.class));
		bindRootPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), IRootPart.class));
		bindPrimaryPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), IPrimaryContentPart.class));
		// define specific policy for affixed Label
		bindAffixedLabelContentPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), AffixedLabelContentPart.class));
		bindFXHandlePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), AbstractHandlePart.class));
		bindCollapseHandlePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), CollapseHandlePart.class));

		binder().bind(IRootPart.class).to(NotationDiagramRootPart.class);

		// Customizations
		bindStyleServices();

		bindAnchorageService();

		bindAnchorProviders();

		bindContentChildrenProvider();

		configureLocators();
	}

	private void bindContentChildrenProvider() {
		binder().bind(new TypeLiteral<HelperProvider<ContentChildrenProvider<View>>>() {
			// Type literal
		}).to(new TypeLiteral<HelperProviderImpl<ContentChildrenProvider<View>>>() {
			// Type literal
		}).in(Singleton.class);
	}

	protected void bindAnchorProviders() {
		AdapterMaps.getAdapterMapBinder(binder(), BaseContentPart.class).addBinding(AdapterRoles.fallbackRole())
				.to(RatioAnchorProvider.class);
	}

	protected void bindScope() {
		ViewPartScope partScope = new ViewPartScope();
		bindScope(PartScoped.class, partScope);
		bind(PartScope.class).toInstance(partScope);
	}

	protected void bindContentPartAdapters(MapBinder<AdapterKey<?>, Object> mapBinder) {
		bindModelAdapters(mapBinder);
		mapBinder.addBinding(AdapterKey.defaultRole()).to(CreateElementAndViewHandler.class);
		mapBinder.addBinding(AdapterKey.defaultRole()).to(CreateConnectionAndViewHandler.class);
	}

	protected void bindRootPartAdapters(MapBinder<AdapterKey<?>, Object> mapBinder) {
		mapBinder.addBinding(AdapterKey.defaultRole()).to(CreateElementAndViewHandler.class);
	}

	protected void bindConnectionAdapters(MapBinder<AdapterKey<?>, Object> mapBinder) {
		Multibinder.newSetBinder(binder(), new TypeLiteral<HelperProviderParticipant<ConnectionService>>() {
			// Type literal
		}).addBinding().toInstance(new GMFProviderParticipant<ConnectionService>(DEFAULT_GMF_PRIORITY,
				getProvider(GMFConnectionService.class)));
	}

	private void bindAnchorageService() {
		Multibinder.newSetBinder(binder(), new TypeLiteral<HelperProviderParticipant<AnchorageService>>() {
			// Type literal
		}).addBinding().toInstance(new GMFProviderParticipant<>(0, getProvider(GMFConnectionService.class)));
	}

	protected void bindStyleServices() {
		Multibinder<HelperProviderParticipant<StyleService>> styleBinder = Multibinder.newSetBinder(binder(),
				new TypeLiteral<HelperProviderParticipant<StyleService>>() {
					// Type literal
				});

		// Install a generic style provider on all parts
		styleBinder.addBinding().toInstance(new GMFProviderParticipant<>(0, getProvider(NotationStyleService.class)));

		// Override the StyleService just for the NodeContentPart
		styleBinder.addBinding().toInstance(new GMFProviderParticipant<StyleService>(1,
				getProvider(ShapeStyleProvider.class), ShapeContentPart.class));
		styleBinder.addBinding().toInstance(new GMFProviderParticipant<StyleService>(2,
				getProvider(LabelStyleProvider.class), NotationLabelContentPart.class));
		styleBinder.addBinding().toInstance(new GMFProviderParticipant<StyleService>(1,
				getProvider(DiagramStyleProvider.class), NotationDiagramContentPart.class));

		Multibinder<HelperProviderParticipant<LabelStyleService>> labelStyleBinder = Multibinder.newSetBinder(binder(),
				new TypeLiteral<HelperProviderParticipant<LabelStyleService>>() {
					// Type Literal
				});
		labelStyleBinder.addBinding()
				.toInstance(new GMFProviderParticipant<>(0, getProvider(NotationLabelStyleProvider.class)));

		Multibinder<HelperProviderParticipant<CompartmentStyleService>> compartmentStyleBinder = Multibinder
				.newSetBinder(binder(), new TypeLiteral<HelperProviderParticipant<CompartmentStyleService>>() {
					// Type Literal
				});
		compartmentStyleBinder.addBinding().toInstance(new GMFProviderParticipant<CompartmentStyleService>(0,
				getProvider(DecorationNodeStyleProvider.class), CompartmentContentPart.class::isInstance));

		Multibinder<HelperProviderParticipant<EdgeStyleService>> edgeStyleBinder = Multibinder.newSetBinder(binder(),
				new TypeLiteral<HelperProviderParticipant<EdgeStyleService>>() {
					// Type Literal
				});
		edgeStyleBinder.addBinding().toInstance(
				new GMFProviderParticipant<>(0, getProvider(ConnectorStyleProvider.class), ConnectorContentPart.class));
	}

	static class InstallAdaptersBehavior extends AbstractBehavior {
		@Override
		protected void doActivate() {
			super.doActivate();

			BaseContentPart<?, ?> host = (BaseContentPart<?, ?>) getHost();
			if (host.getContent() instanceof View) {
				View view = (View) host.getContent();
				AdapterUtil.setModelAdapters(view, findSemanticElement(GMFPartUtil.getBasePart(host)), host);
			}
		}

		protected EObject findSemanticElement(BaseContentPart<? extends View, ?> host) {
			final EObject element = host.getContent().getElement();
			if (element == null) {
				if (this instanceof IPrimaryContentPart) {
					return null; // Do not go beyond the Primary part
				}

				final BaseContentPart<? extends View, ? extends Node> parent = host.getParentBaseContentPart();
				if (parent != null) {
					return findSemanticElement(parent);
				}
			}
			return element;
		}
	}

	protected void bindModelAdapters(MapBinder<AdapterKey<?>, Object> mapBinder) {

		mapBinder.addBinding(AdapterKey.defaultRole()).to(InstallAdaptersBehavior.class)
				.in(AdaptableScopes.typed(BaseContentPart.class));
	}

	@Provides
	@PartScoped
	protected View getView(Diagram diagram) {
		return diagram; // Scoped value; See PartScope //TODO Rename ViewScope
	}

	@Provides
	@PartScoped // TODO Rename ViewScope
	IContentPart<?> createPart(View view, IContentPartProvider<View> provider, Injector injector) {
		IContentPart<?> contentPart = provider.createContentPart(view);
		if (contentPart == null) {
			System.err.println("Unable to create a part for View: " + view.getType());
			return null;
		}
		// TODO Initialize part scope before injecting the part

		// FIXME Optimize part injection by generating a module with all generated
		// edit part classes, and using memberInjectors. This will resolve part
		// dependencies
		// earlier & only once, which is safer & faster
		injector.injectMembers(contentPart);

		return contentPart;
	}

	//
	// Aliases for the IContentPart
	//

	@Provides
	@PartScoped
	IVisualPart<?> getVisualPart(IContentPart<?> contentPart) {
		return contentPart;
	}

	@Provides
	@PartScoped
	BaseContentPart<? extends View, ?> getBasePart(IContentPart<?> contentPart) {
		return GMFPartUtil.getBasePart(contentPart);
	}

	@Provides
	@Singleton
	protected DiagramEventBroker getEventBroker(TransactionalEditingDomain editingDomain) {
		return DiagramEventBroker.getInstance(editingDomain);
	}

	@Provides
	@Singleton
	protected TransactionalEditingDomain getEditingDomain(Diagram diagram) {
		return TransactionUtil.getEditingDomain(diagram);
	}

	@Provides
	@Singleton
	protected ThreadSynchronize getThreadSync() {
		BundleContext bundleContext = FrameworkUtil.getBundle(GMFModule.class).getBundleContext();
		ServiceReference<ThreadSynchronize> serviceReference = bundleContext
				.getServiceReference(ThreadSynchronize.class);
		return bundleContext.getService(serviceReference);
	}

	protected void bindIContentPartFactory() {
		binder().bind(new TypeLiteral<IContentPartFactory>() {
		}).to(ProviderBasedContentPartFactory.class);
	}

	protected void bindDefaultContentChildrenProvider() {

		Multibinder<HelperProviderParticipant<ContentChildrenProvider<View>>> contentChildrenBinder = Multibinder
				.newSetBinder(binder(), new TypeLiteral<HelperProviderParticipant<ContentChildrenProvider<View>>>() {
					// Type Literal
				});

		Provider<NotationContentChildrenProvider> notationContentChildrenProvider = getProvider(
				NotationContentChildrenProvider.class);

		contentChildrenBinder.addBinding().toInstance(new GMFProviderParticipant<>(0, notationContentChildrenProvider));
	}

	private void configureLocators() {
		Multibinder<HelperProviderParticipant<Optional<Locator>>> locators = Multibinder.newSetBinder(binder(),
				new TypeLiteral<HelperProviderParticipant<Optional<Locator>>>() {
					// Type literal
				});

		bindLocators(locators);
	}

	private void bindLocators(Multibinder<HelperProviderParticipant<Optional<Locator>>> locators) {
		Provider<Optional<Locator>> affixedLabelLocator = required(getProvider(AffixedLabelLocator.class));
		locators.addBinding().toInstance(new GMFProviderParticipant<>(DEFAULT_GMF_PRIORITY, affixedLabelLocator,
				FloatingLabelContentPart.class));

		Provider<Optional<Locator>> connectionLabelLocator = required(getProvider(ConnectionLabelLocator.class));
		locators.addBinding().toInstance(new GMFProviderParticipant<>(DEFAULT_GMF_PRIORITY + .3, connectionLabelLocator,
				connectionLabelMatcher()));
	}

	protected <T> Provider<Optional<T>> required(Provider<? extends T> provider) {
		return () -> Optional.of(provider.get());
	}

	protected <T> Provider<Optional<T>> optional(Provider<? extends T> provider) {
		return () -> Optional.ofNullable(provider.get());
	}

	/**
	 * <p>
	 * A Predicate matching {@link AffixedLabelContentPart} owned by a Connector.
	 * </p>
	 */
	private static Predicate<BaseContentPart<? extends View, ?>> connectionLabelMatcher() {
		return part -> {
			if (part instanceof AffixedLabelContentPart) {
				return part.getContent().eContainer() instanceof Edge;
			}
			return false;
		};
	}

	protected void bindNodeAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(MoveOnDragHandler.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(MoveNodeHandler.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ResizeNodeHandler.class);
	}

	protected void bindPrimaryPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// Nothing (yet)
	}

	protected void bindAffixedLabelContentPartAdapters(final MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// For now we use the same MoveHandler for all AffixedLabels;
		// we should configure that after
		// https://github.com/eclipsesource/papyrus-gefx/issues/31 is fixed
		adapterMapBinder.addBinding(AdapterKey.role("AffixedLabel"))// $NON-NLS-1$
				.to(MoveConnectionLabelHandler.class);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(MoveOnDragHandler.class);
	}

	protected void bindCollapseHandlePartAdapters(final MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(CollapseOnClickHandler.class);
	}

	protected void bindFXHandlePartAdapters(final MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// FIXME this shouldn't be installed on all handle parts (e.g. this will be
		// installed on connection's bendpoints and anchors)
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ResizeOnDragHandler.class);
	}

	protected abstract void bindIContentPartProvider();
}
