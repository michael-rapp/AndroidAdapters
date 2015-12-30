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
package de.mrapp.android.adapter.example.fragment;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import de.mrapp.android.adapter.AdapterFactory;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.example.R;
import de.mrapp.android.adapter.example.decorator.SelectableListAdapterDecorator;
import de.mrapp.android.adapter.example.model.Contact;

/**
 * A fragment, which demonstrates the functionality of a {@link MultipleChoiceListAdapter}.
 *
 * @author Michael Rapp
 */
public class MultipleChoiceListAdapterFragment
        extends AbstractSelectableListAdapterFragment<MultipleChoiceListAdapter<Contact>> {

    @Override
    protected final MultipleChoiceListAdapter<Contact> createAdapter() {
        return AdapterFactory.createMultipleChoiceListAdapter(getActivity(),
                new SelectableListAdapterDecorator());
    }

    @Override
    public final void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity());

        String allowDuplicatesKey = getString(
                R.string.multiple_choice_list_adapter_allow_duplicate_items_preference_key);
        boolean allowDuplicates = sharedPreferences.getBoolean(allowDuplicatesKey, false);
        getAdapter().allowDuplicates(allowDuplicates);

        String triggerItemStateOnClickKey = getString(
                R.string.multiple_choice_list_adapter_trigger_item_state_on_click_preference_key);
        boolean triggerItemStateOnClick =
                sharedPreferences.getBoolean(triggerItemStateOnClickKey, false);
        getAdapter().triggerItemStateOnClick(triggerItemStateOnClick);

        String selectItemOnClickKey = getString(
                R.string.multiple_choice_list_adapter_trigger_selection_on_click_preference_key);
        boolean selectItemOnClick = sharedPreferences.getBoolean(selectItemOnClickKey, true);
        getAdapter().selectItemOnClick(selectItemOnClick);
    }

}