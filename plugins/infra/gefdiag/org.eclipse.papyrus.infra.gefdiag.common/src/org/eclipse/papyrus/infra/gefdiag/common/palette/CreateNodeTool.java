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
import org.eclipse.gef.mvc.fx.handlers.IOnStrokeHandler;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.parts.PartUtils;
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
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.parts.NotationDiagramRootPart;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.tools.AbstracTool;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SemanticElementAdapter;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * <p>
 * A Tool that supports creation of a Node by clicking on a target (To create at a given
 * location with a default size), or by Dragging a rectangular area (To create at
 * the given location, with a custom size).
 * </p>
 * <p>
 * The tool supports several element type IDs. In most cases, this is an XOR situation
 * (0..1 tool will match the target). If more than one element type ID matches the target,
 * the tool will use the first matching element type (TODO: Ultimately, the tool should offer the
 * choice to the user, e.g. via a context menu at the end of the interaction)
 * </p>
 */
public class CreateNodeTool extends AbstracTool {

	private Collection<String> elementIds;

	private IHandler activeHandler;

	@Inject
	private ElementTypeRegistry registry;

	@Inject
	private IClientContext clientContext;

	@Inject
	private PreferencesHint preferenceHints;

	public CreateNodeTool(Collection<String> elementIds) {
		this.elementIds = elementIds;
	}

