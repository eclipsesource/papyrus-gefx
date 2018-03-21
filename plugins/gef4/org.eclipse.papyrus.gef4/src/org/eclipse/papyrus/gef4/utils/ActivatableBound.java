package org.eclipse.papyrus.gef4.utils;

import org.eclipse.gef.common.activate.ActivatableSupport;
import org.eclipse.gef.common.activate.IActivatable;
import org.eclipse.gef.common.adapt.IAdaptable;
import org.eclipse.gef.common.adapt.IAdaptable.Bound;

import javafx.beans.property.ReadOnlyBooleanProperty;

public abstract class ActivatableBound<BOUND extends IAdaptable> extends Bound.Impl<BOUND> implements IActivatable {

	private ActivatableSupport acs = new ActivatableSupport(this);

	protected abstract void doActivate();

	protected abstract void doDeactivate();

	@Override
	public void activate() {
		acs.activate(null, this::doActivate);
	}

	@Override
	public ReadOnlyBooleanProperty activeProperty() {
		return acs.activeProperty();
	}

	@Override
	public void deactivate() {
		acs.deactivate(null, this::doDeactivate);
	}

	@Override
	public boolean isActive() {
		return acs.isActive();
	}

}
