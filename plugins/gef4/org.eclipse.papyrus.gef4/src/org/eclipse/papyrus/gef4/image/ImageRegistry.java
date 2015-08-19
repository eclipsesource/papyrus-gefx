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
package org.eclipse.papyrus.gef4.image;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.gef4.Activator;

import javafx.scene.image.Image;

public class ImageRegistry {
	public static final ImageRegistry INSTANCE = new ImageRegistry();

	private static final Map<String, Image> images = new HashMap<String, Image>();

	public synchronized Image getImage(String url) {
		if (url == null) {
			return null;
		}
		if (!(images.containsKey(url))) {
			Image image = new Image(url);
			images.put(url, image);
			if (image.isError()) {
				Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "An error occurred while trying to load an image", image.getException()));
			}
		}
		return images.get(url);
	}

}
