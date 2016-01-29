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

import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IURIEditorInput;

public class EditorUtils {

	/**
	 * Obtains the URI of the EMF resource identified by the given editor input.
	 *
	 * @param editorInput
	 *            an editor input
	 *
	 * @return the best-effort URI of the resource that it edits, or {@code null} if it could not be determined
	 */
	// Copied from org.eclipse.papyrus.infra.ui.util.EditorUtils to avoid a dependency on Papyrus
	public static URI getResourceURI(IEditorInput editorInput) {
		URI result = null;

		if (editorInput instanceof IFileEditorInput) {
			result = URI.createPlatformResourceURI(((IFileEditorInput) editorInput).getFile().getFullPath().toString(), true);
		} else if (editorInput instanceof URIEditorInput) {
			result = ((URIEditorInput) editorInput).getURI();
		} else if (editorInput instanceof IURIEditorInput) {
			result = URI.createURI(((IURIEditorInput) editorInput).getURI().toASCIIString(), true);
		} else if (editorInput != null) {
			// desperation
			Object adapter = editorInput.getAdapter(URI.class);
			if (adapter instanceof URI) {
				result = (URI) adapter;
			}
		}

		return result;
	}
}
