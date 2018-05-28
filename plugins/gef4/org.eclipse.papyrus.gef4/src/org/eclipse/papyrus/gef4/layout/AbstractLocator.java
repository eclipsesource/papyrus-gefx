package org.eclipse.papyrus.gef4.layout;

import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.utils.ActivatableBound;

/**
 * Abstract implementation for Locators applied on a {@link BaseContentPart}
 *
 * @param <T>
 */
public abstract class AbstractLocator<T extends BaseContentPart<?, ?>> extends ActivatableBound<T> implements Locator {

	@Override
	protected void doActivate() {
		getAdaptable().setLocator(this);
	}

	@Override
	protected void doDeactivate() {
		// Empty
	}

}
