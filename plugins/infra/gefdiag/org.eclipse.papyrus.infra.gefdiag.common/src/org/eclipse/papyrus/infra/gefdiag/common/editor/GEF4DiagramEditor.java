/*****************************************************************************
 * Copyright (c) 2015 - 2016 CEA LIST and others.
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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.gef4.editor.GEFEditor;
import org.eclipse.papyrus.gef4.parts.IPrimaryContentPart;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.handler.IRefreshHandlerPart;
import org.eclipse.papyrus.infra.gmfdiag.common.handler.RefreshHandler;
import org.eclipse.papyrus.infra.widgets.util.IRevealSemanticElement;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;

import com.google.inject.Module;

import javafx.scene.Node;

public class GEF4DiagramEditor extends GEFEditor implements IRevealSemanticElement, IRefreshHandlerPart {

	protected boolean disposed = false;

	protected Module module;

	public GEF4DiagramEditor(Diagram diagram, Module module) {
		super(diagram, module);
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);

		RefreshHandler.register(this);
	}

	@Override
	public void dispose() {
		disposed = true;
		super.dispose();
	}

	public boolean isDisposed() {
		return disposed;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void revealSemanticElement(List<?> elementList) {
		IViewer viewer = getViewer();
		if (viewer == null || !viewer.isActive()) {
			return;
		}

		List<IContentPart<? extends Node>> partsToReveal = new LinkedList<>();

		for (IContentPart<? extends Node> contentPart : viewer.getContentPartMap().values()) {
			if (contentPart instanceof IPrimaryContentPart) {
				EObject semanticElement = EMFHelper.getEObject(contentPart);
				if (elementList.contains(semanticElement)) {
					partsToReveal.add(contentPart);
				}
			}
		}

		SelectionModel selectionModel = getSelectionModel();
		selectionModel.setSelection(partsToReveal);
	}

	@Override
	public void refresh(IEditorPart editorPart) {
		if (isDisposed()) {
			return;
		}

		IRootPart<? extends Node> root = getViewer().getRootPart();
		for (IVisualPart<? extends Node> visualPart : root.getChildrenUnmodifiable()) {
			refresh(visualPart, true);
		}
	}

	protected void refresh(IVisualPart<?> visualPart, boolean recursive) {
		visualPart.refreshVisual();
		if (recursive) {
			for (IVisualPart<?> childPart : visualPart.getChildrenUnmodifiable()) {
				refresh(childPart, true);
			}
		}
	}

}
