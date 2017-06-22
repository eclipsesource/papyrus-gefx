/*****************************************************************************
 * Copyright (c) 2015 CEA LIST, Montages AG and others
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Michael Golubev (Montages) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.policies;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.mvc.fx.operations.ITransactionalOperation;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.handlers.AbstractConnectionReconnectHandler;
import org.eclipse.papyrus.gef4.parts.ConnectionContentPart;
import org.eclipse.papyrus.gef4.utils.OperationBuilder;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

/**
 * Policy for handling notation model changes as part of the composite link operation.
 *
 * @see ConnectionBendPolicy
 */
public class ConnectionReconnectSemanticPolicy extends AbstractConnectionReconnectHandler {

	protected ITransactionalOperation createSemanticReconnectOperation() {
		// System.out.println("ConnectionReconnectSemanticPolicy.createSemanticReconnectOperation()");
		// FIXME: what about reference-based links?
		assert resolveSemanticElement() != null : "Reference-based links are not supported yet: " + getHost().getContent();

		View newSource = getAnchorageView(getConnection().getStartAnchor());
		View newTarget = getAnchorageView(getConnection().getEndAnchor());

		if (newSource == null || newTarget == null) {
			return null;
		}

		Optional<ReorientRelationshipRequest> targetRequest = requestForEnd(newTarget, ConnectionContentPart.TARGET, ReorientRequest.REORIENT_TARGET);
		Optional<ReorientRelationshipRequest> sourceRequest = requestForEnd(newSource, ConnectionContentPart.SOURCE, ReorientRequest.REORIENT_SOURCE);

		// System.err.println("-- Source request: " + sourceRequest);
		// System.err.println("-- Target request: " + targetRequest);

		IElementEditService service = ElementEditServiceUtils.getCommandProvider(resolveSemanticElement());

		ITransactionalOperation result = OperationBuilder.withForwardUndo() //
				.add(sourceRequest.map(service::getEditCommand)) //
				.add(targetRequest.map(service::getEditCommand)) //
				.getResult();

		// System.err.println("-- result: " + result);
		return result;
	}

	protected Optional<ReorientRelationshipRequest> requestForEnd(View newEnd, String endRole, int endRelation) {
		// FIXME: what to do with reference-based links?
		EObject semanticConnection = getHostNotationEdge().getElement();
		assert semanticConnection != null : "What to do with reference-based links?"; // FIXME

		return findHostAnchorageForRole(endRole)
				.map(v -> v.getElement())
				.filter(e -> !e.equals(newEnd.getElement()))
				.map(actualSource -> new ReorientRelationshipRequest(getDomain(), //
						semanticConnection, newEnd.getElement(), actualSource, endRelation));
	}

	protected EObject resolveSemanticElement() {
		return ((View) getHost().getContent()).getElement();
	}

	@Override
	protected ITransactionalOperation createOperation() {
		return createSemanticReconnectOperation();
	}


}
