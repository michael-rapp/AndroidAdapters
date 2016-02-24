/*
 * Copyright 2014 - 2016 Michael Rapp
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package de.mrapp.android.adapter.list.sortable;

import android.support.annotation.NonNull;
import android.widget.AbsListView;

import java.util.Comparator;

import de.mrapp.android.adapter.Order;
import de.mrapp.android.adapter.SortingNotSupportedException;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a sortable list of
 * arbitrary items, must implement. Such an adapter's purpose is to provide the underlying data for
 * visualization using a {@link AbsListView} widget.
 *
 * @param <DataType>
 *         The type of the adapter's underlying data
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface SortableListAdapter<DataType> {

    /**
     * Sorts the adapter's items in an ascending order. If the adapter's underlying data does not
     * implement the interface {@link Comparable} a {@link SortingNotSupportedException} will be
     * thrown.
     */
    void sort();

    /**
     * Sorts the adapter's items in a specific order. If the adapter's underlying data does not
     * implement the interface {@link Comparable} a {@link SortingNotSupportedException} will be
     * thrown.
     *
     * @param order
     *         The order, which should be used to sort the items, as a value of the enum {@link
     *         Order}. The order may either be <code>ASCENDING</code> or <code>DESCENDING</code>
     */
    void sort(@NonNull Order order);

    /**
     * Sorts the adapter's items in an ascending order, by using a comparator.
     *
     * @param comparator
     *         The comparator, which should be used to sort the items, as an instance of the type
     *         {@link Comparator}. The comparator may not be null
     */
    void sort(@NonNull Comparator<DataType> comparator);

    /**
     * Sorts the adapter's items in a specific order, by using a comparator.
     *
     * @param comparator
     *         The comparator, which should be used to sort the items, as an instance of the type
     *         {@link Comparator}. The comparator may not be null
     * @param order
     *         The order, which should be used to sort the items, as a value of the enum {@link
     *         Order}. The order may either be <code>ASCENDING</code> or <code>DESCENDING</code>
     */
    void sort(@NonNull Order order, @NonNull Comparator<DataType> comparator);

    /**
     * Returns the current order of the adapter's items.
     *
     * @return The current order of the adapter's items as a value of the enum {@link Order} or
     * null, if the adapter's items have not been sorted yet or if the adapter's underlying data has
     * been changed since it has been sorted the last time. If not null, the order may either be
     * <code>ASCENDING</code> or <code>DESCENDING</code>
     */
    Order getOrder();

    /**
     * Adds a new listener, which should be notified, when the adapter's underlying data has been
     * sorted.
     *
     * @param listener
     *         The listener, which should be added, as an instance of the class {@link
     *         ListSortingListener}. The listener may not be null
     */
    void addSortingListener(@NonNull ListSortingListener<DataType> listener);

    /**
     * Removes a specific listener, which should not be notified, when the adapter's underlying data
     * has been sorted, anymore.
     *
     * @param listener
     *         The listener, which should be removed, as an instance of the class {@link
     *         ListSortingListener}. The listener may not be null
     */
    void removeSortingListener(@NonNull ListSortingListener<DataType> listener);

}