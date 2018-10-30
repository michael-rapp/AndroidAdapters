/*
 * Copyright 2014 - 2018 Michael Rapp
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
package de.mrapp.android.adapter;

import androidx.annotation.NonNull;
import android.widget.AbsListView;

import java.util.List;
import java.util.NoSuchElementException;

import de.mrapp.android.adapter.list.selectable.SelectableListAdapter;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list of arbitrary items,
 * of which multiple items can be selected at once, must implement. Such an adapter's purpose is to
 * provide the underlying data for visualization using a {@link AbsListView} widget.
 *
 * @param <DataType>
 *         The type of the adapter's underlying data
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface MultipleChoiceListAdapter<DataType> extends SelectableListAdapter<DataType> {

    /**
     * Returns the index of the first selected item.
     *
     * @return The index of the first selected item or -1, if no item is currently selected
     */
    int getFirstSelectedIndex();

    /**
     * Returns the first selected item.
     *
     * @return The first selected item, as an instance of the generic type DataType or null, if no
     * item is currently selected
     */
    DataType getFirstSelectedItem();

    /**
     * Returns the index of the last selected item.
     *
     * @return The index of the last selected item or -1, if no item is currently selected
     */
    int getLastSelectedIndex();

    /**
     * Returns the last selected item.
     *
     * @return The last selected item, as an instance of the generic type DataType or null, if no
     * item is currently selected
     */
    DataType getLastSelectedItem();

    /**
     * Returns the index of the first unselected item.
     *
     * @return The index of the first unselected item or -1, if no item is currently unselected
     */
    int getFirstUnselectedIndex();

    /**
     * Returns the first unselected item.
     *
     * @return The first unselected item, as an instance of the generic type DataType or null, if no
     * item is currently unselected
     */
    DataType getFirstUnselectedItem();

    /**
     * Return the index of the last unselected item.
     *
     * @return The index of the last unselected item or -1, if no item is currently unselected
     */
    int getLastUnselectedIndex();

    /**
     * Returns the last unselected item.
     *
     * @return The last unselected item, as an instance of the generic type DataType or null, if no
     * item is currently unselected
     */
    DataType getLastUnselectedItem();

    /**
     * Returns a list, which contains the indices of all currently selected items.
     *
     * @return A list, which contains the indices of all currently selected items, as an instance of
     * the type {@link List} or an empty list, if no item is currently selected
     */
    List<Integer> getSelectedIndices();

    /**
     * Returns a list, which contains all currently selected items.
     *
     * @return A list, which contains all currently selected items, as an instance of the type
     * {@link List} or an empty list, if no item is currently selected
     */
    List<DataType> getSelectedItems();

    /**
     * Returns a list, which contains the indices of all currently unselected items.
     *
     * @return A list, which contains the indices of all currently unselected items, as an instance
     * of the type {@link List} or an empty list, if no item is currently selected
     */
    List<Integer> getUnselectedIndices();

    /**
     * Returns a list, which contains all currently unselected items.
     *
     * @return A list, which contains all currently unselected items, as an instance of the type
     * {@link List} or an empty list, if no item is currently selected
     */
    List<DataType> getUnselectedItems();

    /**
     * Sets the selection of the item, which belongs to a specific index, if it is currently
     * enabled.
     *
     * @param index
     *         The index of the item, whose selection should be set, as an {@link Integer} value.
     *         The index must be between 0 and the value of the method <code>getCount():int</code> -
     *         1, otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @param selected
     *         True, if the item, which belongs to the given index, should be selected, false
     *         otherwise
     * @return True, if the selection of the item, which belongs to the given index, has been
     * changed, false otherwise
     */
    boolean setSelected(int index, boolean selected);

    /**
     * Sets the selection of a specific item, if it is currently enabled.
     *
     * @param item
     *         The item, whose selection should be set, as an instance of the generic type DataType.
     *         The item may not be null. If the item does not belong to the adapter, a {@link
     *         NoSuchElementException} will be thrown
     * @param selected
     *         True, if the given item should be selected, false otherwise
     * @return True, if the selection of the given item has been changed, false otherwise
     */
    boolean setSelected(@NonNull DataType item, boolean selected);

    /**
     * Triggers the selection of the item, which belongs to a specific index, it is currently
     * enabled. This causes the item to become unselected, if it is currently selected and vice
     * versa.
     *
     * @param index
     *         The index of the item, whose selection should be triggered, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @return True, if the selection of the item, which belongs to the given index, has been
     * changed, false otherwise
     */
    boolean triggerSelection(int index);

    /**
     * Triggers the selection of a specific item, it it is currently enabled. This causes the item
     * to become unselected, if it is currently selected and vice versa.
     *
     * @param item
     *         The item, whose selection should be triggered, as an instance of the generic type
     *         DataType. The item may not be null. If the item does not belong to the adapter, a
     *         {@link NoSuchElementException} will be thrown
     * @return True, if the selection of the given item has been changed, false otherwise
     */
    boolean triggerSelection(@NonNull DataType item);

    /**
     * Sets the selection of all items, if they are currently enabled.
     *
     * @param selected
     *         True, if all items should be selected, false otherwise
     * @return True, if the selections of all items have been changed, false otherwise
     */
    boolean setAllSelected(boolean selected);

    /**
     * Triggers the selections of all items, if they are currently enabled. This causes an item to
     * become unselected, if it is currently selected and vice versa.
     *
     * @return True, if the selections of all items have been changed, false otherwise
     */
    boolean triggerAllSelections();

    @Override
    MultipleChoiceListAdapter<DataType> clone() throws CloneNotSupportedException;

}