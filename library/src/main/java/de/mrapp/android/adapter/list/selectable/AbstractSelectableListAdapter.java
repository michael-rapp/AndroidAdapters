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
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.view.View;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import de.mrapp.android.adapter.SelectableListDecorator;
import de.mrapp.android.adapter.datastructure.AppliedFilter;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.list.ListAdapterItemClickListener;
import de.mrapp.android.adapter.list.ListAdapterItemLongClickListener;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.filterable.AbstractFilterableListAdapter;
import de.mrapp.android.adapter.list.filterable.ListFilterListener;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.util.logging.LogLevel;

import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * An abstract base class for all adapters, whose underlying data is managed as a list of arbitrary
 * items, of which one or multiple items can be selected. Such an adapter's purpose is to provide
 * the underlying data for visualization using a {@link AbsListView} widget.
 *
 * @param <DataType>
 *         The type of the adapter's underlying data
 * @author Michael Rapp
 * @since 0.1.0
 */
public abstract class AbstractSelectableListAdapter<DataType>
        extends AbstractFilterableListAdapter<DataType, SelectableListDecorator<DataType>>
        implements SelectableListAdapter<DataType> {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The key, which is used to store, whether an item should be selected, when it is clicked by
     * the user, or not, within a bundle.
     */
    @VisibleForTesting
    protected static final String SELECT_ITEM_ON_CLICK_BUNDLE_KEY =
            AbstractSelectableListAdapter.class.getSimpleName() + "::SelectItemOnClick";

    /**
     * A set, which contains the listeners, which should be notified, when an item has been selected
     * or unselected.
     */
    private transient Set<ListSelectionListener<DataType>> selectionListeners;

    /**
     * True, if the an item should be selected, when it is clicked by the user, false otherwise.
     */
    private boolean selectItemOnClick;

    /**
     * Creates and returns a listener, which allows to adapt the unfiltered items, when an item has
     * been selected or unselected.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * ListSelectionListener}
     */
    private ListSelectionListener<DataType> createSelectionListener() {
        return new ListSelectionListener<DataType>() {

            @Override
            public void onItemSelected(@NonNull final SelectableListAdapter<DataType> adapter,
                                       @NonNull final DataType item, final int index) {
                if (isFiltered()) {
                    getUnfilteredItems().get(getUnfilteredIndex(index)).setSelected(true);
                }
            }

            @Override
            public void onItemUnselected(@NonNull final SelectableListAdapter<DataType> adapter,
                                         @NonNull final DataType item, final int index) {
                if (isFiltered()) {
                    getUnfilteredItems().get(getUnfilteredIndex(index)).setSelected(false);
                }
            }

        };
    }

    /**
     * Notifies all listeners, which have been registered to be notified when the selection of an
     * item of the adapter has been changed, about an item, which has been selected.
     *
     * @param item
     *         The item, which has been selected, as an instance of the generic type DataType. The
     *         item may not be null
     * @param index
     *         The index of the item, which has been selected, as an {@link Integer} value. The
     *         index must be between 0 and the value of the method <code>size():int</code> - 1
     */
    protected final void notifyOnItemSelected(@NonNull final DataType item, final int index) {
        for (ListSelectionListener<DataType> listener : selectionListeners) {
            listener.onItemSelected(this, item, index);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified when the selection of an
     * item of the adapter has been changed, about an item, which has been unselected.
     *
     * @param item
     *         The item, which has been unselected, as an instance of the generic type DataType. The
     *         item may not be null
     * @param index
     *         The index of the item, which has been unselected, as an {@link Integer} value. The
     *         index must be between 0 and the value of the method <code>size():int</code> - 1
     */
    protected final void notifyOnItemUnselected(@NonNull final DataType item, final int index) {
        for (ListSelectionListener<DataType> listener : selectionListeners) {
            listener.onItemUnselected(this, item, index);
        }
    }

    /**
     * Returns a set, which contains the listeners, which should be notified, when the selection of
     * an item of the adapter has been changed.
     *
     * @return A set, which contains the listeners, which should be notified when the selection of
     * an item of the adapter has been changed, as an instance of the type {@link Set} or an empty
     * set, if no listeners should be notified
     */
    protected final Set<ListSelectionListener<DataType>> getSelectionListeners() {
        return selectionListeners;
    }

    /**
     * Sets the set, which contains the listeners, which should be notified, when the selection of
     * an item of the adapter has been changed.
     *
     * @param selectionListeners
     *         The set, which should be set, as an instance of the type {@link Set} or an empty set,
     *         if no listeners should be notified
     */
    protected final void setSelectionListeners(
            @NonNull final Set<ListSelectionListener<DataType>> selectionListeners) {
        ensureNotNull(selectionListeners, "The selection listeners may not be null");
        this.selectionListeners = selectionListeners;
    }

    /**
     * Creates a new adapter, whose underlying data is managed as a list of arbitrary items, of
     * which one or multiple items can be selected.
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
    protected AbstractSelectableListAdapter(@NonNull final Context context,
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
                filterListeners, appliedFilters);
        selectItemOnClick(selectItemOnClick);
        setSelectionListeners(selectionListeners);
        addSelectionListener(createSelectionListener());
    }

    @CallSuper
    @Override
    protected void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SELECT_ITEM_ON_CLICK_BUNDLE_KEY, isItemSelectedOnClick());
    }

    @CallSuper
    @Override
    protected void onRestoreInstanceState(@NonNull final Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        selectItemOnClick = savedState.getBoolean(SELECT_ITEM_ON_CLICK_BUNDLE_KEY, true);
    }

    @Override
    protected final void applyDecorator(@NonNull final Context context, @NonNull final View view,
                                        final int index) {
        DataType item = getItem(index);
        boolean enabled = isEnabled(index);
        int itemState = getItemState(index);
        boolean filtered = isFiltered();
        boolean selected = isSelected(index);
        getDecorator()
                .applyDecorator(context, this, view, item, index, enabled, itemState, filtered,
                        selected);
        String message =
                "Applied decorator \"" + getDecorator() + "\" using arguments: Item=[" + item +
                        ", index=" + index + ", enabled=" + enabled + ", itemState=" + itemState +
                        ", filtered=" + filtered + "]";
        getLogger().logVerbose(getClass(), message);
    }

    @Override
    public final void addSelectionListener(
            @NonNull final ListSelectionListener<DataType> listener) {
        ensureNotNull(listener, "The listener may not be null");
        selectionListeners.add(listener);
        String message = "Added selection listener \"" + listener + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void removeSelectionListener(
            @NonNull final ListSelectionListener<DataType> listener) {
        ensureNotNull(listener, "The listener may not be null");
        selectionListeners.remove(listener);
        String message = "Removed selection listener \"" + listener + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final int getSelectedItemCount() {
        int result = 0;

        for (Item<DataType> item : getItems()) {
            if (item.isSelected()) {
                result++;
            }
        }

        return result;
    }

    @Override
    public final boolean isSelected(final int index) {
        return getItems().get(index).isSelected();
    }

    @Override
    public final boolean isSelected(@NonNull final DataType item) {
        return isSelected(indexOfOrThrowException(item));
    }

    @Override
    public final boolean isItemSelectedOnClick() {
        return selectItemOnClick;
    }

    @Override
    public final void selectItemOnClick(final boolean selectItemOnClick) {
        this.selectItemOnClick = selectItemOnClick;
        String message = "Items are now " + (selectItemOnClick ? "" : "not ") + "selected on click";
        getLogger().logDebug(getClass(), message);
    }

    @CallSuper
    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (selectItemOnClick ? 1231 : 1237);
        return result;
    }

    @CallSuper
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractSelectableListAdapter<?> other = (AbstractSelectableListAdapter<?>) obj;
        if (selectItemOnClick != other.selectItemOnClick)
            return false;
        return true;
    }

    @Override
    public abstract AbstractSelectableListAdapter<DataType> clone()
            throws CloneNotSupportedException;

}