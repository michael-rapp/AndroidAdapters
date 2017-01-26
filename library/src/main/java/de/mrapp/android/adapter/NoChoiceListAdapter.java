/*
 * Copyright 2014 - 2017 Michael Rapp
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

import de.mrapp.android.adapter.list.ListAdapter;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list of arbitrary items,
 * which can not be selected, must implement. Such an adapter's purpose is to provide the underlying
 * data for visualization using a {@link AbsListView} widget.
 *
 * @param <DataType>
 *         The type of the adapter's underlying data
 * @author Michael Rapp
 * @since 0.5.0
 */
public interface NoChoiceListAdapter<DataType> extends ListAdapter<DataType> {

    /**
     * Returns the decorator, which allows to customize the appearance of the views, which are used
     * to visualize the items of the adapter.
     *
     * @return The decorator, which allows to customize the appearance of the views, which are used
     * to visualize the items of the adapter, as an instance of the type {@link ListDecorator}. The
     * decorator may not be null
     */
    ListDecorator<DataType> getDecorator();

    /**
     * Sets the decorator, which allows to customize the appearance of the views, which are used to
     * visualize the items of the adapter.
     *
     * @param decorator
     *         The decorator, which should be set, as an instance of the type {@link ListDecorator}.
     *         The decorator may not be null
     */

    void setDecorator(@NonNull ListDecorator<DataType> decorator);

    @Override
    NoChoiceListAdapter<DataType> clone() throws CloneNotSupportedException;

}