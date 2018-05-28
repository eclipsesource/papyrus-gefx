package org.eclipse.papyrus.gef4.anchors;

import org.eclipse.gef.fx.anchors.AbstractAnchor;
import org.eclipse.gef.fx.anchors.AnchorKey;
import org.eclipse.gef.geometry.convert.fx.FX2Geometry;
import org.eclipse.gef.geometry.planar.Point;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;

public class SideAnchor extends AbstractAnchor {

	private final int heightDistance;
	private final Side side;

	public SideAnchor(Node anchorage, Side side, int heightDistance) {
		super(anchorage);
		this.heightDistance = heightDistance;
		this.side = side;
	}

	@Override
	protected Point computePosition(AnchorKey key) {
		Bounds layoutBounds = getAnchorage().getLayoutBounds();
		Point target = new Point(0, heightDistance);
		if (side == Side.RIGHT) {
			target.translate(layoutBounds.getWidth(), 0);
		}
		Point2D positionInScene = getAnchorage().localToScene(new Point2D(target.x(), target.y()));
		Point position = FX2Geometry.toPoint(key.getAnchored().sceneToLocal(positionInScene));
		return position;
	}

	public static enum Side {
		LEFT, RIGHT
	}

}
