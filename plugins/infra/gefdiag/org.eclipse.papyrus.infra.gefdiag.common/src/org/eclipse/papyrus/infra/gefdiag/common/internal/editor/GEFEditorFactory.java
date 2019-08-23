/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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
package org.eclipse.papyrus.infra.gefdiag.common.internal.editor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageModel;
import org.eclipse.papyrus.infra.gefdiag.common.Activator;
import org.eclipse.papyrus.infra.gefdiag.common.editor.PapyrusGEFxEditor;
import org.eclipse.papyrus.infra.ui.extension.diagrameditor.AbstractEditorFactory;
import org.eclipse.swt.graphics.Image;

import com.google.inject.Module;

public class GEFEditorFactory extends AbstractEditorFactory {

	public static final String EDITOR_TYPE = Activator.PLUGIN_ID + ".gef4Editor"; //$NON-NLS-1$

	public static final String EXTENSION_ID = Activator.PLUGIN_ID + ".diagram"; //$NON-NLS-1$

	private final Map<String, DiagramEditorDescriptor> supportedDiagramTypes;

	public GEFEditorFactory() {
		super(PapyrusGEFxEditor.class, EDITOR_TYPE);
		supportedDiagramTypes = getSupportedDiagramTypes();
	}

	@Override
	public IPageModel createIPageModel(Object pageIdentifier) {
		Diagram diagram = getDiagram(pageIdentifier);
		return new GEFDiagramPageModel(diagram, supportedDiagramTypes.get(diagram.getType()));
	}

	@Override
	public boolean isPageModelFactoryFor(Object pageIdentifier) {
		Diagram diagram = getDiagram(pageIdentifier);
		if (diagram != null) {
			return supportedDiagramTypes.containsKey(diagram.getType());
		}
		return false;
	}

	private Diagram getDiagram(Object pageIdentifier) {
		if (pageIdentifier instanceof Diagram) {
			Diagram diagram = (Diagram) pageIdentifier;
			if (diagram.eIsProxy()) {
				return null;
			}
			return diagram;
		}
		return null;
	}

	private static Map<String, DiagramEditorDescriptor> getSupportedDiagramTypes() {
		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_ID);

		Map<String, DiagramEditorDescriptor> result = new HashMap<>();

		for (IConfigurationElement configElement : config) {
			try {
				String type = configElement.getAttribute("type");

				Object checkModule = configElement.createExecutableExtension("module");
				assert checkModule instanceof Module;

				Supplier<Module> module = () -> {
					try {
						return (Module) configElement.createExecutableExtension("module");
					} catch (CoreException ex) {
						Activator.log().error(ex);
						return null;
					}
				};
				String imagePath = configElement.getAttribute("image");
				String label = configElement.getAttribute("label");

				Assert.isNotNull(type);
				Assert.isNotNull(module);
				Assert.isNotNull(label);

				Image image = null;
				if (imagePath != null) {
					try {
						image = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(configElement.getContributor().getName(), imagePath);
					} catch (Exception ex) {

					}
				}

				if (result.containsKey(type)) {
					Activator.log().warn("Two GEF4 Modules are declared for the type " + type);
				}

				DiagramEditorDescriptor descriptor = new DiagramEditorDescriptor(type, module, image, label);

				result.put(type, descriptor);
			} catch (Throwable t) {
				Activator.log().error("An error occurred while loading a contribution from " + configElement.getContributor(), t);
			}
		}

		return result;
	}

	@Override
	public String getLabel() {
		return "Diagram Editor (GEFx)";
	}
}
