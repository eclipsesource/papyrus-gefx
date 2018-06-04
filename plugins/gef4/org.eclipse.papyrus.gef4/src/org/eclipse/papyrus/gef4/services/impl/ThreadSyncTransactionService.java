/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (EclipseSource) cletavernier@eclipsesource.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.services.impl;

import javax.inject.Inject;

import org.eclipse.fx.core.ThreadSynchronize;
import org.eclipse.papyrus.gef4.services.TransactionService;


public class ThreadSyncTransactionService implements TransactionService {

	private ThreadSynchronize threadSync;

	@Inject
	public ThreadSyncTransactionService(ThreadSynchronize threadSync) {
		this.threadSync = threadSync;
	}

	@Override
	public void refreshPart(Runnable refreshRunnable) {
		if (this.threadSync.isCurrent()) {
			refreshRunnable.run();
		} else {
			this.threadSync.asyncExec(refreshRunnable);
		}
	}

}
