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
