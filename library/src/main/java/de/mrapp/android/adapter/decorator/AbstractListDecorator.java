/*
 * AndroidAdapters Copyright 2014 - 2016 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package de.mrapp.android.adapter.decorator;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.mrapp.android.adapter.ListAdapter;
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