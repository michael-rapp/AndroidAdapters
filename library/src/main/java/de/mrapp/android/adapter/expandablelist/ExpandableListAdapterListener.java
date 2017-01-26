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
package de.mrapp.android.adapter.expandablelist;

import android.support.annotation.NonNull;

/**
 * Defines the interface, all listeners, which should be notified, when the underlying data of an
 * {@link ExpandableListAdapter} has been modified, must implement.
 *
 * @param <GroupType>
 *         The type of the underlying data of the observed adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the observed adapter's child items
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface ExpandableListAdapterListener<GroupType, ChildType> {

    /**
     * The method, which is invoked, when a group item has been added to the adapter.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ExpandableListAdapter}. The
     *         adapter may not be null
     * @param group
     *         The group item, which has been added, as an instance of the generic type GroupType.
     *         The group item may not be null
     * @param index
     *         The index of the group item, which has been added, as an {@link Integer} value
     */
    void onGroupAdded(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                      @NonNull GroupType group, int index);

    /**
     * The method, which is invoked, when a group item has been removed from the adapter.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ExpandableListAdapter}. The
     *         adapter may not be null
     * @param group
     *         The group item, which has been removed, as an instance of the generic type GroupType.
     *         The group item may not be null
     * @param index
     *         The index of the group item, which has been removed, as an {@link Integer} value
     */
    void onGroupRemoved(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                        @NonNull GroupType group, int index);

    /**
     * The method, which is invoked, when a child item has been added to a group of the adapter.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ExpandableListAdapter}. The
     *         adapter may not be null
     * @param child
     *         The child item, which has been added, as an instance of the generic type ChildType.
     *         The child item may not be null
     * @param childIndex
     *         The index of the child item, which has been added, as an {@link Integer} value
     * @param group
     *         The group item, the child has been added to, as an instance of the generic type
     *         GroupType. The group item may not be null
     * @param groupIndex
     *         The index of the group item, the child has been added to, as an {@link Integer}
     *         value
     */
    void onChildAdded(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                      @NonNull ChildType child, int childIndex, @NonNull GroupType group,
                      int groupIndex);

    /**
     * The method, which is invoked, when a child item has been removed from a group of the
     * adapter.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ExpandableListAdapter}. The
     *         adapter may not be null
     * @param child
     *         The child item, which has been removed, as an instance of the generic type ChildType.
     *         The child item may not be null
     * @param childIndex
     *         The index of the child item, which has been removed, as an {@link Integer} value
     * @param group
     *         The group item, the child has been removed from, as an instance of the generic type
     *         GroupType. The group item may not be null
     * @param groupIndex
     *         The index of the group item, the child has been removed from, as an {@link Integer}
     *         value
     */
    void onChildRemoved(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                        @NonNull ChildType child, int childIndex, @NonNull GroupType group,
                        int groupIndex);

}