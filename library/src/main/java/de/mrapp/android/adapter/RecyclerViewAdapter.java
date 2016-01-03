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
package de.mrapp.android.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

/**
 * Defines the interface, all adapters, which can be attached to a RecyclerView, must implement.
 *
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface RecyclerViewAdapter extends Adapter<AbsListView> {

    /**
     * Attaches the adapter to a view.
     *
     * @param adapterView
     *         The view, the adapter should be attached to, as an instance of the generic type
     *         AdapterViewType. The view may not be null
     */
    void attach(@NonNull final RecyclerView adapterView);

    @Override
    RecyclerViewAdapter clone() throws CloneNotSupportedException;

}