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
package org.eclipse.papyrus.gef4.parts;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.NamedStyleProperties;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class DiagramContentPart extends NotationContentPart<Diagram, Pane> {

	private Label frame;

	public DiagramContentPart(Diagram view) {
		super(view);
	}

	@Override
	protected Pane doCreateVisual() {
		Pane group = new Pane();

		frame = new Label();
		group.getChildren().add(frame);

		return group;
	}

	@Override
	protected boolean childrenChanged(Notification msg) {
		if (msg.getNotifier() != getView()) {
			return false;
		}

		if (msg.getFeature() == NotationPackage.Literals.DIAGRAM__PERSISTED_EDGES || msg.getFeature() == NotationPackage.Literals.DIAGRAM__TRANSIENT_EDGES) {
			return true;
		}

		return super.childrenChanged(msg);
	}

	@Override
	protected void refreshVisualInTransaction(Pane visual) {
		super.refreshVisualInTransaction(visual);

		boolean displayFrame = NotationUtils.getBooleanValue(getView(), NamedStyleProperties.DISPLAY_HEADER, false);

		if (displayFrame) {
			frame.setText(getElement().eClass().getName() + "\n" + getView().getType());
			frame.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, null, null, null)));
			frame.setPadding(new Insets(3));

			visual.setPadding(new Insets(5));

			visual.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, null, null, null)));

			visual.setLayoutX(10);
			visual.setLayoutY(10);

			visual.setMinSize(600, 768);
			visual.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			visual.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		} else {
			visual.setBorder(null);
			visual.setPadding(new Insets(0));

			visual.setLayoutX(0);
			visual.setLayoutY(0);

			visual.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			visual.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			visual.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		}

		frame.setManaged(displayFrame);
		frame.setVisible(displayFrame);
	}

	@Override
	protected void doRemoveChildVisual(IVisualPart<? extends Node> child, int index) {
		Node childVisual = child.getVisual();
		if (childVisual == null) {
			return;
		}

		getVisual().getChildren().remove(childVisual);
	}

	@Override
	protected void doAddChildVisual(IVisualPart<? extends Node> child, int index) {
		if (child.getVisual() != null) {
			getVisual().getChildren().add(child.getVisual());
		}
	}

	@Override
	protected String getStyleClass() {
		return "diagram";
	}

}
