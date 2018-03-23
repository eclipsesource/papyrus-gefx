package org.eclipse.papyrus.gef4.services;

import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.google.common.collect.SetMultimap;

/**
 * A service to retrieve the anchorage points for a bound {@link IVisualPart}
 */
public interface AnchorageService {
	SetMultimap<? extends Object, String> getModelAnchorages();
}
