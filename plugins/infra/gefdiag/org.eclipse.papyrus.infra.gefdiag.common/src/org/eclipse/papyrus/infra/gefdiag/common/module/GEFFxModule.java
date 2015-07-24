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
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gefdiag.common.module;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.gef4.common.adapt.AdapterKey;
import org.eclipse.gef4.common.inject.AdaptableScopes;
import org.eclipse.gef4.common.inject.AdapterMaps;
import org.eclipse.gef4.fx.anchors.IFXAnchor;
import org.eclipse.gef4.geometry.planar.IGeometry;
import org.eclipse.gef4.mvc.behaviors.ContentBehavior;
import org.eclipse.gef4.mvc.behaviors.SelectionBehavior;
import org.eclipse.gef4.mvc.fx.MvcFxModule;
import org.eclipse.gef4.mvc.fx.behaviors.FXGridBehavior;
import org.eclipse.gef4.mvc.fx.behaviors.FXViewportBehavior;
import org.eclipse.gef4.mvc.fx.parts.AbstractFXHandlePart;
import org.eclipse.gef4.mvc.fx.parts.ChopBoxAnchorProvider;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultFeedbackPartFactory;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultHandlePartFactory;
import org.eclipse.gef4.mvc.fx.parts.FXRootPart;
import org.eclipse.gef4.mvc.fx.parts.VisualBoundsGeometryProvider;
import org.eclipse.gef4.mvc.fx.policies.FXChangeViewportPolicy;
import org.eclipse.gef4.mvc.fx.policies.FXPanOnScrollPolicy;
import org.eclipse.gef4.mvc.fx.policies.FXZoomOnScrollPolicy;
import org.eclipse.gef4.mvc.fx.tools.FXClickDragTool;
import org.eclipse.gef4.mvc.fx.tools.FXScrollTool;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.parts.IContentPartFactory;
import org.eclipse.gef4.mvc.parts.IFeedbackPartFactory;
import org.eclipse.gef4.mvc.parts.IHandlePartFactory;
import org.eclipse.gef4.mvc.parts.IRootPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.EditingDomainUndoContext;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gefdiag.common.behavior.ChangeBoundsBehavior;
import org.eclipse.papyrus.infra.gefdiag.common.editor.Palette;
import org.eclipse.papyrus.infra.gefdiag.common.editor.ViewerSelectionProvider;
import org.eclipse.papyrus.infra.gefdiag.common.geometry.BoundsGeometryProvider;
import org.eclipse.papyrus.infra.gefdiag.common.history.EmptyOperationHistory;
import org.eclipse.papyrus.infra.gefdiag.common.model.ChangeBoundsModel;
import org.eclipse.papyrus.infra.gefdiag.common.part.DiagramContentPart;
import org.eclipse.papyrus.infra.gefdiag.common.part.NotationContentPart;
import org.eclipse.papyrus.infra.gefdiag.common.part.PapyrusRootPart;
import org.eclipse.papyrus.infra.gefdiag.common.policies.FocusAndSelectOnClickPolicy;
import org.eclipse.papyrus.infra.gefdiag.common.policies.MarqueeOnDragPolicy;
import org.eclipse.papyrus.infra.gefdiag.common.policies.MoveOnDragPolicy;
import org.eclipse.papyrus.infra.gefdiag.common.policies.ResizeOnDragPolicy;
import org.eclipse.papyrus.infra.gefdiag.common.provider.ContentPartFactory;
import org.eclipse.papyrus.infra.gefdiag.common.provider.FeedbackPartFactory;
import org.eclipse.papyrus.infra.gefdiag.common.provider.HandlePartFactory;

import com.google.common.reflect.TypeToken;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;

import javafx.scene.Node;

public class GEFFxModule extends MvcFxModule {

	protected final Diagram diagram;

	public GEFFxModule(Diagram diagram) {
		this.diagram = diagram;
	}

	protected void bindIContentPartFactory() {
		binder().bind(new TypeLiteral<IContentPartFactory<Node>>() {
		}).to(ContentPartFactory.class)
				.in(AdaptableScopes.typed(FXViewer.class));
	}

	@Override
	protected void bindFXViewerAdapters(com.google.inject.multibindings.MapBinder<org.eclipse.gef4.common.adapt.AdapterKey<?>, Object> adapterMapBinder) {
		super.bindFXViewerAdapters(adapterMapBinder);

		adapterMapBinder.addBinding(AdapterKey.get(ChangeBoundsModel.class))
				.to(ChangeBoundsModel.class);
	}

