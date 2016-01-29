/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - add Affixed Label Move On Drag Policy
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.module;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.gef4.common.adapt.AdapterKey;
import org.eclipse.gef4.common.adapt.inject.AdaptableScopes;
import org.eclipse.gef4.common.adapt.inject.AdapterMaps;
import org.eclipse.gef4.mvc.behaviors.ContentBehavior;
import org.eclipse.gef4.mvc.behaviors.HoverBehavior;
import org.eclipse.gef4.mvc.behaviors.SelectionBehavior;
import org.eclipse.gef4.mvc.fx.MvcFxModule;
import org.eclipse.gef4.mvc.fx.behaviors.FXFocusBehavior;
import org.eclipse.gef4.mvc.fx.behaviors.FXGridBehavior;
import org.eclipse.gef4.mvc.fx.domain.FXDomain;
import org.eclipse.gef4.mvc.fx.parts.AbstractFXHandlePart;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultSelectionFeedbackPartFactory;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultSelectionHandlePartFactory;
import org.eclipse.gef4.mvc.fx.policies.FXChangeViewportPolicy;
import org.eclipse.gef4.mvc.fx.policies.FXHoverOnHoverPolicy;
import org.eclipse.gef4.mvc.fx.providers.GeometricOutlineProvider;
import org.eclipse.gef4.mvc.fx.providers.ShapeBoundsProvider;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.parts.IContentPartFactory;
import org.eclipse.gef4.mvc.parts.IFeedbackPartFactory;
import org.eclipse.gef4.mvc.parts.IHandlePartFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.behavior.ChangeBoundsBehavior;
import org.eclipse.papyrus.gef4.editor.Palette;
import org.eclipse.papyrus.gef4.handle.CollapseHandlePart;
import org.eclipse.papyrus.gef4.history.EmptyOperationHistory;
import org.eclipse.papyrus.gef4.model.ChangeBoundsModel;
import org.eclipse.papyrus.gef4.parts.AffixedLabelContentPart;
import org.eclipse.papyrus.gef4.parts.ConnectionContentPart;
import org.eclipse.papyrus.gef4.parts.ContainerContentPart;
import org.eclipse.papyrus.gef4.parts.DiagramContentPart;
import org.eclipse.papyrus.gef4.parts.DiagramRootPart;
import org.eclipse.papyrus.gef4.parts.IPrimaryContentPart;
import org.eclipse.papyrus.gef4.parts.NotationContentPart;
import org.eclipse.papyrus.gef4.policies.AffixedLabelMoveOnDragPolicy;
import org.eclipse.papyrus.gef4.policies.CollapseOnClickPolicy;
import org.eclipse.papyrus.gef4.policies.FocusAndSelectOnClickPolicy;
import org.eclipse.papyrus.gef4.policies.MarqueeOnDragPolicy;
import org.eclipse.papyrus.gef4.policies.MoveOnDragPolicy;
import org.eclipse.papyrus.gef4.policies.ResizeOnDragPolicy;
import org.eclipse.papyrus.gef4.provider.FeedbackPartFactory;
import org.eclipse.papyrus.gef4.provider.HandlePartFactory;
import org.eclipse.papyrus.gef4.provider.IContentChildrenProvider;
import org.eclipse.papyrus.gef4.provider.NotationContentChildrenProvider;
import org.eclipse.papyrus.gef4.provider.ProviderBasedContentPartFactory;

import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;

import javafx.scene.Node;

public abstract class GEFFxModule extends MvcFxModule {

	// {
	// binder().bind(new TypeLiteral<IContentPartFactory<Node>>() {
	// }).to(ContentPartFactory.class)
	// .in(AdaptableScopes.typed(FXViewer.class));
	// }

	@Override
	protected void bindContentViewerAdapters(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindContentViewerAdapters(adapterMapBinder);

		adapterMapBinder.addBinding(AdapterKey.defaultRole())
				.to(ChangeBoundsModel.class);
	}

