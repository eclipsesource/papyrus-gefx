/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.gef4.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmf.runtime.draw2d.ui.graphics.ColorRegistry;

import javafx.scene.paint.Color;

public class ColorUtil {

	/**
	 * Gets the hsl parameters of the color. return list constraint get(0)=hue get(1)=saturation get(2)=lightness
	 *
	 * @param color
	 *            the color
	 * @return the hsl
	 */
	public static List<Integer> getHsl(final Color color) {

		final double r = color.getRed();
		final double g = color.getGreen();
		final double b = color.getBlue();
		final double max = Math.max(Math.max(r, g), b);
		final double min = Math.min(Math.min(r, g), b);
		final double c = max - min;

		double h_ = 0.f;
		if (c == 0) {
			h_ = 0;
		} else if (max == r) {
			h_ = (float) (g - b) / c;
			if (h_ < 0)
				h_ += 6.f;
		} else if (max == g) {
			h_ = (float) (b - r) / c + 2.f;
		} else if (max == b) {
			h_ = (float) (r - g) / c + 4.f;
		}
		final double h = 60.f * h_;

		final double l = (max + min) * 0.5f;

		double s;
		if (c == 0) {
			s = 0.f;
		} else {
			s = c / (1 - Math.abs(2.f * l - 1.f));
		}

		final List<Integer> hsl = new ArrayList<Integer>();
		hsl.add((int) h);
		hsl.add((int) (s * 100));
		hsl.add((int) (l * 100));

		return hsl;
	}

	/**
	 * Gets Color with hsl parameters
	 *
	 * @param hue
	 *            the hue
	 * @param saturation
	 *            the saturation
	 * @param lightness
	 *            the lightness
	 * @param opacity
	 *            the opacity
	 * @return the color
	 */
	public static Color getColor(final int hue, final int saturation, final int lightness, final double opacity) {
		final float h = hue;
		final float s = ((float) saturation) / 100;
		final float l = ((float) lightness) / 100;

		final float c = (1 - Math.abs(2.f * l - 1.f)) * s;
		final float h_ = h / 60.f;
		float h_mod2 = h_;
		if (h_mod2 >= 4.f)
			h_mod2 -= 4.f;
		else if (h_mod2 >= 2.f)
			h_mod2 -= 2.f;

		final float x = c * (1 - Math.abs(h_mod2 - 1));
		float r_, g_, b_;
		if (h_ < 1) {
			r_ = c;
			g_ = x;
			b_ = 0;
		} else if (h_ < 2) {
			r_ = x;
			g_ = c;
			b_ = 0;
		} else if (h_ < 3) {
			r_ = 0;
			g_ = c;
			b_ = x;
		} else if (h_ < 4) {
			r_ = 0;
			g_ = x;
			b_ = c;
		} else if (h_ < 5) {
			r_ = x;
			g_ = 0;
			b_ = c;
		} else {
			r_ = c;
			g_ = 0;
			b_ = x;
		}

		final float m = l - (0.5f * c);
		final int r = (int) ((r_ + m) * (255.f) + 0.5f);
		final int g = (int) ((g_ + m) * (255.f) + 0.5f);
		final int b = (int) ((b_ + m) * (255.f) + 0.5f);
		// return b << 16 | g << 8 | r;
		return getColor(b << 16 | g << 8 | r, opacity);

	}

	/**
	 * Gets the javafx color.
	 *
	 * @param fillcolor
	 *            the swt fill color
	 * @param opacity
	 *            the opacity
	 * @return the color
	 */
	public static Color getColor(final int fillcolor, final double opacity) {
		Color color;
		final org.eclipse.swt.graphics.Color swtColor = ColorRegistry.getInstance().getColor(fillcolor);
		color = new Color((double) swtColor.getRed() / 255, (double) swtColor.getGreen() / 255, (double) swtColor.getBlue() / 255, opacity);
		return color;
	}

}
