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
package de.mrapp.android.adapter.expandablelist.sortable;

import java.util.Collection;
import java.util.Comparator;

import android.renderscript.Element.DataType;
import de.mrapp.android.adapter.ExpandableListAdapter;
import de.mrapp.android.adapter.Order;

/**
 * Defines the interface, all listeners, which should be notified when the
 * underlying data of an {@link ExpandableListAdapter} have been sorted, must
 * implement.
 * 
 * @param <GroupType>
 *            The type of the underlying data of the observed adapter's group
 *            items
 * @param <ChildType>
 *            The type of the underlying data of the observed adapter's child
 *            items
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface ExpandableListSortingListener<GroupType, ChildType> {

	/**
	 * The method, which is invoked, when the adapter's group items have been
	 * sorted.
	 * 
	 * @param adapter
	 *            The observed adapter as an instance of the type
	 *            {@link ExpandableListAdapter}. The adapter may not be null
	 * @param sortedGroups
	 *            A collection, which contains the adapter's sorted group items,
	 *            as an instance of the type {@link Collection} or an empty
	 *            collection, if the adapter does not contain any group items
	 * @param order
	 *            The order, which has been used to sort the adapter's group
	 *            items, as a value of the enum {@link Order}. The order may
	 *            either be <code>ASCENDING</code> or <code>DESCENDING</code>
	 * @param comparator
	 *            The comparator, which has been used to compare the single
	 *            group items, as an instance of the type {@link Comparator} or
	 *            null, if the group items' implementation of the type
	 *            {@link Comparable} has been used instead
	 */
	void onGroupsSorted(ExpandableListAdapter<GroupType, ChildType> adapter,
			Collection<DataType> sortedGroups, Order order,
			Comparator<DataType> comparator);

	/**
	 * The method, which is invoked, when the child items of a group of the
	 * adapter have been sorted.
	 * 
	 * @param adapter
	 *            The observed adapter as an instance of the type
	 *            {@link ExpandableListAdapter}. The adapter may not be null
	 * @param sortedChildren
	 *            A collection, which contains the group's sorted child items,
	 *            as an instance of the type {@link Collection} or an empty
	 *            collection, if the group does not contain any child items
	 * @param order
	 *            The order, which has been used to sort the group's child
	 *            items, as a value of the enum {@link Order}. The order may
	 *            either be <code>ASCENDING</code> or <code>DESCENDING</code>
	 * @param comparator
	 *            The comparator, which has been used to compare the single
	 *            child items, as an instance of the type {@link Comparator} or
	 *            null, if the child items' implementation of the type
	 *            {@link Comparable} has been used instead
	 * @param group
	 *            The group, whose child items have been sorted, as an instance
	 *            of the generic type GroupType. The group may not be null
	 * @param groupIndex
	 *            The index of the group, whose child items have been sorted, as
	 *            an {@link Integer} value
	 */
	void onChildrenSorted(ExpandableListAdapter<GroupType, ChildType> adapter,
			Collection<DataType> sortedChildren, Order order,
			Comparator<DataType> comparator, GroupType group, int groupIndex);

}