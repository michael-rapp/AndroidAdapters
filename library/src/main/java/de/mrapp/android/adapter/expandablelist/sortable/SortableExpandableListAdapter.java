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
package de.mrapp.android.adapter.expandablelist.sortable;

import android.support.annotation.NonNull;
import android.widget.ExpandableListView;

import java.util.Comparator;
import java.util.NoSuchElementException;

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
     *         type {@link Comparator}. The comparator may not be null
     */
    void sortGroups(@NonNull Comparator<GroupType> comparator);

    /**
     * Sorts the adapter's group items in a specific order, by using a comparator.
     *
     * @param comparator
     *         The comparator, which should be used to sort the group items, as an instance of the
     *         type {@link Comparator}. The comparator may not be null
     * @param order
     *         The order, which should be used to sort the group items, as a value of the enum
     *         {@link Order}. The order may either be <code>ASCENDING</code> or
     *         <code>DESCENDING</code>
     */
    void sortGroups(@NonNull Order order, @NonNull Comparator<GroupType> comparator);

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
     *         type {@link Comparator}. The comparator may not be null
     */
    void sortChildren(@NonNull Comparator<ChildType> comparator);

    /**
     * Sorts all of the adapter's child items in a specific order, by using a comparator.
     *
     * @param comparator
     *         The comparator, which should be used to sort the child items, as an instance of the
     *         type {@link Comparator}. The comparator may not be null
     * @param order
     *         The order, which should be used to sort the child items, as a value of the enum
     *         {@link Order}. The order may either be <code>ASCENDING</code> or
     *         <code>DESCENDING</code>
     */
    void sortChildren(@NonNull Order order, @NonNull Comparator<ChildType> comparator);

    /**
     * Sorts the child items of the group, which belongs to a specific index, in an ascending order.
     * If the underlying data of the adapter's child items does not implement the interface {@link
     * Comparable} a {@link SortingNotSupportedException} will be thrown.
     *
     * @param groupIndex
     *         The index of the group, whose child items should be sorted, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
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
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
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
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @param comparator
     *         The comparator, which should be used to sort the child items, as an instance of the
     *         type {@link Comparator}. The comparator may not be null
     */
    void sortChildren(int groupIndex, @NonNull Comparator<ChildType> comparator);

    /**
     * Sorts the child items of the group, which belongs to a specific index, in a specific order,
     * by using a comparator.
     *
     * @param groupIndex
     *         The index of the group, whose child items should be sorted, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @param comparator
     *         The comparator, which should be used to sort the child items, as an instance of the
     *         type {@link Comparator}. The comparator may not be null
     * @param order
     *         The order, which should be used to sort the child items, as a value of the enum
     *         {@link Order}. The order may either be <code>ASCENDING</code> or
     *         <code>DESCENDING</code>
     */
    void sortChildren(int groupIndex, @NonNull Order order,
                      @NonNull Comparator<ChildType> comparator);

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
     *         type {@link Comparator}. The comparator may not be null
     */
    void sortChildren(@NonNull GroupType group, @NonNull Comparator<ChildType> comparator);

    /**
     * Sorts the child items of a specific group in a specific order, by using a comparator.
     *
     * @param group
     *         The group, whose child items should be sorted, as an instance of the generic type
     *         GroupType. The group may not be null. If the group does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @param comparator
     *         The comparator, which should be used to sort the child items, as an instance of the
     *         type {@link Comparator}. The comparator may not be null
     * @param order
     *         The order, which should be used to sort the child items, as a value of the enum
     *         {@link Order}. The order may either be <code>ASCENDING</code> or
     *         <code>DESCENDING</code>
     */
    void sortChildren(@NonNull GroupType group, @NonNull Order order,
                      @NonNull Comparator<ChildType> comparator);

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
     *         <code>getGroupCount():int</code>, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
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