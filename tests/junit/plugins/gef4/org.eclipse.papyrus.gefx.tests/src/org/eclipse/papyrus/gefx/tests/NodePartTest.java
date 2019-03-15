/*****************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  EclipseSource - Initial API and Implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gefx.tests;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.instanceOf;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.papyrus.gef4.layout.Locator;
import org.eclipse.papyrus.gef4.parts.NodeContentPart;
import org.eclipse.papyrus.gef4.services.style.impl.EmptyStyleService;
import org.eclipse.papyrus.gef4.shapes.PackagePath;
import org.eclipse.papyrus.gef4.utils.BorderColors;
import org.eclipse.papyrus.gef4.utils.BorderStrokeStyles;
import org.eclipse.papyrus.gef4.utils.ShapeTypeEnum;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import com.google.common.collect.Multimaps;
import com.google.common.util.concurrent.AtomicDouble;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

/**
 * Unit tests for {@link NodeContentPart}
 * 
 * Note: the tested part is never added to any Viewer/RootPart
 */
public class NodePartTest extends ApplicationTest {

	private static final double DOUBLE_DELTA = 0.01;
	private TestModelElement testElement;
	private NodeContentPart<TestModelElement> nodePart;
	private VBox nodeVisual;
	private Pane parent;

	@Override
	public void start(Stage stage) {
		this.parent = new Pane();
		Scene scene = new Scene(this.parent, 800, 600);
		stage.setScene(scene);
		stage.show();
	}

	@Before
	public void init() {
		this.testElement = new TestModelElement("NodePartTest");
		this.nodePart = new NodeContentPart<TestModelElement>(testElement);
		initPart();
	}

	/**
	 * 
	 * Initialize a part with default/empty implementations for all mandatory
	 * dependencies
	 */
	protected void initPart() {
		this.nodePart.setLocator(part -> Optional.empty());
		this.nodePart.setStyleProvider(part -> new EmptyStyleService());
		this.nodePart.setContentChildrenAdapter(part -> () -> Collections.emptyList());
		this.nodePart.setTransactionService(this::runInUI);
		this.nodePart.setAnchorageService(part -> () -> Multimaps.newSetMultimap(new HashMap<>(), HashSet::new));
	}

	/**
	 * Activate this part, create & refresh its visual
	 */
	protected void activatePart() {
		this.nodePart.activate();
		this.nodeVisual = this.nodePart.getVisual();
		runInUI(() -> {
			this.parent.getChildren().add(nodeVisual);
		});
		Assert.assertEquals(parent, nodeVisual.getParent());
		this.nodePart.refreshVisual();
		Assert.assertNotNull(nodeVisual);
	}

	private void runInUI(Runnable runnable) {
		if (Platform.isFxApplicationThread()) {
			runnable.run();
		} else {
			CompletableFuture<Void> result = new CompletableFuture<>();
//			CompletableFuture<Void> pulse = new CompletableFuture<>();
			AtomicReference<Exception> uiException = new AtomicReference<>();
			Runnable runAndWait = () -> {
				// FIXME Pulse Listener not supported in Java 8
//				Runnable pulseListener = new Runnable() {
//					public void run() {
//						pulse.complete(null);
//						parent.getScene().removePostLayoutPulseListener(this);
//					}
//				};
//				parent.getScene().addPostLayoutPulseListener(pulseListener);
				try {
					runnable.run();
				} catch (Exception ex) {
					uiException.set(ex);
				}
				result.complete(null);
			};
			Platform.runLater(runAndWait);
			try {
				result.get(1, TimeUnit.SECONDS);
				sleep(200); // FIXME Switch to pulse listener when we can build fully on Java 11
				// pulse.get(200, TimeUnit.MILLISECONDS);
				if (uiException.get() != null) {
					throw new RuntimeException(uiException.get());
				}
			} catch (TimeoutException | ExecutionException | InterruptedException ex) {
				if (uiException.get() != null) {
					throw new RuntimeException(uiException.get());
				}
				throw new RuntimeException(ex);
			}
		}
	}

