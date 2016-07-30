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
package de.mrapp.android.adapter.list.selectable;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.view.View;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.SelectableListDecorator;
import de.mrapp.android.adapter.SingleChoiceListAdapter;
import de.mrapp.android.adapter.datastructure.AppliedFilter;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.list.ListAdapter;
import de.mrapp.android.adapter.list.ListAdapterItemClickListener;
import de.mrapp.android.adapter.list.ListAdapterItemLongClickListener;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.filterable.ListFilterListener;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.adapter.logging.LogLevel;

/**
 * An adapter, whose underlying data is managed as a list of arbitrary items, of which only item can
 * be selected at once. Such an adapter's purpose is to provide the underlying data for
 * visualization using a {@link AbsListView} widget.
 *
 * @param <DataType>
 *         The type of the adapter's underlying data
 * @author Michael Rapp
 * @since 0.1.0
 */
public class SingleChoiceListAdapterImplementation<DataType>
        extends AbstractSelectableListAdapter<DataType>
        implements SingleChoiceListAdapter<DataType> {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The key, which is used to store, whether the adapter's selection is adapted automatically, or
     * not, within a bundle.
     */
    @VisibleForTesting
    protected static final String ADAPT_SELECTION_AUTOMATICALLY_BUNDLE_KEY =
            SingleChoiceListAdapterImplementation.class.getSimpleName() +
                    "::AdaptSelectionAutomatically";

    /**
     * True, if the adapter's selection is adapted automatically, false otherwise.
     */
    private boolean adaptSelectionAutomatically;

    /**
     * Creates and returns a listener, which allows to triggerSelection an item, when it is clicked
     * by the user.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * ListAdapterItemClickListener}
     */
    private ListAdapterItemClickListener<DataType> createItemClickListener() {
        return new ListAdapterItemClickListener<DataType>() {

            @Override
            public void onItemClicked(@NonNull final ListAdapter<DataType> adapter,
                                      @NonNull final DataType item, final int index) {
                if (isItemSelectedOnClick()) {
                    getLogger().logVerbose(getClass(), "Selecting item on click...");
                    selectItem(index);
                }
            }

            @Override
            public void onHeaderClicked(@NonNull final ListAdapter<DataType> adapter,
                                        @NonNull final View view, final int index) {

            }

            @Override
            public void onFooterClicked(@NonNull final ListAdapter<DataType> adapter,
                                        @NonNull final View view, final int index) {

            }

        };
    }

    /**
     * Creates and returns a listener, which allows to adapt the selections of the adapter's items,
     * when an item has been removed from or added to the adapter.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * ListAdapterListener}
     */
    private ListAdapterListener<DataType> createAdapterListener() {
        return new ListAdapterListener<DataType>() {

            @Override
            public void onItemAdded(@NonNull final ListAdapter<DataType> adapter,
                                    @NonNull final DataType item, final int index) {
                if (isSelectionAdaptedAutomatically() && getCount() == 1) {
                    selectItem(index);
                }
            }

            @Override
            public void onItemRemoved(@NonNull final ListAdapter<DataType> adapter,
                                      @NonNull final DataType item, final int index) {
                if (isSelectionAdaptedAutomatically() && !isEmpty() && getSelectedIndex() == -1) {
                    selectNearestEnabledItem(index);
                }
            }

        };
    }

    /**
     * Creates and returns a listener, which allows to adapt the selections of the adapter's items,
     * when an item has been enabled or disabled.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * ListEnableStateListener}
     */
    private ListEnableStateListener<DataType> createEnableStateListener() {
        return new ListEnableStateListener<DataType>() {

            @Override
            public void onItemEnabled(@NonNull final ListAdapter<DataType> adapter,
                                      @NonNull final DataType item, final int index) {
                if (isSelectionAdaptedAutomatically() && getEnabledItemCount() == 1) {
                    selectItem(index);
                }
            }

            @Override
            public void onItemDisabled(@NonNull final ListAdapter<DataType> adapter,
                                       @NonNull final DataType item, final int index) {
                if (isSelectionAdaptedAutomatically() && isSelected(index)) {
                    getItems().get(index).setSelected(false);
                    notifyOnItemUnselected(item, index);
                    notifyOnDataSetChanged();
                    selectNearestEnabledItem(index);
                }
            }

        };
    }

    /**
     * Creates and returns a listener, which allows to adapt the selections of the adapter's items,
     * when an the adapter's underlying data has been filtered.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * ListFilterListener}
     */
    private ListFilterListener<DataType> createFilterListener() {
        return new ListFilterListener<DataType>() {

            @Override
            public void onApplyFilter(@NonNull final ListAdapter<DataType> adapter,
                                      @NonNull final String query, final int flags,
                                      @Nullable final Filter<DataType> filter,
                                      @NonNull final List<DataType> filteredItems,
                                      @NonNull final List<DataType> unfilteredItems) {
                if (isSelectionAdaptedAutomatically() && !isEmpty() && getSelectedIndex() == -1) {
                    selectItem(0);
                }
            }

            @Override
            public void onResetFilter(@NonNull final ListAdapter<DataType> adapter,
                                      @NonNull final String query, final int flags,
                                      @NonNull final List<DataType> unfilteredItems) {
                if (isSelectionAdaptedAutomatically() && !isEmpty() && getSelectedIndex() == -1) {
                    selectItem(0);
                }
            }
        };
    }

    /**
     * Selects the nearest enabled item, starting at a specific index. The item is searched
     * alternately by ascending and descending indices. If no enabled item is available, no item
     * will be selected.
     *
     * @param index
     *         The index, the search for the nearest enabled item should be started at, as an {@link
     *         Integer} value
     */
    private void selectNearestEnabledItem(final int index) {
        int ascendingIndex = index;
        int descendingIndex = index - 1;

        while (ascendingIndex < getCount() || descendingIndex >= 0) {
            if (ascendingIndex < getCount() && isEnabled(ascendingIndex)) {
                selectItem(ascendingIndex);
                return;
            } else if (descendingIndex >= 0 && isEnabled(descendingIndex)) {
                selectItem(descendingIndex);
                return;
            }

            ascendingIndex++;
            descendingIndex--;
        }
    }

    /**
     * Selects the item,which corresponds to a specific index. This causes all other items to become
     * unselected.
     *
     * @param index
     *         The index of the item, which should be selected, as an {@link Integer} value
     */
    private void selectItem(final int index) {
        Item<DataType> item = getItems().get(index);

        if (item.isEnabled()) {
            if (!item.isSelected()) {
                for (int i = 0; i < getCount(); i++) {
                    Item<DataType> currentItem = getItems().get(i);

                    if (i == index && !currentItem.isSelected()) {
                        currentItem.setSelected(true);
                        notifyOnItemSelected(currentItem.getData(), i);
                        String message =
                                "Selected item \"" + currentItem.getData() + "\" at index " + i;
                        getLogger().logInfo(getClass(), message);
                    } else if (i != index && currentItem.isSelected()) {
                        currentItem.setSelected(false);
                        notifyOnItemUnselected(currentItem.getData(), i);
                        String message =
                                "Unselected item \"" + currentItem.getData() + "\" at index " + i;
                        getLogger().logInfo(getClass(), message);
                    }
                }

                notifyOnDataSetChanged();
            }
        }
    }

    /**
     * Creates a new adapter, whose underlying data is managed as a list of arbitrary items, of
     * which only one item can be selected at once.
     *
     * @param context
     *         The context, the adapter should belong to, as an instance of the class {@link
     *         Context}. The context may not be null
     * @param decorator
     *         The decorator, which should be used to customize the appearance of the views, which
     *         are used to visualize the items of the adapter, as an instance of the generic type
     *         DecoratorType. The decorator may not be null
     * @param logLevel
     *         The log level, which should be used for logging, as a value of the enum {@link
     *         LogLevel}. The log level may not be null
     * @param items
     *         A list, which contains the the adapter's items, or an empty list, if the adapter
     *         should not contain any items
     * @param allowDuplicates
     *         True, if duplicate items should be allowed, false otherwise
     * @param notifyOnChange
     *         True, if the method <code>notifyDataSetChanged():void</code> should be automatically
     *         called when the adapter's underlying data has been changed, false otherwise
     * @param itemClickListeners
     *         A set, which contains the listeners, which should be notified, when an item of the
     *         adapter has been clicked by the user, as an instance of the type {@link Set} or an
     *         empty set, if no listeners should be notified
     * @param itemLongClickListeners
     *         A set, which contains the listeners, which should be notified, when an item of the
     *         adapter has been long-clicked by the user, as an instance of the type {@link Set} or
     *         an empty set, if no listeners should be notified
     * @param adapterListeners
     *         A set, which contains the listeners, which should be notified when the adapter's
     *         underlying data has been modified or an empty set, if no listeners should be
     *         notified
     * @param enableStateListeners
     *         A set, which contains the listeners, which should be notified when an item has been
     *         disabled or enabled or an empty set, if no listeners should be notified
     * @param numberOfItemStates
     *         The number of states, the adapter's items may have, as an {@link Integer} value. The
     *         value must be at least 1
     * @param triggerItemStateOnClick
     *         True, if the state of an item should be triggered, when it is clicked by the user,
     *         false otherwise
     * @param itemStateListeners
     *         A set, which contains the listeners, which should be notified, when the state of an
     *         item has been changed or an empty set, if no listeners should be notified
     * @param sortingListeners
     *         A set, which contains the listeners, which should be notified, when the adapter's
     *         underlying data has been sorted or an empty set, if no listeners should be notified
     * @param filterListeners
     *         A set, which contains the listeners, which should be notified, when the adapter's
     *         underlying data has been filtered or an empty set, if no listeners should be
     *         notified
     * @param appliedFilters
     *         A set, which contains the filters, which should be used to filter the adapter's
     *         underlying data or an empty set, if the adapter's underlying data should not be
     *         filtered
     * @param selectItemOnClick
     *         True, if an item should be selected, when it is clicked by the user, false otherwise
     * @param selectionListeners
     *         A set, which contains the listeners, which should be notified, when an item's
     *         selection has been changed or an empty set, if no listeners should be notified
     * @param adaptSelectionAutomatically
     *         True, if the adapter's selection should be adapted automatically, false otherwise
     */
    protected SingleChoiceListAdapterImplementation(@NonNull final Context context,
                                                    @NonNull final SelectableListDecorator<DataType> decorator,
                                                    @NonNull final LogLevel logLevel,
                                                    @NonNull final ArrayList<Item<DataType>> items,
                                                    final boolean allowDuplicates,
                                                    final boolean notifyOnChange,
                                                    @NonNull final Set<ListAdapterItemClickListener<DataType>> itemClickListeners,
                                                    @NonNull final Set<ListAdapterItemLongClickListener<DataType>> itemLongClickListeners,
                                                    @NonNull final Set<ListAdapterListener<DataType>> adapterListeners,
                                                    @NonNull final Set<ListEnableStateListener<DataType>> enableStateListeners,
                                                    final int numberOfItemStates,
                                                    final boolean triggerItemStateOnClick,
                                                    @NonNull final Set<ListItemStateListener<DataType>> itemStateListeners,
                                                    @NonNull final Set<ListSortingListener<DataType>> sortingListeners,
                                                    @NonNull final Set<ListFilterListener<DataType>> filterListeners,
                                                    @NonNull final LinkedHashSet<AppliedFilter<DataType>> appliedFilters,
                                                    final boolean selectItemOnClick,
                                                    @NonNull final Set<ListSelectionListener<DataType>> selectionListeners,
                                                    final boolean adaptSelectionAutomatically) {
        super(context, decorator, logLevel, items, allowDuplicates, notifyOnChange,
                itemClickListeners, itemLongClickListeners, adapterListeners, enableStateListeners,
                numberOfItemStates, triggerItemStateOnClick, itemStateListeners, sortingListeners,
                filterListeners, appliedFilters, selectItemOnClick, selectionListeners);
        addItemClickListener(createItemClickListener());
        addAdapterListener(createAdapterListener());
        addEnableStateListener(createEnableStateListener());
        addFilterListener(createFilterListener());
        adaptSelectionAutomatically(adaptSelectionAutomatically);
    }

    /**
     * Creates a new adapter, whose underlying data is managed as a list of arbitrary items, of
     * which only one item can be selected at once.
     *
     * @param context
     *         The context, the adapter should belong to, as an instance of the class {@link
     *         Context}. The context may not be null
     * @param decorator
     *         The decorator, which should be used to customize the appearance of the views, which
     *         are used to visualize the items of the adapter, as an instance of the generic type
     *         DecoratorType. The decorator may not be null
     */
    public SingleChoiceListAdapterImplementation(@NonNull final Context context,
                                                 @NonNull final SelectableListDecorator<DataType> decorator) {
        this(context, decorator, LogLevel.INFO, new ArrayList<Item<DataType>>(), false, true,
                new CopyOnWriteArraySet<ListAdapterItemClickListener<DataType>>(),
                new CopyOnWriteArraySet<ListAdapterItemLongClickListener<DataType>>(),
                new CopyOnWriteArraySet<ListAdapterListener<DataType>>(),
                new CopyOnWriteArraySet<ListEnableStateListener<DataType>>(), 1, false,
                new CopyOnWriteArraySet<ListItemStateListener<DataType>>(),
                new CopyOnWriteArraySet<ListSortingListener<DataType>>(),
                new CopyOnWriteArraySet<ListFilterListener<DataType>>(),
                new LinkedHashSet<AppliedFilter<DataType>>(), true,
                new CopyOnWriteArraySet<ListSelectionListener<DataType>>(), true);
    }

    @Override
    public final int getSelectedIndex() {
        for (int i = 0; i < getCount(); i++) {
            if (getItems().get(i).isSelected()) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final DataType getSelectedItem() {
        for (Item<DataType> item : getItems()) {
            if (item.isSelected()) {
                return item.getData();
            }
        }

        return null;
    }

    @Override
    public final boolean triggerSelection(final int index) {
        Item<DataType> item = getItems().get(index);

        if (item.isEnabled()) {
            if (!item.isSelected()) {
                selectItem(index);
                return true;
            } else {
                item.setSelected(false);
                notifyOnItemUnselected(item.getData(), index);
                String message = "Unselected item \"" + item.getData() + "\" at index " + index;
                getLogger().logDebug(getClass(), message);
                notifyOnDataSetChanged();
                return true;
            }
        } else {
            String message = "Item \"" + item.getData() + "\" at index " + index +
                    " not selected, because it is disabled";
            getLogger().logDebug(getClass(), message);
            return false;
        }
    }

    @Override
    public final boolean triggerSelection(@NonNull final DataType item) {
        return triggerSelection(indexOfOrThrowException(item));
    }

    @Override
    public final void adaptSelectionAutomatically(final boolean adaptSelectionAutomatically) {
        this.adaptSelectionAutomatically = adaptSelectionAutomatically;

        if (adaptSelectionAutomatically && !isEmpty() && getSelectedIndex() == -1) {
            selectNearestEnabledItem(0);
        }
    }

    @Override
    public final boolean isSelectionAdaptedAutomatically() {
        return adaptSelectionAutomatically;
    }

    @Override
    protected final void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ADAPT_SELECTION_AUTOMATICALLY_BUNDLE_KEY,
                isSelectionAdaptedAutomatically());
    }

    @Override
    protected final void onRestoreInstanceState(@NonNull final Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        adaptSelectionAutomatically =
                savedState.getBoolean(ADAPT_SELECTION_AUTOMATICALLY_BUNDLE_KEY, true);
    }

    @Override
    public final String toString() {
        return "SingleChoiceListAdapter (" + getCount() + " items) [logLevel=" + getLogLevel() +
                ", parameters=" + getParameters() + ", notifyOnChange=" + isNotifiedOnChange() +
                ", allowDuplicates=" + areDuplicatesAllowed() + ", numberOfItemStates=" +
                getNumberOfItemStates() + ", triggerItemStateOnClick=" +
                isItemStateTriggeredOnClick() + ", filtered=" + isFiltered() +
                ", selectItemOnClick=" + isItemSelectedOnClick() +
                ", adaptSelectionAutomatically=" + isSelectionAdaptedAutomatically() + "]";
    }

    @Override
    public final SingleChoiceListAdapterImplementation<DataType> clone()
            throws CloneNotSupportedException {
        return new SingleChoiceListAdapterImplementation<>(getContext(), getDecorator(),
                getLogLevel(), cloneItems(), areDuplicatesAllowed(), isNotifiedOnChange(),
                getItemClickListeners(), getItemLongClickListeners(), getAdapterListeners(),
                getEnableStateListeners(), getNumberOfItemStates(), isItemStateTriggeredOnClick(),
                getItemStateListeners(), getSortingListeners(), getFilterListeners(),
                cloneAppliedFilters(), isItemSelectedOnClick(), getSelectionListeners(),
                isSelectionAdaptedAutomatically());
    }

}