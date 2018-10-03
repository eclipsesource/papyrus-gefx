/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gefdiag.common.palette;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.mvc.fx.gestures.IGesture;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.CursorSupport;
import org.eclipse.gef.mvc.fx.handlers.IHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnClickHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnDragHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnHoverHandler;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
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
import org.eclipse.papyrus.gef4.tools.AbstracTool;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SemanticElementAdapter;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * <p>
 * A Tool that supports creation of Connections via a click on the source and target,
 * or via drag and drop between a source and a target.
 * </p>
 */
public class CreateConnectionTool extends AbstracTool {

	private Collection<String> elementIds;

	private CreateConnectionHandler activeHandler;

	@Inject
	private ElementTypeRegistry registry;

	@Inject
	private IClientContext clientContext;

	@Inject
	private PreferencesHint preferenceHints;

	@Inject
	private Diagram diagram;

	private IContentPart<?> sourcePart;

	public CreateConnectionTool(Collection<String> elementIds) {
		this.elementIds = elementIds;
	}

	@Override
	public <T extends IHandler> List<? extends T> resolve(IGesture contextGesture, Node target, IViewer viewer, Class<T> handlerType) {
		if (!isActive()) {
			return null;
		}

		if (handlerType.isAssignableFrom(CreateConnectionHandler.class)) {
			IVisualPart<? extends Node> iVisualPart;
			if (target == viewer.getCanvas()) {
				iVisualPart = viewer.getRootPart();
			} else {
				iVisualPart = viewer.getVisualPartMap().get(target);

				// FIXME: In which case can this be null? It seems we may find a Pane
				// that is neither the Diagram Part nor the RootPart
				if (iVisualPart == null) {
					iVisualPart = viewer.getRootPart();
				}
			}


			if (iVisualPart != null) {
				CreateConnectionHandler handler;
				if (activeHandler != null && iVisualPart == activeHandler.getHost()) {
					handler = activeHandler;
				} else {
					handler = new CreateConnectionHandler(iVisualPart, elementIds);
				}

				if (handlerType.isInstance(handler)) {
					T clickHandler = handlerType.cast(handler);
					activeHandler = handler;
					return Collections.singletonList(clickHandler);
				}
			}
		}
		activeHandler = null;
		return null;
	}

	@Override
	protected void doDeactivate() {
		if (activeHandler != null) {
			activeHandler.getHost().getViewer().getAdapter(CursorSupport.class).restoreCursor();
		}
		activeHandler = null;
		sourcePart = null;
	}

	class CreateConnectionHandler extends AbstractHandler implements IOnClickHandler, IOnDragHandler, IOnHoverHandler {

		private Collection<String> elementIds;

		public CreateConnectionHandler(IVisualPart<? extends Node> iVisualPart, Collection<String> elementIds) {
			setAdaptable(iVisualPart);
			this.elementIds = elementIds;
		}

		@Override
		public void click(MouseEvent e) {
			// Do nothing on click events; we handle press (startDrag()) and release (endDrag()) separately
			return;
		}

		@Override
		public void abortDrag() {
			CreateConnectionTool.this.deactivate();
		}

		@Override
		public void startDrag(MouseEvent e) {
			// Mouse press. Nothing to do (for now?); FX/GEF already takes care
			// of identifying a press-drag event (So the host of a drag event is always
			// the connection source part)
		}

		@Override
		public void drag(MouseEvent e, Dimension delta) {
			if (e.isStillSincePress()) {
				return;
			}

			IVisualPart<?> dragTarget = getDragTarget(e);

			if (CreateConnectionTool.this.sourcePart == null) {
				if (dragTarget instanceof IContentPart) {
					sourcePart = (IContentPart<?>) dragTarget;
				} else {
					CreateConnectionTool.this.deactivate();
				}
			}
		}

		@Override
		public void endDrag(MouseEvent e, Dimension delta) {
			if (e.isStillSincePress()) {
				// Simple click on source or target
				if (sourcePart == null) {
					if (getHost() instanceof IContentPart) {
						sourcePart = (IContentPart<?>) getHost();
					} else {
						CreateConnectionTool.this.deactivate();
					}
				} else {
					createAndDeactivate(e, delta);
				}
			} else {
				// Release after a drag
				if (sourcePart == null) {
					CreateConnectionTool.this.deactivate();
				} else {
					createAndDeactivate(e, delta);
				}
			}
		}

		@Override
		public void hover(MouseEvent e) {
			if (sourcePart == null) {
				// TODO Feedback: Am I a valid connection source?
			} else {
				// TODO Feedback: Am I a valid connection target?
				// TODO Feedback: If not; update target anchor under mouse & show X cursor
			}
		}

		protected IVisualPart<?> getDragTarget(MouseEvent e) {
			Node node = e.getPickResult().getIntersectedNode();
			if (node != null) {
				return getHost().getViewer().getVisualPartMap().get(node);
			}
			return null;
		}

