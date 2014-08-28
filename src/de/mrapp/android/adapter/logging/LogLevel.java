/*
 * AndroidAdapters Copyright 2014 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>. 
 */
package de.mrapp.android.adapter.logging;

/**
 * Contains all possible log levels and their ranks. Only log messages with a
 * rank greater or equal than the current rank of the currently applied log
 * level, are intended to be written to the output.
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public enum LogLevel {

	/**
	 * The ALL level has the lowest possible rank and is intended to turn on all
	 * logging.
	 */
	ALL(0),

	/**
	 * The VERBOSE level designates logging on the highest level of detail,
	 * which is intended to provide very fine-grained information for
	 * developers.
	 */
	VERBOSE(1),

	/**
	 * The DEBUG level designates logging on a high level of detail, which is
	 * intended to provide information for developers.
	 */
	DEBUG(2),

	/**
	 * The INFO level designates logging on a level of detail, which is intended
	 * to give an rudimentary insight on the behavior of the software.
	 */
	INFO(3),

	/**
	 * The WARN level designates logging on a low level of detail, which is
	 * intended to inform about potentially harmful situations.
	 */
	WARN(4),

	/**
	 * The ERROR level designates logging on the lowest level of detail, which
	 * is intended to only inform about the occurrence of critical errors.
	 */
	ERROR(5),

	/**
	 * The OFF level has the highest possible rank and is intended to turn off
	 * all logging.
	 */
	OFF(6);

	/**
	 * The log level's rank.
	 */
	private int rank;

	/**
	 * Creates a new log level, which has a specific rank.
	 * 
	 * @param rank
	 *            The log level's rank, as an {@link Integer} value
	 */
	private LogLevel(final int rank) {
		this.rank = rank;
	}

	/**
	 * Returns the log level's rank.
	 * 
	 * @return The log level's rank as an {@link Integer} value
	 */
	public final int getRank() {
		return rank;
	}

	/**
	 * Creates and returns the log level, which belongs to a specific rank.
	 * 
	 * @param rank
	 *            The rank of the log level, which should be returned, as an
	 *            {@link Integer} value. If the rank is invalid, an
	 *            {@link IllegalArgumentException} will be thrown
	 * @return The log level, which belongs to the given rank, as a value of the
	 *         enum {@link LogLevel}. The log level may either be
	 *         <code>ALL</code>, <code>DEBUG</code>, <code>INFO</code>,
	 *         <code>WARN</code>, <code>ERROR</code> or <code>OFF</code>
	 */
	public static LogLevel fromRank(final int rank) {
		switch (rank) {
		case 0:
			return ALL;
		case 1:
			return VERBOSE;
		case 2:
			return DEBUG;
		case 3:
			return INFO;
		case 4:
			return WARN;
		case 5:
			return ERROR;
		case 6:
			return OFF;
		default:
			throw new IllegalArgumentException();
		}
	}

}