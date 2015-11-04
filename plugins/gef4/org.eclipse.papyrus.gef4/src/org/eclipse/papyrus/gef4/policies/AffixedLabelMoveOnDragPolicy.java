/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.policies;

import java.util.function.Consumer;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.gef4.geometry.planar.Dimension;
import org.eclipse.gef4.mvc.fx.policies.AbstractFXOnDragPolicy;
import org.eclipse.gef4.mvc.fx.tools.FXClickDragTool;
import org.eclipse.gef4.mvc.models.SelectionModel;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.gef4.Activator;
import org.eclipse.papyrus.gef4.model.ChangeBoundsModel;
import org.eclipse.papyrus.gef4.parts.NotationContentPart;
import org.eclipse.papyrus.gef4.utils.BoundsUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * The Class AffixedLabelMoveOnDragPolicy.
 */
public class AffixedLabelMoveOnDragPolicy extends AbstractFXOnDragPolicy {

	/**
	 * Called on Drag.
	 *
	 * @param e
	 *            the e
	 * @param delta
	 *            the delta
	 * @see org.eclipse.gef4.mvc.fx.policies.AbstractFXOnDragPolicy#drag(javafx.scene.input.MouseEvent, org.eclipse.gef4.geometry.planar.Dimension)
	 */
	@Override
	public void drag(final MouseEvent e, final Dimension delta) {


		propagate(e, delta, policy -> policy.drag(e, delta));

		// Nothing
		final ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);

		//
		final Bounds bounds = NotationFactory.eINSTANCE.createBounds();
		final Location location = getLocation();
		if (null != location) {
			bounds.setX(location.getX());
			bounds.setY(location.getY());
		}

		final Location newLocation = computeNewLocation(bounds, delta);

		final Node visual = getPrimaryHost().getVisual();

		final Bounds newBounds = NotationFactory.eINSTANCE.createBounds();

		newBounds.setX(newLocation.getX());
		newBounds.setY(newLocation.getY());

		newBounds.setWidth(BoundsUtil.getWidth(visual));
		newBounds.setHeight(BoundsUtil.getHeight(visual));

