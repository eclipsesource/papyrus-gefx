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
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef4.common.adapt.AdapterKey;
import org.eclipse.gef4.mvc.fx.parts.AbstractFXContentPart;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gef4.mvc.viewer.IViewer;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.FillStyle;
import org.eclipse.gmf.runtime.notation.FontStyle;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.Activator;
import org.eclipse.papyrus.gef4.layout.Locator;
import org.eclipse.papyrus.gef4.provider.IContentChildrenProvider;
import org.eclipse.papyrus.gef4.utils.BorderColors;
import org.eclipse.papyrus.gef4.utils.BorderStrokeStyles;
import org.eclipse.papyrus.gef4.utils.FXUtils;
import org.eclipse.papyrus.gef4.utils.NotationUtil;
import org.eclipse.papyrus.gef4.utils.ShapeTypeEnum;
import org.eclipse.papyrus.gef4.utils.SynchronizedLogger;
import org.eclipse.swt.widgets.Display;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.inject.Inject;

import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * Parent class for all GEF4 ContentParts based on GMF Runtime/Notation. The Content is a {@link View}
 *
 * @author Camille Letavernier
 *
 * @param <V>
 *            The {@link View} represented by this ContentPart
 * @param <N>
 *            The FX {@link Node} used to display this ContentPart
 */
public abstract class NotationContentPart<V extends View, N extends Node> extends AbstractFXContentPart<N> implements IAdaptable {

	private final NotificationListener notificationListener;

	private final V view;

	private TransactionalEditingDomain editingDomain;

	private final EObject semanticElement;

	private Locator locator;

	private IContentChildrenProvider<View> contentChildrenProvider;

	/**
	 * Some changes may happen outside the doCreateVisual/doRefreshVisual methods (e.g. during JavaFX rendering)
	 * Since it is not possible to catch these changes directly, we use a listener to refresh the visualPartMap
	 */
	protected final ListChangeListener<Node> nodeChildrenListener = (change) -> updateOnChange(change);

	// The list of contentChildren, as known by GEF4. It may be different from View#getChildren during updates, as items will be added/removed 1 by 1
	protected final List<View> contentChildren = new ArrayList<>();

	public NotationContentPart(final V view) {
		Assert.isNotNull(view);
		this.view = view;
		this.semanticElement = findSemanticElement(); // May be null
		setContent(view);

		if (semanticElement != null) { // TODO: The adapters will be missing if we're in this case. Check the new implementation of GEF4 AdapterSupport, the adapters may be incorrectly declared here
			setAdapter(semanticElement, "semantic");
			setAdapter(semanticElement, AdapterKey.DEFAULT_ROLE);
		}
		setAdapter(view, "notation");

		notificationListener = createNotificationListener();
	}

	/**
	 * Delegates to {@link #doCreateVisual()} for creating the actual FX {@link Node}, and registers a style class
	 *
	 * @see {@link #getStyleClass()}
	 */
	@Override
	protected final N createVisual() {
		final N visual = doCreateVisual();
		if (visual != null) {
			visual.getStyleClass().add(getStyleClass());
		}
		return visual;
	}

	protected abstract N doCreateVisual();

	private void updateContentChildren() {
		// TODO Update order?


		// FIXME: This operation will have a high complexity:
		// - n = number of children, c = number of changes (Addition/Removal)
		// - We iterate on actual model children & ContentPart contentChildren lists (n²)
		// - For each added/removed item, the super implementation will iterate several times on the list again to check the validity of the operation (3n * c)
		// - The super implementation will do a setAll for each added/removed item (c * n)
		// However, the number of changes should, in general, be limited (The worst case being e.g. the creation of a model element with "a lot" of compartments, with n = c ~= 10)
		// It may however be noticeable when opening a diagram as this list will be updated for all elements in the model
		List<? extends View> modelChildren = getContentChildren();
		for (View modelChild : modelChildren) {
			if (!contentChildren.contains(modelChild)) {
				addContentChild(modelChild, contentChildren.size());
			}
		}
		for (View contentChild : new ArrayList<View>(contentChildren)) {
			if (!modelChildren.contains(contentChild)) {
				removeContentChild(contentChild);
			}
		}

	}

