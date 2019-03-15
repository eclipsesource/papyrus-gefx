/*****************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.gefx.example.library.tests;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.example.library.module.LibraryModule;
import org.eclipse.papyrus.gef4.gmf.module.NotationDiagramModule;
import org.eclipse.papyrus.gef4.gmf.parts.NotationDiagramContentPart;
import org.eclipse.papyrus.gef4.gmf.parts.ShapeContentPart;
import org.eclipse.papyrus.gef4.module.GEFFxModule;
import org.eclipse.papyrus.gefx.gmf.tests.AbstractDiagramTest;
import org.eclipse.papyrus.gefx.gmf.tests.util.DiagramUtils;
import org.junit.Test;

import com.google.inject.Module;
import com.google.inject.util.Modules;

import javafx.scene.Node;

public class LibraryDiagramTest extends AbstractDiagramTest {

	@Test
	public void testOpenDiagram() throws Exception {
		IViewer viewer = diagramViewer.getViewer();

		List<IContentPart<? extends Node>> viewerContents = viewer.getRootPart().getContentPartChildren();

		NotationDiagramContentPart diagramPart = (NotationDiagramContentPart) viewerContents.get(0);

		List<IVisualPart<? extends Node>> diagramContents = diagramPart.getChildrenUnmodifiable();

		assertEquals("The diagram doesn't contain the expected number of elements", 4, diagramContents.size());
		for (IVisualPart<? extends Node> visualChild : diagramContents) {
			View notationElement = visualChild.getAdapter(View.class);
			EObject semanticElement = visualChild.getAdapter(EObject.class);

			assertNotNull(notationElement);
			assertEquals(semanticElement, notationElement.getElement());
			
			assertThat(visualChild, instanceOf(ShapeContentPart.class));
		}
	}

	@Override
	protected Module getModule() throws Exception {
		String folder = "platform:/plugin/org.eclipse.papyrus.gefx.example.library.tests/models/";
		String[] fileNames = new String[] {
				"Library.library",
				"Library.librarydiagram"
		};
		
		String diagramFile = fileNames[1];
		Diagram diagram = DiagramUtils.loadModel("library.test", folder, fileNames, diagramFile);
		
		return Modules.override(
				Modules.override(
						new GEFFxModule())
						.with(new LibraryModule(), new NotationDiagramModule(diagram)))
								.with(getThreadSyncModule());
	}

	
}
