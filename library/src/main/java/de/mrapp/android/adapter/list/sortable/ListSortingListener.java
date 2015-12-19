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
package de.mrapp.android.adapter.list.sortable;

import java.util.Collection;
import java.util.Comparator;

import de.mrapp.android.adapter.ListAdapter;
import de.mrapp.android.adapter.Order;

/**
 * Defines the interface, all listeners, which should be notified when the underlying data of a
 * {@link ListAdapter} have been sorted, must implement.
 *
 * @param <DataType>
 *         The type of the observed adapter's underlying data
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface ListSortingListener<DataType> {

    /**
     * The method, which is invoked, when the adapter's items have been sorted.
     *
     * @param adapter
     *         The observed adapters as an instance of the type {@link ListAdapter}. The adapter may
     *         not be null
     * @param sortedItems
     *         A collection, which contains the adapter's sorted items, as an instance of the type
     *         {@link Collection} or an empty collection, if the adapter does not contain any items
     * @param order
     *         The order, which has been used to sort the adapter's items, as a value of the enum
     *         {@link Order}. The order may either be <code>ASCENDING</code> or
     *         <code>DESCENDING</code>
     * @param comparator
     *         The comparator, which has been used to compare the single items, as an instance of
     *         the type {@link Comparator} or null, if the items' implementation of the type {@link
     *         Comparable} has been used instead
     */
    void onSorted(ListAdapter<DataType> adapter, Collection<DataType> sortedItems, Order order,
                  Comparator<DataType> comparator);

}