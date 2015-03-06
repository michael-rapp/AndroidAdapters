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
 * Defines the interface, all listeners, which should be notified, when a group
 * item of an {@link ExpandableListAdapter} has been expanded or collapsed, must
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
public interface ExpansionListener<GroupType, ChildType> {

	/**
	 * The method, which is invoked, when a group item has been expanded.
	 * 
	 * @param adapter
	 *            The observed adapter as an instance of the type
	 *            {@link ExpandableListAdapter}. The adapter may not be null
	 * @param group
	 *            The group item, which has been expanded, as an instance of the
	 *            generic type GroupType. The group item may not be null
	 * @param index
	 *            The index of the group item, which has been expanded, as an
	 *            {@link Integer} value
	 */
	void onGroupExpanded(
			final ExpandableListAdapter<GroupType, ChildType> adapter,
			final GroupType group, final int index);

	/**
	 * The method, which is invoked, when a group item has been collapsed.
	 * 
	 * @param adapter
	 *            The observed adapter as an instance of the type
	 *            {@link ExpandableListAdapter}. The adapter may not be null
	 * @param group
	 *            The group item, which has been collapsed, as an instance of
	 *            the generic type GroupType. The group item may not be null
	 * @param index
	 *            The index of the group item, which has been collapsed, as an
	 *            {@link Integer} value
	 */
	void onGroupCollapsed(
			final ExpandableListAdapter<GroupType, ChildType> adapter,
			final GroupType group, final int index);

}