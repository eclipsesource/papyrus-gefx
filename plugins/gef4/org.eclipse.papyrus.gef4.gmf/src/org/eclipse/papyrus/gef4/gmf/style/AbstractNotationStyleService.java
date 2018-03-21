package org.eclipse.papyrus.gef4.gmf.style;

import javax.inject.Inject;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.utils.ActivatableBound;

public class AbstractNotationStyleService extends ActivatableBound<BaseContentPart<View, ?>> {

	private TransactionalEditingDomain editingDomain;
	private DiagramEventBroker eventBroker;
	private NotificationListener notificationListener;

	@Inject
	protected void initializeEditingDomain(TransactionalEditingDomain editingDomain, DiagramEventBroker eventBroker) {
		this.eventBroker = eventBroker;
		this.editingDomain = editingDomain;
	}

	private View view;

	protected View getView() {
		if (this.view == null) {
			this.view = getAdaptable().getContent();
		}
		return this.view;
	}

	@Override
	protected void doActivate() {
		this.notificationListener = createNotificationListener();
	}

	@Override
	protected void doDeactivate() {
		// Nothing yet
	}

	protected DiagramEventBroker getEventBroker() {
		return this.eventBroker;
	}

	protected TransactionalEditingDomain getDomain() {
		return this.editingDomain;
	}

	protected NotificationListener getNotificationListener() {
		return this.notificationListener;
	}

	/**
	 * The NotificationListener used to listen to changes on View/Model
	 *
	 * It should call {@link #updateContentChildren()} and {@link #refreshVisual()}
	 * as necessary
	 *
	 * @return
	 */
	protected NotificationListener createNotificationListener() {

		// FIXME: This listener is difficult to extend in subclasses
		return msg -> {
			if (!isActive() || !getAdaptable().isActive()) {
				return;
			}

			if (!(msg.isTouch())) {
				if (childrenChanged(msg)) {
					// FIXME move to Content Adapter
					getAdaptable().updateContentChildren();
				}
				// FIXME: Do not refresh immediately. If we use a ThreadSafeDiagramEventBroker,
				// this will happen synchronously
				// (The command execution will not complete until the view is fully refreshed)
				// Rather mark the view as "needing refresh" and schedule a refresh runnable
				getAdaptable().refreshVisual();
			}
		};
	}

	/**
	 * Returns true if the notification affects the children of this element
	 *
	 * @param msg
	 * @return
	 */
	protected boolean childrenChanged(final Notification msg) {
		// FIXME move to content adapter
		if (msg.getNotifier() != getView()) {
			return false;
		}
		return msg.getFeature() == NotationPackage.Literals.VIEW__PERSISTED_CHILDREN
				|| msg.getFeature() == NotationPackage.Literals.VIEW__TRANSIENT_CHILDREN;
	}
}
