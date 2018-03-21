package org.eclipse.papyrus.gef4.gmf.style;

import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;

import javafx.scene.paint.Color;

public class DiagramStyleProvider extends NotationStyleService {

	@Override
	public Color getBackgroundColor1() {
		String stringValue = NotationUtils.getStringValue(getView(), "fillColor", null);
		if (stringValue != null) {
			return Color.web(stringValue);
		}
		return null;
	}

	@Override
	public Color getBackgroundColor2() {
		String stringValue = NotationUtils.getStringValue(getView(), "gradientColor", null);
		if (stringValue != null) {
			return Color.web(stringValue);
		}
		return null;
	}

}
