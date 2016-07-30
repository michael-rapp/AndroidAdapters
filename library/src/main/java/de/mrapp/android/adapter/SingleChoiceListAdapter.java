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
package de.mrapp.android.adapter;

import android.support.annotation.NonNull;
import android.widget.AbsListView;

import java.util.NoSuchElementException;

import de.mrapp.android.adapter.list.selectable.SelectableListAdapter;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list of arbitrary items,
 * of which only one single item can be selected at once, must implement. Such an adapter's purpose
 * is to provide the underlying data for visualization using a {@link AbsListView} widget.
 *
 * @param <DataType>
 *         The type of the adapter's underlying data
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface SingleChoiceListAdapter<DataType> extends SelectableListAdapter<DataType> {

    /**
     * Returns the index of the currently selected item.
     *
     * @return The index of the currently selected item, as an {@link Integer} value or -1, if no
     * item is currently selected
     */
    int getSelectedIndex();

    /**
     * Returns the currently selected item.
     *
     * @return The currently selected item, as an instance of the generic type DataType or null, if
     * no item is currently selected
     */
    DataType getSelectedItem();

    /**
     * Triggers the selection of the item, which belongs to a specific index, if it is currently
     * enabled. If the item is not currently selected, the item becomes selected and any other
     * selected item becomes unselected. If the item is already selected, the item becomes
     * unselected.
     *
     * @param index
     *         The index of the item, whose selection should be triggered, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @return True, if the selection of the given item has been changed, false otherwise
     */
    boolean triggerSelection(int index);

    /**
     * Triggers the selection of a specific item, if it is currently enabled. If the item is not
     * currently selected, the item becomes selected and any other selected item becomes unselected.
     * If the item is already selected, the item becomes unselected.
     *
     * @param item
     *         The item, which should be selected, as an instance of the generic type DataType. The
     *         item may not be null. If the item does not belong to the adapter, a {@link
     *         NoSuchElementException} will be thrown
     * @return True, if the selection of the given item has been changed, false otherwise
     */
    boolean triggerSelection(@NonNull DataType item);

    /**
     * Sets, whether the adapter's selection should be automatically adapted in order to ensure that
     * an item is always selected if possible, or not. For example this causes the selection to be
     * adapted, when the currently selected item has been removed from the adapter.
     *
     * @param adaptSelectionAutomatically
     *         True, if the adapter's selection should be automatically adapted, false otherwise
     */
    void adaptSelectionAutomatically(boolean adaptSelectionAutomatically);

    /**
     * Returns, whether the adapter's selection is automatically adapted in order to ensure that an
     * item is always selected if possible, or not.
     *
     * @return True, if the adapter's selection is automatically adapted, false otherwise
     */
    boolean isSelectionAdaptedAutomatically();

    @Override
    SingleChoiceListAdapter<DataType> clone() throws CloneNotSupportedException;

}