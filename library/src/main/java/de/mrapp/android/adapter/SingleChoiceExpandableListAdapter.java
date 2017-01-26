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
package de.mrapp.android.adapter;

import android.support.annotation.NonNull;
import android.widget.ExpandableListView;

import java.util.NoSuchElementException;

import de.mrapp.android.adapter.expandablelist.selectable.SelectableExpandableListAdapter;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list of arbitrary group
 * and child items, of which only one single item can be selected at once, must implement. Such an
 * adapter's purpose is to provide the underlying data for visualization using a {@link
 * ExpandableListView} widget.
 *
 * @param <GroupType>
 *         The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the adapter's child items
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface SingleChoiceExpandableListAdapter<GroupType, ChildType>
        extends SelectableExpandableListAdapter<GroupType, ChildType> {

    /**
     * Returns, whether a group item is currently selected, or not.
     *
     * @return True, if a group item is currently selected, false otherwise
     */
    boolean isGroupSelected();

    /**
     * Returns, whether a child item is currently selected, or not.
     *
     * @return True, if a child item is currently selected, false otherwise
     */
    boolean isChildSelected();

    /**
     * Returns the index of the currently selected group item or the index of the group item, the
     * currently selected child belongs to.
     *
     * @return The index of the currently selected group item as an {@link Integer} value or -1, if
     * no item is currently selected
     */
    int getSelectedGroupIndex();

    /**
     * Returns the index of the currently selected child item.
     *
     * @return The index of the currently selected child item as an {@link Integer} value or -1, if
     * no child item is currently selected
     */
    int getSelectedChildIndex();

    /**
     * Returns the currently selected group item.
     *
     * @return The currently selected group item as an instance of the generic type GroupType or
     * null, if no group item is currently selected
     */
    GroupType getSelectedGroup();

    /**
     * Returns the currently selected child item.
     *
     * @return The currently selected child item as an instance of the generic type ChildType or
     * null, if no child item is currently selected
     */
    ChildType getSelectedChild();

    /**
     * Triggers the selection of the group item, which belongs to a specific index, if it is
     * currently enabled. If the item is not currently selected, the item becomes selected and any
     * other selected item becomes unselected. If the item is already selected, the item becomes
     * unselected. If the adapter's choice mode does not allow group items to be selected, an {@link
     * IllegalStateException} is thrown.
     *
     * @param groupIndex
     *         The index of the group item, whose selection should be triggered, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return True, if the selection of the group item, which belongs to the given index, has been
     * changed, false otherwise
     */
    boolean triggerGroupSelection(int groupIndex);

    /**
     * Triggers the selection of a specific group item, if it is currently enabled. If the item is
     * not currently selected, the item becomes selected and any other selected item becomes
     * unselected. If the item is already selected, the item becomes unselected. If the adapter's
     * choice mode does not allow group items to be selected, an {@link IllegalStateException} is
     * thrown.
     *
     * @param group
     *         The group item, whose selection should be triggered, as an instance of the generic
     *         type DataType. The group item may not be null. If the group item does not belong to
     *         the adapter, a {@link NoSuchElementException} will be thrown
     * @return True, if the selection of the group item has been changed, false otherwise
     */
    boolean triggerGroupSelection(@NonNull GroupType group);

    /**
     * Triggers the selection of the child item, which belongs to a specific index of a specific
     * group if it is currently enabled. If the item is not currently selected, the item becomes
     * selected and any other selected item becomes unselected. If the item is already selected, the
     * item becomes unselected. If the adapter's choice mode does not allow child items to be
     * selected, an {@link IllegalStateException} is thrown.
     *
     * @param group
     *         The group, the child item, whose selection should be triggered, belongs to, as an
     *         instance of the generic type GroupType. The group may not be null. If the group does
     *         not belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @param childIndex
     *         The index of the child item, whose selection should be triggered, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getChildCount(group):int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @return True, if the selection of the child item has changed, false otherwise
     */
    boolean triggerChildSelection(@NonNull GroupType group, int childIndex);

    /**
     * Triggers the selection of a specific child item of a specific group, if it is currently
     * enabled. If the item is not currently selected, the item becomes selected and any other
     * selected item becomes unselected. If the item is already selected, the item becomes
     * unselected.  If the adapter's choice mode does not allow child items to be selected, an
     * {@link IllegalStateException} is thrown.
     *
     * @param group
     *         The group, the child item, whose selection should be triggered, belongs to, as an
     *         instance of the generic type GroupType. The group may not be null. If the group does
     *         not belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @param child
     *         The child item, whose selection should be triggered, as an instance of the generic
     *         type ChildType. The child item may not be null. If the child item does not belong to
     *         the adapter, a {@link NoSuchElementException} will be thrown
     * @return True if the selection of the child item has changed, false otherwise
     */
    boolean triggerChildSelection(@NonNull GroupType group, @NonNull ChildType child);

    /**
     * Triggers the selection of the child item, which belongs to a specific index of a specific
     * group. If the item is not currently selected, the item becomes selected and any other
     * selected item becomes unselected. If the item is already selected, the item becomes
     * unselected. If the adapter's choice mode does not allow child items to be selected, an {@link
     * IllegalStateException} is thrown.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose selection should be triggered, belongs
     *         to, as an {@link Integer} value. The value must be between 0 and the value of the
     *         method <code>getGroupCount():int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @param childIndex
     *         The index of the child item, whose selection should be triggered, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getChildCount(groupIndex):int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @return True, if the selection of the child item has changed, false otherwise
     */
    boolean triggerChildSelection(int groupIndex, int childIndex);

    /**
     * Triggers the selection of a specific child item of a specific group. If the item is not
     * currently selected, the item becomes selected and any other selected item becomes unselected.
     * If the item is already selected, the item becomes unselected. If the adapter's choice mode
     * does not allow child items to be selected, an {@link IllegalStateException} is thrown.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose selection should be triggered, belongs
     *         to, as an {@link Integer} value. The value must be between 0 and the value of the
     *         method <code>getGroupCount():int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @param child
     *         The child item, whose selection should be triggered, as an instance of the generic
     *         type ChildType. The child item may not be null. If the child item does not belong to
     *         the adapter, a {@link NoSuchElementException} will be thrown
     * @return True, if the selection of the child item has changed, false otherwise
     */
    boolean triggerChildSelection(int groupIndex, @NonNull ChildType child);

    /**
     * Sets, whether the adapter's selection should be automatically adapted in order to ensure that
     * an item is always selected if possible, or not. For example this causes the selection to be
     * adapted, when the currently selected item has been removed from the adapter.
     *
     * @param adaptSelectionAutomatically
     *         True, if the adapter's selection should be automatically adapted, false otherwise
     */
    void adaptSelectionAutomatically(boolean adaptSelectionAutomatically);

    /**
     * Returns, whether the adapter's selection is automatically adapted in order to ensure that an
     * item is always selected if possible, or not.
     *
     * @return True, if the adapter's selection is automatically adapted, false otherwise
     */
    boolean isSelectionAdaptedAutomatically();

    @Override
    SingleChoiceExpandableListAdapter<GroupType, ChildType> clone()
            throws CloneNotSupportedException;

}