/*****************************************************************************
 * Copyright (c) 2015-2016 CEA LIST and others.
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.gef4.common.dispose.IDisposable;
import org.eclipse.gef4.mvc.models.SelectionModel;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.ui.parts.ContentSelectionProvider;
import org.eclipse.gef4.mvc.viewer.IViewer;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;

//Unlike the GEF4 ContentSelectionProvider, we want to manipulate the IContentPart directly (Rather than the Content element)
public class ViewerSelectionProvider implements ISelectionProvider, IDisposable {

	private class SelectionObserver implements ListChangeListener<IContentPart<Node, ? extends Node>> {

		@Override
		public void onChanged(ListChangeListener.Change<? extends IContentPart<Node, ? extends Node>> c) {
			// notify listeners that selection has changed
			final SelectionChangedEvent e = new SelectionChangedEvent(ViewerSelectionProvider.this, getSelection());
			for (final ISelectionChangedListener l : selectionChangedListeners) {
				SafeRunner.run(new SafeRunnable() {
					@Override
					public void run() {
						l.selectionChanged(e);
					}
				});
			}
		}
	}

	private final SelectionObserver selectionObserver = new SelectionObserver();
	private List<ISelectionChangedListener> selectionChangedListeners = new ArrayList<>();
	private IViewer<Node> viewer;
	private SelectionModel<Node> selectionModel;

	/**
	 * Creates a new {@link ContentSelectionProvider} for the given
	 * {@link SelectionModel}.
	 *
	 * @param viewer
	 *            The {@link IViewer} to associate this
	 *            {@link ContentSelectionProvider} to.
	 */
	@SuppressWarnings("unchecked")
	public ViewerSelectionProvider(IViewer<Node> viewer) {
		this.viewer = viewer;
		this.selectionModel = viewer.getAdapter(SelectionModel.class);
		selectionModel.getSelectionUnmodifiable().addListener(selectionObserver);
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}

	@Override
	public void dispose() {
		if (selectionModel != null) {
			selectionModel.getSelectionUnmodifiable().removeListener(selectionObserver);
		}
	}

	/**
	 * Returns the {@link IViewer} this {@link ContentSelectionProvider} is
	 * bound to.
	 *
	 * @return The {@link IViewer} this {@link ContentSelectionProvider} is
	 *         bound to.
	 */
	protected IViewer<Node> getViewer() {
		return viewer;
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.remove(listener);
	}

	@Override
	public void setSelection(ISelection selection) {
		// update the selection model, which will lead to an update of our
		// selection and to listener notification.
		if (selection.isEmpty()) {
			if (!selectionModel.getSelectionUnmodifiable().isEmpty()) {
				selectionModel.clearSelection();
			}
		} else if (selection instanceof StructuredSelection) {
			// find the content parts associated with the selection
			Object[] selected = ((StructuredSelection) selection).toArray();
			List<IContentPart<Node, ? extends Node>> parts = new ArrayList<>(
					selected.length);

			for (Object content : selected) {
				if (content instanceof IContentPart) {
					parts.add((IContentPart) content);
				}
			}
			// set the content parts as the new selection to the SelectionModel
			if (!selectionModel.getSelectionUnmodifiable().equals(parts)) {
				selectionModel.setSelection(parts);
			}
		}
	}

	@Override
	public ISelection getSelection() {
		return new StructuredSelection(selectionModel.getSelectionUnmodifiable());
	}

}
