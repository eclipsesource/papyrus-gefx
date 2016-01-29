/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST.
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
import java.util.List
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.gmf.common.UnexpectedBehaviourException

class AffixedNodeEditPartEmitter extends NodeEditPartEmitter {

			override generate(IProgressMonitor monitor, List<Object> arguments) throws InterruptedException, InvocationTargetException, UnexpectedBehaviourException '''
	«classHead(arguments)»
	
	public «(arguments.get(0) as GenCommonBase).editPartClassName»(org.eclipse.gmf.runtime.notation.Shape view) {
		super(view);
		setLocator(new org.eclipse.papyrus.gef4.layout.BorderItemLocator(this));
	}
	
	«classFoot()»
	'''
}