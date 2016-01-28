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

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef4.mvc.fx.policies.IFXOnClickPolicy;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gef4.mvc.policies.AbstractInteractionPolicy;
import org.eclipse.gmf.runtime.diagram.core.commands.SetPropertyCommand;
import org.eclipse.gmf.runtime.diagram.ui.internal.properties.Properties;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.DrawerStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.gef4.handle.CollapseHandlePart;
import org.eclipse.papyrus.gef4.utils.CompartmentUtils;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * The Class CollapseOnClickPolicy.
 */
// FIXME use the notation model rather than the ContentPart
public class CollapseOnClickPolicy extends AbstractInteractionPolicy<Node> implements IFXOnClickPolicy {

	/**
	 * @see org.eclipse.gef4.mvc.fx.policies.AbstractFXOnClickPolicy#click(javafx.scene.input.MouseEvent)
	 *
	 * @param event
	 */
	@Override
	public void click(final MouseEvent event) {
		if (getHost() instanceof CollapseHandlePart) {
			final IVisualPart<Node, ? extends Node> compartment = CompartmentUtils.getCollapsablePart(getHost().getAnchoragesUnmodifiable().keys().iterator().next());

			if (null != compartment) {
				DrawerStyle drawerStyle = getDrawerStyle(compartment);
				if (drawerStyle == null) {
					return;
				}

				final boolean valueToSet = !drawerStyle.isCollapsed();
				TransactionalEditingDomain editingDomain = (TransactionalEditingDomain) EMFHelper.resolveEditingDomain(compartment);

				SetPropertyCommand setCommand = new SetPropertyCommand(editingDomain, "Collapse compartment", new EObjectAdapter(drawerStyle), Properties.ID_COLLAPSED, valueToSet);

				editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(setCommand));
				compartment.refreshVisual(); // FIXME Shouldn't be required. The compartment should listen on its DrawerStyle
				getHost().refreshVisual(); // To refresh the +/- button. Install listeners as well?
			}
		}
	}

	protected DrawerStyle getDrawerStyle(final IVisualPart<?, ?> compartment) {
		final View hostView = NotationHelper.findView(compartment);
		if (null != hostView) {
			return (DrawerStyle) hostView.getStyle(NotationPackage.eINSTANCE.getDrawerStyle());
		}
		return null;

	}
}
