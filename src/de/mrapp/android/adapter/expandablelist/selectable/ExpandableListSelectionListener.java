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
package de.mrapp.android.adapter.expandablelist.selectable;

/**
 * Defines the interface, all listeners, which should be notified when the
 * selection of a group or child item of a {@link ExpandableListAdapter} has
 * been modified, must implement.
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
 * @since 0.1.0
 */
public interface ExpandableListSelectionListener<GroupType, ChildType> {

	/**
	 * The method, which is invoked, when a group item has been selected.
	 * 
	 * @param adapter
	 *            The observed adapter as an instance of the type
	 *            {@link SelectableExpandableListAdapter}. The adapter may not
	 *            be null
	 * @param group
	 *            The group item, which has been selected, as an instance of the
	 *            generic type GroupType. The group item may not be null
	 * @param index
	 *            The index of the group item, which has been selected, as an
	 *            {@link Integer} value
	 */
	void onGroupSelected(
			SelectableExpandableListAdapter<GroupType, ChildType> adapter,
			GroupType group, int index);

	/**
	 * The method, which is invoked, when a group item has been unselected.
	 * 
	 * @param adapter
	 *            The observed adapter as an instance of the type
	 *            {@link SelectableExpandableListAdapter}. The adapter may not
	 *            be null
	 * @param group
	 *            The group item, which has been unselected, as an instance of
	 *            the generic type GroupType. The group item may not be null
	 * @param index
	 *            The index of the group item, which has been unselected, as an
	 *            {@link Integer} value
	 */
	void onGroupUnselected(
			SelectableExpandableListAdapter<GroupType, ChildType> adapter,
			GroupType group, int index);

	/**
	 * The method, which is invoked, when a child item has been selected.
	 * 
	 * @param adapter
	 *            The observed adapter as an instance of the type
	 *            {@link SelectableExpandableListAdapter}. The adapter may not
	 *            be null
	 * @param child
	 *            The child item, which has been selected, as an instance of the
	 *            generic type ChildType. The child item may not be null
	 * @param childIndex
	 *            The index of the child item, which has been selected, as an
	 *            {@link Integer} value
	 * @param group
	 *            The group item, the child, which has been selected, belongs
	 *            to, as an instance of the generic type GroupType. The group
	 *            item may not be null
	 * @param groupIndex
	 *            The index of the group item, the child, which has been
	 *            selected, belongs to, as an {@link Integer} value
	 */
	void onChildSelected(
			SelectableExpandableListAdapter<GroupType, ChildType> adapter,
			ChildType child, int childIndex, GroupType group, int groupIndex);

	/**
	 * The method, which is invoked, when a child item has been unselected.
	 * 
	 * @param adapter
	 *            The observed adapter as an instance of the type
	 *            {@link SelectableExpandableListAdapter}. The adapter may not
	 *            be null
	 * @param child
	 *            The child item, which has been unselected, as an instance of
	 *            the generic type ChildType. The child item may not be null
	 * @param childIndex
	 *            The index of the child item, which has been unselected, as an
	 *            {@link Integer} value
	 * @param group
	 *            The group item, the child, which has been unselected, belongs
	 *            to, as an instance of the generic type GroupType. The group
	 *            item may not be null
	 * @param groupIndex
	 *            The index of the group item, the child, which has been
	 *            unselected, belongs to, as an {@link Integer} value
	 */
	void onChildUnselected(
			SelectableExpandableListAdapter<GroupType, ChildType> adapter,
			ChildType child, int childIndex, GroupType group, int groupIndex);

}