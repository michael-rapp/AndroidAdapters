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

import java.util.Comparator;
import java.util.List;

import android.util.Pair;

/**
 * Defines the interface, all sorting algorithms, which allow to sort list
 * entries, must implement.
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface SortingAlgorithm {

	/**
	 * Sorts and returns the entries of a specific list. If the entries of the
	 * list do not implement the interface {@link Comparable}, a
	 * {@link SortingNotSupportedException} will be thrown.
	 * 
	 * @param <T>
	 *            The type of the list entries
	 * @param list
	 *            The list, whose entries should be sorted, as an instance of
	 *            the type {@link List}. The list may not be null
	 * @param selections
	 *            A list, which contains the selection states, which correspond
	 *            to the items of the list, which should be sorted, as an
	 *            instance of the type {@link List}. The list may not be null
	 * @param order
	 *            The order, which should be used to sort the entries of the
	 *            given list, as a value of the enum {@link Order}. The order
	 *            may be <code>ASCENDING</code> or <code>DESCENDING</code>.
	 * @return A list, which contains the sorted entries, as an instance of the
	 *         type {@link List}. The list may not be null
	 */
	<T> Pair<List<T>, List<Boolean>> sort(List<T> list,
			List<Boolean> selections, Order order);

	/**
	 * Sorts and returns the entries of a specific list by using a comparator.
	 * 
	 * @param <T>
	 *            The type of the list entries
	 * @param list
	 *            The list, whose entries should be sorted, as an instance of
	 *            the type {@link List}. The list may not be null
	 * @param selections
	 *            A list, which contains the selection states, which correspond
	 *            to the items of the list, which should be sorted, as an
	 *            instance of the type {@link List}. The list may not be null
	 * @param order
	 *            The order, which should be used to sort the entries of the
	 *            given list, as a value of the enum {@link Order}. The order
	 *            may be <code>ASCENDING</code> or <code>DESCENDING</code>.
	 * @param comparator
	 *            The comparator, which should be used to compare the entries of
	 *            the given list, as an instance of the type {@link Comparator}.
	 *            The comparator may not be null
	 * @return A list, which contains the sorted entries, as an instance of the
	 *         type {@link List}. The list may not be null
	 */
	<T> Pair<List<T>, List<Boolean>> sort(List<T> list,
			List<Boolean> selections, Order order, Comparator<T> comparator);

}