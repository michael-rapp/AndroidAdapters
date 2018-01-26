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

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import de.mrapp.android.adapter.decorator.AbstractListDecorator;
import de.mrapp.android.adapter.list.ListAdapter;

/**
 * An abstract base class for all decorators, which should allow to customize the appearance of the
 * view, which is used to visualize the items of a {@link ListAdapter}.
 *
 * @param <DataType>
 *         The type of the adapter's underlying data
 * @author Michael Rapp
 * @since 0.1.0
 */
public abstract class ListDecorator<DataType> extends AbstractListDecorator<DataType> {

    /**
     * The method, which is invoked by an adapter to apply the decorator. It initializes the view
     * holder pattern, which is provided by the decorator and then delegates the method call to the
     * decorator's custom implementation of the method <code>onShowItem(...):void</code>.
     *
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param adapter
     *         The adapter, whose items are visualized by the decorator, as an instance of the type
     *         {@link ListAdapter}. The adapter may not be null
     * @param view
     *         The view, which is used to visualize the item, as an instance of the class {@link
     *         View}. The view may not be null
     * @param item
     *         The item, which should be visualized, as an instance of the generic type DataType.
     *         The item may not be null
     * @param index
     *         The index of the item, which should be visualized, as an {@link Integer} value
     * @param enabled
     *         True, if the item, which should be visualized, is currently enabled, false otherwise
     * @param state
     *         The current state of the item, which should be visualized, as an {@link Integer}
     *         value
     * @param filtered
     *         True, if at least one filter is currently applied on the adapter, false otherwise
     */
    public final void applyDecorator(@NonNull final Context context,
                                     @NonNull final ListAdapter<DataType> adapter,
                                     @NonNull final View view, @NonNull final DataType item,
                                     final int index, final boolean enabled, final int state,
                                     final boolean filtered) {
        setCurrentParentView(view);
        int viewType = getViewType(item);
        adaptViewState(view, enabled, false);
        onShowItem(context, adapter, view, item, viewType, index, enabled, state, filtered);
    }

    /**
     * The method which is invoked, when the view, which is used to visualize an item, should be
     * shown, respectively when it should be refreshed. The purpose of this method is to customize
     * the appearance of the view, which is used to visualize the appropriate item, depending on its
     * state and whether it is currently enabled or not.
     *
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param adapter
     *         The adapter, whose items are visualized by the decorator, as an instance of the type
     *         {@link ListAdapter}. The adapter may not be null
     * @param view
     *         The view, which is used to visualize the item, as an instance of the class {@link
     *         View}. The view may not be null
     * @param item
     *         The item, which should be visualized, as an instance of the generic type DataType.
     *         The item may not be null
     * @param viewType
     *         The view type of the item, which should be visualized, as an {@link Integer} value
     * @param index
     *         The index of the item, which should be visualized, as an {@link Integer} value
     * @param enabled
     *         True, if the item, which should be visualized, is currently enabled, false otherwise
     * @param state
     *         The current state of the item, which should be visualized, as an {@link Integer}
     *         value
     * @param filtered
     *         True, if at least one filter is currently applied on the adapter, false otherwise
     */
    public abstract void onShowItem(@NonNull Context context,
                                    @NonNull ListAdapter<DataType> adapter, @NonNull View view,
                                    @NonNull DataType item, int viewType, int index,
                                    boolean enabled, int state, boolean filtered);

}