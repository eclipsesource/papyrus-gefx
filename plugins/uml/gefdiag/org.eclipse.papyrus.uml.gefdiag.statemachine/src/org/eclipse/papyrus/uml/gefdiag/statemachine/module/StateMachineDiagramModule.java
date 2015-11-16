package org.eclipse.papyrus.uml.gefdiag.statemachine.module;

import org.eclipse.gef4.common.inject.AdaptableScopes;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.parts.IContentPartFactory;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.gef4.module.GEFFxModule;
import org.eclipse.papyrus.uml.gefdiag.statemachine.providers.ContentPartFactory;

import com.google.inject.TypeLiteral;

import javafx.scene.Node;

public class StateMachineDiagramModule extends GEFFxModule {

	public StateMachineDiagramModule(Diagram diagram) {
		super(diagram);
	}

	@Override
	protected void bindIContentPartFactory() {
		binder().bind(new TypeLiteral<IContentPartFactory<Node>>() {
		}).to(ContentPartFactory.class)
				.in(AdaptableScopes.typed(FXViewer.class));
	}

}
