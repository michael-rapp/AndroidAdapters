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
package de.mrapp.android.adapter.expandablelist.sortable;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.Comparator;

import de.mrapp.android.adapter.expandablelist.ExpandableListAdapter;
import de.mrapp.android.adapter.Order;

/**
 * Defines the interface, all listeners, which should be notified when the underlying data of an
 * {@link ExpandableListAdapter} have been sorted, must implement.
 *
 * @param <GroupType>
 *         The type of the underlying data of the observed adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the observed adapter's child items
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface ExpandableListSortingListener<GroupType, ChildType> {

    /**
     * The method, which is invoked, when the adapter's group items have been sorted.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ExpandableListAdapter}. The
     *         adapter may not be null
     * @param sortedGroups
     *         A collection, which contains the adapter's sorted group items, as an instance of the
     *         type {@link Collection} or an empty collection, if the adapter does not contain any
     *         group items
     * @param order
     *         The order, which has been used to sort the adapter's group items, as a value of the
     *         enum {@link Order}. The order may either be <code>ASCENDING</code> or
     *         <code>DESCENDING</code>
     * @param comparator
     *         The comparator, which has been used to compare the single group items, as an instance
     *         of the type {@link Comparator} or null, if the group items' implementation of the
     *         type {@link Comparable} has been used instead
     */
    void onGroupsSorted(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                        @NonNull Collection<GroupType> sortedGroups, @NonNull Order order,
                        @Nullable Comparator<GroupType> comparator);

    /**
     * The method, which is invoked, when the child items of a group of the adapter have been
     * sorted.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ExpandableListAdapter}. The
     *         adapter may not be null
     * @param sortedChildren
     *         A collection, which contains the group's sorted child items, as an instance of the
     *         type {@link Collection} or an empty collection, if the group does not contain any
     *         child items
     * @param order
     *         The order, which has been used to sort the group's child items, as a value of the
     *         enum {@link Order}. The order may either be <code>ASCENDING</code> or
     *         <code>DESCENDING</code>
     * @param comparator
     *         The comparator, which has been used to compare the single child items, as an instance
     *         of the type {@link Comparator} or null, if the child items' implementation of the
     *         type {@link Comparable} has been used instead
     * @param group
     *         The group, whose child items have been sorted, as an instance of the generic type
     *         GroupType. The group may not be null
     * @param groupIndex
     *         The index of the group, whose child items have been sorted, as an {@link Integer}
     *         value
     */
    void onChildrenSorted(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                          @NonNull Collection<ChildType> sortedChildren, @NonNull Order order,
                          @Nullable Comparator<ChildType> comparator, @NonNull GroupType group,
                          int groupIndex);

}