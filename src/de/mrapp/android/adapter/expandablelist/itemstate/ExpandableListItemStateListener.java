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

/**
 * Defines the interface, all listeners, which should be notified, when the
 * state of a group or child item of a {@link ListAdapter} has been changed,
 * must implement.
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
public interface ExpandableListItemStateListener<GroupType, ChildType> {

	/**
	 * The method, which is invoked, when the state of a group item has been
	 * changed.
	 * 
	 * @param group
	 *            The group item, whose state has been changed, as an instance
	 *            of the generic type GroupType. The group item may not be null
	 * @param index
	 *            The index of the group item, whose state has been changed, as
	 *            an {@link Integer} value
	 * @param state
	 *            The new state of the group item, whose state has been changed,
	 *            as an {@link Integer} value
	 */
	void onGroupStateChanged(GroupType group, int index, int state);

	/**
	 * The method, which is invoked, when the state of a child item has been
	 * changed.
	 * 
	 * @param child
	 *            The child item, whose state has been changed, as an instance
	 *            of the generic type ChildType. The child item may not be null
	 * @param index
	 *            The index of the child item, whose state has been changed, as
	 *            an {@link Integer} value
	 * @param state
	 *            The new state of the child item, whose state has been changed,
	 *            as an {@link Integer} value
	 */
	void onChildStateChanged(ChildType child, int index, int state);

}