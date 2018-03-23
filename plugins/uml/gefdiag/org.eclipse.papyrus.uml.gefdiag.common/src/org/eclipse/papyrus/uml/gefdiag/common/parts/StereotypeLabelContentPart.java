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
package org.eclipse.papyrus.uml.gefdiag.common.parts;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.StringValueStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.parts.NotationLabelContentPart;
import org.eclipse.papyrus.gef4.gmf.utils.NotationUtil;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.infra.tools.util.ListHelper;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Stereotype;

import javafx.geometry.Insets;
import javafx.scene.control.Label;

/**
 * This label part represents all applied stereotypes. According to the UML Specification, it is displayed
 * on top of other element's label (name or body or...), and the stereotypes are displayed in a comma-separated list
 *
 * @author Camille Letavernier
 *
 */
public class StereotypeLabelContentPart extends NotationLabelContentPart {

	/** left Stereotype delimiters ('Guillemets francais'). */
	public static final String ST_LEFT = String.valueOf("\u00AB"); //$NON-NLS-1$

	/** Right Stereotype delimiters ('Guillemets francais'). */
	public static final String ST_RIGHT = String.valueOf("\u00BB"); //$NON-NLS-1$

	/**
	 * The separator for UML Qualified Names
	 * ::
	 */
	public static final String QUALIFIED_NAME_SEPARATOR = NamedElement.SEPARATOR;

	public StereotypeLabelContentPart(View view) {
		super(view);
	}

	@Override
	protected void refreshVisualInTransaction(Label visual) {
		super.refreshVisualInTransaction(visual);

		if (getVisibleStereotypes().isEmpty()) {
			visual.setManaged(false);
			visual.setVisible(false);
		} else {
			visual.setManaged(true);
			visual.setVisible(true);
		}

		visual.setPadding(new Insets(0));
	}

	@Override
	protected String getText() {
		List<Stereotype> visibleStereotypes = getVisibleStereotypes();

		List<String> visibleStereotypeLabels = visibleStereotypes.stream().map((sto) -> getStereotypeLabel(sto)).collect(Collectors.toList());

		return String.format("%s%s%s", ST_LEFT, ListHelper.deepToString(visibleStereotypeLabels, ", "), ST_RIGHT);
	}

	protected List<Stereotype> getVisibleStereotypes() {
		EObject semanticElement = getContent().getElement();

		List<? extends View> childViews = NotationUtil.getNotationChildren(getPrimaryContentPart());
		List<String> hiddenStereotypes = childViews
				.stream()
				.filter((view) -> !view.isVisible())
				.filter((view) -> "StereotypeLabel".equals(view.getType()))
				.map((view) -> getQualifiedStereotypeName(view))
				.filter((name) -> name != null)
				.collect(Collectors.toList());

		if (semanticElement instanceof Element) {
			Element umlElement = (Element) semanticElement;
			// If a stereotype is applied and is not explicitly hidden, then it is visible
			return umlElement.getAppliedStereotypes()
					.stream()
					.filter((sto) -> hiddenStereotypes
							.stream()
							.noneMatch((qName) -> qName.equals(sto.getQualifiedName())))
					.collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public BaseContentPart<View, ?> getPrimaryContentPart() {
		return (BaseContentPart<View, ?>) super.getPrimaryContentPart();
	}

	protected String getQualifiedStereotypeName(View stereotypeLabelView) {
		StringValueStyle stereotypeStyle = (StringValueStyle) stereotypeLabelView.getNamedStyle(NotationPackage.Literals.STRING_VALUE_STYLE, "stereotype");
		if (stereotypeStyle == null) {
			return null;
		}
		return stereotypeStyle.getStringValue();
	}

	protected String getStereotypeLabel(Stereotype stereotype) {
		return stereotype.getName();
	}
}


