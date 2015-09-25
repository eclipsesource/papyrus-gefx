/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.gef4.policies;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.gef4.mvc.fx.policies.AbstractFXOnClickPolicy;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.DrawerStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.gef4.parts.CollapseHandlePart;
import org.eclipse.papyrus.gef4.parts.CompartmentContentPart;
import org.eclipse.papyrus.gef4.utils.VisualPartUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

import javafx.scene.input.MouseEvent;

/**
 * The Class CollapseOnClickPolicy.
 */
public class CollapseOnClickPolicy extends AbstractFXOnClickPolicy {

	/**
	 * @see org.eclipse.gef4.mvc.fx.policies.AbstractFXOnClickPolicy#click(javafx.scene.input.MouseEvent)
	 *
	 * @param event
	 */
	@Override
	public void click(final MouseEvent event) {
		if (getHost() instanceof CollapseHandlePart) {
			@SuppressWarnings("unchecked")
			final CompartmentContentPart<?, ?> compartment = VisualPartUtil.findParentVisualPartInstance(getHost().getAnchorages().keys()
					.iterator().next(), CompartmentContentPart.class);
			if (null != compartment && compartment.isCanCollapse()) {
				final boolean valueToSet = !compartment.isCollapsed();

				// Set the notation
				final DrawerStyle drawerStyle = getCreateDrawerStyle(compartment);

				final SetRequest setRequest = new SetRequest(drawerStyle, NotationPackage.Literals.DRAWER_STYLE__COLLAPSED, valueToSet);

				final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(drawerStyle);
				if (provider != null) {
					final CompositeCommand collapseCommand = new CompositeCommand("Collapse element");
					collapseCommand.add(provider.getEditCommand(setRequest));

					AdapterFactoryEditingDomain.getEditingDomainFor(drawerStyle).getCommandStack().execute(new GMFtoEMFCommandWrapper(collapseCommand));
				}
			}
		}
	}


	/**
	 * Gets the layout constraint.
	 * 
	 * @param compartment
	 *
	 * @return the layout constraint
	 */
	protected DrawerStyle getCreateDrawerStyle(final CompartmentContentPart<?, ?> compartment) {

		final View hostView = NotationHelper.findView(compartment);
		if (null != hostView) {
			return (DrawerStyle) hostView.getStyle(NotationPackage.eINSTANCE.getDrawerStyle());
		}
		return null;

	}
}
