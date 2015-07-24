/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gefdiag.common.history;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.ICompositeOperation;
import org.eclipse.core.commands.operations.IOperationApprover;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

/**
 * An Operation History that only executes the operation (And does nothing else).
 * Used for graphical GEF4 operations
 * that shouldn't rely on the standard OperationHistory
 *
 * @author Camille Letavernier
 *
 */
public class EmptyOperationHistory implements IOperationHistory {

	public static final IOperationHistory INSTANCE = new EmptyOperationHistory();

	@Override
	public void add(IUndoableOperation operation) {
		// Nothing
	}

	@Override
	public void addOperationApprover(IOperationApprover approver) {
		// Nothing
	}

	@Override
	public void addOperationHistoryListener(IOperationHistoryListener listener) {
		// Nothing
	}

	@Override
	public void closeOperation(boolean operationOK, boolean addToHistory, int mode) {
		// Nothing
	}

	@Override
	public boolean canRedo(IUndoContext context) {
		// Not supported
		return false;
	}

	@Override
	public boolean canUndo(IUndoContext context) {
		// Not supported
		return false;
	}

	@Override
	public void dispose(IUndoContext context, boolean flushUndo, boolean flushRedo, boolean flushContext) {
		// Nothing
	}

	@Override
	public IStatus execute(IUndoableOperation operation, IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return operation.execute(monitor, info);
	}

	@Override
	public int getLimit(IUndoContext context) {
		// Not supported
		return 0;
	}

	@Override
	public IUndoableOperation[] getRedoHistory(IUndoContext context) {
		// Not supported
		return null;
	}

	@Override
	public IUndoableOperation getRedoOperation(IUndoContext context) {
		// Not supported
		return null;
	}

	@Override
	public IUndoableOperation[] getUndoHistory(IUndoContext context) {
		// Not supported
		return null;
	}

	@Override
	public void openOperation(ICompositeOperation operation, int mode) {
		// Nothing
	}

	@Override
	public void operationChanged(IUndoableOperation operation) {
		// Nothing
	}

	@Override
	public IUndoableOperation getUndoOperation(IUndoContext context) {
		// Not supported
		return null;
	}

	@Override
	public IStatus redo(IUndoContext context, IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		// Not supported
		return null;
	}

	@Override
	public IStatus redoOperation(IUndoableOperation operation, IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		// Not supported
		return null;
	}

	@Override
	public void removeOperationApprover(IOperationApprover approver) {
		// Nothing
	}

	@Override
	public void removeOperationHistoryListener(IOperationHistoryListener listener) {
		// Nothing
	}

	@Override
	public void replaceOperation(IUndoableOperation operation, IUndoableOperation[] replacements) {
		// Nothing
	}

	@Override
	public void setLimit(IUndoContext context, int limit) {
		// Nothing
	}

	@Override
	public IStatus undo(IUndoContext context, IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		// Not supported
		return null;
	}

	@Override
	public IStatus undoOperation(IUndoableOperation operation, IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		// Not supported
		return null;
	}

}