	@Override
	protected void bindAbstractFXContentPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindAbstractFXContentPartAdapters(adapterMapBinder);

		adapterMapBinder.addBinding(AdapterKey.get(ChangeBoundsBehavior.class))
				.to(ChangeBoundsBehavior.class);
	}

	@Override
	protected void bindFXRootPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(
						AdapterKey.get(FXClickDragTool.CLICK_TOOL_POLICY_KEY))
				.to(FocusAndSelectOnClickPolicy.class);

		adapterMapBinder
				.addBinding(
						AdapterKey.get(FXClickDragTool.DRAG_TOOL_POLICY_KEY))
				.to(MarqueeOnDragPolicy.class);

		adapterMapBinder.addBinding(
				AdapterKey.get(FXScrollTool.TOOL_POLICY_KEY, "zoomOnScroll"))
				.to(FXZoomOnScrollPolicy.class);
		adapterMapBinder.addBinding(
				AdapterKey.get(FXScrollTool.TOOL_POLICY_KEY, "panOnScroll"))
				.to(FXPanOnScrollPolicy.class);


		adapterMapBinder
				.addBinding(AdapterKey.get(FXChangeViewportPolicy.class))
				.to(FXChangeViewportPolicy.class);

		// register default behaviors
		adapterMapBinder.addBinding(AdapterKey.get(ContentBehavior.class))
				.to(new TypeLiteral<ContentBehavior<Node>>() {
				});
		adapterMapBinder.addBinding(AdapterKey.get(SelectionBehavior.class))
				.to(new TypeLiteral<SelectionBehavior<Node>>() {
				});
		adapterMapBinder.addBinding(AdapterKey.get(FXGridBehavior.class))
				.to(FXGridBehavior.class);
		adapterMapBinder.addBinding(AdapterKey.get(FXViewportBehavior.class))
				.to(FXViewportBehavior.class);
	}

	protected void bindFXHandlePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(AdapterKey.get(FXClickDragTool.DRAG_TOOL_POLICY_KEY)).to(ResizeOnDragPolicy.class);
	}

	@Override
	protected void bindIHandlePartFactory() {
		binder().bind(new TypeLiteral<IHandlePartFactory<Node>>() {
		}).to(HandlePartFactory.class)
				.in(AdaptableScopes.typed(FXViewer.class));
	}

	@Override
	protected void bindIFeedbackPartFactory() {
		binder().bind(new TypeLiteral<IFeedbackPartFactory<Node>>() {
		}).to(FeedbackPartFactory.class)
				.in(AdaptableScopes.typed(FXViewer.class));
	}

	protected void bindContentPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(
						AdapterKey.get(FXClickDragTool.CLICK_TOOL_POLICY_KEY))
				.to(FocusAndSelectOnClickPolicy.class);

		adapterMapBinder
				.addBinding(
						AdapterKey
								.get(new TypeToken<Provider<IGeometry>>() {
								},
										FXDefaultFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER))
				.to(BoundsGeometryProvider.class);

		adapterMapBinder.addBinding(
				AdapterKey.get(FXClickDragTool.DRAG_TOOL_POLICY_KEY)).to(MoveOnDragPolicy.class);

		adapterMapBinder.addBinding(
				AdapterKey.get(new TypeToken<Provider<IFXAnchor>>() {
				})).to(ChopBoxAnchorProvider.class);

		adapterMapBinder
				.addBinding(
						AdapterKey
								.get(new TypeToken<Provider<IGeometry>>() {
								},
										FXDefaultHandlePartFactory.SELECTION_HANDLES_GEOMETRY_PROVIDER))
				.to(VisualBoundsGeometryProvider.class);
	}


	protected void bindDiagramPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
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
		}).to(PapyrusRootPart.class).in(AdaptableScopes.typed(FXViewer.class));
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

		bindDiagramPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), DiagramContentPart.class));

		bindFXHandlePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), AbstractFXHandlePart.class));

		bindBoundsBehavior();

		bindBoundsModel();

		bindPalette();
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
				return new EditingDomainUndoContext(EMFHelper.resolveEditingDomain(diagram));
			}
		});
	}

	@Override
	protected void bindIOperationHistory() {
		// Transactions are handled separately in Papyrus. Do not use the operation history for graphical operations, to avoid conflicts
		binder().bind(IOperationHistory.class).toInstance(EmptyOperationHistory.INSTANCE);
	}

}
