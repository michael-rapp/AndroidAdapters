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

import java.lang.reflect.Constructor;

/**
 * An utility class, which offers static methods to ensure, that attributes
 * satisfy specific conditions by throwing exceptions, if the conditions are not
 * satisfied.
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public final class Condition {

	/**
	 * An utility class, which offers static methods to ensure, that attributes
	 * satisfy specific conditions by throwing exceptions, if the conditions are
	 * not satisfied.
	 */
	private Condition() {

	}

	/**
	 * Ensures, that a reference is not null. Otherwise a
	 * {@link NullPointerException} with a specific message will be thrown.
	 * 
	 * @param reference
	 *            The reference, which should be checked, as an instance of the
	 *            class {@link Object}
	 * @param exceptionMessage
	 *            The message of the {@link NullPointerException}, which is
	 *            thrown, if the given reference is null, as a {@link String}
	 */
	public static void ensureNotNull(final Object reference,
			final String exceptionMessage) {
		if (reference == null) {
			throw new NullPointerException(exceptionMessage);
		}
	}

	/**
	 * Ensures, that a string is not empty. Otherwise an
	 * {@link IllegalArgumentException} with a specific message will be thrown.
	 * 
	 * @param string
	 *            The string, which should be checked, as a {@link String}
	 * @param exceptionMessage
	 *            The message of the {@link IllegalArgumentException}, which is
	 *            thrown, if the given string is empty, as a {@link String}
	 */
	public static void ensureNotEmpty(final String string,
			final String exceptionMessage) {
		if (string.length() == 0) {
			throw new IllegalArgumentException(exceptionMessage);
		}
	}

	/**
	 * Ensures, that an {@link Integer} value is at least a specific value.
	 * Otherwise an {@link IndexOutOfBoundsException} with a specific message
	 * will be thrown.
	 * 
	 * @param value
	 *            The value, which should be checked, as an {@link Integer}
	 *            value
	 * @param referenceValue
	 *            The value, the given value must be at least, as an
	 *            {@link Integer} value
	 * @param exceptionMessage
	 *            The message of the {@link IndexOutOfBoundsException}, which is
	 *            thrown, if the given value is less than the reference value,
	 *            as a {@link String}
	 */
	public static void ensureAtLeast(final int value, final int referenceValue,
			final String exceptionMessage) {
		if (value < referenceValue) {
			throw new IndexOutOfBoundsException(exceptionMessage);
		}
	}

	/**
	 * Ensures, that an {@link Integer} value is at least a specific value.
	 * Otherwise a specific exception with a specific message will be thrown. If
	 * an error occurs while instantiating the given exception class, an
	 * {@link IndexOutOfBoundsException} will be thrown instead.
	 * 
	 * @param value
	 *            The value, which should be checked, as an {@link Integer}
	 *            value
	 * @param referenceValue
	 *            The value, the given value must be at least, as an
	 *            {@link Integer} value
	 * @param exceptionMessage
	 *            The message of the {@link IndexOutOfBoundsException}, which is
	 *            thrown, if the given value is less than the reference value,
	 *            as a {@link String}
	 * @param exceptionClass
	 *            The class of the exception, which should be thrown, as an
	 *            instance of the class {@link Class}
	 */
	public static void ensureAtLeast(final int value, final int referenceValue,
			final String exceptionMessage,
			final Class<? extends RuntimeException> exceptionClass) {
		if (value < referenceValue) {
			RuntimeException exception;

			try {
				Constructor<? extends RuntimeException> constructor = exceptionClass
						.getConstructor(String.class);
				exception = constructor.newInstance(exceptionMessage);
			} catch (Exception e) {
				throw new IndexOutOfBoundsException(exceptionMessage);
			}

			throw exception;
		}
	}

	/**
	 * Ensures, that an {@link Integer} value is at maximum a specific value.
	 * Otherwise an {@link IndexOutOfBoundsException} with a specific message
	 * will be thrown.
	 * 
	 * @param value
	 *            The value, which should be checked, as an {@link Integer}
	 *            value
	 * @param referenceValue
	 *            The value, the given value must be at maximum, as an
	 *            {@link Integer} value
	 * @param exceptionMessage
	 *            The message of the {@link IndexOutOfBoundsException}, which is
	 *            thrown, if the given value is greater than the reference
	 *            value, as a {@link String}
	 */
	public static void ensureAtMaximum(final int value,
			final int referenceValue, final String exceptionMessage) {
		if (value > referenceValue) {
			throw new IndexOutOfBoundsException(exceptionMessage);
		}
	}

	/**
	 * Ensures, that an {@link Integer} value is at maximum a specific value.
	 * Otherwise a specific exception with a specific message will be thrown. If
	 * an error occurs, while instantiating the given exception class, an
	 * {@link IndexOutOfBoundsException} will be thrown instead.
	 * 
	 * @param value
	 *            The value, which should be checked, as an {@link Integer}
	 *            value
	 * @param referenceValue
	 *            The value, the given value must be at maximum, as an
	 *            {@link Integer} value
	 * @param exceptionMessage
	 *            The message of the {@link IndexOutOfBoundsException}, which is
	 *            thrown, if the given value is greater than the reference
	 *            value, as a {@link String}
	 * @param exceptionClass
	 *            The class of the exception, which should be thrown, as an
	 *            instance of the class {@link Class}
	 */
	public static void ensureAtMaximum(final int value,
			final int referenceValue, final String exceptionMessage,
			final Class<? extends RuntimeException> exceptionClass) {
		if (value > referenceValue) {
			RuntimeException exception;

			try {
				Constructor<? extends RuntimeException> constructor = exceptionClass
						.getConstructor(String.class);
				exception = constructor.newInstance(exceptionMessage);
			} catch (Exception e) {
				throw new IndexOutOfBoundsException(exceptionMessage);
			}

			throw exception;
		}

	}

}