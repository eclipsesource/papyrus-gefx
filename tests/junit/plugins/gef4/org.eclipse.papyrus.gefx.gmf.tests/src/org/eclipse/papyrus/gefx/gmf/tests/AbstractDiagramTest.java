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
package org.eclipse.papyrus.gefx.gmf.tests;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.fx.core.ThreadSynchronize;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.gef4.viewer.GEFxDiagram;
import org.junit.After;
import org.junit.Before;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.testfx.framework.junit.ApplicationTest;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * <p>
 * Base test class for specific diagrams based on GMF.
 * </p>  
 */
public abstract class AbstractDiagramTest extends ApplicationTest {
	
	protected ThreadSynchronize threadSync;

	protected GEFxDiagram<Diagram> diagramViewer;

	public AbstractDiagramTest() {
		BundleContext bundleContext = FrameworkUtil.getBundle(AbstractDiagramTest.class).getBundleContext();
		IEclipseContext parentContext = EclipseContextFactory.createServiceContext(bundleContext);
		this.threadSync = parentContext.get(ThreadSynchronize.class);
		assertNotNull(threadSync);
		
		// XXX Find a way to make sure we always get the correct implementation (We don't want the one from interopt)
		assertThat(threadSync.getClass().getSimpleName(), containsString("FXThreadSynchronizeImpl"));
	}

	@Override
	public void start(Stage stage) throws Exception {
		Injector injector = Guice.createInjector(getModule());
		diagramViewer = injector.getInstance(Key.get(new TypeLiteral<GEFxDiagram<Diagram>>(){}));
		Pane root = new Pane(diagramViewer.getCanvas());
		stage.setScene(new Scene(root, 800, 600));
		stage.show();
		diagramViewer.initialize();
	}
	
	@Before
	public void waitForInitialization() throws TimeoutException, InterruptedException, ExecutionException {
		diagramViewer.initialize().get(5, TimeUnit.SECONDS);
	}
	
	@After
	public void dispose() {
		threadSync.syncExec(() -> diagramViewer.dispose());
	}
	
	protected abstract Module getModule() throws Exception;
	
	protected Module getThreadSyncModule() {
		return new AbstractModule() {
			
			@Override
			protected void configure() {
				binder().bind(ThreadSynchronize.class).toInstance(threadSync);
			}
		};
	}
	
}
