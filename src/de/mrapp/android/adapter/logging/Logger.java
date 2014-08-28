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

import static de.mrapp.android.adapter.util.Condition.ensureNotEmpty;
import static de.mrapp.android.adapter.util.Condition.ensureNotNull;
import static de.mrapp.android.adapter.util.ClassUtil.getTruncatedName;
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
		this.logLevel = logLevel;
	}

	/**
	 * Logs a specific message on the log level VERBOSE.
	 * 
	 * @param tag
	 *            The tag, which identifies the source of the log message, as an
	 *            instance of the class {@link Class}. The tag may not be null
	 * @param message
	 *            The message, which should be logged, as a {@link String}. The
	 *            message may neither be null, nor empty
	 */
	public final void logVerbose(final Class<?> tag, final String message) {
		ensureNotNull(tag, "The tag may not be null");
		ensureNotNull(message, "The message may not be null");
		ensureNotEmpty(message, "The message may not be empty");

		if (LogLevel.VERBOSE.getRank() >= getLogLevel().getRank()) {
			Log.v(getTruncatedName(tag), message);
		}
	}

	/**
	 * Logs a specific message and exception on the log level VERBOSE.
	 * 
	 * @param tag
	 *            The tag, which identifies the source of the log message, as an
	 *            instance of the class {@link Class}. The tag may not be null
	 * @param message
	 *            The message, which should be logged, as a {@link String}. The
	 *            message may neither be null, nor empty
	 * @param cause
	 *            The exception, which caused the log message, as an instance of
	 *            the class {@link Throwable}. The cause may not be null
	 */
	public final void logVerbose(final Class<?> tag, final String message,
			final Throwable cause) {
		ensureNotNull(tag, "The tag may not be null");
		ensureNotNull(message, "The message may not be null");
		ensureNotEmpty(message, "The message may not be empty");
		ensureNotNull(cause, "The cause may not be null");

		if (LogLevel.VERBOSE.getRank() >= getLogLevel().getRank()) {
			Log.v(getTruncatedName(tag), message, cause);
		}
	}

	/**
	 * Logs a specific message on the log level DEBUG.
	 * 
	 * @param tag
	 *            The tag, which identifies the source of the log message, as an
	 *            instance of the class {@link Class}. The tag may not be null
	 * @param message
	 *            The message, which should be logged, as a {@link String}. The
	 *            message may neither be null, nor empty
	 */
	public final void logDebug(final Class<?> tag, final String message) {
		ensureNotNull(tag, "The tag may not be null");
		ensureNotNull(message, "The message may not be null");
		ensureNotEmpty(message, "The message may not be empty");

		if (LogLevel.DEBUG.getRank() >= getLogLevel().getRank()) {
			Log.d(getTruncatedName(tag), message);
		}
	}

	/**
	 * Logs a specific message and exception on the log level DEBUG.
	 * 
	 * @param tag
	 *            The tag, which identifies the source of the log message, as an
	 *            instance of the class {@link Class}. The tag may not be null
	 * @param message
	 *            The message, which should be logged, as a {@link String}. The
	 *            message may neither be null, nor empty
	 * @param cause
	 *            The exception, which caused the log message, as an instance of
	 *            the class {@link Throwable}. The cause may not be null
	 */
	public final void logDebug(final Class<?> tag, final String message,
			final Throwable cause) {
		ensureNotNull(tag, "The tag may not be null");
		ensureNotNull(message, "The message may not be null");
		ensureNotEmpty(message, "The message may not be empty");
		ensureNotNull(cause, "The cause may not be null");

		if (LogLevel.DEBUG.getRank() >= getLogLevel().getRank()) {
			Log.d(getTruncatedName(tag), message, cause);
		}
	}

	/**
	 * Logs a specific message on the log level INFO.
	 * 
	 * @param tag
	 *            The tag, which identifies the source of the log message, as an
	 *            instance of the class {@link Class}. The tag may not be null
	 * @param message
	 *            The message, which should be logged, as a {@link String}. The
	 *            message may neither be null, nor empty
	 */
	public final void logInfo(final Class<?> tag, final String message) {
		ensureNotNull(tag, "The tag may not be null");
		ensureNotNull(message, "The message may not be null");
		ensureNotEmpty(message, "The message may not be empty");

		if (LogLevel.INFO.getRank() >= getLogLevel().getRank()) {
			Log.i(getTruncatedName(tag), message);
		}
	}

	/**
	 * Logs a specific message and exception on the log level INFO.
	 * 
	 * @param tag
	 *            The tag, which identifies the source of the log message, as an
	 *            instance of the class {@link Class}. The tag may not be null
	 * @param message
	 *            The message, which should be logged, as a {@link String}. The
	 *            message may neither be null, nor empty
	 * @param cause
	 *            The exception, which caused the log message, as an instance of
	 *            the class {@link Throwable}. The cause may not be null
	 */
	public final void logInfo(final Class<?> tag, final String message,
			final Throwable cause) {
		ensureNotNull(tag, "The tag may not be null");
		ensureNotNull(message, "The message may not be null");
		ensureNotEmpty(message, "The message may not be empty");
		ensureNotNull(cause, "The cause may not be null");

		if (LogLevel.INFO.getRank() >= getLogLevel().getRank()) {
			Log.i(getTruncatedName(tag), message, cause);
		}
	}

	/**
	 * Logs a specific message on the log level WARN.
	 * 
	 * @param tag
	 *            The tag, which identifies the source of the log message, as an
	 *            instance of the class {@link Class}. The tag may not be null
	 * @param message
	 *            The message, which should be logged, as a {@link String}. The
	 *            message may neither be null, nor empty
	 */
	public final void logWarn(final Class<?> tag, final String message) {
		ensureNotNull(tag, "The tag may not be null");
		ensureNotNull(message, "The message may not be null");
		ensureNotEmpty(message, "The message may not be empty");

		if (LogLevel.WARN.getRank() >= getLogLevel().getRank()) {
			Log.w(getTruncatedName(tag), message);
		}
	}

	/**
	 * Logs a specific message and exception on the log level WARN.
	 * 
	 * @param tag
	 *            The tag, which identifies the source of the log message, as an
	 *            instance of the class {@link Class}. The tag may not be null
	 * @param message
	 *            The message, which should be logged, as a {@link String}. The
	 *            message may neither be null, nor empty
	 * @param cause
	 *            The exception, which caused the log message, as an instance of
	 *            the class {@link Throwable}. The cause may not be null
	 */
	public final void logWarn(final Class<?> tag, final String message,
			final Throwable cause) {
		ensureNotNull(tag, "The tag may not be null");
		ensureNotNull(message, "The message may not be null");
		ensureNotEmpty(message, "The message may not be empty");
		ensureNotNull(cause, "The cause may not be null");

		if (LogLevel.WARN.getRank() >= getLogLevel().getRank()) {
			Log.w(getTruncatedName(tag), message, cause);
		}
	}

	/**
	 * Logs a specific message on the log level ERROR.
	 * 
	 * @param tag
	 *            The tag, which identifies the source of the log message, as an
	 *            instance of the class {@link Class}. The tag may not be null
	 * @param message
	 *            The message, which should be logged, as a {@link String}. The
	 *            message may neither be null, nor empty
	 */
	public final void logError(final Class<?> tag, final String message) {
		ensureNotNull(tag, "The tag may not be null");
		ensureNotNull(message, "The message may not be null");
		ensureNotEmpty(message, "The message may not be empty");

		if (LogLevel.ERROR.getRank() >= getLogLevel().getRank()) {
			Log.e(getTruncatedName(tag), message);
		}
	}

	/**
	 * Logs a specific message and exception on the log level ERROR.
	 * 
	 * @param tag
	 *            The tag, which identifies the source of the log message, as an
	 *            instance of the class {@link Class}. The tag may not be null
	 * @param message
	 *            The message, which should be logged, as a {@link String}. The
	 *            message may neither be null, nor empty
	 * @param cause
	 *            The exception, which caused the log message, as an instance of
	 *            the class {@link Throwable}. The cause may not be null
	 */
	public final void logError(final Class<?> tag, final String message,
			final Throwable cause) {
		ensureNotNull(tag, "The tag may not be null");
		ensureNotNull(message, "The message may not be null");
		ensureNotEmpty(message, "The message may not be empty");
		ensureNotNull(cause, "The cause may not be null");

		if (LogLevel.ERROR.getRank() >= getLogLevel().getRank()) {
			Log.e(getTruncatedName(tag), message, cause);
		}
	}

}