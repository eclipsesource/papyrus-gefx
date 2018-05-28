package org.eclipse.papyrus.gef4.gmf.editor.provider;

import java.util.function.Function;

import org.eclipse.gef.mvc.fx.providers.DefaultAnchorProvider;
import org.eclipse.gmf.runtime.notation.Anchor;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;

public abstract class TerminalAnchorProvider extends DefaultAnchorProvider {

	protected <T> String getAnchorTerminal(T connectionElement, Function<T, Anchor> anchorSide) {
		Anchor anchor = anchorSide.apply(connectionElement);
		return anchor instanceof IdentityAnchor ? ((IdentityAnchor) anchor).getId() : null;
	}
}
