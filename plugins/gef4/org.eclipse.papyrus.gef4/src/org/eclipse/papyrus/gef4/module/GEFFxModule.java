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

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.common.adapt.inject.AdaptableScopes;
import org.eclipse.gef.common.adapt.inject.AdapterMaps;
import org.eclipse.gef.mvc.fx.MvcFxModule;
import org.eclipse.gef.mvc.fx.behaviors.ContentBehavior;
import org.eclipse.gef.mvc.fx.behaviors.GridBehavior;
import org.eclipse.gef.mvc.fx.behaviors.HoverBehavior;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.handlers.HoverOnHoverHandler;
import org.eclipse.gef.mvc.fx.parts.AbstractHandlePart;
import org.eclipse.gef.mvc.fx.parts.DefaultSelectionFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.DefaultSelectionHandlePartFactory;
import org.eclipse.gef.mvc.fx.providers.DefaultAnchorProvider;
import org.eclipse.gef.mvc.fx.providers.GeometricOutlineProvider;
import org.eclipse.gef.mvc.fx.providers.ShapeBoundsProvider;
import org.eclipse.gef.mvc.fx.ui.parts.ISelectionProviderFactory;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.papyrus.gef4.behavior.ChangeBoundsBehavior;
import org.eclipse.papyrus.gef4.behavior.ElementSelectionBehavior;
import org.eclipse.papyrus.gef4.editor.SelectionProviderFactory;
import org.eclipse.papyrus.gef4.feedback.BoundsFeedbackPartFactory;
import org.eclipse.papyrus.gef4.handle.CollapseHandlePart;
import org.eclipse.papyrus.gef4.handlers.AffixedLabelMoveOnDragHandler;
import org.eclipse.papyrus.gef4.handlers.CollapseOnClickHandler;
import org.eclipse.papyrus.gef4.handlers.MarqueeOnDragHandler;
import org.eclipse.papyrus.gef4.handlers.MoveOnDragHandler;
import org.eclipse.papyrus.gef4.handlers.ResizeOnDragHandler;
import org.eclipse.papyrus.gef4.handlers.SelectOnClickHandler;
import org.eclipse.papyrus.gef4.history.EmptyOperationHistory;
import org.eclipse.papyrus.gef4.model.ChangeBoundsModel;
import org.eclipse.papyrus.gef4.palette.Palette;
import org.eclipse.papyrus.gef4.parts.AffixedLabelContentPart;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.parts.CompartmentContentPart;
import org.eclipse.papyrus.gef4.parts.ConnectionContentPart;
import org.eclipse.papyrus.gef4.parts.ContainerContentPart;
import org.eclipse.papyrus.gef4.parts.DiagramContentPart;
import org.eclipse.papyrus.gef4.parts.DiagramRootPart;
import org.eclipse.papyrus.gef4.parts.IPrimaryContentPart;
import org.eclipse.papyrus.gef4.provider.CollapseHandlePartProvider;
import org.eclipse.papyrus.gef4.provider.HoverHandlePartFactory;
import org.eclipse.papyrus.gef4.services.impl.EmptyAnchorageService;
import org.eclipse.papyrus.gef4.tools.DefaultToolManager;
import org.eclipse.papyrus.gef4.tools.ToolManager;

import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;

public class GEFFxModule extends MvcFxModule {