	protected void installDefaultPolicies() {
		// It is recommended to use dependency injection instead of hard-coding
	}

	/**
	 * @return The view associated with this content part. Never null
	 */
	public final V getView() {
		return view;
	}

	/**
	 * Returns the Primary ContentPart containing this part
	 *
	 * The Primary content part is the top-most part representing a given semantic element
	 *
	 * For example, Compartments are usually not primary content parts, because
	 * they are owned by a Node content part representing the same semantic element
	 *
	 * This method may return the current part (If it is a Primary part)
	 *
	 * @return
	 */
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

	protected IContentPart<Node, ? extends Node> getContentPart(final View forView) {
		if (forView == null) {
			return null;
		}
		return getViewer().getContentPartMap().get(forView);
	}

	/**
	 * The NotificationListener used to listen to changes on View/Model
	 *
	 * It should call {@link #updateContentChildren()} and {@link #refreshVisual()} as necessary
	 *
	 * @return
	 */
	protected NotificationListener createNotificationListener() {

		// FIXME: This listener is difficult to extend in subclasses
		return new NotificationListener() {

			@Override
			public void notifyChanged(final Notification msg) {
				if (!isActive()) {
					return;
				}

				if (!(msg.isTouch())) {
					if (childrenChanged(msg)) {
						updateContentChildren();
					}
					// FIXME: Do not refresh immediately. If we use a ThreadSafeDiagramEventBroker, this will happen synchronously
					// (The command execution will not complete until the view is fully refreshed)
					// Rather mark the view as "needing refresh" and schedule a refresh runnable
					refreshVisual();
				}
			}
		};
	}

	/**
	 * Do not invoke this method. Subclasses should invoke (and implement) {@link #refreshVisualInTransaction(Node)}
	 *
	 * Clients of the public API should invoke {@link #refreshVisual()}
	 */
	@Override
	protected final void doRefreshVisual(final N visual) {
		SynchronizedLogger.log("RefreshVisual");

		if (Display.getCurrent() == null) {
			Display.getDefault().asyncExec(() -> refreshInUIThread(visual));
		} else {
			refreshInUIThread(visual);
		}
	}

