package org.eclipse.papyrus.infra.gefdiag.common.editor;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.models.SelectionModel;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.gef4.editor.GEFEditor;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.widgets.util.IRevealSemanticElement;

import javafx.scene.Node;

public abstract class GEF4DiagramEditor extends GEFEditor implements IRevealSemanticElement {

	public GEF4DiagramEditor(Diagram diagram) {
		super(diagram);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void revealSemanticElement(List<?> elementList) {
		FXViewer viewer = getViewer();
		if (viewer == null || !viewer.isActive()) {
			return;
		}

		List<IContentPart<Node, ? extends Node>> partsToReveal = new LinkedList<>();

		for (IContentPart<Node, ? extends Node> contentPart : viewer.getContentPartMap().values()) {
			EObject semanticElement = EMFHelper.getEObject(contentPart);
			if (elementList.contains(semanticElement)) {
				partsToReveal.add(contentPart);
			}
		}

		SelectionModel<Node> selectionModel = getSelectionModel();
		selectionModel.setSelection(partsToReveal);
	}

}
