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
package de.mrapp.android.adapter.expandablelist;

import android.support.annotation.NonNull;

/**
 * Defines the interface, all listeners, which should be notified, when a group item of an {@link
 * ExpandableListAdapter} has been expanded or collapsed, must implement.
 *
 * @param <GroupType>
 *         The type of the underlying data of the observed adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the observed adapter's child items
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface ExpansionListener<GroupType, ChildType> {

    /**
     * The method, which is invoked, when a group item has been expanded.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ExpandableListAdapter}. The
     *         adapter may not be null
     * @param group
     *         The group item, which has been expanded, as an instance of the generic type
     *         GroupType. The group item may not be null
     * @param index
     *         The index of the group item, which has been expanded, as an {@link Integer} value
     */
    void onGroupExpanded(@NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                         @NonNull final GroupType group, final int index);

    /**
     * The method, which is invoked, when a group item has been collapsed.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ExpandableListAdapter}. The
     *         adapter may not be null
     * @param group
     *         The group item, which has been collapsed, as an instance of the generic type
     *         GroupType. The group item may not be null
     * @param index
     *         The index of the group item, which has been collapsed, as an {@link Integer} value
     */
    void onGroupCollapsed(@NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                          @NonNull final GroupType group, final int index);

}