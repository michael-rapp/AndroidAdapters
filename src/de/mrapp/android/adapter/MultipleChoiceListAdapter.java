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

import java.util.Collection;

import de.mrapp.android.adapter.list.selectable.SelectableListAdapter;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list
 * of arbitrary items, of which multiple items can be selected as once, must
 * implement. Such an adapter's purpose is to provide the underlying data for
 * visualization using a {@link ListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface MultipleChoiceListAdapter<DataType> extends
		ListAdapter<DataType>, SelectableListAdapter<DataType> {

	/**
	 * Returns, whether the item, which belongs to a specific index, is
	 * currently unselected, or not.
	 * 
	 * @param index
	 *            The index of the item, whose selection state should be
	 *            returned, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the method
	 *            <code>getNumberOfItems():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if the item, which belongs to the given index, is currently
	 *         unselected, false otherwise
	 */
	boolean isUnselected(int index);

	/**
	 * Returns, whether a specific item is currently unselected, or not.
	 * 
	 * @param item
	 *            The item, whose selection state should be returned, as an
	 *            instance of the generic type DataType. The item may not be
	 *            null. If the item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return True, if the given item is currently unselected, false otherwise
	 */
	boolean isUnselected(DataType item);

	/**
	 * Returns the index of the first selected item.
	 * 
	 * @return The index of the first selected item or -1, if no item is
	 *         currently selected
	 */
	int getFirstSelectedIndex();

	/**
	 * Returns the first selected item.
	 * 
	 * @return The first selected item, as an instance of the generic type
	 *         DataType or null, if no item is currently selected
	 */
	DataType getFirstSelectedItem();

	/**
	 * Returns the index of the last selected item.
	 * 
	 * @return The index of the last selected item or -1, if no item is
	 *         currently selected
	 */
	int getLastSelectedIndex();

	/**
	 * Returns the last selected item.
	 * 
	 * @return The last selected item, as an instance of the generic type
	 *         DataType or null, if no item is currently selected
	 */
	DataType getLastSelectedItem();

	/**
	 * Returns the index of the first unselected item.
	 * 
	 * @return The index of the first unselected item or -1, if no item is
	 *         currently unselected
	 */
	int getFirstUnselectedIndex();

	/**
	 * Returns the first unselected item.
	 * 
	 * @return The first unselected item, as an instance of the generic type
	 *         DataType or null, if no item is currently unselected
	 */
	DataType getFirstUnselectedItem();

	/**
	 * Return the index of the last unselected item.
	 * 
	 * @return The index of the last unselected item or -1, if no item is
	 *         currently unselected
	 */
	int getLastUnselectedIndex();

	/**
	 * Returns the last unselected item.
	 * 
	 * @return The last unselected item, as an instance of the generic type
	 *         DataType or null, if no item is currently unselected
	 */
	DataType getLastUnselectedItem();

	/**
	 * Returns a collection, which contains the indices of all currently
	 * selected items.
	 * 
	 * @return A collection, which contains the indices of all currently
	 *         selected items, as an instance of the type {@link Collection} or
	 *         an empty collection, if no item is currently selected
	 */
	Collection<Integer> getSelectedIndices();

	/**
	 * Returns a collection, which contains all currently selected items.
	 * 
	 * @return A collection, which contains all currently selected items, as an
	 *         instance of the type {@link Collection} or an empty collection,
	 *         if no item is currently selected
	 */
	Collection<DataType> getSelectedItems();

	/**
	 * Returns a collection, which contains the indices of all currently
	 * unselected items.
	 * 
	 * @return A list, which contains the indices of all currently unselected
	 *         items, as an instance of the type {@link Collection} or an empty
	 *         collection, if no item is currently selected
	 */
	Collection<Integer> getUnselectedIndices();

	/**
	 * Returns a collection, which contains all currently unselected items.
	 * 
	 * @return A list, which contains all currently unselected items, as an
	 *         instance of the type {@link Collection} or an empty list, if no
	 *         item is currently selected
	 */
	Collection<DataType> getUnselectedItems();

	/**
	 * Returns the number of currently selected items.
	 * 
	 * @return The number of currently selected items, as an {@link Integer}
	 *         value
	 */
	int getNumberOfSelectedItems();

	/**
	 * Returns the number currently unselected items.
	 * 
	 * @return The number of currently unselected items, as an {@link Integer}
	 *         value
	 */
	int getNumberOfUnselectedItems();

	/**
	 * Selects the item, which belongs to a specific index, if it is currently
	 * enabled.
	 * 
	 * @param index
	 *            The index of the item, which should be selected, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if the item, which belongs to the given index, has been
	 *         selected, false otherwise
	 */
	boolean select(int index);

	/**
	 * Selects a specific item, if it is currently enabled.
	 * 
	 * @param item
	 *            The item, which should be selected, as an instance of the
	 *            generic type DataType. The item may not be null. If the item
	 *            does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return True, if the given item has been selected, false otherwise
	 */
	boolean select(DataType item);

	/**
	 * Unselects the item, which belongs to a specific index, if it is currently
	 * enabled.
	 * 
	 * @param index
	 *            The index of the item, which should be unselected, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if the item, which belongs to the given index, has been
	 *         unselected, false otherwise
	 */
	boolean unselect(int index);

	/**
	 * Unselects a specific item, if it is currently enabled.
	 * 
	 * @param item
	 *            The item, which should be unselected, as an instance of the
	 *            generic type DataType. The item may not be null. If the item
	 *            does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return True, if the given item has been unselected, false otherwise
	 */
	boolean unselect(DataType item);

	/**
	 * Triggers the selection of the item, which belongs to a specific index, it
	 * is currently enabled. This causes the item to become unselected, if it is
	 * currently selected and vice versa.
	 * 
	 * @param index
	 *            The index of the item, whose selection should be triggered, as
	 *            an {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if the selection of the item, which belongs to the given
	 *         index, has been triggered, false otherwise
	 */
	boolean triggerSelection(int index);

	/**
	 * Triggers the selection of a specific item, it it is currently enabled.
	 * This causes the item to become unselected, if it is currently selected
	 * and vice versa.
	 * 
	 * @param item
	 *            The item, whose selection should be triggered, as an instance
	 *            of the generic type DataType. The item may not be null. If the
	 *            item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return True, if the selection of the given item has been triggered,
	 *         false otherwise
	 */
	boolean triggerSelection(DataType item);

	/**
	 * Selects all items, if they are currently enabled.
	 * 
	 * @return True, if all items have been selected, false otherwise
	 */
	boolean selectAll();

	/**
	 * Unselects all items, if they are currently enabled.
	 * 
	 * @return True, if all items have been unselected, false otherwise
	 */
	boolean unselectAll();

	/**
	 * Triggers the selections of all items, if they are currently enabled. This
	 * causes an item to become unselected, if it is currently selected and vice
	 * versa.
	 * 
	 * @return True, if the selections of all items have been triggered, false
	 *         otherwise
	 */
	boolean triggerAllSelections();

}