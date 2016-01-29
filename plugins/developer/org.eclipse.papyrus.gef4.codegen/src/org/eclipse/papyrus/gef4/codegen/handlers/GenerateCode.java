/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation based on GMF-T generator
 *****************************************************************************/
package org.eclipse.papyrus.gef4.codegen.handlers;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmf.codegen.gmfgen.GenChildLabelNode;
import org.eclipse.gmf.codegen.gmfgen.GenChildNode;
import org.eclipse.gmf.codegen.gmfgen.GenChildSideAffixedNode;
import org.eclipse.gmf.codegen.gmfgen.GenCommonBase;
import org.eclipse.gmf.codegen.gmfgen.GenCompartment;
import org.eclipse.gmf.codegen.gmfgen.GenDiagram;
import org.eclipse.gmf.codegen.gmfgen.GenEditorGenerator;
import org.eclipse.gmf.codegen.gmfgen.GenExternalNodeLabel;
import org.eclipse.gmf.codegen.gmfgen.GenLink;
import org.eclipse.gmf.codegen.gmfgen.GenLinkLabel;
import org.eclipse.gmf.codegen.gmfgen.GenNode;
import org.eclipse.gmf.codegen.gmfgen.GenNodeLabel;
import org.eclipse.gmf.codegen.gmfgen.GenTopLevelNode;
import org.eclipse.gmf.common.UnexpectedBehaviourException;
import org.eclipse.gmf.internal.common.codegen.Messages;
import org.eclipse.gmf.internal.common.codegen.OrganizeImportsPostprocessor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.gef4.codegen.Activator;
import org.eclipse.papyrus.gef4.codegen.emitters.AffixedLabelEditPartEmitter;
import org.eclipse.papyrus.gef4.codegen.emitters.AffixedNodeEditPartEmitter;
import org.eclipse.papyrus.gef4.codegen.emitters.CompartmentEditPartEmitter;
import org.eclipse.papyrus.gef4.codegen.emitters.DiagramEditPartEmitter;
import org.eclipse.papyrus.gef4.codegen.emitters.IEmitter;
import org.eclipse.papyrus.gef4.codegen.emitters.InnerLabelEditPartEmitter;
import org.eclipse.papyrus.gef4.codegen.emitters.LabelNodeEditPartEmitter;
import org.eclipse.papyrus.gef4.codegen.emitters.LinkEditPartEmitter;
import org.eclipse.papyrus.gef4.codegen.emitters.NodeEditPartEmitter;
import org.eclipse.papyrus.gef4.codegen.emitters.VisualPartProviderEmitter;
import org.eclipse.papyrus.papyrusgmfgenextension.VisualIDOverride;
import org.eclipse.swt.widgets.Display;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.handlers.HandlerUtil;

public class GenerateCode extends AbstractHandler {

	protected IProgressMonitor progressMonitor;

	protected IPackageFragmentRoot destRoot;

	protected IProject destProject;

	protected OrganizeImportsPostprocessor importsPostprocessor;

	protected CodeFormatter codeFormatter;

	protected IStatus myRunStatus;

	protected HashSet<GenTopLevelNode> topNodes;

	protected HashMap<String, GenCommonBase> visualIDToPartMap;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ResourceSet resourceSet = new ResourceSetImpl();
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (!(currentSelection instanceof IStructuredSelection) || currentSelection.isEmpty()) {
			return null;
		}

		final IStructuredSelection selection = (IStructuredSelection) currentSelection;
		Object selectedElement = selection.getFirstElement();

