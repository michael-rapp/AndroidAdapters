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
package de.mrapp.android.adapter.expandablelist.itemstate;

import java.util.Collection;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list
 * of arbitrary group and child items, which may have different states, must
 * implement. Such an adapter's purpose is to provide the underlying data for
 * visualization using a {@link ExpandableListView} widget.
 * 
 * @param <GroupType>
 *            The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *            The type of the underlying data of the adapter's child items
 * 
 * @author Michael Rapp
 * 
 * @since 0.1.0
 */
public interface ItemStateExpandableListAdapter<GroupType, ChildType> {

	/**
	 * Returns the number of states, the adapter's group items may have.
	 * 
	 * @return The number of states, the adapter's group items may have, as an
	 *         {@link Integer} value. The value must be at least 1
	 */
	int getNumberOfGroupStates();

	/**
	 * Sets the number of states, the adapter's group items may have. If the
	 * number of states is set to a value, less than the previous value, the
	 * states of the group items, which became obsolete, will be set to the
	 * maximum state.
	 * 
	 * @param numberOfGroupStates
	 *            The number of states, which should be set, as an
	 *            {@link Integer} value. The value must be at least 1, otherwise
	 *            an {@link IllegalArgumentException} will be thrown
	 */
	void setNumberOfGroupStates(int numberOfGroupStates);

	/**
	 * Returns the minimum state, the adapter's group items may have.
	 * 
	 * @return The minimum state, the adapter's group items may have, as an
	 *         {@link Integer} value
	 */
	int minGroupState();

	/**
	 * Returns the maximum state, the adapter's group items may have.
	 * 
	 * @return The maximum state, the adapter's group items may have, as an
	 *         {@link Integer} value
	 */
	int maxGroupState();

	/**
	 * Returns the current state of the group item, which belongs to a specific
	 * index.
	 * 
	 * @param groupIndex
	 *            The index of the group item, whose state should be returned,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getGroupCount():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @return The state of the group item, which belongs to the given index, as
	 *         a {@link Integer} value
	 */
	int getGroupState(int groupIndex);

	/**
	 * Returns the current state of a specific group item.
	 * 
	 * @param group
	 *            The group item, whose state should be returned, as an instance
	 *            of the generic type GroupType. The group item may not be null.
	 *            If the group item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return The state of the given group item as an {@link Integer} value
	 */
	int getGroupState(GroupType group);

	/**
	 * Sets the state of the group item, which belongs to a specific index, to a
	 * specific state, if it is currently enabled.
	 * 
	 * @param groupIndex
	 *            The index of the group item, whose state should be set, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getGroupCount():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param state
	 *            The state, which should be set, as an {@link Integer} value.
	 *            The state must be between 0 and the value of the method
	 *            <code>getNumberOfGroupStates():int</code> - 1, otherwise an
	 *            {@link IllegalArgumentException} will be thrown
	 * @return The previous state of the group item, which belongs to the given
	 *         index, as an {@link Integer} value or -1, if the state has not
	 *         been changed
	 */
	int setGroupState(int groupIndex, int state);

	/**
	 * Sets the state of a specific group item to a specific state, if it is
	 * currently enabled.
	 * 
	 * @param group
	 *            The group item, whose state should be set, as an instance of
	 *            the generic type GroupType. The group item may not be null. If
	 *            the group item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param state
	 *            The state, which should be set, as an {@link Integer} value.
	 *            The state must be between 0 and the value of the method
	 *            <code>getNumberOfGroupStates():int</code> - 1, otherwise an
	 *            {@link IllegalArgumentException} will be thrown
	 * @return The previous state of the given group item as an {@link Integer}
	 *         value or -1, if the state has not been changed
	 */
	int setGroupState(GroupType group, int state);

	/**
	 * Sets the states of all group items to a specific state, if they are
	 * currently enabled.
	 * 
	 * @param state
	 *            The state, which should be set, as an {@link Integer} value.
	 *            The state must be between 0 and the value of the method
	 *            <code>getNumberOfGroupStates():int</code> - 1, otherwise an
	 *            {@link IllegalArgumentException} will be thrown
	 * @return True, if the states of all group items have been changed, false
	 *         otherwise
	 */
	boolean setAllGroupStates(int state);

