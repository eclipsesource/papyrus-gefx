/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gefdiag.common.part.uml;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gefdiag.common.part.IPrimaryContentPart;
import org.eclipse.papyrus.infra.gefdiag.common.part.NotationContentPart;
import org.eclipse.papyrus.infra.tools.util.StringHelper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class InterfaceContentPart extends NotationContentPart<Shape, Parent>implements IPrimaryContentPart {

	private String currentURL = null;

	public InterfaceContentPart(Shape view) {
		super(view);
	}

	@Override
	protected Parent doCreateVisual() {
		try {
			URL url = new URL("platform:/plugin/org.eclipse.papyrus.infra.gefdiag.common/icons/papyrus-origin.fxml");
			Parent fxml = FXMLLoader.load(url);
			return fxml;
		} catch (MalformedURLException e) {
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void doRefreshVisual(Parent visual) {
		super.doRefreshVisual(visual);
		refreshBounds();

		visual.setOnMouseDragEntered(me -> {
			System.out.println("drag");
		});

		String newURL = getURL();
		if (StringHelper.equals(newURL, currentURL)) {
			return;
		}

		// System.out.println(visual);
		currentURL = newURL;

		// visual.setBlendMode(BlendMode.MULTIPLY);

		// WebEngine engine = visual.getEngine();
		/*
		 * engine.documentProperty().addListener(new ChangeListener<Document>() {
		 *
		 * @Override
		 * public void changed(ObservableValue<? extends Document> observable, Document oldValue, Document newValue) {
		 * if (newValue != null) {
		 * ((EventTarget) newValue).addEventListener("click", new EventListener() {
		 *
		 * @Override
		 * public void handleEvent(Event evt) {
		 * System.out.println("Click");
		 * }
		 *
		 * }, false);
		 * }
		 * }
		 *
		 * });
		 */

		// engine.load(newURL);


		// engine.documentProperty().addListener(cl -> {
		// System.out.println("Loaded");
		// System.out.println(visual.getChildrenUnmodifiable());
		// });

		/*
		 * visual.addEventHandler(MouseEvent.MOUSE_PRESSED, me -> {
		 * System.out.println("CC");
		 * InterfaceContentPart.this.getAdapter(FXClickDragTool.CLICK_TOOL_POLICY_KEY).click(me);
		 * });
		 */

		// visual.addEventHandler(MouseEvent.MOUSE_DRAGGED, mg -> {
		// InterfaceContentPart.this.getAdapter(FXClickDragTool.DRAG_TOOL_POLICY_KEY).drag(mg);
		// });

		/*
		 * visual.addEventHandler(MouseEvent.MOUSE_DRAGGED, me -> {
		 * InterfaceContentPart.this.getAdapter(FXClickDragTool.DRAG_TOOL_POLICY_KEY).drag(me);
		 * });
		 *
		 * visual.addEventHandler(MouseEvent.MOUSE_PRESSED, me -> {
		 * InterfaceContentPart.this.getAdapter(FXClickDragTool.CLICK_TOOL_POLICY_KEY).click(me);
		 * });
		 */


	}

	protected String getURL() {
		// return "platform:/plugin/org.eclipse.papyrus.uml.diagram.common/icons/symbols/actor.svg";
		// return "platform:/plugin/org.eclipse.papyrus.infra.gefdiag.common/icons/TUX-G2-SVG.svg";
		// return "platform:/plugin/org.eclipse.papyrus.infra.gefdiag.common/icons/svgInHtml.html";
		return "platform:/plugin/org.eclipse.papyrus.infra.gefdiag.common/icons/papyrus-icone.svg";
	}

	protected void refreshBounds() {
		Parent node = getVisual();

		node.setLayoutX(getX());
		node.setLayoutY(getY());

		// node.setMinHeight(getHeight());
		// node.setMinWidth(getWidth());


		node.setScaleX(0.35);
		node.setScaleY(0.35);
		// node.setMaxSize(200, 200);
	}

	@Override
	public List<View> getContentChildren() {
		return Collections.emptyList();
	}

	@Override
	protected String getStyleClass() {
		return "svgImage";
	}

}
