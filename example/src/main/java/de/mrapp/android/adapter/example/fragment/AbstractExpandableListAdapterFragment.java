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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.MultipleChoiceExpandableListAdapter;
import de.mrapp.android.adapter.Order;
import de.mrapp.android.adapter.RestoreInstanceStateException;
import de.mrapp.android.adapter.SingleChoiceExpandableListAdapter;
import de.mrapp.android.adapter.example.R;
import de.mrapp.android.adapter.example.dialog.AddContactAsChildDialog;
import de.mrapp.android.adapter.example.model.Contact;
import de.mrapp.android.adapter.example.model.Country;
import de.mrapp.android.adapter.example.model.SampleData;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapter;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterListener;
import de.mrapp.android.adapter.expandablelist.enablestate.ExpandableListEnableStateListener;
import de.mrapp.android.adapter.expandablelist.filterable.ExpandableListFilterListener;

/**
 * An abstract base class for all fragments, which demonstrate the functionality of an {@link
 * ExpandableListAdapter}, {@link SingleChoiceExpandableListAdapter} or {@link
 * MultipleChoiceExpandableListAdapter}.
 *
 * @param <AdapterType>
 *         The type of the adapter, whose functionality is demonstrated
 * @author Michael Rapp
 */
public abstract class AbstractExpandableListAdapterFragment<AdapterType extends ExpandableListAdapter<Country, Contact>>
        extends AbstractAdapterFragment implements ExpandableListAdapterListener<Country, Contact>,
        ExpandableListEnableStateListener<Country, Contact>,
        ExpandableListFilterListener<Country, Contact> {

    /**
     * The name of the extra, which is used to save the state of the fragment's adapter, within a
     * bundle.
     */
    private static final String ADAPTER_STATE_EXTRA =
            AbstractExpandableListAdapterFragment.class.getSimpleName() + "::AdapterState";

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
                .setText(String.format(getString(R.string.item_count), adapter.getChildCount()));
    }

    /**
     * Updated the view, which shows the number of enabled items, which are currently contained by
     * the fragment's adapter.
     */
    private void updateEnabledItemCount() {
        enabledItemCountTextView.setText(String.format(getString(R.string.enabled_item_count),
                adapter.getEnabledChildCount()));
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
                adapter.sortGroups(order);
                adapter.sortChildren(order);

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
     * Creates and returns a listener, which allows to disable an item, when it is long-clicked.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * OnItemLongClickListener}
     */
    private OnItemLongClickListener createItemLongClickListener() {
        return new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view,
                                           final int position, final long id) {
                int groupIndex = ExpandableListView.getPackedPositionGroup(id);
                int childIndex = ExpandableListView.getPackedPositionChild(id);
                boolean enabled;

                if (childIndex != -1) {
                    adapter.triggerChildEnableState(groupIndex, childIndex);
                    enabled = adapter.isChildEnabled(groupIndex, childIndex);
                } else {
                    adapter.triggerGroupEnableState(groupIndex);
                    enabled = adapter.isGroupEnabled(groupIndex);
                }

                if (enabled) {
                    Toast.makeText(getActivity(), R.string.enabled_item_toast, Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(getActivity(), R.string.disabled_item_toast, Toast.LENGTH_SHORT)
                            .show();
                }

                return true;
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
                AddContactAsChildDialog.show(getActivity(), adapter);
            }

        };
    }

    /**
     * Adds groups and child items to a specific adapter.
     *
     * @param adapter
     *         The adapter, the items should be added to, as an instance of the generic type
     *         AdapterType
     */
    private void addItemsToAdapter(@NonNull final AdapterType adapter) {
        adapter.setNumberOfChildStates(2);
        adapter.addGroup(SampleData.COUNTRY_US);
        adapter.addAllChildren(0, SampleData.CONTACTS_US);
        adapter.addGroup(SampleData.COUNTRY_UK);
        adapter.addAllChildren(1, SampleData.CONTACTS_UK);
        adapter.addGroup(SampleData.COUNTRY_CA);
        adapter.addAllChildren(2, SampleData.CONTACTS_CA);
        adapter.addGroup(SampleData.COUNTRY_AU);
        adapter.addAllChildren(3, SampleData.CONTACTS_AU);
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
     * The method, which is invoked on subclasses to create the fragment's adapter.
     *
     * @return The adapter, which has been created, as an instance of the generic type AdapterType
     */
    protected abstract AdapterType createAdapter();

    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                   final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expandable_list_view, container, false);
        ExpandableListView expandableListView = view.findViewById(R.id.expandable_list_view);
        expandableListView.setGroupIndicator(null);
        adapter = createAdapter();
        adapter.attach(expandableListView);
        expandableListView.setLongClickable(true);
        expandableListView.setOnItemLongClickListener(createItemLongClickListener());

        ImageButton sortAscendingButton = view.findViewById(R.id.sort_ascending_button);
        sortAscendingButton.setOnClickListener(createSortingClickListener(Order.ASCENDING));

        ImageButton sortDescendingButton = view.findViewById(R.id.sort_descending_button);
        sortDescendingButton.setOnClickListener(createSortingClickListener(Order.DESCENDING));

        ImageButton addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(createAddClickListener());

        removeButton = view.findViewById(R.id.remove_button);

        selectedItemCountTextView = view.findViewById(R.id.selected_item_count_text_view);

        enabledItemCountTextView = view.findViewById(R.id.enabled_item_count_text_view);
        adapter.addEnableStateListener(this);

        itemCountTextView = view.findViewById(R.id.item_count_text_view);
        adapter.addAdapterListener(this);
        adapter.addFilterListener(this);

        if (savedInstanceState != null) {
            try {
                adapter.onRestoreInstanceState(savedInstanceState, ADAPTER_STATE_EXTRA);
            } catch (RestoreInstanceStateException e) {
                addItemsToAdapter(adapter);
            }
        } else {
            addItemsToAdapter(adapter);
        }

        return view;
    }

    @Override
    public void onGroupAdded(@NonNull final ExpandableListAdapter<Country, Contact> adapter,
                             @NonNull final Country group, final int index) {

    }

    @Override
    public void onGroupRemoved(@NonNull final ExpandableListAdapter<Country, Contact> adapter,
                               @NonNull final Country group, final int index) {

    }

    @Override
    public void onChildAdded(@NonNull final ExpandableListAdapter<Country, Contact> adapter,
                             @NonNull final Contact child, final int childIndex,
                             @NonNull final Country group, final int groupIndex) {
        updateItemCount();
        updateEnabledItemCount();
    }

    @Override
    public void onChildRemoved(@NonNull final ExpandableListAdapter<Country, Contact> adapter,
                               @NonNull final Contact child, final int childIndex,
                               @NonNull final Country group, final int groupIndex) {
        updateItemCount();
        updateEnabledItemCount();
    }

    @Override
    public void onGroupEnabled(@NonNull final ExpandableListAdapter<Country, Contact> adapter,
                               @NonNull final Country group, final int index) {

    }

    @Override
    public void onGroupDisabled(@NonNull final ExpandableListAdapter<Country, Contact> adapter,
                                @NonNull final Country group, final int index) {

    }

    @Override
    public void onChildEnabled(@NonNull final ExpandableListAdapter<Country, Contact> adapter,
                               @NonNull final Contact child, final int childIndex,
                               @NonNull final Country group, final int groupIndex) {
        updateEnabledItemCount();
    }

    @Override
    public void onChildDisabled(@NonNull final ExpandableListAdapter<Country, Contact> adapter,
                                @NonNull final Contact child, final int childIndex,
                                @NonNull final Country group, final int groupIndex) {
        updateEnabledItemCount();
    }

    @Override
    public void onApplyGroupFilter(@NonNull final ExpandableListAdapter<Country, Contact> adapter,
                                   @NonNull final String query, final int flags,
                                   final Filter<Country> filter,
                                   @NonNull final List<Country> filteredGroups,
                                   @NonNull final List<Country> unfilteredGroups) {
        updateItemCount();
        updateEnabledItemCount();
    }

    @Override
    public void onResetGroupFilter(@NonNull final ExpandableListAdapter<Country, Contact> adapter,
                                   @NonNull final String query, final int flags,
                                   @NonNull final List<Country> unfilteredGroups) {
        updateItemCount();
        updateEnabledItemCount();
    }

    @Override
    public void onApplyChildFilter(@NonNull final ExpandableListAdapter<Country, Contact> adapter,
                                   @NonNull final String query, final int flags,
                                   final Filter<Contact> filter, @NonNull final Country group,
                                   final int groupIndex,
                                   @NonNull final List<Contact> filteredChildren,
                                   @NonNull final List<Contact> unfilteredChildren) {
        updateItemCount();
        updateEnabledItemCount();
    }

    @Override
    public void onResetChildFilter(@NonNull final ExpandableListAdapter<Country, Contact> adapter,
                                   @NonNull final String query, final int flags,
                                   @NonNull final Country group, final int groupIndex,
                                   @NonNull final List<Contact> unfilteredChildren) {
        updateItemCount();
        updateEnabledItemCount();
    }

    @Override
    public final void search(final String query) {
        adapter.resetAllChildFilters();

        String[] queryStrings = query.split("\\s+");

        for (String queryString : queryStrings) {
            adapter.applyChildFilter(true, queryString, 0);
        }
    }

    @Override
    public final void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (adapter != null) {
            adapter.onSaveInstanceState(outState, ADAPTER_STATE_EXTRA);
        }
    }

}