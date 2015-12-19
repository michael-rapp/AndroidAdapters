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

import de.mrapp.android.adapter.expandablelist.selectable.SelectableExpandableListAdapter;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list
 * of arbitrary group and child items, of which only one single item can be
 * selected at once, must implement. Such an adapter's purpose is to provide the
 * underlying data for visualization using a {@link ExpandableListView} widget.
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
public interface SingleChoiceExpandableListAdapter<GroupType, ChildType>
		extends SelectableExpandableListAdapter<GroupType, ChildType> {

	/**
	 * Returns, whether a group item is currently selected, or not.
	 * 
	 * @return True, if a group item is currently selected, false otherwise
	 */
	boolean isGroupSelected();

	/**
	 * Returns, whether a child item is currently selected, or not.
	 * 
	 * @return True, if a child item is currently selected, false otherwise
	 */
	boolean isChildSelected();

	/**
	 * Returns the index of the currently selected group item or the index of
	 * the group item, the currently selected child belongs to.
	 * 
	 * @return The index of the currently selected group item as an
	 *         {@link Integer} value or -1, if no item is currently selected
	 */
	int getSelectedGroupIndex();

	/**
	 * Returns the index of the currently selected child item.
	 * 
	 * @return The index of the currently selected child item as an
	 *         {@link Integer} value or -1, if no child item is currently
	 *         selected
	 */
	int getSelectedChildIndex();

	/**
	 * Returns the currently selected group item.
	 * 
	 * @return The currently selected group item as an instance of the generic
	 *         type GroupType or null, if no group item is currently selected
	 */
	GroupType getSelectedGroup();

	/**
	 * Returns the currently selected child item.
	 * 
	 * @return The currently selected child item as an instance of the generic
	 *         type ChildType or null, if no child item is currently selected
	 */
	ChildType getSelectedChild();

	/**
	 * Selects the group item, which belongs to a specific index, if it is
	 * currently enabled. This causes any other selected item to become
	 * unselected. If the adapter's choice mode does not allow group items to be
	 * selected, an {@link IllegalStateException} is thrown.
	 * 
	 * @param groupIndex
	 *            The index of the group item, which should be selected, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getGroupCount():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if the selection of the group item, which belongs to the
	 *         given index, has been changed, false otherwise
	 */
	boolean selectGroup(int groupIndex);

	/**
	 * Selects a specific group item, if it is currently enabled. This causes
	 * any other selected item to become unselected. If the adapter's choice
	 * mode does not allow group items to be selected, an
	 * {@link IllegalStateException} is thrown.
	 * 
	 * @param group
	 *            The group item, which should be selected, as an instance of
	 *            the generic type DataType. The group item may not be null. If
	 *            the group item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return True, if the selection of the group item has been changed, false
	 *         otherwise
	 */
	boolean selectGroup(GroupType group);

	/**
	 * Selects the child item, which belongs to a specific index of a specific
	 * group if it is currently enabled. This causes any other selected item to
	 * become unselected. If the adapter's choice mode does not allow child
	 * items to be selected, an {@link IllegalStateException} is thrown.
	 * 
	 * @param group
	 *            The group, the child item, which should be selected, belongs
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @param childIndex
	 *            The index of the child item, which should be selected, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getChildCount(group):int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if the selection of the child item has changed, false
	 *         otherwise
	 */
	boolean selectChild(GroupType group, int childIndex);

	/**
	 * Selects a specific child item of a specific group, if it is currently
	 * enabled. This causes any other selected item to become unselected. If the
	 * adapter's choice mode does not allow child items to be selected, an
	 * {@link IllegalStateException} is thrown.
	 * 
	 * @param group
	 *            The group, the child item, which should be selected, belongs
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @param child
	 *            The child item, which should be selected, as an instance of
	 *            the generic type ChildType. The child item may not be null. If
	 *            the child item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return True if the selection of the child item has changed, false
	 *         otherwise
	 */
	boolean selectChild(GroupType group, ChildType child);

	/**
	 * Selects the child item, which belongs to a specific index of a specific
	 * group. This causes any other selected item to become unselected. If the
	 * adapter's choice mode does not allow child items to be selected, an
	 * {@link IllegalStateException} is thrown.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, which should be
	 *            selected, belongs to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param childIndex
	 *            The index of the child item, which should be selected, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getChildCount(groupIndex):int</code>
	 *            - 1, otherwise an {@link IndexOutOfBoundsException} will be
	 *            thrown
	 * @return True, if the selection of the child item has changed, false
	 *         otherwise
	 */
	boolean selectChild(int groupIndex, int childIndex);

	/**
	 * Selects a specific child item of a specific group. This causes any other
	 * selected item to become unselected. If the adapter's choice mode does not
	 * allow child items to be selected, an {@link IllegalStateException} is
	 * thrown.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, which should be
	 *            selected, belongs to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param child
	 *            The child item, which should be selected, as an instance of
	 *            the generic type ChildType. The child item may not be null. If
	 *            the child item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return True, if the selection of the child item has changed, false
	 *         otherwise
	 */
	boolean selectChild(int groupIndex, ChildType child);

	/**
	 * Sets, whether the adapter's selection should be automatically adapted in
	 * order to ensure that an item is always selected if possible, or not. For
	 * example this causes the selection to be adapted, when the currently
	 * selected item has been removed from the adapter.
	 * 
	 * @param adaptSelectionAutomatically
	 *            True, if the adapter's selection should be automatically
	 *            adapted, false otherwise
	 */
	void adaptSelectionAutomatically(boolean adaptSelectionAutomatically);

	/**
	 * Returns, whether the adapter's selection is automatically adapted in
	 * order to ensure that an item is always selected if possible, or not.
	 * 
	 * @return True, if the adapter's selection is automatically adapted, false
	 *         otherwise
	 */
	boolean isSelectionAdaptedAutomatically();

	@Override
	SingleChoiceExpandableListAdapter<GroupType, ChildType> clone() throws CloneNotSupportedException;

}