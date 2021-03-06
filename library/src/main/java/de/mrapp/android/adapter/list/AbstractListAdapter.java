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
package de.mrapp.android.adapter.list;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Predicate;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import androidx.recyclerview.widget.RecyclerView;
import de.mrapp.android.adapter.RestoreInstanceStateException;
import de.mrapp.android.adapter.datastructure.UnmodifiableList;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.datastructure.item.ItemIterator;
import de.mrapp.android.adapter.datastructure.item.ItemListIterator;
import de.mrapp.android.adapter.datastructure.item.UnmodifiableItemList;
import de.mrapp.android.adapter.decorator.AbstractListDecorator;
import de.mrapp.android.adapter.util.AdapterViewUtil;
import de.mrapp.android.util.logging.LogLevel;
import de.mrapp.android.util.logging.Logger;
import de.mrapp.android.util.view.ViewHolder;
import de.mrapp.util.Condition;

/**
 * An abstract base class for all adapters, whose underlying data is managed as a list of arbitrary
 * items. Such an adapter's purpose is to provide the underlying data for visualization using a
 * {@link AbsListView} widget.
 *
 * @param <DataType>
 *         The type of the adapter's underlying data
 * @param <DecoratorType>
 *         The type of the decorator, which allows to customize the appearance of the views, which
 *         are used to visualize the items of the adapter
 * @author Michael Rapp
 * @since 0.1.0
 */
