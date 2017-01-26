/*
 * Copyright 2014 - 2017 Michael Rapp
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
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.SelectableListDecorator;
import de.mrapp.android.adapter.datastructure.AppliedFilter;
import de.mrapp.android.adapter.datastructure.UnmodifiableList;
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
 * An adapter, whose underlying data is managed as a list of arbitrary items, of which multiple
 * items can be selected at once. Such an adapter's purpose is to provide the underlying data for
 * visualization using a {@link AbsListView} widget.
 *
 * @param <DataType>
 *         The type of the adapter's underlying data
 * @author Michael Rapp
 * @since 0.1.0
 */
public class MultipleChoiceListAdapterImplementation<DataType>
        extends AbstractSelectableListAdapter<DataType>
        implements MultipleChoiceListAdapter<DataType> {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates and returns a listener, which allows to trigger the selection of an item, when it is
     * clicked by the user.
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
                    getLogger().logVerbose(getClass(), "Triggering item selection on click...");
                    triggerSelection(index);
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

            }

            @Override
            public void onItemDisabled(@NonNull final ListAdapter<DataType> adapter,
                                       @NonNull final DataType item, final int index) {
                getItems().get(index).setSelected(false);
                notifyOnItemUnselected(item, index);
                notifyObserversOnItemChanged(index);
            }

        };
    }

    /**
     * Creates a new adapter, whose underlying data is managed as a list of arbitrary items, of
     * which multiple items can be selected at once.
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
     */
    protected MultipleChoiceListAdapterImplementation(@NonNull final Context context,
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
                                                      @NonNull final Set<ListSelectionListener<DataType>> selectionListeners) {
        super(context, decorator, logLevel, items, allowDuplicates, notifyOnChange,
                itemClickListeners, itemLongClickListeners, adapterListeners, enableStateListeners,
                numberOfItemStates, triggerItemStateOnClick, itemStateListeners, sortingListeners,
                filterListeners, appliedFilters, selectItemOnClick, selectionListeners);
        addItemClickListener(createItemClickListener());
        addEnableStateListener(createEnableStateListener());
    }

    /**
     * Creates a new adapter, whose underlying data is managed as a list of arbitrary items, of
     * which multiple items can be selected at once.
     *
     * @param context
     *         The context, the adapter should belong to, as an instance of the class {@link
     *         Context}. The context may not be null
     * @param decorator
     *         The decorator, which should be used to customize the appearance of the views, which
     *         are used to visualize the items of the adapter, as an instance of the generic type
     *         DecoratorType. The decorator may not be null
     */
    public MultipleChoiceListAdapterImplementation(@NonNull final Context context,
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
                new CopyOnWriteArraySet<ListSelectionListener<DataType>>());
    }

    @Override
    public final int getFirstSelectedIndex() {
        for (int i = 0; i < getCount(); i++) {
            if (isSelected(i)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final DataType getFirstSelectedItem() {
        int index = getFirstSelectedIndex();

        if (index != -1) {
            return getItem(index);
        }

        return null;
    }

    @Override
    public final int getLastSelectedIndex() {
        for (int i = getCount() - 1; i >= 0; i--) {
            if (isSelected(i)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final DataType getLastSelectedItem() {
        int index = getLastSelectedIndex();

        if (index != -1) {
            return getItem(index);
        }

        return null;
    }

    @Override
    public final int getFirstUnselectedIndex() {
        for (int i = 0; i < getCount(); i++) {
            if (!isSelected(i)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final DataType getFirstUnselectedItem() {
        int index = getFirstUnselectedIndex();

        if (index != -1) {
            return getItem(index);
        }

        return null;
    }

    @Override
    public final int getLastUnselectedIndex() {
        for (int i = getCount() - 1; i >= 0; i--) {
            if (!isSelected(i)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final DataType getLastUnselectedItem() {
        int index = getLastUnselectedIndex();

        if (index != -1) {
            return getItem(index);
        }

        return null;
    }

    @Override
    public final List<Integer> getSelectedIndices() {
        List<Integer> selectedIndices = new ArrayList<>();

        for (int i = 0; i < getCount(); i++) {
            if (isSelected(i)) {
                selectedIndices.add(i);
            }
        }

        return new UnmodifiableList<>(selectedIndices);
    }

    @Override
    public final List<DataType> getSelectedItems() {
        List<DataType> selectedItems = new ArrayList<>();

        for (Item<DataType> item : getItems()) {
            if (item.isSelected()) {
                selectedItems.add(item.getData());
            }
        }

        return new UnmodifiableList<>(selectedItems);
    }

    @Override
    public final List<Integer> getUnselectedIndices() {
        List<Integer> unselectedIndices = new ArrayList<>();

        for (int i = 0; i < getCount(); i++) {
            if (!isSelected(i)) {
                unselectedIndices.add(i);
            }
        }

        return new UnmodifiableList<>(unselectedIndices);
    }

    @Override
    public final List<DataType> getUnselectedItems() {
        List<DataType> unselectedItems = new ArrayList<>();

        for (Item<DataType> item : getItems()) {
            if (!item.isSelected()) {
                unselectedItems.add(item.getData());
            }
        }

        return new UnmodifiableList<>(unselectedItems);
    }

    @Override
    public final boolean setSelected(final int index, final boolean selected) {
        Item<DataType> item = getItems().get(index);

        if (item.isEnabled()) {
            if (item.isSelected() != selected) {
                item.setSelected(selected);

                if (selected) {
                    notifyOnItemSelected(item.getData(), index);
                } else {
                    notifyOnItemUnselected(item.getData(), index);
                }

                notifyObserversOnItemChanged(index);
                String message = selected ? "Selected" :
                        "Unselected" + " item \"" + item + "\" at index " + index;
                getLogger().logInfo(getClass(), message);
                return true;
            } else {
                String message =
                        "The selection of item \"" + item.getData() + " at index " + index +
                                " has not been changed, because it is already " +
                                (selected ? "selected" : "unselected");
                getLogger().logDebug(getClass(), message);
                return false;
            }
        } else {
            String message = "Item \"" + item.getData() + " at index " + index +
                    " not selected, because it is disabled";
            getLogger().logDebug(getClass(), message);
            return false;
        }
    }

    @Override
    public final boolean setSelected(@NonNull final DataType item, final boolean selected) {
        return setSelected(indexOfOrThrowException(item), selected);
    }

    @Override
    public final boolean triggerSelection(final int index) {
        if (isSelected(index)) {
            return setSelected(index, false);
        } else {
            return setSelected(index, true);
        }
    }

    @Override
    public final boolean triggerSelection(@NonNull final DataType item) {
        return triggerSelection(indexOfOrThrowException(item));
    }

    @Override
    public final boolean setAllSelected(final boolean selected) {
        boolean result = true;

        for (int i = 0; i < getCount(); i++) {
            result &= setSelected(i, selected);
        }

        return result;
    }

    @Override
    public final boolean triggerAllSelections() {
        boolean result = true;

        for (int i = 0; i < getCount(); i++) {
            result &= triggerSelection(i);
        }

        return result;
    }

    @Override
    public final String toString() {
        return "MultipleChoiceListAdapter (" + getCount() + " items) [logLevel=" + getLogLevel() +
                ", parameters=" + getParameters() + ", notifyOnChange=" + isNotifiedOnChange() +
                ", allowDuplicates=" + areDuplicatesAllowed() + ", numberOfItemStates=" +
                getNumberOfItemStates() + ", triggerItemStateOnClick=" +
                isItemStateTriggeredOnClick() + ", filtered=" + isFiltered() +
                ", selectItemOnClick=" + isItemSelectedOnClick() + "]";
    }

    @Override
    public final MultipleChoiceListAdapterImplementation<DataType> clone()
            throws CloneNotSupportedException {
        return new MultipleChoiceListAdapterImplementation<>(getContext(), getDecorator(),
                getLogLevel(), cloneItems(), areDuplicatesAllowed(), isNotifiedOnChange(),
                getItemClickListeners(), getItemLongClickListeners(), getAdapterListeners(),
                getEnableStateListeners(), getNumberOfItemStates(), isItemStateTriggeredOnClick(),
                getItemStateListeners(), getSortingListeners(), getFilterListeners(),
                cloneAppliedFilters(), isItemSelectedOnClick(), getSelectionListeners());
    }

}