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
package org.eclipse.papyrus.infra.gefdiag.common.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef4.fx.ui.canvas.FXCanvasEx;
import org.eclipse.gef4.mvc.domain.IDomain;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.models.ContentModel;
import org.eclipse.gef4.mvc.models.GridModel;
import org.eclipse.gef4.mvc.models.SelectionModel;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IRootPart;
import org.eclipse.gef4.mvc.viewer.IViewer;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gefdiag.common.module.GEFFxModule;
import org.eclipse.papyrus.infra.gefdiag.common.part.PapyrusRootPart;
import org.eclipse.papyrus.infra.widgets.util.IRevealSemanticElement;
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

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;

public class GEFEditor extends EditorPart implements PropertyChangeListener, IRevealSemanticElement {

	private Diagram diagram;

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

	public GEFEditor(Diagram diagram) {
		this.diagram = diagram;
		Injector injector = Guice.createInjector(createModule());
		injector.injectMembers(this);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// Nothing (Handled by the MultiDiagramEditor)
	}

	@Override
	public void doSaveAs() {
		// Nothing (Handled by the MultiDiagramEditor)
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
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
	public void createPartControl(Composite parent) {
		viewer = getDomain().getAdapter(IViewer.class);
		rootPart = viewer.getRootPart();
		if (rootPart instanceof PapyrusRootPart) {
			((PapyrusRootPart) rootPart).setDiagram(diagram);
		}

		// Canvas and SceneContainer
		canvas = new FXCanvasEx(parent, SWT.NONE);

//		SplitPane splitPane = new SplitPane();
//		splitPane.getItems().addAll(viewer.getScrollPane(), palette.getVisual());
		// splitPane.setDividerPositions(0.3f);

		scene = new Scene(viewer.getScrollPane());
		canvas.setScene(scene);

		// Activate
		domain.activate();

		// Set contents
		viewer.getAdapter(ContentModel.class).setContents(getContents());

		// viewer.getScene().getStylesheets().add("platform:/plugin/org.eclipse.papyrus.infra.gefdiag.common/style/defaultFX.css");

		// viewer.getScene().setFill(Color.ALICEBLUE);

		GridModel gridModel = viewer.getAdapter(GridModel.class);
		gridModel.setShowGrid(false);
		gridModel.setSnapToGrid(false);

		// Don't use SelectionForwarder because it selects the Content element rather than the ContentPart
		// selectionForwarder = new SelectionForwarder<>(selectionProvider, viewer);

		// Manual change listener and synchronization with SelectionModel
		getSelectionModel().addPropertyChangeListener(this);

		selectionProvider.setSelection(new StructuredSelection(viewer.getRootPart().getChildren().get(0)));
		getSite().setSelectionProvider(selectionProvider);
	}

	protected final List<Diagram> getContents() {
		return Collections.singletonList(diagram);
	}

	public final IDomain<Node> getDomain() {
		return domain;
	}

	protected Module createModule() {
		return new GEFFxModule(diagram);
	}

	@Override
	public void setFocus() {
		if (canvas != null && !canvas.isDisposed()) {
			canvas.setFocus();
		}
	}

	@Override
	public void dispose() {
		domain.deactivate();
		domain.dispose();
		// this.viewer.dispose();
		super.dispose();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		case SelectionModel.SELECTION_PROPERTY:
			List<?> oldSelection = (List<?>) evt.getOldValue();
			List<?> selectedElements = (List<?>) evt.getNewValue();

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
		return viewer.getAdapter(SelectionModel.class);
	}

	@Override
	public void revealSemanticElement(List<?> elementList) {
		List<IContentPart<Node, ? extends Node>> partsToReveal = new LinkedList<>();
		for (IContentPart<Node, ? extends Node> contentPart : viewer.getContentPartMap().values()) {
			EObject semanticElement = EMFHelper.getEObject(contentPart);
			if (elementList.contains(semanticElement)) {
				partsToReveal.add(contentPart);
			}
		}

		SelectionModel<Node> selectionModel = getSelectionModel();
		selectionModel.updateSelection(partsToReveal);
	}

}
