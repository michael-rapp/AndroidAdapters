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
package de.mrapp.android.adapter.expandablelist.selectable;

import android.support.annotation.NonNull;
import android.widget.ExpandableListView;

import java.util.NoSuchElementException;

import de.mrapp.android.adapter.ChoiceMode;
import de.mrapp.android.adapter.ExpandableListAdapter;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list of arbitrary group
 * and child items of which one or multiple items can be selected, must implement. Such an adapter's
 * purpose is to provide the underlying data for visualization using a {@link ExpandableListView}
 * widget.
 *
 * @param <GroupType>
 *         The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the adapter's child items
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface SelectableExpandableListAdapter<GroupType, ChildType>
        extends ExpandableListAdapter<GroupType, ChildType> {

    /**
     * Adds a new listener, which should be notified when the selection of an item has been
     * changed.
     *
     * @param listener
     *         The listener, which should be added, as an instance of the type {@link
     *         ExpandableListSelectionListener}. The listener may not be null
     */
    void addSelectionListener(
            @NonNull ExpandableListSelectionListener<GroupType, ChildType> listener);

    /**
     * Removes a specific listener, which should not be notified when the selection of an item has
     * been changed, anymore.
     *
     * @param listener
     *         The listener, which should be removed, as an instance of the type {@link
     *         ExpandableListSelectionListener}. The listener may not be null
     */
    void removeSelectionListener(
            @NonNull ExpandableListSelectionListener<GroupType, ChildType> listener);

    /**
     * Returns, whether the group item, which belongs to a specific index, is currently selected, or
     * not.
     *
     * @param groupIndex
     *         The index of the group item, whose selection state should be returned, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return True, if the group item, which belongs to the given index, is currently selected,
     * false otherwise
     */
    boolean isGroupSelected(int groupIndex);

    /**
     * Returns, whether a specific group item is currently selected, or not.
     *
     * @param group
     *         The group item, whose selection state should be returned, as an instance of the
     *         generic type GroupType. The item may not be null. If the item does not belong to the
     *         adapter, a {@link NoSuchElementException} will be thrown
     * @return True, if the given group item is currently selected, false otherwise
     */
    boolean isGroupSelected(@NonNull GroupType group);

    /**
     * Returns the number of currently selected group items.
     *
     * @return The number of currently selected group items, as an {@link Integer} value
     */
    int getSelectedGroupCount();

    /**
     * Returns, whether the child item, which belongs to a specific index of a specific group, is
     * currently selected, or not.
     *
     * @param group
     *         The group, the child item, whose selection state should be returned, belongs to, as
     *         an instance of the generic type GroupType. The item may not be null. If the item does
     *         not belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @param childIndex
     *         The index of the child item, whose selection state should be returned, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getChildCount(group):int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @return True, if the given group item is currently selected, false otherwise
     */
    boolean isChildSelected(@NonNull GroupType group, int childIndex);

    /**
     * Returns, whether a specific child item, which belongs to a specific group, is currently
     * selected, or not.
     *
     * @param group
     *         The group, the child item, whose selection state should be returned, belongs to, as
     *         an instance of the generic type GroupType. The item may not be null. If the item does
     *         not belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @param child
     *         The child item, whose selection state should be returned, as an instance of the
     *         generic type ChildType. The item may not be null. If the item does not belong to the
     *         adapter, a {@link java.util.NoSuchElementException} will be thrown
     * @return True, if the given group item is currently selected, false otherwise
     */
    boolean isChildSelected(@NonNull GroupType group, @NonNull ChildType child);

    /**
     * Returns, whether the child item, which belongs to a specific index of a specific group, is
     * currently selected, or not.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose selection state should be returned,
     *         belongs to, as an {@link Integer} value. The value must be between 0 and the value of
     *         the method <code>getGroupCount():int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @param childIndex
     *         The index of the child item, whose selection state should be returned, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getChildCount(group):int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @return True, if the given group item is currently selected, false otherwise
     */
    boolean isChildSelected(int groupIndex, int childIndex);

    /**
     * Returns, whether a specific child item, which belongs to a specific group, is currently
     * selected, or not.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose selection state should be returned,
     *         belongs to, as an {@link Integer} value. The value must be between 0 and the value of
     *         the method <code>getGroupCount():int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @param child
     *         The child item, whose selection state should be returned, as an instance of the
     *         generic type ChildType. The item may not be null. If the item does not belong to the
     *         adapter, a {@link java.util.NoSuchElementException} will be thrown
     * @return True, if the given group item is currently selected, false otherwise
     */
    boolean isChildSelected(int groupIndex, @NonNull ChildType child);

    /**
     * Returns the number of currently selected child items.
     *
     * @return The number of currently selected child items, as an {@link Integer} value
     */
    int getSelectedChildCount();

    /**
     * Returns the number of currently selected child items of the group, which belongs to a
     * specific index.
     *
     * @param groupIndex
     *         The index of the group, the child items, which should be counted, belong to, as an
     *         {@link Integer} value. The value must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return The number of currently selected child items of the group, which belongs to the given
     * index, as an {@link Integer} value
     */
    int getSelectedChildCount(int groupIndex);

    /**
     * Returns the number of currently selected child items of a specific group.
     *
     * @param group
     *         The group, the child items, which should be counted, belong to, as an instance of the
     *         generic type GroupType. The item may not be null. If the item does not belong to the
     *         adapter, a {@link NoSuchElementException} will be thrown
     * @return The number of the currently selected child items of the given group, as an {@link
     * Integer} value
     */
    int getSelectedChildCount(@NonNull GroupType group);

    /**
     * Returns, whether a group item is selected, when it is clicked by the user, or not.
     *
     * @return True, if a group item is selected, when it is clicked by the user, false otherwise
     */
    boolean isGroupSelectedOnClick();

    /**
     * Sets, whether a group item should be selected, when it is clicked by the user, or not.
     *
     * @param selectItemOnClick
     *         True, if a group item should be selected, when it is clicked by the user, false
     *         otherwise
     */
    void selectGroupOnClick(boolean selectItemOnClick);

    /**
     * Returns, whether a child item is selected, when it is clicked by the user, or not.
     *
     * @return True, if a child item is selected, when it is clicked by the user, false otherwise
     */
    boolean isChildSelectedOnClick();

    /**
     * Sets, whether a child item should be selected, when it is clicked by the user, or not.
     *
     * @param selectItemOnClick
     *         True, if a child item should be selected, when it is clicked by the user, false
     *         otherwise
     */
    void selectChildOnClick(boolean selectItemOnClick);

    /**
     * Returns, whether a group is expanded automatically, when it is selected, or not.
     *
     * @return True, if a group is expanded automatically, when it is selected, false otherwise
     */
    boolean isGroupExpandedOnSelection();

    /**
     * Sets, whether a group should be expanded automatically, when it is selected, or not.
     *
     * @param expandGroupOnSelection
     *         True, if a group should be expanded automatically, when it is selected, false
     *         otherwise
     */
    void expandGroupOnSelection(boolean expandGroupOnSelection);

    /**
     * Returns, whether a group is expanded automatically, when one of its children is selected, or
     * not.
     *
     * @return True, if a group is expanded automatically, when one of its children is selected,
     * false otherwise
     */
    boolean isGroupExpandedOnChildSelection();

    /**
     * Sets, whether a group should be expanded automatically, when one of its children is selected,
     * or not.
     *
     * @param expandGroupOnChildSelection
     *         True, if a group should be expanded automatically, when one of its children is
     *         selected, false otherwise
     */
    void expandGroupOnChildSelection(boolean expandGroupOnChildSelection);

    /**
     * Returns the choice mode of the adapter.
     *
     * @return The choice mode of the adapter as a value of the enum {@link ChoiceMode}. The choice
     * mode may either be <code>GROUPS_ONLY</code>, <code>CHILDREN_ONLY</code> or
     * <code>GROUPS_AND_CHILDREN</code>
     */
    ChoiceMode getChoiceMode();

}