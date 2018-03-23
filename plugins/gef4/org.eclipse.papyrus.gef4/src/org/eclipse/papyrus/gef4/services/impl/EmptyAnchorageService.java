package org.eclipse.papyrus.gef4.services.impl;

import org.eclipse.papyrus.gef4.services.AnchorageService;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

public final class EmptyAnchorageService implements AnchorageService {

	@Override
	public SetMultimap<? extends Object, String> getModelAnchorages() {
		return HashMultimap.create();
	}

}
