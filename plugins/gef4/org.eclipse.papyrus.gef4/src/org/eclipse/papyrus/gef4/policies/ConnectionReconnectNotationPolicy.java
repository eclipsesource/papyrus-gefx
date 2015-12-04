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
import org.eclipse.gef4.fx.anchors.IAnchor;
import org.eclipse.gef4.mvc.operations.ITransactionalOperation;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.util.StringStatics;
import org.eclipse.gmf.runtime.diagram.core.commands.SetConnectionAnchorsCommand;
import org.eclipse.gmf.runtime.diagram.core.commands.SetConnectionEndsCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.fx.anchors.SlidableFxAnchor;
import org.eclipse.papyrus.gef4.parts.ConnectionContentPart;
import org.eclipse.papyrus.gef4.utils.OperationBuilder;

/**
 * Policy for handling notation model changes as part of the composite link operation.
 *
 * @see ConnectionBendPolicy
 */
public class ConnectionReconnectNotationPolicy extends AbstractConnectionReconnectPolicy {

	protected ITransactionalOperation createNotationReconnectOperation() {
		ITransactionalOperation result = OperationBuilder.withForwardUndo("Update notation model")
				.add(createSetConnectionEndsOperation())
				.add(createSetConnectionAnchorsOperation())
				.add(createSetConnectionBendpointsOperation())
				.getResult();

		// System.err.println("ConnectionReconnectNotationPolicy.createNotationReconnectOperation(): canExecute: " + result.canExecute());
		return result;
	}

	protected IUndoableOperation createSetConnectionEndsOperation() {
		View newSource = getAnchorageView(getConnection().getStartAnchor());
		View newTarget = getAnchorageView(getConnection().getEndAnchor());

		if (newSource == null || newTarget == null) {
			return UnexecutableCommand.INSTANCE;
		}

		boolean isSourceChanged = !isSameAnchorageForRole(ConnectionContentPart.SOURCE, newSource);
		boolean isTargetChanged = !isSameAnchorageForRole(ConnectionContentPart.TARGET, newTarget);

		SetConnectionEndsCommand result = null;
		if (isSourceChanged || isTargetChanged) {
			result = new SetConnectionEndsCommand(getDomain(), "Update notation ends");
			result.setEdgeAdaptor(new EObjectAdapter(getHostNotationEdge()));
			if (isSourceChanged) {
				result.setNewSourceAdaptor(new EObjectAdapter(newSource));
			}
			if (isTargetChanged) {
				result.setNewTargetAdaptor(new EObjectAdapter(newTarget));
			}
		}

		/*
		 * System.out.println("ConnectionReconnectNotationPolicy.createSetConnectionEndsOperation(): " +
		 * result + ", can execute :" +
		 * Optional.ofNullable(result).map(IUndoableOperation::canExecute));
		 */

		return result;
	}

	protected boolean isSameAnchorageForRole(String endRole, View actualAnchorage) {
		Optional<View> old = findHostAnchorageForRole(endRole);
		return old.isPresent() && old.get().equals(actualAnchorage);
	}

	protected IUndoableOperation createSetConnectionAnchorsOperation() {
		SetConnectionAnchorsCommand result = new SetConnectionAnchorsCommand(getDomain(), "Update notation anchors");
		result.setEdgeAdaptor(new EObjectAdapter(getHostNotationEdge()));
		result.setNewSourceTerminal(composeTerminalString(getConnection().getStartAnchor()));
		result.setNewTargetTerminal(composeTerminalString(getConnection().getEndAnchor()));

		return result;
	}

	protected String composeTerminalString(IAnchor anchor) {
		String result = Optional.ofNullable(anchor)
				.filter(a -> a instanceof SlidableFxAnchor)
				.map(a -> ((SlidableFxAnchor) a).composeTerminalString())
				.orElse(StringStatics.BLANK);

		return result;
	}

	protected IUndoableOperation createSetConnectionBendpointsOperation() {
		return null;
	}

	@Override
	protected ITransactionalOperation createOperation() {
		return createNotationReconnectOperation();
	}

}