	@Override
	protected void bindAbstractFXContentPartAdapters(final MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindAbstractFXContentPartAdapters(adapterMapBinder);

		adapterMapBinder.addBinding(AdapterKey.defaultRole())
				.to(ChangeBoundsBehavior.class);

		adapterMapBinder.addBinding(AdapterKey.defaultRole())
				.to(FXHoverOnHoverPolicy.class);
	}

	@Override
	protected void bindContentViewerRootPartAdapters(final MapBinder<AdapterKey<?>, Object> adapterMapBinder) {

		bindFocusAndSelectOnClickPolicyAsFXRootPartAdapter(adapterMapBinder);

		adapterMapBinder
				.addBinding(
						AdapterKey.defaultRole())
				.to(MarqueeOnDragPolicy.class);

		bindFXPanOrZoomOnScrollPolicyAsFXRootPartAdapter(adapterMapBinder);

		adapterMapBinder
				.addBinding(AdapterKey.defaultRole())
				.to(FXChangeViewportPolicy.class);

		// register default behaviors
		adapterMapBinder.addBinding(AdapterKey.defaultRole())
				.to(new TypeLiteral<ContentBehavior<Node>>() {
				});
		adapterMapBinder.addBinding(AdapterKey.defaultRole())
				.to(new TypeLiteral<SelectionBehavior<Node>>() {
				});
		adapterMapBinder.addBinding(AdapterKey.defaultRole())
				.to(FXGridBehavior.class);
	}

	protected void bindFXHandlePartAdapters(final MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(AdapterKey.defaultRole()).to(ResizeOnDragPolicy.class); // FIXME this shouldn't be installed on all handle parts (e.g. this will be installed on connection's bendpoints and anchors)
	}

