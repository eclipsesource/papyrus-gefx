package org.eclipse.papyrus.gef4.gmf.style;

import org.eclipse.papyrus.gef4.gmf.utils.NotationUtil;
import org.eclipse.papyrus.gef4.services.style.LabelStyleService;
import org.eclipse.papyrus.gef4.utils.TextOverflowEnum;

public class NotationLabelStyleProvider extends AbstractNotationStyleService implements LabelStyleService {

	@Override
	public TextOverflowEnum getTextOverflow() {
		return NotationUtil.getTextOverflow(getView());
	}

}
