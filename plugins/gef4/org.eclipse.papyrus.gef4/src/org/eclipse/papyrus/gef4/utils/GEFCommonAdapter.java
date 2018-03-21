package org.eclipse.papyrus.gef4.utils;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;

/**
 * Wrapper interface to expose a GEF Common {@link org.eclipse.gef.common.adapt.IAdaptable} as
 * an eclipse {@link IAdaptable}
 */
public class GEFCommonAdapter implements IAdaptable {

	private final org.eclipse.gef.common.adapt.IAdaptable adaptable;

	public GEFCommonAdapter(org.eclipse.gef.common.adapt.IAdaptable gefCommonAdaptable) {
		Assert.isNotNull(gefCommonAdaptable);
		this.adaptable = gefCommonAdaptable;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return this.adaptable.getAdapter(adapter);
	}

}
