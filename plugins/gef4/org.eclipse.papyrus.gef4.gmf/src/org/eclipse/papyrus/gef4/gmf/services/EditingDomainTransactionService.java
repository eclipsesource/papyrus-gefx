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
package org.eclipse.papyrus.gef4.gmf.services;

import javax.inject.Inject;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.fx.core.ThreadSynchronize;
import org.eclipse.papyrus.gef4.services.TransactionService;
import org.eclipse.papyrus.gef4.services.impl.ThreadSyncTransactionService;

public class EditingDomainTransactionService extends ThreadSyncTransactionService implements TransactionService {

	private TransactionalEditingDomain domain;

	@Inject
	public EditingDomainTransactionService(TransactionalEditingDomain domain, ThreadSynchronize threadSync) {
		super(threadSync);
		this.domain = domain;
	}

	@Override
	public void refreshPart(Runnable refreshRunnable) {
		super.refreshPart(() -> {
			try {
				domain.runExclusive(refreshRunnable);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

}
