/*****************************************************************************
 * Copyright (c) 2019 EclipseSource
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  EclipseSource - Initial API and implementation
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
import org.eclipse.gef.fx.anchors.DynamicAnchor;
import org.eclipse.gef.fx.anchors.IAnchor;
import org.eclipse.gef.fx.anchors.StaticAnchor;
import org.eclipse.gef.geometry.convert.fx.FX2Geometry;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
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
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.impl.NodeImpl;
import org.eclipse.papyrus.gef4.behavior.ConnectionCreationBehavior;
import org.eclipse.papyrus.gef4.feedback.Anchors;
import org.eclipse.papyrus.gef4.utils.ModelUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SemanticElementAdapter;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

import javafx.geometry.Point2D;

public class CreateConnectionAndViewHandler extends AbstractHandler implements CreateConnectionHandler {

	private static final Logger logger = LoggerCreator.createLogger(CreateConnectionAndViewHandler.class);

	@Inject
	private ElementTypeRegistry registry;

	@Inject
	private IClientContext clientContext;

	@Inject
	private PreferencesHint preferenceHints;

	@Inject
	private TransactionalEditingDomain editingDomain;

	@Inject
	private Diagram diagram;

	@Override
	public ICommand getSourceCommand(Point2D location, Collection<String> elementIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICommand getCompleteCommand(Point2D location, IContentPart<?> targetPart, Collection<String> elementIds) {
		if (targetPart == null) {
			return null;
		}
		IVisualPart<?> sourcePart = getHost();

		if (false == sourcePart instanceof IContentPart || false == targetPart instanceof IContentPart) {
			logger.debug("Invalid step: source or target is not a content part");
			return null;
		}

		final IContentPart<?> sourceContentPart = (IContentPart<?>) sourcePart;
		final IContentPart<?> targetContentPart = targetPart;

		final Object sourceContent = sourceContentPart.getContent();
		final Object targetContent = targetContentPart.getContent();

		if (false == sourceContent instanceof View || false == targetContent instanceof View) {
			logger.debug("Invalid step: source or target is not a notation view");
			return null;
		}

		final View sourceView = findConnectableView((View) sourceContent);
		final View targetView = findConnectableView((View) targetContent);

		if (sourceView == null || targetView == null) {
			logger.debug("Can't find a connectable view");
			return null;
		}

		// Edges are always created on the diagram
		final View contextElement = diagram;

		EObject container = contextElement.getElement();
		IElementType[] elementTypes = registry.getElementTypes(clientContext);

		IElementType[] validTypes = Arrays.stream(elementTypes) //
				.filter(t -> elementIds.contains(t.getId())) //
				.toArray(IElementType[]::new);

		final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(contextElement);

		if (provider != null) {
			for (IElementType possibleType : validTypes) {
				CreateRelationshipRequest createElementRequest = new CreateRelationshipRequest(container,
						sourceView.getElement(), targetView.getElement(), possibleType);

				ICommand createCommand = provider.getEditCommand(createElementRequest);
				if (createCommand == null || !createCommand.canExecute()) {
					continue;
				}

				CreateElementRequestAdapter semanticAdapter = new CreateElementRequestAdapter(createElementRequest);
				String hint = (possibleType instanceof IHintedType) ? ((IHintedType) possibleType).getSemanticHint()
						: null;
				ICommand createViewCommand = new AbstractTransactionalCommand(editingDomain, "Create edge view", null) {

					@Override
					protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
							throws ExecutionException {

						SemanticElementAdapter semanticAdapter = new SemanticElementAdapter(
								(EObject) createCommand.getCommandResult().getReturnValue(), possibleType);
						org.eclipse.gmf.runtime.notation.View edge = ViewService.getInstance()
								.createEdge(semanticAdapter, contextElement, hint, -1, preferenceHints);
						if (edge == null) {
							return CommandResult.newErrorCommandResult("Unable to create a node for this element type");
						}
						configureEdge(edge);
						return CommandResult.newOKCommandResult();
					}

					protected void configureEdge(org.eclipse.gmf.runtime.notation.View edgeView) {
						if (false == edgeView instanceof Edge) {
							return;
						}
						Edge edge = (Edge) edgeView;
						edge.setSource(sourceView);
						edge.setTarget(targetView);
						edge.setSourceAnchor(NotationFactory.eINSTANCE.createIdentityAnchor());
						edge.setTargetAnchor(NotationFactory.eINSTANCE.createIdentityAnchor());
					}

					@Override
					public boolean canExecute() {
						boolean result = ViewService.getInstance().provides(org.eclipse.gmf.runtime.notation.Edge.class,
								semanticAdapter, contextElement, hint, -1, true, preferenceHints);
						return result;
					}
				};

				CompositeTransactionalCommand command = new CompositeTransactionalCommand(editingDomain,
						"Create " + possibleType.getDisplayName());
				command.add(createCommand);
				command.add(createViewCommand);

				if (command.canExecute()) {
					return command;
				} else {
					continue;
				}
			}
		}

		return null;
	}

	/*
	 * In the GMF Metamodel, all views can be connected via edges. However, by
	 * implementation, only Nodes and Edges actually support connection (For
	 * example, Compartments can't be connected)
	 */
	private View findConnectableView(View sourceContent) {
		if (sourceContent == null) {
			return null;
		}

		// XXX We test NodeImpl because the metamodel implementation is broken.
		// BasicCompartment is a Node (Which should be Connectable according to the
		// metamodel)
		// but doesn't properly implement source edges or target edges. Only NodeImpl
		// and ShapeImpl (And edges) actually do.
		if (sourceContent instanceof Edge || sourceContent instanceof NodeImpl) {
			return sourceContent;
		}
		EObject parent = sourceContent.eContainer();
		if (parent instanceof View) {
			return findConnectableView((View) parent);
		}
		return null;
	}

	private IContentPart<?> findConnectablePart(IContentPart<?> part) {
		Object partContent = part.getContent();
		if (partContent instanceof View) {
			View connectableView = findConnectableView((View) partContent);
			if (connectableView != null) {
				return part.getRoot().getViewer().getContentPartMap().get(connectableView);
			}
		}
		return part;
	}

	@Override
	public void showFeedback(Point2D location, Collection<String> elementType) {
		ConnectionCreationBehavior creationBehavior = ModelUtil.getConnectionCreationBehavior(getHost());
		if (creationBehavior == null) {
			logger.debug("No connection feedback behavior installed on " + getHost());
			return;
		}

		IContentPart<?> realSourcePart = findConnectablePart((IContentPart<?>) getHost());
		if (realSourcePart == null) {
			removeFeedback();
			return;
		}

		creationBehavior.addFeedback(getHost(), new Anchors() {

			@Override
			public IAnchor getSourceAnchor() {
				return new DynamicAnchor(realSourcePart.getVisual());
			}

			@Override
			public IAnchor getTargetAnchor() {
				return new StaticAnchor(FX2Geometry.toPoint(location)); // Mouse location
			}

		});
	}

	@Override
	public void showFeedback(Point2D location, IContentPart<?> targetPart, Collection<String> elementType) {
		ConnectionCreationBehavior creationBehavior = ModelUtil.getConnectionCreationBehavior(getHost());
		if (creationBehavior == null) {
			logger.debug("No connection feedback behavior installed on " + getHost());
			return;
		}

		IContentPart<?> realSourcePart = findConnectablePart((IContentPart<?>) getHost());
		IContentPart<?> realTargetPart = findConnectablePart(targetPart);

		final IAnchor sourceAnchor;
		final IAnchor targetAnchor;

		if (realSourcePart != null) {
			sourceAnchor = new DynamicAnchor(realSourcePart.getVisual());
		} else {
			removeFeedback();
			return;
		}

		if (realTargetPart != null) {
			targetAnchor = new DynamicAnchor(realTargetPart.getVisual());
		} else {
			targetAnchor = new StaticAnchor(FX2Geometry.toPoint(location));
		}

		creationBehavior.addFeedback(getHost(), new Anchors() {

			@Override
			public IAnchor getSourceAnchor() {
				return sourceAnchor;
			}

			@Override
			public IAnchor getTargetAnchor() {
				return targetAnchor;
			}

		});
	}

	@Override
	public void removeFeedback() {
		ConnectionCreationBehavior creationBehavior = ModelUtil.getConnectionCreationBehavior(getHost());
		if (creationBehavior == null) {
			logger.debug("No connection feedback behavior installed on " + getHost());
			return;
		}

		creationBehavior.deleteFeedback(getHost());
	}

}
