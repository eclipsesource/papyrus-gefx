package org.eclipse.papyrus.gef4.gmf.editor.handlers;

import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.mvc.fx.handlers.IHandler;
import org.eclipse.gmf.runtime.common.core.command.ICommand;

public interface MoveHandler extends IHandler {

	ICommand move(Dimension delta);

	void showFeedback(Dimension delta);

	void removeFeedback();

}
