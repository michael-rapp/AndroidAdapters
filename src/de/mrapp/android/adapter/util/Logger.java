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
package de.mrapp.android.adapter.util;

import static de.mrapp.android.adapter.util.Condition.ensureNotEmpty;
import static de.mrapp.android.adapter.util.Condition.ensureNotNull;
import android.util.Log;

/**
 * A logger, which allows to log messages or exceptions on various log levels,
 * depending on whether logging is enabled or not.
 * 
 * @author Michael Rapp
 */
public class Logger implements Entity {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The tag, which is used for logging.
	 */
	private final String tag;

	/**
	 * True, if logging is enabled, false otherwise.
	 */
	private final boolean loggingEnabled;

	/**
	 * Creates a new logger, which allows to log messages or exceptions on
	 * various log levels, depending on whether logging is enabled or not.
	 * 
	 * @param tag
	 *            The tag, which should be used for logging, as a {@link String}
	 *            . The tag may not be null or empty
	 * @param loggingEnabled
	 *            True, if logging should be enabled, false otherwise
	 */
	public Logger(final String tag, final boolean loggingEnabled) {
		ensureNotNull(tag, "The tag may not be null");
		ensureNotEmpty(tag, "The tag may not be empty");
		this.tag = tag;
		this.loggingEnabled = loggingEnabled;
	}

	/**
	 * Returns, whether logging is enabled, or not.
	 * 
	 * @return True, if logging is enabled, false otherwise
	 */
	public final boolean isLoggingEnabled() {
		return loggingEnabled;
	}

	/**
	 * Logs a specific message on log level VERBOSE.
	 * 
	 * @param message
	 *            The message, which should be logged, as a {@link String}
	 */
	public final void logVerbose(final String message) {
		if (loggingEnabled) {
			Log.v(tag, message);
		}
	}

	/**
	 * Logs a specific formatted message on log level VERBOSE.
	 * 
	 * @param format
	 *            The formatted message, as a {@link String}
	 * @param args
	 *            The arguments, which are inserted into the message, as
	 *            instance of the class {@link Object}
	 */
	public final void logVerbose(final String format, final Object... args) {
		String logMessage = String.format(format, args);
		logVerbose(logMessage);
	}

	/**
	 * Logs a specific message and an exception on log level VERBOSE.
	 * 
	 * @param message
	 *            The message, which should be logged, as a {@link String}
	 * @param exception
	 *            The exception, which should be logged, as an instance of the
	 *            class {@link Throwable}
	 */
	public final void logVerbose(final String message, final Throwable exception) {
		if (loggingEnabled) {
			Log.v(tag, message, exception);
		}
	}

	/**
	 * Logs a specific exception on log level VERBOSE.
	 * 
	 * @param exception
	 *            The exception, which should be logged, as an instance of the
	 *            class {@link Throwable}
	 */
	public final void logVerbose(final Throwable exception) {
		logVerbose(exception.getMessage(), exception);
	}

	/**
	 * Logs a specific message on log level DEBUG.
	 * 
	 * @param message
	 *            The message, which should be logged, as a {@link String}
	 */
	public final void logDebug(final String message) {
		if (loggingEnabled) {
			Log.d(tag, message);
		}
	}

	/**
	 * Logs a specific formatted message on log level DEBUG.
	 * 
	 * @param format
	 *            The formatted message, as a {@link String}
	 * @param args
	 *            The arguments, which are inserted into the message, as
	 *            instance of the class {@link Object}
	 */

	public final void logDebug(final String format, final Object... args) {
		String logMessage = String.format(format, args);
		logDebug(logMessage);
	}

	/**
	 * Logs a specific message and an exception on log level DEBUG.
	 * 
	 * @param message
	 *            The message, which should be logged, as a {@link String}
	 * @param exception
	 *            The exception, which should be logged, as an instance of the
	 *            class {@link Throwable}
	 */
	public final void logDebug(final String message, final Throwable exception) {
		if (loggingEnabled) {
			Log.d(tag, message, exception);
		}
	}

	/**
	 * Logs a specific exception on log level DEBUG.
	 * 
	 * @param exception
	 *            The exception, which should be logged, as an instance of the
	 *            class {@link Throwable}
	 */
	public final void logDebug(final Throwable exception) {
		logDebug(exception.getMessage(), exception);
	}

