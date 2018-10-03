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

import java.net.URL;
import java.util.Arrays;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.fx.core.command.Command;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.gef4.palette.PaletteDescriptor;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SemanticElementAdapter;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

import javafx.beans.binding.ObjectExpression;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Basic Demo implementation
// TODO Support various action tools (CreateNode, CreateEdge, Delete...)
public class SimpleClassPalette implements PaletteDescriptor {

	private final ListProperty<Drawer> drawers = new SimpleListProperty<>(FXCollections.observableArrayList());

	@Inject
	public SimpleClassPalette() {
		drawers.addAll(createNodesDrawer(), createEdgesDrawer());
	}

	private Drawer createNodesDrawer() {
		ObservableList<ChildElement> nodes = FXCollections.observableArrayList();
		// Top level Nodes
		nodes.add(new NodeToolEntry("org.eclipse.papyrus.umldi.Class_Shape", "Class", null));
		nodes.add(new NodeToolEntry("org.eclipse.papyrus.umldi.Interface_Shape", "Interface", null));
		nodes.add(new NodeToolEntry("org.eclipse.papyrus.umldi.Enumeration_Shape", "Enumeration", null));
		nodes.add(new NodeToolEntry("org.eclipse.papyrus.umldi.Comment_Shape", "Comment", null));
		nodes.add(new NodeToolEntry("org.eclipse.papyrus.umldi.Package_Shape", "Package", null));
		// Minor items
		nodes.add(new SeparatorImpl());
		nodes.add(new NodeToolEntry("org.eclipse.papyrus.umldi.DataType_Shape", "Data Type", null));
		nodes.add(new NodeToolEntry("org.eclipse.papyrus.umldi.PrimitiveType_Shape", "Primitive Type", null));
		nodes.add(new NodeToolEntry("org.eclipse.papyrus.umldi.Constraint_PackagedElementShape", "Constraint", null));
		nodes.add(new NodeToolEntry("org.eclipse.papyrus.umldi.InstanceSpecification_Shape", "Instance Specification", null));
		// List items
		nodes.add(new SeparatorImpl());
		nodes.add(new NodeToolEntry("org.eclipse.papyrus.umldi.Property_Shape", "Property", null));
		nodes.add(new NodeToolEntry("org.eclipse.papyrus.umldi.Operation_Shape", "Operation", null));
		nodes.add(new NodeToolEntry("org.eclipse.papyrus.umldi.EnumerationLiteral_Shape", "Enumeration Literal", null));
		nodes.add(new NodeToolEntry("org.eclipse.papyrus.umldi.Slot_SlotLabel", "Slot", null));


		return new Drawer() {

			@Override
			public ObjectExpression<CharSequence> getName() {
				return new SimpleObjectProperty<>("Nodes");
			}

			@Override
			public ObjectExpression<URL> getIconURL() {
				return null;
			}

			@Override
			public ObservableList<ChildElement> getChildren() {
				return nodes;
			}

		};
	}

	private Drawer createEdgesDrawer() {

		ObservableList<ChildElement> edges = FXCollections.observableArrayList();
		edges.add(new EdgeToolEntry("org.eclipse.papyrus.umldi.Association_Edge", "Association", null));
		edges.add(new EdgeToolEntry("org.eclipse.papyrus.umldi.Generalization_Edge", "Generalization", null));
		edges.add(new EdgeToolEntry("org.eclipse.papyrus.umldi.InterfaceRealization_Edge", "Interface Realization", null));
		edges.add(new EdgeToolEntry("org.eclipse.papyrus.umldi.Dependency_Edge", "Dependency", null));
		edges.add(new EdgeToolEntry("org.eclipse.papyrus.umldi.Usage_Edge", "Usage", null));
		edges.add(new SeparatorImpl());
		edges.add(new EdgeToolEntry("org.eclipse.papyrus.umldi.Link_Edge", "Constraint/Comment Link", null));

		return new Drawer() {

			@Override
			public ObjectExpression<CharSequence> getName() {
				return new SimpleObjectProperty<>("Edges");
			}

			@Override
			public ObjectExpression<URL> getIconURL() {
				return null;
			}

			@Override
			public ObservableList<ChildElement> getChildren() {
				return edges;
			}

		};
	}

	@Override
	public String getId() {
		return "org.eclipse.papyrus.uml.gefdiag.clazz.palette";
	}

	@Override
	public String getLabel() {
		return "Class Diagram Palette";
	}

	@Override
	public ObservableList<Drawer> getDrawers() {
		return drawers;
	}

	@Inject
	private ElementTypeRegistry registry;

	@Inject
	private Diagram diagram;

	@Inject
	private IClientContext clientContext;

	@Inject
	private PreferencesHint preferenceHints;

	private void doCreate(String elementId) {
		// WiP TODO:
		// - Find a container
		// - Sample creation (Mostly hard-coded) of a Class in the diagram
		// - This should still go through the Command Stack and Element Type framework as much as possible (At least for the semantic create and view provider)

		IElementType[] elementTypes = registry.getElementTypes(clientContext);
		IElementType classType = Arrays.stream(elementTypes) //
				.filter(t -> elementId.equals(t.getId())) //
				.findFirst().orElse(null);

		EObject container = diagram.getElement();

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
						ViewService.getInstance().createNode(new SemanticElementAdapter((EObject) createCommand.getCommandResult().getReturnValue(), classType), diagram, hint, -1, preferenceHints);
					}
				};
				editingDomainFor.getCommandStack().execute(command);
			}
		}
	}

	private class SeparatorImpl implements Separator {
		// Nothing
	}

	private class NodeToolEntry implements ToolEntry {

		private String elementTypeId;
		private String label;
		private URL icon;

		public NodeToolEntry(String elementTypeId, String label, URL icon) {
			this.elementTypeId = elementTypeId;
			this.label = label;
			this.icon = icon;
		}

		@Override
		public ObjectExpression<CharSequence> getName() {
			return new SimpleObjectProperty<>(this.label);
		}

		@Override
		public ObjectExpression<URL> getIconURL() {
			return icon == null ? null : new SimpleObjectProperty<>(icon);
		}

		@Override
		public Command<Void> getCommand() {
			return Command.createCommand(() -> {
				doCreate(elementTypeId);
			});
		}
	}

	private class EdgeToolEntry implements ToolEntry {

		private String elementTypeId;
		private String label;
		private URL icon;

		public EdgeToolEntry(String elementTypeId, String label, URL icon) {
			this.elementTypeId = elementTypeId;
			this.label = label;
			this.icon = icon;
		}

		@Override
		public ObjectExpression<CharSequence> getName() {
			return new SimpleObjectProperty<>(label);
		}

		@Override
		public ObjectExpression<URL> getIconURL() {
			return icon == null ? null : new SimpleObjectProperty<>(icon);
		}

		/**
		 * @see org.eclipse.papyrus.gef4.palette.PaletteDescriptor.ToolEntry#getCommand()
		 *
		 * @return
		 */
		@Override
		public Command<Void> getCommand() {
			return Command.createCommand(() -> {
				System.out.println("Edges not supported yet");
			});
		}
	}

}
