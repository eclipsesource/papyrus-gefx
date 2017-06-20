/*****************************************************************************
 * Copyright (c) 2015 CEA LIST, Montages AG and others
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Michael Golubev (Montages) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.fx.anchors;

import java.util.Set;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.gef.fx.anchors.DynamicAnchor;
import org.eclipse.gef.fx.anchors.ProjectionStrategy;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gmf.runtime.draw2d.ui.figures.BaseSlidableAnchor;
import org.eclipse.gmf.runtime.notation.Anchor;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.papyrus.gef4.utils.AnchorParser;

import javafx.scene.Node;

/**
 * Extension of the {@link DynamicAnchor} which uses configurable
 * point inside the host bounds as a target point for connection
 * (which is always the center of the host for {@link ChopboxAnchor}).
 * <p/>
 * This way position of the visible anchor point depends both on the reference point
 * and the target point, providing anchor with ability to slide over the host bounds.
 * <p/>
 * Position of the target is specified as a ratio. However it is bound to the real
 * position at the creation time and is not recomputed on the host resize. To handle resize,
 * some external code should replace an anchor instance.
 */
public class SlidableFxAnchor extends DynamicAnchor {

	private final RatioBasedAnchorStrategy myStrategy;

	public SlidableFxAnchor(Node visual, double ratioX, double ratioY) {
		this(visual, new RatioBasedAnchorStrategy(ratioX, ratioY));
	}

	public SlidableFxAnchor(Node visual, Anchor notationAnchor) {
		this(visual, strategyFromNotationAnchor(notationAnchor));
	}

	protected SlidableFxAnchor(Node visual, RatioBasedAnchorStrategy strategy) {
		super(visual, strategy);
		myStrategy = strategy;
	}

	public String composeTerminalString() {
		return composeTerminalString(getRatioX(), getRatioY());
	}

	/**
	 * Creates a terminal string for any reference point passed in the format understandable by
	 * slidable anchors, namely as <code>"(ratioX,ratioY)"</code>
	 *
	 * @see BaseSlidableAnchor#getTerminal()
	 */
	public static String composeTerminalString(double ratioX, double ratioY) {
		StringBuffer s = new StringBuffer(24);
		s.append('('); // 1 char
		s.append(ratioX); // 10 chars
		s.append(','); // 1 char
		s.append(ratioY); // 10 chars
		s.append(')'); // 1 char
		return s.toString(); // 24 chars max (+1 for safety, i.e. for string termination)
	}

	public double getRatioX() {
		return myStrategy.getRatioX();
	}

	public double getRatioY() {
		return myStrategy.getRatioY();
	}

	protected static RatioBasedAnchorStrategy strategyFromNotationAnchor(Anchor notationAnchor) {
		double ratioX = 0.;
		double ratioY = 0.;
		if (notationAnchor instanceof IdentityAnchor) {
			String id = ((IdentityAnchor) notationAnchor).getId();
			if (id != null && id.length() != 0) {
				ratioX = AnchorParser.getX(id);
				ratioY = AnchorParser.getY(id);
			}
		}
		return new RatioBasedAnchorStrategy(ratioX, ratioY);
	}

	/**
	 * Overrides default {@link FXChopBoxAnchor.ComputationStrategy.Impl} with customization of
	 * the position of the anchor in the host bounds, which is set as a ratio.
	 */
	public static class RatioBasedAnchorStrategy extends ProjectionStrategy {

		private final double myRatioX;
		private final double myRatioY;

		/**
		 * @param ratioX
		 *            the ratio of the x-position of anchor to the host width. Value of 1.0 means the right side of the host.
		 * @param ratioY
		 *            the ratio of the y-position of anchor to the host heights. Value of 1.0 means the bottom side of the host.
		 */
		public RatioBasedAnchorStrategy(double ratioX, double ratioY) {
			myRatioX = ratioX;
			myRatioY = ratioY;
		}

		@Override
		public Point computePositionInScene(Node anchorage, Node anchored, Set<Parameter<?>> parameters) {
			// TODO reimplement this class to match the refactoring of ChopBoxAnchor in GEF4 (Bug 488355, commit 05157a8 and newer)
			return super.computePositionInScene(anchorage, anchored, parameters);
		}

		public double getRatioX() {
			return myRatioX;
		}

		public double getRatioY() {
			return myRatioY;
		}

	}

}
