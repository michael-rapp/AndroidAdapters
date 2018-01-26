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
package de.mrapp.android.adapter.list.itemstate;

import android.support.annotation.NonNull;

import de.mrapp.android.adapter.list.ListAdapter;

/**
 * Defines the interface, all listeners, which should be notified, when the state of an item of a
 * {@link ListAdapter} has been changed, must implement.
 *
 * @param <DataType>
 *         The type of the observed adapter's underlying data
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface ListItemStateListener<DataType> {

    /**
     * The method, which is invoked, when the state of an item has been changed.
     *
     * @param adapter
     *         The observed adapters as an instance of the type {@link ListAdapter}. The adapter may
     *         not be null
     * @param item
     *         The item, whose state has been changed, as an instance of the generic type DataType.
     *         The item may not be null
     * @param index
     *         The index of the item, whose state has been changed, as an {@link Integer} value
     * @param state
     *         The new state of the item, whose state has been changed, as an {@link Integer} value
     */
    void onItemStateChanged(@NonNull ListAdapter<DataType> adapter, @NonNull DataType item,
                            int index, int state);

}