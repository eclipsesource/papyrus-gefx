package org.eclipse.papyrus.gef4.palette.declarative;

import java.util.Collection;

import org.eclipse.papyrus.gef4.palette.PaletteDescriptor;

/**
 * This interface can be used to provide additional declarative
 * properties of a ToolEntry. 
 */
// Implementation note: GEFx Palettes are Imperative (But configured via
// declarative models when using Papyrus Palette Configurations). This
// interface was added to provide GLSP Declarative Palettes from a GEFx
// Palette Descriptor. At the moment, it is not used (or meant to be used) 
// in GEFx directly .
public interface IdCreationTool extends PaletteDescriptor.ToolEntry {
	Collection<String> getElementTypeIds();
	
	CreationKind getCreationKind();
	
	enum CreationKind {
		CREATE_NODE,
		CREATE_EDGE
	}
}
