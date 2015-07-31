/*
 * AndroidAdapters Copyright 2014 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>. 
 */
package de.mrapp.android.adapter.expandablelist.sortable;

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;

import android.content.Context;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.Order;
import de.mrapp.android.adapter.datastructure.group.Group;
import de.mrapp.android.adapter.datastructure.group.GroupComparator;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterItemClickListener;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterListener;
import de.mrapp.android.adapter.expandablelist.ExpansionListener;
import de.mrapp.android.adapter.expandablelist.enablestate.ExpandableListEnableStateListener;
import de.mrapp.android.adapter.expandablelist.itemstate.AbstractItemStateExpandableListAdapter;
import de.mrapp.android.adapter.expandablelist.itemstate.ExpandableListItemStateListener;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.logging.LogLevel;

/**
 * An abstract base class for all adapters, whose underlying data is managed as
 * a sortable list of arbitrary group and child items. Such an adapter's purpose
 * is to provide the underlying data for visualization using a
 * {@link ExpandableListView} widget.
 * 
 * @param <GroupType>
 *            The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *            The type of the underlying data of the adapter's child items
 * @param <DecoratorType>
 *            The type of the decorator, which allows to customize the
 *            appearance of the views, which are used to visualize the group and
 *            child items of the adapter
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public abstract class AbstractSortableExpandableListAdapter<GroupType, ChildType, DecoratorType>
		extends AbstractItemStateExpandableListAdapter<GroupType, ChildType, DecoratorType>
		implements SortableExpandableListAdapter<GroupType, ChildType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A set, which contains the listeners, which should be notified, when the
	 * adapter's underlying data has been sorted.
	 */
	private transient Set<ExpandableListSortingListener<GroupType, ChildType>> sortingListeners;

	/**
	 * Notifies all listeners, which have been registered to be notified, when
	 * the adapter's underlying data has been sorted, about the group items
	 * being sorted.
	 * 
	 * @param sortedGroups
	 *            A collection, which contains the adapter's sorted group items,
	 *            as an instance of the type {@link Collection} or an empty
	 *            collection, if the adapter does not contain any items
	 * @param order
	 *            The order, which has been used to sort the adapter's group
	 *            items, as a value of the enum {@link Order}. The order may
	 *            either be <code>ASCENDING</code> or <code>DESCENDING</code>
	 * @param comparator
	 *            The comparator, which has been used to compare the single
	 *            group items, as an instance of the type {@link Comparator} or
	 *            null, if the group items' implementation of the type
	 *            {@link Comparable} has been used instead
	 */
	private void notifyOnGroupsSorted(final Collection<GroupType> sortedGroups, final Order order,
			final Comparator<GroupType> comparator) {
		for (ExpandableListSortingListener<GroupType, ChildType> listener : sortingListeners) {
			listener.onGroupsSorted(this, sortedGroups, order, comparator);
		}
	}

	/**
	 * Notifies all listeners, which have been registered to be notified, when
	 * the adapter's underlying data has been sorted, about child items of a
	 * specific group being sorted.
	 * 
	 * @param sortedChildren
	 *            A collection, which contains the sorted child items, as an
	 *            instance of the type {@link Collection} or an empty
	 *            collection, if the adapter does not contain any items
	 * @param order
	 *            The order, which has been used to sort the child items, as a
	 *            value of the enum {@link Order}. The order may either be
	 *            <code>ASCENDING</code> or <code>DESCENDING</code>
	 * @param comparator
	 *            The comparator, which has been used to compare the single
	 *            child items, as an instance of the type {@link Comparator} or
	 *            null, if the group items' implementation of the type
	 *            {@link Comparable} has been used instead
	 * @param group
	 *            The group, the child items, which have been sorted, belong to,
	 *            as an instance of the generic type GroupType
	 * @param groupIndex
	 *            The index of the group, the child items, which have been
	 *            sorted, belong to, as an {@link Integer} value
	 */
	private void notifyOnChildrenSorted(final Collection<ChildType> sortedChildren, final Order order,
			final Comparator<ChildType> comparator, final GroupType group, final int groupIndex) {
		for (ExpandableListSortingListener<GroupType, ChildType> listener : sortingListeners) {
			listener.onChildrenSorted(this, sortedChildren, order, comparator, group, groupIndex);
		}
	}

	/**
	 * Returns a set, which contains the listeners, which should be notified,
	 * when the adapter's underlying data has been sorted.
	 * 
	 * @return A set, which contains the listeners, which should be notified,
	 *         when the adapter's underlying data has been filtered, as an
	 *         instance of the type {@link Set} or an empty set, if no listeners
	 *         should be notified
	 */
	protected final Set<ExpandableListSortingListener<GroupType, ChildType>> getSortingListeners() {
		return sortingListeners;
	}

	/**
	 * Sets the set, which contains the listeners, which should be notified,
	 * when the adapter's underlying data has been sorted.
	 * 
	 * @param sortingListeners
	 *            The set, which should be set, as an instance of the type
	 *            {@link Set} or an empty set, if no listeners should be
	 *            notified
	 */
	protected final void setSortingListeners(
			final Set<ExpandableListSortingListener<GroupType, ChildType>> sortingListeners) {
		ensureNotNull(sortingListeners, "The sorting listeners may not be null");
		this.sortingListeners = sortingListeners;
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary group and child items, which may have multiple states.
	 * 
	 * @param context
	 *            The context, the adapter belongs to, as an instance of the
	 *            class {@link Context}. The context may not be null
	 * @param groupInflater
	 *            The inflater, which should be used to inflate the views, which
	 *            are used to visualize the adapter's group items, as an
	 *            instance of the type {@link Inflater}. The inflater may not be
	 *            null
	 * @param childInflater
	 *            The inflater, which should be used to inflate the views, which
	 *            are used to visualize the adapter's child items, as an
	 *            instance of the type {@link Inflater}. The inflater may not be
	 *            null
	 * @param decorator
	 *            The decorator, which should be used to customize the
	 *            appearance of the views, which are used to visualize the group
	 *            and child items of the adapter, as an instance of the generic
	 *            type DecoratorType. The decorator may not be null
	 * @param logLevel
	 *            The log level, which should be used for logging, as a value of
	 *            the enum {@link LogLevel}. The log level may not be null
	 * @param groupAdapter
	 *            The adapter, which should manage the adapter's group items, as
	 *            an instance of the type {@link MultipleChoiceListAdapter}. The
	 *            adapter may not be null
	 * @param allowDuplicateChildren
	 *            True, if duplicate group items, regardless from the group they
	 *            belong to, should be allowed, false otherwise
	 * @param notifyOnChange
	 *            True, if the method <code>notifyDataSetChanged():void</code>
	 *            should be automatically called when the adapter's underlying
	 *            data has been changed, false otherwise
	 * @param expandGroupOnClick
	 *            True, if a group should be expanded, when it is clicked by the
	 *            user, false otherwise
	 * @param itemClickListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when an item of the adapter has been clicked by the user, as
	 *            an instance of the type {@link Set} or an empty set, if no
	 *            listeners should be notified
	 * @param adapterListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been modified, as an
	 *            instance of the type {@link Set} or an empty set, if no
	 *            listeners should be notified
	 * @param expansionListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when a group item has been expanded or collapsed, as an
	 *            instance of the type {@link Set} or an empty set, if no
	 *            listeners should be notified
	 * @param enableStateListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when an item has been disabled or enabled, as an instance of
	 *            the type {@link Set} or an empty set, if no listeners should
	 *            be notified
	 * @param numberOfGroupStates
	 *            The number of states, the adapter's group items may have, as
	 *            an {@link Integer} value. The value must be at least 1
	 * @param numberOfChildStates
	 *            The number of states, the adapter's child items may have, as
	 *            an {@link Integer} value. The value must be at least 1
	 * @param triggerGroupStateOnClick
	 *            True, if the state of a group item should be triggered, when
	 *            it is clicked by the user, false otherwise
	 * @param triggerChildStateOnClick
	 *            True, if the state of a child item should be triggered, when
	 *            it is clicked by the user, false otherwise
	 * @param itemStateListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the state of an item has been changed or an empty set, if
	 *            no listeners should be notified
	 * @param sortingListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been sorted or an empty
	 *            set, if no listeners should be notified
	 */
	protected AbstractSortableExpandableListAdapter(final Context context, final Inflater groupInflater,
			final Inflater childInflater, final DecoratorType decorator, final LogLevel logLevel,
			final MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter,
			final boolean allowDuplicateChildren, final boolean notifyOnChange, final boolean expandGroupOnClick,
			final Set<ExpandableListAdapterItemClickListener<GroupType, ChildType>> itemClickListeners,
			final Set<ExpandableListAdapterListener<GroupType, ChildType>> adapterListeners,
			final Set<ExpansionListener<GroupType, ChildType>> expansionListeners,
			final Set<ExpandableListEnableStateListener<GroupType, ChildType>> enableStateListeners,
			final int numberOfGroupStates, final int numberOfChildStates, final boolean triggerGroupStateOnClick,
			final boolean triggerChildStateOnClick,
			final Set<ExpandableListItemStateListener<GroupType, ChildType>> itemStateListeners,
			final Set<ExpandableListSortingListener<GroupType, ChildType>> sortingListeners) {
		super(context, groupInflater, childInflater, decorator, logLevel, groupAdapter, allowDuplicateChildren,
				notifyOnChange, expandGroupOnClick, itemClickListeners, adapterListeners, expansionListeners,
				enableStateListeners, numberOfGroupStates, numberOfChildStates, triggerGroupStateOnClick,
				triggerChildStateOnClick, itemStateListeners);
		setSortingListeners(sortingListeners);
	}

	@Override
	public final void sortGroups() {
		sortGroups(Order.ASCENDING);
	}

	@Override
	public final void sortGroups(final Order order) {
		getGroupAdapter().sort(order);

		if (order == Order.ASCENDING) {
			String message = "Sorted groups in ascending order";
			getLogger().logInfo(getClass(), message);
		} else {
			String message = "Sorted groups in descending order";
			getLogger().logInfo(getClass(), message);
		}

		notifyOnGroupsSorted(getAllGroups(), order, null);
	}

	@Override
	public final void sortGroups(final Comparator<GroupType> comparator) {
		sortGroups(Order.ASCENDING, comparator);
	}

	@Override
	public final void sortGroups(final Order order, final Comparator<GroupType> comparator) {
		Comparator<Group<GroupType, ChildType>> groupComparator = new GroupComparator<>(comparator);
		getGroupAdapter().sort(order, groupComparator);

		if (order == Order.ASCENDING) {
			String message = "Sorted groups in ascending order using the comparator \""
					+ comparator.getClass().getSimpleName() + "\"";
			getLogger().logInfo(getClass(), message);
		} else {
			String message = "Sorted groups in descending order using the comparator \""
					+ comparator.getClass().getSimpleName() + "\"";
			getLogger().logInfo(getClass(), message);
		}

		notifyOnGroupsSorted(getAllGroups(), order, comparator);
	}

	@Override
	public final void sortChildren() {
		sortChildren(Order.ASCENDING);
	}

	@Override
	public final void sortChildren(final Order order) {
		for (int i = 0; i < getNumberOfGroups(); i++) {
			sortChildren(i, order);
		}
	}

	@Override
	public final void sortChildren(final Comparator<ChildType> comparator) {
		sortChildren(Order.ASCENDING, comparator);
	}

	@Override
	public final void sortChildren(final Order order, final Comparator<ChildType> comparator) {
		for (int i = 0; i < getNumberOfGroups(); i++) {
			sortChildren(i, order, comparator);
		}
	}

	@Override
	public final void sortChildren(final int groupIndex) {
		sortChildren(groupIndex, Order.ASCENDING);
	}

	@Override
	public final void sortChildren(final int groupIndex, final Order order) {
		Group<GroupType, ChildType> group = getGroupAdapter().getItem(groupIndex);
		group.getChildAdapter().sort(order);

		if (order == Order.ASCENDING) {
			String message = "Sorted children of group \"" + group.getData() + "\" in ascending order";
			getLogger().logInfo(getClass(), message);
		} else {
			String message = "Sorted children of group \"" + group.getData() + "\" in descending order";
			getLogger().logInfo(getClass(), message);
		}

		notifyOnChildrenSorted(getAllChildren(groupIndex), order, null, group.getData(), groupIndex);
	}

	@Override
	public final void sortChildren(final int groupIndex, final Comparator<ChildType> comparator) {
		sortChildren(groupIndex, Order.ASCENDING, comparator);
	}

	@Override
	public final void sortChildren(final int groupIndex, final Order order, final Comparator<ChildType> comparator) {
		Group<GroupType, ChildType> group = getGroupAdapter().getItem(groupIndex);
		group.getChildAdapter().sort(order, comparator);

		if (order == Order.ASCENDING) {
			String message = "Sorted children of group \"" + group.getData()
					+ "\" in ascending order using the comparator \"" + comparator.getClass().getSimpleName() + "\"";
			getLogger().logInfo(getClass(), message);
		} else {
			String message = "Sorted children of group \"" + group.getData()
					+ "\" in descending order using the comparator \"" + comparator.getClass().getSimpleName() + "\"";
			getLogger().logInfo(getClass(), message);
		}

		notifyOnChildrenSorted(getAllChildren(groupIndex), order, null, group.getData(), groupIndex);
	}

	@Override
	public final void sortChildren(final GroupType group) {
		sortChildren(group, Order.ASCENDING);
	}

	@Override
	public final void sortChildren(final GroupType group, final Order order) {
		sortChildren(indexOfGroupOrThrowException(group), order);
	}

	@Override
	public final void sortChildren(final GroupType group, final Comparator<ChildType> comparator) {
		sortChildren(group, Order.ASCENDING, comparator);
	}

	@Override
	public final void sortChildren(final GroupType group, final Order order, final Comparator<ChildType> comparator) {
		sortChildren(indexOfGroupOrThrowException(group), order, comparator);
	}

	@Override
	public final void addSortingListener(final ExpandableListSortingListener<GroupType, ChildType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		sortingListeners.add(listener);
	}

	@Override
	public final void removeSortingListener(final ExpandableListSortingListener<GroupType, ChildType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		sortingListeners.remove(listener);
	}

	@Override
	public abstract AbstractSortableExpandableListAdapter<GroupType, ChildType, DecoratorType> clone()
			throws CloneNotSupportedException;

}