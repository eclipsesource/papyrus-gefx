package org.eclipse.papyrus.gef4.services;

import java.util.List;

import org.eclipse.gef.mvc.fx.parts.IBendableContentPart.BendPoint;

public interface ConnectionService {

	public List<BendPoint> getModelBendpoints();

}
