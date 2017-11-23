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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.fx.swt.canvas.FXCanvasEx;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.models.GridModel;
import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.ui.parts.ISelectionProviderFactory;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.gef4.parts.DiagramRootPart;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public abstract class GEFEditor extends EditorPart {

	/**
	 * Set during {@link #init(Diagram)}
	 */
	private Diagram diagram;

	private IRootPart<? extends Node> rootPart;

	@Inject
	private IDomain domain;

	@Inject
	private ISelectionProviderFactory selectionProviderFactory;

	private ISelectionProvider selectionProvider;

	private IViewer viewer;

	private FXCanvasEx canvas;

	private Scene scene;

	private final ListChangeListener<IContentPart<? extends Node>> selectionListener;

	public GEFEditor(final Diagram diagram, final Module module) {
		this();

		init(diagram, module);
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
			getSite().getSelectionProvider().setSelection(selection);
		};
	}

	public void init(final Diagram diagram, final Module module) {
		if (this.diagram != null) {
			throw new IllegalStateException("This editor has already been initialized");
		}

		if (module == null) {
			throw new IllegalArgumentException("The module is undefined. It must be either passed in the constructor, or the method createModule() must be overridden");
		}

		this.diagram = diagram;
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
		viewer = getDomain().getViewers().values().stream().findFirst().orElseThrow(IllegalStateException::new);
		selectionProvider = selectionProviderFactory.create(this);
		rootPart = viewer.getRootPart();
		if (rootPart instanceof DiagramRootPart) {
			((DiagramRootPart) rootPart).setDiagram(diagram);
		}

		// Canvas and SceneContainer
		canvas = new FXCanvasEx(parent, SWT.NONE);

		scene = new Scene(viewer.getCanvas());
		canvas.setScene(scene);

		setSceneColor(Color.ANTIQUEWHITE);

		// Activate
		domain.activate();

		// Set contents
		viewer.getContents().setAll(getContents());

		final GridModel gridModel = viewer.getAdapter(GridModel.class);
		gridModel.setShowGrid(false);

		getSelectionModel().getSelectionUnmodifiable().addListener(selectionListener);

		if (viewer.getRootPart() == null || viewer.getRootPart().getChildrenUnmodifiable().isEmpty()) {
			selectionProvider.setSelection(StructuredSelection.EMPTY);
		} else {
			selectionProvider.setSelection(new StructuredSelection(viewer.getRootPart().getChildrenUnmodifiable().get(0)));
		}
		getSite().setSelectionProvider(selectionProvider);
	}

	protected final void setSceneColor(Paint paint) {
		if (viewer != null) {
			Parent viewerCanvas = viewer.getCanvas();
			if (viewerCanvas instanceof Region) {
				((Region) viewerCanvas).setBackground(new Background(new BackgroundFill(paint, null, null)));
			}
		}
	}

	protected final List<Diagram> getContents() {
		return Collections.singletonList(diagram);
	}

	public final IDomain getDomain() {
		return domain;
	}

	@Override
	public void setFocus() {
		// if (canvas != null && !canvas.isDisposed()) {
		// canvas.setFocus();
		// }
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

	public Diagram getDiagram() {
		return diagram;
	}

	public IViewer getViewer() {
		return viewer;
	}

}
