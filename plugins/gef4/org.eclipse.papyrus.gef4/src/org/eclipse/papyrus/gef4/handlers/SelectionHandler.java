/*****************************************************************************
 * Copyright (c) 2017 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.gef4.handlers;

import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.parts.IContentPart;

import javafx.scene.input.MouseEvent;

public class SelectionHandler extends AbstractDragHandler {

	// private int nextMousePressedEvent = 1; //Only one event possible
	private int nextMouseReleasedEvent = 1;

	@Override
	public void handleMousePressed(MouseEvent e) {
		mousePressed(e);
	}

	@Override
	public void handleMouseReleased(MouseEvent e) {
		switch (nextMouseReleasedEvent) {
		case 1:
			mouseReleased1(e);
			return;
		case 2:
			mouseReleased2(e);
			return;
		default:
			return;
		}
	}

	private void mousePressed(MouseEvent e) {
		// ObjectFlow#Transform
		Point point = new Point(e.getX(), e.getY());
		// GetPartAt
		IContentPart<?> part = interactionService.getContentPartAt(point);
		if (!interactionService.isSelected(part)) {
			if (interactionService.isCtrlPressed()) {
				// Continue
			} else {
				interactionService.clearSelection();
			}
			interactionService.select(part);

			// Wait for mouseReleased event
			nextMouseReleasedEvent = 2;
		} else {
			// nextMousePressedEvent = 1;
			// nextMouseReleasedEvent = 1;
			return; // Terminate execution
		}
	}

	/**
	 * @param e
	 */
	private void mouseReleased1(MouseEvent e) {
		// ObjectFlow#Transform
		Point point = new Point(e.getX(), e.getY());
		IContentPart<?> part = interactionService.getContentPartAt(point);
		if (interactionService.isSelected(part)) {
			if (interactionService.isCtrlPressed()) {
				interactionService.deselect(part);
			} else {
				interactionService.clearSelection();
				interactionService.select(part);
			}
			// nextMousePressedEvent = 1;
			nextMouseReleasedEvent = 1;
		} else {
			// nextMousePressedEvent = 1;
			nextMouseReleasedEvent = 1;
		}
	}

	/**
	 * @param e
	 */
	private void mouseReleased2(MouseEvent e) {
		// nextMousePressedEvent = 1;
		nextMouseReleasedEvent = 1;
	}

}