	/**
	 * Triggers the state of the group item, which belongs to a specific index,
	 * if it is currently enabled. This causes the state to be increased by one.
	 * If the state is already the maximum state, the state will be set to 0
	 * instead.
	 * 
	 * @param groupIndex
	 *            The index of the group item, whose state should be triggered,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getGroupCount():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @return The previous state of the group item, which belongs to the given
	 *         index, as an {@link Integer} value or -1, if the state has not
	 *         been changed
	 */
	int triggerGroupState(int groupIndex);

	/**
	 * Triggers the state of the group item, which belongs to a specific index,
	 * if it is currently enabled. This causes the state to be increased by one.
	 * If the state is already the maximum state, the state will be set to 0
	 * instead.
	 * 
	 * @param triggerChildStates
	 *            True, if the states of the group's children should also be
	 *            triggered, false otherwise
	 * @param groupIndex
	 *            The index of the group item, whose state should be triggered,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getGroupCount():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @return The previous state of the group item, which belongs to the given
	 *         index, as an {@link Integer} value or -1, if the state has not
	 *         been changed
	 */
	int triggerGroupState(boolean triggerChildStates, int groupIndex);

	/**
	 * Triggers the state of a specific group item, if it is currently enabled.
	 * This causes the state to be increased by one. If the state is already the
	 * maximum state, the state will be set to 0 instead.
	 * 
	 * @param group
	 *            The group item, whose state should be triggered, as an
	 *            instance of the generic type GroupType. The group item may not
	 *            be null. If the group item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return The previous state of the given group item, as an {@link Integer}
	 *         value or -1, if the state has not been changed
	 */
	int triggerGroupState(GroupType group);

	/**
	 * Triggers the state of a specific group item, if it is currently enabled.
	 * This causes the state to be increased by one. If the state is already the
	 * maximum state, the state will be set to 0 instead.
	 * 
	 * @param triggerChildStates
	 *            True, if the states of the group's children should also be
	 *            triggered, false otherwise
	 * @param group
	 *            The group item, whose state should be triggered, as an
	 *            instance of the generic type GroupType. The group item may not
	 *            be null. If the group item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return The previous state of the given group item, as an {@link Integer}
	 *         value or -1, if the state has not been changed
	 */
	int triggerGroupState(boolean triggerChildStates, GroupType group);

	/**
	 * Triggers the states of all group items, regardless of the group they
	 * belong to, if they are currently enabled. This causes the states to be
	 * increased by one. If a state is already the maximum state, the state will
	 * be set to 0 instead.
	 * 
	 * @return True, if the states of all group items have been changed, false
	 *         otherwise
	 */
	boolean triggerAllGroupStates();

	/**
	 * Triggers the states of all group items, regardless of the group they
	 * belong to, if they are currently enabled. This causes the states to be
	 * increased by one. If a state is already the maximum state, the state will
	 * be set to 0 instead.
	 * 
	 * @param triggerChildStates
	 *            True, if the states of the groups' children should also be
	 *            triggered, false otherwise
	 * @return True, if the states of all group items have been changed, false
	 *         otherwise
	 */
	boolean triggerAllGroupStates(boolean triggerChildStates);

	/**
	 * Returns the index of the first group item, which currently has a specific
	 * state.
	 * 
	 * @param state
	 *            The state of the group item, whose index should be returned,
	 *            as an {@link Integer} value
	 * @return The index of the first group item, which currently has the given
	 *         state, as an {@link Integer} value or -1, if the adapter does not
	 *         contain a group item with the given state
	 */
	int getFirstGroupIndexWithSpecificState(int state);

	/**
	 * Returns the first group item, which currently has a specific state.
	 * 
	 * @param state
	 *            The state of the group item, which should be returned, as an
	 *            {@link Integer} value
	 * @return The first group item, which currently has the given state, as an
	 *         instance of the generic type GroupType or null, if the adapter
	 *         does not contain a group item with the given state
	 */
	GroupType getFirstGroupWithSpecificState(int state);

	/**
	 * Returns the index of the last group item, which currently has a specific
	 * state.
	 * 
	 * @param state
	 *            The state of the group item, whose index should be returned,
	 *            as an {@link Integer} value
	 * @return The index of the last group item, which currently has the given
	 *         state, as an {@link Integer} value or -1, if the adapter does not
	 *         contain a group item with the given state
	 */
	int getLastGroupIndexWithSpecificState(int state);