		if (selectedElement instanceof IFile) {

			if (((IFile) selectedElement).getFileExtension().equals("gmfgen")) {

				String selectedFilePath = ((IFile) selectedElement).getFullPath().toString();

				final Resource inputResource = resourceSet.getResource(URI.createPlatformResourceURI(selectedFilePath, true), true);

				if (inputResource != null) {
					ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(Display.getCurrent().getActiveShell());



					progressMonitor = new NullProgressMonitor();

					try {
						progressMonitorDialog.run(true, true, new IRunnableWithProgress() {

							@Override
							public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
								try {
									generateCode(inputResource);
									myRunStatus = new Status(IStatus.OK, Activator.PLUGIN_ID, 0, "Generation Completed", null);
								} catch (InterruptedException e) {
									myRunStatus = new Status(IStatus.CANCEL, Activator.PLUGIN_ID, 0, "Generation interrupted", e);
								} catch (UnexpectedBehaviourException e) {
									myRunStatus = new Status(IStatus.CANCEL, Activator.PLUGIN_ID, 0, "Generation interrupted", e);
								}
							}
						});


					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						if (myRunStatus.getSeverity() == IStatus.CANCEL && myRunStatus.getException() instanceof InterruptedException) {
							throw new ExecutionException("Execption during generation", myRunStatus.getException());
						}
						e.printStackTrace();
					} finally {
						progressMonitor.done();
					}

				}
			}

		}
		return null;
	}

	protected void genLabelNode(GenChildLabelNode eObject) throws InterruptedException {
		generateJava(new LabelNodeEditPartEmitter(), eObject.getEditPartQualifiedClassName(), eObject);
		visualIDToPartMap.put(getVisualID(eObject), eObject);
		processCompartments(eObject);
		processLabels(eObject);
	}

	protected void genAffixedNode(GenChildSideAffixedNode eObject) throws InterruptedException {
		generateJava(new AffixedNodeEditPartEmitter(), eObject.getEditPartQualifiedClassName(), eObject);
		visualIDToPartMap.put(getVisualID(eObject), eObject);
		processCompartments(eObject);
		processLabels(eObject);
	}

	protected void genLink(GenLink eObject) throws InterruptedException {
		generateJava(new LinkEditPartEmitter(), eObject.getEditPartQualifiedClassName(), eObject);
		visualIDToPartMap.put(getVisualID(eObject), eObject);
		processLabels(eObject);
	}

	protected void processLabels(GenLink eObject) throws InterruptedException {
		for (GenLinkLabel label : eObject.getLabels()) {
			genAffixedLabel(label);
		}
	}

	protected void genCompartment(GenCompartment eObject) throws InterruptedException {
		generateJava(new CompartmentEditPartEmitter(), eObject.getEditPartQualifiedClassName(), eObject);
		visualIDToPartMap.put(getVisualID(eObject), eObject);
	}

	protected void genAffixedLabel(GenCommonBase eObject) throws InterruptedException {
		generateJava(new AffixedLabelEditPartEmitter(), eObject.getEditPartQualifiedClassName(), eObject);
		visualIDToPartMap.put(getVisualID(eObject), eObject);
	}

	protected void genInnerLabel(GenCommonBase eObject) throws InterruptedException {
		generateJava(new InnerLabelEditPartEmitter(), eObject.getEditPartQualifiedClassName(), eObject);
		visualIDToPartMap.put(getVisualID(eObject), eObject);
	}

	protected void processLabels(GenNode eObject) throws InterruptedException {
		for (GenNodeLabel label : eObject.getLabels()) {
			if (label instanceof GenExternalNodeLabel) {
				genAffixedLabel(label);
			} else {
				genInnerLabel(label);
			}
		}
	}

	protected String getVisualID(GenCommonBase eObject) {
		// Check if the GMF Tooling visualID (Integer) has been overridden with a String ID (Papyrus 2.0)
		VisualIDOverride override = getOverride(eObject);
		if (override != null) {
			return override.getVisualID();
		}
		// Default case
		return Integer.toString(eObject.getVisualID());
	}

	protected VisualIDOverride getOverride(GenCommonBase targetElement) {
		for (EObject rootElement : targetElement.eResource().getContents()) {
			if (rootElement instanceof VisualIDOverride) {
				VisualIDOverride override = (VisualIDOverride) rootElement;
				return getOverride(targetElement, override);
			}
		}
		return null;
	}

	protected VisualIDOverride getOverride(GenCommonBase targetElement, VisualIDOverride rootElement) {
		if (rootElement.getGenView() == targetElement) {
			return rootElement;
		}

		for (VisualIDOverride childOverride : rootElement.getChild()) {
			VisualIDOverride override = getOverride(targetElement, childOverride);
			if (override != null) {
				return override;
			}
		}

		return null;
	}

	protected void genNode(GenNode eObject) throws InterruptedException {
		generateJava(new NodeEditPartEmitter(), eObject.getEditPartQualifiedClassName(), eObject);
		visualIDToPartMap.put(getVisualID(eObject), eObject);

		processCompartments(eObject);
		processLabels(eObject);
	}

	protected void processCompartments(GenNode eObject) throws InterruptedException {
		for (GenCompartment genCompartment : eObject.getCompartments()) {
			genCompartment(genCompartment);
		}
	}



	protected void genDiagram(GenDiagram eObject) throws InterruptedException {
		generateJava(new DiagramEditPartEmitter(), eObject.getEditPartQualifiedClassName(), eObject);
		visualIDToPartMap.put(eObject.getEditorGen().getModelID(), eObject);

	}

	protected Collection<GenTopLevelNode> getTopNodes(Resource resource) {
		if (topNodes.isEmpty()) {
			TreeIterator<EObject> it = resource.getAllContents();

			while (it.hasNext()) {
				EObject eObject = it.next();

				if (eObject instanceof GenTopLevelNode) {
					topNodes.add((GenTopLevelNode) eObject);
				}
			}
		}

		return topNodes;
	}

	protected boolean isUsefullChildNode(GenChildNode childNode) {
		GenClass childNodeMetaclass = childNode.getModelFacet().getMetaClass();

		for (GenTopLevelNode genTopLevelNode : getTopNodes(childNode.eResource())) {
			if (genTopLevelNode.getModelFacet().getMetaClass().equals(childNodeMetaclass)) {
				Activator.log.info("Pointless childNode: " + childNode);

				visualIDToPartMap.put(getVisualID(childNode), genTopLevelNode);
				return false;
			}
		}
		return true;
	}

	protected void generateCode(Resource inputResource) throws InterruptedException, UnexpectedBehaviourException {

		topNodes = new HashSet<>();

		visualIDToPartMap = new HashMap<>();

		EObject root = inputResource.getContents().get(0);
		if (root instanceof GenEditorGenerator) {
			GenEditorGenerator editorGen = (GenEditorGenerator) root;


			final Path pluginDirectory = new Path(editorGen.getPluginDirectory());
			initializeEditorProject(pluginDirectory, guessProjectLocation(pluginDirectory.segment(0), editorGen), Collections.<IProject> emptyList());

			TreeIterator<EObject> it = inputResource.getAllContents();

			while (it.hasNext()) {
				EObject eObject = it.next();

				if (eObject instanceof GenTopLevelNode) {
					genNode((GenNode) eObject);
				} else if (eObject instanceof GenChildNode) {
					if (eObject instanceof GenChildLabelNode) {
						genLabelNode((GenChildLabelNode) eObject);

					} else if (eObject instanceof GenChildSideAffixedNode) {
						genAffixedNode((GenChildSideAffixedNode) eObject);

					} else {
						if (isUsefullChildNode((GenChildNode) eObject)) {
							genNode((GenNode) eObject);
						} else {
							// Still keep the recursion
							// FIXME Should be mapped to their respective TopNode to avoid useless duplication (Similar to the isUsefulChildNode method)
							processCompartments((GenNode) eObject);
							processLabels((GenNode) eObject);
						}
					}
				} else if (eObject instanceof GenLink) {
					genLink((GenLink) eObject);
				} else if (eObject instanceof GenDiagram) {
					genDiagram((GenDiagram) eObject);
				}
			}

			generateJava(new VisualPartProviderEmitter(), editorGen.getPackageNamePrefix() + ".providers.VisualPartProvider", editorGen, visualIDToPartMap);
		}
	}



	/**
	 * @see #initializeEditorProject(String, IPath, List)
	 */
	protected final void initializeEditorProject(String pluginId, IPath projectLocation) throws UnexpectedBehaviourException, InterruptedException {
		initializeEditorProject(pluginId, projectLocation, Collections.<IProject> emptyList());
	}

	/**
	 * Delegates to {@link #initializeEditorProject(IPath, IPath, List)}, using plug-in id as workspace project name and
	 * 'src' as Java sources location.
	 *
	 * @param pluginId
	 *            both name of workspace project and plug-in id
	 * @param projectLocation
	 *            {@link IPath} to folder where <code>.project</code> file would reside. Use <code>null</code> to use default workspace location.
	 * @param referencedProjects
	 *            collection of {@link IProject}
	 *
	 */
	protected final void initializeEditorProject(String pluginId, IPath projectLocation, List<IProject> referencedProjects) throws UnexpectedBehaviourException, InterruptedException {
		// not sure if there's any reason to get project's name via IProject (not use pluginId directly), this is just how it was done from 1.1.
		IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject(pluginId);
		initializeEditorProject(new Path('/' + p.getName() + "/src"), projectLocation, referencedProjects);
	}

	/**
	 * @param javaSource
	 *            workspace absolute path to java source folder of the generated project, e.g. '/org.sample.aaa/sources'.
	 * @param projectLocation
	 *            {@link IPath} to folder where <code>.project</code> file would reside. Use <code>null</code> to use default workspace location.
	 * @param referencedProjects
	 *            collection of {@link IProject}
	 * @throws UnexpectedBehaviourException
	 *             something goes really wrong
	 * @throws InterruptedException
	 *             user canceled operation
	 */
	protected final void initializeEditorProject(IPath javaSource, IPath projectLocation, List<IProject> referencedProjects) throws UnexpectedBehaviourException, InterruptedException {
		destProject = ResourcesPlugin.getWorkspace().getRoot().getProject(javaSource.segment(0));
		final int style = org.eclipse.emf.codegen.ecore.Generator.EMF_PLUGIN_PROJECT_STYLE;
		// pluginVariables is NOT used when style is EMF_PLUGIN_PROJECT_STYLE
		final List<?> pluginVariables = null;
		final IProgressMonitor pm = getNextStepMonitor();
		setProgressTaskName(Messages.initproject);

		org.eclipse.emf.codegen.ecore.Generator.createEMFProject(javaSource, projectLocation, referencedProjects, pm, style, pluginVariables);

		try {
			final IJavaProject jp = JavaCore.create(destProject);
			destRoot = jp.findPackageFragmentRoot(javaSource);
			// createEMFProject doesn't create source entry in case project exists and has some classpath entries already,
			// though the folder gets created.
			if (destRoot == null) {
				IClasspathEntry[] oldCP = jp.getRawClasspath();
				IClasspathEntry[] newCP = new IClasspathEntry[oldCP.length + 1];
				System.arraycopy(oldCP, 0, newCP, 0, oldCP.length);
				newCP[oldCP.length] = JavaCore.newSourceEntry(javaSource);
				jp.setRawClasspath(newCP, new NullProgressMonitor());
				destRoot = jp.findPackageFragmentRoot(javaSource);
			}
		} catch (JavaModelException ex) {
			throw new UnexpectedBehaviourException(ex.getMessage());
		}
		if (destRoot == null) {
			throw new UnexpectedBehaviourException("no source root can be found");
		}
	}

	protected IProgressMonitor getNextStepMonitor() throws InterruptedException {
		if (progressMonitor.isCanceled()) {
			throw new InterruptedException();
		}
		return SubMonitor.convert(progressMonitor, 1);// new SubProgressMonitor(progressMonitor, 1);
	}

	protected void setProgressTaskName(String text) {
		progressMonitor.subTask(text);
	}


	protected OrganizeImportsPostprocessor getImportsPostrocessor() {
		if (importsPostprocessor == null) {
			importsPostprocessor = new OrganizeImportsPostprocessor(true);
		}
		return importsPostprocessor;
	}

	protected CodeFormatter getCodeFormatter() {
		if (codeFormatter == null) {
			codeFormatter = ToolFactory.createCodeFormatter(null);
		}
		return codeFormatter;
	}

	protected final String formatCode(String text) {
		IDocument doc = new Document(text);
		TextEdit edit = getCodeFormatter().format(CodeFormatter.K_COMPILATION_UNIT, doc.get(), 0, doc.get().length(), 0, null);

		try {
			// check if text formatted successfully
			if (edit != null) {
				edit.apply(doc);
				text = doc.get();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return text;
	}

	protected void generateJava(IEmitter emitter, String qualifiedClassName, Object... input) throws InterruptedException {

		if (emitter != null) {
			generateJava(emitter, CodeGenUtil.getPackageName(qualifiedClassName), CodeGenUtil.getSimpleClassName(qualifiedClassName), input);
		}

	}


	protected void generateJava(IEmitter emitter, String packageName, String className, Object... input) throws InterruptedException {
		IProgressMonitor pm = getNextStepMonitor();
		setProgressTaskName(className);
		pm.beginTask(null, 7);
		if (emitter == null) {
			pm.done();
			return;
		}
		try {
			String genText = emitter.generate(SubMonitor.convert(pm, 2), Arrays.asList(input));
			IPackageFragment pf = destRoot.createPackageFragment(packageName, true, SubMonitor.convert(pm, 1));
			ICompilationUnit cu = pf.getCompilationUnit(className + ".java"); //$NON-NLS-1$
			if (cu.exists()) {
				ICompilationUnit workingCopy = null;
				try {
					workingCopy = cu.getWorkingCopy(SubMonitor.convert(pm, 1));
					final String oldContents = workingCopy.getSource();
					IImportDeclaration[] declaredImports = workingCopy.getImports();
					workingCopy.getBuffer().setContents(genText);
					workingCopy.reconcile(ICompilationUnit.NO_AST, false, null, null);
					try {
						// Since we do organizeImports prior to merge, we must ensure imports added manually are known to OrganizeImportsProcessor
						String[] declaredImportsAsStrings = new String[declaredImports.length];
						for (int i = 0; i < declaredImports.length; i++) {
							declaredImportsAsStrings[i] = declaredImports[i].getElementName();
						}
						getImportsPostrocessor().organizeImports(workingCopy, declaredImportsAsStrings, SubMonitor.convert(pm, 1));
					} catch (CoreException e) {
						workingCopy.commitWorkingCopy(true, SubMonitor.convert(pm, 1)); // save to investigate contents
						throw e;
					}
					// genText = mergeJavaCode(oldContents, workingCopy.getSource(), new SubProgressMonitor(pm, 1));
					genText = formatCode(genText);
					if (!genText.equals(oldContents)) {
						workingCopy.getBuffer().setContents(genText);
						workingCopy.reconcile(ICompilationUnit.NO_AST, false, null, null);
						workingCopy.commitWorkingCopy(true, SubMonitor.convert(pm, 1));
					} else {
						// discard changes - would happen in finally, nothing else to do
						pm.worked(1);
					}
				} finally {
					if (workingCopy != null) {
						workingCopy.discardWorkingCopy();
					}
				}
			} else {
				cu = pf.createCompilationUnit(cu.getElementName(), genText, true, SubMonitor.convert(pm, 1));
				getImportsPostrocessor().organizeImports(cu, null, SubMonitor.convert(pm, 1));
				String newContents = formatCode(cu.getSource());
				cu.getBuffer().setContents(newContents);
				cu.save(SubMonitor.convert(pm, 2), true);
			}


		} catch (NullPointerException ex) {
			Activator.log.error(ex);
		} catch (InvocationTargetException ex) {
			Activator.log.error(ex);
		} catch (UnexpectedBehaviourException ex) {
			Activator.log.error(ex);
		} catch (CoreException ex) {
			Activator.log.error(ex);
		} finally {
			pm.done();
		}
	}


	/**
	 * Inspired by GenBaseImpl.EclipseUtil.findOrCreateContainer
	 * Although later (with EMF API adopting Platform changes) we might need to return URI here
	 *
	 * @return path suitable for IProjectDescription, or <code>null</code> to indicate use of default
	 * @throws UnexpectedBehaviourException
	 * @throws CoreException
	 */
	protected final IPath guessNewProjectLocation(Path examplaryProjectPath, String newProjectName) throws UnexpectedBehaviourException {
		assert newProjectName != null;
		try {
			if (ResourcesPlugin.getWorkspace().getRoot().getProject(newProjectName).exists()) {
				// just use whatever already specified.
				// Returned value doesn't make sense in this case -
				// oee.codegen.ecore.Generator#EclipseHelper#createEMFProject doesn't use it then.
				return null;
			}
			if (examplaryProjectPath == null || !examplaryProjectPath.isAbsolute()) {
				return null;
			}
			IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject(examplaryProjectPath.segment(0));
			if (!p.exists()) {
				return null;
			}
			java.net.URI locationURI = p.getDescription().getLocationURI();
			// org.eclipse.core.internal.utils.FileUtil#toPath
			if (locationURI == null) {
				return null;
			}
			if (locationURI.getScheme() != null && !"file".equals(locationURI.getScheme())) {
				return null;
			}
			return new Path(locationURI.getSchemeSpecificPart()).removeLastSegments(1).append(newProjectName);
		} catch (CoreException ex) {
			throw new UnexpectedBehaviourException(ex);
		}

	}

	protected IPath guessProjectLocation(String projectName, GenEditorGenerator editorGen) throws UnexpectedBehaviourException {
		if (editorGen.getDomainGenModel() == null) {
			return null;
		}
		Path modelProjectPath = new Path(editorGen.getDomainGenModel().getModelDirectory());
		return guessNewProjectLocation(modelProjectPath, projectName);
	}
}