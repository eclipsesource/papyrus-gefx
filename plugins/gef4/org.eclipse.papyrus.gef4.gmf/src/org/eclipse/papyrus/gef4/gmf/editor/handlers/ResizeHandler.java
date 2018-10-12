package org.eclipse.papyrus.gef4.gmf.editor.handlers;

import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gmf.runtime.common.core.command.ICommand;

public interface ResizeHandler {

	final int NORTH_WEST = 0;

	final int NORTH_EAST = 1;

	final int SOUTH_EAST = 2;

	final int SOUTH_WEST = 3;

	ICommand resize(Dimension delta, int direction);

	void showFeedback(Dimension delta, int direction);

	void removeFeedback();

}