	/**
	 * Returns the last group item, which currently has a specific state.
	 * 
	 * @param state
	 *            The state of the group item, which should be returned, as an
	 *            {@link Integer} value
	 * @return The last group item, which currently has the given state, as an
	 *         instance of the generic type GroupType or null, if the adapter
	 *         does not contain a group item with the given state
	 */
	GroupType getLastGroupWithSpecificState(int state);

	/**
	 * Returns a collection, which contains the indices of all group items,
	 * which currently have a specific state.
	 * 
	 * @param state
	 *            The state of the group items, whose indices should be
	 *            returned, as an {@link Integer} value
	 * @return A collection, which contains the indices of all group items,
	 *         which currently have a specific state, as an instance of the type
	 *         {@link Collection} or an empty collection, if the adapter does
	 *         not contain any group items with the given state
	 */
	Collection<Integer> getGroupIndicesWithSpecificState(int state);

	/**
	 * Returns a collection, which contains all group items, which currently
	 * have a specific state.
	 * 
	 * @param state
	 *            The state of the group items, which should be returned, as an
	 *            {@link Integer} value
	 * @return A collection, which contains the group items, which currently
	 *         have the given state, as an instance of the type
	 *         {@link Collection} or an empty collection, if the adapter
	 *         contains no group items with the given state
	 */
	Collection<GroupType> getGroupsWithSpecificState(int state);

	/**
	 * Returns the number of group items, which currently have a specific state.
	 * 
	 * @param state
	 *            The state of the group items, which should be counted, as an
	 *            {@link Integer} value
	 * @return The number of group items, which currently have the given state,
	 *         as an {@link Integer} value
	 */
	int getGroupStateCount(int state);

	/**
	 * Returns, whether the state of a group item is triggered, when it is
	 * clicked by the user, or not.
	 * 
	 * @return True, if the state of a group item is triggered, when it is
	 *         clicked by the user, false otherwise
	 */
	boolean isGroupStateTriggeredOnClick();

	/**
	 * Sets, whether the state of a group item should be triggered, when it is
	 * clicked by the user, or not.
	 * 
	 * @param triggerGroupStateOnClick
	 *            True, if the state of a group item should be triggered, when
	 *            it is clicked by the user, false otherwise
	 */
	void triggerGroupStateOnClick(boolean triggerGroupStateOnClick);

	/**
	 * Returns the number of states, the adapter's child items may have.
	 * 
	 * @return The number of states, the adapter's child items may have, as an
	 *         {@link Integer} value. The value must be at least 1
	 */
	int getNumberOfChildStates();

	/**
	 * Sets the number of states, the adapter's child items may have. If the
	 * number of states is set to a value, less than the previous value, the
	 * states of the child items, which became obsolete, will be set to the
	 * maximum state.
	 * 
	 * @param numberOfChildStates
	 *            The number of states, which should be set, as an
	 *            {@link Integer} value. The value must be at least 1, otherwise
	 *            an {@link IllegalArgumentException} will be thrown
	 */
	void setNumberOfChildStates(int numberOfChildStates);

	/**
	 * Returns the minimum state, the adapter's child items may have.
	 * 
	 * @return The minimum state, the adapter's child items may have, as an
	 *         {@link Integer} value
	 */
	int minChildState();

	/**
	 * Returns the maximum state, the adapter's child items may have.
	 * 
	 * @return The maximum state, the adapter's child items may have, as an
	 *         {@link Integer} value
	 */
	int maxChildState();

	/**
	 * Returns the current state of the child item, which belongs to a specific
	 * index of a specific group.
	 * 
	 * @param group
	 *            The group, the child item, whose state should be returned,
	 *            belongs to, as an instance of the generic type GroupType. The
	 *            group may not be null. If the group does not belong to the
	 *            adapter, a {@link NoSuchElementException} will be thrown
	 * @param childIndex
	 *            The index of the child item, whose state should be returned,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getChildCount(group):int</code>
	 *            - 1, otherwise an {@link IndexOutOfBoundsException} will be
	 *            thrown
	 * @return The state of the child item, which belongs to the given index, as
	 *         an {@link Integer} value
	 */
	int getChildState(GroupType group, int childIndex);

