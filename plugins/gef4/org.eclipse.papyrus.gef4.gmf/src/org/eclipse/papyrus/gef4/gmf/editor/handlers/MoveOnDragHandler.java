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
package org.eclipse.papyrus.gef4.gmf.editor.handlers;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.fx.core.log.Logger;
import org.eclipse.fx.core.log.LoggerCreator;
import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnDragHandler;
import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.gef4.utils.ModelUtil;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;

import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class MoveOnDragHandler extends AbstractHandler implements IOnDragHandler {

	static final Logger logger = LoggerCreator.createLogger(MoveOnDragHandler.class);

	@Inject
	TransactionalEditingDomain editingDomain;

	@Override
	public void drag(final MouseEvent e, final Dimension delta) {
		// FIXME Take host transformations into account (esp. scale)
		if (e.isStillSincePress()) {
			return;
		}
		if (!isSelected()) {
			return;
		}

		final SelectionModel selectionModel = ModelUtil.getSelectionModel(getHost());

		List<IContentPart<? extends Node>> selection = selectionModel.getSelectionUnmodifiable();

		for (final IContentPart<? extends Node> selectedPart : selection) {
			for (final MoveHandler moveHandler : selectedPart.getAdapters(MoveHandler.class).values()) {
				moveHandler.showFeedback(delta);
			}
		}
	}

	@Override
	public void startDrag(final MouseEvent e) {
		// Nothing
	}

	@Override
	public void endDrag(final MouseEvent e, final Dimension delta) {
		// FIXME Take host transformations into account (esp. scale)
		if (e.isStillSincePress()) {
			return;
		}
		final SelectionModel selectionModel = ModelUtil.getSelectionModel(getHost());

		List<IContentPart<? extends Node>> selection = selectionModel.getSelectionUnmodifiable();

		ICommand command = null;
		for (final IContentPart<? extends Node> selectedPart : selection) {
			for (final MoveHandler moveHandler : selectedPart.getAdapters(MoveHandler.class).values()) {
				ICommand handlerCommand = moveHandler.move(delta);
				if (command == null) {
					command = handlerCommand;
				} else {
					command = command.compose(handlerCommand);
				}

				moveHandler.removeFeedback();
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
			for (final MoveHandler moveHandler : selectedPart.getAdapters(MoveHandler.class).values()) {
				moveHandler.removeFeedback();
			}
		}
	}

	protected boolean isSelected() {
		// Since Bug 484690, the entire hierarchy of parts will receive the event.
		// Filter on selected elements
		SelectionModel selectionModel = ModelUtil.getSelectionModel(getHost());
		return selectionModel.getSelectionUnmodifiable().contains(getHost());
	}

	@Override
	public void hideIndicationCursor() {
		// Nothing
	}

	@Override
	public boolean showIndicationCursor(KeyEvent event) {
		return false;
	}

	@Override
	public boolean showIndicationCursor(MouseEvent event) {
		return false;
	}

}
