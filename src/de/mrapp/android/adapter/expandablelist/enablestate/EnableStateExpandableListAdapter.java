/*
 * AndroidAdapters Copyright 2014 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>. 
 */
package de.mrapp.android.adapter.expandablelist.enablestate;

import java.util.Collection;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list
 * of arbitrary group and child items, which may be enabled or disabled, must
 * implement. Such an adapter's purpose is to provide the underlying data for
 * visualization using an {@link ExpandableListView} widget.
 * 
 * @param <GroupType>
 *            The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *            The type of the underlying data of the adapter's child items
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface EnableStateExpandableListAdapter<GroupType, ChildType> {

	/**
	 * Returns, whether the group item, which belongs to a specific index, is
	 * currently enabled, or not.
	 * 
	 * @param groupIndex
	 *            The index of the group item, whose enable state should be
	 *            checked, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the method
	 *            <code>getNumberOfGroupItems():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if the group item, which belongs to the given index, is
	 *         currently enabled, false otherwise
	 */
	boolean isGroupEnabled(int groupIndex);

	/**
	 * Returns, whether a specific group item is currently enabled, or not.
	 * 
	 * @param group
	 *            The group item, whose enable state should be checked, as an
	 *            instance of the generic type GroupType. The group item may not
	 *            be null. If the group item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return True, if the given group item is currently enabled, false
	 *         otherwise
	 */
	boolean isGroupEnabled(GroupType group);

	/**
	 * Returns the index of the first enabled group item.
	 * 
	 * @return The index of the first enabled group item, as an {@link Integer}
	 *         value or -1, if no group item is currently enabled
	 */
	int getFirstEnabledGroupIndex();

	/**
	 * Returns the first enabled group item.
	 * 
	 * @return The first enabled group item, as an instance of the generic type
	 *         GroupType or null, if no group item is currently enabled
	 */
	GroupType getFirstEnabledGroup();

	/**
	 * Returns the index of the last enabled group item.
	 * 
	 * @return The index of the last enabled group item, as an {@link Integer}
	 *         value or -1, if no group item is currently enabled
	 */
	int getLastEnabledGroupIndex();

	/**
	 * Returns the last enabled group item.
	 * 
	 * @return The last enabled group item, as an instance of the generic type
	 *         GroupType or null, if no group item is currently enabled
	 */
	GroupType getLastEnabledGroup();

	/**
	 * Returns the index of the first disabled group item.
	 * 
	 * @return The index of the first disabled group item, as an {@link Integer}
	 *         value or -1, if no item is currently disabled
	 */
	int getFirstDisabledGroupIndex();

	/**
	 * Returns the first disabled group item.
	 * 
	 * @return The first disabled group item, as an instance of the generic type
	 *         GroupType or null, if no group item is currently disabled
	 */
	GroupType getFirstDisabledGroup();

	/**
	 * Returns the index of the last disabled group item.
	 * 
	 * @return The index of the last disabled group item, as an {@link Integer}
	 *         value or -1, if no group item is currently disabled
	 */
	int getLastDisabledGroupIndex();

	/**
	 * Returns the index of the last disabled group item.
	 * 
	 * @return The index of the last disabled group item, as an {@link Integer}
	 *         value or -1, if no group item is currently disabled
	 */
	GroupType getLastDisabledGroup();

	/**
	 * Returns a collection, which contains the indices of all currently enabled
	 * group items.
	 * 
	 * @return A collection, which contains the indices of all currently enabled
	 *         group items, as an instance of the type {@link Collection} or an
	 *         empty collection, if no group item is currently enabled
	 */
	Collection<Integer> getEnabledGroupIndices();

	/**
	 * Returns a collection, which contains all currently enabled group items.
	 * 
	 * @return A collection, which contains all currently enabled group items,
	 *         as an instance of the type {@link Collection} or an empty
	 *         collection, if no group item is currently enabled
	 */
	Collection<GroupType> getEnabledGroups();

	/**
	 * Returns a collection, which contains the indices of all currently
	 * disabled group items.
	 * 
	 * @return A collection, which contains the indices of all currently
	 *         disabled group items, as an instance of the type
	 *         {@link Collection} or an empty collection, if no group item is
	 *         currently disabled
	 */
	Collection<Integer> getDisabledGroupIndices();

	/**
	 * Returns a collection, which contains all currently disabled group items.
	 * 
	 * @return A collection, which contains all currently disabled group items,
	 *         as an instance of the type {@link Collection} or an empty
	 *         collection, if no group item is currently disabled
	 */
	Collection<GroupType> getDisabledGroups();

	/**
	 * Returns the number of currently enabled groups.
	 * 
	 * @return The number of currently enabled groups, as an {@link Integer}
	 *         value
	 */
	int getEnabledGroupCount();

	/**
	 * Sets the enable state of the group item, which belongs to a specific
	 * index.
	 * 
	 * @param groupIndex
	 *            The index of the group item, whose enable state should be set,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getNumberOfGroups():int</code> -
	 *            1, otherwise an {@link IndexOutOfBoundsException} will be
	 *            thrown
	 * @param enabled
	 *            True, if the group item, which belongs to the given index,
	 *            should be enabled, false otherwise
	 */
	void setGroupEnabled(int groupIndex, boolean enabled);

	/**
	 * Sets the enable state of the group item, which belongs to a specific
	 * index.
	 * 
	 * @param enableChildren
	 *            True, if the enable states of the group item's children should
	 *            also be set, false otherwise
	 * @param groupIndex
	 *            The index of the group item, whose enable state should be set,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getNumberOfGroups():int</code> -
	 *            1, otherwise an {@link IndexOutOfBoundsException} will be
	 *            thrown
	 * @param enabled
	 *            True, if the group item, which belongs to the given index,
	 *            should be enabled, false otherwise
	 */
	void setGroupEnabled(boolean enableChildren, int groupIndex, boolean enabled);

	/**
	 * Sets the enable state of a specific group item.
	 * 
	 * @param item
	 *            The group item, which should be enabled, as an instance of the
	 *            generic type GroupType. The group item may not be null. If the
	 *            group item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param enabled
	 *            True, if the given group item should be enabled, false
	 *            otherwise
	 */
	void setGroupEnabled(GroupType item, boolean enabled);

	/**
	 * Sets the enable state of a specific group item.
	 * 
	 * @param enableChildren
	 *            True, if the enable states of the group item's children should
	 *            also be set, false otherwise
	 * @param item
	 *            The group item, which should be enabled, as an instance of the
	 *            generic type GroupType. The group item may not be null. If the
	 *            group item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param enabled
	 *            True, if the given group item should be enabled, false
	 *            otherwise
	 */
	void setGroupEnabled(boolean enableChildren, GroupType item, boolean enabled);

	/**
	 * Triggers the enable state of the group item, which belongs to a specific
	 * index. This causes the group item to become disabled, if it is currently
	 * enabled and vice versa.
	 * 
	 * @param groupIndex
	 *            The index of the group item, whose enable state should be
	 *            triggered, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if the group item has been enabled, false, if the group
	 *         item has been disabled
	 */
	boolean triggerGroupEnableState(int groupIndex);

	/**
	 * Triggers the enable state of the group item, which belongs to a specific
	 * index. This causes the group item to become disabled, if it is currently
	 * enabled and vice versa.
	 * 
	 * @param triggerChildStates
	 *            True, if the enable states of the group item's children should
	 *            also be triggered, false otherwise
	 * @param groupIndex
	 *            The index of the group item, whose enable state should be
	 *            triggered, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if the group item has been enabled, false, if the group
	 *         item has been disabled
	 */
	boolean triggerGroupEnableState(boolean triggerChildStates, int groupIndex);

	/**
	 * Triggers the enable state of a specific group item. This causes the group
	 * item to become disabled, if it is currently enabled and vice versa.
	 * 
	 * @param item
	 *            The group item, whose enable state should be triggered, as an
	 *            instance of the generic type GroupType. The group item may not
	 *            be null. If the group item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return True, if the group item has been enabled, false, if the group
	 *         item has been disabled
	 */
	boolean triggerGroupEnableState(GroupType item);

	/**
	 * Triggers the enable state of a specific group item. This causes the group
	 * item to become disabled, if it is currently enabled and vice versa.
	 * 
	 * @param triggerChildStates
	 *            True, if the enable states of the group item's children should
	 *            also be triggered, false otherwise
	 * @param item
	 *            The group item, whose enable state should be triggered, as an
	 *            instance of the generic type GroupType. The group item may not
	 *            be null. If the group item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return True, if the group item has been enabled, false, if the group
	 *         item has been disabled
	 */
	boolean triggerGroupEnableState(boolean triggerChildStates, GroupType item);

	/**
	 * Sets the enable states of all group items.
	 * 
	 * @param enabled
	 *            True, if all group items should be enabled, false otherwise
	 */
	void setAllGroupsEnabled(boolean enabled);

	/**
	 * Sets the enable states of all group items.
	 * 
	 * @param enableChildren
	 *            True, if the enable states of the group items' children should
	 *            be also set, false otherwise
	 * @param enabled
	 *            True, if all group items should be enabled, false otherwise
	 */
	void setAllGroupsEnabled(boolean enableChildren, boolean enabled);

	/**
	 * Triggers the enable states of all group items. This causes a group item
	 * to become disabled, if it is currently enabled and vice versa.
	 */
	void triggerAllGroupEnableStates();

	/**
	 * Triggers the enable states of all group items. This causes a group item
	 * to become disabled, if it is currently enabled and vice versa.
	 * 
	 * @param triggerChildStates
	 *            True, if the enable states of the group items' children should
	 *            be also triggered, false otherwise
	 */
	void triggerAllGroupEnableStates(boolean triggerChildStates);

	/**
	 * Returns, whether the child item, which belongs to a specific index of a
	 * specific group, is currently enabled, or not.
	 * 
	 * @param group
	 *            The group, the child item, whose enable state should be
	 *            checked, belongs to, as an instance of the generic type
	 *            GroupType. The group may not be null. If the group does not
	 *            belong to the adapter, a {@link NoSuchElementException} will
	 *            be thrown
	 * @param childIndex
	 *            The index of the child item, whose enable state should be
	 *            checked, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the method
	 *            <code>getNumberOfChildren(group):int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if the child item, which belongs to the given index, is
	 *         currently enabled, false otherwise
	 */
	boolean isChildEnabled(GroupType group, int childIndex);

	/**
	 * Returns, whether a specific child item, which belongs to a specific
	 * group, is currently enabled, or not.
	 * 
	 * @param group
	 *            The group, the child item, whose enable state should be
	 *            checked, belongs to, as an instance of the generic type
	 *            GroupType. The group may not be null. If the group does not
	 *            belong to the adapter, a {@link NoSuchElementException} will
	 *            be thrown
	 * @param child
	 *            The child item, whose enable state should be checked, as an
	 *            instance of the generic type ChildType. The child item may not
	 *            be null. If the child item does not belong to the given group,
	 *            a {@link NoSuchElementException} will be thrown
	 * @return True, if the given child item is currently enabled, false
	 *         otherwise
	 */
	boolean isChildEnabled(GroupType group, ChildType child);

	/**
	 * Returns, whether the child item, which belongs to the group, which
	 * belongs to a specific index, is currently enabled, or not.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose enable state
	 *            should be checked, belongs to, as an {@link Integer} value.
	 *            The value must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param childIndex
	 *            The index of the child item, whose enable state should be
	 *            checked, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the method
	 *            <code>getNumberOfChildren(groupIndex):int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if the child item, which belongs to the given index, is
	 *         currently enabled, false otherwise
	 */
	boolean isChildEnabled(int groupIndex, int childIndex);

	/**
	 * Returns, whether a specific child item, which belongs to the group, which
	 * belongs to a specific index, is currently enabled, or not.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose enable state
	 *            should be checked, belongs to, as an {@link Integer} value.
	 *            The value must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param child
	 *            The child item, whose enable state should be checked, as an
	 *            instance of the generic type ChildType. The child item may not
	 *            be null. If the child item does not belong to the given group,
	 *            a {@link NoSuchElementException} will be thrown
	 * @return True, if the given child item is currently enabled, false
	 *         otherwise
	 */
	boolean isChildEnabled(int groupIndex, ChildType child);

	/**
	 * Returns the index of the first enabled child item of a specific group.
	 * 
	 * @param group
	 *            The group, the child item, whose index should be returned,
	 *            belongs to, as an instance of the generic type GroupType. The
	 *            group may not be null. If the group does not belong to the
	 *            adapter, a {@link NoSuchElementException} will be thrown
	 * @return The index of the first enabled child item of the given group, as
	 *         an {@link Integer} value or -1, if no child item is currently
	 *         enabled
	 */
	int getFirstEnabledChildIndex(GroupType group);

	/**
	 * Returns the first enabled child item of a specific group.
	 * 
	 * @param group
	 *            The group, the child item, which should be returned, belongs
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @return The first enabled child item of the given group, as an instance
	 *         of the generic type ChildType or null, if no child item is
	 *         currently enabled
	 */
	ChildType getFirstEnabledChild(GroupType group);

	/**
	 * Returns the index of the first enabled child item of the group, which
	 * belongs to a specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose index should be
	 *            returned, belongs to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return The index of the first enabled child item of the given group, as
	 *         an {@link Integer} value or -1, if no child item is currently
	 *         enabled
	 */
	int getFirstEnabledChildIndex(int groupIndex);

	/**
	 * Returns the first enabled child item of the group, which belongs to a
	 * specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, which should be
	 *            returned, belongs to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return The first enabled child item of the given group, as an instance
	 *         of the generic type ChildType or null, if no child item is
	 *         currently enabled
	 */
	ChildType getFirstEnabledChild(int groupIndex);

	/**
	 * Returns the index of the last enabled child item of a specific group.
	 * 
	 * @param group
	 *            The group, the child item, whose index should be returned,
	 *            belongs to, as an instance of the generic type GroupType. The
	 *            group may not be null. If the group does not belong to the
	 *            adapter, a {@link NoSuchElementException} will be thrown
	 * @return The index of the last enabled child item of the given group, as
	 *         an {@link Integer} value or -1, if no child item is currently
	 *         enabled
	 */
	int getLastEnabledChildIndex(GroupType group);

	/**
	 * Returns the last enabled child item of a specific group.
	 * 
	 * @param group
	 *            The group, the child item, which should be returned, belongs
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @return The last enabled child item of the given group, as an instance of
	 *         the generic type ChildType or null, if no child item is currently
	 *         enabled
	 */
	ChildType getLastEnabledChild(GroupType group);

	/**
	 * Returns the index of the last enabled child item of the group, which
	 * belongs to a specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose index should be
	 *            returned, belongs to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return The index of the last enabled child item of the given group, as
	 *         an {@link Integer} value or -1, if no child item is currently
	 *         enabled
	 */
	int getLastEnabledChildIndex(int groupIndex);

	/**
	 * Returns the last enabled child item of the group, which belongs to a
	 * specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, which should be
	 *            returned, belongs to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return The last enabled child item of the given group, as an instance of
	 *         the generic type ChildType or null, if no child item is currently
	 *         enabled
	 */
	ChildType getLastEnabledChild(int groupIndex);

	/**
	 * Returns the index of the first disabled child item of a specific group.
	 * 
	 * @param group
	 *            The group, the child item, whose index should be returned,
	 *            belongs to, as an instance of the generic type GroupType. The
	 *            group may not be null. If the group does not belong to the
	 *            adapter, a {@link NoSuchElementException} will be thrown
	 * @return The index of the first disabled child item of the given group, as
	 *         an {@link Integer} value or -1, if no child item is currently
	 *         disabled
	 */
	int getFirstDisabledChildIndex(GroupType group);

	/**
	 * Returns the first disabled child item of a specific group.
	 * 
	 * @param group
	 *            The group, the child item, which should be returned, belongs
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @return The first disabled child item of the given group, as an instance
	 *         of the generic type ChildType or null, if no child item is
	 *         currently disabled
	 */
	ChildType getFirstDisabledChild(GroupType group);

	/**
	 * Returns the index of the first disabled child item of the group, which
	 * belongs to a specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose index should be
	 *            returned, belongs to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return The index of the first disabled child item of the given group, as
	 *         an {@link Integer} value or -1, if no child item is currently
	 *         disabled
	 */
	int getFirstDisabledChildIndex(int groupIndex);

	/**
	 * Returns the first disabled child item of the group, which belongs to a
	 * specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, which should be
	 *            returned, belongs to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return The first disabled child item of the given group, as an instance
	 *         of the generic type ChildType or null, if no child item is
	 *         currently disabled
	 */
	ChildType getFirstDisabledChild(int groupIndex);

	/**
	 * Returns the index of the last disabled child item of a specific group.
	 * 
	 * @param group
	 *            The group, the child item, whose index should be returned,
	 *            belongs to, as an instance of the generic type GroupType. The
	 *            group may not be null. If the group does not belong to the
	 *            adapter, a {@link NoSuchElementException} will be thrown
	 * @return The index of the last disabled child item of the given group, as
	 *         an {@link Integer} value or -1, if no child item is currently
	 *         disabled
	 */
	int getLastDisabledChildIndex(GroupType group);

	/**
	 * Returns the last disabled child item of a specific group.
	 * 
	 * @param group
	 *            The group, the child item, which should be returned, belongs
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @return The last disabled child item of the given group, as an instance
	 *         of the generic type ChildType or null, if no child item is
	 *         currently disabled
	 */
	ChildType getLastDisabledChild(GroupType group);

	/**
	 * Returns the index of the last disabled child item of the group, which
	 * belongs to a specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose index should be
	 *            returned, belongs to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return The index of the last disabled child item of the given group, as
	 *         an {@link Integer} value or -1, if no child item is currently
	 *         disabled
	 */
	int getLastDisabledChildIndex(int groupIndex);

	/**
	 * Returns the last disabled child item of the group, which belongs to a
	 * specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, which should be
	 *            returned, belongs to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return The last disabled child item of the given group, as an instance
	 *         of the generic type ChildType or null, if no child item is
	 *         currently disabled
	 */
	ChildType getLastDisabledChild(int groupIndex);

	/**
	 * Returns a collection, which contains all currently enabled child items,
	 * regardless of the group they belong to.
	 * 
	 * @return A collection, which contains all currently enabled child items,
	 *         as an instance of the type {@link Collection} or an empty
	 *         collection, if no child item is currently enabled
	 */
	Collection<ChildType> getEnabledChildren();

	/**
	 * Returns a collection, which contains the indices of all currently enabled
	 * child items of a specific group.
	 * 
	 * @param group
	 *            The group, the child items, whose indices should be returned,
	 *            belong to, as an instance of the generic type GroupType. The
	 *            group may not be null. If the group does not belong to the
	 *            adapter, a {@link NoSuchElementException} will be thrown
	 * @return A collection, which contains the indices of all currently enabled
	 *         child items of the given group, as an instance of the type
	 *         {@link Collection} or an empty collection, if no child item is
	 *         currently enabled
	 */
	Collection<Integer> getEnabledChildIndices(GroupType group);

	/**
	 * Returns a collection, which contains all currently enabled child items of
	 * a specific group.
	 * 
	 * @param group
	 *            The group, the child items, which should be returned, belong
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @return A collection, which contains all currently enabled child items of
	 *         the given group, as an instance of the type {@link Collection} or
	 *         an empty collection, if no child item is currently enabled
	 */
	Collection<ChildType> getEnabledChildren(GroupType group);

	/**
	 * Returns a collection, which contains the indices of all currently enabled
	 * child items of a specific group.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items, whose indices should
	 *            be returned, belong to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return A collection, which contains the indices of all currently enabled
	 *         child items of the given group, as an instance of the type
	 *         {@link Collection} or an empty collection, if no child item is
	 *         currently enabled
	 */
	Collection<Integer> getEnabledChildIndices(int groupIndex);

	/**
	 * Returns a collection, which contains all currently enabled child items of
	 * a specific group.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items, which should be
	 *            returned, belong to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return A collection, which contains all currently enabled child items of
	 *         the given group, as an instance of the type {@link Collection} or
	 *         an empty collection, if no child item is currently enabled
	 */
	Collection<ChildType> getEnabledChildren(int groupIndex);

	/**
	 * Returns a collection, which contains all currently disabled child items,
	 * regardless of the group they belong to.
	 * 
	 * @return A collection, which contains all currently disabled child items,
	 *         as an instance of the type {@link Collection} or an empty
	 *         collection, if no child item is currently disabled
	 */
	Collection<ChildType> getDisabledChildren();

	/**
	 * Returns a collection, which contains the indices of all currently
	 * disabled child items of a specific group.
	 * 
	 * @param group
	 *            The group, the child items, whose indices should be returned,
	 *            belong to, as an instance of the generic type GroupType. The
	 *            group may not be null. If the group does not belong to the
	 *            adapter, a {@link NoSuchElementException} will be thrown
	 * @return A collection, which contains the indices of all currently
	 *         disabled child items of the given group, as an instance of the
	 *         type {@link Collection} or an empty collection, if no child item
	 *         is currently disabled
	 */
	Collection<Integer> getDisabledChildIndices(GroupType group);

	/**
	 * Returns a collection, which contains all currently disabled child items
	 * of a specific group.
	 * 
	 * @param group
	 *            The group, the child items, which should be returned, belong
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @return A collection, which contains all currently disabled child items
	 *         of the given group, as an instance of the type {@link Collection}
	 *         or an empty collection, if no child item is currently disabled
	 */
	Collection<ChildType> getDisabledChildren(GroupType group);

	/**
	 * Returns a collection, which contains the indices of all currently
	 * disabled child items of a specific group.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items, whose indices should
	 *            be returned, belong to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return A collection, which contains the indices of all currently
	 *         disabled child items of the given group, as an instance of the
	 *         type {@link Collection} or an empty collection, if no child item
	 *         is currently disabled
	 */
	Collection<Integer> getDisabledChildIndices(int groupIndex);

	/**
	 * Returns a collection, which contains all currently disabled child items
	 * of a specific group.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items, which should be
	 *            returned, belong to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return A collection, which contains all currently disabled child items
	 *         of the given group, as an instance of the type {@link Collection}
	 *         or an empty collection, if no child item is currently disabled
	 */
	Collection<ChildType> getDisabledChildren(int groupIndex);

	/**
	 * Returns the number of currently enabled children, regardless of the group
	 * they belong to.
	 * 
	 * @return The number of currently enabled children, as an {@link Integer}
	 *         value
	 */
	int getEnabledChildCount();

	/**
	 * Returns the number of currently enabled children of a specific group.
	 * 
	 * @param group
	 *            The group, the child items, which should be counted, belong
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @return The number of currently enabled children of the given group, as
	 *         an {@link Integer} value
	 */
	int getEnabledChildCount(GroupType group);

	/**
	 * Returns the number of currently enabled children of the group, which
	 * belongs to a specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items, which should be
	 *            counted, belong to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return The number of currently enabled children of the given group, as
	 *         an {@link Integer} value
	 */
	int getEnabledChildCount(int groupIndex);

	/**
	 * Sets the enable state of the child item, which belongs to a specific
	 * index of a specific group.
	 * 
	 * @param group
	 *            The group, the child item, whose enable state should be set,
	 *            belongs to, as an instance of the generic type GroupType. The
	 *            group may not be null. If the group does not belong to the
	 *            adapter, a {@link NoSuchElementException} will be thrown
	 * @param childIndex
	 *            The index of the child item, whose enable state should be set,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method
	 *            <code>getNumberOfChildren(group):int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param enabled
	 *            True, if the child item, which belongs to the given index,
	 *            should be enabled, false otherwise
	 */
	void setChildEnabled(GroupType group, int childIndex, boolean enabled);

	/**
	 * Sets the enable state of a specific child item of a specific group.
	 * 
	 * @param group
	 *            The group, the child item, whose enable state should be set,
	 *            belongs to, as an instance of the generic type GroupType. The
	 *            group may not be null. If the group does not belong to the
	 *            adapter, a {@link NoSuchElementException} will be thrown
	 * @param child
	 *            The child item, which should be enabled, as an instance of the
	 *            generic type ChildType. The child item may not be null. If the
	 *            child item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param enabled
	 *            True, if the given child item should be enabled, false
	 *            otherwise
	 */
	void setChildEnabled(GroupType group, ChildType child, boolean enabled);

	/**
	 * Sets the enable state of the child item, which belongs to a specific
	 * index of a specific group.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose enable state
	 *            should be set, belongs to, as an {@link Integer} value. The
	 *            value must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param childIndex
	 *            The index of the child item, whose enable state should be set,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method
	 *            <code>getNumberOfChildren(groupIndex):int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param enabled
	 *            True, if the child item, which belongs to the given index,
	 *            should be enabled, false otherwise
	 */
	void setChildEnabled(int groupIndex, int childIndex, boolean enabled);

	/**
	 * Sets the enable state of a specific child item of a specific group.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose enable state
	 *            should be set, belongs to, as an {@link Integer} value. The
	 *            value must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param child
	 *            The child item, which should be enabled, as an instance of the
	 *            generic type ChildType. The child item may not be null. If the
	 *            child item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param enabled
	 *            True, if the given child item should be enabled, false
	 *            otherwise
	 */
	void setChildEnabled(int groupIndex, ChildType child, boolean enabled);

	/**
	 * Triggers the enable state of the child item, which belongs to a specific
	 * index of a specific group. This causes the child item to become disabled,
	 * if it is currently enabled and vice versa.
	 * 
	 * @param group
	 *            The group, the child item, whose enable state should be
	 *            triggered, belongs to, as an instance of the generic type
	 *            GroupType. The group may not be null. If the group does not
	 *            belong to the adapter, a {@link NoSuchElementException} will
	 *            be thrown
	 * @param childIndex
	 *            The index of the child item, whose enable state should be
	 *            triggered, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the method
	 *            <code>getNumberOfChildren(group):int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if the child item has been enabled, false, if the child
	 *         item has been disabled
	 */
	boolean triggerChildEnableState(GroupType group, int childIndex);

	/**
	 * Triggers the enable state of a specific child item of a specific group.
	 * This causes the child item to become disabled, if it is currently enabled
	 * and vice versa.
	 * 
	 * @param group
	 *            The group, the child item, whose enable state should be
	 *            triggered, belongs to, as an instance of the generic type
	 *            GroupType. The group may not be null. If the group does not
	 *            belong to the adapter, a {@link NoSuchElementException} will
	 *            be thrown
	 * @param childItem
	 *            The child item, whose enable state should be triggered, as an
	 *            instance of the generic type ChildType. The child item may not
	 *            be null. If the child item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return True, if the child item has been enabled, false, if the child
	 *         item has been disabled
	 */
	boolean triggerChildEnableState(GroupType group, ChildType childItem);

	/**
	 * Triggers the enable state of the child item, which belongs to a specific
	 * index of a specific group. This causes the child item to become disabled,
	 * if it is currently enabled and vice versa.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose enable state
	 *            should be triggered, belongs to, as an {@link Integer} value.
	 *            The value must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param childIndex
	 *            The index of the child item, whose enable state should be
	 *            triggered, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the method
	 *            <code>getNumberOfChildren(groupIndex):int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if the child item has been enabled, false, if the child
	 *         item has been disabled
	 */
	boolean triggerChildEnableState(int groupIndex, int childIndex);

	/**
	 * Triggers the enable state of a specific child item of a specific group.
	 * This causes the child item to become disabled, if it is currently enabled
	 * and vice versa.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose enable state
	 *            should be triggered, belongs to, as an {@link Integer} value.
	 *            The value must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param childItem
	 *            The child item, whose enable state should be triggered, as an
	 *            instance of the generic type ChildType. The child item may not
	 *            be null. If the child item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return True, if the child item has been enabled, false, if the child
	 *         item has been disabled
	 */
	boolean triggerChildEnableState(int groupIndex, ChildType childItem);

	/**
	 * Sets the enable states of all child items, regardless of the group they
	 * belong to.
	 * 
	 * @param enabled
	 *            True, if all child items should be enabled, false otherwise
	 */
	void setAllChildrenEnabled(boolean enabled);

	/**
	 * Sets the enable states of all child items of a specific group.
	 * 
	 * @param group
	 *            The group, the child items, whose enable states should be set,
	 *            belong to, as an instance of the generic type GroupType. The
	 *            group may not be null. If the group does not belong to the
	 *            adapter, a {@link NoSuchElementException} will be thrown
	 * @param enabled
	 *            True, if all child items of the given group should be enabled,
	 *            false otherwise
	 */
	void setAllChildrenEnabled(GroupType group, boolean enabled);

	/**
	 * Sets the enable states of all child items of the group, which belongs to
	 * a specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items, whose enable states
	 *            should be set, belong to, as an {@link Integer} value. The
	 *            value must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param enabled
	 *            True, if all child items of the given group should be enabled,
	 *            false otherwise
	 */
	void setAllChildrenEnabled(int groupIndex, boolean enabled);

	/**
	 * Triggers the enable states of all child items, regardless of the group
	 * they belong to. This causes a child item to become disabled, if it is
	 * currently enabled and vice versa.
	 */
	void triggerAllChildEnableStates();

	/**
	 * Triggers the enable states of all child items of a specific group. This
	 * causes a child item to become disabled, if it is currently enabled and
	 * vice versa.
	 * 
	 * @param group
	 *            The group, the child items, whose enable states should be
	 *            triggered, belong to, as an instance of the generic type
	 *            GroupType. The group may not be null. If the group does not
	 *            belong to the adapter, a {@link NoSuchElementException} will
	 *            be thrown
	 */
	void triggerAllChildEnableStates(GroupType group);

	/**
	 * Triggers the enable states of all child items of the group, which belongs
	 * to a specific index. This causes a child item to become disabled, if it
	 * is currently enabled and vice versa.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items, whose enable states
	 *            should be triggered, belong to, as an {@link Integer} value.
	 *            The value must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 */
	void triggerAllChildEnableStates(int groupIndex);

	/**
	 * Adds a new listener, which should be notified, when a group item has been
	 * disabled or enabled.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            type {@link ExpandableListEnableStateListener}. The listener
	 *            may not be null
	 */
	void addEnableStateListener(ExpandableListEnableStateListener<GroupType, ChildType> listener);

	/**
	 * Removes a specific listener, which should not be notified, when a group
	 * item has been disabled or enabled, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            type {@link ExpandableListEnableStateListener}. The listener
	 *            may not be null
	 */
	void removeEnableStateListener(ExpandableListEnableStateListener<GroupType, ChildType> listener);

}