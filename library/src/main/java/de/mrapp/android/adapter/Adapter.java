/*
 * Copyright 2014 - 2019 Michael Rapp
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
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import de.mrapp.android.adapter.datastructure.DataStructure;
import de.mrapp.android.adapter.datastructure.Parameterizable;
import de.mrapp.android.adapter.datastructure.Restorable;
import de.mrapp.android.adapter.logging.Loggable;

/**
 * Defines the interface, all adapters must implement.
 *
 * @param <AdapterViewType>
 *         The type of the views, the adapter can be attached to
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface Adapter<AdapterViewType extends AdapterView<? extends android.widget.Adapter>>
        extends DataStructure, Restorable, Loggable, Parameterizable {

    /**
     * Attaches the adapter to a view.
     *
     * @param adapterView
     *         The view, the adapter should be attached to, as an instance of the generic type
     *         AdapterViewType. The view may not be null
     */
    void attach(@NonNull final AdapterViewType adapterView);

    /**
     * Detaches the adapter from the view, it is currently attached to.
     */
    void detach();

    /**
     * Returns, whether the adapter is currently attached to a view, or not.
     *
     * @return True, if the adapter is currently attached to a view, false otherwise
     */
    boolean isAttached();

    /**
     * Returns the view, the adapter is attached to.
     *
     * @return The view, the adapter is attached to, as an instance of the class {@link View} or
     * null, if the adapter is not attached to a view
     */
    @Nullable
    View getAdapterView();

    /**
     * Notifies all attached observers about the underlying data of the adapter having changed. This
     * causes the adapter's views to be refreshed.
     */
    void notifyDataSetChanged();

    /**
     * Returns, whether the method <code>notifyDataSetChanged():void</code> is automatically called
     * when the adapter's underlying data has been changed, or not.
     *
     * @return True, if the method <code>notifyDataSetChanged():void</code> is automatically called
     * when the adapter's underlying data has been changed, false otherwise
     */
    boolean isNotifiedOnChange();

    /**
     * Sets, whether the method <code>notifyDataSetChanged():void</code> should be automatically
     * called when the adapter's underlying data has been changed, or not.
     *
     * @param notifyOnChange
     *         True, if the method <code>notifyDataSetChanged():void</code> should be automatically
     *         called when the adapter's underlying data has been changed, false otherwise
     */
    void notifyOnChange(boolean notifyOnChange);

    /**
     * Returns, whether the adapter is empty, or not.
     *
     * @return True, if the adapter is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns, whether the adapter's items have stable ids, or not.
     *
     * @return True, if the items have stable ids, false otherwise
     */
    boolean hasStableIds();

    @Override
    Adapter<AdapterViewType> clone() throws CloneNotSupportedException;

}