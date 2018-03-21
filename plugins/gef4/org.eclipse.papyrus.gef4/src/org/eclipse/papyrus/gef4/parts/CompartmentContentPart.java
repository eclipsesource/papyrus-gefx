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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.Activator;
import org.eclipse.papyrus.gef4.services.style.CompartmentStyleService;
import org.eclipse.papyrus.gef4.utils.BorderColors;
import org.eclipse.papyrus.gef4.utils.BorderStrokeStyles;
import org.eclipse.papyrus.gef4.utils.FXUtils;
import org.eclipse.papyrus.gef4.utils.VisualPartUtil;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.When;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Structure:
 *
 * <pre>
 * VBox (Wrapper)
 * -- Label(Title)
 * -- ScrollPane
 * ---- ConcretePane(Subclass-dependent)
 * ------ Contents(Subclass-dependent)
 * </pre>
 *
 * @author Camille Letavernier
 *
 * @param <V>The
 *            kind of notation::View (Extending DecorationNode)
 * @param
 * 			<P>
 *            The concrete Pane type
 */
abstract public class CompartmentContentPart<MODEL, P extends Pane> extends ContainerContentPart<MODEL, VBox> {

	// A value smaller than 18 will prevent the scrollbars from working properly (Size of the scrollbar arrows)
	protected static final int MINIMUM_COMPARTMENT_SCROLLPANE_HEIGHT = 20;

	protected static final int MINIMUM_COMPARTMENT_HEIGHT = 8;

	protected VBox wrapper;

	protected P compartment;

	protected Label titleLabel;

	protected ScrollPane scrollPane;

	// Current state of the "collapsed" property
	protected SimpleBooleanProperty collapsed = new SimpleBooleanProperty(false);

	public CompartmentContentPart(final MODEL model) {
		super(model);
	}

	@Override
	protected final VBox doCreateVisual() {
		wrapper = new VBox();
		titleLabel = new Label();

		compartment = doCreatePane();

		if (compartment == null) {
			String message = String.format("The class %s did not properly implement the #doCreatePane() method. This method should never return null", getClass().getName());
			Activator.getDefault().getLog().log(new Status(IStatus.WARNING, Activator.PLUGIN_ID, message));
			scrollPane = new ScrollPane();
		} else {
			scrollPane = new ScrollPane(compartment);
		}

		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);

		bindMinHeight();

		VBox.setVgrow(titleLabel, Priority.NEVER);
		VBox.setVgrow(scrollPane, Priority.ALWAYS);
		VBox.setVgrow(wrapper, Priority.ALWAYS);

		// Set stylesheet to hide viewport child which can't
		scrollPane.getStylesheets().add(URI.createPlatformPluginURI(VisualPartUtil.VIEWPORT_SCROLL_PANE_STYLE, false).toPlatformString(false));

		wrapper.getChildren().addAll(titleLabel, scrollPane);

		wrapper.setMinHeight(MINIMUM_COMPARTMENT_HEIGHT);

		// refreshCollapsed(false);

