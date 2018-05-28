package org.eclipse.papyrus.gef4.anchors;

import org.eclipse.gef.fx.anchors.AbstractAnchor;
import org.eclipse.gef.fx.anchors.AnchorKey;
import org.eclipse.gef.geometry.convert.fx.FX2Geometry;
import org.eclipse.gef.geometry.planar.Point;

import javafx.geometry.Point2D;
import javafx.scene.Node;

public class DistanceAnchor extends AbstractAnchor {

	private final int height;

	public DistanceAnchor(Node anchorage, int height) {
		super(anchorage);
		this.height = height;
	}

	@Override
	protected Point computePosition(AnchorKey key) {
		Point2D positionInScene = getAnchorage().localToScene(new Point2D(0, height));

		Point position = FX2Geometry.toPoint(key.getAnchored().sceneToLocal(positionInScene));
		return position;
	}

}
