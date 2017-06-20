package org.eclipse.papyrus.uml.gefdiag.clazz.tests.editor;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.parts.DiagramContentPart;
import org.eclipse.papyrus.gef4.tests.utils.EditorUtils;
import org.junit.Assert;
import org.junit.Test;

import javafx.scene.Node;

public class EditorTests {
	@Test
	public void testOpenEditor() throws Exception {

		String folder = "platform:/plugin/org.eclipse.papyrus.uml.gefdiag.clazz.tests/models/";
		String[] fileNames = new String[] {
				"umlClasses.uml",
				"umlClasses.notation"
		};

		ClazzStandaloneEditor libraryEditor = (ClazzStandaloneEditor) EditorUtils.openEditor("gef4ClassEditor", folder, fileNames, fileNames[1], ClazzStandaloneEditor.ID);

		Assert.assertNotNull(libraryEditor);

		IViewer viewer = libraryEditor.getViewer();

		List<IContentPart<? extends Node>> viewerContents = viewer.getRootPart().getContentPartChildren();

		DiagramContentPart diagramPart = (DiagramContentPart) viewerContents.get(0);

		List<IVisualPart<? extends Node>> diagramContents = diagramPart.getChildrenUnmodifiable();

		Assert.assertEquals("The diagram doesn't contain the expected number of elements", 3, diagramContents.size());
		for (IVisualPart<? extends Node> visualChild : diagramContents) {
			View notationElement = visualChild.getAdapter(View.class);
			EObject semanticElement = visualChild.getAdapter(EObject.class);

			Assert.assertNotNull(notationElement);
			Assert.assertEquals(semanticElement, notationElement.getElement());
		}
	}
}
