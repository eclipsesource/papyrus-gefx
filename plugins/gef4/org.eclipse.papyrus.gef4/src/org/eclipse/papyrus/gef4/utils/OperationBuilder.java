package org.eclipse.papyrus.gef4.utils;

import java.util.Optional;

import org.eclipse.core.commands.operations.ICompositeOperation;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.gef4.mvc.operations.ForwardUndoCompositeOperation;
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
		public IUndoableOperation getResult() {
			return myResult;
		}

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
		public IUndoableOperation getResult() {
			return myTheOnlyOp;
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

	public abstract IUndoableOperation getResult();

	public final OperationBuilder add(Optional<? extends IUndoableOperation> op) {
		return op.isPresent() ? add(op.get()) : this;
	}

}
