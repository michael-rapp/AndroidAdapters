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
package de.mrapp.android.adapter.list.itemstate;

import java.util.List;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list
 * of arbitrary items, which may have different states, must implement. Such an
 * adapter's purpose is to provide the underlying data for visualization using a
 * {@link ListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface ItemStateListAdapter<DataType> {

	/**
	 * Returns the number of states, the adapter's items may have.
	 * 
	 * @return The number of states, the adapter's items may have, as an
	 *         {@link Integer} value. The value must be at least 1
	 */
	int getNumberOfItemStates();

	/**
	 * Returns the current state of the item, which belongs to a specific index.
	 * 
	 * @param index
	 *            The index of the item, whose state should be returned, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @return The state of the item, which belongs to the given index, as a
	 *         {@link Integer} value
	 */
	int getItemState(int index);

	/**
	 * Returns the current state of a specific item.
	 * 
	 * @param item
	 *            The item, whose state should be returned, as an instance of
	 *            the generic type DataType. The item may not be null. If the
	 *            item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return The state of the given item as an {@link Integer} value
	 */
	int getItemState(DataType item);

	/**
	 * Sets the state of the item, which belongs to a specific index, to a
	 * specific state.
	 * 
	 * @param index
	 *            The index of the item, whose state should be set, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param state
	 *            The state, which should be set, as an {@link Integer} value.
	 *            The state must be between 0 and the value of the method
	 *            <code>getNumberOfStates():int</code> - 1
	 * @return The previous state of the item, which belongs to the given index,
	 *         as an {@link Integer} value
	 */
	int setItemState(int index, int state);

	/**
	 * Sets the state of a specific item to a specific state.
	 * 
	 * @param item
	 *            The item, whose state should be set, as an instance of the
	 *            generic type DataType. The item may not be null. If the item
	 *            does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @param state
	 *            The state, which should be set, as an {@link Integer} value.
	 *            The state must be between 0 and the value of the method
	 *            <code>getNumberOfStates():int</code> - 1
	 * @return The previous state of the given item as an {@link Integer} value
	 */
	int setItemState(DataType item, int state);

	/**
	 * Sets the states of all items to a specific state.
	 * 
	 * @param state
	 *            The state, which should be set, as an {@link Integer} value.
	 *            The state must be between 0 and the value of the method
	 *            <code>getNumberOfStates():int</code> - 1
	 */
	void setAllItemStates(int state);

	/**
	 * Triggers the state of the item, which belongs to a specific index. This
	 * causes the state to be increased by one. If the state is already the
	 * maximum state, the state will be set to 0 instead.
	 * 
	 * @param index
	 *            The index of the item, whose state should be triggered, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @return The previous state of the item, which belongs to the given index,
	 *         as an {@link Integer} value
	 */
	int triggerItemState(int index);

	/**
	 * Triggers the state of a specific item. This causes the state to be
	 * increased by one. If the state is already the maximum state, the state
	 * will be set to 0 instead.
	 * 
	 * @param item
	 *            The item, whose state should be triggered, as an instance of
	 *            the generic type DataType. The item may not be null. If the
	 *            item does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return The previous state of the given item, as an {@link Integer} value
	 */
	int triggerItemState(DataType item);

	/**
	 * Triggers the states of all items. This causes the states to be increased
	 * by one. If a state is already the maximum state, the state will be set to
	 * 0 instead.
	 */
	void triggerAllItemStates();

	/**
	 * Returns the index of the first item, which currently has a specific
	 * state.
	 * 
	 * @param state
	 *            The state of the item, whose index should be returned, as an
	 *            {@link Integer} value
	 * @return The index of the first item, which currently has the given state,
	 *         as an {@link Integer} value or -1, if the adapter does not
	 *         contain an item with the given state
	 */
	int getFirstIndexWithSpecificState(int state);

	/**
	 * Returns the first item, which currently has a specific state.
	 * 
	 * @param state
	 *            The state of the item, which should be returned, as an
	 *            {@link Integer} value
	 * @return The first item, which currently has the given state, as an
	 *         instance of the generic type DataType or null, if the adapter
	 *         does not contain an item with the given state
	 */
	DataType getFirstItemWithSpecificState(int state);

	/**
	 * Returns the index of the last item, which currently has a specific state.
	 * 
	 * @param state
	 *            The state of the item, whose index should be returned, as an
	 *            {@link Integer} value
	 * @return The index of the last item, which currently has the given state,
	 *         as an {@link Integer} value or -1, if the adapter does not
	 *         contain an item with the given state
	 */
	int getLastIndexWithSpecificState(int state);

	/**
	 * Returns the last item, which currently has a specific state.
	 * 
	 * @param state
	 *            The state of the item, which should be returned, as an
	 *            {@link Integer} value
	 * @return The last item, which currently has the given state, as an
	 *         instance of the generic type DataType or null, if the adapter
	 *         does not contain an item with the given state
	 */
	DataType getLastItemWithSpecificState(int state);

	/**
	 * Returns a list, which contains the indices of all items, which currently
	 * have a specific state.
	 * 
	 * @param state
	 *            The state of the items, whose indices should be returned, as
	 *            an {@link Integer} value
	 * @return A list, which contains the indices of all items, which currently
	 *         have a specific state, as an instance of the type {@link List} or
	 *         an empty list, if the adapter does not contain any items with the
	 *         given state
	 */
	List<Integer> getIndicesWithSpecificState(int state);

	/**
	 * Returns a list, which contains all items, which currently have a specific
	 * state.
	 * 
	 * @param state
	 *            The state of the items, which should be returned, as an
	 *            {@link Integer} value
	 * @return A list, which contains the items, which currently have the given
	 *         state, as an instance of the type {@link List} or an empty list,
	 *         if the adapter contains no items with the given state
	 */
	List<DataType> getItemsWithSpecificState(int state);

	/**
	 * Returns the number of items, which currently have a specific state.
	 * 
	 * @param state
	 *            The state of the items, which should be counted, as an
	 *            {@link Integer} value
	 * @return The number of items, which currently have the given state, as an
	 *         {@link Integer} value
	 */
	int getNumberOfItemsWithSpecificState(int state);

	/**
	 * Returns, whether the state of an item is triggered, when it is clicked by
	 * the user, or not.
	 * 
	 * @return True, if the state of an item is triggered, when it is clicked by
	 *         the user, false otherwise
	 */
	boolean isItemStateTriggeredOnClick();

	/**
	 * Sets, whether the state of an item should be triggered, when it is
	 * clicked by the user, or not.
	 * 
	 * @param triggerItemStateOnClick
	 *            True, if the state of an item should be triggered, when it is
	 *            clicked by the user, false otherwise
	 */
	void triggerItemStateOnClick(boolean triggerItemStateOnClick);

	/**
	 * Adds a new listener, which should be notified, when the state of an item
	 * has been changed.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            class {@link ListItemStateListener}. The listener may not be
	 *            null
	 */
	void addItemStateListner(final ListItemStateListener<DataType> listener);

	/**
	 * Removes a specific listener, which should not be notified, when the state
	 * of an item has been changed, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            class {@link ListItemStateListener}. The listener may not be
	 *            null
	 */
	void removeItemStateListener(ListItemStateListener<DataType> listener);

}