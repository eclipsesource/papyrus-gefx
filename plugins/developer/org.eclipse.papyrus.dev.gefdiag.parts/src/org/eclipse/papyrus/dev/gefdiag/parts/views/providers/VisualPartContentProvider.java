package org.eclipse.papyrus.dev.gefdiag.parts.views.providers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IFeedbackPart;
import org.eclipse.gef4.mvc.parts.IHandlePart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class VisualPartContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
		// Nothing
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// Nothing
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Object[]) {
			return Arrays.asList(((Object[]) inputElement)).stream().filter((e) -> e instanceof IVisualPart).toArray();
		}

		return new Object[0];
	}

	@Override
	public Object[] getChildren(Object parentElement) {

		List<Object> childrenAndAnchored = new LinkedList<>();

		if (parentElement instanceof IVisualPart) {
			IVisualPart<?, ?> parent = (IVisualPart<?, ?>) parentElement;
			childrenAndAnchored.addAll(parent.getChildrenUnmodifiable());

			if (!parent.getAnchoredsUnmodifiable().isEmpty()) {
				if (parent.getAnchoredsUnmodifiable().stream().anyMatch((anchored) -> anchored instanceof IContentPart)) {
					childrenAndAnchored.add(new AnchorsWrapper(AnchorKind.CONTENT, parent));
				}
				if (parent.getAnchoredsUnmodifiable().stream().anyMatch((anchored) -> anchored instanceof IHandlePart)) {
					childrenAndAnchored.add(new AnchorsWrapper(AnchorKind.HANDLE, parent));
				}
				if (parent.getAnchoredsUnmodifiable().stream().anyMatch((anchored) -> anchored instanceof IFeedbackPart)) {
					childrenAndAnchored.add(new AnchorsWrapper(AnchorKind.FEEDBACK, parent));
				}
			}
		} else if (parentElement instanceof AnchorsWrapper) {
			AnchorsWrapper wrapper = (AnchorsWrapper) parentElement;
			switch (wrapper.kind) {
			case FEEDBACK:
				for (IVisualPart<?, ?> anchored : wrapper.owner.getAnchoredsUnmodifiable()) {
					if (anchored instanceof IFeedbackPart) {
						childrenAndAnchored.add(anchored);
					}
				}
				break;
			case HANDLE:
				for (IVisualPart<?, ?> anchored : wrapper.owner.getAnchoredsUnmodifiable()) {
					if (anchored instanceof IHandlePart) {
						childrenAndAnchored.add(anchored);
					}
				}
				break;
			case CONTENT:
				for (IVisualPart<?, ?> anchored : wrapper.owner.getAnchoredsUnmodifiable()) {
					if (anchored instanceof IContentPart) {
						childrenAndAnchored.add(anchored);
					}
				}
				break;
			}

		}

		return childrenAndAnchored.toArray();
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof IVisualPart) {
			return ((IVisualPart<?, ?>) element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

	class AnchorsWrapper {
		public AnchorsWrapper(AnchorKind kind, IVisualPart<?, ?> owner) {
			super();
			this.kind = kind;
			this.owner = owner;
		}

		public AnchorKind kind;

		public IVisualPart<?, ?> owner;
	}

	enum AnchorKind {
		CONTENT, HANDLE, FEEDBACK
	}

}