	/**
	 * Returns the current state of a specific child item, which belongs to a
	 * specific group.
	 * 
	 * @param group
	 *            The group, the child item, whose state should be returned,
	 *            belongs to, as an instance of the generic type GroupType. The
	 *            group may not be null. If the group does not belong to the
	 *            adapter, a {@link NoSuchElementException} will be thrown
	 * @param child
	 *            The child item, whose state should be returned, as an instance
	 *            of the generic type ChildType. The child may not be null. If
	 *            the child does not belong to the given group, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return The state of the given child item, as an {@link Integer} value
	 */
	int getChildState(GroupType group, ChildType child);

	/**
	 * Returns the current state of the child item, which belongs to a specific
	 * index of the group, which belongs to a specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose state should be
	 *            returned, belongs to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param childIndex
	 *            The index of the child item, whose state should be returned,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method
	 *            <code>getChildCount(groupIndex):int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return The state of the child item, which belongs to the given index, as
	 *         an {@link Integer} value
	 */
	int getChildState(int groupIndex, int childIndex);

	/**
	 * Returns the current state of a specific child item of the group, which
	 * belongs to a specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose state should be
	 *            returned, belongs to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param child
	 *            The child item, whose state should be returned, as an instance
	 *            of the generic type ChildType. The child may not be null. If
	 *            the child does not belong to the given group, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return The state of the child item, which belongs to the given index, as
	 *         an {@link Integer} value
	 */
	int getChildState(int groupIndex, ChildType child);

	/**
	 * Sets the state of the child item, which belongs to a specific index of a
	 * specific group, to a specific state, if it is currently enabled.
	 * 
	 * @param group
	 *            The group, the child item, whose state should be set, belongs
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @param childIndex
	 *            The index of the child item, whose state should be set, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getChildCount(group):int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param state
	 *            The state, which should be set, as an {@link Integer} value.
	 *            The state must be between 0 and the value of the method
	 *            <code>getNumberOfChildStates():int</code> - 1, otherwise an
	 *            {@link IllegalArgumentException} will be thrown
	 * @return The previous state of the child item, which belongs to the given
	 *         index, as an {@link Integer} value or -1, if the state has not
	 *         been changed
	 */
	int setChildState(GroupType group, int childIndex, int state);

	/**
	 * Sets the state of a specific child item, which belongs to a specific
	 * group, to a specific state, if it is currently enabled.
	 * 
	 * @param group
	 *            The group, the child item, whose state should be set, belongs
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @param child
	 *            The child item, whose state should be set, as an instance of
	 *            the generic type ChildType. The child may not be null. If the
	 *            child does not belong to the given group, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param state
	 *            The state, which should be set, as an {@link Integer} value.
	 *            The state must be between 0 and the value of the method
	 *            <code>getNumberOfChildStates():int</code> - 1, otherwise an
	 *            {@link IllegalArgumentException} will be thrown
	 * @return The previous state of the given child item, as an {@link Integer}
	 *         value or -1, if the state has not been changed
	 */
	int setChildState(GroupType group, ChildType child, int state);

	/**
	 * Sets the state of the child item, which belongs to a specific index of
	 * the group, which belongs to a specific index, to a specific state, if it
	 * is currently enabled.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose state should be
	 *            set, belongs to, as an {@link Integer} value. The value must
	 *            be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param childIndex
	 *            The index of the child item, whose state should be set, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getChildCount(group):int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param state
	 *            The state, which should be set, as an {@link Integer} value.
	 *            The state must be between 0 and the value of the method
	 *            <code>getNumberOfChildStates():int</code> - 1, otherwise an
	 *            {@link IllegalArgumentException} will be thrown
	 * @return The previous state of the child item, which belongs to the given
	 *         index, as an {@link Integer} value or -1, if the state has not
	 *         been changed
	 */
	int setChildState(int groupIndex, int childIndex, int state);

