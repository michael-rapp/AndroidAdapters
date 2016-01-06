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
package de.mrapp.android.adapter.list.sortable;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import de.mrapp.android.adapter.ListAdapter;
import de.mrapp.android.adapter.Order;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.datastructure.item.ItemComparator;
import de.mrapp.android.adapter.decorator.AbstractListDecorator;
import de.mrapp.android.adapter.list.ListAdapterItemClickListener;
import de.mrapp.android.adapter.list.ListAdapterItemLongClickListener;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.itemstate.AbstractItemStateListAdapter;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
import de.mrapp.android.adapter.logging.LogLevel;

import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * An abstract base class for all adapters, whose underlying data is managed as a sortable list of
 * arbitrary items. Such an adapter's purpose is to provide the underlying data for visualization
 * using a {@link AbsListView} widget.
 *
 * @param <DataType>
 *         The type of the adapter's underlying data
 * @param <DecoratorType>
 *         The type of the decorator, which allows to customize the appearance of the views, which
 *         are used to visualize the items of the adapter
 * @author Michael Rapp
 * @since 0.1.0
 */
public abstract class AbstractSortableListAdapter<DataType, DecoratorType extends AbstractListDecorator<DataType>>
        extends AbstractItemStateListAdapter<DataType, DecoratorType>
        implements SortableListAdapter<DataType> {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The key, which is used to store the current order of the adapter's items within a bundle.
     */
    @VisibleForTesting
    protected static final String ORDER_BUNDLE_KEY =
            AbstractSortableListAdapter.class.getSimpleName() + "::Order";

    /**
     * The current order of the adapter's items.
     */
    private Order order;

    /**
     * A set, which contains the listeners, which should be notified, when the adapter's underlying
     * data has been sorted.
     */
    private transient Set<ListSortingListener<DataType>> sortingListeners;

    /**
     * Notifies all listeners, which have been registered to be notified, when the adapter's
     * underlying data has been sorted.
     *
     * @param sortedItems
     *         A collection, which contains the adapter's sorted items, as an instance of the type
     *         {@link Collection} or an empty collection, if the adapter does not contain any items
     * @param order
     *         The order, which has been used to sort the adapter's items, as a value of the enum
     *         {@link Order}. The order may either be <code>ASCENDING</code> or
     *         <code>DESCENDING</code>
     * @param comparator
     *         The comparator, which has been used to compare the single items, as an instance of
     *         the type {@link Comparator} or null, if the items' implementation of the type {@link
     *         Comparable} has been used instead
     */
    private void notifyOnSorted(@NonNull final Collection<DataType> sortedItems,
                                @NonNull final Order order,
                                @Nullable final Comparator<DataType> comparator) {
        for (ListSortingListener<DataType> listener : sortingListeners) {
            listener.onSorted(this, sortedItems, order, comparator);
        }
    }

    /**
     * Creates and returns a listener, which allows to invalidate the current order of the adapter's
     * items, when its underlying data is changed.
     *
     * @return The listener, which has been created, as an instance of the type {@link ListAdapter}
     */
    private ListAdapterListener<DataType> createAdapterListener() {
        return new ListAdapterListener<DataType>() {

            @Override
            public void onItemAdded(@NonNull final ListAdapter<DataType> adapter,
                                    @NonNull final DataType item, final int index) {
                order = null;
            }

            @Override
            public void onItemRemoved(@NonNull final ListAdapter<DataType> adapter,
                                      @NonNull final DataType item, final int index) {
                order = null;
            }

        };
    }

    /**
     * Returns a set, which contains the listeners, which should be notified, when the adapter's
     * underlying data has been sorted.
     *
     * @return A set, which contains the listeners, which should be notified, when the adapter's
     * underlying data has been filtered, as an instance of the type {@link Set} or an empty set, if
     * no listeners should be notified
     */
    protected final Set<ListSortingListener<DataType>> getSortingListeners() {
        return sortingListeners;
    }

    /**
     * Sets the set, which contains the listeners, which should be notified, when the adapter's
     * underlying data has been sorted.
     *
     * @param sortingListeners
     *         The set, which should be set, as an instance of the type {@link Set} or an empty set,
     *         if no listeners should be notified
     */
    protected final void setSortingListeners(
            @NonNull final Set<ListSortingListener<DataType>> sortingListeners) {
        ensureNotNull(sortingListeners, "The sorting listeners may not be null");
        this.sortingListeners = sortingListeners;
    }

    /**
     * Creates a new adapter, whose underlying data is managed as a sortable list of arbitrary
     * items.
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
     */
    protected AbstractSortableListAdapter(@NonNull final Context context,
                                          @NonNull final DecoratorType decorator,
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
                                          @NonNull final Set<ListSortingListener<DataType>> sortingListeners) {
        super(context, decorator, logLevel, items, allowDuplicates, notifyOnChange,
                itemClickListeners, itemLongClickListeners, adapterListeners, enableStateListeners,
                numberOfItemStates, triggerItemStateOnClick, itemStateListeners);
        this.order = null;
        setSortingListeners(sortingListeners);
        addAdapterListener(createAdapterListener());
    }

    @CallSuper
    @Override
    protected void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ORDER_BUNDLE_KEY, getOrder());
    }

    @CallSuper
    @Override
    protected void onRestoreInstanceState(@NonNull final Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        this.order = (Order) savedState.getSerializable(ORDER_BUNDLE_KEY);
    }

    @Override
    public final void sort() {
        sort(Order.ASCENDING);
    }

    @Override
    public final void sort(@NonNull final Order order) {
        ensureNotNull(order, "The order may not be null");
        this.order = order;

        if (order == Order.ASCENDING) {
            Collections.sort(getItems());
            String message = "Sorted items in ascending order";
            getLogger().logInfo(getClass(), message);
        } else {
            Collections.sort(getItems(), Collections.reverseOrder());
            String message = "Sorted items in descending order";
            getLogger().logInfo(getClass(), message);
        }

        notifyOnSorted(getAllItems(), order, null);
        notifyOnDataSetChanged();
    }

    @Override
    public final void sort(@NonNull final Comparator<DataType> comparator) {
        sort(Order.ASCENDING, comparator);
    }

    @Override
    public final void sort(@NonNull final Order order,
                           @NonNull final Comparator<DataType> comparator) {
        ensureNotNull(order, "The order may not be null");
        this.order = order;
        Comparator<Item<DataType>> itemComparator = new ItemComparator<>(comparator);

        if (order == Order.ASCENDING) {
            Collections.sort(getItems(), itemComparator);
            String message = "Sorted items in ascending order using the comparator \"" +
                    comparator.getClass().getSimpleName() + "\"";
            getLogger().logInfo(getClass(), message);
        } else {
            Collections.sort(getItems(), Collections.reverseOrder(itemComparator));
            String message = "Sorted items in descending order using the comparator \"" +
                    comparator.getClass().getSimpleName() + "\"";
            getLogger().logInfo(getClass(), message);
        }

        notifyOnSorted(getAllItems(), order, comparator);
        notifyOnDataSetChanged();
    }

    @Override
    public final Order getOrder() {
        return order;
    }

    @Override
    public final void addSortingListener(@NonNull final ListSortingListener<DataType> listener) {
        ensureNotNull(listener, "The listener may not be null");
        sortingListeners.add(listener);
        String message = "Added sorting listener \"" + listener + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void removeSortingListener(@NonNull final ListSortingListener<DataType> listener) {
        ensureNotNull(listener, "The listener may not be null");
        sortingListeners.remove(listener);
        String message = "Removed sorting listener \"" + listener + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @CallSuper
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((order == null) ? 0 : order.hashCode());
        return result;
    }

    @CallSuper
    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractSortableListAdapter<?, ?> other = (AbstractSortableListAdapter<?, ?>) obj;
        if (order != other.order)
            return false;
        return true;
    }

    @Override
    public abstract AbstractSortableListAdapter<DataType, DecoratorType> clone()
            throws CloneNotSupportedException;

}