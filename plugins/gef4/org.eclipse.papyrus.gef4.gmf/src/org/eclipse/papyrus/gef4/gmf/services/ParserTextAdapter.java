package org.eclipse.papyrus.gef4.gmf.services;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.common.adapt.IAdaptable;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.utils.AdapterUtil;
import org.eclipse.papyrus.gef4.services.TextAdapter;
import org.eclipse.papyrus.gef4.utils.GEFCommonAdapter;
import org.eclipse.papyrus.infra.gmfdiag.common.adapter.SemanticAdapter;

import javafx.scene.Node;

public class ParserTextAdapter extends IAdaptable.Bound.Impl<IContentPart<? extends Node>> implements TextAdapter {

	@Override
	public String getText() {
		IParser parser = getParser(getAdaptable());
		if (parser == null) {
			return "<No parser>";
		}

		return parser.getPrintString(new GEFCommonAdapter(getAdaptable()), ParserOptions.NONE.intValue());
	}

	protected final IParser getParser(IContentPart<?> contentPart) {
		return ParserService.getInstance().getParser(new SemanticAdapter(getElement(), getView()));
	}

	private EObject getElement() {
		return AdapterUtil.getSemanticElement(getAdaptable());
	}

	private View getView() {
		return AdapterUtil.getView(getAdaptable());
	}

}
