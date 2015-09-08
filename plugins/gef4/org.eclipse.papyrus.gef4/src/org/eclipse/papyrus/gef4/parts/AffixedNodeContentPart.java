/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.parts;

import org.eclipse.gmf.runtime.notation.Shape;

public class AffixedNodeContentPart extends NodeContentPart implements IPrimaryContentPart {

	public AffixedNodeContentPart(final Shape view) {
		super(view);
	}

	@Override
	protected double getMinHeight() {
		return 20;
	}

	@Override
	protected double getMinWidth() {
		return 20;
	}

	@Override
	protected String getStyleClass() {
		return "genericAffixedNode";
	}

}
