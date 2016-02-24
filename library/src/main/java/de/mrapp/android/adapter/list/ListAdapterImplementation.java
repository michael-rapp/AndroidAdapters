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
package de.mrapp.android.adapter.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import de.mrapp.android.adapter.ListDecorator;
import de.mrapp.android.adapter.datastructure.AppliedFilter;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.filterable.AbstractFilterableListAdapter;
import de.mrapp.android.adapter.list.filterable.ListFilterListener;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.adapter.logging.LogLevel;

/**
 * An adapter, whose underlying data is managed as a list of arbitrary items. Such an adapter's
 * purpose is to provide the underlying data for visualization using a {@link AbsListView} widget.
 *
 * @param <DataType>
 *         The type of the adapter's underlying data
 * @author Michael Rapp
 * @since 0.1.0
 */
public class ListAdapterImplementation<DataType>
        extends AbstractFilterableListAdapter<DataType, ListDecorator<DataType>> {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new adapter, whose underlying data is managed as a list of arbitrary items.
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
     */
    protected ListAdapterImplementation(@NonNull final Context context,
                                        @NonNull final ListDecorator<DataType> decorator,
                                        @NonNull final LogLevel logLevel,
                                        @NonNull final ArrayList<Item<DataType>> items,
                                        final boolean allowDuplicates, final boolean notifyOnChange,
                                        @NonNull final Set<ListAdapterItemClickListener<DataType>> itemClickListeners,
                                        @NonNull final Set<ListAdapterItemLongClickListener<DataType>> itemLongClickListeners,
                                        @NonNull final Set<ListAdapterListener<DataType>> adapterListeners,
                                        @NonNull final Set<ListEnableStateListener<DataType>> enableStateListeners,
                                        final int numberOfItemStates,
                                        final boolean triggerItemStateOnClick,
                                        @NonNull final Set<ListItemStateListener<DataType>> itemStateListeners,
                                        @NonNull final Set<ListSortingListener<DataType>> sortingListeners,
                                        @NonNull final Set<ListFilterListener<DataType>> filterListeners,
                                        @NonNull final LinkedHashSet<AppliedFilter<DataType>> appliedFilters) {
        super(context, decorator, logLevel, items, allowDuplicates, notifyOnChange,
                itemClickListeners, itemLongClickListeners, adapterListeners, enableStateListeners,
                numberOfItemStates, triggerItemStateOnClick, itemStateListeners, sortingListeners,
                filterListeners, appliedFilters);
    }

    /**
     * Creates a new adapter, whose underlying data is managed as a list of arbitrary items.
     *
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param decorator
     *         The decorator, which should be used to customize the appearance of the views, which
     *         are used to visualize the items of the adapter, as an instance of the type {@link
     *         ListDecorator}. The decorator may not be null
     */
    public ListAdapterImplementation(@NonNull final Context context,
                                     @NonNull final ListDecorator<DataType> decorator) {
        this(context, decorator, LogLevel.INFO, new ArrayList<Item<DataType>>(), false, true,
                new LinkedHashSet<ListAdapterItemClickListener<DataType>>(),
                new LinkedHashSet<ListAdapterItemLongClickListener<DataType>>(),
                new LinkedHashSet<ListAdapterListener<DataType>>(),
                new LinkedHashSet<ListEnableStateListener<DataType>>(), 1, false,
                new LinkedHashSet<ListItemStateListener<DataType>>(),
                new LinkedHashSet<ListSortingListener<DataType>>(),
                new LinkedHashSet<ListFilterListener<DataType>>(),
                new LinkedHashSet<AppliedFilter<DataType>>());
    }

    @Override
    protected final void applyDecorator(@NonNull final Context context, @NonNull final View view,
                                        final int index) {
        DataType item = getItem(index);
        boolean enabled = isEnabled(index);
        int itemState = getItemState(index);
        boolean filtered = isFiltered();
        getDecorator()
                .applyDecorator(context, this, view, item, index, enabled, itemState, filtered);
        String message =
                "Applied decorator \"" + getDecorator() + "\" using arguments: Item=[" + item +
                        ", index=" + index + ", enabled=" + enabled + ", itemState=" + itemState +
                        ", filtered=" + filtered + "]";
        getLogger().logVerbose(getClass(), message);
    }

    @Override
    public final int getViewTypeCount() {
        return getDecorator().getViewTypeCount();
    }

    @Override
    public final int getItemViewType(final int index) {
        return getDecorator().getViewType(getItem(index));
    }

    @Override
    public final String toString() {
        return "ListAdapter (" + getCount() + " items) [logLevel=" + getLogLevel() +
                ", parameters=" + getParameters() + ", notifyOnChange=" + isNotifiedOnChange() +
                ", allowDuplicates=" + areDuplicatesAllowed() + ", numberOfItemStates=" +
                getNumberOfItemStates() + ", triggerItemStateOnClick=" +
                isItemStateTriggeredOnClick() + ", filtered=" + isFiltered() + "]";
    }

    @Override
    public final ListAdapterImplementation<DataType> clone() throws CloneNotSupportedException {
        return new ListAdapterImplementation<>(getContext(), getDecorator(), getLogLevel(),
                cloneItems(), areDuplicatesAllowed(), isNotifiedOnChange(), getItemClickListeners(),
                getItemLongClickListeners(), getAdapterListeners(), getEnableStateListeners(),
                getNumberOfItemStates(), isItemStateTriggeredOnClick(), getItemStateListeners(),
                getSortingListeners(), getFilterListeners(), cloneAppliedFilters());
    }

}