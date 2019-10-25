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

package org.eclipse.papyrus.uml.gefdiag.common.services.label;

import java.util.function.Predicate;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.services.AbstractGMFProviderParticipant;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.services.LabelService;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralNull;
import org.eclipse.uml2.uml.LiteralReal;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.util.UMLSwitch;

public class ConstraintBodyLabelService extends AbstractGMFProviderParticipant<LabelService> {

	private static final Predicate<BaseContentPart<? extends View, ?>> CONSTRAINT_MATCHER = part -> getConstraint(part) != null;

	public ConstraintBodyLabelService(double priority, Predicate<BaseContentPart<? extends View, ?>> constraintPartMatcher) {
		super(priority, CONSTRAINT_MATCHER.and(constraintPartMatcher));
	}

	@Override
	protected LabelService doCreateInstance(BaseContentPart<? extends View, ?> basePart) {
		return () -> getLabel(getConstraint(basePart));
	}

	private static Constraint getConstraint(BaseContentPart<? extends View, ?> basePart) {
		EObject semanticElement = basePart.getContent().getElement();
		return semanticElement instanceof Constraint ? (Constraint) semanticElement : null;
	}

	protected String getLabel(Constraint constraint) {
		ValueSpecification specification = constraint.getSpecification();
		return new UMLSwitch<String>() {
			public String caseLiteralBoolean(LiteralBoolean object) {
				return object.booleanValue() ? "true" : "false";
			}

			public String caseLiteralInteger(LiteralInteger object) {
				return Integer.toString(object.integerValue());
			}

			public String caseLiteralNull(LiteralNull object) {
				return "null";
			}


			public String caseLiteralReal(LiteralReal object) {
				return Double.toString(object.realValue());
			}

			public String caseLiteralString(LiteralString object) {
				return "\"" + object.stringValue() + "\"";
			}

			public String caseLiteralUnlimitedNatural(LiteralUnlimitedNatural object) {
				return object.getValue() < 0 ? "*" : Integer.toString(object.getValue());
			}

			public String caseOpaqueExpression(OpaqueExpression object) {
				EList<String> languages = object.getLanguages();
				EList<String> bodies = object.getBodies();

				if (languages.isEmpty()) {
					return super.caseOpaqueExpression(object);
				}

				String language = languages.get(0);
				StringBuilder value = new StringBuilder();
				value.append('{');
				value.append('{').append(language).append('}');

				if (!bodies.isEmpty()) {
					String body = bodies.get(0);
					value.append(' ').append(body);
				}
				value.append('}');
				return value.toString();
			}

			public String caseValueSpecification(ValueSpecification object) {
				return "<Unsupported value specification>";
			}

		}.doSwitch(specification);
	}



}
