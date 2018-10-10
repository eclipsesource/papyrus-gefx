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
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IContentPartFactory;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.module.anchors.RatioAnchorProvider;
import org.eclipse.papyrus.gef4.gmf.parts.ConnectorContentPart;
import org.eclipse.papyrus.gef4.gmf.parts.FloatingLabelContentPart;
import org.eclipse.papyrus.gef4.gmf.parts.NotationDiagramContentPart;
import org.eclipse.papyrus.gef4.gmf.parts.NotationDiagramRootPart;
import org.eclipse.papyrus.gef4.gmf.parts.ShapeContentPart;
import org.eclipse.papyrus.gef4.gmf.scope.ViewPartScope;
import org.eclipse.papyrus.gef4.gmf.services.AbstractGMFProviderParticipant;
import org.eclipse.papyrus.gef4.gmf.services.EditingDomainTransactionService;
import org.eclipse.papyrus.gef4.gmf.services.GMFConnectionService;
import org.eclipse.papyrus.gef4.gmf.services.NotationContentChildrenProvider;
import org.eclipse.papyrus.gef4.gmf.services.ProviderBasedContentPartFactory;
import org.eclipse.papyrus.gef4.gmf.style.ConnectorStyleProvider;
import org.eclipse.papyrus.gef4.gmf.style.DecorationNodeStyleProvider;
import org.eclipse.papyrus.gef4.gmf.style.DiagramStyleProvider;
import org.eclipse.papyrus.gef4.gmf.style.NotationLabelStyleProvider;
import org.eclipse.papyrus.gef4.gmf.style.NotationStyleService;
import org.eclipse.papyrus.gef4.gmf.style.ShapeStyleProvider;
import org.eclipse.papyrus.gef4.gmf.utils.AdapterUtil;
import org.eclipse.papyrus.gef4.gmf.utils.GMFPartUtil;
import org.eclipse.papyrus.gef4.layout.AffixedLabelLocator;
import org.eclipse.papyrus.gef4.layout.ConnectionLabelLocator;
import org.eclipse.papyrus.gef4.layout.Locator;
import org.eclipse.papyrus.gef4.module.AdapterRoles;
import org.eclipse.papyrus.gef4.parts.AffixedLabelContentPart;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.parts.CompartmentContentPart;
import org.eclipse.papyrus.gef4.parts.ConnectionContentPart;
import org.eclipse.papyrus.gef4.parts.IPrimaryContentPart;
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
public abstract class GMFModule extends AbstractModule {

	public static final double DEFAULT_GMF_PRIORITY = 1;
	public static final double MAX_GMF_PRIORITY = 9;

