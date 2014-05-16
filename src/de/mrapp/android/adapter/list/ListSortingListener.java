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
package de.mrapp.android.adapter.list;

import java.util.List;

import de.mrapp.android.adapter.Order;

/**
 * Defines the interface, all listeners, which should be notified when the
 * underlying data of a {@link AbstractListAdapter} has been sorted, must
 * implement.
 * 
 * @param <DataType>
 *            The type of the observed adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface ListSortingListener<DataType> {

	/**
	 * The method, which is invoked, when the adapter's underlying data has been
	 * sorted.
	 * 
	 * @param sortedList
	 *            A list, which contains the adapter's sorted items, as an
	 *            instance of the type {@link List}. The list may not be null
	 * @param order
	 *            The order, which has been used to sort the adapter's items, as
	 *            a value of the enum {@link Order}. The order may either be
	 *            <code>ASCENDING</code> or <code>DESCENDING</code>
	 */
	void onSorted(List<DataType> sortedList, Order order);

}