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

import de.mrapp.android.adapter.ExpandableListAdapter;
import de.mrapp.android.adapter.ExpandableListChoiceMode;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list
 * of arbitrary group and child items of which one or multiple items can be
 * selected, must implement. Such an adapter's purpose is to provide the
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
public interface SelectableExpandableListAdapter<GroupType, ChildType>
		extends ExpandableListAdapter<GroupType, ChildType> {

	/**
	 * Adds a new listener, which should be notified when the selection of an
	 * item has been changed.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            type {@link ExpandableListSelectionListener}. The listener may
	 *            not be null
	 */
	void addSelectionListener(ExpandableListSelectionListener<GroupType, ChildType> listener);

	/**
	 * Removes a specific listener, which should not be notified when the
	 * selection of an item has been changed, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            type {@link ExpandableListSelectionListener}. The listener may
	 *            not be null
	 */
	void removeSelectionListener(ExpandableListSelectionListener<GroupType, ChildType> listener);

	/**
	 * Returns, whether the group item, which belongs to a specific index, is
	 * currently selected, or not.
	 * 
	 * @param groupIndex
	 *            The index of the group item, whose selection state should be
	 *            returned, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if the group item, which belongs to the given index, is
	 *         currently selected, false otherwise
	 */
	boolean isGroupSelected(int groupIndex);

	/**
	 * Returns, whether a specific group item is currently selected, or not.
	 * 
	 * @param groupItem
	 *            The group item, whose selection state should be returned, as
	 *            an instance of the generic type GroupType. The item may not be
	 *            null. If the item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return True, if the given group item is currently selected, false
	 *         otherwise
	 */
	boolean isGroupSelected(GroupType groupItem);

	/**
	 * Returns the number of currently selected group items.
	 * 
	 * @return The number of currently selected group items, as an
	 *         {@link Integer} value
	 */
	int getSelectedGroupCount();

	/**
	 * Returns, whether the child item, which belongs to a specific index of a
	 * specific group, is currently selected, or not.
	 * 
	 * @param groupItem
	 *            The group, the child item, whose selection state should be
	 *            returned, belongs to, as an instance of the generic type
	 *            GroupType. The item may not be null. If the item does not
	 *            belong to the adapter, a {@link NoSuchElementException} will
	 *            be thrown
	 * @param childIndex
	 *            The index of the child item, whose selection state should be
	 *            returned, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the method
	 *            <code>getChildCount(group):int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if the given group item is currently selected, false
	 *         otherwise
	 */
	boolean isChildSelected(GroupType groupItem, int childIndex);

	/**
	 * Returns, whether a specific child item, which belongs to a specific
	 * group, is currently selected, or not.
	 * 
	 * @param groupItem
	 *            The group, the child item, whose selection state should be
	 *            returned, belongs to, as an instance of the generic type
	 *            GroupType. The item may not be null. If the item does not
	 *            belong to the adapter, a {@link NoSuchElementException} will
	 *            be thrown
	 * @param childItem
	 *            The child item, whose selection state should be returned, as
	 *            an instance of the generic type ChildType. The item may not be
	 *            null. If the item does not belong to the adapter, a
	 *            {@link java.util.NoSuchElementException} will be thrown
	 * @return True, if the given group item is currently selected, false
	 *         otherwise
	 */
	boolean isChildSelected(GroupType groupItem, ChildType childItem);

	/**
	 * Returns, whether the child item, which belongs to a specific index of a
	 * specific group, is currently selected, or not.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose selection state
	 *            should be returned, belongs to, as an {@link Integer} value.
	 *            The value must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param childIndex
	 *            The index of the child item, whose selection state should be
	 *            returned, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the method
	 *            <code>getChildCount(group):int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if the given group item is currently selected, false
	 *         otherwise
	 */
	boolean isChildSelected(int groupIndex, int childIndex);

	/**
	 * Returns, whether a specific child item, which belongs to a specific
	 * group, is currently selected, or not.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, whose selection state
	 *            should be returned, belongs to, as an {@link Integer} value.
	 *            The value must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param childItem
	 *            The child item, whose selection state should be returned, as
	 *            an instance of the generic type ChildType. The item may not be
	 *            null. If the item does not belong to the adapter, a
	 *            {@link java.util.NoSuchElementException} will be thrown
	 * @return True, if the given group item is currently selected, false
	 *         otherwise
	 */
	boolean isChildSelected(int groupIndex, ChildType childItem);

	/**
	 * Returns the number of currently selected child items.
	 * 
	 * @return The number of currently selected child items, as an
	 *         {@link Integer} value
	 */
	int getSelectedChildCount();

	/**
	 * Returns, whether a group item is selected, when it is clicked by the
	 * user, or not.
	 * 
	 * @return True, if a group item is selected, when it is clicked by the
	 *         user, false otherwise
	 */
	boolean isGroupSelectedOnClick();

	/**
	 * Sets, whether a group item should be selected, when it is clicked by the
	 * user, or not.
	 * 
	 * @param selectItemOnClick
	 *            True, if a group item should be selected, when it is clicked
	 *            by the user, false otherwise
	 */
	void selectGroupOnClick(boolean selectItemOnClick);

	/**
	 * Returns, whether a child item is selected, when it is clicked by the
	 * user, or not.
	 * 
	 * @return True, if a child item is selected, when it is clicked by the
	 *         user, false otherwise
	 */
	boolean isChildSelectedOnClick();

	/**
	 * Sets, whether a child item should be selected, when it is clicked by the
	 * user, or not.
	 * 
	 * @param selectItemOnClick
	 *            True, if a child item should be selected, when it is clicked
	 *            by the user, false otherwise
	 */
	void selectChildOnClick(boolean selectItemOnClick);

	/**
	 * Returns the choice mode of the adapter.
	 * 
	 * @return The choice mode of the adapter as a value of the enum
	 *         {@link ExpandableListChoiceMode}. The choice mode may either be
	 *         <code>GROUPS_ONLY</code>, <code>CHILDREN_ONLY</code> or
	 *         <code>GROUPS_AND_CHILDREN</code>
	 */
	ExpandableListChoiceMode getChoiceMode();

}