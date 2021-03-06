/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  EclipseSource - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.gmf.editor.handlers;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.CursorSupport;
import org.eclipse.gef.mvc.fx.handlers.IOnDragHandler;
import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.parts.AbstractSegmentHandlePart;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.gef4.handle.Direction;
import org.eclipse.papyrus.gef4.handle.ResizeHandlePart;
import org.eclipse.papyrus.gef4.utils.ModelUtil;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ResizeOnDragHandler extends AbstractHandler implements IOnDragHandler {

	private CursorSupport cursorSupport;

	@Inject
	TransactionalEditingDomain editingDomain;

	@Override
	public void drag(final MouseEvent e, final Dimension delta) {
		if (e.isStillSincePress()) {
			return;
		}
		final SelectionModel selectionModel = ModelUtil.getSelectionModel(getHost());

		List<IContentPart<? extends Node>> selection = selectionModel.getSelectionUnmodifiable();

		for (final IContentPart<? extends Node> selectedPart : selection) {
			for (final ResizeHandler resizeHandler : selectedPart.getAdapters(ResizeHandler.class).values()) {
				resizeHandler.showFeedback(delta, getAnchorDirection());
			}
		}
	}

	protected Direction getAnchorDirection() {
		if (getHost() instanceof AbstractSegmentHandlePart) {
			switch (((AbstractSegmentHandlePart<?>) getHost()).getSegmentIndex()) {
			case 0:
				return Direction.NORTH_WEST;
			case 1:
				return Direction.NORTH_EAST;
			case 2:
				return Direction.SOUTH_EAST;
			case 3:
				return Direction.SOUTH_WEST;
			}
		} else if (getHost() instanceof ResizeHandlePart) {
			return ((ResizeHandlePart) getHost()).getResizeDirection();
		}

		return null;
	}

	@Override
	public void startDrag(final MouseEvent e) {
		// Nothing
	}

	@Override
	public void endDrag(final MouseEvent e, final Dimension delta) {
		if (e.isStillSincePress()) {
			return;
		}
		final SelectionModel selectionModel = ModelUtil.getSelectionModel(getHost());

		List<IContentPart<? extends Node>> selection = selectionModel.getSelectionUnmodifiable();

		ICommand command = null;
		for (final IContentPart<? extends Node> selectedPart : selection) {
			for (final ResizeHandler resizeHandler : selectedPart.getAdapters(ResizeHandler.class).values()) {
				ICommand handlerCommand = resizeHandler.resize(delta, getAnchorDirection());
				if (command == null) {
					command = handlerCommand;
				} else {
					command = command.compose(handlerCommand);
				}

				resizeHandler.removeFeedback();
			}
		}

		if (command != null) {
			command = command.reduce();
			editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(command));
		}
	}

	@Override
	public void abortDrag() {
		final SelectionModel selectionModel = ModelUtil.getSelectionModel(getHost());

		List<IContentPart<? extends Node>> selection = selectionModel.getSelectionUnmodifiable();

		for (final IContentPart<? extends Node> selectedPart : selection) {
			for (final ResizeHandler resizeHandler : selectedPart.getAdapters(ResizeHandler.class).values()) {
				resizeHandler.removeFeedback();
			}
		}
	}

	protected IVisualPart<? extends Node> getAnchorageHost() {
		return getHost().getAnchoragesUnmodifiable().keys().iterator().next();
	}

	@Override
	public void hideIndicationCursor() {
		if (cursorSupport != null) {
			cursorSupport.restoreCursor();
		}
	}

	@Override
	public boolean showIndicationCursor(KeyEvent event) {
		return false;
	}

	@Override
	public boolean showIndicationCursor(MouseEvent event) {
		if (cursorSupport != null) {
			cursorSupport.storeAndReplaceCursor(getCursor());
		}
		return true;
	}

	/**
	 * @see org.eclipse.gef.common.adapt.IAdaptable.Bound.Impl#setAdaptable(org.eclipse.gef.common.adapt.IAdaptable)
	 *
	 * @param adaptable
	 */
	@Override
	public void setAdaptable(IVisualPart<? extends Node> adaptable) {
		super.setAdaptable(adaptable);
		if (adaptable != null) {
			cursorSupport = adaptable.getViewer().getAdapter(CursorSupport.class);
		}
	}

	private Cursor getCursor() {
		switch (getAnchorDirection()) {
		case NORTH:
			return Cursor.N_RESIZE;
		case SOUTH:
			return Cursor.S_RESIZE;
		case EAST:
			return Cursor.E_RESIZE;
		case WEST:
			return Cursor.W_RESIZE;
		case NORTH_WEST:
			return Cursor.NW_RESIZE;
		case NORTH_EAST:
			return Cursor.NE_RESIZE;
		case SOUTH_WEST:
			return Cursor.SW_RESIZE;
		case SOUTH_EAST:
			return Cursor.SE_RESIZE;
		default:
			return Cursor.DEFAULT;
		}
	}

}
