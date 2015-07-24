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
package org.eclipse.papyrus.infra.gefdiag.common.part.uml;


import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.infra.gefdiag.common.part.NodeContentPart;
import org.eclipse.papyrus.infra.gefdiag.common.util.EffectsUtil;

import javafx.scene.layout.VBox;

public class ClassContentPart extends NodeContentPart {

	public ClassContentPart(Shape view) {
		super(view);
	}

	@Override
	public org.eclipse.uml2.uml.Class getElement() {
		EObject element = super.getElement();
		if (element instanceof org.eclipse.uml2.uml.Class) {
			return (org.eclipse.uml2.uml.Class) element;
		}
		return null;
	}

	@Override
	protected void doRefreshVisual(VBox visual) {
		super.doRefreshVisual(visual);

		visual.setEffect(EffectsUtil.createShadowEffect());

		// if (Math.random() > 0.9) {

		// final Timeline timeline = new Timeline();
		// visual.setOpacity(1);
		// timeline.setCycleCount(10);
		// timeline.setAutoReverse(true);
		//
		// // BackgroundFill fill = new BackgroundFill(new LinearGradient(0, 0, 0, 1.0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.WHITE), new Stop(1, Color.RED)), null, null);
		// // Background bg = new Background(fill);
		//
		// final KeyValue kv = new KeyValue(visual.opacityProperty(), 0.1);
		// final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
		// timeline.getKeyFrames().add(kf);
		// timeline.play();
		//
		// timeline.setOnFinished(new EventHandler<ActionEvent>() {
		//
		// @Override
		// public void handle(ActionEvent event) {
		// visual.setOpacity(1);
		// }
		// });
	}

	@Override
	protected void refreshBackground() {
		super.refreshBackground();
		if (Math.random() > 0.9) {
			// getVisual().setRotate(-15);
		} else {
			getVisual().setRotate(0);
		}
	}

	@Override
	protected String getStyleClass() {
		return "class";
	}

}