	@Override
	protected void bindHoverHandlePartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(AdapterKey
						.role(HoverBehavior.HOVER_HANDLE_PART_FACTORY))
				.to(HandlePartFactory.class);
	}

	@Override
	protected void bindSelectionHandlePartFactoryAsContentViewerAdapter(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(AdapterKey
						.role(SelectionBehavior.SELECTION_HANDLE_PART_FACTORY))
				.to(FXDefaultSelectionHandlePartFactory.class);
	}

	@Override
	protected void bindSelectionFeedbackPartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(AdapterKey
						.role(SelectionBehavior.SELECTION_FEEDBACK_PART_FACTORY))
				.to(FeedbackPartFactory.class);
	}

	@Override
	protected void bindHoverFeedbackPartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(AdapterKey
						.role(HoverBehavior.HOVER_FEEDBACK_PART_FACTORY))
				.to(FeedbackPartFactory.class);
	}

	@Override
	protected void bindFocusFeedbackPartFactoryAsContentViewerAdapter(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(AdapterKey
						.role(FXFocusBehavior.FOCUS_FEEDBACK_PART_FACTORY))
				.to(FeedbackPartFactory.class);
	}

	protected void bindContentPartAdapters(final MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// Nothing (Most binding moved to PrimaryPartAdapter due to the new propagation mechanism of policies in GEF4)
	}

	@Override
	protected void bindFocusAndSelectOnClickPolicyAsFXRootPartAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.role("0"))
				.to(FocusAndSelectOnClickPolicy.class);
	}

	protected void bindConnectionPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(
						AdapterKey.role(FXDefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER))
				.to(GeometricOutlineProvider.class);

		adapterMapBinder
				.addBinding(
						AdapterKey.role(FXDefaultSelectionHandlePartFactory.SELECTION_HANDLES_GEOMETRY_PROVIDER))
				.to(GeometricOutlineProvider.class);
	}

	protected void bindNodePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// The GeometricOutlineProvider supports most elements, but not FX VBox for example
		// Use ShapeBoundsProvider for Nodes (We mostly use VBoxes...)
		// TODO: Use a common provider that supports both Connection and VBoxes (And others)
		// See org.eclipse.gef4.fx.utils.NodeUtils.getGeometricOutline(Node)
		adapterMapBinder
				.addBinding(
						AdapterKey.role(FXDefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER))
				.to(ShapeBoundsProvider.class);

		adapterMapBinder
				.addBinding(
						AdapterKey.role(FXDefaultSelectionHandlePartFactory.SELECTION_HANDLES_GEOMETRY_PROVIDER))
				.to(ShapeBoundsProvider.class);
	}

	protected void bindCollapseHandlePartAdapters(final MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(AdapterKey.defaultRole())
				.to(CollapseOnClickPolicy.class);
	}

	protected void bindAffixedLabelContentPartAdapters(final MapBinder<AdapterKey<?>, Object> adapterMapBinder) {

		adapterMapBinder.addBinding(
				AdapterKey.role("AffixedLabel"))// $NON-NLS-1$
				.to(AffixedLabelMoveOnDragPolicy.class);
	}

	protected void bindDiagramPartAdapters(final MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// adapterMapBinder
		// .addBinding(
		// AdapterKey
		// .get(new TypeToken<Provider<IGeometry>>() {
		// },
		// FXDefaultFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER))
		// .toProvider(Providers.of(null));
	}

	// TODO The SelectionProvider now takes the Viewer as parameter (But it is not Injected). This makes injection non-trivial. The SelectionProvider is currently instantiated in the GEFEditor
	// protected void bindSelectionProvider() {
	// binder().bind(ISelectionProvider.class).to(
	// ViewerSelectionProvider.class);
	// }

	@Override
	protected void bindFXRootPartAsContentViewerAdapter(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {

		adapterMapBinder
				.addBinding(AdapterKey.role(FXDomain.CONTENT_VIEWER_ROLE))
				.to(DiagramRootPart.class).in(AdaptableScopes.typed(FXViewer.class));
	}

	@Override
	protected void configure() {
		super.configure();

		bindIContentPartFactory();
		bindIVisualPartProvider();

		// bind selection provider
		// bindSelectionProvider();


		bindContentPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), NotationContentPart.class));
		bindNodePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), ContainerContentPart.class));
		bindConnectionPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), ConnectionContentPart.class));
		bindPrimaryPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), IPrimaryContentPart.class));

		// define specific policy for affixed Label
		bindAffixedLabelContentPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), AffixedLabelContentPart.class));

		bindDiagramPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), DiagramContentPart.class));

		bindFXHandlePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), AbstractFXHandlePart.class));

		bindCollapseHandlePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), CollapseHandlePart.class));

		bindBoundsBehavior();

		bindBoundsModel();

		bindPalette();

		bindDefaultContentChildrenProvider();





		// Generic handle part factory binding for unnamed injects
		binder().bind(new TypeLiteral<IHandlePartFactory<Node>>() {
		}).to(HandlePartFactory.class)
				.in(AdaptableScopes.typed(FXViewer.class));


		// Generic feedback part factory binding for unnamed injects
		binder().bind(new TypeLiteral<IFeedbackPartFactory<Node>>() {
		}).to(FeedbackPartFactory.class)
				.in(AdaptableScopes.typed(FXViewer.class));
	}

	protected void bindPrimaryPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(
						AdapterKey.defaultRole())
				.to(FocusAndSelectOnClickPolicy.class);

		adapterMapBinder
				.addBinding(
						AdapterKey.defaultRole())
				.to(MoveOnDragPolicy.class);
	}

	protected void bindPalette() {
		binder().bind(Palette.class);
	}

	protected void bindBoundsBehavior() {
		binder().bind(ChangeBoundsBehavior.class);
	}

	protected void bindBoundsModel() {
		binder().bind(ChangeBoundsModel.class);
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

	protected abstract void bindIVisualPartProvider();

	protected void bindIContentPartFactory() {
		binder().bind(new TypeLiteral<IContentPartFactory<Node>>() {
		}).to(ProviderBasedContentPartFactory.class);
	}

	protected void bindDefaultContentChildrenProvider() {
		binder().bind(new TypeLiteral<IContentChildrenProvider<View>>() {
		}).to(NotationContentChildrenProvider.class);
	}

}
