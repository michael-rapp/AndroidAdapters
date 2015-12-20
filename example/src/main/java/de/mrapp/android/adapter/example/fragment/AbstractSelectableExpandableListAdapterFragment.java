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

import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.List;

import de.mrapp.android.adapter.ExpandableListAdapter;
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
            @NonNull final List<Country> filteredGroups) {
        super.onApplyGroupFilter(adapter, query, flags, filter, filteredGroups);
        updateSelectedItemCount();
    }

    @Override
    public final void onResetGroupFilter(
            @NonNull final ExpandableListAdapter<Country, Contact> adapter,
            @NonNull final String query, final int flags,
            @NonNull final List<Country> filteredGroups) {
        super.onResetGroupFilter(adapter, query, flags, filteredGroups);
        updateSelectedItemCount();
    }

    @Override
    public final void onApplyChildFilter(
            @NonNull final ExpandableListAdapter<Country, Contact> adapter,
            @NonNull final String query, final int flags, @NonNull final Filter<Contact> filter,
            @NonNull final Country group, final int groupIndex,
            @NonNull final List<Contact> filteredChildren) {
        super.onApplyChildFilter(adapter, query, flags, filter, group, groupIndex,
                filteredChildren);
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