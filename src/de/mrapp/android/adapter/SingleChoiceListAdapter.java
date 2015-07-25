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

import de.mrapp.android.adapter.list.selectable.SelectableListAdapter;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list
 * of arbitrary items, of which only one single item can be selected at once,
 * must implement. Such an adapter's purpose is to provide the underlying data
 * for visualization using a {@link AbsListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface SingleChoiceListAdapter<DataType> extends SelectableListAdapter<DataType> {

	/**
	 * Returns the index of the currently selected item.
	 * 
	 * @return The index of the currently selected item, as an {@link Integer}
	 *         value or -1, if the adapter does not contain any enabled items
	 */
	int getSelectedIndex();

	/**
	 * Returns the currently selected item.
	 * 
	 * @return The currently selected item, as an instance of the generic type
	 *         DataType or null, if the adapter does not contain any enabled
	 *         items
	 */
	DataType getSelectedItem();

	/**
	 * Selects the item, which belongs to a specific index, if it is currently
	 * enabled. This causes any other selected item to become unselected.
	 * 
	 * @param index
	 *            The index of the item, which should be selected, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if the selection of the item, which belongs to the given
	 *         index, has been changed, false otherwise
	 */
	boolean select(int index);

	/**
	 * Selects a specific item, if it is currently enabled. This causes any
	 * other selected item to become unselected.
	 * 
	 * @param item
	 *            The item, which should be selected, as an instance of the
	 *            generic type DataType. The item may not be null. If the item
	 *            does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return True, if the selection of the given item has been changed, false
	 *         otherwise
	 */
	boolean select(DataType item);

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
	SingleChoiceListAdapter<DataType> clone() throws CloneNotSupportedException;

}