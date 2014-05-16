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
package de.mrapp.android.adapter;

import java.util.Comparator;
import java.util.List;

import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.ListSortingListener;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list
 * of arbitrary items, must implement. Such an adapter is meant to provide the
 * underlying data for visualization using a {@link ListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface ListAdapter<DataType> extends Adapter, List<DataType> {

	/**
	 * Returns a list, which contains the adapter's items.
	 * 
	 * @return A list, which contains the adapter's items, as an instance of the
	 *         type {@link List} or an empty list, if the adapter does not
	 *         contain any items
	 */
	List<DataType> getItems();

	/**
	 * Sorts the items of the adapter in an ascending order.
	 */
	void sort();

	/**
	 * Sorts the items of the adapter in a specific order.
	 * 
	 * @param order
	 *            The order, which should be used to sort the items, as a value
	 *            of the enum {@link Order}. The order may either be
	 *            <code>ASCENDING</code> order <code>DESCENDING</code>
	 */
	void sort(Order order);

	/**
	 * Sorts the items of the adapter by using a comparator in an ascending
	 * order.
	 * 
	 * @param comparator
	 *            The comparable, which should be used to sort the items, as an
	 *            instance of the type {@link Comparator}. The comparator may
	 *            not be null
	 */
	void sort(Comparator<DataType> comparator);

	/**
	 * Sorts the entries of the adapter by using a comparator in a specific
	 * order.
	 * 
	 * @param comparator
	 *            The comparable, which should be used to sort the entries, as
	 *            an instance of the type {@link Comparator}. The comparator may
	 *            not be null
	 * @param order
	 *            The order, which should be used to sort the entries, as a
	 *            value of the enum {@link Order}. The order may either be
	 *            <code>ASCENDING</code> order <code>DESCENDING</code>
	 */
	void sort(Order order, Comparator<DataType> comparator);

	/**
	 * Adds a new listener, which should be notified when the adapter's
	 * underlying data has been modified.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            class {@link ListAdapterListener}. The listener may not be
	 *            null
	 */
	void addAdapterListener(ListAdapterListener<DataType> listener);

	/**
	 * Removes a specific listener, which should not be notified when the
	 * adapter's underlying data has been modified, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            class {@link ListAdapterListener}. The listener may not be
	 *            null
	 */
	void removeAdapterListener(ListAdapterListener<DataType> listener);

	/**
	 * Adds a new listener, which should be notified when the adapter's
	 * underlying data has been sorted.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            class {@link ListSortingListener}. The listener may not be
	 *            null
	 */
	void addSortingListner(ListSortingListener<DataType> listener);

	/**
	 * Removes a specific listener, which should not be notified when the
	 * adapter's underlying data has been modified, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            class {@link ListSortingListener}. The listener may not be
	 *            null
	 */
	void removeSortingListener(ListSortingListener<DataType> listener);

	@Override
	ListAdapter<DataType> clone() throws CloneNotSupportedException;

}