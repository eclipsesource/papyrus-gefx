package org.eclipse.papyrus.gefx.tests;

import org.junit.Assert;
import org.junit.Test;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * <p>
 * Getting a working JavaFX setup in Maven and/or PDE is not as simple as it should be...
 * This test just makes sure that all required JavaFX dependencies are present; essentially
 * for test-debugging purpose.
 * </p>
 */
public class FXTest {
	
	@Test
	public void testFXOProperties() {
		BooleanProperty p = new SimpleBooleanProperty(this, "testProperty", false);
		Assert.assertEquals(false, p.getValue());
		p.set(true);
		Assert.assertEquals(true, p.getValue());
	}
	
	@Test
	public void testFXControls() {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		Assert.assertNotNull(scene);
	}
	
	@Test
	public void testJavaFX9() {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		Assert.assertNotNull(scene);
		// Ensure that this JavaFX 9 Method exists
		scene.addPostLayoutPulseListener(() -> System.err.println("Post layout"));
	}
	
}
