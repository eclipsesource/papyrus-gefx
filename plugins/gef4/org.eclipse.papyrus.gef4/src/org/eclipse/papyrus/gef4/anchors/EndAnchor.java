package org.eclipse.papyrus.gef4.anchors;

import org.eclipse.gef.fx.anchors.AbstractAnchor;
import org.eclipse.gef.fx.anchors.AnchorKey;
import org.eclipse.gef.fx.nodes.Connection;
import org.eclipse.gef.geometry.convert.fx.FX2Geometry;
import org.eclipse.gef.geometry.planar.Point;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;

/**
 * <p>
 * Anchor representing the End point of a Connection or Node.
 * </p>
 * <p>
 * For {@link Connection Connections}, this corresponds to {@link Connection#getStartPoint()}. For {@link Node Nodes},
 * this corresponds to the Bottom-Center point of the Node bounds.
 * </p>
 */
public class EndAnchor extends AbstractAnchor {

	public EndAnchor(Node anchorage) {
		super(anchorage);
	}

	@Override
	protected Point computePosition(AnchorKey key) {
		if (getAnchorage() instanceof Connection) {
			return computeConnectionAnchor((Connection) getAnchorage(), key);
		} else {
			return computeNodeAnchor(getAnchorage(), key);
		}
	}

	private Point computeNodeAnchor(Node anchorage, AnchorKey key) {
		Bounds layoutBounds = anchorage.getLayoutBounds();
		int center = (int) Math.round(layoutBounds.getMinX() + layoutBounds.getWidth() / 2);
		Point target = new Point(center, layoutBounds.getMaxY());
		return convertToAnchored(anchorage, key, target);
	}

	private Point computeConnectionAnchor(Connection anchorage, AnchorKey key) {
		Point target = anchorage.getEndPoint();
		return convertToAnchored(anchorage, key, target);
	}

	private Point convertToAnchored(Node anchorage, AnchorKey key, Point localToAnchorage) {
		Point2D positionInScene = anchorage.localToScene(new Point2D(localToAnchorage.x(), localToAnchorage.y()));
		Point position = FX2Geometry.toPoint(key.getAnchored().sceneToLocal(positionInScene));
		return position;
	}
}
