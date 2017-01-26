/*
 * Copyright 2014 - 2017 Michael Rapp
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

import de.mrapp.android.adapter.AdapterFactory;
import de.mrapp.android.adapter.SingleChoiceListAdapter;
import de.mrapp.android.adapter.example.R;
import de.mrapp.android.adapter.example.decorator.SelectableListAdapterDecorator;
import de.mrapp.android.adapter.example.model.Contact;

/**
 * A fragment, which demonstrates the functionality of a {@link SingleChoiceListAdapter}.
 *
 * @author Michael Rapp
 */
public class SingleChoiceListAdapterFragment
        extends AbstractSelectableListAdapterFragment<SingleChoiceListAdapter<Contact>> {

    @Override
    protected final SingleChoiceListAdapter<Contact> createAdapter() {
        return AdapterFactory
                .createSingleChoiceListAdapter(getActivity(), new SelectableListAdapterDecorator());
    }

    @Override
    public final void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity());

        String allowDuplicatesKey =
                getString(R.string.single_choice_list_adapter_allow_duplicate_items_preference_key);
        boolean allowDuplicates = sharedPreferences.getBoolean(allowDuplicatesKey, false);
        getAdapter().allowDuplicates(allowDuplicates);

        String triggerItemStateOnClickKey = getString(
                R.string.single_choice_list_adapter_trigger_item_state_on_click_preference_key);
        boolean triggerItemStateOnClick =
                sharedPreferences.getBoolean(triggerItemStateOnClickKey, false);
        getAdapter().triggerItemStateOnClick(triggerItemStateOnClick);

        String selectItemOnClickKey = getString(
                R.string.single_choice_list_adapter_trigger_selection_on_click_preference_key);
        boolean selectItemOnClick = sharedPreferences.getBoolean(selectItemOnClickKey, true);
        getAdapter().selectItemOnClick(selectItemOnClick);
    }

}