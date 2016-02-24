/*
 * Copyright 2014 - 2016 Michael Rapp
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
package de.mrapp.android.adapter.list.selectable;

import android.support.annotation.NonNull;
import android.widget.AbsListView;

import java.util.NoSuchElementException;

import de.mrapp.android.adapter.ListAdapter;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list of arbitrary items,
 * of which one or multiple items can be selected, must implement. Such an adapter's purpose is to
 * provide the underlying data for visualization using a {@link AbsListView} widget.
 *
 * @param <DataType>
 *         The type of the adapter's underlying data
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface SelectableListAdapter<DataType> extends ListAdapter<DataType> {

    /**
     * Adds a new listener, which should be notified when the selection of an item has been
     * changed.
     *
     * @param listener
     *         The listener, which should be added, as an instance of the type {@link
     *         ListSelectionListener}. The listener may not be null
     */
    void addSelectionListener(@NonNull ListSelectionListener<DataType> listener);

    /**
     * Removes a specific listener, which should not be notified when the selection of an item has
     * been changed, anymore.
     *
     * @param listener
     *         The listener, which should be removed, as an instance of the type {@link
     *         ListSelectionListener}. The listener may not be null
     */
    void removeSelectionListener(@NonNull ListSelectionListener<DataType> listener);

    /**
     * Returns the number of currently selected items.
     *
     * @return The number of currently selected items, as an {@link Integer} value
     */
    int getSelectedItemCount();

    /**
     * Returns, whether the item, which belongs to a specific index, is currently selected, or not.
     *
     * @param index
     *         The index of the item, whose selection state should be returned, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @return True, if the item, which belongs to the given index, is currently selected, false
     * otherwise
     */
    boolean isSelected(int index);

    /**
     * Returns, whether a specific item is currently selected, or not.
     *
     * @param item
     *         The item, whose selection state should be returned, as an instance of the generic
     *         type DataType. The item may not be null. If the item does not belong to the adapter,
     *         a {@link NoSuchElementException} will be thrown
     * @return True, if the item, which belongs to the given index, is currently selected, false
     * otherwise
     */
    boolean isSelected(@NonNull DataType item);

    /**
     * Returns, whether an item is selected, when it is clicked by the user, or not.
     *
     * @return True, if an item is selected, when it is clicked by the user, false otherwise
     */
    boolean isItemSelectedOnClick();

    /**
     * Sets, whether an item should be selected, when it is clicked by the user, or not.
     *
     * @param selectItemOnClick
     *         True, if an item should be selected, when it is clicked by the user, false otherwise
     */
    void selectItemOnClick(boolean selectItemOnClick);

}