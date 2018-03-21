package org.eclipse.papyrus.gef4.module;


import org.eclipse.core.runtime.Assert;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * Base module for Papyrus-GEFx editors
 *
 * @param <MODEL>
 *            The generic type representing the root model of this diagram editor.
 */
@Deprecated // FIXME This class is too generic to be useful. Let the specialized modules define this explicitly
public class DiagramModule<MODEL> implements Module {

	private final MODEL diagramRoot;

	private final Class<MODEL> clazz;

	public DiagramModule(Class<MODEL> clazz, MODEL diagram) {
		Assert.isNotNull(clazz);
		Assert.isNotNull(diagram);
		this.clazz = clazz;
		this.diagramRoot = diagram;
	}

	@Override
	public void configure(Binder binder) {
		binder.bind(clazz).toInstance(this.diagramRoot);
	}

}
