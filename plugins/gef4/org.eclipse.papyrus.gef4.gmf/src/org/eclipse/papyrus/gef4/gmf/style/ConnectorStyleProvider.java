package org.eclipse.papyrus.gef4.gmf.style;

import org.eclipse.papyrus.gef4.gmf.utils.NotationUtil;
import org.eclipse.papyrus.gef4.services.style.EdgeStyleService;

public class ConnectorStyleProvider extends AbstractNotationStyleService implements EdgeStyleService {

	@Override
	public String getSourceDecoration() {
		return NotationUtil.getSourceDecoration(getView());
	}

	@Override
	public String getTargetDecoration() {
		return NotationUtil.getTargetDecoration(getView());
	}
}
