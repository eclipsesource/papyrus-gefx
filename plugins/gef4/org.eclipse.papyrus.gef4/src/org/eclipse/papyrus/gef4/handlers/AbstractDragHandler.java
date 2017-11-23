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

import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnDragHandler;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public abstract class AbstractDragHandler extends AbstractHandler implements IOnDragHandler {

	private FXInteractionServiceImpl fxInteractionService;
	protected FXInteractionService interactionService;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.common.adapt.IAdaptable.Bound.Impl#setAdaptable(org.eclipse.gef.common.adapt.IAdaptable)
	 */
	@Override
	public void setAdaptable(IVisualPart<? extends Node> adaptable) {
		super.setAdaptable(adaptable);
		interactionService = fxInteractionService = new FXInteractionServiceImpl(adaptable);
	}

	/**
	 * @see org.eclipse.gef.mvc.fx.handlers.IOnDragHandler#startDrag(javafx.scene.input.MouseEvent)
	 *
	 * @param e
	 */
	@Override
	public final void startDrag(MouseEvent e) {
		fxInteractionService.setEvent(e);
		handleMousePressed(e);
		fxInteractionService.setEvent(null);
	}

	protected abstract void handleMousePressed(MouseEvent e);

	/**
	 * @see org.eclipse.gef.mvc.fx.handlers.IOnDragHandler#endDrag(javafx.scene.input.MouseEvent, org.eclipse.gef.geometry.planar.Dimension)
	 *
	 * @param e
	 * @param delta
	 */
	@Override
	public final void endDrag(MouseEvent e, Dimension delta) {
		fxInteractionService.setEvent(e);
		handleMouseReleased(e);
		fxInteractionService.setEvent(null);
	}

	protected abstract void handleMouseReleased(MouseEvent e);

	/**
	 * @see org.eclipse.gef.mvc.fx.handlers.IOnDragHandler#abortDrag()
	 *
	 */
	@Override
	public void abortDrag() {
		// Ignore
	}

	/**
	 * @see org.eclipse.gef.mvc.fx.handlers.IOnDragHandler#drag(javafx.scene.input.MouseEvent, org.eclipse.gef.geometry.planar.Dimension)
	 *
	 * @param e
	 * @param delta
	 */
	@Override
	public void drag(MouseEvent e, Dimension delta) {
		// Ignore
	}

	/**
	 * @see org.eclipse.gef.mvc.fx.handlers.IOnDragHandler#hideIndicationCursor()
	 *
	 */
	@Override
	public void hideIndicationCursor() {
		// Ignore
	}

	/**
	 * @see org.eclipse.gef.mvc.fx.handlers.IOnDragHandler#showIndicationCursor(javafx.scene.input.KeyEvent)
	 *
	 * @param event
	 * @return
	 */
	@Override
	public boolean showIndicationCursor(KeyEvent event) {
		return false;
	}

	/**
	 * @see org.eclipse.gef.mvc.fx.handlers.IOnDragHandler#showIndicationCursor(javafx.scene.input.MouseEvent)
	 *
	 * @param event
	 * @return
	 */
	@Override
	public boolean showIndicationCursor(MouseEvent event) {
		return false;
	}


}
