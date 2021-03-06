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
package de.mrapp.android.adapter.list.enablestate;

import android.content.Context;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import de.mrapp.android.adapter.datastructure.UnmodifiableList;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.decorator.AbstractListDecorator;
import de.mrapp.android.adapter.list.AbstractListAdapter;
import de.mrapp.android.adapter.list.ListAdapterItemClickListener;
import de.mrapp.android.adapter.list.ListAdapterItemLongClickListener;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.util.logging.LogLevel;
import de.mrapp.util.Condition;

/**
 * An abstract base class for all adapters, whose underlying data is managed as a list of arbitrary
 * items, which may be disabled or enabled. Such an adapter's purpose is to provide the underlying
 * data for visualization using a {@link AbsListView} widget.
 *
 * @param <DataType>
 *         The type of the adapter's underlying data
 * @param <DecoratorType>
 *         The type of the decorator, which allows to customize the appearance of the views, which
 *         are used to visualize the items of the adapter
 * @author Michael Rapp
 * @since 0.1.0
 */
public abstract class AbstractEnableStateListAdapter<DataType, DecoratorType extends AbstractListDecorator<DataType>>
        extends AbstractListAdapter<DataType, DecoratorType>
        implements EnableStateListAdapter<DataType> {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * A set, which contains the listeners, which should be notified, when an item has been disabled
     * or enabled.
     */
    private transient Set<ListEnableStateListener<DataType>> enableStateListeners;

    /**
     * Notifies all listeners, which have been registered to be notified, when an item has been
     * disabled or enabled, about an item, which has been enabled.
     *
     * @param item
     *         The item, which has been enabled, as an instance of the generic type DataType. The
     *         item may not be null
     * @param index
     *         The index of the item, which has been enabled, as an {@link Integer} value. The index
     *         must be between 0 and the value of the method <code>getCount():int</code> - 1
     */
    private void notifyOnItemEnabled(@NonNull final DataType item, final int index) {
        for (ListEnableStateListener<DataType> listener : enableStateListeners) {
            listener.onItemEnabled(this, item, index);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when an item has been
     * disabled or enabled, about an item, which has been disabled.
     *
     * @param item
     *         The item, which has been disabled, as an instance of the generic type DataType. The
     *         item may not be null
     * @param index
     *         The index of the item, which has been disabled, as an {@link Integer} value. The
     *         index must be between 0 and the value of the method <code>getCount():int</code> - 1
     */
    private void notifyOnItemDisabled(@NonNull final DataType item, final int index) {
        for (ListEnableStateListener<DataType> listener : enableStateListeners) {
            listener.onItemDisabled(this, item, index);
        }
    }

    /**
     * Returns a set, which contains the listeners, which should be notified, when an item has been
     * disabled or enabled.
     *
     * @return A set, which contains the listeners, which should be notified, when an item has been
     * disabled or enabled, as an instance of the type {@link Set} or an empty set, if no listeners
     * should be notified
     */
    protected final Set<ListEnableStateListener<DataType>> getEnableStateListeners() {
        return enableStateListeners;
    }

    /**
     * Sets the set, which contains the listeners, which should be notified, when an item has been
     * disabled or enabled.
     *
     * @param enableStateListeners
     *         The set, which should be set, as an instance of the type {@link Set} or an empty set,
     *         if no listeners should be notified
     */
    protected final void setEnableStateListeners(
            @NonNull final Set<ListEnableStateListener<DataType>> enableStateListeners) {
        Condition.INSTANCE
                .ensureNotNull(enableStateListeners, "The enable state listeners may not be null");
        this.enableStateListeners = enableStateListeners;
    }

    /**
     * Creates a new adapter, whose underlying data is managed as a list of arbitrary items, which
     * may be disabled or enabled.
     *
     * @param context
     *         The context, the adapter should belong to, as an instance of the class {@link
     *         Context}. The context may not be null
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
     *         underlying data has been modified or an empty set, if no listeners should be
     *         notified
     * @param enableStateListeners
     *         A set, which contains the listeners, which should be notified, when an item has been
     *         disabled or enabled or an empty set, if no listeners should be notified
     */
    protected AbstractEnableStateListAdapter(@NonNull final Context context,
                                             @NonNull final DecoratorType decorator,
                                             @NonNull final LogLevel logLevel,
                                             @NonNull final ArrayList<Item<DataType>> items,
                                             final boolean allowDuplicates,
                                             final boolean notifyOnChange,
                                             @NonNull final Set<ListAdapterItemClickListener<DataType>> itemClickListeners,
                                             @NonNull final Set<ListAdapterItemLongClickListener<DataType>> itemLongClickListeners,
                                             @NonNull final Set<ListAdapterListener<DataType>> adapterListeners,
                                             @NonNull final Set<ListEnableStateListener<DataType>> enableStateListeners) {
        super(context, decorator, logLevel, items, allowDuplicates, notifyOnChange,
                itemClickListeners, itemLongClickListeners, adapterListeners);
        setEnableStateListeners(enableStateListeners);
    }

    @Override
    public final boolean areAllItemsEnabled() {
        return getEnabledItemCount() == getCount();
    }

    @Override
    public final boolean isEnabled(final int index) {
        return getItems().get(index).isEnabled();
    }

    @Override
    public final boolean isEnabled(@NonNull final DataType item) {
        return getItems().get(indexOf(item)).isEnabled();
    }

    @Override
    public final int getFirstEnabledIndex() {
        for (int i = 0; i < getCount(); i++) {
            if (getItems().get(i).isEnabled()) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final DataType getFirstEnabledItem() {
        int index = getFirstEnabledIndex();

        if (index != -1) {
            return getItem(index);
        } else {
            return null;
        }
    }

    @Override
    public final int getLastEnabledIndex() {
        for (int i = getCount() - 1; i >= 0; i--) {
            if (getItems().get(i).isEnabled()) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final DataType getLastEnabledItem() {
        int index = getLastEnabledIndex();

        if (index != -1) {
            return getItem(index);
        } else {
            return null;
        }
    }

    @Override
    public final int getFirstDisabledIndex() {
        for (int i = 0; i < getCount(); i++) {
            if (!getItems().get(i).isEnabled()) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final DataType getFirstDisabledItem() {
        int index = getFirstDisabledIndex();

        if (index != -1) {
            return getItem(index);
        } else {
            return null;
        }
    }

    @Override
    public final int getLastDisabledIndex() {
        for (int i = getCount() - 1; i >= 0; i--) {
            if (!getItems().get(i).isEnabled()) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final DataType getLastDisabledItem() {
        int index = getLastDisabledIndex();

        if (index != -1) {
            return getItem(index);
        } else {
            return null;
        }
    }

    @Override
    public final List<Integer> getEnabledIndices() {
        List<Integer> enabledIndices = new ArrayList<>();

        for (int i = 0; i < getCount(); i++) {
            if (getItems().get(i).isEnabled()) {
                enabledIndices.add(i);
            }
        }

        return new UnmodifiableList<>(enabledIndices);
    }

    @Override
    public final List<DataType> getEnabledItems() {
        List<DataType> enabledItems = new ArrayList<>();

        for (Item<DataType> item : getItems()) {
            if (item.isEnabled()) {
                enabledItems.add(item.getData());
            }
        }

        return new UnmodifiableList<>(enabledItems);
    }

    @Override
    public final List<Integer> getDisabledIndices() {
        List<Integer> disabledIndices = new ArrayList<>();

        for (int i = 0; i < getCount(); i++) {
            if (!getItems().get(i).isEnabled()) {
                disabledIndices.add(i);
            }
        }

        return new UnmodifiableList<>(disabledIndices);
    }

    @Override
    public final List<DataType> getDisabledItems() {
        List<DataType> disabledItems = new ArrayList<>();

        for (Item<DataType> item : getItems()) {
            if (!item.isEnabled()) {
                disabledItems.add(item.getData());
            }
        }

        return new UnmodifiableList<>(disabledItems);
    }

    @Override
    public final int getEnabledItemCount() {
        return getEnabledItems().size();
    }

    @Override
    public final void setEnabled(final int index, final boolean enabled) {
        Item<DataType> item = getItems().get(index);

        if (item.isEnabled() != enabled) {
            item.setEnabled(enabled);

            if (enabled) {
                notifyOnItemEnabled(item.getData(), index);
            } else {
                notifyOnItemDisabled(item.getData(), index);
            }

            notifyObserversOnItemChanged(index);
            String message = enabled ? "Enabled" :
                    "Disabled" + " item \"" + item.getData() + "\" at index " + index;
            getLogger().logInfo(getClass(), message);
        } else {
            String message =
                    "The enable state of item \"" + item.getData() + "\" at index " + index +
                            " has not been changed, because it is already " +
                            (enabled ? "enabled" : "disabled");
            getLogger().logDebug(getClass(), message);
        }
    }

    @Override
    public final void setEnabled(@NonNull final DataType item, final boolean enabled) {
        setEnabled(indexOfOrThrowException(item), enabled);
    }

    @Override
    public final boolean triggerEnableState(final int index) {
        boolean enabled = !isEnabled(index);
        setEnabled(index, enabled);
        return enabled;
    }

    @Override
    public final boolean triggerEnableState(@NonNull final DataType item) {
        return triggerEnableState(indexOfOrThrowException(item));
    }

    @Override
    public final void setAllEnabled(final boolean enabled) {
        for (int i = 0; i < getCount(); i++) {
            setEnabled(i, enabled);
        }
    }

    @Override
    public final void triggerAllEnableStates() {
        for (int i = 0; i < getCount(); i++) {
            triggerEnableState(i);
        }
    }

    @Override
    public final void addEnableStateListener(
            @NonNull final ListEnableStateListener<DataType> listener) {
        Condition.INSTANCE.ensureNotNull(listener, "The listener may not be null");
        enableStateListeners.add(listener);
        String message = "Added enable state listener \"" + listener + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void removeEnableStateListener(
            @NonNull final ListEnableStateListener<DataType> listener) {
        Condition.INSTANCE.ensureNotNull(listener, "The listener may not be null");
        enableStateListeners.remove(listener);
        String message = "Removed enable state listener \"" + listener + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public abstract AbstractEnableStateListAdapter<DataType, DecoratorType> clone()
            throws CloneNotSupportedException;

}