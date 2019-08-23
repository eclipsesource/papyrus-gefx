package org.eclipse.papyrus.gef4.shapes;

import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.services.style.StyleService;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class DiamondDecoration implements ShapeDecoration {

	private boolean full;

	public DiamondDecoration(boolean full) {
		this.full = full;
	}

	@Override
	public Decoration applyShape(IVisualPart<? extends Region> visualPart, ObservableList<Node> decorationContainer) {
		Region region = visualPart.getVisual();

		Polygon diamond = new Polygon(0, 2, 2, 0, 4, 2, 2, 4);

		region.setShape(diamond);
		region.setScaleShape(true);

		Runnable refresh = () -> {
			if (full) {
				StyleService partStyleService = visualPart.getAdapter(StyleService.class);
				Color fgColor;
				if (partStyleService != null) {
					fgColor = partStyleService.getBorderColors().getRight();
				} else {
					fgColor = Color.BLACK;
				}
				region.setBackground(new Background(new BackgroundFill(fgColor, null, null)));
				region.setBorder(null);
			} // For empty shapes, just use whatever background is configured on the region
		};

		refresh.run();

		return new Decoration() {
			@Override
			public void refresh() {
				refresh.run();
			}

			@Override
			public void dispose() {
				region.setShape(null);
				region.setScaleShape(true);
			}
		};
	}

}
