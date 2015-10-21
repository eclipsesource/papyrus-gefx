package org.eclipse.papyrus.gef4.utils;

import java.util.Optional;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.ICompositeOperation;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.gef4.mvc.operations.ForwardUndoCompositeOperation;
import org.eclipse.gef4.mvc.operations.ITransactionalOperation;
import org.eclipse.gef4.mvc.operations.ReverseUndoCompositeOperation;

public abstract class OperationBuilder {

	protected static class CompositeOperationBuilder extends OperationBuilder {
		private final ICompositeOperation myResult;

		protected CompositeOperationBuilder(ICompositeOperation composite) {
			myResult = composite;
		}

		@Override
		public OperationBuilder add(IUndoableOperation op) {
			if (op != null) {
				myResult.add(op);
			}
			return this;
		}

		@Override
		public ITransactionalOperation getResult() {
			return toTransactional(myResult);
		}

	}

	public static ITransactionalOperation toTransactional(IUndoableOperation operation) {
		if (operation == null) {
			return null;
		}

		return new ITransactionalOperation() {

			@Override
			public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				return operation.undo(monitor, info);
			}

			@Override
			public void removeContext(IUndoContext context) {
				operation.removeContext(context);
			}

			@Override
			public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				return operation.redo(monitor, info);
			}

			@Override
			public boolean hasContext(IUndoContext context) {
				return operation.hasContext(context);
			}

			@Override
			public String getLabel() {
				return operation.getLabel();
			}

			@Override
			public IUndoContext[] getContexts() {
				return operation.getContexts();
			}

			@Override
			public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				return operation.execute(monitor, info);
			}

			@Override
			public void dispose() {
				operation.dispose();
			}

			@Override
			public boolean canUndo() {
				return operation.canUndo();
			}

			@Override
			public boolean canRedo() {
				return operation.canRedo();
			}

			@Override
			public boolean canExecute() {
				return operation.canExecute();
			}

			@Override
			public void addContext(IUndoContext context) {
				operation.addContext(context);
			}

			@Override
			public boolean isNoOp() {
				return false;
			}
		};
	}

	protected static class SingleOperationBuilder extends OperationBuilder {
		private final boolean myReverseNotForwardUndo;

		private IUndoableOperation myTheOnlyOp;

		private String myExplicitLabel;

		public SingleOperationBuilder(boolean reverseNotForwardUndo) {
			myReverseNotForwardUndo = reverseNotForwardUndo;
		}

		@Override
		public OperationBuilder add(IUndoableOperation op) {
			if (op == null) {
				return this;
			}
			if (myTheOnlyOp == null) {
				myTheOnlyOp = op;
				return this;
			}

			String label = myExplicitLabel != null ? myExplicitLabel
					: myTheOnlyOp.getLabel();
			ICompositeOperation result = myReverseNotForwardUndo
					? new ReverseUndoCompositeOperation(label)
					: new ForwardUndoCompositeOperation(label);
			result.add(myTheOnlyOp);

			return new CompositeOperationBuilder(result).add(op);
		}

		@Override
		public ITransactionalOperation getResult() {
			return toTransactional(myTheOnlyOp);
		}

		public SingleOperationBuilder withLabel(String label) {
			myExplicitLabel = label;
			return this;
		}
	}

	public static OperationBuilder withForwardUndo() {
		return withForwardUndo(null);
	}

	public static OperationBuilder withForwardUndo(String label) {
		return new SingleOperationBuilder(false).withLabel(label);
	}

	public static OperationBuilder withReverseUndo() {
		return withReverseUndo(null);
	}

	public static OperationBuilder withReverseUndo(String label) {
		return new SingleOperationBuilder(true).withLabel(label);
	}

	public abstract OperationBuilder add(IUndoableOperation op);

	public abstract ITransactionalOperation getResult();

	public final OperationBuilder add(Optional<? extends IUndoableOperation> op) {
		return op.isPresent() ? add(op.get()) : this;
	}

}
