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

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.uml2.uml.Slot;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.util.UMLSwitch;

public class SlotLabelService extends AbstractUMLLabelServiceParticipant<Slot> {

	public SlotLabelService(double priority) {
		super(priority, Slot.class);
	}

	@Override
	protected String getLabel(Slot slot) {
		String featureName = slot.getDefiningFeature() == null ? null : slot.getDefiningFeature().getName();
		String value = parseValues(slot);
		if (featureName == null && value == null) {
			return UNDEFINED;
		} else if (featureName == null) {
			return value;
		} else if (value == null) {
			return featureName;
		} else {
			return featureName + " = " + value;
		}
	}

	protected String parseValues(Slot slot) {
		List<ValueSpecification> values = slot.getValues();
		if (values.isEmpty()) {
			return null;
		} else if (values.size() == 1) {
			return parseValue(values.get(0));
		} else {
			return "[" + values.stream().map(this::parseValue).collect(Collectors.joining(", ")) + "]";
		}
	}

	protected String parseValue(ValueSpecification valueSpecification) {
		return new UMLSwitch<String>() {
			@Override
			public String caseLiteralBoolean(org.eclipse.uml2.uml.LiteralBoolean object) {
				return object.booleanValue() ? "true" : "false";
			}

			@Override
			public String caseLiteralInteger(org.eclipse.uml2.uml.LiteralInteger object) {
				return Integer.toString(object.integerValue());
			}

			@Override
			public String caseLiteralNull(org.eclipse.uml2.uml.LiteralNull object) {
				return "null";
			}

			@Override
			public String caseLiteralReal(org.eclipse.uml2.uml.LiteralReal object) {
				return Double.toString(object.realValue());
			}

			@Override
			public String caseLiteralString(org.eclipse.uml2.uml.LiteralString object) {
				return "\"" + object.stringValue() + "\"";
			}

			@Override
			public String caseLiteralUnlimitedNatural(org.eclipse.uml2.uml.LiteralUnlimitedNatural object) {
				int unlimitedValue = object.unlimitedValue();
				if (unlimitedValue < 0) {
					return "*";
				}
				return Integer.toString(unlimitedValue);
			}

			@Override
			public String caseValueSpecification(ValueSpecification object) {
				// TODO Add missing cases; this is just a fallback
				return "<Unknown value type>";
			}
		}.doSwitch(valueSpecification);
	}

}
