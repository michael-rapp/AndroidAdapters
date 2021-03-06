/*
 * Copyright 2014 - 2019 Michael Rapp
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package de.mrapp.android.adapter.list.itemstate;

import androidx.annotation.NonNull;
import android.widget.AbsListView;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list of arbitrary items,
 * which may have different states, must implement. Such an adapter's purpose is to provide the
 * underlying data for visualization using a {@link AbsListView} widget.
 *
 * @param <DataType>
 *         The type of the adapter's underlying data
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface ItemStateListAdapter<DataType> {

    /**
     * Returns the number of states, the adapter's items may have.
     *
     * @return The number of states, the adapter's items may have, as an {@link Integer} value. The
     * value must be at least 1
     */
    int getNumberOfItemStates();

    /**
     * Sets the number of states, the adapter's items may have. If the number of states is set to a
     * value, less than the previous value, the states of the items, which became obsolete, will be
     * set to the maximum state.
     *
     * @param numberOfItemStates
     *         The number of states, which should be set, as an {@link Integer} value. The value
     *         must be at least 1, otherwise an {@link IllegalArgumentException} will be thrown
     */
    void setNumberOfItemStates(int numberOfItemStates);

    /**
     * Returns the minimum state, the adapter's items may have.
     *
     * @return The minimum state, the adapter's items may have, as an {@link Integer} value
     */
    int minItemState();

    /**
     * Returns the maximum state, the adapter's items may have.
     *
     * @return The maximum state, the adapter's items may have, as an {@link Integer} value
     */
    int maxItemState();

    /**
     * Returns the current state of the item, which belongs to a specific index.
     *
     * @param index
     *         The index of the item, whose state should be returned, as an {@link Integer} value.
     *         The index must be between 0 and the value of the method <code>getCount():int</code> -
     *         1, otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @return The state of the item, which belongs to the given index, as a {@link Integer} value
     */
    int getItemState(int index);

    /**
     * Returns the current state of a specific item.
     *
     * @param item
     *         The item, whose state should be returned, as an instance of the generic type
     *         DataType. The item may not be null. If the item does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @return The state of the given item as an {@link Integer} value
     */
    int getItemState(@NonNull DataType item);

    /**
     * Sets the state of the item, which belongs to a specific index, to a specific state, if it is
     * currently enabled.
     *
     * @param index
     *         The index of the item, whose state should be set, as an {@link Integer} value. The
     *         index must be between 0 and the value of the method <code>getCount():int</code> - 1,
     *         otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @param state
     *         The state, which should be set, as an {@link Integer} value. The state must be
     *         between 0 and the value of the method <code>getNumberOfStates():int</code> - 1,
     *         otherwise an {@link IllegalArgumentException} will be thrown
     * @return The previous state of the item, which belongs to the given index, as an {@link
     * Integer} value or -1, if the state has not been changed
     */
    int setItemState(int index, int state);

    /**
     * Sets the state of a specific item to a specific state, if it is currently enabled.
     *
     * @param item
     *         The item, whose state should be set, as an instance of the generic type DataType. The
     *         item may not be null. If the item does not belong to the adapter, a {@link
     *         NoSuchElementException} will be thrown
     * @param state
     *         The state, which should be set, as an {@link Integer} value. The state must be
     *         between 0 and the value of the method <code>getNumberOfStates():int</code> - 1,
     *         otherwise an {@link IllegalArgumentException} will be thrown
     * @return The previous state of the given item as an {@link Integer} value or -1, if the state
     * has not been changed
     */
    int setItemState(@NonNull DataType item, int state);

    /**
     * Sets the states of all items to a specific state, if they are currently enabled.
     *
     * @param state
     *         The state, which should be set, as an {@link Integer} value. The state must be
     *         between 0 and the value of the method <code>getNumberOfStates():int</code> - 1,
     *         otherwise an {@link IllegalArgumentException} will be thrown
     * @return True, if the states of all items have been changed, false otherwise
     */
    boolean setAllItemStates(int state);

    /**
     * Triggers the state of the item, which belongs to a specific index, if it is currently
     * enabled. This causes the state to be increased by one. If the state is already the maximum
     * state, the state will be set to 0 instead.
     *
     * @param index
     *         The index of the item, whose state should be triggered, as an {@link Integer} value.
     *         The index must be between 0 and the value of the method <code>getCount():int</code> -
     *         1, otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @return The previous state of the item, which belongs to the given index, as an {@link
     * Integer} value or -1, if the state has not been changed
     */
    int triggerItemState(int index);

    /**
     * Triggers the state of a specific item, if it is currently enabled. This causes the state to
     * be increased by one. If the state is already the maximum state, the state will be set to 0
     * instead.
     *
     * @param item
     *         The item, whose state should be triggered, as an instance of the generic type
     *         DataType. The item may not be null. If the item does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @return The previous state of the given item, as an {@link Integer} value or -1, if the state
     * has not been changed
     */
    int triggerItemState(@NonNull DataType item);

    /**
     * Triggers the states of all items. if they are currently enabled. This causes the states to be
     * increased by one. If a state is already the maximum state, the state will be set to 0
     * instead.
     *
     * @return True, if the states of all items have been changed, false otherwise
     */
    boolean triggerAllItemStates();

    /**
     * Returns the index of the first item, which currently has a specific state.
     *
     * @param state
     *         The state of the item, whose index should be returned, as an {@link Integer} value
     * @return The index of the first item, which currently has the given state, as an {@link
     * Integer} value or -1, if the adapter does not contain an item with the given state
     */
    int getFirstIndexWithSpecificState(int state);

    /**
     * Returns the first item, which currently has a specific state.
     *
     * @param state
     *         The state of the item, which should be returned, as an {@link Integer} value
     * @return The first item, which currently has the given state, as an instance of the generic
     * type DataType or null, if the adapter does not contain an item with the given state
     */
    DataType getFirstItemWithSpecificState(int state);

    /**
     * Returns the index of the last item, which currently has a specific state.
     *
     * @param state
     *         The state of the item, whose index should be returned, as an {@link Integer} value
     * @return The index of the last item, which currently has the given state, as an {@link
     * Integer} value or -1, if the adapter does not contain an item with the given state
     */
    int getLastIndexWithSpecificState(int state);

    /**
     * Returns the last item, which currently has a specific state.
     *
     * @param state
     *         The state of the item, which should be returned, as an {@link Integer} value
     * @return The last item, which currently has the given state, as an instance of the generic
     * type DataType or null, if the adapter does not contain an item with the given state
     */
    DataType getLastItemWithSpecificState(int state);

    /**
     * Returns a list, which contains the indices of all items, which currently have a specific
     * state.
     *
     * @param state
     *         The state of the items, whose indices should be returned, as an {@link Integer}
     *         value
     * @return A list, which contains the indices of all items, which currently have a specific
     * state, as an instance of the type {@link List} or an empty list, if the adapter does not
     * contain any items with the given state
     */
    List<Integer> getIndicesWithSpecificState(int state);

    /**
     * Returns a list, which contains all items, which currently have a specific state.
     *
     * @param state
     *         The state of the items, which should be returned, as an {@link Integer} value
     * @return A list, which contains the items, which currently have the given state, as an
     * instance of the type {@link List} or an empty list, if the adapter contains no items with the
     * given state
     */
    List<DataType> getItemsWithSpecificState(int state);

    /**
     * Returns the number of items, which currently have a specific state.
     *
     * @param state
     *         The state of the items, which should be counted, as an {@link Integer} value
     * @return The number of items, which currently have the given state, as an {@link Integer}
     * value
     */
    int getItemStateCount(int state);

    /**
     * Returns, whether the state of an item is triggered, when it is clicked by the user, or not.
     *
     * @return True, if the state of an item is triggered, when it is clicked by the user, false
     * otherwise
     */
    boolean isItemStateTriggeredOnClick();

    /**
     * Sets, whether the state of an item should be triggered, when it is clicked by the user, or
     * not.
     *
     * @param triggerItemStateOnClick
     *         True, if the state of an item should be triggered, when it is clicked by the user,
     *         false otherwise
     */
    void triggerItemStateOnClick(boolean triggerItemStateOnClick);

    /**
     * Adds a new listener, which should be notified, when the state of an item has been changed.
     *
     * @param listener
     *         The listener, which should be added, as an instance of the class {@link
     *         ListItemStateListener}. The listener may not be null
     */
    void addItemStateListener(@NonNull final ListItemStateListener<DataType> listener);

    /**
     * Removes a specific listener, which should not be notified, when the state of an item has been
     * changed, anymore.
     *
     * @param listener
     *         The listener, which should be removed, as an instance of the class {@link
     *         ListItemStateListener}. The listener may not be null
     */
    void removeItemStateListener(@NonNull ListItemStateListener<DataType> listener);

}