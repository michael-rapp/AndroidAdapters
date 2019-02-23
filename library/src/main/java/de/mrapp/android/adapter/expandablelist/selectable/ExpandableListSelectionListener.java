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
package de.mrapp.android.adapter.expandablelist.selectable;

import androidx.annotation.NonNull;

import de.mrapp.android.adapter.expandablelist.ExpandableListAdapter;

/**
 * Defines the interface, all listeners, which should be notified when the selection of a group or
 * child item of a {@link ExpandableListAdapter} has been modified, must implement.
 *
 * @param <GroupType>
 *         The type of the underlying data of the observed adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the observed adapter's child items
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface ExpandableListSelectionListener<GroupType, ChildType> {

    /**
     * The method, which is invoked, when a group item has been selected.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link SelectableExpandableListAdapter}.
     *         The adapter may not be null
     * @param group
     *         The group item, which has been selected, as an instance of the generic type
     *         GroupType. The group item may not be null
     * @param index
     *         The index of the group item, which has been selected, as an {@link Integer} value
     */
    void onGroupSelected(@NonNull SelectableExpandableListAdapter<GroupType, ChildType> adapter,
                         @NonNull GroupType group, int index);

    /**
     * The method, which is invoked, when a group item has been unselected.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link SelectableExpandableListAdapter}.
     *         The adapter may not be null
     * @param group
     *         The group item, which has been unselected, as an instance of the generic type
     *         GroupType. The group item may not be null
     * @param index
     *         The index of the group item, which has been unselected, as an {@link Integer} value
     */
    void onGroupUnselected(@NonNull SelectableExpandableListAdapter<GroupType, ChildType> adapter,
                           @NonNull GroupType group, int index);

    /**
     * The method, which is invoked, when a child item has been selected.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link SelectableExpandableListAdapter}.
     *         The adapter may not be null
     * @param child
     *         The child item, which has been selected, as an instance of the generic type
     *         ChildType. The child item may not be null
     * @param childIndex
     *         The index of the child item, which has been selected, as an {@link Integer} value
     * @param group
     *         The group item, the child, which has been selected, belongs to, as an instance of the
     *         generic type GroupType. The group item may not be null
     * @param groupIndex
     *         The index of the group item, the child, which has been selected, belongs to, as an
     *         {@link Integer} value
     */
    void onChildSelected(@NonNull SelectableExpandableListAdapter<GroupType, ChildType> adapter,
                         @NonNull ChildType child, int childIndex, @NonNull GroupType group,
                         int groupIndex);

    /**
     * The method, which is invoked, when a child item has been unselected.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link SelectableExpandableListAdapter}.
     *         The adapter may not be null
     * @param child
     *         The child item, which has been unselected, as an instance of the generic type
     *         ChildType. The child item may not be null
     * @param childIndex
     *         The index of the child item, which has been unselected, as an {@link Integer} value
     * @param group
     *         The group item, the child, which has been unselected, belongs to, as an instance of
     *         the generic type GroupType. The group item may not be null
     * @param groupIndex
     *         The index of the group item, the child, which has been unselected, belongs to, as an
     *         {@link Integer} value
     */
    void onChildUnselected(@NonNull SelectableExpandableListAdapter<GroupType, ChildType> adapter,
                           @NonNull ChildType child, int childIndex, @NonNull GroupType group,
                           int groupIndex);

}