		return wrapper;
	}

	protected void bindMinHeight() {
		// The isEmpty() property is not directly observable. We need to manually create an observable boolean property
		BooleanBinding isCompartmentEmpty = Bindings.createBooleanBinding(() -> compartment.getChildren().isEmpty(), compartment.getChildren()).or(collapsed);

		// If the collapse animation is running, always use the minimal min size, to get a fluid transition
		BooleanBinding useMinSize = isCompartmentEmpty.or(getCollapseTimeline().statusProperty().isEqualTo(Timeline.Status.RUNNING));

		// If the compartment is empty, then set a small minHeight. Otherwise, leave enough room to display at least one item
		scrollPane.minHeightProperty().bind(new When(useMinSize).then(0).otherwise(MINIMUM_COMPARTMENT_SCROLLPANE_HEIGHT));
	}

	/**
	 * Create the pane that will actually contain this part's children
	 *
	 * Specific to subclasses (e.g. VBox for a ListCompartment, Pane for a XYCompartment, ...)
	 *
	 * @return
	 */
	protected abstract P doCreatePane();

	@Override
	protected void refreshVisualInTransaction(final VBox visual) {
		super.refreshVisualInTransaction(visual);
		refreshCollapsed(false);
		refreshScrollBar();
		refreshTitle();
	}

	protected void refreshTitle() {
		// Add or remove the Title label
		if (getCompartmentStyleProvider().isShowTitle()) {
			titleLabel.setVisible(true);
			titleLabel.managedProperty().unbind();
			titleLabel.setManaged(true);

			titleLabel.setMaxWidth(Double.MAX_VALUE);
			titleLabel.setAlignment(Pos.BASELINE_CENTER);
			titleLabel.setFont(getStyleProvider().getFont(7));
			FXUtils.setPadding(titleLabel, 1, 1);

			// Update the Title text
			titleLabel.setText(getTitle());
		} else {
			titleLabel.setVisible(false);
			titleLabel.setManaged(false);

			return;
		}
	}

	/**
	 * Returns the title of this compartment
	 *
	 * Subclasses should override
	 *
	 * @return
	 */
	// FIXME This should be configurable (Using parsers, ...?)
	protected String getTitle() {
		return "compartment"; // Default title
	}

	// The primary part is responsible for the general layout. Especially, if the primary part specifies that the node should
	// extend automatically based on the size of its contents, this scroll bar policy will most likely not be useful
	protected void refreshScrollBar() {
		scrollPane.setHbarPolicy(getStyleProvider().getHorizontalBarPolicy());
		scrollPane.setVbarPolicy(getStyleProvider().getVerticalBarPolicy());
	}

	/**
	 * Refreshes the compartment's collapsed state
	 */
	protected void refreshCollapsed(boolean animate) {
		setCollapsed(getCompartmentStyleProvider().isCollapsed(), animate);
	}

	protected CompartmentStyleService getCompartmentStyleProvider() {
		return getAdapter(AdapterKey.get(CompartmentStyleService.class));
	}

	@Override
	protected void refreshBorder() {
		BorderStroke stroke = null;
		final BorderColors borderColors = getStyleProvider().getBorderColors();
		final BorderStrokeStyles borderStyles = getStyleProvider().getBorderStyles();
		final BorderWidths borderWidths = getStyleProvider().getBorderWidths();

		Border border = null;
		if (borderWidths != null) {
			stroke = new BorderStroke(borderColors.getTop(), borderColors.getRight(), borderColors.getBottom(), borderColors.getLeft(), borderStyles.getTop(), borderStyles.getRight(), borderStyles.getBottom(),
					borderStyles.getLeft(),
					getStyleProvider().getCornerRadii(), borderWidths,
					getStyleProvider().getMargin());
			border = new Border(stroke);
		}
		wrapper.setBorder(border);
	}

	// @Override
	// protected void refreshShape() {
	// final ScrollPane pane = getVisual();
	//
	// // Set the shape
	// final int relativeX = BoundsUtil.getRelativeX(pane);
	// final int relativeY = BoundsUtil.getRelativeY(pane);
	// final int width = BoundsUtil.getWidth(pane);
	// final int height = BoundsUtil.getHeight(pane);
	//
	// // if (ShapeTypeEnum.OVAL.equals(getShapeType())) {
	// // pane.setShape(new Ellipse(relativeX, relativeY, width, height));
	// // } else {
	// // pane.setShape(null);
	// // }
	//
	// // Set the clip to avoid the compartment to be outside of the parent
	// final Shape parentShape = ((Region) getParent().getVisual()).getShape();
	// Shape clip = null;
	//
	// // set the clip in case of Ellipse/Oval
	// if (parentShape instanceof Ellipse) {
	// parentShape.setFill(Color.BLACK);
	// clip = Shape.union(parentShape, parentShape);// Create a copy of parent shape
	// clip.setFill(Color.BLACK);
	// clip.setTranslateX(-relativeX);
	// clip.setTranslateY(-relativeY);
	// }
	// pane.setClip(clip);
	// }

	@Override
	protected String getStyleClass() {
		return "genericListCompartment";
	}

	private Timeline collapseAnimation;

	protected void setCollapsed(final boolean collapsed, boolean animate) {
		if (this.collapsed.get() == collapsed) {
			return;
		}

		this.collapsed.set(collapsed);

		if (animate) {
			// TODO Several graphical artifacts remain (Especially because the ScrollPane has a min size that depends on other compartments in the same parent, due to VBox.setVGrow())
			// TODO improve the animation:
			// - If the scrollbars are initially hidden, they appear during animation, then disappear at the end. Use a scrollbar policy to avoid this?
			// - Animate the width as well (Currently, width is set at the end of the animation when collapsing, and at the beginning when expanding)
			// - Handle the case when we are already animating the compartment (Animation is running) and the collapse state changes at the same time.

			// Animate the compartment (Appearing or disappearing)
			if (collapsed) {// Shrink to size 0, then hide and remove from layout
				final Timeline timeline = getCollapseTimeline();

				if (timeline.getStatus() == Animation.Status.RUNNING) {
					// TODO improve this
					timeline.stop();
				}

				scrollPane.setPrefHeight(scrollPane.prefHeight(scrollPane.getWidth()));
				scrollPane.setMaxHeight(Region.USE_PREF_SIZE);

				KeyFrame[] targetFrame = createCollapseKeyFrames(0);
				timeline.getKeyFrames().setAll(targetFrame);

				timeline.setOnFinished(e -> doSetCollapsed(collapsed));

				timeline.play();
			} else { // Set visible, then expand to standard size
				final Timeline timeline = getCollapseTimeline();

				if (timeline.getStatus() == Animation.Status.RUNNING) {
					// TODO improve this
					timeline.stop();
				}

				scrollPane.getChildrenUnmodifiable().forEach(n -> n.setVisible(true));

				scrollPane.setVisible(true);
				scrollPane.setManaged(true);

				KeyFrame[] targetFrame = createCollapseKeyFrames(scrollPane.prefHeight(scrollPane.getWidth()));
				scrollPane.setPrefHeight(0);
				scrollPane.setMaxHeight(Region.USE_PREF_SIZE);
				timeline.getKeyFrames().setAll(targetFrame);

				timeline.setOnFinished(e -> doSetCollapsed(collapsed));
				timeline.play();
			}
		} else {
			doSetCollapsed(collapsed);
		}
	}

	private void doSetCollapsed(boolean collapsed) {
		if (collapsed) {
			scrollPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
			scrollPane.setMaxHeight(Region.USE_COMPUTED_SIZE);
			VBox.setVgrow(wrapper, Priority.NEVER);

			scrollPane.setVisible(false);
			scrollPane.setManaged(false);
		} else {
			scrollPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
			scrollPane.setMaxHeight(Region.USE_COMPUTED_SIZE);
			VBox.setVgrow(wrapper, Priority.ALWAYS);
			scrollPane.setOpacity(1.0); // In case it has been previously animated with an opacity change

			scrollPane.setVisible(true);
			scrollPane.setManaged(true);
		}

		wrapper.pseudoClassStateChanged(PseudoClass.getPseudoClass("collapsed"), collapsed);
	}

	protected Timeline getCollapseTimeline() {
		if (collapseAnimation == null) {
			collapseAnimation = new Timeline();
			collapseAnimation.setCycleCount(1);
			collapseAnimation.setAutoReverse(false);
		}
		return collapseAnimation;
	}

	protected KeyFrame[] createCollapseKeyFrames(double targetValue) {
		KeyValue collapseTargetValue = new KeyValue(scrollPane.prefHeightProperty(), targetValue, Interpolator.EASE_BOTH);

		KeyValue opacity = new KeyValue(scrollPane.opacityProperty(), targetValue < 0.0001 ? 0 : 1.0, Interpolator.EASE_BOTH); // Compartments do not have their own transparency, so it is always 1.0

		double time = 750;

		return new KeyFrame[] {
				new KeyFrame(Duration.millis(time * 0.8), opacity), // Opacity slightly faster than size to reduce artifacts during animation
				new KeyFrame(Duration.millis(time), collapseTargetValue)
		};
	}

	/**
	 * Adds the child visual.
	 *
	 * @param child
	 *            the child
	 * @param index
	 *            the index
	 * @see org.eclipse.gef4.mvc.parts.AbstractVisualPart#addChildVisual(org.eclipse.gef4.mvc.parts.IVisualPart, int)
	 */
	@Override
	protected void doAddChildVisual(final IVisualPart<? extends Node> child, final int index) {
		final Node childVisual = child.getVisual();
		if (null != childVisual && null != compartment) {
			compartment.getChildren().add(childVisual);
		}
	}

	@Override
	protected void refreshLayout() {
		// Do nothing
	}

	/**
	 * Removes the child visual.
	 *
	 * @param child
	 *            the child
	 * @param index
	 *            the index
	 * @see org.eclipse.gef4.mvc.parts.AbstractVisualPart#removeChildVisual(org.eclipse.gef4.mvc.parts.IVisualPart, int)
	 */
	@Override
	protected void doRemoveChildVisual(final IVisualPart<? extends Node> child, final int index) {
		final Node childVisual = child.getVisual();
		if (childVisual == null || compartment == null) {
			return;
		}
		compartment.getChildren().remove(childVisual);
	}

}
