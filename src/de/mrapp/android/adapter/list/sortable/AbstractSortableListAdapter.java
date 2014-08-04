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
package de.mrapp.android.adapter.list.sortable;

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import de.mrapp.android.adapter.datastructure.SerializableWrapper;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.datastructure.item.ItemComparator;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.itemstate.AbstractItemStateListAdapter;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
import de.mrapp.android.adapter.util.VisibleForTesting;

/**
 * An abstract base class for all adapters, whose underlying data is managed as
 * a sortable list of arbitrary items. Such an adapter's purpose is to provide
 * the underlying data for visualization using a {@link ListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * @param <DecoratorType>
 *            The type of the decorator, which allows to customize the
 *            appearance of the views, which are used to visualize the items of
 *            the adapter
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public abstract class AbstractSortableListAdapter<DataType, DecoratorType>
		extends AbstractItemStateListAdapter<DataType, DecoratorType> implements
		SortableListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The key, which is used to store the listeners, which should be notified,
	 * when the adapter's underlying data has been sorted, within a bundle.
	 */
	@VisibleForTesting
	protected static final String SORTING_LISTENERS_BUNDLE_KEY = AbstractSortableListAdapter.class
			.getSimpleName() + "::SortingListeners";

	/**
	 * A set, which contains the listeners, which should be notified, when the
	 * adapter's underlying data has been sorted.
	 */
	private Set<ListSortingListener<DataType>> sortingListeners;

	/**
	 * Notifies all listeners, which have been registered to be notified, when
	 * the adapter's underlying data has been sorted.
	 * 
	 * @param sortedItems
	 *            A collection, which contains the adapter's sorted items, as an
	 *            instance of the type {@link Collection} or an empty
	 *            collection, if the adapter does not contain any items
	 * @param order
	 *            The order, which has been used to sort the list, as a value of
	 *            the enum {@link Order}. The order may either be
	 *            <code>ASCENDING</code> or <code>DESCENDING</code>
	 * @param comparator
	 *            The comparator, which has been used to compare the single
	 *            items, as an instance of the type {@link Comparator} or null,
	 *            if the items' implementation of the type {@link Comparable}
	 *            has been used instead
	 */
	private void notifyOnSorted(final Collection<DataType> sortedItems,
			final Order order, final Comparator<DataType> comparator) {
		for (ListSortingListener<DataType> listener : sortingListeners) {
			listener.onSorted(sortedItems, order, comparator);
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
	protected final Set<ListSortingListener<DataType>> getSortingListeners() {
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
			final Set<ListSortingListener<DataType>> sortingListeners) {
		ensureNotNull(sortingListeners, "The sorting listeners may not be null");
		this.sortingListeners = sortingListeners;
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a sortable
	 * list of arbitrary items.
	 * 
	 * @param context
	 *            The context, the adapter should belong to, as an instance of
	 *            the class {@link Context}. The context may not be null
	 * @param inflater
	 *            The inflater, which should be used to inflate the views, which
	 *            are used to visualize the adapter's items, as an instance of
	 *            the type {@link Inflater}. The inflater may not be null
	 * @param decorator
	 *            The decorator, which should be used to customize the
	 *            appearance of the views, which are used to visualize the items
	 *            of the adapter, as an instance of the generic type
	 *            DecoratorType. The decorator may not be null
	 * @param items
	 *            A list, which contains the the adapter's items, or an empty
	 *            list, if the adapter should not contain any items
	 * @param allowDuplicates
	 *            True, if duplicate items should be allowed, false otherwise
	 * @param adapterListeners
	 *            A set, which contains the listeners, which should be notified
	 *            when the adapter's underlying data has been modified or an
	 *            empty set, if no listeners should be notified
	 * @param enableStateListeners
	 *            A set, which contains the listeners, which should be notified
	 *            when an item has been disabled or enabled or an empty set, if
	 *            no listeners should be notified
	 * @param numberOfItemStates
	 *            The number of states, the adapter's items may have, as an
	 *            {@link Integer} value. The value must be at least 1
	 * @param triggerItemStateOnClick
	 *            True, if the state of an item should be triggered, when it is
	 *            clicked by the user, false otherwise
	 * @param itemStateListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the state of an item has been changed or an empty set, if
	 *            no listeners should be notified
	 * @param sortingListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been sorted or an empty
	 *            set, if no listeners should be notified
	 */
	protected AbstractSortableListAdapter(final Context context,
			final Inflater inflater, final DecoratorType decorator,
			final List<Item<DataType>> items, final boolean allowDuplicates,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListEnableStateListener<DataType>> enableStateListeners,
			final int numberOfItemStates,
			final boolean triggerItemStateOnClick,
			final Set<ListItemStateListener<DataType>> itemStateListeners,
			final Set<ListSortingListener<DataType>> sortingListeners) {
		super(context, inflater, decorator, items, allowDuplicates,
				adapterListeners, enableStateListeners, numberOfItemStates,
				triggerItemStateOnClick, itemStateListeners);
		setSortingListeners(sortingListeners);
	}

	@Override
	public final void sort() {
		sort(Order.ASCENDING);

	}

	@Override
	public final void sort(final Order order) {
		ensureNotNull(order, "The order may not be null");

		if (order == Order.ASCENDING) {
			Collections.sort(getItems());
		} else {
			Collections.sort(getItems(), Collections.reverseOrder());
		}

		notifyOnSorted(getAllItems(), order, null);
		notifyDataSetChanged();
	}

	@Override
	public final void sort(final Comparator<DataType> comparator) {
		sort(Order.ASCENDING, comparator);
	}

	@Override
	public final void sort(final Order order,
			final Comparator<DataType> comparator) {
		ensureNotNull(order, "The order may not be null");
		Comparator<Item<DataType>> itemComparator = new ItemComparator<DataType>(
				comparator);

		if (order == Order.ASCENDING) {
			Collections.sort(getItems(), itemComparator);
		} else {
			Collections.sort(getItems(),
					Collections.reverseOrder(itemComparator));
		}

		notifyOnSorted(getAllItems(), order, comparator);
		notifyDataSetChanged();
	}

	@Override
	public final void addSortingListner(
			final ListSortingListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		sortingListeners.add(listener);
	}

	@Override
	public final void removeSortingListener(
			final ListSortingListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		sortingListeners.remove(listener);
	}

	@Override
	public void onSaveInstanceState(final Bundle outState) {
		super.onSaveInstanceState(outState);

		SerializableWrapper<Set<ListSortingListener<DataType>>> wrappedSortingListeners = new SerializableWrapper<Set<ListSortingListener<DataType>>>(
				getSortingListeners());
		outState.putSerializable(SORTING_LISTENERS_BUNDLE_KEY,
				wrappedSortingListeners);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onRestoreInstanceState(final Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		if (savedInstanceState != null) {
			SerializableWrapper<Set<ListSortingListener<DataType>>> wrappedSortingListeners = (SerializableWrapper<Set<ListSortingListener<DataType>>>) savedInstanceState
					.getSerializable(SORTING_LISTENERS_BUNDLE_KEY);
			setSortingListeners(wrappedSortingListeners.getWrappedInstance());

			notifyDataSetChanged();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + sortingListeners.hashCode();
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractSortableListAdapter<?, ?> other = (AbstractSortableListAdapter<?, ?>) obj;
		if (!sortingListeners.equals(other.sortingListeners))
			return false;
		return true;
	}

	@Override
	public abstract AbstractSortableListAdapter<DataType, DecoratorType> clone()
			throws CloneNotSupportedException;

}