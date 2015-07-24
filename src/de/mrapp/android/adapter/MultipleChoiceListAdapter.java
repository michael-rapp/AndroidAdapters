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
import java.util.NoSuchElementException;

import android.widget.AbsListView;
import de.mrapp.android.adapter.list.selectable.SelectableListAdapter;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list
 * of arbitrary items, of which multiple items can be selected at once, must
 * implement. Such an adapter's purpose is to provide the underlying data for
 * visualization using a {@link AbsListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface MultipleChoiceListAdapter<DataType> extends SelectableListAdapter<DataType> {

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
	 * Selects the item, which belongs to a specific index, if it is currently
	 * enabled.
	 * 
	 * @param index
	 *            The index of the item, which should be selected, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if the selection of the item, which belongs to the given
	 *         index, has been changed, false otherwise
	 */
	@Override
	boolean select(int index);

	/**
	 * Selects a specific item, if it is currently enabled.
	 * 
	 * @param item
	 *            The item, which should be selected, as an instance of the
	 *            generic type DataType. The item may not be null. If the item
	 *            does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return True, if the selection of the given item has been changed, false
	 *         otherwise
	 */
	@Override
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
	 * @return True, if the selection of the item, which belongs to the given
	 *         index, has been changed, false otherwise
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
	 * @return True, if the selection of the given item has been changed, false
	 *         otherwise
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
	 *         index, has been changed, false otherwise
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
	 * @return True, if the selection of the given item has been changed, false
	 *         otherwise
	 */
	boolean triggerSelection(DataType item);

	/**
	 * Selects all items, if they are currently enabled.
	 * 
	 * @return True, if the selections of all items have been changed, false
	 *         otherwise
	 */
	boolean selectAll();

	/**
	 * Unselects all items, if they are currently enabled.
	 * 
	 * @return True, if the selections of all items have been changed, false
	 *         otherwise
	 */
	boolean unselectAll();

	/**
	 * Triggers the selections of all items, if they are currently enabled. This
	 * causes an item to become unselected, if it is currently selected and vice
	 * versa.
	 * 
	 * @return True, if the selections of all items have been changed, false
	 *         otherwise
	 */
	boolean triggerAllSelections();

	@Override
	MultipleChoiceListAdapter<DataType> clone() throws CloneNotSupportedException;

}