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
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.utils.ModelUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

import javafx.scene.Node;

public class ResizeNodeHandler extends AbstractHandler implements ResizeHandler {

	private static Logger logger = LoggerCreator.createLogger(ResizeNodeHandler.class);

	@Override
	public ICommand resize(Dimension delta, int direction) {
		final Bounds bounds = getBounds();
		if (bounds == null) {
			return null;
		}

		final Rectangle newBounds = computeNewBounds(bounds, delta, direction);

		final SetRequest setXRequest = new SetRequest(bounds, NotationPackage.Literals.LOCATION__X,
				(int) newBounds.getX());
		final SetRequest setYRequest = new SetRequest(bounds, NotationPackage.Literals.LOCATION__Y,
				(int) newBounds.getY());
		final SetRequest setWidthRequest = new SetRequest(bounds, NotationPackage.Literals.SIZE__WIDTH,
				(int) newBounds.getWidth());
		final SetRequest setHeightRequest = new SetRequest(bounds, NotationPackage.Literals.SIZE__HEIGHT,
				(int) newBounds.getHeight());

		final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(bounds);
		if (provider != null) {
			final CompositeCommand resizeCommand = new CompositeCommand("Resize element");
			resizeCommand.add(provider.getEditCommand(setXRequest));
			resizeCommand.add(provider.getEditCommand(setYRequest));
			resizeCommand.add(provider.getEditCommand(setWidthRequest));
			resizeCommand.add(provider.getEditCommand(setHeightRequest));

			return resizeCommand;
		}

		return null;
	}

	@Override
	public void showFeedback(Dimension delta, int direction) {
		final ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);
		final Rectangle newBounds = computeNewBounds(getBounds(), delta, direction);
		if (newBounds == null) { // If the host element doesn't have bounds (e.g. Connections)
			return;
		}
		boundsModel.addManagedElement(getPrimaryHost(), newBounds);
	}

	@Override
	public void removeFeedback() {
		try {
			final ChangeBoundsModel boundsModel = ModelUtil.getChangeBoundsModel(getHost());
			boundsModel.removeManagedElement(getPrimaryHost());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	protected Rectangle computeNewBounds(final Bounds bounds, final Dimension delta, int direction) {
		if (bounds == null) {
			return null;
		}

		final Rectangle newBounds = new Rectangle();

		final int xOffset = toPixels(delta.getWidth());
		final int yOffset = toPixels(delta.getHeight());

		if (xOffset == 0 && yOffset == 0) {
			return null;
		}

		int x, y, width, height;

		switch (direction) {
		case NORTH_WEST: // Change all
			x = bounds.getX() + xOffset;
			y = bounds.getY() + yOffset;

			width = getCurrentWidth() - xOffset;
			height = getCurrentHeight() - yOffset;
			break;
		case NORTH_EAST: // Only change Y position and size
			x = bounds.getX();
			y = bounds.getY() + yOffset;

			width = getCurrentWidth() + xOffset;
			height = getCurrentHeight() - yOffset;
			break;
		case SOUTH_EAST: // Only change size
			x = bounds.getX();
			y = bounds.getY();

			width = getCurrentWidth() + xOffset;
			height = getCurrentHeight() + yOffset;
			break;
		case SOUTH_WEST: // Only change X position and size
			x = bounds.getX() + xOffset;
			y = bounds.getY();

			width = getCurrentWidth() - xOffset;
			height = getCurrentHeight() + yOffset;
			break;
		default:
			return null;
		}

		newBounds.setX(x);
		newBounds.setY(y);
		newBounds.setWidth(Math.max(getHostMinWidth(), width));
		newBounds.setHeight(Math.max(getHostMinHeight(), height));

		return newBounds;
	}

	// FIXME Improve specification of MinWidth/MinHeight before implementing
	private int getHostMinWidth() {
		// if (getPrimaryHost() instanceof ContainerContentPart) {
		// return ((ContainerContentPart<?, ?>) getPrimaryHost()).getMinWidth();
		// }
		return -1;
	}

	private int getHostMinHeight() {
		// if (getPrimaryHost() instanceof ContainerContentPart) {
		// return ((ContainerContentPart<?, ?>) getPrimaryHost()).getMinHeight();
		// }
		return -1;
	}

	protected int getCurrentWidth() {
		final Node visual = getPrimaryHost().getVisual();
		final javafx.geometry.Bounds local = visual.getLayoutBounds();

		return toPixels(local.getWidth());
	}

	protected int getCurrentHeight() {
		final Node visual = getPrimaryHost().getVisual();
		final javafx.geometry.Bounds local = visual.getLayoutBounds();

		return toPixels(local.getHeight());
	}

	protected IVisualPart<? extends Node> getPrimaryHost() {
		IVisualPart<? extends Node> host = getHost();
		if (host instanceof BaseContentPart) {
			host = ((BaseContentPart<?, ?>) host).getPrimaryContentPart();
		}
		return host;
	}

	protected final int toPixels(final double pos) {
		return (int) Math.round(pos);
	}

	protected Bounds getBounds() {
		final IVisualPart<?> host = getPrimaryHost();

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
