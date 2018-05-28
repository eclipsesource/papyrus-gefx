package org.eclipse.papyrus.gef4.gmf.module;

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
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.module.anchors.RatioAnchorProvider;
import org.eclipse.papyrus.gef4.gmf.parts.NotationDiagramRootPart;
import org.eclipse.papyrus.gef4.gmf.scope.ViewPartScope;
import org.eclipse.papyrus.gef4.gmf.services.EditingDomainTransactionService;
import org.eclipse.papyrus.gef4.gmf.services.GMFConnectionService;
import org.eclipse.papyrus.gef4.gmf.services.NotationContentChildrenProvider;
import org.eclipse.papyrus.gef4.gmf.services.ParserTextAdapter;
import org.eclipse.papyrus.gef4.gmf.services.ProviderBasedContentPartFactory;
import org.eclipse.papyrus.gef4.gmf.style.ConnectorStyleProvider;
import org.eclipse.papyrus.gef4.gmf.style.DecorationNodeStyleProvider;
import org.eclipse.papyrus.gef4.gmf.style.DiagramStyleProvider;
import org.eclipse.papyrus.gef4.gmf.style.NotationLabelStyleProvider;
import org.eclipse.papyrus.gef4.gmf.style.NotationStyleService;
import org.eclipse.papyrus.gef4.gmf.style.ShapeStyleProvider;
import org.eclipse.papyrus.gef4.gmf.utils.AdapterUtil;
import org.eclipse.papyrus.gef4.module.AdapterRoles;
import org.eclipse.papyrus.gef4.parts.AbstractLabelContentPart;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.parts.CompartmentContentPart;
import org.eclipse.papyrus.gef4.parts.ConnectionContentPart;
import org.eclipse.papyrus.gef4.parts.DiagramContentPart;
import org.eclipse.papyrus.gef4.parts.IPrimaryContentPart;
import org.eclipse.papyrus.gef4.parts.NodeContentPart;
import org.eclipse.papyrus.gef4.provider.IContentPartProvider;
import org.eclipse.papyrus.gef4.scopes.PartScope;
import org.eclipse.papyrus.gef4.scopes.PartScoped;
import org.eclipse.papyrus.gef4.services.ContentChildrenAdapter;
import org.eclipse.papyrus.gef4.services.TransactionService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;

import javafx.scene.Node;

/**
 * <p>
 * Abstract module to be extended by specific diagram editors based on
 * GMF/Notation.
 * </p>
 */
public abstract class GMFModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(TransactionService.class).to(EditingDomainTransactionService.class);

		bindIContentPartFactory();
		bindDefaultContentChildrenProvider();
		bindSpecificContentChildrenProviders();

		bindScope();

		bindStyleAdapters();
		bindContentPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), BaseContentPart.class));
		bindConnectionAdapters(AdapterMaps.getAdapterMapBinder(binder(), ConnectionContentPart.class));

		bindIContentPartProvider();

		binder().bind(IRootPart.class).to(NotationDiagramRootPart.class);

		bindAnchorageService();

		bindAnchorProviders();
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
		bindTextAdapter(mapBinder);
	}

	protected void bindConnectionAdapters(MapBinder<AdapterKey<?>, Object> mapBinder) {
		mapBinder.addBinding(AdapterKey.defaultRole()).to(GMFConnectionService.class);
	}

	protected void bindAnchorageService() {
		// binder().bind(AnchorageService.class).to(GMFConnectionService.class);
	}

	protected void bindStyleAdapters() {

		// FIXME Find a proper way to override generic adapters in more specific classes
		// Currently, we use this "fallbackRole", but this can only work on one level

		// Install a generic style provider on all parts (With a custom role so it's not
		// the default if another one is present)
		AdapterMaps.getAdapterMapBinder(binder(), BaseContentPart.class).addBinding(AdapterRoles.fallbackRole())
				.to(NotationStyleService.class);

		// Override the StyleService just for the NodeContentPart
		AdapterMaps.getAdapterMapBinder(binder(), NodeContentPart.class).addBinding(AdapterKey.defaultRole())
				.to(ShapeStyleProvider.class);

		AdapterMaps.getAdapterMapBinder(binder(), DiagramContentPart.class).addBinding(AdapterKey.defaultRole())
				.to(DiagramStyleProvider.class);

		AdapterMaps.getAdapterMapBinder(binder(), AbstractLabelContentPart.class).addBinding(AdapterKey.defaultRole())
				.to(NotationLabelStyleProvider.class);
		AdapterMaps.getAdapterMapBinder(binder(), CompartmentContentPart.class).addBinding(AdapterKey.defaultRole())
				.to(DecorationNodeStyleProvider.class);
		AdapterMaps.getAdapterMapBinder(binder(), ConnectionContentPart.class).addBinding(AdapterKey.defaultRole())
				.to(ConnectorStyleProvider.class);
	}

	static class InstallAdaptersBehavior extends AbstractBehavior {
		@Override
		protected void doActivate() {
			super.doActivate();

			BaseContentPart<?, ?> host = (BaseContentPart<?, ?>) getHost();
			if (host.getContent() instanceof View) {
				View view = (View) host.getContent();
				AdapterUtil.setModelAdapters(view, findSemanticElement((BaseContentPart<View, ?>) host), host);
			}
		}

		protected EObject findSemanticElement(BaseContentPart<View, ?> host) {
			final EObject element = host.getContent().getElement();
			if (element == null) {
				if (this instanceof IPrimaryContentPart) {
					return null; // Do not go beyond the Primary part
				}

				final BaseContentPart<View, ? extends Node> parent = host.getParentBaseContentPart();
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

	protected void bindTextAdapter(MapBinder<AdapterKey<?>, Object> mapBinder) {
		mapBinder.addBinding(AdapterKey.defaultRole()).to(ParserTextAdapter.class);
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
		AdapterMaps.getAdapterMapBinder(binder(), BaseContentPart.class).addBinding(AdapterRoles.fallbackRole())
				.to(new TypeLiteral<ContentChildrenAdapter<View>>() {
				});

		binder().bind(new TypeLiteral<ContentChildrenAdapter<View>>() {
		}).to(NotationContentChildrenProvider.class);
	}

	protected void bindSpecificContentChildrenProviders() {
		// Sub-modules may override for each specific ContentPart. Otherwise,
		// #bindDefaultContentChildrenProvider will be used.
		// Example:
		// AdapterMaps.getAdapterMapBinder(binder(),
		// MySpecificContentPart.class).addBinding(AdapterKey.defaultRole())
		// .to(MySpecificContentChildrenProvider.class);
	}

	protected abstract void bindIContentPartProvider();
}
