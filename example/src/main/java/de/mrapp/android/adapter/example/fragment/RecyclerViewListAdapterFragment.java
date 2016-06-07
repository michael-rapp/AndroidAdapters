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

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import de.mrapp.android.adapter.AdapterFactory;
import de.mrapp.android.adapter.NoChoiceListAdapter;
import de.mrapp.android.adapter.example.R;
import de.mrapp.android.adapter.example.decorator.ListAdapterDecorator;
import de.mrapp.android.adapter.example.model.Contact;

/**
 * A fragment, which demonstrates the functionality of a {@link NoChoiceListAdapter} when populating
 * a {@link RecyclerView}.
 *
 * @author Michael Rapp
 */
public class RecyclerViewListAdapterFragment
        extends AbstractRecyclerViewFragment<NoChoiceListAdapter<Contact>> {

    @Override
    protected NoChoiceListAdapter<Contact> createAdapter() {
        return AdapterFactory.createListAdapter(getActivity(), new ListAdapterDecorator());
    }

    @Override
    public final void onStart() {
        super.onStart();
        getRemoveButton().setVisibility(View.GONE);

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity());

        String allowDuplicatesKey =
                getString(R.string.list_adapter_allow_duplicate_items_preference_key);
        boolean allowDuplicates = sharedPreferences.getBoolean(allowDuplicatesKey, false);
        getAdapter().allowDuplicates(allowDuplicates);

        String triggerItemStateOnClickKey =
                getString(R.string.list_adapter_trigger_item_state_on_click_preference_key);
        boolean triggerItemStateOnClick =
                sharedPreferences.getBoolean(triggerItemStateOnClickKey, false);
        getAdapter().triggerItemStateOnClick(triggerItemStateOnClick);
    }

}
