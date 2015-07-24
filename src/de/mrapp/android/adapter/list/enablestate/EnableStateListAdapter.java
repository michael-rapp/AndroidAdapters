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
package de.mrapp.android.adapter.list.enablestate;

import java.util.Collection;

import android.widget.AbsListView;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list
 * of arbitrary items, which may be enabled or disabled, must implement. Such an
 * adapter's purpose is to provide the underlying data for visualization using a
 * {@link AbsListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface EnableStateListAdapter<DataType> {

	/**
	 * Returns, whether the item, which belongs to a specific index, is
	 * currently enabled, or not.
	 * 
	 * @param index
	 *            The index of the item, whose enable state should be checked,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getNumberOfItems():int</code> -
	 *            1, otherwise an {@link IndexOutOfBoundsException} will be
	 *            thrown
	 * @return True, if the item, which belongs to the given index, is currently
	 *         enabled, false otherwise
	 */
	boolean isEnabled(int index);

	/**
	 * Returns, whether a specific item is currently enabled, or not.
	 * 
	 * @param item
	 *            The item, whose enable state should be checked, as an instance
	 *            of the generic type DataType. The item may not be null. If the
	 *            item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return True, if the given item is currently enabled, false otherwise
	 */
	boolean isEnabled(DataType item);

	/**
	 * Returns the index of the first enabled item.
	 * 
	 * @return The index of the first enabled item, as an {@link Integer} value
	 *         or -1, if no item is currently enabled
	 */
	int getFirstEnabledIndex();

	/**
	 * Returns the first enabled item.
	 * 
	 * @return The first enabled item, as an instance of the generic type
	 *         DataType or null, if no item is currently enabled
	 */
	DataType getFirstEnabledItem();

	/**
	 * Returns the index of the last enabled item.
	 * 
	 * @return The index of the last enabled item, as an {@link Integer} value
	 *         or -1, if no item is currently enabled
	 */
	int getLastEnabledIndex();

	/**
	 * Returns the last enabled item.
	 * 
	 * @return The last enabled item, as an instance of the generic type
	 *         DataType or null, if no item is currently enabled
	 */
	DataType getLastEnabledItem();

	/**
	 * Returns the index of the first disabled item.
	 * 
	 * @return The index of the first disabled item, as an {@link Integer} value
	 *         or -1, if no item is currently disabled
	 */
	int getFirstDisabledIndex();

	/**
	 * Returns the first disabled item.
	 * 
	 * @return The first disabled item, as an instance of the generic type
	 *         DataType or null, if no item is currently disabled
	 */
	DataType getFirstDisabledItem();

	/**
	 * Returns the index of the last disabled item.
	 * 
	 * @return The index of the last disabled item, as an {@link Integer} value
	 *         or -1, if no item is currently disabled
	 */
	int getLastDisabledIndex();

	/**
	 * Returns the index of the last disabled item.
	 * 
	 * @return The index of the last disabled item, as an {@link Integer} value
	 *         or -1, if no item is currently disabled
	 */
	DataType getLastDisabledItem();

	/**
	 * Returns a collection, which contains the indices of all currently enabled
	 * items.
	 * 
	 * @return A collection, which contains the indices of all currently enabled
	 *         items, as an instance of the type {@link Collection} or an empty
	 *         collection, if no item is currently enabled
	 */
	Collection<Integer> getEnabledIndices();

	/**
	 * Returns a collection, which contains all currently enabled items.
	 * 
	 * @return A collection, which contains all currently enabled items, as an
	 *         instance of the type {@link Collection} or an empty collection,
	 *         if no item is currently enabled
	 */
	Collection<DataType> getEnabledItems();

	/**
	 * Returns a collection, which contains the indices of all currently
	 * disabled items.
	 * 
	 * @return A collection, which contains the indices of all currently
	 *         disabled items, as an instance of the type {@link Collection} or
	 *         an empty collection, if no item is currently disabled
	 */
	Collection<Integer> getDisabledIndices();

	/**
	 * Returns a collection, which contains all currently disabled items.
	 * 
	 * @return A collection, which contains all currently disabled items, as an
	 *         instance of the type {@link Collection} or an empty collection,
	 *         if no item is currently disabled
	 */
	Collection<DataType> getDisabledItems();

	/**
	 * Returns the number of currently enabled items.
	 * 
	 * @return The number of currently enabled items, as an {@link Integer}
	 *         value
	 */
	int getNumberOfEnabledItems();

	/**
	 * Returns the number of currently disabled items.
	 * 
	 * @return The number of currently disabled items, as an {@link Integer}
	 *         value.
	 */
	int getNumberOfDisabledItems();

	/**
	 * Enables the item, which belongs to a specific index.
	 * 
	 * @param index
	 *            The index of the item, which should be enabled, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 */
	void enable(int index);

	/**
	 * Enables a specific item.
	 * 
	 * @param item
	 *            The item, which should be enabled, as an instance of the
	 *            generic type DataType. The item may not be null. If the item
	 *            does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 */
	void enable(DataType item);

	/**
	 * Disables the item, which belongs to a specific index.
	 * 
	 * @param index
	 *            The index of the item, which should be disabled, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 */
	void disable(int index);

	/**
	 * Disables a specific item.
	 * 
	 * @param item
	 *            The item, which should be disabled, as an instance of the
	 *            generic type DataType. The item may not be null. If the item
	 *            does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 */
	void disable(DataType item);

	/**
	 * Triggers the enable state of the item, which belongs to a specific index.
	 * This causes the item to become disabled, if it is currently enabled and
	 * vice versa.
	 * 
	 * @param index
	 *            The index of the item, whose enable state should be triggered,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>getNumberOfItems():int</code> -
	 *            1, otherwise an {@link IndexOutOfBoundsException} will be
	 *            thrown
	 * @return True, if the item has been enabled, false, if the item has been
	 *         disabled
	 */
	boolean triggerEnableState(int index);

	/**
	 * Triggers the enable state of a specific item. This causes the item to
	 * become disabled, if it is currently enabled and vice versa.
	 * 
	 * @param item
	 *            The item, whose enable state should be triggered, as an
	 *            instance of the generic type DataType. The item may not be
	 *            null. If the item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return True, if the item has been enabled, false, if the item has been
	 *         disabled
	 */
	boolean triggerEnableState(DataType item);

	/**
	 * Enables all items.
	 */
	void enableAll();

	/**
	 * Disables all items.
	 */
	void disableAll();

	/**
	 * Triggers the enable states of all items. This causes an item to become
	 * disabled, if it is currently enabled and vice versa.
	 */
	void triggerAllEnableStates();

	/**
	 * Adds a new listener, which should be notified, when an item has been
	 * disable or enabled.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            type {@link ListEnableStateListener}. The listener may not be
	 *            null
	 */
	void addEnableStateListner(ListEnableStateListener<DataType> listener);

	/**
	 * Removes a specific listener, which should not be notified, when an item
	 * has been disable or enabled, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            type {@link ListEnableStateListener}. The listener may not be
	 *            null
	 */
	void removeEnableStateListener(ListEnableStateListener<DataType> listener);

}