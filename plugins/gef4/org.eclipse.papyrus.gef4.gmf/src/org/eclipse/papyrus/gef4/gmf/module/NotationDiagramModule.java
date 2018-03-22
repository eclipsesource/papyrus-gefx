package org.eclipse.papyrus.gef4.gmf.module;

import org.eclipse.gmf.runtime.notation.Diagram;

import com.google.inject.AbstractModule;

/**
 * A {@link DiagramModule} using a GMF Notation {@link Diagram} model
 */
public class NotationDiagramModule extends AbstractModule {

	private Diagram diagram;

	public NotationDiagramModule(Diagram diagram) {
		this.diagram = diagram;
	}

	@Override
	protected void configure() {
		binder().bind(Diagram.class).toInstance(diagram);
	}

}
