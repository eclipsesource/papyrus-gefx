package org.eclipse.papyrus.gef4.example.library.editor.palette;

import javax.inject.Inject;

import org.eclipse.papyrus.gef4.palette.Palette;
import org.eclipse.papyrus.gef4.tools.CreateElementTool;
import org.eclipse.papyrus.gef4.tools.Tool;
import org.eclipse.papyrus.gef4.tools.ToolManager;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class LibraryPalette implements Palette {

	@Inject
	private ToolManager toolManager;

	@Override
	public Node createPaletteControl() {
		VBox palette = new VBox();
		Button createPerson = new Button("Create Person");
		Button createLibrary = new Button("Create Library");

		createPerson.setOnAction(event -> toolManager.setTool(createPersonTool()));
		createLibrary.setOnAction(event -> toolManager.setTool(createLibraryTool()));

		palette.getChildren().addAll(createPerson, createLibrary);

		return palette;
	}

	/**
	 * @return
	 */
	private Tool createLibraryTool() {
		return (CreateElementTool) () -> "example.library.library";
	}

	/**
	 * @return
	 */
	private Tool createPersonTool() {
		return (CreateElementTool) () -> "example.library.person";
	}

}
