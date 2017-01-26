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
package de.mrapp.android.adapter.expandablelist.sortable;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.widget.ExpandableListView;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;

import de.mrapp.android.adapter.expandablelist.ExpandableListAdapter;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.Order;
import de.mrapp.android.adapter.datastructure.group.Group;
import de.mrapp.android.adapter.datastructure.group.GroupComparator;
import de.mrapp.android.adapter.decorator.AbstractExpandableListDecorator;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterItemClickListener;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterItemLongClickListener;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterListener;
import de.mrapp.android.adapter.expandablelist.ExpansionListener;
import de.mrapp.android.adapter.expandablelist.enablestate.ExpandableListEnableStateListener;
import de.mrapp.android.adapter.expandablelist.itemstate.AbstractItemStateExpandableListAdapter;
import de.mrapp.android.adapter.expandablelist.itemstate.ExpandableListItemStateListener;
import de.mrapp.android.adapter.logging.LogLevel;

import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * An abstract base class for all adapters, whose underlying data is managed as a sortable list of
 * arbitrary group and child items. Such an adapter's purpose is to provide the underlying data for
 * visualization using a {@link ExpandableListView} widget.
 *
 * @param <GroupType>
 *         The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the adapter's child items
 * @param <DecoratorType>
 *         The type of the decorator, which allows to customize the appearance of the views, which
 *         are used to visualize the group and child items of the adapter
 * @author Michael Rapp
 * @since 0.1.0
 */
