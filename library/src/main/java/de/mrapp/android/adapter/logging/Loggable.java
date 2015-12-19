/*
 * AndroidAdapters Copyright 2014 - 2015 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package de.mrapp.android.adapter.logging;

/**
 * Defines the interface, a class, which should allow to use logging, must implement.
 *
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface Loggable {

    /**
     * Returns the current log level. Only log messages with a rank greater or equal than the rank
     * of the currently applied log level, are written to the output.
     *
     * @return The current log level as a value of the enum {@link LogLevel}. The log level may
     * either be <code>ALL</code>, <code>DEBUG</code>, <code>INFO</code>, <code>WARN</code>,
     * <code>ERROR</code> or <code>OFF</code>
     */
    LogLevel getLogLevel();

    /**
     * Sets the log level. Only log messages with a rank greater or equal than the rank of the
     * currently applied log level, are written to the output.
     *
     * @param logLevel
     *         The log level, which should be set, as a value of the enum {@link LogLevel}. The log
     *         level may not be null
     */
    void setLogLevel(LogLevel logLevel);

}