/*****************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  EclipseSource
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.gefdiag.clazz.service.label;

import org.eclipse.fx.core.log.Logger;
import org.eclipse.fx.core.log.LoggerCreator;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.uml.gefdiag.clazz.ClassConstants;
import org.eclipse.papyrus.uml.gefdiag.common.services.label.AbstractUMLLabelServiceParticipant;
import org.eclipse.papyrus.uml.gefdiag.common.services.label.MultiplicityHelper;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;

public class AssociationLabelService extends AbstractUMLLabelServiceParticipant<Association> {

	private static final Logger logger = LoggerCreator.createLogger(AssociationLabelService.class);

	private MultiplicityHelper multiplicityHelper = new MultiplicityHelper();

	public AssociationLabelService(double priority) {
		super(priority, Association.class);
	}

	@Override
	protected String getLabel(Association element) {
		String name = element.getName();
		return name == null || name.isEmpty() ? deriveAssociationName(element) : name;
	}

	private String deriveAssociationName(Association element) {
		return null; // We don't display derived names in the diagram
	}

	@Override
	protected String getLabel(BaseContentPart<? extends View, ?> basePart) {
		View content = basePart.getContent();
		Association association = getElement(basePart);
		switch (content.getType()) {
		case ClassConstants.ASSOCIATION_SOURCE_ROLE:
			return getRole(getSource(association));
		case ClassConstants.ASSOCIATION_TARGET_ROLE:
			return getRole(getTarget(association));
		case ClassConstants.ASSOCIATION_SOURCE_MULTIPLICITY:
			return getMultiplicity(getSource(association));
		case ClassConstants.ASSOCIATION_TARGET_MULTIPLICITY:
			return getMultiplicity(getTarget(association));
		case ClassConstants.ASSOCIATION_NAME:
			return getLabel(association);
		case ClassConstants.ASSOCIATION_STEREOTYPE:
			return null; // TODO
		default:
			logger.warning("Unsupported Association Label view type: " + content.getType());
			return null;
		}
	}

	private String getMultiplicity(Property memberEnd) {
		return memberEnd == null ? null : multiplicityHelper.getMultiplicity(memberEnd, false);
	}

	private String getRole(Property memberEnd) {
		// FIXME Remove the "+"; just a temporary workaround to get 100% consistency with Papyrus labels
		return memberEnd == null ? null : memberEnd.getName();
	}

	private Property getSource(Association association) {
		return association.getMemberEnds().size() < 1 ? null : association.getMemberEnds().get(0);
	}

	private Property getTarget(Association association) {
		return association.getMemberEnds().size() < 2 ? null : association.getMemberEnds().get(1);
	}

}
