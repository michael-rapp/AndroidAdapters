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
package de.mrapp.android.adapter.list.selectable;

import de.mrapp.android.adapter.ListAdapter;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list
 * of arbitrary items, of which one or multiple items can be selected, must
 * implement. Such an adapter's purpose is to provide the underlying data for
 * visualization using a {@link AbsListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 0.1.0
 */
public interface SelectableListAdapter<DataType> extends ListAdapter<DataType> {

	/**
	 * Adds a new listener, which should be notified when the selection of an
	 * item has been changed.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            type {@link ListSelectionListener}. The listener may not be
	 *            null
	 */
	void addSelectionListener(ListSelectionListener<DataType> listener);

	/**
	 * Removes a specific listener, which should not be notified when the
	 * selection of an item has been changed, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            type {@link ListSelectionListener}. The listener may not be
	 *            null
	 */
	void removeSelectionListener(ListSelectionListener<DataType> listener);

	/**
	 * Returns, whether the item, which belongs to a specific index, is
	 * currently selected, or not.
	 * 
	 * @param index
	 *            The index of the item, whose selection state should be
	 *            returned, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the method
	 *            <code>getCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if the item, which belongs to the given index, is currently
	 *         selected, false otherwise
	 */
	boolean isSelected(int index);

	/**
	 * Returns, whether a specific item is currently selected, or not.
	 * 
	 * @param item
	 *            The item, whose selection state should be returned, as an
	 *            instance of the generic type DataType. The item may not be
	 *            null. If the item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return True, if the item, which belongs to the given index, is currently
	 *         selected, false otherwise
	 */
	boolean isSelected(DataType item);

	/**
	 * Returns, whether an item is selected, when it is clicked by the user, or
	 * not.
	 * 
	 * @return True, if an item is selected, when it is clicked by the user,
	 *         false otherwise
	 */
	boolean isItemSelectedOnClick();

	/**
	 * Sets, whether an item should be selected, when it is clicked by the user,
	 * or not.
	 * 
	 * @param selectItemOnClick
	 *            True, if an item should be selected, when it is clicked by the
	 *            user, false otherwise
	 */
	void selectItemOnClick(boolean selectItemOnClick);

}