package org.eclipse.papyrus.gef4.services;

/**
 * Simple wrapper interface to support transactions
 */
public interface TransactionService {

	void refreshPart(Runnable refreshRunnable);

}
