/*
 * Copyright 2014 - 2018 Michael Rapp
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
import de.mrapp.android.adapter.ChoiceMode;
import de.mrapp.android.adapter.MultipleChoiceExpandableListAdapter;
import de.mrapp.android.adapter.example.R;
import de.mrapp.android.adapter.example.decorator.SelectableExpandableListAdapterDecorator;
import de.mrapp.android.adapter.example.model.Contact;
import de.mrapp.android.adapter.example.model.Country;

/**
 * A fragment, which demonstrates the functionality of a {@link MultipleChoiceExpandableListAdapter}.
 *
 * @author Michael Rapp
 */
public class MultipleChoiceExpandableListAdapterFragment extends
        AbstractSelectableExpandableListAdapterFragment<MultipleChoiceExpandableListAdapter<Country, Contact>> {

    @Override
    protected final MultipleChoiceExpandableListAdapter<Country, Contact> createAdapter() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        String choiceModeKey = getString(
                R.string.multiple_choice_expandable_list_adapter_choice_mode_preference_key);
        int choiceModeValue = Integer.valueOf(sharedPreferences.getString(choiceModeKey, "0"));
        ChoiceMode choiceMode = choiceModeValue == 0 ? ChoiceMode.GROUPS_AND_CHILDREN :
                (choiceModeValue == 1 ? ChoiceMode.GROUPS_ONLY : ChoiceMode.CHILDREN_ONLY);
        return AdapterFactory.createMultipleChoiceExpandableListAdapter(getActivity(),
                new SelectableExpandableListAdapterDecorator(), choiceMode);
    }

    @Override
    public final void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity());

        String allowDuplicateGroupsKey = getString(
                R.string.multiple_choice_expandable_list_adapter_allow_duplicate_groups_preference_key);
        boolean allowDuplicateGroups = sharedPreferences.getBoolean(allowDuplicateGroupsKey, false);
        getAdapter().allowDuplicateGroups(allowDuplicateGroups);

        String allowDuplicateChildrenKey = getString(
                R.string.multiple_choice_expandable_list_adapter_allow_duplicate_children_preference_key);
        boolean allowDuplicateChildren =
                sharedPreferences.getBoolean(allowDuplicateChildrenKey, false);
        getAdapter().allowDuplicateChildren(allowDuplicateChildren);

        String triggerChildStateOnClickKey = getString(
                R.string.multiple_choice_expandable_list_adapter_trigger_child_state_on_click_preference_key);
        boolean triggerChildStateOnClick =
                sharedPreferences.getBoolean(triggerChildStateOnClickKey, false);
        getAdapter().triggerChildStateOnClick(triggerChildStateOnClick);

        String triggerGroupSelectionOnClickKey = getString(
                R.string.multiple_choice_expandable_list_adapter_trigger_group_selection_on_click_preference_key);
        boolean triggerGroupSelectionOnClick =
                sharedPreferences.getBoolean(triggerGroupSelectionOnClickKey, true);
        getAdapter().selectGroupOnClick(triggerGroupSelectionOnClick);

        String triggerChildSelectionOnClickKey = getString(
                R.string.multiple_choice_expandable_list_adapter_trigger_child_selection_on_click_preference_key);
        boolean triggerChildSelectionOnClick =
                sharedPreferences.getBoolean(triggerChildSelectionOnClickKey, true);
        getAdapter().selectGroupOnClick(triggerChildSelectionOnClick);
    }

}