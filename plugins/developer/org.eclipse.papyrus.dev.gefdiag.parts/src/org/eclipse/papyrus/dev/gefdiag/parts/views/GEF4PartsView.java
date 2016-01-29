/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.dev.gefdiag.parts.views;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.papyrus.dev.gefdiag.parts.views.providers.VisualPartContentProvider;
import org.eclipse.papyrus.dev.gefdiag.parts.views.providers.VisualPartLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import javafx.scene.Node;

/**
 * A View to help debugging an FX Node Graph
 *
 * @author Camille Letavernier
 */
public class GEF4PartsView extends ViewPart implements ISelectionListener, ISelectionChangedListener {

	private Composite viewParent;

	private TreeViewer treeViewer;

	private Composite viewComposite;

	private Composite viewPropertiesComposite;

	private Composite viewDescriptionComposite;

	// private TableViewer properties;

	public GEF4PartsView() {
		// Nothing
	}

	@Override
	public void createPartControl(Composite parent) {
		viewParent = new Composite(parent, SWT.NONE);
		viewParent.setLayout(new GridLayout(2, false));

		treeViewer = new TreeViewer(viewParent, SWT.BORDER);
		treeViewer.setContentProvider(new VisualPartContentProvider());
		treeViewer.setLabelProvider(new VisualPartLabelProvider());
		GridData treeLayoutData = new GridData(SWT.BEGINNING, SWT.FILL, false, true);
		treeLayoutData.minimumWidth = 450;
		treeLayoutData.widthHint = 450;
		treeViewer.getTree().setLayoutData(treeLayoutData);
		treeViewer.addSelectionChangedListener(this);

		viewComposite = new Composite(viewParent, SWT.BORDER);
		viewComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		viewComposite.setLayout(new GridLayout(2, true));

		viewDescriptionComposite = new Composite(viewComposite, SWT.NONE);
		GridLayout viewDescriptionCompositeLayout = new GridLayout(2, false);
		viewDescriptionCompositeLayout.marginHeight = 0;
		viewDescriptionCompositeLayout.marginWidth = 0;
		viewDescriptionComposite.setLayout(viewDescriptionCompositeLayout);
		viewDescriptionComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		viewPropertiesComposite = new Composite(viewComposite, SWT.NONE);
		GridLayout viewPropertiesCompositeLayout = new GridLayout(2, false);
		viewPropertiesCompositeLayout.marginHeight = 0;
		viewPropertiesCompositeLayout.marginWidth = 0;
		viewPropertiesComposite.setLayout(viewPropertiesCompositeLayout);
		viewPropertiesComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		Label semanticPropertiesHeader = new Label(viewComposite, SWT.NONE);
		semanticPropertiesHeader.setText("Details:");
		semanticPropertiesHeader.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));

		// properties = new TableViewer(viewComposite, SWT.BORDER);
		// properties.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		// properties.setContentProvider(new FXStyleContentProvider());
		// properties.setLabelProvider(new FXStyleLabelProvider());

		// createColumn(properties, "Property");
		// createColumn(properties, "Value");

		getViewSite().getPage().addSelectionListener(this);
	}

	@Override
	public void setFocus() {
		// Nothing
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		Object selectedElement = getSelectedPart(selection);
		if (selectedElement == null) {
			return;
		}

		clean();
		debug(selectedElement);
	}

	private Object getSelectedPart(ISelection selection) {
		if (selection.isEmpty()) {
			return null;
		}

		if (selection instanceof IStructuredSelection) {
			return ((IStructuredSelection) selection).getFirstElement();
		}

		return null;
	}

	@Override
	public void dispose() {
		getViewSite().getPage().removeSelectionListener(this);
		super.dispose();
	}

	private void debug(Object selectedElement) {
		if (selectedElement == null) {
			return; // Do not change the state of the debug view if the selection isn't interesting
		}

		treeViewer.setInput(new Object[] { selectedElement }); // Wrap into an array, because TreeViewer has a strange behavior when the root element equals the input element.
		treeViewer.setSelection(new StructuredSelection(selectedElement));
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		ISelection selection = event.getSelection();
		if (selection.isEmpty()) {
			clean();
			return;
		}

		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Object selectedElement = structuredSelection.getFirstElement();
			if (selectedElement instanceof Node) {
				// properties.setInput(selectedElement);
			}
		}
	}

	private void clean() {
		clean(viewDescriptionComposite);
		clean(viewPropertiesComposite);
		// properties.setInput("");
		viewComposite.layout();
		viewDescriptionComposite.layout();
	}

	private void clean(Composite composite) {
		for (Control control : composite.getChildren()) {
			control.dispose();
		}
	}

}
