package org.eclipse.papyrus.gef4.provider;

import org.eclipse.gef.fx.utils.NodeUtils;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.mvc.fx.providers.GeometricOutlineProvider;
import org.eclipse.papyrus.gef4.parts.ConnectionContentPart;

public class ConnectionOutlineProvider extends GeometricOutlineProvider {

	@Override
	public IGeometry get() {
		if (getAdaptable() instanceof ConnectionContentPart) {
			// For this part, the connection is wrapped in a group (That can owned floating labels)
			return NodeUtils.getGeometricOutline(((ConnectionContentPart<?>) getAdaptable()).getConnection());
		}
		return super.get();
	}

}
