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
package de.mrapp.android.adapter.list;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Defines the interface, all listeners, which should be notified, when an item of a {@link
 * ListAdapter} has been long-clicked by the user, must implement.
 *
 * @param <DataType>
 *         The type of the observed adapter's underlying data
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface ListAdapterItemLongClickListener<DataType> {

    /**
     * The method, which is invoked, when an item of the adapter has been long-clicked by the user.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ListAdapter}. The adapter may
     *         not be null
     * @param item
     *         The item, which has been long-clicked by the user, as an instance of the generic type
     *         DataType. The item may not be null
     * @param index
     *         The index of the item, which has been long-clicked by the user, as an {@link Integer}
     *         value
     * @return True, if the listener has consumed the long-click, false otherwise
     */
    boolean onItemLongClicked(@NonNull ListAdapter<DataType> adapter, @NonNull DataType item,
                              int index);

    /**
     * The method, which is invoked, when a header of the adapter view, the adapter is attached to,
     * has been long-clicked by the user.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ListAdapter}. The adapter may
     *         not be null
     * @param view
     *         The header view, which has been long-clicked by the user, as an instance of the class
     *         {@link View}. The view may not be null
     * @param index
     *         The index of the header, which has been long-clicked by the user, as an {@link
     *         Integer} value
     * @return True, if the listener has consumed the long-click, false otherwise
     */
    boolean onHeaderLongClicked(@NonNull ListAdapter<DataType> adapter, @NonNull View view,
                                int index);

    /**
     * The method, which is invoked, when a footer of the adapter view, the adapter is attached to,
     * has been long-clicked by the user.
     *
     * @param adapter
     *         The observed adapter as an instance of the type {@link ListAdapter}. The adapter may
     *         not be null
     * @param view
     *         The footer view, which has been long-clicked by the user, as an instance of the class
     *         {@link View}. The view may not be null
     * @param index
     *         The index of the footer, which has been long-clicked by the user, as an {@link
     *         Integer} value
     * @return True, if the listener has consumed the long-click, false otherwise
     */
    boolean onFooterLongClicked(@NonNull ListAdapter<DataType> adapter, @NonNull View view,
                                int index);

}