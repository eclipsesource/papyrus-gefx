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
package org.eclipse.papyrus.gef4.palette;

import java.net.URL;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.eclipse.fx.core.bindings.FXBindings;
import org.eclipse.fx.core.command.Command;
import org.eclipse.fx.core.observable.FXObservableUtil;
import org.eclipse.papyrus.gef4.palette.PaletteDescriptor.ChildElement;
import org.eclipse.papyrus.gef4.palette.PaletteDescriptor.Drawer;
import org.eclipse.papyrus.gef4.palette.PaletteDescriptor.Separator;
import org.eclipse.papyrus.gef4.palette.PaletteDescriptor.Stack;
import org.eclipse.papyrus.gef4.palette.PaletteDescriptor.ToolEntry;

import javafx.beans.property.BooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

/**
 * The default implementation of {@link PaletteRenderer}. The contents are provided by a PaletteDescriptor
 */
public class DefaultPaletteRenderer implements PaletteRenderer {

	public static final PseudoClass ACTIVE_ENTRY = PseudoClass.getPseudoClass("active");

	private PaletteDescriptor paletteDescriptor;

	@Inject
	public void setPaletteDescriptor(PaletteDescriptor descriptor) {
		this.paletteDescriptor = descriptor;
	}

	@Override
	public Node createPaletteControl() {
		VBox paletteRoot = new VBox();

		Node paletteHeader = createHeader();
		VBox paletteContents = new VBox();

		FXBindings.bindContent(paletteContents.getChildren(), paletteDescriptor.getDrawers(), DrawerNode::new);

		paletteRoot.getChildren().addAll(paletteHeader, paletteContents);
		paletteRoot.getStylesheets().add(getClass().getResource("palette.css").toExternalForm());

		return paletteRoot;
	}

	private Node createHeader() {
		Label header = new Label("Palette");
		header.getStyleClass().add("header");
		header.setGraphic(new ImageView("platform:/plugin/org.eclipse.papyrus.gef4/icons/palette_view.gif"));
		return header;
	}

	private class DrawerNode extends TitledPane {

		public DrawerNode(Drawer drawer) {
			textProperty().bind(drawer.getName().asString());

			// TODO Palettes aren't so big that we need a ListView renderer.
			// Replace with ScrollPane + VBox, which gives more flexibility
			ListView<ChildElement> drawerContents = new ListView<>();

			drawerContents.setCellFactory(ChildElementCell::new);

			drawerContents.getItems().setAll(drawer.getChildren().stream().flatMap(this::flattenChildren).collect(Collectors.toList()));

			setContent(drawerContents);
		}

		// TODO Properly support stack. For now we just bypass the stack container and directly
		// return its children
		private Stream<? extends ChildElement> flattenChildren(ChildElement childElement) {
			if (childElement instanceof Stack) {
				return ((Stack) childElement).getChildren().stream();
			}
			return Stream.of(childElement);
		}
	}

	private class ChildElementCell extends ListCell<ChildElement> {

		public ChildElementCell(ListView<ChildElement> listView) {
			// Nothing
		}

		@Override
		protected void updateItem(ChildElement item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				setGraphic(null);
				setText(null);
				return;
			}

			if (item instanceof Separator) {
				setGraphic(new javafx.scene.control.Separator());
			} else if (item instanceof ToolEntry) {
				Button toolButton = new Button();
				ToolEntry toolEntry = (ToolEntry) item;
				URL iconUrl = toolEntry.getIconURL() == null ? null : toolEntry.getIconURL().get();
				if (iconUrl != null) {
					toolButton.setGraphic(new ImageView(iconUrl.toExternalForm()));
				}

				if (toolEntry.getTooltip() != null && toolEntry.getTooltip().getValue() != null && toolEntry.getTooltip().getValue().length() > 0) {
					toolButton.setTooltip(new Tooltip(toolEntry.getTooltip().getValue().toString()));
				}

				setGraphic(toolButton);

				Command<Void> command = toolEntry.getCommand();
				if (command == null) {
					toolButton.setDisable(true);
				} else {
					toolButton.disableProperty().bind(command.enabledProperty().not());
					toolButton.setOnAction(event -> command.execute());
				}

				BooleanProperty activeProperty = toolEntry.activeProperty();
				FXObservableUtil.onChange(activeProperty, activeState -> toolButton.pseudoClassStateChanged(ACTIVE_ENTRY, activeState));
				toolButton.pseudoClassStateChanged(ACTIVE_ENTRY, activeProperty.get());

				toolButton.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
					if (event.getCode() == KeyCode.ESCAPE) {
						activeProperty.set(false);
					}
				});

				toolButton.textProperty().bind(((ToolEntry) item).getName().asString());
			}
		}
	}

}