		@Override
		public void hideIndicationCursor() {
			getHost().getViewer().getAdapter(CursorSupport.class).restoreCursor();
		}

		@Override
		public boolean showIndicationCursor(MouseEvent event) {
			CursorSupport adapter = getHost().getViewer().getAdapter(CursorSupport.class);
			adapter.storeAndReplaceCursor(Cursor.CROSSHAIR);
			return adapter.isCursorChanged();
		}

		@Override
		public boolean showIndicationCursor(KeyEvent event) {
			CursorSupport adapter = getHost().getViewer().getAdapter(CursorSupport.class);
			adapter.storeAndReplaceCursor(Cursor.CROSSHAIR);
			return adapter.isCursorChanged();
		}

		@Override
		public void hoverIntent(Node hoverIntent) {
			// Nothing
		}

		protected void createAndDeactivate(MouseEvent e, Dimension delta) {
			try {
				doCreate(e, delta);
			} finally {
				CreateConnectionTool.this.deactivate();
			}
		}

		protected void doCreate(MouseEvent e, Dimension delta) {
			if (sourcePart == null) {
				return;
			}
			IVisualPart<?> targetPart = getDragTarget(e);

			if (false == sourcePart instanceof IContentPart || false == targetPart instanceof IContentPart) {
				System.out.println("Invalid step: source or target is not a content part");
				return;
			}

			final IContentPart<?> sourceContentPart = sourcePart;
			final IContentPart<?> targetContentPart = (IContentPart<?>) targetPart;

			final Object sourceContent = sourceContentPart.getContent();
			final Object targetContent = targetContentPart.getContent();

			if (false == sourceContent instanceof View || false == targetContent instanceof View) {
				System.out.println("Invalid step: source or target is not a notation view");
				return;
			}

			final View sourceView = findConnectableView((View) sourceContent);
			final View targetView = findConnectableView((View) targetContent);

			if (sourceView == null || targetView == null) {
				System.out.println("Can't find a connectable view");
				return;
			}

			// Edges are always created on the diagram
			final View contextElement = diagram;

			EObject container = contextElement.getElement();
			IElementType[] elementTypes = registry.getElementTypes(clientContext);

			IElementType[] validTypes = Arrays.stream(elementTypes) //
					.filter(t -> elementIds.contains(t.getId())) //
					.toArray(IElementType[]::new);

			final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(contextElement);
			TransactionalEditingDomain editingDomain = (TransactionalEditingDomain) AdapterFactoryEditingDomain.getEditingDomainFor(contextElement);

			if (provider != null) {
				for (IElementType possibleType : validTypes) {
					CreateRelationshipRequest createElementRequest = new CreateRelationshipRequest(container, sourceView.getElement(), targetView.getElement(), possibleType);

					ICommand createCommand = provider.getEditCommand(createElementRequest);
					if (createCommand == null || !createCommand.canExecute()) {
						continue;
					}

					CreateElementRequestAdapter semanticAdapter = new CreateElementRequestAdapter(createElementRequest);
					String hint = (possibleType instanceof IHintedType) ? ((IHintedType) possibleType).getSemanticHint() : null;
					ICommand createViewCommand = new AbstractTransactionalCommand(editingDomain, "Create edge view", null) {

						@Override
						protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {


							SemanticElementAdapter semanticAdapter = new SemanticElementAdapter((EObject) createCommand.getCommandResult().getReturnValue(), possibleType);
							org.eclipse.gmf.runtime.notation.View edge = ViewService.getInstance().createEdge(semanticAdapter, contextElement, hint, -1, preferenceHints);
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
							boolean result = ViewService.getInstance().provides(org.eclipse.gmf.runtime.notation.Edge.class, semanticAdapter, contextElement, hint, -1, true, preferenceHints);
							return result;
						}
					};

					CompositeTransactionalCommand command = new CompositeTransactionalCommand(editingDomain, "Create " + possibleType.getDisplayName());
					command.add(createCommand);
					command.add(createViewCommand);

					if (command.canExecute()) {
						editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(command));
						if (command.getCommandResult().getStatus().isOK()) {
							return;
						}
					}
				}
			}
		}

		/*
		 * In the GMF Metamodel, all views can be connected via edges. However, by implementation,
		 * only Nodes and Edges actually support connection (For example, Compartments can't be connected)
		 */
		private View findConnectableView(View sourceContent) {
			if (sourceContent == null) {
				return null;
			}

			// XXX We test NodeImpl because the metamodel implementation is broken.
			// BasicCompartment is a Node (Which should be Connectable according to the metamodel)
			// but doesn't properly implement source edges or target edges. Only NodeImpl and ShapeImpl (And edges) actually do.
			if (sourceContent instanceof Edge || sourceContent instanceof NodeImpl) {
				return sourceContent;
			}
			EObject parent = sourceContent.eContainer();
			if (parent instanceof View) {
				return findConnectableView((View) parent);
			}
			return null;
		}
	}

}
