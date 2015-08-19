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
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;

import com.google.common.reflect.TypeToken;

import javafx.scene.Node;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public abstract class NotationContentPart<V extends View, N extends Node> extends AbstractFXContentPart<N> {

	protected final Adapter changeListener;

	protected final V view;

	protected List<View> lastKnownChildren;

	public NotationContentPart(V view) {
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
		N visual = doCreateVisual();
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

		NotationContentPart<?, ?> parentPart = getParentContentPart();
		if (parentPart != null) {
			return parentPart.getPrimaryContentPart();
		}

		return null;
	}

	protected void installListeners() {
		getView().eAdapters().add(changeListener);
		EObject element = getElement();
		if (element != null) {
			element.eAdapters().add(changeListener);
		}
		LayoutConstraint layout = getLayout();
		if (layout != null) {
			layout.eAdapters().add(changeListener);
		}
	}

	protected IContentPart<Node, ? extends Node> getContentPart(View forView) {
		if (forView == null) {
			return null;
		}
		return getViewer().getContentPartMap().get(forView);
	}

	@Override
	protected void doDeactivate() {
		getView().eAdapters().remove(changeListener);
		EObject element = getElement();
		if (element != null) {
			element.eAdapters().remove(changeListener);
		}
		LayoutConstraint layout = getLayout();
		if (layout != null) {
			layout.eAdapters().remove(changeListener);
		}
		super.doDeactivate();
	}

	protected Adapter createAdapter() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				if (!isActive()) {
					return;
				}

				if (!(msg.isTouch())) {
					if (childrenChanged(msg)) {
						notifyChildrenChanged();
					}
					refreshVisual();
				}
			}
		};
	}

	protected void notifyChildrenChanged() {
		pcs.firePropertyChange(CONTENT_PROPERTY, lastKnownChildren, getContentChildren());
		updateLastKnownChildren();
	}

	protected boolean childrenChanged(Notification msg) {
		if (msg.getNotifier() != getView()) {
			return false;
		}
		return msg.getFeature() == NotationPackage.Literals.VIEW__PERSISTED_CHILDREN || msg.getFeature() == NotationPackage.Literals.VIEW__TRANSIENT_CHILDREN;
	}

	public EObject getElement() {
		EObject element = getView().getElement();
		if (element == null) {
			NotationContentPart<? extends View, ? extends Node> parent = getParentContentPart();
			if (parent != null) {
				return parent.getElement();
			}
		}
		return element;
	}

	protected NotationContentPart<? extends View, ? extends Node> getParentContentPart() {
		IVisualPart<Node, ? extends Node> parent = getParent();
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
		LayoutConstraint constraint = getLayout();
		if (constraint instanceof Bounds) {
			return (Bounds) constraint;
		}
		return null;
	}

	protected double getX() {
		Bounds bounds = getBounds();
		return bounds == null ? 0 : bounds.getX();
	}

	protected double getY() {
		Bounds bounds = getBounds();
		return bounds == null ? 0 : bounds.getY();
	}

	protected double getHeight() {
		Bounds bounds = getBounds();
		return bounds == null ? 0 : bounds.getHeight();
	}

	protected double getWidth() {
		Bounds bounds = getBounds();
		return bounds == null ? 0 : bounds.getWidth();
	}

	@Override
	protected FXViewer getViewer() {
		return (FXViewer) super.getViewer();
	}

	@Override
	protected void doActivate() {
		installDefaultPolicies();
		// refreshChildren();
		super.doActivate();
	}

	protected Color getBorderColor() {
		return Color.BLACK;
	}

	protected BorderStrokeStyle getBorderStyle() {
		return BorderStrokeStyle.SOLID;
	}

	protected CornerRadii getCornerRadii() {
		return new CornerRadii(5);
	}

	protected BorderWidths getBorderWidths() {
		return new BorderWidths(1);
	}

	@Override
	protected void doRefreshVisual(N visual) {
		refreshVisibility();
	}

	protected void refreshVisibility() {
		getVisual().setVisible(getView().isVisible());
	}

	protected IContentPartFactory<Node> getFactory() {
		IContentPartFactory<Node> factory = getViewer().getAdapter(new TypeToken<IContentPartFactory<Node>>() {
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

	protected abstract String getStyleClass();

}


