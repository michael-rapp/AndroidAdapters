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
import android.view.View;

import de.mrapp.android.adapter.ExpandableListAdapter;

/**
 * Defines the interface, all listeners, which should be notified, when an item of an {@link
 * ExpandableListAdapter} has been long-clicked by the user, must implement.
 *
 * @param <GroupType>
 *         The type of the underlying data of the observed adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the observed adapter's child items
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface ExpandableListAdapterItemLongClickListener<GroupType, ChildType> {

    /**
     * The method, which is invoked, when a group item of the adapter has been long-clicked by the
     * user.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ExpandableListAdapter}. The
     *         adapter may not be null
     * @param group
     *         The group item, which has been long-clicked by the user, as an instance of the
     *         generic type GroupType. The item may not be null
     * @param index
     *         The index of the group item, which has been long-clicked by the user, as an {@link
     *         Integer} value
     * @return True, if the listener has consumed the long-click, false otherwise
     */
    boolean onGroupLongClicked(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                               @NonNull GroupType group, int index);

    /**
     * The method, which is invoked, when a child item of the adapter has been long-clicked by the
     * user.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ExpandableListAdapter}. The
     *         adapter may not be null
     * @param child
     *         The child item, which has been long-clicked by the user, as an instance of the
     *         generic type ChildType. The child item may not be null
     * @param childIndex
     *         The index of the child item, which has been long-clicked by the user, as an {@link
     *         Integer} value
     * @param group
     *         The group item, the child, which has been long-clicked by the user, belongs to, as an
     *         instance of the generic type GroupType. The group item may not be null
     * @param groupIndex
     *         The index of the group item, the child, which has been long-clicked by the user,
     *         belongs to, as an {@link Integer} value
     * @return True, if the listener has consumed the long-click, false otherwise
     */
    boolean onChildLongClicked(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                               @NonNull ChildType child, int childIndex, @NonNull GroupType group,
                               int groupIndex);

    /**
     * The method, which is invoked, when a header of the adapter view, the adapter is attached to,
     * has been long-clicked by the user.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ExpandableListAdapter}. The
     *         adapter may not be null
     * @param view
     *         The header view, which has been long-clicked by the user, as an instance of the class
     *         {@link View}. The view may not be null
     * @param index
     *         The index of the header, which has been long-clicked by the user, as an {@link
     *         Integer} value
     * @return True, if the listener has consumed the long-click, false otherwise
     */
    boolean onHeaderLongClicked(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                                @NonNull View view, int index);

    /**
     * The method, which is invoked, when a footer of the adapter view, the adapter is attached to,
     * has been long-clicked by the user.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ExpandableListAdapter}. The
     *         adapter may not be null
     * @param view
     *         The footer view, which has been long-clicked by the user, as an instance of the class
     *         {@link View}. The view may not be null
     * @param index
     *         The index of the footer, which has been long-clicked by the user, as an {@link
     *         Integer} value
     * @return True, if the listener has consumed the long-click, false otherwise
     */
    boolean onFooterLongClicked(@NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                                @NonNull View view, int index);

}