public abstract class AbstractListAdapter<DataType, DecoratorType extends AbstractListDecorator<DataType>>
        extends RecyclerView.Adapter<ViewHolder> implements ListAdapter<DataType> {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The key, which is used to store the state of the view, the adapter has been attached to,
     * within a bundle.
     */
    @VisibleForTesting
    protected static final String ADAPTER_VIEW_STATE_BUNDLE_KEY =
            AbstractListAdapter.class.getSimpleName() + "::AdapterViewState";

    /**
     * The key, which is used to store the adapter's underlying data within a bundle, if it
     * implements the interface {@link Parcelable}.
     */
    @VisibleForTesting
    protected static final String PARCELABLE_ITEMS_BUNDLE_KEY =
            AbstractListAdapter.class.getSimpleName() + "::ParcelableItems";

    /**
     * The key, which is used to store the adapter's underlying data within a bundle, if it
     * implements the interface {@link Serializable}.
     */
    @VisibleForTesting
    protected static final String SERIALIZABLE_ITEMS_BUNDLE_KEY =
            AbstractListAdapter.class.getSimpleName() + "::SerializableItems";

    /**
     * The key, which is used to store, whether duplicate items should be allowed, or not, within a
     * bundle.
     */
    @VisibleForTesting
    protected static final String ALLOW_DUPLICATES_BUNDLE_KEY =
            AbstractListAdapter.class.getSimpleName() + "::AllowDuplicates";

    /**
     * The key, which is used to store, whether the method <code>notifyDataSetChanged():void</code>
     * should be called automatically when the adapter's underlying data has been changed, or not,
     * within a bundle.
     */
    @VisibleForTesting
    protected static final String NOTIFY_ON_CHANGE_BUNDLE_KEY =
            AbstractListAdapter.class.getSimpleName() + "::NotifyOnChange";

    /**
     * The key, which is used to store the key value pairs, which are stored within the adapter,
     * within a bundle.
     */
    @VisibleForTesting
    protected static final String PARAMETERS_BUNDLE_KEY =
            AbstractListAdapter.class.getSimpleName() + "::Parameters";

    /**
     * The key, which is used to store the log level, which is used for logging, within a bundle.
     */
    @VisibleForTesting
    protected static final String LOG_LEVEL_BUNDLE_KEY =
            AbstractListAdapter.class.getSimpleName() + "::LogLevel";

    /**
     * The context, the adapter belongs to.
     */
    private final transient Context context;

    /**
     * The decorator, which allows to customize the appearance of the views, which are used to
     * visualize the items of the adapter.
     */
    private transient DecoratorType decorator;

    /**
     * A map, which contains the data set observers, which are notified, when the underlying data of
     * the adapter changes.
     */
    private final transient Map<DataSetObserver, RecyclerView.AdapterDataObserver> dataSetObservers;

    /**
     * The logger, which is used for logging.
     */
    private final transient Logger logger;

    /**
     * A set, which contains the listeners, which should be notified, when an item of the adapter
     * has been clicked by the user.
     */
    private transient Set<ListAdapterItemClickListener<DataType>> itemClickListeners;

    /**
     * A set, which contains the listeners, which should be notified, when an item of the adapter
     * has been long-clicked by the user.
     */
    private transient Set<ListAdapterItemLongClickListener<DataType>> itemLongClickListeners;

    /**
     * A set, which contains the listeners, which should be notified, when the adapter's underlying
     * data has been modified.
     */
    private transient Set<ListAdapterListener<DataType>> adapterListeners;

    /**
     * The view, the adapter is currently attached to.
     */
    private transient AbsListView adapterView;

    /**
     * The recycler view, the adapter is currently attached to.
     */
    private transient RecyclerView recyclerView;

    /**
     * A bundle, which contains key value pairs, which are stored within the adapter.
     */
    private Bundle parameters;

    /**
     * True, if duplicate items are allowed, false otherwise.
     */
    private boolean allowDuplicates;

    /**
     * True, if the method <code>notifyDataSetChanged():void</code> is automatically called when the
     * adapter's underlying data has been changed, false otherwise.
     */
    private boolean notifyOnChange;

    /**
     * A list, which contains the the adapter's underlying data.
     */
    private ArrayList<Item<DataType>> items;

    /**
     * Notifies all listeners, which have been registered to be notified, when an item of the
     * adapter has been clicked by the user, about an item, which has been clicked.
     *
     * @param item
     *         The item, which has been clicked by the user, as an instance of the generic type
     *         DataType. The item may not be null
     * @param index
     *         The index of the item, which has been clicked by the user, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getCount():int</code> - 1
     */
    private void notifyOnItemClicked(@NonNull final DataType item, final int index) {
        for (ListAdapterItemClickListener<DataType> listener : itemClickListeners) {
            listener.onItemClicked(this, item, index);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when an item of the
     * adapter has been clicked by the user, about a header, which has been clicked.
     *
     * @param view
     *         The header view, which has been clicked by the user, as an instance of the class
     *         {@link View}. The view may not be null
     * @param index
     *         The index of the header, which has been clicked by the user, as an {@link Integer}
     *         value
     */
    private void notifyOnHeaderClicked(@NonNull final View view, final int index) {
        for (ListAdapterItemClickListener<DataType> listener : itemClickListeners) {
            listener.onHeaderClicked(this, view, index);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when an item of the
     * adapter has been clicked by the user, about a footer, which has been clicked.
     *
     * @param view
     *         The footer view, which has been clicked by the user, as an instance of the class
     *         {@link View}. The view may not be null
     * @param index
     *         The index of the footer, which has been clicked by the user, as an {@link Integer}
     *         value
     */
    private void notifyOnFooterClicked(@NonNull final View view, final int index) {
        for (ListAdapterItemClickListener<DataType> listener : itemClickListeners) {
            listener.onFooterClicked(this, view, index);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when an item of the
     * adapter has been long-clicked by the user, about an item, which has been long-clicked.
     *
     * @param item
     *         The item, which has been long-clicked by the user, as an instance of the generic type
     *         DataType. The item may not be null
     * @param index
     *         The index of the item, which has been long-clicked by the user, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getCount():int</code> - 1
     * @return True, if a listener has consumed the long-click, false otherwise
     */
    private boolean notifyOnItemLongClicked(@NonNull final DataType item, final int index) {
        for (ListAdapterItemLongClickListener<DataType> listener : itemLongClickListeners) {
            if (listener.onItemLongClicked(this, item, index)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when an item of the
     * adapter has been long-clicked by the user, about a header, which has been long-clicked.
     *
     * @param view
     *         The header view, which has been clicked by the user, as an instance of the class
     *         {@link View}. The view may not be null
     * @param index
     *         The index of the header, which has been clicked by the user, as an {@link Integer}
     *         value
     * @return True, if a listener has consumed the long-click, false otherwise
     */
    private boolean notifyOnHeaderLongClicked(@NonNull final View view, final int index) {
        for (ListAdapterItemLongClickListener<DataType> listener : itemLongClickListeners) {
            if (listener.onHeaderLongClicked(this, view, index)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when an item of the
     * adapter has been long-clicked by the user, about a footer, which has been long-clicked.
     *
     * @param view
     *         The footer view, which has been clicked by the user, as an instance of the class
     *         {@link View}. The view may not be null
     * @param index
     *         The index of the footer, which has been clicked by the user, as an {@link Integer}
     *         value
     * @return True, if a listener has consumed the long-click, false otherwise
     */
    private boolean notifyOnFooterLongClicked(@NonNull final View view, final int index) {
        for (ListAdapterItemLongClickListener<DataType> listener : itemLongClickListeners) {
            if (listener.onFooterLongClicked(this, view, index)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when the adapter's
     * underlying data has been modified, about an item, which has been added to the adapter.
     *
     * @param item
     *         The item, which has been added to the adapter, as an instance of the generic type
     *         DataType. The item may not be null
     * @param index
     *         The index of the item, which has been added to the adapter, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getCount():int</code> - 1
     */
    private void notifyOnItemAdded(@NonNull final DataType item, final int index) {
        for (ListAdapterListener<DataType> listener : adapterListeners) {
            listener.onItemAdded(this, item, index);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when the adapter's
     * underlying data has been modified, about an item, which has been removed from the adapter.
     *
     * @param item
     *         The item, which has been removed from the adapter, as an instance of the generic type
     *         DataType. The item may not be null
     * @param index
     *         The index of the item, which has been removed from the adapter, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getCount():int</code> - 1
     */
    private void notifyOnItemRemoved(@NonNull final DataType item, final int index) {
        for (ListAdapterListener<DataType> listener : adapterListeners) {
            listener.onItemRemoved(this, item, index);
        }
    }

    /**
     * Creates and returns an {@link OnClickListener}, which is invoked, when a specific item has
     * been clicked.
     *
     * @param index
     *         The index of the item, which should cause the listener to be invoked, when clicked,
     *         as an {@link Integer} value
     * @return The listener, which has been created, as an instance of the type {@link
     * OnClickListener}
     */
    private OnClickListener createItemOnClickListener(final int index) {
        return new OnClickListener() {

            @Override
            public void onClick(final View v) {
                notifyOnItemClicked(getItem(index), index);
            }

        };
    }

    /**
     * Creates and returns an {@link android.widget.AdapterView.OnItemClickListener}, which is
     * invoked, when an item of the adapter has been clicked.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * android.widget.AdapterView.OnItemClickListener}
     */
    private AdapterView.OnItemClickListener createAdapterViewOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, final View view,
                                    final int position, final long id) {
                if (adapterView instanceof ListView) {
                    ListView listView = (ListView) adapterView;

                    if (position < listView.getHeaderViewsCount()) {
                        notifyOnHeaderClicked(view, position);
                    } else if (position >= getCount() + listView.getHeaderViewsCount()) {
                        notifyOnFooterClicked(view,
                                position - getCount() - listView.getHeaderViewsCount());
                    } else {
                        int index = position - listView.getHeaderViewsCount();
                        notifyOnItemClicked(getItem(index), index);
                    }
                } else {
                    notifyOnItemClicked(getItem(position), position);
                }
            }

        };
    }

    /**
     * Creates and returns an {@link OnClickListener}, which is invoked, when a specific item has
     * been long-clicked.
     *
     * @param index
     *         The index of the item, wihch should cause the listener to be invoked, when
     *         long-clicked, as an {@link Integer} value
     * @return The listener, which has been created, as an instance of the type {@link
     * OnLongClickListener}
     */
    private OnLongClickListener createItemOnLongClickListener(final int index) {
        return new OnLongClickListener() {

            @Override
            public boolean onLongClick(final View v) {
                return notifyOnItemLongClicked(getItem(index), index);
            }

        };
    }

    /**
     * Creates and returns an {@link android.widget.AdapterView.OnItemLongClickListener}, which is
     * invoked, when an item of the adapter has been long-clicked.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * android.widget.AdapterView.OnItemLongClickListener}
     */
    private AdapterView.OnItemLongClickListener createAdapterViewOnItemLongClickListener() {
        return new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view,
                                           final int position, final long id) {
                if (adapterView instanceof ListView) {
                    ListView listView = (ListView) adapterView;

                    if (position < listView.getHeaderViewsCount()) {
                        return notifyOnHeaderLongClicked(view, position);
                    } else if (position >= getCount() + listView.getHeaderViewsCount()) {
                        return notifyOnFooterLongClicked(view,
                                position - getCount() - listView.getHeaderViewsCount());
                    } else {
                        int index = position - listView.getHeaderViewsCount();
                        return notifyOnItemLongClicked(getItem(index), index);
                    }
                } else {
                    return notifyOnItemLongClicked(getItem(position), position);
                }
            }

        };
    }

    /**
     * Returns, whether the adapter's underlying data implements the interface {@link Parcelable},
     * or not.
     *
     * @return True, if the adapter's underlying data implements the interface {@link Parcelable} or
     * if the adapter is empty, false otherwise
     */
    private boolean isUnderlyingDataParcelable() {
        if (!getUnfilteredItems().isEmpty()) {
            Item<DataType> item = getUnfilteredItems().get(0);
            return item.isParcelable();
        }

        return true;
    }

    /**
     * Returns, whether the adapter's underlying data implements the interface {@link Serializable},
     * or not.
     *
     * @return True, if the adapter's underlying data implements the interface {@link Serializable}
     * or if the adapter is empty, false otherwise
     */
    private boolean isUnderlyingDataSerializable() {
        if (!getUnfilteredItems().isEmpty()) {
            Item<DataType> item = getUnfilteredItems().get(0);
            return item.isSerializable();
        }

        return true;
    }

    /**
     * Returns, the context, the adapter belongs to.
     *
     * @return The context, the adapter belongs to, as an instance of the class {@link Context}. The
     * context may not be null
     */
    protected final Context getContext() {
        return context;
    }

    /**
     * Returns the logger, which is used for logging.
     *
     * @return The logger, which is used for logging, as an instance of the class Logger. The logger
     * may not be null
     */
    protected final Logger getLogger() {
        return logger;
    }

    /**
     * Returns a list, which contains the adapter's underlying data.
     *
     * @return A list, which contains the adapters underlying data, as an instance of the type
     * {@link ArrayList} or an empty list, if the adapter does not contain any data
     */
    protected final ArrayList<Item<DataType>> getItems() {
        return items;
    }

    /**
     * Returns a list, which contains the adapter's unfiltered items. This method has to be
     * overridden by subclasses, which filter the adapter's underlying data.
     *
     * @return A list, which contains the adapter's unfiltered items, as an instance of the type
     * {@link ArrayList} or an empty list, if the adapter does not contain any data
     */
    protected ArrayList<Item<DataType>> getUnfilteredItems() {
        return items;
    }

    /**
     * Sets the list, which contains the adapter's underlying data.
     *
     * @param items
     *         The list, which should be set, as an instance of the type {@link ArrayList} or an
     *         empty list, if the adapter should not contain any data
     */
    protected final void setItems(@NonNull final ArrayList<Item<DataType>> items) {
        Condition.INSTANCE.ensureNotNull(items, "The items may not be null");
        this.items = items;
    }

    /**
     * Creates and returns a deep copy of the list, which contains the adapter's underlying data.
     *
     * @return A deep copy of the list, which contains the adapter's underlying data, as an instance
     * of the type {@link ArrayList}. The list may not be null
     * @throws CloneNotSupportedException
     *         The exception, which is thrown, if cloning is not supported by the adapter's
     *         underlying data
     */
    protected final ArrayList<Item<DataType>> cloneItems() throws CloneNotSupportedException {
        ArrayList<Item<DataType>> clonedItems = new ArrayList<>();

        for (Item<DataType> item : items) {
            clonedItems.add(item.clone());
        }

        return clonedItems;
    }

    /**
     * Returns a set, which contains the listeners, which should be notified, when an item of the
     * adapter has been clicked by the user.
     *
     * @return A set, which contains the listeners, which should be notified, when an item of the
     * adapter has been clicked by the user, as an instance of the type {@link Set} or an empty set,
     * if no listeners should be notified
     */
    protected final Set<ListAdapterItemClickListener<DataType>> getItemClickListeners() {
        return itemClickListeners;
    }

    /**
     * Returns a set, which contains the listeners, which should be notified, when an item of the
     * adapter has been long-clicked by the user.
     *
     * @return A set, which contains the listeners, which should be notified, when an item of the
     * adapter has been long-clicked by the user, as an instance of the type {@link Set} or an empty
     * set, if no listeners should be notified
     */
    protected final Set<ListAdapterItemLongClickListener<DataType>> getItemLongClickListeners() {
        return itemLongClickListeners;
    }

    /**
     * Returns a set, which contains the listeners, which should be notified, when the adapter's
     * underlying data has been modified.
     *
     * @return A set, which contains the listeners, which should be notified, when the adapter's
     * underlying data has been modified, as an instance of the type {@link Set} or an empty set, if
     * no listeners should be notified
     */
    protected final Set<ListAdapterListener<DataType>> getAdapterListeners() {
        return adapterListeners;
    }

    /**
     * Notifies all observers, that an item has been changed, if automatically notifying such evens
     * is currently enabled.
     *
     * @param index
     *         The index of the item, which has been changed, as an {@link Integer} value
     */
    protected final void notifyObserversOnItemChanged(final int index) {
        if (isNotifiedOnChange()) {
            if (recyclerView != null) {
                notifyItemChanged(index);
            } else {
                notifyDataSetChanged();
            }
        }
    }

    /**
     * Notifies all observers, that an item has been added, if automatically notifying such events
     * is currently enabled.
     *
     * @param index
     *         The index of the item, which has been added, as an {@link Integer} value
     */
    protected final void notifyObserversOnItemInserted(final int index) {
        if (isNotifiedOnChange()) {
            if (recyclerView != null) {
                notifyItemInserted(index);
            } else {
                notifyDataSetChanged();
            }
        }
    }

    /**
     * Notifies all observers, that an item has been removed, if automatically notifying such events
     * is currently enabled.
     *
     * @param index
     *         The index of the item, which has been removed, as an {@link Integer} value
     */
    protected final void notifyObserversOnItemRemoved(final int index) {
        if (isNotifiedOnChange()) {
            if (recyclerView != null) {
                notifyItemRemoved(index);
            } else {
                notifyDataSetChanged();
            }
        }
    }

    /**
     * Notifies all observers, that the adapter's underlying data has been changed, if automatically
     * notifying such events is currently enabled.
     */
    protected final void notifyObserversOnDataSetChanged() {
        if (isNotifiedOnChange()) {
            notifyDataSetChanged();
        }
    }

    /**
     * Returns the index of a specific item or throws a {@link NoSuchElementException}, if the
     * adapter does not contain the item.
     *
     * @param item
     *         The item, whose index should be returned, as an instance of the generic type
     *         DataType. The item may not be null
     * @return The index of the the given item, as an {@link Integer} value
     */
    protected final int indexOfOrThrowException(@NonNull final DataType item) {
        int index = indexOf(item);

        if (index != -1) {
            return index;
        } else {
            throw new NoSuchElementException("Adapter does not contain item \"" + item + "\"");
        }
    }

    /**
     * Adds a new item at a specific index.
     *
     * @param index
     *         The index, the item should be added at, as an {@link Integer} value
     * @param item
     *         The item, which should be added, as an instance of the class {@link Item}. The item
     *         may not be null
     * @return True, if the item has been added, false otherwise
     */
    protected boolean addItem(final int index, @NonNull final Item<DataType> item) {
        if (!areDuplicatesAllowed() && containsItem(item.getData())) {
            String message = "Item \"" + item.getData() + "\" at index " + index +
                    " not added, because adapter already contains item";
            getLogger().logDebug(getClass(), message);
            return false;
        }

        items.add(index, item);
        notifyOnItemAdded(item.getData(), index);
        notifyObserversOnItemInserted(index);
        String message = "Item \"" + item.getData() + "\" added at index " + index;
        getLogger().logInfo(getClass(), message);
        return true;
    }

    /**
     * This method is invoked when the state of the adapter is about to be stored within a bundle.
     *
     * @param outState
     *         The bundle, which is used to store the state of the adapter within a bundle, as an
     *         instance of the class {@link Bundle}. The bundle may not be null
     */
    protected abstract void onSaveInstanceState(@NonNull final Bundle outState);

    /**
     * This method is invoked when the state of the adapter, which has previously been stored within
     * a bundle, is about to be restored.
     *
     * @param savedState
     *         The bundle, which has been previously used to store the state of the adapter, as an
     *         instance of the class {@link Bundle}. The bundle may not be null
     */
    protected abstract void onRestoreInstanceState(@NonNull final Bundle savedState);

    /**
     * This method is invoked to apply the decorator, which allows to customize the view, which is
     * used to visualize a specific item.
     *
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param view
     *         The view, which is used to visualize the item, as an instance of the class {@link
     *         View}. The view may not be null
     * @param index
     *         The index of the item, which should be visualized, as an {@link Integer} value
     */
    protected abstract void applyDecorator(@NonNull final Context context, @NonNull final View view,
                                           final int index);

    /**
     * Creates a new adapter, whose underlying data is managed as a list of arbitrary items.
     *
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param decorator
     *         The decorator, which should be used to customize the appearance of the views, which
     *         are used to visualize the items of the adapter, as an instance of the generic type
     *         DecoratorType. The decorator may not be null
     * @param logLevel
     *         The log level, which should be used for logging, as a value of the enum LogLevel. The
     *         log level may not be null
     * @param items
     *         A list, which contains the adapter's items, or an empty list, if the adapter should
     *         not contain any items
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
     *         A set, which contains the listeners, which should be notified, when the adapter's
     *         underlying data has been modified, as an instance of the type {@link Set} or an empty
     *         set, if no listeners should be notified
     */
    protected AbstractListAdapter(@NonNull final Context context,
                                  @NonNull final DecoratorType decorator,
                                  @NonNull final LogLevel logLevel,
                                  @NonNull final ArrayList<Item<DataType>> items,
                                  final boolean allowDuplicates, final boolean notifyOnChange,
                                  @NonNull final Set<ListAdapterItemClickListener<DataType>> itemClickListeners,
                                  @NonNull final Set<ListAdapterItemLongClickListener<DataType>> itemLongClickListeners,
                                  @NonNull final Set<ListAdapterListener<DataType>> adapterListeners) {
        Condition.INSTANCE.ensureNotNull(context, "The context may not be null");
        Condition.INSTANCE.ensureNotNull(decorator, "The decorator may not be null");
        Condition.INSTANCE.ensureNotNull(items, "The items may not be null");
        Condition.INSTANCE
                .ensureNotNull(itemClickListeners, "The item click listeners may not be null");
        Condition.INSTANCE.ensureNotNull(itemLongClickListeners,
                "The item long click listeners may not be null");
        Condition.INSTANCE.ensureNotNull(adapterListeners, "The adapter listeners may not be null");
        this.context = context;
        this.decorator = decorator;
        this.dataSetObservers = new HashMap<>();
        this.logger = new Logger(logLevel);
        this.items = items;
        this.parameters = null;
        this.allowDuplicates = allowDuplicates;
        this.notifyOnChange = notifyOnChange;
        this.itemClickListeners = itemClickListeners;
        this.itemLongClickListeners = itemLongClickListeners;
        this.adapterListeners = adapterListeners;
        setHasStableIds(true);
    }

    /**
     * Returns the decorator, which allows to customize the appearance of the views, which are used
     * to visualize the items of the adapter.
     *
     * @return The decorator, which allows to customize the appearance of the views, which are used
     * to visualize the items of the adapter, as an instance of the generic type DecoratorType. The
     * decorator may not be null
     */
    public final DecoratorType getDecorator() {
        return decorator;
    }

    /**
     * Sets the decorator, which allows to customize the appearance of the views, which are used to
     * visualize the items of the adapter.
     *
     * @param decorator
     *         The decorator, which should be set, as an instance of the generic type DecoratorType.
     *         The decorator may not be null
     */
    public final void setDecorator(@NonNull final DecoratorType decorator) {
        Condition.INSTANCE.ensureNotNull(decorator, "The decorator may not be null");
        this.decorator = decorator;
        notifyObserversOnDataSetChanged();
    }

    @Override
    public final void registerDataSetObserver(@NonNull final DataSetObserver observer) {
        DataSetObserverWrapper dataObserver = new DataSetObserverWrapper(observer);
        dataSetObservers.put(observer, dataObserver);
        registerAdapterDataObserver(dataObserver);
    }

    @Override
    public final void unregisterDataSetObserver(@NonNull final DataSetObserver observer) {
        RecyclerView.AdapterDataObserver dataObserver = dataSetObservers.get(observer);

        if (dataObserver != null) {
            unregisterAdapterDataObserver(dataObserver);
        }
    }

    @Override
    public final boolean isNotifiedOnChange() {
        return notifyOnChange;
    }

    @Override
    public final void notifyOnChange(final boolean notifyOnChange) {
        this.notifyOnChange = notifyOnChange;
        String message = "Changes of the adapter's underlying data are now " +
                (notifyOnChange ? "" : "not ") + "automatically notified";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final LogLevel getLogLevel() {
        return getLogger().getLogLevel();
    }

    @Override
    public final void setLogLevel(@NonNull final LogLevel logLevel) {
        getLogger().setLogLevel(logLevel);
    }

    @Override
    public final Bundle getParameters() {
        return parameters;
    }

    @Override
    public final void setParameters(@Nullable final Bundle parameters) {
        this.parameters = parameters;
        String message = "Set parameters to \"" + parameters + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final boolean areDuplicatesAllowed() {
        return allowDuplicates;
    }

    @Override
    public final void allowDuplicates(final boolean allowDuplicates) {
        this.allowDuplicates = allowDuplicates;
        String message = "Duplicate items are now " + (allowDuplicates ? "allowed" : "disallowed");
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void addAdapterListener(@NonNull final ListAdapterListener<DataType> listener) {
        Condition.INSTANCE.ensureNotNull(listener, "The listener may not be null");
        adapterListeners.add(listener);
        String message = "Added adapter listener \"" + listener + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void removeAdapterListener(@NonNull final ListAdapterListener<DataType> listener) {
        Condition.INSTANCE.ensureNotNull(listener, "The listener may not be null");
        adapterListeners.remove(listener);
        String message = "Removed adapter listener \"" + listener + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void addItemClickListener(
            @NonNull final ListAdapterItemClickListener<DataType> listener) {
        Condition.INSTANCE.ensureNotNull(listener, "The listener may not be null");
        itemClickListeners.add(listener);
    }

    @Override
    public final void removeItemClickListener(
            @NonNull final ListAdapterItemClickListener<DataType> listener) {
        Condition.INSTANCE.ensureNotNull(listener, "The listener may not be null");
        itemClickListeners.remove(listener);
    }

    @Override
    public final void addItemLongClickListener(
            @NonNull final ListAdapterItemLongClickListener<DataType> listener) {
        Condition.INSTANCE.ensureNotNull(listener, "The listener may not be null");
        itemLongClickListeners.add(listener);
    }

    @Override
    public final void removeItemLongClickListener(
            @NonNull final ListAdapterItemLongClickListener<DataType> listener) {
        Condition.INSTANCE.ensureNotNull(listener, "The listener may not be null");
        itemLongClickListeners.add(listener);
    }

    @Override
    public final int addItem(@NonNull final DataType item) {
        int index = getCount();
        boolean added = addItem(index, item);
        return added ? index : -1;
    }

    @Override
    public final boolean addItem(final int index, @NonNull final DataType item) {
        Condition.INSTANCE.ensureNotNull(item, "The item may not be null");
        return addItem(index, new Item<>(item));
    }

    @Override
    public final boolean addAllItems(@NonNull final Collection<? extends DataType> items) {
        return addAllItems(getCount(), items);
    }

    @Override
    public final boolean addAllItems(final int index,
                                     @NonNull final Collection<? extends DataType> items) {
        Condition.INSTANCE.ensureNotNull(items, "The collection may not be null");
        boolean result = true;
        int currentIndex = index;

        for (DataType item : items) {
            boolean added = addItem(currentIndex, item);
            result &= added;

            if (added) {
                currentIndex++;
            }
        }

        return result;
    }

    @SafeVarargs
    @Override
    public final boolean addAllItems(@NonNull final DataType... items) {
        return addAllItems(getCount(), items);
    }

    @SafeVarargs
    @Override
    public final boolean addAllItems(final int index, @NonNull final DataType... items) {
        Condition.INSTANCE.ensureNotNull(items, "The array may not be null");
        return addAllItems(index, Arrays.asList(items));
    }

    @Override
    public final DataType replaceItem(final int index, @NonNull final DataType item) {
        Condition.INSTANCE.ensureNotNull(item, "The item may not be null");
        DataType replacedItem = items.set(index, new Item<>(item)).getData();
        notifyOnItemRemoved(replacedItem, index);
        notifyOnItemAdded(item, index);
        notifyObserversOnItemChanged(index);
        String message =
                "Replaced item \"" + replacedItem + "\" at index " + index + " with item \"" +
                        item + "\"";
        getLogger().logInfo(getClass(), message);
        return replacedItem;
    }

    @Override
    public final DataType removeItem(final int index) {
        DataType removedItem = items.remove(index).getData();
        notifyOnItemRemoved(removedItem, index);
        notifyObserversOnItemRemoved(index);
        String message = "Removed item \"" + removedItem + "\" from index " + index;
        getLogger().logInfo(getClass(), message);
        return removedItem;
    }

    @Override
    public final boolean removeItem(@NonNull final DataType item) {
        Condition.INSTANCE.ensureNotNull(item, "The item may not be null");
        int index = indexOf(item);

        if (index != -1) {
            items.remove(index);
            notifyOnItemRemoved(item, index);
            notifyObserversOnItemRemoved(index);
            String message = "Removed item \"" + item + "\" from index " + index;
            getLogger().logInfo(getClass(), message);
            return true;
        }

        String message = "Item \"" + item + "\" not removed, because adapter does not contain item";
        getLogger().logDebug(getClass(), message);
        return false;
    }

    @Override
    public final boolean removeAllItems(@NonNull final Collection<? extends DataType> items) {
        Condition.INSTANCE.ensureNotNull(items, "The collection may not be null");
        int numberOfRemovedItems = 0;

        for (int i = getCount() - 1; i >= 0; i--) {
            if (items.contains(getItem(i))) {
                removeItem(i);
                numberOfRemovedItems++;
            }
        }

        return numberOfRemovedItems == items.size();
    }

    @SafeVarargs
    @Override
    public final boolean removeAllItems(@NonNull final DataType... items) {
        Condition.INSTANCE.ensureNotNull(items, "The array may not be null");
        return removeAllItems(Arrays.asList(items));
    }

    @Override
    public final void retainAllItems(@NonNull final Collection<? extends DataType> items) {
        Condition.INSTANCE.ensureNotNull(items, "The collection may not be null");

        for (int i = getCount() - 1; i >= 0; i--) {
            if (!items.contains(getItem(i))) {
                removeItem(i);
            }
        }
    }

    @SafeVarargs
    @Override
    public final void retainAllItems(@NonNull final DataType... items) {
        Condition.INSTANCE.ensureNotNull(items, "The array may not be null");
        retainAllItems(Arrays.asList(items));
    }

    @Override
    public final void clearItems() {
        for (int i = getCount() - 1; i >= 0; i--) {
            removeItem(i);
        }
    }

    @Override
    public final Iterator<DataType> iterator() {
        return new ItemIterator<>(items, this);
    }

    @Override
    public final ListIterator<DataType> listIterator() {
        return new ItemListIterator<>(items, this);
    }

    @Override
    public final ListIterator<DataType> listIterator(final int index) {
        return new ItemListIterator<>(items, this, index);
    }

    @Override
    public final List<DataType> subList(final int start, final int end) {
        List<DataType> subList = new ArrayList<>();

        for (int i = start; i < end; i++) {
            subList.add(getItem(i));
        }

        return new UnmodifiableList<>(subList);
    }

    @Override
    public final Object[] toArray() {
        return getAllItems().toArray();
    }

    @Override
    public final <T> T[] toArray(@NonNull final T[] array) {
        return getAllItems().toArray(array);
    }

    @Override
    public final int indexOf(@NonNull final DataType item) {
        Condition.INSTANCE.ensureNotNull(item, "The item may not be null");

        for (int i = 0; i < getCount(); i++) {
            if (getItem(i).equals(item)) {
                return i;
            }
        }

        return -1;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public final int indexOf(@NonNull final Predicate<DataType> predicate) {
        Condition.INSTANCE.ensureNotNull(predicate, "The predicate may not be null");

        for (int i = 0; i < getCount(); i++) {
            if (predicate.test(getItem(i))) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final int lastIndexOf(@NonNull final DataType item) {
        Condition.INSTANCE.ensureNotNull(item, "The item may not be null");

        for (int i = getCount() - 1; i >= 0; i--) {
            if (getItem(i).equals(item)) {
                return i;
            }
        }

        return -1;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public final int lastIndexOf(@NonNull final Predicate<DataType> predicate) {
        Condition.INSTANCE.ensureNotNull(predicate, "The predicate may not be null");

        for (int i = getCount() - 1; i >= 0; i--) {
            if (predicate.test(getItem(i))) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final boolean containsItem(@NonNull final DataType item) {
        Condition.INSTANCE.ensureNotNull(item, "The item may not be null");
        return indexOf(item) != -1;
    }

    @Override
    public final boolean containsAllItems(@NonNull final Collection<? extends DataType> items) {
        Condition.INSTANCE.ensureNotNull(items, "The collection may not be null");
        boolean result = true;

        for (DataType item : items) {
            result &= containsItem(item);
        }

        return result;
    }

    @SafeVarargs
    @Override
    public final boolean containsAllItems(@NonNull final DataType... items) {
        Condition.INSTANCE.ensureNotNull(items, "The array may not be null");
        return containsAllItems(Arrays.asList(items));
    }

    @Override
    public final DataType getItem(final int index) {
        return items.get(index).getData();
    }

    @Override
    public final List<DataType> getAllItems() {
        return new UnmodifiableItemList<>(items);
    }

    @Override
    public final boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public final void attach(@NonNull final AbsListView adapterView) {
        Condition.INSTANCE.ensureNotNull(adapterView, "The adapter view may not be null");
        detach();
        adapterView.setAdapter(this);
        this.adapterView = adapterView;
        this.adapterView.setOnItemClickListener(createAdapterViewOnItemClickListener());
        this.adapterView.setOnItemLongClickListener(createAdapterViewOnItemLongClickListener());
        getLogger().logDebug(getClass(), "Attached adapter to view \"" + adapterView + "\"");
    }

    @Override
    public final void attach(@NonNull final RecyclerView adapterView) {
        Condition.INSTANCE.ensureNotNull(adapterView, "The adapter view may not be null");
        detach();
        adapterView.setAdapter(this);
        this.recyclerView = adapterView;
        getLogger().logDebug(getClass(), "Attached adapter to view \"" + adapterView + "\"");
    }

    @Override
    public final void detach() {
        if (adapterView != null) {
            if (adapterView.getAdapter() == this) {
                adapterView.setAdapter(null);
                String message = "Detached adapter from view \"" + adapterView + "\"";
                getLogger().logDebug(getClass(), message);
            } else {
                String message = "Adapter has not been detached, because the " +
                        "adapter of the corresponding view has been changed in the meantime";
                getLogger().logVerbose(getClass(), message);
            }

            adapterView = null;
        } else if (recyclerView != null) {
            if (recyclerView.getAdapter() == this) {
                recyclerView.setAdapter(null);
                String message = "Detached adapter from view \"" + recyclerView + "\"";
                getLogger().logDebug(getClass(), message);
            } else {
                String message = "Adapter has not been detached, because the " +
                        "adapter of the corresponding view has been changed in the meantime";
                getLogger().logVerbose(getClass(), message);
            }
        } else {
            String message = "Adapter has not been detached, because it has not " +
                    "been attached to a view yet";
            getLogger().logVerbose(getClass(), message);
        }
    }

    @Override
    public final boolean isAttached() {
        return getAdapterView() != null;
    }

    @Nullable
    @Override
    public final View getAdapterView() {
        if (adapterView != null) {
            return adapterView;
        } else if (recyclerView != null) {
            return recyclerView;
        }

        return null;
    }

    @Override
    public final int getCount() {
        return items.size();
    }

    @Override
    public final int getItemCount() {
        return getCount();
    }

    @Override
    public final long getItemId(final int index) {
        return System.identityHashCode(getItem(index));
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
    public final View getView(final int index, @Nullable final View convertView,
                              @Nullable final ViewGroup parent) {
        if (adapterView == null) {
            throw new IllegalStateException(
                    "Adapter must be attached to a AbsListView using its attach method");
        }

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            DataType item = getItem(index);
            view = getDecorator().inflateView(inflater, parent, item);
            String message = "Inflated view to visualize the item at index " + index;
            getLogger().logVerbose(getClass(), message);
        }

        applyDecorator(getContext(), view, index);
        return view;
    }

    @Override
    public final ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                               final int viewType) {
        if (recyclerView == null) {
            throw new IllegalStateException(
                    "Adapter must be attached to a RecyclerView using its attach-method");
        }

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = getDecorator().onInflateView(inflater, parent, viewType);
        String message = "Inflated view to visualize item with view type " + viewType;
        getLogger().logVerbose(getClass(), message);
        return new ListItemViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int index) {
        ListItemViewHolder listItemViewHolder = (ListItemViewHolder) viewHolder;
        listItemViewHolder.setItemIndex(index);
        listItemViewHolder.getParentView().setOnClickListener(createItemOnClickListener(index));
        listItemViewHolder.getParentView()
                .setOnLongClickListener(createItemOnLongClickListener(index));
        applyDecorator(getContext(), listItemViewHolder.getParentView(), index);
    }

    @Override
    public final void onSaveInstanceState(@NonNull final Bundle outState,
                                          @NonNull final String key) {
        Condition.INSTANCE.ensureNotNull(outState, "The bundle may not be null");
        Condition.INSTANCE.ensureNotNull(key, "The key may not be null");
        Condition.INSTANCE.ensureNotEmpty(key, "The key may not be null");
        Bundle savedState = new Bundle();

        if (adapterView != null) {
            AdapterViewUtil
                    .onSaveInstanceState(adapterView, savedState, ADAPTER_VIEW_STATE_BUNDLE_KEY);
        } else if (recyclerView != null) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

            if (layoutManager != null) {
                savedState.putParcelable(ADAPTER_VIEW_STATE_BUNDLE_KEY,
                        layoutManager.onSaveInstanceState());
            }
        } else {
            String message = "The state of the adapter view can not be stored, because the " +
                    "adapter has not been attached to a view";
            getLogger().logWarn(getClass(), message);
        }

        if (isUnderlyingDataParcelable()) {
            savedState.putParcelableArrayList(PARCELABLE_ITEMS_BUNDLE_KEY, getUnfilteredItems());
        } else if (isUnderlyingDataSerializable()) {
            savedState.putSerializable(SERIALIZABLE_ITEMS_BUNDLE_KEY, getUnfilteredItems());
        } else {
            String message = "The adapter's items can not be stored, because the " +
                    "underlying data does neither implement the interface \"" +
                    Parcelable.class.getName() + "\", nor the interface \"" +
                    Serializable.class.getName() + "\"";
            getLogger().logWarn(getClass(), message);
        }

        savedState.putBundle(PARAMETERS_BUNDLE_KEY, getParameters());
        savedState.putBoolean(ALLOW_DUPLICATES_BUNDLE_KEY, areDuplicatesAllowed());
        savedState.putBoolean(NOTIFY_ON_CHANGE_BUNDLE_KEY, isNotifiedOnChange());
        savedState.putInt(LOG_LEVEL_BUNDLE_KEY, getLogLevel().getRank());
        onSaveInstanceState(savedState);
        outState.putBundle(key, savedState);
        getLogger().logDebug(getClass(), "Saved instance state");
    }

    @SuppressWarnings("unchecked")
    @Override
    public final void onRestoreInstanceState(@NonNull final Bundle savedInstanceState,
                                             @NonNull final String key)
            throws RestoreInstanceStateException {
        Condition.INSTANCE.ensureNotNull(savedInstanceState, "The bundle may not be null");
        Condition.INSTANCE.ensureNotNull(key, "The key may not be null");
        Condition.INSTANCE.ensureNotEmpty(key, "The key may not be null");
        Bundle savedState = savedInstanceState.getBundle(key);

        if (savedState != null) {
            try {
                if (savedState.containsKey(ADAPTER_VIEW_STATE_BUNDLE_KEY)) {
                    if (adapterView != null) {
                        AdapterViewUtil.onRestoreInstanceState(adapterView, savedState,
                                ADAPTER_VIEW_STATE_BUNDLE_KEY);
                    } else if (recyclerView != null && recyclerView.getLayoutManager() != null) {
                        recyclerView.getLayoutManager().onRestoreInstanceState(
                                savedState.getParcelable(ADAPTER_VIEW_STATE_BUNDLE_KEY));
                    }
                }

                ArrayList<Item<DataType>> restoredItems = null;

                if (savedState.containsKey(PARCELABLE_ITEMS_BUNDLE_KEY)) {
                    restoredItems = savedState.getParcelableArrayList(PARCELABLE_ITEMS_BUNDLE_KEY);
                } else if (savedState.containsKey(SERIALIZABLE_ITEMS_BUNDLE_KEY)) {
                    restoredItems = (ArrayList<Item<DataType>>) savedState
                            .getSerializable(SERIALIZABLE_ITEMS_BUNDLE_KEY);
                }

                if (restoredItems != null) {
                    for (Item<DataType> item : restoredItems) {
                        if (item != null) {
                            items.add(item);
                        }
                    }
                }

                parameters = savedState.getBundle(PARAMETERS_BUNDLE_KEY);
                allowDuplicates = savedState.getBoolean(ALLOW_DUPLICATES_BUNDLE_KEY, false);
                notifyOnChange = savedState.getBoolean(NOTIFY_ON_CHANGE_BUNDLE_KEY, true);
                setLogLevel(LogLevel.fromRank(
                        savedState.getInt(LOG_LEVEL_BUNDLE_KEY, LogLevel.ALL.getRank())));
                onRestoreInstanceState(savedState);
                notifyDataSetChanged();
                getLogger().logDebug(getClass(), "Restored instance state");
            } catch (Exception e) {
                throw new RestoreInstanceStateException(e);
            }
        } else {
            throw new RestoreInstanceStateException(
                    "Saved instance state does not contain bundle with key \"" + key + "\"");
        }
    }

    @CallSuper
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (allowDuplicates ? 1231 : 1237);
        result = prime * result + (notifyOnChange ? 1231 : 1237);
        result = prime * result + items.hashCode();
        result = prime * result + getLogLevel().getRank();
        result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
        return result;
    }

    @CallSuper
    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractListAdapter<?, ?> other = (AbstractListAdapter<?, ?>) obj;
        if (allowDuplicates != other.allowDuplicates)
            return false;
        if (notifyOnChange != other.notifyOnChange)
            return false;
        if (!items.equals(other.items))
            return false;
        if (!getLogLevel().equals(other.getLogLevel()))
            return false;
        if (parameters == null) {
            if (other.parameters != null)
                return false;
        } else if (!parameters.equals(other.parameters))
            return false;
        return true;
    }

    @Override
    public abstract AbstractListAdapter<DataType, DecoratorType> clone()
            throws CloneNotSupportedException;

}