	@Override
	protected void configure() {
		bind(TransactionService.class).to(EditingDomainTransactionService.class);

		bindIContentPartFactory();
		bindDefaultContentChildrenProvider();

		bindScope();

		bindStyleServices();
		bindContentPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), BaseContentPart.class));
		bindConnectionAdapters(AdapterMaps.getAdapterMapBinder(binder(), ConnectionContentPart.class));

		bindIContentPartProvider();

		binder().bind(IRootPart.class).to(NotationDiagramRootPart.class);

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
	}

	protected void bindConnectionAdapters(MapBinder<AdapterKey<?>, Object> mapBinder) {
		mapBinder.addBinding(AdapterKey.defaultRole()).to(GMFConnectionService.class);

		Multibinder.newSetBinder(binder(), new TypeLiteral<HelperProviderParticipant<ConnectionService>>() {
			// Type literal
		}).addBinding().toInstance(new AbstractGMFProviderParticipant<ConnectionService>(0) {

			@Override
			protected ConnectionService doCreateInstance(BaseContentPart<? extends View, ?> basePart) {
				GMFConnectionService gmfConnectionService = new GMFConnectionService();
				gmfConnectionService.setAdaptable(basePart);
				return gmfConnectionService;
			}

		});
	}

	private void bindAnchorageService() {
		Multibinder.newSetBinder(binder(), new TypeLiteral<HelperProviderParticipant<AnchorageService>>() {
			// Type literal
		}).addBinding().toInstance(new AbstractGMFProviderParticipant<AnchorageService>(0) {

			@Override
			protected AnchorageService doCreateInstance(BaseContentPart<? extends View, ?> basePart) {
				GMFConnectionService gmfConnectionService = new GMFConnectionService();
				gmfConnectionService.setAdaptable(basePart);
				return gmfConnectionService;
			}

		});
	}

	protected void bindStyleServices() {
		Multibinder<HelperProviderParticipant<StyleService>> styleBinder = Multibinder.newSetBinder(binder(),
				new TypeLiteral<HelperProviderParticipant<StyleService>>() {
					// Type literal
				});

		// Install a generic style provider on all parts
		styleBinder.addBinding().toInstance(new AbstractGMFProviderParticipant<StyleService>(0) {

			@Override
			public StyleService doCreateInstance(BaseContentPart<? extends View, ?> part) {
				return new NotationStyleService(part);
			}

		});

		// Override the StyleService just for the NodeContentPart
		styleBinder.addBinding()
				.toInstance(new AbstractGMFProviderParticipant<StyleService>(1, ShapeContentPart.class) {
					@Override
					protected StyleService doCreateInstance(BaseContentPart<? extends View, ?> part) {
						return new ShapeStyleProvider(part);
					}
				});

		styleBinder.addBinding()
				.toInstance(new AbstractGMFProviderParticipant<StyleService>(1, NotationDiagramContentPart.class) {
					@Override
					protected StyleService doCreateInstance(BaseContentPart<? extends View, ?> part) {
						return new DiagramStyleProvider(part);
					}
				});

		Multibinder<HelperProviderParticipant<LabelStyleService>> labelStyleBinder = Multibinder.newSetBinder(binder(),
				new TypeLiteral<HelperProviderParticipant<LabelStyleService>>() {
					// Type Literal
				});

		labelStyleBinder.addBinding().toInstance(new AbstractGMFProviderParticipant<LabelStyleService>(0) {

			@Override
			protected LabelStyleService doCreateInstance(BaseContentPart<? extends View, ?> basePart) {
				return new NotationLabelStyleProvider(basePart);
			}

		});

		Multibinder<HelperProviderParticipant<CompartmentStyleService>> compartmentStyleBinder = Multibinder
				.newSetBinder(binder(), new TypeLiteral<HelperProviderParticipant<CompartmentStyleService>>() {
					// Type Literal
				});

		compartmentStyleBinder.addBinding().toInstance(new AbstractGMFProviderParticipant<CompartmentStyleService>(0,
				CompartmentContentPart.class::isInstance) {

			@Override
			protected CompartmentStyleService doCreateInstance(BaseContentPart<? extends View, ?> basePart) {
				return new DecorationNodeStyleProvider(basePart);
			}

		});

		Multibinder<HelperProviderParticipant<EdgeStyleService>> edgeStyleBinder = Multibinder.newSetBinder(binder(),
				new TypeLiteral<HelperProviderParticipant<EdgeStyleService>>() {
					// Type Literal
				});

		edgeStyleBinder.addBinding().toInstance(
				new AbstractGMFProviderParticipant<EdgeStyleService>(0, ConnectorContentPart.class::isInstance) {

					@Override
					protected EdgeStyleService doCreateInstance(BaseContentPart<? extends View, ?> basePart) {
						return new ConnectorStyleProvider(basePart);
					}

				});
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
		return diagram; // Scoped value; See PartScope
	}

	@Provides
	@PartScoped
	IContentPart<?> createPart(View view, IContentPartProvider<View> provider, Injector injector) {
		IContentPart<?> contentPart = provider.createContentPart(view);
		if (contentPart == null) {
			System.err.println("Unable to create a part for View: " + view.getType());
			return null;
		}
		injector.injectMembers(contentPart);
		return contentPart;
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

		contentChildrenBinder.addBinding()
				.toInstance(new AbstractGMFProviderParticipant<ContentChildrenProvider<View>>(0) {

					@Override
					protected ContentChildrenProvider<View> doCreateInstance(
							BaseContentPart<? extends View, ?> basePart) {
						return new NotationContentChildrenProvider(basePart);
					}
				});
	}

	private void configureLocators() {
		Multibinder<HelperProviderParticipant<Optional<Locator>>> locators = Multibinder.newSetBinder(binder(),
				new TypeLiteral<HelperProviderParticipant<Optional<Locator>>>() {
					// Type literal
				});

		bindLocators(locators);
	}

	private void bindLocators(Multibinder<HelperProviderParticipant<Optional<Locator>>> locators) {
		locators.addBinding().toInstance(new AbstractGMFProviderParticipant<Optional<Locator>>(DEFAULT_GMF_PRIORITY,
				FloatingLabelContentPart.class) {

			@Override
			protected Optional<Locator> doCreateInstance(BaseContentPart<? extends View, ?> basePart) {
				return Optional.of(new AffixedLabelLocator(basePart));
			}
		});

		locators.addBinding().toInstance(new AbstractGMFProviderParticipant<Optional<Locator>>(
				DEFAULT_GMF_PRIORITY + .3, connectionLabelMatcher()) {
			@Override
			protected Optional<Locator> doCreateInstance(BaseContentPart<? extends View, ?> basePart) {
				return Optional.of(new ConnectionLabelLocator(basePart));
			}
		});
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

	protected abstract void bindIContentPartProvider();
}
