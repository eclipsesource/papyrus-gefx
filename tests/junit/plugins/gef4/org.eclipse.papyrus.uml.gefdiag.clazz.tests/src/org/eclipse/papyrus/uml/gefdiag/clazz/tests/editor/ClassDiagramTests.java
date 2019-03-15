package org.eclipse.papyrus.uml.gefdiag.clazz.tests.editor;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.module.NotationDiagramModule;
import org.eclipse.papyrus.gef4.module.GEFFxModule;
import org.eclipse.papyrus.gef4.parts.DiagramContentPart;
import org.eclipse.papyrus.gefx.gmf.tests.AbstractDiagramTest;
import org.eclipse.papyrus.gefx.gmf.tests.util.DiagramUtils;
import org.eclipse.papyrus.uml.gefdiag.clazz.module.ClassDiagramModule;
import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Module;
import com.google.inject.util.Modules;

import javafx.scene.Node;

public class ClassDiagramTests extends AbstractDiagramTest {
	@Test
	public void testOpenEditor() throws Exception {
		List<IContentPart<? extends Node>> viewerContents = diagramViewer.getViewer().getRootPart().getContentPartChildren();

		DiagramContentPart<?> diagramPart = (DiagramContentPart<?>) viewerContents.get(0);

		List<IVisualPart<? extends Node>> diagramContents = diagramPart.getChildrenUnmodifiable();

		Assert.assertEquals("The diagram doesn't contain the expected number of elements", 3, diagramContents.size());
		for (IVisualPart<? extends Node> visualChild : diagramContents) {
			View notationElement = visualChild.getAdapter(View.class);
			EObject semanticElement = visualChild.getAdapter(EObject.class);

			Assert.assertNotNull(notationElement);
			Assert.assertEquals(semanticElement, notationElement.getElement());
		}
	}

	@Override
	protected Module getModule() throws Exception {
		String folder = "platform:/plugin/org.eclipse.papyrus.uml.gefdiag.clazz.tests/models/";
		String[] fileNames = new String[] {
				"umlClasses.uml",
				"umlClasses.notation"
		};

		String diagramFile = fileNames[1];
		Diagram diagram = DiagramUtils.loadModel("library.test", folder, fileNames, diagramFile);

		return Modules.override(
				Modules.override(
						new GEFFxModule())
						.with(new ClassDiagramModule(), new NotationDiagramModule(diagram)))
				.with(getThreadSyncModule());
	}
}
