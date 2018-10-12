/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (EclipseSource) cletavernier@eclipsesource.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.handle;

public enum Direction {

	NORTH(1), //
	SOUTH(4), //
	WEST(8), //
	EAST(16), //
	NORTH_EAST(17), //
	NORTH_WEST(9), //
	SOUTH_EAST(20), //
	SOUTH_WEST(12), //
	NORTH_SOUTH(5), //
	EAST_WEST(24), //
	NSWE(29); //

	public static final int NORTH_VALUE = 1;
	public static final int SOUTH_VALUE = 4;
	public static final int WEST_VALUE = 8;
	public static final int EAST_VALUE = 16;
	public static final int NORTH_EAST_VALUE = NORTH_VALUE | EAST_VALUE;
	public static final int NORTH_WEST_VALUE = NORTH_VALUE | WEST_VALUE;
	public static final int SOUTH_EAST_VALUE = SOUTH_VALUE | EAST_VALUE;
	public static final int SOUTH_WEST_VALUE = SOUTH_VALUE | WEST_VALUE;

	public static final int NORTH_SOUTH_VALUE = NORTH_VALUE | SOUTH_VALUE;
	public static final int EAST_WEST_VALUE = EAST_VALUE | WEST_VALUE;

	public static final int NSWE_VALUE = NORTH_SOUTH_VALUE | EAST_WEST_VALUE;

	private final int posConstant;

	private Direction(int posConstant) {
		this.posConstant = posConstant;
	}

	/**
	 * <p>
	 * An integer constant representing this position. This value is the same
	 * as GEF 3.x PositionConstant, and is mostly there for simplifying migration.
	 * </p>
	 * <p>
	 * It may also be used for bit operations
	 * </p>
	 *
	 * @return
	 * 		The GEF 3.x position constant corresponding to this direction
	 */
	public int getPositionConstant() {
		return posConstant;
	}

	/**
	 *
	 * @param posConstant
	 * @return
	 * 		The {@link Direction} corresponding to this constant
	 *
	 * @throws IllegalArgumentException
	 *             If the request position constant doesn't match any Direction
	 */
	public static Direction getDirection(int posConstant) throws IllegalArgumentException {
		switch (posConstant) {
		case 1:
			return NORTH;
		case 4:
			return SOUTH;
		case 8:
			return WEST;
		case 16:
			return EAST;
		case 17:
			return NORTH_EAST;
		case 9:
			return NORTH_WEST;
		case 20:
			return SOUTH_EAST;
		case 12:
			return SOUTH_WEST;
		case 5:
			return NORTH_SOUTH;
		case 24:
			return EAST_WEST;
		case 29:
			return NSWE;
		default:
			throw new IllegalArgumentException("Unknown direction constant: " + posConstant);
		}
	}

}