	@Test
	public void testModel() {
		activatePart();
		Assert.assertEquals(testElement, nodePart.getContent());
	}

	@Test
	public void testLocator() {
		double x = 18;
		double y = 45;
		this.nodePart.setLocator(part -> Optional.of(new Locator() {

			@Override
			public void applyLayout(Node node) {
				node.setManaged(false);
				node.setLayoutX(x);
				node.setLayoutY(y);
			}
		}));
		activatePart();

		Assert.assertEquals(x, nodeVisual.getLayoutX(), DOUBLE_DELTA);
		Assert.assertEquals(y, nodeVisual.getLayoutY(), DOUBLE_DELTA);
		Assert.assertFalse(nodeVisual.isManaged());
	}

	@Test
	public void testBackgroundAndBorderStyle() {

		AtomicReference<Color> bgColor = new AtomicReference<>(Color.SKYBLUE);
		AtomicReference<Color> gradientColor = new AtomicReference<>(null);
		AtomicReference<Color> borders = new AtomicReference<>(Color.DARKBLUE);
		AtomicReference<BorderStrokeStyles> borderStyle = new AtomicReference<>(BorderStrokeStyles.DASHED);

		this.nodePart.setStyleProvider(part -> new EmptyStyleService() {

			@Override
			public Color getBackgroundPaint() {
				if (gradientColor.get() == null) {
					return getBackgroundColor1();
				}
				return null;
			}

			@Override
			public Color getBackgroundColor1() {
				return bgColor.get();
			}

			@Override
			public Color getBackgroundColor2() {
				return gradientColor.get();
			}

			@Override
			public BorderColors getBorderColors() {
				return new BorderColors(borders.get());
			}

			@Override
			public BorderStrokeStyles getBorderStyles() {
				return borderStyle.get();
			}
		});
		activatePart();

		//
		// Initial state
		//

		{
			Assert.assertEquals(Color.SKYBLUE, nodeVisual.getBackground().getFills().get(0).getFill());

			BorderStroke borderStroke = nodeVisual.getBorder().getStrokes().get(0);
			Paint[] sideColors = { borderStroke.getLeftStroke(), borderStroke.getTopStroke(),
					borderStroke.getRightStroke(), borderStroke.getBottomStroke() };
			for (Paint side : sideColors) {
				Assert.assertEquals(Color.DARKBLUE, side);
			}

			BorderStrokeStyle[] sideStyles = { borderStroke.getLeftStyle(), borderStroke.getTopStyle(),
					borderStroke.getRightStyle(), borderStroke.getBottomStyle() };
			for (BorderStrokeStyle side : sideStyles) {
				Assert.assertEquals(BorderStrokeStyle.DASHED, side);
			}
		}

		//
		// Refreshed state
		//

		bgColor.set(Color.LIGHTBLUE);
		gradientColor.set(Color.WHITE);
		borders.set(Color.DARKGRAY);
		borderStyle.set(BorderStrokeStyles.DOTTED);

		nodePart.refreshVisual();

		{
			Paint fill = nodeVisual.getBackground().getFills().get(0).getFill();
			Assert.assertThat(fill, IsInstanceOf.instanceOf(LinearGradient.class));
			LinearGradient gradient = (LinearGradient) fill;

			Assert.assertEquals(Color.WHITE, gradient.getStops().get(0).getColor());
			Assert.assertEquals(Color.LIGHTBLUE, gradient.getStops().get(1).getColor());

			BorderStroke borderStroke = nodeVisual.getBorder().getStrokes().get(0);
			Paint[] sideColors = { borderStroke.getLeftStroke(), borderStroke.getTopStroke(),
					borderStroke.getRightStroke(), borderStroke.getBottomStroke() };
			for (Paint side : sideColors) {
				Assert.assertEquals(Color.DARKGRAY, side);
			}

			BorderStrokeStyle[] sideStyles = { borderStroke.getLeftStyle(), borderStroke.getTopStyle(),
					borderStroke.getRightStyle(), borderStroke.getBottomStyle() };
			for (BorderStrokeStyle side : sideStyles) {
				Assert.assertEquals(BorderStrokeStyle.DOTTED, side);
			}
		}
	}

