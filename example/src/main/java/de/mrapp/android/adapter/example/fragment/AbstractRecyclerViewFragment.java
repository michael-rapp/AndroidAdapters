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
package de.mrapp.android.adapter.example.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.mrapp.android.adapter.list.ListAdapter;
import de.mrapp.android.adapter.example.R;
import de.mrapp.android.adapter.example.model.Contact;

/**
 * An abstract base class for all fragments, which demonstrate the functionality of adapters, which
 * populate views of the type {@link RecyclerView}.
 *
 * @param <AdapterType>
 *         The type of the adapter, whose functionality is demonstrated
 * @author Michael Rapp
 */
public abstract class AbstractRecyclerViewFragment<AdapterType extends ListAdapter<Contact>>
        extends AbstractListAdapterFragment<AdapterType> {

    @Override
    protected final View inflateLayout(final LayoutInflater inflater, final ViewGroup container) {
        return inflater.inflate(R.layout.recycler_view, container, false);
    }

    @Override
    protected final void attachAdapter(final View rootView, final ListAdapter adapter) {
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter.attach(recyclerView);
    }

}