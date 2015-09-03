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

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef4.mvc.policies.ContentPolicy;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.parts.ConnectionContentPart;
import org.eclipse.papyrus.gef4.utils.OperationBuilder;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

import javafx.scene.Node;

/**
 * Policy for handling notation model changes as part of the composite link operation.
 * 
 * @see ConnectionBendPolicy
 */
public class ConnectionReconnectSemanticPolicy extends AbstractConnectionReconnectPolicy {

	@Override
	public IUndoableOperation commit() {
		return createSemanticReconnectOperation();
	}

	protected IUndoableOperation createSemanticReconnectOperation() {
		// System.out.println("ConnectionReconnectSemanticPolicy.createSemanticReconnectOperation()");
		// FIXME: what about reference-based links?
		assert resolveSemanticElement() != null : "Reference-based links are not supported yet: " + getHost().getContent();

		View newSource = getAnchorageView(getConnection().getStartAnchor());
		View newTarget = getAnchorageView(getConnection().getEndAnchor());

		if (newSource == null || newTarget == null) {
			return UnexecutableCommand.INSTANCE;
		}

		Optional<ReorientRelationshipRequest> targetRequest = requestForEnd(newTarget, ConnectionContentPart.TARGET, ReorientRequest.REORIENT_TARGET);
		Optional<ReorientRelationshipRequest> sourceRequest = requestForEnd(newSource, ConnectionContentPart.SOURCE, ReorientRequest.REORIENT_SOURCE);

		// System.err.println("-- Source request: " + sourceRequest);
		// System.err.println("-- Target request: " + targetRequest);

		IElementEditService service = ElementEditServiceUtils.getCommandProvider(resolveSemanticElement());

		IUndoableOperation result = OperationBuilder.withForwardUndo() //
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

	private IUndoableOperation createSemanticReconnectOperation_old() {
		// create anchorage operations, start with detaching all anchorages
		ContentPolicy<Node> contentPolicy = getAdaptable()
				.<ContentPolicy<Node>> getAdapter(ContentPolicy.class);
		contentPolicy.init();
		contentPolicy.detachFromAllContentAnchorages();
		final IUndoableOperation detachOperation = contentPolicy.commit();

		// then attach source and target (if available)
		contentPolicy.init();
		Object sourceContentAnchorage = getAnchorageContent(getConnection().getStartAnchor());
		if (sourceContentAnchorage != null) {
			contentPolicy.attachToContentAnchorage(sourceContentAnchorage,
					"START");
		}
		Object targetContentAnchorage = getAnchorageContent(
				getConnection().getEndAnchor());
		if (targetContentAnchorage != null) {
			contentPolicy.attachToContentAnchorage(targetContentAnchorage,
					"END");
		}
		final IUndoableOperation attachOperation = contentPolicy.commit();

		return OperationBuilder //
				.withReverseUndo("Change Anchorages") //
				.add(detachOperation) //
				.add(attachOperation) //
				.getResult();

	}

	protected EObject resolveSemanticElement() {
		return ((View) getHost().getContent()).getElement();
	}

	@Override
	public void init() {
		// FIXME: should we be ITransactional then?
	}


}
