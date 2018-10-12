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
package org.eclipse.papyrus.gef4.editor;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fx.core.ThreadSynchronize;
import org.eclipse.fx.core.observable.FXObservableUtil;
import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.fx.swt.canvas.FXCanvasEx;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.models.GridModel;
import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.ui.parts.ISelectionProviderFactory;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.gef4.palette.PaletteRenderer;
import org.eclipse.papyrus.gef4.style.GEFStyle;
import org.eclipse.papyrus.gef4.utils.ModelUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

public abstract class GEFEditor<MODEL_ROOT> extends EditorPart {

	@Inject
	private MODEL_ROOT diagramRoot;

	@Inject
	private IDomain domain;

	@Inject
	private ISelectionProviderFactory selectionProviderFactory;

	@Inject
	@Nullable
	private PaletteRenderer palette;

	@Inject
	private ThreadSynchronize threadSync;

	private ISelectionProvider selectionProvider;

	private IViewer viewer;

	private FXCanvasEx canvas;

	private Scene scene;

	private BorderPane rootPane;

	private final ListChangeListener<IContentPart<? extends Node>> selectionListener;

	private final AtomicBoolean isReady = new AtomicBoolean(false);

	private final AtomicBoolean isInitialized = new AtomicBoolean(false);

	public GEFEditor(final Module module) {
		this();

		init(module);
	}

	public GEFEditor() {
		selectionListener = change -> {
			final List<?> selectedElements = change.getList();

			IStructuredSelection selection;
			if (selectedElements.size() > 0) {
				selection = new StructuredSelection(selectedElements);
			} else {
				selection = StructuredSelection.EMPTY;
			}
			if (getSite().getSelectionProvider() != null) {
				// May be null during initialization
				getSite().getSelectionProvider().setSelection(selection);
			}
		};
	}

	public void init(final Module module) {
		if (module == null) {
			throw new IllegalArgumentException("The module is undefined. It must be either passed in the constructor, or the method createModule() must be overridden");
		}

		final Injector injector = Guice.createInjector(module);
		injector.injectMembers(this);
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// Nothing (Handled by the MultiDiagramEditor)
	}

	@Override
	public void doSaveAs() {
		// Nothing (Handled by the MultiDiagramEditor)
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		this.setSite(site);
		this.setInput(input);
	}

	@Override
	public IEditorSite getEditorSite() {
		return super.getEditorSite();
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(final Composite parent) {
		viewer = getDomain().getAdapter(AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE));
		selectionProvider = selectionProviderFactory.create(this);

		// Canvas and SceneContainer
		canvas = new FXCanvasEx(parent, SWT.NONE);

		BorderPane splashPane = new BorderPane();
		splashPane.setCenter(new Label("Initializing diagram viewer. This may take a few seconds for larger diagrams..."));

		canvas.setScene(new Scene(splashPane));
		// XXX Because of the async nature of JavaFX in SWT, it's difficult to
		// know then the splash is actually visible to the user. Add a paint listener
		// to make sure we display a message *before* freezing the screen for a few seconds.
		canvas.addPaintListener(e -> isReady.set(true));

		final GridModel gridModel = viewer.getAdapter(GridModel.class);
		gridModel.setShowGrid(false);

		rootPane = new BorderPane();
		SplitPane diagramSplitPane = new SplitPane();
		rootPane.setCenter(diagramSplitPane);

		FXObservableUtil.onChange(diagramSplitPane.getChildrenUnmodifiable(), change -> threadSync.asyncExec(() -> optimizePalettePosition(diagramSplitPane)));
		diagramSplitPane.getItems().add(viewer.getCanvas());

		scene = new Scene(rootPane);
		scene.getStylesheets().add(GEFStyle.class.getResource("gefx.css").toExternalForm());

		// Set contents
		viewer.getContents().setAll(getContents());

		getSelectionModel().getSelectionUnmodifiable().addListener(selectionListener);

		if (viewer.getRootPart() == null || viewer.getRootPart().getChildrenUnmodifiable().isEmpty()) {
			selectionProvider.setSelection(StructuredSelection.EMPTY);
		} else {
			selectionProvider.setSelection(new StructuredSelection(viewer.getRootPart().getChildrenUnmodifiable().get(0)));
		}
		getSite().setSelectionProvider(selectionProvider);

		Node paletteNode = palette.createPaletteControl();
		if (paletteNode != null) {
			((SplitPane) (rootPane.getCenter())).getItems().add(paletteNode);
		}
	}

	protected void initViewerContents() {
		threadSync.asyncExec(() -> {
			canvas.setScene(scene);
			// Activate
			domain.activate();
		});

		// XXX Once everything is in place, we still need to render the initial JavaFX Frame. The first one
		// takes time, because the first layout/css pass is expensive. It may still take a few seconds after
		// this point until the diagram is visible (Depending on how big the diagram is). After the first
		// frame, the diagram editor becomes much more responsive, since all further layout updates are incremental.
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

	@Override
	public void setFocus() {
		asyncInit();
	}

	private void asyncInit() {
		if (!isInitialized.get()) {
			if (isReady.get()) {
				initViewerContents();
				// threadSync.asyncExec(this::initViewerContents);
				// Job job = Job.create("Init viewer contents", monitor -> {
				// initViewerContents();
				// return Status.OK_STATUS;
				// });
				// job.schedule();
			} else {
				// Try again later
				threadSync.asyncExec(this::asyncInit);
			}
		}
	}

	@Override
	public void dispose() {
		domain.deactivate();
		domain.dispose();
		super.dispose();
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
