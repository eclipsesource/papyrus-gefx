package org.eclipse.papyrus.gef4.tools;

import javafx.beans.property.Property;

public interface ToolManager {
	public void setTool(Tool activeTool);

	public Tool getActiveTool();

	public Property<Tool> activeToolProperty();

}
