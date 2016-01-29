package org.eclipse.papyrus.gef4.tests.utils;

import javafx.scene.paint.Color;

public class ColorUtils {

	/**
	 * Returns a JavaFX Color from a GMF/AWT rgb integer
	 *
	 * @param color
	 * @return
	 */
	public static Color fromRGB(int color) {
		return Color.rgb(color & 0xFF, (color >> 8) & 0xFF, (color >> 16) & 0xFF);
	}

	/**
	 * Returns a GMF/AWT rgb integer from a JavaFX Color
	 *
	 * @param color
	 * @return
	 */
	public static int toRGB(Color color) {
		return toInt(color.getRed()) | (toInt(color.getGreen()) << 8) | (toInt(color.getBlue()) << 16);
	}

	private static int toInt(double fxColor) {
		return (int) Math.round(fxColor * 255f);
	}
}
