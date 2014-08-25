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
package de.mrapp.android.adapter;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

import de.mrapp.android.adapter.datastructure.DataStructure;
import de.mrapp.android.adapter.datastructure.Restorable;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterListener;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list
 * of arbitrary group and child items, must implement. Such an adapter's purpose
 * is to provide the underlying data for visualization using a
 * {@link ExpandableListView} widget.
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
public interface ExpandableListAdapter<GroupType, ChildType> extends
		DataStructure, Restorable, android.widget.ExpandableListAdapter {

	/**
	 * Returns, whether duplicate group items are allowed, or not.
	 * 
	 * @return True, if duplicate group items are allowed, false otherwise
	 */
	boolean areDuplicateGroupsAllowed();

	/**
	 * Sets, whether duplicate group items should be allowed, or not.
	 * 
	 * @param allowDuplicateGroups
	 *            True , if duplicate group items should be allowed, false
	 *            otherwise
	 */
	void allowDuplicateGroups(boolean allowDuplicateGroups);

	/**
	 * Adds a specific group item to the adapter. The item will be added at the
	 * end.
	 * 
	 * @param group
	 *            The group item, which should be added to the adapter, as an
	 *            instance of the generic type GroupType. The group item may not
	 *            be null
	 * @return True, if the group item has been added to the adapter, false
	 *         otherwise
	 */
	boolean addGroup(GroupType group);

	/**
	 * Adds a specific group item to the adapter. The group item will be added
	 * at a specific index.
	 * 
	 * @param index
	 *            The index, the group item should be added at, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfGroups():int</code>,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param group
	 *            The group item, which should be added to the adapter, as an
	 *            instance of the generic type GroupType. The group item may not
	 *            be null
	 * @return True, if the group item has been added to the adapter, false
	 *         otherwise
	 */
	boolean addGroup(int index, GroupType group);

	/**
	 * Adds all group items, which are contained by a specific collection, to
	 * the adapter. The group items will be added in a consecutive order at the
	 * end.
	 * 
	 * @param groups
	 *            The collection, which contains the group items, which should
	 *            be added to the adapter, as an instance of the type
	 *            {@link Collection} or an empty collection, if no group items
	 *            should be added
	 * @return True, if all group items have been added to the adapter, false
	 *         otherwise
	 */
	boolean addAllGroups(Collection<GroupType> groups);

	/**
	 * Adds all group items, which are contained by a specific collection, to
	 * the adapter. The group items will be added in a consecutive order at a
	 * specific index.
	 * 
	 * @param index
	 *            The index, the group items should be added at, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfGroups():int</code>,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param groups
	 *            The collection, which contains the group items, which should
	 *            be added to the adapter, as an instance of the type
	 *            {@link Collection} or an empty collection, if no group items
	 *            should be added
	 * @return True, if all items have been added to the adapter, false
	 *         otherwise
	 */
	boolean addAllGroups(int index, Collection<GroupType> groups);

	/**
	 * Adds all group items, which are contained by a specific array, to the
	 * adapter. The group items will be added in a consecutive order at the end.
	 * 
	 * @param groups
	 *            The array, which contains the group items, which should be
	 *            added to the adapter, as an array of the generic type
	 *            GroupType or an empty array, if no group items should be added
	 * @return True, if all group items have been added to the adapter, false
	 *         otherwise
	 */
	boolean addAllGroups(GroupType... groups);

	/**
	 * Adds all group items, which are contained by a specific array, to the
	 * adapter. The group items will be added in a consecutive order at a
	 * specific index.
	 * 
	 * @param index
	 *            The index, the group items should be added at, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfGroups():int</code>,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param groups
	 *            The array, which contains the group items, which should be
	 *            added to the adapter, as an array of the generic type
	 *            GroupType or an empty array, if no group items should be added
	 * @return True, if all group items have been added to the adapter, false
	 *         otherwise
	 */
	boolean addAllGroups(int index, GroupType... groups);

	/**
	 * Replaces the group item, which belongs to a specific index, by an other
	 * group item. The group's children will be retained.
	 * 
	 * @param index
	 *            The index of the group item, which should be replaced, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfGroups():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param group
	 *            The group item, which should replace the group item at the
	 *            given index, as an instance of the generic type GroupType. The
	 *            group item may not be null
	 * @return The group item, which has been replaced, as an instance of the
	 *         generic type GroupType. The group item may not be null
	 */
	GroupType replaceGroup(int index, GroupType group);

	/**
	 * Removes the group item, which belongs to a specific index, from the
	 * adapter. The group's children will be also removed from the adapter.
	 * 
	 * @param index
	 *            The index of the group item, which should be removed from the
	 *            adapter, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return The group item, which has been removed, as an instance of the
	 *         generic type GroupType. The group item may not be null
	 */
	GroupType removeGroup(int index);

	/**
	 * Removes a specific group item from the adapter. The group's children will
	 * be also removed from the adapter.
	 * 
	 * @param group
	 *            The group item, which should be removed, as an instance of the
	 *            generic type GroupType. The group item may not be null
	 * @return True, if the group item has been removed, false otherwise
	 */
	boolean removeGroup(GroupType group);

	/**
	 * Removes all group items, which are contained by a specific collection,
	 * from the adapter. The groups' children will also be removed from the
	 * adapter.
	 * 
	 * @param groups
	 *            The collection, which contains the group items, which should
	 *            be removed from the adapter, as an instance of the type
	 *            {@link Collection} or an empty collection, if no group items
	 *            should be removed
	 * @return True, if all group items have been removed from the adapter,
	 *         false otherwise
	 */
	boolean removeAllGroups(Collection<GroupType> groups);

	/**
	 * Removes all group items, which are contained by a specific array, from
	 * the adapter. The groups' children will also be removed from the adapter.
	 * 
	 * @param groups
	 *            The array, which contains the group items, which should be
	 *            removed from the adapter, as an array of the generic type
	 *            GroupType or an empty array, if no group items should be
	 *            removed
	 * @return True, if all group items have been removed from the adapter,
	 *         false otherwise
	 */
	boolean removeAllGroups(GroupType... groups);

	/**
	 * Removes all group items from the adapter, except of the group items,
	 * which are contained by a specific collection. The removed groups'
	 * children will also be removed from the adapter.
	 * 
	 * @param groups
	 *            The collection, which contains the group items, which should
	 *            be retained, as an instance of the type {@link Collection} or
	 *            an empty collection, if no group items should be retained
	 */
	void retainAllGroups(Collection<GroupType> groups);

	/**
	 * Removes all group items from the adapter, except of the group items,
	 * which are contained by a specific array. The removed groups' children
	 * will also be removed from the adapter.
	 * 
	 * @param groups
	 *            The array, which contains the group items, which should be
	 *            retained, as an array of the generic type DataType or an empty
	 *            array, if no group items should be retained
	 */
	void retainAllGroups(GroupType... groups);

	/**
	 * Removes all groups and their child items from the adapter.
	 */
	void clearGroups();

	/**
	 * Returns an iterator, which allows to iterate the adapter's group items.
	 * 
	 * @return An iterator, which allows to iterate the adapter's group items,
	 *         as an instance of the type {@link Iterator}. The iterator may not
	 *         be null
	 */
	Iterator<GroupType> groupIterator();

	/**
	 * Returns a list iterator, which allows to iterate the adapter's group
	 * items.
	 * 
	 * @return A list iterator, which allows to iterate the adapter's group
	 *         items, as an instance of the type {@link ListIterator}. The
	 *         iterator may not be null
	 */
	ListIterator<GroupType> groupListIterator();

	/**
	 * Returns a list iterator, which allows to iterate the adapter's group
	 * items, starting at a specific index.
	 * 
	 * @param index
	 *            The index, the iterator should start at, as an {@link Integer}
	 *            value. The index must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return A list iterator, which allows to iterate the adapter's group
	 *         items, starting at the given index, as an instance of the type
	 *         {@link ListIterator}. The iterator may not be null
	 */
	ListIterator<GroupType> groupListIterator(int index);

	/**
	 * Returns a collection, which contains the adapter's group items between a
	 * specific start and end index.
	 * 
	 * @param start
	 *            The start index of the group items, which should be returned,
	 *            as an {@link Integer} value. The group item, which belongs to
	 *            the start index will be included. The index must be between 0
	 *            and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param end
	 *            The end index of the group items, which should be returned, as
	 *            an {@link Integer} value. The group item, which belongs to the
	 *            end index, will be excluded. The index must be between 0 and
	 *            the value of the method <code>getNumberOfGroups():int</code>
	 *            -1 and it must be greater than the start index, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return A collection, which contains the adapter's group items, between a
	 *         specific start end end index, as an instance of the type
	 *         {@link Collection} or an empty collection, if the adapter does
	 *         not contain any group items
	 */
	Collection<GroupType> subListGroups(int start, int end);

	/**
	 * Returns an array, which contains the adapter's group items.
	 * 
	 * @return An array, which contains the adapter's group items, as an
	 *         {@link Object} array or an empty array, if the adapter does not
	 *         contain any group items
	 */
	Object[] groupsToArray();

	/**
	 * Returns an array, which contains all of the adapter's group items. If the
	 * given array is large enough to hold the items, the specified array is
	 * used, otherwise an array of the same type is created. If the given array
	 * can hold more items, the array's elements, following the adapter's items,
	 * are set to null.
	 * 
	 * @param <T>
	 *            The type of the array, which should be returned
	 * @param array
	 *            The array, which should be used, if it is large enough, as an
	 *            array of the generic type T. The array may not be null
	 * @return An array, which contains all of the adapter's group item, as an
	 *         array of the generic type T or an empty array, if the adapter
	 *         does not contain any group items
	 */
	<T> T[] groupsToArray(T[] array);

	/**
	 * Returns the group item, which belongs to a specific index.
	 * 
	 * @param index
	 *            The index of the group item, which should be returned, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfGroups():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @return The group item, which belongs to the given index, as an instance
	 *         of the generic type GroupType. The group item may not be null
	 */
	GroupType getGroup(int index);

	/**
	 * Returns the index of a specific group item.
	 * 
	 * @param group
	 *            The group item, whose index should be returned, as an instance
	 *            of the generic type GroupType. The group item may not be null
	 * @return The index of the the given group item, as an {@link Integer}
	 *         value or -1, if the adapter does not contain the given group item
	 */
	int indexOfGroup(GroupType group);

	/**
	 * Returns the last index of a specific group item.
	 * 
	 * @param group
	 *            The group item, whose last index should be returned, as an
	 *            instance of the generic type GroupType. The group item may not
	 *            be null
	 * @return The last index of the given group item, as an {@link Integer}
	 *         value or -1, if the adapter does not contain the given group item
	 */
	int lastIndexOfGroup(GroupType group);

	/**
	 * Returns, whether the adapter contains a specific group item, or not.
	 * 
	 * @param group
	 *            The group item, whose presence should be checked, as an
	 *            instance of the generic type GroupType. The group item may not
	 *            be null
	 * @return True, if the adapter contains the given group item, false
	 *         otherwise
	 */
	boolean containsGroup(GroupType group);

	/**
	 * Returns, whether the adapter contains all group items, which are
	 * contained by a specific collection, or not.
	 * 
	 * @param groups
	 *            The collection, which contains the group items, which whose
	 *            presence should be checked, as an instance of the type
	 *            {@link Collection}. The collection may not be null
	 * @return True, if the adapter contains all group items, which are
	 *         contained by the given collection, false otherwise
	 */
	boolean containsAllGroups(Collection<GroupType> groups);

	/**
	 * Returns, whether the adapter contains all group items, which are
	 * contained by a specific array, or not.
	 * 
	 * @param groups
	 *            The array, which contains the group items, which whose
	 *            presence should be checked, as an array of the generic type
	 *            GroupType. The array may not be null
	 * @return True, if the adapter contains all group items, which are
	 *         contained by the given array, false otherwise
	 */
	boolean containsAllGroups(GroupType... groups);

	/**
	 * Returns the number of group items, which are contained by the adapter.
	 * 
	 * @return The number of group items, which are contained by the adapter, as
	 *         an {@link Integer} value
	 */
	int getNumberOfGroups();

	/**
	 * Returns a collection, which contains all of the adapter's group items.
	 * 
	 * @return A collection, which contains all of the adapter's group items, as
	 *         an instance of the type {@link Collection} or an empty
	 *         collection, if the adapter does not contain any group items
	 */
	Collection<GroupType> getAllGroups();

	/**
	 * Returns, whether the adapter is empty, or not.
	 * 
	 * @return True, if the adapter is empty, false otherwise
	 */
	boolean isEmpty();

	/**
	 * Returns, whether the group, which belongs to a specific index, is empty
	 * or not.
	 * 
	 * @param groupIndex
	 *            The index of the group, which should be checked, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfGroups():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if the group is empty, false otherwise
	 */
	boolean isGroupEmpty(int groupIndex);

	/**
	 * Returns, whether a specific group is empty or not.
	 * 
	 * @param group
	 *            The group, which should be checked, as an instance of the
	 *            generic type GroupType. The group may not be null. If the
	 *            group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return True, if the group is empty, false otherwise
	 */
	boolean isGroupEmpty(GroupType group);

	/**
	 * Returns, whether duplicate child items, regardless from the group they
	 * belong to, are allowed, or not.
	 * 
	 * @return True, if duplicate child items, regardless from the group they
	 *         belong to, are allowed, false otherwise
	 */
	boolean areDuplicateChildrenAllowed();

	/**
	 * Sets, whether duplicate child items, regardless from the group they
	 * belong to, should be allowed, or not.
	 * 
	 * @param allowDuplicateChildren
	 *            True, if duplicate child items, regardless from the group they
	 *            belong to, should be allowed, false otherwise
	 */
	void allowDuplicateChildren(boolean allowDuplicateChildren);

	/**
	 * Returns, whether duplicate child items within the group, which belongs to
	 * a specific index, are allowed, or not.
	 * 
	 * @param groupIndex
	 *            The index of the group, whose constraints regarding duplicate
	 *            child items should be checked, as an {@link Integer} value.
	 *            The index must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if duplicate child items are allowed within the group,
	 *         false otherwise
	 */
	boolean areDuplicateChildrenAllowed(int groupIndex);

	/**
	 * Sets, whether duplicate child items within the group, which belongs to a
	 * specific index, should be allowed, or not.
	 * 
	 * @param groupIndex
	 *            The index of the group, whose constraints regarding duplicate
	 *            child items should be set, as an {@link Integer} value. The
	 *            index must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param allowDuplicateChildren
	 *            True, if duplicate child items should be allowed within the
	 *            group, false otherwise
	 */
	void allowDuplicateChildren(int groupIndex, boolean allowDuplicateChildren);

	/**
	 * Returns, whether duplicate child items within a specific group are
	 * allowed, or not.
	 * 
	 * @param group
	 *            The group, whose constraints regarding duplicate child items
	 *            should be checked, as an instance of the generic type
	 *            GroupType. The group may not be null. If the group does not
	 *            belong to the adapter, a {@link NoSuchElementException} will
	 *            be thrown
	 * @return True, if duplicate child items are allowed within the group,
	 *         false otherwise
	 */
	boolean areDuplicateChildrenAllowed(GroupType group);

	/**
	 * Sets, whether duplicate child items within a specific group should be
	 * allowed, or not.
	 * 
	 * @param group
	 *            The group, whose constraints regarding duplicate child items
	 *            should be set, as an instance of the generic type GroupType.
	 *            The group may not be null. If the group does not belong to the
	 *            adapter, a {@link NoSuchElementException} will be thrown
	 * @param allowDuplicateChildren
	 *            True, if duplicate child items should be allowed within the
	 *            group, false otherwise
	 */
	void allowDuplicateChildren(GroupType group, boolean allowDuplicateChildren);

	/**
	 * Adds a specific child item to the group, which belongs to a specific
	 * index. The child item will be added at the end of the group.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item should be added to, as
	 *            an {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfGroups():int</code>,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param child
	 *            The child item, which should be added to the group, as an
	 *            instance of the generic type ChildType. The child item may not
	 *            be null
	 * @return True, if the child item has been added to the group, false
	 *         otherwise
	 */
	boolean addChild(int groupIndex, ChildType child);

	/**
	 * Adds a specific child item to a specific group. The child item will be
	 * added at the end of the group.
	 * 
	 * @param group
	 *            The group, the child item should be added to, as an instance
	 *            of the generic type GroupType. The group may not be null. If
	 *            the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param child
	 *            The child item, which should be added to the group, as an
	 *            instance of the generic type ChildType. The child item may not
	 *            be null
	 * @return True, if the child item has been added to the group, false
	 *         otherwise
	 */
	boolean addChild(GroupType group, ChildType child);

	/**
	 * Adds a specific child item to the group, which belongs to a specific
	 * index. The child item will be added at a specific index of the group.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item should be added to, as
	 *            an {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfGroups():int</code>,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param index
	 *            The index, the child item should be added at, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method
	 *            <code>getNumberOfChildren(groupIndex):int</code>, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param child
	 *            The child item, which should be added to the group, as an
	 *            instance of the generic type ChildType. The child item may not
	 *            be null
	 * @return True, if the child item has been added to the group, false
	 *         otherwise
	 */
	boolean addChild(int groupIndex, int index, ChildType child);

	/**
	 * Adds a specific child item to a specific group. The child item will be
	 * added at the end of the group. The child item will be added at a specific
	 * index of the group.
	 * 
	 * @param group
	 *            The group, the child item should be added to, as an instance
	 *            of the generic type GroupType. The group may not be null. If
	 *            the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param index
	 *            The index, the child item should be added at, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method
	 *            <code>getNumberOfChildren(group):int</code>, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param child
	 *            The child item, which should be added to the group, as an
	 *            instance of the generic type ChildType. The child item may not
	 *            be null
	 * @return True, if the child item has been added to the group, false
	 *         otherwise
	 */
	boolean addChild(GroupType group, int index, ChildType child);

	/**
	 * Adds all child items, which are contained by a specific collection, to
	 * the group, which belongs to a specific index. The items will be added in
	 * a consecutive order at the end of the group.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items should be added to, as
	 *            an {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfGroups():int</code>,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param children
	 *            The collection, which contains the child items, which should
	 *            be added to the group, as an instance of the type
	 *            {@link Collection} or an empty collection, if no child items
	 *            should be added
	 * @return True, if all child items have been added to the group, false
	 *         otherwise
	 */
	boolean addAllChildren(int groupIndex, Collection<ChildType> children);

	/**
	 * Adds all child items, which are contained by a specific collection, to a
	 * specific group. The items will be added in a consecutive order at the end
	 * of the group.
	 * 
	 * @param group
	 *            The group, the child items should be added to, as an instance
	 *            of the generic type GroupType. The group may not be null. If
	 *            the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param children
	 *            The collection, which contains the child items, which should
	 *            be added to the group, as an instance of the type
	 *            {@link Collection} or an empty collection, if no child items
	 *            should be added
	 * @return True, if all child items have been added to the group, false
	 *         otherwise
	 */
	boolean addAllChildren(GroupType group, Collection<ChildType> children);

	/**
	 * Adds all child items, which are contained by a specific collection, to
	 * the group, which belongs to a specific index. The items will be added in
	 * a consecutive order at a specific index of the group.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items should be added to, as
	 *            an {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfGroups():int</code>,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param index
	 *            The index, the child items should be added at, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method
	 *            <code>getNumberOfChildren(groupIndex):int</code>, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param children
	 *            The collection, which contains the child items, which should
	 *            be added to the group, as an instance of the type
	 *            {@link Collection} or an empty collection, if no child items
	 *            should be added
	 * @return True, if all child items have been added to the group, false
	 *         otherwise
	 */
	boolean addAllChildren(int groupIndex, int index,
			Collection<ChildType> children);

	/**
	 * Adds all child items, which are contained by a specific collection, to a
	 * specific group. The items will be added in a consecutive order at a
	 * specific index of the group.
	 * 
	 * @param group
	 *            The group, the child items should be added to, as an instance
	 *            of the generic type GroupType. The group may not be null. If
	 *            the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param index
	 *            The index, the child items should be added at, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method
	 *            <code>getNumberOfChildren(group):int</code>, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param children
	 *            The collection, which contains the child items, which should
	 *            be added to the group, as an instance of the type
	 *            {@link Collection} or an empty collection, if no child items
	 *            should be added
	 * @return True, if all child items have been added to the group, false
	 *         otherwise
	 */
	boolean addAllChildren(GroupType group, int index,
			Collection<ChildType> children);

	/**
	 * Adds all child items, which are contained by a specific array, to the
	 * group, which belongs to a specific index. The items will be added in a
	 * consecutive order at the end of the group.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items should be added to, as
	 *            an {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfGroups():int</code>,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param children
	 *            The array, which contains the child items, which should be
	 *            added to the group, as an array of the generic type ChildType
	 *            or an empty array, if no child items should be added
	 * @return True, if all child items have been added to the group, false
	 *         otherwise
	 */
	boolean addAllChildren(int groupIndex, ChildType... children);

	/**
	 * Adds all child items, which are contained by a specific array, to a
	 * specific group. The items will be added in a consecutive order at the end
	 * of the group.
	 * 
	 * @param group
	 *            The group, the child items should be added to, as an instance
	 *            of the generic type GroupType. The group may not be null. If
	 *            the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param children
	 *            The array, which contains the child items, which should be
	 *            added to the group, as an array of the generic type ChildType
	 *            or an empty array, if no child items should be added
	 * @return True, if all child items have been added to the group, false
	 *         otherwise
	 */
	boolean addAllChildren(GroupType group, ChildType... children);

	/**
	 * Adds all child items, which are contained by a specific array, to the
	 * group, which belongs to a specific index. The items will be added in a
	 * consecutive order at a specific index of the group.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items should be added to, as
	 *            an {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfGroups():int</code>,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param index
	 *            The index, the child items should be added at, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method
	 *            <code>getNumberOfChildren(groupIndex):int</code>, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param children
	 *            The array, which contains the child items, which should be
	 *            added to the group, as an array of the generic type ChildType
	 *            or an empty array, if no child items should be added
	 * @return True, if all child items have been added to the group, false
	 *         otherwise
	 */
	boolean addAllChildren(int groupIndex, int index, ChildType... children);

	/**
	 * Adds all child items, which are contained by a specific array, to a
	 * specific group. The items will be added in a consecutive order at a
	 * specific index of the group.
	 * 
	 * @param group
	 *            The group, the child items should be added to, as an instance
	 *            of the generic type GroupType. The group may not be null. If
	 *            the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param index
	 *            The index, the child items should be added at, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method
	 *            <code>getNumberOfChildren(group):int</code>, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param children
	 *            The array, which contains the child items, which should be
	 *            added to the group, as an array of the generic type ChildType
	 *            or an empty array, if no child items should be added
	 * @return True, if all child items have been added to the group, false
	 *         otherwise
	 */
	boolean addAllChildren(GroupType group, int index, ChildType... children);

	/**
	 * Replaces the child item, which belongs to a specific index of a specific
	 * group, by an other item.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, which should be
	 *            replaced, belongs to, as an {@link Integer} value. The index
	 *            must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code>, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param index
	 *            The index of the item, which should be replaced, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method
	 *            <code>getNumberOfChildren(groupIndex):int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param child
	 *            The child item, which should replace the child item at the
	 *            given index, as an instance of the generic type ChildType. The
	 *            child item may not be null
	 * @return The child item, which has been replaced, as an instance of the
	 *         generic type ChildType. The child item may not be null
	 */
	ChildType replaceChild(int groupIndex, int index, ChildType child);

	/**
	 * Replaces the child item, which belongs to a specific index of a specific
	 * group, by an other item.
	 * 
	 * @param group
	 *            The group, the child item, which should be replaced, belongs
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @param index
	 *            The index of the item, which should be replaced, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method
	 *            <code>getNumberOfChildren(group):int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param child
	 *            The child item, which should replace the child item at the
	 *            given index, as an instance of the generic type ChildType. The
	 *            child item may not be null
	 * @return The child item, which has been replaced, as an instance of the
	 *         generic type ChildType. The child item may not be null
	 */
	ChildType replaceChild(GroupType group, int index, ChildType child);

	/**
	 * Removes the child item, which belongs to a specific index, from the
	 * group, which belongs to a specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item should be removed from,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getNumberOfGroups():int</code>,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param index
	 *            The index of the child item, which should be removed from the
	 *            group, as an {@link Integer} value. The index must be between
	 *            0 and the value of the method
	 *            <code>getNumberOfChildren(groupIndex):int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @return The child item, which has been removed, as an instance of the
	 *         generic type ChildType. The item may not be null
	 */
	ChildType removeChild(int groupIndex, int index);

	/**
	 * Removes the child item, which belongs to a specific index, from a
	 * specific group.
	 * 
	 * @param group
	 *            The group, the child item should be removed from, as an
	 *            instance of the generic type GroupType. The group may not be
	 *            null. If the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param index
	 *            The index of the child item, which should be removed from the
	 *            group, as an {@link Integer} value. The index must be between
	 *            0 and the value of the method
	 *            <code>getNumberOfChildren(group):int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return The child item, which has been removed, as an instance of the
	 *         generic type ChildType. The item may not be null
	 */
	ChildType removeChild(GroupType group, int index);

	/**
	 * Removes a specific child item from the group, which belongs to a specific
	 * index.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item should be removed from,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getNumberOfGroups():int</code>,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param child
	 *            The child item, which should be removed from the group, as an
	 *            instance of the generic type ChildType. The child item may not
	 *            be null
	 * @return True, if the child item has been removed from the group, false
	 *         otherwise
	 */
	boolean removeChild(int groupIndex, ChildType child);

	/**
	 * Removes a specific child item from a specific group.
	 * 
	 * @param group
	 *            The group, the child item should be removed from, as an
	 *            instance of the generic type GroupType. The group may not be
	 *            null. If the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param child
	 *            The child item, which should be removed from the group, as an
	 *            instance of the generic type ChildType. The child item may not
	 *            be null
	 * @return True, if the child item has been removed from the group, false
	 *         otherwise
	 */
	boolean removeChild(GroupType group, ChildType child);

	/**
	 * Removes all child items, which are contained by a specific collection,
	 * from the adapter.
	 * 
	 * @param children
	 *            The collection, which contains the child items, which should
	 *            be removed from the adapter, as an instance of the type
	 *            {@link Collection} or an empty collection, if no child items
	 *            should be removed
	 * @return True, if all child items have been removed from the adapter,
	 *         false otherwise
	 */
	boolean removeAllChildren(Collection<ChildType> children);

	/**
	 * Removes all child items, which are contained by a specific collection,
	 * from the group, which belongs to a specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items should be removed
	 *            from, as an {@link Integer} value. The index must be between 0
	 *            and the value of the method
	 *            <code>getNumberOfGroups():int</code>, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param children
	 *            The collection, which contains the child items, which should
	 *            be removed from the group, as an instance of the type
	 *            {@link Collection} or an empty collection, if no child items
	 *            should be removed
	 * @return True, if all child items have been removed from the group, false
	 *         otherwise
	 */
	boolean removeAllChildren(int groupIndex, Collection<ChildType> children);

	/**
	 * Removes all child items, which are contained by a specific collection,
	 * from a specific group.
	 * 
	 * @param group
	 *            The group, the child items should be removed from, as an
	 *            instance of the generic type GroupType. The group may not be
	 *            null. If the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param children
	 *            The collection, which contains the child items, which should
	 *            be removed from the group, as an instance of the type
	 *            {@link Collection} or an empty collection, if no child items
	 *            should be removed
	 * @return True, if all child items have been removed from the group, false
	 *         otherwise
	 */
	boolean removeAllChildren(GroupType group, Collection<ChildType> children);

	/**
	 * Removes all child items, which are contained by a specific array, from
	 * the adapter.
	 * 
	 * @param children
	 *            The array, which contains the child items, which should be
	 *            removed from the adapter, as an array of the generic type
	 *            ChildType or an empty array, if no child items should be
	 *            removed
	 * @return True, if all child items have been removed from the adapter,
	 *         false otherwise
	 */
	boolean removeAllChildren(ChildType... children);

	/**
	 * Removes all child items, which are contained by a specific array, from
	 * the group, which belongs to a specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items should be removed
	 *            from, as an {@link Integer} value. The index must be between 0
	 *            and the value of the method
	 *            <code>getNumberOfGroups():int</code>, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param children
	 *            The array, which contains the child items, which should be
	 *            removed from the group, as an array of the generic type
	 *            ChildType or an empty array, if no child items should be
	 *            removed
	 * @return True, if all child items have been removed from the group, false
	 *         otherwise
	 */
	boolean removeAllChildren(int groupIndex, ChildType... children);

	/**
	 * Removes all child items, which are contained by a specific array, from a
	 * specific group.
	 * 
	 * @param group
	 *            The group, the child items should be removed from, as an
	 *            instance of the generic type GroupType. The group may not be
	 *            null. If the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param children
	 *            The array, which contains the child items, which should be
	 *            removed from the group, as an array of the generic type
	 *            ChildType or an empty collection, if no child items should be
	 *            removed
	 * @return True, if all child items have been removed from the group, false
	 *         otherwise
	 */
	boolean removeAllChildren(GroupType group, ChildType... children);

	/**
	 * Removes all child items from the adapter, except of the items, which are
	 * contained by a specific collection.
	 * 
	 * @param children
	 *            The collection, which contains the child items, which should
	 *            be retained, as an instance of the type {@link Collection} or
	 *            an empty collection, if no child items should be retained
	 */
	void retainAllChildren(Collection<ChildType> children);

	/**
	 * Removes all child items from the group, which belongs to a specific
	 * index, except of the items, which are contained by a specific collection.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items should be removed
	 *            from, as an {@link Integer} value. The index must be between 0
	 *            and the value of the method
	 *            <code>getNumberOfGroups():int</code>, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param children
	 *            The collection, which contains the child items, which should
	 *            be retained, as an instance of the type {@link Collection} or
	 *            an empty collection, if no child items should be retained
	 */
	void retainAllChildren(int groupIndex, Collection<ChildType> children);

	/**
	 * Removes all child items from a specific group, except of the items, which
	 * are contained by a specific collection.
	 * 
	 * @param group
	 *            The group, the child items should be removed from, as an
	 *            instance of the generic type GroupType. The group may not be
	 *            null. If the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param children
	 *            The collection, which contains the child items, which should
	 *            be retained, as an instance of the type {@link Collection} or
	 *            an empty collection, if no child items should be retained
	 */
	void retainAllChildren(GroupType group, Collection<ChildType> children);

	/**
	 * Removes all child items from the adapter, except of the items, which are
	 * contained by a specific array.
	 * 
	 * @param children
	 *            The array, which contains the child items, which should be
	 *            retained, as an array of the generic type ChildType or an
	 *            empty array, if no child items should be retained
	 */
	void retainAllChildren(ChildType... children);

	/**
	 * Removes all child items from the group, which belongs to a specific
	 * index, except of the items, which are contained by a specific array.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items should be removed
	 *            from, as an {@link Integer} value. The index must be between 0
	 *            and the value of the method
	 *            <code>getNumberOfGroups():int</code>, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param children
	 *            The array, which contains the child items, which should be
	 *            retained, as an array of the generic type ChildType or an
	 *            empty collection, if no child items should be retained
	 */
	void retainAllChildren(int groupIndex, ChildType... children);

	/**
	 * Removes all child items from a specific group, except of the items, which
	 * are contained by a specific array.
	 * 
	 * @param group
	 *            The group, the child items should be removed from, as an
	 *            instance of the generic type GroupType. The group may not be
	 *            null. If the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param children
	 *            The array, which contains the child items, which should be
	 *            retained, as an array of the generic type ChildType or an
	 *            empty collection, if no child items should be retained
	 */
	void retainAllChildren(GroupType group, ChildType... children);

	/**
	 * Removes all child items from the adapter.
	 */
	void clearChildren();

	/**
	 * Removes all child items from the group, which belongs to a specific
	 * index.
	 * 
	 * @param groupIndex
	 *            The index of the group, whose child item should be removed, as
	 *            an {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfGroups():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 */
	void clearChildren(int groupIndex);

	/**
	 * Removes all child items from a specific group.
	 * 
	 * @param group
	 *            The group, whose child items should be removed, as an instance
	 *            of the generic type GroupType. The group may not be null. If
	 *            the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 */
	void clearChildren(GroupType group);

	/**
	 * Returns an iterator, which allows to iterate the adapter's child items.
	 * 
	 * @return An iterator, which allows to iterate the adapter's child items,
	 *         as an instance of the type {@link Iterator}. The iterator may not
	 *         be null
	 */
	Iterator<ChildType> childIterator();

	/**
	 * Returns an iterator, which allows to iterate the child items of the
	 * group, which belongs to a specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, whose child items should be iterated,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getNumberOfGroups():int</code> -
	 *            1, otherwise an {@link IndexOutOfBoundsException} will be
	 *            thrown
	 * @return An iterator, which allows to iterate the group's items, as an
	 *         instance of the type {@link Iterator}. The iterator may not be
	 *         null
	 */
	Iterator<ChildType> childIterator(int groupIndex);

	/**
	 * Returns an iterator, which allows to iterate the child items of a
	 * specific group.
	 * 
	 * @param group
	 *            The group, whose child items should be iterated, as an
	 *            instance of the generic type GroupType. The group may not be
	 *            null. If the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return An iterator, which allows to iterate the group's items, as an
	 *         instance of the type {@link Iterator}. The iterator may not be
	 *         null
	 */
	Iterator<ChildType> childIterator(GroupType group);

	/**
	 * Returns a list iterator, which allows to iterate the adapter's child
	 * items.
	 * 
	 * @return A list iterator, which allows to iterate the adapter's child
	 *         items, as an instance of the type {@link ListIterator}. The
	 *         iterator may not be null
	 */
	ListIterator<ChildType> childListIterator();

	/**
	 * Returns a list iterator, which allow to iterate the child items of the
	 * group, which belongs to a specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, whose child items should be iterated,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getNumberOfGroups():int</code> -
	 *            1, otherwise an {@link IndexOutOfBoundsException} will be
	 *            thrown
	 * @return A list iterator, which allows to iterate the group's items, as an
	 *         instance of the type {@link ListIterator}. The iterator may not
	 *         be null
	 */
	ListIterator<ChildType> childListIterator(int groupIndex);

	/**
	 * Returns a list iterator, which allow to iterate the child items of a
	 * specific group.
	 * 
	 * @param group
	 *            The group, whose child items should be iterated, as an
	 *            instance of the generic type GroupType. The group may not be
	 *            null. If the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return A list iterator, which allows to iterate the group's items, as an
	 *         instance of the type {@link ListIterator}. The iterator may not
	 *         be null
	 */
	ListIterator<ChildType> childListIterator(GroupType group);

	/**
	 * Returns a list iterator, which allow to iterate the child items of the
	 * group, which belongs to a specific index, starting at a specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, whose child items should be iterated,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getNumberOfGroups():int</code> -
	 *            1, otherwise an {@link IndexOutOfBoundsException} will be
	 *            thrown
	 * @param index
	 *            The index, the iterator should start at, as an {@link Integer}
	 *            value. The index must be between 0 and the value of the method
	 *            <code>getNumberOfChildren(groupIndex):int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @return A list iterator, which allows to iterate the group's items, as an
	 *         instance of the type {@link ListIterator}. The iterator may not
	 *         be null
	 */
	ListIterator<ChildType> childListIterator(int groupIndex, int index);

	/**
	 * Returns a list iterator, which allow to iterate the child items of a
	 * specific group, starting at a specific index.
	 * 
	 * @param group
	 *            The group, whose child items should be iterated, as an
	 *            instance of the generic type GroupType. The group may not be
	 *            null. If the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param index
	 *            The index, the iterator should start at, as an {@link Integer}
	 *            value. The index must be between 0 and the value of the method
	 *            <code>getNumberOfChildren(group):int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return A list iterator, which allows to iterate the group's items, as an
	 *         instance of the type {@link ListIterator}. The iterator may not
	 *         be null
	 */
	ListIterator<ChildType> childListIterator(GroupType group, int index);

	/**
	 * Returns a collection, which contains the child items of the group, which
	 * belongs to a specific index, between a specific start and end index.
	 * 
	 * @param groupIndex
	 *            The index of the group, whose child items should be returned,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getNumberOfGroups():int</code> -
	 *            1, otherwise an {@link IndexOutOfBoundsException} will be
	 *            thrown
	 * @param start
	 *            The start index of the child items, which should be returned,
	 *            as an {@link Integer} value. The child item, which belongs to
	 *            the start index will be included. The index must be between 0
	 *            and the value of the method
	 *            <code>getNumberOfChildren(groupIndex):int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param end
	 *            The end index of the child items, which should be returned, as
	 *            an {@link Integer} value. The child item, which belongs to the
	 *            end index, will be excluded. The index must be between 0 and
	 *            the value of the method
	 *            <code>getNumberOfChildren(groupIndex):int</code> -1 and it
	 *            must be greater than the start index, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return A collection, which contains the group's child items, between a
	 *         specific start end end index, as an instance of the type
	 *         {@link Collection} or an empty collection, if the group does not
	 *         contain any child items
	 */
	Collection<ChildType> subListChildren(int groupIndex, int start, int end);

	/**
	 * Returns a collection, which contains the child items of a specific group
	 * between a specific start and end index.
	 * 
	 * @param group
	 *            The group, whose child items should be returned, as an
	 *            instance of the generic type GroupType. The group may not be
	 *            null. If the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param start
	 *            The start index of the child items, which should be returned,
	 *            as an {@link Integer} value. The child item, which belongs to
	 *            the start index will be included. The index must be between 0
	 *            and the value of the method
	 *            <code>getNumberOfChildren(group):int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param end
	 *            The end index of the child items, which should be returned, as
	 *            an {@link Integer} value. The child item, which belongs to the
	 *            end index, will be excluded. The index must be between 0 and
	 *            the value of the method
	 *            <code>getNumberOfChildren(group):int</code> -1 and it must be
	 *            greater than the start index, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return A collection, which contains the group's child items, between a
	 *         specific start end end index, as an instance of the type
	 *         {@link Collection} or an empty collection, if the group does not
	 *         contain any child items
	 */
	Collection<ChildType> subListChildren(GroupType group, int start, int end);

	/**
	 * Returns an array, which contains all of the adapter's child items.
	 * 
	 * @return An array, which contains all of the adapter's child items, as an
	 *         {@link Object} array or an empty array, if the adapter does not
	 *         contain any child items
	 */
	Object[] childrenToArray();

	/**
	 * Returns an array, which contains the child items of the group, which
	 * belongs to a specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, whose child items should be returned,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getNumberOfGroups():int</code> -
	 *            1, otherwise an {@link IndexOutOfBoundsException} will be
	 *            thrown
	 * @return An array, which contains the group's child items, as an
	 *         {@link Object} array or an empty array, if the group does not
	 *         contain any child items
	 */
	Object[] childrenToArray(int groupIndex);

	/**
	 * Returns an array, which contains the child items of a specific group. *
	 * 
	 * @param group
	 *            The group, whose child items should be returned, as an
	 *            instance of the generic type GroupType. The group may not be
	 *            null. If the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return An array, which contains the group's child items, as an
	 *         {@link Object} array or an empty array, if the group does not
	 *         contain any child items
	 */
	Object[] childrenToArray(GroupType group);

	/**
	 * Returns an array, which contains all of the adapter's child items. If the
	 * given array is large enough to hold the items, the specified array is
	 * used, otherwise an array of the same type is created. If the given array
	 * can hold more items, the array's elements, following the group's child
	 * items, are set to null.
	 * 
	 * @param <T>
	 *            The type of the array, which should be returned
	 * @param array
	 *            The array, which should be used, if it is large enough, as an
	 *            array of the generic type T. The array may not be null
	 * @return An array, which contains all of adapter's child items, as an
	 *         array of the generic type T or an empty array, if the adapter
	 *         does not contain any child items
	 */
	<T> T[] childrenToArray(T[] array);

	/**
	 * Returns an array, which contains all child items of the group, which
	 * belongs to a specific index. If the given array is large enough to hold
	 * the items, the specified array is used, otherwise an array of the same
	 * type is created. If the given array can hold more items, the array's
	 * elements, following the group's child items, are set to null.
	 * 
	 * @param <T>
	 *            The type of the array, which should be returned
	 * @param groupIndex
	 *            The index of the group, whose child items should be returned,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getNumberOfGroups():int</code> -
	 *            1, otherwise an {@link IndexOutOfBoundsException} will be
	 *            thrown
	 * @param array
	 *            The array, which should be used, if it is large enough, as an
	 *            array of the generic type T. The array may not be null
	 * @return An array, which contains all of the group's child items, as an
	 *         array of the generic type T or an empty array, if the group does
	 *         not contain any child items
	 */
	<T> T[] childrenToArray(int groupIndex, T[] array);

	/**
	 * Returns an array, which contains all child items of a specific group. If
	 * the given array is large enough to hold the items, the specified array is
	 * used, otherwise an array of the same type is created. If the given array
	 * can hold more items, the array's elements, following the group's child
	 * items, are set to null.
	 * 
	 * @param <T>
	 *            The type of the array, which should be returned
	 * @param group
	 *            The group, whose child items should be returned, as an
	 *            instance of the generic type GroupType. The group may not be
	 *            null. If the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param array
	 *            The array, which should be used, if it is large enough, as an
	 *            array of the generic type T. The array may not be null
	 * @return An array, which contains all of the group's child items, as an
	 *         array of the generic type T or an empty array, if the group does
	 *         not contain any child items
	 */
	<T> T[] childrenToArray(GroupType group, T[] array);

	/**
	 * Returns the child item, which belongs to a specific index of the group,
	 * which belongs to a specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, which contains the child item, which
	 *            should be returned, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param index
	 *            The index of the child item, which should be returned, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method
	 *            <code>getNumberOfChildren(groupIndex):int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @return The child item, which belongs to the given index, as an instance
	 *         of the generic type ChildType. The child item may not be null
	 */
	ChildType getChild(int groupIndex, int index);

	/**
	 * Returns the child item, which belongs to a specific index of a specific
	 * group.
	 * 
	 * @param group
	 *            The group, which contains the child item, which should be
	 *            returned, as an instance of the generic type GroupType. The
	 *            group may not be null. If the group does not belong to the
	 *            adapter, a {@link NoSuchElementException} will be thrown
	 * @param index
	 *            The index of the child item, which should be returned, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method
	 *            <code>getNumberOfChildren(group):int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return The child item, which belongs to the given index, as an instance
	 *         of the generic type ChildType. The child item may not be null
	 */
	ChildType getChild(GroupType group, int index);

	/**
	 * Returns the index of the group, a specific child item belongs to.
	 * 
	 * @param child
	 *            The child item, whose group index should be returned, as an
	 *            instance of the generic type ChildType. The child item may not
	 *            be null
	 * @return The index of the group, the given child item belongs to, as an
	 *         {@link Integer} value or -1, if the adapter does not contain the
	 *         given child item
	 */
	int indexOfChild(ChildType child);

	/**
	 * Returns the index of a specific child item within the group, which
	 * belongs to a specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose index should be
	 *            returned, belongs to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param child
	 *            The child item, whose index should be returned, as an instance
	 *            of the generic type ChildType. The child item may not be null
	 * @return The index of the given child item, as an {@link Integer} value or
	 *         -1, if the group does not contain the given child item
	 */
	int indexOfChild(int groupIndex, ChildType child);

	/**
	 * Returns the index of a specific child item within a specific group.
	 * 
	 * @param group
	 *            The group, the child item, whose index should be returned,
	 *            belongs to, as an instance of the generic type GroupType. The
	 *            group may not be null. If the group does not belong to the
	 *            adapter, a {@link NoSuchElementException} will be thrown
	 * @param child
	 *            The child item, whose index should be returned, as an instance
	 *            of the generic type ChildType. The child item may not be null
	 * @return The index of the given child item, as an {@link Integer} value or
	 *         -1, if the group does not contain the given child item
	 */
	int indexOfChild(GroupType group, ChildType child);

	/**
	 * Returns the last index of the group, a specific child item belongs to.
	 * 
	 * @param child
	 *            The child item, whose last group index should be returned, as
	 *            an instance of the generic type ChildType. The child item may
	 *            not be null
	 * @return The last index of the group, the given child item belongs to, as
	 *         an {@link Integer} value or -1, if the adapter does not contain
	 *         the given child item
	 */
	int lastIndexOfChild(ChildType child);

	/**
	 * Returns the last index of a specific child item within the group, which
	 * belongs to a specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose last index
	 *            should be returned, belongs to, as an {@link Integer} value.
	 *            The index must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param child
	 *            The child item, whose last index should be returned, as an
	 *            instance of the generic type ChildType. The child item may not
	 *            be null
	 * @return The last index of the given child item, as an {@link Integer}
	 *         value or -1, if the group does not contain the given child item
	 */
	int lastIndexOfChild(int groupIndex, ChildType child);

	/**
	 * Returns the last index of a specific child item within a specific group.
	 * 
	 * @param group
	 *            The group, the child item, whose last index should be
	 *            returned, belongs to, as an instance of the generic type
	 *            GroupType. The group may not be null. If the group does not
	 *            belong to the adapter, a {@link NoSuchElementException} will
	 *            be thrown
	 * @param child
	 *            The child item, whose last index should be returned, as an
	 *            instance of the generic type ChildType. The child item may not
	 *            be null
	 * @return The last index of the given child item, as an {@link Integer}
	 *         value or -1, if the group does not contain the given child item
	 */
	int lastIndexOfChild(GroupType group, ChildType child);

	/**
	 * Returns, whether the adapter contains a specific child item, or not.
	 * 
	 * @param child
	 *            The child item, whose presence should be checked, as an
	 *            instance of the generic type ChildType. The child item may not
	 *            be null
	 * @return True, if the adapter contains the given child item, false
	 *         otherwise
	 */
	boolean containsChild(ChildType child);

	/**
	 * Returns, whether the group, which belongs to a specific index, contains a
	 * specific child item, or not.
	 * 
	 * @param groupIndex
	 *            The index of the group, whose child items should be checked,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getNumberOfGroups():int</code>
	 *            -1, otherwise an {@link IndexOutOfBoundsException} will be
	 *            thrown
	 * @param child
	 *            The child item, whose presence should be checked, as an
	 *            instance of the generic type ChildType. The child item may not
	 *            be null
	 * @return True, if the group contains the given child item, false otherwise
	 */
	boolean containsChild(int groupIndex, ChildType child);

	/**
	 * Returns, whether a specific group contains a specific child item, or not.
	 * 
	 * @param group
	 *            The group, whose child items should be checked, as an instance
	 *            of the generic type GroupType. The group may not be null. If
	 *            the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param child
	 *            The child item, whose presence should be checked, as an
	 *            instance of the generic type ChildType. The child item may not
	 *            be null
	 * @return True, if the group contains the given child item, false otherwise
	 */
	boolean containsChild(GroupType group, ChildType child);

	/**
	 * Returns, whether the adapter contains all child items, which are
	 * contained by a specific collection, or not.
	 * 
	 * @param children
	 *            The collection, which contains the child items, whose presence
	 *            should be checked, as an instance of the type
	 *            {@link Collection}. The collection may not be null
	 * @return True, if the adapter contains all child items, which are
	 *         contained by the given collection, false otherwise
	 */
	boolean containsAllChildren(Collection<ChildType> children);

	/**
	 * Returns, whether the group, which belongs to a specific index, contains
	 * all child items, which are contained by a specific collection, or not.
	 * 
	 * @param groupIndex
	 *            The index of the group, whose child items should be checked,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getNumberOfGroups():int</code>
	 *            -1, otherwise an {@link IndexOutOfBoundsException} will be
	 *            thrown
	 * @param children
	 *            The collection, which contains the child items, whose presence
	 *            should be checked, as an instance of the type
	 *            {@link Collection}. The collection may not be null
	 * @return True, if the group contains all child items, which are contained
	 *         by the given collection, false otherwise
	 */
	boolean containsAllChildren(int groupIndex, Collection<ChildType> children);

	/**
	 * Returns, whether a specific group contains all child items, which are
	 * contained by a specific collection, or not.
	 * 
	 * @param group
	 *            The group, whose child items should be checked, as an instance
	 *            of the generic type GroupType. The group may not be null. If
	 *            the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param children
	 *            The collection, which contains the child items, whose presence
	 *            should be checked, as an instance of the type
	 *            {@link Collection}. The collection may not be null
	 * @return True, if the group contains all child items, which are contained
	 *         by the given collection, false otherwise
	 */
	boolean containsAllChildren(GroupType group, Collection<ChildType> children);

	/**
	 * Returns, whether the adapter contains all child items, which are
	 * contained by a specific array, or not.
	 * 
	 * @param children
	 *            The array, which contains the child items, whose presence
	 *            should be checked, as an array of the generic type ChildType.
	 *            The array may not be null
	 * @return True, if the adapter contains all child items, which are
	 *         contained by the given array, false otherwise
	 */
	boolean containsAllChildren(ChildType... children);

	/**
	 * Returns the total number of child items, which are contained by the
	 * adapter.
	 * 
	 * @return The total number of child items, which are contained by the
	 *         adapter, as an {@link Integer} value
	 */
	int getNumberOfChildren();

	/**
	 * Returns the number of child items, which are contained by the group,
	 * which belongs to a specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, whose child items should be counted,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getNumberOfGroups():int</code> -
	 *            1, otherwise an {@link IndexOutOfBoundsException} will be
	 *            thrown
	 * @return The number of child items, which are contained by the group, as
	 *         an {@link Integer} value
	 */
	int getNumberOfChildren(int groupIndex);

	/**
	 * Returns the number of child items, which are contained by a specific
	 * group.
	 * 
	 * @param group
	 *            The group, whose child items should be counted, as an instance
	 *            of the generic type GroupType. The group may not be null. If
	 *            the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return The number of child items, which are contained by the group, as
	 *         an {@link Integer} value
	 */
	int getNumberOfChildren(GroupType group);

	/**
	 * Returns a collection, which contains all of the adapter's child items.
	 * 
	 * @return A collection, which contains all of the adapter's child items, as
	 *         an instance of the type {@link Collection} or an empty
	 *         collection, if the adapter does not contain any child items
	 */
	Collection<ChildType> getAllChildren();

	/**
	 * Returns a collection, which contains all child items of the group, which
	 * belongs to a specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, whose child items should be returned,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getNumberOfGroups():int</code> -
	 *            1, otherwise an {@link IndexOutOfBoundsException} will be
	 *            thrown
	 * @return A collection, which contains all child items of the group, as an
	 *         instance of the type {@link Collection} or an empty collection,
	 *         if the group does not contain any child items
	 */
	Collection<ChildType> getAllChildren(int groupIndex);

	/**
	 * Returns a collection, which contains all child items of a specific group.
	 * 
	 * @param group
	 *            The group, whose child items should be returned, as an
	 *            instance of the generic type GroupType. The group may not be
	 *            null. If the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return A collection, which contains all child items of the group, as an
	 *         instance of the type {@link Collection} or an empty collection,
	 *         if the group does not contain any child items
	 */
	Collection<ChildType> getAllChildren(GroupType group);

	/**
	 * Returns, whether the group, which belongs to a specific index, contains
	 * all child items, which are contained by a specific array, or not.
	 * 
	 * @param groupIndex
	 *            The index of the group, whose child items should be checked,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getNumberOfGroups():int</code>
	 *            -1, otherwise an {@link IndexOutOfBoundsException} will be
	 *            thrown
	 * @param children
	 *            The array, which contains the child items, whose presence
	 *            should be checked, as an array of the generic type ChildType.
	 *            The array may not be null
	 * @return True, if the group contains all child items, which are contained
	 *         by the given array, false otherwise
	 */
	boolean containsAllChildren(int groupIndex, ChildType... children);

	/**
	 * Returns, whether a specific group contains all child items, which are
	 * contained by a specific array, or not.
	 * 
	 * @param group
	 *            The group, whose child items should be checked, as an instance
	 *            of the generic type GroupType. The group may not be null. If
	 *            the group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param children
	 *            The array, which contains the child items, whose presence
	 *            should be checked, as an array of the generic type ChildType.
	 *            The array may not be null
	 * @return True, if the group contains all child items, which are contained
	 *         by the given array, false otherwise
	 */
	boolean containsAllChildren(GroupType group, ChildType... children);

	/**
	 * Adds a new listener, which should be notified, when the adapter's
	 * underlying data has been modified.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            class {@link ExpandableListAdapterListener}. The listener may
	 *            not be null
	 */
	void addAdapterListener(ExpandableListAdapter<GroupType, ChildType> listener);

	/**
	 * Removes a specific listener, which should not be notified, when the
	 * adapter's underlying data has been modified, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            class {@link ExpandableListAdapterListener}. The listener may
	 *            not be null
	 */
	void removeAdapterListener(
			ExpandableListAdapterListener<GroupType, ChildType> listener);

}