	/**
	 * Sets the state of a specific child item, which belongs to the group,
	 * which belongs to a specific index, to a specific state, if it is
	 * currently enabled.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose state should be
	 *            set, belongs to, as an {@link Integer} value. The value must
	 *            be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param child
	 *            The child item, whose state should be set, as an instance of
	 *            the generic type ChildType. The child may not be null. If the
	 *            child does not belong to the given group, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param state
	 *            The state, which should be set, as an {@link Integer} value.
	 *            The state must be between 0 and the value of the method
	 *            <code>getNumberOfChildStates():int</code> - 1, otherwise an
	 *            {@link IllegalArgumentException} will be thrown
	 * @return The previous state of the given child item as an {@link Integer}
	 *         value or -1, if the state has not been changed
	 */
	int setChildState(int groupIndex, ChildType child, int state);

	/**
	 * Sets the states of all child items, regardless of the group the belong
	 * to, if they are currently enabled.
	 * 
	 * @param state
	 *            The state, which should be set, as an {@link Integer} value.
	 *            The state must be between 0 and the value of the method
	 *            <code>getNumberOfChildStates():int</code> - 1, otherwise an
	 *            {@link IllegalArgumentException} will be thrown
	 * @return True, if the states of all child items have been changed, false
	 *         otherwise
	 */
	boolean setAllChildStates(int state);

	/**
	 * Sets the states of all child items, which belong to a specific group, to
	 * a specific state, if they are currently enabled.
	 * 
	 * @param group
	 *            The group, the child items, whose states should be set, belong
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @param state
	 *            The state, which should be set, as an {@link Integer} value.
	 *            The state must be between 0 and the value of the method
	 *            <code>getNumberOfChildStates():int</code> - 1, otherwise an
	 *            {@link IllegalArgumentException} will be thrown
	 * @return True, if the states of all child items of the given group have
	 *         been changed, false otherwise
	 */
	boolean setAllChildStates(GroupType group, int state);

	/**
	 * Sets the states of all child items, which belong to the group, which
	 * belongs to a specific index, to a specific state, if they are currently
	 * enabled.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items, whose states should
	 *            be set, belong to, as an {@link Integer} value. The value must
	 *            be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param state
	 *            The state, which should be set, as an {@link Integer} value.
	 *            The state must be between 0 and the value of the method
	 *            <code>getNumberOfChildStates():int</code> - 1, otherwise an
	 *            {@link IllegalArgumentException} will be thrown
	 * @return True, if the states of all child items of the given group have
	 *         been changed, false otherwise
	 */
	boolean setAllChildStates(int groupIndex, int state);

	/**
	 * Triggers the state of the child item, which belongs to a specific index
	 * of a specific group, if it is currently enabled. This causes the state to
	 * be increased by one. If the state is already the maximum state, the state
	 * will be set to 0 instead.
	 * 
	 * @param group
	 *            The group, the child item, whose state should be triggered,
	 *            belongs to, as an instance of the generic type GroupType. The
	 *            group may not be null. If the group does not belong to the
	 *            adapter, a {@link NoSuchElementException} will be thrown
	 * @param childIndex
	 *            The index of the child item, whose state should be triggered,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getChildCount(group):int</code>
	 *            - 1, otherwise an {@link IndexOutOfBoundsException} will be
	 *            thrown
	 * @return The previous state of the child item, which belongs to the given
	 *         index, as an {@link Integer} value or -1, if the state has not
	 *         been changed
	 */
	int triggerChildState(GroupType group, int childIndex);

	/**
	 * Triggers the state of the child item, which belongs to a specific group,
	 * if it is currently enabled. This causes the state to be increased by one.
	 * If the state is already the maximum state, the state will be set to 0
	 * instead.
	 * 
	 * @param group
	 *            The group, the child item, whose state should be triggered,
	 *            belongs to, as an instance of the generic type GroupType. The
	 *            group may not be null. If the group does not belong to the
	 *            adapter, a {@link NoSuchElementException} will be thrown
	 * @param child
	 *            The child item, whose state should be triggered, as an
	 *            instance of the generic type ChildType. The child may not be
	 *            null. If the child does not belong to the given group, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return The previous state of the given child item, as an {@link Integer}
	 *         value or -1, if the state has not been changed
	 */
	int triggerChildState(GroupType group, ChildType child);

	/**
	 * Triggers the state of the child item, which belongs to a specific index
	 * of the group, which belongs to a specific index, if it is currently
	 * enabled. This causes the state to be increased by one. If the state is
	 * already the maximum state, the state will be set to 0 instead.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose state should be
	 *            triggered, belongs to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param childIndex
	 *            The index of the child item, whose state should be triggered,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getChildCount(group):int</code>
	 *            - 1, otherwise an {@link IndexOutOfBoundsException} will be
	 *            thrown
	 * @return The previous state of the child item, which belongs to the given
	 *         index, as an {@link Integer} value or -1, if the state has not
	 *         been changed
	 */
	int triggerChildState(int groupIndex, int childIndex);