		if (null != newBounds) {
			boundsModel.addManagedElement(getPrimaryHost(), newBounds);
		}

	}



	/**
	 * Called on Press.
	 *
	 * @param e
	 *            the e
	 * @see org.eclipse.gef4.mvc.fx.policies.AbstractFXOnDragPolicy#press(javafx.scene.input.MouseEvent)
	 */
	@Override
	public void press(final MouseEvent e) {
		// Nothing
	}

	/**
	 * coverte the position from double to pixels.
	 *
	 * @param pos
	 *            the pos
	 * @return the int
	 */
	protected final int toPixels(final double pos) {
		return (int) Math.round(pos);
	}

	/**
	 * Propagate the event. //TODO to verify
	 *
	 * @param e
	 *            the e
	 * @param delta
	 *            the delta
	 * @param actionToPropagate
	 *            the action to propagate
	 */
	protected void propagate(final MouseEvent e, final Dimension delta, final Consumer<AbstractFXOnDragPolicy> actionToPropagate) {
		final SelectionModel<Node> selectionModel = getHost().getRoot().getViewer().getAdapter(SelectionModel.class);

		if (selectionModel.getSelection().size() > 1) {

			// If I'm the main receiver of the event, I propagate it to other selected elements
			// If I'm not the main receiver, do nothing; someone else will do the propagation
			if (e.getTarget() == getHost().getVisual()) {

				for (final IContentPart<Node, ? extends Node> selectedPart : selectionModel.getSelection()) {
					if (selectedPart != getPrimaryHost()) {
						for (final AbstractFXOnDragPolicy dragPolicy : selectedPart.getAdapters(FXClickDragTool.DRAG_TOOL_POLICY_KEY).values()) {
							actionToPropagate.accept(dragPolicy);
						}
					}
				}

			}
		}
	}

	/**
	 * Called on Release.
	 *
	 * @param e
	 *            the e
	 * @param delta
	 *            the delta
	 * @see org.eclipse.gef4.mvc.fx.policies.AbstractFXOnDragPolicy#release(javafx.scene.input.MouseEvent, org.eclipse.gef4.geometry.planar.Dimension)
	 */
	@Override
	public void release(final MouseEvent e, final Dimension delta) {

		// Propagation, in case of multi-selection

		propagate(e, delta, policy -> policy.release(e, delta));

		// Own behavior
		final ChangeBoundsModel boundsModel = getHost().getRoot().getViewer().getAdapter(ChangeBoundsModel.class);

		Location location = getLocation();
		if (null == location) {
			//If Constraint doesn't exit create it.
			location = NotationFactory.eINSTANCE.createLocation();
			final View hostView = NotationHelper.findView(getHost());
			if (hostView instanceof org.eclipse.gmf.runtime.notation.Node) {
				final SetRequest setRequest = new SetRequest(hostView, NotationPackage.Literals.NODE__LAYOUT_CONSTRAINT, location);

				final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(hostView);
				if (provider != null) {
					final CompositeCommand command = new CompositeCommand("Create Constraint");
					command.add(provider.getEditCommand(setRequest));
					AdapterFactoryEditingDomain.getEditingDomainFor(hostView).getCommandStack().execute(new GMFtoEMFCommandWrapper(command));
				}

			}
		}
		final Location newLocation = computeNewLocation(location, delta);

		if (location == null || newLocation == null) {
			boundsModel.removeManagedElement(getPrimaryHost());
			return;
		}

		final SetRequest setXRequest = new SetRequest(location, NotationPackage.Literals.LOCATION__X, newLocation.getX());
		final SetRequest setYRequest = new SetRequest(location, NotationPackage.Literals.LOCATION__Y, newLocation.getY());

		final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(location);
		if (provider != null) {
			final CompositeCommand moveCommand = new CompositeCommand("Move element");
			moveCommand.add(provider.getEditCommand(setXRequest));
			moveCommand.add(provider.getEditCommand(setYRequest));

			AdapterFactoryEditingDomain.getEditingDomainFor(location).getCommandStack().execute(new GMFtoEMFCommandWrapper(moveCommand));
		}

		try {
			boundsModel.removeManagedElement(getPrimaryHost());
		} catch (final Exception ex) {
			Activator.error(ex);
		}
	}

	/**
	 * Compute new location.
	 *
	 * @param currentLocation
	 *            the current location
	 * @param delta
	 *            the delta
	 * @return the location
	 */
	protected Location computeNewLocation(final Location currentLocation, final Dimension delta) {
		if (currentLocation == null || delta == null) {
			return null;
		}

		final Location newLocation = NotationFactory.eINSTANCE.createLocation();

		final int xOffset = toPixels(delta.getWidth());
		final int yOffset = toPixels(delta.getHeight());

		if (xOffset == 0 && yOffset == 0) {
			return null;
		}

		newLocation.setX(currentLocation.getX() + xOffset);
		newLocation.setY(currentLocation.getY() + yOffset);


		return newLocation;
	}

	/**
	 * Gets the primary host.
	 *
	 * @return the primary host
	 */
	protected IVisualPart<Node, ? extends Node> getPrimaryHost() {
		IVisualPart<Node, ? extends Node> host = getHost();
		if (host instanceof NotationContentPart) {
			host = ((NotationContentPart<?, ?>) host).getPrimaryContentPart();
		}
		return host;
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	protected Location getLocation() {
		// For label case which return location
		final LayoutConstraint layout = getLayoutConstraint();
		if (layout instanceof Location) {
			return (Location) layout;
		}
		return null;
	}

	/**
	 * Gets the layout constraint.
	 *
	 * @return the layout constraint
	 */
	protected LayoutConstraint getLayoutConstraint() {
		final IVisualPart<?, ?> host = getPrimaryHost();

		if (host == null) {
			return null;
		}

		final View hostView = NotationHelper.findView(host);
		if (hostView instanceof org.eclipse.gmf.runtime.notation.Node) {
			return ((org.eclipse.gmf.runtime.notation.Node) hostView).getLayoutConstraint();
		}
		return null;

	}

}
