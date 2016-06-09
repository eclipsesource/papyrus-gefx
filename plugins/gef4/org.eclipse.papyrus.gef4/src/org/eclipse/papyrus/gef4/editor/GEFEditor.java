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
import org.eclipse.gef4.fx.swt.canvas.FXCanvasEx;
import org.eclipse.gef4.mvc.domain.IDomain;
import org.eclipse.gef4.mvc.fx.domain.FXDomain;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.models.ContentModel;
import org.eclipse.gef4.mvc.models.GridModel;
import org.eclipse.gef4.mvc.models.SelectionModel;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IRootPart;
import org.eclipse.gef4.mvc.ui.parts.ISelectionProviderFactory;
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
import javafx.scene.Scene;

public abstract class GEFEditor extends EditorPart {

	/**
	 * Set during {@link #init(Diagram)}
	 */
	private Diagram diagram;

	private IRootPart<Node, ? extends Node> rootPart;

	@Inject
	private FXDomain domain;

	@Inject
	private ISelectionProviderFactory selectionProviderFactory;

	private ISelectionProvider selectionProvider;

	private FXViewer viewer;

	private FXCanvasEx canvas;

	private Scene scene;

	private final ListChangeListener<IContentPart<Node, ? extends Node>> selectionListener;

	public GEFEditor(final Diagram diagram, final Module module) {
		this();

		init(diagram, module);
	}

	public GEFEditor() {
		selectionListener = new ListChangeListener<IContentPart<Node, ? extends Node>>() {
			@Override
			public void onChanged(ListChangeListener.Change<? extends IContentPart<Node, ? extends Node>> change) {
				final List<?> selectedElements = change.getList();

				IStructuredSelection selection;
				if (selectedElements.size() > 0) {
					selection = new StructuredSelection(selectedElements);
				} else {
					selection = StructuredSelection.EMPTY;
				}
				getSite().getSelectionProvider().setSelection(selection);
			}
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
		viewer = getDomain().getAdapter(FXViewer.class);
		selectionProvider = selectionProviderFactory.create(this);
		rootPart = viewer.getRootPart();
		if (rootPart instanceof DiagramRootPart) {
			((DiagramRootPart) rootPart).setDiagram(diagram);
		}

		// Canvas and SceneContainer
		canvas = new FXCanvasEx(parent, SWT.NONE);

		// SplitPane splitPane = new SplitPane();
		// splitPane.getItems().addAll(viewer.getScrollPane(), palette.getVisual());
		// splitPane.setDividerPositions(0.3f);

		scene = new Scene(viewer.getCanvas());
		canvas.setScene(scene);

		// Activate
		domain.activate();

		// Set contents
		viewer.getAdapter(ContentModel.class).getContents().setAll(getContents());

		// viewer.getScene().getStylesheets().add("platform:/plugin/org.eclipse.papyrus.infra.gefdiag.common/style/defaultFX.css");

		// viewer.getScene().setFill(Color.ALICEBLUE);

		final GridModel gridModel = viewer.getAdapter(GridModel.class);
		gridModel.setShowGrid(false);
		gridModel.setSnapToGrid(false);

		// Don't use SelectionForwarder because it selects the Content element rather than the ContentPart
		// selectionForwarder = new SelectionForwarder<>(selectionProvider, viewer);

		// Manual change listener and synchronization with SelectionModel
		getSelectionModel().getSelectionUnmodifiable().addListener(selectionListener);

		if (viewer.getRootPart() == null || viewer.getRootPart().getChildrenUnmodifiable().isEmpty()) {
			selectionProvider.setSelection(StructuredSelection.EMPTY);
		} else {
			selectionProvider.setSelection(new StructuredSelection(viewer.getRootPart().getChildrenUnmodifiable().get(0)));
		}
		getSite().setSelectionProvider(selectionProvider);
	}

	protected final List<Diagram> getContents() {
		return Collections.singletonList(diagram);
	}

	public final IDomain<Node> getDomain() {
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
		// this.viewer.dispose();
		super.dispose();
	}

	protected SelectionModel<Node> getSelectionModel() {
		return ModelUtil.getSelectionModel(viewer);
	}

	public Diagram getDiagram() {
		return diagram;
	}

	public FXViewer getViewer() {
		return viewer;
	}

}
