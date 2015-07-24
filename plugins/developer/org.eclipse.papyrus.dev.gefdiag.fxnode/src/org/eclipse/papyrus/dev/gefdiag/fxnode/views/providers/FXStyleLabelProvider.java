/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.dev.gefdiag.fxnode.views.providers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import javafx.css.CssMetaData;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;

public class FXStyleLabelProvider extends LabelProvider {
	@Override
	public Image getImage(Object element) {
		return super.getImage(element);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof CssMetaData<?, ?>) {
			CssMetaData<?, ?> meta = (CssMetaData<?, ?>) element;
			String property = meta.getProperty();
			return property + ": ";
		}
		if (element instanceof Map.Entry) {
			Map.Entry<?, ?> entry = (Map.Entry<?, ?>) element;
			Object value = entry.getValue();

			String stringValue = "undefined";
			if (value instanceof Node) {
				Node node = (Node) value;
				for (CssMetaData<?, ?> data : node.getCssMetaData()) {
					if (entry.getKey().equals(data.getProperty())) {
						Object styleValue = ((CssMetaData<Node, ?>) data).getStyleableProperty(node).getValue();
						stringValue = styleValue == null ? "null" : parseValue(styleValue);
						break;
					}
				}
			} else if (value instanceof String) {
				stringValue = (String) value;
			} else {
				stringValue = value == null ? "null" : value.toString();
			}

			return entry.getKey() + ": " + stringValue;
		}
		if (element instanceof String) {
			return "." + (String) element;
		}
		return "<>";
	}

	private String parseValue(Object styleValue) {
		if (styleValue instanceof Background) {
			Background background = (Background) styleValue;
			return getText(background);
		}
		if (styleValue instanceof Border) {
			Border border = (Border) styleValue;
			return getText(border);
		}
		if (styleValue instanceof Object[]) {
			return Arrays.deepToString((Object[]) styleValue);
		}
		return styleValue == null ? "null" : styleValue.toString();
	}

	private String getText(Background background) {
		if (background.getFills() == null || background.getFills().isEmpty()) {
			return "none";
		}
		return background.getFills().get(0).getFill().toString();
	}

	private String getText(Border border) {
		return String.format("Insets: %s, Outsets: %s, Strokes: %s", getText(border.getInsets()), getText(border.getOutsets()), getText(border.getStrokes()));
	}

	private String getText(Insets insets) {
		if (insets == null) {
			return "null";
		}
		return String.format("Top: %s, Right: %s, Bottom: %s, Left: %s", insets.getTop(), insets.getRight(), insets.getBottom(), insets.getLeft());
	}

	private String getText(List<BorderStroke> strokes) {
		if (strokes == null || strokes.isEmpty()) {
			return "null";
		}
		BorderStroke stroke = strokes.get(0);
		return String.format("Top: %s, Right: %s, Bottom: %s, Left: %s", stroke.getTopStyle(), stroke.getRightStyle(), stroke.getBottomStyle(), stroke.getLeftStyle());
		// return String.format("Top: %s, Right: %s, Bottom: %s, Left: %s", insets.getTop(), insets.getRight(), insets.getBottom(), insets.getLeft());
	}
}
