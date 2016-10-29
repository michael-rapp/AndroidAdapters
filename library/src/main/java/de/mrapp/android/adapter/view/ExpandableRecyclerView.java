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
package de.mrapp.android.adapter.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import de.mrapp.android.adapter.expandablelist.ExpandableListAdapter;

/**
 * @author Michael Rapp
 */
public class ExpandableRecyclerView extends RecyclerView {
    public ExpandableRecyclerView(Context context) {
        super(context);
    }

    public ExpandableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Sets the adapter that provides data to this view.
     *
     * @param adapter
     *         The adapter, which should be set, as an instance of the type {@link
     *         ExpandableListAdapter} or null, if no adapter should be set
     */
    public final void setAdapter(@Nullable final ExpandableListAdapter adapter) {

    }

    /**
     * Returns the adapter that provides data to this view.
     *
     * @return The adapter that provides data to this view as an instance of the type {@link
     * ExpandableListAdapter} or null, if no adapter has been set.
     */
    public ExpandableListAdapter getExpandableListAdapter() {
        return null;
    }

    public void expandGroup(int i) {

    }

    public void collapseGroup(int i) {

    }

}
