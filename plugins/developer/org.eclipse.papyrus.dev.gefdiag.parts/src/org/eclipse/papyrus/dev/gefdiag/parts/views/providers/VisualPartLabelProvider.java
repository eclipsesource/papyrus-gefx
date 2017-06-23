package org.eclipse.papyrus.dev.gefdiag.parts.views.providers;

import org.eclipse.gef.mvc.fx.parts.IFeedbackPart;
import org.eclipse.gef.mvc.fx.parts.IHandlePart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.gef4.parts.NotationContentPart;

import javafx.scene.Node;

public class VisualPartLabelProvider extends LabelProvider {
	@Override
	public String getText(Object element) {
		StringBuffer buffer = new StringBuffer(element.getClass().getSimpleName());

		if (element instanceof NotationContentPart) {
			buffer = new StringBuffer("[Content] ").append(buffer);
			NotationContentPart<? extends View, ? extends Node> part = (NotationContentPart<?, ?>) element;
			if (part.getElement() != null) {
				buffer.append(": " + part.getElement().eClass().getName());
			} else {
				buffer.append(": <No Semantic>");
			}
		} else if (element instanceof IHandlePart) {
			buffer = new StringBuffer("[Handle] ").append(buffer);
		} else if (element instanceof IFeedbackPart) {
			buffer = new StringBuffer("[Feedback] ").append(buffer);
		}

		if (element instanceof VisualPartContentProvider.AnchorsWrapper) {
			switch (((VisualPartContentProvider.AnchorsWrapper) element).kind) {
			case FEEDBACK:
				return "Feedbacks";
			case HANDLE:
				return "Handles";
			case CONTENT:
				return "Anchors";
			}
		}

		return buffer.toString();
	}
}
