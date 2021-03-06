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
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Layout and visualization API and Implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.parts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gef.mvc.fx.parts.AbstractContentPart;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.adapt.AdapterHelper;
import org.eclipse.papyrus.gef4.layout.Locator;
import org.eclipse.papyrus.gef4.services.AnchorageService;
import org.eclipse.papyrus.gef4.services.ContentChildrenProvider;
import org.eclipse.papyrus.gef4.services.HelperProvider;
import org.eclipse.papyrus.gef4.services.TransactionService;
import org.eclipse.papyrus.gef4.services.style.StyleService;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.reflect.TypeToken;

import javafx.scene.Node;

/**
 * Parent class for all GEFx ContentParts.
 *
 * @author Camille Letavernier
 *
 * @param <MODEL>
 *            The Model type represented by this ContentPart
 * @param <N>
 *            The FX {@link Node} used to display this ContentPart
 */
public abstract class BaseContentPart<MODEL, N extends Node> extends AbstractContentPart<N> implements IAdaptable {

	private Locator locator;

	private StyleService styleService;

	private ContentChildrenProvider<MODEL> contentChildrenProvider;

	private boolean visualCreated = false;

	// The list of contentChildren, as known by GEFx. It may be different from View#getChildren during updates, as items will be added/removed 1 by 1
	protected final List<MODEL> contentChildren = new ArrayList<>();

	// The list of contentAnchorages, as known by GEFx. It may be different from the model anchorages during updates, as items will be added/removed 1 by 1
	protected SetMultimap<Object, String> contentAnchorages;

	private TransactionService transactionService;

	private AnchorageService anchorageService;

	@Inject
	private AdapterHelper adapterHelper;

	public BaseContentPart(MODEL content) {
		Assert.isNotNull(content);
		setContent(content);
	}

	@Inject
	public void setStyleProvider(HelperProvider<StyleService> styleServiceProvider) {
		this.styleService = styleServiceProvider.get(this);
	}

	protected StyleService getStyleProvider() {
		return styleService;
	}

	/**
	 * Delegates to {@link #doCreateVisual()} for creating the actual FX {@link Node}, and registers a style class
	 *
	 * @see {@link #getStyleClass()}
	 */
	@Override
	public final N getVisual() {
		final N visual = super.getVisual();
		if (!visualCreated && visual != null) {
			Collection<String> classes = getStyleClasses();
			if (classes != null) {
				visual.getStyleClass().addAll(classes);
			}
			visualCreated = true;
		}
		return visual;
	}

	static int childIterations;
	static long childrenTime;

	public void updateContentChildren() {
		// TODO Update order?

		// FIXME: This operation will have a high complexity:
		// - n = number of children, c = number of changes (Addition/Removal)
		// - We iterate on actual model children & ContentPart contentChildren lists (n²)
		// - For each added/removed item, the super implementation will iterate several times on the list again to check the validity of the operation (3n * c)
		// - The super implementation will do a setAll for each added/removed item (c * n)
		// However, the number of changes should, in general, be limited (The worst case being e.g. the creation of a model element with "a lot" of compartments, with n = c ~= 10)
		// It may however be noticeable when opening a diagram as this list will be updated for all elements in the model
		List<? extends MODEL> modelChildren = getContentChildren();
		for (MODEL modelChild : modelChildren) {
			if (!contentChildren.contains(modelChild)) {
				addContentChild(modelChild, contentChildren.size());
			}
		}
		for (MODEL contentChild : new ArrayList<>(contentChildren)) {
			if (!modelChildren.contains(contentChild)) {
				removeContentChild(contentChild);
			}
		}
	}

	protected void installDefaultPolicies() {
		// It is recommended to use dependency injection instead of hard-coding
	}

	/**
	 * Returns the Primary ContentPart containing this part
	 *
	 * The Primary content part is the top-most part representing a given semantic element
	 *
	 * For example, Compartments are usually not primary content parts, because
	 * they are owned by a Node content part representing the same semantic element
	 *
	 * This method may return the current part (If it is a Primary part)
	 *
	 * @return
	 */
	public BaseContentPart<?, ? extends Node> getPrimaryContentPart() {
		if (this instanceof IPrimaryContentPart) {
			return this;
		}

		final BaseContentPart<?, ?> parentPart = getParentContentPart();
		if (parentPart != null) {
			return parentPart.getPrimaryContentPart();
		}

		return null;
	}

	protected BaseContentPart<?, ? extends Node> getParentContentPart() {
		final IVisualPart<? extends Node> parent = getParent();
		if (parent instanceof BaseContentPart) {
			return (BaseContentPart<?, ?>) parent;
		}
		return null;
	}

	protected IContentPart<? extends Node> getContentPart(final MODEL forModel) {
		if (forModel == null) {
			return null;
		}
		return getViewer().getContentPartMap().get(forModel);
	}

	/**
	 * Do not invoke this method. Subclasses should invoke (and implement) {@link #refreshVisualInTransaction(Node)}
	 *
	 * Clients of the public API should invoke {@link #refreshVisual()}
	 */
	@Override
	protected final void doRefreshVisual(final N visual) {
		this.transactionService.refreshPart(() -> refreshVisualInTransaction(visual));
	}

	protected void refreshVisualInTransaction(final N visual) {
		refreshPick();
	}

	protected void refreshPick() {
		// We need to set pickOnBounds = false, because border items expand the bounds
		// of their parent (Without affecting their geometry). We e.g. don't want to pick
		// a Node when clicking in the empty area between it and its Floating Label
		getVisual().setPickOnBounds(false);
	}

