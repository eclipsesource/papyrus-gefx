package org.eclipse.papyrus.gef4.gmf.module;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.gef4.module.DiagramModule;

/**
 * A {@link DiagramModule} using a GMF Notation {@link Diagram} model
 */
public class NotationDiagramModule extends DiagramModule<Diagram> {

	public NotationDiagramModule(Diagram diagram) {
		super(Diagram.class, diagram);
	}

}
