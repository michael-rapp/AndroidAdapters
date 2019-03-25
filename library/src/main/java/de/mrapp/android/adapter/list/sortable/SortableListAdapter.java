/*
 * Copyright 2014 - 2019 Michael Rapp
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

import android.widget.AbsListView;

import java.util.Collection;
import java.util.Comparator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
     *         {@link Comparator} or null, if the natural order should be used
     */
    void sort(@Nullable Comparator<DataType> comparator);

    /**
     * Sorts the adapter's items in a specific order, by using a comparator.
     *
     * @param comparator
     *         The comparator, which should be used to sort the items, as an instance of the type
     *         {@link Comparator} or null, if the natural order should be used
     * @param order
     *         The order, which should be used to sort the items, as a value of the enum {@link
     *         Order}. The order may either be <code>ASCENDING</code> or <code>DESCENDING</code>
     */
    void sort(@NonNull Order order, @Nullable Comparator<DataType> comparator);

    /**
     * Adds a specific item to the adapter. If the adapter's items are currently sorted, the item
     * will be added at the correct position regarding the current order. Otherwise, it will be
     * added at the end. If the adapter's underlying data does not implement the interface {@link
     * Comparable} a {@link SortingNotSupportedException} will be thrown.
     *
     * @param item
     *         The item, which should be added to the adapter, as an instance of the generic type
     *         DataType. The item may not be null
     * @return The index of the the item, which has been added to the adapter, as an {@link Integer}
     * value or -1, if the item has not been added
     */
    int addItemSorted(@NonNull DataType item);

    /**
     * Adds a specific item to the adapter. If the adapter's items are currently sorted, the item
     * will be added at the correct position regarding the current order. Otherwise, it will be
     * added at the end.
     *
     * @param item
     *         The item, which should be added to the adapter, as an instance of the generic type
     *         DataType. The item may not be null
     * @param comparator
     *         The comparator, which should be used to sort the items, as an instance of the type
     *         {@link Comparator} or null, if the natural order should be used
     * @return The index of the the item, which has been added to the adapter, as an {@link Integer}
     * value or -1, if the item has not been added
     */
    int addItemSorted(@NonNull DataType item, @Nullable Comparator<DataType> comparator);

    /**
     * Adds all items, which are contained by a specific collection, to the adapter. If the
     * adapter's items are currently sorted, the items will be added at the correct position
     * regarding the current order. Otherwise, they will be added at the end. If the adapter's
     * underlying data does not implement the interface {@link Comparable} a {@link
     * SortingNotSupportedException} will be thrown.
     *
     * @param items
     *         The collection, which contains the items, which should be added to the adapter, as an
     *         instance of the type {@link Collection} or an empty collection, if no items should be
     *         added
     * @return True, if all items have been added to the adapter, false otherwise
     */
    boolean addAllItemsSorted(@NonNull Collection<? extends DataType> items);

    /**
     * Adds all items, which are contained by a specific collection, to the adapter. If the
     * adapter's items are currently sorted, the items will be added at the correct position
     * regarding the current order. Otherwise, they will be added at the end.
     *
     * @param items
     *         The collection, which contains the items, which should be added to the adapter, as an
     *         instance of the type {@link Collection} or an empty collection, if no items should be
     *         added
     * @param comparator
     *         The comparator, which should be used to sort the items, as an instance of the type
     *         {@link Comparator} or null, if the natural order should be used
     * @return True, if all items have been added to the adapter, false otherwise
     */
    boolean addAllItemsSorted(@NonNull Collection<? extends DataType> items,
                              @Nullable Comparator<DataType> comparator);

    /**
     * Adds all items, which are contained by a specific array, to the adapter. If the adapter's
     * items are currently sorted, the items will be added at the correct position regarding the
     * current order. Otherwise, they will be added at the end. If the adapter's underlying data
     * does not implement the interface {@link Comparable} a {@link SortingNotSupportedException}
     * will be thrown.
     *
     * @param items
     *         The array, which contains the items, which should be added to the adapter, as an
     *         array of the generic type DataType or an empty array, if no items should be added
     * @return True, if all items have been added to the adapter, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean addAllItemsSorted(@NonNull DataType... items);

    /**
     * Adds all items, which are contained by a specific array, to the adapter. If the adapter's
     * items are currently sorted, the items will be added at the correct position regarding the
     * current order. Otherwise, they will be added at the end.
     *
     * @param comparator
     *         The comparator, which should be used to sort the items, as an instance of the type
     *         {@link Comparator} or null, if the natural order should be used
     * @param items
     *         The array, which contains the items, which should be added to the adapter, as an
     *         array of the generic type DataType or an empty array, if no items should be added
     * @return True, if all items have been added to the adapter, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean addAllItemsSorted(@Nullable Comparator<DataType> comparator,
                              @NonNull DataType... items);

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