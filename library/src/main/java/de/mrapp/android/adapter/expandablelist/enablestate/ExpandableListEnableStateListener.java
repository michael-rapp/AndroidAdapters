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
package de.mrapp.android.adapter.expandablelist.enablestate;

import androidx.annotation.NonNull;

import de.mrapp.android.adapter.expandablelist.ExpandableListAdapter;

/**
 * Defines the interface, all listeners, which should be notified, when a group or child item of a
 * {@link ExpandableListAdapter} has been enabled or disabled, must implement.
 *
 * @param <GroupType>
 *         The type of the underlying data of the observed adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the observed adapter's child items
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface ExpandableListEnableStateListener<GroupType, ChildType> {

    /**
     * The method, which is invoked, when a group item has been enabled.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ExpandableListAdapter}. The
     *         adapter may not be null
     * @param group
     *         The group item, which has been enabled, as an instance of the generic type GroupType.
     *         The group item may not be null
     * @param index
     *         The index of the group item, which has been enabled, as an {@link Integer} value
     */
    void onGroupEnabled(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                        @NonNull GroupType group, int index);

    /**
     * The method, which is invoked, when a group item has been disabled.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ExpandableListAdapter}. The
     *         adapter may not be null
     * @param group
     *         The group item, which has been disabled, as an instance of the generic type
     *         GroupType. The group item may not be null
     * @param index
     *         The index of the group item, which has been disabled, as an {@link Integer} value
     */
    void onGroupDisabled(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                         @NonNull GroupType group, int index);

    /**
     * The method, which is invoked, when a child item has been enabled.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ExpandableListAdapter}. The
     *         adapter may not be null
     * @param child
     *         The child item, which has been enabled, as an instance of the generic type ChildType.
     *         The child item may not be null
     * @param childIndex
     *         The index of the child item, which has been enabled, as an instance of the generic
     *         type ChildType. The child item may not be null
     * @param group
     *         The group item, the child, which has been enabled, belongs to, as an instance of the
     *         generic type GroupType. The group item may not be null
     * @param groupIndex
     *         The index of the group item, the child, which has been enabled, belongs to, as an
     *         {@link Integer} value
     */
    void onChildEnabled(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                        @NonNull ChildType child, int childIndex, @NonNull GroupType group,
                        int groupIndex);

    /**
     * The method, which is invoked, when a child item has been disabled.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ExpandableListAdapter}. The
     *         adapter may not be null
     * @param child
     *         The child item, which has been disabled, as an instance of the generic type
     *         ChildType. The child item may not be null
     * @param childIndex
     *         The index of the child item, which has been disabled, as an instance of the generic
     *         type ChildType. The child item may not be null
     * @param group
     *         The group item, the child, which has been disabled, belongs to, as an instance of the
     *         generic type GroupType. The group item may not be null
     * @param groupIndex
     *         The index of the group item, the child, which has been disabled, belongs to, as an
     *         {@link Integer} value
     */
    void onChildDisabled(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                         @NonNull ChildType child, int childIndex, @NonNull GroupType group,
                         int groupIndex);

}