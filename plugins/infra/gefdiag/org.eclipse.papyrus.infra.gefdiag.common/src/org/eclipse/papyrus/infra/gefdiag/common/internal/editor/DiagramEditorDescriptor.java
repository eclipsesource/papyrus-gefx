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
package org.eclipse.papyrus.infra.gefdiag.common.internal.editor;

import java.util.function.Supplier;

import org.eclipse.swt.graphics.Image;

import com.google.inject.Module;

class DiagramEditorDescriptor {
	private final String type;

	private final Supplier<Module> module;

	private final Image image;

	private final String label;

	public DiagramEditorDescriptor(String type, Supplier<Module> module, Image image, String label) {
		super();
		this.type = type;
		this.module = module;
		this.image = image;
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public Module getModule() {
		return module.get();
	}

	public Image getImage() {
		return image;
	}

	public String getLabel() {
		return label;
	}
}