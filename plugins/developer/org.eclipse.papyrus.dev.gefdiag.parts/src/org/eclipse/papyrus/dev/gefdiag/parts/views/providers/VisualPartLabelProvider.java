package org.eclipse.papyrus.dev.gefdiag.parts.views.providers;

import org.eclipse.gef.mvc.fx.parts.IFeedbackPart;
import org.eclipse.gef.mvc.fx.parts.IHandlePart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;

public class VisualPartLabelProvider extends LabelProvider {
	@Override
	public String getText(Object element) {
		StringBuffer buffer = new StringBuffer(element.getClass().getSimpleName());

		if (element instanceof BaseContentPart) {
			buffer = new StringBuffer("[Content] ").append(buffer);
			BaseContentPart<?, ?> part = (BaseContentPart<?, ?>) element;
			if (part.getContent() instanceof View) {
				buffer.append(": " + ((View) part.getContent()).eClass().getName());
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
