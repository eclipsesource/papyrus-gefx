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

import java.util.stream.Collectors;

import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;

public class OperationLabelService extends AbstractUMLLabelServiceParticipant<Operation> {

	public OperationLabelService(double priority) {
		super(priority, Operation.class);
	}

	@Override
	protected String getLabel(Operation element) {
		StringBuilder builder = new StringBuilder();

		if (element.getName() != null && !element.getName().isEmpty()) {
			builder.append(element.getName());
		} else {
			builder.append(UNNAMED);
		}

		builder.append('(');

		builder.append(element.getOwnedParameters().stream() //
				.filter(p -> p.getDirection() != ParameterDirectionKind.RETURN_LITERAL) //
				.map(this::getLabel) //
				.collect(Collectors.joining(", ")));
		builder.append(')');

		element.getOwnedParameters().stream() //
				.filter(p -> p.getDirection() == ParameterDirectionKind.RETURN_LITERAL) //
				.findFirst() //
				.ifPresent(returnParameter -> {
					builder.append(": ");
					if (returnParameter.getType() != null) {
						Type returnType = returnParameter.getType();
						String returnTypeName = returnType.getName();
						if (returnTypeName != null && !returnTypeName.isEmpty()) {
							builder.append(returnTypeName);
						} else {
							builder.append(UNNAMED);
						}
					} else {
						builder.append(UNDEFINED);
					}
				});

		return builder.toString();

	}

	protected CharSequence getLabel(Parameter parameter) {
		StringBuilder builder = new StringBuilder();

		boolean nameOrType = false;

		if (parameter.getName() != null && !parameter.getName().isEmpty()) {
			builder.append(parameter.getName());
			nameOrType = true;
		}

		if (parameter.getType() != null) {
			nameOrType = true;
			String typeName = parameter.getType().getName();
			builder.append(": ");
			if (typeName == null || typeName.isEmpty()) {
				builder.append(UNNAMED);
			} else {
				builder.append(typeName);
			}
		}

		if (!nameOrType) {
			builder.append(UNNAMED);
		}

		return builder;
	}

}