	/**
	 * Triggers the state of a specific child item, which belongs to the group,
	 * which belongs to a specific index, if it is currently enabled. This
	 * causes the state to be increased by one. If the state is already the
	 * maximum state, the state will be set to 0 instead.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose state should be
	 *            triggered, belongs to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param child
	 *            The child item, whose state should be triggered, as an
	 *            instance of the generic type ChildType. The child may not be
	 *            null. If the child does not belong to the given group, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return The previous state of the given child item as an {@link Integer}
	 *         value or -1, if the state has not been changed
	 */
	int triggerChildState(int groupIndex, ChildType child);

	/**
	 * Triggers the states of all child items, regardless of the group they
	 * belong to, if they are currently enabled. This causes the states to be
	 * increase by one. If a state is already the maximum state, the state will
	 * be set to 0 instead.
	 * 
	 * @return True, if the states of all child items have been changed, false
	 *         otherwise
	 */
	boolean triggerAllChildStates();

	/**
	 * Triggers the states of all child items, which belong to a specific group,
	 * if they are currently enabled. This causes the states to be increased by
	 * one. If a state is already the maximum state, the state will be set to 0
	 * instead.
	 * 
	 * @param group
	 *            The group, the child items, whose states should be triggered,
	 *            belong to, as an instance of the generic type GroupType. The
	 *            group may not be null. If the group does not belong to the
	 *            adapter, a {@link NoSuchElementException} will be thrown
	 * @return True, if the states of all child items of the given group have
	 *         been changed, false otherwise
	 */
	boolean triggerAllChildStates(GroupType group);

	/**
	 * Triggers the states of all child items, which belong to the group, which
	 * belongs to a specific index, if they are currently enabled. This causes
	 * the states to be increased by one. If a state is already the maximum
	 * state, the state will be set to 0 instead.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items, whose states should
	 *            be triggered, belong to, as an {@link Integer} value. The
	 *            value must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if the states of all child items of the given group have
	 *         been changed, false otherwise
	 */
	boolean triggerAllChildStates(int groupIndex);

	/**
	 * Returns the index of the first child item of a specific group, which
	 * currently has a specific state.
	 * 
	 * @param group
	 *            The group, the child item, whose index should be returned,
	 *            belongs to, as an instance of the generic type GroupType. The
	 *            group may not be null. If the group does not belong to the
	 *            adapter, a {@link NoSuchElementException} will be thrown
	 * @param state
	 *            The state of the child item, whose index should be returned,
	 *            as an {@link Integer} value
	 * @return The index of the first child item of the given group, which
	 *         currently has the given state, as an {@link Integer} value or -1,
	 *         if the group does not contain a child item with the given state
	 */
	int getFirstChildIndexWithSpecificState(GroupType group, int state);

	/**
	 * Returns the index of the first child item of the group, which belongs to
	 * a specific index, which currently has a specific state.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose index should be
	 *            returned, belongs to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param state
	 *            The state of the child item, whose index should be returned,
	 *            as an {@link Integer} value
	 * @return The index of the first child item of the given group, which
	 *         currently has the given state, as an {@link Integer} value or -1,
	 *         if the group does not contain a child item with the given state
	 */
	int getFirstChildIndexWithSpecificState(int groupIndex, int state);

	/**
	 * Returns the first child item of a specific group, which currently has a
	 * specific state.
	 * 
	 * @param group
	 *            The group, the child item, which should be returned, belongs
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @param state
	 *            The state of the child item, which should be returned, as an
	 *            {@link Integer} value
	 * @return The first child item of the given group, which currently has the
	 *         given state, as an instance of the generic type ChildType or
	 *         null, if the group does not contain a child item with the given
	 *         state
	 */
	ChildType getFirstChildWithSpecificState(GroupType group, int state);

