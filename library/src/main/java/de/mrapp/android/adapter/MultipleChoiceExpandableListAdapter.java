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
package de.mrapp.android.adapter;

import androidx.annotation.NonNull;

import android.widget.ExpandableListView;

import java.util.List;
import java.util.NoSuchElementException;

import de.mrapp.android.adapter.expandablelist.selectable.SelectableExpandableListAdapter;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list of arbitrary group
 * and child items, of which multiple items can be selected at once, must implement. Such an
 * adapter's purpose is to provide the underlying data for visualization using an {@link
 * ExpandableListView} widget.
 *
 * @param <GroupType>
 *         The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the adapter's child items
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface MultipleChoiceExpandableListAdapter<GroupType, ChildType>
        extends SelectableExpandableListAdapter<GroupType, ChildType> {

    /**
     * Returns the index of the first selected group item.
     *
     * @return The index of the first selected group item or -1, if no group item is currently
     * selected
     */
    int getFirstSelectedGroupIndex();

    /**
     * Returns the first selected group item.
     *
     * @return The first selected group item, as an instance of the generic type GroupType or null,
     * if no group item is currently selected
     */
    GroupType getFirstSelectedGroup();

    /**
     * Returns the index of the last selected group item.
     *
     * @return The index of the last selected group item or -1, if no group item is currently
     * selected
     */
    int getLastSelectedGroupIndex();

    /**
     * Returns the last selected group item.
     *
     * @return The last selected group item, as an instance of the generic type GroupType or null,
     * if no group item is currently selected
     */
    GroupType getLastSelectedGroup();

    /**
     * Returns the index of the first unselected group item.
     *
     * @return The index of the first unselected group item or -1, if no group item is currently
     * unselected
     */
    int getFirstUnselectedGroupIndex();

    /**
     * Returns the first unselected group item.
     *
     * @return The first unselected group item, as an instance of the generic type GroupType or
     * null, if no group item is currently unselected
     */
    GroupType getFirstUnselectedGroup();

    /**
     * Return the index of the last unselected group item.
     *
     * @return The index of the last unselected group item or -1, if no group item is currently
     * unselected
     */
    int getLastUnselectedGroupIndex();

    /**
     * Returns the last unselected group item.
     *
     * @return The last unselected group item, as an instance of the generic type GroupType or null,
     * if no group item is currently unselected
     */
    GroupType getLastUnselectedGroup();

    /**
     * Returns a list, which contains the indices of all currently selected group items.
     *
     * @return A list, which contains the indices of all currently selected group items, as an
     * instance of the type {@link List} or an empty list, if no group item is currently selected
     */
    List<Integer> getSelectedGroupIndices();

    /**
     * Returns a list, which contains all currently selected groups.
     *
     * @return A list, which contains all currently selected group items, as an instance of the type
     * {@link List} or an empty list, if no group item is currently selected
     */
    List<GroupType> getSelectedGroups();

    /**
     * Returns a list, which contains the indices of all currently unselected group items.
     *
     * @return A list, which contains the indices of all currently unselected group items, as an
     * instance of the type {@link List} or an empty list, if no group item is currently selected
     */
    List<Integer> getUnselectedGroupIndices();

    /**
     * Returns a list, which contains all currently unselected group items.
     *
     * @return A list, which contains all currently unselected group items, as an instance of the
     * type {@link List} or an empty list, if no group item is currently selected
     */
    List<GroupType> getUnselectedGroups();

    /**
     * Sets the selection of the group item, which belongs to a specific index, if it is currently
     * enabled.
     *
     * @param index
     *         The index of the group item, whose selection should be set, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @param selected
     *         True, if the group item, which belongs to the given index, should be selected, false
     *         otherwise
     * @return True, if the selection of the group item, which belongs to the given index, has been
     * changed, false otherwise
     */
    boolean setGroupSelected(int index, boolean selected);

    /**
     * Sets the selection of a specific group item, if it is currently enabled.
     *
     * @param group
     *         The group item, whose selection should be set, as an instance of the generic type
     *         GroupType. The group item may not be null. If the group item does not belong to the
     *         adapter, a {@link NoSuchElementException} will be thrown
     * @param selected
     *         True, if the given group item should be selected, false otherwise
     * @return True, if the selection of the given group item has been changed, false otherwise
     */
    boolean setGroupSelected(@NonNull GroupType group, boolean selected);

    /**
     * Triggers the selection of the group item, which belongs to a specific index, it is currently
     * enabled. This causes the group item to become unselected, if it is currently selected and
     * vice versa.
     *
     * @param index
     *         The index of the group item, whose selection should be triggered, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return True, if the selection of the group item, which belongs to the given index, has been
     * changed, false otherwise
     */
    boolean triggerGroupSelection(int index);

    /**
     * Triggers the selection of a specific group item, it it is currently enabled. This causes the
     * group item to become unselected, if it is currently selected and vice versa.
     *
     * @param group
     *         The group item, whose selection should be triggered, as an instance of the generic
     *         type GroupType. The group item may not be null. If the group item does not belong to
     *         the adapter, a {@link NoSuchElementException} will be thrown
     * @return True, if the selection of the given group item has been changed, false otherwise
     */
    boolean triggerGroupSelection(@NonNull GroupType group);

    /**
     * Sets the selection of all group items, if they are currently enabled.
     *
     * @param selected
     *         True, if all group items should be selected, false otherwise
     * @return True, if the selections of all group items have been changed, false otherwise
     */
    boolean setAllGroupsSelected(boolean selected);

    /**
     * Triggers the selections of all group items, if they are currently enabled. This causes a
     * group item to become unselected, if it is currently selected and vice versa.
     *
     * @return True, if the selections of all group items have been changed, false otherwise
     */
    boolean triggerAllGroupSelections();

    /**
     * Returns the index of the first selected child item of a specific group.
     *
     * @param group
     *         The group, the child item, whose index should be returned, belongs to, as an instance
     *         of the generic type GroupType. The group may not be null. If the group does not
     *         belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @return The index of the first selected child item of the given group or -1, if no group item
     * is currently selected
     */
    int getFirstSelectedChildIndex(@NonNull GroupType group);

    /**
     * Returns the index of the first selected child item of the group, which belongs to a specific
     * index.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose index should be returned, belongs to,
     *         as an {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return The index of the first selected child item of the given group or -1, if no group item
     * is currently selected
     */
    int getFirstSelectedChildIndex(int groupIndex);

    /**
     * Returns the first selected child item.
     *
     * @return The first selected child item, as an instance of the generic type ChildType or null,
     * if no child item is currently selected
     */
    ChildType getFirstSelectedChild();

    /**
     * Returns the first selected child item of a specific group.
     *
     * @param group
     *         The group, the child item, which should be returned, belongs to, as an instance of
     *         the generic type GroupType. The group may not be null. If the group does not belong
     *         to the adapter, a {@link NoSuchElementException} will be thrown
     * @return The first selected child item of the given group, as an instance of the generic type
     * ChildType or null, if no child item is currently selected
     */
    ChildType getFirstSelectedChild(@NonNull GroupType group);

    /**
     * Returns the first selected child item of the group, which belongs to a specific index.
     *
     * @param groupIndex
     *         The index of the group, the child item, which should be returned, belongs to, as an
     *         {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return The first selected child item of the given group, as an instance of the generic type
     * ChildType or null, if no child item is currently selected
     */
    ChildType getFirstSelectedChild(int groupIndex);

    /**
     * Returns the index of the last selected child item of a specific group.
     *
     * @param group
     *         The group, the child item, whose index should be returned, belongs to, as an instance
     *         of the generic type GroupType. The group may not be null. If the group does not
     *         belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @return The index of the last selected child item of the given group or -1, if no group item
     * is currently selected
     */
    int getLastSelectedChildIndex(@NonNull GroupType group);

    /**
     * Returns the index of the last selected child item of the group, which belongs to a specific
     * index.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose index should be returned, belongs to,
     *         as an {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return The index of the last selected child item of the given group or -1, if no group item
     * is currently selected
     */
    int getLastSelectedChildIndex(int groupIndex);

    /**
     * Returns the last selected child item.
     *
     * @return The last selected child item, as an instance of the generic type ChildType or null,
     * if no child item is currently selected
     */
    ChildType getLastSelectedChild();

    /**
     * Returns the last selected child item of a specific group.
     *
     * @param group
     *         The group, the child item, which should be returned, belongs to, as an instance of
     *         the generic type GroupType. The group may not be null. If the group does not belong
     *         to the adapter, a {@link NoSuchElementException} will be thrown
     * @return The last selected child item of the given group, as an instance of the generic type
     * ChildType or null, if no child item of the given group is currently selected
     */
    ChildType getLastSelectedChild(@NonNull GroupType group);

    /**
     * Returns the last selected child item of the group, which belongs to a specific index.
     *
     * @param groupIndex
     *         The index of the group, the child item, which should be returned, belongs to, as an
     *         {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return The last selected child item of the given group, as an instance of the generic type
     * ChildType or null, if no child item of the given group is currently unselected
     */
    ChildType getLastSelectedChild(int groupIndex);

    /**
     * Returns the index of the first unselected child item of a specific group.
     *
     * @param group
     *         The group, the child item, whose index should be returned, belongs to, as an instance
     *         of the generic type GroupType. The group may not be null. If the group does not
     *         belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @return The index of the first unselected child item of the given group or -1, if no group
     * item is currently unselected
     */
    int getFirstUnselectedChildIndex(@NonNull GroupType group);

    /**
     * Returns the index of the first unselected child item of the group, which belongs to a
     * specific index.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose index should be returned, belongs to,
     *         as an {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return The index of the first unselected child item of the given group or -1, if no group
     * item is currently unselected
     */
    int getFirstUnselectedChildIndex(int groupIndex);

    /**
     * Returns the first unselected child item.
     *
     * @return The first unselected child item, as an instance of the generic type ChildType or
     * null, if no child item is currently unselected
     */
    ChildType getFirstUnselectedChild();

    /**
     * Returns the first unselected child item of a specific group.
     *
     * @param group
     *         The group, the child item, which should be returned, belongs to, as an instance of
     *         the generic type GroupType. The group may not be null. If the group does not belong
     *         to the adapter, a {@link NoSuchElementException} will be thrown
     * @return The first unselected child item of the given group, as an instance of the generic
     * type ChildType or null, if no child item is currently unselected
     */
    ChildType getFirstUnselectedChild(@NonNull GroupType group);

    /**
     * Returns the first unselected child item of the group, which belongs to a specific index.
     *
     * @param groupIndex
     *         The index of the group, the child item, which should be returned, belongs to, as an
     *         {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return The first unselected child item of the given group, as an instance of the generic
     * type ChildType or null, if no child item is currently unselected
     */
    ChildType getFirstUnselectedChild(int groupIndex);

    /**
     * Returns the index of the last unselected child item of a specific group.
     *
     * @param group
     *         The group, the child item, whose index should be returned, belongs to, as an instance
     *         of the generic type GroupType. The group may not be null. If the group does not
     *         belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @return The index of the last unselected child item of the given group or -1, if no group
     * item is currently unselected
     */
    int getLastUnselectedChildIndex(@NonNull GroupType group);

    /**
     * Returns the index of the last unselected child item of the group, which belongs to a specific
     * index.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose index should be returned, belongs to,
     *         as an {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return The index of the last unselected child item of the given group or -1, if no group
     * item is currently unselected
     */
    int getLastUnselectedChildIndex(int groupIndex);

    /**
     * Returns the last unselected child item.
     *
     * @return The last unselected child item, as an instance of the generic type ChildType or null,
     * if no child item is currently unselected
     */
    ChildType getLastUnselectedChild();

    /**
     * Returns the last unselected child item of a specific group.
     *
     * @param group
     *         The group, the child item, which should be returned, belongs to, as an instance of
     *         the generic type GroupType. The group may not be null. If the group does not belong
     *         to the adapter, a {@link NoSuchElementException} will be thrown
     * @return The last unselected child item of the given group, as an instance of the generic type
     * ChildType or null, if no child item of the given group is currently unselected
     */
    ChildType getLastUnselectedChild(@NonNull GroupType group);

    /**
     * Returns the last unselected child item of the group, which belongs to a specific index.
     *
     * @param groupIndex
     *         The index of the group, the child item, which should be returned, belongs to, as an
     *         {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return The last unselected child item of the given group, as an instance of the generic type
     * ChildType or null, if no child item of the given group is currently unselected
     */
    ChildType getLastUnselectedChild(int groupIndex);

    /**
     * Returns a list, which contains the indices of all currently selected child items.
     *
     * @return A list, which contains the indices of all currently selected child items, as an
     * instance of the type {@link List} or an empty list, if no child item is currently selected
     */
    List<Integer> getSelectedChildIndices();

    /**
     * Returns a list, which contains the indices of all currently selected child items of a
     * specific group.
     *
     * @param group
     *         The group, the child items, whose indices should be returned, belong to, as an
     *         instance of the generic type GroupType. The group may not be null. If the group does
     *         not belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @return A list, which contains the indices of all currently selected child items of the given
     * group, as an instance of the type {@link List} or an empty list, if no child item is
     * currently selected
     */
    List<Integer> getSelectedChildIndices(@NonNull GroupType group);

    /**
     * Returns a list, which contains the indices of all currently selected child items of the
     * group, which belongs to a specific index.
     *
     * @param groupIndex
     *         The index of the group, the child items, whose indices should be returned, belong to,
     *         as an {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return A list, which contains the indices of all currently selected child items of the given
     * group, as an instance of the type {@link List} or an empty list, if no child item is
     * currently selected
     */
    List<Integer> getSelectedChildIndices(int groupIndex);

    /**
     * Returns a list, which contains all currently selected child items.
     *
     * @return A list, which contains all currently selected child items, as an instance of the type
     * {@link List} or an empty list, if no child item is currently selected
     */
    List<ChildType> getSelectedChildren();

    /**
     * Returns a list, which contains all currently selected child items of a specific group.
     *
     * @param group
     *         The group, the child items, which should be returned, belong to, as an instance of
     *         the generic type GroupType. The group may not be null. If the group does not belong
     *         to the adapter, a {@link NoSuchElementException} will be thrown
     * @return A list, which contains all currently selected child items of the given group, as an
     * instance of the type {@link List} or an empty list, if no child item is currently selected
     */
    List<ChildType> getSelectedChildren(@NonNull GroupType group);

    /**
     * Returns a list, which contains all currently selected child items of the group, which belongs
     * to a specific index.
     *
     * @param groupIndex
     *         The index of the group, the child items, which should be returned, belong to, as an
     *         {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return A list, which contains all currently selected child items of the given group, as an
     * instance of the type {@link List} or an empty list, if no child item is currently selected
     */
    List<ChildType> getSelectedChildren(int groupIndex);

    /**
     * Returns a list, which contains the indices of all currently unselected child items.
     *
     * @return A list, which contains the indices of all currently unselected child items, as an
     * instance of the type {@link List} or an empty list, if no child item is currently unselected
     */
    List<Integer> getUnselectedChildIndices();

    /**
     * Returns a list, which contains the indices of all currently unselected child items of a
     * specific group.
     *
     * @param group
     *         The group, the child items, whose indices should be returned, belong to, as an
     *         instance of the generic type GroupType. The group may not be null. If the group does
     *         not belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @return A list, which contains the indices of all currently unselected child items of the
     * given group, as an instance of the type {@link List} or an empty list, if no child item is
     * currently unselected
     */
    List<Integer> getUnselectedChildIndices(@NonNull GroupType group);

    /**
     * Returns a list, which contains the indices of all currently unselected child items of the
     * group, which belongs to a specific index.
     *
     * @param groupIndex
     *         The index of the group, the child items, whose indices should be returned, belong to,
     *         as an {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return A list, which contains the indices of all currently unselected child items of the
     * given group, as an instance of the type {@link List} or an empty list, if no child item is
     * currently unselected
     */
    List<Integer> getUnselectedChildIndices(int groupIndex);

    /**
     * Returns a list, which contains all currently unselected child items.
     *
     * @return A list, which contains all currently unselected child items, as an instance of the
     * type {@link List} or an empty list, if no child item is currently unselected
     */
    List<ChildType> getUnselectedChildren();

    /**
     * Returns a list, which contains all currently unselected child items of a specific group.
     *
     * @param group
     *         The group, the child items, which should be returned, belong to, as an instance of
     *         the generic type GroupType. The group may not be null. If the group does not belong
     *         to the adapter, a {@link NoSuchElementException} will be thrown
     * @return A list, which contains all currently unselected child items of the given group, as an
     * instance of the type {@link List} or an empty list, if no child item is currently unselected
     */
    List<ChildType> getUnselectedChildren(@NonNull GroupType group);

    /**
     * Returns a list, which contains all currently unselected child items of the group, which
     * belongs to a specific index.
     *
     * @param groupIndex
     *         The index of the group, the child items, which should be returned, belong to, as an
     *         {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return A list, which contains all currently unselected child items of the given group, as an
     * instance of the type {@link List} or an empty list, if no child item is currently unselected
     */
    List<ChildType> getUnselectedChildren(int groupIndex);

    /**
     * Sets the selection of a specific child item of a specific group, if it is currently enabled.
     *
     * @param group
     *         The group, the child item, whose selection should be set, belongs to, as an instance
     *         of the generic type GroupType. The group may not be null. If the group does not
     *         belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @param childIndex
     *         The index of the child item, whose selection should be set, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getChildCount(group):int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @param selected
     *         True, if the given child item should be selected, false otherwise
     * @return True, if the selection of the given child item has been changed, false otherwise
     */
    boolean setChildSelected(@NonNull GroupType group, int childIndex, boolean selected);

    /**
     * Sets the selection of a specific child item of a specific group, if it is currently enabled.
     *
     * @param group
     *         The group, the child item, whose selection should be set, belongs to, as an instance
     *         of the generic type GroupType. The group may not be null. If the group does not
     *         belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @param child
     *         The child item, whose selection should be set, as an instance of the generic type
     *         ChildType. The child item may not be null. If the child item does not belong to the
     *         adapter, a {@link NoSuchElementException} will be thrown
     * @param selected
     *         True, if the given child item should be selected, false otherwise
     * @return True, if the selection of the given child item has been changed, false otherwise
     */
    boolean setChildSelected(@NonNull GroupType group, @NonNull ChildType child, boolean selected);

    /**
     * Sets the selection of a specific child item of a specific group, if it is currently enabled.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose selection should be set, belongs to, as
     *         an {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @param childIndex
     *         The index of the child item, whose selection should be set, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getChildCount(groupIndex):int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @param selected
     *         True, if the given child item should be selected, false otherwise
     * @return True, if the selection of the given child item has been changed, false otherwise
     */
    boolean setChildSelected(int groupIndex, int childIndex, boolean selected);

    /**
     * Sets the selection of a specific child item of a specific group, if it is currently enabled.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose selection should be set, belongs to, as
     *         an {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @param child
     *         The child item, whose selection should be set, as an instance of the generic type
     *         ChildType. The child item may not be null. If the child item does not belong to the
     *         adapter, a {@link NoSuchElementException} will be thrown
     * @param selected
     *         True, if the given child item should be selected, false otherwise
     * @return True, if the selection of the given child item has been changed, false otherwise
     */
    boolean setChildSelected(int groupIndex, @NonNull ChildType child, boolean selected);

    /**
     * Triggers the selection of a specific child item, it it is currently enabled. This causes the
     * child item to become unselected, if it is currently selected and vice versa.
     *
     * @param group
     *         The group, the child item, whose selection should be triggered, belongs to, as an
     *         instance of the generic type GroupType. The group may not be null. If the group does
     *         not belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @param childIndex
     *         The index of the child item, whose selection should be triggered, as an {@link
     *         Integer} value. The index must be between the value of the method
     *         <code>getChildCount(group):int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @return True, if the selection of the given child item has been changed, false otherwise
     */
    boolean triggerChildSelection(@NonNull GroupType group, int childIndex);

    /**
     * Triggers the selection of a specific child item, it it is currently enabled. This causes the
     * child item to become unselected, if it is currently selected and vice versa.
     *
     * @param group
     *         The group, the child item, whose selection should be triggered, belongs to, as an
     *         instance of the generic type GroupType. The group may not be null. If the group does
     *         not belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @param child
     *         The child item, whose selection should be triggered, as an instance of the generic
     *         type ChildType. The child item may not be null. If the child item does not belong to
     *         the adapter, a {@link NoSuchElementException} will be thrown
     * @return True, if the selection of the given child item has been changed, false otherwise
     */
    boolean triggerChildSelection(@NonNull GroupType group, @NonNull ChildType child);

    /**
     * Triggers the selection of a specific child item, it it is currently enabled. This causes the
     * child item to become unselected, if it is currently selected and vice versa.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose selection should be triggered, belongs
     *         to, as an {@link Integer} value. The index must be between 0 and the value of the
     *         method <code>getGroupCount():int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @param childIndex
     *         The index of the child item, whose selection should be triggered, as an {@link
     *         Integer} value. The index must be between the value of the method
     *         <code>getChildCount(groupIndex):int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @return True, if the selection of the given child item has been changed, false otherwise
     */
    boolean triggerChildSelection(int groupIndex, int childIndex);

    /**
     * Triggers the selection of a specific child item, it it is currently enabled. This causes the
     * child item to become unselected, if it is currently selected and vice versa.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose selection should be triggered, belongs
     *         to, as an {@link Integer} value. The index must be between 0 and the value of the
     *         method <code>getGroupCount():int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @param child
     *         The child item, whose selection should be triggered, as an instance of the generic
     *         type ChildType. The child item may not be null. If the child item does not belong to
     *         the adapter, a {@link NoSuchElementException} will be thrown
     * @return True, if the selection of the given child item has been changed, false otherwise
     */
    boolean triggerChildSelection(int groupIndex, @NonNull ChildType child);

    /**
     * Sets the selection of all child items, regardless of the group they belong to, if they are
     * currently enabled.
     *
     * @param selected
     *         True, if all child items should be selected, false otherwise
     * @return True, if the selections of all child items have been changed, false otherwise
     */
    boolean setAllChildrenSelected(boolean selected);

    /**
     * Sets the selection of all child items of a specific group, if they are currently enabled.
     *
     * @param group
     *         The group, the child items, which should be selected, belong to, as an instance of
     *         the generic type GroupType. The group may not be null. If the group does not belong
     *         to the adapter, a {@link NoSuchElementException} will be thrown
     * @param selected
     *         True, if all child items should be selected, false otherwise
     * @return True, if the selections of all child items have been changed, false otherwise
     */
    boolean setAllChildrenSelected(@NonNull GroupType group, boolean selected);

    /**
     * Sets the selection of all child items of a specific group, if they are currently enabled.
     *
     * @param groupIndex
     *         The index of the group, the children, which should be selected, belong to, as an
     *         {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @param selected
     *         True, if all child items should be selected, false otherwise
     * @return True, if the selections of all child items have been changed, false otherwise
     */
    boolean setAllChildrenSelected(int groupIndex, boolean selected);

    /**
     * Triggers the selections of all child items of a specific group, if they are currently
     * enabled. This causes a child item to become unselected, if it is currently selected and vice
     * versa.
     *
     * @param group
     *         The group, the child items, which should be selected, belong to, as an instance of
     *         the generic type GroupType. The group may not be null. If the group does not belong
     *         to the adapter, a {@link NoSuchElementException} will be thrown
     * @return True, if the selections of all child items of the given group have been changed,
     * false otherwise
     */
    boolean triggerAllChildSelections(@NonNull GroupType group);

    /**
     * Triggers the selections of all child items of a group, which belongs to a specific index, if
     * they are currently enabled. This causes a child item to become unselected, if it is currently
     * selected and vice versa.
     *
     * @param groupIndex
     *         The index of the group, the children, which should be selected, belong to, as an
     *         {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return True, if the selections of all child items of the given group have been changed,
     * false otherwise
     */
    boolean triggerAllChildSelections(int groupIndex);

    @Override
    MultipleChoiceExpandableListAdapter<GroupType, ChildType> clone()
            throws CloneNotSupportedException;

}