public abstract class AbstractSortableExpandableListAdapter<GroupType, ChildType, DecoratorType extends AbstractExpandableListDecorator<GroupType, ChildType>>
        extends AbstractItemStateExpandableListAdapter<GroupType, ChildType, DecoratorType>
        implements SortableExpandableListAdapter<GroupType, ChildType> {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The key, which is used to store the current order of all of the adapter's child items,
     * regardless of the group they belong to, within a bundle.
     */
    @VisibleForTesting
    protected static final String CHILD_ORDER_BUNDLE_KEY =
            AbstractSortableExpandableListAdapter.class.getSimpleName() + "::ChildOrder";

    /**
     * The current order of all of the adapter's child items, regardless of the group they belong
     * to.
     */
    private Order childOrder;

    /**
     * A set, which contains the listeners, which should be notified, when the adapter's underlying
     * data has been sorted.
     */
    private transient Set<ExpandableListSortingListener<GroupType, ChildType>> sortingListeners;

    /**
     * Notifies all listeners, which have been registered to be notified, when the adapter's
     * underlying data has been sorted, about the group items being sorted.
     *
     * @param sortedGroups
     *         A collection, which contains the adapter's sorted group items, as an instance of the
     *         type {@link Collection} or an empty collection, if the adapter does not contain any
     *         items
     * @param order
     *         The order, which has been used to sort the adapter's group items, as a value of the
     *         enum {@link Order}. The order may either be <code>ASCENDING</code> or
     *         <code>DESCENDING</code>
     * @param comparator
     *         The comparator, which has been used to compare the single group items, as an instance
     *         of the type {@link Comparator} or null, if the group items' implementation of the
     *         type {@link Comparable} has been used instead
     */
    private void notifyOnGroupsSorted(@NonNull final Collection<GroupType> sortedGroups,
                                      @NonNull final Order order,
                                      @Nullable final Comparator<GroupType> comparator) {
        for (ExpandableListSortingListener<GroupType, ChildType> listener : sortingListeners) {
            listener.onGroupsSorted(this, sortedGroups, order, comparator);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when the adapter's
     * underlying data has been sorted, about child items of a specific group being sorted.
     *
     * @param sortedChildren
     *         A collection, which contains the sorted child items, as an instance of the type
     *         {@link Collection} or an empty collection, if the adapter does not contain any items
     * @param order
     *         The order, which has been used to sort the child items, as a value of the enum {@link
     *         Order}. The order may either be <code>ASCENDING</code> or <code>DESCENDING</code>
     * @param comparator
     *         The comparator, which has been used to compare the single child items, as an instance
     *         of the type {@link Comparator} or null, if the group items' implementation of the
     *         type {@link Comparable} has been used instead
     * @param group
     *         The group, the child items, which have been sorted, belong to, as an instance of the
     *         generic type GroupType
     * @param groupIndex
     *         The index of the group, the child items, which have been sorted, belong to, as an
     *         {@link Integer} value
     */
    private void notifyOnChildrenSorted(@NonNull final Collection<ChildType> sortedChildren,
                                        @NonNull final Order order,
                                        @Nullable final Comparator<ChildType> comparator,
                                        @NonNull final GroupType group, final int groupIndex) {
        for (ExpandableListSortingListener<GroupType, ChildType> listener : sortingListeners) {
            listener.onChildrenSorted(this, sortedChildren, order, comparator, group, groupIndex);
        }
    }

    /**
     * Creates and returns a listener, which allows to invalidate the current order of all of the
     * adapter's child items, regardless of the group they belong to, when its underlying data is
     * changed.
     *
     * @return The adapter, which has been created, as an instance of the type {@link
     * ExpandableListAdapterListener}
     */
    private ExpandableListAdapterListener<GroupType, ChildType> createAdapterListener() {
        return new ExpandableListAdapterListener<GroupType, ChildType>() {

            @Override
            public void onGroupAdded(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final GroupType group, final int index) {

            }

            @Override
            public void onGroupRemoved(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final GroupType group, final int index) {

            }

            @Override
            public void onChildAdded(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final ChildType child, final int childIndex,
                    @NonNull final GroupType group, final int groupIndex) {
                childOrder = null;
            }

            @Override
            public void onChildRemoved(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final ChildType child, final int childIndex,
                    @NonNull final GroupType group, final int groupIndex) {
                childOrder = null;
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
    protected final Set<ExpandableListSortingListener<GroupType, ChildType>> getSortingListeners() {
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
            @NonNull final Set<ExpandableListSortingListener<GroupType, ChildType>> sortingListeners) {
        ensureNotNull(sortingListeners, "The sorting listeners may not be null");
        this.sortingListeners = sortingListeners;
    }

    /**
     * Creates a new adapter, whose underlying data is managed as a sortable list of arbitrary group
     * and child items.
     *
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param decorator
     *         The decorator, which should be used to customize the appearance of the views, which
     *         are used to visualize the group and child items of the adapter, as an instance of the
     *         generic type DecoratorType. The decorator may not be null
     * @param logLevel
     *         The log level, which should be used for logging, as a value of the enum {@link
     *         LogLevel}. The log level may not be null
     * @param groupAdapter
     *         The adapter, which should manage the adapter's group items, as an instance of the
     *         type {@link MultipleChoiceListAdapter}. The adapter may not be null
     * @param allowDuplicateChildren
     *         True, if duplicate child items, regardless from the group they belong to, should be
     *         allowed, false otherwise
     * @param notifyOnChange
     *         True, if the method <code>notifyDataSetChanged():void</code> should be automatically
     *         called when the adapter's underlying data has been changed, false otherwise
     * @param triggerGroupExpansionOnClick
     *         True, if a group's expansion should be triggered, when it is clicked by the user,
     *         false otherwise
     * @param itemClickListeners
     *         A set, which contains the listeners, which should be notified, when an item of the
     *         adapter has been clicked by the user, as an instance of the type {@link Set}, or an
     *         empty set, if no listeners should be notified
     * @param itemLongClickListeners
     *         A set, which contains the listeners, which should be notified, when an item of the
     *         adapter has been long-clicked by the user, as an instance of the type {@link Set}, or
     *         an empty set, if no listeners should be notified
     * @param adapterListeners
     *         A set, which contains the listeners, which should be notified, when the adapter's
     *         underlying data has been modified, as an instance of the type {@link Set}, or an
     *         empty set, if no listeners should be notified
     * @param expansionListeners
     *         A set, which contains the listeners, which should be notified, when a group item has
     *         been expanded or collapsed, as an instance of the type {@link Set}, or an empty set,
     *         if no listeners should be notified
     * @param setChildEnableStatesImplicitly
     *         True, if the enable states of children should be also set, when the enable state of
     *         the group, they belong to, is set
     * @param enableStateListeners
     *         A set, which contains the listeners, which should be notified, when an item has been
     *         disabled or enabled, as an instance of the type {@link Set}, or an empty set, if no
     *         listeners should be notified
     * @param numberOfGroupStates
     *         The number of states, the adapter's group items may have, as an {@link Integer}
     *         value. The value must be at least 1
     * @param numberOfChildStates
     *         The number of states, the adapter's child items may have, as an {@link Integer}
     *         value. The value must be at least 1
     * @param triggerGroupStateOnClick
     *         True, if the state of a group item should be triggered, when it is clicked by the
     *         user, false otherwise
     * @param triggerChildStateOnClick
     *         True, if the state of a child item should be triggered, when it is clicked by the
     *         user, false otherwise
     * @param setChildStatesImplicitly
     *         True, if the states of children should be also set, when the state of the group, they
     *         belong to, is set, false otherwise
     * @param itemStateListeners
     *         A set, which contains the listeners, which should be notified, when the state of an
     *         item has been changed, or an empty set, if no listeners should be notified
     * @param sortingListeners
     *         A set, which contains the listeners, which should be notified, when the adapter's
     *         underlying data has been sorted, or an empty set, if no listeners should be notified
     */
    protected AbstractSortableExpandableListAdapter(@NonNull final Context context,
                                                    @NonNull final DecoratorType decorator,
                                                    @NonNull final LogLevel logLevel,
                                                    @NonNull final MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter,
                                                    final boolean allowDuplicateChildren,
                                                    final boolean notifyOnChange,
                                                    final boolean triggerGroupExpansionOnClick,
                                                    @NonNull final Set<ExpandableListAdapterItemClickListener<GroupType, ChildType>> itemClickListeners,
                                                    @NonNull final Set<ExpandableListAdapterItemLongClickListener<GroupType, ChildType>> itemLongClickListeners,
                                                    @NonNull final Set<ExpandableListAdapterListener<GroupType, ChildType>> adapterListeners,
                                                    @NonNull final Set<ExpansionListener<GroupType, ChildType>> expansionListeners,
                                                    final boolean setChildEnableStatesImplicitly,
                                                    @NonNull final Set<ExpandableListEnableStateListener<GroupType, ChildType>> enableStateListeners,
                                                    final int numberOfGroupStates,
                                                    final int numberOfChildStates,
                                                    final boolean triggerGroupStateOnClick,
                                                    final boolean triggerChildStateOnClick,
                                                    final boolean setChildStatesImplicitly,
                                                    @NonNull final Set<ExpandableListItemStateListener<GroupType, ChildType>> itemStateListeners,
                                                    @NonNull final Set<ExpandableListSortingListener<GroupType, ChildType>> sortingListeners) {
        super(context, decorator, logLevel, groupAdapter, allowDuplicateChildren, notifyOnChange,
                triggerGroupExpansionOnClick, itemClickListeners, itemLongClickListeners,
                adapterListeners, expansionListeners, setChildEnableStatesImplicitly,
                enableStateListeners, numberOfGroupStates, numberOfChildStates,
                triggerGroupStateOnClick, triggerChildStateOnClick, setChildStatesImplicitly,
                itemStateListeners);
        this.childOrder = null;
        setSortingListeners(sortingListeners);
        addAdapterListener(createAdapterListener());
    }

    @CallSuper
    @Override
    protected void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CHILD_ORDER_BUNDLE_KEY, getChildOrder());
    }

    @CallSuper
    @Override
    protected void onRestoreInstanceState(@NonNull final Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        this.childOrder = (Order) savedState.getSerializable(CHILD_ORDER_BUNDLE_KEY);
    }

    @Override
    public final void sortGroups() {
        sortGroups(Order.ASCENDING);
    }

    @Override
    public final void sortGroups(@NonNull final Order order) {
        getGroupAdapter().sort(order);

        if (order == Order.ASCENDING) {
            String message = "Sorted groups in ascending order";
            getLogger().logInfo(getClass(), message);
        } else {
            String message = "Sorted groups in descending order";
            getLogger().logInfo(getClass(), message);
        }

        notifyObserversOnDataSetChanged();
        notifyOnGroupsSorted(getAllGroups(), order, null);
    }

    @Override
    public final void sortGroups(@NonNull final Comparator<GroupType> comparator) {
        sortGroups(Order.ASCENDING, comparator);
    }

    @Override
    public final void sortGroups(@NonNull final Order order,
                                 @NonNull final Comparator<GroupType> comparator) {
        Comparator<Group<GroupType, ChildType>> groupComparator = new GroupComparator<>(comparator);
        getGroupAdapter().sort(order, groupComparator);

        if (order == Order.ASCENDING) {
            String message = "Sorted groups in ascending order using the comparator \"" +
                    comparator.getClass().getSimpleName() + "\"";
            getLogger().logInfo(getClass(), message);
        } else {
            String message = "Sorted groups in descending order using the comparator \"" +
                    comparator.getClass().getSimpleName() + "\"";
            getLogger().logInfo(getClass(), message);
        }

        notifyObserversOnDataSetChanged();
        notifyOnGroupsSorted(getAllGroups(), order, comparator);
    }

    @Override
    public final Order getGroupOrder() {
        return getGroupAdapter().getOrder();
    }

    @Override
    public final void sortChildren() {
        sortChildren(Order.ASCENDING);
    }

    @Override
    public final void sortChildren(@NonNull final Order order) {
        childOrder = order;

        for (int i = 0; i < getGroupCount(); i++) {
            sortChildren(i, order);
        }
    }

    @Override
    public final void sortChildren(@NonNull final Comparator<ChildType> comparator) {
        sortChildren(Order.ASCENDING, comparator);
    }

    @Override
    public final void sortChildren(@NonNull final Order order,
                                   @NonNull final Comparator<ChildType> comparator) {
        childOrder = order;

        for (int i = 0; i < getGroupCount(); i++) {
            sortChildren(i, order, comparator);
        }
    }

    @Override
    public final void sortChildren(final int groupIndex) {
        sortChildren(groupIndex, Order.ASCENDING);
    }

    @Override
    public final void sortChildren(final int groupIndex, @NonNull final Order order) {
        Group<GroupType, ChildType> group = getGroupAdapter().getItem(groupIndex);
        group.getChildAdapter().sort(order);

        if (order == Order.ASCENDING) {
            String message =
                    "Sorted children of group \"" + group.getData() + "\" in ascending order";
            getLogger().logInfo(getClass(), message);
        } else {
            String message =
                    "Sorted children of group \"" + group.getData() + "\" in descending order";
            getLogger().logInfo(getClass(), message);
        }

        notifyObserversOnDataSetChanged();
        notifyOnChildrenSorted(getAllChildren(groupIndex), order, null, group.getData(),
                groupIndex);
    }

    @Override
    public final void sortChildren(final int groupIndex,
                                   @NonNull final Comparator<ChildType> comparator) {
        sortChildren(groupIndex, Order.ASCENDING, comparator);
    }

    @Override
    public final void sortChildren(final int groupIndex, @NonNull final Order order,
                                   @NonNull final Comparator<ChildType> comparator) {
        Group<GroupType, ChildType> group = getGroupAdapter().getItem(groupIndex);
        group.getChildAdapter().sort(order, comparator);

        if (order == Order.ASCENDING) {
            String message = "Sorted children of group \"" + group.getData() +
                    "\" in ascending order using the comparator \"" +
                    comparator.getClass().getSimpleName() + "\"";
            getLogger().logInfo(getClass(), message);
        } else {
            String message = "Sorted children of group \"" + group.getData() +
                    "\" in descending order using the comparator \"" +
                    comparator.getClass().getSimpleName() + "\"";
            getLogger().logInfo(getClass(), message);
        }

        notifyObserversOnDataSetChanged();
        notifyOnChildrenSorted(getAllChildren(groupIndex), order, null, group.getData(),
                groupIndex);
    }

    @Override
    public final void sortChildren(@NonNull final GroupType group) {
        sortChildren(group, Order.ASCENDING);
    }

    @Override
    public final void sortChildren(@NonNull final GroupType group, @NonNull final Order order) {
        sortChildren(indexOfGroupOrThrowException(group), order);
    }

    @Override
    public final void sortChildren(@NonNull final GroupType group,
                                   @NonNull final Comparator<ChildType> comparator) {
        sortChildren(group, Order.ASCENDING, comparator);
    }

    @Override
    public final void sortChildren(@NonNull final GroupType group, @NonNull final Order order,
                                   @NonNull final Comparator<ChildType> comparator) {
        sortChildren(indexOfGroupOrThrowException(group), order, comparator);
    }

    @Override
    public final Order getChildOrder() {
        return childOrder;
    }

    @Override
    public final Order getChildOrder(@NonNull final GroupType group) {
        return getChildOrder(indexOfGroupOrThrowException(group));
    }

    @Override
    public final Order getChildOrder(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getOrder();
    }

    @Override
    public final void addSortingListener(
            @NonNull final ExpandableListSortingListener<GroupType, ChildType> listener) {
        ensureNotNull(listener, "The listener may not be null");
        sortingListeners.add(listener);
        String message = "Added sorting listener \"" + listener + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void removeSortingListener(
            @NonNull final ExpandableListSortingListener<GroupType, ChildType> listener) {
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
        result = prime * result + ((childOrder == null) ? 0 : childOrder.hashCode());
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
        AbstractSortableExpandableListAdapter<?, ?, ?> other =
                (AbstractSortableExpandableListAdapter<?, ?, ?>) obj;
        if (childOrder != other.childOrder)
            return false;
        return true;
    }

    @Override
    public abstract AbstractSortableExpandableListAdapter<GroupType, ChildType, DecoratorType> clone()
            throws CloneNotSupportedException;

}