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
package de.mrapp.android.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/**
 * Defines the interface, all expandable list adapters, which can be attached to a RecyclerView,
 * must implement.
 *
 * @author Michael Rapp
 * @since 0.8.0
 */
public interface ExpandableRecyclerViewAdapter extends ExpandableGridViewAdapter {

    /**
     * Attaches the adapter to a view.
     *
     * @param adapterView
     *         The view, the adapter should be attached to, as an instance of the class
     *         RecyclerView. The view may not be null
     */
    void attach(@NonNull final RecyclerView adapterView);

    @Override
    ExpandableRecyclerViewAdapter clone() throws CloneNotSupportedException;

}