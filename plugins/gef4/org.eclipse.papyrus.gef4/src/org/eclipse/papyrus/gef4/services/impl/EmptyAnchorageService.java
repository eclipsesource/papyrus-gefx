package org.eclipse.papyrus.gef4.services.impl;

import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.services.AnchorageService;
import org.eclipse.papyrus.gef4.utils.ActivatableBound;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

public class EmptyAnchorageService extends ActivatableBound<IVisualPart<?>>
		implements AnchorageService {

	@Override
	public SetMultimap<? extends Object, String> getModelAnchorages() {
		return HashMultimap.create();
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
