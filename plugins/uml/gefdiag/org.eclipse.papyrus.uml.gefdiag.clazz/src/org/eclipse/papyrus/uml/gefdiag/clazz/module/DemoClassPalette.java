/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (EclipseSource) cletavernier@eclipsesource.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.gefdiag.clazz.module;

import java.util.Arrays;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.gef4.palette.Palette;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SemanticElementAdapter;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DemoClassPalette implements Palette {
	@Inject
	private ElementTypeRegistry registry;

	@Inject
	private Diagram diagram;

	@Inject
	private IClientContext clientContext;

	@Override
	public Node createPaletteControl() {
		VBox palette = new VBox();

		Label paletteLabel = new Label("Palette");

		Button doCreate = new Button("Create class");
		doCreate.setOnAction(event -> doCreate());

		palette.getChildren().addAll(paletteLabel, doCreate);

		return palette;
	}

	private void doCreate() {
		// WiP TODO:
		// - Find a container
		// - Sample creation (Mostly hard-coded) of a Class in the diagram
		// - This should still go through the Command Stack and Element Type framework as much as possible (At least for the semantic create and view provider)

		IElementType[] elementTypes = registry.getElementTypes(clientContext);
		IElementType classType = Arrays.stream(elementTypes) //
				.filter(t -> "org.eclipse.papyrus.umldi.Class_Shape".equals(t.getId())) //
				.findFirst().orElse(null);

		EObject container = diagram.getElement();

		PreferencesHint clazzHint = new PreferencesHint("org.eclipse.papyrus.uml.diagram.clazz");

		CreateElementRequest createElementRequest = new CreateElementRequest(classType);
		createElementRequest.setContainer(container);

		final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(diagram);
		if (provider != null) {
			ICommand createCommand = provider.getEditCommand(createElementRequest);
			if (createCommand.canExecute()) {
				EditingDomain editingDomainFor = AdapterFactoryEditingDomain.getEditingDomainFor(diagram);
				editingDomainFor.getCommandStack().execute(new GMFtoEMFCommandWrapper(createCommand));
				RecordingCommand command = new RecordingCommand((TransactionalEditingDomain) editingDomainFor, "Create clazz") {
					@Override
					protected void doExecute() {
						String hint = (classType instanceof IHintedType) ? ((IHintedType) classType).getSemanticHint() : null;
						ViewService.getInstance().createNode(new SemanticElementAdapter((EObject) createCommand.getCommandResult().getReturnValue(), classType), diagram, hint, -1, clazzHint);
					}
				};
				editingDomainFor.getCommandStack().execute(command);
			}
		}
	}

}
