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

import java.util.List
import org.eclipse.emf.codegen.util.CodeGenUtil
import org.eclipse.gmf.codegen.gmfgen.GenCommonBase

abstract class AbstractEditPartEmitter implements IEmitter {

	def classHead(List<Object> arguments) '''
		
		package «CodeGenUtil.getPackageName((arguments.get(0) as GenCommonBase).editPartQualifiedClassName)»;
		
		public class «(arguments.get(0) as GenCommonBase).editPartClassName» «getExtended(arguments)»{
	'''
	
	abstract def String getExtended(List<Object> arguments);
	
	def classFoot() '''
		}
	'''

}