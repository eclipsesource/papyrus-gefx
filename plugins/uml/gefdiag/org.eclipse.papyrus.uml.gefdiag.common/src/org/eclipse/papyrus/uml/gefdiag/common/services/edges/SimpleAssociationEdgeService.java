/*****************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
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
package org.eclipse.papyrus.uml.gefdiag.common.services.edges;

import org.eclipse.papyrus.gef4.decorations.DecorationFactory;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;

public class SimpleAssociationEdgeService extends AbstractUMLEdgeStyleServiceParticipant<Association> {

	public SimpleAssociationEdgeService(double priority) {
		super(priority, Association.class, true);
	}

	@Override
	protected String getSourceDecoration(Association association) {
		if (association == null || association.getMemberEnds().size() < 2) {
			return null;
		}

		Property sourceProperty = association.getMemberEnds().get(0);
		Property targetProperty = association.getMemberEnds().get(1);
		return getDecoration(association, sourceProperty, targetProperty);
	}

	@Override
	protected String getTargetDecoration(Association association) {
		if (association == null || association.getMemberEnds().size() < 2) {
			return null;
		}

		Property sourceProperty = association.getMemberEnds().get(0);
		Property targetProperty = association.getMemberEnds().get(1);
		return getDecoration(association, targetProperty, sourceProperty);
	}

	/**
	 * <p>
	 * Simple implementation, that returns the Aggregation decoration (For Composite or Shared member ends),
	 * or the Navigability (For references), or nothing (<code>null</code>) if the member end is neither Aggregated nor Navigable.
	 * </p>
	 *
	 * <p>
	 * This implementation doesn't support the "owned by association" dot decoration, and doesn't support
	 * composite decorations (e.g. owned by association + navigable, "dot+arrow")
	 * </p>
	 */
	private String getDecoration(Association association, Property memberEnd, Property oppositeEnd) {
		switch (memberEnd.getAggregation()) {
		case COMPOSITE_LITERAL:
			return DecorationFactory.BLACK_DIAMOND;
		case SHARED_LITERAL:
			return DecorationFactory.WHITE_DIAMOND;
		case NONE_LITERAL:
		default:
			break;
		}

		// Navigable = Owned by Classifier or Navigable Owned By Association
		// The arrow is displayed on the other side
		// (Source is navigable = To navigate from source to target, so we display the arrow on the target)
		if (!association.getOwnedEnds().contains(oppositeEnd) || association.getNavigableOwnedEnds().contains(oppositeEnd)) {
			return DecorationFactory.OPEN_ARROW;
		}

		// Not aggregated, not navigable: decoration is not supported.
		return null;
	}


}
