/*****************************************************************************
 * Copyright (c) 2015 - 2019 CEA LIST, EclipseSource and others.
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
package org.eclipse.papyrus.gefx.e3;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.fx.swt.canvas.FXCanvasEx;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.ui.parts.ISelectionProviderFactory;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.gef4.utils.ModelUtil;
import org.eclipse.papyrus.gef4.viewer.GEFxDiagram;
import org.eclipse.papyrus.gefx.e3.module.GEFEclipseUIModule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.google.inject.Guice;
import javax.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;


/**
 * <p>
 * Integrates a {@link GEFxDiagram} in an Eclipse 3 {@link IEditorPart}
 * </p>
 */
public abstract class GEFEditor<MODEL_ROOT> extends EditorPart {

	@Inject
	private GEFxDiagram<MODEL_ROOT> diagramRoot;

	@Inject
	private ISelectionProviderFactory selectionProviderFactory;

	private ISelectionProvider selectionProvider;

	private FXCanvasEx canvas;

	private final ListChangeListener<IContentPart<? extends Node>> selectionListener;
	
	private Module module;

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
		
		this.module = module;
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
		// Create the FX Canvas before injecting anything; to make sure JavaFX
		// is initialized first.
		canvas = new FXCanvasEx(parent, SWT.NONE);
		
		final Injector injector = Guice.createInjector(Modules.override(new GEFEclipseUIModule()).with(module));
		injector.injectMembers(this);
		
		selectionProvider = selectionProviderFactory.create(this);

		canvas.setScene(new Scene(diagramRoot.getCanvas()));

		getSelectionModel().getSelectionUnmodifiable().addListener(selectionListener);

		IViewer viewer = getViewer();
		if (viewer.getRootPart() == null || viewer.getRootPart().getChildrenUnmodifiable().isEmpty()) {
			selectionProvider.setSelection(StructuredSelection.EMPTY);
		} else {
			selectionProvider.setSelection(new StructuredSelection(viewer.getRootPart().getChildrenUnmodifiable().get(0)));
		}
		getSite().setSelectionProvider(selectionProvider);
	}

	public final IDomain getDomain() {
		return diagramRoot.getDomain();
	}

	@Override
	public void setFocus() {
		diagramRoot.initialize();
	}

	@Override
	public void dispose() {
		diagramRoot.dispose();
		super.dispose();
	}

	protected SelectionModel getSelectionModel() {
		return ModelUtil.getSelectionModel(getViewer());
	}

	public MODEL_ROOT getModel() {
		return diagramRoot.getModel();
	}

	public IViewer getViewer() {
		return diagramRoot.getViewer();
	}

}
