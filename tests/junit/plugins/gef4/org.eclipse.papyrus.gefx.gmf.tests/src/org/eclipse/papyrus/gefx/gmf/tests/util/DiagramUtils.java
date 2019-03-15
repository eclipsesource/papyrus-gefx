package org.eclipse.papyrus.gefx.gmf.tests.util;

import static org.hamcrest.Matchers.instanceOf;

import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.junit.Assert;

public class DiagramUtils {

	public static Diagram loadModel(String projectName, String folder, String[] sourceFileNames, String diagramFile) throws Exception {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		project.create(new NullProgressMonitor());
		project.open(new NullProgressMonitor());

		for (String sourceFileName : sourceFileNames) {
			URL url = new URL(folder + "/" + sourceFileName);
			FilesUtils.copyFiles(project, sourceFileName, url);
		}
		
		IFile libraryDiagram = project.getFile(diagramFile);
		URI workspaceURI = URI.createPlatformResourceURI(libraryDiagram.getFullPath().toString(), true); 
		
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource diagramResource = resourceSet.getResource(workspaceURI, true);
		EObject rootElement = diagramResource.getContents().get(0);
		
		TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain(resourceSet);
		Assert.assertThat("The first root element of the diagram resource must be a Diagram", rootElement, instanceOf(Diagram.class));
		return (Diagram) rootElement;
	}

}
