/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
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
package org.eclipse.papyrus.uml.gefdiag.common.services.label;

import org.eclipse.uml2.uml.Comment;

public class CommentLabelService extends AbstractUMLLabelServiceParticipant<Comment> {

	public CommentLabelService(double priority) {
		super(priority, Comment.class);
	}

	@Override
	protected String getLabel(Comment comment) {
		return comment.getBody();
	}


}
