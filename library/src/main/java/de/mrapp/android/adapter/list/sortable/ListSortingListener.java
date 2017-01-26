/*
 * Copyright 2014 - 2017 Michael Rapp
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
import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.Comparator;

import de.mrapp.android.adapter.list.ListAdapter;
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
    void onSorted(@NonNull ListAdapter<DataType> adapter, @NonNull Collection<DataType> sortedItems,
                  @NonNull Order order, @Nullable Comparator<DataType> comparator);

}