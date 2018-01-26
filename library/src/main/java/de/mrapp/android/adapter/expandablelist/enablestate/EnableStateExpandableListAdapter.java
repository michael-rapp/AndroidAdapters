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
package de.mrapp.android.adapter.expandablelist.enablestate;

import android.support.annotation.NonNull;
import android.widget.ExpandableListView;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list of arbitrary group
 * and child items, which may be enabled or disabled, must implement. Such an adapter's purpose is
 * to provide the underlying data for visualization using an {@link ExpandableListView} widget.
 *
 * @param <GroupType>
 *         The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the adapter's child items
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface EnableStateExpandableListAdapter<GroupType, ChildType> {

    /**
     * Returns, whether the group item, which belongs to a specific index, is currently enabled, or
     * not.
     *
     * @param groupIndex
     *         The index of the group item, whose enable state should be checked, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return True, if the group item, which belongs to the given index, is currently enabled,
     * false otherwise
     */
    boolean isGroupEnabled(int groupIndex);

    /**
     * Returns, whether a specific group item is currently enabled, or not.
     *
     * @param group
     *         The group item, whose enable state should be checked, as an instance of the generic
     *         type GroupType. The group item may not be null. If the group item does not belong to
     *         the adapter, a {@link NoSuchElementException} will be thrown
     * @return True, if the given group item is currently enabled, false otherwise
     */
    boolean isGroupEnabled(@NonNull GroupType group);

    /**
     * Returns the index of the first enabled group item.
     *
     * @return The index of the first enabled group item, as an {@link Integer} value or -1, if no
     * group item is currently enabled
     */
    int getFirstEnabledGroupIndex();

    /**
     * Returns the first enabled group item.
     *
     * @return The first enabled group item, as an instance of the generic type GroupType or null,
     * if no group item is currently enabled
     */
    GroupType getFirstEnabledGroup();

    /**
     * Returns the index of the last enabled group item.
     *
     * @return The index of the last enabled group item, as an {@link Integer} value or -1, if no
     * group item is currently enabled
     */
    int getLastEnabledGroupIndex();

    /**
     * Returns the last enabled group item.
     *
     * @return The last enabled group item, as an instance of the generic type GroupType or null, if
     * no group item is currently enabled
     */
    GroupType getLastEnabledGroup();

    /**
     * Returns the index of the first disabled group item.
     *
     * @return The index of the first disabled group item, as an {@link Integer} value or -1, if no
     * item is currently disabled
     */
    int getFirstDisabledGroupIndex();

    /**
     * Returns the first disabled group item.
     *
     * @return The first disabled group item, as an instance of the generic type GroupType or null,
     * if no group item is currently disabled
     */
    GroupType getFirstDisabledGroup();

    /**
     * Returns the index of the last disabled group item.
     *
     * @return The index of the last disabled group item, as an {@link Integer} value or -1, if no
     * group item is currently disabled
     */
    int getLastDisabledGroupIndex();

    /**
     * Returns the index of the last disabled group item.
     *
     * @return The index of the last disabled group item, as an {@link Integer} value or -1, if no
     * group item is currently disabled
     */
    GroupType getLastDisabledGroup();

    /**
     * Returns a list, which contains the indices of all currently enabled group items.
     *
     * @return A list, which contains the indices of all currently enabled group items, as an
     * instance of the type {@link List} or an empty list, if no group item is currently enabled
     */
    List<Integer> getEnabledGroupIndices();

    /**
     * Returns a list, which contains all currently enabled group items.
     *
     * @return A list, which contains all currently enabled group items, as an instance of the type
     * {@link List} or an empty list, if no group item is currently enabled
     */
    List<GroupType> getEnabledGroups();

    /**
     * Returns a list, which contains the indices of all currently disabled group items.
     *
     * @return A list, which contains the indices of all currently disabled group items, as an
     * instance of the type {@link List} or an empty list, if no group item is currently disabled
     */
    List<Integer> getDisabledGroupIndices();

    /**
     * Returns a list, which contains all currently disabled group items.
     *
     * @return A list, which contains all currently disabled group items, as an instance of the type
     * {@link List} or an empty list, if no group item is currently disabled
     */
    List<GroupType> getDisabledGroups();

    /**
     * Returns the number of currently enabled groups.
     *
     * @return The number of currently enabled groups, as an {@link Integer} value
     */
    int getEnabledGroupCount();

    /**
     * Sets the enable state of the group item, which belongs to a specific index.
     *
     * @param groupIndex
     *         The index of the group item, whose enable state should be set, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @param enabled
     *         True, if the group item, which belongs to the given index, should be enabled, false
     *         otherwise
     */
    void setGroupEnabled(int groupIndex, boolean enabled);

    /**
     * Sets the enable state of a specific group item.
     *
     * @param group
     *         The group item, which should be enabled, as an instance of the generic type
     *         GroupType. The group item may not be null. If the group item does not belong to the
     *         adapter, a {@link NoSuchElementException} will be thrown
     * @param enabled
     *         True, if the given group item should be enabled, false otherwise
     */
    void setGroupEnabled(@NonNull GroupType group, boolean enabled);

    /**
     * Triggers the enable state of the group item, which belongs to a specific index. This causes
     * the group item to become disabled, if it is currently enabled and vice versa.
     *
     * @param groupIndex
     *         The index of the group item, whose enable state should be triggered, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return True, if the group item has been enabled, false, if the group item has been disabled
     */
    boolean triggerGroupEnableState(int groupIndex);

    /**
     * Triggers the enable state of the group item, which belongs to a specific index. This causes
     * the group item to become disabled, if it is currently enabled and vice versa.
     *
     * @param triggerChildStates
     *         True, if the enable states of the group item's children should also be triggered,
     *         false otherwise
     * @param groupIndex
     *         The index of the group item, whose enable state should be triggered, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return True, if the group item has been enabled, false, if the group item has been disabled
     */
    boolean triggerGroupEnableState(boolean triggerChildStates, int groupIndex);

    /**
     * Triggers the enable state of a specific group item. This causes the group item to become
     * disabled, if it is currently enabled and vice versa.
     *
     * @param group
     *         The group item, whose enable state should be triggered, as an instance of the generic
     *         type GroupType. The group item may not be null. If the group item does not belong to
     *         the adapter, a {@link NoSuchElementException} will be thrown
     * @return True, if the group item has been enabled, false, if the group item has been disabled
     */
    boolean triggerGroupEnableState(@NonNull GroupType group);

    /**
     * Triggers the enable state of a specific group item. This causes the group item to become
     * disabled, if it is currently enabled and vice versa.
     *
     * @param triggerChildStates
     *         True, if the enable states of the group item's children should also be triggered,
     *         false otherwise
     * @param group
     *         The group item, whose enable state should be triggered, as an instance of the generic
     *         type GroupType. The group item may not be null. If the group item does not belong to
     *         the adapter, a {@link NoSuchElementException} will be thrown
     * @return True, if the group item has been enabled, false, if the group item has been disabled
     */
    boolean triggerGroupEnableState(boolean triggerChildStates, @NonNull GroupType group);

    /**
     * Sets the enable states of all group items.
     *
     * @param enabled
     *         True, if all group items should be enabled, false otherwise
     */
    void setAllGroupsEnabled(boolean enabled);

    /**
     * Triggers the enable states of all group items. This causes a group item to become disabled,
     * if it is currently enabled and vice versa.
     */
    void triggerAllGroupEnableStates();

    /**
     * Triggers the enable states of all group items. This causes a group item to become disabled,
     * if it is currently enabled and vice versa.
     *
     * @param triggerChildStates
     *         True, if the enable states of the group items' children should be also triggered,
     *         false otherwise
     */
    void triggerAllGroupEnableStates(boolean triggerChildStates);

    /**
     * Returns, whether the child item, which belongs to a specific index of a specific group, is
     * currently enabled, or not.
     *
     * @param group
     *         The group, the child item, whose enable state should be checked, belongs to, as an
     *         instance of the generic type GroupType. The group may not be null. If the group does
     *         not belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @param childIndex
     *         The index of the child item, whose enable state should be checked, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getChildCount(group):int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @return True, if the child item, which belongs to the given index, is currently enabled,
     * false otherwise
     */
    boolean isChildEnabled(@NonNull GroupType group, int childIndex);

    /**
     * Returns, whether a specific child item, which belongs to a specific group, is currently
     * enabled, or not.
     *
     * @param group
     *         The group, the child item, whose enable state should be checked, belongs to, as an
     *         instance of the generic type GroupType. The group may not be null. If the group does
     *         not belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @param child
     *         The child item, whose enable state should be checked, as an instance of the generic
     *         type ChildType. The child item may not be null. If the child item does not belong to
     *         the given group, a {@link NoSuchElementException} will be thrown
     * @return True, if the given child item is currently enabled, false otherwise
     */
    boolean isChildEnabled(@NonNull GroupType group, @NonNull ChildType child);

    /**
     * Returns, whether the child item, which belongs to the group, which belongs to a specific
     * index, is currently enabled, or not.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose enable state should be checked, belongs
     *         to, as an {@link Integer} value. The value must be between 0 and the value of the
     *         method <code>getGroupCount():int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @param childIndex
     *         The index of the child item, whose enable state should be checked, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getChildCount(groupIndex):int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @return True, if the child item, which belongs to the given index, is currently enabled,
     * false otherwise
     */
    boolean isChildEnabled(int groupIndex, int childIndex);

    /**
     * Returns, whether a specific child item, which belongs to the group, which belongs to a
     * specific index, is currently enabled, or not.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose enable state should be checked, belongs
     *         to, as an {@link Integer} value. The value must be between 0 and the value of the
     *         method <code>getGroupCount():int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @param child
     *         The child item, whose enable state should be checked, as an instance of the generic
     *         type ChildType. The child item may not be null. If the child item does not belong to
     *         the given group, a {@link NoSuchElementException} will be thrown
     * @return True, if the given child item is currently enabled, false otherwise
     */
    boolean isChildEnabled(int groupIndex, @NonNull ChildType child);

    /**
     * Returns the index of the first enabled child item of a specific group.
     *
     * @param group
     *         The group, the child item, whose index should be returned, belongs to, as an instance
     *         of the generic type GroupType. The group may not be null. If the group does not
     *         belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @return The index of the first enabled child item of the given group, as an {@link Integer}
     * value or -1, if no child item is currently enabled
     */
    int getFirstEnabledChildIndex(@NonNull GroupType group);

    /**
     * Returns the first enabled child item of a specific group.
     *
     * @param group
     *         The group, the child item, which should be returned, belongs to, as an instance of
     *         the generic type GroupType. The group may not be null. If the group does not belong
     *         to the adapter, a {@link NoSuchElementException} will be thrown
     * @return The first enabled child item of the given group, as an instance of the generic type
     * ChildType or null, if no child item is currently enabled
     */
    ChildType getFirstEnabledChild(@NonNull GroupType group);

    /**
     * Returns the index of the first enabled child item of the group, which belongs to a specific
     * index.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose index should be returned, belongs to,
     *         as an {@link Integer} value. The value must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return The index of the first enabled child item of the given group, as an {@link Integer}
     * value or -1, if no child item is currently enabled
     */
    int getFirstEnabledChildIndex(int groupIndex);

    /**
     * Returns the first enabled child item of the group, which belongs to a specific index.
     *
     * @param groupIndex
     *         The index of the group, the child item, which should be returned, belongs to, as an
     *         {@link Integer} value. The value must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return The first enabled child item of the given group, as an instance of the generic type
     * ChildType or null, if no child item is currently enabled
     */
    ChildType getFirstEnabledChild(int groupIndex);

    /**
     * Returns the index of the last enabled child item of a specific group.
     *
     * @param group
     *         The group, the child item, whose index should be returned, belongs to, as an instance
     *         of the generic type GroupType. The group may not be null. If the group does not
     *         belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @return The index of the last enabled child item of the given group, as an {@link Integer}
     * value or -1, if no child item is currently enabled
     */
    int getLastEnabledChildIndex(@NonNull GroupType group);

    /**
     * Returns the last enabled child item of a specific group.
     *
     * @param group
     *         The group, the child item, which should be returned, belongs to, as an instance of
     *         the generic type GroupType. The group may not be null. If the group does not belong
     *         to the adapter, a {@link NoSuchElementException} will be thrown
     * @return The last enabled child item of the given group, as an instance of the generic type
     * ChildType or null, if no child item is currently enabled
     */
    ChildType getLastEnabledChild(@NonNull GroupType group);

    /**
     * Returns the index of the last enabled child item of the group, which belongs to a specific
     * index.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose index should be returned, belongs to,
     *         as an {@link Integer} value. The value must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return The index of the last enabled child item of the given group, as an {@link Integer}
     * value or -1, if no child item is currently enabled
     */
    int getLastEnabledChildIndex(int groupIndex);

    /**
     * Returns the last enabled child item of the group, which belongs to a specific index.
     *
     * @param groupIndex
     *         The index of the group, the child item, which should be returned, belongs to, as an
     *         {@link Integer} value. The value must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return The last enabled child item of the given group, as an instance of the generic type
     * ChildType or null, if no child item is currently enabled
     */
    ChildType getLastEnabledChild(int groupIndex);

    /**
     * Returns the index of the first disabled child item of a specific group.
     *
     * @param group
     *         The group, the child item, whose index should be returned, belongs to, as an instance
     *         of the generic type GroupType. The group may not be null. If the group does not
     *         belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @return The index of the first disabled child item of the given group, as an {@link Integer}
     * value or -1, if no child item is currently disabled
     */
    int getFirstDisabledChildIndex(@NonNull GroupType group);

    /**
     * Returns the first disabled child item of a specific group.
     *
     * @param group
     *         The group, the child item, which should be returned, belongs to, as an instance of
     *         the generic type GroupType. The group may not be null. If the group does not belong
     *         to the adapter, a {@link NoSuchElementException} will be thrown
     * @return The first disabled child item of the given group, as an instance of the generic type
     * ChildType or null, if no child item is currently disabled
     */
    ChildType getFirstDisabledChild(@NonNull GroupType group);

    /**
     * Returns the index of the first disabled child item of the group, which belongs to a specific
     * index.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose index should be returned, belongs to,
     *         as an {@link Integer} value. The value must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return The index of the first disabled child item of the given group, as an {@link Integer}
     * value or -1, if no child item is currently disabled
     */
    int getFirstDisabledChildIndex(int groupIndex);

    /**
     * Returns the first disabled child item of the group, which belongs to a specific index.
     *
     * @param groupIndex
     *         The index of the group, the child item, which should be returned, belongs to, as an
     *         {@link Integer} value. The value must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return The first disabled child item of the given group, as an instance of the generic type
     * ChildType or null, if no child item is currently disabled
     */
    ChildType getFirstDisabledChild(int groupIndex);

    /**
     * Returns the index of the last disabled child item of a specific group.
     *
     * @param group
     *         The group, the child item, whose index should be returned, belongs to, as an instance
     *         of the generic type GroupType. The group may not be null. If the group does not
     *         belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @return The index of the last disabled child item of the given group, as an {@link Integer}
     * value or -1, if no child item is currently disabled
     */
    int getLastDisabledChildIndex(@NonNull GroupType group);

    /**
     * Returns the last disabled child item of a specific group.
     *
     * @param group
     *         The group, the child item, which should be returned, belongs to, as an instance of
     *         the generic type GroupType. The group may not be null. If the group does not belong
     *         to the adapter, a {@link NoSuchElementException} will be thrown
     * @return The last disabled child item of the given group, as an instance of the generic type
     * ChildType or null, if no child item is currently disabled
     */
    ChildType getLastDisabledChild(@NonNull GroupType group);

    /**
     * Returns the index of the last disabled child item of the group, which belongs to a specific
     * index.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose index should be returned, belongs to,
     *         as an {@link Integer} value. The value must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return The index of the last disabled child item of the given group, as an {@link Integer}
     * value or -1, if no child item is currently disabled
     */
    int getLastDisabledChildIndex(int groupIndex);

    /**
     * Returns the last disabled child item of the group, which belongs to a specific index.
     *
     * @param groupIndex
     *         The index of the group, the child item, which should be returned, belongs to, as an
     *         {@link Integer} value. The value must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return The last disabled child item of the given group, as an instance of the generic type
     * ChildType or null, if no child item is currently disabled
     */
    ChildType getLastDisabledChild(int groupIndex);

    /**
     * Returns a list, which contains all currently enabled child items, regardless of the group
     * they belong to.
     *
     * @return A list, which contains all currently enabled child items, as an instance of the type
     * {@link List} or an empty list, if no child item is currently enabled
     */
    List<ChildType> getEnabledChildren();

    /**
     * Returns a list, which contains the indices of all currently enabled child items of a specific
     * group.
     *
     * @param group
     *         The group, the child items, whose indices should be returned, belong to, as an
     *         instance of the generic type GroupType. The group may not be null. If the group does
     *         not belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @return A list, which contains the indices of all currently enabled child items of the given
     * group, as an instance of the type {@link List} or an empty list, if no child item is
     * currently enabled
     */
    List<Integer> getEnabledChildIndices(@NonNull GroupType group);

    /**
     * Returns a list, which contains all currently enabled child items of a specific group.
     *
     * @param group
     *         The group, the child items, which should be returned, belong to, as an instance of
     *         the generic type GroupType. The group may not be null. If the group does not belong
     *         to the adapter, a {@link NoSuchElementException} will be thrown
     * @return A list, which contains all currently enabled child items of the given group, as an
     * instance of the type {@link List} or an empty list, if no child item is currently enabled
     */
    List<ChildType> getEnabledChildren(@NonNull GroupType group);

    /**
     * Returns a list, which contains the indices of all currently enabled child items of a specific
     * group.
     *
     * @param groupIndex
     *         The index of the group, the child items, whose indices should be returned, belong to,
     *         as an {@link Integer} value. The value must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return A list, which contains the indices of all currently enabled child items of the given
     * group, as an instance of the type {@link List} or an empty list, if no child item is
     * currently enabled
     */
    List<Integer> getEnabledChildIndices(int groupIndex);

    /**
     * Returns a list, which contains all currently enabled child items of a specific group.
     *
     * @param groupIndex
     *         The index of the group, the child items, which should be returned, belong to, as an
     *         {@link Integer} value. The value must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return A list, which contains all currently enabled child items of the given group, as an
     * instance of the type {@link List} or an empty list, if no child item is currently enabled
     */
    List<ChildType> getEnabledChildren(int groupIndex);

    /**
     * Returns a list, which contains all currently disabled child items, regardless of the group
     * they belong to.
     *
     * @return A list, which contains all currently disabled child items, as an instance of the type
     * {@link List} or an empty list, if no child item is currently disabled
     */
    List<ChildType> getDisabledChildren();

    /**
     * Returns a list, which contains the indices of all currently disabled child items of a
     * specific group.
     *
     * @param group
     *         The group, the child items, whose indices should be returned, belong to, as an
     *         instance of the generic type GroupType. The group may not be null. If the group does
     *         not belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @return A list, which contains the indices of all currently disabled child items of the given
     * group, as an instance of the type {@link List} or an empty list, if no child item is
     * currently disabled
     */
    List<Integer> getDisabledChildIndices(@NonNull GroupType group);

    /**
     * Returns a list, which contains all currently disabled child items of a specific group.
     *
     * @param group
     *         The group, the child items, which should be returned, belong to, as an instance of
     *         the generic type GroupType. The group may not be null. If the group does not belong
     *         to the adapter, a {@link NoSuchElementException} will be thrown
     * @return A list, which contains all currently disabled child items of the given group, as an
     * instance of the type {@link List} or an empty list, if no child item is currently disabled
     */
    List<ChildType> getDisabledChildren(@NonNull GroupType group);

    /**
     * Returns a list, which contains the indices of all currently disabled child items of a
     * specific group.
     *
     * @param groupIndex
     *         The index of the group, the child items, whose indices should be returned, belong to,
     *         as an {@link Integer} value. The value must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return A list, which contains the indices of all currently disabled child items of the given
     * group, as an instance of the type {@link List} or an empty list, if no child item is
     * currently disabled
     */
    List<Integer> getDisabledChildIndices(int groupIndex);

    /**
     * Returns a list, which contains all currently disabled child items of a specific group.
     *
     * @param groupIndex
     *         The index of the group, the child items, which should be returned, belong to, as an
     *         {@link Integer} value. The value must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return A list, which contains all currently disabled child items of the given group, as an
     * instance of the type {@link List} or an empty list, if no child item is currently disabled
     */
    List<ChildType> getDisabledChildren(int groupIndex);

    /**
     * Returns the number of currently enabled children, regardless of the group they belong to.
     *
     * @return The number of currently enabled children, as an {@link Integer} value
     */
    int getEnabledChildCount();

    /**
     * Returns the number of currently enabled children of a specific group.
     *
     * @param group
     *         The group, the child items, which should be counted, belong to, as an instance of the
     *         generic type GroupType. The group may not be null. If the group does not belong to
     *         the adapter, a {@link NoSuchElementException} will be thrown
     * @return The number of currently enabled children of the given group, as an {@link Integer}
     * value
     */
    int getEnabledChildCount(@NonNull GroupType group);

    /**
     * Returns the number of currently enabled children of the group, which belongs to a specific
     * index.
     *
     * @param groupIndex
     *         The index of the group, the child items, which should be counted, belong to, as an
     *         {@link Integer} value. The value must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return The number of currently enabled children of the given group, as an {@link Integer}
     * value
     */
    int getEnabledChildCount(int groupIndex);

    /**
     * Sets the enable state of the child item, which belongs to a specific index of a specific
     * group.
     *
     * @param group
     *         The group, the child item, whose enable state should be set, belongs to, as an
     *         instance of the generic type GroupType. The group may not be null. If the group does
     *         not belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @param childIndex
     *         The index of the child item, whose enable state should be set, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getChildCount(group):int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @param enabled
     *         True, if the child item, which belongs to the given index, should be enabled, false
     *         otherwise
     */
    void setChildEnabled(@NonNull GroupType group, int childIndex, boolean enabled);

    /**
     * Sets the enable state of a specific child item of a specific group.
     *
     * @param group
     *         The group, the child item, whose enable state should be set, belongs to, as an
     *         instance of the generic type GroupType. The group may not be null. If the group does
     *         not belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @param child
     *         The child item, which should be enabled, as an instance of the generic type
     *         ChildType. The child item may not be null. If the child item does not belong to the
     *         adapter, a {@link NoSuchElementException} will be thrown
     * @param enabled
     *         True, if the given child item should be enabled, false otherwise
     */
    void setChildEnabled(@NonNull GroupType group, @NonNull ChildType child, boolean enabled);

    /**
     * Sets the enable state of the child item, which belongs to a specific index of a specific
     * group.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose enable state should be set, belongs to,
     *         as an {@link Integer} value. The value must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @param childIndex
     *         The index of the child item, whose enable state should be set, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getChildCount(groupIndex):int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @param enabled
     *         True, if the child item, which belongs to the given index, should be enabled, false
     *         otherwise
     */
    void setChildEnabled(int groupIndex, int childIndex, boolean enabled);

    /**
     * Sets the enable state of a specific child item of a specific group.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose enable state should be set, belongs to,
     *         as an {@link Integer} value. The value must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @param child
     *         The child item, which should be enabled, as an instance of the generic type
     *         ChildType. The child item may not be null. If the child item does not belong to the
     *         adapter, a {@link NoSuchElementException} will be thrown
     * @param enabled
     *         True, if the given child item should be enabled, false otherwise
     */
    void setChildEnabled(int groupIndex, @NonNull ChildType child, boolean enabled);

    /**
     * Triggers the enable state of the child item, which belongs to a specific index of a specific
     * group. This causes the child item to become disabled, if it is currently enabled and vice
     * versa.
     *
     * @param group
     *         The group, the child item, whose enable state should be triggered, belongs to, as an
     *         instance of the generic type GroupType. The group may not be null. If the group does
     *         not belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @param childIndex
     *         The index of the child item, whose enable state should be triggered, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getChildCount(group):int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @return True, if the child item has been enabled, false, if the child item has been disabled
     */
    boolean triggerChildEnableState(@NonNull GroupType group, int childIndex);

    /**
     * Triggers the enable state of a specific child item of a specific group. This causes the child
     * item to become disabled, if it is currently enabled and vice versa.
     *
     * @param group
     *         The group, the child item, whose enable state should be triggered, belongs to, as an
     *         instance of the generic type GroupType. The group may not be null. If the group does
     *         not belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @param childItem
     *         The child item, whose enable state should be triggered, as an instance of the generic
     *         type ChildType. The child item may not be null. If the child item does not belong to
     *         the adapter, a {@link NoSuchElementException} will be thrown
     * @return True, if the child item has been enabled, false, if the child item has been disabled
     */
    boolean triggerChildEnableState(@NonNull GroupType group, @NonNull ChildType childItem);

    /**
     * Triggers the enable state of the child item, which belongs to a specific index of a specific
     * group. This causes the child item to become disabled, if it is currently enabled and vice
     * versa.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose enable state should be triggered,
     *         belongs to, as an {@link Integer} value. The value must be between 0 and the value of
     *         the method <code>getGroupCount():int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @param childIndex
     *         The index of the child item, whose enable state should be triggered, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getChildCount(groupIndex):int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @return True, if the child item has been enabled, false, if the child item has been disabled
     */
    boolean triggerChildEnableState(int groupIndex, int childIndex);

    /**
     * Triggers the enable state of a specific child item of a specific group. This causes the child
     * item to become disabled, if it is currently enabled and vice versa.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose enable state should be triggered,
     *         belongs to, as an {@link Integer} value. The value must be between 0 and the value of
     *         the method <code>getGroupCount():int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @param childItem
     *         The child item, whose enable state should be triggered, as an instance of the generic
     *         type ChildType. The child item may not be null. If the child item does not belong to
     *         the adapter, a {@link NoSuchElementException} will be thrown
     * @return True, if the child item has been enabled, false, if the child item has been disabled
     */
    boolean triggerChildEnableState(int groupIndex, @NonNull ChildType childItem);

    /**
     * Sets the enable states of all child items, regardless of the group they belong to.
     *
     * @param enabled
     *         True, if all child items should be enabled, false otherwise
     */
    void setAllChildrenEnabled(boolean enabled);

    /**
     * Sets the enable states of all child items of a specific group.
     *
     * @param group
     *         The group, the child items, whose enable states should be set, belong to, as an
     *         instance of the generic type GroupType. The group may not be null. If the group does
     *         not belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @param enabled
     *         True, if all child items of the given group should be enabled, false otherwise
     */
    void setAllChildrenEnabled(@NonNull GroupType group, boolean enabled);

    /**
     * Sets the enable states of all child items of the group, which belongs to a specific index.
     *
     * @param groupIndex
     *         The index of the group, the child items, whose enable states should be set, belong
     *         to, as an {@link Integer} value. The value must be between 0 and the value of the
     *         method <code>getGroupCount():int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @param enabled
     *         True, if all child items of the given group should be enabled, false otherwise
     */
    void setAllChildrenEnabled(int groupIndex, boolean enabled);

    /**
     * Triggers the enable states of all child items, regardless of the group they belong to. This
     * causes a child item to become disabled, if it is currently enabled and vice versa.
     */
    void triggerAllChildEnableStates();

    /**
     * Triggers the enable states of all child items of a specific group. This causes a child item
     * to become disabled, if it is currently enabled and vice versa.
     *
     * @param group
     *         The group, the child items, whose enable states should be triggered, belong to, as an
     *         instance of the generic type GroupType. The group may not be null. If the group does
     *         not belong to the adapter, a {@link NoSuchElementException} will be thrown
     */
    void triggerAllChildEnableStates(@NonNull GroupType group);

    /**
     * Triggers the enable states of all child items of the group, which belongs to a specific
     * index. This causes a child item to become disabled, if it is currently enabled and vice
     * versa.
     *
     * @param groupIndex
     *         The index of the group, the child items, whose enable states should be triggered,
     *         belong to, as an {@link Integer} value. The value must be between 0 and the value of
     *         the method <code>getGroupCount():int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     */
    void triggerAllChildEnableStates(int groupIndex);

    /**
     * Returns, whether the enable states of children are also set, when the enable state of the
     * group, they belong to, is set.
     *
     * @return True, if the enable states of children are also set, when the enable state of the
     * group, they belong to, is set, false otherwise
     */
    boolean areChildEnableStatesSetImplicitly();

    /**
     * Sets, whether the enable states of children should also be set, when the enable state of the
     * group, they belong to, is set.
     *
     * @param setChildEnableStatesImplicitly
     *         True, if the enable states of children should also be set, when the enable state of
     *         the group, they belong to, is set, false otherwise
     */
    void setChildEnableStatesImplicitly(boolean setChildEnableStatesImplicitly);

    /**
     * Adds a new listener, which should be notified, when a group item has been disabled or
     * enabled.
     *
     * @param listener
     *         The listener, which should be added, as an instance of the type {@link
     *         ExpandableListEnableStateListener}. The listener may not be null
     */
    void addEnableStateListener(
            @NonNull ExpandableListEnableStateListener<GroupType, ChildType> listener);

    /**
     * Removes a specific listener, which should not be notified, when a group item has been
     * disabled or enabled, anymore.
     *
     * @param listener
     *         The listener, which should be removed, as an instance of the type {@link
     *         ExpandableListEnableStateListener}. The listener may not be null
     */
    void removeEnableStateListener(
            @NonNull ExpandableListEnableStateListener<GroupType, ChildType> listener);

}