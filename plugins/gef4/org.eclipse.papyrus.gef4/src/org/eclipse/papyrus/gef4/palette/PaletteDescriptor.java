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
package org.eclipse.papyrus.gef4.palette;

import java.net.URL;

import org.eclipse.fx.core.command.Command;

import javafx.beans.binding.BooleanExpression;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;

/**
 * Simple Palette Descriptor
 */
public interface PaletteDescriptor {

	/**
	 * @return
	 * 		The ID of this palette descriptor
	 */
	String getId();

	/**
	 *
	 * @return
	 * 		The label of this palette descriptor
	 */
	String getLabel();

	/**
	 *
	 * @return
	 * 		The drawers of this palette descriptor
	 */
	ObservableList<Drawer> getDrawers();

	/**
	 * Tag interface for elements that can be contained in a Drawer or a Stack; e.g. {@link ToolEntry}, {@link Separator} or {@link Stack}.
	 *
	 * Clients should usually implement one of the sub-interfaces
	 */
	interface LeafElement extends ChildElement {
		// Nothing
	}

	/**
	 * Tag interface for elements that can be contained in a Drawer; e.g. {@link ToolEntry} or {@link Separator}.
	 *
	 * Clients should usually implement one of the sub-interfaces
	 */
	interface ChildElement {
		// Tag interface for elements that can be contained in a Drawer; either ToolEntry, Separator or Stack
	}

	/**
	 * Drawer is the main top-level group in a Palette
	 */
	interface Drawer extends PaletteElement {

		ObservableList<ChildElement> getChildren();
	}

	/**
	 * A Palette Entry that can enable a Tool, which can be then be used to manipulate the diagram
	 */
	interface ToolEntry extends PaletteElement, LeafElement {
		/**
		 * @param commandTrigger
		 *            The Node used to trigger the command
		 * @return
		 * 		The command to execute when the palette tool is selected
		 */
		Command<Void> getCommand();

		/**
		 *
		 * @return
		 * 		A {@link BooleanExpression} that can be used by the {@link PaletteRenderer} to
		 *         indicate that a tool entry is active
		 */
		default BooleanProperty activeProperty() {
			return new SimpleBooleanProperty(false);
		}
	}

	/**
	 * A separator between some elements in a group (e.g. a {@link Drawer} or a {@link Stack})
	 */
	interface Separator extends LeafElement {
		// Nothing
	}

	/**
	 * A Stack in an intermediate group that can contain one level of {@link LeafElement}
	 */
	interface Stack extends ChildElement {
		ObservableList<LeafElement> getChildren();
	}

	/**
	 * A generic interface for palette elements that are displayed with a label and icon
	 */
	interface PaletteElement {
		/**
		 * @return
		 * 		The observable name for this palette element
		 */
		ObjectExpression<CharSequence> getName();

		/**
		 * @return
		 * 		The observable Icon URL for this palette element
		 */
		default ObjectExpression<URL> getIconURL() {
			return null;
		}

		default ObjectExpression<CharSequence> getTooltip() {
			return null;
		}
	}

}
