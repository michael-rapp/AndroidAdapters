/*
 * AndroidAdapters Copyright 2014 - 2016 Michael Rapp
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
     *         A collection, which contains the adapter's filtered group items, as an instance of
     *         the type {@link List} or an empty collection, if the adapter does not contain any
     *         group items
     */
    void onApplyGroupFilter(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                            @NonNull String query, int flags, @Nullable Filter<GroupType> filter,
                            @NonNull List<GroupType> filteredGroups);

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
     * @param filteredGroups
     *         A collection, which contains the adapter's filtered group items, as an instance of
     *         the type {@link List} or an empty collection, if the adapter does not contain any
     *         group items
     */
    void onResetGroupFilter(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                            @NonNull String query, int flags,
                            @NonNull List<GroupType> filteredGroups);

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
     *         A collection, which contains the group's filtered child items, as an instance of the
     *         type {@link List} or an empty collection, if the group does not contain any child
     *         items
     */
    void onApplyChildFilter(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                            @NonNull String query, int flags, @Nullable Filter<ChildType> filter,
                            @NonNull GroupType group, int groupIndex,
                            @NonNull List<ChildType> filteredChildren);

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
     *         A collection, which contains the group's filtered child items, as an instance of the
     *         type {@link List} or an empty collection, if the group does not contain any child
     *         items
     */
    void onResetChildFilter(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                            @NonNull String query, int flags, @NonNull GroupType group,
                            int groupIndex, @NonNull List<ChildType> unfilteredChildren);

}