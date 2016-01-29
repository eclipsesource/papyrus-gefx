/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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
package org.eclipse.papyrus.gef4.tests.utils;

import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.parts.IRootPart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.papyrus.gef4.editor.GEFEditor;
import org.eclipse.papyrus.gef4.parts.DiagramContentPart;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import javafx.scene.Node;

public class EditorUtils {

	public static IEditorPart openEditor(String projectName, String folder, String[] sourceFileNames, String fileToOpen, String editorID) throws Exception {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		project.create(new NullProgressMonitor());
		project.open(new NullProgressMonitor());

		for (String sourceFileName : sourceFileNames) {
			URL url = new URL(folder + "/" + sourceFileName);
			FilesUtils.copyFiles(project, sourceFileName, url);
		}

		IFile libraryDiagram = project.getFile(fileToOpen);

		AtomicReference<IEditorPart> result = new AtomicReference<>();
		AtomicReference<Exception> error = new AtomicReference<>();
		Display.getDefault().syncExec(() -> {
			try {
				result.set(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new FileEditorInput(libraryDiagram), editorID));
			} catch (Exception ex) {
				error.set(ex);
			}
		});

		if (error.get() != null) {
			throw error.get(); // Throw the exception in the JUnit thread to get proper results
		}

		return result.get();
	}

	public static DiagramContentPart openDiagram(String projectName, String folder, String[] sourceFileNames, String fileToOpen, String editorID) throws Exception {
		GEFEditor editorPart = (GEFEditor) openEditor(projectName, folder, sourceFileNames, fileToOpen, editorID);
		FXViewer viewer = editorPart.getViewer();
		IRootPart<Node, ? extends Node> root = viewer.getRootPart();
		IVisualPart<Node, ? extends Node> diagramPart = root.getChildrenUnmodifiable().get(0);

		return (DiagramContentPart) diagramPart;
	}
}
