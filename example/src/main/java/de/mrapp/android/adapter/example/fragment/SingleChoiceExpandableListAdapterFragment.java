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
package de.mrapp.android.adapter.example.fragment;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import de.mrapp.android.adapter.AdapterFactory;
import de.mrapp.android.adapter.ChoiceMode;
import de.mrapp.android.adapter.SingleChoiceExpandableListAdapter;
import de.mrapp.android.adapter.example.R;
import de.mrapp.android.adapter.example.decorator.SelectableExpandableListAdapterDecorator;
import de.mrapp.android.adapter.example.model.Contact;
import de.mrapp.android.adapter.example.model.Country;

/**
 * A fragment, which demonstrates the functionality of a {@link SingleChoiceExpandableListAdapter}.
 *
 * @author Michael Rapp
 */
public class SingleChoiceExpandableListAdapterFragment extends
        AbstractSelectableExpandableListAdapterFragment<SingleChoiceExpandableListAdapter<Country, Contact>> {

    @Override
    protected final SingleChoiceExpandableListAdapter<Country, Contact> createAdapter() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        String choiceModeKey = getString(
                R.string.single_choice_expandable_list_adapter_choice_mode_preference_key);
        int choiceModeValue = Integer.valueOf(sharedPreferences.getString(choiceModeKey, "0"));
        ChoiceMode choiceMode = choiceModeValue == 0 ? ChoiceMode.GROUPS_AND_CHILDREN :
                (choiceModeValue == 1 ? ChoiceMode.GROUPS_ONLY : ChoiceMode.CHILDREN_ONLY);
        return AdapterFactory.createSingleChoiceExpandableListAdapter(getActivity(),
                new SelectableExpandableListAdapterDecorator(), choiceMode);
    }

    @Override
    public final void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity());

        String allowDuplicateGroupsKey = getString(
                R.string.single_choice_expandable_list_adapter_allow_duplicate_groups_preference_key);
        boolean allowDuplicateGroups = sharedPreferences.getBoolean(allowDuplicateGroupsKey, false);
        getAdapter().allowDuplicateGroups(allowDuplicateGroups);

        String allowDuplicateChildrenKey = getString(
                R.string.single_choice_expandable_list_adapter_allow_duplicate_children_preference_key);
        boolean allowDuplicateChildren =
                sharedPreferences.getBoolean(allowDuplicateChildrenKey, false);
        getAdapter().allowDuplicateChildren(allowDuplicateChildren);

        String triggerChildStateOnClickKey = getString(
                R.string.single_choice_expandable_list_adapter_trigger_child_state_on_click_preference_key);
        boolean triggerChildStateOnClick =
                sharedPreferences.getBoolean(triggerChildStateOnClickKey, false);
        getAdapter().triggerChildStateOnClick(triggerChildStateOnClick);

        String triggerGroupSelectionOnClickKey = getString(
                R.string.single_choice_expandable_list_adapter_trigger_group_selection_on_click_preference_key);
        boolean triggerGroupSelectionOnClick =
                sharedPreferences.getBoolean(triggerGroupSelectionOnClickKey, true);
        getAdapter().selectGroupOnClick(triggerGroupSelectionOnClick);

        String triggerChildSelectionOnClickKey = getString(
                R.string.single_choice_expandable_list_adapter_trigger_child_selection_on_click_preference_key);
        boolean triggerChildSelectionOnClick =
                sharedPreferences.getBoolean(triggerChildSelectionOnClickKey, true);
        getAdapter().selectGroupOnClick(triggerChildSelectionOnClick);
    }

}