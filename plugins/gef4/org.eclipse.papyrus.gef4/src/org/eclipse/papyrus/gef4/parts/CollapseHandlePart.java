/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.gef4.parts;

import org.eclipse.gef4.mvc.fx.parts.AbstractFXHandlePart;
import org.eclipse.gef4.mvc.fx.tools.FXClickDragTool;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.papyrus.gef4.policies.CollapseOnClickPolicy;
import org.eclipse.papyrus.gef4.utils.BoundsUtil;
import org.eclipse.papyrus.gef4.utils.VisualPartUtil;

import com.google.common.collect.SetMultimap;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class CollapseHandlePart extends AbstractFXHandlePart<StackPane> {

	/** The width of the collapse handle part. */
	private static final int WIDTH = 8;

	/** The height of the collapse handle part. */
	private static final int HEIGHT = 8;

	/** The text. */
	private Text text;

	/**
	 * Instantiates a new collapse handle part.
	 */
	public CollapseHandlePart() {
	}

	@Override
	protected void doActivate() {
		super.doActivate();
		setAdapter(FXClickDragTool.CLICK_TOOL_POLICY_KEY, new CollapseOnClickPolicy());
	}

	/**
	 * @see org.eclipse.gef4.mvc.parts.AbstractVisualPart#createVisual()
	 *
	 * @return
	 */
	@Override
	protected StackPane createVisual() {
		final StackPane stackPane = new StackPane();
		stackPane.setPickOnBounds(true);
		stackPane.setStyle(""
				+ "-fx-background-color: linear-gradient(from 0% 50% to 100% 50%, white 0%, lightgrey 100%);"//$NON-NLS-1$
				+ "-fx-background-radius:3;"//$NON-NLS-1$
				+ "-fx-border-color:darkgrey;"//$NON-NLS-1$
				+ "-fx-border-radius:3");//$NON-NLS-1$
		stackPane.applyCss();

		text = new Text("+");//$NON-NLS-1$
		text.setBoundsType(TextBoundsType.VISUAL);
		stackPane.getChildren().add(text);

		return stackPane;
	}

	/**
	 * @see org.eclipse.gef4.mvc.parts.AbstractVisualPart#doRefreshVisual(java.lang.Object)
	 *
	 * @param visual
	 */
	@Override
	protected void doRefreshVisual(final StackPane visual) {
		// check if we have a host
		final SetMultimap<IVisualPart<Node, ? extends Node>, String> anchorages = getAnchorages();
		if (anchorages.isEmpty()) {
			return;
		}
		// determine center location of host visual
		final IVisualPart<Node, ? extends Node> anchorage = anchorages.keys()
				.iterator().next();

		// Get the parent compartment
		final CompartmentContentPart<?, ?> compartment = VisualPartUtil.findParentVisualPartInstance(anchorage, CompartmentContentPart.class);
		if (null != compartment && compartment.canCollapse) {
			final CompartmentContentPart<?, ?> compartmentContentPart = compartment;
			if (compartmentContentPart.isCollapsed()) {
				text.setText("+");//$NON-NLS-1$
			} else {
				text.setText("-");//$NON-NLS-1$
			}
			refreshHandleLocation(compartment);
			getVisual().setPrefSize(WIDTH, HEIGHT);
			getVisual().toFront();
		}
	}

	/**
	 * Refresh handle location.
	 *
	 * @param compartment
	 *            the host visual
	 */
	protected void refreshHandleLocation(final CompartmentContentPart<?, ?> compartment) {
		getVisual().setLayoutX(BoundsUtil.getAbsoluteX(compartment) + compartment.getMargin().getLeft());
		getVisual().setLayoutY(BoundsUtil.getAbsoluteY(compartment) + compartment.getMargin().getTop());
	}

}
