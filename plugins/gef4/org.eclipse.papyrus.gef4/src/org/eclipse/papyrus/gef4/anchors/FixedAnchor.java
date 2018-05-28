package org.eclipse.papyrus.gef4.anchors;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.core.runtime.Assert;
import org.eclipse.fx.core.Subscription;
import org.eclipse.fx.core.observable.FXObservableUtil;
import org.eclipse.gef.fx.anchors.AnchorKey;
import org.eclipse.gef.fx.anchors.DynamicAnchor;
import org.eclipse.gef.fx.anchors.IComputationStrategy.Parameter;
import org.eclipse.gef.fx.anchors.OrthogonalProjectionStrategy;
import org.eclipse.gef.geometry.convert.fx.FX2Geometry;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.papyrus.gef4.utils.Position;

import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.Node;

public class FixedAnchor extends DynamicAnchor {

	private final Position position;
	private final Orientation orientation; // Depends on Position

	private Subscription anchorageSubscription;
	private final Map<AnchorKey, Subscription> anchorKeySubscription = new HashMap<>();

	public FixedAnchor(Node anchorage, Position anchorPosition) {
		super(anchorage, new OrthogonalProjectionStrategy());
		Assert.isNotNull(anchorPosition);
		this.position = anchorPosition;

		if (anchorPosition.is(Position.EAST) || anchorPosition.is(Position.WEST)) {
			orientation = Orientation.HORIZONTAL;
		} else {
			orientation = Orientation.VERTICAL;
		}
	}

	@Override
	public void attach(AnchorKey key) {
		if (getKeys().isEmpty()) {
			anchorageSubscription = FXObservableUtil.onChange(getAnchorage().layoutBoundsProperty(), newBounds -> {
				updateReferencePoints();
			});
		}
		super.attach(key);

		Subscription keySubX = FXObservableUtil.onChange(key.getAnchored().layoutXProperty(), newBounds -> {
			updateReferencePoint(key);
		});
		Subscription keySubY = FXObservableUtil.onChange(key.getAnchored().layoutYProperty(), newBounds -> {
			updateReferencePoint(key);
		});
		Subscription keySubscription = () -> {
			keySubX.dispose();
			keySubY.dispose();
		};
		anchorKeySubscription.put(key, keySubscription);

		updateReferencePoint(key);
		getComputationParameter(key, PreferredOrientation.class).set(orientation);
	}

	private void updateReferencePoints() {
		getKeys().forEach(this::updateReferencePoint);
	}

	private void updateReferencePoint(AnchorKey anchorKey) {
		Point refPointForKey = getReferencePoint(anchorKey);
		AnchoredReferencePoint referencePoint = getComputationParameter(anchorKey, AnchoredReferencePoint.class);
		if (!Objects.equals(referencePoint.get(), refPointForKey)) {
			referencePoint.set(refPointForKey);
		}
	}

	private Point getReferencePoint(AnchorKey key) {
		Bounds layoutBounds = getAnchorage().getLayoutBounds();

		final double x;
		if (position.is(Position.WEST)) {
			x = 0;
		} else if (position.is(Position.EAST)) {
			x = layoutBounds.getWidth();
		} else { // H-Center
			x = layoutBounds.getWidth() / 2;
		}

		final double y;
		if (position.is(Position.NORTH)) {
			y = 0;
		} else if (position.is(Position.SOUTH)) {
			y = layoutBounds.getHeight();
		} else { // V-Center
			y = layoutBounds.getHeight() / 2;
		}

		Point2D positionInScene = getAnchorage().localToScene(x, y);
		Point positionInAnchoredLocal = FX2Geometry.toPoint(key.getAnchored().sceneToLocal(positionInScene));
		return positionInAnchoredLocal;
	}

	@Override
	public void detach(AnchorKey key) {
		super.detach(key);
		if (getKeys().isEmpty()) {
			Subscription.disposeIfExists(anchorageSubscription);
			anchorageSubscription = null;
		}

		if (anchorKeySubscription.containsKey(key)) {
			anchorKeySubscription.get(key).dispose();
			anchorKeySubscription.remove(key);
		}
	}

	@Override
	protected Point computePosition(AnchorKey key) {
		return super.computePosition(key);
	}

	public static class AnchorPosition extends Parameter<Position> {

		public AnchorPosition() {
			super(Kind.ANCHORAGE, false);
			set(Position.NORTH_WEST);
		}

	}

}
