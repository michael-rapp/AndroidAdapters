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

import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.List;

import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.ListAdapter;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.SingleChoiceListAdapter;
import de.mrapp.android.adapter.example.R;
import de.mrapp.android.adapter.example.dialog.RemoveContactDialog;
import de.mrapp.android.adapter.example.model.Contact;
import de.mrapp.android.adapter.list.selectable.ListSelectionListener;
import de.mrapp.android.adapter.list.selectable.SelectableListAdapter;

/**
 * An abstract base class for all fragments, which demonstrate the functionality of a {@link
 * SingleChoiceListAdapter} or {@link MultipleChoiceListAdapter}.
 *
 * @param <AdapterType>
 * @author Michael Rapp
 */
public abstract class AbstractSelectableListAdapterFragment<AdapterType extends SelectableListAdapter<Contact>>
        extends AbstractListViewFragment<AdapterType> implements ListSelectionListener<Contact> {

    /**
     * Updates the view, which shows the number of selected items, which are currently contained by
     * the fragment's adapter.
     */
    private void updateSelectedItemCount() {
        getSelectedItemCountTextView().setText(
                String.format(getString(R.string.selected_item_count),
                        getAdapter().getSelectedItemCount()));
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
                if (getAdapter().getSelectedItemCount() > 0) {
                    RemoveContactDialog.show(getActivity(), getAdapter());
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
    public final void onItemSelected(@NonNull final SelectableListAdapter<Contact> adapter,
                                     @NonNull final Contact item, final int index) {
        updateSelectedItemCount();
    }

    @Override
    public final void onItemUnselected(@NonNull final SelectableListAdapter<Contact> adapter,
                                       @NonNull final Contact item, final int index) {
        updateSelectedItemCount();
    }

    @Override
    public final void onItemEnabled(@NonNull final ListAdapter<Contact> adapter,
                                    @NonNull final Contact item, final int index) {
        super.onItemEnabled(adapter, item, index);
        updateSelectedItemCount();
    }

    @Override
    public final void onItemDisabled(@NonNull final ListAdapter<Contact> adapter,
                                     @NonNull final Contact item, final int index) {
        super.onItemDisabled(adapter, item, index);
        updateSelectedItemCount();
    }

    @Override
    public final void onItemAdded(@NonNull final ListAdapter<Contact> adapter,
                                  @NonNull final Contact item, final int index) {
        super.onItemAdded(adapter, item, index);
        updateSelectedItemCount();
    }

    @Override
    public final void onItemRemoved(@NonNull final ListAdapter<Contact> adapter,
                                    @NonNull final Contact item, final int index) {
        super.onItemRemoved(adapter, item, index);
        updateSelectedItemCount();
    }

    @Override
    public final void onApplyFilter(@NonNull final ListAdapter<Contact> adapter,
                                    @NonNull final String query, final int flags,
                                    final Filter<Contact> filter,
                                    @NonNull final List<Contact> filteredItems,
                                    @NonNull final List<Contact> unfilteredItems) {
        super.onApplyFilter(adapter, query, flags, filter, filteredItems, unfilteredItems);
        updateSelectedItemCount();
    }

    @Override
    public final void onResetFilter(@NonNull final ListAdapter<Contact> adapter,
                                    @NonNull final String query, final int flags,
                                    @NonNull final List<Contact> unfilteredItems) {
        super.onResetFilter(adapter, query, flags, unfilteredItems);
        updateSelectedItemCount();
    }

}