	@Override
	public <T extends IHandler> List<? extends T> resolve(IGesture contextGesture, Node target, IViewer viewer, Class<T> handlerType) {
		if (!isActive()) {
			return null;
		}

		if (handlerType.isAssignableFrom(CreateNodeHandler.class)) {
			IVisualPart<? extends Node> iVisualPart;
			if (target == viewer.getCanvas()) {
				iVisualPart = viewer.getRootPart();
			} else {
				iVisualPart = PartUtils.retrieveVisualPart(viewer, target);

				assert iVisualPart != null : "Unable to find a part for node " + target + " (" + target.getStyleClass() + ")";

				// FIXME: In which case can this be null? It seems we may find a Pane
				// that is neither the Diagram Part nor the RootPart
				if (iVisualPart == null) {
					iVisualPart = viewer.getRootPart();
				}
			}


			if (iVisualPart != null) {
				IHandler handler;
				if (activeHandler != null && iVisualPart == activeHandler.getHost()) {
					handler = activeHandler;
				} else {
					handler = new CreateNodeHandler(iVisualPart, elementIds);
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
		activeHandler = null;
	}

	class CreateNodeHandler extends AbstractHandler implements IOnClickHandler, IOnDragHandler, IOnStrokeHandler, IOnHoverHandler {

		private Collection<String> elementIds;

		public CreateNodeHandler(IVisualPart<? extends Node> iVisualPart, Collection<String> elementIds) {
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
			CreateNodeTool.this.deactivate();
		}

		@Override
		public void drag(MouseEvent e, Dimension delta) {
			if (e.isStillSincePress()) {
				return;
			}
			// TODO Show feedback
		}

		@Override
		public void hover(MouseEvent e) {
			// TODO Show feedback

		}

		@Override
		public void endDrag(MouseEvent e, Dimension delta) {
			doCreate(e, delta);
			if (!e.isShortcutDown()) {
				CreateNodeTool.this.deactivate();
			}
		}

		@Override
		public void hideIndicationCursor() {
			getHost().getViewer().getAdapter(CursorSupport.class).restoreCursor();
		}

		@Override
		public boolean showIndicationCursor(MouseEvent event) {
			getHost().getViewer().getAdapter(CursorSupport.class).storeAndReplaceCursor(Cursor.CLOSED_HAND);
			return true;
		}

		/**
		 * @see org.eclipse.gef.mvc.fx.handlers.IOnStrokeHandler#release(javafx.scene.input.KeyEvent)
		 *
		 * @param event
		 */
		@Override
		public void release(KeyEvent event) {
			if (event.getCode() == KeyCode.ESCAPE) {
				CreateNodeTool.this.deactivate();
			}
		}

		@Override
		public void finalRelease(KeyEvent event) {
			if (event.getCode() == KeyCode.ESCAPE) {
				CreateNodeTool.this.deactivate();
			}
		}

		@Override
		public boolean showIndicationCursor(KeyEvent event) {
			return false;
		}

		@Override
		public void startDrag(MouseEvent e) {
			// Mouse press
		}

		@Override
		public void abortPress() {
			// Nothing
		}

		@Override
		public void initialPress(KeyEvent event) {
			// Nothing
		}

		@Override
		public void press(KeyEvent event) {
			// Nothing
		}

		@Override
		public void hoverIntent(Node hoverIntent) {
			// Nothing
		}

		protected void doCreate(MouseEvent e, Dimension delta) {

			final View contextElement;
			if (getHost() instanceof NotationDiagramRootPart) {
				contextElement = ((NotationDiagramRootPart) getHost()).getModelRoot();
			} else if (getHost() instanceof BaseContentPart) {
				Object content = ((BaseContentPart<?, ?>) getHost()).getContent();
				if (content instanceof View) {
					contextElement = (View) content;
				} else {
					return;
				}
			} else {
				return;
			}

			if (contextElement == null) {
				return;
			}

			EObject container = contextElement.getElement();
			IElementType[] elementTypes = registry.getElementTypes(clientContext);

			IElementType[] validTypes = Arrays.stream(elementTypes) //
					.filter(t -> elementIds.contains(t.getId())) //
					.toArray(IElementType[]::new);

			final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(contextElement);
			TransactionalEditingDomain editingDomain = (TransactionalEditingDomain) AdapterFactoryEditingDomain.getEditingDomainFor(contextElement);

			if (provider != null) {
				for (IElementType possibleType : validTypes) {
					CreateElementRequest createElementRequest = new CreateElementRequest(possibleType);
					createElementRequest.setContainer(container);

					ICommand createCommand = provider.getEditCommand(createElementRequest);
					if (createCommand == null || !createCommand.canExecute()) {
						continue;
					}

					CreateElementRequestAdapter semanticAdapter = new CreateElementRequestAdapter(createElementRequest);
					String hint = (possibleType instanceof IHintedType) ? ((IHintedType) possibleType).getSemanticHint() : null;
					ICommand createViewCommand = new AbstractTransactionalCommand(editingDomain, "Create view", null) {

						@Override
						protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
							org.eclipse.gmf.runtime.notation.Node node = ViewService.getInstance().createNode(new SemanticElementAdapter((EObject) createCommand.getCommandResult().getReturnValue(), possibleType), contextElement, hint, -1, preferenceHints);
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

								Point2D screenTarget = new Point2D(e.getSceneX(), e.getSceneY());

								// FIXME: This isn't exactly right. We don't account for paddings or other elements taking extra space to
								// the top or left of the actual client area (e.g. Compartment Title)
								Point2D localTarget = getHost().getVisual().sceneToLocal(screenTarget);

								if (false == node.getLayoutConstraint() instanceof Bounds) {
									return;
								}

								// Mouse location is at the end of the drag movement; not the beginning. We need to find the beginning point
								// TODO: We also need to account for bottom-to-top or right-to-left creation (i.e. the location is the top-left point, the size is the absolute delta value)
								double x = localTarget.getX() - delta.getWidth();
								double y = localTarget.getY() - delta.getHeight();

								Bounds bounds = (Bounds) node.getLayoutConstraint();
								bounds.setX((int) x);
								bounds.setY((int) y);
								if (delta.getHeight() > 30) {
									bounds.setHeight((int) delta.getHeight());
								}
								if (delta.getWidth() > 30) {
									bounds.setWidth((int) delta.getWidth());
								}
							}
						}

						@Override
						public boolean canExecute() {
							boolean result = ViewService.getInstance().provides(org.eclipse.gmf.runtime.notation.Node.class, semanticAdapter, contextElement, hint, -1, true, preferenceHints);
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
	}

}
