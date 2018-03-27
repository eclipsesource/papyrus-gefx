/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.gef4.codegen.emitters

import java.lang.reflect.InvocationTargetException
import java.util.HashMap
import java.util.List
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.gmf.codegen.gmfgen.GenEditorGenerator
import org.eclipse.gmf.codegen.gmfgen.GenLink
import org.eclipse.gmf.codegen.gmfgen.GenNode
import org.eclipse.gmf.common.UnexpectedBehaviourException
import org.eclipse.gmf.codegen.gmfgen.GenLabel
import org.eclipse.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.gmf.codegen.gmfgen.GenCompartment

class VisualPartProviderEmitter implements IEmitter {

	override String generate(IProgressMonitor monitor,
		List<Object> arguments) throws InterruptedException, InvocationTargetException, UnexpectedBehaviourException '''
		package «(arguments.get(0) as GenEditorGenerator).packageNamePrefix».providers;
		
		public class ContentPartProvider extends org.eclipse.papyrus.uml.gefdiag.common.provider.AbstractUMLContentPartProvider{
			
			@Override
			public org.eclipse.gef.mvc.fx.parts.IContentPart<?> createContentPart(org.eclipse.gmf.runtime.notation.View view) {
				switch (view.getType()) {

				«FOR String element : (arguments.get(1) as HashMap<String, GenCommonBase>).keySet»
					«var value=(arguments.get(1) as HashMap<String, GenCommonBase>).get(element)»
					«IF value instanceof GenNode»
						case "«element»":
							return new «value.editPartQualifiedClassName»((org.eclipse.gmf.runtime.notation.Shape)view);
					«ELSEIF value instanceof GenLink»
						case "«element»":
							return new «value.editPartQualifiedClassName»((org.eclipse.gmf.runtime.notation.Connector)view);
					«ELSEIF value instanceof GenLabel»
						case "«element»":
							return new «value.editPartQualifiedClassName»((org.eclipse.gmf.runtime.notation.View)view);
					«ELSEIF value instanceof GenDiagram»
						case "«element»":
							return new «value.editPartQualifiedClassName»((org.eclipse.gmf.runtime.notation.Diagram)view);
					«ELSEIF value instanceof GenCompartment»
						 	case "«element»":
						 		return new «value.editPartQualifiedClassName»((org.eclipse.gmf.runtime.notation.DecorationNode)view);
					«ENDIF»
				«ENDFOR»
				
				default:
					// System.out.println("View not supported: " + view);
					return (org.eclipse.gef.mvc.fx.parts.IContentPart<?>) new org.eclipse.gmf.runtime.notation.util.NotationSwitch() {
						@Override
						public Object caseDecorationNode(org.eclipse.gmf.runtime.notation.DecorationNode object) {
							return new org.eclipse.papyrus.gef4.gmf.parts.NotationLabelContentPart(object);
						}
		
						@Override
						public Object caseShape(org.eclipse.gmf.runtime.notation.Shape object) {
							return new org.eclipse.papyrus.gef4.gmf.parts.ShapeContentPart(object);
						}
		
						@Override
						public Object caseBasicCompartment(org.eclipse.gmf.runtime.notation.BasicCompartment object) {
							return new org.eclipse.papyrus.gef4.gmf.parts.NotationListCompartmentContentPart(object);
						}
					}.doSwitch(view);
				// return new EmptyContentPart(view);
				}
			}
			
		}
	'''

}