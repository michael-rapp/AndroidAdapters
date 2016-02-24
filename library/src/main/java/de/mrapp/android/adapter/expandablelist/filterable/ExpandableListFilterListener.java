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
package de.mrapp.android.adapter.expandablelist.filterable;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import de.mrapp.android.adapter.ExpandableListAdapter;
import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.Filterable;

/**
 * Defines the interface, all listeners, which should be notified when the underlying data of an
 * {@link ExpandableListAdapter} has been filtered, must implement.
 *
 * @param <GroupType>
 *         The type of the underlying data of the observed adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the observed adapter's child items
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface ExpandableListFilterListener<GroupType, ChildType> {

    /**
     * The method, which is invoked, when the adapter's group items have been filtered by using a
     * query.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ExpandableListAdapter}. The
     *         adapter may not be null
     * @param query
     *         The query, which has been used, as a {@link String}. The query may not be null
     * @param flags
     *         The flags, which have been used, as an {@link Integer} value, or 0, if no flags have
     *         been used instead
     * @param filter
     *         The filter, which has been used to apply the query on the single group items, as an
     *         instance of the type {@link Filter} or null, if the group items' implementations of
     *         the interface {@link Filterable} have been used instead
     * @param filteredGroups
     *         A collection, which contains the group items, which have been filtered, as an
     *         instance of the type {@link List} or an empty collection, if no group items have been
     *         filtered
     * @param unfilteredGroups
     *         A collection, which contains the adapter's unfiltered group items, as an instance of
     *         the type {@link List} or an empty collection, if the adapter does not contain any
     *         group items
     */
    void onApplyGroupFilter(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                            @NonNull String query, int flags, @Nullable Filter<GroupType> filter,
                            @NonNull List<GroupType> filteredGroups,
                            @NonNull List<GroupType> unfilteredGroups);

    /**
     * The method, which is invoked, when a filter, which has been used to filter the adapter's
     * group items, has been reseted.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ExpandableListAdapter}. The
     *         adapter may not be null
     * @param query
     *         The query used by the filter, which has been reseted, as a {@link String}. The query
     *         may not be null
     * @param flags
     *         The flags used by the filter, which has been reseted, as an {@link Integer} value or
     *         0, if no flags have been used by the filter
     * @param unfilteredGroups
     *         A collection, which contains the adapter's unfiltered group items, as an instance of
     *         the type {@link List} or an empty collection, if the adapter does not contain any
     *         group items
     */
    void onResetGroupFilter(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                            @NonNull String query, int flags,
                            @NonNull List<GroupType> unfilteredGroups);

    /**
     * The method, which is invoked, when the child items of a specific group have been filtered by
     * using a query.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ExpandableListAdapter}. The
     *         adapter may not be null
     * @param query
     *         The query, which has been used, as a {@link String}. The query may not be null
     * @param flags
     *         The flags, which have been used, as an {@link Integer} value, or 0, if no flags have
     *         been used instead
     * @param filter
     *         The filter, which has been used to apply the query on the single child items, as an
     *         instance of the type {@link Filter} or null, if the child items' implementations of
     *         the interface {@link Filterable} have been used instead
     * @param group
     *         The group, whose child items have been filtered, as an instance of the generic type
     *         GroupType. The group may not be null
     * @param groupIndex
     *         The index of the group, whose child items have been filtered, as an {@link Integer}
     *         value
     * @param filteredChildren
     *         A collection, which contains the child items, which have been filtered, as an
     *         instance of the type {@link List} or an empty collection, if no child items have been
     *         filtered
     * @param unfilteredChildren
     *         A collection, which contains the group's unfiltered child items, as an instance of
     *         the type {@link List} or an empty collection, if the group does not contain any child
     *         items
     */
    void onApplyChildFilter(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                            @NonNull String query, int flags, @Nullable Filter<ChildType> filter,
                            @NonNull GroupType group, int groupIndex,
                            @NonNull List<ChildType> filteredChildren,
                            @NonNull List<ChildType> unfilteredChildren);

    /**
     * The method, which is invoked, when a filter, which has been used to filter the child items of
     * a specific group, has been reseted.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ExpandableListAdapter}. The
     *         adapter may not be null
     * @param query
     *         The query used by the filter, which has been reseted, as a {@link String}. The query
     *         may not be null
     * @param flags
     *         The flags used by the filter, which has been reseted, as an {@link Integer} value or
     *         0, if no flags have been used
     * @param group
     *         The group, whose child items have been filtered, as an instance of the generic type
     *         GroupType. The group may not be null
     * @param groupIndex
     *         The index of the group, whose child items have been filtered, as an {@link Integer}
     *         value
     * @param unfilteredChildren
     *         A collection, which contains the group's unfiltered child items, as an instance of
     *         the type {@link List} or an empty collection, if the group does not contain any child
     *         items
     */
    void onResetChildFilter(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                            @NonNull String query, int flags, @NonNull GroupType group,
                            int groupIndex, @NonNull List<ChildType> unfilteredChildren);

}