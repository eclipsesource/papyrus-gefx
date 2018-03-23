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
package org.eclipse.papyrus.gef4.tests.editor;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.example.library.editor.LibraryEditor;
import org.eclipse.papyrus.gef4.gmf.parts.NotationDiagramContentPart;
import org.eclipse.papyrus.gef4.tests.utils.EditorUtils;
import org.junit.Assert;
import org.junit.Test;

import javafx.scene.Node;

public class EditorTests {
	@Test
	public void testOpenEditor() throws Exception {

		String folder = "platform:/plugin/org.eclipse.papyrus.gef4.tests/models/";
		String[] fileNames = new String[] {
				"Library.library",
				"Library.librarydiagram"
		};

		LibraryEditor libraryEditor = (LibraryEditor) EditorUtils.openEditor("gef4Editor", folder, fileNames, fileNames[1], LibraryEditor.EDITOR_ID);

		Assert.assertNotNull(libraryEditor);

		IViewer viewer = libraryEditor.getViewer();

		List<IContentPart<? extends Node>> viewerContents = viewer.getRootPart().getContentPartChildren();

		NotationDiagramContentPart diagramPart = (NotationDiagramContentPart) viewerContents.get(0);

		List<IVisualPart<? extends Node>> diagramContents = diagramPart.getChildrenUnmodifiable();

		Assert.assertEquals("The diagram doesn't contain the expected number of elements", 4, diagramContents.size());
		for (IVisualPart<? extends Node> visualChild : diagramContents) {
			View notationElement = visualChild.getAdapter(View.class);
			EObject semanticElement = visualChild.getAdapter(EObject.class);

			Assert.assertNotNull(notationElement);
			Assert.assertEquals(semanticElement, notationElement.getElement());
		}
	}
}
