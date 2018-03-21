package org.eclipse.papyrus.gef4.services.impl;

import org.eclipse.gef.common.adapt.AdaptableSupport;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.parts.ConnectionContentPart;
import org.eclipse.papyrus.gef4.services.AnchorageService;
import org.eclipse.papyrus.gef4.utils.ActivatableBoundAdaptable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

public class EmptyAnchorageService extends ActivatableBoundAdaptable<AnchorageService, ConnectionContentPart<View>>
		implements AnchorageService {

	@Override
	public SetMultimap<? extends Object, String> getModelAnchorages() {
		return HashMultimap.create();
	}

	@Override
	protected AdaptableSupport<AnchorageService> createAdaptableSupport() {
		return new AdaptableSupport<>(this);
	}

	@Override
	protected void doActivate() {
		// Nothing
	}

	@Override
	protected void doDeactivate() {
		// Nothing
	}


}
