/*
 * AndroidAdapters Copyright 2014 - 2015 Michael Rapp
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
package de.mrapp.android.adapter;

import android.support.annotation.NonNull;
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
     * Notifies all attached observers about the underlying data of the adapter having changed. This
     * causes the adapter's views to be refreshed.
     */
    void notifyDataSetChanged();

    /**
     * Notifies all attached observers about the underlying data being no longer valid or available.
     * Once invoked this adapter is no longer valid and should not report further data set changes.
     */
    void notifyDataSetInvalidated();

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

    @Override
    Adapter<AdapterViewType> clone() throws CloneNotSupportedException;

}