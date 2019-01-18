/*****************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.gmf.editor.handlers;

import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.fx.core.log.Logger;
import org.eclipse.fx.core.log.LoggerCreator;
import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.behavior.CreationBehavior;
import org.eclipse.papyrus.gef4.gmf.parts.NotationDiagramRootPart;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.utils.ModelUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SemanticElementAdapter;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

import javafx.geometry.Point2D;

/**
 * <p>
 * A {@link CreateNodeHandler} that creates an Element and View from an
 * {@link IElementType}.
 * </p>
 */
public class CreateElementAndViewHandler extends AbstractHandler implements CreateNodeHandler {

	private static Logger logger = LoggerCreator.createLogger(CreateElementAndViewHandler.class);

	/**
	 * Arbitrary value to decide when a size should be set in the creation request.
	 * If the mouse drag delta is smaller than this value, we just pass a point to
	 * creation request. If the move delta is higher, we pass a location + size
	 */
	private static final int MIN_CREATION_SIZE = 20;

	@Inject
	private ElementTypeRegistry registry;

	@Inject
	private IClientContext clientContext;

	@Inject
	private PreferencesHint preferenceHints;

	@Inject
	private TransactionalEditingDomain editingDomain;

	@Override
	public ICommand create(Point2D location, Dimension size, Collection<String> elementIds) {
		final View contextElement;
		if (getHost() instanceof NotationDiagramRootPart) {
			contextElement = ((NotationDiagramRootPart) getHost()).getModelRoot();
		} else if (getHost() instanceof BaseContentPart) {
			Object content = ((BaseContentPart<?, ?>) getHost()).getContent();
			if (content instanceof View) {
				contextElement = (View) content;
			} else {
				return null;
			}
		} else {
			return null;
		}

		if (contextElement == null) {
			return null;
		}

		EObject container = contextElement.getElement();
		IElementType[] elementTypes = registry.getElementTypes(clientContext);

		IElementType[] validTypes = Arrays.stream(elementTypes) //
				.filter(t -> elementIds.contains(t.getId())) //
				.toArray(IElementType[]::new);

		final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(contextElement);

		if (provider != null) {
			for (IElementType possibleType : validTypes) {
				CreateElementRequest createElementRequest = new CreateElementRequest(possibleType);
				createElementRequest.setContainer(container);

				ICommand createCommand = provider.getEditCommand(createElementRequest);
				if (createCommand == null || !createCommand.canExecute()) {
					continue;
				}

				CreateElementRequestAdapter semanticAdapter = new CreateElementRequestAdapter(createElementRequest);
				String hint = (possibleType instanceof IHintedType) ? ((IHintedType) possibleType).getSemanticHint()
						: null;
				ICommand createViewCommand = new AbstractTransactionalCommand(editingDomain, "Create view", null) {

					@Override
					protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
							throws ExecutionException {
						org.eclipse.gmf.runtime.notation.Node node = ViewService.getInstance()
								.createNode(new SemanticElementAdapter(
										(EObject) createCommand.getCommandResult().getReturnValue(), possibleType),
										contextElement, hint, -1, preferenceHints);
						configureNode(node);
						if (node == null) {
							return CommandResult.newErrorCommandResult("Unable to create a node for this element type");
						}
						return CommandResult.newOKCommandResult();
					}

					protected void configureNode(org.eclipse.gmf.runtime.notation.Node node) {
						if (node instanceof Shape) {
							if (node.getLayoutConstraint() == null) {
								node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
							}

							if (false == node.getLayoutConstraint() instanceof Bounds) {
								return;
							}

							Bounds bounds = (Bounds) node.getLayoutConstraint();
							bounds.setX((int) location.getX());
							bounds.setY((int) location.getY());
							if (size.getHeight() >= MIN_CREATION_SIZE) {
								bounds.setHeight((int) size.getHeight());
							}
							if (size.getWidth() >= MIN_CREATION_SIZE) {
								bounds.setWidth((int) size.getWidth());
							}
						}
					}

					@Override
					public boolean canExecute() {
						boolean result = ViewService.getInstance().provides(org.eclipse.gmf.runtime.notation.Node.class,
								semanticAdapter, contextElement, hint, -1, true, preferenceHints);
						return result;
					}
				};

				CompositeTransactionalCommand command = new CompositeTransactionalCommand(editingDomain,
						"Create " + possibleType.getDisplayName());
				command.add(createCommand);
				command.add(createViewCommand);

				if (command.canExecute()) {
					return command; // FIXME: If several element types are valid, we should Task the user. This
									// should be done on the UI side; not here
				} else {
					continue; // Check the other element types
				}
			}
		}

		return null;
	}

	@Override
	public void showFeedback(Point2D location, Dimension size, Collection<String> elementTypes) {
		ICommand command = create(location, size, elementTypes);
		if (command != null && command.canExecute()) {

			final CreationBehavior creationBehavior = ModelUtil.getCreationBehavior(getHost());

			final Rectangle creationBounds = new Rectangle(location.getX(), location.getY(),
					size == null ? 0 : size.getWidth(), size == null ? 0 : size.getHeight());

			creationBehavior.addFeedback(getHost(), creationBounds);
		}
	}

	@Override
	public void removeFeedback() {
		final CreationBehavior creationBehavior = ModelUtil.getCreationBehavior(getHost());
		try {
			creationBehavior.deleteFeedback(getHost());
		} catch (final Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

}
