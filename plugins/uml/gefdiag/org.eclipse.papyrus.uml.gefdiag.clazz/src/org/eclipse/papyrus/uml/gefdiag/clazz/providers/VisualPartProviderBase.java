/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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
package org.eclipse.papyrus.uml.gefdiag.clazz.providers;

import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gmf.runtime.notation.BasicCompartment;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.util.NotationSwitch;
import org.eclipse.papyrus.gef4.parts.DiagramContentPart;
import org.eclipse.papyrus.gef4.parts.ListCompartmentContentPart;
import org.eclipse.papyrus.gef4.parts.NodeContentPart;
import org.eclipse.papyrus.gef4.parts.XYCompartmentContentPart;
import org.eclipse.papyrus.gef4.provider.AbstractVisualPartProvider;
import org.eclipse.papyrus.uml.gefdiag.clazz.editor.ClassDiagramEditor;
import org.eclipse.papyrus.uml.gefdiag.clazz.parts.AssociationContentPart;
import org.eclipse.papyrus.uml.gefdiag.clazz.parts.ClassContentPart;
import org.eclipse.papyrus.uml.gefdiag.clazz.parts.InterfaceContentPart;
import org.eclipse.papyrus.uml.gefdiag.clazz.parts.PackageContentPart;
import org.eclipse.papyrus.uml.gefdiag.clazz.parts.PackageLabelContentPart;
import org.eclipse.papyrus.uml.gefdiag.clazz.parts.PropertyLabelContentPart;
import org.eclipse.papyrus.uml.gefdiag.common.parts.NamedElementLabelContentPart;
import org.eclipse.papyrus.uml.gefdiag.common.parts.NamedElementListItemContentPart;

import javafx.scene.Node;

public class VisualPartProviderBase extends AbstractVisualPartProvider {

	@Override
	public IContentPart<Node, ? extends Node> createContentPart(View view) {
		switch (view.getType()) {
		case ClassDiagramEditor.DIAGRAM_TYPE:
			return new DiagramContentPart(getDiagram(view));
		case "2008": // Class
		case "3010": // Class
			return new ClassContentPart(getShape(view));
		case "5029": // Class#Name
			return new NamedElementLabelContentPart(getDecorationNode(view));
		case "3014": // Inner Class
		case "3013": // Inner Operation
			return new NamedElementListItemContentPart(getShape(view));
		case "2007": // Package
			return new PackageContentPart(getShape(view));
		case "5026": // Package#name
			return new PackageLabelContentPart(getDecorationNode(view));
		case "7017": // Class#Attributes
		case "7018": // Class#Operations
		case "7019": // Class#InnerClass
			return new ListCompartmentContentPart<BasicCompartment>(getCompartment(view));
		case "7016": // Package#Contents
			return new XYCompartmentContentPart<BasicCompartment>(getCompartment(view));
		case "3012": // Inner Property
			return new PropertyLabelContentPart(getShape(view));
		case "4001": // Association
			return new AssociationContentPart(getConnector(view));
		case "2004": // Interface
		case "3023": // Interface
			return new InterfaceContentPart(getShape(view));
		default:
			// System.out.println("View not supported: " + view);
			return (IContentPart<Node, ? extends Node>) new NotationSwitch() {
				@Override
				public Object caseDecorationNode(DecorationNode object) {
					return new NamedElementLabelContentPart(object);
				}

				@Override
				public Object caseShape(Shape object) {
					return new NodeContentPart(object);
				}

				@Override
				public Object caseBasicCompartment(BasicCompartment object) {
					return new ListCompartmentContentPart<DecorationNode>(object);
				}
			}.doSwitch(view);
		// return new EmptyContentPart(view);
		}
	}

	private Connector getConnector(View view) {
		if (view instanceof Connector) {
			return (Connector) view;
		}
		return null;
	}

	private static final Diagram getDiagram(View view) {
		if (view instanceof Diagram) {
			return (Diagram) view;
		}
		return null;
	}

	private static final DecorationNode getDecorationNode(View view) {
		if (view instanceof DecorationNode) {
			return (DecorationNode) view;
		}
		return null;
	}

	private static final Shape getShape(View view) {
		if (view instanceof Shape) {
			return (Shape) view;
		}
		return null;
	}

	private static final BasicCompartment getCompartment(View view) {
		if (view instanceof BasicCompartment) {
			return (BasicCompartment) view;
		}
		return null;
	}

}
