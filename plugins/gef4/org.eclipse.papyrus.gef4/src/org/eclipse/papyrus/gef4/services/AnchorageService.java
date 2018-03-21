package org.eclipse.papyrus.gef4.services;

import org.eclipse.gef.common.activate.IActivatable;
import org.eclipse.gef.common.adapt.IAdaptable;

import com.google.common.collect.SetMultimap;

public interface AnchorageService extends IAdaptable, IActivatable {
	SetMultimap<? extends Object, String> getModelAnchorages();
}