	@Override
	protected void configure() {
		super.configure();

		// bind selection provider
		// bindSelectionProvider();

		bindContentPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), BaseContentPart.class));
		bindNodePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), ContainerContentPart.class));
		bindConnectionPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), ConnectionContentPart.class));
		bindPrimaryPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), IPrimaryContentPart.class));
		bindCompartmentPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), CompartmentContentPart.class));

		// define specific policy for affixed Label
		bindAffixedLabelContentPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), AffixedLabelContentPart.class));

		bindDiagramPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), DiagramContentPart.class));

		bindSelectionProviderFactory();

		bindFXHandlePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), AbstractHandlePart.class));

		bindCollapseHandlePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), CollapseHandlePart.class));

		bindBoundsBehavior();

		bindBoundsModel();

		bindPalette();

		bindToolManager();


		// binder().bind(new TypeLiteral<IHandlePartFactory>() {
		// }).to(SelectionHandlePartFactory.class)
		// .in(AdaptableScopes.typed(IViewer.class));


		// binder().bind(new TypeLiteral<IFeedbackPartFactory>() {
		// }).to(BoundsFeedbackPartFactory.class)
		// .in(AdaptableScopes.typed(IViewer.class));
	}

	@Override
	protected void bindIViewerAdaptersForContentViewer(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindIViewerAdaptersForContentViewer(adapterMapBinder);

		adapterMapBinder.addBinding(AdapterKey.defaultRole())
				.to(ChangeBoundsModel.class);

		bindBoundsFeedbackPartFactoryAsContentViewerAdapter(adapterMapBinder);

	}

	protected void bindBoundsFeedbackPartFactoryAsContentViewerAdapter(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(AdapterKey
						.role(ChangeBoundsBehavior.BOUNDS_ROLE))
				.to(BoundsFeedbackPartFactory.class);

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

	protected void bindFXHandlePartAdapters(final MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(AdapterKey.defaultRole()).to(ResizeOnDragHandler.class); // FIXME this shouldn't be installed on all handle parts (e.g. this will be installed on connection's bendpoints and anchors)
	}

	@Override
	protected void bindHoverHandlePartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// Disable parent

		adapterMapBinder.addBinding(AdapterKey.role(HoverBehavior.HOVER_HANDLE_PART_FACTORY))
				.to(HoverHandlePartFactory.class);
	}

	protected void bindContentPartAdapters(final MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// Most binding moved to PrimaryPartAdapter due to the new propagation mechanism of policies in GEF4

		adapterMapBinder.addBinding(AdapterRoles.fallbackRole()).to(EmptyAnchorageService.class);
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
				.to(GeometricOutlineProvider.class);

		adapterMapBinder
				.addBinding(
						AdapterKey.role(DefaultSelectionHandlePartFactory.SELECTION_HANDLES_GEOMETRY_PROVIDER))
				.to(GeometricOutlineProvider.class);
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

	protected void bindCollapseHandlePartAdapters(final MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(AdapterKey.defaultRole())
				.to(CollapseOnClickHandler.class);
	}

	protected void bindAffixedLabelContentPartAdapters(final MapBinder<AdapterKey<?>, Object> adapterMapBinder) {

		adapterMapBinder.addBinding(
				AdapterKey.role("AffixedLabel"))// $NON-NLS-1$
				.to(AffixedLabelMoveOnDragHandler.class);
	}

	protected void bindDiagramPartAdapters(final MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// Nothing
	}

	protected void bindSelectionProviderFactory() {
		binder().bind(ISelectionProviderFactory.class).to(
				SelectionProviderFactory.class);
	}

	@Override
	protected void bindRootPartAsContentViewerAdapter(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {

		adapterMapBinder
				.addBinding(AdapterKey.role(IDomain.CONTENT_VIEWER_ROLE))
				.to(DiagramRootPart.class).in(AdaptableScopes.typed(IViewer.class));
	}

	protected void bindCompartmentPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// bindHoverBehaviorAsCompartmentPartAdapter(adapterMapBinder);
		bindCollapseHandleProviderAsCompartmentPartAdapter(adapterMapBinder);
	}

	/**
	 * @param adapterMapBinder
	 */
	protected void bindCollapseHandleProviderAsCompartmentPartAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.role(HoverHandlePartFactory.ROLE))
				.to(CollapseHandlePartProvider.class);
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

		adapterMapBinder
				.addBinding(
						AdapterKey.defaultRole())
				.to(MoveOnDragHandler.class);

		adapterMapBinder
				.addBinding(
						AdapterRoles.fallbackRole())
				.to(DefaultAnchorProvider.class);
	}

	protected void bindToolManager() {
		binder().bind(ToolManager.class).to(DefaultToolManager.class);
	}

	protected void bindPalette() {
		binder().bind(Palette.class).toInstance(() -> null);
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

}
