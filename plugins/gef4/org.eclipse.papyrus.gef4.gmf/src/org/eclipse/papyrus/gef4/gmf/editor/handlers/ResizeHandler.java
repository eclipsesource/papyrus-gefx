package org.eclipse.papyrus.gef4.gmf.editor.handlers;

import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.gef4.handle.Direction;

public interface ResizeHandler {

	ICommand resize(Dimension delta, Direction direction);

	void showFeedback(Dimension delta, Direction direction);

	void removeFeedback();

}
