/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Layout and visualization API and Implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef4.common.adapt.AdapterKey;
import org.eclipse.gef4.mvc.fx.parts.AbstractFXContentPart;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IContentPartFactory;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.FillStyle;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.utils.BorderColors;
import org.eclipse.papyrus.gef4.utils.BorderStrokeStyles;
import org.eclipse.papyrus.gef4.utils.NotationUtil;
import org.eclipse.papyrus.gef4.utils.ShapeTypeEnum;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.reflect.TypeToken;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public abstract class NotationContentPart<V extends View, N extends Node> extends AbstractFXContentPart<N> {

	protected final Adapter changeListener;

	protected final V view;

	protected List<View> lastKnownChildren;

	public NotationContentPart(final V view) {
		Assert.isNotNull(view);
		this.view = view;
		setContent(view);

		setAdapter(AdapterKey.get(EObject.class, "semantic"), getElement());
		setAdapter(AdapterKey.get(EObject.class, AdapterKey.DEFAULT_ROLE), getElement());
		setAdapter(AdapterKey.get(View.class, "notation"), view);

		changeListener = createAdapter();

		updateLastKnownChildren();

		installListeners();
	}

	@Override
	protected final N createVisual() {
		final N visual = doCreateVisual();
		if (visual != null) {
			visual.getStyleClass().add(getStyleClass());
		}
		return visual;
	}

	protected abstract N doCreateVisual();

	private void updateLastKnownChildren() {
		lastKnownChildren = new ArrayList<View>(getContentChildren());
	}

	protected void installDefaultPolicies() {
		// It is recommended to use dependency injection instead of hard-coding
	}

	public boolean isPrimary() {
		return this instanceof IPrimaryContentPart;
	}

	public V getView() {
		return view;
	}

	public NotationContentPart<? extends View, ? extends Node> getPrimaryContentPart() {
		if (this instanceof IPrimaryContentPart) {
			return this;
		}

		final NotationContentPart<?, ?> parentPart = getParentContentPart();
		if (parentPart != null) {
			return parentPart.getPrimaryContentPart();
		}

		return null;
	}

	protected void installListeners() {
		getView().eAdapters().add(changeListener);
		final EObject element = getElement();
		if (element != null) {
			element.eAdapters().add(changeListener);
		}
		final LayoutConstraint layout = getLayout();
		if (layout != null) {
			layout.eAdapters().add(changeListener);
		}
	}

	protected IContentPart<Node, ? extends Node> getContentPart(final View forView) {
		if (forView == null) {
			return null;
		}
		return getViewer().getContentPartMap().get(forView);
	}

	@Override
	protected void doDeactivate() {
		getView().eAdapters().remove(changeListener);
		final EObject element = getElement();
		if (element != null) {
			element.eAdapters().remove(changeListener);
		}
		final LayoutConstraint layout = getLayout();
		if (layout != null) {
			layout.eAdapters().remove(changeListener);
		}
		super.doDeactivate();
	}

	/** flag to indicate that the refresh have been triggered with a resize of the element. */
	protected boolean decorationToRefresh;

	protected Adapter createAdapter() {
		return new AdapterImpl() {

			@Override
			public void notifyChanged(final Notification msg) {
				if (!isActive()) {
					return;
				}

				// Resize case
				if (msg.getFeature() == NotationPackage.Literals.SIZE__WIDTH || msg.getFeature() == NotationPackage.Literals.SIZE__HEIGHT) {
					if (msg.getOldValue() != msg.getNewValue()) {
						decorationToRefresh = true;
					} else {
						decorationToRefresh = false;
					}
				} else {
					decorationToRefresh = false;
				}

				if (!(msg.isTouch())) {
					if (childrenChanged(msg)) {
						notifyChildrenChanged();
					}
					refreshVisual();
				}
				decorationToRefresh = false;
			}
		};
	}

	@Override
	protected void doRefreshVisual(final N visual) {
		refreshVisibility();
	}

	protected void refreshVisibility() {
		getVisual().setVisible(getView().isVisible());
	}

	protected void notifyChildrenChanged() {
		pcs.firePropertyChange(CONTENT_PROPERTY, lastKnownChildren, getContentChildren());
		updateLastKnownChildren();
	}

	protected boolean childrenChanged(final Notification msg) {
		if (msg.getNotifier() != getView()) {
			return false;
		}
		return msg.getFeature() == NotationPackage.Literals.VIEW__PERSISTED_CHILDREN || msg.getFeature() == NotationPackage.Literals.VIEW__TRANSIENT_CHILDREN;
	}

	public EObject getElement() {
		final EObject element = getView().getElement();
		if (element == null) {
			final NotationContentPart<? extends View, ? extends Node> parent = getParentContentPart();
			if (parent != null) {
				return parent.getElement();
			}
		}
		return element;
	}

	protected NotationContentPart<? extends View, ? extends Node> getParentContentPart() {
		final IVisualPart<Node, ? extends Node> parent = getParent();
		if (parent instanceof NotationContentPart) {
			return (NotationContentPart<?, ?>) parent;
		}
		return null;
	}

	// May be null
	protected LayoutConstraint getLayout() {
		if (view instanceof org.eclipse.gmf.runtime.notation.Node) {
			return ((org.eclipse.gmf.runtime.notation.Node) view).getLayoutConstraint();
		}
		return null;
	}

	// May be null
	protected Bounds getBounds() {
		final LayoutConstraint constraint = getLayout();
		if (constraint instanceof Bounds) {
			return (Bounds) constraint;
		} else if (constraint instanceof Location) {
			final Bounds bounds = NotationFactory.eINSTANCE.createBounds();
			bounds.setX(((Location) constraint).getX());
			bounds.setY(((Location) constraint).getY());
			bounds.setWidth(-1);
			bounds.setHeight(-1);

			return bounds;
		}

		return null;
	}

	protected double getX() {
		final Bounds bounds = getBounds();
		return bounds == null ? 0 : bounds.getX();
	}

	protected double getY() {
		final Bounds bounds = getBounds();
		return bounds == null ? 0 : bounds.getY();
	}

	protected double getHeight() {
		final Bounds bounds = getBounds();
		return bounds == null ? 0 : bounds.getHeight();
	}

	protected double getWidth() {
		final Bounds bounds = getBounds();
		return bounds == null ? 0 : bounds.getWidth();
	}

	@Override
	protected FXViewer getViewer() {
		return (FXViewer) super.getViewer();
	}

	@Override
	protected void doActivate() {
		installDefaultPolicies();
		super.doActivate();
		refreshChildren();// TODO
	}

	// TODO to remove if possible
	protected void refreshChildren() {
		// refresh children
		final List<IVisualPart<Node, ? extends Node>> children = getChildren();
		for (final IVisualPart<Node, ? extends Node> child : children) {
			child.refreshVisual();
		}
	}

	protected BorderColors getBorderColors() {
		return NotationUtil.getBorderColor(view);
	}

	protected int getTransparency() {
		int transparency = 0;
		final FillStyle style = (FillStyle) view.getStyle(NotationPackage.Literals.FILL_STYLE);
		if (null != style) {
			transparency = style.getTransparency();
		}
		return transparency;
	}

	protected Color getBackgroundColor1() {
		return NotationUtil.getFillColor(view);
	}

	protected Color getBackgroundColor2() {
		return NotationUtil.getGradientColor(view);
	}

	/**
	 * Gets the background Paint.
	 *
	 * @return the background color
	 */
	protected Paint getBackgroundPaint() {
		return NotationUtil.getBackgroundPaint(view);
	}

	protected Point2D getBackgroundGradientStartPosition() {
		return NotationUtil.getBackgroundGradientStartPosition(view);
	}

	protected Point2D getBackgroundGradientEndPosition() {
		return NotationUtil.getBackgroundGradientEndPosition(view);
	}

	protected BorderStrokeStyles getBorderStyles() {
		return NotationUtil.getBorderStyle(view);
	}

	/**
	 * Gets the corner radii.
	 *
	 * @return the corner radii
	 */
	protected CornerRadii getCornerRadii() {
		return NotationUtil.getCornerRadii(view);
	}

	/**
	 * Gets the margin.
	 *
	 * @return the margin
	 */
	protected Insets getMargin() {
		return NotationUtil.getMargin(view);
	}


	/**
	 * Gets the padding.
	 *
	 * @return the padding
	 */
	protected Insets getPadding() {
		return NotationUtil.getPadding(view);
	}

	/**
	 * Gets the spacing.
	 *
	 * @return the spacing
	 */
	protected double getSpacing() {
		return NotationUtil.getSpacing(view);
	}

	/**
	 * Gets the shape type.
	 *
	 * @return the shape type
	 */
	protected ShapeTypeEnum getShapeType() {
		return NotationUtil.getShapeType(view);
	}

	/**
	 * Gets the border widths.
	 *
	 * @return the border widths
	 */
	protected BorderWidths getBorderWidths() {
		return NotationUtil.getBorderWidths(view);
	}

	/**
	 * Checks for double border.
	 *
	 * @return true, if successful
	 */
	protected boolean hasDoubleBorder() {
		return NotationUtil.hasDoubleBorder(view);
	}


	/**
	 * Gets the double border widths.
	 *
	 * @return the double border widths
	 */
	protected Insets getDoubleBorderWidths() {
		return NotationUtil.getDoubleBorderWidths(view);
	}

	/**
	 * Gets the text alignment.
	 *
	 * @return the text alignment
	 */
	public Pos getTextAlignment() {
		return NotationUtil.getTextAlignment(view);
	}

	/**
	 * Gets the shadow.
	 *
	 * @return the shadow
	 */
	protected DropShadow getShadow() {
		return NotationUtil.getShadow(view);
	}

	/**
	 * Gets the shadow color.
	 *
	 * @return the shadow color
	 */
	protected Color getShadowColor() {
		return NotationUtil.getShadowColor(view);
	}

	/**
	 * Gets the shadow width.
	 *
	 * @return the shadow width
	 */
	protected int getShadowWidth() {
		return NotationUtil.getShadowWidth(view);
	}

	/**
	 * Gets the effect.
	 *
	 * @return the effect
	 */
	protected Effect getEffect() {
		return NotationUtil.getEffect(view);
	}

	/**
	 * Gets the corner bend color.
	 *
	 * @return the corner bend color
	 */
	protected Paint getCornerBendColor() {
		return NotationUtil.getCornerBendColor(view);
	}

	/**
	 * Gets the corner bend width.
	 *
	 * @return the corner bend width
	 */
	protected double getCornerBendWidth() {
		return NotationUtil.getCornerBendWidth(view);
	}

	/**
	 * Gets the rotate.
	 *
	 * @return the rotate
	 */
	protected double getRotate() {
		return NotationUtil.getRotate(view);
	}

	protected ScrollBarPolicy getVerticalBarPolicy() {
		return NotationUtil.getVerticalBarPolicy(view);
	}

	protected ScrollBarPolicy getHorizontalBarPolicy() {
		return NotationUtil.getHorizontalBarPolicy(view);
	}

	protected int getNotationMinWidth() {
		return NotationUtil.getNotationMinWidth(view);
	}

	protected int getNotationMinHeight() {
		return NotationUtil.getNotationMinHeight(view);
	}

	protected IContentPartFactory<Node> getFactory() {
		final IContentPartFactory<Node> factory = getViewer().getAdapter(new TypeToken<IContentPartFactory<Node>>() {
		});

		if (factory == null) {
			System.err.println("FIXME: no content part factory found");
		}

		return factory;
	}

	@Override
	public List<View> getContentChildren() {
		return getView().getChildren();
	}

	protected abstract String getStyleClass();// TODO support mutli styleClass named label should match on .genericLabel and .namedLabel

	@Override
	public SetMultimap<? extends Object, String> getContentAnchorages() {
		return HashMultimap.create();
	}
}


