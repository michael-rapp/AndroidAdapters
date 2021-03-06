/*
 * Copyright 2014 - 2019 Michael Rapp
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

import androidx.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.List;

import de.mrapp.android.adapter.expandablelist.ExpandableListAdapter;
import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.MultipleChoiceExpandableListAdapter;
import de.mrapp.android.adapter.SingleChoiceExpandableListAdapter;
import de.mrapp.android.adapter.example.R;
import de.mrapp.android.adapter.example.dialog.RemoveContactOrCountryDialog;
import de.mrapp.android.adapter.example.model.Contact;
import de.mrapp.android.adapter.example.model.Country;
import de.mrapp.android.adapter.expandablelist.selectable.ExpandableListSelectionListener;
import de.mrapp.android.adapter.expandablelist.selectable.SelectableExpandableListAdapter;

/**
 * An abstract base class for all fragments, which demonstrate the functionality of a {@link
 * SingleChoiceExpandableListAdapter} or {@link MultipleChoiceExpandableListAdapter}.
 *
 * @param <AdapterType>
 * @author Michael Rapp
 */
public abstract class AbstractSelectableExpandableListAdapterFragment<AdapterType extends SelectableExpandableListAdapter<Country, Contact>>
        extends AbstractExpandableListAdapterFragment<AdapterType>
        implements ExpandableListSelectionListener<Country, Contact> {

    /**
     * Updates the view, which shows the number of selected items, which are currently contained by
     * the fragment's adapter.
     */
    private void updateSelectedItemCount() {
        getSelectedItemCountTextView().setText(
                String.format(getString(R.string.selected_item_count),
                        getAdapter().getSelectedChildCount() +
                                getAdapter().getSelectedGroupCount()));
    }

    /**
     * Creates and returns a listener, which allows to remove all selected items, when a button is
     * clicked.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * OnClickListener}
     */
    private OnClickListener createRemoveClickListener() {
        return new OnClickListener() {

            @Override
            public void onClick(final View v) {
                if (getAdapter().getSelectedGroupCount() != 0 ||
                        getAdapter().getSelectedChildCount() != 0) {
                    RemoveContactOrCountryDialog.show(getActivity(), getAdapter());
                } else {
                    Toast.makeText(getActivity(), R.string.no_items_selected, Toast.LENGTH_SHORT)
                            .show();
                }
            }

        };

    }

    @Override
    public void onStart() {
        super.onStart();
        getRemoveButton().setOnClickListener(createRemoveClickListener());
        getAdapter().addSelectionListener(this);
        updateSelectedItemCount();
    }

    @Override
    public final void onGroupAdded(@NonNull final ExpandableListAdapter<Country, Contact> adapter,
                                   @NonNull final Country group, final int index) {
        super.onGroupAdded(adapter, group, index);
        updateSelectedItemCount();
    }

    @Override
    public final void onGroupRemoved(@NonNull final ExpandableListAdapter<Country, Contact> adapter,
                                     @NonNull final Country group, final int index) {
        super.onGroupRemoved(adapter, group, index);
        updateSelectedItemCount();
    }

    @Override
    public final void onChildAdded(@NonNull final ExpandableListAdapter<Country, Contact> adapter,
                                   @NonNull final Contact child, final int childIndex,
                                   @NonNull final Country group, final int groupIndex) {
        super.onChildAdded(adapter, child, childIndex, group, groupIndex);
        updateSelectedItemCount();
    }

    @Override
    public final void onChildRemoved(@NonNull final ExpandableListAdapter<Country, Contact> adapter,
                                     @NonNull final Contact child, final int childIndex,
                                     @NonNull final Country group, final int groupIndex) {
        super.onChildRemoved(adapter, child, childIndex, group, groupIndex);
        updateSelectedItemCount();
    }

    @Override
    public final void onGroupEnabled(@NonNull final ExpandableListAdapter<Country, Contact> adapter,
                                     @NonNull final Country group, final int index) {
        super.onGroupEnabled(adapter, group, index);
        updateSelectedItemCount();
    }

    @Override
    public final void onGroupDisabled(
            @NonNull final ExpandableListAdapter<Country, Contact> adapter,
            @NonNull final Country group, final int index) {
        super.onGroupDisabled(adapter, group, index);
        updateSelectedItemCount();
    }

    @Override
    public final void onChildEnabled(@NonNull final ExpandableListAdapter<Country, Contact> adapter,
                                     @NonNull final Contact child, final int childIndex,
                                     @NonNull final Country group, final int groupIndex) {
        super.onChildEnabled(adapter, child, childIndex, group, groupIndex);
        updateSelectedItemCount();
    }

    @Override
    public final void onChildDisabled(
            @NonNull final ExpandableListAdapter<Country, Contact> adapter,
            @NonNull final Contact child, final int childIndex, @NonNull final Country group,
            final int groupIndex) {
        super.onChildDisabled(adapter, child, childIndex, group, groupIndex);
        updateSelectedItemCount();
    }

    @Override
    public final void onGroupSelected(
            @NonNull final SelectableExpandableListAdapter<Country, Contact> adapter,
            @NonNull final Country group, final int index) {
        updateSelectedItemCount();
    }

    @Override
    public final void onGroupUnselected(
            @NonNull final SelectableExpandableListAdapter<Country, Contact> adapter,
            @NonNull final Country group, final int index) {
        updateSelectedItemCount();
    }

    @Override
    public final void onChildSelected(
            @NonNull final SelectableExpandableListAdapter<Country, Contact> adapter,
            @NonNull final Contact child, final int childIndex, @NonNull final Country group,
            final int groupIndex) {
        updateSelectedItemCount();
    }

    @Override
    public final void onChildUnselected(
            @NonNull final SelectableExpandableListAdapter<Country, Contact> adapter,
            @NonNull final Contact child, final int childIndex, @NonNull final Country group,
            final int groupIndex) {
        updateSelectedItemCount();
    }

    @Override
    public final void onApplyGroupFilter(
            @NonNull final ExpandableListAdapter<Country, Contact> adapter,
            @NonNull final String query, final int flags, final Filter<Country> filter,
            @NonNull final List<Country> filteredGroups,
            @NonNull final List<Country> unfilteredGroups) {
        super.onApplyGroupFilter(adapter, query, flags, filter, filteredGroups, unfilteredGroups);
        updateSelectedItemCount();
    }

    @Override
    public final void onResetGroupFilter(
            @NonNull final ExpandableListAdapter<Country, Contact> adapter,
            @NonNull final String query, final int flags,
            @NonNull final List<Country> unfilteredGroups) {
        super.onResetGroupFilter(adapter, query, flags, unfilteredGroups);
        updateSelectedItemCount();
    }

    @Override
    public final void onApplyChildFilter(
            @NonNull final ExpandableListAdapter<Country, Contact> adapter,
            @NonNull final String query, final int flags, @NonNull final Filter<Contact> filter,
            @NonNull final Country group, final int groupIndex,
            @NonNull final List<Contact> filteredChildren,
            @NonNull final List<Contact> unfilteredChildren) {
        super.onApplyChildFilter(adapter, query, flags, filter, group, groupIndex, filteredChildren,
                unfilteredChildren);
        updateSelectedItemCount();
    }

    @Override
    public final void onResetChildFilter(
            @NonNull final ExpandableListAdapter<Country, Contact> adapter,
            @NonNull final String query, final int flags, @NonNull final Country group,
            final int groupIndex, @NonNull final List<Contact> unfilteredChildren) {
        super.onResetChildFilter(adapter, query, flags, group, groupIndex, unfilteredChildren);
        updateSelectedItemCount();
    }

}