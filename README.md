# Papyrus GEFx Integration

The focus of this project is to integrate a GEFx Editor (Currently based on the GEF 5.x branch, available in Eclipse/Photon) with GMF Runtime and Papyrus

The idea is to provide an editor based on GEFx and JavaFX, while retaining the compatibility with existing Papyrus models (Semantic + Notation, CSS, Properties View, Model Explorer, GMF Genmodel...)

## Dependencies

The project depends on Eclipse 2019-03 & Java 11, GEFx (Currently, 5.0.2 / 2019-03), Papyrus 4.x (Currently, [4.0.0, 4.3.0], any version between Photon.0 and 2019-03) and E(fx)clipse (3.5.0, not available from the Eclipse release train anymore)

## Project status

This project is still work-in-progress, and some parts of the code have been implemented a long time ago, while GEFx APIs were still moving a lot. Thus, these parts of the code may be disabled (Typically commented out) or broken, although most of them have now been properly migrated.

When everything is setup properly, you may open a Class, Component or Composite Diagram from an existing Papyrus Model, via the Model Explorer: Right click > Open With > Diagram Editor (GEF4). If the Diagram only contains supported elements, here's what it might look like:

![GEF Legacy vs GEFx-GMF](images/GEF3-GMF%20vs%20GEFx-GMF%202019-03.png)

Side-by-Side in the same Papyrus Editor: GEF Legacy (Left), GEFx (Right)

### Supported Diagrams & Elements

The editor is able to render the following elements:

- Nodes (Including Border Items)
- Compartments (Structure or List)
- Labels
- Connectors
- Palettes

Currently, only a few Papyrus Diagram are supported (Mostly the Class Diagram). Simple versions of Composite Structure and Component Diagrams are provided, although with very little diagram customization. State Machine Diagrams exist, but don't provide any customization at all. 

Basic interactions are supported, including:

- Select/Multiselect/Deselect
- Move (But not reparent)
- Resize
- Creation of (Most) Nodes and List Items
- Creation of (Most) Edges
- ~~Collapse/Expand compartment~~ To be restored

