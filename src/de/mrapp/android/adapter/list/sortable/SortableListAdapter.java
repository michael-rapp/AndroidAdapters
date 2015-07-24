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
package de.mrapp.android.adapter.list.sortable;

import java.util.Comparator;

import android.widget.AbsListView;
import de.mrapp.android.adapter.Order;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a
 * sortable list of arbitrary items, must implement. Such an adapter's purpose
 * is to provide the underlying data for visualization using a
 * {@link AbsListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface SortableListAdapter<DataType> {

	/**
	 * Sorts the adapter's items in an ascending order. If the adapter's
	 * underlying data does not implement the interface {@link Comparable} a
	 * {@link SortingNotSupportedException} will be thrown.
	 */
	void sort();

	/**
	 * Sorts the adapter's items in a specific order. If the adapter's
	 * underlying data does not implement the interface {@link Comparable} a
	 * {@link SortingNotSupportedException} will be thrown.
	 * 
	 * @param order
	 *            The order, which should be used to sort the items, as a value
	 *            of the enum {@link Order}. The order may either be
	 *            <code>ASCENDING</code> or <code>DESCENDING</code>
	 */
	void sort(Order order);

	/**
	 * Sorts the adapter's items in an ascending order, by using a comparator.
	 * 
	 * @param comparator
	 *            The comparator, which should be used to sort the items, as an
	 *            instance of the type {@link Comparator}. The comparator may
	 *            not be null
	 */
	void sort(Comparator<DataType> comparator);

	/**
	 * Sorts the adapter's items in a specific order, by using a comparator.
	 * 
	 * @param comparator
	 *            The comparator, which should be used to sort the items, as an
	 *            instance of the type {@link Comparator}. The comparator may
	 *            not be null
	 * @param order
	 *            The order, which should be used to sort the items, as a value
	 *            of the enum {@link Order}. The order may either be
	 *            <code>ASCENDING</code> or <code>DESCENDING</code>
	 */
	void sort(Order order, Comparator<DataType> comparator);

	/**
	 * Adds a new listener, which should be notified, when the adapter's
	 * underlying data has been sorted.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            class {@link ListSortingListener}. The listener may not be
	 *            null
	 */
	void addSortingListner(ListSortingListener<DataType> listener);

	/**
	 * Removes a specific listener, which should not be notified, when the
	 * adapter's underlying data has been sorted, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            class {@link ListSortingListener}. The listener may not be
	 *            null
	 */
	void removeSortingListener(ListSortingListener<DataType> listener);

}