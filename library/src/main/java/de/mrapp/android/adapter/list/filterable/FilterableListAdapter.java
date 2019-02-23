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
package de.mrapp.android.adapter.list.filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.AbsListView;

import java.util.List;
import java.util.Set;

import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.FilterQuery;
import de.mrapp.android.adapter.Filterable;
import de.mrapp.android.adapter.FilteringNotSupportedException;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a filterable list of
 * arbitrary items, must implement. Such an adapter's purpose is to provide the underlying data for
 * visualization using a {@link AbsListView} widget.
 *
 * @param <DataType>
 *         The type of the adapter's underlying data
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface FilterableListAdapter<DataType> {

    /**
     * Filters the adapter's items by using a specific query, if no filter using the same query has
     * been applied yet. If the adapter's underlying data does not implement the interface {@link
     * Filterable} a {@link FilteringNotSupportedException} will be thrown. This method can be
     * called multiple times without resetting the filtering, which causes the filtered item to be
     * filtered once more.
     *
     * @param query
     *         The query, which should be used to filter the items, as a {@link String}. The query
     *         may not be null
     * @param flags
     *         The flags, which should be used to filter the item, as an {@link Integer} value, or
     *         0, if no flags should be used
     * @return A list, which contains the items, which have been filtered, as an instance of the
     * type {@link List}, or null, if the filter has not been applied
     */
    @Nullable
    List<DataType> applyFilter(@NonNull String query, int flags);

    /**
     * Filters the adapter's items by using a specific query and a filter, which is used to apply
     * the query on the single items, if no filter using the same query has been applied yet. This
     * method can be called multiple times without resetting the filtering, which causes the
     * filtered items to be filtered once more.
     *
     * @param query
     *         The query, which should be used to filter the items, as a {@link String}. The query
     *         may not be null
     * @param flags
     *         The flags, which should be used to filter the items, as an {@link Integer} value, or
     *         0, if no flags should be used
     * @param filter
     *         The filter, which should be used to apply the given query on the adapter's items, as
     *         an instance of the type {@link Filter} . The filter may not be null
     * @return A list, which contains the items, which have been filtered, as an instance of the
     * type {@link List}, or null, if the filter has not been applied
     */
    @Nullable
    List<DataType> applyFilter(@NonNull String query, int flags, @NonNull Filter<DataType> filter);

    /**
     * Resets the filter, which uses a specific query.
     *
     * @param query
     *         The query of the filter, which should be reseted, as a {@link String}. The query may
     *         not be null
     * @param flags
     *         The flags of the filter, which should be reseted, as an {@link Integer} value
     * @return True, if the filter has been reseted, false otherwise
     */
    boolean resetFilter(@NonNull String query, int flags);

    /**
     * Resets all applied filters.
     */
    void resetAllFilters();

    /**
     * Returns, whether a filter, which uses a specific query, is currently applied on the adapter.
     *
     * @param query
     *         The query of the filter, which should be checked, as a {@link String}. The query may
     *         not be null
     * @param flags
     *         The flags of the filter, which should be checked, as an {@link Integer} value
     * @return True, if a filter, which uses the given query, is currently applied on the adapter,
     * false otherwise
     */
    boolean isFilterApplied(@NonNull String query, int flags);

    /**
     * Returns, whether at least one filter is currently applied on the adapter to filter its items,
     * or not.
     *
     * @return True, if at least one filter is currently applied on the adapter, false otherwise.
     */
    boolean isFiltered();

    /**
     * Returns a set, which contains all queries, which are currently used to filter the adapter's
     * underlying data.
     *
     * @return A set, which contains all queries, which are currently used to filter the adapter's
     * underlying data, as an instance of the type {@link Set} or an empty set, if no filters are
     * currently applied on the adapter
     */
    Set<? extends FilterQuery> getFilterQueries();

    /**
     * Adds a new listener, which should be notified, when the adapter's underlying data has been
     * filtered.
     *
     * @param listener
     *         The listener, which should be added, as an instance of the class {@link
     *         ListFilterListener}. The listener may not be null
     */
    void addFilterListener(@NonNull ListFilterListener<DataType> listener);

    /**
     * Removes a specific listener, which should not be notified, when the adapter's underlying data
     * has been filtered, anymore.
     *
     * @param listener
     *         The listener, which should be removed, as an instance of the class {@link
     *         ListFilterListener}. The listener may not be null
     */
    void removeFilterListener(@NonNull ListFilterListener<DataType> listener);

}