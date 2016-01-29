/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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
package org.eclipse.papyrus.gef4.utils;

import org.eclipse.gef4.mvc.models.FocusModel;
import org.eclipse.gef4.mvc.models.SelectionModel;
import org.eclipse.gef4.mvc.parts.IRootPart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gef4.mvc.viewer.IViewer;
import org.eclipse.papyrus.gef4.model.ChangeBoundsModel;

import com.google.common.reflect.TypeToken;

import javafx.scene.Node;

@SuppressWarnings("serial")
public class ModelUtil {

	/**
	 * Returns the SelectionModel associated with the given IVisualPart
	 *
	 * May be null (e.g. if the visual part is not associated to a viewer anymore,
	 * which probably means it has already been disposed)
	 *
	 * @param host
	 * @return
	 */
	public static SelectionModel<Node> getSelectionModel(IVisualPart<Node, ? extends Node> host) {
		IViewer<Node> viewer = getViewer(host);

		if (viewer == null) {
			return null;
		}

		return getSelectionModel(viewer);
	}

	public static ChangeBoundsModel getChangeBoundsModel(IVisualPart<?, ?> host) {
		IViewer<?> viewer = getViewer(host);
		if (viewer == null) {
			return null;
		}

		return viewer.getAdapter(ChangeBoundsModel.class);
	}

	public static <VR> IRootPart<VR, ? extends VR> getRoot(IVisualPart<VR, ? extends VR> host) {
		if (host == null) {
			return null;
		}
		return host.getRoot();
	}

	public static <VR> IViewer<VR> getViewer(IVisualPart<VR, ? extends VR> host) {
		IRootPart<VR, ? extends VR> rootPart = getRoot(host);
		if (rootPart == null) {
			return null;
		}
		return rootPart.getViewer();
	}

	public static FocusModel<Node> getFocusModel(IVisualPart<Node, ? extends Node> host) {
		IViewer<Node> viewer = getViewer(host);
		if (viewer == null) {
			return null;
		}
		return viewer.getAdapter(new TypeToken<FocusModel<Node>>() {
			// Nothing
		});
	}

	public static SelectionModel<Node> getSelectionModel(IViewer<Node> viewer) {
		return viewer.getAdapter(new TypeToken<SelectionModel<Node>>() {
			// Nothing
		});
	}
}
