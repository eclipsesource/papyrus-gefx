package org.eclipse.papyrus.gef4.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum Position {
	NORTH, EAST, SOUTH, WEST, //
	NORTH_EAST(NORTH, EAST), //
	SOUTH_EAST(SOUTH, EAST), //
	NORTH_WEST(NORTH, WEST), //
	SOUTH_WEST(SOUTH, WEST);

	private final Set<Position> intersections = new HashSet<>();

	private Position(Position... intersects) {
		this();
		intersections.addAll(Arrays.asList(intersects));
	}

	private Position() {
		intersections.add(this);
	}

	public boolean is(Position pos) {
		return intersections.contains(pos);
	}

}
