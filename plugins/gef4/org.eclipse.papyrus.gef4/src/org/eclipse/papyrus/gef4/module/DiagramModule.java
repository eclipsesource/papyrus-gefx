package org.eclipse.papyrus.gef4.module;

import org.eclipse.gmf.runtime.notation.Diagram;

import com.google.inject.Binder;
import com.google.inject.Module;

public class DiagramModule implements Module {

	private final Diagram diagram;

	public DiagramModule(Diagram diagram) {
		this.diagram = diagram;
	}

	@Override
	public void configure(Binder binder) {
		binder.bind(Diagram.class).toInstance(this.diagram);
	}

}
