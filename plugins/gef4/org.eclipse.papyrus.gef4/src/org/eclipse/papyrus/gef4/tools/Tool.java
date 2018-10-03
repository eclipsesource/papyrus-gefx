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
package org.eclipse.papyrus.gef4.tools;

import java.util.EventListener;

import org.eclipse.gef.common.activate.IActivatable;
import org.eclipse.gef.mvc.fx.gestures.IHandlerResolver;
import org.eclipse.gef.mvc.fx.parts.IContentPart;

import javafx.event.Event;

/**
 * <p>
 * A {@link Tool} is an active {@link EventListener}. It may handle various kinds of events.
 * </p>
 *
 * <p>
 * Before handling any events, {@link IContentPart} are expected to check if there is an active tool
 * able to handle the event. If so, the tool is responsible for handling the event; otherwise the
 * {@link IContentPart} may handle the event directly.
 * </p>
 *
 * <p>
 * In most cases, if the Tool supports a specific event, it will redirect a more specific action
 * to the EditPart.
 * </p>
 *
 * <p>
 * For example, when a SelectionTool is active, the EditPart will redirect all events to it. In the case
 * of a MousePressed event, the SelectionTool may handle it, and would convert it to a SelectionEvent,
 * that should then be handled by the EditPart.
 * </p>
 *
 * <p>
 * The notion of Tool is useful when several actions may be bound to the same Gesture (Typically Mouse Gestures),
 * or when an action requires multi-steps Gestures (Such as the creation of Connections, which requires a click on the source element,
 * followed by a click on the target element: in that case, a CreateConnectionTool would be responsible for handling
 * these events in order, and would then send a CreateConnectionEvent to the source and/or target {@link IContentPart}s)
 * </p>
 *
 * <p>
 * The {@link Tool} should {@link Event#consume() consume} the events that it can handle, and leave the other events
 * untouched. The {@link IContentPart} should delegate all {@link Event}s to the active tool (If there is one), and only handle
 * the ones that were not {@link Event#consume() consumed}.
 * </p>
 *
 * @see ToolManager
 */
public interface Tool extends IActivatable, IHandlerResolver {
	// A tool is a IHandlerResolver with needs to be explicitly enabled/activated (e.g. when using the palette)
}
