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
import android.view.View;

import de.mrapp.android.adapter.AdapterFactory;
import de.mrapp.android.adapter.NoChoiceExpandableListAdapter;
import de.mrapp.android.adapter.example.R;
import de.mrapp.android.adapter.example.decorator.ExpandableListAdapterDecorator;
import de.mrapp.android.adapter.example.model.Contact;
import de.mrapp.android.adapter.example.model.Country;

/**
 * A fragment, which demonstrates the functionality of an {@link NoChoiceExpandableListAdapter}.
 *
 * @author Michael Rapp
 */
public class ExpandableListAdapterFragment extends
        AbstractExpandableListAdapterFragment<NoChoiceExpandableListAdapter<Country, Contact>> {

    @Override
    protected final NoChoiceExpandableListAdapter<Country, Contact> createAdapter() {
        return AdapterFactory
                .createExpandableListAdapter(getActivity(), new ExpandableListAdapterDecorator());
    }

    @Override
    public final void onStart() {
        super.onStart();
        getRemoveButton().setVisibility(View.GONE);

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity());

        String allowDuplicateGroupsKey =
                getString(R.string.expandable_list_adapter_allow_duplicate_groups_preference_key);
        boolean allowDuplicateGroups = sharedPreferences.getBoolean(allowDuplicateGroupsKey, false);
        getAdapter().allowDuplicateGroups(allowDuplicateGroups);

        String allowDuplicateChildrenKey =
                getString(R.string.expandable_list_adapter_allow_duplicate_children_preference_key);
        boolean allowDuplicateChildren =
                sharedPreferences.getBoolean(allowDuplicateChildrenKey, false);
        getAdapter().allowDuplicateChildren(allowDuplicateChildren);

        String triggerChildStateOnClickKey = getString(
                R.string.expandable_list_adapter_trigger_child_state_on_click_preference_key);
        boolean triggerChildStateOnClick =
                sharedPreferences.getBoolean(triggerChildStateOnClickKey, false);
        getAdapter().triggerChildStateOnClick(triggerChildStateOnClick);
    }

}