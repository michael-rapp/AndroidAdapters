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
package de.mrapp.android.adapter.list;

import androidx.annotation.NonNull;

/**
 * Defines the interface, all listeners, which should be notified, when the underlying data of a
 * {@link ListAdapter} has been modified, must implement.
 *
 * @param <DataType>
 *         The type of the observed adapter's underlying data
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface ListAdapterListener<DataType> {

    /**
     * The method, which is invoked, when an item has been added to the adapter.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ListAdapter}. The adapter may
     *         not be null
     * @param item
     *         The item, which has been added, as an instance of the generic type DataType. The item
     *         may not be null
     * @param index
     *         The index of the item, which has been added, as an {@link Integer} value
     */
    void onItemAdded(@NonNull ListAdapter<DataType> adapter, @NonNull DataType item, int index);

    /**
     * The method, which is invoked, when an item has been removed from the adapter.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ListAdapter}. The adapter may
     *         not be null
     * @param item
     *         The item, which has been removed, as an instance of the generic type DataType. The
     *         item may not be null
     * @param index
     *         The index of the item, which has been removed, as an {@link Integer} value
     */
    void onItemRemoved(@NonNull ListAdapter<DataType> adapter, @NonNull DataType item, int index);

}