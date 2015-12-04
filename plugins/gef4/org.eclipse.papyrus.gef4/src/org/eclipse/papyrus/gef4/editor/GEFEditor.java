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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef4.fx.swt.canvas.FXCanvasEx;
import org.eclipse.gef4.mvc.domain.IDomain;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.models.ContentModel;
import org.eclipse.gef4.mvc.models.GridModel;
import org.eclipse.gef4.mvc.models.SelectionModel;
import org.eclipse.gef4.mvc.parts.IRootPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.gef4.parts.DiagramRootPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.google.common.reflect.TypeToken;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

import javafx.scene.Node;
import javafx.scene.Scene;

@SuppressWarnings("serial")
public abstract class GEFEditor extends EditorPart implements PropertyChangeListener {

	private final Diagram diagram;

	private IRootPart<Node, ? extends Node> rootPart;

	@Inject
	private IDomain<Node> domain;

	@Inject
	private ISelectionProvider selectionProvider;

	@Inject
	private Palette palette;

	private FXViewer viewer;

	private FXCanvasEx canvas;

	private Scene scene;

	public GEFEditor(final Diagram diagram) {
		this.diagram = diagram;
		final Injector injector = Guice.createInjector(createModule());
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createPartControl(final Composite parent) {
		viewer = getDomain().getAdapter(FXViewer.class);
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
		viewer.getAdapter(ContentModel.class).setContents(getContents());

		// viewer.getScene().getStylesheets().add("platform:/plugin/org.eclipse.papyrus.infra.gefdiag.common/style/defaultFX.css");

		// viewer.getScene().setFill(Color.ALICEBLUE);

		final GridModel gridModel = viewer.getAdapter(GridModel.class);
		gridModel.setShowGrid(false);
		gridModel.setSnapToGrid(false);

		// Don't use SelectionForwarder because it selects the Content element rather than the ContentPart
		// selectionForwarder = new SelectionForwarder<>(selectionProvider, viewer);

		// Manual change listener and synchronization with SelectionModel
		getSelectionModel().addPropertyChangeListener(this);

		if (viewer.getRootPart() == null || viewer.getRootPart().getChildren().isEmpty()) {
			System.out.println("Break");
		}

		selectionProvider.setSelection(new StructuredSelection(viewer.getRootPart().getChildren().get(0)));
		getSite().setSelectionProvider(selectionProvider);
	}

	protected final List<Diagram> getContents() {
		return Collections.singletonList(diagram);
	}

	public final IDomain<Node> getDomain() {
		return domain;
	}

	protected abstract Module createModule();

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

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		case SelectionModel.SELECTION_PROPERTY:
			final List<?> oldSelection = (List<?>) evt.getOldValue();
			final List<?> selectedElements = (List<?>) evt.getNewValue();

			IStructuredSelection selection;
			if (selectedElements.size() > 0) {
				selection = new StructuredSelection(selectedElements);
			} else {
				selection = StructuredSelection.EMPTY;
			}
			getSite().getSelectionProvider().setSelection(selection);
			break;
		}
	}

	protected SelectionModel<Node> getSelectionModel() {
		return viewer.getAdapter(new TypeToken<SelectionModel<Node>>() {
		});
	}

	public Diagram getDiagram() {
		return diagram;
	}

	public FXViewer getViewer() {
		return viewer;
	}

}
