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
package de.mrapp.android.adapter.expandablelist;

import de.mrapp.android.adapter.ExpandableListAdapter;

/**
 * Defines the interface, all listeners, which should be notified, when the
 * underlying data of an {@link ExpandableListAdapter} has been modified, must
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
public interface ExpandableListAdapterListener<GroupType, ChildType> {

	/**
	 * The method, which is invoked, when a group item has been added to the
	 * adapter.
	 * 
	 * @param adapter
	 *            The observed adapter as an instance of the type
	 *            {@link ExpandableListAdapter}. The adapter may not be null
	 * @param group
	 *            The group item, which has been added, as an instance of the
	 *            generic type GroupType. The group item may not be null
	 * @param index
	 *            The index of the group item, which has been added, as an
	 *            {@link Integer} value
	 */
	void onGroupAdded(
			ExpandableListAdapterListener<GroupType, ChildType> adapter,
			GroupType group, int index);

	/**
	 * The method, which is invoked, when a group item has been removed from the
	 * adapter.
	 * 
	 * @param adapter
	 *            The observed adapter as an instance of the type
	 *            {@link ExpandableListAdapter}. The adapter may not be null
	 * @param group
	 *            The group item, which has been removed, as an instance of the
	 *            generic type GroupType. The group item may not be null
	 * @param index
	 *            The index of the group item, which has been removed, as an
	 *            {@link Integer} value
	 */
	void onGroupRemoved(ExpandableListAdapter<GroupType, ChildType> adapter,
			GroupType group, int index);

	/**
	 * The method, which is invoked, when a child item has been added to a group
	 * of the adapter.
	 * 
	 * @param adapter
	 *            The observed adapter as an instance of the type
	 *            {@link ExpandableListAdapter}. The adapter may not be null
	 * @param child
	 *            The child item, which has been added, as an instance of the
	 *            generic type ChildType. The child item may not be null
	 * @param childIndex
	 *            The index of the child item, which has been added, as an
	 *            {@link Integer} value
	 * @param group
	 *            The group item, the child has been added to, as an instance of
	 *            the generic type GroupType. The group item may not be null
	 * @param groupIndex
	 *            The index of the group item, the child has been added to, as
	 *            an {@link Integer} value
	 */
	void onChildAdded(ExpandableListAdapter<GroupType, ChildType> adapter,
			ChildType child, int childIndex, GroupType group, int groupIndex);

	/**
	 * The method, which is invoked, when a child item has been removed from a
	 * group of the adapter.
	 * 
	 * @param adapter
	 *            The observed adapter as an instance of the type
	 *            {@link ExpandableListAdapter}. The adapter may not be null
	 * @param child
	 *            The child item, which has been removed, as an instance of the
	 *            generic type ChildType. The child item may not be null
	 * @param childIndex
	 *            The index of the child item, which has been removed, as an
	 *            {@link Integer} value
	 * @param group
	 *            The group item, the child has been removed from, as an
	 *            instance of the generic type GroupType. The group item may not
	 *            be null
	 * @param groupIndex
	 *            The index of the group item, the child has been removed from,
	 *            as an {@link Integer} value
	 */
	void onChildRemoved(ExpandableListAdapter<GroupType, ChildType> adapter,
			ChildType child, int childIndex, GroupType group, int groupIndex);

}