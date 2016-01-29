/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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
package org.eclipse.papyrus.gef4.tests.parts;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.ui.internal.DiagramUIPlugin;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.example.library.editor.LibraryEditor;
import org.eclipse.papyrus.gef4.parts.DiagramContentPart;
import org.eclipse.papyrus.gef4.parts.NodeContentPart;
import org.eclipse.papyrus.gef4.parts.NotationContentPart;
import org.eclipse.papyrus.gef4.tests.utils.ColorUtils;
import org.eclipse.papyrus.gef4.tests.utils.EditorUtils;
import org.eclipse.papyrus.gef4.utils.SynchronizedLogger;
import org.eclipse.swt.widgets.Display;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;

public class PartsTests {

	@BeforeClass
	public static void initEventBroker() {
		DiagramUIPlugin.getInstance(); // Force activation of DiagramUIPlugin, which registers the Thread-Safe event broker. We will override this

		DiagramEventBroker.registerDiagramEventBrokerFactory(new DiagramEventBroker.DiagramEventBrokerFactory() {
			@Override
			public DiagramEventBroker createDiagramEventBroker(TransactionalEditingDomain editingDomain) {
				return new DiagramEventBroker() {
					// Nothing; but we need this because the constructor is protected
				};
			}
		});
	}

	@Test
	public void testNodeFigure() throws Exception {
		String folder = "platform:/plugin/org.eclipse.papyrus.gef4.tests/models/";
		String[] fileNames = new String[] {
				"Library.library",
				"Library.librarydiagram"
		};

		DiagramContentPart diagramPart = EditorUtils.openDiagram("partTets", folder, fileNames, fileNames[1], LibraryEditor.EDITOR_ID);
		NotationContentPart<? extends View, ? extends Node> firstPerson = (NotationContentPart<?, ?>) diagramPart.getChildrenUnmodifiable().get(0);
		Assert.assertThat(firstPerson, instanceOf(NodeContentPart.class));

		Node visual = firstPerson.getVisual();
		assertThat(visual, instanceOf(VBox.class));

		Color defaultLineColor = ColorUtils.fromRGB((int) NotationPackage.Literals.LINE_STYLE__LINE_COLOR.getDefaultValue()); // Default GMF Notation color

		testBorders((NodeContentPart) firstPerson, defaultLineColor);
		testFill((NodeContentPart) firstPerson, Color.WHITE);
	}

	protected void testFill(NodeContentPart nodePart, Color initialColor) throws Exception {
		Color targetColor = Color.DEEPSKYBLUE;

		VBox vBox = nodePart.getVisual();

		Runnable beforeTest = () -> testFillColor(vBox, initialColor);
		Runnable afterTest = () -> testFillColor(vBox, targetColor);

		EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(nodePart.getView());
		SetCommand setFillColor = new SetCommand(domain, nodePart.getView(), NotationPackage.Literals.FILL_STYLE__FILL_COLOR, ColorUtils.toRGB(targetColor));

		executeAndTest(domain, setFillColor, beforeTest, afterTest);
	}

	protected void testBorders(NodeContentPart nodePart, Color initialColor) throws Exception {
		VBox vBox = nodePart.getVisual();

		EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(nodePart.getView());
		SetCommand setLineColor = new SetCommand(domain, nodePart.getView(), NotationPackage.Literals.LINE_STYLE__LINE_COLOR, 0);

		Runnable beforeTest = () -> testBorderColor(vBox, initialColor);
		Runnable afterTest = () -> testBorderColor(vBox, Color.BLACK);

		executeAndTest(domain, setLineColor, beforeTest, afterTest);
	}

	protected void testFillColor(Region visual, Color expectedColor) {
		Background background = visual.getBackground();
		BackgroundFill fill = background.getFills().get(0);
		Paint paint = fill.getFill();

		if (paint instanceof LinearGradient) { // Might be a gradient with the same color for each stop
			LinearGradient linearGradient = (LinearGradient) paint;
			assertFalse(linearGradient.getStops().isEmpty());
			for (Stop stop : linearGradient.getStops()) {
				assertEquals(expectedColor, stop.getColor());
			}
		} else {
			assertEquals(expectedColor, paint);
		}
	}

	protected void executeAndTest(EditingDomain domain, Command commandToExecute, Runnable beforeTest, Runnable afterTest) throws Exception {
		beforeTest.run();

		flushEvents(); // Wait for the UI to be ready before running timing tests
		SynchronizedLogger.flush();

		SynchronizedLogger.log("Run command");
		long beginExecution = System.nanoTime();
		domain.getCommandStack().execute(commandToExecute);
		long endExecution = System.nanoTime();

		long beginDisplay = System.nanoTime();
		long endDisplay = flushEvents();
		SynchronizedLogger.print();

		long displayDuration = endDisplay - beginDisplay;
		long commandDuration = endExecution - beginExecution;
		System.out.println("Execution: " + (commandDuration / 1000) + "µs");
		System.out.println("Display: " + (displayDuration / 1000) + "µs");

		afterTest.run();
		domain.getCommandStack().undo();
		flushEvents();
		beforeTest.run();
		domain.getCommandStack().redo();
		flushEvents();
		afterTest.run();
	}

	/**
	 * Thread-safe method to flush the events in the UI Queue
	 *
	 * @return
	 * 		The end date of the last runnable
	 */
	private static long flushEvents() {
		if (Display.getCurrent() == Display.getDefault()) { // UI Thread: Flush all events
			// If we're in the UI Thread, flush all events until the timer runnable is executed
			int i = 0;
			while (Display.getCurrent().readAndDispatch()) {
				SynchronizedLogger.log("Flush event");
				i++;
				// Nothing
			}
			SynchronizedLogger.log("End timer (" + i + ")");
			return System.nanoTime();
		} else { // Worker thread: Wait until the timer runnable is executed
			AtomicLong endDisplay = new AtomicLong();
			AtomicBoolean isComplete = new AtomicBoolean(false);

			Display.getDefault().asyncExec(() -> {
				// When this runnable has been executed, the display becomes reactive again
				// The end time is computed in the UI Thread. Since the Worker thread sleeps for arbitrary amounts of time, it wouldn't be reliable
				endDisplay.set(System.nanoTime());
				isComplete.set(true);
				SynchronizedLogger.log("End timer");
			}); // Check time

			while (!isComplete.get()) {
				// If we're in a worker Thread, just wait a little bit longer so that the UI Thread can run the timer
				sleep();
			}

			return endDisplay.get();
		}
	}

	protected void testBorderColor(Region visual, Color expectedColor) {
		Border border = visual.getBorder();
		List<BorderStroke> strokes = border.getStrokes();
		BorderStroke stroke = strokes.get(0);

		testColors(stroke, expectedColor);
	}

	protected void testColors(BorderStroke stroke, Color expectedColor) {
		Assert.assertEquals(expectedColor, stroke.getTopStroke());
		Assert.assertEquals(expectedColor, stroke.getRightStroke());
		Assert.assertEquals(expectedColor, stroke.getBottomStroke());
		Assert.assertEquals(expectedColor, stroke.getLeftStroke());
	}

	private static void sleep() {
		try {
			Thread.sleep(200);
		} catch (Exception ex) {

		}
	}

}




