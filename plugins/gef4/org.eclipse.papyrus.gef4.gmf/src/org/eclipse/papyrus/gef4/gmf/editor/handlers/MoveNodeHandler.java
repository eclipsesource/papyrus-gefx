package org.eclipse.papyrus.gef4.gmf.editor.handlers;

import org.eclipse.fx.core.log.Logger;
import org.eclipse.fx.core.log.LoggerCreator;
import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.model.ChangeBoundsModel;
import org.eclipse.papyrus.gef4.utils.BoundsUtil;
import org.eclipse.papyrus.gef4.utils.ModelUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

import javafx.scene.Node;

public class MoveNodeHandler extends AbstractHandler implements MoveHandler {

	private static Logger logger = LoggerCreator.createLogger(MoveNodeHandler.class);

	@Override
	public void showFeedback(Dimension delta) {
		final ChangeBoundsModel boundsModel = ModelUtil.getChangeBoundsModel(getHost());
		final Rectangle newBounds = computeNewBounds(getBounds(), delta);

		if (null != newBounds) {
			boundsModel.addManagedElement(getHost(), newBounds);
		}
	}

	@Override
	public ICommand move(Dimension delta) {
		final ChangeBoundsModel boundsModel = ModelUtil.getChangeBoundsModel(getHost());

		final Bounds bounds = getBounds();

		final Rectangle newBounds = computeNewBounds(bounds, delta);
		if (bounds == null || newBounds == null) {
			IVisualPart<? extends Node> primaryHost = getHost();
			if (boundsModel.getManagedElements().containsKey(primaryHost)) {
				boundsModel.removeManagedElement(getHost());
			}
			return null;
		}

		final SetRequest setXRequest = new SetRequest(bounds, NotationPackage.Literals.LOCATION__X,
				(int) newBounds.getX());
		final SetRequest setYRequest = new SetRequest(bounds, NotationPackage.Literals.LOCATION__Y,
				(int) newBounds.getY());

		final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(bounds);
		if (provider != null) {
			final CompositeCommand moveCommand = new CompositeCommand("Move element");
			moveCommand.add(provider.getEditCommand(setXRequest));
			moveCommand.add(provider.getEditCommand(setYRequest));
			return moveCommand;
		}

		return null;
	}

	@Override
	public void removeFeedback() {
		final ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);
		try {
			boundsModel.removeManagedElement(getHost());
		} catch (final Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	protected Rectangle computeNewBounds(final Bounds currentBounds, final Dimension delta) {
		if (currentBounds == null || delta == null) {
			return null;
		}

		final Rectangle newBounds = new Rectangle();

		final int xOffset = toPixels(delta.getWidth());
		final int yOffset = toPixels(delta.getHeight());

		if (xOffset == 0 && yOffset == 0) {
			return null;
		}

		newBounds.setX(currentBounds.getX() + xOffset);
		newBounds.setY(currentBounds.getY() + yOffset);

		final Node visual = getHost().getVisual();

		newBounds.setWidth(BoundsUtil.getWidth(visual));
		newBounds.setHeight(BoundsUtil.getHeight(visual));

		return newBounds;
	}

	protected final int toPixels(final double pos) {
		return (int) Math.round(pos);
	}

	protected Bounds getBounds() {
		final IVisualPart<?> host = getHost();

		if (host == null) {
			return null;
		}

		final View hostView = NotationHelper.findView(host);
		if (hostView instanceof org.eclipse.gmf.runtime.notation.Node) {
			final LayoutConstraint layout = ((org.eclipse.gmf.runtime.notation.Node) hostView).getLayoutConstraint();
			if (layout instanceof Bounds) {
				return (Bounds) layout;
			}
		}

		return null;
	}

}
