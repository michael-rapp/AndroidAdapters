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
	 * The DEBUG level designates logging on a high level of detail, which is
	 * intended to provide information for developers.
	 */
	DEBUG(1),

	/**
	 * The INFO level designates logging on a level of detail, which is intended
	 * to give an rudimentary insight on the behavior of the software.
	 */
	INFO(2),

	/**
	 * The WARN level designates logging on a low level of detail, which is
	 * intended to inform about potentially harmful situations.
	 */
	WARN(3),

	/**
	 * The ERROR level designates logging on the lowest level of detail, which
	 * is intended to only inform about the occurrence of critical errors.
	 */
	ERROR(4),

	/**
	 * The OFF level has the highest possible rank and is intended to turn off
	 * all logging.
	 */
	OFF(5);

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

}