package org.eclipse.papyrus.gef4.shapes;

import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.papyrus.gef4.services.style.StyleService;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CircleDecoration implements ShapeDecoration {

	private boolean full;

	public CircleDecoration(boolean full) {
		this.full = full;
	}

	@Override
	public Decoration applyShape(IVisualPart<? extends Region> visualPart, ObservableList<Node> decorationContainer) {
		Region region = visualPart.getVisual();

		DoubleProperty radius = new SimpleDoubleProperty();
		radius.bind(
				Bindings.createDoubleBinding(
						() -> Math.max(4, Math.min(region.getWidth(), region.getHeight())) / 2,
						region.widthProperty(),
						region.heightProperty()));
		
		Circle circle = new Circle();
		circle.radiusProperty().bind(radius);
		circle.centerXProperty().bind(radius);
		circle.centerYProperty().bind(radius);
		
		region.setShape(circle);
		region.setScaleShape(false);

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
