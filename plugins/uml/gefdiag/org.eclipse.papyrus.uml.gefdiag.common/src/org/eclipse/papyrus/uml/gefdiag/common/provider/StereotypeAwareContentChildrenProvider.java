/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.gefdiag.common.provider;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.provider.NotationContentChildrenProvider;
import org.eclipse.papyrus.infra.emf.gmf.util.GMFUnsafe;

/**
 * Filters the child views of this element, removing the Stereotype Views that shouldn't be associated to ContentParts
 *
 * @author Camille Letavernier
 *
 */
public class StereotypeAwareContentChildrenProvider extends NotationContentChildrenProvider {

	private static Collection<String> excludedTypes = new HashSet<>();
	{
		excludedTypes.add("StereotypeLabel");
		// excludedTypes.add("...");
	}

	@Override
	public List<? extends View> getContentChildren(View parent) {
		List<? extends View> allVisibleChildren = super.getContentChildren(parent);

		List<View> filteredChildren = allVisibleChildren.stream()
				.filter(
						(view) -> !excludedTypes.contains(view.getType()))
				.collect(Collectors.toList());

		if (isPrimaryView(parent)) {
			addDynamicStereotypeLabelView(parent, filteredChildren);
		}

		return filteredChildren;
	}

	protected void addDynamicStereotypeLabelView(View parent, List<View> children) {
		if (true) {
			// This method doesn't work for all nodes. We need more flexibility in the
			// Injector/Module before enabling this (using a different provider for e.g. Labels)
			return;
		}

		View stereotypeLabel = children
				.stream()
				.filter((view) -> "DynamicStereotypeLabel".equals(view.getType()))
				.findAny()
				.orElse(null);

		if (stereotypeLabel == null) {
			stereotypeLabel = NotationFactory.eINSTANCE.createDecorationNode();
			stereotypeLabel.setType("DynamicStereotypeLabel");

			EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(parent);
			if (domain instanceof TransactionalEditingDomain) {
				try {
					final View labelToAdd = stereotypeLabel;
					GMFUnsafe.write((TransactionalEditingDomain) domain, () -> parent.getTransientChildren().add(0, labelToAdd));
				} catch (InterruptedException | RollbackException e) {
					e.printStackTrace(); // TODO improve log
				}
			}
		}

		// Ensure the Stereotype label is always the first element
		// FIXME: Add a separate strategy for sorting children
		children.add(0, stereotypeLabel);
	}

	protected boolean isPrimaryView(View view) {
		View parentView = getParentView(view);
		if (view instanceof Diagram) {
			return false; // Might be true if we support Frames, but currently Diagrams are treated separately
		}
		if (parentView == null || parentView instanceof Diagram) {
			return true;
		}

		EObject semanticElement = view.getElement();
		return semanticElement != null && getElement(parentView) != semanticElement;
	}

	protected View getParentView(View view) {
		EObject parentElement = view.eContainer();
		if (parentElement instanceof View) {
			return (View) parentElement;
		}
		return null;

	}

	protected EObject getElement(View view) {
		if (view.getElement() != null) {
			return view.getElement();
		}

		View parentView = getParentView(view);
		if (parentView != null) {
			return getElement(parentView);
		}

		return null;
	}


}