	/**
	 * Logs a specific message on log level INFO.
	 * 
	 * @param message
	 *            The message, which should be logged, as a {@link String}
	 */
	public final void logInfo(final String message) {
		if (loggingEnabled) {
			Log.i(tag, message);
		}
	}

	/**
	 * Logs a specific formatted message on log level INFO.
	 * 
	 * @param format
	 *            The formatted message, as a {@link String}
	 * @param args
	 *            The arguments, which are inserted into the message, as
	 *            instance of the class {@link Object}
	 */
	public final void logInfo(final String format, final Object... args) {
		String logMessage = String.format(format, args);
		logInfo(logMessage);
	}

	/**
	 * Logs a specific message and an exception on log level INFO.
	 * 
	 * @param message
	 *            The message, which should be logged, as a {@link String}
	 * @param exception
	 *            The exception, which should be logged, as an instance of the
	 *            class {@link Throwable}
	 */
	public final void logInfo(final String message, final Throwable exception) {
		if (loggingEnabled) {
			Log.i(tag, message, exception);
		}
	}

	/**
	 * Logs a specific exception on log level INFO.
	 * 
	 * @param exception
	 *            The exception, which should be logged, as an instance of the
	 *            class {@link Throwable}
	 */
	public final void logInfo(final Throwable exception) {
		logInfo(exception.getMessage(), exception);
	}

	/**
	 * Logs a specific message on log level WARN.
	 * 
	 * @param message
	 *            The message, which should be logged, as a {@link String}
	 */
	public final void logWarn(final String message) {
		if (loggingEnabled) {
			Log.w(tag, message);
		}
	}

	/**
	 * Logs a specific formatted message on log level WARN.
	 * 
	 * @param format
	 *            The formatted message, as a {@link String}
	 * @param args
	 *            The arguments, which are inserted into the message, as
	 *            instance of the class {@link Object}
	 */
	public final void logWarn(final String format, final Object... args) {
		String logMessage = String.format(format, args);
		logWarn(logMessage);
	}

	/**
	 * Logs a specific message and an exception on log level WARN.
	 * 
	 * @param message
	 *            The message, which should be logged, as a {@link String}
	 * @param exception
	 *            The exception, which should be logged, as an instance of the
	 *            class {@link Throwable}
	 */
	public final void logWarn(final String message, final Throwable exception) {
		if (loggingEnabled) {
			Log.w(tag, message, exception);
		}
	}

	/**
	 * Logs a specific exception on log level WARN.
	 * 
	 * @param exception
	 *            The exception, which should be logged, as an instance of the
	 *            class {@link Throwable}
	 */
	public final void logWarn(final Throwable exception) {
		logWarn(exception.getMessage(), exception);
	}

	/**
	 * Logs a specific message on log level ERROR.
	 * 
	 * @param message
	 *            The message, which should be logged, as a {@link String}
	 */
	public final void logError(final String message) {
		if (loggingEnabled) {
			Log.e(tag, message);
		}
	}

	/**
	 * Logs a specific formatted message on log level ERROR.
	 * 
	 * @param format
	 *            The formatted message, as a {@link String}
	 * @param args
	 *            The arguments, which are inserted into the message, as
	 *            instance of the class {@link Object}
	 */
	public final void logError(final String format, final Object... args) {
		String logMessage = String.format(format, args);
		logError(logMessage);
	}

	/**
	 * Logs a specific message and an exception on log level ERROR.
	 * 
	 * @param message
	 *            The message, which should be logged, as a {@link String}
	 * @param exception
	 *            The exception, which should be logged, as an instance of the
	 *            class {@link Throwable}
	 */
	public final void logError(final String message, final Throwable exception) {
		if (loggingEnabled) {
			Log.e(tag, message, exception);
		}
	}

	/**
	 * Logs a specific exception on log level ERROR.
	 * 
	 * @param exception
	 *            The exception, which should be logged, as an instance of the
	 *            class {@link Throwable}
	 */
	public final void logError(final Throwable exception) {
		logError(exception.getMessage(), exception);
	}

	@Override
	public final String toString() {
		return "Logger [tag=" + tag + ", loggingEnabled=" + loggingEnabled
				+ "]";
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (loggingEnabled ? 1231 : 1237);
		result = prime * result + tag.hashCode();
		return result;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Logger other = (Logger) obj;
		if (loggingEnabled != other.loggingEnabled)
			return false;
		if (!tag.equals(other.tag))
			return false;
		return true;
	}

	@Override
	public final Logger clone() {
		return new Logger(new String(tag), loggingEnabled);
	}

}