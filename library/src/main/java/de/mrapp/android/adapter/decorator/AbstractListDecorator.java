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
package de.mrapp.android.adapter.decorator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.mrapp.android.adapter.list.ListAdapter;
import de.mrapp.android.adapter.SingleChoiceListAdapter;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;

/**
 * An abstract base class for all decorators, which should allow to customize the appearance of the
 * view, which is used to visualize the items of a {@link ListAdapter}, {@link
 * SingleChoiceListAdapter} or {@link MultipleChoiceListAdapter}.
 *
 * @param <DataType>
 *         The type of the adapter's underlying data
 * @author Michael Rapp
 * @since 0.2.0
 */
public abstract class AbstractListDecorator<DataType> extends AbstractDecorator {

    /**
     * The method which is invoked in order to retrieve the view type of a specific item, which is
     * about to be visualized. This method has to be overridden by custom decorators which should be
     * able to visualize some items optically divergent from others, returning a different integer
     * constant for each type.
     *
     * @param item
     *         The item, which should be visualized, as an instance of the generic type DataType.
     *         The item may not be null
     * @return The view type of the item, which is about to be visualized, as an {@link Integer}
     * value
     */
    public int getViewType(@NonNull final DataType item) {
        return 0;
    }

    /**
     * Returns the number of view types, which are used by the decorator in order to visualize
     * items. This method has to be overridden by custom decorators in order to return a value,
     * which is consistent with the implementation of the <code>getViewType</code>-method.
     *
     * @return The number of view types, which are used by the decorator in order to visualize
     * items, as an {@link Integer} value
     */
    public int getViewTypeCount() {
        return 1;
    }

    /**
     * The method which is invoked by an adapter to inflate the view, which should be used to
     * visualize a specific item.
     *
     * @param inflater
     *         The inflater, which should be used to inflate the view, as an instance of the class
     *         {@link LayoutInflater}. The inflater may not be null
     * @param parent
     *         The parent view of the view, which should be inflated, as an instance of the class
     *         {@link ViewGroup} or null, if no parent view is available
     * @param item
     *         The item, which should be visualized, as an instance of the generic type DataType.
     *         The item may not be null
     * @return The view, which has been inflated, as an instance of the class {@link View}. The view
     * may not be null
     */
    public final View inflateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
                                  @NonNull DataType item) {
        return onInflateView(inflater, parent, getViewType(item));
    }

    /**
     * The method which is invoked, when a view, which is used to visualize an item, should be
     * inflated.
     *
     * @param inflater
     *         The inflater, which should be used to inflate the view, as an instance of the class
     *         {@link LayoutInflater}. The inflater may not be null
     * @param parent
     *         The parent view of the view, which should be inflated, as an instance of the class
     *         {@link ViewGroup} or null, if no parent view is available
     * @param viewType
     *         The view type of the item, which should be visualized, as an {@link Integer} value
     * @return The view, which has been inflated, as an instance of the class {@link View}. The view
     * may not be null
     */
    @NonNull
    public abstract View onInflateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
                                       int viewType);

}