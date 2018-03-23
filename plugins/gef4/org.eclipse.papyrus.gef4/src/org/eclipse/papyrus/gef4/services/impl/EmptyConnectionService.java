package org.eclipse.papyrus.gef4.services.impl;

import java.util.Collections;
import java.util.List;

import org.eclipse.gef.mvc.fx.parts.IBendableContentPart.BendPoint;
import org.eclipse.papyrus.gef4.services.ConnectionService;

public class EmptyConnectionService implements ConnectionService {

	@Override
	public List<BendPoint> getModelBendpoints() {
		return Collections.emptyList();
	}

}
