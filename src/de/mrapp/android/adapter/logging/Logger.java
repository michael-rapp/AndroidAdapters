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

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;
import static de.mrapp.android.adapter.util.Condition.ensureNotEmpty;
import android.util.Log;

/**
 * A logger, which allows to log messages on specific log levels.
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public class Logger {

	/**
	 * The current log level.
	 */
	private LogLevel logLevel;

	/**
	 * Creates a new logger, which uses a specific log level.
	 * 
	 * @param logLevel
	 *            The log level, which should be used by the logger, as a value
	 *            of the enum {@link LogLevel}. The log level may not be null
	 */
	public Logger(final LogLevel logLevel) {
		setLogLevel(logLevel);
	}

	/**
	 * Returns the current log level. Only log messages with a rank greater or
	 * equal than the rank of the currently applied log level, are written to
	 * the output.
	 * 
	 * @return The current log level as a value of the enum {@link LogLevel}.
	 *         The log level may either be <code>ALL</code>, <code>DEBUG</code>,
	 *         <code>INFO</code>, <code>WARN</code>, <code>ERROR</code> or
	 *         <code>OFF</code>
	 */
	public final LogLevel getLogLevel() {
		return logLevel;
	}

	/**
	 * Sets the log level. Only log messages with a rank greater or equal than
	 * the rank of the currently applied log level, are written to the output.
	 * 
	 * @param logLevel
	 *            The log level, which should be set, as a value of the enum
	 *            {@link LogLevel}. The log level may not be null
	 */
	public final void setLogLevel(final LogLevel logLevel) {
		ensureNotNull(logLevel, "The log level may not be null");
	}

	/**
	 * Logs a specific message on the log level DEBUG.
	 * 
	 * @param tag
	 *            The tag, which identifies the source of the log message, as a
	 *            {@link String}. The tag may neither be null, nor empty
	 * @param message
	 *            The message, which should be logged, as a {@link String}. The
	 *            message may neither be null, nor empty
	 */
	public final void logDebug(final String tag, final String message) {
		ensureNotNull(tag, "The tag may not be null");
		ensureNotEmpty(tag, "The tag may not be empty");
		ensureNotNull(message, "The message may not be null");
		ensureNotEmpty(message, "The message may not be empty");

		if (getLogLevel().getRank() >= LogLevel.DEBUG.getRank()) {
			Log.d(tag, message);
		}
	}

	/**
	 * Logs a specific message and exception on the log level DEBUG.
	 * 
	 * @param tag
	 *            The tag, which identifies the source of the log message, as a
	 *            {@link String}. The tag may neither be null, nor empty
	 * @param message
	 *            The message, which should be logged, as a {@link String}. The
	 *            message may neither be null, nor empty
	 * @param cause
	 *            The exception, which caused the log message, as an instance of
	 *            the class {@link Throwable}. The cause may not be null
	 */
	public final void logDebug(final String tag, final String message,
			final Throwable cause) {
		ensureNotNull(tag, "The tag may not be null");
		ensureNotEmpty(tag, "The tag may not be empty");
		ensureNotNull(message, "The message may not be null");
		ensureNotEmpty(message, "The message may not be empty");
		ensureNotNull(cause, "The cause may not be null");

		if (getLogLevel().getRank() >= LogLevel.DEBUG.getRank()) {
			Log.d(tag, message, cause);
		}
	}

	/**
	 * Logs a specific message on the log level INFO.
	 * 
	 * @param tag
	 *            The tag, which identifies the source of the log message, as a
	 *            {@link String}. The tag may neither be null, nor empty
	 * @param message
	 *            The message, which should be logged, as a {@link String}. The
	 *            message may neither be null, nor empty
	 */
	public final void logInfo(final String tag, final String message) {
		ensureNotNull(tag, "The tag may not be null");
		ensureNotEmpty(tag, "The tag may not be empty");
		ensureNotNull(message, "The message may not be null");
		ensureNotEmpty(message, "The message may not be empty");

		if (getLogLevel().getRank() >= LogLevel.INFO.getRank()) {
			Log.i(tag, message);
		}
	}

	/**
	 * Logs a specific message and exception on the log level INFO.
	 * 
	 * @param tag
	 *            The tag, which identifies the source of the log message, as a
	 *            {@link String}. The tag may neither be null, nor empty
	 * @param message
	 *            The message, which should be logged, as a {@link String}. The
	 *            message may neither be null, nor empty
	 * @param cause
	 *            The exception, which caused the log message, as an instance of
	 *            the class {@link Throwable}. The cause may not be null
	 */
	public final void logInfo(final String tag, final String message,
			final Throwable cause) {
		ensureNotNull(tag, "The tag may not be null");
		ensureNotEmpty(tag, "The tag may not be empty");
		ensureNotNull(message, "The message may not be null");
		ensureNotEmpty(message, "The message may not be empty");
		ensureNotNull(cause, "The cause may not be null");

		if (getLogLevel().getRank() >= LogLevel.INFO.getRank()) {
			Log.i(tag, message, cause);
		}
	}

	/**
	 * Logs a specific message on the log level WARN.
	 * 
	 * @param tag
	 *            The tag, which identifies the source of the log message, as a
	 *            {@link String}. The tag may neither be null, nor empty
	 * @param message
	 *            The message, which should be logged, as a {@link String}. The
	 *            message may neither be null, nor empty
	 */
	public final void logWarn(final String tag, final String message) {
		ensureNotNull(tag, "The tag may not be null");
		ensureNotEmpty(tag, "The tag may not be empty");
		ensureNotNull(message, "The message may not be null");
		ensureNotEmpty(message, "The message may not be empty");

		if (getLogLevel().getRank() >= LogLevel.WARN.getRank()) {
			Log.w(tag, message);
		}
	}

	/**
	 * Logs a specific message and exception on the log level WARN.
	 * 
	 * @param tag
	 *            The tag, which identifies the source of the log message, as a
	 *            {@link String}. The tag may neither be null, nor empty
	 * @param message
	 *            The message, which should be logged, as a {@link String}. The
	 *            message may neither be null, nor empty
	 * @param cause
	 *            The exception, which caused the log message, as an instance of
	 *            the class {@link Throwable}. The cause may not be null
	 */
	public final void logWarn(final String tag, final String message,
			final Throwable cause) {
		ensureNotNull(tag, "The tag may not be null");
		ensureNotEmpty(tag, "The tag may not be empty");
		ensureNotNull(message, "The message may not be null");
		ensureNotEmpty(message, "The message may not be empty");
		ensureNotNull(cause, "The cause may not be null");

		if (getLogLevel().getRank() >= LogLevel.WARN.getRank()) {
			Log.w(tag, message, cause);
		}
	}

	/**
	 * Logs a specific message on the log level ERROR.
	 * 
	 * @param tag
	 *            The tag, which identifies the source of the log message, as a
	 *            {@link String}. The tag may neither be null, nor empty
	 * @param message
	 *            The message, which should be logged, as a {@link String}. The
	 *            message may neither be null, nor empty
	 */
	public final void logError(final String tag, final String message) {
		ensureNotNull(tag, "The tag may not be null");
		ensureNotEmpty(tag, "The tag may not be empty");
		ensureNotNull(message, "The message may not be null");
		ensureNotEmpty(message, "The message may not be empty");

		if (getLogLevel().getRank() >= LogLevel.ERROR.getRank()) {
			Log.e(tag, message);
		}
	}

	/**
	 * Logs a specific message and exception on the log level ERROR.
	 * 
	 * @param tag
	 *            The tag, which identifies the source of the log message, as a
	 *            {@link String}. The tag may neither be null, nor empty
	 * @param message
	 *            The message, which should be logged, as a {@link String}. The
	 *            message may neither be null, nor empty
	 * @param cause
	 *            The exception, which caused the log message, as an instance of
	 *            the class {@link Throwable}. The cause may not be null
	 */
	public final void logError(final String tag, final String message,
			final Throwable cause) {
		ensureNotNull(tag, "The tag may not be null");
		ensureNotEmpty(tag, "The tag may not be empty");
		ensureNotNull(message, "The message may not be null");
		ensureNotEmpty(message, "The message may not be empty");
		ensureNotNull(cause, "The cause may not be null");

		if (getLogLevel().getRank() >= LogLevel.ERROR.getRank()) {
			Log.e(tag, message, cause);
		}
	}

}