	/**
	 * Returns the first child item of the group, which belongs to a specific
	 * index, which currently has a specific state.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, which should be
	 *            returned, belongs to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param state
	 *            The state of the child item, which should be returned, as an
	 *            {@link Integer} value
	 * @return The first child item of the given group, which currently has the
	 *         given state, as an instance of the generic type ChildType or
	 *         null, if the group does not contain a child item with the given
	 *         state
	 */
	ChildType getFirstChildWithSpecificState(int groupIndex, int state);

	/**
	 * Returns the index of the last child item of a specific group, which
	 * currently has a specific state.
	 * 
	 * @param group
	 *            The group, the child item, whose index should be returned,
	 *            belongs to, as an instance of the generic type GroupType. The
	 *            group may not be null. If the group does not belong to the
	 *            adapter, a {@link NoSuchElementException} will be thrown
	 * @param state
	 *            The state of the child item, whose index should be returned,
	 *            as an {@link Integer} value
	 * @return The index of the last child item of the given group, which
	 *         currently has the given state, as an {@link Integer} value or -1,
	 *         if the group does not contain a child item with the given state
	 */
	int getLastChildIndexWithSpecificState(GroupType group, int state);

	/**
	 * Returns the index of the last child item of the group, which belongs to a
	 * specific index, which currently has a specific state.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose index should be
	 *            returned, belongs to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param state
	 *            The state of the child item, whose index should be returned,
	 *            as an {@link Integer} value
	 * @return The index of the last child item of the given group, which
	 *         currently has the given state, as an {@link Integer} value or -1,
	 *         if the group does not contain a child item with the given state
	 */
	int getLastChildIndexWithSpecificState(int groupIndex, int state);

	/**
	 * Returns the last child item of a specific group, which currently has a
	 * specific state.
	 * 
	 * @param group
	 *            The group, the child item, which should be returned, belongs
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @param state
	 *            The state of the child item, which should be returned, as an
	 *            {@link Integer} value
	 * @return The last child item of the given group, which currently has the
	 *         given state, as an instance of the generic type ChildType or
	 *         null, if the group does not contain a child item with the given
	 *         state
	 */
	ChildType getLastChildWithSpecificState(GroupType group, int state);

	/**
	 * Returns the last child item of the group, which belongs to a specific
	 * index, which currently has a specific state.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, which should be
	 *            returned, belongs to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param state
	 *            The state of the child item, which should be returned, as an
	 *            {@link Integer} value
	 * @return The last child item of the given group, which currently has the
	 *         given state, as an instance of the generic type ChildType or
	 *         null, if the group does not contain a child item with the given
	 *         state
	 */
	ChildType getLastChildWithSpecificState(int groupIndex, int state);

	/**
	 * Returns a collection, which contains the indices of all child items of a
	 * specific group, which currently have a specific state.
	 * 
	 * @param group
	 *            The group, the child items, whose indices should be returned,
	 *            belong to, as an instance of the generic type GroupType. The
	 *            group may not be null. If the group does not belong to the
	 *            adapter, a {@link NoSuchElementException} will be thrown
	 * @param state
	 *            The state of the child items, whose indices should be
	 *            returned, as an {@link Integer} value
	 * @return A collection, which contains the indices of all child items of
	 *         the given group, which currently have a specific state, as an
	 *         instance of the type {@link Collection} or an empty collection,
	 *         if the group does not contain any child items with the given
	 *         state
	 */
	Collection<Integer> getChildIndicesWithSpecificState(GroupType group, int state);

	/**
	 * Returns a collection, which contains the indices of all child items of
	 * the group, which belongs to a specific index, which currently have a
	 * specific state.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items, whose indices should
	 *            be returned, belong to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param state
	 *            The state of the child items, whose indices should be
	 *            returned, as an {@link Integer} value
	 * @return A collection, which contains the indices of all child items of
	 *         the given group, which currently have a specific state, as an
	 *         instance of the type {@link Collection} or an empty collection,
	 *         if the group does not contain any child items with the given
	 *         state
	 */
	Collection<Integer> getChildIndicesWithSpecificState(int groupIndex, int state);

	/**
	 * Returns a collection, which contains all child items, regardless of the
	 * group they belong to, which currently have a specific state.
	 * 
	 * @param state
	 *            The state of the child items, which should be returned, as an
	 *            {@link Integer} value
	 * @return A collection, which contains all child items, which currently
	 *         have a specific state, as an instance of the type
	 *         {@link Collection} or an empty collection, if the adapter does
	 *         not contain any child items with the given state
	 */
	Collection<ChildType> getChildrenWithSpecificState(int state);

