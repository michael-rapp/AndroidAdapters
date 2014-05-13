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
package de.mrapp.android.adapter.sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import de.mrapp.android.adapter.SortingNotSupportedException;

/**
 * An abstract base class for all sorting algorithms.
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public abstract class AbstractSortingAlgorithm implements SortingAlgorithm {

	/**
	 * Compares two objects to determine their relative ascending order.
	 * 
	 * @param <T>
	 *            The type of the objects, which should be compared
	 * @param object1
	 *            The first object, which should be compared, as an instance of
	 *            the generic type T. The object may not be null
	 * @param object2
	 *            The second object, which should be compared, as an instance of
	 *            the generic type T. The object may not be null
	 * @param comparator
	 *            The comparator, which should be used to compare the given
	 *            objects, as an instance of the type {@link Comparator} or
	 *            null, if no comparator should be used. In such case the list
	 *            entries must implement the interface {@link Comparable},
	 *            otherwise a {@link SortingNotSupportedException} will be
	 *            thrown
	 * @return A negative integer, if the first object is less than the second
	 *         one, a positive integer, if the first object is greater than the
	 *         second one or 0 if both object have the same order
	 */
	private <T> int compareAscending(final T object1, final T object2,
			final Comparator<T> comparator) {
		if (comparator == null) {
			return compare(object1, object2);
		} else {
			return comparator.compare(object1, object2);
		}
	}

	/**
	 * Compares two objects to determine their relative ascending order.
	 * 
	 * @param <T>
	 *            The type of the objects, which should be compared
	 * @param object1
	 *            The first object, which should be compared, as an instance of
	 *            the generic type T. The object may not be null
	 * @param object2
	 *            The second object, which should be compared, as an instance of
	 *            the generic type T. The object may not be null
	 * @param comparator
	 *            The comparator, which should be used to compare the given
	 *            objects, as an instance of the type {@link Comparator} or
	 *            null, if no comparator should be used. In such case the list
	 *            entries must implement the interface {@link Comparable},
	 *            otherwise a {@link SortingNotSupportedException} will be
	 *            thrown
	 * @return A negative integer, if the first object is greater than the
	 *         second one, a positive integer, if the first object is less than
	 *         the second one or 0 if both object have the same order
	 */
	private <T> int compareDescending(final T object1, final T object2,
			final Comparator<T> comparator) {
		if (comparator == null) {
			return compare(object2, object1);
		} else {
			return comparator.compare(object2, object1);
		}
	}

	/**
	 * Compares two objects to determine their relative order. Both objects must
	 * implement the interface {@link Comparable}, otherwise a
	 * {@link SortingNotSupportedException} will be thrown.
	 * 
	 * @param <T>
	 *            The type of the objects, which should be compared
	 * @param object1
	 *            The first object, which should be compared, as an instance of
	 *            the generic type T. The object may not be null
	 * @param object2
	 *            The second object, which should be compared, as an instance of
	 *            the generic type T. The object may not be null
	 * @return A negative integer, if the first object is less than the second
	 *         one, a positive integer, if the first object is greater than the
	 *         second one or 0 if both object have the same order
	 */
	@SuppressWarnings("unchecked")
	private <T> int compare(final T object1, final T object2) {
		try {
			Comparable<T> comparable = (Comparable<T>) object1;
			return comparable.compareTo(object2);
		} catch (ClassCastException e) {
			throw new SortingNotSupportedException();
		}
	}

	/**
	 * Compares two objects to determine their relative order.
	 * 
	 * @param <T>
	 *            The type of the objects, which should be compared
	 * @param object1
	 *            The first object, which should be compared, as an instance of
	 *            the generic type T. The object may not be null
	 * @param object2
	 *            The second object, which should be compared, as an instance of
	 *            the generic type T. The object may not be null
	 * @param order
	 *            The order, which should be used to compare the given objects,
	 *            as a value of the enum {@link Order}. The value may either be
	 *            <code>ASCENDING</code> or <code>DESCENDING</code>
	 * @param comparator
	 *            The comparator, which should be used to compare the given
	 *            objects, as an instance of the type {@link Comparator} or
	 *            null, if no comparator should be used. In such case the list
	 *            entries must implement the interface {@link Comparable},
	 *            otherwise a {@link SortingNotSupportedException} will be
	 *            thrown
	 * @return A negative integer, if the first object is less than the second
	 *         one, a positive integer, if the first object is greater than the
	 *         second one or 0 if both object have the same order
	 */
	protected final <T> int compare(final T object1, final T object2,
			final Order order, final Comparator<T> comparator) {
		if (order == Order.ASCENDING) {
			return compareAscending(object1, object2, comparator);
		} else {
			return compareDescending(object1, object2, comparator);
		}
	}

	/**
	 * Instantiates and returns a list, which is of the same type as a given
	 * list. If an error occurs while instantiating the list, an
	 * {@link ArrayList} will be returned.
	 * 
	 * @param <T>
	 *            The type of the list entries
	 * @param list
	 *            The list, which determines the type, as an instance of the
	 *            type {@link List}. The list may not be null
	 * @return The list, which has been instantiated, as an instance of the type
	 *         {@link List}
	 */
	@SuppressWarnings("unchecked")
	protected final <T> List<T> instantiateList(final List<T> list) {
		try {
			return list.getClass().newInstance();
		} catch (InstantiationException e) {
			return new ArrayList<T>();
		} catch (IllegalAccessException e) {
			return new ArrayList<T>();
		}
	}

}