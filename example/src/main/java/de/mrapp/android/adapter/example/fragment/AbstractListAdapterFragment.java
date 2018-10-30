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

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.list.ListAdapter;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.Order;
import de.mrapp.android.adapter.SingleChoiceListAdapter;
import de.mrapp.android.adapter.example.R;
import de.mrapp.android.adapter.example.dialog.AddContactDialog;
import de.mrapp.android.adapter.example.model.Contact;
import de.mrapp.android.adapter.example.model.SampleData;
import de.mrapp.android.adapter.list.ListAdapterItemLongClickListener;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.filterable.ListFilterListener;

/**
 * An abstract base class for all fragments, which demonstrate the functionality of an {@link
 * ListAdapter}, {@link SingleChoiceListAdapter}, {@link MultipleChoiceListAdapter}.
 *
 * @param <AdapterType>
 *         The type of the adapter, whose functionality is demonstrated
 * @author Michael Rapp
 */
public abstract class AbstractListAdapterFragment<AdapterType extends ListAdapter<Contact>>
        extends AbstractAdapterFragment
        implements ListAdapterListener<Contact>, ListEnableStateListener<Contact>,
        ListFilterListener<Contact> {

    /**
     * The name of the extra, which is used to save the state of the fragment's adapter, within a
     * bundle.
     */
    private static final String ADAPTER_STATE_EXTRA =
            AbstractListAdapterFragment.class.getSimpleName() + "::AdapterState";

    /**
     * The fragment's adapter.
     */
    private AdapterType adapter;

    /**
     * The button, which allows to remove items from the fragment's adapter.
     */
    private ImageButton removeButton;

    /**
     * The view, which shows the number of items, which are currently contained by the fragment's
     * adapter.
     */
    private TextView itemCountTextView;

    /**
     * The view, which shows the number of enabled items, which are currently contained by the
     * fragment's adapter.
     */
    private TextView enabledItemCountTextView;

    /**
     * The view, which shows the number of selected items, which are currently contained by the
     * fragment's adapter.
     */
    private TextView selectedItemCountTextView;

    /**
     * Updates the view, which shows the number of items, which are currently contained by the
     * fragment's adapter.
     */
    private void updateItemCount() {
        itemCountTextView
                .setText(String.format(getString(R.string.item_count), adapter.getCount()));
    }

    /**
     * Updated the view, which shows the number of enabled items, which are currently contained by
     * the fragment's adapter.
     */
    private void updateEnabledItemCount() {
        enabledItemCountTextView.setText(String.format(getString(R.string.enabled_item_count),
                adapter.getEnabledItemCount()));
    }

    /**
     * Creates and returns a listener, which allows to disable an item, when it is long-clicked.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * ListAdapterItemLongClickListener}
     */
    private ListAdapterItemLongClickListener<Contact> createItemLongClickListener() {
        return new ListAdapterItemLongClickListener<Contact>() {

            @Override
            public boolean onItemLongClicked(@NonNull final ListAdapter<Contact> adapter,
                                             @NonNull final Contact item, final int index) {
                getAdapter().triggerEnableState(index);

                if (getAdapter().isEnabled(index)) {
                    Toast.makeText(getActivity(), R.string.enabled_item_toast, Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(getActivity(), R.string.disabled_item_toast, Toast.LENGTH_SHORT)
                            .show();
                }

                return true;
            }

            @Override
            public boolean onHeaderLongClicked(@NonNull final ListAdapter<Contact> adapter,
                                               @NonNull final View view, final int index) {
                return false;
            }

            @Override
            public boolean onFooterLongClicked(@NonNull final ListAdapter<Contact> adapter,
                                               @NonNull final View view, final int index) {
                return false;
            }

        };
    }

    /**
     * Creates and returns a listener, which allows to sort the fragment's adapter in a specific
     * order.
     *
     * @param order
     *         The order, which should be used for sorting, as a value of the enum {@link Order}
     * @return The listener, which has been created, as an instance of the type {@link
     * OnClickListener}
     */
    private OnClickListener createSortingClickListener(final Order order) {
        return new OnClickListener() {

            @Override
            public void onClick(final View v) {
                adapter.sort(order);

                if (order == Order.ASCENDING) {
                    Toast.makeText(getActivity(), R.string.sorted_ascending_toast,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), R.string.sorted_descending_toast,
                            Toast.LENGTH_SHORT).show();
                }
            }

        };
    }

    /**
     * Creates and returns a listener, which allows to show a dialog, which allows to add a new
     * item, when a button is clicked.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * OnClickListener}
     */
    private OnClickListener createAddClickListener() {
        return new OnClickListener() {

            @Override
            public void onClick(final View v) {
                AddContactDialog.show(getActivity(), adapter);
            }

        };
    }

    /**
     * Returns the fragment's adapter.
     *
     * @return The fragments adapter as an instance of the generic type AdapterType
     */
    protected final AdapterType getAdapter() {
        return adapter;
    }

    /**
     * Returns the button, which allows to remove items from the fragment's adapter.
     *
     * @return The button, which allows to remove items from the fragment's adapter, as an instance
     * of the class {@link ImageButton}
     */
    protected final ImageButton getRemoveButton() {
        return removeButton;
    }

    /**
     * Returns the view, which is used to show the number of selected items, which are contained by
     * the fragment's adapter.
     *
     * @return The view, which is used to show the number of selected items, which are contained by
     * the fragment's adapter, as an instance of the class {@link TextView}
     */
    protected final TextView getSelectedItemCountTextView() {
        return selectedItemCountTextView;
    }

    /**
     * The method, which is invoked on subclasses to inflate the fragment's layout.
     *
     * @param inflater
     *         The inflater, whic should be used, as an instance of the class {@link
     *         LayoutInflater}
     * @param container
     *         The root view of the fragment as an instance of the class {@link ViewGroup}
     * @return The view, which has been inflated, as an instance of the class {@link View}
     */
    protected abstract View inflateLayout(final LayoutInflater inflater, final ViewGroup container);

    /**
     * The method, which is invoked on subclasses to create the fragment's adapter.
     *
     * @return The adapter, which has been created, as an instance of the generic type AdapterType
     */
    protected abstract AdapterType createAdapter();

    /**
     * The method, which is invoked on subclasses to attach the fragment's adapter to the
     * corresponding view.
     *
     * @param rootView
     *         The root view of the fragment as an instance of the class {@link View}
     * @param adapter
     *         The adapter, which should be attached, as an instance of the generic type
     *         AdapterType
     */
    protected abstract void attachAdapter(final View rootView, final AdapterType adapter);

    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                   final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflateLayout(inflater, container);
        adapter = createAdapter();
        adapter.addItemLongClickListener(createItemLongClickListener());
        attachAdapter(view, adapter);

        ImageButton sortAscendingButton =
                (ImageButton) view.findViewById(R.id.sort_ascending_button);
        sortAscendingButton.setOnClickListener(createSortingClickListener(Order.ASCENDING));

        ImageButton sortDescendingButton =
                (ImageButton) view.findViewById(R.id.sort_descending_button);
        sortDescendingButton.setOnClickListener(createSortingClickListener(Order.DESCENDING));

        ImageButton addButton = (ImageButton) view.findViewById(R.id.add_button);
        addButton.setOnClickListener(createAddClickListener());

        removeButton = (ImageButton) view.findViewById(R.id.remove_button);

        selectedItemCountTextView =
                (TextView) view.findViewById(R.id.selected_item_count_text_view);

        enabledItemCountTextView = (TextView) view.findViewById(R.id.enabled_item_count_text_view);
        adapter.addEnableStateListener(this);

        itemCountTextView = (TextView) view.findViewById(R.id.item_count_text_view);
        adapter.addAdapterListener(this);
        adapter.addFilterListener(this);

        if (savedInstanceState != null) {
            adapter.onRestoreInstanceState(savedInstanceState, ADAPTER_STATE_EXTRA);
            updateItemCount();
            updateEnabledItemCount();
        } else {
            adapter.setNumberOfItemStates(2);
            adapter.addAllItems(SampleData.CONTACTS_US);
        }

        return view;
    }

    @Override
    public void onItemEnabled(@NonNull final ListAdapter<Contact> adapter,
                              @NonNull final Contact item, final int index) {
        updateEnabledItemCount();
    }

    @Override
    public void onItemDisabled(@NonNull final ListAdapter<Contact> adapter,
                               @NonNull final Contact item, final int index) {
        updateEnabledItemCount();
    }

    @Override
    public void onItemAdded(@NonNull final ListAdapter<Contact> adapter,
                            @NonNull final Contact item, final int index) {
        updateItemCount();
        updateEnabledItemCount();
    }

    @Override
    public void onItemRemoved(@NonNull final ListAdapter<Contact> adapter,
                              @NonNull final Contact item, final int index) {
        updateItemCount();
        updateEnabledItemCount();
    }

    @Override
    public void onApplyFilter(@NonNull final ListAdapter<Contact> adapter,
                              @NonNull final String query, final int flags,
                              final Filter<Contact> filter,
                              @NonNull final List<Contact> filteredItems,
                              @NonNull final List<Contact> unfilteredItems) {
        updateItemCount();
        updateEnabledItemCount();
    }

    @Override
    public void onResetFilter(@NonNull final ListAdapter<Contact> adapter,
                              @NonNull final String query, final int flags,
                              @NonNull final List<Contact> unfilteredItems) {
        updateItemCount();
        updateEnabledItemCount();
    }

    @Override
    public final void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (adapter != null) {
            adapter.onSaveInstanceState(outState, ADAPTER_STATE_EXTRA);
        }
    }

    @Override
    public final void search(final String query) {
        adapter.resetAllFilters();

        String[] queryStrings = query.split("\\s+");

        for (String queryString : queryStrings) {
            adapter.applyFilter(queryString, 0);
        }
    }

}