	/**
	 * Returns a collection, which contains all child items of a specific group,
	 * which currently have a specific state.
	 * 
	 * @param group
	 *            The group, the child items, which should be returned, belong
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @param state
	 *            The state of the child items, which should be returned, as an
	 *            {@link Integer} value
	 * @return A collection, which contains the all child items of the given
	 *         group, which currently have a specific state, as an instance of
	 *         the type {@link Collection} or an empty collection, if the group
	 *         does not contain any child items with the given state
	 */
	Collection<ChildType> getChildrenWithSpecificState(GroupType group, int state);

	/**
	 * Returns a collection, which contains all child items of the group, which
	 * belongs to a specific index, which currently have a specific state.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items, which should be
	 *            returned, belong to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param state
	 *            The state of the child items, which should be returned, as an
	 *            {@link Integer} value
	 * @return A collection, which contains the all child items of the given
	 *         group, which currently have a specific state, as an instance of
	 *         the type {@link Collection} or an empty collection, if the group
	 *         does not contain any child items with the given state
	 */
	Collection<ChildType> getChildrenWithSpecificState(int groupIndex, int state);

	/**
	 * Returns the number of child items, which currently have a specific state.
	 * 
	 * @param state
	 *            The state of the child items, which should be counted, as an
	 *            {@link Integer} value
	 * @return The number of child items, which currently have the given state,
	 *         as an {@link Integer} value
	 */
	int getChildStateCount(int state);

	/**
	 * Returns the number of child items of a specific group, which currently
	 * have a specific state.
	 * 
	 * @param group
	 *            The group, the child items, which should be counted, belong
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @param state
	 *            The state of the child items, which should be counted, as an
	 *            {@link Integer} value
	 * @return The number of child items of the given group, which currently
	 *         have the given state, as an {@link Integer} value
	 */
	int getChildStateCount(GroupType group, int state);

	/**
	 * Returns the number of child items of the group, which belongs to a
	 * specific index, which currently have a specific state.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items, which should be
	 *            counted, belong to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param state
	 *            The state of the child items, which should be counted, as an
	 *            {@link Integer} value
	 * @return The number of child items of the given group, which currently
	 *         have the given state, as an {@link Integer} value
	 */
	int getChildStateCount(int groupIndex, int state);

	/**
	 * Returns, whether the state of a child item is triggered, when it is
	 * clicked by the user, or not.
	 * 
	 * @return True, if the state of a child item is triggered, when it is
	 *         clicked by the user, false otherwise
	 */
	boolean isChildStateTriggeredOnClick();

	/**
	 * Sets, whether the state of a child item should be triggered, when it is
	 * clicked by the user, or not.
	 * 
	 * @param triggerChildStateOnClick
	 *            True, if the state of a child item should be triggered, when
	 *            it is clicked by the user, false otherwise
	 */
	void triggerChildStateOnClick(boolean triggerChildStateOnClick);

	/**
	 * Returns, whether the states of children are also set, when the state of
	 * the group, they belong to, is set.
	 * 
	 * @return True, if the states of children are also set, when the state of
	 *         the group, they belong to, is set, false otherwise
	 */
	boolean areChildStatesSetImplicitly();

	/**
	 * Sets, whether the states of children should also be set, when the state
	 * of the group, they belong to, is set.
	 * 
	 * @param setChildStatesImplicitly
	 *            True, if the states of children should balso be set, when the
	 *            state of the group, they belong to, is set, false otherwise
	 */
	void setChildStatesImplicitly(boolean setChildStatesImplicitly);

	/**
	 * Adds a new listener, which should be notified, when the state of an item
	 * has been changed.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            class {@link ExpandableListItemStateListener}. The listener
	 *            may not be null
	 */
	void addItemStateListener(final ExpandableListItemStateListener<GroupType, ChildType> listener);

	/**
	 * Removes a specific listener, which should not be notified, when the state
	 * of an item has been changed, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            class {@link ExpandableListItemStateListener}. The listener
	 *            may not be null
	 */
	void removeItemStateListener(ExpandableListItemStateListener<GroupType, ChildType> listener);

}