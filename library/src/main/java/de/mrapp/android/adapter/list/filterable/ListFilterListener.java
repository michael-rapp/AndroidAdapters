/*
 * Copyright 2014 - 2018 Michael Rapp
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
package de.mrapp.android.adapter.list.filterable;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.Filterable;
import de.mrapp.android.adapter.list.ListAdapter;

/**
 * Defines the interface, all listeners, which should be notified when the underlying data of a
 * {@link ListAdapter} has been filtered, must implement.
 *
 * @param <DataType>
 *         The type of the observed adapter's underlying data
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface ListFilterListener<DataType> {

    /**
     * The method, which is invoked, when the adapter's items have been filtered by using a query.
     *
     * @param adapter
     *         The observed adapters as an instance of the type {@link ListAdapter}. The adapter may
     *         not be null
     * @param query
     *         The query, which has been used, as a {@link String}. The query may not be null
     * @param flags
     *         The flags, which have been used, as an {@link Integer} value, or 0, if no flags have
     *         been used
     * @param filter
     *         The filter, which has been used to apply the query on the single items, as an
     *         instance of the type {@link Filter} or null, if the items' implementations of the
     *         interface {@link Filterable} have been used instead
     * @param filteredItems
     *         A collection, which contains the items, which have been filtered, as an instance of
     *         the type {@link List} or an empty collection, if no items have been filtered
     * @param unfilteredItems
     *         A collection, which contains the adapter's unfiltered items, as an instance of the
     *         type {@link List} or an empty collection, if the adapter does not contain any items
     */
    void onApplyFilter(@NonNull ListAdapter<DataType> adapter, @NonNull String query, int flags,
                       @Nullable Filter<DataType> filter, @NonNull List<DataType> filteredItems,
                       @NonNull List<DataType> unfilteredItems);

    /**
     * The method, which is invoked, when a filter has been reseted.
     *
     * @param adapter
     *         The observed adapters as an instance of the type {@link ListAdapter}. The adapter may
     *         not be null
     * @param query
     *         The query used by the filter, which has been reseted, as a {@link String}. The query
     *         may not be null
     * @param flags
     *         The flags used by the filter, which has been reseted, as an {@link Integer} value or
     *         0, if no flags have been used by the filter
     * @param unfilteredItems
     *         A collection, which contains the adapter's unfiltered items, as an instance of the
     *         type {@link List} or an empty collection, if the adapter does not contain any items
     */
    void onResetFilter(@NonNull ListAdapter<DataType> adapter, @NonNull String query, int flags,
                       @NonNull List<DataType> unfilteredItems);

}