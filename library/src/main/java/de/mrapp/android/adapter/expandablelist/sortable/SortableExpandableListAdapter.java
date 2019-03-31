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
package de.mrapp.android.adapter.expandablelist.sortable;

import android.widget.ExpandableListView;

import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import de.mrapp.android.adapter.Order;
import de.mrapp.android.adapter.SortingNotSupportedException;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a sortable list of
 * arbitrary group and child items, must implement. Such an adapter's purpose is to provide the
 * underlying data for visualization using a {@link ExpandableListView} widget.
 *
 * @param <GroupType>
 *         The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the adapter's child items
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface SortableExpandableListAdapter<GroupType, ChildType> {

    /**
     * Sorts the adapter's group items in an ascending order. If the underlying data of the
     * adapter's group items does not implement the interface {@link Comparable} a {@link
     * SortingNotSupportedException} will be thrown.
     */
    void sortGroups();

    /**
     * Sorts the adapter's group items in a specific order. If the underlying data of the adapter's
     * group items does not implement the interface {@link Comparable} a {@link
     * SortingNotSupportedException} will be thrown.
     *
     * @param order
     *         The order, which should be used to sort the group items, as a value of the enum
     *         {@link Order}. The order may either be <code>ASCENDING</code> or
     *         <code>DESCENDING</code>
     */
    void sortGroups(@NonNull Order order);

    /**
     * Sorts the adapter's group items in an ascending order, by using a comparator.
     *
     * @param comparator
     *         The comparator, which should be used to sort the group items, as an instance of the
     *         type {@link Comparator} or null, if the natural order should be used
     */
    void sortGroups(@Nullable Comparator<GroupType> comparator);

    /**
     * Sorts the adapter's group items in a specific order, by using a comparator.
     *
     * @param comparator
     *         The comparator, which should be used to sort the group items, as an instance of the
     *         type {@link Comparator} or null, if the natural order should be used
     * @param order
     *         The order, which should be used to sort the group items, as a value of the enum
     *         {@link Order}. The order may either be <code>ASCENDING</code> or
     *         <code>DESCENDING</code>
     */
    void sortGroups(@NonNull Order order, @Nullable Comparator<GroupType> comparator);

    /**
     * Adds a specific group item to the adapter. If the adapter's groups are currently sorted, the
     * group item will be added at the correct position regarding the current order. Otherwise, it
     * will be added at the end. If the adapter's underlying data does not implement the interface
     * {@link Comparable} a {@link SortingNotSupportedException} will be thrown.
     *
     * @param group
     *         The group item, which should be added to the adapter, as an instance of the generic
     *         type GroupType. The group item may not be null
     * @return The index of the the group item, which has been added to the adapter, as an {@link
     * Integer} value or -1, if the group item has not been added
     */
    int addGroupSorted(@NonNull GroupType group);

    /**
     * Adds a specific group item to the adapter. If the adapter's groups are currently sorted, the
     * group item will be added at the correct position regarding the current order. Otherwise, it
     * will be added at the end.
     *
     * @param group
     *         The group item, which should be added to the adapter, as an instance of the generic
     *         type GroupType. The group item may not be null
     * @param comparator
     *         The comparator, which should be used to sort the group items, as an instance of the
     *         type {@link Comparator} or null, if the natural order should be used
     * @return The index of the the group item, which has been added to the adapter, as an {@link
     * Integer} value or -1, if the item has not been added
     */
    int addGroupSorted(@NonNull GroupType group, @Nullable Comparator<GroupType> comparator);

    /**
     * Adds all group items, which are contained by a specific collection, to the adapter. If the
     * adapter's groups are currently sorted, the group items will be added at the correct position
     * regarding the current order. Otherwise, they will be added at the end. If the adapter's
     * underlying data does not implement the interface {@link Comparable} a {@link
     * SortingNotSupportedException} will be thrown.
     *
     * @param groups
     *         The collection, which contains the group items, which should be added to the adapter,
     *         as an instance of the type {@link Collection} or an empty collection, if no group
     *         items should be added
     * @return True, if all group items have been added to the adapter, false otherwise
     */
    boolean addAllGroupsSorted(@NonNull Collection<? extends GroupType> groups);

    /**
     * Adds all group items, which are contained by a specific collection, to the adapter. If the
     * adapter's groups are currently sorted, the group items will be added at the correct position
     * regarding the current order. Otherwise, they will be added at the end.
     *
     * @param groups
     *         The collection, which contains the group items, which should be added to the adapter,
     *         as an instance of the type {@link Collection} or an empty collection, if no group
     *         items should be added
     * @param comparator
     *         The comparator, which should be used to sort the group items, as an instance of the
     *         type {@link Comparator} or null, if the natural order should be used
     * @return True, if all group items have been added to the adapter, false otherwise
     */
    boolean addAllGroupsSorted(@NonNull Collection<? extends GroupType> groups,
                               @Nullable Comparator<GroupType> comparator);

    /**
     * Adds all group items, which are contained by a specific array, to the adapter. If the
     * adapter's groups are currently sorted, the group items will be added at the correct position
     * regarding the current order. Otherwise, they will be added at the end. If the adapter's
     * underlying data does not implement the interface {@link Comparable} a {@link
     * SortingNotSupportedException} will be thrown.
     *
     * @param groups
     *         The array, which contains the group items, which should be added to the adapter, as
     *         an array of the generic type GroupType or an empty array, if no group items should be
     *         added
     * @return True, if all group items have been added to the adapter, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean addAllGroupsSorted(@NonNull GroupType... groups);

    /**
     * Adds all group items, which are contained by a specific array, to the adapter. If the
     * adapter's groups are currently sorted, the group items will be added at the correct position
     * regarding the current order. Otherwise, they will be added at the end.
     *
     * @param comparator
     *         The comparator, which should be used to sort the group items, as an instance of the
     *         type {@link Comparator} or null, if the natural order should be used
     * @param groups
     *         The array, which contains the group items, which should be added to the adapter, as
     *         an array of the generic type GroupType or an empty array, if no group items should be
     *         added
     * @return True, if all group items have been added to the adapter, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean addAllGroupsSorted(@Nullable Comparator<GroupType> comparator,
                               @NonNull GroupType... groups);

    /**
     * Returns the current order of the adapter's group items.
     *
     * @return The current order of the adapter's group items as a value of the enum {@link Order}
     * or null, if the adapter's group items have not been sorted yet or if the adapter's underlying
     * data has been changed since it has been sorted the last time. If not null, the order may
     * either be <code>ASCENDING</code> or <code>DESCENDING</code>
     */
    Order getGroupOrder();

    /**
     * Sorts all of the adapter's child items in an ascending order. If the underlying data of the
     * adapter's child items does not implement the interface {@link Comparable} a {@link
     * SortingNotSupportedException} will be thrown.
     */
    void sortChildren();

    /**
     * Sorts all of the adapter's child items in a specific order. If the underlying data of the
     * adapter's child items does not implement the interface {@link Comparable} a {@link
     * SortingNotSupportedException} will be thrown.
     *
     * @param order
     *         The order, which should be used to sort the child items, as a value of the enum
     *         {@link Order}. The order may either be <code>ASCENDING</code> or
     *         <code>DESCENDING</code>
     */
    void sortChildren(@NonNull Order order);

    /**
     * Sorts all of the adapter's child items in an ascending order, by using a comparator.
     *
     * @param comparator
     *         The comparator, which should be used to sort the child items, as an instance of the
     *         type {@link Comparator} or null, if the natural order should be used
     */
    void sortChildren(@Nullable Comparator<ChildType> comparator);

    /**
     * Sorts all of the adapter's child items in a specific order, by using a comparator.
     *
     * @param comparator
     *         The comparator, which should be used to sort the child items, as an instance of the
     *         type {@link Comparator} or null, if the natural order should be used
     * @param order
     *         The order, which should be used to sort the child items, as a value of the enum
     *         {@link Order}. The order may either be <code>ASCENDING</code> or
     *         <code>DESCENDING</code>
     */
    void sortChildren(@NonNull Order order, @Nullable Comparator<ChildType> comparator);

    /**
     * Sorts the child items of the group, which belongs to a specific index, in an ascending order.
     * If the underlying data of the adapter's child items does not implement the interface {@link
     * Comparable} a {@link SortingNotSupportedException} will be thrown.
     *
     * @param groupIndex
     *         The index of the group, whose child items should be sorted, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     */
    void sortChildren(int groupIndex);

    /**
     * Sorts child items of the group, which belongs to a specific index, in a specific order. If
     * the underlying data of the adapter's child items does not implement the interface {@link
     * Comparable} a {@link SortingNotSupportedException} will be thrown.
     *
     * @param groupIndex
     *         The index of the group, whose child items should be sorted, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @param order
     *         The order, which should be used to sort the child items, as a value of the enum
     *         {@link Order}. The order may either be <code>ASCENDING</code> or
     *         <code>DESCENDING</code>
     */
    void sortChildren(int groupIndex, @NonNull Order order);

    /**
     * Sorts the child items of the group, which belongs to a specific index, in an ascending order,
     * by using a comparator.
     *
     * @param groupIndex
     *         The index of the group, whose child items should be sorted, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @param comparator
     *         The comparator, which should be used to sort the child items, as an instance of the
     *         type {@link Comparator} or null, if the natural order should be used
     */
    void sortChildren(int groupIndex, @Nullable Comparator<ChildType> comparator);

    /**
     * Sorts the child items of the group, which belongs to a specific index, in a specific order,
     * by using a comparator.
     *
     * @param groupIndex
     *         The index of the group, whose child items should be sorted, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @param comparator
     *         The comparator, which should be used to sort the child items, as an instance of the
     *         type {@link Comparator} or null, if the natural order should be used
     * @param order
     *         The order, which should be used to sort the child items, as a value of the enum
     *         {@link Order}. The order may either be <code>ASCENDING</code> or
     *         <code>DESCENDING</code>
     */
    void sortChildren(int groupIndex, @NonNull Order order,
                      @Nullable Comparator<ChildType> comparator);

    /**
     * Sorts the child items of a specific group in an ascending order. If the underlying data of
     * the adapter's child items does not implement the interface {@link Comparable} a {@link
     * SortingNotSupportedException} will be thrown.
     *
     * @param group
     *         The group, whose child items should be sorted, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     */
    void sortChildren(@NonNull GroupType group);

    /**
     * Sorts child items of a specific group in a specific order. If the underlying data of the
     * adapter's child items does not implement the interface {@link Comparable} a {@link
     * SortingNotSupportedException} will be thrown.
     *
     * @param group
     *         The group, whose child items should be sorted, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param order
     *         The order, which should be used to sort the child items, as a value of the enum
     *         {@link Order}. The order may either be <code>ASCENDING</code> or
     *         <code>DESCENDING</code>
     */
    void sortChildren(@NonNull GroupType group, @NonNull Order order);

    /**
     * Sorts the child items of a specific group in an ascending order, by using a comparator.
     *
     * @param group
     *         The group, whose child items should be sorted, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param comparator
     *         The comparator, which should be used to sort the child items, as an instance of the
     *         type {@link Comparator} or null, if the natural order should be used
     */
    void sortChildren(@NonNull GroupType group, @Nullable Comparator<ChildType> comparator);

    /**
     * Sorts the child items of a specific group in a specific order, by using a comparator.
     *
     * @param group
     *         The group, whose child items should be sorted, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param comparator
     *         The comparator, which should be used to sort the child items, as an instance of the
     *         type {@link Comparator} or null, if the natural order should be used
     * @param order
     *         The order, which should be used to sort the child items, as a value of the enum
     *         {@link Order}. The order may either be <code>ASCENDING</code> or
     *         <code>DESCENDING</code>
     */
    void sortChildren(@NonNull GroupType group, @NonNull Order order,
                      @Nullable Comparator<ChildType> comparator);

    /**
     * Returns the current order of all child items, regardless of the group they belong to.
     *
     * @return The current order of the child items as a value of the enum {@link Order} or null, if
     * not all child items have been sorted yet or if the adapter's underlying data has been changed
     * since it has been sorted the last time. If not null, the order may either be
     * <code>ASCENDING</code> or <code>DESCENDING</code>
     */
    Order getChildOrder();

    /**
     * Returns the current order of the child items of a specific group.
     *
     * @param group
     *         The group, the children, whose order should be returned, belong to, as an instance of
     *         the generic type GroupType. The group may not be null. If the group does not belong
     *         to the adapter, a {@link NoSuchElementException} will be thrown
     * @return The current order of the child items, which belong to the given group, as a value of
     * the enum {@link Order} or null, if the child items have not been sorted yet or if the
     * adapter's underlying data has been changed since it has been sorted the last time. If not
     * null, the order may either be <code>ASCENDING</code> or <code>DESCENDING</code>
     */
    Order getChildOrder(@NonNull final GroupType group);

    /**
     * Returns the current order of the child items of the group, which belongs to a specific
     * index.
     *
     * @param groupIndex
     *         The index of the group, the children, whose order should be returned, belong to, as
     *         an {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @return The current order of the child items, which belong to the given group, as a value of
     * the enum {@link Order} or null, if the child items have not been sorted yet or if the
     * adapter's underlying data has been changed since it has been sorted the last time. If not
     * null, the order may either be <code>ASCENDING</code> or <code>DESCENDING</code>
     */
    Order getChildOrder(final int groupIndex);

    /**
     * Adds a new listener, which should be notified, when the adapter's underlying data has been
     * sorted.
     *
     * @param listener
     *         The listener, which should be added, as an instance of the class {@link
     *         ExpandableListSortingListener}. The listener may not be null
     */
    void addSortingListener(@NonNull ExpandableListSortingListener<GroupType, ChildType> listener);

    /**
     * Removes a specific listener, which should not be notified, when the adapter's underlying data
     * has been sorted, anymore.
     *
     * @param listener
     *         The listener, which should be removed, as an instance of the class {@link
     *         ExpandableListSortingListener}. The listener may not be null
     */
    void removeSortingListener(
            @NonNull ExpandableListSortingListener<GroupType, ChildType> listener);

}