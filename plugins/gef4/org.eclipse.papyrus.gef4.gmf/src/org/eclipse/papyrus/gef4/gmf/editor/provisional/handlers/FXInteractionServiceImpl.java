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

package org.eclipse.papyrus.gef4.gmf.editor.provisional.handlers;

import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IFeedbackPart;
import org.eclipse.gef.mvc.fx.parts.IHandlePart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.utils.ModelUtil;

import javafx.scene.input.MouseEvent;

public class FXInteractionServiceImpl implements FXInteractionService {

	private final IVisualPart<?> host;

	private MouseEvent currentEvent;

	public FXInteractionServiceImpl(IVisualPart<?> host) {
		this.host = host;
	}

	/**
	 * @param host
	 * @param point
	 * @return
	 */
	@Override
	public IVisualPart<?> getPartAt(Point point) {
		return host;
	}

	/**
	 * @param host
	 * @param part
	 * @return
	 */
	@Override
	public boolean isSelected(IVisualPart<?> part) {
		if (!(part instanceof IContentPart)) {
			return false;
		}
		SelectionModel selectionModel = ModelUtil.getSelectionModel(part);
		return selectionModel != null && selectionModel.isSelected((IContentPart<?>) part);
	}

	/**
	 * @param object
	 */
	public void setEvent(MouseEvent event) {
		this.currentEvent = event;
	}

	@Override
	public boolean isCtrlPressed() {
		return this.currentEvent != null && this.currentEvent.isControlDown();
	}

	@Override
	public void clearSelection() {
		SelectionModel selectionModel = ModelUtil.getSelectionModel(host);
		selectionModel.clearSelection();
	}

	@Override
	public void select(IContentPart<?> part) {
		SelectionModel selectionModel = ModelUtil.getSelectionModel(part);
		selectionModel.appendToSelection(part);
	}

	@Override
	public void deselect(IContentPart<?> part) {
		SelectionModel selectionModel = ModelUtil.getSelectionModel(part);
		selectionModel.removeFromSelection(part);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.papyrus.gef4.handlers.FXInteractionService#getContentPartAt(org.eclipse.gef.geometry.planar.Point)
	 */
	@Override
	public IContentPart<?> getContentPartAt(Point point) {
		IVisualPart<?> visualPart = getPartAt(point);
		if (visualPart instanceof IContentPart) {
			return (IContentPart<?>) visualPart;
		}
		if (visualPart instanceof IFeedbackPart) {
			// Find attachment?
		}
		if (visualPart instanceof IHandlePart) {
			// Find attachment?
		}
		return null;
	}

}
