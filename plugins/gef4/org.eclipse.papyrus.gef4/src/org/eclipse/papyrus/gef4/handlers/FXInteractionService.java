package org.eclipse.papyrus.gef4.handlers;

import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

public interface FXInteractionService {

	boolean isCtrlPressed();

	boolean isSelected(IVisualPart<?> part);

	IVisualPart<?> getPartAt(Point point);

	IContentPart<?> getContentPartAt(Point point);

	void clearSelection();

	/**
	 * @param part
	 */
	void deselect(IContentPart<?> part);

	/**
	 * @param part
	 */
	void select(IContentPart<?> part);

}