	@Test
	public void testLayout() {

		AtomicDouble x = new AtomicDouble(), y = new AtomicDouble(), width = new AtomicDouble(),
				height = new AtomicDouble();
		x.set(14);
		y.set(53);
		width.set(82);
		height.set(45);
		this.nodePart.setStyleProvider(part -> new EmptyStyleService() {
			@Override
			public double getX() {
				return x.get();
			}

			@Override
			public double getY() {
				return y.get();
			}

			@Override
			public double getWidth() {
				return width.get();
			}

			@Override
			public double getHeight() {
				return height.get();
			}
		});

		activatePart();

		Assert.assertEquals(x.get(), nodeVisual.getLayoutX(), DOUBLE_DELTA);
		Assert.assertEquals(y.get(), nodeVisual.getLayoutY(), DOUBLE_DELTA);
		Assert.assertEquals(width.get(), nodeVisual.getWidth(), DOUBLE_DELTA);
		Assert.assertEquals(height.get(), nodeVisual.getHeight(), DOUBLE_DELTA);

		x.set(42);
		width.set(65);
		height.set(75);

		nodePart.refreshVisual();

		Assert.assertEquals(x.get(), nodeVisual.getLayoutX(), DOUBLE_DELTA);
		Assert.assertEquals(y.get(), nodeVisual.getLayoutY(), DOUBLE_DELTA);
		Assert.assertEquals(width.get(), nodeVisual.getWidth(), DOUBLE_DELTA);
		Assert.assertEquals(height.get(), nodeVisual.getHeight(), DOUBLE_DELTA);
	}

	@Test
	public void testPackageShape() {
		AtomicReference<ShapeTypeEnum> shapeType = new AtomicReference<>();
		AtomicDouble height = new AtomicDouble(60);
		AtomicDouble width = new AtomicDouble(140);

		nodePart.setStyleProvider(part -> new EmptyStyleService() {
			public ShapeTypeEnum getShapeType() {
				return shapeType.get();
			}

			@Override
			public double getWidth() {
				return width.get();
			}

			@Override
			public double getHeight() {
				return height.get();
			}

		});

		shapeType.set(ShapeTypeEnum.PACKAGE);
		activatePart();

		// Initial state
		Assert.assertThat(nodeVisual.getShape(), instanceOf(PackagePath.class));
		Shape initialShape = nodeVisual.getShape();

		Bounds nodeBounds = nodeVisual.getLayoutBounds();
		Bounds shapeBounds = initialShape.getLayoutBounds();
		compareBounds(nodeBounds, shapeBounds, 2); // FIXME Should they be exactly equal, or is the Border outside of
													// layout bounds?

		// No change
		nodePart.refreshVisual();
		Assert.assertEquals(initialShape, nodeVisual.getShape());

		// Size change
		height.set(80);
		width.set(120);

		Assert.assertEquals(initialShape, nodeVisual.getShape()); // Shape is the same; just with different size
		compareBounds(nodeBounds, shapeBounds, 2); // FIXME Should they be exactly equal, or is the Border outside of
													// layout bounds?

		// Unset Package Shape
		shapeType.set(ShapeTypeEnum.NONE);
		nodePart.refreshVisual();

		Assert.assertEquals(null, nodeVisual.getShape());
	}

	private void compareBounds(Bounds expected, Bounds actual, double maxDiff) {
		Assert.assertThat(expected.getMinX(), closeTo(actual.getMinX(), maxDiff));
		Assert.assertThat(expected.getMinY(), closeTo(actual.getMinY(), maxDiff));
		Assert.assertThat(expected.getWidth(), closeTo(actual.getWidth(), maxDiff));
		Assert.assertThat(expected.getHeight(), closeTo(actual.getHeight(), maxDiff));
	}

}