	@Override
	protected void doActivate() {
		installDefaultPolicies();

		this.contentChildren.addAll(getContentChildren());
		doGetContentAnchorages().putAll(getAnchorageService().getModelAnchorages());

		refreshContentChildren();
		refreshContentAnchorages();

		super.doActivate();
		updateAnchorages();

		// FIXME: AbstractVisualPart refreshes the child before activating it
		// Since we rely on adapters to properly refresh the visual, the content is incorrect during refresh
		// Force a refresh after activation, to be sure.
		// A better solution would be to either make sure the AbstractVisualPart#addChild refreshes the visual *after* activating the adapters,
		// or to avoid relying on adapters for mandatory elements with a strong relation to this content part (Text, Style, ...)
		refreshVisual();
	}

	protected List<? extends MODEL> getContentChildren() {
		return getContentChildrenAdapter().getContentChildren();
	}

	@Override
	@SuppressWarnings("unchecked")
	public MODEL getContent() {
		return (MODEL) super.getContent();
	}

	@Override
	public <T> T getAdapter(Class<T> classKey) {
		return adapterHelper.getAdapter(this, classKey);
	}

	@Override
	public <T> T getAdapter(TypeToken<T> key) {
		return adapterHelper.getAdapter(this, key);
	}

	protected abstract Collection<String> getStyleClasses();

	@Override
	protected List<? extends Object> doGetContentChildren() {
		return contentChildren;
	}

	@Override
	protected void doAddContentChild(Object contentChild, int index) {
		contentChildren.add(index, (MODEL) contentChild);
	}

	@Override
	protected void doRemoveContentChild(Object contentChild) {
		contentChildren.remove(contentChild);
	}

	@Override
	protected void doReorderContentChild(Object contentChild, int newIndex) {
		contentChildren.remove(contentChild);
		contentChildren.add(newIndex, (MODEL) contentChild);
	}

	@SuppressWarnings("unchecked")
	public BaseContentPart<MODEL, ?> getParentBaseContentPart() {
		IVisualPart<? extends Node> parent = getParent();
		if (parent instanceof BaseContentPart) {
			return (BaseContentPart<MODEL, ?>) parent;
		}
		return null;
	}

	/**
	 * Sets the locator for this ContentPart
	 *
	 * @param locatorFactory
	 */
	@Inject
	public void setLocator(HelperProvider<Optional<Locator>> locatorFactory) {
		setLocator(locatorFactory.get(this).orElse(null));
	}

	/**
	 * Sets the locator for this ContentPart
	 *
	 * @param locator
	 *            the locator
	 */
	protected void setLocator(Locator locator) {
		this.locator = locator;
	}

	/**
	 *
	 * @return the active Locator for this ContentPart
	 */
	public Locator getLocator() {
		return locator;
	}

	/**
	 *
	 * @return the (never null) content children provider for this content part. Defaults to NotationContentChildrenProvider.getInstance()
	 */
	protected final ContentChildrenProvider<MODEL> getContentChildrenAdapter() {
		return this.contentChildrenProvider;
	}

	@Inject
	public void setContentChildrenAdapter(HelperProvider<ContentChildrenProvider<MODEL>> provider) {
		this.contentChildrenProvider = provider.get(this);
	}

	@Inject
	public void setTransactionService(TransactionService transactionService) {
		assert transactionService != null;
		this.transactionService = transactionService;
	}

	@Override
	public SetMultimap<Object, String> doGetContentAnchorages() {
		if (contentAnchorages == null) { // Cannot initialize earlier, as this may be (indirectly) called from the
											// parent's constructor. This instance may not be fully initialized yet...
			contentAnchorages = HashMultimap.create();
		}
		return contentAnchorages;
	}

	@Override
	protected void doAttachToContentAnchorage(Object contentAnchorage, String role) {
		contentAnchorages.put(contentAnchorage, role);
	}

	@Override
	protected void doDetachFromContentAnchorage(Object contentAnchorage, String role) {
		contentAnchorages.remove(contentAnchorage, role);
	}

	public void updateAnchorages() {
		SetMultimap<?, String> anchorages = doGetContentAnchorages();
		SetMultimap<?, String> notationAnchorages = getAnchorageService().getModelAnchorages();
		for (Map.Entry<?, String> entry : new HashSet<>(notationAnchorages.entries())) {
			if (!anchorages.containsEntry(entry.getKey(), entry.getValue())) {
				attachToContentAnchorage(entry.getKey(), entry.getValue());
			}
		}

		for (Map.Entry<?, String> entry : new HashSet<>(anchorages.entries())) {
			if (!notationAnchorages.containsEntry(entry.getKey(), entry.getValue())) {
				detachFromContentAnchorage(entry.getKey(), entry.getValue());
			}
		}
	}

	@Inject
	public void setAnchorageService(HelperProvider<AnchorageService> provider) {
		this.anchorageService = provider.get(this);
	}

	protected AnchorageService getAnchorageService() {
		return anchorageService;
	}

	public void updateAnchors() {
		for (Map.Entry<IVisualPart<?>, String> entry : getAnchoragesUnmodifiable().entries()) {
			IVisualPart<?> anchorage = entry.getKey();
			String role = entry.getValue();
			doAttachToAnchorageVisual(anchorage, role);
		}

		refreshVisual(); // XXX This shouldn't be synchronous
	}

}