	private final void refreshInUIThread(final N visual) {
		try {
			getDomain().runExclusive(() -> {
				refreshVisualInTransaction(visual);
			});
		} catch (InterruptedException ex) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "An error occurred while refreshing the content part", ex));
		}
	}

	protected void updateOnChange(Change<? extends Node> change) {
		IViewer<Node> viewer = getRoot().getViewer();
		while (change.next()) {
			for (Node removedNode : change.getRemoved()) {
				unregisterVisuals(viewer, removedNode);
			}

			for (Node addedNode : change.getAddedSubList()) {
				registerVisuals(viewer, addedNode);
			}
		}
	}

	@Override
	protected void registerAtVisualPartMap(IViewer<Node> viewer, N visual) {
		// Override the parent behavior to ensure that listeners are properly installed when required
		registerVisuals(viewer, visual);
	}

	@Override
	protected void unregisterFromVisualPartMap(IViewer<Node> viewer, N visual) {
		// Override the parent behavior to ensure that listeners are properly uninstalled
		unregisterVisuals(viewer, visual);
	}

	protected void unregisterVisuals(IViewer<Node> viewer, Node visual) {
		Map<Node, IVisualPart<Node, ? extends Node>> visualPartMap = viewer.getVisualPartMap();

		// Do not unregister child content parts; only child figures for this content part
		if (visualPartMap.get(visual) == this) {
			visualPartMap.remove(visual);

			if (visual instanceof Parent) {
				Parent parentVisual = (Parent) visual;
				for (Node childNode : parentVisual.getChildrenUnmodifiable()) {
					unregisterVisuals(viewer, childNode);
				}
				parentVisual.getChildrenUnmodifiable().removeListener(nodeChildrenListener);
			}
		}
	}

	protected void registerVisuals(IViewer<Node> viewer, Node visual) {
		Map<Node, IVisualPart<Node, ? extends Node>> visualPartMap = viewer.getVisualPartMap();

		IVisualPart<Node, ? extends Node> currentPart = visualPartMap.get(visual);
		if (currentPart == null) { // Not yet registered
			visualPartMap.put(visual, this);
		}

		if (visual instanceof Parent && (currentPart == null || currentPart == this)) { // Do not register nested visuals if they are associated with a child part
			Parent parentVisual = (Parent) visual;
			parentVisual.getChildrenUnmodifiable().addListener(nodeChildrenListener);
			for (Node child : parentVisual.getChildrenUnmodifiable()) {
				registerVisuals(viewer, child);
			}
		}
	}

	protected DiagramEventBroker getEventBroker() {
		TransactionalEditingDomain domain = getDomain();
		if (domain == null) {
			return null;
		}
		return DiagramEventBroker.getInstance(domain);
	}

	protected TransactionalEditingDomain getDomain() {
		if (editingDomain == null) {
			editingDomain = TransactionUtil.getEditingDomain(getView());
		}
		return editingDomain;
	}

	protected void refreshVisualInTransaction(final N visual) {
		SynchronizedLogger.log("RefreshVisualInTransaction");
		// Nothing
	}

	/**
	 * Returns true if the notification affects the children of this element
	 *
	 * @param msg
	 * @return
	 */
	protected boolean childrenChanged(final Notification msg) {
		if (msg.getNotifier() != getView()) {
			return false;
		}
		return msg.getFeature() == NotationPackage.Literals.VIEW__PERSISTED_CHILDREN || msg.getFeature() == NotationPackage.Literals.VIEW__TRANSIENT_CHILDREN;
	}

	/**
	 * The semantic element referenced by the view
	 * May be null
	 */
	public final EObject getElement() {
		return semanticElement;
	}

	protected EObject findSemanticElement() {
		final EObject element = getView().getElement();
		if (element == null) {
			if (this instanceof IPrimaryContentPart) {
				return null; // Do not go beyond the Primary part
			}

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
			// TODO Check this for floating labels
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
		return Math.max(20, bounds == null ? 20 : bounds.getHeight());
	}

	protected double getWidth() {
		final Bounds bounds = getBounds();
		return Math.max(20, bounds == null ? 20 : bounds.getWidth());
	}

	@Override
	protected FXViewer getViewer() {
		return (FXViewer) super.getViewer();
	}

	protected void installListeners() {
		DiagramEventBroker eventBroker = getEventBroker();
		eventBroker.addNotificationListener(getView(), notificationListener);
		if (semanticElement != null) {
			eventBroker.addNotificationListener(semanticElement, notificationListener);
		}
		final LayoutConstraint layout = getLayout();
		if (layout != null) {
			eventBroker.addNotificationListener(layout, notificationListener);
		}
	}

	@Override
	protected void doActivate() {
		installDefaultPolicies();

		super.doActivate();

		updateContentChildren();

		installListeners();
	}

	@Override
	protected void doDeactivate() {
		DiagramEventBroker eventBroker = getEventBroker();

		eventBroker.removeNotificationListener(getView(), notificationListener);
		EObject element = getElement();
		if (element != null) {
			eventBroker.removeNotificationListener(element, notificationListener);
		}
		final LayoutConstraint layout = getLayout();
		if (layout != null) {
			eventBroker.removeNotificationListener(layout, notificationListener);
		}
		super.doDeactivate();
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
	protected Pos getTextAlignment() {
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
	 * @return
	 * 		The FX Font, corresponding to the View's fontName and fontSize
	 */
	protected Font getFont() {
		return getFont(getFontSize());
	}

	/**
	 *
	 * @param fontSize
	 *            The font size (In pixels)
	 * @return
	 * 		The FX Font, corresponding to this View's fontName and specified fontSize (in pixels)
	 */
	protected Font getFont(int fontSize) {
		return new Font(getFontName(), FXUtils.scaleFont(fontSize));
	}

	/**
	 *
	 * @return
	 * 		The name of the font to use to render this part
	 */
	protected String getFontName() {
		View view = getView();
		FontStyle fontStyle = (FontStyle) view.getStyle(NotationPackage.eINSTANCE.getFontStyle());
		if (fontStyle == null) {
			return "Segoe UI"; //$NON-NLS-1$ //Default
		}

		return fontStyle.getFontName();
	}

	/**
	 *
	 * @return
	 * 		The size of the font to use to render this part
	 */
	protected int getFontSize() {
		View view = getView();
		FontStyle fontStyle = (FontStyle) view.getStyle(NotationPackage.eINSTANCE.getFontStyle());
		if (fontStyle == null) {
			return 9; // Default
		}

		return fontStyle.getFontHeight();
	}

	/**
	 *
	 * @return
	 * 		The font color, as a 24-bits integer (#00BBGGRR)
	 */
	protected int getNotationFontColor() {
		View view = getView();

		FontStyle fontStyle = (FontStyle) view.getStyle(NotationPackage.eINSTANCE.getFontStyle());
		if (fontStyle == null) {
			return 0; // Black, default
		}

		return fontStyle.getFontColor();
	}

	/**
	 *
	 * @return
	 * 		The font color, as a JavaFX Color
	 */
	protected Color getFontColor() {
		int color = getNotationFontColor();

		return Color.rgb(color & 255, (color >> 8) & 255, color >> 16 & 255); // & 255 to retain only the last 8-bits
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
		return ScrollBarPolicy.AS_NEEDED;
	}

	protected ScrollBarPolicy getHorizontalBarPolicy() {
		return ScrollBarPolicy.AS_NEEDED;
	}

	// protected int getNotationMinWidth() {
	// return NotationUtil.getNotationMinWidth(view);
	// }
	//
	// protected int getNotationMinHeight() {
	// return NotationUtil.getNotationMinHeight(view);
	// }

	protected final List<? extends View> getContentChildren() {
		return getContentChildrenProvider().getContentChildren(getView());
	}

	protected abstract String getStyleClass();// TODO support mutli styleClass named label should match on .genericLabel and .namedLabel

	@Override
	public SetMultimap<? extends Object, String> doGetContentAnchorages() {
		return HashMultimap.create();
	}

	@Override
	protected List<? extends Object> doGetContentChildren() {
		return contentChildren;
	}

	@Override
	protected void doAddContentChild(Object contentChild, int index) {
		Assert.isTrue(contentChild instanceof View);
		contentChildren.add(index, (View) contentChild);
	}

	@Override
	protected void doRemoveContentChild(Object contentChild) {
		contentChildren.remove(contentChild);
	}

	@Override
	protected void doReorderContentChild(Object contentChild, int newIndex) {
		Assert.isTrue(contentChild instanceof View);
		contentChildren.remove(contentChild);
		contentChildren.add(newIndex, (View) contentChild);
	}

	/**
	 * Sets the locator for this ContentPart
	 *
	 * @param locator
	 */
	public void setLocator(Locator locator) {
		this.locator = locator;
	}

	/**
	 *
	 * * @return the active Locator for this ContentPart
	 */
	public Locator getLocator() {
		return locator;
	}

	/**
	 *
	 * @return the (never null) content children provider for this content part. Defaults to NotationContentChildrenProvider.getInstance()
	 */
	protected final IContentChildrenProvider<View> getContentChildrenProvider() {
		return contentChildrenProvider;
	}

	@Inject
	protected void setContentChildrenProvider(IContentChildrenProvider<View> provider) {
		Assert.isNotNull(provider);
		this.contentChildrenProvider = provider;
	}

}


