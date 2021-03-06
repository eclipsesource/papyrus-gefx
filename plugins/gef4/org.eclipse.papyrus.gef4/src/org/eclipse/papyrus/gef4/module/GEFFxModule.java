/*****************************************************************************
 * Copyright (c) 2015-2017 CEA LIST, EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - add Affixed Label Move On Drag Policy
 *  Camille Letavernier (EclipseSource) cletavernier@eclipsesource.com - Migrate to Oxygen
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.module;

import java.util.Optional;

import javax.inject.Singleton;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.common.adapt.inject.AdapterMaps;
import org.eclipse.gef.mvc.fx.MvcFxModule;
import org.eclipse.gef.mvc.fx.behaviors.ContentBehavior;
import org.eclipse.gef.mvc.fx.behaviors.GridBehavior;
import org.eclipse.gef.mvc.fx.behaviors.HoverBehavior;
import org.eclipse.gef.mvc.fx.behaviors.SelectionBehavior;
import org.eclipse.gef.mvc.fx.domain.HistoricizingDomain;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.gestures.IHandlerResolver;
import org.eclipse.gef.mvc.fx.handlers.HoverOnHoverHandler;
import org.eclipse.gef.mvc.fx.parts.DefaultSelectionFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.DefaultSelectionHandlePartFactory;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.providers.ShapeBoundsProvider;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.papyrus.gef4.behavior.ChangeBoundsBehavior;
import org.eclipse.papyrus.gef4.behavior.ConnectionCreationBehavior;
import org.eclipse.papyrus.gef4.behavior.CreationBehavior;
import org.eclipse.papyrus.gef4.behavior.ElementSelectionBehavior;
import org.eclipse.papyrus.gef4.feedback.ChangeBoundsFeedbackPartFactory;
import org.eclipse.papyrus.gef4.feedback.ConnectionCreationFeedbackPartFactory;
import org.eclipse.papyrus.gef4.feedback.CreationFeedbackPartFactory;
import org.eclipse.papyrus.gef4.gestures.ToolHandlerResolver;
import org.eclipse.papyrus.gef4.handle.SelectionHandleFactory;
import org.eclipse.papyrus.gef4.handlers.MarqueeOnDragHandler;
import org.eclipse.papyrus.gef4.handlers.SelectOnClickHandler;
import org.eclipse.papyrus.gef4.history.EmptyOperationHistory;
import org.eclipse.papyrus.gef4.layout.Locator;
import org.eclipse.papyrus.gef4.model.ChangeBoundsModel;
import org.eclipse.papyrus.gef4.parts.AffixedLabelContentPart;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.parts.CompartmentContentPart;
import org.eclipse.papyrus.gef4.parts.ConnectionContentPart;
import org.eclipse.papyrus.gef4.parts.ContainerContentPart;
import org.eclipse.papyrus.gef4.parts.DiagramContentPart;
import org.eclipse.papyrus.gef4.parts.IPrimaryContentPart;
import org.eclipse.papyrus.gef4.parts.LabelContentPart;
import org.eclipse.papyrus.gef4.provider.ConnectionOutlineProvider;
import org.eclipse.papyrus.gef4.provider.HoverHandlePartFactory;
import org.eclipse.papyrus.gef4.provider.LabelOutlineProvider;
import org.eclipse.papyrus.gef4.services.AnchorageService;
import org.eclipse.papyrus.gef4.services.ConnectionService;
import org.eclipse.papyrus.gef4.services.HelperProvider;
import org.eclipse.papyrus.gef4.services.HelperProviderParticipant;
import org.eclipse.papyrus.gef4.services.ImageService;
import org.eclipse.papyrus.gef4.services.LabelService;
import org.eclipse.papyrus.gef4.services.impl.EmptyImageService;
import org.eclipse.papyrus.gef4.services.impl.HelperProviderImpl;
import org.eclipse.papyrus.gef4.services.style.CompartmentStyleService;
import org.eclipse.papyrus.gef4.services.style.EdgeStyleService;
import org.eclipse.papyrus.gef4.services.style.LabelStyleService;
import org.eclipse.papyrus.gef4.services.style.StyleService;
import org.eclipse.papyrus.gef4.tools.DefaultToolManager;
import org.eclipse.papyrus.gef4.tools.ToolManager;

import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;

public class GEFFxModule extends MvcFxModule {

	public static final double DEFAULT_GEFX_PRIORITY = Double.MIN_VALUE;

	@Override
	protected void configure() {
		super.configure();

		bindContentPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), BaseContentPart.class));
		bindNodePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), ContainerContentPart.class));
		bindConnectionPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), ConnectionContentPart.class));
		bindPrimaryPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), IPrimaryContentPart.class));
		bindCompartmentPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), CompartmentContentPart.class));
		bindLabelPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), LabelContentPart.class));
		bindAffixedLabelPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), AffixedLabelContentPart.class));
		bindContentAndRootPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), BaseContentPart.class));
		bindContentAndRootPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), IRootPart.class));

		bindDiagramPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), DiagramContentPart.class));

		bindBoundsBehavior();
		bindBoundsModel();

		bindCreationBehavior();

		bindToolManager();


		// binder().bind(new TypeLiteral<IHandlePartFactory>() {
		// }).to(SelectionHandlePartFactory.class)
		// .in(AdaptableScopes.typed(IViewer.class));


		// binder().bind(new TypeLiteral<IFeedbackPartFactory>() {
		// }).to(BoundsFeedbackPartFactory.class)
		// .in(AdaptableScopes.typed(IViewer.class));

		bindLocators();
		bindLabelService();
		bindImageService();
		bindStyleServices();
		bindEdgeStyleServices();
		bindCompartmentStyleServices();
		bindLabelStyleServices();
		bindConnectionService();
		bindAnchorageService();
	}

	protected void bindContentAndRootPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole())
				.to(CreationBehavior.class);
	}

	@Override
	protected void bindSelectionHandlePartFactoryAsContentViewerAdapter(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(AdapterKey
						.role(SelectionBehavior.SELECTION_HANDLE_PART_FACTORY))
				.to(SelectionHandleFactory.class);
	}

	@Override
	protected void bindSelectionFeedbackPartFactoryAsContentViewerAdapter(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(AdapterKey
						.role(SelectionBehavior.SELECTION_FEEDBACK_PART_FACTORY))
				.to(DefaultSelectionFeedbackPartFactory.class);
	}

	@Override
	protected void bindIHandlerResolver() {
		binder().bind(IHandlerResolver.class).to(ToolHandlerResolver.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.mvc.fx.MvcFxModule#bindIDomainAdapters(com.google.inject.multibindings.MapBinder)
	 */
	@Override
	protected void bindIDomainAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindIDomainAdapters(adapterMapBinder);
	}

	protected void bindToolPolicies(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ActiveToolPolicy.class);
	}

	protected void bindToolManager() {
		binder().bind(ToolManager.class).to(DefaultToolManager.class).in(Singleton.class);
		AdapterMaps.getAdapterMapBinder(binder(), IViewer.class).addBinding(AdapterKey.defaultRole()).to(ToolManager.class);
	}

	protected void bindImageService() {
		binder().bind(new TypeLiteral<HelperProvider<ImageService>>() {
			// Type Literal
		}).to(new TypeLiteral<HelperProviderImpl<ImageService>>() {
			// Type Literal
		}).in(Singleton.class);

		Multibinder<HelperProviderParticipant<ImageService>> imageProviders = Multibinder.newSetBinder(binder(),
				new TypeLiteral<HelperProviderParticipant<ImageService>>() {
					// Type literal
				});

		imageProviders.addBinding().toInstance(new HelperProviderParticipant<ImageService>() {

			private ImageService noImageProvider = new EmptyImageService();

			@Override
			public ImageService get(IVisualPart<?> part) {
				return noImageProvider;
			}

			@Override
			public double getPriority(IVisualPart<?> part) {
				return DEFAULT_GEFX_PRIORITY;
			}
		});
	}

	protected void bindLabelService() {
		binder().bind(new TypeLiteral<HelperProvider<LabelService>>() {
			// Type Literal
		}).to(new TypeLiteral<HelperProviderImpl<LabelService>>() {
			// Type Literal
		}).in(Singleton.class);

		Multibinder<HelperProviderParticipant<LabelService>> labelProviders = Multibinder.newSetBinder(binder(),
				new TypeLiteral<HelperProviderParticipant<LabelService>>() {
					// Type literal
				});

		labelProviders.addBinding().toInstance(new HelperProviderParticipant<LabelService>() {

			private LabelService noLabelProvider = () -> "<Missing label provider>";

			@Override
			public LabelService get(IVisualPart<?> part) {
				return noLabelProvider;
			}

			@Override
			public double getPriority(IVisualPart<?> part) {
				return DEFAULT_GEFX_PRIORITY;
			}
		});
	}

	protected void bindAnchorageService() {
		binder().bind(new TypeLiteral<HelperProvider<AnchorageService>>() {
			// Type literal
		}).to(new TypeLiteral<HelperProviderImpl<AnchorageService>>() {
			// Type literal
		}).in(Singleton.class);
	}

	private void bindConnectionService() {
		binder().bind(new TypeLiteral<HelperProvider<ConnectionService>>() {
			// Type literal
		}).to(new TypeLiteral<HelperProviderImpl<ConnectionService>>() {
			// Type literal
		}).in(Singleton.class);
	}

	private void bindCompartmentStyleServices() {
		binder().bind(new TypeLiteral<HelperProvider<CompartmentStyleService>>() {
			// Type literal
		}).to(new TypeLiteral<HelperProviderImpl<CompartmentStyleService>>() {
			// Type literal
		}).in(Singleton.class);
	}

	private void bindLabelStyleServices() {
		binder().bind(new TypeLiteral<HelperProvider<LabelStyleService>>() {
			// Type literal
		}).to(new TypeLiteral<HelperProviderImpl<LabelStyleService>>() {
			// Type literal
		}).in(Singleton.class);
	}

	private void bindEdgeStyleServices() {
		binder().bind(new TypeLiteral<HelperProvider<EdgeStyleService>>() {
			// Type literal
		}).to(new TypeLiteral<HelperProviderImpl<EdgeStyleService>>() {
			// Type literal
		}).in(Singleton.class);
	}

	private void bindStyleServices() {
		binder().bind(new TypeLiteral<HelperProvider<StyleService>>() {
			// Type literal
		}).to(new TypeLiteral<HelperProviderImpl<StyleService>>() {
			// Type literal
		}).in(Singleton.class);
	}

	protected void bindLocators() {
		binder().bind(new TypeLiteral<HelperProvider<Optional<Locator>>>() {
			// Type literal
		}).to(new TypeLiteral<HelperProviderImpl<Optional<Locator>>>() {
			// Type literal
		}).in(Singleton.class);

		Multibinder<HelperProviderParticipant<Optional<Locator>>> participants = Multibinder.newSetBinder(binder(), new TypeLiteral<HelperProviderParticipant<Optional<Locator>>>() {
			// Type literal
		});

		participants.addBinding().to(EmptyLocatorParticipant.class);
	}

	static class EmptyLocatorParticipant implements HelperProviderParticipant<Optional<Locator>> {

		@Override
		public Optional<Locator> get(IVisualPart<?> part) {
			return Optional.empty();
		}

		@Override
		public double getPriority(IVisualPart<?> part) {
			return DEFAULT_GEFX_PRIORITY;
		}

	}

	@Override
	protected void bindIViewerAdaptersForContentViewer(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindIViewerAdaptersForContentViewer(adapterMapBinder);

		adapterMapBinder.addBinding(AdapterKey.defaultRole())
				.to(ChangeBoundsModel.class);

		bindBoundsFeedbackPartFactoryAsContentViewerAdapter(adapterMapBinder);
		bindCreationFeedbackPartFactoryAsContentViewerAdapter(adapterMapBinder);
		bindConnectionCreationFeedbackPartFactoryAsContentViewerAdapter(adapterMapBinder);
	}

	protected void bindCreationFeedbackPartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(AdapterKey
						.role(CreationBehavior.CREATION_ROLE))
				.to(CreationFeedbackPartFactory.class);
	}

	protected void bindConnectionCreationFeedbackPartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(AdapterKey
						.role(ConnectionCreationBehavior.CONNECTION_CREATION_ROLE))
				.to(ConnectionCreationFeedbackPartFactory.class);
	}

	protected void bindBoundsFeedbackPartFactoryAsContentViewerAdapter(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(AdapterKey
						.role(ChangeBoundsBehavior.BOUNDS_ROLE))
				.to(ChangeBoundsFeedbackPartFactory.class);

	}

	@Override
	protected void bindAbstractContentPartAdapters(final MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindAbstractContentPartAdapters(adapterMapBinder);

		adapterMapBinder.addBinding(AdapterKey.defaultRole())
				.to(ChangeBoundsBehavior.class);

		adapterMapBinder.addBinding(AdapterKey.defaultRole())
				.to(HoverOnHoverHandler.class);
	}

	@Override
	protected void bindIDomain() {
		// FIXME This is a workaround to avoid accidentally instantiating several Domains/Viewers/Selection models
		// however, we should clarify the scope of the selection model in case of multiple viewers/domains, for
		// injected elements that are not bound to a Part (e.g. a Palette).
		// Multiple domains/viewers in the same injector are not a use case yet, so using Singletons is the simplest solution
		binder().bind(IDomain.class).to(HistoricizingDomain.class).in(Singleton.class);
	}

	@Override
	protected void bindIRootPartAdaptersForContentViewer(final MapBinder<AdapterKey<?>, Object> adapterMapBinder) {

		bindFocusAndSelectOnClickHandlerAsIRootPartAdapter(adapterMapBinder);

		adapterMapBinder
				.addBinding(
						AdapterKey.defaultRole())
				.to(MarqueeOnDragHandler.class);

		bindHoverBehaviorAsIRootPartAdapter(adapterMapBinder);

		bindPanOrZoomOnScrollHandlerAsIRootPartAdapter(adapterMapBinder);

		bindChangeViewportPolicyAsIRootPartAdapter(adapterMapBinder);

		// adapterMapBinder
		// .addBinding(AdapterKey.defaultRole())
		// .to(ChangeViewportPolicy.class);

		// register default behaviors
		adapterMapBinder.addBinding(AdapterKey.defaultRole())
				.to(new TypeLiteral<ContentBehavior>() {
				});
		adapterMapBinder.addBinding(AdapterKey.defaultRole())
				.to(new TypeLiteral<ElementSelectionBehavior>() {
				});
		adapterMapBinder.addBinding(AdapterKey.defaultRole())
				.to(GridBehavior.class);
	}

	@Override
	protected void bindHoverHandlePartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// Disable parent

		adapterMapBinder.addBinding(AdapterKey.role(HoverBehavior.HOVER_HANDLE_PART_FACTORY))
				.to(HoverHandlePartFactory.class);
	}

	protected void bindContentPartAdapters(final MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole())
				.to(ConnectionCreationBehavior.class);
	}

	@Override
	protected void bindFocusAndSelectOnClickHandlerAsIRootPartAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(AdapterKey.defaultRole())
				.to(SelectOnClickHandler.class);
	}

	protected void bindConnectionPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(
						AdapterKey.role(DefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER))
				.to(ConnectionOutlineProvider.class);

		adapterMapBinder
				.addBinding(
						AdapterKey.role(DefaultSelectionHandlePartFactory.SELECTION_HANDLES_GEOMETRY_PROVIDER))
				.to(ConnectionOutlineProvider.class);
	}

	protected void bindNodePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// The GeometricOutlineProvider supports most elements, but not FX VBox for example
		// Use ShapeBoundsProvider for Nodes (We mostly use VBoxes...)
		// TODO: Use a common provider that supports both Connection and VBoxes (And others)
		// See org.eclipse.gef4.fx.utils.NodeUtils.getGeometricOutline(Node)
		adapterMapBinder
				.addBinding(
						AdapterKey.role(DefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER))
				.to(ShapeBoundsProvider.class);

		adapterMapBinder
				.addBinding(
						AdapterKey.role(DefaultSelectionHandlePartFactory.SELECTION_HANDLES_GEOMETRY_PROVIDER))
				.to(ShapeBoundsProvider.class);
	}

	@Override
	protected void bindHoverOnHoverHandlerAsAbstractHandlePartAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// Disable hover on handles
	}

	protected void bindLabelPartAdapters(final MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(
						AdapterKey.role(DefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER))
				.to(LabelOutlineProvider.class);
	}

	protected void bindDiagramPartAdapters(final MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// Nothing
	}

	protected void bindCompartmentPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		bindCollapseHandleProviderAsCompartmentPartAdapter(adapterMapBinder);
	}

	/**
	 * @param adapterMapBinder
	 */
	protected void bindCollapseHandleProviderAsCompartmentPartAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// XXX Move to GMF Module
		// adapterMapBinder.addBinding(AdapterKey.role(HoverHandlePartFactory.ROLE))
		// .to(CollapseHandlePartProvider.class);
	}

	protected void bindHoverBehaviorAsCompartmentPartAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole())
				.to(HoverBehavior.class);
	}

	protected void bindPrimaryPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(
						AdapterKey.defaultRole())
				.to(SelectOnClickHandler.class);
	}

	protected void bindAffixedLabelPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(
						AdapterKey.defaultRole())
				.to(SelectOnClickHandler.class);
	}

	protected void bindBoundsBehavior() {
		binder().bind(ChangeBoundsBehavior.class);
	}

	protected void bindBoundsModel() {
		binder().bind(ChangeBoundsModel.class);
	}

	protected void bindCreationBehavior() {
		binder().bind(CreationBehavior.class);
	}

	@Override
	protected void bindIUndoContext() {
		super.bindIUndoContext();
	}

	@Override
	protected void bindIOperationHistory() {
		// Transactions are handled separately in Papyrus. Do not use the operation history for graphical operations, to avoid conflicts
		binder().bind(IOperationHistory.class).toInstance(EmptyOperationHistory.INSTANCE);
	}

}
