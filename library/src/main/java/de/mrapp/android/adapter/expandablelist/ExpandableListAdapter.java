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
package de.mrapp.android.adapter.expandablelist;

import android.support.annotation.NonNull;
import android.widget.ExpandableListView;
import android.widget.HeterogeneousExpandableList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import de.mrapp.android.adapter.ExpandableRecyclerViewAdapter;
import de.mrapp.android.adapter.expandablelist.enablestate.EnableStateExpandableListAdapter;
import de.mrapp.android.adapter.expandablelist.filterable.FilterableExpandableListAdapter;
import de.mrapp.android.adapter.expandablelist.itemstate.ItemStateExpandableListAdapter;
import de.mrapp.android.adapter.expandablelist.sortable.SortableExpandableListAdapter;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list of arbitrary group
 * and child items, must implement. Such an adapter's purpose is to provide the underlying data for
 * visualization using a {@link ExpandableListView} widget.
 *
 * @param <GroupType>
 *         The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the adapter's child items
 * @author Michael Rapp
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public interface ExpandableListAdapter<GroupType, ChildType>
        extends ExpandableRecyclerViewAdapter, android.widget.ExpandableListAdapter,
        HeterogeneousExpandableList, EnableStateExpandableListAdapter<GroupType, ChildType>,
        ItemStateExpandableListAdapter<GroupType, ChildType>,
        SortableExpandableListAdapter<GroupType, ChildType>,
        FilterableExpandableListAdapter<GroupType, ChildType> {

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
     *         True , if duplicate group items should be allowed, false otherwise
     */
    void allowDuplicateGroups(boolean allowDuplicateGroups);

    /**
     * Adds a specific group item to the adapter. The item will be added at the end.
     *
     * @param group
     *         The group item, which should be added to the adapter, as an instance of the generic
     *         type GroupType. The group item may not be null
     * @return The index of the group, which has been added to the adapter, as an {@link Integer}
     * value or -1, if the group has not been added
     */
    int addGroup(@NonNull GroupType group);

    /**
     * Adds a specific group item to the adapter. The group item will be added at a specific index.
     *
     * @param index
     *         The index, the group item should be added at, as an {@link Integer} value. The index
     *         must be between 0 and the value of the method <code>getGroupCount():int</code>,
     *         otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @param group
     *         The group item, which should be added to the adapter, as an instance of the generic
     *         type GroupType. The group item may not be null
     * @return True, if the group item has been added to the adapter, false otherwise
     */
    boolean addGroup(int index, @NonNull GroupType group);

    /**
     * Adds all group items, which are contained by a specific collection, to the adapter. The group
     * items will be added in a consecutive order at the end.
     *
     * @param groups
     *         The collection, which contains the group items, which should be added to the adapter,
     *         as an instance of the type {@link Collection} or an empty collection, if no group
     *         items should be added
     * @return True, if all group items have been added to the adapter, false otherwise
     */
    boolean addAllGroups(@NonNull Collection<? extends GroupType> groups);

    /**
     * Adds all group items, which are contained by a specific collection, to the adapter. The group
     * items will be added in a consecutive order at a specific index.
     *
     * @param index
     *         The index, the group items should be added at, as an {@link Integer} value. The index
     *         must be between 0 and the value of the method <code>getGroupCount():int</code>,
     *         otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @param groups
     *         The collection, which contains the group items, which should be added to the adapter,
     *         as an instance of the type {@link Collection} or an empty collection, if no group
     *         items should be added
     * @return True, if all items have been added to the adapter, false otherwise
     */
    boolean addAllGroups(int index, @NonNull Collection<? extends GroupType> groups);

    /**
     * Adds all group items, which are contained by a specific array, to the adapter. The group
     * items will be added in a consecutive order at the end.
     *
     * @param groups
     *         The array, which contains the group items, which should be added to the adapter, as
     *         an array of the generic type GroupType or an empty array, if no group items should be
     *         added
     * @return True, if all group items have been added to the adapter, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean addAllGroups(@NonNull GroupType... groups);

    /**
     * Adds all group items, which are contained by a specific array, to the adapter. The group
     * items will be added in a consecutive order at a specific index.
     *
     * @param index
     *         The index, the group items should be added at, as an {@link Integer} value. The index
     *         must be between 0 and the value of the method <code>getGroupCount():int</code>,
     *         otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @param groups
     *         The array, which contains the group items, which should be added to the adapter, as
     *         an array of the generic type GroupType or an empty array, if no group items should be
     *         added
     * @return True, if all group items have been added to the adapter, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean addAllGroups(int index, @NonNull GroupType... groups);

    /**
     * Replaces the group item, which belongs to a specific index, by an other group item. The
     * group's children will be retained.
     *
     * @param index
     *         The index of the group item, which should be replaced, as an {@link Integer} value.
     *         The index must be between 0 and the value of the method <code>getGroupCount():int</code>
     *         - 1, otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @param group
     *         The group item, which should replace the group item at the given index, as an
     *         instance of the generic type GroupType. The group item may not be null
     * @return The group item, which has been replaced, as an instance of the generic type
     * GroupType. The group item may not be null
     */
    GroupType replaceGroup(int index, @NonNull GroupType group);

    /**
     * Removes the group item, which belongs to a specific index, from the adapter. The group's
     * children will be also removed from the adapter.
     *
     * @param index
     *         The index of the group item, which should be removed from the adapter, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return The group item, which has been removed, as an instance of the generic type GroupType.
     * The group item may not be null
     */
    GroupType removeGroup(int index);

    /**
     * Removes a specific group item from the adapter. The group's children will be also removed
     * from the adapter.
     *
     * @param group
     *         The group item, which should be removed, as an instance of the generic type
     *         GroupType. The group item may not be null
     * @return True, if the group item has been removed, false otherwise
     */
    boolean removeGroup(@NonNull GroupType group);

    /**
     * Removes all group items, which are contained by a specific collection, from the adapter. The
     * groups' children will also be removed from the adapter.
     *
     * @param groups
     *         The collection, which contains the group items, which should be removed from the
     *         adapter, as an instance of the type {@link Collection} or an empty collection, if no
     *         group items should be removed
     * @return True, if all group items have been removed from the adapter, false otherwise
     */
    boolean removeAllGroups(@NonNull Collection<? extends GroupType> groups);

    /**
     * Removes all group items, which are contained by a specific array, from the adapter. The
     * groups' children will also be removed from the adapter.
     *
     * @param groups
     *         The array, which contains the group items, which should be removed from the adapter,
     *         as an array of the generic type GroupType or an empty array, if no group items should
     *         be removed
     * @return True, if all group items have been removed from the adapter, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean removeAllGroups(@NonNull GroupType... groups);

    /**
     * Removes all group items from the adapter, except of the group items, which are contained by a
     * specific collection. The removed groups' children will also be removed from the adapter.
     *
     * @param groups
     *         The collection, which contains the group items, which should be retained, as an
     *         instance of the type {@link Collection} or an empty collection, if no group items
     *         should be retained
     */
    void retainAllGroups(@NonNull Collection<? extends GroupType> groups);

    /**
     * Removes all group items from the adapter, except of the group items, which are contained by a
     * specific array. The removed groups' children will also be removed from the adapter.
     *
     * @param groups
     *         The array, which contains the group items, which should be retained, as an array of
     *         the generic type DataType or an empty array, if no group items should be retained
     */
    @SuppressWarnings("unchecked")
    void retainAllGroups(@NonNull GroupType... groups);

    /**
     * Removes all groups and their child items from the adapter.
     */
    void clearGroups();

    /**
     * Returns an iterator, which allows to iterate the adapter's group items.
     *
     * @return An iterator, which allows to iterate the adapter's group items, as an instance of the
     * type {@link Iterator}. The iterator may not be null
     */
    Iterator<GroupType> groupIterator();

    /**
     * Returns a list iterator, which allows to iterate the adapter's group items.
     *
     * @return A list iterator, which allows to iterate the adapter's group items, as an instance of
     * the type {@link ListIterator}. The iterator may not be null
     */
    ListIterator<GroupType> groupListIterator();

    /**
     * Returns a list iterator, which allows to iterate the adapter's group items, starting at a
     * specific index.
     *
     * @param index
     *         The index, the iterator should start at, as an {@link Integer} value. The index must
     *         be between 0 and the value of the method <code>getGroupCount():int</code> - 1,
     *         otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @return A list iterator, which allows to iterate the adapter's group items, starting at the
     * given index, as an instance of the type {@link ListIterator}. The iterator may not be null
     */
    ListIterator<GroupType> groupListIterator(int index);

    /**
     * Returns a list, which contains the adapter's group items between a specific start and end
     * index.
     *
     * @param start
     *         The start index of the group items, which should be returned, as an {@link Integer}
     *         value. The group item, which belongs to the start index will be included. The index
     *         must be between 0 and the value of the method <code>getGroupCount():int</code> - 1,
     *         otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @param end
     *         The end index of the group items, which should be returned, as an {@link Integer}
     *         value. The group item, which belongs to the end index, will be excluded. The index
     *         must be between 0 and the value of the method <code>getGroupCount():int</code> -1 and
     *         it must be greater than the start index, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @return A list, which contains the adapter's group items, between a specific start end end
     * index, as an instance of the type {@link List} or an empty list, if the adapter does not
     * contain any group items
     */
    List<GroupType> subListGroups(int start, int end);

    /**
     * Returns an array, which contains the adapter's group items.
     *
     * @return An array, which contains the adapter's group items, as an {@link Object} array or an
     * empty array, if the adapter does not contain any group items
     */
    Object[] groupsToArray();

    /**
     * Returns an array, which contains all of the adapter's group items. If the given array is
     * large enough to hold the items, the specified array is used, otherwise an array of the same
     * type is created. If the given array can hold more items, the array's elements, following the
     * adapter's items, are set to null.
     *
     * @param <T>
     *         The type of the array, which should be returned
     * @param array
     *         The array, which should be used, if it is large enough, as an array of the generic
     *         type T. The array may not be null
     * @return An array, which contains all of the adapter's group item, as an array of the generic
     * type T or an empty array, if the adapter does not contain any group items
     */
    <T> T[] groupsToArray(@NonNull T[] array);

    /**
     * Returns the group item, which belongs to a specific index.
     *
     * @param index
     *         The index of the group item, which should be returned, as an {@link Integer} value.
     *         The index must be between 0 and the value of the method <code>getGroupCount():int</code>
     *         - 1, otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @return The group item, which belongs to the given index, as an instance of the generic type
     * GroupType. The group item may not be null
     */
    GroupType getGroup(int index);

    /**
     * Returns the index of a specific group item.
     *
     * @param group
     *         The group item, whose index should be returned, as an instance of the generic type
     *         GroupType. The group item may not be null
     * @return The index of the the given group item, as an {@link Integer} value or -1, if the
     * adapter does not contain the given group item
     */
    int indexOfGroup(@NonNull GroupType group);

    /**
     * Returns, whether the adapter contains a specific group item, or not.
     *
     * @param group
     *         The group item, whose presence should be checked, as an instance of the generic type
     *         GroupType. The group item may not be null
     * @return True, if the adapter contains the given group item, false otherwise
     */
    boolean containsGroup(@NonNull GroupType group);

    /**
     * Returns, whether the adapter contains all group items, which are contained by a specific
     * collection, or not.
     *
     * @param groups
     *         The collection, which contains the group items, which whose presence should be
     *         checked, as an instance of the type {@link Collection}. The collection may not be
     *         null
     * @return True, if the adapter contains all group items, which are contained by the given
     * collection, false otherwise
     */
    boolean containsAllGroups(@NonNull Collection<? extends GroupType> groups);

    /**
     * Returns, whether the adapter contains all group items, which are contained by a specific
     * array, or not.
     *
     * @param groups
     *         The array, which contains the group items, which whose presence should be checked, as
     *         an array of the generic type GroupType. The array may not be null
     * @return True, if the adapter contains all group items, which are contained by the given
     * array, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean containsAllGroups(@NonNull GroupType... groups);

    /**
     * Returns the number of group items, which are contained by the adapter.
     *
     * @return The number of group items, which are contained by the adapter, as an {@link Integer}
     * value
     */
    int getGroupCount();

    /**
     * Returns a list, which contains all of the adapter's group items.
     *
     * @return A list, which contains all of the adapter's group items, as an instance of the type
     * {@link List} or an empty list, if the adapter does not contain any group items
     */
    List<GroupType> getAllGroups();

    /**
     * Returns, whether the group, which belongs to a specific index, is empty or not.
     *
     * @param groupIndex
     *         The index of the group, which should be checked, as an {@link Integer} value. The
     *         index must be between 0 and the value of the method <code>getGroupCount():int</code>
     *         - 1, otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @return True, if the group is empty, false otherwise
     */
    boolean isGroupEmpty(int groupIndex);

    /**
     * Returns, whether a specific group is empty or not.
     *
     * @param group
     *         The group, which should be checked, as an instance of the generic type GroupType. The
     *         group may not be null. If the group does not belong to the adapter, a {@link
     *         NoSuchElementException} will be thrown
     * @return True, if the group is empty, false otherwise
     */
    boolean isGroupEmpty(@NonNull GroupType group);

    /**
     * Returns, whether duplicate child items within a single group are allowed, or not.
     *
     * @return True, if duplicate child items within a single group are allowed, false otherwise
     */
    boolean areDuplicateChildrenAllowed();

    /**
     * Sets, whether duplicate child items within a single group should be allowed, or not.
     *
     * @param allowDuplicateChildren
     *         True, if duplicate child items within a single group should be allowed, false
     *         otherwise
     */
    void allowDuplicateChildren(boolean allowDuplicateChildren);

    /**
     * Returns, whether duplicate child items within the group, which belongs to a specific index,
     * are allowed, or not.
     *
     * @param groupIndex
     *         The index of the group, whose constraints regarding duplicate child items should be
     *         checked, as an {@link Integer} value. The index must be between 0 and the value of
     *         the method <code>getGroupCount():int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @return True, if duplicate child items are allowed within the group, false otherwise
     */
    boolean areDuplicateChildrenAllowed(int groupIndex);

    /**
     * Sets, whether duplicate child items within the group, which belongs to a specific index,
     * should be allowed, or not.
     *
     * @param groupIndex
     *         The index of the group, whose constraints regarding duplicate child items should be
     *         set, as an {@link Integer} value. The index must be between 0 and the value of the
     *         method <code>getGroupCount():int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @param allowDuplicateChildren
     *         True, if duplicate child items should be allowed within the group, false otherwise
     */
    void allowDuplicateChildren(int groupIndex, boolean allowDuplicateChildren);

    /**
     * Returns, whether duplicate child items within a specific group are allowed, or not.
     *
     * @param group
     *         The group, whose constraints regarding duplicate child items should be checked, as an
     *         instance of the generic type GroupType. The group may not be null. If the group does
     *         not belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @return True, if duplicate child items are allowed within the group, false otherwise
     */
    boolean areDuplicateChildrenAllowed(@NonNull GroupType group);

    /**
     * Sets, whether duplicate child items within a specific group should be allowed, or not.
     *
     * @param group
     *         The group, whose constraints regarding duplicate child items should be set, as an
     *         instance of the generic type GroupType. The group may not be null. If the group does
     *         not belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @param allowDuplicateChildren
     *         True, if duplicate child items should be allowed within the group, false otherwise
     */
    void allowDuplicateChildren(@NonNull GroupType group, boolean allowDuplicateChildren);

    /**
     * Adds a specific child item to the group, which belongs to a specific index. The child item
     * will be added at the end of the group.
     *
     * @param groupIndex
     *         The index of the group, the child item should be added to, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @param child
     *         The child item, which should be added to the group, as an instance of the generic
     *         type ChildType. The child item may not be null
     * @return The index of the child, which has been added, as an {@link Integer} value or -1, if
     * the child has not been added
     */
    int addChild(int groupIndex, @NonNull ChildType child);

    /**
     * Adds a specific child item to a specific group. The child item will be added at the end of
     * the group.
     *
     * @param group
     *         The group, the child item should be added to, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param child
     *         The child item, which should be added to the group, as an instance of the generic
     *         type ChildType. The child item may not be null
     * @return The index of the child, which has been added, as an {@link Integer} value or -1, if
     * the child has not been added
     */
    int addChild(@NonNull GroupType group, @NonNull ChildType child);

    /**
     * Adds a specific child item to the group, which belongs to a specific index. The child item
     * will be added at a specific index of the group.
     *
     * @param groupIndex
     *         The index of the group, the child item should be added to, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @param index
     *         The index, the child item should be added at, as an {@link Integer} value. The index
     *         must be between 0 and the value of the method <code>getChildCount(groupIndex):int</code>
     *         , otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @param child
     *         The child item, which should be added to the group, as an instance of the generic
     *         type ChildType. The child item may not be null
     * @return True, if the child item has been added to the group, false otherwise
     */
    boolean addChild(int groupIndex, int index, @NonNull ChildType child);

    /**
     * Adds a specific child item to a specific group. The child item will be added at the end of
     * the group. The child item will be added at a specific index of the group.
     *
     * @param group
     *         The group, the child item should be added to, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param index
     *         The index, the child item should be added at, as an {@link Integer} value. The index
     *         must be between 0 and the value of the method <code>getChildCount(group):int</code>,
     *         otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @param child
     *         The child item, which should be added to the group, as an instance of the generic
     *         type ChildType. The child item may not be null
     * @return True, if the child item has been added to the group, false otherwise
     */
    boolean addChild(@NonNull GroupType group, int index, @NonNull ChildType child);

    /**
     * Adds all child items, which are contained by a specific collection, to the group, which
     * belongs to a specific index. The items will be added in a consecutive order at the end of the
     * group.
     *
     * @param groupIndex
     *         The index of the group, the child items should be added to, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @param children
     *         The collection, which contains the child items, which should be added to the group,
     *         as an instance of the type {@link Collection} or an empty collection, if no child
     *         items should be added
     * @return True, if all child items have been added to the group, false otherwise
     */
    boolean addAllChildren(int groupIndex, @NonNull Collection<? extends ChildType> children);

    /**
     * Adds all child items, which are contained by a specific collection, to a specific group. The
     * items will be added in a consecutive order at the end of the group.
     *
     * @param group
     *         The group, the child items should be added to, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param children
     *         The collection, which contains the child items, which should be added to the group,
     *         as an instance of the type {@link Collection} or an empty collection, if no child
     *         items should be added
     * @return True, if all child items have been added to the group, false otherwise
     */
    boolean addAllChildren(@NonNull GroupType group,
                           @NonNull Collection<? extends ChildType> children);

    /**
     * Adds all child items, which are contained by a specific collection, to the group, which
     * belongs to a specific index. The items will be added in a consecutive order at a specific
     * index of the group.
     *
     * @param groupIndex
     *         The index of the group, the child items should be added to, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @param index
     *         The index, the child items should be added at, as an {@link Integer} value. The index
     *         must be between 0 and the value of the method <code>getChildCount(groupIndex):int</code>
     *         , otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @param children
     *         The collection, which contains the child items, which should be added to the group,
     *         as an instance of the type {@link Collection} or an empty collection, if no child
     *         items should be added
     * @return True, if all child items have been added to the group, false otherwise
     */
    boolean addAllChildren(int groupIndex, int index,
                           @NonNull Collection<? extends ChildType> children);

    /**
     * Adds all child items, which are contained by a specific collection, to a specific group. The
     * items will be added in a consecutive order at a specific index of the group.
     *
     * @param group
     *         The group, the child items should be added to, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param index
     *         The index, the child items should be added at, as an {@link Integer} value. The index
     *         must be between 0 and the value of the method <code>getChildCount(group):int</code>,
     *         otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @param children
     *         The collection, which contains the child items, which should be added to the group,
     *         as an instance of the type {@link Collection} or an empty collection, if no child
     *         items should be added
     * @return True, if all child items have been added to the group, false otherwise
     */
    boolean addAllChildren(@NonNull GroupType group, int index,
                           @NonNull Collection<? extends ChildType> children);

    /**
     * Adds all child items, which are contained by a specific array, to the group, which belongs to
     * a specific index. The items will be added in a consecutive order at the end of the group.
     *
     * @param groupIndex
     *         The index of the group, the child items should be added to, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @param children
     *         The array, which contains the child items, which should be added to the group, as an
     *         array of the generic type ChildType or an empty array, if no child items should be
     *         added
     * @return True, if all child items have been added to the group, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean addAllChildren(int groupIndex, @NonNull ChildType... children);

    /**
     * Adds all child items, which are contained by a specific array, to a specific group. The items
     * will be added in a consecutive order at the end of the group.
     *
     * @param group
     *         The group, the child items should be added to, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param children
     *         The array, which contains the child items, which should be added to the group, as an
     *         array of the generic type ChildType or an empty array, if no child items should be
     *         added
     * @return True, if all child items have been added to the group, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean addAllChildren(@NonNull GroupType group, @NonNull ChildType... children);

    /**
     * Adds all child items, which are contained by a specific array, to the group, which belongs to
     * a specific index. The items will be added in a consecutive order at a specific index of the
     * group.
     *
     * @param groupIndex
     *         The index of the group, the child items should be added to, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @param index
     *         The index, the child items should be added at, as an {@link Integer} value. The index
     *         must be between 0 and the value of the method <code>getChildCount(groupIndex):int</code>
     *         , otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @param children
     *         The array, which contains the child items, which should be added to the group, as an
     *         array of the generic type ChildType or an empty array, if no child items should be
     *         added
     * @return True, if all child items have been added to the group, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean addAllChildren(int groupIndex, int index, @NonNull ChildType... children);

    /**
     * Adds all child items, which are contained by a specific array, to a specific group. The items
     * will be added in a consecutive order at a specific index of the group.
     *
     * @param group
     *         The group, the child items should be added to, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param index
     *         The index, the child items should be added at, as an {@link Integer} value. The index
     *         must be between 0 and the value of the method <code>getChildCount(group):int</code>,
     *         otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @param children
     *         The array, which contains the child items, which should be added to the group, as an
     *         array of the generic type ChildType or an empty array, if no child items should be
     *         added
     * @return True, if all child items have been added to the group, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean addAllChildren(@NonNull GroupType group, int index, @NonNull ChildType... children);

    /**
     * Replaces the child item, which belongs to a specific index of a specific group, by an other
     * item.
     *
     * @param groupIndex
     *         The index of the group, the child item, which should be replaced, belongs to, as an
     *         {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @param index
     *         The index of the item, which should be replaced, as an {@link Integer} value. The
     *         index must be between 0 and the value of the method <code>getChildCount(groupIndex):int</code>
     *         - 1, otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @param child
     *         The child item, which should replace the child item at the given index, as an
     *         instance of the generic type ChildType. The child item may not be null
     * @return The child item, which has been replaced, as an instance of the generic type
     * ChildType. The child item may not be null
     */
    ChildType replaceChild(int groupIndex, int index, @NonNull ChildType child);

    /**
     * Replaces the child item, which belongs to a specific index of a specific group, by an other
     * item.
     *
     * @param group
     *         The group, the child item, which should be replaced, belongs to, as an instance of
     *         the generic type GroupType. The group may not be null. If the group does not belong
     *         to the adapter, a {@link NoSuchElementException} will be thrown
     * @param index
     *         The index of the item, which should be replaced, as an {@link Integer} value. The
     *         index must be between 0 and the value of the method <code>getChildCount(group):int</code>
     *         - 1, otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @param child
     *         The child item, which should replace the child item at the given index, as an
     *         instance of the generic type ChildType. The child item may not be null
     * @return The child item, which has been replaced, as an instance of the generic type
     * ChildType. The child item may not be null
     */
    ChildType replaceChild(@NonNull GroupType group, int index, @NonNull ChildType child);

    /**
     * Removes the child item, which belongs to a specific index, from the group, which belongs to a
     * specific index. The group, the child item belongs to, will not be removed, even if it becomes
     * empty.
     *
     * @param groupIndex
     *         The index of the group, the child item should be removed from, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @param index
     *         The index of the child item, which should be removed from the group, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getChildCount(groupIndex):int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @return The child item, which has been removed, as an instance of the generic type ChildType.
     * The item may not be null
     */
    ChildType removeChild(int groupIndex, int index);

    /**
     * Removes the child item, which belongs to a specific index, from the group, which belongs to a
     * specific index.
     *
     * @param removeEmptyGroup
     *         True, if the group, the child item belongs to, should also be removed, if it becomes
     *         empty, false otherwise
     * @param groupIndex
     *         The index of the group, the child item should be removed from, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @param index
     *         The index of the child item, which should be removed from the group, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getChildCount(groupIndex):int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @return The child item, which has been removed, as an instance of the generic type ChildType.
     * The item may not be null
     */
    ChildType removeChild(boolean removeEmptyGroup, int groupIndex, int index);

    /**
     * Removes the child item, which belongs to a specific index, from a specific group. The group,
     * the child item belongs to, will not be removed, even if it becomes empty.
     *
     * @param group
     *         The group, the child item should be removed from, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param index
     *         The index of the child item, which should be removed from the group, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getChildCount(group):int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @return The child item, which has been removed, as an instance of the generic type ChildType.
     * The item may not be null
     */
    ChildType removeChild(@NonNull GroupType group, int index);

    /**
     * Removes the child item, which belongs to a specific index, from a specific group.
     *
     * @param removeEmptyGroup
     *         True, if the group, the child item belongs to, should also be removed, if it becomes
     *         empty, false otherwise
     * @param group
     *         The group, the child item should be removed from, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param index
     *         The index of the child item, which should be removed from the group, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getChildCount(group):int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @return The child item, which has been removed, as an instance of the generic type ChildType.
     * The item may not be null
     */
    ChildType removeChild(boolean removeEmptyGroup, @NonNull GroupType group, int index);

    /**
     * Removes a specific child item from the group, which belongs to a specific index. The group,
     * the child item belongs to, will not be removed, even if it becomes empty.
     *
     * @param groupIndex
     *         The index of the group, the child item should be removed from, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @param child
     *         The child item, which should be removed from the group, as an instance of the generic
     *         type ChildType. The child item may not be null
     * @return True, if the child item has been removed from the group, false otherwise
     */
    boolean removeChild(int groupIndex, @NonNull ChildType child);

    /**
     * Removes a specific child item from the group, which belongs to a specific index.
     *
     * @param removeEmptyGroup
     *         True, if the group, the child item belongs to, should also be removed, if it becomes
     *         empty, false otherwise
     * @param groupIndex
     *         The index of the group, the child item should be removed from, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @param child
     *         The child item, which should be removed from the group, as an instance of the generic
     *         type ChildType. The child item may not be null
     * @return True, if the child item has been removed from the group, false otherwise
     */
    boolean removeChild(boolean removeEmptyGroup, int groupIndex, @NonNull ChildType child);

    /**
     * Removes a specific child item from a specific group. The group, the child item belongs to,
     * will not be removed, even if it becomes empty.
     *
     * @param group
     *         The group, the child item should be removed from, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param child
     *         The child item, which should be removed from the group, as an instance of the generic
     *         type ChildType. The child item may not be null
     * @return True, if the child item has been removed from the group, false otherwise
     */
    boolean removeChild(@NonNull GroupType group, @NonNull ChildType child);

    /**
     * Removes a specific child item from a specific group.
     *
     * @param removeEmptyGroup
     *         True, if the group, the child item belongs to, should also be removed, if it becomes
     *         empty, false otherwise
     * @param group
     *         The group, the child item should be removed from, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param child
     *         The child item, which should be removed from the group, as an instance of the generic
     *         type ChildType. The child item may not be null
     * @return True, if the child item has been removed from the group, false otherwise
     */
    boolean removeChild(boolean removeEmptyGroup, @NonNull GroupType group,
                        @NonNull ChildType child);

    /**
     * Removes all child items, which are contained by a specific collection, from the adapter. No
     * groups will be removed, even if they become empty.
     *
     * @param children
     *         The collection, which contains the child items, which should be removed from the
     *         adapter, as an instance of the type {@link Collection} or an empty collection, if no
     *         child items should be removed
     * @return True, if all child items have been removed from the adapter, false otherwise
     */
    boolean removeAllChildren(@NonNull Collection<? extends ChildType> children);

    /**
     * Removes all child items, which are contained by a specific collection, from the adapter.
     *
     * @param removeEmptyGroups
     *         True, if groups, which become empty, should also be removed, false otherwise
     * @param children
     *         The collection, which contains the child items, which should be removed from the
     *         adapter, as an instance of the type {@link Collection} or an empty collection, if no
     *         child items should be removed
     * @return True, if all child items have been removed from the adapter, false otherwise
     */
    boolean removeAllChildren(boolean removeEmptyGroups,
                              @NonNull Collection<? extends ChildType> children);

    /**
     * Removes all child items, which are contained by a specific collection, from the group, which
     * belongs to a specific index. The group, the child items belong to, will not be removed, even
     * if it becomes empty.
     *
     * @param groupIndex
     *         The index of the group, the child items should be removed from, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @param children
     *         The collection, which contains the child items, which should be removed from the
     *         group, as an instance of the type {@link Collection} or an empty collection, if no
     *         child items should be removed
     * @return True, if all child items have been removed from the group, false otherwise
     */
    boolean removeAllChildren(int groupIndex, @NonNull Collection<? extends ChildType> children);

    /**
     * Removes all child items, which are contained by a specific collection, from the group, which
     * belongs to a specific index.
     *
     * @param removeEmptyGroup
     *         True, if the group, the child items belong to, should also be removed, if it becomes
     *         empty, false otherwise
     * @param groupIndex
     *         The index of the group, the child items should be removed from, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @param children
     *         The collection, which contains the child items, which should be removed from the
     *         group, as an instance of the type {@link Collection} or an empty collection, if no
     *         child items should be removed
     * @return True, if all child items have been removed from the group, false otherwise
     */
    boolean removeAllChildren(boolean removeEmptyGroup, int groupIndex,
                              @NonNull Collection<? extends ChildType> children);

    /**
     * Removes all child items, which are contained by a specific collection, from a specific group.
     * No groups will be removed, even if they become empty.
     *
     * @param group
     *         The group, the child items should be removed from, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param children
     *         The collection, which contains the child items, which should be removed from the
     *         group, as an instance of the type {@link Collection} or an empty collection, if no
     *         child items should be removed
     * @return True, if all child items have been removed from the group, false otherwise
     */
    boolean removeAllChildren(@NonNull GroupType group,
                              @NonNull Collection<? extends ChildType> children);

    /**
     * Removes all child items, which are contained by a specific collection, from a specific
     * group.
     *
     * @param removeEmptyGroup
     *         True, if the group, the child items belong to, should also be removed, if it becomes
     *         empty, false otherwise
     * @param group
     *         The group, the child items should be removed from, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param children
     *         The collection, which contains the child items, which should be removed from the
     *         group, as an instance of the type {@link Collection} or an empty collection, if no
     *         child items should be removed
     * @return True, if all child items have been removed from the group, false otherwise
     */
    boolean removeAllChildren(boolean removeEmptyGroup, @NonNull GroupType group,
                              @NonNull Collection<? extends ChildType> children);

    /**
     * Removes all child items, which are contained by a specific array, from the adapter. No groups
     * will be removed, even if they become empty.
     *
     * @param children
     *         The array, which contains the child items, which should be removed from the adapter,
     *         as an array of the generic type ChildType or an empty array, if no child items should
     *         be removed
     * @return True, if all child items have been removed from the adapter, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean removeAllChildren(@NonNull ChildType... children);

    /**
     * Removes all child items, which are contained by a specific array, from the adapter.
     *
     * @param removeEmptyGroups
     *         True, if the groups, which become empty, should also be removed, false otherwise
     * @param children
     *         The array, which contains the child items, which should be removed from the adapter,
     *         as an array of the generic type ChildType or an empty array, if no child items should
     *         be removed
     * @return True, if all child items have been removed from the adapter, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean removeAllChildren(boolean removeEmptyGroups, @NonNull ChildType... children);

    /**
     * Removes all child items, which are contained by a specific array, from the group, which
     * belongs to a specific index. The group, the child items belong to, will not be removed, even
     * if it becomes empty.
     *
     * @param groupIndex
     *         The index of the group, the child items should be removed from, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @param children
     *         The array, which contains the child items, which should be removed from the group, as
     *         an array of the generic type ChildType or an empty array, if no child items should be
     *         removed
     * @return True, if all child items have been removed from the group, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean removeAllChildren(int groupIndex, @NonNull ChildType... children);

    /**
     * Removes all child items, which are contained by a specific array, from the group, which
     * belongs to a specific index.
     *
     * @param removeEmptyGroup
     *         True, if the group, the child items belong to, should also be removed, if it becomes
     *         empty, false otherwise
     * @param groupIndex
     *         The index of the group, the child items should be removed from, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @param children
     *         The array, which contains the child items, which should be removed from the group, as
     *         an array of the generic type ChildType or an empty array, if no child items should be
     *         removed
     * @return True, if all child items have been removed from the group, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean removeAllChildren(boolean removeEmptyGroup, int groupIndex,
                              @NonNull ChildType... children);

    /**
     * Removes all child items, which are contained by a specific array, from a specific group. The
     * group, the child items belong to, will not be removed, even if it becomes empty.
     *
     * @param group
     *         The group, the child items should be removed from, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param children
     *         The array, which contains the child items, which should be removed from the group, as
     *         an array of the generic type ChildType or an empty collection, if no child items
     *         should be removed
     * @return True, if all child items have been removed from the group, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean removeAllChildren(@NonNull GroupType group, @NonNull ChildType... children);

    /**
     * Removes all child items, which are contained by a specific array, from a specific group.
     *
     * @param removeEmptyGroup
     *         True, if the group, the child items belong to, should also be removed, if it becomes
     *         empty, false otherwise
     * @param group
     *         The group, the child items should be removed from, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param children
     *         The array, which contains the child items, which should be removed from the group, as
     *         an array of the generic type ChildType or an empty collection, if no child items
     *         should be removed
     * @return True, if all child items have been removed from the group, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean removeAllChildren(boolean removeEmptyGroup, @NonNull GroupType group,
                              @NonNull ChildType... children);

    /**
     * Removes all child items from the adapter, except of the items, which are contained by a
     * specific collection. No groups will be removed, even if they become empty.
     *
     * @param children
     *         The collection, which contains the child items, which should be retained, as an
     *         instance of the type {@link Collection} or an empty collection, if no child items
     *         should be retained
     */
    void retainAllChildren(@NonNull Collection<? extends ChildType> children);

    /**
     * Removes all child items from the adapter, except of the items, which are contained by a
     * specific collection.
     *
     * @param removeEmptyGroups
     *         True, if groups, which become empty, should be removed, false otherwise
     * @param children
     *         The collection, which contains the child items, which should be retained, as an
     *         instance of the type {@link Collection} or an empty collection, if no child items
     *         should be retained
     */
    void retainAllChildren(boolean removeEmptyGroups,
                           @NonNull Collection<? extends ChildType> children);

    /**
     * Removes all child items from the group, which belongs to a specific index, except of the
     * items, which are contained by a specific collection. The group, the child items belong to,
     * will not be removed, even if it becomes empty.
     *
     * @param groupIndex
     *         The index of the group, the child items should be removed from, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @param children
     *         The collection, which contains the child items, which should be retained, as an
     *         instance of the type {@link Collection} or an empty collection, if no child items
     *         should be retained
     */
    void retainAllChildren(int groupIndex, @NonNull Collection<? extends ChildType> children);

    /**
     * Removes all child items from the group, which belongs to a specific index, except of the
     * items, which are contained by a specific collection.
     *
     * @param removeEmptyGroup
     *         True, if the group, the child items belong to, should be removed, if it becomes
     *         empty, false otherwise
     * @param groupIndex
     *         The index of the group, the child items should be removed from, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @param children
     *         The collection, which contains the child items, which should be retained, as an
     *         instance of the type {@link Collection} or an empty collection, if no child items
     *         should be retained
     */
    void retainAllChildren(boolean removeEmptyGroup, int groupIndex,
                           @NonNull Collection<? extends ChildType> children);

    /**
     * Removes all child items from a specific group, except of the items, which are contained by a
     * specific collection. The group, the child items belong to, will not be removed, even if it
     * becomes empty.
     *
     * @param group
     *         The group, the child items should be removed from, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param children
     *         The collection, which contains the child items, which should be retained, as an
     *         instance of the type {@link Collection} or an empty collection, if no child items
     *         should be retained
     */
    void retainAllChildren(@NonNull GroupType group,
                           @NonNull Collection<? extends ChildType> children);

    /**
     * Removes all child items from a specific group, except of the items, which are contained by a
     * specific collection.
     *
     * @param removeEmptyGroup
     *         True, if the group, the child items belong to, should be removed, if it becomes
     *         empty, false otherwise
     * @param group
     *         The group, the child items should be removed from, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param children
     *         The collection, which contains the child items, which should be retained, as an
     *         instance of the type {@link Collection} or an empty collection, if no child items
     *         should be retained
     */
    void retainAllChildren(boolean removeEmptyGroup, @NonNull GroupType group,
                           @NonNull Collection<? extends ChildType> children);

    /**
     * Removes all child items from the adapter, except of the items, which are contained by a
     * specific array. No groups will be removed, even if they become empty.
     *
     * @param children
     *         The array, which contains the child items, which should be retained, as an array of
     *         the generic type ChildType or an empty array, if no child items should be retained
     */
    @SuppressWarnings("unchecked")
    void retainAllChildren(@NonNull ChildType... children);

    /**
     * Removes all child items from the adapter, except of the items, which are contained by a
     * specific array.
     *
     * @param removeEmptyGroups
     *         True, if groups, which become empty, should also be removed, false otherwise
     * @param children
     *         The array, which contains the child items, which should be retained, as an array of
     *         the generic type ChildType or an empty array, if no child items should be retained
     */
    @SuppressWarnings("unchecked")
    void retainAllChildren(boolean removeEmptyGroups, @NonNull ChildType... children);

    /**
     * Removes all child items from the group, which belongs to a specific index, except of the
     * items, which are contained by a specific array. The group, the child items belong to, will
     * not be removed, even if it becomes empty.
     *
     * @param groupIndex
     *         The index of the group, the child items should be removed from, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @param children
     *         The array, which contains the child items, which should be retained, as an array of
     *         the generic type ChildType or an empty collection, if no child items should be
     *         retained
     */
    @SuppressWarnings("unchecked")
    void retainAllChildren(int groupIndex, @NonNull ChildType... children);

    /**
     * Removes all child items from the group, which belongs to a specific index, except of the
     * items, which are contained by a specific array.
     *
     * @param removeEmptyGroup
     *         True, if the group, the child items belong to, should be removed, if it becomes
     *         empty, false otherwise
     * @param groupIndex
     *         The index of the group, the child items should be removed from, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @param children
     *         The array, which contains the child items, which should be retained, as an array of
     *         the generic type ChildType or an empty collection, if no child items should be
     *         retained
     */
    @SuppressWarnings("unchecked")
    void retainAllChildren(boolean removeEmptyGroup, int groupIndex,
                           @NonNull ChildType... children);

    /**
     * Removes all child items from a specific group, except of the items, which are contained by a
     * specific array. The group, the child items belong to, will not be removed, even if it becomes
     * empty.
     *
     * @param group
     *         The group, the child items should be removed from, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param children
     *         The array, which contains the child items, which should be retained, as an array of
     *         the generic type ChildType or an empty collection, if no child items should be
     *         retained
     */
    @SuppressWarnings("unchecked")
    void retainAllChildren(@NonNull GroupType group, @NonNull ChildType... children);

    /**
     * Removes all child items from a specific group, except of the items, which are contained by a
     * specific array.
     *
     * @param removeEmptyGroup
     *         True, if the group, the child items belong to, should be removed, if it becomes
     *         empty, false otherwise
     * @param group
     *         The group, the child items should be removed from, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param children
     *         The array, which contains the child items, which should be retained, as an array of
     *         the generic type ChildType or an empty collection, if no child items should be
     *         retained
     */
    @SuppressWarnings("unchecked")
    void retainAllChildren(boolean removeEmptyGroup, @NonNull GroupType group,
                           @NonNull ChildType... children);

    /**
     * Removes all child items from the adapter.
     */
    void clearChildren();

    /**
     * Removes all child items from the adapter.
     *
     * @param removeEmptyGroups
     *         True, if groups, which become empty, should also be removed, false otherwise
     */
    void clearChildren(boolean removeEmptyGroups);

    /**
     * Removes all child items from the group, which belongs to a specific index.
     *
     * @param groupIndex
     *         The index of the group, whose child item should be removed, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     */
    void clearChildren(int groupIndex);

    /**
     * Removes all child items from the group, which belongs to a specific index.
     *
     * @param removeEmptyGroup
     *         True, if the group, the child items belong to, should also be removed, if it becomes
     *         empty, false otherwise
     * @param groupIndex
     *         The index of the group, whose child item should be removed, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     */
    void clearChildren(boolean removeEmptyGroup, int groupIndex);

    /**
     * Removes all child items from a specific group.
     *
     * @param group
     *         The group, whose child items should be removed, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     */
    void clearChildren(@NonNull GroupType group);

    /**
     * Removes all child items from a specific group.
     *
     * @param removeEmptyGroup
     *         True, if the group, the child items belong to, should also be removed, if it becomes
     *         empty, false otherwise
     * @param group
     *         The group, whose child items should be removed, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     */
    void clearChildren(boolean removeEmptyGroup, @NonNull GroupType group);

    /**
     * Returns an iterator, which allows to iterate the adapter's child items.
     *
     * @return An iterator, which allows to iterate the adapter's child items, as an instance of the
     * type {@link Iterator}. The iterator may not be null
     */
    Iterator<ChildType> childIterator();

    /**
     * Returns an iterator, which allows to iterate the child items of the group, which belongs to a
     * specific index.
     *
     * @param groupIndex
     *         The index of the group, whose child items should be iterated, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return An iterator, which allows to iterate the group's items, as an instance of the type
     * {@link Iterator}. The iterator may not be null
     */
    Iterator<ChildType> childIterator(int groupIndex);

    /**
     * Returns an iterator, which allows to iterate the child items of a specific group.
     *
     * @param group
     *         The group, whose child items should be iterated, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @return An iterator, which allows to iterate the group's items, as an instance of the type
     * {@link Iterator}. The iterator may not be null
     */
    Iterator<ChildType> childIterator(@NonNull GroupType group);

    /**
     * Returns a list iterator, which allow to iterate the child items of the group, which belongs
     * to a specific index.
     *
     * @param groupIndex
     *         The index of the group, whose child items should be iterated, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return A list iterator, which allows to iterate the group's items, as an instance of the
     * type {@link ListIterator}. The iterator may not be null
     */
    ListIterator<ChildType> childListIterator(int groupIndex);

    /**
     * Returns a list iterator, which allow to iterate the child items of a specific group.
     *
     * @param group
     *         The group, whose child items should be iterated, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @return A list iterator, which allows to iterate the group's items, as an instance of the
     * type {@link ListIterator}. The iterator may not be null
     */
    ListIterator<ChildType> childListIterator(@NonNull GroupType group);

    /**
     * Returns a list iterator, which allow to iterate the child items of the group, which belongs
     * to a specific index, starting at a specific index.
     *
     * @param groupIndex
     *         The index of the group, whose child items should be iterated, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @param index
     *         The index, the iterator should start at, as an {@link Integer} value. The index must
     *         be between 0 and the value of the method <code>getChildCount(groupIndex):int</code> -
     *         1, otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @return A list iterator, which allows to iterate the group's items, as an instance of the
     * type {@link ListIterator}. The iterator may not be null
     */
    ListIterator<ChildType> childListIterator(int groupIndex, int index);

    /**
     * Returns a list iterator, which allow to iterate the child items of a specific group, starting
     * at a specific index.
     *
     * @param group
     *         The group, whose child items should be iterated, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param index
     *         The index, the iterator should start at, as an {@link Integer} value. The index must
     *         be between 0 and the value of the method <code>getChildCount(group):int</code> - 1,
     *         otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @return A list iterator, which allows to iterate the group's items, as an instance of the
     * type {@link ListIterator}. The iterator may not be null
     */
    ListIterator<ChildType> childListIterator(@NonNull GroupType group, int index);

    /**
     * Returns a list, which contains the child items of the group, which belongs to a specific
     * index, between a specific start and end index.
     *
     * @param groupIndex
     *         The index of the group, whose child items should be returned, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @param start
     *         The start index of the child items, which should be returned, as an {@link Integer}
     *         value. The child item, which belongs to the start index will be included. The index
     *         must be between 0 and the value of the method <code>getChildCount(groupIndex):int</code>
     *         - 1, otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @param end
     *         The end index of the child items, which should be returned, as an {@link Integer}
     *         value. The child item, which belongs to the end index, will be excluded. The index
     *         must be between 0 and the value of the method <code>getChildCount(groupIndex):int</code>
     *         -1 and it must be greater than the start index, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @return A list, which contains the group's child items, between a specific start end end
     * index, as an instance of the type {@link List} or an empty list, if the group does not
     * contain any child items
     */
    List<ChildType> subListChildren(int groupIndex, int start, int end);

    /**
     * Returns a list, which contains the child items of a specific group between a specific start
     * and end index.
     *
     * @param group
     *         The group, whose child items should be returned, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param start
     *         The start index of the child items, which should be returned, as an {@link Integer}
     *         value. The child item, which belongs to the start index will be included. The index
     *         must be between 0 and the value of the method <code>getChildCount(group):int</code> -
     *         1, otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @param end
     *         The end index of the child items, which should be returned, as an {@link Integer}
     *         value. The child item, which belongs to the end index, will be excluded. The index
     *         must be between 0 and the value of the method <code>getChildCount(group):int</code>
     *         -1 and it must be greater than the start index, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @return A list, which contains the group's child items, between a specific start end end
     * index, as an instance of the type {@link List} or an empty list, if the group does not
     * contain any child items
     */
    List<ChildType> subListChildren(@NonNull GroupType group, int start, int end);

    /**
     * Returns an array, which contains all of the adapter's child items.
     *
     * @return An array, which contains all of the adapter's child items, as an {@link Object} array
     * or an empty array, if the adapter does not contain any child items
     */
    Object[] childrenToArray();

    /**
     * Returns an array, which contains the child items of the group, which belongs to a specific
     * index.
     *
     * @param groupIndex
     *         The index of the group, whose child items should be returned, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return An array, which contains the group's child items, as an {@link Object} array or an
     * empty array, if the group does not contain any child items
     */
    Object[] childrenToArray(int groupIndex);

    /**
     * Returns an array, which contains the child items of a specific group. *
     *
     * @param group
     *         The group, whose child items should be returned, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @return An array, which contains the group's child items, as an {@link Object} array or an
     * empty array, if the group does not contain any child items
     */
    Object[] childrenToArray(@NonNull GroupType group);

    /**
     * Returns an array, which contains all of the adapter's child items. If the given array is
     * large enough to hold the items, the specified array is used, otherwise an array of the same
     * type is created. If the given array can hold more items, the array's elements, following the
     * group's child items, are set to null.
     *
     * @param <T>
     *         The type of the array, which should be returned
     * @param array
     *         The array, which should be used, if it is large enough, as an array of the generic
     *         type T. The array may not be null
     * @return An array, which contains all of adapter's child items, as an array of the generic
     * type T or an empty array, if the adapter does not contain any child items
     */
    <T> T[] childrenToArray(@NonNull T[] array);

    /**
     * Returns an array, which contains all child items of the group, which belongs to a specific
     * index. If the given array is large enough to hold the items, the specified array is used,
     * otherwise an array of the same type is created. If the given array can hold more items, the
     * array's elements, following the group's child items, are set to null.
     *
     * @param <T>
     *         The type of the array, which should be returned
     * @param groupIndex
     *         The index of the group, whose child items should be returned, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @param array
     *         The array, which should be used, if it is large enough, as an array of the generic
     *         type T. The array may not be null
     * @return An array, which contains all of the group's child items, as an array of the generic
     * type T or an empty array, if the group does not contain any child items
     */
    <T> T[] childrenToArray(int groupIndex, @NonNull T[] array);

    /**
     * Returns an array, which contains all child items of a specific group. If the given array is
     * large enough to hold the items, the specified array is used, otherwise an array of the same
     * type is created. If the given array can hold more items, the array's elements, following the
     * group's child items, are set to null.
     *
     * @param <T>
     *         The type of the array, which should be returned
     * @param group
     *         The group, whose child items should be returned, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param array
     *         The array, which should be used, if it is large enough, as an array of the generic
     *         type T. The array may not be null
     * @return An array, which contains all of the group's child items, as an array of the generic
     * type T or an empty array, if the group does not contain any child items
     */
    <T> T[] childrenToArray(@NonNull GroupType group, @NonNull T[] array);

    /**
     * Returns the child item, which belongs to a specific index of the group, which belongs to a
     * specific index.
     *
     * @param groupIndex
     *         The index of the group, which contains the child item, which should be returned, as
     *         an {@link Integer} value. The value must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @param index
     *         The index of the child item, which should be returned, as an {@link Integer} value.
     *         The index must be between 0 and the value of the method <code>getChildCount(groupIndex):int</code>
     *         - 1, otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @return The child item, which belongs to the given index, as an instance of the generic type
     * ChildType. The child item may not be null
     */
    ChildType getChild(int groupIndex, int index);

    /**
     * Returns the child item, which belongs to a specific index of a specific group.
     *
     * @param group
     *         The group, which contains the child item, which should be returned, as an instance of
     *         the generic type GroupType. The group may not be null. If the group does not belong
     *         to the adapter, a {@link NoSuchElementException} will be thrown
     * @param index
     *         The index of the child item, which should be returned, as an {@link Integer} value.
     *         The index must be between 0 and the value of the method <code>getChildCount(group):int</code>
     *         - 1, otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @return The child item, which belongs to the given index, as an instance of the generic type
     * ChildType. The child item may not be null
     */
    ChildType getChild(@NonNull GroupType group, int index);

    /**
     * Returns the index of the group, a specific child item belongs to.
     *
     * @param child
     *         The child item, whose group index should be returned, as an instance of the generic
     *         type ChildType. The child item may not be null
     * @return The index of the group, the given child item belongs to, as an {@link Integer} value
     * or -1, if the adapter does not contain the given child item
     */
    int indexOfChild(@NonNull ChildType child);

    /**
     * Returns the index of a specific child item within the group, which belongs to a specific
     * index.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose index should be returned, belongs to,
     *         as an {@link Integer} value. The value must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @param child
     *         The child item, whose index should be returned, as an instance of the generic type
     *         ChildType. The child item may not be null
     * @return The index of the given child item, as an {@link Integer} value or -1, if the group
     * does not contain the given child item
     */
    int indexOfChild(int groupIndex, @NonNull ChildType child);

    /**
     * Returns the index of a specific child item within a specific group.
     *
     * @param group
     *         The group, the child item, whose index should be returned, belongs to, as an instance
     *         of the generic type GroupType. The group may not be null. If the group does not
     *         belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @param child
     *         The child item, whose index should be returned, as an instance of the generic type
     *         ChildType. The child item may not be null
     * @return The index of the given child item, as an {@link Integer} value or -1, if the group
     * does not contain the given child item
     */
    int indexOfChild(@NonNull GroupType group, @NonNull ChildType child);

    /**
     * Returns the last index of the group, a specific child item belongs to.
     *
     * @param child
     *         The child item, whose last group index should be returned, as an instance of the
     *         generic type ChildType. The child item may not be null
     * @return The last index of the group, the given child item belongs to, as an {@link Integer}
     * value or -1, if the adapter does not contain the given child item
     */
    int lastIndexOfChild(@NonNull ChildType child);

    /**
     * Returns the last index of a specific child item within the group, which belongs to a specific
     * index.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose last index should be returned, belongs
     *         to, as an {@link Integer} value. The index must be between 0 and the value of the
     *         method <code>getGroupCount():int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @param child
     *         The child item, whose last index should be returned, as an instance of the generic
     *         type ChildType. The child item may not be null
     * @return The last index of the given child item, as an {@link Integer} value or -1, if the
     * group does not contain the given child item
     */
    int lastIndexOfChild(int groupIndex, @NonNull ChildType child);

    /**
     * Returns the last index of a specific child item within a specific group.
     *
     * @param group
     *         The group, the child item, whose last index should be returned, belongs to, as an
     *         instance of the generic type GroupType. The group may not be null. If the group does
     *         not belong to the adapter, a {@link NoSuchElementException} will be thrown
     * @param child
     *         The child item, whose last index should be returned, as an instance of the generic
     *         type ChildType. The child item may not be null
     * @return The last index of the given child item, as an {@link Integer} value or -1, if the
     * group does not contain the given child item
     */
    int lastIndexOfChild(@NonNull GroupType group, @NonNull ChildType child);

    /**
     * Returns, whether the adapter contains a specific child item, or not.
     *
     * @param child
     *         The child item, whose presence should be checked, as an instance of the generic type
     *         ChildType. The child item may not be null
     * @return True, if the adapter contains the given child item, false otherwise
     */
    boolean containsChild(@NonNull ChildType child);

    /**
     * Returns, whether the group, which belongs to a specific index, contains a specific child
     * item, or not.
     *
     * @param groupIndex
     *         The index of the group, whose child items should be checked, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> -1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @param child
     *         The child item, whose presence should be checked, as an instance of the generic type
     *         ChildType. The child item may not be null
     * @return True, if the group contains the given child item, false otherwise
     */
    boolean containsChild(int groupIndex, @NonNull ChildType child);

    /**
     * Returns, whether a specific group contains a specific child item, or not.
     *
     * @param group
     *         The group, whose child items should be checked, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param child
     *         The child item, whose presence should be checked, as an instance of the generic type
     *         ChildType. The child item may not be null
     * @return True, if the group contains the given child item, false otherwise
     */
    boolean containsChild(@NonNull GroupType group, @NonNull ChildType child);

    /**
     * Returns, whether the adapter contains all child items, which are contained by a specific
     * collection, or not.
     *
     * @param children
     *         The collection, which contains the child items, whose presence should be checked, as
     *         an instance of the type {@link Collection}. The collection may not be null
     * @return True, if the adapter contains all child items, which are contained by the given
     * collection, false otherwise
     */
    boolean containsAllChildren(@NonNull Collection<? extends ChildType> children);

    /**
     * Returns, whether the group, which belongs to a specific index, contains all child items,
     * which are contained by a specific collection, or not.
     *
     * @param groupIndex
     *         The index of the group, whose child items should be checked, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> -1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @param children
     *         The collection, which contains the child items, whose presence should be checked, as
     *         an instance of the type {@link Collection}. The collection may not be null
     * @return True, if the group contains all child items, which are contained by the given
     * collection, false otherwise
     */
    boolean containsAllChildren(int groupIndex, @NonNull Collection<? extends ChildType> children);

    /**
     * Returns, whether a specific group contains all child items, which are contained by a specific
     * collection, or not.
     *
     * @param group
     *         The group, whose child items should be checked, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param children
     *         The collection, which contains the child items, whose presence should be checked, as
     *         an instance of the type {@link Collection}. The collection may not be null
     * @return True, if the group contains all child items, which are contained by the given
     * collection, false otherwise
     */
    boolean containsAllChildren(@NonNull GroupType group,
                                @NonNull Collection<? extends ChildType> children);

    /**
     * Returns, whether the adapter contains all child items, which are contained by a specific
     * array, or not.
     *
     * @param children
     *         The array, which contains the child items, whose presence should be checked, as an
     *         array of the generic type ChildType. The array may not be null
     * @return True, if the adapter contains all child items, which are contained by the given
     * array, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean containsAllChildren(@NonNull ChildType... children);

    /**
     * Returns the total number of child items, which are contained by the adapter.
     *
     * @return The total number of child items, which are contained by the adapter, as an {@link
     * Integer} value
     */
    int getChildCount();

    /**
     * Returns the number of child items, which are contained by the group, which belongs to a
     * specific index.
     *
     * @param groupIndex
     *         The index of the group, whose child items should be counted, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return The number of child items, which are contained by the group, as an {@link Integer}
     * value
     */
    int getChildCount(int groupIndex);

    /**
     * Returns the number of child items, which are contained by a specific group.
     *
     * @param group
     *         The group, whose child items should be counted, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @return The number of child items, which are contained by the group, as an {@link Integer}
     * value
     */
    int getChildCount(@NonNull GroupType group);

    /**
     * Returns a list, which contains all of the adapter's child items.
     *
     * @return A list, which contains all of the adapter's child items, as an instance of the type
     * {@link List} or an empty list, if the adapter does not contain any child items
     */
    List<ChildType> getAllChildren();

    /**
     * Returns a list, which contains all child items of the group, which belongs to a specific
     * index.
     *
     * @param groupIndex
     *         The index of the group, whose child items should be returned, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return A list, which contains all child items of the group, as an instance of the type
     * {@link List} or an empty list, if the group does not contain any child items
     */
    List<ChildType> getAllChildren(int groupIndex);

    /**
     * Returns a list, which contains all child items of a specific group.
     *
     * @param group
     *         The group, whose child items should be returned, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @return A list, which contains all child items of the group, as an instance of the type
     * {@link List} or an empty list, if the group does not contain any child items
     */
    List<ChildType> getAllChildren(@NonNull GroupType group);

    /**
     * Returns, whether the group, which belongs to a specific index, contains all child items,
     * which are contained by a specific array, or not.
     *
     * @param groupIndex
     *         The index of the group, whose child items should be checked, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> -1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @param children
     *         The array, which contains the child items, whose presence should be checked, as an
     *         array of the generic type ChildType. The array may not be null
     * @return True, if the group contains all child items, which are contained by the given array,
     * false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean containsAllChildren(int groupIndex, @NonNull ChildType... children);

    /**
     * Returns, whether a specific group contains all child items, which are contained by a specific
     * array, or not.
     *
     * @param group
     *         The group, whose child items should be checked, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param children
     *         The array, which contains the child items, whose presence should be checked, as an
     *         array of the generic type ChildType. The array may not be null
     * @return True, if the group contains all child items, which are contained by the given array,
     * false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean containsAllChildren(@NonNull GroupType group, @NonNull ChildType... children);

    /**
     * Returns, whether the group, which belongs to a specific index, is currently expanded, or
     * not.
     *
     * @param index
     *         The index of the group, whose expansion should be checked, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @return True, if the group, which belongs to the given index, is currently expanded, false if
     * the group is collapsed
     */
    boolean isGroupExpanded(int index);

    /**
     * Returns, whether a specific group is currently expanded, or not.
     *
     * @param group
     *         The group, whose expansion should be checked, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @return True, if the given group is currently expanded, false if the group is collapsed
     */
    boolean isGroupExpanded(@NonNull GroupType group);

    /**
     * Returns the first expanded group.
     *
     * @return The first expanded group, as an instance of the generic type GroupType or null, if no
     * group is currently expanded
     */
    GroupType getFirstExpandedGroup();

    /**
     * Returns the index of the first expanded group.
     *
     * @return The index of the first expanded group, as an {@link Integer} value or -1, if no group
     * is currently expanded
     */
    int getFirstExpandedGroupIndex();

    /**
     * Returns the last expanded group.
     *
     * @return The last expanded group, as an instance of the generic type GroupType or null, if no
     * group is currently expanded
     */
    GroupType getLastExpandedGroup();

    /**
     * Returns the index of the last expanded group.
     *
     * @return The index of the last expanded group, as an {@link Integer} value or -1, if no group
     * is currently expanded
     */
    int getLastExpandedGroupIndex();

    /**
     * Returns the first collapsed group.
     *
     * @return The first collapsed group, as an instance of the generic type GroupType or null, if
     * no group is currently expanded
     */
    GroupType getFirstCollapsedGroup();

    /**
     * Returns the index of the first collapsed group.
     *
     * @return The index of the first collapsed group, as an {@link Integer} value or -1, if no
     * group is currently expanded
     */
    int getFirstCollapsedGroupIndex();

    /**
     * Returns the last expanded group.
     *
     * @return The last expanded group, as an instance of the generic type GroupType or null, if no
     * group is currently expanded
     */
    GroupType getLastCollapsedGroup();

    /**
     * Returns the index of the last collapsed group.
     *
     * @return The index of the last collapsed group, as an {@link Integer} value or -1, if no group
     * is currently expanded
     */
    int getLastCollapsedGroupIndex();

    /**
     * Returns a list, which contains all currently expanded groups.
     *
     * @return A lust, which contains all currently expanded groups, as an instance of the type
     * {@link List} or an empty list, if no group is currently expanded
     */
    List<GroupType> getExpandedGroups();

    /**
     * Returns a list, which contains the indices of all expanded groups.
     *
     * @return A list, which contains the indices of all currently expanded groups, as an instance
     * of the type {@link List} or an empty list, if no group is currently expanded
     */
    List<Integer> getExpandedGroupIndices();

    /**
     * Returns a list, which contains all currently collapsed groups.
     *
     * @return A list, which contains all currently collapsed groups, as an instance of the type
     * {@link List} or an empty list, if no group is currently collapsed
     */
    List<GroupType> getCollapsedGroups();

    /**
     * Returns a list, which contains the indices of all collapsed groups.
     *
     * @return A list, which contains the indices of all currently collapsed groups, as an instance
     * of the type {@link List} or an empty list, if no group is currently collapsed
     */
    List<Integer> getCollapsedGroupIndices();

    /**
     * Returns the number of currently expanded groups.
     *
     * @return The number of currently expanded groups as an {@link Integer} value
     */
    int getExpandedGroupCount();

    /**
     * Sets, whether a specific group should be expanded, or not.
     *
     * @param group
     *         The group, which should be expanded, as an instance of the generic type GroupType.
     *         The group may not be null. If the group does not belong to the adapter, a {@link
     *         NoSuchElementException} will be thrown
     * @param expanded
     *         True, if the group should be expanded, false otherwise
     */
    void setGroupExpanded(@NonNull GroupType group, boolean expanded);

    /**
     * Sets, whether a specific group should be expanded, or not.
     *
     * @param index
     *         The index of the group, which should be expanded, as an {@link Integer} value. The
     *         index must be between 0 and the value of the method <code>getGroupCount():int</code>
     *         - 1, otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @param expanded
     *         True, if the group should be expanded, false otherwise
     */
    void setGroupExpanded(int index, boolean expanded);

    /**
     * Triggers the expansion of a specific group. This causes the group to become expanded, if it
     * is currently collapsed and vice versa.
     *
     * @param group
     *         The group, whose expansion should be triggered, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @return True, if the group has been expanded, false, if the item has been collapsed
     */
    boolean triggerGroupExpansion(@NonNull GroupType group);

    /**
     * Triggers the expansion of the group, which belongs to a specific index. This causes the group
     * to become expanded, if it is currently collapsed and vice versa.
     *
     * @param index
     *         The index of the group, whose expansion should be triggered, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return True, if the group has been expanded, false, if the item has been collapsed
     */
    boolean triggerGroupExpansion(int index);

    /**
     * Sets the expansion of all groups.
     *
     * @param expanded
     *         True, if the groups should be expanded, false otherwise
     */
    void setAllGroupsExpanded(boolean expanded);

    /**
     * Triggers the expansion of all groups. This causes a group to become expanded, if it is
     * currently collapsed and vice versa.
     */
    void triggerAllGroupExpansions();

    /**
     * Returns, whether a group's expansion is triggered, when it is clicked by the user, or not.
     *
     * @return True, if a group's expansion is triggered, when it is clicked by the user, false
     * otherwise
     */
    boolean isGroupExpansionTriggeredOnClick();

    /**
     * Sets, whether a group's expansion should be triggered, when it is clicked by the user, or
     * not.
     *
     * @param triggerGroupExpansionOnClick
     *         True, if a group's expansion should be triggered, when it is clicked by the user,
     *         false otherwise
     */
    void triggerGroupExpansionOnClick(boolean triggerGroupExpansionOnClick);

    /**
     * Adds a new listener, which should be notified, when the adapter's underlying data has been
     * modified.
     *
     * @param listener
     *         The listener, which should be added, as an instance of the type {@link
     *         ExpandableListAdapterListener}. The listener may not be null
     */
    void addAdapterListener(@NonNull ExpandableListAdapterListener<GroupType, ChildType> listener);

    /**
     * Removes a specific listener, which should not be notified, when the adapter's underlying data
     * has been modified, anymore.
     *
     * @param listener
     *         The listener, which should be removed, as an instance of the type {@link
     *         ExpandableListAdapterListener}. The listener may not be null
     */
    void removeAdapterListener(
            @NonNull ExpandableListAdapterListener<GroupType, ChildType> listener);

    /**
     * Adds a specific listener, which should be notified, when a group item has been expanded or
     * collapsed.
     *
     * @param listener
     *         The listener, which should be added, as an instance of the class {@link
     *         ExpansionListener}. The listener may not be null
     */
    void addExpansionListener(@NonNull ExpansionListener<GroupType, ChildType> listener);

    /**
     * Removes a specific listener, which should not be notified, when a group item has been
     * expanded or collapsed, anymore.
     *
     * @param listener
     *         The listener, which should be removed, as an instance of the class {@link
     *         ExpansionListener}. The listener may not be null
     */
    void removeExpansionListener(@NonNull ExpansionListener<GroupType, ChildType> listener);

    /**
     * Adds a new listener, which should be notified, when an item of the adapter has been clicked
     * by the user, anymore.
     *
     * @param listener
     *         The listener, which should be added, as an instance of the type {@link
     *         ExpandableListAdapterItemClickListener}. The listener may not be null
     */
    void addItemClickListener(
            @NonNull ExpandableListAdapterItemClickListener<GroupType, ChildType> listener);

    /**
     * Removes a specific listener, which should not be notified, when an item of the adapter has
     * been clicked by the user, anymore.
     *
     * @param listener
     *         The listener, which should be removed, as an instance of the type {@link
     *         ExpandableListAdapterItemClickListener}. The listener may not be null
     */
    void removeItemClickListener(
            @NonNull ExpandableListAdapterItemClickListener<GroupType, ChildType> listener);

    /**
     * Adds a new listener, which should be notified, when an item of the adapter has been
     * long-clicked by the user.
     *
     * @param listener
     *         The listener, which should be added, as an instance of the type {@link
     *         ExpandableListAdapterItemLongClickListener}. The listener may not be null
     */
    void addItemLongClickListener(
            @NonNull ExpandableListAdapterItemLongClickListener<GroupType, ChildType> listener);

    /**
     * Removes a specific listener, which should not be notified, when an item of the adapter has
     * been long-clicked by the user, anymore.
     *
     * @param listener
     *         The listener, which should be removed, as an instance of the type {@link
     *         ExpandableListAdapterItemLongClickListener}. The listener may not be null
     */
    void removeItemLongClickListener(
            @NonNull ExpandableListAdapterItemLongClickListener<GroupType, ChildType> listener);

    @Override
    ExpandableListAdapter<GroupType, ChildType> clone() throws CloneNotSupportedException;

}