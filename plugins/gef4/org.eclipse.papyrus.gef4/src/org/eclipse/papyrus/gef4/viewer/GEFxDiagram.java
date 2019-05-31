/*****************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  EclipseSource - Initial API and Implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.viewer;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fx.core.ThreadSynchronize;
import org.eclipse.fx.core.observable.FXObservableUtil;
import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.common.dispose.IDisposable;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.models.GridModel;
import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.papyrus.gef4.palette.PaletteRenderer;
import org.eclipse.papyrus.gef4.style.GEFStyle;
import org.eclipse.papyrus.gef4.utils.ModelUtil;

import com.google.inject.Inject;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

/**
 * <p>
 * The {@link GEFxDiagram} is the entry point for creating a new diagram renderer.
 * The {@link #getCanvas() Canvas} is created as soon as the {@link GEFxDiagram} is
 * instantiated; then the diagram needs to be {@link #initialize() initialized}.
 * </p>
 * <p>
 * Once the {@link GEFxDiagram} has been created, it's root node can be attached
 * to a scene graph (see {@link #getCanvas()})
 * </p>
 * <p>
 * In order for the diagram to display its contents, it needs to be
 * {@link #initialize() initialized}. Initialization can take a few seconds, so it will
 * run in background (Which is why it has to be invoked separately).
 * </p>
 *
 * <p>
 * Typicaly usage:
 *
 * <pre>
 * &#64;Inject
 * private GEFxDiagram&lt;MyModelType&gt; gefxDiagram;
 *
 * public void display(BorderPane parent) {
 * 	parent.setCenter(gefxDiagram.getCanvas());
 * 	gefxDiagram.initialize(); // Async
 * }
 * </pre>
 * </p>
 *
 * <p>
 * The {@link GEFxDiagram} needs to be {@link #dispose() disposed} when it is no longer
 * needed.
 * </p>
 *
 * @param <MODEL_ROOT>
 *            The type of the diagram's root content element
 */
public class GEFxDiagram<MODEL_ROOT> implements IDisposable {

	// TODO Get the diagram root asynchronously as well, via a Provider
	// While this doesn't matter in the Papyrus Multi-Editor (Because the
	// entire model is pre-loaded), loading the diagram model in background
	// can help improving UI fluidity in standalone editors.
	private final MODEL_ROOT diagramRoot;
	private final IDomain domain;
	private final ThreadSynchronize threadSync;

	private final AtomicBoolean isDisposed = new AtomicBoolean(false);
	private CompletableFuture<Void> initialization;

	private IViewer viewer;

	private BorderPane rootPane;

	private SplitPane diagramSplitPane;

	@Inject
	public GEFxDiagram(MODEL_ROOT diagramRoot, IDomain domain, ThreadSynchronize threadSync) {
		this.diagramRoot = diagramRoot;
		this.domain = domain;
		this.threadSync = threadSync;
		createControl();
	}

	public Parent getCanvas() {
		return rootPane;
	}

	private void createControl() {
		viewer = getDomain().getAdapter(AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE));

		BorderPane splashPane = new BorderPane();
		splashPane.setCenter(new Label("Initializing diagram viewer. This may take a few seconds for larger diagrams..."));

		final GridModel gridModel = viewer.getAdapter(GridModel.class);
		gridModel.setShowGrid(false);

		rootPane = new BorderPane();
		diagramSplitPane = new SplitPane();
		rootPane.setCenter(splashPane);

		FXObservableUtil.onChange(diagramSplitPane.getChildrenUnmodifiable(), change -> threadSync.asyncExec(() -> optimizePalettePosition(diagramSplitPane)));
		diagramSplitPane.getItems().add(viewer.getCanvas());

		rootPane.getStylesheets().add(GEFStyle.getGEFxCSS().toExternalForm());

		// Set contents
		viewer.getContents().setAll(getContents());
	}

	@Inject(optional = true)
	public void setPaletteRenderer(PaletteRenderer paletteRenderer) {
		Node palette = paletteRenderer.createPaletteControl();
		if (palette != null) {
			diagramSplitPane.getItems().add(palette);
		}
	}

	protected CompletableFuture<Void> initViewerContents() {
		CompletableFuture<Void> initialized = new CompletableFuture<>();
		threadSync.asyncExec(() -> {
			if (isDisposed.get()) {
				initialized.completeExceptionally(new OperationCanceledException("The GEFxDiagram Viewer was disposed before it could be initialized"));
				return;
			}
			// Activate
			rootPane.setCenter(diagramSplitPane);
			activateDomain();
		});

		return initialized;

		// XXX Once everything is in place, we still need to render the initial JavaFX Frame. The first one
		// takes time, because the first layout/css pass is expensive. It may still take a few seconds after
		// this point until the diagram is visible (Depending on how big the diagram is). After the first
		// frame, the diagram editor becomes much more responsive, since all further layout updates are incremental.
	}

	private void activateDomain() {
		if (viewer.getCanvas().getScene() != null) {
			domain.activate();
			initialization.complete(null);
		} else {
			// Until GEF 5.1 is released (2019-06), we can't activate the domain
			// until the canvas is attached to the scene graph
			threadSync.asyncExec(this::activateDomain);
		}
	}

	protected void optimizePalettePosition(SplitPane diagramSplitPane) {
		if (diagramSplitPane.getItems().size() != 2) {
			// Expected is diagram on the left, palette on the right.
			// If we get anything else, skip this
			return;
		}
		double totalWidth = diagramSplitPane.getWidth();
		Node paletteNode = diagramSplitPane.getItems().get(1);
		if (paletteNode instanceof Region) {
			Region palette = (Region) paletteNode;
			// FIXME: The palette pref width is not correct because it doesn't take scrollbars into account
			// We should get a proper way to compute optimal width (Maybe delegating to the palette renderer
			// directly). Also note that if the palette renderer uses a ListView, pref width may change based
			// on palette scrolling, because ListView is a virtual flow (But we really just care about "acceptable defaults")
			// For now, we just use a hard coded workaround here; but it should really be the responsibility
			// of the palette Region to provide appropriate pref width.
			double scrollbarExtra = 40;
			double paletteWidth = palette.prefWidth(diagramSplitPane.getHeight()) + scrollbarExtra;
			diagramSplitPane.setDividerPositions(Math.max(0.7, 1 - paletteWidth / totalWidth));
		}
	}

	protected final List<MODEL_ROOT> getContents() {
		return Collections.singletonList(diagramRoot);
	}

	public final IDomain getDomain() {
		return domain;
	}

	/**
	 * Initializes the contents of this diagram (asynchronously). Calling this method after
	 * the diagram has been initialized will have no effect.
	 */
	public synchronized Future<Void> initialize() {
		if (initialization == null) {
			initialization = initViewerContents();
		}
		return initialization;
	}

	@Override
	public void dispose() {
		isDisposed.set(true);
		domain.deactivate();
		domain.dispose();
	}

	protected SelectionModel getSelectionModel() {
		return ModelUtil.getSelectionModel(viewer);
	}

	public MODEL_ROOT getModel() {
		return diagramRoot;
	}

	public IViewer getViewer() {
		return viewer;
	}

}
