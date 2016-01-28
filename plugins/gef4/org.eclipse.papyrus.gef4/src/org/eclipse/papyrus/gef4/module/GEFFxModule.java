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
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.gef4.common.adapt.AdapterKey;
import org.eclipse.gef4.common.adapt.inject.AdaptableScopes;
import org.eclipse.gef4.common.adapt.inject.AdapterMaps;
import org.eclipse.gef4.mvc.behaviors.ContentBehavior;
import org.eclipse.gef4.mvc.behaviors.HoverBehavior;
import org.eclipse.gef4.mvc.behaviors.SelectionBehavior;
import org.eclipse.gef4.mvc.fx.MvcFxModule;
import org.eclipse.gef4.mvc.fx.behaviors.FXGridBehavior;
import org.eclipse.gef4.mvc.fx.parts.AbstractFXHandlePart;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultSelectionFeedbackPartFactory;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultSelectionHandlePartFactory;
import org.eclipse.gef4.mvc.fx.parts.FXRootPart;
import org.eclipse.gef4.mvc.fx.policies.FXChangeViewportPolicy;
import org.eclipse.gef4.mvc.fx.policies.FXHoverOnHoverPolicy;
import org.eclipse.gef4.mvc.fx.providers.GeometricOutlineProvider;
import org.eclipse.gef4.mvc.fx.providers.ShapeBoundsProvider;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.parts.IFeedbackPartFactory;
import org.eclipse.gef4.mvc.parts.IHandlePartFactory;
import org.eclipse.gef4.mvc.parts.IRootPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.EditingDomainUndoContext;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.papyrus.gef4.behavior.ChangeBoundsBehavior;
import org.eclipse.papyrus.gef4.editor.Palette;
import org.eclipse.papyrus.gef4.editor.ViewerSelectionProvider;
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

import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.name.Names;

import javafx.scene.Node;

public abstract class GEFFxModule extends MvcFxModule {

	protected final Diagram diagram;

	public GEFFxModule(final Diagram diagram) {
		this.diagram = diagram;
	}

	protected abstract void bindIContentPartFactory();
	// {
	// binder().bind(new TypeLiteral<IContentPartFactory<Node>>() {
	// }).to(ContentPartFactory.class)
	// .in(AdaptableScopes.typed(FXViewer.class));
	// }

	@Override
	protected void bindFXViewerAdapters(final com.google.inject.multibindings.MapBinder<org.eclipse.gef4.common.adapt.AdapterKey<?>, Object> adapterMapBinder) {
		super.bindFXViewerAdapters(adapterMapBinder);

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
	protected void bindFXRootPartAdapters(final MapBinder<AdapterKey<?>, Object> adapterMapBinder) {

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
	protected void bindIHandlePartFactories() {

		// Parent module uses named injects.
		binder().bind(new TypeLiteral<IHandlePartFactory<Node>>() {
		}).annotatedWith(
				Names.named(SelectionBehavior.PART_FACTORIES_BINDING_NAME))
				.to(HandlePartFactory.class)
				.in(AdaptableScopes.typed(FXViewer.class));

		binder().bind(new TypeLiteral<IHandlePartFactory<Node>>() {
		}).annotatedWith(Names.named(HoverBehavior.PART_FACTORIES_BINDING_NAME))
				.to(HandlePartFactory.class)
				.in(AdaptableScopes.typed(FXViewer.class));

		// Generic handle part factory binding for unnamed injects
		binder().bind(new TypeLiteral<IHandlePartFactory<Node>>() {
		}).to(HandlePartFactory.class)
				.in(AdaptableScopes.typed(FXViewer.class));
	}

	@Override
	protected void bindIFeedbackPartFactories() {
		// Parent module uses named injects.
		binder().bind(new TypeLiteral<IFeedbackPartFactory<Node>>() {
		}).annotatedWith(
				Names.named(SelectionBehavior.PART_FACTORIES_BINDING_NAME))
				.to(FeedbackPartFactory.class)
				.in(AdaptableScopes.typed(FXViewer.class));
		binder().bind(new TypeLiteral<IFeedbackPartFactory<Node>>() {
		}).annotatedWith(Names.named(HoverBehavior.PART_FACTORIES_BINDING_NAME))
				.to(FeedbackPartFactory.class)
				.in(AdaptableScopes.typed(FXViewer.class));

		// Generic feedback part factory binding for unnamed injects
		binder().bind(new TypeLiteral<IFeedbackPartFactory<Node>>() {
		}).to(FeedbackPartFactory.class)
				.in(AdaptableScopes.typed(FXViewer.class));
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

	protected void bindSelectionProvider() {
		binder().bind(ISelectionProvider.class).to(
				ViewerSelectionProvider.class);
	}

	/**
	 * Binds {@link FXRootPart} to {@link IRootPart}, parameterized by
	 * {@link Node}, in adaptable scope of {@link FXViewer}.
	 */
	@Override
	protected void bindIRootPart() {
		binder().bind(new TypeLiteral<IRootPart<Node, ? extends Node>>() {
		}).to(DiagramRootPart.class).in(AdaptableScopes.typed(FXViewer.class));
	}

	// /**
	// * Binds {@link FXRootPart} to {@link IRootPart}, parameterized by
	// * {@link Node}, in adaptable scope of {@link FXViewer}.
	// */
	// @Override
	// protected void bindIRootPart() {
	// binder().bind(new TypeLiteral<IRootPart<Node, ? extends Node>>() {
	// }).to(PapyrusRootPart.class).in(AdaptableScopes.typed(FXViewer.class));
	// }

	@Override
	protected void configure() {
		super.configure();

		bindIContentPartFactory();

		// bind selection provider
		bindSelectionProvider();


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
		if (true) {
			return;
		}
		binder().bind(IUndoContext.class).toProvider(new Provider<IUndoContext>() {
			@Override
			public IUndoContext get() {
				return new EditingDomainUndoContext(AdapterFactoryEditingDomain.getEditingDomainFor(diagram));
			}
		});
	}

	@Override
	protected void bindIOperationHistory() {
		// Transactions are handled separately in Papyrus. Do not use the operation history for graphical operations, to avoid conflicts
		binder().bind(IOperationHistory.class).toInstance(EmptyOperationHistory.INSTANCE);
	}

}
