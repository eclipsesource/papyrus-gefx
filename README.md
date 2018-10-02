# Papyrus GEFx Integration

The focus of this project is to integrate a GEFx Editor (Currently based on the GEF 5.x branch, available in Eclipse/Photon) with GMF Runtime and Papyrus

The idea is to provide an editor based on GEFx and JavaFX, while retaining the compatibility with existing Papyrus models (Semantic + Notation, CSS, Properties View, Model Explorer, GMF Genmodel...)

## Dependencies

The project depends on GEFx (Currently, 5.0.2 / Photon.0 or 2018-09), Papyrus 4.x (Currently, [4.0.0, 4.1.0], Photon.0 or 2018-09) and E(fx)clipse ([3.3.0, 3.4.0], Photon.0 or 2018-09)

## Project status

This project is still work-in-progress, and some parts of the code have been implemented a long time ago, while GEFx APIs were still moving a lot. Thus, these parts of the code may be disabled (Typically commented out) or broken. This is e.g. the case for Connections, as well as most interactions.

When everything is setup properly, you may open a Class Diagram from an existing Papyrus Model, via the Model Explorer: Right click > Open With > Diagram Editor (GEF4). If the Diagram only contains supported elements, here's what it might look like:

![GEF Legacy vs GEFx-GMF](images/GEF3-GMF%20vs%20GEFx-GMF.png)

Side-by-Side in the same Papyrus Editor: GEF Legacy (Left), GEFx (Right)

### Supported Diagrams & Elements

The editor is able to render the following elements:

- Nodes
- Compartments (Structure or List)
- Labels

Connectors and Border Items are being refactored and may be broken.

Currently, only the Papyrus Class Diagram is supported, although older versions used to support StateMachines, Composite Structures and Components.

Most interactions have been disabled or broken (Or are simply missing), due to the (not so) recent GEFx changes related to Policies and Handlers. Partially supported interactions are:

- Select/Multiselect/Deselect
- Move (But not reparent)
- Resize
- Collapse/Expand compartment
