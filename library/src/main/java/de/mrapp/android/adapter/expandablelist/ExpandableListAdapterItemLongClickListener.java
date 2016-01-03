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
package de.mrapp.android.adapter.expandablelist;

import android.support.annotation.NonNull;

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

}