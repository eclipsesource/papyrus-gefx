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
